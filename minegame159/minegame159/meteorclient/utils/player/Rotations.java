/*
 * Decompiled with CFR 0.151.
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
    private static float preYaw;
    private static final Pool<Rotation> rotationPool;
    private static final List<Rotation> rotations;
    public static float serverYaw;
    private static int i;
    private static boolean sentLastRotation;
    private static int lastRotationTimer;
    private static final MinecraftClient mc;
    public static float serverPitch;
    private static float prePitch;
    private static Rotation lastRotation;
    public static int rotationTimer;

    static MinecraftClient access$000() {
        return mc;
    }

    @EventHandler
    private static void onTick(TickEvent.Pre pre) {
        ++rotationTimer;
    }

    @EventHandler
    private static void onSendMovementPacketsPre(SendMovementPacketsEvent.Pre pre) {
        if (Rotations.mc.cameraEntity != Rotations.mc.player) {
            return;
        }
        sentLastRotation = false;
        if (!rotations.isEmpty()) {
            Rotations.resetLastRotation();
            Rotation rotation = rotations.get(i);
            Rotations.setupMovementPacketRotation(rotation);
            if (rotations.size() > 1) {
                rotationPool.free(rotation);
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

    public static double getYaw(Entity Entity2) {
        return Rotations.mc.player.yaw + MathHelper.wrapDegrees((float)((float)Math.toDegrees(Math.atan2(Entity2.getZ() - Rotations.mc.player.getZ(), Entity2.getX() - Rotations.mc.player.getX())) - 90.0f - Rotations.mc.player.yaw));
    }

    private static void resetPreRotation() {
        Rotations.mc.player.yaw = preYaw;
        Rotations.mc.player.pitch = prePitch;
    }

    private static void setupMovementPacketRotation(Rotation rotation) {
        Rotations.setClientRotation(rotation);
        Rotations.setCamRotation(rotation.yaw, rotation.pitch);
    }

    private static void setClientRotation(Rotation rotation) {
        preYaw = Rotations.mc.player.yaw;
        prePitch = Rotations.mc.player.pitch;
        Rotations.mc.player.yaw = (float)rotation.yaw;
        Rotations.mc.player.pitch = (float)rotation.pitch;
    }

    private static Rotation lambda$static$0() {
        return new Rotation(null);
    }

    public static void setCamRotation(double d, double d2) {
        serverYaw = (float)d;
        serverPitch = (float)d2;
        rotationTimer = 0;
    }

    @EventHandler
    private static void onSendMovementPacketsPost(SendMovementPacketsEvent.Post post) {
        if (!rotations.isEmpty()) {
            if (Rotations.mc.cameraEntity == Rotations.mc.player) {
                rotations.get(i - 1).runCallback();
                if (rotations.size() == 1) {
                    lastRotation = rotations.get(i - 1);
                }
                Rotations.resetPreRotation();
            }
            while (++i < rotations.size()) {
                Rotation rotation = rotations.get(i);
                Rotations.setCamRotation(rotation.yaw, rotation.pitch);
                if (rotation.clientSide) {
                    Rotations.setClientRotation(rotation);
                }
                rotation.sendPacket();
                if (rotation.clientSide) {
                    Rotations.resetPreRotation();
                }
                if (i == rotations.size() - 1) {
                    lastRotation = rotation;
                    continue;
                }
                rotationPool.free(rotation);
            }
            rotations.clear();
            i = 0;
        } else if (sentLastRotation) {
            Rotations.resetPreRotation();
        }
    }

    public static double getPitch(Vec3d Vec3d2) {
        double d = Vec3d2.getX() - Rotations.mc.player.getX();
        double d2 = Vec3d2.getY() - (Rotations.mc.player.getY() + (double)Rotations.mc.player.getEyeHeight(Rotations.mc.player.getPose()));
        double d3 = Vec3d2.getZ() - Rotations.mc.player.getZ();
        double d4 = Math.sqrt(d * d + d3 * d3);
        return Rotations.mc.player.pitch + MathHelper.wrapDegrees((float)((float)(-Math.toDegrees(Math.atan2(d2, d4))) - Rotations.mc.player.pitch));
    }

    public static double getYaw(BlockPos BlockPos2) {
        return Rotations.mc.player.yaw + MathHelper.wrapDegrees((float)((float)Math.toDegrees(Math.atan2((double)BlockPos2.getZ() + 0.5 - Rotations.mc.player.getZ(), (double)BlockPos2.getX() + 0.5 - Rotations.mc.player.getX())) - 90.0f - Rotations.mc.player.yaw));
    }

    public static double getPitch(Entity Entity2) {
        return Rotations.getPitch(Entity2, Target.Body);
    }

    public static double getPitch(BlockPos BlockPos2) {
        double d = (double)BlockPos2.getX() + 0.5 - Rotations.mc.player.getX();
        double d2 = (double)BlockPos2.getY() + 0.5 - (Rotations.mc.player.getY() + (double)Rotations.mc.player.getEyeHeight(Rotations.mc.player.getPose()));
        double d3 = (double)BlockPos2.getZ() + 0.5 - Rotations.mc.player.getZ();
        double d4 = Math.sqrt(d * d + d3 * d3);
        return Rotations.mc.player.pitch + MathHelper.wrapDegrees((float)((float)(-Math.toDegrees(Math.atan2(d2, d4))) - Rotations.mc.player.pitch));
    }

    public static void rotate(double d, double d2, int n, boolean bl, Runnable runnable) {
        int n2;
        Rotation rotation = rotationPool.get();
        rotation.set(d, d2, n, bl, runnable);
        for (n2 = 0; n2 < rotations.size() && n <= Rotations.rotations.get((int)n2).priority; ++n2) {
            if (false <= true) continue;
            return;
        }
        rotations.add(n2, rotation);
    }

    public static void rotate(double d, double d2) {
        Rotations.rotate(d, d2, 0, null);
    }

    public static double getYaw(Vec3d Vec3d2) {
        return Rotations.mc.player.yaw + MathHelper.wrapDegrees((float)((float)Math.toDegrees(Math.atan2(Vec3d2.getZ() - Rotations.mc.player.getZ(), Vec3d2.getX() - Rotations.mc.player.getX())) - 90.0f - Rotations.mc.player.yaw));
    }

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(Rotations.class);
    }

    static {
        mc = MinecraftClient.getInstance();
        rotationPool = new Pool<Rotation>(Rotations::lambda$static$0);
        rotations = new ArrayList<Rotation>();
        i = 0;
    }

    public static void rotate(double d, double d2, int n, Runnable runnable) {
        Rotations.rotate(d, d2, n, false, runnable);
    }

    private static void resetLastRotation() {
        if (lastRotation != null) {
            rotationPool.free(lastRotation);
            lastRotation = null;
            lastRotationTimer = 0;
        }
    }

    public static void rotate(double d, double d2, Runnable runnable) {
        Rotations.rotate(d, d2, 0, runnable);
    }

    public static double getPitch(Entity Entity2, Target target) {
        double d = target == Target.Head ? Entity2.getEyeY() : (target == Target.Body ? Entity2.getY() + (double)(Entity2.getHeight() / 2.0f) : Entity2.getY());
        double d2 = Entity2.getX() - Rotations.mc.player.getX();
        double d3 = d - (Rotations.mc.player.getY() + (double)Rotations.mc.player.getEyeHeight(Rotations.mc.player.getPose()));
        double d4 = Entity2.getZ() - Rotations.mc.player.getZ();
        double d5 = Math.sqrt(d2 * d2 + d4 * d4);
        return Rotations.mc.player.pitch + MathHelper.wrapDegrees((float)((float)(-Math.toDegrees(Math.atan2(d3, d5))) - Rotations.mc.player.pitch));
    }

    private static class Rotation {
        public double pitch;
        public Runnable callback;
        public boolean clientSide;
        public int priority;
        public double yaw;

        public void runCallback() {
            if (this.callback != null) {
                this.callback.run();
            }
        }

        private Rotation() {
        }

        public void set(double d, double d2, int n, boolean bl, Runnable runnable) {
            this.yaw = d;
            this.pitch = d2;
            this.priority = n;
            this.clientSide = bl;
            this.callback = runnable;
        }

        public void sendPacket() {
            Rotations.access$000().getNetworkHandler().sendPacket((Packet)new PlayerMoveC2SPacket.class_2831((float)this.yaw, (float)this.pitch, Rotations.access$000().player.isOnGround()));
            this.runCallback();
        }

        Rotation(1 var1_1) {
            this();
        }
    }
}

