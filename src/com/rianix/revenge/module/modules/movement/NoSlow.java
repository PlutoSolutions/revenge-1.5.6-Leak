/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiIngameMenu
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreenOptionsSounds
 *  net.minecraft.client.gui.GuiVideoSettings
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemShield
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.client.event.InputUpdateEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickItem
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 */
package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemShield;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class NoSlow
extends Module {
    private static final KeyBinding[] keys = new KeyBinding[]{NoSlow.mc.gameSettings.keyBindForward, NoSlow.mc.gameSettings.keyBindBack, NoSlow.mc.gameSettings.keyBindLeft, NoSlow.mc.gameSettings.keyBindRight, NoSlow.mc.gameSettings.keyBindJump, NoSlow.mc.gameSettings.keyBindSprint, NoSlow.mc.gameSettings.keyBindSneak};
    SettingBoolean inventoryMove = this.register("InventoryMove", true);
    public SettingBoolean soulSand = this.register("SoulSand", true);
    SettingBoolean strict = this.register("Strict", false);
    SettingBoolean sneak = this.register("Sneak", true);
    private boolean sneaking = false;
    @EventHandler
    private final Listener<PacketEvent.Send> sendListener = new Listener<PacketEvent.Send>(event -> {
        if (event.getPacket() instanceof CPacketPlayer && this.strict.getValue() && NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding()) {
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(Math.floor(NoSlow.mc.player.posX), Math.floor(NoSlow.mc.player.posY), Math.floor(NoSlow.mc.player.posZ)), EnumFacing.DOWN));
        }
    }, new Predicate[0]);

    public NoSlow() {
        super("NoSlow", "", 0, Module.Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUseItem(PlayerInteractEvent.RightClickItem event) {
        Item item = NoSlow.mc.player.getHeldItem(event.getHand()).getItem();
        if ((item instanceof ItemFood || item instanceof ItemBow || item instanceof ItemPotion && !this.sneaking) && this.sneak.getValue()) {
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlow.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.sneaking = true;
        }
    }

    @SubscribeEvent
    public void onMove(InputUpdateEvent event) {
        if (NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding()) {
            NoSlow.mc.player.movementInput.moveForward /= 0.2f;
            NoSlow.mc.player.movementInput.moveStrafe /= 0.2f;
        }
    }

    @Override
    public void onUpdate() {
        if (this.inventoryMove.getValue()) {
            if (NoSlow.mc.currentScreen instanceof GuiOptions || NoSlow.mc.currentScreen instanceof GuiVideoSettings || NoSlow.mc.currentScreen instanceof GuiScreenOptionsSounds || NoSlow.mc.currentScreen instanceof GuiContainer || NoSlow.mc.currentScreen instanceof GuiIngameMenu) {
                for (KeyBinding bind : keys) {
                    KeyBinding.setKeyBindState((int)bind.getKeyCode(), (boolean)Keyboard.isKeyDown((int)bind.getKeyCode()));
                }
            } else if (NoSlow.mc.currentScreen == null) {
                for (KeyBinding bind : keys) {
                    if (Keyboard.isKeyDown((int)bind.getKeyCode())) continue;
                    KeyBinding.setKeyBindState((int)bind.getKeyCode(), (boolean)false);
                }
            }
        }
        if (NoSlow.mc.player.isHandActive() && NoSlow.mc.player.getHeldItem(NoSlow.mc.player.getActiveHand()).getItem() instanceof ItemShield && (NoSlow.mc.player.movementInput.moveStrafe != 0.0f || NoSlow.mc.player.movementInput.moveForward != 0.0f && NoSlow.mc.player.getItemInUseMaxCount() >= 8)) {
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, NoSlow.mc.player.getHorizontalFacing()));
        }
        if (this.sneaking && !NoSlow.mc.player.isHandActive() && this.sneak.getValue()) {
            NoSlow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlow.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.sneaking = false;
        }
    }
}

