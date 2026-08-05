package com.mezfi.cookiemod;

import com.mezfi.cookiemod.entity.CakeGolemEntity;
import com.mezfi.cookiemod.entity.ChocolateBunnyEntity;
import com.mezfi.cookiemod.entity.CookieSoldierEntity;
import com.mezfi.cookiemod.entity.GummyBearEntity;
import com.mezfi.cookiemod.entity.WaffleGuyEntity;
import com.mezfi.cookiemod.entity.WaffleMageEntity;
import com.mezfi.cookiemod.registry.ModEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

/** Mod-bus event handlers (attributes, spawn placement, etc.). Auto-registered via the annotation. */
@EventBusSubscriber(modid = CookieMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEvents {
    private ModEvents() {}

    @SubscribeEvent
    static void onEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.COOKIE_SOLDIER.get(), CookieSoldierEntity.createAttributes().build());
        event.put(ModEntities.WAFFLE_GUY.get(), WaffleGuyEntity.createAttributes().build());
        event.put(ModEntities.CAKE_GOLEM.get(), CakeGolemEntity.createAttributes().build());
        event.put(ModEntities.GUMMY_BEAR.get(), GummyBearEntity.createAttributes().build());
        event.put(ModEntities.CHOCOLATE_BUNNY.get(), ChocolateBunnyEntity.createAttributes().build());
        event.put(ModEntities.WAFFLE_MAGE.get(), WaffleMageEntity.createAttributes().build());
    }

    @SubscribeEvent
    static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.GUMMY_BEAR.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.CHOCOLATE_BUNNY.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        // Wild cookie soldiers ("Gingerbread Men") roam the cookie biome untamed, and
        // are recruited by feeding a cookie. TamableAnimal extends Animal, so the standard
        // animal spawn rule (block in animals_spawnable_on + light > 8) applies.
        event.register(ModEntities.COOKIE_SOLDIER.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        // Waffle guys spawn in the cookie biome only (their sole biome spawn entry), and at
        // ANY light level — day or night (MECHANICS_SPEC §9.1). Note: this also lifts the
        // light-gate on the waffle-guy spawner block (torches no longer disable it).
        event.register(ModEntities.WAFFLE_GUY.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkAnyLightMonsterSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
