/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.client.MinecraftClient;

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
        private static final MinecraftClient mc = MinecraftClient.getInstance();
        private static double prevGamma;
        private static int lastTimesEnabled;
        private static int timesEnabled;

        private StaticListener() {
        }

        @EventHandler
        private static void onTick(TickEvent.Post post) {
            if (timesEnabled > 0 && lastTimesEnabled == 0) {
                prevGamma = StaticListener.mc.options.gamma;
            } else if (timesEnabled == 0 && lastTimesEnabled > 0) {
                StaticListener.mc.options.gamma = prevGamma;
            }
            if (timesEnabled > 0) {
                StaticListener.mc.options.gamma = 16.0;
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

