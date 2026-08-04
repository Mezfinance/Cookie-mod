package com.mezfi.cookiemod.block;

import com.mezfi.cookiemod.registry.ModEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.BaseEntityBlock;

/** Spawns chocolate bunnies (MECHANICS_SPEC §9.3). */
public class ChocolateBunnySpawnerBlock extends CookieSpawnerBlock {

    public static final MapCodec<ChocolateBunnySpawnerBlock> CODEC = simpleCodec(ChocolateBunnySpawnerBlock::new);

    public ChocolateBunnySpawnerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public EntityType<?> spawnedEntityType() {
        return ModEntities.CHOCOLATE_BUNNY.get();
    }
}
