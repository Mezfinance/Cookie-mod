package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.block.entity.ArenaSpawnerBlockEntity;
import com.mezfi.cookiemod.block.entity.CookieSpawnerBlockEntity;
import com.mezfi.cookiemod.block.entity.GingerbreadFurnaceBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Custom block entities. */
public final class ModBlockEntities {
    private ModBlockEntities() {}

    public static final DeferredRegister<BlockEntityType<?>> REGISTER =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CookieMod.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CookieSpawnerBlockEntity>> COOKIE_SPAWNER =
            REGISTER.register("cookie_spawner", () -> BlockEntityType.Builder.of(
                    CookieSpawnerBlockEntity::new,
                    ModBlocks.WAFFLE_GUY_SPAWNER.get(),
                    ModBlocks.CHOCOLATE_BUNNY_SPAWNER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ArenaSpawnerBlockEntity>> ARENA_SPAWNER =
            REGISTER.register("arena_spawner", () -> BlockEntityType.Builder.of(
                    ArenaSpawnerBlockEntity::new,
                    ModBlocks.ARENA_SPAWNER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GingerbreadFurnaceBlockEntity>> GINGERBREAD_FURNACE =
            REGISTER.register("gingerbread_furnace", () -> BlockEntityType.Builder.of(
                    GingerbreadFurnaceBlockEntity::new,
                    ModBlocks.GINGERBREAD_FURNACE.get()).build(null));
}
