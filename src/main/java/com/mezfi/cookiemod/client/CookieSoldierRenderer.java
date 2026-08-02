package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.CookieSoldierEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renders the cookie soldier: a humanoid with a red-feather helmet plume
 * ({@link CookieSoldierModel}) and a hand-reconstructed skin (gray armour, purple
 * buttons, white-eyed smiling face) built from the reference footage.
 */
public class CookieSoldierRenderer extends MobRenderer<CookieSoldierEntity, CookieSoldierModel<CookieSoldierEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "textures/entity/cookie_soldier.png");

    public CookieSoldierRenderer(EntityRendererProvider.Context context) {
        super(context, new CookieSoldierModel<>(context.bakeLayer(ModModelLayers.COOKIE_SOLDIER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(CookieSoldierEntity entity) {
        return TEXTURE;
    }
}
