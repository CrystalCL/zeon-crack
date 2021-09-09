/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.movement;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.meteor.KeyEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.utils.misc.input.KeyAction;

public class AirJump
extends Module {
    private final /* synthetic */ Setting<Boolean> onGround;
    private final /* synthetic */ Setting<Boolean> maintainY;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> onHold;
    private /* synthetic */ int level;

    @EventHandler
    private void onKey(KeyEvent lllIIIlIllIl) {
        AirJump lllIIIllIIII;
        if (Modules.get().isActive(Freecam.class) || lllIIIllIIII.mc.currentScreen != null || !lllIIIllIIII.onGround.get().booleanValue() && lllIIIllIIII.mc.player.isOnGround()) {
            return;
        }
        if ((lllIIIlIllIl.action == KeyAction.Press || lllIIIlIllIl.action == KeyAction.Repeat && lllIIIllIIII.onHold.get().booleanValue()) && lllIIIllIIII.mc.options.keyJump.matchesKey(lllIIIlIllIl.key, 0)) {
            lllIIIllIIII.mc.player.jump();
            lllIIIllIIII.level = lllIIIllIIII.mc.player.getBlockPos().getY();
        }
        if ((lllIIIlIllIl.action == KeyAction.Press || lllIIIlIllIl.action == KeyAction.Repeat && lllIIIllIIII.onHold.get().booleanValue()) && lllIIIllIIII.mc.options.keySneak.matchesKey(lllIIIlIllIl.key, 0)) {
            --lllIIIllIIII.level;
        }
    }

    public AirJump() {
        super(Categories.Movement, "air-jump", "Lets you jump in the air.");
        AirJump lllIIIllIIll;
        lllIIIllIIll.sgGeneral = lllIIIllIIll.settings.getDefaultGroup();
        lllIIIllIIll.maintainY = lllIIIllIIll.sgGeneral.add(new BoolSetting.Builder().name("maintain-level").description("Maintains your current Y level.").defaultValue(false).build());
        lllIIIllIIll.onHold = lllIIIllIIll.sgGeneral.add(new BoolSetting.Builder().name("on-hold").description("Whether or not to air jump if you hold down the space bar.").defaultValue(true).build());
        lllIIIllIIll.onGround = lllIIIllIIll.sgGeneral.add(new BoolSetting.Builder().name("on-ground").description("Whether to airjump if you are on the ground.").defaultValue(false).build());
        lllIIIllIIll.level = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Post lllIIIlIlIlI) {
        AirJump lllIIIlIlIll;
        if (Modules.get().isActive(Freecam.class) || !lllIIIlIlIll.onGround.get().booleanValue() && lllIIIlIlIll.mc.player.isOnGround()) {
            return;
        }
        if (lllIIIlIlIll.maintainY.get().booleanValue() && lllIIIlIlIll.mc.player.getBlockPos().getY() == lllIIIlIlIll.level) {
            lllIIIlIlIll.mc.player.jump();
        }
    }
}

