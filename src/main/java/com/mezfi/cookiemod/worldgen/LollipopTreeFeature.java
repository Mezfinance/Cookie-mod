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
 * Builds a grove of lollipop trees — tall white stems topped with a flat, vertical checkerboard
 * disc of hard candy (BIOME_MAP §3). Each placement drops a whole clump of lollipops of varied
 * heights, so they gather in distinct patches rather than scattering evenly.
 */
public class LollipopTreeFeature extends Feature<NoneFeatureConfiguration> {

    private static final int CLUMP_MIN = 5;
    private static final int CLUMP_MAX = 9;   // lollipops per grove
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

        BlockState stem = ModBlocks.MARSHMALLOW_BLOCK.get().defaultBlockState(); // flat matte white
        int height = 5 + random.nextInt(10); // 5–14 — interspersed heights across the grove

        BlockPos.MutableBlockPos cursor = base.mutable();
        for (int i = 0; i < height; i++) {
            level.setBlock(cursor, stem, 2);
            cursor.move(Direction.UP);
        }

        // Disc: a checkerboard of one candy flavour and white aniseed.
        BlockState candy = switch (random.nextInt(3)) {
            case 1 -> ModBlocks.GRAPE_HARD_CANDY_BLOCK.get().defaultBlockState();
            case 2 -> ModBlocks.MINTY_HARD_CANDY_BLOCK.get().defaultBlockState();
            default -> ModBlocks.RASPBERRY_HARD_CANDY_BLOCK.get().defaultBlockState();
        };
        BlockState white = ModBlocks.ANISEED_HARD_CANDY_BLOCK.get().defaultBlockState();

        Direction side = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos center = base.above(height + 1);
        for (int a = -2; a <= 2; a++) {
            for (int b = -2; b <= 2; b++) {
                if (a * a + b * b > 5) {
                    continue; // round off the corners
                }
                BlockPos p = center.relative(side, a).relative(Direction.UP, b);
                level.setBlock(p, ((a + b) & 1) == 0 ? candy : white, 2);
            }
        }
        return true;
    }
}
