package com.mezfi.cookiemod.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;

/**
 * Cookie soldier model: a standard humanoid plus a thin red feather rising from the
 * top of the helmet (a child of the head, so it turns with the head). Everything else
 * — armour, buttons, face — is painted in the skin texture.
 */
public class CookieSoldierModel<T extends LivingEntity> extends HumanoidModel<T> {

    public CookieSoldierModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F);
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.getChild("head");
        // Red feather plume: 1x7x1, sitting on top of the head (head top is at local y=-8).
        head.addOrReplaceChild("feather",
                CubeListBuilder.create()
                        .texOffs(56, 16)
                        .addBox(-0.5F, -15.0F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }
}
