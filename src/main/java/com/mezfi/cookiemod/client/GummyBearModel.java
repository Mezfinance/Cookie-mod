package com.mezfi.cookiemod.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.PolarBearModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

/**
 * Gummy bear model. Reuses the polar-bear cuboid geometry (the gummy bears are re-skinned
 * polar bears) but animates generically, so it runs on a plain {@link Mob} rather than a
 * vanilla PolarBear (whose model casts to PolarBear and would crash here).
 */
public class GummyBearModel<T extends Mob> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public GummyBearModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
    }

    /** Borrow the vanilla polar-bear cuboid layout. */
    public static LayerDefinition createBodyLayer() {
        return PolarBearModel.createBodyLayer();
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        float speed = 0.6662F;
        float amp = 1.4F * limbSwingAmount;
        this.rightHindLeg.xRot = Mth.cos(limbSwing * speed) * amp;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * speed + (float) Math.PI) * amp;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * speed + (float) Math.PI) * amp;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * speed) * amp;
    }
}
