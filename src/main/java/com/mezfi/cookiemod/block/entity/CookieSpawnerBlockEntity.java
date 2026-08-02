package com.mezfi.cookiemod.block.entity;

import com.mezfi.cookiemod.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Block entity backing the mod's mob spawners. Wraps a vanilla {@link BaseSpawner}, so it
 * inherits all of vanilla's behaviour — 16-block player range, spawn-space checks (cap it
 * with a block to stop spawns), and per-mob spawn rules (light-gate the hostile ones with
 * torches). See MECHANICS_SPEC §9.3.
 */
public class CookieSpawnerBlockEntity extends BlockEntity {

    private final BaseSpawner spawner = new BaseSpawner() {
        @Override
        public void broadcastEvent(Level level, BlockPos pos, int eventId) {
            level.blockEvent(pos, getBlockState().getBlock(), eventId, 0);
        }

        @Override
        public void setNextSpawnData(@Nullable Level level, BlockPos pos, SpawnData data) {
            super.setNextSpawnData(level, pos, data);
            if (level != null) {
                BlockState state = level.getBlockState(pos);
                level.sendBlockUpdated(pos, state, state, 4);
            }
        }
    };

    public CookieSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COOKIE_SPAWNER.get(), pos, state);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.spawner.load(this.level, this.worldPosition, tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        this.spawner.save(tag);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, CookieSpawnerBlockEntity be) {
        be.spawner.clientTick(level, pos);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CookieSpawnerBlockEntity be) {
        be.spawner.serverTick((ServerLevel) level, pos);
    }

    /** Configure which mob this spawner produces (called on placement). */
    public void setEntityId(EntityType<?> type, RandomSource random) {
        this.spawner.setEntityId(type, this.level, random, this.worldPosition);
        this.setChanged();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = this.saveCustomOnly(registries);
        tag.remove("SpawnPotentials");
        return tag;
    }

    @Override
    public boolean triggerEvent(int id, int param) {
        return this.spawner.onEventTriggered(this.level, id) || super.triggerEvent(id, param);
    }

    public BaseSpawner getSpawner() {
        return this.spawner;
    }
}
