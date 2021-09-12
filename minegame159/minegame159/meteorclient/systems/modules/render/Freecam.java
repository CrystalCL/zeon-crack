/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.TookDamageEvent;
import minegame159.meteorclient.events.game.GameLeftEvent;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.events.meteor.KeyEvent;
import minegame159.meteorclient.events.world.ChunkOcclusionEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.Vec3;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.Rotations;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.class_124;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_304;
import net.minecraft.class_3532;
import net.minecraft.class_3965;
import net.minecraft.class_3966;

public class Freecam
extends Module {
    private final Setting<Boolean> autoDisableOnLog;
    public final Vec3 pos;
    public float yaw;
    public float prevYaw;
    private final Setting<Boolean> rotate;
    private final SettingGroup sgGeneral;
    private boolean up;
    public float pitch;
    private boolean backward;
    private boolean left;
    private final Setting<Boolean> reloadChunks;
    private final Setting<Double> speed;
    private final Setting<Boolean> renderHands;
    private boolean right;
    private final Setting<AutoDisableEvent> autoDisableOnDamage;
    private boolean forward;
    private boolean down;
    public float prevPitch;
    public final Vec3 prevPos;

    public Freecam() {
        super(Categories.Render, "freecam", "Makes you fly out of your body.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.speed = this.sgGeneral.add(new DoubleSetting.Builder().name("speed").description("Your speed while in Freecam.").defaultValue(1.0).min(0.0).build());
        this.autoDisableOnDamage = this.sgGeneral.add(new EnumSetting.Builder().name("auto-disable-on-damage").description("Disables Freecam when you either take damage or die.").defaultValue(AutoDisableEvent.OnDamage).build());
        this.autoDisableOnLog = this.sgGeneral.add(new BoolSetting.Builder().name("auto-disable-on-log").description("Disables Freecam when you disconnect from a server.").defaultValue(true).build());
        this.reloadChunks = this.sgGeneral.add(new BoolSetting.Builder().name("reload-chunks").description("Disables cave culling.").defaultValue(true).build());
        this.renderHands = this.sgGeneral.add(new BoolSetting.Builder().name("render-hand").description("Whether or not to render your hand in Freecam.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Rotates your character to whatever block or entity you are looking at.").defaultValue(false).build());
        this.pos = new Vec3();
        this.prevPos = new Vec3();
    }

    public double getPitch(float f) {
        return class_3532.method_16439((float)f, (float)this.prevPitch, (float)this.pitch);
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.mc.field_1719.method_5757()) {
            this.mc.method_1560().field_5960 = true;
        }
        if (this.mc.field_1755 != null) {
            return;
        }
        class_243 class_2432 = class_243.method_1030((float)0.0f, (float)this.yaw);
        class_243 class_2433 = class_243.method_1030((float)0.0f, (float)(this.yaw + 90.0f));
        double d = 0.0;
        double d2 = 0.0;
        double d3 = 0.0;
        if (this.rotate.get().booleanValue()) {
            class_2338 class_23382;
            if (this.mc.field_1765 instanceof class_3965) {
                class_243 class_2434 = this.mc.field_1765.method_17784();
                class_23382 = ((class_3965)this.mc.field_1765).method_17777();
                if (!this.mc.field_1687.method_8320(class_23382).method_26215()) {
                    this.mc.field_1724.field_6031 = (float)Rotations.getYaw(class_2434);
                    this.mc.field_1724.field_5965 = (float)Rotations.getPitch(class_2434);
                }
            } else {
                class_23382 = ((class_3966)this.mc.field_1765).method_17782().method_24515();
                this.mc.field_1724.field_6031 = (float)Rotations.getYaw(class_23382);
                this.mc.field_1724.field_5965 = (float)Rotations.getPitch(class_23382);
            }
        }
        double d4 = 0.5;
        if (this.mc.field_1690.field_1867.method_1434()) {
            d4 = 1.0;
        }
        boolean bl = false;
        if (this.forward) {
            d += class_2432.field_1352 * d4 * this.speed.get();
            d3 += class_2432.field_1350 * d4 * this.speed.get();
            bl = true;
        }
        if (this.backward) {
            d -= class_2432.field_1352 * d4 * this.speed.get();
            d3 -= class_2432.field_1350 * d4 * this.speed.get();
            bl = true;
        }
        boolean bl2 = false;
        if (this.right) {
            d += class_2433.field_1352 * d4 * this.speed.get();
            d3 += class_2433.field_1350 * d4 * this.speed.get();
            bl2 = true;
        }
        if (this.left) {
            d -= class_2433.field_1352 * d4 * this.speed.get();
            d3 -= class_2433.field_1350 * d4 * this.speed.get();
            bl2 = true;
        }
        if (bl && bl2) {
            double d5 = 1.0 / Math.sqrt(2.0);
            d *= d5;
            d3 *= d5;
        }
        if (this.up) {
            d2 += d4 * this.speed.get();
        }
        if (this.down) {
            d2 -= d4 * this.speed.get();
        }
        this.prevPos.set(this.pos);
        this.pos.set(this.pos.x + d, this.pos.y + d2, this.pos.z + d3);
    }

    @EventHandler
    private void onKey(KeyEvent keyEvent) {
        boolean bl = true;
        if (KeyBindingHelper.getBoundKeyOf((class_304)this.mc.field_1690.field_1894).method_1444() == keyEvent.key) {
            this.forward = keyEvent.action != KeyAction.Release;
        } else if (KeyBindingHelper.getBoundKeyOf((class_304)this.mc.field_1690.field_1881).method_1444() == keyEvent.key) {
            this.backward = keyEvent.action != KeyAction.Release;
        } else if (KeyBindingHelper.getBoundKeyOf((class_304)this.mc.field_1690.field_1849).method_1444() == keyEvent.key) {
            this.right = keyEvent.action != KeyAction.Release;
        } else if (KeyBindingHelper.getBoundKeyOf((class_304)this.mc.field_1690.field_1913).method_1444() == keyEvent.key) {
            this.left = keyEvent.action != KeyAction.Release;
        } else if (KeyBindingHelper.getBoundKeyOf((class_304)this.mc.field_1690.field_1903).method_1444() == keyEvent.key) {
            this.up = keyEvent.action != KeyAction.Release;
        } else if (KeyBindingHelper.getBoundKeyOf((class_304)this.mc.field_1690.field_1832).method_1444() == keyEvent.key) {
            this.down = keyEvent.action != KeyAction.Release;
        } else {
            bl = false;
        }
        if (bl) {
            keyEvent.cancel();
        }
    }

    @EventHandler
    private void onTookDamage(TookDamageEvent tookDamageEvent) {
        if (tookDamageEvent.entity.method_5667() == null) {
            return;
        }
        if (!tookDamageEvent.entity.method_5667().equals(this.mc.field_1724.method_5667())) {
            return;
        }
        if (this.autoDisableOnDamage.get() == AutoDisableEvent.OnDamage || this.autoDisableOnDamage.get() == AutoDisableEvent.OnDeath && tookDamageEvent.entity.method_6032() <= 0.0f) {
            this.toggle();
            ChatUtils.moduleInfo(this, "Auto toggled %s(default).", this.isActive() ? String.valueOf(new StringBuilder().append(class_124.field_1060).append("on")) : String.valueOf(new StringBuilder().append(class_124.field_1061).append("off")));
        }
    }

    public double getYaw(float f) {
        return class_3532.method_16439((float)f, (float)this.prevYaw, (float)this.yaw);
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent gameLeftEvent) {
        if (!this.autoDisableOnLog.get().booleanValue()) {
            return;
        }
        this.toggle();
    }

    public double getX(float f) {
        return class_3532.method_16436((double)f, (double)this.prevPos.x, (double)this.pos.x);
    }

    public double getZ(float f) {
        return class_3532.method_16436((double)f, (double)this.prevPos.z, (double)this.pos.z);
    }

    public boolean renderHands() {
        return !this.isActive() || this.renderHands.get() != false;
    }

    @Override
    public void onDeactivate() {
        if (this.reloadChunks.get().booleanValue()) {
            this.mc.field_1769.method_3279();
        }
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent openScreenEvent) {
        this.unpress();
        this.prevPos.set(this.pos);
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
    }

    @EventHandler
    private void onChunkOcclusion(ChunkOcclusionEvent chunkOcclusionEvent) {
        chunkOcclusionEvent.cancel();
    }

    private void unpress() {
        this.mc.field_1690.field_1894.method_23481(false);
        this.mc.field_1690.field_1881.method_23481(false);
        this.mc.field_1690.field_1849.method_23481(false);
        this.mc.field_1690.field_1913.method_23481(false);
        this.mc.field_1690.field_1903.method_23481(false);
        this.mc.field_1690.field_1832.method_23481(false);
    }

    @Override
    public void onActivate() {
        this.yaw = this.mc.field_1724.field_6031;
        this.pitch = this.mc.field_1724.field_5965;
        this.pos.set(this.mc.field_1773.method_19418().method_19326());
        this.prevPos.set(this.mc.field_1773.method_19418().method_19326());
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
        this.forward = false;
        this.backward = false;
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;
        this.unpress();
        if (this.reloadChunks.get().booleanValue()) {
            this.mc.field_1769.method_3279();
        }
    }

    public double getY(float f) {
        return class_3532.method_16436((double)f, (double)this.prevPos.y, (double)this.pos.y);
    }

    public void changeLookDirection(double d, double d2) {
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
        this.yaw = (float)((double)this.yaw + d);
        this.pitch = (float)((double)this.pitch + d2);
        this.pitch = class_3532.method_15363((float)this.pitch, (float)-90.0f, (float)90.0f);
    }

    public static final class AutoDisableEvent
    extends Enum<AutoDisableEvent> {
        public static final /* enum */ AutoDisableEvent OnDamage;
        public static final /* enum */ AutoDisableEvent OnDeath;
        private static final AutoDisableEvent[] $VALUES;
        public static final /* enum */ AutoDisableEvent None;

        private static AutoDisableEvent[] $values() {
            return new AutoDisableEvent[]{None, OnDamage, OnDeath};
        }

        static {
            None = new AutoDisableEvent();
            OnDamage = new AutoDisableEvent();
            OnDeath = new AutoDisableEvent();
            $VALUES = AutoDisableEvent.$values();
        }

        public static AutoDisableEvent[] values() {
            return (AutoDisableEvent[])$VALUES.clone();
        }

        public static AutoDisableEvent valueOf(String string) {
            return Enum.valueOf(AutoDisableEvent.class, string);
        }
    }
}

