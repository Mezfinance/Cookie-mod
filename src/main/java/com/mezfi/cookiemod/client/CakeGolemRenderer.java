package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.CakeGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renders the Cake Golem: iron-golem geometry ({@link CakeGolemModel}) re-skinned in
 * chocolate with a frosted-cookie head, scaled up to boss size.
 */
public class CakeGolemRenderer extends MobRenderer<CakeGolemEntity, CakeGolemModel<CakeGolemEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "textures/entity/cake_golem.png");

    public CakeGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new CakeGolemModel<>(context.bakeLayer(ModModelLayers.CAKE_GOLEM)), 1.1F);
    }

    @Override
    protected void scale(CakeGolemEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.3F, 1.3F, 1.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(CakeGolemEntity entity) {
        return TEXTURE;
    }
}
