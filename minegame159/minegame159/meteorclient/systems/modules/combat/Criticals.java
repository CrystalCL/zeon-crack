/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixininterface.IPlayerMoveC2SPacket;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.KillAura;
import net.minecraft.class_1511;
import net.minecraft.class_1937;
import net.minecraft.class_2596;
import net.minecraft.class_2824;
import net.minecraft.class_2828;
import net.minecraft.class_2879;

public class Criticals
extends Module {
    private int sendTimer;
    private final Setting<Mode> mode;
    private final SettingGroup sgGeneral;
    private class_2824 attackPacket;
    private class_2879 swingPacket;
    private final Setting<Boolean> crystals;
    private boolean sendPackets;
    private final Setting<Boolean> ka;

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (send.packet instanceof class_2824 && ((class_2824)send.packet).method_12252() == class_2824.class_2825.field_12875) {
            if (((class_2824)send.packet).method_12248((class_1937)this.mc.field_1687) != Modules.get().get(KillAura.class).getTarget() && this.ka.get().booleanValue()) {
                return;
            }
            if (((class_2824)send.packet).method_12248((class_1937)this.mc.field_1687) instanceof class_1511 && !this.crystals.get().booleanValue()) {
                return;
            }
            if (this.skipCrit()) {
                return;
            }
            if (this.mode.get() == Mode.Packet) {
                this.doPacketMode();
            } else {
                this.doJumpMode(send);
            }
        } else if (send.packet instanceof class_2879 && this.mode.get() != Mode.Packet) {
            if (this.skipCrit()) {
                return;
            }
            this.doJumpModeSwing(send);
        }
    }

    private void doPacketMode() {
        double d = this.mc.field_1724.method_23317();
        double d2 = this.mc.field_1724.method_23318();
        double d3 = this.mc.field_1724.method_23321();
        class_2828.class_2829 class_28292 = new class_2828.class_2829(d, d2 + 0.0625, d3, false);
        class_2828.class_2829 class_28293 = new class_2828.class_2829(d, d2, d3, false);
        ((IPlayerMoveC2SPacket)class_28292).setTag(1337);
        ((IPlayerMoveC2SPacket)class_28293).setTag(1337);
        this.mc.field_1724.field_3944.method_2883((class_2596)class_28292);
        this.mc.field_1724.field_3944.method_2883((class_2596)class_28293);
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.sendPackets) {
            if (this.sendTimer <= 0) {
                this.sendPackets = false;
                if (this.attackPacket == null || this.swingPacket == null) {
                    return;
                }
                this.mc.method_1562().method_2883((class_2596)this.attackPacket);
                this.mc.method_1562().method_2883((class_2596)this.swingPacket);
                this.attackPacket = null;
                this.swingPacket = null;
            } else {
                --this.sendTimer;
            }
        }
    }

    @Override
    public void onActivate() {
        this.attackPacket = null;
        this.swingPacket = null;
        this.sendPackets = false;
        this.sendTimer = 0;
    }

    public Criticals() {
        super(Categories.Combat, "criticals", "Performs critical attacks when you hit your target.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode on how Criticals will function.").defaultValue(Mode.Packet).build());
        this.ka = this.sgGeneral.add(new BoolSetting.Builder().name("only-killaura").description("Only performs crits when using killaura.").defaultValue(false).build());
        this.crystals = this.sgGeneral.add(new BoolSetting.Builder().name("crystals").description("Wether to crit crystals or not.").defaultValue(false).build());
    }

    private void doJumpModeSwing(PacketEvent.Send send) {
        if (this.sendPackets && this.swingPacket == null) {
            this.swingPacket = (class_2879)send.packet;
            send.cancel();
        }
    }

    private boolean skipCrit() {
        boolean bl;
        boolean bl2 = bl = !this.mc.field_1724.method_5869() && !this.mc.field_1724.method_5771() && !this.mc.field_1724.method_6101();
        if (!this.mc.field_1724.method_24828()) {
            return true;
        }
        return !bl;
    }

    @Override
    public String getInfoString() {
        return this.mode.get().name();
    }

    private void doJumpMode(PacketEvent.Send send) {
        if (!this.sendPackets) {
            this.sendPackets = true;
            this.sendTimer = this.mode.get() == Mode.Jump ? 6 : 4;
            this.attackPacket = (class_2824)send.packet;
            if (this.mode.get() == Mode.Jump) {
                this.mc.field_1724.method_6043();
            } else {
                ((IVec3d)this.mc.field_1724.method_18798()).setY(0.25);
            }
            send.cancel();
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Jump;
        public static final /* enum */ Mode MiniJump;
        public static final /* enum */ Mode Packet;

        static {
            Packet = new Mode();
            Jump = new Mode();
            MiniJump = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        private static Mode[] $values() {
            return new Mode[]{Packet, Jump, MiniJump};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

