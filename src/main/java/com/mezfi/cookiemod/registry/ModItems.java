package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import com.mezfi.cookiemod.item.HolyCookieItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
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
    /** Dropped by polar bears; the crafting ingredient for gummy armour (MECHANICS_SPEC §5.1, §7). */
    public static final DeferredItem<Item> GUMMY_BEAR = food("gummy_bear", 2, 0.2F);

    // --- weapons ---
    /** Lollipop weapon (MECHANICS_SPEC §6): 5 attack damage, 1.6 attack speed — stone-sword stats. */
    public static final DeferredItem<Item> LOLLIPOP = REGISTER.registerItem("lollipop",
            p -> new SwordItem(Tiers.STONE, p.attributes(SwordItem.createAttributes(Tiers.STONE, 3, -2.4F))));

    // --- gummy armour (MECHANICS_SPEC §9.1): the purple set waffle guys sometimes wear ---
    public static final DeferredItem<ArmorItem> GUMMY_HELMET = REGISTER.registerItem("gummy_helmet",
            p -> new ArmorItem(ModArmorMaterials.GUMMY, ArmorItem.Type.HELMET, p.durability(ArmorItem.Type.HELMET.getDurability(12))));
    public static final DeferredItem<ArmorItem> GUMMY_CHESTPLATE = REGISTER.registerItem("gummy_chestplate",
            p -> new ArmorItem(ModArmorMaterials.GUMMY, ArmorItem.Type.CHESTPLATE, p.durability(ArmorItem.Type.CHESTPLATE.getDurability(12))));
    public static final DeferredItem<ArmorItem> GUMMY_LEGGINGS = REGISTER.registerItem("gummy_leggings",
            p -> new ArmorItem(ModArmorMaterials.GUMMY, ArmorItem.Type.LEGGINGS, p.durability(ArmorItem.Type.LEGGINGS.getDurability(12))));
    public static final DeferredItem<ArmorItem> GUMMY_BOOTS = REGISTER.registerItem("gummy_boots",
            p -> new ArmorItem(ModArmorMaterials.GUMMY, ArmorItem.Type.BOOTS, p.durability(ArmorItem.Type.BOOTS.getDurability(12))));

    // --- totems / special items ---
    /** Dropped by the Cake Golem. Held-in-hand death save (see CombatEvents; MECHANICS_SPEC §5.3). */
    public static final DeferredItem<Item> HOLY_COOKIE = REGISTER.registerItem("holy_cookie",
            p -> new HolyCookieItem(p.rarity(Rarity.UNCOMMON)));

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
