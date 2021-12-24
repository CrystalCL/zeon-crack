package crystal.addons;

import crystal.addons.commands.Armor;
import crystal.addons.commands.Count;
import crystal.addons.elytra.bot.ElytraBaritone;
import crystal.addons.modules.*;
import crystal.addons.modules.hud.client.IconHud;
import crystal.addons.modules.hud.client.WatermarkHud;
import crystal.addons.modules.hud.client.WelcomeHud;
import crystal.addons.modules.hud.items.CrystalHud;
import crystal.addons.modules.hud.items.EXPHud;
import crystal.addons.modules.hud.items.ObsidianHud;
import crystal.addons.modules.hud.items.TotemHud;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixin.MinecraftClientAccessor;
import meteordevelopment.meteorclient.systems.commands.Commands;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.render.hud.HUD;
import meteordevelopment.orbit.EventHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public class CrystalCL extends MeteorAddon {
    public static final Category Exc = new Category("Combat+", Items.END_CRYSTAL.getDefaultStack());
    public static final Category PvE = new Category("Misc+", Items.GRASS_BLOCK.getDefaultStack());
    public static final String VERSION = "0.3";
    public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(), "meteor-client");
    boolean NEED_LOGIN = false;

    @EventHandler(priority = 500)
    private void AutoLogin(PacketEvent.Receive e) {
        if (e.packet instanceof GameMessageS2CPacket && (Modules.get().get(AutoLogin.class)).isActive()) {
            String s = ((GameMessageS2CPacket)e.packet).getMessage().getString();
            if (s.contains("/captcha ")) {
                Pattern p = Pattern.compile("/captcha \\w+");
                Matcher m = p.matcher(s);
                m.find();
                mc.player.sendChatMessage(m.group());
            }

            if (s.toLowerCase().contains("/login ")) {
                NEED_LOGIN = true;
            }
        }
    }

    @EventHandler
    private void onTick1(TickEvent.Post e) {
        if (NEED_LOGIN && mc.currentScreen == null && MinecraftClientAccessor.getFps() >= 59) {
            NEED_LOGIN = false;
            Modules.get().get(AutoLogin.class).login();
        }
    }

    @EventHandler
    private void AutoLoginCOMMANDS(PacketEvent.Send e) {
        if (e.packet instanceof ChatMessageC2SPacket) {
            String s = ((ChatMessageC2SPacket)e.packet).getChatMessage();
            if (s.equalsIgnoreCase("/LOGIN") || s.equalsIgnoreCase("/L")) {
                e.cancel();
                Modules.get().get(AutoLogin.class).login();
            }
        }
    }

    public void onInitialize() {
        MeteorClient.EVENT_BUS.registerLambdaFactory("crystal.addons", (lookupInMethod, klass) -> (Lookup)lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
        Modules.get().add(new ActionLogger());
        Modules.get().add(new AntiCrystal());
        Modules.get().add(new AntiGhost());
        Modules.get().add(new AntiSetHome());
        Modules.get().add(new AutoCrystalHead());
        Modules.get().add(new AutoEz());
        Modules.get().add(new AutoLeave());
        Modules.get().add(new BedAuraPlus());
        Modules.get().add(new ButtonTrap());
        Modules.get().add(new CevBreaker());
        Modules.get().add(new CevBreakerTest());
        Modules.get().add(new CityExploit());
        Modules.get().add(new CustomAutoTotem());
        Modules.get().add(new CustomCrystalAuraV1());
        Modules.get().add(new CustomCrystalAuraV2());
        Modules.get().add(new DiscordPrecencePlus());
        Modules.get().add(new ExplosionProtector());
        Modules.get().add(new ExtraNuker());
        Modules.get().add(new ExtraSurround());
        Modules.get().add(new FastBreak());
        Modules.get().add(new FireWorksAura());
        Modules.get().add(new HoleFillerPlus());
        Modules.get().add(new InstaAutoCity());
        Modules.get().add(new ItemShrader());
        Modules.get().add(new LiquidFiller2());
        Modules.get().add(new NewChunks());
        Modules.get().add(new NewAutoEz());
        Modules.get().add(new NotiferPlus());
        Modules.get().add(new PortalGodMode());
        Modules.get().add(new Spinner());
        Modules.get().add(new Strafe());
        Modules.get().add(new TntTrap());
        Modules.get().add(new TunnelESP());
        Modules.get().add(new MultiTask());
        Modules.get().add(new ElytraBaritone());
        Commands.get().add(new Armor());
        Commands.get().add(new Count());
        HUD hud = Modules.get().get(HUD.class);
        hud.elements.add(new IconHud(hud));
        hud.elements.add(new CrystalHud(hud));
        hud.elements.add(new EXPHud(hud));
        hud.elements.add(new ObsidianHud(hud));
        hud.elements.add(new TotemHud(hud));
        hud.elements.add(new WatermarkHud(hud));
        hud.elements.add(new WelcomeHud(hud));
    }

    public void onRegisterCategories() {
        Modules.registerCategory(Exc);
        Modules.registerCategory(PvE);
    }
}
