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
import net.minecraft.class_1297;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import net.minecraft.class_310;
import net.minecraft.class_3532;

public class Rotations {
    private static float preYaw;
    private static final Pool<Rotation> rotationPool;
    private static final List<Rotation> rotations;
    public static float serverYaw;
    private static int i;
    private static boolean sentLastRotation;
    private static int lastRotationTimer;
    private static final class_310 mc;
    public static float serverPitch;
    private static float prePitch;
    private static Rotation lastRotation;
    public static int rotationTimer;

    static class_310 access$000() {
        return mc;
    }

    @EventHandler
    private static void onTick(TickEvent.Pre pre) {
        ++rotationTimer;
    }

    @EventHandler
    private static void onSendMovementPacketsPre(SendMovementPacketsEvent.Pre pre) {
        if (Rotations.mc.field_1719 != Rotations.mc.field_1724) {
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

    public static double getYaw(class_1297 class_12972) {
        return Rotations.mc.field_1724.field_6031 + class_3532.method_15393((float)((float)Math.toDegrees(Math.atan2(class_12972.method_23321() - Rotations.mc.field_1724.method_23321(), class_12972.method_23317() - Rotations.mc.field_1724.method_23317())) - 90.0f - Rotations.mc.field_1724.field_6031));
    }

    private static void resetPreRotation() {
        Rotations.mc.field_1724.field_6031 = preYaw;
        Rotations.mc.field_1724.field_5965 = prePitch;
    }

    private static void setupMovementPacketRotation(Rotation rotation) {
        Rotations.setClientRotation(rotation);
        Rotations.setCamRotation(rotation.yaw, rotation.pitch);
    }

    private static void setClientRotation(Rotation rotation) {
        preYaw = Rotations.mc.field_1724.field_6031;
        prePitch = Rotations.mc.field_1724.field_5965;
        Rotations.mc.field_1724.field_6031 = (float)rotation.yaw;
        Rotations.mc.field_1724.field_5965 = (float)rotation.pitch;
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
            if (Rotations.mc.field_1719 == Rotations.mc.field_1724) {
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

    public static double getPitch(class_243 class_2432) {
        double d = class_2432.method_10216() - Rotations.mc.field_1724.method_23317();
        double d2 = class_2432.method_10214() - (Rotations.mc.field_1724.method_23318() + (double)Rotations.mc.field_1724.method_18381(Rotations.mc.field_1724.method_18376()));
        double d3 = class_2432.method_10215() - Rotations.mc.field_1724.method_23321();
        double d4 = Math.sqrt(d * d + d3 * d3);
        return Rotations.mc.field_1724.field_5965 + class_3532.method_15393((float)((float)(-Math.toDegrees(Math.atan2(d2, d4))) - Rotations.mc.field_1724.field_5965));
    }

    public static double getYaw(class_2338 class_23382) {
        return Rotations.mc.field_1724.field_6031 + class_3532.method_15393((float)((float)Math.toDegrees(Math.atan2((double)class_23382.method_10260() + 0.5 - Rotations.mc.field_1724.method_23321(), (double)class_23382.method_10263() + 0.5 - Rotations.mc.field_1724.method_23317())) - 90.0f - Rotations.mc.field_1724.field_6031));
    }

    public static double getPitch(class_1297 class_12972) {
        return Rotations.getPitch(class_12972, Target.Body);
    }

    public static double getPitch(class_2338 class_23382) {
        double d = (double)class_23382.method_10263() + 0.5 - Rotations.mc.field_1724.method_23317();
        double d2 = (double)class_23382.method_10264() + 0.5 - (Rotations.mc.field_1724.method_23318() + (double)Rotations.mc.field_1724.method_18381(Rotations.mc.field_1724.method_18376()));
        double d3 = (double)class_23382.method_10260() + 0.5 - Rotations.mc.field_1724.method_23321();
        double d4 = Math.sqrt(d * d + d3 * d3);
        return Rotations.mc.field_1724.field_5965 + class_3532.method_15393((float)((float)(-Math.toDegrees(Math.atan2(d2, d4))) - Rotations.mc.field_1724.field_5965));
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

    public static double getYaw(class_243 class_2432) {
        return Rotations.mc.field_1724.field_6031 + class_3532.method_15393((float)((float)Math.toDegrees(Math.atan2(class_2432.method_10215() - Rotations.mc.field_1724.method_23321(), class_2432.method_10216() - Rotations.mc.field_1724.method_23317())) - 90.0f - Rotations.mc.field_1724.field_6031));
    }

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(Rotations.class);
    }

    static {
        mc = class_310.method_1551();
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

    public static double getPitch(class_1297 class_12972, Target target) {
        double d = target == Target.Head ? class_12972.method_23320() : (target == Target.Body ? class_12972.method_23318() + (double)(class_12972.method_17682() / 2.0f) : class_12972.method_23318());
        double d2 = class_12972.method_23317() - Rotations.mc.field_1724.method_23317();
        double d3 = d - (Rotations.mc.field_1724.method_23318() + (double)Rotations.mc.field_1724.method_18381(Rotations.mc.field_1724.method_18376()));
        double d4 = class_12972.method_23321() - Rotations.mc.field_1724.method_23321();
        double d5 = Math.sqrt(d2 * d2 + d4 * d4);
        return Rotations.mc.field_1724.field_5965 + class_3532.method_15393((float)((float)(-Math.toDegrees(Math.atan2(d3, d5))) - Rotations.mc.field_1724.field_5965));
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
            Rotations.access$000().method_1562().method_2883((class_2596)new class_2828.class_2831((float)this.yaw, (float)this.pitch, Rotations.access$000().field_1724.method_24828()));
            this.runCallback();
        }

        Rotation(1 var1_1) {
            this();
        }
    }
}

