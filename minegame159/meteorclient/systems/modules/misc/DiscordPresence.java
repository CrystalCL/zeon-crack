/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;

public class DiscordPresence
extends Module {
    private static final /* synthetic */ DiscordRichPresence rpc;
    private /* synthetic */ SmallImage currentSmallImage;
    private final /* synthetic */ Setting<String> line1;
    private final /* synthetic */ Setting<String> line3;
    private /* synthetic */ int ticks;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<String> line2;
    private static final /* synthetic */ DiscordRPC instance;

    private String getName() {
        DiscordPresence lllllllllllllllllllllIlIlllllIll;
        return lllllllllllllllllllllIlIlllllIll.mc.player.getGameProfile().getName();
    }

    private void updateDetails() {
        DiscordPresence lllllllllllllllllllllIlIllllIlll;
        if (lllllllllllllllllllllIlIllllIlll.isActive() && Utils.canUpdate()) {
            DiscordPresence.rpc.details = lllllllllllllllllllllIlIllllIlll.getLine(lllllllllllllllllllllIlIllllIlll.line1);
            DiscordPresence.rpc.state = lllllllllllllllllllllIlIllllIlll.getLine(lllllllllllllllllllllIlIllllIlll.line2);
            DiscordPresence.rpc.state = lllllllllllllllllllllIlIllllIlll.getLine(lllllllllllllllllllllIlIllllIlll.line3);
            instance.Discord_UpdatePresence(rpc);
        }
    }

    public DiscordPresence() {
        super(Categories.Misc, "discord-presence", "Displays a RPC for you on Discord to show that you're playing Meteor Client!");
        DiscordPresence lllllllllllllllllllllIllIIIlIlII;
        lllllllllllllllllllllIllIIIlIlII.sgGeneral = lllllllllllllllllllllIllIIIlIlII.settings.getDefaultGroup();
        lllllllllllllllllllllIllIIIlIlII.line1 = lllllllllllllllllllllIllIIIlIlII.sgGeneral.add(new StringSetting.Builder().name("line-1").description("The text it displays on line 1 of the RPC.").defaultValue("{player} || {server}").onChanged(lllllllllllllllllllllIlIlllIllII -> {
            DiscordPresence lllllllllllllllllllllIlIlllIllIl;
            lllllllllllllllllllllIlIlllIllIl.updateDetails();
        }).build());
        lllllllllllllllllllllIllIIIlIlII.line2 = lllllllllllllllllllllIllIIIlIlII.sgGeneral.add(new StringSetting.Builder().name("line-2").description("The text it displays on line 2 of the RPC.").defaultValue("ArtikHack 0n t0p!").onChanged(lllllllllllllllllllllIlIllllIIII -> {
            DiscordPresence lllllllllllllllllllllIlIlllIllll;
            lllllllllllllllllllllIlIlllIllll.updateDetails();
        }).build());
        lllllllllllllllllllllIllIIIlIlII.line3 = lllllllllllllllllllllIllIIIlIlII.sgGeneral.add(new StringSetting.Builder().name("line-3").description("Kill count.").defaultValue("EZ! I got 18 kills!").onChanged(lllllllllllllllllllllIlIllllIlII -> {
            DiscordPresence lllllllllllllllllllllIlIllllIIll;
            lllllllllllllllllllllIlIllllIIll.updateDetails();
        }).build());
    }

    static {
        rpc = new DiscordRichPresence();
        instance = DiscordRPC.INSTANCE;
    }

    @Override
    public void onDeactivate() {
        instance.Discord_ClearPresence();
        instance.Discord_Shutdown();
    }

    private String getServer() {
        DiscordPresence lllllllllllllllllllllIlIllllllIl;
        if (lllllllllllllllllllllIlIllllllIl.mc.isInSingleplayer()) {
            return "SinglePlayer";
        }
        return Utils.getWorldName();
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllllIllIIIIIlll) {
        DiscordPresence lllllllllllllllllllllIllIIIIIllI;
        if (!Utils.canUpdate()) {
            return;
        }
        ++lllllllllllllllllllllIllIIIIIllI.ticks;
        if (lllllllllllllllllllllIllIIIIIllI.ticks >= 200) {
            lllllllllllllllllllllIllIIIIIllI.currentSmallImage = lllllllllllllllllllllIllIIIIIllI.currentSmallImage.next();
            lllllllllllllllllllllIllIIIIIllI.currentSmallImage.apply();
            instance.Discord_UpdatePresence(rpc);
            lllllllllllllllllllllIllIIIIIllI.ticks = 0;
        }
        lllllllllllllllllllllIllIIIIIllI.updateDetails();
        instance.Discord_RunCallbacks();
    }

    @Override
    public void onActivate() {
        DiscordPresence lllllllllllllllllllllIllIIIIllIl;
        DiscordEventHandlers lllllllllllllllllllllIllIIIIllll = new DiscordEventHandlers();
        instance.Discord_Initialize("847813526659268628", lllllllllllllllllllllIllIIIIllll, true, null);
        DiscordPresence.rpc.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPresence.rpc.largeImageKey = "artiklogo";
        String lllllllllllllllllllllIllIIIIlllI = "ArtikHack Client v1.6";
        if (!Config.get().devBuild.isEmpty()) {
            lllllllllllllllllllllIllIIIIlllI = String.valueOf(new StringBuilder().append(lllllllllllllllllllllIllIIIIlllI).append(" Dev Build: ").append(Config.get().devBuild));
        }
        DiscordPresence.rpc.largeImageText = lllllllllllllllllllllIllIIIIlllI;
        lllllllllllllllllllllIllIIIIllIl.currentSmallImage = SmallImage.Artik;
        lllllllllllllllllllllIllIIIIllIl.updateDetails();
        instance.Discord_UpdatePresence(rpc);
        instance.Discord_RunCallbacks();
    }

    private String getLine(Setting<String> lllllllllllllllllllllIllIIIIIIlI) {
        if (lllllllllllllllllllllIllIIIIIIlI.get().length() > 0) {
            DiscordPresence lllllllllllllllllllllIllIIIIIIll;
            return lllllllllllllllllllllIllIIIIIIlI.get().replace("{player}", lllllllllllllllllllllIllIIIIIIll.getName()).replace("{server}", lllllllllllllllllllllIllIIIIIIll.getServer());
        }
        return null;
    }

    private static final class SmallImage
    extends Enum<SmallImage> {
        private final /* synthetic */ String text;
        public static final /* synthetic */ /* enum */ SmallImage Artik;
        private final /* synthetic */ String key;
        private static final /* synthetic */ SmallImage[] $VALUES;

        SmallImage next() {
            return Artik;
        }

        void apply() {
            SmallImage llllllllllllllllllIlIIIIIIlIlIII;
            rpc.smallImageKey = llllllllllllllllllIlIIIIIIlIlIII.key;
            rpc.smallImageText = llllllllllllllllllIlIIIIIIlIlIII.text;
        }

        private SmallImage(String llllllllllllllllllIlIIIIIIllIIlI, String llllllllllllllllllIlIIIIIIllIIIl) {
            SmallImage llllllllllllllllllIlIIIIIIllIIll;
            llllllllllllllllllIlIIIIIIllIIll.key = llllllllllllllllllIlIIIIIIllIIlI;
            llllllllllllllllllIlIIIIIIllIIll.text = llllllllllllllllllIlIIIIIIllIIIl;
        }

        private static /* synthetic */ SmallImage[] $values() {
            return new SmallImage[]{Artik};
        }

        public static SmallImage valueOf(String llllllllllllllllllIlIIIIIlIIIIIl) {
            return Enum.valueOf(SmallImage.class, llllllllllllllllllIlIIIIIlIIIIIl);
        }

        public static SmallImage[] values() {
            return (SmallImage[])$VALUES.clone();
        }

        static {
            Artik = new SmallImage("artik", "Artik");
            $VALUES = SmallImage.$values();
        }
    }
}

