package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.CookieSoldierEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Custom entities. */
public final class ModEntities {
    private ModEntities() {}

    public static final DeferredRegister<EntityType<?>> REGISTER =
            DeferredRegister.create(Registries.ENTITY_TYPE, CookieMod.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<CookieSoldierEntity>> COOKIE_SOLDIER =
            REGISTER.register("cookie_soldier", () -> EntityType.Builder
                    .of(CookieSoldierEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .build("cookie_soldier"));
}
