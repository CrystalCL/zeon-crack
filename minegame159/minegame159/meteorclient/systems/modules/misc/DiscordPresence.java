/*
 * Decompiled with CFR 0.151.
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
    private final Setting<String> line3;
    private int ticks;
    private static final DiscordRichPresence rpc = new DiscordRichPresence();
    private final SettingGroup sgGeneral;
    private SmallImage currentSmallImage;
    private final Setting<String> line1;
    private final Setting<String> line2;
    private static final DiscordRPC instance = DiscordRPC.INSTANCE;

    public DiscordPresence() {
        super(Categories.Misc, "discord-presence", "Displays a RPC for you on Discord to show that you're playing Meteor Client!");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.line1 = this.sgGeneral.add(new StringSetting.Builder().name("line-1").description("The text it displays on line 1 of the RPC.").defaultValue("{player} || {server}").onChanged(this::lambda$new$0).build());
        this.line2 = this.sgGeneral.add(new StringSetting.Builder().name("line-2").description("The text it displays on line 2 of the RPC.").defaultValue("ArtikHack 0n t0p!").onChanged(this::lambda$new$1).build());
        this.line3 = this.sgGeneral.add(new StringSetting.Builder().name("line-3").description("Kill count.").defaultValue("EZ! I got 18 kills!").onChanged(this::lambda$new$2).build());
    }

    private String getLine(Setting<String> setting) {
        if (setting.get().length() > 0) {
            return setting.get().replace("{player}", this.getName()).replace("{server}", this.getServer());
        }
        return null;
    }

    private String getName() {
        return this.mc.field_1724.method_7334().getName();
    }

    private String getServer() {
        if (this.mc.method_1542()) {
            return "SinglePlayer";
        }
        return Utils.getWorldName();
    }

    private void lambda$new$2(String string) {
        this.updateDetails();
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (!Utils.canUpdate()) {
            return;
        }
        ++this.ticks;
        if (this.ticks >= 200) {
            this.currentSmallImage = this.currentSmallImage.next();
            this.currentSmallImage.apply();
            instance.Discord_UpdatePresence(rpc);
            this.ticks = 0;
        }
        this.updateDetails();
        instance.Discord_RunCallbacks();
    }

    static DiscordRichPresence access$000() {
        return rpc;
    }

    private void updateDetails() {
        if (this.isActive() && Utils.canUpdate()) {
            DiscordPresence.rpc.details = this.getLine(this.line1);
            DiscordPresence.rpc.state = this.getLine(this.line2);
            DiscordPresence.rpc.state = this.getLine(this.line3);
            instance.Discord_UpdatePresence(rpc);
        }
    }

    private void lambda$new$0(String string) {
        this.updateDetails();
    }

    @Override
    public void onDeactivate() {
        instance.Discord_ClearPresence();
        instance.Discord_Shutdown();
    }

    private void lambda$new$1(String string) {
        this.updateDetails();
    }

    @Override
    public void onActivate() {
        DiscordEventHandlers discordEventHandlers = new DiscordEventHandlers();
        instance.Discord_Initialize("847813526659268628", discordEventHandlers, true, null);
        DiscordPresence.rpc.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPresence.rpc.largeImageKey = "artiklogo";
        String string = "ArtikHack Client v1.6";
        if (!Config.get().devBuild.isEmpty()) {
            string = String.valueOf(new StringBuilder().append(string).append(" Dev Build: ").append(Config.get().devBuild));
        }
        DiscordPresence.rpc.largeImageText = string;
        this.currentSmallImage = SmallImage.Artik;
        this.updateDetails();
        instance.Discord_UpdatePresence(rpc);
        instance.Discord_RunCallbacks();
    }

    private static final class SmallImage
    extends Enum<SmallImage> {
        private final String key;
        public static final /* enum */ SmallImage Artik = new SmallImage("artik", "Artik");
        private final String text;
        private static final SmallImage[] $VALUES = SmallImage.$values();

        private SmallImage(String string2, String string3) {
            this.key = string2;
            this.text = string3;
        }

        private static SmallImage[] $values() {
            return new SmallImage[]{Artik};
        }

        public static SmallImage valueOf(String string) {
            return Enum.valueOf(SmallImage.class, string);
        }

        SmallImage next() {
            return Artik;
        }

        void apply() {
            DiscordPresence.access$000().smallImageKey = this.key;
            DiscordPresence.access$000().smallImageText = this.text;
        }

        public static SmallImage[] values() {
            return (SmallImage[])$VALUES.clone();
        }
    }
}

