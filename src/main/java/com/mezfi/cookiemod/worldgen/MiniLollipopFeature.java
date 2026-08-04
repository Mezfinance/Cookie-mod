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
 * Tiny round-checkerboard lollipops at grass height (BIOME_MAP §3): a 1-block marshmallow stem
 * topped with a small round candy/white head, in green or brown. Scattered as ground cover
 * among the candy grass, in small clusters.
 */
public class MiniLollipopFeature extends Feature<NoneFeatureConfiguration> {

    private static final int CLUSTER_MIN = 4;
    private static final int CLUSTER_MAX = 7;
    private static final int CLUSTER_RADIUS = 3;

    public MiniLollipopFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int n = CLUSTER_MIN + random.nextInt(CLUSTER_MAX - CLUSTER_MIN + 1);
        int placed = 0;
        for (int i = 0; i < n; i++) {
            int x = origin.getX() + random.nextInt(CLUSTER_RADIUS * 2 + 1) - CLUSTER_RADIUS;
            int z = origin.getZ() + random.nextInt(CLUSTER_RADIUS * 2 + 1) - CLUSTER_RADIUS;
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
        BlockState candy = random.nextBoolean()
                ? ModBlocks.MINTY_HARD_CANDY_BLOCK.get().defaultBlockState()   // green
                : ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState();        // brown
        Direction side = Direction.Plane.HORIZONTAL.getRandomDirection(random);

        // 3-tall mini round lollipop: stem, a 3-wide checker middle, a candy cap.
        level.setBlock(base, stem, 2);                                 // y0 short stem
        BlockPos mid = base.above(1);
        level.setBlock(mid, white, 2);                                 // head centre
        level.setBlock(mid.relative(side, 1), candy, 2);               // head sides
        level.setBlock(mid.relative(side, -1), candy, 2);
        level.setBlock(base.above(2), candy, 2);                       // head cap
        return true;
    }
}
