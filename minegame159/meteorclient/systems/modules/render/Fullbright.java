/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.MinecraftClient
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

    @Override
    public void onActivate() {
        Fullbright.enable();
    }

    public static void disable() {
        StaticListener.timesEnabled--;
    }

    public Fullbright() {
        super(Categories.Render, "fullbright", "Lights up your world!");
        Fullbright lIIllIIIIlIllII;
        MeteorClient.EVENT_BUS.subscribe(StaticListener.class);
    }

    public static void enable() {
        StaticListener.timesEnabled++;
    }

    private static class StaticListener {
        private static final /* synthetic */ MinecraftClient mc;
        private static /* synthetic */ int lastTimesEnabled;
        private static /* synthetic */ double prevGamma;
        private static /* synthetic */ int timesEnabled;

        private StaticListener() {
            StaticListener lIIlllllllIIlll;
        }

        static {
            mc = MinecraftClient.getInstance();
        }

        @EventHandler
        private static void onTick(TickEvent.Post lIIlllllllIIlIl) {
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
    }
}

