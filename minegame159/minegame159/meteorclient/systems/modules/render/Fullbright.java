/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.class_310;

public class Fullbright
extends Module {
    @Override
    public void onDeactivate() {
        Fullbright.disable();
    }

    public Fullbright() {
        super(Categories.Render, "fullbright", "Lights up your world!");
        MeteorClient.EVENT_BUS.subscribe(StaticListener.class);
    }

    public static void disable() {
        StaticListener.access$010();
    }

    @Override
    public void onActivate() {
        Fullbright.enable();
    }

    public static void enable() {
        StaticListener.access$008();
    }

    private static class StaticListener {
        private static final class_310 mc = class_310.method_1551();
        private static double prevGamma;
        private static int lastTimesEnabled;
        private static int timesEnabled;

        private StaticListener() {
        }

        @EventHandler
        private static void onTick(TickEvent.Post post) {
            if (timesEnabled > 0 && lastTimesEnabled == 0) {
                prevGamma = StaticListener.mc.field_1690.field_1840;
            } else if (timesEnabled == 0 && lastTimesEnabled > 0) {
                StaticListener.mc.field_1690.field_1840 = prevGamma;
            }
            if (timesEnabled > 0) {
                StaticListener.mc.field_1690.field_1840 = 16.0;
            }
            lastTimesEnabled = timesEnabled;
        }

        static int access$010() {
            return timesEnabled--;
        }

        static int access$008() {
            return timesEnabled++;
        }
    }
}

