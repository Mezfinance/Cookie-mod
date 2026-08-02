package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.entity.CakeGolemEntity;
import com.mezfi.cookiemod.entity.CookieSoldierEntity;
import com.mezfi.cookiemod.entity.WaffleGuyEntity;
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

    public static final DeferredHolder<EntityType<?>, EntityType<WaffleGuyEntity>> WAFFLE_GUY =
            REGISTER.register("waffle_guy", () -> EntityType.Builder
                    .of(WaffleGuyEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .build("waffle_guy"));

    public static final DeferredHolder<EntityType<?>, EntityType<CakeGolemEntity>> CAKE_GOLEM =
            REGISTER.register("cake_golem", () -> EntityType.Builder
                    .of(CakeGolemEntity::new, MobCategory.MONSTER)
                    .sized(1.4F, 2.9F)
                    .clientTrackingRange(40)
                    .fireImmune()
                    .build("cake_golem"));
}
