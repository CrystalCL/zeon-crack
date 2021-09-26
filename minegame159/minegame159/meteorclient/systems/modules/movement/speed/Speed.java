/*
 * Decompiled with CFR 0.151.
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
    public final Setting<Double> timer;
    public final Setting<Boolean> jump;
    public final Setting<Double> speed;
    public final Setting<Boolean> applySpeedPotions;
    public final Setting<SpeedModes> speedMode;
    public final Setting<Double> ncpSpeed;
    public final Setting<Boolean> whenSneaking;
    private SpeedMode currentMode;
    private final SettingGroup sgVanilla;
    public final Setting<Double> hopHeight;
    private final SettingGroup sgDefault;
    public final Setting<Boolean> onlyOnGround;
    public final Setting<AutoJump.Mode> jumpMode;
    public final Setting<Boolean> ncpSpeedLimit;
    public final Setting<AutoJump.JumpWhen> jumpIf;
    public final Setting<Boolean> inLiquids;
    private final SettingGroup sgNCP;

    @EventHandler
    private void onPreTick(TickEvent.Pre pre) {
        if (this.mc.player.isFallFlying() || this.mc.player.isClimbing() || this.mc.player.getVehicle() != null || this.whenSneaking.get() == false && this.mc.player.isSneaking() || this.onlyOnGround.get() != false && !this.mc.player.isOnGround() || !this.inLiquids.get().booleanValue() && (this.mc.player.isTouchingWater() || this.mc.player.isInLava())) {
            return;
        }
        this.currentMode.onTick();
    }

    @Override
    public String getInfoString() {
        return this.currentMode.getHudString();
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(1.0);
        this.currentMode.onDeactivate();
    }

    private void lambda$new$0(Setting setting) {
        this.onSpeedModeChanged((SpeedModes)((Object)setting.get()));
    }

    public Speed() {
        super(Categories.Movement, "speed", "Modifies your movement speed when moving on the ground.");
        this.sgDefault = this.settings.getDefaultGroup();
        this.sgVanilla = this.settings.createGroup("Vanilla");
        this.sgNCP = this.settings.createGroup("NCP");
        this.speedMode = this.sgDefault.add(new EnumSetting.Builder().name("mode").description("The method of applying speed.").defaultValue(SpeedModes.Vanilla).onModuleActivated(this::lambda$new$0).onChanged(this::onSpeedModeChanged).build());
        this.timer = this.sgDefault.add(new DoubleSetting.Builder().name("timer").description("Timer override.").defaultValue(1.0).min(0.01).sliderMin(0.01).sliderMax(10.0).build());
        this.inLiquids = this.sgDefault.add(new BoolSetting.Builder().name("in-liquids").description("Uses speed when in lava or water.").defaultValue(false).build());
        this.whenSneaking = this.sgDefault.add(new BoolSetting.Builder().name("when-sneaking").description("Uses speed when sneaking.").defaultValue(false).build());
        this.speed = this.sgVanilla.add(new DoubleSetting.Builder().name("speed").description("How fast you want to go in blocks per second.").defaultValue(5.6).min(0.0).sliderMax(50.0).build());
        this.onlyOnGround = this.sgVanilla.add(new BoolSetting.Builder().name("only-on-ground").description("Uses speed only when standing on a block.").defaultValue(false).build());
        this.applySpeedPotions = this.sgVanilla.add(new BoolSetting.Builder().name("apply-speed-potions").description("Applies the speed effect via potions.").defaultValue(true).build());
        this.jump = this.sgVanilla.add(new BoolSetting.Builder().name("jump").description("Automatically jumps.").defaultValue(false).build());
        this.jumpMode = this.sgVanilla.add(new EnumSetting.Builder().name("mode").description("The method of jumping.").defaultValue(AutoJump.Mode.Jump).build());
        this.hopHeight = this.sgVanilla.add(new DoubleSetting.Builder().name("hop-height").description("The distance that lowhop moves you.").defaultValue(0.25).min(0.0).sliderMax(2.0).build());
        this.jumpIf = this.sgVanilla.add(new EnumSetting.Builder().name("jump-when").description("Jumps when you are doing said action.").defaultValue(AutoJump.JumpWhen.Walking).build());
        this.ncpSpeed = this.sgNCP.add(new DoubleSetting.Builder().name("speed").description("How fast you go.").defaultValue(1.6).min(0.0).sliderMax(3.0).build());
        this.ncpSpeedLimit = this.sgNCP.add(new BoolSetting.Builder().name("speed-limit").description("Limits your speed on servers with very strict anticheats.").defaultValue(false).build());
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent playerMoveEvent) {
        if (playerMoveEvent.type != MovementType.SELF || this.mc.player.isFallFlying() || this.mc.player.isClimbing() || this.mc.player.getVehicle() != null) {
            return;
        }
        if (!this.whenSneaking.get().booleanValue() && this.mc.player.isSneaking()) {
            return;
        }
        if (this.onlyOnGround.get().booleanValue() && !this.mc.player.isOnGround()) {
            return;
        }
        if (!this.inLiquids.get().booleanValue() && (this.mc.player.isTouchingWater() || this.mc.player.isInLava())) {
            return;
        }
        Modules.get().get(Timer.class).setOverride(PlayerUtils.isMoving() ? this.timer.get() : 1.0);
        this.currentMode.onMove(playerMoveEvent);
    }

    private void onSpeedModeChanged(SpeedModes speedModes) {
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$movement$speed$SpeedModes[speedModes.ordinal()]) {
            case 1: {
                this.currentMode = new Vanilla();
                break;
            }
            case 2: {
                this.currentMode = new NCP();
            }
        }
    }

    @EventHandler
    private void onPacketRecieve(PacketEvent.Receive receive) {
        if (receive.packet instanceof PlayerPositionLookS2CPacket) {
            this.currentMode.onRubberband();
        }
    }

    @Override
    public void onActivate() {
        this.currentMode.onActivate();
    }
}

