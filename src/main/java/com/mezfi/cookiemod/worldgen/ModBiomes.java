package com.mezfi.cookiemod.worldgen;

import com.mezfi.cookiemod.CookieMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

/** Biome keys. The cookie biome itself is defined by a datapack JSON. */
public final class ModBiomes {
    private ModBiomes() {}

    public static final ResourceKey<Biome> COOKIE_BIOME = ResourceKey.create(
            Registries.BIOME, ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "cookie_biome"));
}
