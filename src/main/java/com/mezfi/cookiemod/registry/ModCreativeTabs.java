package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** The mod's creative-inventory tab. New content is appended to {@code displayItems} as phases land. */
public final class ModCreativeTabs {
    private ModCreativeTabs() {}

    public static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CookieMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COOKIE_TAB =
            REGISTER.register("cookie", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + CookieMod.MODID))
                    .icon(() -> new ItemStack(ModItems.COOKIE.get()))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.COOKIE.get());
                        output.accept(ModBlocks.COOKIE_BLOCK.get());
                    })
                    .build());
}
