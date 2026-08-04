package com.mezfi.cookiemod.entity;

import com.mezfi.cookiemod.registry.ModEntities;
import com.mezfi.cookiemod.registry.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * A candy shard flung by the Waffle Mage (MECHANICS_SPEC §8.3 — "projectile spit"). Flies
 * straight (no gravity), deals a small hit, and pops on contact. Rendered as a spinning
 * candy sprite via the vanilla thrown-item renderer.
 */
public class WaffleShardEntity extends ThrowableItemProjectile {

    private static final float DAMAGE = 6.0F;

    public WaffleShardEntity(EntityType<? extends WaffleShardEntity> type, Level level) {
        super(type, level);
    }

    public WaffleShardEntity(Level level, LivingEntity owner) {
        super(ModEntities.WAFFLE_SHARD.get(), owner, level);
        this.setNoGravity(true);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.CANDY.get();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.0D;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), DAMAGE);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }
}
