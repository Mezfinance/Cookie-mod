package com.mezfi.cookiemod;

import com.mezfi.cookiemod.registry.ModFluids;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * Milk contact behaviour (MECHANICS_SPEC §5.2): while a living entity is touching milk it is
 * healed (Saturation + Regeneration each second) and carried upward like an elevator; while
 * fully submerged it also gets Nausea. Sneaking lets you sink instead of rising.
 */
@EventBusSubscriber(modid = CookieMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class MilkEffects {
    private MilkEffects() {}

    private static final double RISE_STEP = 0.06D;   // upward acceleration per tick
    private static final double RISE_CAP = 0.30D;    // max upward speed

    @SubscribeEvent
    static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        if (entity.level().isClientSide) {
            return;
        }
        if (entity.getFluidTypeHeight(ModFluids.MILK_TYPE.get()) <= 0.0D) {
            return; // not touching milk
        }

        // Heal every second while in contact.
        if (entity.tickCount % 20 == 0) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0, true, false));
            entity.addEffect(new MobEffectInstance(MobEffects.SATURATION, 40, 0, true, false));
        }

        // Nausea while fully submerged.
        if (entity.getEyeInFluidType() == ModFluids.MILK_TYPE.get()) {
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0, true, false));
        }

        // Upward elevator: rise unless the player is deliberately sneaking to descend.
        boolean sinking = entity instanceof Player player && player.isShiftKeyDown();
        if (!sinking) {
            Vec3 v = entity.getDeltaMovement();
            entity.setDeltaMovement(v.x, Math.min(v.y + RISE_STEP, RISE_CAP), v.z);
            entity.fallDistance = 0.0F;
            entity.resetFallDistance();
        }
    }
}
