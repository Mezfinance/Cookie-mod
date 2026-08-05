package com.mezfi.cookiemod.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

/**
 * Chocolate bunny model. Reuses the vanilla rabbit cuboid geometry but animates generically,
 * so it runs on a plain {@link Mob} instead of a vanilla Rabbit (whose model casts to Rabbit
 * and would crash here).
 */
public class ChocolateBunnyModel<T extends Mob> extends HierarchicalModel<T> {

    private static final float DEG = (float) Math.PI / 180F;
    private static final float FRONT_REST = (float) (-Math.PI / 18);
    private static final float HAUNCH_REST = (float) (-Math.PI / 9);

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart rightEar;
    private final ModelPart leftEar;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHaunch;
    private final ModelPart leftHaunch;
    private final ModelPart rightHindFoot;
    private final ModelPart leftHindFoot;

    public ChocolateBunnyModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.nose = root.getChild("nose");
        this.rightEar = root.getChild("right_ear");
        this.leftEar = root.getChild("left_ear");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightHaunch = root.getChild("right_haunch");
        this.leftHaunch = root.getChild("left_haunch");
        this.rightHindFoot = root.getChild("right_hind_foot");
        this.leftHindFoot = root.getChild("left_hind_foot");
    }

    /** Borrow the vanilla rabbit cuboid layout. */
    public static LayerDefinition createBodyLayer() {
        return RabbitModel.createBodyLayer();
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        float pitch = headPitch * DEG;
        float yaw = netHeadYaw * DEG;
        this.head.xRot = pitch;
        this.head.yRot = yaw;
        this.nose.xRot = pitch;
        this.nose.yRot = yaw;
        this.rightEar.xRot = pitch;
        this.leftEar.xRot = pitch;

        float swing = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = FRONT_REST + swing;
        this.leftFrontLeg.xRot = FRONT_REST - swing;
        this.rightHindFoot.xRot = -swing;
        this.leftHindFoot.xRot = swing;
        this.rightHaunch.xRot = HAUNCH_REST - swing * 0.5F;
        this.leftHaunch.xRot = HAUNCH_REST + swing * 0.5F;
    }
}
