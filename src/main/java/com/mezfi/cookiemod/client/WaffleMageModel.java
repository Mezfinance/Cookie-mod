package com.mezfi.cookiemod.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

/**
 * The Waffle Mage — a floating, disjointed cake boss (MECHANICS_SPEC §8.3).
 *
 * <p>A wide cream cake slab (red sprinkles + red maze) with a cube on top, a belly and a
 * chocolate face beneath, and two arms raised diagonally up-and-out — each a chain of a
 * cream shoulder, an orange waffle elbow, and a cream fist bearing a chocolate-brick panel.
 * Segments float with gaps and bob independently, reading as a wither-like hovering boss.
 */
public class WaffleMageModel<T extends Mob> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart topCube;
    private final ModelPart belly;
    private final ModelPart face;
    private final ModelPart leftShoulder;
    private final ModelPart leftElbow;
    private final ModelPart leftFist;
    private final ModelPart rightShoulder;
    private final ModelPart rightElbow;
    private final ModelPart rightFist;

    public WaffleMageModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.topCube = root.getChild("top");
        this.belly = root.getChild("belly");
        this.face = root.getChild("face");
        this.leftShoulder = root.getChild("left_shoulder");
        this.leftElbow = root.getChild("left_elbow");
        this.leftFist = root.getChild("left_fist");
        this.rightShoulder = root.getChild("right_shoulder");
        this.rightElbow = root.getChild("right_elbow");
        this.rightFist = root.getChild("right_fist");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Wide cream slab body.
        root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-14F, -3F, -7F, 28F, 7F, 14F),
                PartPose.offset(0F, 9F, 0F));
        // Cube sitting on top-centre.
        root.addOrReplaceChild("top",
                CubeListBuilder.create().texOffs(48, 0).addBox(-5F, -11F, -5F, 10F, 10F, 10F),
                PartPose.offset(0F, 5F, 0F));
        // Belly under the slab.
        root.addOrReplaceChild("belly",
                CubeListBuilder.create().texOffs(0, 44).addBox(-7F, 4F, -6F, 14F, 9F, 11F),
                PartPose.offset(0F, 9F, 0F));
        // Chocolate face on the lower front (UV in the brown patch).
        root.addOrReplaceChild("face",
                CubeListBuilder.create().texOffs(96, 0).addBox(-4F, 4F, -9F, 8F, 6F, 4F),
                PartPose.offset(0F, 9F, 0F));

        // --- arms: cream shoulder → waffle elbow → paneled fist, raised diagonally ---
        addArm(root, "left", 1F);
        addArm(root, "right", -1F);

        return LayerDefinition.create(mesh, 128, 128);
    }

    /** side = +1 (left, +x) or -1 (right, -x). */
    private static void addArm(PartDefinition root, String name, float side) {
        float tilt = side * 0.55F; // splay outward
        root.addOrReplaceChild(name + "_shoulder",
                CubeListBuilder.create().texOffs(0, 24).addBox(-3F, -3F, -3F, 6F, 6F, 6F),
                PartPose.offsetAndRotation(side * 12F, 5F, 0F, 0F, 0F, -tilt));
        root.addOrReplaceChild(name + "_elbow",
                CubeListBuilder.create().texOffs(0, 96).addBox(-3.5F, -3.5F, -3.5F, 7F, 7F, 7F),
                PartPose.offsetAndRotation(side * 17F, -2F, 0F, 0F, 0F, -tilt));
        // fist: cream cube + a chocolate-brick panel on its outer/front face.
        root.addOrReplaceChild(name + "_fist",
                CubeListBuilder.create().texOffs(48, 44).addBox(-4.5F, -4.5F, -4.5F, 9F, 9F, 9F)
                        .texOffs(96, 40).addBox(-4.5F, -4.5F, -5.5F, 9F, 9F, 1F),
                PartPose.offsetAndRotation(side * 23F, -9F, 0F, 0F, 0F, -tilt));
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        float bob = Mth.sin(ageInTicks * 0.10F) * 1.2F;
        this.body.y = 9F + bob;
        this.belly.y = 9F + bob * 0.7F;
        this.face.y = 9F + bob * 0.7F;
        this.topCube.y = 5F + bob * 1.2F;
        this.face.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.face.xRot = headPitch * ((float) Math.PI / 180F);

        // arms flap/float out of phase with the body
        float flap = Mth.sin(ageInTicks * 0.08F) * 0.12F;
        animArm(this.leftShoulder, this.leftElbow, this.leftFist, 1F, bob, flap);
        animArm(this.rightShoulder, this.rightElbow, this.rightFist, -1F, -bob, flap);
    }

    private static void animArm(ModelPart shoulder, ModelPart elbow, ModelPart fist,
                                float side, float bob, float flap) {
        shoulder.z = Mth.sin(flap) * 2F;
        elbow.y = -2F + bob;
        elbow.zRot = -side * (0.55F + flap);
        fist.y = -9F + bob * 1.3F;
        fist.zRot = -side * (0.55F + flap * 1.5F);
    }
}
