package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.CookieSoldierEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renders the cookie soldier with a standard humanoid model and a placeholder texture.
 * (Swap in a bespoke model/texture once art is ready — see reference/ENTITY_CATALOG.md.)
 */
public class CookieSoldierRenderer extends MobRenderer<CookieSoldierEntity, HumanoidModel<CookieSoldierEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "textures/entity/cookie_soldier.png");

    public CookieSoldierRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModModelLayers.COOKIE_SOLDIER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(CookieSoldierEntity entity) {
        return TEXTURE;
    }
}
