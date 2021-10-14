/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 */
package com.rianix.revenge.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.main.Globals;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class Messages {
    public static void sendPlayerMessage(String ... message) {
        for (String m : message) {
            Minecraft.getMinecraft().player.sendChatMessage(m);
        }
    }

    public static void sendSilentMessage(String ... message) {
        for (String m : message) {
            Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentString(m));
        }
    }

    public static void sendClientMessage(String ... message) {
        for (String m : message) {
            String prefix;
            if (Revenge.moduleManager.getModuleByClass(Globals.class).rainbowPrefix.getValue()) {
                prefix = "\u00a7+[revenge]\u00a7r ";
                Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentString(prefix + m));
                continue;
            }
            prefix = (Object)ChatFormatting.GRAY + "[" + (Object)ChatFormatting.WHITE + "revenge" + (Object)ChatFormatting.GRAY + "] " + (Object)ChatFormatting.RESET;
            Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentString(prefix + m));
        }
    }
}

