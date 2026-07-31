package com.mezfi.cookiemod;

import com.mezfi.cookiemod.entity.CookieSoldierEntity;
import com.mezfi.cookiemod.registry.ModEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

/** Mod-bus event handlers (attributes, etc.). Auto-registered via the annotation. */
@EventBusSubscriber(modid = CookieMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEvents {
    private ModEvents() {}

    @SubscribeEvent
    static void onEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.COOKIE_SOLDIER.get(), CookieSoldierEntity.createAttributes().build());
    }
}
