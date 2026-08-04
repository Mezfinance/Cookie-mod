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
 * Miniature candy canes (BIOME_MAP §3): tiny 3-tall hooked canes in green or brown, scattered as
 * ground cover among the candy grass. Each placement drops a small cluster.
 */
public class MiniCandyCaneFeature extends Feature<NoneFeatureConfiguration> {

    private static final int CLUSTER_MIN = 4;
    private static final int CLUSTER_MAX = 7;
    private static final int CLUSTER_RADIUS = 3;

    public MiniCandyCaneFeature(Codec<NoneFeatureConfiguration> codec) {
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

        BlockState white = ModBlocks.ANISEED_HARD_CANDY_BLOCK.get().defaultBlockState();
        BlockState colour = random.nextBoolean()
                ? ModBlocks.MINTY_HARD_CANDY_BLOCK.get().defaultBlockState()      // green
                : ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState();           // brown
        Direction hook = Direction.Plane.HORIZONTAL.getRandomDirection(random);

        level.setBlock(base, colour, 2);                                  // h0 shaft base
        level.setBlock(base.above(1), white, 2);                          // h1 shaft
        level.setBlock(base.above(2), colour, 2);                         // h2 shaft top
        level.setBlock(base.above(2).relative(hook, 1), white, 2);        // hook top
        level.setBlock(base.above(1).relative(hook, 1), colour, 2);       // hook curl down
        return true;
    }
}
