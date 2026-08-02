package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** The mod's creative-inventory tab. Content is appended as phases land. */
public final class ModCreativeTabs {
    private ModCreativeTabs() {}

    public static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CookieMod.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COOKIE_TAB =
            REGISTER.register("cookie", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + CookieMod.MODID))
                    .icon(() -> new ItemStack(ModItems.COOKIE.get()))
                    .displayItems((params, output) -> {
                        // foods first
                        output.accept(ModItems.COOKIE.get());
                        output.accept(ModItems.CHOCOLATE_BAR.get());
                        output.accept(ModItems.CANDY.get());
                        output.accept(ModItems.WAFFLE.get());
                        output.accept(ModItems.LOLLIPOP.get());
                        output.accept(ModItems.HOLY_COOKIE.get());
                        output.accept(ModItems.COOKIE_SOLDIER_SPAWN_EGG.get());
                        output.accept(ModItems.WAFFLE_GUY_SPAWN_EGG.get());
                        output.accept(ModItems.CAKE_GOLEM_SPAWN_EGG.get());
                        // then every block, in registration order
                        ModBlocks.ALL.forEach(block -> output.accept(block.get()));
                    })
                    .build());
}
