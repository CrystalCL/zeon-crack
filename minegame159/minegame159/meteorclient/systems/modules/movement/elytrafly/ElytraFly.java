/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement.elytrafly;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.player.PlayerMoveEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixininterface.IVec3d;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.elytrafly.ElytraFlightMode;
import minegame159.meteorclient.systems.modules.movement.elytrafly.ElytraFlightModes;
import minegame159.meteorclient.systems.modules.movement.elytrafly.modes.Packet;
import minegame159.meteorclient.systems.modules.movement.elytrafly.modes.Vanilla;
import minegame159.meteorclient.systems.modules.player.ChestSwap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;

public class ElytraFly
extends Module {
    public final Setting<Double> fallMultiplier;
    private final StaticInstaDropListener staticInstadropListener;
    private final SettingGroup sgAutopilot;
    public final Setting<Double> horizontalSpeed;
    public final Setting<ChestSwapMode> chestSwap;
    public final Setting<Boolean> replace;
    public final Setting<Boolean> dontGoIntoUnloadedChunks;
    public final Setting<Boolean> stopInWater;
    public final Setting<Boolean> useFireworks;
    public final Setting<Integer> crashLookAhead;
    private final SettingGroup sgDefault;
    private final Setting<Boolean> instaDrop;
    public final Setting<Double> autoPilotMinimumHeight;
    private final Setting<Boolean> enableAutopilot;
    public final Setting<Boolean> noCrash;
    public final Setting<Double> autoPilotFireworkDelay;
    public final Setting<Double> verticalSpeed;
    private ElytraFlightMode currentMode;
    public final Setting<Boolean> autoTakeOff;
    public final Setting<ElytraFlightModes> flightMode;
    public final Setting<Boolean> moveForward;
    public final Setting<Integer> replaceDurability;
    public final Setting<Boolean> autoPilotFireworkGhosthand;
    private final StaticGroundListener staticGroundListener;

    protected void disableInstaDropListener() {
        MeteorClient.EVENT_BUS.unsubscribe(this.staticInstadropListener);
    }

    @Override
    public void onDeactivate() {
        if (this.moveForward.get().booleanValue()) {
            this.mc.options.keyForward.setPressed(false);
        }
        if (this.chestSwap.get() == ChestSwapMode.Always && this.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
            Modules.get().get(ChestSwap.class).swap();
        } else if (this.chestSwap.get() == ChestSwapMode.WaitForGround) {
            this.enableGroundListener();
        }
        if (this.mc.player.isFallFlying() && this.instaDrop.get().booleanValue()) {
            this.enableInstaDropListener();
        }
        this.currentMode.onDeactivate();
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent playerMoveEvent) {
        if (!(this.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ElytraItem)) {
            return;
        }
        this.currentMode.autoTakeoff();
        if (this.mc.player.isFallFlying()) {
            this.currentMode.velX = 0.0;
            this.currentMode.velY = playerMoveEvent.movement.y;
            this.currentMode.velZ = 0.0;
            this.currentMode.forward = Vec3d.fromPolar((float)0.0f, (float)this.mc.player.yaw).multiply(0.1);
            this.currentMode.right = Vec3d.fromPolar((float)0.0f, (float)(this.mc.player.yaw + 90.0f)).multiply(0.1);
            if (this.mc.player.isTouchingWater() && this.stopInWater.get().booleanValue()) {
                this.mc.getNetworkHandler().sendPacket((Packet)new ClientCommandC2SPacket((Entity)this.mc.player, ClientCommandC2SPacket.class_2849.START_FALL_FLYING));
                return;
            }
            this.currentMode.handleFallMultiplier();
            if (this.enableAutopilot.get().booleanValue()) {
                this.currentMode.handleAutopilot();
            }
            this.currentMode.handleHorizontalSpeed();
            this.currentMode.handleVerticalSpeed();
            int n = (int)((this.mc.player.getX() + this.currentMode.velX) / 16.0);
            int n2 = (int)((this.mc.player.getZ() + this.currentMode.velZ) / 16.0);
            if (this.dontGoIntoUnloadedChunks.get().booleanValue()) {
                if (this.mc.world.getChunkManager().isChunkLoaded(n, n2)) {
                    ((IVec3d)playerMoveEvent.movement).set(this.currentMode.velX, this.currentMode.velY, this.currentMode.velZ);
                } else {
                    ((IVec3d)playerMoveEvent.movement).set(0.0, this.currentMode.velY, 0.0);
                }
            } else {
                ((IVec3d)playerMoveEvent.movement).set(this.currentMode.velX, this.currentMode.velY, this.currentMode.velZ);
            }
            this.currentMode.onPlayerMove();
        } else if (this.currentMode.lastForwardPressed) {
            this.mc.options.keyForward.setPressed(false);
            this.currentMode.lastForwardPressed = false;
        }
        if (this.noCrash.get().booleanValue() && this.mc.player.isFallFlying()) {
            Vec3d Vec3d2 = this.mc.player.getPos().add(this.mc.player.getVelocity().normalize().multiply((double)this.crashLookAhead.get().intValue()));
            RaycastContext RaycastContext2 = new RaycastContext(this.mc.player.getPos(), new Vec3d(Vec3d2.getX(), this.mc.player.getY(), Vec3d2.getZ()), RaycastContext.class_3960.OUTLINE, RaycastContext.class_242.NONE, (Entity)this.mc.player);
            BlockHitResult BlockHitResult2 = this.mc.world.raycast(RaycastContext2);
            if (BlockHitResult2 != null && BlockHitResult2.getType() == HitResult.class_240.BLOCK) {
                ((IVec3d)playerMoveEvent.movement).set(0.0, this.currentMode.velY, 0.0);
            }
        }
    }

    static MinecraftClient access$200(ElytraFly elytraFly) {
        return elytraFly.mc;
    }

    static MinecraftClient access$500(ElytraFly elytraFly) {
        return elytraFly.mc;
    }

    private void onModeChanged(ElytraFlightModes elytraFlightModes) {
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$movement$elytrafly$ElytraFlightModes[elytraFlightModes.ordinal()]) {
            case 1: {
                this.currentMode = new Vanilla();
                break;
            }
            case 2: {
                this.currentMode = new Packet();
            }
        }
    }

    @Override
    public String getInfoString() {
        return this.currentMode.getHudString();
    }

    static MinecraftClient access$100(ElytraFly elytraFly) {
        return elytraFly.mc;
    }

    protected void enableGroundListener() {
        MeteorClient.EVENT_BUS.subscribe(this.staticGroundListener);
    }

    @Override
    public void onActivate() {
        this.currentMode.onActivate();
        if ((this.chestSwap.get() == ChestSwapMode.Always || this.chestSwap.get() == ChestSwapMode.WaitForGround) && this.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() != Items.ELYTRA) {
            Modules.get().get(ChestSwap.class).swap();
        }
    }

    protected void disableGroundListener() {
        MeteorClient.EVENT_BUS.unsubscribe(this.staticGroundListener);
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send send) {
        this.currentMode.onPacketSend(send);
    }

    static MinecraftClient access$000(ElytraFly elytraFly) {
        return elytraFly.mc;
    }

    private void lambda$new$0(Setting setting) {
        this.onModeChanged((ElytraFlightModes)((Object)setting.get()));
    }

    public ElytraFly() {
        super(Categories.Movement, "elytra-fly", "Gives you more control over your elytra.");
        this.sgDefault = this.settings.getDefaultGroup();
        this.sgAutopilot = this.settings.createGroup("Autopilot");
        this.flightMode = this.sgDefault.add(new EnumSetting.Builder().name("mode").description("The mode of flying.").defaultValue(ElytraFlightModes.Vanilla).onModuleActivated(this::lambda$new$0).onChanged(this::onModeChanged).build());
        this.autoTakeOff = this.sgDefault.add(new BoolSetting.Builder().name("auto-take-off").description("Automatically takes off when you hold jump without needing to double jump.").defaultValue(false).build());
        this.replace = this.sgDefault.add(new BoolSetting.Builder().name("elytra-replace").description("Replaces broken elytra with a new elytra.").defaultValue(false).build());
        this.replaceDurability = this.sgDefault.add(new IntSetting.Builder().name("replace-durability").description("The durability threshold your elytra will be replaced at.").defaultValue(2).min(1).max(Items.ELYTRA.getMaxDamage() - 1).sliderMax(20).build());
        this.fallMultiplier = this.sgDefault.add(new DoubleSetting.Builder().name("fall-multiplier").description("Controls how fast will you go down naturally.").defaultValue(0.01).min(0.0).build());
        this.horizontalSpeed = this.sgDefault.add(new DoubleSetting.Builder().name("horizontal-speed").description("How fast you go forward and backward.").defaultValue(1.0).min(0.0).build());
        this.verticalSpeed = this.sgDefault.add(new DoubleSetting.Builder().name("vertical-speed").description("How fast you go up and down.").defaultValue(1.0).min(0.0).build());
        this.stopInWater = this.sgDefault.add(new BoolSetting.Builder().name("stop-in-water").description("Stops flying in water.").defaultValue(true).build());
        this.dontGoIntoUnloadedChunks = this.sgDefault.add(new BoolSetting.Builder().name("no-unloaded-chunks").description("Stops you from going into unloaded chunks.").defaultValue(true).build());
        this.noCrash = this.sgDefault.add(new BoolSetting.Builder().name("no-crash").description("Stops you from going into walls.").defaultValue(true).build());
        this.crashLookAhead = this.sgDefault.add(new IntSetting.Builder().name("crash-look-ahead").description("Distance to look ahead when flying.").defaultValue(5).min(1).max(15).sliderMin(1).sliderMax(10).build());
        this.chestSwap = this.sgDefault.add(new EnumSetting.Builder().name("chest-swap").description("Enables ChestSwap when toggling this module.").defaultValue(ChestSwapMode.Never).build());
        this.instaDrop = this.sgDefault.add(new BoolSetting.Builder().name("insta-drop").description("Makes you drop out of flight instantly.").defaultValue(false).build());
        this.enableAutopilot = this.sgAutopilot.add(new BoolSetting.Builder().name("enable-autopilot").description("Use autopilot.").defaultValue(false).build());
        this.useFireworks = this.sgAutopilot.add(new BoolSetting.Builder().name("use-fireworks").description("Uses firework rockets every second of your choice.").defaultValue(false).build());
        this.autoPilotFireworkDelay = this.sgAutopilot.add(new DoubleSetting.Builder().name("firework-delay").description("The delay in seconds in between shooting fireworks for Firework mode.").min(1.0).defaultValue(10.0).sliderMax(20.0).build());
        this.autoPilotFireworkGhosthand = this.sgAutopilot.add(new BoolSetting.Builder().name("firework-ghost-hand").description("Doesn't switch to your firework slot client-side.").defaultValue(false).build());
        this.moveForward = this.sgAutopilot.add(new BoolSetting.Builder().name("move-forward").description("Moves forward while elytra flying.").defaultValue(false).build());
        this.autoPilotMinimumHeight = this.sgAutopilot.add(new DoubleSetting.Builder().name("minimum-height").description("The minimum height for moving forward.").defaultValue(120.0).min(0.0).sliderMax(260.0).build());
        this.staticGroundListener = new StaticGroundListener(this, null);
        this.staticInstadropListener = new StaticInstaDropListener(this, null);
    }

    static MinecraftClient access$600(ElytraFly elytraFly) {
        return elytraFly.mc;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        this.currentMode.onTick();
    }

    protected void enableInstaDropListener() {
        MeteorClient.EVENT_BUS.subscribe(this.staticInstadropListener);
    }

    static MinecraftClient access$400(ElytraFly elytraFly) {
        return elytraFly.mc;
    }

    private class StaticGroundListener {
        final ElytraFly this$0;

        private StaticGroundListener(ElytraFly elytraFly) {
            this.this$0 = elytraFly;
        }

        @EventHandler
        private void chestSwapGroundListener(PlayerMoveEvent playerMoveEvent) {
            if (ElytraFly.access$000((ElytraFly)this.this$0).player != null && ElytraFly.access$100((ElytraFly)this.this$0).player.isOnGround() && ElytraFly.access$200((ElytraFly)this.this$0).player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
                Modules.get().get(ChestSwap.class).swap();
                this.this$0.disableGroundListener();
            }
        }

        StaticGroundListener(ElytraFly elytraFly, 1 var2_2) {
            this(elytraFly);
        }
    }

    private class StaticInstaDropListener {
        final ElytraFly this$0;

        StaticInstaDropListener(ElytraFly elytraFly, 1 var2_2) {
            this(elytraFly);
        }

        @EventHandler
        private void onInstadropTick(TickEvent.Post post) {
            if (ElytraFly.access$400((ElytraFly)this.this$0).player.isFallFlying()) {
                ElytraFly.access$500((ElytraFly)this.this$0).player.setVelocity(0.0, 0.0, 0.0);
                ElytraFly.access$600((ElytraFly)this.this$0).player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket(true));
            } else {
                this.this$0.disableInstaDropListener();
            }
        }

        private StaticInstaDropListener(ElytraFly elytraFly) {
            this.this$0 = elytraFly;
        }
    }

    public static final class ChestSwapMode
    extends Enum<ChestSwapMode> {
        private static final ChestSwapMode[] $VALUES;
        public static final /* enum */ ChestSwapMode Always;
        public static final /* enum */ ChestSwapMode WaitForGround;
        public static final /* enum */ ChestSwapMode Never;

        private static ChestSwapMode[] $values() {
            return new ChestSwapMode[]{Always, Never, WaitForGround};
        }

        public static ChestSwapMode[] values() {
            return (ChestSwapMode[])$VALUES.clone();
        }

        static {
            Always = new ChestSwapMode();
            Never = new ChestSwapMode();
            WaitForGround = new ChestSwapMode();
            $VALUES = ChestSwapMode.$values();
        }

        public static ChestSwapMode valueOf(String string) {
            return Enum.valueOf(ChestSwapMode.class, string);
        }
    }
}

