package com.mezfi.cookiemod.block.entity;

import com.mezfi.cookiemod.entity.CookieSoldierEntity;
import com.mezfi.cookiemod.registry.ModBlockEntities;
import com.mezfi.cookiemod.registry.ModEntities;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

/**
 * Cookie Army Factory (MECHANICS_SPEC §3.6, §10): while a player is nearby, it continuously
 * spawns cookie soldiers tamed to that player — no fuel, no input. A soft nearby-soldier cap
 * keeps it from tearing the server apart (the video runs uncapped and lags at 1,000+).
 */
public class GingerbreadFurnaceBlockEntity extends BlockEntity {

    /** [EST] §3.6: one soldier every 3–5 s. */
    private static final int MIN_COOLDOWN = 60;   // 3 s
    private static final int MAX_COOLDOWN = 100;  // 5 s
    private static final double PLAYER_RANGE = 16.0D;
    private static final double CROWD_RADIUS = 12.0D;
    private static final int CROWD_CAP = 24;      // soft cap on nearby soldiers

    private int cooldown = MIN_COOLDOWN;
    /** The player who placed the furnace; new soldiers are tamed to them. */
    @Nullable
    private UUID owner;

    public GingerbreadFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GINGERBREAD_FURNACE.get(), pos, state);
    }

    /** Called on placement to bind the factory to its owner. */
    public void setOwner(@Nullable UUID owner) {
        this.owner = owner;
        this.setChanged();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.cooldown = tag.getInt("Cooldown");
        this.owner = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Cooldown", this.cooldown);
        if (this.owner != null) {
            tag.putUUID("Owner", this.owner);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state,
                                  GingerbreadFurnaceBlockEntity be) {
        ServerLevel server = (ServerLevel) level;
        Player player = server.getNearestPlayer(
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, PLAYER_RANGE, false);
        if (player == null) {
            return; // idle unless a player is nearby (also caps world-wide load)
        }
        if (--be.cooldown > 0) {
            return;
        }
        be.cooldown = MIN_COOLDOWN + server.random.nextInt(MAX_COOLDOWN - MIN_COOLDOWN + 1);

        int nearby = server.getEntitiesOfClass(CookieSoldierEntity.class,
                new AABB(pos).inflate(CROWD_RADIUS)).size();
        if (nearby >= CROWD_CAP) {
            return; // let the crowd thin out before making more
        }
        // Tame to the furnace's owner (whoever placed it); fall back to the nearby player.
        UUID ownerId = be.owner != null ? be.owner : player.getUUID();
        spawnSoldier(server, pos, ownerId);
    }

    private static void spawnSoldier(ServerLevel level, BlockPos pos, UUID ownerId) {
        BlockPos where = findSpawnSpot(level, pos);
        if (where == null) {
            return;
        }
        CookieSoldierEntity soldier = ModEntities.COOKIE_SOLDIER.get().create(level);
        if (soldier == null) {
            return;
        }
        soldier.moveTo(where.getX() + 0.5, where.getY(), where.getZ() + 0.5,
                level.random.nextFloat() * 360.0F, 0.0F);
        soldier.finalizeSpawn(level, level.getCurrentDifficultyAt(where), MobSpawnType.MOB_SUMMONED, null);
        soldier.setOwnerUUID(ownerId);   // joins the placer's army immediately
        soldier.setTame(true, true);
        soldier.equipSoldierGear();      // same sword a recruited soldier gets
        level.addFreshEntity(soldier);
    }

    /** First open, ground-supported spot around the furnace (sides then top). */
    private static BlockPos findSpawnSpot(ServerLevel level, BlockPos pos) {
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos p = pos.relative(dir);
            if (isOpen(level, p)) {
                return p;
            }
        }
        BlockPos up = pos.above();
        return isOpen(level, up) ? up : null;
    }

    private static boolean isOpen(ServerLevel level, BlockPos p) {
        return level.getBlockState(p).getCollisionShape(level, p).isEmpty()
                && level.getBlockState(p.above()).getCollisionShape(level, p.above()).isEmpty();
    }
}
