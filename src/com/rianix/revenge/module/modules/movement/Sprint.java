/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 */
package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingMode;
import java.util.ArrayList;
import net.minecraft.client.settings.KeyBinding;

public class Sprint
extends Module {
    ArrayList<String> modes = new ArrayList();
    SettingMode mode = this.register("Mode", this.modes, "Rage");

    public Sprint() {
        super("Sprint", "Automatic sprints.", 0, Module.Category.MOVEMENT);
        this.modes.add("Legit");
        this.modes.add("Rage");
    }

    @Override
    public void onDisable() {
        if (Sprint.mc.world != null) {
            Sprint.mc.player.setSprinting(false);
        }
    }

    @Override
    public void onUpdate() {
        if (this.mode.getValue().equals("Legit") && Sprint.mc.gameSettings.keyBindForward.isKeyDown() && !Sprint.mc.player.isSneaking() && !Sprint.mc.player.isHandActive() && !Sprint.mc.player.collidedHorizontally && Sprint.mc.currentScreen == null && !((float)Sprint.mc.player.getFoodStats().getFoodLevel() <= 6.0f)) {
            Sprint.mc.player.setSprinting(true);
        }
        if (this.mode.getValue().equals("Rage") && (Sprint.mc.gameSettings.keyBindForward.isKeyDown() || Sprint.mc.gameSettings.keyBindBack.isKeyDown() || Sprint.mc.gameSettings.keyBindLeft.isKeyDown() || Sprint.mc.gameSettings.keyBindRight.isKeyDown()) && !Sprint.mc.player.isSneaking() && !Sprint.mc.player.collidedHorizontally && !((float)Sprint.mc.player.getFoodStats().getFoodLevel() <= 6.0f)) {
            Sprint.mc.player.setSprinting(true);
        }
        KeyBinding.setKeyBindState((int)Sprint.mc.gameSettings.keyBindSprint.getKeyCode(), (boolean)true);
    }

    @Override
    public String getDisplayInfo() {
        if (this.mode.getValue().equals("Legit")) {
            return "Legit";
        }
        if (this.mode.getValue().equals("Rage")) {
            return "Rage";
        }
        return null;
    }
}

