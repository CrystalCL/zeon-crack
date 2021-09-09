/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket$class_2849
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class NoSlow
extends Module {
    private final /* synthetic */ Setting<Boolean> slimeBlock;
    private final /* synthetic */ Setting<Boolean> soulSand;
    private /* synthetic */ boolean shouldSneak;
    private /* synthetic */ ClientCommandC2SPacket START;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> web;
    private /* synthetic */ ClientCommandC2SPacket STOP;
    private final /* synthetic */ Setting<Boolean> airStrict;
    private final /* synthetic */ Setting<Boolean> items;

    public boolean soulSand() {
        NoSlow llIIlIIIIllIIlI;
        return llIIlIIIIllIIlI.isActive() && llIIlIIIIllIIlI.soulSand.get() != false;
    }

    public boolean items() {
        NoSlow llIIlIIIIlllIII;
        return llIIlIIIIlllIII.isActive() && llIIlIIIIlllIII.items.get() != false;
    }

    public boolean web() {
        NoSlow llIIlIIIIllIlIl;
        return llIIlIIIIllIlIl.isActive() && llIIlIIIIllIlIl.web.get() != false;
    }

    public NoSlow() {
        super(Categories.Movement, "no-slow", "Allows you to move normally when using objects that will slow you.");
        NoSlow llIIlIIIlIIIIlI;
        llIIlIIIlIIIIlI.sgGeneral = llIIlIIIlIIIIlI.settings.getDefaultGroup();
        llIIlIIIlIIIIlI.items = llIIlIIIlIIIIlI.sgGeneral.add(new BoolSetting.Builder().name("items").description("Whether or not using items will slow you.").defaultValue(true).build());
        llIIlIIIlIIIIlI.web = llIIlIIIlIIIIlI.sgGeneral.add(new BoolSetting.Builder().name("web").description("Whether or not cobwebs will slow you.").defaultValue(true).build());
        llIIlIIIlIIIIlI.soulSand = llIIlIIIlIIIIlI.sgGeneral.add(new BoolSetting.Builder().name("soul-sand").description("Whether or not Soul Sand will slow you.").defaultValue(true).build());
        llIIlIIIlIIIIlI.slimeBlock = llIIlIIIlIIIIlI.sgGeneral.add(new BoolSetting.Builder().name("slime-block").description("Whether or not slime blocks will slow you.").defaultValue(true).build());
        llIIlIIIlIIIIlI.airStrict = llIIlIIIlIIIIlI.sgGeneral.add(new BoolSetting.Builder().name("air-strict").description("Will attempt to bypass anti-cheats like 2b2t's. Only works while in air.").defaultValue(false).build());
        llIIlIIIlIIIIlI.shouldSneak = false;
    }

    public boolean slimeBlock() {
        NoSlow llIIlIIIIllIIII;
        return llIIlIIIIllIIII.isActive() && llIIlIIIIllIIII.slimeBlock.get() != false;
    }

    @Override
    public void onActivate() {
        NoSlow llIIlIIIIllllll;
        llIIlIIIIllllll.START = new ClientCommandC2SPacket((Entity)llIIlIIIIllllll.mc.player, ClientCommandC2SPacket.class_2849.PRESS_SHIFT_KEY);
        llIIlIIIIllllll.STOP = new ClientCommandC2SPacket((Entity)llIIlIIIIllllll.mc.player, ClientCommandC2SPacket.class_2849.RELEASE_SHIFT_KEY);
    }

    @EventHandler
    public void onPreTick(TickEvent.Pre llIIlIIIIllllII) {
        NoSlow llIIlIIIIllllIl;
        if (!llIIlIIIIllllIl.airStrict.get().booleanValue()) {
            return;
        }
        if (llIIlIIIIllllIl.mc.player.isUsingItem()) {
            llIIlIIIIllllIl.mc.player.networkHandler.sendPacket((Packet)llIIlIIIIllllIl.START);
            llIIlIIIIllllIl.shouldSneak = true;
        } else if (llIIlIIIIllllIl.shouldSneak && !llIIlIIIIllllIl.mc.player.isUsingItem()) {
            llIIlIIIIllllIl.mc.player.networkHandler.sendPacket((Packet)llIIlIIIIllllIl.STOP);
            llIIlIIIIllllIl.shouldSneak = false;
        }
    }
}

