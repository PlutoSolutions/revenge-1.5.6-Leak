/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.network.play.server.SPacketDestroyEntities
 */
package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingDouble;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.server.SPacketDestroyEntities;

public class CrystalScale
extends Module {
    public SettingDouble scale = this.register("Scale", 1.0, 0.1f, 3.0);
    public SettingDouble x = this.register("ScaleX", 0.0, -1.0, 0.0);
    public SettingDouble y = this.register("ScaleY", 0.0, -1.0, 0.0);
    public SettingDouble z = this.register("ScaleZ", 0.0, -1.0, 0.0);
    public Map<EntityEnderCrystal, Float> scaleMap = new ConcurrentHashMap<EntityEnderCrystal, Float>();
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<PacketEvent.Receive>(event -> {
        if (event.getPacket() instanceof SPacketDestroyEntities) {
            SPacketDestroyEntities packet = (SPacketDestroyEntities)event.getPacket();
            for (int id : packet.getEntityIDs()) {
                Entity entity = CrystalScale.mc.world.getEntityByID(id);
                if (!(entity instanceof EntityEnderCrystal)) continue;
                this.scaleMap.remove((Object)entity);
            }
        }
    }, new Predicate[0]);

    public CrystalScale() {
        super("CrystalScale", "", 0, Module.Category.RENDER);
    }

    @Override
    public void onUpdate() {
        for (Entity crystal : CrystalScale.mc.world.loadedEntityList) {
            if (!(crystal instanceof EntityEnderCrystal)) continue;
            if (!this.scaleMap.containsKey((Object)crystal)) {
                this.scaleMap.put((EntityEnderCrystal)crystal, Float.valueOf(3.125E-4f));
            } else {
                this.scaleMap.put((EntityEnderCrystal)crystal, Float.valueOf(this.scaleMap.get((Object)crystal).floatValue() + 3.125E-4f));
            }
            if (!((double)this.scaleMap.get((Object)crystal).floatValue() >= 0.0625 * this.scale.getValue())) continue;
            this.scaleMap.remove((Object)crystal);
        }
    }
}

