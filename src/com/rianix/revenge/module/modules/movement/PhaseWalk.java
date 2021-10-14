/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 */
package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingDouble;
import com.rianix.revenge.setting.settings.SettingInteger;
import com.rianix.revenge.util.MathUtil;
import com.rianix.revenge.util.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

public class PhaseWalk
extends Module {
    SettingBoolean fallPacket = this.register("FallPacket", true);
    SettingBoolean instantWalk = this.register("InstantWalk", true);
    SettingDouble instantWalkSpeed = this.register("InstantWalkSpeed", 18.0, 10.0, 19.0);
    SettingDouble phaseSpeed = this.register("PhaseSpeed", 4.24, 1.0, 7.0);
    SettingBoolean phaseCheck = this.register("PhaseCheck", true);
    SettingBoolean downOnShift = this.register("DownOnShift", true);
    SettingBoolean stopMotion = this.register("StopMotion", true);
    SettingInteger stopMotionDelay = this.register("StopMotionDelay", 5, 0, 20);
    int delay = 0;

    public PhaseWalk() {
        super("PhaseWalk", "", 0, Module.Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        ++this.delay;
        if ((PhaseWalk.mc.gameSettings.keyBindForward.isKeyDown() || PhaseWalk.mc.gameSettings.keyBindRight.isKeyDown() || PhaseWalk.mc.gameSettings.keyBindLeft.isKeyDown() || PhaseWalk.mc.gameSettings.keyBindBack.isKeyDown() || PhaseWalk.mc.gameSettings.keyBindSneak.isKeyDown()) && (this.phaseCheck.getValue() && !this.eChestCheck() && !PhaseWalk.mc.world.getBlockState(PlayerUtil.getPlayerPos()).getBlock().equals((Object)Blocks.AIR) || !PhaseWalk.mc.world.getBlockState(PlayerUtil.getPlayerPos().up()).getBlock().equals((Object)Blocks.AIR))) {
            double[] dirSpeed;
            if (PhaseWalk.mc.player.collidedVertically && PhaseWalk.mc.gameSettings.keyBindSneak.isPressed() && PhaseWalk.mc.player.isSneaking()) {
                dirSpeed = this.getMotion(this.phaseSpeed.getValue() / 100.0);
                if (this.downOnShift.getValue() && PhaseWalk.mc.player.collidedVertically && PhaseWalk.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    PhaseWalk.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PhaseWalk.mc.player.posX + dirSpeed[0], PhaseWalk.mc.player.posY - 0.0424, PhaseWalk.mc.player.posZ + dirSpeed[1], PhaseWalk.mc.player.rotationYaw, PhaseWalk.mc.player.rotationPitch, false));
                } else {
                    PhaseWalk.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PhaseWalk.mc.player.posX + dirSpeed[0], PhaseWalk.mc.player.posY, PhaseWalk.mc.player.posZ + dirSpeed[1], PhaseWalk.mc.player.rotationYaw, PhaseWalk.mc.player.rotationPitch, false));
                }
                PhaseWalk.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PhaseWalk.mc.player.posX, -1337.0, PhaseWalk.mc.player.posZ, PhaseWalk.mc.player.rotationYaw * -5.0f, PhaseWalk.mc.player.rotationPitch * -5.0f, true));
                if (this.fallPacket.getValue()) {
                    PhaseWalk.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PhaseWalk.mc.player, CPacketEntityAction.Action.STOP_RIDING_JUMP));
                }
                if (this.downOnShift.getValue() && PhaseWalk.mc.player.collidedVertically && PhaseWalk.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    PhaseWalk.mc.player.setPosition(PhaseWalk.mc.player.posX + dirSpeed[0], PhaseWalk.mc.player.posY - 0.0424, PhaseWalk.mc.player.posZ + dirSpeed[1]);
                } else {
                    PhaseWalk.mc.player.setPosition(PhaseWalk.mc.player.posX + dirSpeed[0], PhaseWalk.mc.player.posY, PhaseWalk.mc.player.posZ + dirSpeed[1]);
                }
                PhaseWalk.mc.player.motionZ = 0.0;
                PhaseWalk.mc.player.motionY = 0.0;
                PhaseWalk.mc.player.motionX = 0.0;
                PhaseWalk.mc.player.noClip = true;
            }
            if (PhaseWalk.mc.player.collidedHorizontally && this.stopMotion.getValue() ? this.delay >= this.stopMotionDelay.getValue() : PhaseWalk.mc.player.collidedHorizontally) {
                dirSpeed = this.getMotion(this.phaseSpeed.getValue() / 100.0);
                if (this.downOnShift.getValue() && PhaseWalk.mc.player.collidedVertically && PhaseWalk.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    PhaseWalk.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PhaseWalk.mc.player.posX + dirSpeed[0], PhaseWalk.mc.player.posY - 0.1, PhaseWalk.mc.player.posZ + dirSpeed[1], PhaseWalk.mc.player.rotationYaw, PhaseWalk.mc.player.rotationPitch, false));
                } else {
                    PhaseWalk.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PhaseWalk.mc.player.posX + dirSpeed[0], PhaseWalk.mc.player.posY, PhaseWalk.mc.player.posZ + dirSpeed[1], PhaseWalk.mc.player.rotationYaw, PhaseWalk.mc.player.rotationPitch, false));
                }
                PhaseWalk.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PhaseWalk.mc.player.posX, -1337.0, PhaseWalk.mc.player.posZ, PhaseWalk.mc.player.rotationYaw * -5.0f, PhaseWalk.mc.player.rotationPitch * -5.0f, true));
                if (this.fallPacket.getValue()) {
                    PhaseWalk.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PhaseWalk.mc.player, CPacketEntityAction.Action.STOP_RIDING_JUMP));
                }
                if (this.downOnShift.getValue() && PhaseWalk.mc.player.collidedVertically && PhaseWalk.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    PhaseWalk.mc.player.setPosition(PhaseWalk.mc.player.posX + dirSpeed[0], PhaseWalk.mc.player.posY - 0.1, PhaseWalk.mc.player.posZ + dirSpeed[1]);
                } else {
                    PhaseWalk.mc.player.setPosition(PhaseWalk.mc.player.posX + dirSpeed[0], PhaseWalk.mc.player.posY, PhaseWalk.mc.player.posZ + dirSpeed[1]);
                }
                PhaseWalk.mc.player.motionZ = 0.0;
                PhaseWalk.mc.player.motionY = 0.0;
                PhaseWalk.mc.player.motionX = 0.0;
                PhaseWalk.mc.player.noClip = true;
                this.delay = 0;
            } else if (this.instantWalk.getValue()) {
                double[] dir = MathUtil.directionSpeed(this.instantWalkSpeed.getValue() / 100.0);
                PhaseWalk.mc.player.motionX = dir[0];
                PhaseWalk.mc.player.motionZ = dir[1];
            }
        }
    }

    @Override
    public void onEnable() {
    }

    private double[] getMotion(double speed) {
        float moveForward = PhaseWalk.mc.player.movementInput.moveForward;
        float moveStrafe = PhaseWalk.mc.player.movementInput.moveStrafe;
        float rotationYaw = PhaseWalk.mc.player.prevRotationYaw + (PhaseWalk.mc.player.rotationYaw - PhaseWalk.mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (moveForward != 0.0f) {
            if (moveStrafe > 0.0f) {
                rotationYaw += (float)(moveForward > 0.0f ? -45 : 45);
            } else if (moveStrafe < 0.0f) {
                rotationYaw += (float)(moveForward > 0.0f ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            } else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        double posX = (double)moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + (double)moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
        double posZ = (double)moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - (double)moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
        return new double[]{posX, posZ};
    }

    @Override
    public void onDisable() {
        PhaseWalk.mc.player.noClip = false;
    }

    private boolean eChestCheck() {
        String loc = String.valueOf(PhaseWalk.mc.player.posY);
        String deciaml = loc.split("\\.")[1];
        return deciaml.equals("875");
    }
}

