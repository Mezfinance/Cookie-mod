package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Custom items.
 *
 * <p>The {@code cookie} is the economy's core currency: crafted from cookie blocks and fed
 * to cookie dudes to recruit soldiers (MECHANICS_SPEC §3-4). Its edible stats use the
 * spec's estimated values (+2 hunger).
 */
public final class ModItems {
    private ModItems() {}

    public static final DeferredRegister.Items REGISTER =
            DeferredRegister.createItems(CookieMod.MODID);

    /** Recruitment currency + snack. [EST] +2 nutrition, light saturation. */
    public static final FoodProperties COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .build();

    public static final DeferredItem<Item> COOKIE = REGISTER.registerItem(
            "cookie",
            props -> new Item(props.food(COOKIE_FOOD)));

    /** BlockItem for the cookie block (references ModBlocks, forcing its class init first). */
    public static final DeferredItem<BlockItem> COOKIE_BLOCK_ITEM =
            REGISTER.registerSimpleBlockItem(ModBlocks.COOKIE_BLOCK);
}
