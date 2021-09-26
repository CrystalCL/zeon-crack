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
import net.minecraft.item.ItemGroup;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.AbstractCommandBlockScreen;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.StructureBlockScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;

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
                    if (!(this.mc.currentScreen instanceof WidgetScreen)) break;
                    this.tickSneakJumpAndSprint();
                    break;
                }
                case 2: {
                    if (this.mc.currentScreen instanceof WidgetScreen) break;
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
        this.mc.player.input.movementForward = 0.0f;
        this.mc.player.input.movementSideways = 0.0f;
        if (Input.isPressed(this.mc.options.keyForward)) {
            this.mc.player.input.pressingForward = true;
            this.mc.player.input.movementForward += 1.0f;
        } else {
            this.mc.player.input.pressingForward = false;
        }
        if (Input.isPressed(this.mc.options.keyBack)) {
            this.mc.player.input.pressingBack = true;
            this.mc.player.input.movementForward -= 1.0f;
        } else {
            this.mc.player.input.pressingBack = false;
        }
        if (Input.isPressed(this.mc.options.keyRight)) {
            this.mc.player.input.pressingRight = true;
            this.mc.player.input.movementSideways -= 1.0f;
        } else {
            this.mc.player.input.pressingRight = false;
        }
        if (Input.isPressed(this.mc.options.keyLeft)) {
            this.mc.player.input.pressingLeft = true;
            this.mc.player.input.movementSideways += 1.0f;
        } else {
            this.mc.player.input.pressingLeft = false;
        }
        this.tickSneakJumpAndSprint();
        if (this.arrowsRotate.get().booleanValue()) {
            int n = 0;
            while ((double)n < this.rotateSpeed.get() * 2.0) {
                if (Input.isKeyPressed(263)) {
                    this.mc.player.yaw = (float)((double)this.mc.player.yaw - 0.5);
                }
                if (Input.isKeyPressed(262)) {
                    this.mc.player.yaw = (float)((double)this.mc.player.yaw + 0.5);
                }
                if (Input.isKeyPressed(265)) {
                    this.mc.player.pitch = (float)((double)this.mc.player.pitch - 0.5);
                }
                if (Input.isKeyPressed(264)) {
                    this.mc.player.pitch = (float)((double)this.mc.player.pitch + 0.5);
                }
                ++n;
                if (0 >= 0) continue;
                return;
            }
            this.mc.player.pitch = Utils.clamp(this.mc.player.pitch, -90.0f, 90.0f);
        }
    }

    private boolean skip() {
        return this.mc.currentScreen == null || Modules.get().isActive(Freecam.class) || this.mc.currentScreen instanceof CreativeInventoryScreen && ((CreativeInventoryScreenAccessor)this.mc.currentScreen).getSelectedTab() == ItemGroup.SEARCH.getIndex() || this.mc.currentScreen instanceof ChatScreen || this.mc.currentScreen instanceof SignEditScreen || this.mc.currentScreen instanceof AnvilScreen || this.mc.currentScreen instanceof AbstractCommandBlockScreen || this.mc.currentScreen instanceof StructureBlockScreen;
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
        this.mc.player.input.jumping = this.jump.get() != false && Input.isPressed(this.mc.options.keyJump);
        this.mc.player.input.sneaking = this.sneak.get() != false && Input.isPressed(this.mc.options.keySneak);
        this.mc.player.setSprinting(this.sprint.get() != false && Input.isPressed(this.mc.options.keySprint));
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

