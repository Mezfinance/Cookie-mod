package com.mezfi.cookiemod.worldgen;

import com.mezfi.cookiemod.registry.ModBlocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

/** Surface rules that pave the cookie biome with cookie blocks (BIOME_MAP §2). */
public final class CookieSurfaceRules {
    private CookieSurfaceRules() {}

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.RuleSource surface =
                SurfaceRules.state(ModBlocks.COOKIE_SURFACE.get().defaultBlockState()); // waffle top
        SurfaceRules.RuleSource cookie =
                SurfaceRules.state(ModBlocks.COOKIE_BLOCK.get().defaultBlockState());    // brown-chip
        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(ModBiomes.COOKIE_BIOME),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface),  // top layer
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, cookie) // a few blocks under
                ));
    }
}
