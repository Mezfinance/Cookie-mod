package com.mezfi.cookiemod.client;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

/**
 * Cake Golem model. Reuses the iron-golem geometry (bulky, boss-sized — matches the
 * reference silhouette) but animates generically, so it works on a plain {@link Mob}
 * instead of a vanilla IronGolem (whose model casts to IronGolem and would crash here).
 */
public class CakeGolemModel<T extends Mob> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public CakeGolemModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    /** Borrow the vanilla iron-golem cuboid layout. */
    public static LayerDefinition createBodyLayer() {
        return IronGolemModel.createBodyLayer();
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        float swing = Mth.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.rightLeg.xRot = -1.5F * swing;
        this.leftLeg.xRot = 1.5F * swing;
        this.rightArm.xRot = -1.0F * swing;
        this.leftArm.xRot = 1.0F * swing;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
    }
}
