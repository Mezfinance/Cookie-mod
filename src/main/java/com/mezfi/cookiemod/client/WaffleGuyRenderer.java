package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.WaffleGuyEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Renders the waffle guy with a humanoid model and a placeholder texture. */
public class WaffleGuyRenderer extends MobRenderer<WaffleGuyEntity, HumanoidModel<WaffleGuyEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "textures/entity/waffle_guy.png");

    public WaffleGuyRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModModelLayers.WAFFLE_GUY)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(WaffleGuyEntity entity) {
        return TEXTURE;
    }
}
