/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.ServerInfo
 */
package minegame159.meteorclient.systems.modules.misc;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.ConnectToServerEvent;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.client.network.ServerInfo;

public class AutoReconnect
extends Module {
    private final /* synthetic */ SettingGroup sgGeneral;
    public /* synthetic */ ServerInfo lastServerInfo;
    public final /* synthetic */ Setting<Double> time;

    public AutoReconnect() {
        super(Categories.Misc, "auto-reconnect", "Automatically reconnects when disconnected from a server.");
        AutoReconnect llIIIIIllIllI;
        llIIIIIllIllI.sgGeneral = llIIIIIllIllI.settings.getDefaultGroup();
        llIIIIIllIllI.time = llIIIIIllIllI.sgGeneral.add(new DoubleSetting.Builder().name("delay").description("The amount of seconds to wait before reconnecting to the server.").defaultValue(5.0).min(0.0).build());
        MeteorClient.EVENT_BUS.subscribe(llIIIIIllIllI.new StaticListener());
    }

    private class StaticListener {
        private StaticListener() {
            StaticListener lIlIIIlIIIIlllI;
        }

        @EventHandler
        private void onConnectToServer(ConnectToServerEvent lIlIIIlIIIIlIIl) {
            StaticListener lIlIIIlIIIIlIlI;
            lIlIIIlIIIIlIlI.AutoReconnect.this.lastServerInfo = lIlIIIlIIIIlIlI.AutoReconnect.this.mc.isInSingleplayer() ? null : lIlIIIlIIIIlIlI.AutoReconnect.this.mc.getCurrentServerEntry();
        }
    }
}

