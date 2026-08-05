package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.registry.ModEntities;
import com.mezfi.cookiemod.registry.ModFluids;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

/** Client-only mod-bus handlers: model layers and entity renderers. */
@EventBusSubscriber(modid = CookieMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModClientEvents {
    private ModClientEvents() {}

    @SubscribeEvent
    static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.COOKIE_SOLDIER, CookieSoldierModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.WAFFLE_GUY, WaffleGuyModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.CAKE_GOLEM, CakeGolemModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.GUMMY_BEAR, GummyBearModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.CHOCOLATE_BUNNY, ChocolateBunnyModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.WAFFLE_MAGE, WaffleMageModel::createBodyLayer);
    }

    @SubscribeEvent
    static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.COOKIE_SOLDIER.get(), CookieSoldierRenderer::new);
        event.registerEntityRenderer(ModEntities.WAFFLE_GUY.get(), WaffleGuyRenderer::new);
        event.registerEntityRenderer(ModEntities.CAKE_GOLEM.get(), CakeGolemRenderer::new);
        event.registerEntityRenderer(ModEntities.GUMMY_BEAR.get(), GummyBearRenderer::new);
        event.registerEntityRenderer(ModEntities.CHOCOLATE_BUNNY.get(), ChocolateBunnyRenderer::new);
        event.registerEntityRenderer(ModEntities.WAFFLE_MAGE.get(), WaffleMageRenderer::new);
        event.registerEntityRenderer(ModEntities.WAFFLE_SHARD.get(),
                net.minecraft.client.renderer.entity.ThrownItemRenderer::new);
    }

    @SubscribeEvent
    static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            private final ResourceLocation still =
                    ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "block/milk_still");
            private final ResourceLocation flow =
                    ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "block/milk_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return still;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flow;
            }

            @Override
            public int getTintColor() {
                return 0xFFFFFFFF; // opaque white milk
            }
        }, ModFluids.MILK_TYPE.get());
    }
}
