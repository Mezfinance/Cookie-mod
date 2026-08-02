package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.CakeGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renders the Cake Golem: a humanoid model scaled up to boss size, with a placeholder
 * texture. (A bespoke model comes with the art pass — see reference/ENTITY_CATALOG.md.)
 */
public class CakeGolemRenderer extends MobRenderer<CakeGolemEntity, HumanoidModel<CakeGolemEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "textures/entity/cake_golem.png");

    public CakeGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModModelLayers.CAKE_GOLEM)), 0.9F);
    }

    @Override
    protected void scale(CakeGolemEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.5F, 1.5F, 1.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(CakeGolemEntity entity) {
        return TEXTURE;
    }
}
