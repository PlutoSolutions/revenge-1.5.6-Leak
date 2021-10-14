/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 */
package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingDouble;
import com.rianix.revenge.util.EntityUtil;
import com.rianix.revenge.util.PlayerUtil;
import net.minecraft.entity.EntityLivingBase;

public class YPort
extends Module {
    SettingDouble speed = this.register("Speed", 0.6, 0.5, 1.5);

    public YPort() {
        super("YPort", "", 0, Module.Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        EntityUtil.resetTimer();
    }

    @Override
    public void onUpdate() {
        if (!PlayerUtil.isMoving((EntityLivingBase)YPort.mc.player) || YPort.mc.player.isInWater() && YPort.mc.player.isInLava() || YPort.mc.player.collidedHorizontally) {
            return;
        }
        if (YPort.mc.player.onGround) {
            EntityUtil.setTimer(1.15f);
            YPort.mc.player.jump();
            PlayerUtil.setSpeed((EntityLivingBase)YPort.mc.player, PlayerUtil.getBaseMoveSpeed() + this.speed.getValue() / 10.0);
        } else {
            YPort.mc.player.motionY = -1.0;
            EntityUtil.resetTimer();
        }
    }
}

