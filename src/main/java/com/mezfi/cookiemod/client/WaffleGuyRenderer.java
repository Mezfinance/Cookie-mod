package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.WaffleGuyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

/** Renders the waffle guy: humanoid ({@link WaffleGuyModel}) holding its lollipop. */
public class WaffleGuyRenderer extends MobRenderer<WaffleGuyEntity, WaffleGuyModel<WaffleGuyEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "textures/entity/waffle_guy.png");

    public WaffleGuyRenderer(EntityRendererProvider.Context context) {
        super(context, new WaffleGuyModel<>(context.bakeLayer(ModModelLayers.WAFFLE_GUY)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(WaffleGuyEntity entity) {
        return TEXTURE;
    }
}
