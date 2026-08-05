package com.mezfi.cookiemod.block;

import com.mezfi.cookiemod.registry.ModEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.BaseEntityBlock;

/**
 * The Waffle Tower's chocolate-bunny spawner. Shares {@link ArenaSpawnerBlock}'s direct-code
 * block entity (any light, works when placed by worldgen) but fields chocolate bunnies —
 * the tower doubles as a chocolate-bar farm alongside the waffle-guy defenders.
 */
public class TowerBunnySpawnerBlock extends ArenaSpawnerBlock {

    public static final MapCodec<TowerBunnySpawnerBlock> CODEC = simpleCodec(TowerBunnySpawnerBlock::new);

    public TowerBunnySpawnerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public EntityType<? extends Mob> spawnType() {
        return ModEntities.CHOCOLATE_BUNNY.get();
    }

    @Override
    public int nearbyCap() {
        return 8;
    }
}
