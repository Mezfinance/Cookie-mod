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
 * The Waffle Mage — a floating cake creature (MECHANICS_SPEC §8.3). A wide cream cake slab
 * body with a rounded belly, a chocolate face, and two waffle-cube fists on stubby arms.
 * Parts bob out of phase and the fists drift, reading as a disjointed, hovering boss.
 *
 * <p>Texture zones (128×128): cream + red sprinkles everywhere; a golden waffle patch at
 * (0,96); a chocolate face patch at (100,0).
 */
public class WaffleMageModel<T extends Mob> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart belly;
    private final ModelPart face;
    private final ModelPart leftFist;
    private final ModelPart rightFist;

    public WaffleMageModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.belly = root.getChild("belly");
        this.face = root.getChild("face");
        this.leftFist = root.getChild("left_fist");
        this.rightFist = root.getChild("right_fist");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Wide cream cake slab (main body).
        root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-14F, -4F, -7F, 28F, 8F, 14F),
                PartPose.offset(0F, 8F, 0F));

        // Rounded belly beneath the slab.
        root.addOrReplaceChild("belly",
                CubeListBuilder.create().texOffs(0, 26).addBox(-8F, 0F, -6F, 16F, 10F, 12F),
                PartPose.offset(0F, 14F, 0F));

        // Chocolate face on the front (UV in the brown patch at 100,0).
        root.addOrReplaceChild("face",
                CubeListBuilder.create().texOffs(100, 0).addBox(-4F, -3F, -2F, 8F, 6F, 4F),
                PartPose.offset(0F, 13F, -9F));

        // Waffle-cube fists (UV in the golden patch at 0,96).
        CubeListBuilder fist = CubeListBuilder.create().texOffs(0, 96).addBox(-5F, -5F, -5F, 10F, 10F, 10F);
        root.addOrReplaceChild("left_fist", fist, PartPose.offset(18F, 6F, 0F));
        root.addOrReplaceChild("right_fist", fist, PartPose.offset(-18F, 6F, 0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        float bob = Mth.sin(ageInTicks * 0.12F) * 1.2F;
        this.body.y = 8F + bob;
        this.belly.y = 14F + bob * 0.6F;
        this.face.y = 13F + bob * 0.6F;
        this.face.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.face.xRot = headPitch * ((float) Math.PI / 180F);
        // fists drift out of phase and sway
        this.leftFist.y = 6F - bob;
        this.rightFist.y = 6F + bob;
        this.leftFist.zRot = Mth.sin(ageInTicks * 0.10F) * 0.15F;
        this.rightFist.zRot = -Mth.sin(ageInTicks * 0.10F) * 0.15F;
    }
}
