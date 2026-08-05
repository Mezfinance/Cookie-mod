package com.mezfi.cookiemod.worldgen;

import com.mezfi.cookiemod.registry.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * Builds a tall red/white candy structure (BIOME_MAP §3a): mostly hooked candy canes, but
 * sometimes a plain straight striped pole for variety.
 */
public class CandyCaneFeature extends Feature<NoneFeatureConfiguration> {

    public CandyCaneFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        if (!level.getBlockState(origin.below()).isFaceSturdy(level, origin.below(), Direction.UP)) {
            return false;
        }
        if (!level.isEmptyBlock(origin)) {
            return false;
        }

        BlockState cane = ModBlocks.CANDY_CANE_BLOCK.get().defaultBlockState();
        int height = 9 + random.nextInt(8); // 9–16 tall — tall and prominent

        BlockPos.MutableBlockPos cursor = origin.mutable();
        for (int i = 0; i < height; i++) {
            level.setBlock(cursor, cane, 2);
            cursor.move(Direction.UP);
        }

        // 40% of the time leave it as a straight striped pole; otherwise curl a hook over the top.
        if (random.nextFloat() < 0.40F) {
            return true;
        }
        Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos top = origin.above(height - 1);
        BlockPos h1 = top.relative(dir);
        BlockPos h2 = h1.relative(dir);
        level.setBlock(h1, cane, 2);
        level.setBlock(h2, cane, 2);
        level.setBlock(h2.below(), cane, 2);         // curl down
        level.setBlock(h2.below(2), cane, 2);        // longer curl for the taller cane
        return true;
    }
}
