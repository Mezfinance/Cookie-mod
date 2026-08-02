package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.WaffleGuyEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

/** Renders the waffle guy: humanoid ({@link WaffleGuyModel}) with worn armour and a held lollipop. */
public class WaffleGuyRenderer extends MobRenderer<WaffleGuyEntity, WaffleGuyModel<WaffleGuyEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "textures/entity/waffle_guy.png");

    public WaffleGuyRenderer(EntityRendererProvider.Context context) {
        super(context, new WaffleGuyModel<>(context.bakeLayer(ModModelLayers.WAFFLE_GUY)), 0.5F);
        // Draw worn armour (the gummy set): a custom humanoid mob needs this layer explicitly,
        // otherwise equipped armour is invisible even though it is set on the entity.
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(WaffleGuyEntity entity) {
        return TEXTURE;
    }
}
