/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.network.play.server.SPacketChat
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Team
 *  net.minecraft.util.text.ChatType
 */
package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.command.Messages;
import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ChatType;

public class NameProtect
extends Module {
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        SPacketChat packet;
        if (event.getPacket() instanceof SPacketChat && (packet = (SPacketChat)event.getPacket()).getType() != ChatType.GAME_INFO && this.getChatNames(packet.getChatComponent().getFormattedText(), packet.getChatComponent().getUnformattedText())) {
            event.cancel();
        }
    }, new Predicate[0]);

    public NameProtect() {
        super("NameProtect", "", 0, Module.Category.MISC);
    }

    private boolean getChatNames(String message, String unformatted) {
        String out = message;
        if (NameProtect.mc.player == null) {
            return false;
        }
        out = out.replace(NameProtect.mc.player.getName(), "Player");
        Messages.sendSilentMessage(out);
        return true;
    }

    public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        String dname = networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), (String)networkPlayerInfoIn.getGameProfile().getName());
        dname = dname.replace(NameProtect.mc.player.getName(), "Player");
        return dname;
    }
}

