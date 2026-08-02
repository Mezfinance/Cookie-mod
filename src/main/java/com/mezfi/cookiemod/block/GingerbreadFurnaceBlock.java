package com.mezfi.cookiemod.block;

import com.mezfi.cookiemod.block.entity.GingerbreadFurnaceBlockEntity;
import com.mezfi.cookiemod.registry.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * The Gingerbread Furnace — the "Cookie Army Factory" (MECHANICS_SPEC §3.6, §10). While a
 * player is nearby it continuously spawns cookie soldiers tamed to them.
 */
public class GingerbreadFurnaceBlock extends BaseEntityBlock {

    public static final MapCodec<GingerbreadFurnaceBlock> CODEC = simpleCodec(GingerbreadFurnaceBlock::new);

    public GingerbreadFurnaceBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GingerbreadFurnaceBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }
        return createTickerHelper(type, ModBlockEntities.GINGERBREAD_FURNACE.get(),
                GingerbreadFurnaceBlockEntity::serverTick);
    }
}
