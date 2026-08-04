package com.mezfi.cookiemod.worldgen;

import com.mezfi.cookiemod.registry.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * Builds a grove of lollipops (BIOME_MAP §3): white marshmallow stems topped with a candy head.
 * Heads are exact hand-authored patterns — a big purple disc, a brown disc, a red disc — plus a
 * short green cube. Each placement drops a clump of varied heights so they gather in patches.
 *
 * <p>In a pattern, a letter cell is the candy colour, {@code w} is white aniseed, {@code .} is a gap.
 */
public class LollipopTreeFeature extends Feature<NoneFeatureConfiguration> {

    private static final String[] PURPLE = {
            ".pwwpp.",
            "pwppwpp",
            "wppwpwp",
            "wpwpwpp",
            "wppwppw",
            "pwpppwp",
            ".pwwwp.",
    };
    private static final String[] BROWN = {
            ".bbw.",
            "bbbww",
            "bbwbb",
            "wwbbb",
            ".wbb.",
    };
    private static final String[] RED = {
            ".rrw.",
            "rwrww",
            "rrwrr",
            "wwrwr",
            ".wrr.",
    };
    /** A small solid red square with a single white centre. */
    private static final String[] SQUARE_RED = {
            "rrr",
            "rwr",
            "rrr",
    };
    /** A small brown/white checker (white corners + centre). */
    private static final String[] SMALL_BROWN = {
            "wbw",
            "bwb",
            "wbw",
    };
    /** The green cube's front face (a flat 3x3 green/white checker) — used on branches. */
    private static final String[] GREEN_SQUARE = {
            "gwg",
            "wgw",
            "gwg",
    };

    private static final int CLUMP_MIN = 5;
    private static final int CLUMP_MAX = 9;
    private static final int CLUMP_RADIUS = 4;

    public LollipopTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int count = CLUMP_MIN + random.nextInt(CLUMP_MAX - CLUMP_MIN + 1);
        int placed = 0;
        for (int i = 0; i < count; i++) {
            int x = origin.getX() + random.nextInt(CLUMP_RADIUS * 2 + 1) - CLUMP_RADIUS;
            int z = origin.getZ() + random.nextInt(CLUMP_RADIUS * 2 + 1) - CLUMP_RADIUS;
            int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
            if (placeOne(level, new BlockPos(x, y, z), random)) {
                placed++;
            }
        }
        return placed > 0;
    }

    private boolean placeOne(WorldGenLevel level, BlockPos base, RandomSource random) {
        if (!level.getBlockState(base.below()).isFaceSturdy(level, base.below(), Direction.UP)) {
            return false;
        }
        if (!level.isEmptyBlock(base)) {
            return false;
        }

        BlockState stem = ModBlocks.MARSHMALLOW_BLOCK.get().defaultBlockState();
        BlockState white = ModBlocks.ANISEED_HARD_CANDY_BLOCK.get().defaultBlockState();
        BlockState raspberry = ModBlocks.RASPBERRY_HARD_CANDY_BLOCK.get().defaultBlockState();

        // Each head has a SET height, tallest to shortest:
        // purple 13, brown 11, red 9, red-square 7, green cube 5, small brown 3.
        int style = random.nextInt(6);
        int height = switch (style) {
            case 0 -> 13; // purple (tallest)
            case 1 -> 11; // brown
            case 2 -> 9;  // red disc
            case 3 -> 7;  // red square
            case 4 -> 5;  // green cube
            default -> 3; // small brown (shortest)
        };

        BlockPos.MutableBlockPos cursor = base.mutable();
        for (int i = 0; i < height; i++) {
            level.setBlock(cursor, stem, 2);
            cursor.move(Direction.UP);
        }

        Direction side = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        switch (style) {
            case 0 -> panel(level, base, height, side, PURPLE,
                    ModBlocks.GRAPE_HARD_CANDY_BLOCK.get().defaultBlockState(), white);
            case 1 -> panel(level, base, height, side, BROWN,
                    ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState(), white);
            case 2 -> panel(level, base, height, side, RED, raspberry, white);
            case 3 -> panel(level, base, height, side, SQUARE_RED, raspberry, white);
            case 4 -> cube(level, base.above(height + 1),
                    ModBlocks.MINTY_HARD_CANDY_BLOCK.get().defaultBlockState(), white);
            default -> panel(level, base, height, side, SMALL_BROWN,
                    ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState(), white);
        }

        // Tall trees (purple/brown/red disc) grow medium-height side branches that carry a small
        // red square or a green cube-face.
        if (style <= 2) {
            BlockState minty = ModBlocks.MINTY_HARD_CANDY_BLOCK.get().defaultBlockState();
            BlockState chocolate = ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState();
            int branches = 1 + random.nextInt(3); // 1–3
            for (int b = 0; b < branches; b++) {
                Direction bDir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                int hb = height / 3 + random.nextInt(Math.max(1, height / 3));
                int armLen = 1 + random.nextInt(2); // 1–2
                BlockPos arm = base.above(hb);
                for (int k = 1; k <= armLen; k++) {
                    level.setBlock(arm.relative(bDir, k), stem, 2);
                }
                BlockPos end = arm.relative(bDir, armLen);
                Direction pSide = bDir.getClockWise();
                switch (random.nextInt(3)) {
                    case 0 -> panelAt(level, end, pSide, SQUARE_RED, raspberry, white);
                    case 1 -> panelAt(level, end, pSide, GREEN_SQUARE, minty, white);
                    default -> panelAt(level, end, pSide, SMALL_BROWN, chocolate, white);
                }
            }
        }
        return true;
    }

    /**
     * Place a hand-authored head as a flat vertical panel, centred on the stem, its bottom row
     * sitting just above the stem top.
     */
    private static void panel(WorldGenLevel level, BlockPos base, int stemHeight, Direction side,
                              String[] pattern, BlockState candy, BlockState white) {
        int rows = pattern.length;
        int cols = pattern[0].length();
        int half = cols / 2;
        for (int r = 0; r < rows; r++) {
            String line = pattern[r];
            for (int c = 0; c < cols; c++) {
                char ch = line.charAt(c);
                if (ch == '.') {
                    continue;
                }
                BlockState state = ch == 'w' ? white : candy;
                int up = stemHeight + (rows - 1 - r); // bottom row = just above the stem top
                BlockPos pos = base.above(up).relative(side, c - half);
                level.setBlock(pos, state, 2);
            }
        }
    }

    /** Place a small pattern centred on a point (used for branch heads). */
    private static void panelAt(WorldGenLevel level, BlockPos center, Direction side,
                                String[] pattern, BlockState candy, BlockState white) {
        int rows = pattern.length;
        int cols = pattern[0].length();
        int hr = rows / 2;
        int hc = cols / 2;
        for (int r = 0; r < rows; r++) {
            String line = pattern[r];
            for (int c = 0; c < cols; c++) {
                char ch = line.charAt(c);
                if (ch == '.') {
                    continue;
                }
                BlockState state = ch == 'w' ? white : candy;
                BlockPos pos = center.above(hr - r).relative(side, c - hc);
                level.setBlock(pos, state, 2);
            }
        }
    }

    /** Solid 3x3x3 cube, checkerboard candy/white — the short green lollipop head. */
    private static void cube(WorldGenLevel level, BlockPos c, BlockState candy, BlockState white) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockState state = ((dx + dy + dz) & 1) == 0 ? candy : white;
                    level.setBlock(c.offset(dx, dy, dz), state, 2);
                }
            }
        }
    }
}
