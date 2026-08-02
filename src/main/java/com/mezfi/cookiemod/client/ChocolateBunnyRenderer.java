package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.ChocolateBunnyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Renders the chocolate bunny (rabbit geometry, brown chocolate skin). */
public class ChocolateBunnyRenderer
        extends MobRenderer<ChocolateBunnyEntity, ChocolateBunnyModel<ChocolateBunnyEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "textures/entity/chocolate_bunny.png");

    public ChocolateBunnyRenderer(EntityRendererProvider.Context context) {
        super(context, new ChocolateBunnyModel<>(context.bakeLayer(ModModelLayers.CHOCOLATE_BUNNY)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(ChocolateBunnyEntity entity) {
        return TEXTURE;
    }
}
