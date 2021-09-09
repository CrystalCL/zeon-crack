/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookOnly
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.util.math.MathHelper
 */
package minegame159.meteorclient.utils.player;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.player.SendMovementPacketsEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.utils.entity.Target;
import minegame159.meteorclient.utils.misc.Pool;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

public class Rotations {
    private static final /* synthetic */ MinecraftClient mc;
    public static /* synthetic */ float serverPitch;
    private static final /* synthetic */ List<Rotation> rotations;
    public static /* synthetic */ int rotationTimer;
    private static /* synthetic */ Rotation lastRotation;
    private static /* synthetic */ float prePitch;
    private static /* synthetic */ int i;
    private static /* synthetic */ int lastRotationTimer;
    public static /* synthetic */ float serverYaw;
    private static final /* synthetic */ Pool<Rotation> rotationPool;
    private static /* synthetic */ float preYaw;
    private static /* synthetic */ boolean sentLastRotation;

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(Rotations.class);
    }

    @EventHandler
    private static void onSendMovementPacketsPost(SendMovementPacketsEvent.Post lllIIIIIIlIIIll) {
        if (!rotations.isEmpty()) {
            if (Rotations.mc.cameraEntity == Rotations.mc.player) {
                rotations.get(i - 1).runCallback();
                if (rotations.size() == 1) {
                    lastRotation = rotations.get(i - 1);
                }
                Rotations.resetPreRotation();
            }
            while (++i < rotations.size()) {
                Rotation lllIIIIIIlIIlII = rotations.get(i);
                Rotations.setCamRotation(lllIIIIIIlIIlII.yaw, lllIIIIIIlIIlII.pitch);
                if (lllIIIIIIlIIlII.clientSide) {
                    Rotations.setClientRotation(lllIIIIIIlIIlII);
                }
                lllIIIIIIlIIlII.sendPacket();
                if (lllIIIIIIlIIlII.clientSide) {
                    Rotations.resetPreRotation();
                }
                if (i == rotations.size() - 1) {
                    lastRotation = lllIIIIIIlIIlII;
                    continue;
                }
                rotationPool.free(lllIIIIIIlIIlII);
            }
            rotations.clear();
            i = 0;
        } else if (sentLastRotation) {
            Rotations.resetPreRotation();
        }
    }

    private static void setClientRotation(Rotation lllIIIIIIlIIllI) {
        preYaw = Rotations.mc.player.yaw;
        prePitch = Rotations.mc.player.pitch;
        Rotations.mc.player.yaw = (float)lllIIIIIIlIIllI.yaw;
        Rotations.mc.player.pitch = (float)lllIIIIIIlIIllI.pitch;
    }

    public static void rotate(double lllIIIIIlIIIIlI, double lllIIIIIlIIIIIl, int lllIIIIIlIIIlII, Runnable lllIIIIIIllllll) {
        Rotations.rotate(lllIIIIIlIIIIlI, lllIIIIIlIIIIIl, lllIIIIIlIIIlII, false, lllIIIIIIllllll);
    }

    public static double getYaw(Vec3d lllIIIIIIIllIll) {
        return Rotations.mc.player.yaw + MathHelper.wrapDegrees((float)((float)Math.toDegrees(Math.atan2(lllIIIIIIIllIll.getZ() - Rotations.mc.player.getZ(), lllIIIIIIIllIll.getX() - Rotations.mc.player.getX())) - 90.0f - Rotations.mc.player.yaw));
    }

    public static double getYaw(BlockPos llIlllllllIllll) {
        return Rotations.mc.player.yaw + MathHelper.wrapDegrees((float)((float)Math.toDegrees(Math.atan2((double)llIlllllllIllll.getZ() + 0.5 - Rotations.mc.player.getZ(), (double)llIlllllllIllll.getX() + 0.5 - Rotations.mc.player.getX())) - 90.0f - Rotations.mc.player.yaw));
    }

    public static void setCamRotation(double llIllllllIlllIl, double llIllllllIllIlI) {
        serverYaw = (float)llIllllllIlllIl;
        serverPitch = (float)llIllllllIllIlI;
        rotationTimer = 0;
    }

    @EventHandler
    private static void onTick(TickEvent.Pre lllIIIIIIlIIIIl) {
        ++rotationTimer;
    }

    public static void rotate(double lllIIIIIlIllIII, double lllIIIIIlIlIIII, int lllIIIIIlIIllll, boolean lllIIIIIlIlIlIl, Runnable lllIIIIIlIIllIl) {
        int lllIIIIIlIlIIlI;
        Rotation lllIIIIIlIlIIll = rotationPool.get();
        lllIIIIIlIlIIll.set(lllIIIIIlIllIII, lllIIIIIlIlIIII, lllIIIIIlIIllll, lllIIIIIlIlIlIl, lllIIIIIlIIllIl);
        for (lllIIIIIlIlIIlI = 0; lllIIIIIlIlIIlI < rotations.size() && lllIIIIIlIIllll <= Rotations.rotations.get((int)lllIIIIIlIlIIlI).priority; ++lllIIIIIlIlIIlI) {
        }
        rotations.add(lllIIIIIlIlIIlI, lllIIIIIlIlIIll);
    }

    public Rotations() {
        Rotations lllIIIIIllIIIII;
    }

    static {
        mc = MinecraftClient.getInstance();
        rotationPool = new Pool<Rotation>(() -> new Rotation());
        rotations = new ArrayList<Rotation>();
        i = 0;
    }

    @EventHandler
    private static void onSendMovementPacketsPre(SendMovementPacketsEvent.Pre lllIIIIIIlIllIl) {
        if (Rotations.mc.cameraEntity != Rotations.mc.player) {
            return;
        }
        sentLastRotation = false;
        if (!rotations.isEmpty()) {
            Rotations.resetLastRotation();
            Rotation lllIIIIIIlIlllI = rotations.get(i);
            Rotations.setupMovementPacketRotation(lllIIIIIIlIlllI);
            if (rotations.size() > 1) {
                rotationPool.free(lllIIIIIIlIlllI);
            }
            ++i;
        } else if (lastRotation != null) {
            if (lastRotationTimer >= Config.get().rotationHoldTicks) {
                Rotations.resetLastRotation();
            } else {
                Rotations.setupMovementPacketRotation(lastRotation);
                sentLastRotation = true;
                ++lastRotationTimer;
            }
        }
    }

    private static void resetPreRotation() {
        Rotations.mc.player.yaw = preYaw;
        Rotations.mc.player.pitch = prePitch;
    }

    public static double getPitch(Entity lllIIIIIIIIIIlI, Target lllIIIIIIIIIIIl) {
        double lllIIIIIIIIIIII;
        if (lllIIIIIIIIIIIl == Target.Head) {
            double lllIIIIIIIIIlII = lllIIIIIIIIIIlI.getEyeY();
        } else if (lllIIIIIIIIIIIl == Target.Body) {
            double lllIIIIIIIIIIll = lllIIIIIIIIIIlI.getY() + (double)(lllIIIIIIIIIIlI.getHeight() / 2.0f);
        } else {
            lllIIIIIIIIIIII = lllIIIIIIIIIIlI.getY();
        }
        double llIllllllllllll = lllIIIIIIIIIIlI.getX() - Rotations.mc.player.getX();
        double llIlllllllllllI = lllIIIIIIIIIIII - (Rotations.mc.player.getY() + (double)Rotations.mc.player.getEyeHeight(Rotations.mc.player.getPose()));
        double llIllllllllllIl = lllIIIIIIIIIIlI.getZ() - Rotations.mc.player.getZ();
        double llIllllllllllII = Math.sqrt(llIllllllllllll * llIllllllllllll + llIllllllllllIl * llIllllllllllIl);
        return Rotations.mc.player.pitch + MathHelper.wrapDegrees((float)((float)(-Math.toDegrees(Math.atan2(llIlllllllllllI, llIllllllllllII))) - Rotations.mc.player.pitch));
    }

    private static void setupMovementPacketRotation(Rotation lllIIIIIIlIlIIl) {
        Rotations.setClientRotation(lllIIIIIIlIlIIl);
        Rotations.setCamRotation(lllIIIIIIlIlIIl.yaw, lllIIIIIIlIlIIl.pitch);
    }

    public static void rotate(double lllIIIIIIlllIII, double lllIIIIIIlllIlI, Runnable lllIIIIIIlllIIl) {
        Rotations.rotate(lllIIIIIIlllIII, lllIIIIIIlllIlI, 0, lllIIIIIIlllIIl);
    }

    public static double getPitch(Vec3d lllIIIIIIIlIlIl) {
        double lllIIIIIIIlIlII = lllIIIIIIIlIlIl.getX() - Rotations.mc.player.getX();
        double lllIIIIIIIlIIll = lllIIIIIIIlIlIl.getY() - (Rotations.mc.player.getY() + (double)Rotations.mc.player.getEyeHeight(Rotations.mc.player.getPose()));
        double lllIIIIIIIlIIlI = lllIIIIIIIlIlIl.getZ() - Rotations.mc.player.getZ();
        double lllIIIIIIIlIIIl = Math.sqrt(lllIIIIIIIlIlII * lllIIIIIIIlIlII + lllIIIIIIIlIIlI * lllIIIIIIIlIIlI);
        return Rotations.mc.player.pitch + MathHelper.wrapDegrees((float)((float)(-Math.toDegrees(Math.atan2(lllIIIIIIIlIIll, lllIIIIIIIlIIIl))) - Rotations.mc.player.pitch));
    }

    public static double getPitch(Entity llIllllllllIIlI) {
        return Rotations.getPitch(llIllllllllIIlI, Target.Body);
    }

    public static double getPitch(BlockPos llIlllllllIIlII) {
        double llIlllllllIlIII = (double)llIlllllllIIlII.getX() + 0.5 - Rotations.mc.player.getX();
        double llIlllllllIIlll = (double)llIlllllllIIlII.getY() + 0.5 - (Rotations.mc.player.getY() + (double)Rotations.mc.player.getEyeHeight(Rotations.mc.player.getPose()));
        double llIlllllllIIllI = (double)llIlllllllIIlII.getZ() + 0.5 - Rotations.mc.player.getZ();
        double llIlllllllIIlIl = Math.sqrt(llIlllllllIlIII * llIlllllllIlIII + llIlllllllIIllI * llIlllllllIIllI);
        return Rotations.mc.player.pitch + MathHelper.wrapDegrees((float)((float)(-Math.toDegrees(Math.atan2(llIlllllllIIlll, llIlllllllIIlIl))) - Rotations.mc.player.pitch));
    }

    private static void resetLastRotation() {
        if (lastRotation != null) {
            rotationPool.free(lastRotation);
            lastRotation = null;
            lastRotationTimer = 0;
        }
    }

    public static double getYaw(Entity lllIIIIIIIllllI) {
        return Rotations.mc.player.yaw + MathHelper.wrapDegrees((float)((float)Math.toDegrees(Math.atan2(lllIIIIIIIllllI.getZ() - Rotations.mc.player.getZ(), lllIIIIIIIllllI.getX() - Rotations.mc.player.getX())) - 90.0f - Rotations.mc.player.yaw));
    }

    public static void rotate(double lllIIIIIIllIIll, double lllIIIIIIllIIlI) {
        Rotations.rotate(lllIIIIIIllIIll, lllIIIIIIllIIlI, 0, null);
    }

    private static class Rotation {
        public /* synthetic */ int priority;
        public /* synthetic */ double yaw;
        public /* synthetic */ Runnable callback;
        public /* synthetic */ double pitch;
        public /* synthetic */ boolean clientSide;

        public void sendPacket() {
            Rotation llllllllllllllllllIIlIIllllIIlII;
            mc.getNetworkHandler().sendPacket((Packet)new LookOnly((float)llllllllllllllllllIIlIIllllIIlII.yaw, (float)llllllllllllllllllIIlIIllllIIlII.pitch, mc.player.isOnGround()));
            llllllllllllllllllIIlIIllllIIlII.runCallback();
        }

        public void set(double llllllllllllllllllIIlIIlllllIIIl, double llllllllllllllllllIIlIIllllIlIlI, int llllllllllllllllllIIlIIllllIlIIl, boolean llllllllllllllllllIIlIIllllIlIII, Runnable llllllllllllllllllIIlIIllllIIlll) {
            llllllllllllllllllIIlIIlllllIIlI.yaw = llllllllllllllllllIIlIIlllllIIIl;
            llllllllllllllllllIIlIIlllllIIlI.pitch = llllllllllllllllllIIlIIllllIlIlI;
            llllllllllllllllllIIlIIlllllIIlI.priority = llllllllllllllllllIIlIIllllIlIIl;
            llllllllllllllllllIIlIIlllllIIlI.clientSide = llllllllllllllllllIIlIIllllIlIII;
            llllllllllllllllllIIlIIlllllIIlI.callback = llllllllllllllllllIIlIIllllIIlll;
        }

        private Rotation() {
            Rotation llllllllllllllllllIIlIIllllllIlI;
        }

        public void runCallback() {
            Rotation llllllllllllllllllIIlIIllllIIIIl;
            if (llllllllllllllllllIIlIIllllIIIIl.callback != null) {
                llllllllllllllllllIIlIIllllIIIIl.callback.run();
            }
        }
    }
}

