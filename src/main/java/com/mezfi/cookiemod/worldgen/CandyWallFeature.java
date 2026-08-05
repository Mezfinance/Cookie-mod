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
 * A red/white candy wall (BIOME_MAP §3): a hand-authored vertical panel that stands on the ground,
 * scattered through the cookie biome. {@code r} = raspberry, {@code w} = white aniseed, {@code .} = gap.
 */
public class CandyWallFeature extends Feature<NoneFeatureConfiguration> {

    private static final String[] PATTERN = {
            "..rrrrr..",
            ".rwwwrwr.",
            "rwrrwrrwr",
            "rrrwrwrwr",
            "rwwrwrwwr",
    };

    public CandyWallFeature(Codec<NoneFeatureConfiguration> codec) {
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

        BlockState red = ModBlocks.RASPBERRY_HARD_CANDY_BLOCK.get().defaultBlockState();
        BlockState white = ModBlocks.ANISEED_HARD_CANDY_BLOCK.get().defaultBlockState();
        Direction side = Direction.Plane.HORIZONTAL.getRandomDirection(random);

        int rows = PATTERN.length;
        int cols = PATTERN[0].length();
        int half = cols / 2;
        for (int r = 0; r < rows; r++) {
            String line = PATTERN[r];
            for (int c = 0; c < cols; c++) {
                char ch = line.charAt(c);
                if (ch == '.') {
                    continue;
                }
                BlockState state = ch == 'w' ? white : red;
                int up = rows - 1 - r; // bottom row sits on the ground
                BlockPos pos = origin.above(up).relative(side, c - half);
                level.setBlock(pos, state, 2);
            }
        }
        return true;
    }
}
