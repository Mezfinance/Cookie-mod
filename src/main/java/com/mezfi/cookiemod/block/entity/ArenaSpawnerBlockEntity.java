package com.mezfi.cookiemod.block.entity;

import com.mezfi.cookiemod.block.ArenaSpawnerBlock;
import com.mezfi.cookiemod.registry.ModBlockEntities;
import com.mezfi.cookiemod.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
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

        EntityType<? extends Mob> type = spawnTypeOf(state);
        int cap = capOf(state);
        int nearby = server.getEntitiesOfClass(Mob.class,
                new AABB(pos).inflate(NEARBY_RANGE), m -> m.getType() == type).size();
        if (nearby >= cap) {
            return;
        }

        RandomSource random = server.getRandom();
        int budget = Math.min(BATCH, cap - nearby);
        for (int i = 0; i < budget; i++) {
            spawnOne(server, pos, random, type);
        }
    }

    private static EntityType<? extends Mob> spawnTypeOf(BlockState state) {
        return state.getBlock() instanceof ArenaSpawnerBlock b ? b.spawnType() : ModEntities.WAFFLE_GUY.get();
    }

    private static int capOf(BlockState state) {
        return state.getBlock() instanceof ArenaSpawnerBlock b ? b.nearbyCap() : NEARBY_CAP;
    }

    private static void spawnOne(ServerLevel level, BlockPos origin, RandomSource random,
                                 EntityType<? extends Mob> type) {
        for (int t = 0; t < TRIES; t++) {
            int dx = random.nextInt(2 * SPREAD + 1) - SPREAD;
            int dz = random.nextInt(2 * SPREAD + 1) - SPREAD;
            BlockPos at = origin.offset(dx, 0, dz);
            if (!hasRoom(level, at, type)) {
                continue;
            }
            Mob mob = type.create(level);
            if (mob == null) {
                return;
            }
            mob.moveTo(at.getX() + 0.5D, at.getY(), at.getZ() + 0.5D, random.nextFloat() * 360F, 0F);
            mob.finalizeSpawn(level, level.getCurrentDifficultyAt(at), MobSpawnType.SPAWNER, null);
            level.addFreshEntity(mob);
            level.levelEvent(2004, at, 0); // spawner "poof" particles
            return;
        }
    }

    /** Solid floor below and clear space for the mob's body. */
    private static boolean hasRoom(ServerLevel level, BlockPos at, EntityType<? extends Mob> type) {
        if (!level.getBlockState(at.below()).isFaceSturdy(level, at.below(), Direction.UP)) {
            return false;
        }
        return level.noCollision(type.getSpawnAABB(at.getX() + 0.5D, at.getY(), at.getZ() + 0.5D));
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
