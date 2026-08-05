package com.mezfi.cookiemod;

import com.mezfi.cookiemod.registry.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * Game-bus combat handlers.
 *
 * <p>Holy Cookie totem (MECHANICS_SPEC §5.3): when any living entity would die while
 * holding a Holy Cookie in either hand, it is consumed instead — the entity survives at
 * 1 HP with a totem-style burst and short buffs. Works for the player and cookie soldiers.
 */
@EventBusSubscriber(modid = CookieMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class CombatEvents {
    private CombatEvents() {}

    @SubscribeEvent
    static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) {
            return;
        }
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack stack = entity.getItemInHand(hand);
            if (stack.is(ModItems.HOLY_COOKIE.get())) {
                stack.shrink(1);
                entity.setHealth(1.0F);
                entity.removeAllEffects();
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                entity.level().broadcastEntityEvent(entity, (byte) 35); // totem-of-undying burst
                event.setCanceled(true);
                return;
            }
        }
    }
}
