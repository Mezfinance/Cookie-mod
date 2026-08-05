package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Custom armor materials.
 *
 * <p>Gummy Armour is the soft, chewy purple set that waffle guys sometimes spawn wearing
 * (MECHANICS_SPEC §9.1). Stats sit a notch above leather — it protects, but it is gummy,
 * not plate. Repaired with candy.
 */
public final class ModArmorMaterials {
    private ModArmorMaterials() {}

    public static final DeferredRegister<ArmorMaterial> REGISTER =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, CookieMod.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GUMMY =
            REGISTER.register("gummy", ModArmorMaterials::gummy);

    private static ArmorMaterial gummy() {
        Map<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        defense.put(ArmorItem.Type.BOOTS, 2);
        defense.put(ArmorItem.Type.LEGGINGS, 3);
        defense.put(ArmorItem.Type.CHESTPLATE, 5);
        defense.put(ArmorItem.Type.HELMET, 2);
        defense.put(ArmorItem.Type.BODY, 5);
        return new ArmorMaterial(
                defense,
                15,                                       // enchantment value (leather/gold-ish)
                SoundEvents.ARMOR_EQUIP_LEATHER,          // soft, gummy equip sound
                () -> Ingredient.of(ModItems.CANDY.get()),// repaired with candy
                List.of(new ArmorMaterial.Layer(
                        ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "gummy"))),
                0.0F,                                     // toughness
                0.0F);                                    // knockback resistance
    }
}
