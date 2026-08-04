package com.mezfi.cookiemod.block.entity;

import com.mezfi.cookiemod.entity.WaffleGuyEntity;
import com.mezfi.cookiemod.registry.ModBlockEntities;
import com.mezfi.cookiemod.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * The Cake Golem arena's waffle-guy spawner (arena spec §9). Unlike the vanilla-backed
 * {@link CookieSpawnerBlockEntity}, this spawns directly in code — no light gate, no
 * placement rules — so it reliably fields waffle-guy adds during the boss fight, day or
 * night, even though it was placed by worldgen rather than by a player.
 */
public class ArenaSpawnerBlockEntity extends BlockEntity {

    private static final int INTERVAL = 140;      // ticks between spawn waves (~7 s)
    private static final int IDLE_RECHECK = 40;   // ticks to wait when no player is near
    private static final double ACTIVATE_RANGE = 28.0;
    private static final double NEARBY_RANGE = 14.0;
    private static final int NEARBY_CAP = 6;      // don't exceed this many waffle guys nearby
    private static final int BATCH = 2;           // spawns per wave
    private static final int SPREAD = 4;          // horizontal spawn radius
    private static final int TRIES = 10;          // placement attempts per spawn

    private int cooldown = 60;

    public ArenaSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARENA_SPAWNER.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ArenaSpawnerBlockEntity be) {
        if (--be.cooldown > 0) {
            return;
        }
        ServerLevel server = (ServerLevel) level;
        double cx = pos.getX() + 0.5, cy = pos.getY() + 0.5, cz = pos.getZ() + 0.5;
        if (server.getNearestPlayer(cx, cy, cz, ACTIVATE_RANGE, false) == null) {
            be.cooldown = IDLE_RECHECK; // no one here — check back soon, cheaply
            return;
        }
        be.cooldown = INTERVAL;

        int nearby = server.getEntitiesOfClass(WaffleGuyEntity.class,
                new AABB(pos).inflate(NEARBY_RANGE)).size();
        if (nearby >= NEARBY_CAP) {
            return;
        }

        RandomSource random = server.getRandom();
        int budget = Math.min(BATCH, NEARBY_CAP - nearby);
        for (int i = 0; i < budget; i++) {
            spawnOne(server, pos, random);
        }
    }

    private static void spawnOne(ServerLevel level, BlockPos origin, RandomSource random) {
        for (int t = 0; t < TRIES; t++) {
            int dx = random.nextInt(2 * SPREAD + 1) - SPREAD;
            int dz = random.nextInt(2 * SPREAD + 1) - SPREAD;
            BlockPos at = origin.offset(dx, 0, dz);
            if (!hasRoom(level, at)) {
                continue;
            }
            WaffleGuyEntity guy = ModEntities.WAFFLE_GUY.get().create(level);
            if (guy == null) {
                return;
            }
            guy.moveTo(at.getX() + 0.5D, at.getY(), at.getZ() + 0.5D, random.nextFloat() * 360F, 0F);
            guy.finalizeSpawn(level, level.getCurrentDifficultyAt(at), MobSpawnType.SPAWNER, null);
            level.addFreshEntity(guy);
            level.levelEvent(2004, at, 0); // spawner "poof" particles
            return;
        }
    }

    /** Solid floor below and two clear blocks for the waffle guy's body. */
    private static boolean hasRoom(ServerLevel level, BlockPos at) {
        if (!level.getBlockState(at.below()).isFaceSturdy(level, at.below(), Direction.UP)) {
            return false;
        }
        return level.noCollision(ModEntities.WAFFLE_GUY.get().getSpawnAABB(
                at.getX() + 0.5D, at.getY(), at.getZ() + 0.5D));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Cooldown")) {
            this.cooldown = tag.getInt("Cooldown");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Cooldown", this.cooldown);
    }
}
