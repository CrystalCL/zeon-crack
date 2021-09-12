/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import baritone.api.BaritoneAPI;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.PlayerMoveC2SPacketAccessor;
import minegame159.meteorclient.mixininterface.IPlayerMoveC2SPacket;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1657;
import net.minecraft.class_1747;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_239;
import net.minecraft.class_2828;
import net.minecraft.class_3612;
import net.minecraft.class_3959;
import net.minecraft.class_3965;

public class NoFall
extends Module {
    private final Setting<Double> height;
    private final Setting<Boolean> baritone;
    private final Setting<Mode> mode;
    private final Setting<PlaceMode> placeMode;
    private final Setting<Boolean> elytra;
    private boolean placedWater;
    private final SettingGroup sgGeneral;
    private int fallHeightBaritone;

    private void lambda$useBucket$1(int n, boolean bl) {
        int n2 = this.mc.field_1724.field_7514.field_7545;
        this.mc.field_1724.field_7514.field_7545 = n;
        this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, (class_1937)this.mc.field_1687, class_1268.field_5808);
        this.mc.field_1724.field_7514.field_7545 = n2;
        this.placedWater = bl;
    }

    private static boolean lambda$onTick$0(class_1799 class_17992) {
        return class_17992.method_7909() instanceof class_1747;
    }

    @Override
    public String getInfoString() {
        return this.mode.get().toString();
    }

    private void useBucket(int n, boolean bl) {
        Rotations.rotate(this.mc.field_1724.field_6031, 90.0, 10, true, () -> this.lambda$useBucket$1(n, bl));
    }

    public NoFall() {
        super(Categories.Movement, "no-fall", "Prevents you from taking fall damage.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The way you are saved from fall damage.").defaultValue(Mode.Packet).build());
        this.elytra = this.sgGeneral.add(new BoolSetting.Builder().name("elytra-compatibility").description("Stops No Fall from working when using an elytra.").defaultValue(true).build());
        this.baritone = this.sgGeneral.add(new BoolSetting.Builder().name("baritone-compatibility").description("Makes baritone assume you can fall 255 blocks without damage.").defaultValue(true).build());
        this.height = this.sgGeneral.add(new DoubleSetting.Builder().name("height").description("How high you have to be off the ground for this to toggle on.").defaultValue(0.5).min(0.1).sliderMax(1.0).build());
        this.placeMode = this.sgGeneral.add(new EnumSetting.Builder().name("place-mode").description("Whether place mode places before you die or before you take damage.").defaultValue(PlaceMode.BeforeDeath).build());
    }

    @Override
    public void onActivate() {
        if (this.baritone.get().booleanValue()) {
            this.fallHeightBaritone = (Integer)BaritoneAPI.getSettings().maxFallHeightNoWater.get();
            BaritoneAPI.getSettings().maxFallHeightNoWater.value = 255;
        }
        this.placedWater = false;
    }

    @Override
    public void onDeactivate() {
        if (this.baritone.get().booleanValue()) {
            BaritoneAPI.getSettings().maxFallHeightNoWater.value = this.fallHeightBaritone;
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.mc.field_1724.field_7503.field_7477) {
            return;
        }
        if (this.mode.get() == Mode.AirPlace && (this.placeMode.get() == PlaceMode.BeforeDamage && this.mc.field_1724.field_6017 > 2.0f || this.placeMode.get() == PlaceMode.BeforeDeath && this.mc.field_1724.method_6032() + this.mc.field_1724.method_6067() < this.mc.field_1724.field_6017)) {
            int n = InvUtils.findItemInHotbar(NoFall::lambda$onTick$0);
            if (n != -1) {
                BlockUtils.place(this.mc.field_1724.method_24515().method_10074(), class_1268.field_5808, n, true, 10, true);
            }
        } else if (this.mode.get() == Mode.Bucket) {
            class_3965 class_39652;
            int n;
            if (this.placedWater) {
                int n2 = InvUtils.findItemInHotbar(class_1802.field_8550);
                if (n2 != -1 && this.mc.field_1724.method_16212().method_26227().method_15772() == class_3612.field_15910) {
                    this.useBucket(n2, false);
                }
            } else if (this.mc.field_1724.field_6017 > 3.0f && !EntityUtils.isAboveWater((class_1297)this.mc.field_1724) && (n = InvUtils.findItemInHotbar(class_1802.field_8705)) != -1 && (class_39652 = this.mc.field_1687.method_17742(new class_3959(this.mc.field_1724.method_19538(), this.mc.field_1724.method_19538().method_1023(0.0, 5.0, 0.0), class_3959.class_3960.field_17559, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724))) != null && class_39652.method_17783() == class_239.class_240.field_1332) {
                this.useBucket(n, true);
            }
        }
        if (this.mc.field_1724.field_6017 == 0.0f) {
            this.placedWater = false;
        }
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (this.mc.field_1724 != null && this.mc.field_1724.field_7503.field_7477) {
            return;
        }
        if (send.packet instanceof class_2828) {
            if (this.elytra.get().booleanValue() && (this.mc.field_1724.method_6118(class_1304.field_6174).method_7909() == class_1802.field_8833 && this.mc.field_1690.field_1903.method_1434() || this.mc.field_1724.method_6128())) {
                int n = 0;
                while ((double)n <= Math.ceil(this.height.get())) {
                    if (!this.mc.field_1687.method_8320(this.mc.field_1724.method_24515().method_10069(0, -n, 0)).method_26207().method_15800() && (double)(this.mc.field_1724.method_24515().method_10069(0, -n, 0).method_10264() + 1) + this.height.get() >= this.mc.field_1724.method_19538().method_10214()) {
                        ((PlayerMoveC2SPacketAccessor)send.packet).setOnGround(true);
                        return;
                    }
                    ++n;
                }
            } else if (this.mode.get() == Mode.Packet && ((IPlayerMoveC2SPacket)send.packet).getTag() != 1337) {
                ((PlayerMoveC2SPacketAccessor)send.packet).setOnGround(true);
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode AirPlace;
        public static final /* enum */ Mode Packet;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Bucket;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            Packet = new Mode();
            AirPlace = new Mode();
            Bucket = new Mode();
            $VALUES = Mode.$values();
        }

        private static Mode[] $values() {
            return new Mode[]{Packet, AirPlace, Bucket};
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }
    }

    public static final class PlaceMode
    extends Enum<PlaceMode> {
        private static final PlaceMode[] $VALUES;
        public static final /* enum */ PlaceMode BeforeDeath;
        public static final /* enum */ PlaceMode BeforeDamage;

        private static PlaceMode[] $values() {
            return new PlaceMode[]{BeforeDeath, BeforeDamage};
        }

        public static PlaceMode[] values() {
            return (PlaceMode[])$VALUES.clone();
        }

        static {
            BeforeDeath = new PlaceMode();
            BeforeDamage = new PlaceMode();
            $VALUES = PlaceMode.$values();
        }

        public static PlaceMode valueOf(String string) {
            return Enum.valueOf(PlaceMode.class, string);
        }
    }
}

