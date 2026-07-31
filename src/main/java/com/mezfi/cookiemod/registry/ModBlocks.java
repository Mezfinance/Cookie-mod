package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Custom blocks. Phase 1 introduces the cookie-biome building blocks; more flavours
 * (waffle, chocolate, candy variants) are added as the phases progress.
 */
public final class ModBlocks {
    private ModBlocks() {}

    public static final DeferredRegister.Blocks REGISTER =
            DeferredRegister.createBlocks(CookieMod.MODID);

    /** The cookie-biome ground block: golden waffle top, brown cookie-chip sides (see biome map). */
    public static final DeferredBlock<Block> COOKIE_BLOCK = REGISTER.registerSimpleBlock(
            "cookie_block",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(0.6F)
                    .sound(SoundType.WOOL));
}
