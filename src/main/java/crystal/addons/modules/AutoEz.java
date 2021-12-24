package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.entity.player.AttackEntityEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

import java.util.HashMap;
import java.util.Map;

public class AutoEz extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Mode> b = sgGeneral.add(new EnumSetting.Builder<Mode>().name("Mode").description("The mode.").defaultValue(Mode.Message).build());
    private final Setting<String> format = sgGeneral.add(new StringSetting.Builder().name("message").description("Send a chat message about killing a player.").defaultValue("EZ! -{name}!").build());
    private final Setting<String> formatTOTEM = sgGeneral.add(new StringSetting.Builder().name("totem-message").description("Send a chat message about killing a player.").defaultValue("EZ! -{name}! Died after popping {totem} totem(s)!").build());
    private final Setting<Integer> minArmor = sgGeneral.add(new IntSetting.Builder().name("min-armor").description("Minimum number of armor elements.").defaultValue(2).min(0).max(4).sliderMin(0).sliderMax(4).build());
    private final Setting<Boolean> ignoreFriends = sgGeneral.add(new BoolSetting.Builder().name("ignore-friends").defaultValue(true).build());

    Map<Integer, Integer> players = new HashMap();

    public AutoEz() {
        super(CrystalCL.Exc, "auto-ez", "Send a chat message after killing a player.");
    }

    public void onActivate() {
        players.clear();
    }

    private boolean checkArmor(PlayerEntity p) {
        int armor = 0;
        EquipmentSlot[] var3 = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

        for (EquipmentSlot a : var3) {
            if (p.getEquippedStack(a).getItem() != Items.AIR) {
                armor++;
            }
        }

        return armor < minArmor.get();
    }

    private boolean checkFriend(PlayerEntity p) {
        return ignoreFriends.get() && Friends.get().get(p.getName().asString()) != null;
    }

    private void add(int a) {
        if (players.get(a) == null) {
            players.put(a, 0);
        } else {
            players.put(a, players.get(a));
        }
    }

    @EventHandler
    private void AttackEntity(AttackEntityEvent e) {
        if (e.entity instanceof EndCrystalEntity) {
            mc.world.getPlayers().forEach((p) -> {
                if (checkTarget(p) && p.distanceTo(e.entity) < 12.0F) {
                    add(p.getId());
                }
            });
        } else if (e.entity instanceof PlayerEntity && checkTarget(e.entity)) {
            add(e.entity.getId());
        }
    }

    @EventHandler
    private void PacketEvent(PacketEvent.Receive e) {
        if (e.packet instanceof EntityStatusS2CPacket p) {
            if (p.getEntity(mc.world) instanceof PlayerEntity && checkTarget(p.getEntity(mc.world)) && players.containsKey(p.getEntity(mc.world).getId())) {
                if (p.getStatus() == 3) {
                    ezz(p.getEntity(mc.world));
                }

                if (p.getStatus() == 35) {
                    int id = p.getEntity(mc.world).getId();
                    players.merge(id, 1, Integer::sum);
                }
            }
        }
    }

    private boolean checkTarget(Entity a) {
        PlayerEntity p = (PlayerEntity) a;
        return !p.isSpectator() && !p.isCreative() && !p.isInvulnerable() && !mc.player.equals(p) && !checkArmor(p) && !checkFriend(p);
    }

    private void ezz(Entity e) {
        Object feat = " feat. CrystalCl+ ";
        int id = e.getId();
        if (b.get() == Mode.Message) {
            ClientPlayerEntity var10000;
            String var10001;
            if (players.get(id) == 0) {
                var10000 = mc.player;
                var10001 = (format.get()).replace("{name}", e.getName().asString());
                var10000.sendChatMessage(var10001 + feat);
            } else {
                var10000 = mc.player;
                var10001 = (formatTOTEM.get()).replace("{name}", e.getName().asString()).replace("{totem}", String.valueOf(players.get(id)));
                var10000.sendChatMessage(var10001 + feat);
            }

            players.remove(id);
        } else if (b.get() == Mode.Client) {
            if (players.get(id) == 0) {
                ChatUtils.info((format.get()).replace("{name}", e.getName().asString()) + feat);
            } else {
                String var4 = (formatTOTEM.get()).replace("{name}", e.getName().asString()).replace("{totem}", String.valueOf(players.get(id)));
                ChatUtils.info(var4 + feat);
            }

            players.remove(id);
        }
    }

    public enum Mode {
        Client,
        Message,
        None
    }
}
