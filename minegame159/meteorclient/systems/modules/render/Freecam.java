/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
 *  net.minecraft.util.Formatting
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.client.option.KeyBinding
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.hit.BlockHitResult
 *  net.minecraft.util.hit.EntityHitResult
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
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public class Freecam
extends Module {
    private final /* synthetic */ Setting<AutoDisableEvent> autoDisableOnDamage;
    private final /* synthetic */ Setting<Boolean> rotate;
    public final /* synthetic */ Vec3 prevPos;
    private final /* synthetic */ Setting<Boolean> renderHands;
    private final /* synthetic */ Setting<Double> speed;
    private /* synthetic */ boolean right;
    public /* synthetic */ float prevPitch;
    private final /* synthetic */ Setting<Boolean> autoDisableOnLog;
    public /* synthetic */ float prevYaw;
    private /* synthetic */ boolean left;
    private final /* synthetic */ SettingGroup sgGeneral;
    public /* synthetic */ float pitch;
    private final /* synthetic */ Setting<Boolean> reloadChunks;
    private /* synthetic */ boolean backward;
    public final /* synthetic */ Vec3 pos;
    private /* synthetic */ boolean up;
    private /* synthetic */ boolean down;
    public /* synthetic */ float yaw;
    private /* synthetic */ boolean forward;

    public void changeLookDirection(double llllllllllllllllIlIlIllIllIIIlIl, double llllllllllllllllIlIlIllIllIIIlll) {
        Freecam llllllllllllllllIlIlIllIllIIIllI;
        llllllllllllllllIlIlIllIllIIIllI.prevYaw = llllllllllllllllIlIlIllIllIIIllI.yaw;
        llllllllllllllllIlIlIllIllIIIllI.prevPitch = llllllllllllllllIlIlIllIllIIIllI.pitch;
        llllllllllllllllIlIlIllIllIIIllI.yaw = (float)((double)llllllllllllllllIlIlIllIllIIIllI.yaw + llllllllllllllllIlIlIllIllIIIlIl);
        llllllllllllllllIlIlIllIllIIIllI.pitch = (float)((double)llllllllllllllllIlIlIllIllIIIllI.pitch + llllllllllllllllIlIlIllIllIIIlll);
        llllllllllllllllIlIlIllIllIIIllI.pitch = MathHelper.clamp((float)llllllllllllllllIlIlIllIllIIIllI.pitch, (float)-90.0f, (float)90.0f);
    }

    @Override
    public void onDeactivate() {
        Freecam llllllllllllllllIlIlIlllIIIlIIII;
        if (llllllllllllllllIlIlIlllIIIlIIII.reloadChunks.get().booleanValue()) {
            llllllllllllllllIlIlIlllIIIlIIII.mc.worldRenderer.reload();
        }
    }

    public double getX(float llllllllllllllllIlIlIllIlIlllIll) {
        Freecam llllllllllllllllIlIlIllIlIllllII;
        return MathHelper.lerp((double)llllllllllllllllIlIlIllIlIlllIll, (double)llllllllllllllllIlIlIllIlIllllII.prevPos.x, (double)llllllllllllllllIlIlIllIlIllllII.pos.x);
    }

    public double getPitch(float llllllllllllllllIlIlIllIlIlIIlIl) {
        Freecam llllllllllllllllIlIlIllIlIlIIlII;
        return MathHelper.lerp((float)llllllllllllllllIlIlIllIlIlIIlIl, (float)llllllllllllllllIlIlIllIlIlIIlII.prevPitch, (float)llllllllllllllllIlIlIllIlIlIIlII.pitch);
    }

    public double getYaw(float llllllllllllllllIlIlIllIlIlIlIll) {
        Freecam llllllllllllllllIlIlIllIlIlIllII;
        return MathHelper.lerp((float)llllllllllllllllIlIlIllIlIlIlIll, (float)llllllllllllllllIlIlIllIlIlIllII.prevYaw, (float)llllllllllllllllIlIlIllIlIlIllII.yaw);
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent llllllllllllllllIlIlIlllIIIIllII) {
        Freecam llllllllllllllllIlIlIlllIIIIllIl;
        llllllllllllllllIlIlIlllIIIIllIl.unpress();
        llllllllllllllllIlIlIlllIIIIllIl.prevPos.set(llllllllllllllllIlIlIlllIIIIllIl.pos);
        llllllllllllllllIlIlIlllIIIIllIl.prevYaw = llllllllllllllllIlIlIlllIIIIllIl.yaw;
        llllllllllllllllIlIlIlllIIIIllIl.prevPitch = llllllllllllllllIlIlIlllIIIIllIl.pitch;
    }

    public Freecam() {
        super(Categories.Render, "freecam", "Makes you fly out of your body.");
        Freecam llllllllllllllllIlIlIlllIIIlIllI;
        llllllllllllllllIlIlIlllIIIlIllI.sgGeneral = llllllllllllllllIlIlIlllIIIlIllI.settings.getDefaultGroup();
        llllllllllllllllIlIlIlllIIIlIllI.speed = llllllllllllllllIlIlIlllIIIlIllI.sgGeneral.add(new DoubleSetting.Builder().name("speed").description("Your speed while in Freecam.").defaultValue(1.0).min(0.0).build());
        llllllllllllllllIlIlIlllIIIlIllI.autoDisableOnDamage = llllllllllllllllIlIlIlllIIIlIllI.sgGeneral.add(new EnumSetting.Builder().name("auto-disable-on-damage").description("Disables Freecam when you either take damage or die.").defaultValue(AutoDisableEvent.OnDamage).build());
        llllllllllllllllIlIlIlllIIIlIllI.autoDisableOnLog = llllllllllllllllIlIlIlllIIIlIllI.sgGeneral.add(new BoolSetting.Builder().name("auto-disable-on-log").description("Disables Freecam when you disconnect from a server.").defaultValue(true).build());
        llllllllllllllllIlIlIlllIIIlIllI.reloadChunks = llllllllllllllllIlIlIlllIIIlIllI.sgGeneral.add(new BoolSetting.Builder().name("reload-chunks").description("Disables cave culling.").defaultValue(true).build());
        llllllllllllllllIlIlIlllIIIlIllI.renderHands = llllllllllllllllIlIlIlllIIIlIllI.sgGeneral.add(new BoolSetting.Builder().name("render-hand").description("Whether or not to render your hand in Freecam.").defaultValue(true).build());
        llllllllllllllllIlIlIlllIIIlIllI.rotate = llllllllllllllllIlIlIlllIIIlIllI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Rotates your character to whatever block or entity you are looking at.").defaultValue(false).build());
        llllllllllllllllIlIlIlllIIIlIllI.pos = new Vec3();
        llllllllllllllllIlIlIlllIIIlIllI.prevPos = new Vec3();
    }

    @EventHandler
    private void onKey(KeyEvent llllllllllllllllIlIlIllIllIlllII) {
        Freecam llllllllllllllllIlIlIllIlllIIIII;
        boolean llllllllllllllllIlIlIllIllIllllI = true;
        if (KeyBindingHelper.getBoundKeyOf((KeyBinding)llllllllllllllllIlIlIllIlllIIIII.mc.options.keyForward).getCode() == llllllllllllllllIlIlIllIllIlllII.key) {
            llllllllllllllllIlIlIllIlllIIIII.forward = llllllllllllllllIlIlIllIllIlllII.action != KeyAction.Release;
        } else if (KeyBindingHelper.getBoundKeyOf((KeyBinding)llllllllllllllllIlIlIllIlllIIIII.mc.options.keyBack).getCode() == llllllllllllllllIlIlIllIllIlllII.key) {
            llllllllllllllllIlIlIllIlllIIIII.backward = llllllllllllllllIlIlIllIllIlllII.action != KeyAction.Release;
        } else if (KeyBindingHelper.getBoundKeyOf((KeyBinding)llllllllllllllllIlIlIllIlllIIIII.mc.options.keyRight).getCode() == llllllllllllllllIlIlIllIllIlllII.key) {
            llllllllllllllllIlIlIllIlllIIIII.right = llllllllllllllllIlIlIllIllIlllII.action != KeyAction.Release;
        } else if (KeyBindingHelper.getBoundKeyOf((KeyBinding)llllllllllllllllIlIlIllIlllIIIII.mc.options.keyLeft).getCode() == llllllllllllllllIlIlIllIllIlllII.key) {
            llllllllllllllllIlIlIllIlllIIIII.left = llllllllllllllllIlIlIllIllIlllII.action != KeyAction.Release;
        } else if (KeyBindingHelper.getBoundKeyOf((KeyBinding)llllllllllllllllIlIlIllIlllIIIII.mc.options.keyJump).getCode() == llllllllllllllllIlIlIllIllIlllII.key) {
            llllllllllllllllIlIlIllIlllIIIII.up = llllllllllllllllIlIlIllIllIlllII.action != KeyAction.Release;
        } else if (KeyBindingHelper.getBoundKeyOf((KeyBinding)llllllllllllllllIlIlIllIlllIIIII.mc.options.keySneak).getCode() == llllllllllllllllIlIlIllIllIlllII.key) {
            llllllllllllllllIlIlIllIlllIIIII.down = llllllllllllllllIlIlIllIllIlllII.action != KeyAction.Release;
        } else {
            llllllllllllllllIlIlIllIllIllllI = false;
        }
        if (llllllllllllllllIlIlIllIllIllllI) {
            llllllllllllllllIlIlIllIllIlllII.cancel();
        }
    }

    public boolean renderHands() {
        Freecam llllllllllllllllIlIlIllIllIIIIIl;
        return !llllllllllllllllIlIlIllIllIIIIIl.isActive() || llllllllllllllllIlIlIllIllIIIIIl.renderHands.get() != false;
    }

    @EventHandler
    private void onChunkOcclusion(ChunkOcclusionEvent llllllllllllllllIlIlIllIllIllIII) {
        llllllllllllllllIlIlIllIllIllIII.cancel();
    }

    private void unpress() {
        Freecam llllllllllllllllIlIlIlllIIIIlIII;
        llllllllllllllllIlIlIlllIIIIlIII.mc.options.keyForward.setPressed(false);
        llllllllllllllllIlIlIlllIIIIlIII.mc.options.keyBack.setPressed(false);
        llllllllllllllllIlIlIlllIIIIlIII.mc.options.keyRight.setPressed(false);
        llllllllllllllllIlIlIlllIIIIlIII.mc.options.keyLeft.setPressed(false);
        llllllllllllllllIlIlIlllIIIIlIII.mc.options.keyJump.setPressed(false);
        llllllllllllllllIlIlIlllIIIIlIII.mc.options.keySneak.setPressed(false);
    }

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllIlIlIllIllllIlll) {
        Freecam llllllllllllllllIlIlIllIlllllIII;
        if (llllllllllllllllIlIlIllIlllllIII.mc.cameraEntity.isInsideWall()) {
            llllllllllllllllIlIlIllIlllllIII.mc.getCameraEntity().noClip = true;
        }
        if (llllllllllllllllIlIlIllIlllllIII.mc.currentScreen != null) {
            return;
        }
        Vec3d llllllllllllllllIlIlIllIllllIllI = Vec3d.fromPolar((float)0.0f, (float)llllllllllllllllIlIlIllIlllllIII.yaw);
        Vec3d llllllllllllllllIlIlIllIllllIlIl = Vec3d.fromPolar((float)0.0f, (float)(llllllllllllllllIlIlIllIlllllIII.yaw + 90.0f));
        double llllllllllllllllIlIlIllIllllIlII = 0.0;
        double llllllllllllllllIlIlIllIllllIIll = 0.0;
        double llllllllllllllllIlIlIllIllllIIlI = 0.0;
        if (llllllllllllllllIlIlIllIlllllIII.rotate.get().booleanValue()) {
            if (llllllllllllllllIlIlIllIlllllIII.mc.crosshairTarget instanceof BlockHitResult) {
                Vec3d llllllllllllllllIlIlIllIlllllIll = llllllllllllllllIlIlIllIlllllIII.mc.crosshairTarget.getPos();
                BlockPos llllllllllllllllIlIlIllIllllllII = ((BlockHitResult)llllllllllllllllIlIlIllIlllllIII.mc.crosshairTarget).getBlockPos();
                if (!llllllllllllllllIlIlIllIlllllIII.mc.world.getBlockState(llllllllllllllllIlIlIllIllllllII).isAir()) {
                    llllllllllllllllIlIlIllIlllllIII.mc.player.yaw = (float)Rotations.getYaw(llllllllllllllllIlIlIllIlllllIll);
                    llllllllllllllllIlIlIllIlllllIII.mc.player.pitch = (float)Rotations.getPitch(llllllllllllllllIlIlIllIlllllIll);
                }
            } else {
                BlockPos llllllllllllllllIlIlIllIlllllIlI = ((EntityHitResult)llllllllllllllllIlIlIllIlllllIII.mc.crosshairTarget).getEntity().getBlockPos();
                llllllllllllllllIlIlIllIlllllIII.mc.player.yaw = (float)Rotations.getYaw(llllllllllllllllIlIlIllIlllllIlI);
                llllllllllllllllIlIlIllIlllllIII.mc.player.pitch = (float)Rotations.getPitch(llllllllllllllllIlIlIllIlllllIlI);
            }
        }
        double llllllllllllllllIlIlIllIllllIIIl = 0.5;
        if (llllllllllllllllIlIlIllIlllllIII.mc.options.keySprint.isPressed()) {
            llllllllllllllllIlIlIllIllllIIIl = 1.0;
        }
        boolean llllllllllllllllIlIlIllIllllIIII = false;
        if (llllllllllllllllIlIlIllIlllllIII.forward) {
            llllllllllllllllIlIlIllIllllIlII += llllllllllllllllIlIlIllIllllIllI.x * llllllllllllllllIlIlIllIllllIIIl * llllllllllllllllIlIlIllIlllllIII.speed.get();
            llllllllllllllllIlIlIllIllllIIlI += llllllllllllllllIlIlIllIllllIllI.z * llllllllllllllllIlIlIllIllllIIIl * llllllllllllllllIlIlIllIlllllIII.speed.get();
            llllllllllllllllIlIlIllIllllIIII = true;
        }
        if (llllllllllllllllIlIlIllIlllllIII.backward) {
            llllllllllllllllIlIlIllIllllIlII -= llllllllllllllllIlIlIllIllllIllI.x * llllllllllllllllIlIlIllIllllIIIl * llllllllllllllllIlIlIllIlllllIII.speed.get();
            llllllllllllllllIlIlIllIllllIIlI -= llllllllllllllllIlIlIllIllllIllI.z * llllllllllllllllIlIlIllIllllIIIl * llllllllllllllllIlIlIllIlllllIII.speed.get();
            llllllllllllllllIlIlIllIllllIIII = true;
        }
        boolean llllllllllllllllIlIlIllIlllIllll = false;
        if (llllllllllllllllIlIlIllIlllllIII.right) {
            llllllllllllllllIlIlIllIllllIlII += llllllllllllllllIlIlIllIllllIlIl.x * llllllllllllllllIlIlIllIllllIIIl * llllllllllllllllIlIlIllIlllllIII.speed.get();
            llllllllllllllllIlIlIllIllllIIlI += llllllllllllllllIlIlIllIllllIlIl.z * llllllllllllllllIlIlIllIllllIIIl * llllllllllllllllIlIlIllIlllllIII.speed.get();
            llllllllllllllllIlIlIllIlllIllll = true;
        }
        if (llllllllllllllllIlIlIllIlllllIII.left) {
            llllllllllllllllIlIlIllIllllIlII -= llllllllllllllllIlIlIllIllllIlIl.x * llllllllllllllllIlIlIllIllllIIIl * llllllllllllllllIlIlIllIlllllIII.speed.get();
            llllllllllllllllIlIlIllIllllIIlI -= llllllllllllllllIlIlIllIllllIlIl.z * llllllllllllllllIlIlIllIllllIIIl * llllllllllllllllIlIlIllIlllllIII.speed.get();
            llllllllllllllllIlIlIllIlllIllll = true;
        }
        if (llllllllllllllllIlIlIllIllllIIII && llllllllllllllllIlIlIllIlllIllll) {
            double llllllllllllllllIlIlIllIlllllIIl = 1.0 / Math.sqrt(2.0);
            llllllllllllllllIlIlIllIllllIlII *= llllllllllllllllIlIlIllIlllllIIl;
            llllllllllllllllIlIlIllIllllIIlI *= llllllllllllllllIlIlIllIlllllIIl;
        }
        if (llllllllllllllllIlIlIllIlllllIII.up) {
            llllllllllllllllIlIlIllIllllIIll += llllllllllllllllIlIlIllIllllIIIl * llllllllllllllllIlIlIllIlllllIII.speed.get();
        }
        if (llllllllllllllllIlIlIllIlllllIII.down) {
            llllllllllllllllIlIlIllIllllIIll -= llllllllllllllllIlIlIllIllllIIIl * llllllllllllllllIlIlIllIlllllIII.speed.get();
        }
        llllllllllllllllIlIlIllIlllllIII.prevPos.set(llllllllllllllllIlIlIllIlllllIII.pos);
        llllllllllllllllIlIlIllIlllllIII.pos.set(llllllllllllllllIlIlIllIlllllIII.pos.x + llllllllllllllllIlIlIllIllllIlII, llllllllllllllllIlIlIllIlllllIII.pos.y + llllllllllllllllIlIlIllIllllIIll, llllllllllllllllIlIlIllIlllllIII.pos.z + llllllllllllllllIlIlIllIllllIIlI);
    }

    public double getY(float llllllllllllllllIlIlIllIlIllIlIl) {
        Freecam llllllllllllllllIlIlIllIlIlllIII;
        return MathHelper.lerp((double)llllllllllllllllIlIlIllIlIllIlIl, (double)llllllllllllllllIlIlIllIlIlllIII.prevPos.y, (double)llllllllllllllllIlIlIllIlIlllIII.pos.y);
    }

    public double getZ(float llllllllllllllllIlIlIllIlIllIIIl) {
        Freecam llllllllllllllllIlIlIllIlIllIIII;
        return MathHelper.lerp((double)llllllllllllllllIlIlIllIlIllIIIl, (double)llllllllllllllllIlIlIllIlIllIIII.prevPos.z, (double)llllllllllllllllIlIlIllIlIllIIII.pos.z);
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent llllllllllllllllIlIlIllIllIIlllI) {
        Freecam llllllllllllllllIlIlIllIllIIllll;
        if (!llllllllllllllllIlIlIllIllIIllll.autoDisableOnLog.get().booleanValue()) {
            return;
        }
        llllllllllllllllIlIlIllIllIIllll.toggle();
    }

    @EventHandler
    private void onTookDamage(TookDamageEvent llllllllllllllllIlIlIllIllIlIIIl) {
        Freecam llllllllllllllllIlIlIllIllIlIlII;
        if (llllllllllllllllIlIlIllIllIlIIIl.entity.getUuid() == null) {
            return;
        }
        if (!llllllllllllllllIlIlIllIllIlIIIl.entity.getUuid().equals(llllllllllllllllIlIlIllIllIlIlII.mc.player.getUuid())) {
            return;
        }
        if (llllllllllllllllIlIlIllIllIlIlII.autoDisableOnDamage.get() == AutoDisableEvent.OnDamage || llllllllllllllllIlIlIllIllIlIlII.autoDisableOnDamage.get() == AutoDisableEvent.OnDeath && llllllllllllllllIlIlIllIllIlIIIl.entity.getHealth() <= 0.0f) {
            llllllllllllllllIlIlIllIllIlIlII.toggle();
            ChatUtils.moduleInfo(llllllllllllllllIlIlIllIllIlIlII, "Auto toggled %s(default).", llllllllllllllllIlIlIllIllIlIlII.isActive() ? String.valueOf(new StringBuilder().append((Object)Formatting.GREEN).append("on")) : String.valueOf(new StringBuilder().append((Object)Formatting.RED).append("off")));
        }
    }

    @Override
    public void onActivate() {
        Freecam llllllllllllllllIlIlIlllIIIlIIlI;
        llllllllllllllllIlIlIlllIIIlIIlI.yaw = llllllllllllllllIlIlIlllIIIlIIlI.mc.player.yaw;
        llllllllllllllllIlIlIlllIIIlIIlI.pitch = llllllllllllllllIlIlIlllIIIlIIlI.mc.player.pitch;
        llllllllllllllllIlIlIlllIIIlIIlI.pos.set(llllllllllllllllIlIlIlllIIIlIIlI.mc.gameRenderer.getCamera().getPos());
        llllllllllllllllIlIlIlllIIIlIIlI.prevPos.set(llllllllllllllllIlIlIlllIIIlIIlI.mc.gameRenderer.getCamera().getPos());
        llllllllllllllllIlIlIlllIIIlIIlI.prevYaw = llllllllllllllllIlIlIlllIIIlIIlI.yaw;
        llllllllllllllllIlIlIlllIIIlIIlI.prevPitch = llllllllllllllllIlIlIlllIIIlIIlI.pitch;
        llllllllllllllllIlIlIlllIIIlIIlI.forward = false;
        llllllllllllllllIlIlIlllIIIlIIlI.backward = false;
        llllllllllllllllIlIlIlllIIIlIIlI.right = false;
        llllllllllllllllIlIlIlllIIIlIIlI.left = false;
        llllllllllllllllIlIlIlllIIIlIIlI.up = false;
        llllllllllllllllIlIlIlllIIIlIIlI.down = false;
        llllllllllllllllIlIlIlllIIIlIIlI.unpress();
        if (llllllllllllllllIlIlIlllIIIlIIlI.reloadChunks.get().booleanValue()) {
            llllllllllllllllIlIlIlllIIIlIIlI.mc.worldRenderer.reload();
        }
    }

    public static final class AutoDisableEvent
    extends Enum<AutoDisableEvent> {
        public static final /* synthetic */ /* enum */ AutoDisableEvent OnDamage;
        public static final /* synthetic */ /* enum */ AutoDisableEvent None;
        public static final /* synthetic */ /* enum */ AutoDisableEvent OnDeath;
        private static final /* synthetic */ AutoDisableEvent[] $VALUES;

        public static AutoDisableEvent[] values() {
            return (AutoDisableEvent[])$VALUES.clone();
        }

        public static AutoDisableEvent valueOf(String llIIlllIIIllll) {
            return Enum.valueOf(AutoDisableEvent.class, llIIlllIIIllll);
        }

        private static /* synthetic */ AutoDisableEvent[] $values() {
            return new AutoDisableEvent[]{None, OnDamage, OnDeath};
        }

        static {
            None = new AutoDisableEvent();
            OnDamage = new AutoDisableEvent();
            OnDeath = new AutoDisableEvent();
            $VALUES = AutoDisableEvent.$values();
        }

        private AutoDisableEvent() {
            AutoDisableEvent llIIlllIIIlIlI;
        }
    }
}

