/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.mixin.CreativeInventoryScreenAccessor;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.input.Input;
import net.minecraft.class_1761;
import net.minecraft.class_408;
import net.minecraft.class_463;
import net.minecraft.class_471;
import net.minecraft.class_481;
import net.minecraft.class_497;
import net.minecraft.class_498;

public class GUIMove
extends Module {
    private final Setting<Boolean> jump;
    private final SettingGroup sgGeneral;
    private final Setting<Double> rotateSpeed;
    private final Setting<Boolean> sneak;
    private final Setting<Boolean> sprint;
    private final Setting<Screens> screens;
    private final Setting<Boolean> arrowsRotate;

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (!this.skip()) {
            switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$movement$GUIMove$Screens[this.screens.get().ordinal()]) {
                case 1: {
                    if (!(this.mc.field_1755 instanceof WidgetScreen)) break;
                    this.tickSneakJumpAndSprint();
                    break;
                }
                case 2: {
                    if (this.mc.field_1755 instanceof WidgetScreen) break;
                    this.tickSneakJumpAndSprint();
                    break;
                }
                case 3: {
                    this.tickSneakJumpAndSprint();
                }
            }
        }
    }

    public void tick() {
        if (!this.isActive() || this.skip()) {
            return;
        }
        this.mc.field_1724.field_3913.field_3905 = 0.0f;
        this.mc.field_1724.field_3913.field_3907 = 0.0f;
        if (Input.isPressed(this.mc.field_1690.field_1894)) {
            this.mc.field_1724.field_3913.field_3910 = true;
            this.mc.field_1724.field_3913.field_3905 += 1.0f;
        } else {
            this.mc.field_1724.field_3913.field_3910 = false;
        }
        if (Input.isPressed(this.mc.field_1690.field_1881)) {
            this.mc.field_1724.field_3913.field_3909 = true;
            this.mc.field_1724.field_3913.field_3905 -= 1.0f;
        } else {
            this.mc.field_1724.field_3913.field_3909 = false;
        }
        if (Input.isPressed(this.mc.field_1690.field_1849)) {
            this.mc.field_1724.field_3913.field_3906 = true;
            this.mc.field_1724.field_3913.field_3907 -= 1.0f;
        } else {
            this.mc.field_1724.field_3913.field_3906 = false;
        }
        if (Input.isPressed(this.mc.field_1690.field_1913)) {
            this.mc.field_1724.field_3913.field_3908 = true;
            this.mc.field_1724.field_3913.field_3907 += 1.0f;
        } else {
            this.mc.field_1724.field_3913.field_3908 = false;
        }
        this.tickSneakJumpAndSprint();
        if (this.arrowsRotate.get().booleanValue()) {
            int n = 0;
            while ((double)n < this.rotateSpeed.get() * 2.0) {
                if (Input.isKeyPressed(263)) {
                    this.mc.field_1724.field_6031 = (float)((double)this.mc.field_1724.field_6031 - 0.5);
                }
                if (Input.isKeyPressed(262)) {
                    this.mc.field_1724.field_6031 = (float)((double)this.mc.field_1724.field_6031 + 0.5);
                }
                if (Input.isKeyPressed(265)) {
                    this.mc.field_1724.field_5965 = (float)((double)this.mc.field_1724.field_5965 - 0.5);
                }
                if (Input.isKeyPressed(264)) {
                    this.mc.field_1724.field_5965 = (float)((double)this.mc.field_1724.field_5965 + 0.5);
                }
                ++n;
                if (0 >= 0) continue;
                return;
            }
            this.mc.field_1724.field_5965 = Utils.clamp(this.mc.field_1724.field_5965, -90.0f, 90.0f);
        }
    }

    private boolean skip() {
        return this.mc.field_1755 == null || Modules.get().isActive(Freecam.class) || this.mc.field_1755 instanceof class_481 && ((CreativeInventoryScreenAccessor)this.mc.field_1755).getSelectedTab() == class_1761.field_7915.method_7741() || this.mc.field_1755 instanceof class_408 || this.mc.field_1755 instanceof class_498 || this.mc.field_1755 instanceof class_471 || this.mc.field_1755 instanceof class_463 || this.mc.field_1755 instanceof class_497;
    }

    public GUIMove() {
        super(Categories.Movement, "gui-move", "Allows you to perform various actions while in GUIs.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.screens = this.sgGeneral.add(new EnumSetting.Builder().name("gUIs").description("Which GUIs to move in.").defaultValue(Screens.Inventory).build());
        this.sneak = this.sgGeneral.add(new BoolSetting.Builder().name("sneak").description("Allows you to sneak while in GUIs.").defaultValue(true).build());
        this.jump = this.sgGeneral.add(new BoolSetting.Builder().name("jump").description("Allows you to jump while in GUIs.").defaultValue(true).build());
        this.sprint = this.sgGeneral.add(new BoolSetting.Builder().name("sprint").description("Allows you to sprint while in GUIs.").defaultValue(true).build());
        this.arrowsRotate = this.sgGeneral.add(new BoolSetting.Builder().name("arrows-rotate").description("Allows you to use your arrow keys to rotate while in GUIs.").defaultValue(true).build());
        this.rotateSpeed = this.sgGeneral.add(new DoubleSetting.Builder().name("rotate-speed").description("Rotation speed while in GUIs.").defaultValue(4.0).min(0.0).build());
    }

    private void tickSneakJumpAndSprint() {
        this.mc.field_1724.field_3913.field_3904 = this.jump.get() != false && Input.isPressed(this.mc.field_1690.field_1903);
        this.mc.field_1724.field_3913.field_3903 = this.sneak.get() != false && Input.isPressed(this.mc.field_1690.field_1832);
        this.mc.field_1724.method_5728(this.sprint.get() != false && Input.isPressed(this.mc.field_1690.field_1867));
    }

    public static final class Screens
    extends Enum<Screens> {
        public static final /* enum */ Screens Inventory;
        private static final Screens[] $VALUES;
        public static final /* enum */ Screens Both;
        public static final /* enum */ Screens GUI;

        public static Screens valueOf(String string) {
            return Enum.valueOf(Screens.class, string);
        }

        static {
            GUI = new Screens();
            Inventory = new Screens();
            Both = new Screens();
            $VALUES = Screens.$values();
        }

        private static Screens[] $values() {
            return new Screens[]{GUI, Inventory, Both};
        }

        public static Screens[] values() {
            return (Screens[])$VALUES.clone();
        }
    }
}

