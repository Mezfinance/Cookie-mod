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
 * Builds a grove of lollipops (BIOME_MAP §3): white marshmallow stems topped with one of several
 * candy heads — a square checker disc, a diamond disc, a rounded chocolate ball, or a short
 * green cube. Each placement drops a clump of varied heights so they gather in patches.
 */
public class LollipopTreeFeature extends Feature<NoneFeatureConfiguration> {

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
        int style = random.nextInt(5); // 0,1 = square disc; 2 = diamond; 3 = choc ball; 4 = green cube

        boolean shortStem = style == 4; // the green cube lollipop is short
        int height = shortStem ? 2 + random.nextInt(3) : 5 + random.nextInt(10);

        BlockPos.MutableBlockPos cursor = base.mutable();
        for (int i = 0; i < height; i++) {
            level.setBlock(cursor, stem, 2);
            cursor.move(Direction.UP);
        }

        Direction side = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos head = base.above(height + 1);
        switch (style) {
            case 2 -> diamondDisc(level, head, side, randomCandy(random), white);
            case 3 -> ball(level, head,
                    ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState(), white);   // rounded chocolate
            case 4 -> cube(level, base.above(height + 1),
                    ModBlocks.MINTY_HARD_CANDY_BLOCK.get().defaultBlockState(), white); // short green cube
            default -> squareDisc(level, head, side, randomCandy(random), white);
        }
        return true;
    }

    private static BlockState randomCandy(RandomSource random) {
        return switch (random.nextInt(3)) {
            case 1 -> ModBlocks.GRAPE_HARD_CANDY_BLOCK.get().defaultBlockState();
            case 2 -> ModBlocks.MINTY_HARD_CANDY_BLOCK.get().defaultBlockState();
            default -> ModBlocks.RASPBERRY_HARD_CANDY_BLOCK.get().defaultBlockState();
        };
    }

    /** Flat vertical disc (round), checkerboard candy/white. */
    private static void squareDisc(WorldGenLevel level, BlockPos c, Direction side,
                                   BlockState candy, BlockState white) {
        for (int a = -2; a <= 2; a++) {
            for (int b = -2; b <= 2; b++) {
                if (a * a + b * b > 5) {
                    continue;
                }
                place(level, c.relative(side, a).relative(Direction.UP, b), candy, white, a + b);
            }
        }
    }

    /** Flat vertical diamond (rhombus) disc, checkerboard candy/white. */
    private static void diamondDisc(WorldGenLevel level, BlockPos c, Direction side,
                                    BlockState candy, BlockState white) {
        for (int a = -3; a <= 3; a++) {
            for (int b = -3; b <= 3; b++) {
                if (Math.abs(a) + Math.abs(b) > 3) {
                    continue;
                }
                place(level, c.relative(side, a).relative(Direction.UP, b), candy, white, a + b);
            }
        }
    }

    /** Rounded 3D ball, checkerboard candy/white. */
    private static void ball(WorldGenLevel level, BlockPos c, BlockState candy, BlockState white) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (dx * dx + dy * dy + dz * dz > 5) {
                        continue;
                    }
                    place(level, c.offset(dx, dy, dz), candy, white, dx + dy + dz);
                }
            }
        }
    }

    /** Solid 3x3x3 cube, checkerboard candy/white. */
    private static void cube(WorldGenLevel level, BlockPos c, BlockState candy, BlockState white) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    place(level, c.offset(dx, dy, dz), candy, white, dx + dy + dz);
                }
            }
        }
    }

    private static void place(WorldGenLevel level, BlockPos pos, BlockState candy, BlockState white, int parity) {
        level.setBlock(pos, (parity & 1) == 0 ? candy : white, 2);
    }
}
