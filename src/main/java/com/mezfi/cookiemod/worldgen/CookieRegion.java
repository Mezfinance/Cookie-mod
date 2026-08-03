package com.mezfi.cookiemod.worldgen;

import com.mezfi.cookiemod.CookieMod;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

/**
 * Places the cookie biome into the overworld (MECHANICS_SPEC §12 / BIOME_MAP). It occupies a
 * warm, fairly dry, inland climate band; vanilla fills everything else.
 */
public class CookieRegion extends Region {

    public CookieRegion(int weight) {
        super(ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "cookie_region"),
                RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry,
                          Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        Climate.Parameter full = Climate.Parameter.span(-1.0F, 1.0F);
        Climate.ParameterPoint point = Climate.parameters(
                Climate.Parameter.span(0.2F, 0.9F),    // temperature: warm
                Climate.Parameter.span(-0.4F, 0.3F),   // humidity: dry-neutral
                Climate.Parameter.span(0.1F, 1.0F),    // continentalness: inland
                full,                                  // erosion
                full,                                  // depth
                full,                                  // weirdness
                0.0F);
        this.addBiome(mapper, point, ModBiomes.COOKIE_BIOME);
    }
}
