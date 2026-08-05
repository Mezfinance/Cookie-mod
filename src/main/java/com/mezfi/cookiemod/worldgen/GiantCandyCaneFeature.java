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
 * A giant blocky candy cane (BIOME_MAP §3): a thick vertical shaft that arcs into a hook at the
 * top, built from a red/white checkerboard of raspberry + aniseed hard candy. Bigger and chunkier
 * than the thin {@link CandyCaneFeature} poles.
 */
public class GiantCandyCaneFeature extends Feature<NoneFeatureConfiguration> {

    private static final int THICK = 2;    // depth in blocks
    private static final int SHAFT_W = 2;  // shaft width in blocks
    private static final double CW = 4.0;  // hook circle centre (along the width axis)
    private static final double RI = 2.0;  // hook inner radius
    private static final double RO = 4.0;  // hook outer radius

    public GiantCandyCaneFeature(Codec<NoneFeatureConfiguration> codec) {
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

        Direction wDir = Direction.Plane.HORIZONTAL.getRandomDirection(random); // hook curls this way
        Direction dDir = wDir.getClockWise();                                   // thickness axis
        int shaftH = 11 + random.nextInt(6);   // 11–16
        int ch = shaftH - 1;
        int down = 3 + random.nextInt(2);       // hook down-stroke length
        int farW = (int) Math.round(CW + (RI + RO) / 2.0);

        // Vertical shaft
        for (int w = 0; w < SHAFT_W; w++) {
            for (int h = 0; h < shaftH; h++) {
                putCell(level, origin, wDir, dDir, w, h, red, white);
            }
        }
        // Arced hook: the upper half of an annulus centred at (CW, ch)
        for (int w = -1; w <= (int) (CW + RO) + 1; w++) {
            for (int h = ch; h <= ch + (int) Math.ceil(RO) + 1; h++) {
                double dist = Math.hypot(w - CW, (double) (h - ch));
                if (dist >= RI - 0.5 && dist <= RO + 0.5) {
                    putCell(level, origin, wDir, dDir, w, h, red, white);
                }
            }
        }
        // Down-stroke on the far side, closing the hook
        for (int w = farW - 1; w <= farW; w++) {
            for (int h = ch - down; h < ch; h++) {
                putCell(level, origin, wDir, dDir, w, h, red, white);
            }
        }
        return true;
    }

    /** Place both thickness layers of one silhouette cell, checker-coloured. */
    private static void putCell(WorldGenLevel level, BlockPos origin, Direction wDir, Direction dDir,
                                int w, int h, BlockState red, BlockState white) {
        for (int t = 0; t < THICK; t++) {
            BlockPos pos = origin.relative(wDir, w).above(h).relative(dDir, t);
            level.setBlock(pos, ((w + h + t) & 1) == 0 ? red : white, 2);
        }
    }
}
