/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> items;
    private final Setting<Boolean> airStrict;
    private final Setting<Boolean> slimeBlock;
    private ClientCommandC2SPacket STOP;
    private final Setting<Boolean> web;
    private final Setting<Boolean> soulSand;
    private ClientCommandC2SPacket START;
    private boolean shouldSneak;
    private final SettingGroup sgGeneral;

    public boolean slimeBlock() {
        return this.isActive() && this.slimeBlock.get() != false;
    }

    public boolean items() {
        return this.isActive() && this.items.get() != false;
    }

    public boolean web() {
        return this.isActive() && this.web.get() != false;
    }

    public NoSlow() {
        super(Categories.Movement, "no-slow", "Allows you to move normally when using objects that will slow you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.items = this.sgGeneral.add(new BoolSetting.Builder().name("items").description("Whether or not using items will slow you.").defaultValue(true).build());
        this.web = this.sgGeneral.add(new BoolSetting.Builder().name("web").description("Whether or not cobwebs will slow you.").defaultValue(true).build());
        this.soulSand = this.sgGeneral.add(new BoolSetting.Builder().name("soul-sand").description("Whether or not Soul Sand will slow you.").defaultValue(true).build());
        this.slimeBlock = this.sgGeneral.add(new BoolSetting.Builder().name("slime-block").description("Whether or not slime blocks will slow you.").defaultValue(true).build());
        this.airStrict = this.sgGeneral.add(new BoolSetting.Builder().name("air-strict").description("Will attempt to bypass anti-cheats like 2b2t's. Only works while in air.").defaultValue(false).build());
        this.shouldSneak = false;
    }

    @EventHandler
    public void onPreTick(TickEvent.Pre pre) {
        if (!this.airStrict.get().booleanValue()) {
            return;
        }
        if (this.mc.player.isUsingItem()) {
            this.mc.player.networkHandler.sendPacket((Packet)this.START);
            this.shouldSneak = true;
        } else if (this.shouldSneak && !this.mc.player.isUsingItem()) {
            this.mc.player.networkHandler.sendPacket((Packet)this.STOP);
            this.shouldSneak = false;
        }
    }

    public boolean soulSand() {
        return this.isActive() && this.soulSand.get() != false;
    }

    @Override
    public void onActivate() {
        this.START = new ClientCommandC2SPacket((Entity)this.mc.player, ClientCommandC2SPacket.class_2849.PRESS_SHIFT_KEY);
        this.STOP = new ClientCommandC2SPacket((Entity)this.mc.player, ClientCommandC2SPacket.class_2849.RELEASE_SHIFT_KEY);
    }
}

