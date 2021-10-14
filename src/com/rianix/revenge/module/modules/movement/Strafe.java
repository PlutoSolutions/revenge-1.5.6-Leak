/*
 * Decompiled with CFR 0.150.
 */
package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.util.MathUtil;

public class Strafe
extends Module {
    int delay = 0;

    public Strafe() {
        super("Strafe", "", 0, Module.Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        Strafe.mc.player.setSprinting(true);
        ++this.delay;
        Strafe.mc.player.motionY *= 0.985;
        if (Strafe.mc.player.onGround) {
            if (Strafe.mc.gameSettings.keyBindForward.isKeyDown() || Strafe.mc.gameSettings.keyBindBack.isKeyDown() || Strafe.mc.gameSettings.keyBindLeft.isKeyDown() || Strafe.mc.gameSettings.keyBindRight.isKeyDown()) {
                Strafe.mc.player.jump();
                double[] dir = MathUtil.directionSpeed(0.6);
                Strafe.mc.player.motionX = dir[0];
                Strafe.mc.player.motionZ = dir[1];
            }
        } else {
            double[] dir = MathUtil.directionSpeed(0.26);
            Strafe.mc.player.motionX = dir[0];
            Strafe.mc.player.motionZ = dir[1];
        }
    }
}

