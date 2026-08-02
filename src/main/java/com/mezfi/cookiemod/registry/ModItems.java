package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Custom items.
 *
 * <p>The {@code cookie} is the economy's core currency — crafted from cookie blocks and
 * fed to cookie dudes to recruit soldiers (MECHANICS_SPEC §3-4). Edible stats use the
 * spec's estimated values.
 */
public final class ModItems {
    private ModItems() {}

    public static final DeferredRegister.Items REGISTER =
            DeferredRegister.createItems(CookieMod.MODID);

    // --- foods ([EST] nutrition values from MECHANICS_SPEC) ---
    public static final DeferredItem<Item> COOKIE = food("cookie", 2, 0.3F);
    public static final DeferredItem<Item> CHOCOLATE_BAR = food("chocolate_bar", 4, 0.4F);
    public static final DeferredItem<Item> CANDY = food("candy", 3, 0.3F);
    /** Dropped by waffle guys; 4 craft into a Waffle Block (design deviation — source was tower-only). */
    public static final DeferredItem<Item> WAFFLE = food("waffle", 3, 0.3F);

    // --- spawn eggs ---
    public static final DeferredItem<DeferredSpawnEggItem> COOKIE_SOLDIER_SPAWN_EGG =
            REGISTER.registerItem("cookie_soldier_spawn_egg",
                    p -> new DeferredSpawnEggItem(ModEntities.COOKIE_SOLDIER, 0xC68A4E, 0x6B4423, p));
    public static final DeferredItem<DeferredSpawnEggItem> WAFFLE_GUY_SPAWN_EGG =
            REGISTER.registerItem("waffle_guy_spawn_egg",
                    p -> new DeferredSpawnEggItem(ModEntities.WAFFLE_GUY, 0xC9A24B, 0x8A5A2B, p));
    public static final DeferredItem<DeferredSpawnEggItem> CAKE_GOLEM_SPAWN_EGG =
            REGISTER.registerItem("cake_golem_spawn_egg",
                    p -> new DeferredSpawnEggItem(ModEntities.CAKE_GOLEM, 0x6B4A2B, 0xE8DCC0, p));

    // --- block items for every registered block (registered after the items above) ---
    static {
        ModBlocks.ALL.forEach(REGISTER::registerSimpleBlockItem);
    }

    private static DeferredItem<Item> food(String name, int nutrition, float saturation) {
        FoodProperties props = new FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturation)
                .build();
        return REGISTER.registerItem(name, p -> new Item(p.food(props)));
    }
}
