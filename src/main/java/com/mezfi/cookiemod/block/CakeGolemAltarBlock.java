package com.mezfi.cookiemod.block;

import com.mezfi.cookiemod.entity.CakeGolemEntity;
import com.mezfi.cookiemod.registry.ModBlocks;
import com.mezfi.cookiemod.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Hidden one-shot spawner buried at the centre of a Cake Golem arena
 * ({@link com.mezfi.cookiemod.worldgen.CakeGolemArenaFeature}).
 *
 * <p>Random ticks only fire in chunks a player has loaded into simulation range, so this
 * effectively triggers "on first approach": when a player is within {@link #TRIGGER_RANGE},
 * it spawns one Cake Golem on the floor above and replaces itself with plain cookie block —
 * so the boss appears when the player arrives, not at world generation.
 */
public class CakeGolemAltarBlock extends Block {

    /** Blocks up from the buried marker to the arena floor / golem feet. */
    private static final int FLOOR_OFFSET = 2;
    /** Player must be this close (blocks) before the golem spawns. */
    private static final double TRIGGER_RANGE = 40.0;

    public CakeGolemAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos floor = pos.above(FLOOR_OFFSET);
        Player near = level.getNearestPlayer(
                floor.getX() + 0.5D, floor.getY(), floor.getZ() + 0.5D, TRIGGER_RANGE, false);
        if (near == null) {
            return; // player not close enough yet — wait
        }

        CakeGolemEntity golem = ModEntities.CAKE_GOLEM.get().create(level);
        if (golem != null) {
            golem.moveTo(floor.getX() + 0.5D, floor.getY(), floor.getZ() + 0.5D,
                    random.nextFloat() * 360F, 0F);
            golem.finalizeSpawn(level, level.getCurrentDifficultyAt(floor),
                    MobSpawnType.EVENT, null);
            level.addFreshEntity(golem);
        }
        // Consume the marker so the boss spawns exactly once.
        level.setBlockAndUpdate(pos, ModBlocks.COOKIE_BLOCK.get().defaultBlockState());
    }
}
