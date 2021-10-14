/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.item.ItemAppleGold
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemFood
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.event.entity.living.LivingEntityUseItemEvent$Finish
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 */
package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.command.Messages;
import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingDouble;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class Announcer
extends Module {
    SettingDouble delay = this.register("Delay", 1.0, 1.0, 20.0);
    SettingBoolean clientSide = this.register("ClientSide", false);
    SettingBoolean walk = this.register("Walk", true);
    SettingBoolean attack = this.register("Attack", true);
    SettingBoolean eat = this.register("Eat", true);
    SettingBoolean place = this.register("Place", true);
    public static int blockBrokeDelay = 0;
    static int blockPlacedDelay = 0;
    static int jumpDelay = 0;
    static int attackDelay = 0;
    static int eattingDelay = 0;
    static long lastPositionUpdate;
    static double lastPositionX;
    static double lastPositionY;
    static double lastPositionZ;
    private static double speed;
    String heldItem = "";
    int blocksPlaced = 0;
    int blocksBroken = 0;
    int eaten = 0;
    public static String walkMessage;
    public static String placeMessage;
    public static String attackMessage;
    public static String eatMessage;
    @EventHandler
    private Listener<LivingEntityUseItemEvent.Finish> eatListener = new Listener<LivingEntityUseItemEvent.Finish>(event -> {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
        if (event.getEntity() == Announcer.mc.player && (event.getItem().getItem() instanceof ItemFood || event.getItem().getItem() instanceof ItemAppleGold)) {
            ++this.eaten;
            if ((double)eattingDelay >= 300.0 * this.delay.getValue() && this.eat.getValue() && this.eaten > randomNum) {
                if (this.clientSide.getValue()) {
                    Messages.sendClientMessage(eatMessage.replace("{amount}", this.eaten + "").replace("{name}", Announcer.mc.player.getHeldItemMainhand().getDisplayName()));
                } else {
                    Announcer.mc.player.sendChatMessage(eatMessage.replace("{amount}", this.eaten + "").replace("{name}", Announcer.mc.player.getHeldItemMainhand().getDisplayName()));
                }
                this.eaten = 0;
                eattingDelay = 0;
            }
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<PacketEvent.Send> sendListener = new Listener<PacketEvent.Send>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && Announcer.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
            ++this.blocksPlaced;
            int randomNum = ThreadLocalRandom.current().nextInt(1, 11);
            if ((double)blockPlacedDelay >= 150.0 * this.delay.getValue() && this.place.getValue() && this.blocksPlaced > randomNum) {
                String msg = placeMessage.replace("{amount}", this.blocksPlaced + "").replace("{name}", Announcer.mc.player.getHeldItemMainhand().getDisplayName());
                if (this.clientSide.getValue()) {
                    Messages.sendClientMessage(msg);
                } else {
                    Announcer.mc.player.sendChatMessage(msg);
                }
                this.blocksPlaced = 0;
                blockPlacedDelay = 0;
            }
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<AttackEntityEvent> attackListener = new Listener<AttackEntityEvent>(event -> {
        if (this.attack.getValue() && !(event.getTarget() instanceof EntityEnderCrystal) && (double)attackDelay >= 300.0 * this.delay.getValue()) {
            String msg = attackMessage.replace("{name}", event.getTarget().getName()).replace("{item}", Announcer.mc.player.getHeldItemMainhand().getDisplayName());
            if (this.clientSide.getValue()) {
                Messages.sendClientMessage(msg);
            } else {
                Announcer.mc.player.sendChatMessage(msg);
            }
            attackDelay = 0;
        }
    }, new Predicate[0]);

    public Announcer() {
        super("Announcer", "", 0, Module.Category.MISC);
    }

    @Override
    public void onEnable() {
        this.blocksPlaced = 0;
        this.blocksBroken = 0;
        this.eaten = 0;
        speed = 0.0;
        blockBrokeDelay = 0;
        blockPlacedDelay = 0;
        jumpDelay = 0;
        attackDelay = 0;
        eattingDelay = 0;
    }

    @Override
    public void onUpdate() {
        double d3;
        double d2;
        double d0;
        ++blockBrokeDelay;
        ++blockPlacedDelay;
        ++jumpDelay;
        ++attackDelay;
        ++eattingDelay;
        this.heldItem = Announcer.mc.player.getHeldItemMainhand().getDisplayName();
        if (this.walk.getValue() && (double)lastPositionUpdate + 5000.0 * this.delay.getValue() < (double)System.currentTimeMillis() && !((speed = Math.sqrt((d0 = lastPositionX - Announcer.mc.player.lastTickPosX) * d0 + (d2 = lastPositionY - Announcer.mc.player.lastTickPosY) * d2 + (d3 = lastPositionZ - Announcer.mc.player.lastTickPosZ) * d3)) <= 1.0) && !(speed > 5000.0)) {
            String walkAmount = new DecimalFormat("0").format(speed);
            if (this.clientSide.getValue()) {
                Messages.sendClientMessage(walkMessage.replace("{blocks}", walkAmount));
            } else {
                Announcer.mc.player.sendChatMessage(walkMessage.replace("{blocks}", walkAmount));
            }
            lastPositionUpdate = System.currentTimeMillis();
            lastPositionX = Announcer.mc.player.lastTickPosX;
            lastPositionY = Announcer.mc.player.lastTickPosY;
            lastPositionZ = Announcer.mc.player.lastTickPosZ;
        }
    }

    static {
        walkMessage = "I just walked {blocks} blocks thanks to Revenge!";
        placeMessage = "I just placed {amount} {name} thanks to Revenge!";
        attackMessage = "I just attacked {name} with a {item} thanks to Revenge!";
        eatMessage = "I just ate {amount} {name} thanks to Revenge!";
    }
}

