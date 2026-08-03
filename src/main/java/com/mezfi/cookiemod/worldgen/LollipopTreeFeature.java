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
 * Builds a lollipop tree: a tall white stem topped with a flat, vertical disc of hard candy
 * (a random flavour), reading like a giant lollipop (BIOME_MAP §3).
 */
public class LollipopTreeFeature extends Feature<NoneFeatureConfiguration> {

    public LollipopTreeFeature(Codec<NoneFeatureConfiguration> codec) {
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

        BlockState stem = ModBlocks.ANISEED_HARD_CANDY_BLOCK.get().defaultBlockState(); // white
        int height = 6 + random.nextInt(6); // 6–11 tall

        BlockPos.MutableBlockPos cursor = origin.mutable();
        for (int i = 0; i < height; i++) {
            level.setBlock(cursor, stem, 2);
            cursor.move(Direction.UP);
        }

        // Pick a candy flavour for the disc.
        BlockState candy = switch (random.nextInt(3)) {
            case 1 -> ModBlocks.GRAPE_HARD_CANDY_BLOCK.get().defaultBlockState();
            case 2 -> ModBlocks.MINTY_HARD_CANDY_BLOCK.get().defaultBlockState();
            default -> ModBlocks.RASPBERRY_HARD_CANDY_BLOCK.get().defaultBlockState();
        };

        // A vertical disc (radius ~2) in a random horizontal plane, sitting above the stem top.
        Direction side = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos center = origin.above(height + 1);
        for (int a = -2; a <= 2; a++) {
            for (int b = -2; b <= 2; b++) {
                if (a * a + b * b > 5) {
                    continue; // round off the corners
                }
                BlockPos p = center.relative(side, a).relative(Direction.UP, b);
                level.setBlock(p, candy, 2);
            }
        }
        return true;
    }
}
