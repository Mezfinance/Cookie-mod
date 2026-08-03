package com.mezfi.cookiemod.registry;

import com.mezfi.cookiemod.CookieMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Milk — a custom, thick white fluid (MECHANICS_SPEC §5.2). Standing in it heals you
 * (Saturation + Regeneration each second, Nausea while submerged — applied in
 * {@link com.mezfi.cookiemod.MilkEffects}); it also carries you upward like an elevator.
 */
public final class ModFluids {
    private ModFluids() {}

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, CookieMod.MODID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, CookieMod.MODID);

    public static final DeferredHolder<FluidType, FluidType> MILK_TYPE =
            FLUID_TYPES.register("milk", () -> new FluidType(FluidType.Properties.create()
                    .density(1400)          // heavier than water — thick
                    .viscosity(1400)        // sluggish flow
                    .canConvertToSource(false)
                    .canSwim(true)
                    .canDrown(false)
                    .canPushEntity(true)
                    .supportsBoating(false)
                    .canHydrate(false)
                    .lightLevel(1)));

    public static final DeferredHolder<Fluid, FlowingFluid> MILK_SOURCE =
            FLUIDS.register("milk", () -> new BaseFlowingFluid.Source(ModFluids.properties()));
    public static final DeferredHolder<Fluid, FlowingFluid> MILK_FLOWING =
            FLUIDS.register("flowing_milk", () -> new BaseFlowingFluid.Flowing(ModFluids.properties()));

    /** Shared properties; block + bucket are resolved lazily to avoid class-init ordering issues. */
    private static BaseFlowingFluid.Properties properties() {
        return new BaseFlowingFluid.Properties(MILK_TYPE, MILK_SOURCE, MILK_FLOWING)
                .block(() -> ModBlocks.MILK.get())
                .bucket(() -> ModItems.COOKIE_MILK_BUCKET.get())
                .slopeFindDistance(3)
                .levelDecreasePerBlock(1)
                .tickRate(20)
                .explosionResistance(100.0F);
    }
}
