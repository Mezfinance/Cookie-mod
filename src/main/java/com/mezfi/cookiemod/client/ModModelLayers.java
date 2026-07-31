package com.mezfi.cookiemod.client;

import com.mezfi.cookiemod.CookieMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

/** Client model-layer locations. */
public final class ModModelLayers {
    private ModModelLayers() {}

    public static final ModelLayerLocation COOKIE_SOLDIER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(CookieMod.MODID, "cookie_soldier"), "main");
}
