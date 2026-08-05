package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.CookieSoldierEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renders the cookie soldier: a humanoid with a red-feather helmet plume
 * ({@link CookieSoldierModel}) and a hand-reconstructed skin (gray armour, purple
 * buttons, white-eyed smiling face) built from the reference footage.
 */
public class CookieSoldierRenderer extends MobRenderer<CookieSoldierEntity, CookieSoldierModel<CookieSoldierEntity>> {

    /** Recruited: gray armour, feather, buttons, smiling face. */
    private static final ResourceLocation TAMED =
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "textures/entity/cookie_soldier.png");
    /** Wild cookie dude: plain, no armour/feather/smile (the feather geometry is transparent here). */
    private static final ResourceLocation UNTAMED =
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "textures/entity/cookie_dude.png");

    public CookieSoldierRenderer(EntityRendererProvider.Context context) {
        super(context, new CookieSoldierModel<>(context.bakeLayer(ModModelLayers.COOKIE_SOLDIER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(CookieSoldierEntity entity) {
        return entity.isTame() ? TAMED : UNTAMED;
    }
}
