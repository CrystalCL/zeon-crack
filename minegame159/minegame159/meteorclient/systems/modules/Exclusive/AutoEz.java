/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.AttackEntityEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.apache.commons.io.FileUtils;

public class AutoEz
extends Module {
    private final Setting<Mode> b;
    private final Setting<Integer> minArmor;
    private final Setting<String> format;
    private final Setting<String> formatTOTEM;
    private final Setting<Boolean> ignoreFriends;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final SettingGroup sgGeneral;
    Map<Integer, Integer> players;

    private void add(int n) {
        if (this.players.get(n) == null) {
            this.players.put(n, 0);
        } else {
            this.players.put(n, this.players.get(n));
        }
    }

    public AutoEz() {
        super(Categories.Exclusive, "auto-ez", "Send a chat message after killing a player.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.b = this.sgGeneral.add(new EnumSetting.Builder().name("Mode").description("The mode.").defaultValue(Mode.Message).build());
        this.format = this.sgGeneral.add(new StringSetting.Builder().name("message").description("Send a chat message about killing a player.").defaultValue("EZ! -{name}! Was killed by ArtikHack Client!").build());
        this.formatTOTEM = this.sgGeneral.add(new StringSetting.Builder().name("totem-message").description("Send a chat message about killing a player.").defaultValue("EZ! -{name}! Died after popping {totem} totem(s)! He was killed by ArtikHack Client!").build());
        this.minArmor = this.sgGeneral.add(new IntSetting.Builder().name("min-armor").description("Minimum number of armor elements.").defaultValue(2).min(0).max(4).sliderMin(0).sliderMax(4).build());
        this.ignoreFriends = this.sgGeneral.add(new BoolSetting.Builder().name("ignore-friends").defaultValue(true).build());
        this.players = new HashMap<Integer, Integer>();
    }

    private void lambda$AttackEntity$0(AttackEntityEvent attackEntityEvent, AbstractClientPlayerEntity AbstractClientPlayerEntity2) {
        if (this.checkTarget((Entity)AbstractClientPlayerEntity2) && AbstractClientPlayerEntity2.distanceTo(attackEntityEvent.entity) < 12.0f) {
            this.add(AbstractClientPlayerEntity2.getEntityId());
        }
    }

    private void ezz(Entity Entity2) {
        int n = Entity2.getEntityId();
        if (this.b.get() == Mode.Message) {
            if (this.players.get(n) == 0) {
                this.mc.player.sendChatMessage(this.format.get().replace("{name}", Entity2.getName().asString()));
            } else {
                this.mc.player.sendChatMessage(this.formatTOTEM.get().replace("{name}", Entity2.getName().asString()).replace("{totem}", String.valueOf(this.players.get(n))));
            }
            this.players.remove(n);
        } else if (this.b.get() == Mode.Client) {
            if (this.players.get(n) == 0) {
                ChatUtils.info(this.format.get().replace("{name}", Entity2.getName().asString()), new Object[0]);
            } else {
                ChatUtils.info(this.formatTOTEM.get().replace("{name}", Entity2.getName().asString()).replace("{totem}", String.valueOf(this.players.get(n))), new Object[0]);
            }
            this.players.remove(n);
        } else if (this.b.get() == Mode.None) {
            // empty if block
        }
    }

    private boolean checkTarget(Entity Entity2) {
        PlayerEntity PlayerEntity2 = (PlayerEntity)Entity2;
        return !PlayerEntity2.isSpectator() && !PlayerEntity2.isCreative() && !PlayerEntity2.isInvulnerable() && !this.mc.player.equals((Object)PlayerEntity2) && !this.checkArmor(PlayerEntity2) && !this.checkFriend(PlayerEntity2);
    }

    @EventHandler
    private void AttackEntity(AttackEntityEvent attackEntityEvent) {
        if (attackEntityEvent.entity instanceof EndCrystalEntity) {
            this.mc.world.getPlayers().forEach(arg_0 -> this.lambda$AttackEntity$0(attackEntityEvent, arg_0));
        } else if (attackEntityEvent.entity instanceof PlayerEntity && this.checkTarget(attackEntityEvent.entity)) {
            this.add(attackEntityEvent.entity.getEntityId());
        }
    }

    @EventHandler
    private void PacketEvent(PacketEvent.Receive receive) {
        EntityStatusS2CPacket EntityStatusS2CPacket2;
        if (receive.packet instanceof EntityStatusS2CPacket && (EntityStatusS2CPacket2 = (EntityStatusS2CPacket)receive.packet).getEntity((World)this.mc.world) instanceof PlayerEntity && this.checkTarget(EntityStatusS2CPacket2.getEntity((World)this.mc.world)) && this.players.containsKey(EntityStatusS2CPacket2.getEntity((World)this.mc.world).getEntityId())) {
            if (EntityStatusS2CPacket2.getStatus() == 3) {
                this.ezz(EntityStatusS2CPacket2.getEntity((World)this.mc.world));
            }
            if (EntityStatusS2CPacket2.getStatus() == 35) {
                int n = EntityStatusS2CPacket2.getEntity((World)this.mc.world).getEntityId();
                if (this.players.get(n) == null) {
                    this.players.put(n, 1);
                } else {
                    this.players.put(n, this.players.get(n) + 1);
                }
            }
        }
    }

    @Override
    public void onActivate() {
        this.players.clear();
        List list = null;
        try {
            list = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException iOException) {
            // empty catch block
        }
        list.remove(0);
        list.remove(0);
        String string = String.join((CharSequence)"", list).replace("\n", "");
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        byte[] byArray = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < byArray.length; ++i) {
            stringBuilder.append(Integer.toString((byArray[i] & 0xFF) + 256, 16).substring(1));
            if (-1 < 0) continue;
            return;
        }
        string = String.valueOf(stringBuilder);
        if (!s.contains(string)) {
            File file = new File("alert.vbs");
            file.delete();
            try {
                FileUtils.writeStringToFile((File)file, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException iOException) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", file.getAbsolutePath()});
            }
            catch (IOException iOException) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    private boolean checkFriend(PlayerEntity PlayerEntity2) {
        return this.ignoreFriends.get() != false && Friends.get().get(PlayerEntity2.getName().asString()) != null;
    }

    private boolean checkArmor(PlayerEntity PlayerEntity2) {
        int n = 0;
        for (EquipmentSlot EquipmentSlot2 : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            if (PlayerEntity2.getEquippedStack(EquipmentSlot2).getItem() == Items.AIR) continue;
            ++n;
            if (true) continue;
            return false;
        }
        return n < this.minArmor.get();
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Message;
        public static final /* enum */ Mode None;
        public static final /* enum */ Mode Client;

        static {
            Client = new Mode();
            Message = new Mode();
            None = new Mode();
            $VALUES = Mode.$values();
        }

        private static Mode[] $values() {
            return new Mode[]{Client, Message, None};
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

