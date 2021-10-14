/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 */
package com.rianix.revenge.util;

import com.rianix.revenge.util.Global;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class InventoryUtil
implements Global {
    public static int findHotbarBlock(Class c) {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY) continue;
            if (c.isInstance((Object)stack.getItem())) {
                return i;
            }
            if (!(stack.getItem() instanceof ItemBlock) || !c.isInstance((Object)((ItemBlock)stack.getItem()).getBlock())) continue;
            return i;
        }
        return -1;
    }

    public static int findItemInHotbar(Item itemToFind) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY) continue;
            stack.getItem();
            Item item = stack.getItem();
            if (!item.equals((Object)itemToFind)) continue;
            slot = i;
            break;
        }
        return slot;
    }

    public static boolean isBlock(Item item, Class clazz) {
        if (item instanceof ItemBlock) {
            Block block = ((ItemBlock)item).getBlock();
            return clazz.isInstance((Object)block);
        }
        return false;
    }
}

