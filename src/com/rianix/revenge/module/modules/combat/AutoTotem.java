/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemStack
 */
package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

public class AutoTotem
extends Module {
    SettingBoolean soft = this.register("Soft", false);
    private boolean dragging = false;

    public AutoTotem() {
        super("AutoTotem", "", 0, Module.Category.COMBAT);
    }

    @Override
    public void onUpdate() {
        if (AutoTotem.mc.currentScreen instanceof GuiInventory) {
            return;
        }
        EntityPlayerSP player = AutoTotem.mc.player;
        if (player == null) {
            return;
        }
        if (!player.inventory.getItemStack().isEmpty() && !this.dragging) {
            for (int i = 0; i < 45; ++i) {
                if (!player.inventory.getStackInSlot(i).isEmpty() && player.inventory.getStackInSlot(i).getItem() != Items.AIR) continue;
                int slot = i < 9 ? i + 36 : i;
                AutoTotem.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)player);
                return;
            }
        }
        int totems = 0;
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack.getItem() != Items.TOTEM_OF_UNDYING) continue;
            totems += stack.getCount();
        }
        if (player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            totems += player.getHeldItemOffhand().getCount();
            return;
        }
        if (this.soft.getValue() && !player.getHeldItemOffhand().isEmpty()) {
            return;
        }
        if (this.dragging) {
            AutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)player);
            this.dragging = false;
            return;
        }
        for (int i = 0; i < 45; ++i) {
            if (player.inventory.getStackInSlot(i).getItem() != Items.TOTEM_OF_UNDYING) continue;
            int slot = i < 9 ? i + 36 : i;
            AutoTotem.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)player);
            this.dragging = true;
            return;
        }
    }
}

