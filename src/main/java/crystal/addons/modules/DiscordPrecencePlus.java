package crystal.addons.modules;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.orbit.EventHandler;

public class DiscordPrecencePlus extends Module {
    private static final DiscordRichPresence rpc = new DiscordRichPresence();
    private static final DiscordRPC instance;
    private SmallImage currentSmallImage;
    private int ticks;

    public DiscordPrecencePlus() {
        super(CrystalCL.PvE, "discord-RPC", "Displays a RPC for you on Discord to show that you're playing Crystal Client!");
    }

    public void onActivate() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        instance.Discord_Initialize("874271141584310313", handlers, true, null);
        rpc.startTimestamp = System.currentTimeMillis() / 1000L;
        rpc.largeImageKey = "crystal";
        String largeText = "CrystalCL Addon v0.3";
        rpc.largeImageText = largeText;
        currentSmallImage = SmallImage.Artik;
        updateDetails();
        instance.Discord_UpdatePresence(rpc);
        instance.Discord_RunCallbacks();
    }

    public void onDeactivate() {
        instance.Discord_ClearPresence();
        instance.Discord_Shutdown();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (Utils.canUpdate()) {
            ticks++;
            if (ticks >= 200) {
                currentSmallImage = currentSmallImage.next();
                currentSmallImage.apply();
                instance.Discord_UpdatePresence(rpc);
                ticks = 0;
            }

            updateDetails();
            instance.Discord_RunCallbacks();
        }
    }

    private void updateDetails() {
        if (isActive() && Utils.canUpdate()) {
            rpc.details = "https://discord.gg/chJNFZzTgq";
            rpc.state = "Crystal Client 0n t0p!";
            instance.Discord_UpdatePresence(rpc);
        }

    }

    static {
        instance = DiscordRPC.INSTANCE;
    }

    private static enum SmallImage {
        Artik("artik", "Artik");

        private final String key;
        private final String text;

        private SmallImage(String key, String text) {
            this.key = key;
            this.text = text;
        }

        void apply() {
            DiscordPrecencePlus.rpc.smallImageKey = key;
            DiscordPrecencePlus.rpc.smallImageText = text;
        }

        SmallImage next() {
            return Artik;
        }


        private static SmallImage[] $values() {
            return new SmallImage[]{Artik};
        }
    }
}
