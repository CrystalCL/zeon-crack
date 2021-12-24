package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.combat.AnchorAura;
import meteordevelopment.meteorclient.systems.modules.combat.BedAura;
import meteordevelopment.meteorclient.systems.modules.combat.CrystalAura;
import meteordevelopment.meteorclient.systems.modules.combat.KillAura;
import meteordevelopment.meteorclient.utils.entity.EntityUtils;
import meteordevelopment.meteorclient.utils.misc.Placeholders;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class NewAutoEz extends Module {
    private final SettingGroup sgKills = settings.createGroup("Kills");
    private final SettingGroup sgTotemPops = settings.createGroup("Totem Pops");
    private final Setting<Boolean> kills = sgKills.add(new BoolSetting.Builder().name("enabled").description("Enables the kill messages.").defaultValue(true).build());
    private final Setting<Mode> killMode = sgKills.add(new EnumSetting.Builder<Mode>().name("mode").description("Determines what messages to use.").defaultValue(Mode.CrystalCL).build());
    private final Setting<MessageStyle> killMessageStyle = sgKills.add(new EnumSetting.Builder<MessageStyle>().name("style").description("Determines what message style to use.").defaultValue(MessageStyle.EZ).visible(() -> killMode.get() == Mode.CrystalCL).build());
    private final Setting<Boolean> killIgnoreFriends = sgKills.add(new BoolSetting.Builder().name("ignore-friends").description("Ignores friends.").defaultValue(true).build());
    private final Setting<List<String>> killMessages = sgKills.add(new StringListSetting.Builder().name("messages").description("Custom messages when you kill someone.").defaultValue(Arrays.asList("haha {killed_player} is a noob! EZZz", "I just raped {killed_player}!", "I just ended {killed_player}!", "I just EZZz'd {killed_player}!", "I just fucked {killed_player}!", "Take the L nerd {killed_player}! You just got ended!", "I just nae nae'd {killed_player}!", "I am too good for {killed_player}!")).visible(() -> killMode.get() == Mode.Custom).build());
    private final Setting<Boolean> totems = sgTotemPops.add(new BoolSetting.Builder().name("enabled").description("Enables the totem pop messages.").defaultValue(true).build());
    private final Setting<Integer> delay = sgTotemPops.add(new IntSetting.Builder().name("delay").description("Determines how often to send totem pop messages.").defaultValue(600).min(0).sliderRange(0, 600).build());
    private final Setting<Mode> totemMode = sgTotemPops.add(new EnumSetting.Builder<Mode>().name("mode").description("Determines what messages to use.").defaultValue(Mode.CrystalCL).build());
    private final Setting<Boolean> totemIgnoreFriends = sgTotemPops.add(new BoolSetting.Builder().name("ignore-friends").description("Ignores friends.").defaultValue(true).build());
    private final Setting<List<String>> totemMessages = sgTotemPops.add(new StringListSetting.Builder().name("messages").description("Custom messages when you pop someone.").defaultValue(Arrays.asList("{popped_player} just lost 1 totem thanks to my skill!", "I just ended {popped_player}'s totem!", "I just popped {popped_player}!", "I just easily popped {popped_player}!")).visible(() -> totemMode.get() == Mode.Custom).build());

    private int ticks;
    private boolean canSendPop;
    private final Random random = new Random();

    public NewAutoEz() {
        super(CrystalCL.Exc, "new-auto-ez", "Announces EASY when you kill or pop someone.");
    }

    @EventHandler
    public void onPacketReadMessage(PacketEvent.Receive event) {
        if (kills.get()) {
            if (!(killMessages.get()).isEmpty() || killMode.get() != Mode.Custom) {
                if (event.packet instanceof GameMessageS2CPacket) {
                    String msg = ((GameMessageS2CPacket) event.packet).getMessage().getString();
                    Iterator var3 = mc.world.getPlayers().iterator();


                    while (true) {
                        PlayerEntity player;
                        do {
                            do {
                                if (!var3.hasNext()) {
                                    return;
                                }

                                player = (PlayerEntity) var3.next();
                            } while (player == mc.player);

                            if (player.getName().getString().equals(mc.getSession().getUsername())) {
                                return;
                            }
                        } while (!msg.contains(player.getName().getString()));

                        String message;
                        if (!msg.contains("by " + mc.getSession().getUsername()) && !msg.contains("whilst fighting " + mc.getSession().getUsername()) && !msg.contains(mc.getSession().getUsername() + " sniped") && !msg.contains(mc.getSession().getUsername() + " annaly fucked") && !msg.contains(mc.getSession().getUsername() + " destroyed") && !msg.contains(mc.getSession().getUsername() + " killed") && !msg.contains(mc.getSession().getUsername() + " fucked") && !msg.contains(mc.getSession().getUsername() + " separated") && !msg.contains(mc.getSession().getUsername() + " punched") && !msg.contains(mc.getSession().getUsername() + " shoved")) {
                            if ((msg.contains("bed") || msg.contains("[Intentional Game Design]")) && Modules.get().isActive(BedAura.class)) {
                                if (mc.player.distanceTo(player) < 6.0F) {
                                    if (killIgnoreFriends.get() && Friends.get().isFriend(player)) {
                                        return;
                                    }

                                    if (EntityUtils.getGameMode(player).isCreative()) {
                                        return;
                                    }

                                    message = getMessageStyle();
                                    mc.player.sendChatMessage(Placeholders.apply(message).replace("{killed_player}", player.getName().getString()));
                                }
                            } else if ((msg.contains("bed") || msg.contains("[Intentional Game Design]")) && Modules.get().isActive(BedAuraPlus.class)) {
                                if (mc.player.distanceTo(player) < 6.0F) {
                                    if (killIgnoreFriends.get() && Friends.get().isFriend(player)) {
                                        return;
                                    }

                                    if (EntityUtils.getGameMode(player).isCreative()) {
                                        return;
                                    }

                                    message = getMessageStyle();
                                    mc.player.sendChatMessage(Placeholders.apply(message + "using BedAura+!").replace("{killed_player}", player.getName().getString()));
                                }
                            } else if ((msg.contains("anchor") || msg.contains("[Intentional Game Design]")) && Modules.get().isActive(AnchorAura.class) && mc.player.distanceTo(player) < 6.0F) {
                                if (killIgnoreFriends.get() && Friends.get().isFriend(player)) {
                                    return;
                                }

                                if (EntityUtils.getGameMode(player).isCreative()) {
                                    return;
                                }

                                message = getMessageStyle();
                                mc.player.sendChatMessage(Placeholders.apply(message).replace("{killed_player}", player.getName().getString()));
                            }
                        } else if (!msg.contains("end crystal") && !msg.contains("end-crystal")) {
                            if (Modules.get().isActive(KillAura.class)) {
                                if (mc.player.distanceTo(player) < 6.0F) {
                                    if (killIgnoreFriends.get() && Friends.get().isFriend(player)) {
                                        return;
                                    }

                                    if (EntityUtils.getGameMode(player).isCreative()) {
                                        return;
                                    }

                                    message = getMessageStyle();
                                    mc.player.sendChatMessage(Placeholders.apply(message).replace("{killed_player}", player.getName().getString()));
                                }
                            } else if (mc.player.distanceTo(player) < 8.0F) {
                                if (killIgnoreFriends.get() && Friends.get().isFriend(player)) {
                                    return;
                                }

                                if (EntityUtils.getGameMode(player).isCreative()) {
                                    return;
                                }

                                message = getMessageStyle();
                                mc.player.sendChatMessage(Placeholders.apply(message).replace("{killed_player}", player.getName().getString()));
                            }
                        } else if (Modules.get().isActive(CrystalAura.class)) {
                            if (mc.player.distanceTo(player) < 6.0F) {
                                if (killIgnoreFriends.get() && Friends.get().isFriend(player)) {
                                    return;
                                }

                                if (EntityUtils.getGameMode(player).isCreative()) {
                                    return;
                                }

                                message = getMessageStyle();
                                mc.player.sendChatMessage(Placeholders.apply(message).replace("{killed_player}", player.getName().getString()));
                            }
                        } else if (!msg.contains("end crystal") && !msg.contains("end-crystal")) {
                            if (Modules.get().isActive(CevBreakerTest.class)) {
                                if (mc.player.distanceTo(player) < 6.0F) {
                                    if (killIgnoreFriends.get() && Friends.get().isFriend(player)) {
                                        return;
                                    }

                                    if (EntityUtils.getGameMode(player).isCreative()) {
                                        return;
                                    }

                                    message = getMessageStyle();
                                    mc.player.sendChatMessage(Placeholders.apply(message + "using CevBreaker!").replace("{killed_player}", player.getName().getString()));
                                }
                            } else if (mc.player.distanceTo(player) < 7.0F) {
                                if (killIgnoreFriends.get() && Friends.get().isFriend(player)) {
                                    return;
                                }

                                if (EntityUtils.getGameMode(player).isCreative()) {
                                    return;
                                }

                                message = getMessageStyle();
                                mc.player.sendChatMessage(Placeholders.apply(message).replace("{killed_player}", player.getName().getString()));
                            }
                        } else if ((Modules.get().isActive(CustomCrystalAuraV2.class) || Modules.get().isActive(CustomCrystalAuraV1.class)) && mc.player.distanceTo(player) < 6.0F) {
                            if (killIgnoreFriends.get() && Friends.get().isFriend(player)) {
                                return;
                            }

                            if (EntityUtils.getGameMode(player).isCreative()) {
                                return;
                            }

                            message = getMessageStyle();
                            mc.player.sendChatMessage(Placeholders.apply(message + "using CrystalAura+!").replace("{killed_player}", player.getName().getString()));
                        }
                    }
                }
            }
        }
    }

    public String getMessageStyle() {
        String var10000;
        switch (killMode.get()) {
            case CrystalCL:
                switch (killMessageStyle.get()) {
                    case EZ -> {
                        var10000 = getMessage().get(random.nextInt(getMessage().size()));
                        return var10000;
                    }
                    case GG -> {
                        var10000 = getGGMessage().get(random.nextInt(getGGMessage().size()));
                        return var10000;
                    }
                    default -> throw new IncompatibleClassChangeError();
                }
            case Custom:
                var10000 = (killMessages.get()).get(random.nextInt((killMessages.get()).size()));
                return var10000;
            default:
                throw new IncompatibleClassChangeError();
        }
    }

    private static List<String> getMessage() {
        return Arrays.asList("{killed_player} just got raped by CrysatlCL!", "{killed_player} just got ended by CrystalCL+!", "haha {killed_player} is a noob! CrystalCL+ on top!", "I just EZZz'd {killed_player} using CrystalCL+!", "I just fucked {killed_player} using CrystalCL+!", "Take the L nerd {killed_player}! You just got ended by CrystalCL+!", "I just nae nae'd {killed_player} using CrystalCL+!", "I am too good for {killed_player}! CrystalCL+ on top!", "Cope harder bro, {killed_player}! by CrystalCL+!");
    }

    private static List<String> getGGMessage() {
        return Arrays.asList("GG {killed_player}! CrystalCL+ is so op!", "Nice fight {killed_player}! I really enjoyed it!", "Close fight {killed_player}, but i won!", "Good fight, {killed_player}! CrystalCL+ on top!");
    }

    @EventHandler
    private void onReceivePacket(PacketEvent.Receive event) {
        if (totems.get()) {
            if (!(totemMessages.get()).isEmpty() || totemMode.get() != Mode.Custom) {
                if (event.packet instanceof EntityStatusS2CPacket) {
                    EntityStatusS2CPacket packet = (EntityStatusS2CPacket) event.packet;
                    if (packet.getStatus() == 35) {
                        Entity entity = packet.getEntity(mc.world);
                        if (entity instanceof PlayerEntity) {
                            if (entity != mc.player) {
                                if (!(mc.player.distanceTo(entity) > 8.0F)) {
                                    if (!Friends.get().isFriend((PlayerEntity) entity) || !totemIgnoreFriends.get()) {
                                        if (!EntityUtils.getGameMode(mc.player).isCreative()) {
                                            if (canSendPop) {
                                                String message = getTotemMessageStyle();
                                                mc.player.sendChatMessage(Placeholders.apply(message).replace("{popped_player}", entity.getName().getString()));
                                                canSendPop = false;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void onActivate() {
        canSendPop = true;
        ticks = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (ticks > delay.get()) {
            canSendPop = true;
            ticks = 0;
        }

        if (!canSendPop) {
            ticks++;
        }
    }

    public String getTotemMessageStyle() {
        String var10000 = switch ((Mode) totemMode.get()) {
            case CrystalCL -> getTotemMessage().get(random.nextInt(getTotemMessage().size()));
            case Custom -> (totemMessages.get()).get(random.nextInt((totemMessages.get()).size()));
        };

        return var10000;
    }

    private static List<String> getTotemMessage() {
        return Arrays.asList("{popped_player} just got popped by CrystalCL+!", "Keep popping {popped_player}! CrystalCL+ owns you!", "{popped_player}'s totem just got ended by CrystalCL+!", "{popped_player} just lost 1 totem thanks to CrystalCL+!", "I just easily popped {popped_player} using CrystalCL+!", "Ez pop, {popped_player}!");
    }

    public enum Mode {
        CrystalCL,
        Custom
    }

    public enum MessageStyle {
        EZ,
        GG
    }
}
