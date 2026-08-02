package com.mezfi.cookiemod.block;

import com.mezfi.cookiemod.registry.ModEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.BaseEntityBlock;

/** Spawns waffle guys (MECHANICS_SPEC §9.3). Hostile — light it up to disable spawns. */
public class WaffleGuySpawnerBlock extends CookieSpawnerBlock {

    public static final MapCodec<WaffleGuySpawnerBlock> CODEC = simpleCodec(WaffleGuySpawnerBlock::new);

    public WaffleGuySpawnerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected EntityType<?> spawnedEntityType() {
        return ModEntities.WAFFLE_GUY.get();
    }
}
