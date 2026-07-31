package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
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

    // --- block items for every registered block (registered after the foods above) ---
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
