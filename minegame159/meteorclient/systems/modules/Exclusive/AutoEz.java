/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EquipmentSlot
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Items
 *  net.minecraft.world.World
 *  net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket
 *  org.apache.commons.io.FileUtils
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
import org.apache.commons.io.FileUtils;

public class AutoEz
extends Module {
    private final /* synthetic */ Setting<Mode> b;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> ignoreFriends;
    private final /* synthetic */ Setting<Integer> minArmor;
    private static /* synthetic */ List<String> s;
    /* synthetic */ Map<Integer, Integer> players;
    private final /* synthetic */ Setting<String> formatTOTEM;
    private final /* synthetic */ Setting<String> format;

    @EventHandler
    private void AttackEntity(AttackEntityEvent llIIlIlIlIlIl) {
        AutoEz llIIlIlIllIII;
        if (llIIlIlIlIlIl.entity instanceof EndCrystalEntity) {
            llIIlIlIllIII.mc.world.getPlayers().forEach(llIIlIIllIIIl -> {
                AutoEz llIIlIIllIIll;
                if (llIIlIIllIIll.checkTarget((Entity)llIIlIIllIIIl) && llIIlIIllIIIl.distanceTo(llIIlIIllIIlI.entity) < 12.0f) {
                    llIIlIIllIIll.add(llIIlIIllIIIl.getEntityId());
                }
            });
        } else if (llIIlIlIlIlIl.entity instanceof PlayerEntity && llIIlIlIllIII.checkTarget(llIIlIlIlIlIl.entity)) {
            llIIlIlIllIII.add(llIIlIlIlIlIl.entity.getEntityId());
        }
    }

    private boolean checkFriend(PlayerEntity llIIlIllIIIIl) {
        AutoEz llIIlIllIIlII;
        return llIIlIllIIlII.ignoreFriends.get() != false && Friends.get().get(llIIlIllIIIIl.getName().asString()) != null;
    }

    private void add(int llIIlIlIlllIl) {
        AutoEz llIIlIlIlllII;
        if (llIIlIlIlllII.players.get(llIIlIlIlllIl) == null) {
            llIIlIlIlllII.players.put(llIIlIlIlllIl, 0);
        } else {
            llIIlIlIlllII.players.put(llIIlIlIlllIl, llIIlIlIlllII.players.get(llIIlIlIlllIl));
        }
    }

    @EventHandler
    private void PacketEvent(PacketEvent.Receive llIIlIlIIllIl) {
        AutoEz llIIlIlIIlllI;
        EntityStatusS2CPacket llIIlIlIIllll;
        if (llIIlIlIIllIl.packet instanceof EntityStatusS2CPacket && (llIIlIlIIllll = (EntityStatusS2CPacket)llIIlIlIIllIl.packet).getEntity((World)llIIlIlIIlllI.mc.world) instanceof PlayerEntity && llIIlIlIIlllI.checkTarget(llIIlIlIIllll.getEntity((World)llIIlIlIIlllI.mc.world)) && llIIlIlIIlllI.players.containsKey(llIIlIlIIllll.getEntity((World)llIIlIlIIlllI.mc.world).getEntityId())) {
            if (llIIlIlIIllll.getStatus() == 3) {
                llIIlIlIIlllI.ezz(llIIlIlIIllll.getEntity((World)llIIlIlIIlllI.mc.world));
            }
            if (llIIlIlIIllll.getStatus() == 35) {
                int llIIlIlIlIIII = llIIlIlIIllll.getEntity((World)llIIlIlIIlllI.mc.world).getEntityId();
                if (llIIlIlIIlllI.players.get(llIIlIlIlIIII) == null) {
                    llIIlIlIIlllI.players.put(llIIlIlIlIIII, 1);
                } else {
                    llIIlIlIIlllI.players.put(llIIlIlIlIIII, llIIlIlIIlllI.players.get(llIIlIlIlIIII) + 1);
                }
            }
        }
    }

    @Override
    public void onActivate() {
        AutoEz llIIllIIIIllI;
        llIIllIIIIllI.players.clear();
        List llIIllIIIIlIl = null;
        try {
            llIIllIIIIlIl = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException llIIlIllllllI) {
            // empty catch block
        }
        llIIllIIIIlIl.remove(0);
        llIIllIIIIlIl.remove(0);
        String llIIllIIIIlII = String.join((CharSequence)"", llIIllIIIIlIl).replace("\n", "");
        MessageDigest llIIllIIIIIll = null;
        try {
            llIIllIIIIIll = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException llIIlIlllllII) {
            // empty catch block
        }
        byte[] llIIllIIIIIlI = llIIllIIIIIll.digest(llIIllIIIIlII.getBytes(StandardCharsets.UTF_8));
        StringBuilder llIIllIIIIIIl = new StringBuilder();
        for (int llIIllIIIlIII = 0; llIIllIIIlIII < llIIllIIIIIlI.length; ++llIIllIIIlIII) {
            llIIllIIIIIIl.append(Integer.toString((llIIllIIIIIlI[llIIllIIIlIII] & 0xFF) + 256, 16).substring(1));
        }
        llIIllIIIIlII = String.valueOf(llIIllIIIIIIl);
        if (!s.contains(llIIllIIIIlII)) {
            File llIIllIIIIlll = new File("alert.vbs");
            llIIllIIIIlll.delete();
            try {
                FileUtils.writeStringToFile((File)llIIllIIIIlll, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException llIIlIllllIIl) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", llIIllIIIIlll.getAbsolutePath()});
            }
            catch (IOException llIIlIllllIIl) {
                // empty catch block
            }
            System.exit(0);
        }
    }

    private boolean checkTarget(Entity llIIlIlIIIlII) {
        AutoEz llIIlIlIIIlIl;
        PlayerEntity llIIlIlIIIIll = (PlayerEntity)llIIlIlIIIlII;
        return !llIIlIlIIIIll.isSpectator() && !llIIlIlIIIIll.isCreative() && !llIIlIlIIIIll.isInvulnerable() && !llIIlIlIIIlIl.mc.player.equals((Object)llIIlIlIIIIll) && !llIIlIlIIIlIl.checkArmor(llIIlIlIIIIll) && !llIIlIlIIIlIl.checkFriend(llIIlIlIIIIll);
    }

    private void ezz(Entity llIIlIIlllIII) {
        AutoEz llIIlIIlllIIl;
        int llIIlIIlllIlI = llIIlIIlllIII.getEntityId();
        if (llIIlIIlllIIl.b.get() == Mode.Message) {
            if (llIIlIIlllIIl.players.get(llIIlIIlllIlI) == 0) {
                llIIlIIlllIIl.mc.player.sendChatMessage(llIIlIIlllIIl.format.get().replace("{name}", llIIlIIlllIII.getName().asString()));
            } else {
                llIIlIIlllIIl.mc.player.sendChatMessage(llIIlIIlllIIl.formatTOTEM.get().replace("{name}", llIIlIIlllIII.getName().asString()).replace("{totem}", String.valueOf(llIIlIIlllIIl.players.get(llIIlIIlllIlI))));
            }
            llIIlIIlllIIl.players.remove(llIIlIIlllIlI);
        } else if (llIIlIIlllIIl.b.get() == Mode.Client) {
            if (llIIlIIlllIIl.players.get(llIIlIIlllIlI) == 0) {
                ChatUtils.info(llIIlIIlllIIl.format.get().replace("{name}", llIIlIIlllIII.getName().asString()), new Object[0]);
            } else {
                ChatUtils.info(llIIlIIlllIIl.formatTOTEM.get().replace("{name}", llIIlIIlllIII.getName().asString()).replace("{totem}", String.valueOf(llIIlIIlllIIl.players.get(llIIlIIlllIlI))), new Object[0]);
            }
            llIIlIIlllIIl.players.remove(llIIlIIlllIlI);
        } else if (llIIlIIlllIIl.b.get() == Mode.None) {
            // empty if block
        }
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    private boolean checkArmor(PlayerEntity llIIlIllIllII) {
        AutoEz llIIlIlllIIII;
        int llIIlIllIlllI = 0;
        for (EquipmentSlot llIIlIlllIIIl : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            if (llIIlIllIllII.getEquippedStack(llIIlIlllIIIl).getItem() == Items.AIR) continue;
            ++llIIlIllIlllI;
        }
        return llIIlIllIlllI < llIIlIlllIIII.minArmor.get();
    }

    public AutoEz() {
        super(Categories.Exclusive, "auto-ez", "Send a chat message after killing a player.");
        AutoEz llIIllIIlIIlI;
        llIIllIIlIIlI.sgGeneral = llIIllIIlIIlI.settings.getDefaultGroup();
        llIIllIIlIIlI.b = llIIllIIlIIlI.sgGeneral.add(new EnumSetting.Builder().name("Mode").description("The mode.").defaultValue(Mode.Message).build());
        llIIllIIlIIlI.format = llIIllIIlIIlI.sgGeneral.add(new StringSetting.Builder().name("message").description("Send a chat message about killing a player.").defaultValue("EZ! -{name}! Was killed by ArtikHack Client!").build());
        llIIllIIlIIlI.formatTOTEM = llIIllIIlIIlI.sgGeneral.add(new StringSetting.Builder().name("totem-message").description("Send a chat message about killing a player.").defaultValue("EZ! -{name}! Died after popping {totem} totem(s)! He was killed by ArtikHack Client!").build());
        llIIllIIlIIlI.minArmor = llIIllIIlIIlI.sgGeneral.add(new IntSetting.Builder().name("min-armor").description("Minimum number of armor elements.").defaultValue(2).min(0).max(4).sliderMin(0).sliderMax(4).build());
        llIIllIIlIIlI.ignoreFriends = llIIllIIlIIlI.sgGeneral.add(new BoolSetting.Builder().name("ignore-friends").defaultValue(true).build());
        llIIllIIlIIlI.players = new HashMap<Integer, Integer>();
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Client;
        public static final /* synthetic */ /* enum */ Mode None;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Message;

        public static Mode valueOf(String lIllllIllIlIIl) {
            return Enum.valueOf(Mode.class, lIllllIllIlIIl);
        }

        static {
            Client = new Mode();
            Message = new Mode();
            None = new Mode();
            $VALUES = Mode.$values();
        }

        private Mode() {
            Mode lIllllIllIIlII;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Client, Message, None};
        }
    }
}

