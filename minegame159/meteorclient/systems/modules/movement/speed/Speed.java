/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.MovementType
 *  net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket
 */
package minegame159.meteorclient.systems.modules.movement.speed;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.PlayerMoveEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.AutoJump;
import minegame159.meteorclient.systems.modules.movement.speed.SpeedMode;
import minegame159.meteorclient.systems.modules.movement.speed.SpeedModes;
import minegame159.meteorclient.systems.modules.movement.speed.modes.NCP;
import minegame159.meteorclient.systems.modules.movement.speed.modes.Vanilla;
import minegame159.meteorclient.systems.modules.world.Timer;
import minegame159.meteorclient.utils.player.PlayerUtils;
import net.minecraft.entity.MovementType;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public class Speed
extends Module {
    private final /* synthetic */ SettingGroup sgNCP;
    public final /* synthetic */ Setting<Boolean> jump;
    public final /* synthetic */ Setting<Boolean> onlyOnGround;
    public final /* synthetic */ Setting<Boolean> whenSneaking;
    private final /* synthetic */ SettingGroup sgDefault;
    public final /* synthetic */ Setting<Boolean> applySpeedPotions;
    public final /* synthetic */ Setting<Double> speed;
    public final /* synthetic */ Setting<Boolean> inLiquids;
    public final /* synthetic */ Setting<SpeedModes> speedMode;
    public final /* synthetic */ Setting<Double> hopHeight;
    public final /* synthetic */ Setting<Double> timer;
    public final /* synthetic */ Setting<Double> ncpSpeed;
    public final /* synthetic */ Setting<AutoJump.JumpWhen> jumpIf;
    private final /* synthetic */ SettingGroup sgVanilla;
    private /* synthetic */ SpeedMode currentMode;
    public final /* synthetic */ Setting<Boolean> ncpSpeedLimit;
    public final /* synthetic */ Setting<AutoJump.Mode> jumpMode;

    @Override
    public void onDeactivate() {
        Speed llllllllllllllllllIIllIIlIlIIIII;
        Modules.get().get(Timer.class).setOverride(1.0);
        llllllllllllllllllIIllIIlIlIIIII.currentMode.onDeactivate();
    }

    @Override
    public void onActivate() {
        Speed llllllllllllllllllIIllIIlIlIIIlI;
        llllllllllllllllllIIllIIlIlIIIlI.currentMode.onActivate();
    }

    @Override
    public String getInfoString() {
        Speed llllllllllllllllllIIllIIlIIIIlll;
        return llllllllllllllllllIIllIIlIIIIlll.currentMode.getHudString();
    }

    private void onSpeedModeChanged(SpeedModes llllllllllllllllllIIllIIlIIIlIIl) {
        switch (llllllllllllllllllIIllIIlIIIlIIl) {
            case Vanilla: {
                llllllllllllllllllIIllIIlIIIllII.currentMode = new Vanilla();
                break;
            }
            case NCP: {
                llllllllllllllllllIIllIIlIIIllII.currentMode = new NCP();
            }
        }
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre llllllllllllllllllIIllIIlIIlIllI) {
        Speed llllllllllllllllllIIllIIlIIlIlll;
        if (llllllllllllllllllIIllIIlIIlIlll.mc.player.isFallFlying() || llllllllllllllllllIIllIIlIIlIlll.mc.player.isClimbing() || llllllllllllllllllIIllIIlIIlIlll.mc.player.getVehicle() != null || llllllllllllllllllIIllIIlIIlIlll.whenSneaking.get() == false && llllllllllllllllllIIllIIlIIlIlll.mc.player.isSneaking() || llllllllllllllllllIIllIIlIIlIlll.onlyOnGround.get() != false && !llllllllllllllllllIIllIIlIIlIlll.mc.player.isOnGround() || !llllllllllllllllllIIllIIlIIlIlll.inLiquids.get().booleanValue() && (llllllllllllllllllIIllIIlIIlIlll.mc.player.isTouchingWater() || llllllllllllllllllIIllIIlIIlIlll.mc.player.isInLava())) {
            return;
        }
        llllllllllllllllllIIllIIlIIlIlll.currentMode.onTick();
    }

    public Speed() {
        super(Categories.Movement, "speed", "Modifies your movement speed when moving on the ground.");
        Speed llllllllllllllllllIIllIIlIlIIllI;
        llllllllllllllllllIIllIIlIlIIllI.sgDefault = llllllllllllllllllIIllIIlIlIIllI.settings.getDefaultGroup();
        llllllllllllllllllIIllIIlIlIIllI.sgVanilla = llllllllllllllllllIIllIIlIlIIllI.settings.createGroup("Vanilla");
        llllllllllllllllllIIllIIlIlIIllI.sgNCP = llllllllllllllllllIIllIIlIlIIllI.settings.createGroup("NCP");
        llllllllllllllllllIIllIIlIlIIllI.speedMode = llllllllllllllllllIIllIIlIlIIllI.sgDefault.add(new EnumSetting.Builder().name("mode").description("The method of applying speed.").defaultValue(SpeedModes.Vanilla).onModuleActivated(llllllllllllllllllIIllIIlIIIIIlI -> {
            Speed llllllllllllllllllIIllIIlIIIIIll;
            llllllllllllllllllIIllIIlIIIIIll.onSpeedModeChanged((SpeedModes)((Object)((Object)llllllllllllllllllIIllIIlIIIIIlI.get())));
        }).onChanged(llllllllllllllllllIIllIIlIlIIllI::onSpeedModeChanged).build());
        llllllllllllllllllIIllIIlIlIIllI.timer = llllllllllllllllllIIllIIlIlIIllI.sgDefault.add(new DoubleSetting.Builder().name("timer").description("Timer override.").defaultValue(1.0).min(0.01).sliderMin(0.01).sliderMax(10.0).build());
        llllllllllllllllllIIllIIlIlIIllI.inLiquids = llllllllllllllllllIIllIIlIlIIllI.sgDefault.add(new BoolSetting.Builder().name("in-liquids").description("Uses speed when in lava or water.").defaultValue(false).build());
        llllllllllllllllllIIllIIlIlIIllI.whenSneaking = llllllllllllllllllIIllIIlIlIIllI.sgDefault.add(new BoolSetting.Builder().name("when-sneaking").description("Uses speed when sneaking.").defaultValue(false).build());
        llllllllllllllllllIIllIIlIlIIllI.speed = llllllllllllllllllIIllIIlIlIIllI.sgVanilla.add(new DoubleSetting.Builder().name("speed").description("How fast you want to go in blocks per second.").defaultValue(5.6).min(0.0).sliderMax(50.0).build());
        llllllllllllllllllIIllIIlIlIIllI.onlyOnGround = llllllllllllllllllIIllIIlIlIIllI.sgVanilla.add(new BoolSetting.Builder().name("only-on-ground").description("Uses speed only when standing on a block.").defaultValue(false).build());
        llllllllllllllllllIIllIIlIlIIllI.applySpeedPotions = llllllllllllllllllIIllIIlIlIIllI.sgVanilla.add(new BoolSetting.Builder().name("apply-speed-potions").description("Applies the speed effect via potions.").defaultValue(true).build());
        llllllllllllllllllIIllIIlIlIIllI.jump = llllllllllllllllllIIllIIlIlIIllI.sgVanilla.add(new BoolSetting.Builder().name("jump").description("Automatically jumps.").defaultValue(false).build());
        llllllllllllllllllIIllIIlIlIIllI.jumpMode = llllllllllllllllllIIllIIlIlIIllI.sgVanilla.add(new EnumSetting.Builder().name("mode").description("The method of jumping.").defaultValue(AutoJump.Mode.Jump).build());
        llllllllllllllllllIIllIIlIlIIllI.hopHeight = llllllllllllllllllIIllIIlIlIIllI.sgVanilla.add(new DoubleSetting.Builder().name("hop-height").description("The distance that lowhop moves you.").defaultValue(0.25).min(0.0).sliderMax(2.0).build());
        llllllllllllllllllIIllIIlIlIIllI.jumpIf = llllllllllllllllllIIllIIlIlIIllI.sgVanilla.add(new EnumSetting.Builder().name("jump-when").description("Jumps when you are doing said action.").defaultValue(AutoJump.JumpWhen.Walking).build());
        llllllllllllllllllIIllIIlIlIIllI.ncpSpeed = llllllllllllllllllIIllIIlIlIIllI.sgNCP.add(new DoubleSetting.Builder().name("speed").description("How fast you go.").defaultValue(1.6).min(0.0).sliderMax(3.0).build());
        llllllllllllllllllIIllIIlIlIIllI.ncpSpeedLimit = llllllllllllllllllIIllIIlIlIIllI.sgNCP.add(new BoolSetting.Builder().name("speed-limit").description("Limits your speed on servers with very strict anticheats.").defaultValue(false).build());
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent llllllllllllllllllIIllIIlIIllIll) {
        Speed llllllllllllllllllIIllIIlIIllIlI;
        if (llllllllllllllllllIIllIIlIIllIll.type != MovementType.SELF || llllllllllllllllllIIllIIlIIllIlI.mc.player.isFallFlying() || llllllllllllllllllIIllIIlIIllIlI.mc.player.isClimbing() || llllllllllllllllllIIllIIlIIllIlI.mc.player.getVehicle() != null) {
            return;
        }
        if (!llllllllllllllllllIIllIIlIIllIlI.whenSneaking.get().booleanValue() && llllllllllllllllllIIllIIlIIllIlI.mc.player.isSneaking()) {
            return;
        }
        if (llllllllllllllllllIIllIIlIIllIlI.onlyOnGround.get().booleanValue() && !llllllllllllllllllIIllIIlIIllIlI.mc.player.isOnGround()) {
            return;
        }
        if (!llllllllllllllllllIIllIIlIIllIlI.inLiquids.get().booleanValue() && (llllllllllllllllllIIllIIlIIllIlI.mc.player.isTouchingWater() || llllllllllllllllllIIllIIlIIllIlI.mc.player.isInLava())) {
            return;
        }
        Modules.get().get(Timer.class).setOverride(PlayerUtils.isMoving() ? llllllllllllllllllIIllIIlIIllIlI.timer.get() : 1.0);
        llllllllllllllllllIIllIIlIIllIlI.currentMode.onMove(llllllllllllllllllIIllIIlIIllIll);
    }

    @EventHandler
    private void onPacketRecieve(PacketEvent.Receive llllllllllllllllllIIllIIlIIlIIIl) {
        if (llllllllllllllllllIIllIIlIIlIIIl.packet instanceof PlayerPositionLookS2CPacket) {
            Speed llllllllllllllllllIIllIIlIIlIIII;
            llllllllllllllllllIIllIIlIIlIIII.currentMode.onRubberband();
        }
    }
}

