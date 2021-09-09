/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EquipmentSlot
 *  net.minecraft.item.ElytraItem
 *  net.minecraft.item.Items
 *  net.minecraft.util.hit.HitResult.Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket$class_2849
 *  net.minecraft.world.RaycastContext
 *  net.minecraft.world.RaycastContext$class_242
 *  net.minecraft.world.RaycastContext$class_3960
 *  net.minecraft.util.hit.BlockHitResult
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
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;

public class ElytraFly
extends Module {
    public final /* synthetic */ Setting<ElytraFlightModes> flightMode;
    private /* synthetic */ ElytraFlightMode currentMode;
    private final /* synthetic */ StaticInstaDropListener staticInstadropListener;
    private final /* synthetic */ Setting<Boolean> enableAutopilot;
    public final /* synthetic */ Setting<Boolean> noCrash;
    private final /* synthetic */ SettingGroup sgAutopilot;
    private final /* synthetic */ Setting<Boolean> instaDrop;
    public final /* synthetic */ Setting<Boolean> dontGoIntoUnloadedChunks;
    public final /* synthetic */ Setting<Double> autoPilotMinimumHeight;
    public final /* synthetic */ Setting<Integer> crashLookAhead;
    public final /* synthetic */ Setting<Double> fallMultiplier;
    public final /* synthetic */ Setting<Boolean> stopInWater;
    public final /* synthetic */ Setting<Boolean> autoPilotFireworkGhosthand;
    public final /* synthetic */ Setting<Integer> replaceDurability;
    public final /* synthetic */ Setting<Boolean> autoTakeOff;
    private final /* synthetic */ StaticGroundListener staticGroundListener;
    public final /* synthetic */ Setting<Double> horizontalSpeed;
    private final /* synthetic */ SettingGroup sgDefault;
    public final /* synthetic */ Setting<Double> autoPilotFireworkDelay;
    public final /* synthetic */ Setting<Boolean> moveForward;
    public final /* synthetic */ Setting<ChestSwapMode> chestSwap;
    public final /* synthetic */ Setting<Boolean> replace;
    public final /* synthetic */ Setting<Double> verticalSpeed;
    public final /* synthetic */ Setting<Boolean> useFireworks;

    @EventHandler
    private void onTick(TickEvent.Post lllIIIIllIllIll) {
        ElytraFly lllIIIIllIllIlI;
        lllIIIIllIllIlI.currentMode.onTick();
    }

    private void onModeChanged(ElytraFlightModes lllIIIIllIIlllI) {
        switch (lllIIIIllIIlllI) {
            case Vanilla: {
                lllIIIIllIIllll.currentMode = new Vanilla();
                break;
            }
            case Packet: {
                lllIIIIllIIllll.currentMode = new Packet();
            }
        }
    }

    public ElytraFly() {
        super(Categories.Movement, "elytra-fly", "Gives you more control over your elytra.");
        ElytraFly lllIIIIllllIllI;
        lllIIIIllllIllI.sgDefault = lllIIIIllllIllI.settings.getDefaultGroup();
        lllIIIIllllIllI.sgAutopilot = lllIIIIllllIllI.settings.createGroup("Autopilot");
        lllIIIIllllIllI.flightMode = lllIIIIllllIllI.sgDefault.add(new EnumSetting.Builder().name("mode").description("The mode of flying.").defaultValue(ElytraFlightModes.Vanilla).onModuleActivated(lllIIIIlIlllIIl -> {
            ElytraFly lllIIIIlIllllII;
            lllIIIIlIllllII.onModeChanged((ElytraFlightModes)((Object)((Object)lllIIIIlIlllIIl.get())));
        }).onChanged(lllIIIIllllIllI::onModeChanged).build());
        lllIIIIllllIllI.autoTakeOff = lllIIIIllllIllI.sgDefault.add(new BoolSetting.Builder().name("auto-take-off").description("Automatically takes off when you hold jump without needing to double jump.").defaultValue(false).build());
        lllIIIIllllIllI.replace = lllIIIIllllIllI.sgDefault.add(new BoolSetting.Builder().name("elytra-replace").description("Replaces broken elytra with a new elytra.").defaultValue(false).build());
        lllIIIIllllIllI.replaceDurability = lllIIIIllllIllI.sgDefault.add(new IntSetting.Builder().name("replace-durability").description("The durability threshold your elytra will be replaced at.").defaultValue(2).min(1).max(Items.ELYTRA.getMaxDamage() - 1).sliderMax(20).build());
        lllIIIIllllIllI.fallMultiplier = lllIIIIllllIllI.sgDefault.add(new DoubleSetting.Builder().name("fall-multiplier").description("Controls how fast will you go down naturally.").defaultValue(0.01).min(0.0).build());
        lllIIIIllllIllI.horizontalSpeed = lllIIIIllllIllI.sgDefault.add(new DoubleSetting.Builder().name("horizontal-speed").description("How fast you go forward and backward.").defaultValue(1.0).min(0.0).build());
        lllIIIIllllIllI.verticalSpeed = lllIIIIllllIllI.sgDefault.add(new DoubleSetting.Builder().name("vertical-speed").description("How fast you go up and down.").defaultValue(1.0).min(0.0).build());
        lllIIIIllllIllI.stopInWater = lllIIIIllllIllI.sgDefault.add(new BoolSetting.Builder().name("stop-in-water").description("Stops flying in water.").defaultValue(true).build());
        lllIIIIllllIllI.dontGoIntoUnloadedChunks = lllIIIIllllIllI.sgDefault.add(new BoolSetting.Builder().name("no-unloaded-chunks").description("Stops you from going into unloaded chunks.").defaultValue(true).build());
        lllIIIIllllIllI.noCrash = lllIIIIllllIllI.sgDefault.add(new BoolSetting.Builder().name("no-crash").description("Stops you from going into walls.").defaultValue(true).build());
        lllIIIIllllIllI.crashLookAhead = lllIIIIllllIllI.sgDefault.add(new IntSetting.Builder().name("crash-look-ahead").description("Distance to look ahead when flying.").defaultValue(5).min(1).max(15).sliderMin(1).sliderMax(10).build());
        lllIIIIllllIllI.chestSwap = lllIIIIllllIllI.sgDefault.add(new EnumSetting.Builder().name("chest-swap").description("Enables ChestSwap when toggling this module.").defaultValue(ChestSwapMode.Never).build());
        lllIIIIllllIllI.instaDrop = lllIIIIllllIllI.sgDefault.add(new BoolSetting.Builder().name("insta-drop").description("Makes you drop out of flight instantly.").defaultValue(false).build());
        lllIIIIllllIllI.enableAutopilot = lllIIIIllllIllI.sgAutopilot.add(new BoolSetting.Builder().name("enable-autopilot").description("Use autopilot.").defaultValue(false).build());
        lllIIIIllllIllI.useFireworks = lllIIIIllllIllI.sgAutopilot.add(new BoolSetting.Builder().name("use-fireworks").description("Uses firework rockets every second of your choice.").defaultValue(false).build());
        lllIIIIllllIllI.autoPilotFireworkDelay = lllIIIIllllIllI.sgAutopilot.add(new DoubleSetting.Builder().name("firework-delay").description("The delay in seconds in between shooting fireworks for Firework mode.").min(1.0).defaultValue(10.0).sliderMax(20.0).build());
        lllIIIIllllIllI.autoPilotFireworkGhosthand = lllIIIIllllIllI.sgAutopilot.add(new BoolSetting.Builder().name("firework-ghost-hand").description("Doesn't switch to your firework slot client-side.").defaultValue(false).build());
        lllIIIIllllIllI.moveForward = lllIIIIllllIllI.sgAutopilot.add(new BoolSetting.Builder().name("move-forward").description("Moves forward while elytra flying.").defaultValue(false).build());
        lllIIIIllllIllI.autoPilotMinimumHeight = lllIIIIllllIllI.sgAutopilot.add(new DoubleSetting.Builder().name("minimum-height").description("The minimum height for moving forward.").defaultValue(120.0).min(0.0).sliderMax(260.0).build());
        lllIIIIllllIllI.staticGroundListener = lllIIIIllllIllI.new StaticGroundListener();
        lllIIIIllllIllI.staticInstadropListener = lllIIIIllllIllI.new StaticInstaDropListener();
    }

    protected void enableGroundListener() {
        ElytraFly lllIIIIllIIllII;
        MeteorClient.EVENT_BUS.subscribe(lllIIIIllIIllII.staticGroundListener);
    }

    @Override
    public void onDeactivate() {
        ElytraFly lllIIIIllllIIII;
        if (lllIIIIllllIIII.moveForward.get().booleanValue()) {
            lllIIIIllllIIII.mc.options.keyForward.setPressed(false);
        }
        if (lllIIIIllllIIII.chestSwap.get() == ChestSwapMode.Always && lllIIIIllllIIII.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
            Modules.get().get(ChestSwap.class).swap();
        } else if (lllIIIIllllIIII.chestSwap.get() == ChestSwapMode.WaitForGround) {
            lllIIIIllllIIII.enableGroundListener();
        }
        if (lllIIIIllllIIII.mc.player.isFallFlying() && lllIIIIllllIIII.instaDrop.get().booleanValue()) {
            lllIIIIllllIIII.enableInstaDropListener();
        }
        lllIIIIllllIIII.currentMode.onDeactivate();
    }

    @Override
    public String getInfoString() {
        ElytraFly lllIIIIllIIIIII;
        return lllIIIIllIIIIII.currentMode.getHudString();
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent lllIIIIlllIIIll) {
        ElytraFly lllIIIIlllIIlII;
        if (!(lllIIIIlllIIlII.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ElytraItem)) {
            return;
        }
        lllIIIIlllIIlII.currentMode.autoTakeoff();
        if (lllIIIIlllIIlII.mc.player.isFallFlying()) {
            lllIIIIlllIIlII.currentMode.velX = 0.0;
            lllIIIIlllIIlII.currentMode.velY = lllIIIIlllIIIll.movement.y;
            lllIIIIlllIIlII.currentMode.velZ = 0.0;
            lllIIIIlllIIlII.currentMode.forward = Vec3d.fromPolar((float)0.0f, (float)lllIIIIlllIIlII.mc.player.yaw).multiply(0.1);
            lllIIIIlllIIlII.currentMode.right = Vec3d.fromPolar((float)0.0f, (float)(lllIIIIlllIIlII.mc.player.yaw + 90.0f)).multiply(0.1);
            if (lllIIIIlllIIlII.mc.player.isTouchingWater() && lllIIIIlllIIlII.stopInWater.get().booleanValue()) {
                lllIIIIlllIIlII.mc.getNetworkHandler().sendPacket((Packet)new ClientCommandC2SPacket((Entity)lllIIIIlllIIlII.mc.player, ClientCommandC2SPacket.class_2849.START_FALL_FLYING));
                return;
            }
            lllIIIIlllIIlII.currentMode.handleFallMultiplier();
            if (lllIIIIlllIIlII.enableAutopilot.get().booleanValue()) {
                lllIIIIlllIIlII.currentMode.handleAutopilot();
            }
            lllIIIIlllIIlII.currentMode.handleHorizontalSpeed();
            lllIIIIlllIIlII.currentMode.handleVerticalSpeed();
            int lllIIIIlllIlIIl = (int)((lllIIIIlllIIlII.mc.player.getX() + lllIIIIlllIIlII.currentMode.velX) / 16.0);
            int lllIIIIlllIlIII = (int)((lllIIIIlllIIlII.mc.player.getZ() + lllIIIIlllIIlII.currentMode.velZ) / 16.0);
            if (lllIIIIlllIIlII.dontGoIntoUnloadedChunks.get().booleanValue()) {
                if (lllIIIIlllIIlII.mc.world.getChunkManager().isChunkLoaded(lllIIIIlllIlIIl, lllIIIIlllIlIII)) {
                    ((IVec3d)lllIIIIlllIIIll.movement).set(lllIIIIlllIIlII.currentMode.velX, lllIIIIlllIIlII.currentMode.velY, lllIIIIlllIIlII.currentMode.velZ);
                } else {
                    ((IVec3d)lllIIIIlllIIIll.movement).set(0.0, lllIIIIlllIIlII.currentMode.velY, 0.0);
                }
            } else {
                ((IVec3d)lllIIIIlllIIIll.movement).set(lllIIIIlllIIlII.currentMode.velX, lllIIIIlllIIlII.currentMode.velY, lllIIIIlllIIlII.currentMode.velZ);
            }
            lllIIIIlllIIlII.currentMode.onPlayerMove();
        } else if (lllIIIIlllIIlII.currentMode.lastForwardPressed) {
            lllIIIIlllIIlII.mc.options.keyForward.setPressed(false);
            lllIIIIlllIIlII.currentMode.lastForwardPressed = false;
        }
        if (lllIIIIlllIIlII.noCrash.get().booleanValue() && lllIIIIlllIIlII.mc.player.isFallFlying()) {
            Vec3d lllIIIIlllIIlll = lllIIIIlllIIlII.mc.player.getPos().add(lllIIIIlllIIlII.mc.player.getVelocity().normalize().multiply((double)lllIIIIlllIIlII.crashLookAhead.get().intValue()));
            RaycastContext lllIIIIlllIIllI = new RaycastContext(lllIIIIlllIIlII.mc.player.getPos(), new Vec3d(lllIIIIlllIIlll.getX(), lllIIIIlllIIlII.mc.player.getY(), lllIIIIlllIIlll.getZ()), RaycastContext.class_3960.OUTLINE, RaycastContext.class_242.NONE, (Entity)lllIIIIlllIIlII.mc.player);
            BlockHitResult lllIIIIlllIIlIl = lllIIIIlllIIlII.mc.world.raycast(lllIIIIlllIIllI);
            if (lllIIIIlllIIlIl != null && lllIIIIlllIIlIl.getType() == Type.BLOCK) {
                ((IVec3d)lllIIIIlllIIIll.movement).set(0.0, lllIIIIlllIIlII.currentMode.velY, 0.0);
            }
        }
    }

    protected void disableInstaDropListener() {
        ElytraFly lllIIIIllIIIIll;
        MeteorClient.EVENT_BUS.unsubscribe(lllIIIIllIIIIll.staticInstadropListener);
    }

    protected void disableGroundListener() {
        ElytraFly lllIIIIllIIlIIl;
        MeteorClient.EVENT_BUS.unsubscribe(lllIIIIllIIlIIl.staticGroundListener);
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send lllIIIIllIlIlII) {
        ElytraFly lllIIIIllIlIlll;
        lllIIIIllIlIlll.currentMode.onPacketSend(lllIIIIllIlIlII);
    }

    protected void enableInstaDropListener() {
        ElytraFly lllIIIIllIIIlIl;
        MeteorClient.EVENT_BUS.subscribe(lllIIIIllIIIlIl.staticInstadropListener);
    }

    @Override
    public void onActivate() {
        ElytraFly lllIIIIllllIIll;
        lllIIIIllllIIll.currentMode.onActivate();
        if ((lllIIIIllllIIll.chestSwap.get() == ChestSwapMode.Always || lllIIIIllllIIll.chestSwap.get() == ChestSwapMode.WaitForGround) && lllIIIIllllIIll.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() != Items.ELYTRA) {
            Modules.get().get(ChestSwap.class).swap();
        }
    }

    private class StaticInstaDropListener {
        private StaticInstaDropListener() {
            StaticInstaDropListener lIlIlllIIIlllll;
        }

        @EventHandler
        private void onInstadropTick(TickEvent.Post lIlIlllIIIllIll) {
            StaticInstaDropListener lIlIlllIIIllIlI;
            if (((ElytraFly)lIlIlllIIIllIlI.ElytraFly.this).mc.player.isFallFlying()) {
                ((ElytraFly)lIlIlllIIIllIlI.ElytraFly.this).mc.player.setVelocity(0.0, 0.0, 0.0);
                ((ElytraFly)lIlIlllIIIllIlI.ElytraFly.this).mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket(true));
            } else {
                lIlIlllIIIllIlI.ElytraFly.this.disableInstaDropListener();
            }
        }
    }

    public static final class ChestSwapMode
    extends Enum<ChestSwapMode> {
        private static final /* synthetic */ ChestSwapMode[] $VALUES;
        public static final /* synthetic */ /* enum */ ChestSwapMode WaitForGround;
        public static final /* synthetic */ /* enum */ ChestSwapMode Never;
        public static final /* synthetic */ /* enum */ ChestSwapMode Always;

        public static ChestSwapMode valueOf(String llllllllllllllllIlIllIlIllIIlIll) {
            return Enum.valueOf(ChestSwapMode.class, llllllllllllllllIlIllIlIllIIlIll);
        }

        private static /* synthetic */ ChestSwapMode[] $values() {
            return new ChestSwapMode[]{Always, Never, WaitForGround};
        }

        private ChestSwapMode() {
            ChestSwapMode llllllllllllllllIlIllIlIllIIIllI;
        }

        static {
            Always = new ChestSwapMode();
            Never = new ChestSwapMode();
            WaitForGround = new ChestSwapMode();
            $VALUES = ChestSwapMode.$values();
        }

        public static ChestSwapMode[] values() {
            return (ChestSwapMode[])$VALUES.clone();
        }
    }

    private class StaticGroundListener {
        @EventHandler
        private void chestSwapGroundListener(PlayerMoveEvent llllllllllllllllllIIIlllllIlIIlI) {
            StaticGroundListener llllllllllllllllllIIIlllllIlIIIl;
            if (((ElytraFly)llllllllllllllllllIIIlllllIlIIIl.ElytraFly.this).mc.player != null && ((ElytraFly)llllllllllllllllllIIIlllllIlIIIl.ElytraFly.this).mc.player.isOnGround() && ((ElytraFly)llllllllllllllllllIIIlllllIlIIIl.ElytraFly.this).mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
                Modules.get().get(ChestSwap.class).swap();
                llllllllllllllllllIIIlllllIlIIIl.ElytraFly.this.disableGroundListener();
            }
        }

        private StaticGroundListener() {
            StaticGroundListener llllllllllllllllllIIIlllllIlIlll;
        }
    }
}

