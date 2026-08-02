package com.mezfi.cookiemod.block;

import com.mezfi.cookiemod.block.entity.CookieSpawnerBlockEntity;
import com.mezfi.cookiemod.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * A themed mob spawner block (MECHANICS_SPEC §9.3). Each subclass fixes which mob it produces.
 * The heavy lifting is done by {@link CookieSpawnerBlockEntity}'s vanilla spawner.
 */
public abstract class CookieSpawnerBlock extends BaseEntityBlock {

    protected CookieSpawnerBlock(Properties properties) {
        super(properties);
    }

    /** Which mob this spawner spawns. */
    protected abstract EntityType<?> spawnedEntityType();

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CookieSpawnerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.COOKIE_SPAWNER.get(),
                level.isClientSide ? CookieSpawnerBlockEntity::clientTick : CookieSpawnerBlockEntity::serverTick);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state,
                            @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof CookieSpawnerBlockEntity be) {
            be.setEntityId(spawnedEntityType(), level.getRandom());
        }
    }
}
