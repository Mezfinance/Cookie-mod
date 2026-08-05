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
 * <p>Body: a wide cream cake slab (red sprinkles + red maze) with a cube on top; the
 * chocolate face is stuck to the <b>front of the top cube</b>; a belly hangs below. Each
 * arm dangles <b>down-and-out at 45°</b>, separated from the chest, as an alternating
 * cascade: waffle block → mini cream chest <i>prism</i> → waffle block → <i>half of the
 * body chest slab</i> (a wide flat prism, kinked 45°) → waffle-block fist (angled, embedded).
 */
public class WaffleMageModel<T extends Mob> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart topCube;
    private final ModelPart belly;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public WaffleMageModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.topCube = root.getChild("top");   // the head; the face is glued to it as a child
        this.belly = root.getChild("belly");
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
        // The head: a cube sitting on top-centre. The face is a CHILD so it stays glued
        // and moves with the head.
        PartDefinition head = root.addOrReplaceChild("top",
                CubeListBuilder.create().texOffs(48, 0).addBox(-5F, -11F, -5F, 10F, 10F, 10F),
                PartPose.offset(0F, 5F, 0F));
        head.addOrReplaceChild("face",
                CubeListBuilder.create().texOffs(96, 0).addBox(-4F, -8F, -9F, 8F, 6F, 4F),
                PartPose.ZERO);
        // Belly under the slab.
        root.addOrReplaceChild("belly",
                CubeListBuilder.create().texOffs(0, 44).addBox(-7F, 4F, -6F, 14F, 9F, 11F),
                PartPose.offset(0F, 9F, 0F));

        addArm(root, "left_arm", 1F);
        addArm(root, "right_arm", -1F);

        return LayerDefinition.create(mesh, 128, 128);
    }

    /**
     * One dangling arm: a shoulder pivot (separated from the chest) with a 45° down-out
     * cascade. side = +1 (left / +x) or -1 (right / -x).
     */
    private static void addArm(PartDefinition root, String name, float side) {
        PartDefinition arm = root.addOrReplaceChild(name,
                CubeListBuilder.create(), PartPose.offset(side * 15F, 7F, 0F));

        // Every segment steps equal x and y from the shoulder → a true 45° down-out line,
        // starting from the first block (no horizontal stub).
        arm.addOrReplaceChild(name + "_s0",  // waffle block
                waffleCube(8F), PartPose.offset(side * 5F, 5F, 0F));
        arm.addOrReplaceChild(name + "_s1",  // mini cream chest PRISM
                miniChest(), PartPose.offset(side * 10F, 10F, 0F));
        arm.addOrReplaceChild(name + "_s2",  // waffle block
                waffleCube(8F), PartPose.offset(side * 15F, 15F, 0F));
        arm.addOrReplaceChild(name + "_s3",  // HALF of the body chest slab, kinked 45°, over s2
                halfChest(), PartPose.offsetAndRotation(side * 18F, 18F, 0F, 0F, 0F, side * 0.785F));
        arm.addOrReplaceChild(name + "_s4",  // waffle-block fist, slightly angled, embedded in s3
                waffleCube(8F), PartPose.offsetAndRotation(side * 21F, 21F, 0F, 0F, 0F, side * 0.35F));
    }

    private static CubeListBuilder waffleCube(float s) {
        return CubeListBuilder.create().texOffs(0, 96).addBox(-s / 2, -s / 2, -s / 2, s, s, s);
    }

    /** Mini cream chest block — a small rectangular prism (wider than tall), not a cube. */
    private static CubeListBuilder miniChest() {
        return CubeListBuilder.create().texOffs(0, 24).addBox(-4.5F, -2F, -3F, 9F, 4F, 6F);
    }

    /** Half of the actual body chest slab (28×7×14 → 14×4×7): a wide flat prism. */
    private static CubeListBuilder halfChest() {
        return CubeListBuilder.create().texOffs(0, 66).addBox(-7F, -2F, -3.5F, 14F, 4F, 7F);
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
        // The head bobs and does the looking; the glued face (its child) rides along.
        this.topCube.y = 5F + bob * 1.2F;
        this.topCube.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.topCube.xRot = headPitch * ((float) Math.PI / 180F);

        // Arms sway from the shoulder, out of phase with the body.
        float sway = Mth.sin(ageInTicks * 0.07F) * 0.10F;
        this.leftArm.y = 7F + bob;
        this.leftArm.zRot = sway;
        this.rightArm.y = 7F - bob;
        this.rightArm.zRot = -sway;
    }
}
