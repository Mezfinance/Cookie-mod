package com.mezfi.cookiemod.block;

import com.mezfi.cookiemod.registry.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Cream-coloured candy-grass tuft that carpets the cookie biome (BIOME_MAP §1/§3). */
public class CandyGrassBlock extends BushBlock {

    public static final MapCodec<CandyGrassBlock> CODEC = simpleCodec(CandyGrassBlock::new);
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public CandyGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.COOKIE_SURFACE.get())
                || state.is(ModBlocks.COOKIE_BLOCK.get())
                || state.is(BlockTags.DIRT);
    }
}
