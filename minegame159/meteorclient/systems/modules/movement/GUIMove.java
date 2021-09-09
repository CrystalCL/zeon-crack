/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemGroup
 *  net.minecraft.client.gui.screen.ChatScreen
 *  net.minecraft.client.gui.screen.ingame.AbstractCommandBlockScreen
 *  net.minecraft.client.gui.screen.ingame.AnvilScreen
 *  net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen
 *  net.minecraft.client.gui.screen.ingame.StructureBlockScreen
 *  net.minecraft.client.gui.screen.ingame.SignEditScreen
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
    private final /* synthetic */ Setting<Boolean> sprint;
    private final /* synthetic */ Setting<Double> rotateSpeed;
    private final /* synthetic */ Setting<Boolean> arrowsRotate;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Screens> screens;
    private final /* synthetic */ Setting<Boolean> jump;
    private final /* synthetic */ Setting<Boolean> sneak;

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIlIIIIllllIIlIl) {
        GUIMove lllllllllllllllllIlIIIIllllIIlII;
        if (!lllllllllllllllllIlIIIIllllIIlII.skip()) {
            switch (lllllllllllllllllIlIIIIllllIIlII.screens.get()) {
                case GUI: {
                    if (!(lllllllllllllllllIlIIIIllllIIlII.mc.currentScreen instanceof WidgetScreen)) break;
                    lllllllllllllllllIlIIIIllllIIlII.tickSneakJumpAndSprint();
                    break;
                }
                case Inventory: {
                    if (lllllllllllllllllIlIIIIllllIIlII.mc.currentScreen instanceof WidgetScreen) break;
                    lllllllllllllllllIlIIIIllllIIlII.tickSneakJumpAndSprint();
                    break;
                }
                case Both: {
                    lllllllllllllllllIlIIIIllllIIlII.tickSneakJumpAndSprint();
                }
            }
        }
    }

    public GUIMove() {
        super(Categories.Movement, "gui-move", "Allows you to perform various actions while in GUIs.");
        GUIMove lllllllllllllllllIlIIIIllllIlIIl;
        lllllllllllllllllIlIIIIllllIlIIl.sgGeneral = lllllllllllllllllIlIIIIllllIlIIl.settings.getDefaultGroup();
        lllllllllllllllllIlIIIIllllIlIIl.screens = lllllllllllllllllIlIIIIllllIlIIl.sgGeneral.add(new EnumSetting.Builder().name("gUIs").description("Which GUIs to move in.").defaultValue(Screens.Inventory).build());
        lllllllllllllllllIlIIIIllllIlIIl.sneak = lllllllllllllllllIlIIIIllllIlIIl.sgGeneral.add(new BoolSetting.Builder().name("sneak").description("Allows you to sneak while in GUIs.").defaultValue(true).build());
        lllllllllllllllllIlIIIIllllIlIIl.jump = lllllllllllllllllIlIIIIllllIlIIl.sgGeneral.add(new BoolSetting.Builder().name("jump").description("Allows you to jump while in GUIs.").defaultValue(true).build());
        lllllllllllllllllIlIIIIllllIlIIl.sprint = lllllllllllllllllIlIIIIllllIlIIl.sgGeneral.add(new BoolSetting.Builder().name("sprint").description("Allows you to sprint while in GUIs.").defaultValue(true).build());
        lllllllllllllllllIlIIIIllllIlIIl.arrowsRotate = lllllllllllllllllIlIIIIllllIlIIl.sgGeneral.add(new BoolSetting.Builder().name("arrows-rotate").description("Allows you to use your arrow keys to rotate while in GUIs.").defaultValue(true).build());
        lllllllllllllllllIlIIIIllllIlIIl.rotateSpeed = lllllllllllllllllIlIIIIllllIlIIl.sgGeneral.add(new DoubleSetting.Builder().name("rotate-speed").description("Rotation speed while in GUIs.").defaultValue(4.0).min(0.0).build());
    }

    private boolean skip() {
        GUIMove lllllllllllllllllIlIIIIlllIllIII;
        return lllllllllllllllllIlIIIIlllIllIII.mc.currentScreen == null || Modules.get().isActive(Freecam.class) || lllllllllllllllllIlIIIIlllIllIII.mc.currentScreen instanceof CreativeInventoryScreen && ((CreativeInventoryScreenAccessor)lllllllllllllllllIlIIIIlllIllIII.mc.currentScreen).getSelectedTab() == ItemGroup.SEARCH.getIndex() || lllllllllllllllllIlIIIIlllIllIII.mc.currentScreen instanceof ChatScreen || lllllllllllllllllIlIIIIlllIllIII.mc.currentScreen instanceof SignEditScreen || lllllllllllllllllIlIIIIlllIllIII.mc.currentScreen instanceof AnvilScreen || lllllllllllllllllIlIIIIlllIllIII.mc.currentScreen instanceof AbstractCommandBlockScreen || lllllllllllllllllIlIIIIlllIllIII.mc.currentScreen instanceof StructureBlockScreen;
    }

    private void tickSneakJumpAndSprint() {
        GUIMove lllllllllllllllllIlIIIIlllIlllII;
        lllllllllllllllllIlIIIIlllIlllII.mc.player.input.jumping = lllllllllllllllllIlIIIIlllIlllII.jump.get() != false && Input.isPressed(lllllllllllllllllIlIIIIlllIlllII.mc.options.keyJump);
        lllllllllllllllllIlIIIIlllIlllII.mc.player.input.sneaking = lllllllllllllllllIlIIIIlllIlllII.sneak.get() != false && Input.isPressed(lllllllllllllllllIlIIIIlllIlllII.mc.options.keySneak);
        lllllllllllllllllIlIIIIlllIlllII.mc.player.setSprinting(lllllllllllllllllIlIIIIlllIlllII.sprint.get() != false && Input.isPressed(lllllllllllllllllIlIIIIlllIlllII.mc.options.keySprint));
    }

    public void tick() {
        GUIMove lllllllllllllllllIlIIIIlllIlllll;
        if (!lllllllllllllllllIlIIIIlllIlllll.isActive() || lllllllllllllllllIlIIIIlllIlllll.skip()) {
            return;
        }
        lllllllllllllllllIlIIIIlllIlllll.mc.player.input.movementForward = 0.0f;
        lllllllllllllllllIlIIIIlllIlllll.mc.player.input.movementSideways = 0.0f;
        if (Input.isPressed(lllllllllllllllllIlIIIIlllIlllll.mc.options.keyForward)) {
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.pressingForward = true;
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.movementForward += 1.0f;
        } else {
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.pressingForward = false;
        }
        if (Input.isPressed(lllllllllllllllllIlIIIIlllIlllll.mc.options.keyBack)) {
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.pressingBack = true;
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.movementForward -= 1.0f;
        } else {
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.pressingBack = false;
        }
        if (Input.isPressed(lllllllllllllllllIlIIIIlllIlllll.mc.options.keyRight)) {
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.pressingRight = true;
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.movementSideways -= 1.0f;
        } else {
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.pressingRight = false;
        }
        if (Input.isPressed(lllllllllllllllllIlIIIIlllIlllll.mc.options.keyLeft)) {
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.pressingLeft = true;
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.movementSideways += 1.0f;
        } else {
            lllllllllllllllllIlIIIIlllIlllll.mc.player.input.pressingLeft = false;
        }
        lllllllllllllllllIlIIIIlllIlllll.tickSneakJumpAndSprint();
        if (lllllllllllllllllIlIIIIlllIlllll.arrowsRotate.get().booleanValue()) {
            int lllllllllllllllllIlIIIIllllIIIIl = 0;
            while ((double)lllllllllllllllllIlIIIIllllIIIIl < lllllllllllllllllIlIIIIlllIlllll.rotateSpeed.get() * 2.0) {
                if (Input.isKeyPressed(263)) {
                    lllllllllllllllllIlIIIIlllIlllll.mc.player.yaw = (float)((double)lllllllllllllllllIlIIIIlllIlllll.mc.player.yaw - 0.5);
                }
                if (Input.isKeyPressed(262)) {
                    lllllllllllllllllIlIIIIlllIlllll.mc.player.yaw = (float)((double)lllllllllllllllllIlIIIIlllIlllll.mc.player.yaw + 0.5);
                }
                if (Input.isKeyPressed(265)) {
                    lllllllllllllllllIlIIIIlllIlllll.mc.player.pitch = (float)((double)lllllllllllllllllIlIIIIlllIlllll.mc.player.pitch - 0.5);
                }
                if (Input.isKeyPressed(264)) {
                    lllllllllllllllllIlIIIIlllIlllll.mc.player.pitch = (float)((double)lllllllllllllllllIlIIIIlllIlllll.mc.player.pitch + 0.5);
                }
                ++lllllllllllllllllIlIIIIllllIIIIl;
            }
            lllllllllllllllllIlIIIIlllIlllll.mc.player.pitch = Utils.clamp(lllllllllllllllllIlIIIIlllIlllll.mc.player.pitch, -90.0f, 90.0f);
        }
    }

    public static final class Screens
    extends Enum<Screens> {
        private static final /* synthetic */ Screens[] $VALUES;
        public static final /* synthetic */ /* enum */ Screens Inventory;
        public static final /* synthetic */ /* enum */ Screens Both;
        public static final /* synthetic */ /* enum */ Screens GUI;

        private Screens() {
            Screens llllllllllllllllllIllIlIlIlIlllI;
        }

        static {
            GUI = new Screens();
            Inventory = new Screens();
            Both = new Screens();
            $VALUES = Screens.$values();
        }

        public static Screens valueOf(String llllllllllllllllllIllIlIlIllIIll) {
            return Enum.valueOf(Screens.class, llllllllllllllllllIllIlIlIllIIll);
        }

        private static /* synthetic */ Screens[] $values() {
            return new Screens[]{GUI, Inventory, Both};
        }

        public static Screens[] values() {
            return (Screens[])$VALUES.clone();
        }
    }
}

