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
 * <p>Body: a wide cream cake slab (red sprinkles + red maze) with a cube on top, a belly,
 * and a chocolate face beneath. Each arm hangs <b>down-and-out at 45°</b>, separated from
 * the chest, as an alternating cascade: waffle block → mini cream chest block → waffle
 * block → half-size cream chest block (kinked 45°) → waffle-block fist (slightly angled).
 * Arms are nested under a shoulder pivot so the whole limb sways and floats as one.
 */
public class WaffleMageModel<T extends Mob> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart topCube;
    private final ModelPart belly;
    private final ModelPart face;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public WaffleMageModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.topCube = root.getChild("top");
        this.belly = root.getChild("belly");
        this.face = root.getChild("face");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
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
        // Chocolate face on the lower front.
        root.addOrReplaceChild("face",
                CubeListBuilder.create().texOffs(96, 0).addBox(-4F, 4F, -9F, 8F, 6F, 4F),
                PartPose.offset(0F, 9F, 0F));

        addArm(root, "left_arm", 1F);
        addArm(root, "right_arm", -1F);

        return LayerDefinition.create(mesh, 128, 128);
    }

    /**
     * Builds one dangling arm as a shoulder pivot with a 45° down-out cascade of cubes.
     * side = +1 (left / +x) or -1 (right / -x).
     */
    private static void addArm(PartDefinition root, String name, float side) {
        // Shoulder pivot, separated from the chest edge (body half-width 14).
        PartDefinition arm = root.addOrReplaceChild(name,
                CubeListBuilder.create(), PartPose.offset(side * 15F, 6F, 0F));

        // segment cubes centred on their pivots, cascading down-out (x & y both grow → 45°)
        arm.addOrReplaceChild(name + "_s0",  // waffle block
                waffle(8F), PartPose.offset(side * 2F, 2F, 0F));
        arm.addOrReplaceChild(name + "_s1",  // mini cream chest block
                creamMini(6F), PartPose.offset(side * 7F, 8F, 0F));
        arm.addOrReplaceChild(name + "_s2",  // waffle block
                waffle(8F), PartPose.offset(side * 13F, 14F, 0F));
        arm.addOrReplaceChild(name + "_s3",  // half-size cream chest, kinked 45°, overlapping s2
                creamHalf(4F), PartPose.offsetAndRotation(side * 17F, 18F, 0F, 0F, 0F, side * 0.785F));
        arm.addOrReplaceChild(name + "_s4",  // waffle-block fist, slightly angled, embedded in s3
                waffle(8F), PartPose.offsetAndRotation(side * 20F, 21F, 0F, 0F, 0F, side * 0.35F));
    }

    private static CubeListBuilder waffle(float s) {
        return CubeListBuilder.create().texOffs(0, 96).addBox(-s / 2, -s / 2, -s / 2, s, s, s);
    }

    private static CubeListBuilder creamMini(float s) {
        return CubeListBuilder.create().texOffs(0, 24).addBox(-s / 2, -s / 2, -s / 2, s, s, s);
    }

    private static CubeListBuilder creamHalf(float s) {
        return CubeListBuilder.create().texOffs(28, 24).addBox(-s / 2, -s / 2, -s / 2, s, s, s);
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

        // Arms sway from the shoulder and float out of phase with the body.
        float sway = Mth.sin(ageInTicks * 0.07F) * 0.10F;
        this.leftArm.y = 6F + bob;
        this.leftArm.zRot = sway;
        this.rightArm.y = 6F - bob;
        this.rightArm.zRot = -sway;
    }
}
