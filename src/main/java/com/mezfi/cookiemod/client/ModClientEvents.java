package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.registry.ModEntities;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/** Client-only mod-bus handlers: model layers and entity renderers. */
@EventBusSubscriber(modid = CookieMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModClientEvents {
    private ModClientEvents() {}

    @SubscribeEvent
    static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.COOKIE_SOLDIER,
                () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F), 64, 64));
        event.registerLayerDefinition(ModModelLayers.WAFFLE_GUY,
                () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F), 64, 64));
        event.registerLayerDefinition(ModModelLayers.CAKE_GOLEM,
                () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F), 64, 64));
    }

    @SubscribeEvent
    static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.COOKIE_SOLDIER.get(), CookieSoldierRenderer::new);
        event.registerEntityRenderer(ModEntities.WAFFLE_GUY.get(), WaffleGuyRenderer::new);
        event.registerEntityRenderer(ModEntities.CAKE_GOLEM.get(), CakeGolemRenderer::new);
    }
}
