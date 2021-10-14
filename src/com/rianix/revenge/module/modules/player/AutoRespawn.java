/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGameOver
 */
package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.command.Messages;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import net.minecraft.client.gui.GuiGameOver;

public class AutoRespawn
extends Module {
    SettingBoolean coords = this.register("DeathCoords", true);

    public AutoRespawn() {
        super("AutoRespawn", "Removes the death screen.", 0, Module.Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (AutoRespawn.mc.currentScreen instanceof GuiGameOver) {
            AutoRespawn.mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
        if (this.coords.getValue() && AutoRespawn.mc.currentScreen instanceof GuiGameOver) {
            Messages.sendClientMessage("You have died at x" + (int)AutoRespawn.mc.player.posX + " y" + (int)AutoRespawn.mc.player.posY + " z" + (int)AutoRespawn.mc.player.posZ);
        }
    }
}

