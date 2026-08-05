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

    private static final float ARM_ANGLE = (float) (Math.PI / 4.0); // 45° per joint

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
        addHand(root, "left_hand", 1F);
        addHand(root, "right_hand", -1F);

        return LayerDefinition.create(mesh, 128, 128);
    }

    /**
     * Upper bone (pieces 1–3): a shoulder pivot rotated 45° down-out (separated from the
     * chest). Piece 1 (waffle) → piece 2 (mini prism, rotated 90° so it reads lengthways
     * between 1 and 3) → piece 3 (waffle) connect end-to-end along the bone.
     * side = +1 (left / +x) or −1 (right / −x).
     */
    private static void addArm(PartDefinition root, String name, float side) {
        PartDefinition arm = root.addOrReplaceChild(name,
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(side * 14F, 7F, 0F, 0F, 0F, -side * ARM_ANGLE));
        arm.addOrReplaceChild(name + "_s0", waffleCube(8F), PartPose.offset(0F, 4F, 0F));       // piece 1
        arm.addOrReplaceChild(name + "_s1", miniPrism(),                                        // piece 2, lengthways
                PartPose.offsetAndRotation(0F, 12.5F, 0F, 0F, 0F, (float) (Math.PI / 2.0)));
        arm.addOrReplaceChild(name + "_s2", waffleCube(8F), PartPose.offset(0F, 21F, 0F));      // piece 3
    }

    /**
     * Hand (pieces 4–5) placed in world coordinates (unrotated) so they sit precisely under
     * the 45°-rotated piece 3. Piece 4 (belly) tucks just below piece 3's corner; piece 5
     * (waffle fist) hangs off the bottom of piece 4, half-embedded.
     */
    private static void addHand(PartDefinition root, String name, float side) {
        root.addOrReplaceChild(name + "_s3", bottomTorso(), PartPose.offset(side * 27F, 27F, 0F));    // piece 4
        root.addOrReplaceChild(name + "_s4", waffleCube(8F), PartPose.offset(side * 27F, 31.5F, 0F)); // piece 5
    }

    private static CubeListBuilder waffleCube(float s) {
        return CubeListBuilder.create().texOffs(0, 96).addBox(-s / 2, -s / 2, -s / 2, s, s, s);
    }

    /** Mini cream chest block — a small rectangular prism. */
    private static CubeListBuilder miniPrism() {
        return CubeListBuilder.create().texOffs(0, 24).addBox(-4.5F, -3F, -3F, 9F, 6F, 6F);
    }

    /** A full-size bottom-torso (belly) block — 14×9×11, same as the body's belly. */
    private static CubeListBuilder bottomTorso() {
        return CubeListBuilder.create().texOffs(0, 44).addBox(-7F, -4.5F, -5.5F, 14F, 9F, 11F);
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

        // Arms/hands hold their built pose so the world-placed hands stay aligned with the
        // upper bone; only the body/head bob animates.
    }
}
