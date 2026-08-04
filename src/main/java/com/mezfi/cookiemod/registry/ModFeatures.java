package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.worldgen.CakeGolemArenaFeature;
import com.mezfi.cookiemod.worldgen.CandyCaneFeature;
import com.mezfi.cookiemod.worldgen.CandyWallFeature;
import com.mezfi.cookiemod.worldgen.GiantCandyCaneFeature;
import com.mezfi.cookiemod.worldgen.LollipopTreeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Custom worldgen features for the cookie biome's candy flora. */
public final class ModFeatures {
    private ModFeatures() {}

    public static final DeferredRegister<Feature<?>> REGISTER =
            DeferredRegister.create(Registries.FEATURE, CookieMod.MODID);

    public static final DeferredHolder<Feature<?>, CandyCaneFeature> CANDY_CANE =
            REGISTER.register("candy_cane", () -> new CandyCaneFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, LollipopTreeFeature> LOLLIPOP_TREE =
            REGISTER.register("lollipop_tree", () -> new LollipopTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, GiantCandyCaneFeature> GIANT_CANDY_CANE =
            REGISTER.register("giant_candy_cane", () -> new GiantCandyCaneFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, CandyWallFeature> CANDY_WALL =
            REGISTER.register("candy_wall", () -> new CandyWallFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, CakeGolemArenaFeature> CAKE_GOLEM_ARENA =
            REGISTER.register("cake_golem_arena", () -> new CakeGolemArenaFeature(NoneFeatureConfiguration.CODEC));
}
