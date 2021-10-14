/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.EnumHand
 */
package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.command.Messages;
import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingInteger;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class BowBomb
extends Module {
    private long lastShootTime;
    SettingBoolean Bows = this.register("Bows", true);
    SettingBoolean pearls = this.register("Pearls", true);
    SettingBoolean eggs = this.register("Eggs", true);
    SettingBoolean snowballs = this.register("SnowBalls", true);
    SettingInteger Timeout = this.register("Timeout", 5000, 100, 20000);
    SettingInteger spoofs = this.register("Spoofs", 10, 1, 300);
    SettingBoolean bypass = this.register("Bypass", false);
    SettingBoolean debug = this.register("Debug", false);
    @EventHandler
    private final Listener<PacketEvent.Send> receiveListener = new Listener<PacketEvent.Send>(event -> {
        ItemStack handStack;
        CPacketPlayerTryUseItem packet2;
        if (event.getPacket() instanceof CPacketPlayerDigging) {
            ItemStack handStack2;
            CPacketPlayerDigging packet = (CPacketPlayerDigging)event.getPacket();
            if (packet.getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM && !(handStack2 = BowBomb.mc.player.getHeldItem(EnumHand.MAIN_HAND)).isEmpty()) {
                handStack2.getItem();
                if (handStack2.getItem() instanceof ItemBow && this.Bows.getValue()) {
                    this.doSpoofs();
                    if (this.debug.getValue()) {
                        Messages.sendClientMessage("trying to spoof");
                    }
                }
            }
        } else if (event.getPacket() instanceof CPacketPlayerTryUseItem && (packet2 = (CPacketPlayerTryUseItem)event.getPacket()).getHand() == EnumHand.MAIN_HAND && !(handStack = BowBomb.mc.player.getHeldItem(EnumHand.MAIN_HAND)).isEmpty()) {
            handStack.getItem();
            if (handStack.getItem() instanceof ItemEgg && this.eggs.getValue()) {
                this.doSpoofs();
            } else if (handStack.getItem() instanceof ItemEnderPearl && this.pearls.getValue()) {
                this.doSpoofs();
            } else if (handStack.getItem() instanceof ItemSnowball && this.snowballs.getValue()) {
                this.doSpoofs();
            }
        }
    }, new Predicate[0]);

    public BowBomb() {
        super("BowBomb", "", 0, Module.Category.COMBAT);
    }

    @Override
    public void onEnable() {
        this.lastShootTime = System.currentTimeMillis();
    }

    private void doSpoofs() {
        if (System.currentTimeMillis() - this.lastShootTime >= (long)this.Timeout.getValue()) {
            this.lastShootTime = System.currentTimeMillis();
            BowBomb.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BowBomb.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            for (int index = 0; index < this.spoofs.getValue(); ++index) {
                if (this.bypass.getValue()) {
                    BowBomb.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(BowBomb.mc.player.posX, BowBomb.mc.player.posY + 1.0E-10, BowBomb.mc.player.posZ, false));
                    BowBomb.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(BowBomb.mc.player.posX, BowBomb.mc.player.posY - 1.0E-10, BowBomb.mc.player.posZ, true));
                    continue;
                }
                BowBomb.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(BowBomb.mc.player.posX, BowBomb.mc.player.posY - 1.0E-10, BowBomb.mc.player.posZ, true));
                BowBomb.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(BowBomb.mc.player.posX, BowBomb.mc.player.posY + 1.0E-10, BowBomb.mc.player.posZ, false));
            }
            if (this.debug.getValue()) {
                Messages.sendClientMessage("spoofed");
            }
        }
    }
}

