package com.mezfi.cookiemod.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * The Holy Cookie — a totem-style death save (MECHANICS_SPEC §5.3).
 *
 * <p>The consume-on-death behaviour lives in {@link com.mezfi.cookiemod.CombatEvents}
 * (it must react to any living entity's death, not just item use). This class only adds
 * the descriptive tooltip.
 */
public class HolyCookieItem extends Item {

    public HolyCookieItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.cookiemod.holy_cookie").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
