package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.block.ArenaSpawnerBlock;
import com.mezfi.cookiemod.block.CakeGolemAltarBlock;
import com.mezfi.cookiemod.block.TowerBunnySpawnerBlock;
import com.mezfi.cookiemod.block.WaffleMageAltarBlock;
import com.mezfi.cookiemod.block.CandyGrassBlock;
import com.mezfi.cookiemod.block.ChocolateBunnySpawnerBlock;
import com.mezfi.cookiemod.block.GingerbreadFurnaceBlock;
import com.mezfi.cookiemod.block.WaffleGuySpawnerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom blocks — the cookie-biome building set (see {@code reference/BIOME_MAP.md}
 * and {@code reference/ENTITY_CATALOG.md}).
 *
 * <p>Every entry is added to {@link #ALL} so item-blocks, the creative tab, and data
 * generation can iterate the full set without repeating names.
 */
public final class ModBlocks {
    private ModBlocks() {}

    public static final DeferredRegister.Blocks REGISTER =
            DeferredRegister.createBlocks(CookieMod.MODID);

    /** Registration order = creative-tab order. */
    public static final List<DeferredBlock<Block>> ALL = new ArrayList<>();

    // --- edible / building blocks (soft) ---
    public static final DeferredBlock<Block> COOKIE_BLOCK =
            soft("cookie_block", MapColor.COLOR_BROWN);
    public static final DeferredBlock<Block> FROSTED_COOKIE_BLOCK =
            soft("frosted_cookie_block", MapColor.SAND);
    public static final DeferredBlock<Block> WAFFLE_BLOCK =
            soft("waffle_block", MapColor.GOLD);
    /** Waffle slab and lattice — building parts used by the Waffle Tower's roof pavilion. */
    public static final DeferredBlock<SlabBlock> WAFFLE_SLAB =
            REGISTER.registerBlock("waffle_slab", SlabBlock::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD).strength(0.6F).sound(SoundType.WOOL));
    public static final DeferredBlock<FenceBlock> WAFFLE_LATTICE =
            REGISTER.registerBlock("waffle_lattice", FenceBlock::new, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD).strength(0.6F).sound(SoundType.WOOL));
    public static final DeferredBlock<Block> CHOCOLATE_BLOCK =
            soft("chocolate_block", MapColor.TERRACOTTA_BROWN);
    public static final DeferredBlock<Block> CHOCOLATE_BRICK_BLOCK =
            soft("chocolate_brick_block", MapColor.COLOR_BROWN);

    /** Cookie-biome surface: golden-waffle top, brown chocolate-chip sides (BIOME_MAP §2). */
    public static final DeferredBlock<Block> COOKIE_SURFACE =
            soft("cookie_surface", MapColor.GOLD);

    /** Flat matte white — the lollipop/candy-cane stems (distinct from the glossy candies). */
    public static final DeferredBlock<Block> MARSHMALLOW_BLOCK =
            soft("marshmallow_block", MapColor.SNOW);

    // --- candy / hard-candy blocks (harder, glassy) ---
    public static final DeferredBlock<Block> CANDY_CANE_BLOCK =
            candy("candy_cane_block", MapColor.COLOR_RED, 0.8F);
    public static final DeferredBlock<Block> RASPBERRY_HARD_CANDY_BLOCK =
            candy("raspberry_hard_candy_block", MapColor.COLOR_RED, 1.0F);
    public static final DeferredBlock<Block> GRAPE_HARD_CANDY_BLOCK =
            candy("grape_hard_candy_block", MapColor.COLOR_PURPLE, 1.0F);
    public static final DeferredBlock<Block> ANISEED_HARD_CANDY_BLOCK =
            candy("aniseed_hard_candy_block", MapColor.SNOW, 1.0F);
    public static final DeferredBlock<Block> MINTY_HARD_CANDY_BLOCK =
            candy("minty_hard_candy_block", MapColor.COLOR_CYAN, 1.0F);
    /** Black/red/white banded "humbug" candy — the striped arena pillars (arena spec §7). */
    public static final DeferredBlock<Block> HUMBUG_CANDY_BLOCK =
            candy("humbug_candy_block", MapColor.COLOR_BLACK, 1.0F);

    // --- mob spawners (MECHANICS_SPEC §9.3); not in ALL (custom type) — handled explicitly ---
    public static final DeferredBlock<WaffleGuySpawnerBlock> WAFFLE_GUY_SPAWNER =
            REGISTER.registerBlock("waffle_guy_spawner", WaffleGuySpawnerBlock::new, spawnerProps(MapColor.GOLD));
    public static final DeferredBlock<ChocolateBunnySpawnerBlock> CHOCOLATE_BUNNY_SPAWNER =
            REGISTER.registerBlock("chocolate_bunny_spawner", ChocolateBunnySpawnerBlock::new, spawnerProps(MapColor.COLOR_BROWN));

    /** Cake Golem arena's waffle-guy spawner — direct code spawning, any light. Not in ALL
     *  (no creative item); placed only by {@code CakeGolemArenaFeature}. */
    public static final DeferredBlock<ArenaSpawnerBlock> ARENA_SPAWNER =
            REGISTER.registerBlock("arena_spawner", ArenaSpawnerBlock::new, spawnerProps(MapColor.GOLD));
    /** Waffle Tower's chocolate-bunny spawner — same direct-code BE, fields bunnies. Not in ALL. */
    public static final DeferredBlock<TowerBunnySpawnerBlock> TOWER_BUNNY_SPAWNER =
            REGISTER.registerBlock("tower_bunny_spawner", TowerBunnySpawnerBlock::new, spawnerProps(MapColor.COLOR_BROWN));

    /**
     * Hidden one-shot boss trigger buried at a Cake Golem arena's centre. Random-ticks to
     * spawn the golem on player approach, then turns to cookie block. Not in ALL (no creative
     * item) — placed only by {@code CakeGolemArenaFeature}.
     */
    public static final DeferredBlock<CakeGolemAltarBlock> CAKE_GOLEM_ALTAR =
            REGISTER.registerBlock("cake_golem_altar", CakeGolemAltarBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BROWN)
                            .strength(0.6F)
                            .sound(SoundType.WOOL)
                            .randomTicks());

    /**
     * Hidden one-shot boss trigger at a Waffle Tower's top. Random-ticks to release the
     * Waffle Mage once a player is near and the defenders are cleared, then turns to waffle
     * block. Not in ALL (no creative item) — placed only by {@code WaffleTowerFeature}.
     */
    public static final DeferredBlock<WaffleMageAltarBlock> WAFFLE_MAGE_ALTAR =
            REGISTER.registerBlock("waffle_mage_altar", WaffleMageAltarBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_YELLOW)
                            .strength(0.6F)
                            .sound(SoundType.WOOL)
                            .randomTicks());

    /** Milk fluid's block form (MECHANICS_SPEC §5.2). Rendered by the fluid, not a model. */
    @SuppressWarnings("deprecation")
    public static final DeferredBlock<LiquidBlock> MILK =
            REGISTER.registerBlock("milk", props -> new LiquidBlock(
                    com.mezfi.cookiemod.registry.ModFluids.MILK_SOURCE.get(), props),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.SNOW)
                            .replaceable()
                            .noCollission()
                            .strength(100.0F)
                            .pushReaction(PushReaction.DESTROY)
                            .noLootTable()
                            .liquid());

    /** Cookie Army Factory (§3.6, §10) — auto-spawns soldiers. Drops itself when mined. */
    public static final DeferredBlock<GingerbreadFurnaceBlock> GINGERBREAD_FURNACE =
            REGISTER.registerBlock("gingerbread_furnace", GingerbreadFurnaceBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BROWN)
                            .strength(3.5F)
                            .sound(SoundType.WOOD)
                            .requiresCorrectToolForDrops());

    /** Spawner cage: hard, needs a tool, drops nothing (see loot tables). */
    private static BlockBehaviour.Properties spawnerProps(MapColor color) {
        return BlockBehaviour.Properties.of()
                .mapColor(color)
                .strength(5.0F)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
                .noOcclusion();
    }

    /** Candy-grass tuft (BIOME_MAP §1/§3). Not in ALL — a plant, handled explicitly. */
    public static final DeferredBlock<CandyGrassBlock> CANDY_GRASS =
            REGISTER.registerBlock("candy_grass", CandyGrassBlock::new, plantProps(MapColor.TERRACOTTA_WHITE));

    /** Flower-sized round-checker lollipops that dot the grass (BIOME_MAP §3). */
    public static final DeferredBlock<CandyGrassBlock> MINI_LOLLIPOP_GREEN =
            REGISTER.registerBlock("mini_lollipop_green", CandyGrassBlock::new, plantProps(MapColor.COLOR_GREEN));
    public static final DeferredBlock<CandyGrassBlock> MINI_LOLLIPOP_BROWN =
            REGISTER.registerBlock("mini_lollipop_brown", CandyGrassBlock::new, plantProps(MapColor.COLOR_BROWN));

    /** Shared props for the candy plants (flower-like, no collision). */
    private static BlockBehaviour.Properties plantProps(MapColor color) {
        return BlockBehaviour.Properties.of()
                .mapColor(color)
                .noCollission()
                .instabreak()
                .sound(SoundType.GRASS)
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .pushReaction(PushReaction.DESTROY);
    }

    /** Soft, edible/building block: hand-breakable, wool-like. */
    private static DeferredBlock<Block> soft(String name, MapColor color) {
        return track(REGISTER.registerSimpleBlock(name, BlockBehaviour.Properties.of()
                .mapColor(color)
                .strength(0.6F)
                .sound(SoundType.WOOL)));
    }

    /** Hard candy: sturdier, glassy sound. */
    private static DeferredBlock<Block> candy(String name, MapColor color, float strength) {
        return track(REGISTER.registerSimpleBlock(name, BlockBehaviour.Properties.of()
                .mapColor(color)
                .strength(strength)
                .sound(SoundType.GLASS)));
    }

    private static DeferredBlock<Block> track(DeferredBlock<Block> block) {
        ALL.add(block);
        return block;
    }
}
