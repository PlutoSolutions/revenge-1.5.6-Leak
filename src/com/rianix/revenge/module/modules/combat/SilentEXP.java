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
package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingInteger;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class SilentEXP
extends Module {
    SettingInteger tickDelay = this.register("TickDelay", 1, 0, 10);
    int prvSlot;
    private int delayStep = 0;

    public SilentEXP() {
        super("SilentEXP", "", 0, Module.Category.COMBAT);
    }

    @Override
    public void onUpdate() {
        if (SilentEXP.mc.currentScreen == null) {
            if (this.delayStep < this.tickDelay.getValue()) {
                ++this.delayStep;
                return;
            }
            this.delayStep = 0;
            this.prvSlot = SilentEXP.mc.player.inventory.currentItem;
            SilentEXP.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.findExpInHotbar()));
            SilentEXP.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            SilentEXP.mc.player.inventory.currentItem = this.prvSlot;
            SilentEXP.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.prvSlot));
        }
    }

    private int findExpInHotbar() {
        int slot = 0;
        for (int i = 0; i < 9; ++i) {
            if (SilentEXP.mc.player.inventory.getStackInSlot(i).getItem() != Items.EXPERIENCE_BOTTLE) continue;
            slot = i;
            break;
        }
        return slot;
    }
}

