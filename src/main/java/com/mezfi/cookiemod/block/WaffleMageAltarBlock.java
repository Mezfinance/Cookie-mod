package com.mezfi.cookiemod.block;

import com.mezfi.cookiemod.entity.WaffleGuyEntity;
import com.mezfi.cookiemod.entity.WaffleMageEntity;
import com.mezfi.cookiemod.registry.ModBlocks;
import com.mezfi.cookiemod.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * Hidden one-shot boss trigger set at the top of a Waffle Tower
 * ({@link com.mezfi.cookiemod.worldgen.WaffleTowerFeature}).
 *
 * <p>Random ticks only fire in player-loaded chunks, so this triggers on approach — but,
 * per MECHANICS_SPEC §8.3, it only releases the Waffle Mage once the tower's waffle-guy
 * <b>defenders around the top are cleared</b>. When a player is close and few waffle guys
 * remain nearby, it spawns the Mage hovering over the roof and reverts to waffle block.
 */
public class WaffleMageAltarBlock extends Block {

    /** Blocks up from the marker the Mage spawns — hovering over the deck. */
    private static final int SPAWN_OFFSET = 3;
    private static final double TRIGGER_RANGE = 24.0;  // player must be at/near the top
    private static final double DEFENDER_RANGE = 12.0; // "cleared" = few waffle guys this close
    private static final int DEFENDER_LIMIT = 2;

    public WaffleMageAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        double x = pos.getX() + 0.5, y = pos.getY(), z = pos.getZ() + 0.5;
        if (level.getNearestPlayer(x, y, z, TRIGGER_RANGE, false) == null) {
            return; // nobody at the top yet
        }
        int defenders = level.getEntitiesOfClass(WaffleGuyEntity.class,
                new AABB(pos).inflate(DEFENDER_RANGE)).size();
        if (defenders > DEFENDER_LIMIT) {
            return; // defenders still holding the tower — the Mage waits
        }

        WaffleMageEntity mage = ModEntities.WAFFLE_MAGE.get().create(level);
        if (mage != null) {
            BlockPos at = pos.above(SPAWN_OFFSET);
            mage.moveTo(at.getX() + 0.5D, at.getY(), at.getZ() + 0.5D,
                    random.nextFloat() * 360F, 0F);
            mage.finalizeSpawn(level, level.getCurrentDifficultyAt(at), MobSpawnType.EVENT, null);
            level.addFreshEntity(mage);
        }
        level.setBlockAndUpdate(pos, ModBlocks.WAFFLE_BLOCK.get().defaultBlockState());
    }
}
