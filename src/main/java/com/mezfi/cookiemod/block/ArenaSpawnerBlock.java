package com.mezfi.cookiemod.block;

import com.mezfi.cookiemod.block.entity.ArenaSpawnerBlockEntity;
import com.mezfi.cookiemod.registry.ModBlockEntities;
import com.mezfi.cookiemod.registry.ModEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * The Cake Golem arena's dedicated waffle-guy spawner (arena spec §9). Backed by
 * {@link ArenaSpawnerBlockEntity}, which spawns directly in code so it works reliably in
 * any light and when placed by worldgen. Placed only by {@code CakeGolemArenaFeature}.
 */
public class ArenaSpawnerBlock extends BaseEntityBlock {

    public static final MapCodec<ArenaSpawnerBlock> CODEC = simpleCodec(ArenaSpawnerBlock::new);

    public ArenaSpawnerBlock(Properties properties) {
        super(properties);
    }

    /** The mob this spawner fields (subclasses override). Read by {@link ArenaSpawnerBlockEntity}. */
    public EntityType<? extends Mob> spawnType() {
        return ModEntities.WAFFLE_GUY.get();
    }

    /** How many of {@link #spawnType()} may be nearby before spawning pauses. */
    public int nearbyCap() {
        return 6;
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
        return new ArenaSpawnerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }
        return createTickerHelper(type, ModBlockEntities.ARENA_SPAWNER.get(),
                ArenaSpawnerBlockEntity::serverTick);
    }
}
