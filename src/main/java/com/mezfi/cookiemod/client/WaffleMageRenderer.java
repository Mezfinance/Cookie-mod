package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.WaffleMageEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Renders the Waffle Mage — the floating cake boss ({@link WaffleMageModel}), boss-scaled. */
public class WaffleMageRenderer extends MobRenderer<WaffleMageEntity, WaffleMageModel<WaffleMageEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "textures/entity/waffle_mage.png");

    public WaffleMageRenderer(EntityRendererProvider.Context context) {
        super(context, new WaffleMageModel<>(context.bakeLayer(ModModelLayers.WAFFLE_MAGE)), 1.2F);
    }

    @Override
    protected void scale(WaffleMageEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.4F, 1.4F, 1.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(WaffleMageEntity entity) {
        return TEXTURE;
    }
}
