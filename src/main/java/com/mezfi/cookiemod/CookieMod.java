package com.mezfi.cookiemod;

import com.mezfi.cookiemod.registry.ModArmorMaterials;
import com.mezfi.cookiemod.registry.ModBlockEntities;
import com.mezfi.cookiemod.registry.ModBlocks;
import com.mezfi.cookiemod.registry.ModCreativeTabs;
import com.mezfi.cookiemod.registry.ModEntities;
import com.mezfi.cookiemod.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point for the Cookie Mod recreation.
 *
 * <p>Content is registered through {@link net.neoforged.neoforge.registries.DeferredRegister}s
 * held in the {@code registry} package. Each phase of the build (see
 * {@code reference/MECHANICS_SPEC.md}) adds to those registers.
 */
@Mod(CookieMod.MODID)
public class CookieMod {
    public static final String MODID = "cookiemod";
    public static final Logger LOGGER = LoggerFactory.getLogger("CookieMod");

    public CookieMod(IEventBus modBus, ModContainer container) {
        // Order matters: blocks before item-blocks, items before the creative tab that lists them.
        ModEntities.REGISTER.register(modBus);
        ModArmorMaterials.REGISTER.register(modBus);
        ModBlocks.REGISTER.register(modBus);
        ModBlockEntities.REGISTER.register(modBus);
        ModItems.REGISTER.register(modBus);
        ModCreativeTabs.REGISTER.register(modBus);

        LOGGER.info("Cookie Mod loaded — the cookie army awaits.");
    }
}
