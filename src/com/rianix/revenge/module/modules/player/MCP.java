/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 *  net.minecraft.util.EnumHand
 *  net.minecraft.world.World
 *  org.lwjgl.input.Mouse
 */
package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.module.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class MCP
extends Module {
    private boolean clicked = false;

    public MCP() {
        super("MCP", "Middle click pearl.", 0, Module.Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (Mouse.isButtonDown((int)2)) {
            if (!this.clicked) {
                this.throwPearl();
            }
            this.clicked = true;
        } else {
            this.clicked = false;
        }
    }

    private void throwPearl() {
        int oldslot = MCP.mc.player.inventory.currentItem;
        for (int i = 0; i < 9; ++i) {
            ItemStack Stack = MCP.mc.player.inventory.getStackInSlot(i);
            if (MCP.mc.player.inventory.getStackInSlot(i).isEmpty() || !(Stack.getItem() instanceof ItemEnderPearl)) continue;
            MCP.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(i));
            MCP.mc.playerController.updateController();
            break;
        }
        MCP.mc.playerController.processRightClick((EntityPlayer)MCP.mc.player, (World)MCP.mc.world, EnumHand.MAIN_HAND);
        MCP.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(oldslot));
        MCP.mc.playerController.updateController();
    }
}

