package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.GummyBearEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Renders the gummy bear, choosing the colour texture that matches its variant. */
public class GummyBearRenderer extends MobRenderer<GummyBearEntity, GummyBearModel<GummyBearEntity>> {

    private static final ResourceLocation[] TEXTURES = {
            tex("purple"), tex("green"), tex("blue"), tex("yellow"),
    };

    private static ResourceLocation tex(String colour) {
        return ResourceLocation.fromNamespaceAndPath(
                CookieMod.MODID, "textures/entity/gummy_bear/" + colour + ".png");
    }

    public GummyBearRenderer(EntityRendererProvider.Context context) {
        super(context, new GummyBearModel<>(context.bakeLayer(ModModelLayers.GUMMY_BEAR)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(GummyBearEntity entity) {
        int v = entity.getVariant();
        return TEXTURES[(v < 0 || v >= TEXTURES.length) ? 0 : v];
    }
}
