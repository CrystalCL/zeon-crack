/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.MinecraftClient
 */
package minegame159.meteorclient.utils.network;

import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.utils.network.HttpUtils;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import net.minecraft.client.MinecraftClient;

public class OnlinePlayers {
    private static /* synthetic */ long lastPingTime;

    public static void leave() {
        MeteorExecutor.execute(() -> HttpUtils.post("http://meteorclient.com/api/online/leave"));
    }

    public static void update() {
        long llllllllllllllllIllllIlIllllIIII = System.currentTimeMillis();
        if (llllllllllllllllIllllIlIllllIIII - lastPingTime > 300000L) {
            MeteorExecutor.execute(() -> {
                String llllllllllllllllIllllIlIlllIllII = "http://meteorclient.com/api/online/ping";
                String llllllllllllllllIllllIlIlllIlIll = MinecraftClient.getInstance().getSession().getUuid();
                if (llllllllllllllllIllllIlIlllIlIll != null && !llllllllllllllllIllllIlIlllIlIll.isEmpty() && Config.get().sendDataToApi) {
                    llllllllllllllllIllllIlIlllIllII = String.valueOf(new StringBuilder().append(llllllllllllllllIllllIlIlllIllII).append("?uuid=").append(llllllllllllllllIllllIlIlllIlIll));
                }
                HttpUtils.post(llllllllllllllllIllllIlIlllIllII);
            });
            lastPingTime = llllllllllllllllIllllIlIllllIIII;
        }
    }

    public OnlinePlayers() {
        OnlinePlayers llllllllllllllllIllllIlIllllIIll;
    }

    public static void forcePing() {
        lastPingTime = 0L;
    }
}

