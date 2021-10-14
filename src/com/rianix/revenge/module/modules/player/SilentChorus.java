/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.EnumHand
 */
package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class SilentChorus
extends Module {
    int oldSlot;

    public SilentChorus() {
        super("SilentChorus", "", 0, Module.Category.PLAYER);
    }

    @Override
    public void onEnable() {
        this.oldSlot = SilentChorus.mc.player.inventory.currentItem;
    }

    @Override
    public void onUpdate() {
        int slot = InventoryUtil.findItemInHotbar(Items.CHORUS_FRUIT);
        if (InventoryUtil.findItemInHotbar(Items.CHORUS_FRUIT) != -1) {
            SilentChorus.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            SilentChorus.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            this.toggle();
        }
    }
}

