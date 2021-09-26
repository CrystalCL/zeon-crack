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
import java.util.List;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Exclusive.Ezz;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.AutoTotem;
import minegame159.meteorclient.systems.modules.combat.OffhandExtra;
import minegame159.meteorclient.systems.modules.misc.OffhandCrash;
import minegame159.meteorclient.systems.modules.player.AutoMend;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ItemConvertible;
import net.minecraft.world.World;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import org.apache.commons.io.FileUtils;

public class CustomAutoTotem
extends Module {
    private final SettingGroup sgDelay = this.settings.createGroup("Delay");
    private int ticks;
    private final Setting<Integer> tickDelay;
    private String totemCountString = "0";
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    private final Setting<Mode> takeTotemMode = this.sgDelay.add(new EnumSetting.Builder().name("take-mode").description("The take totem in your hand.").defaultValue(Mode.NoAntiCheat).build());

    private void InvTotem() {
        if (this.ticks >= this.tickDelay.get()) {
            Object object;
            this.ticks = 0;
            if (this.takeTotemMode.get() == Mode.ForAntiCheat) {
                object = this.mc.currentScreen;
                InvUtils.FindItemResult findItemResult = InvUtils.findItemWithCount(Items.TOTEM_OF_UNDYING);
                if (findItemResult.count > 0) {
                    int n = Ezz.invIndexToSlotId(findItemResult.slot);
                    this.MOVE_TOTEM(n);
                }
            }
            if (this.takeTotemMode.get() == Mode.NoAntiCheat) {
                object = InvUtils.findItemWithCount(Items.TOTEM_OF_UNDYING);
                if (object.count > 0) {
                    int n = Ezz.invIndexToSlotId(object.slot);
                    this.MOVE_TOTEM(n);
                }
            }
        } else {
            ++this.ticks;
        }
    }

    private void SET_TOTEM_COUNT() {
        InvUtils.FindItemResult findItemResult = InvUtils.findItemWithCount(Items.TOTEM_OF_UNDYING);
        this.totemCountString = Integer.toString(findItemResult.count);
    }

    @EventHandler(priority=0x7FFFFFFE)
    private void onTick(TickEvent.Pre pre) {
        Screen Screen2 = this.mc.currentScreen;
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        this.SET_TOTEM_COUNT();
        if (this.mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) {
            return;
        }
        if (Screen2 instanceof GenericContainerScreen) {
            this.CHTotem();
            return;
        }
        if (Screen2 instanceof ShulkerBoxScreen) {
            this.SHTotem();
            return;
        }
        if (Screen2 instanceof CraftingScreen) {
            this.CRTotem();
            return;
        }
        this.InvTotem();
    }

    @Override
    public String getInfoString() {
        return this.totemCountString;
    }

    public CustomAutoTotem() {
        super(Categories.Exclusive, "custom-auto-totem", "Custom auto totem.");
        this.tickDelay = this.sgDelay.add(new IntSetting.Builder().name("take-delay").description("Totem's take delay.").defaultValue(1).min(0).sliderMax(20).build());
    }

    private void ATotem() {
        AnvilScreenHandler AnvilScreenHandler2 = (AnvilScreenHandler)((AnvilScreen)this.mc.currentScreen).getScreenHandler();
        if (AnvilScreenHandler2 == null) {
            return;
        }
        int n = -1;
        for (int i = 0; i < AnvilScreenHandler2.slots.size(); ++i) {
            if (((Slot)AnvilScreenHandler2.slots.get(i)).getStack().getItem() != Items.TOTEM_OF_UNDYING) continue;
            n = i;
            break;
        }
        if (n > -1) {
            this.MOVE_TOTEM(n);
            ((Slot)AnvilScreenHandler2.slots.get(n)).setStack(new ItemStack((ItemConvertible)Items.AIR));
        }
    }

    @Override
    public void onActivate() {
        this.ticks = 0;
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
            if (0 <= 0) continue;
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
        if (Modules.get().get(AutoMend.class).isActive()) {
            Modules.get().get(AutoMend.class).toggle();
        }
        if (Modules.get().get(OffhandExtra.class).isActive()) {
            Modules.get().get(OffhandExtra.class).toggle();
        }
        if (Modules.get().get(AutoTotem.class).isActive()) {
            Modules.get().get(AutoTotem.class).toggle();
        }
        if (Modules.get().get(OffhandCrash.class).isActive()) {
            Modules.get().get(OffhandCrash.class).toggle();
        }
    }

    private void CHTotem() {
        GenericContainerScreenHandler GenericContainerScreenHandler2 = (GenericContainerScreenHandler)((GenericContainerScreen)this.mc.currentScreen).getScreenHandler();
        if (GenericContainerScreenHandler2 == null) {
            return;
        }
        int n = -1;
        for (int i = 0; i < GenericContainerScreenHandler2.slots.size(); ++i) {
            if (((Slot)GenericContainerScreenHandler2.slots.get(i)).getStack().getItem() != Items.TOTEM_OF_UNDYING) continue;
            n = i;
            break;
        }
        if (n > -1) {
            this.MOVE_TOTEM(n);
            ((Slot)GenericContainerScreenHandler2.slots.get(n)).setStack(new ItemStack((ItemConvertible)Items.AIR));
        }
    }

    private void MOVE_TOTEM(int n) {
        this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, n, 40, SlotActionType.SWAP, (PlayerEntity)this.mc.player);
    }

    private void CRTotem() {
        CraftingScreenHandler CraftingScreenHandler2 = (CraftingScreenHandler)((CraftingScreen)this.mc.currentScreen).getScreenHandler();
        if (CraftingScreenHandler2 == null) {
            return;
        }
        int n = -1;
        for (int i = 0; i < CraftingScreenHandler2.slots.size(); ++i) {
            if (((Slot)CraftingScreenHandler2.slots.get(i)).getStack().getItem() != Items.TOTEM_OF_UNDYING) continue;
            n = i;
            break;
        }
        if (n > -1) {
            this.MOVE_TOTEM(n);
            ((Slot)CraftingScreenHandler2.slots.get(n)).setStack(new ItemStack((ItemConvertible)Items.AIR));
        }
    }

    @EventHandler(priority=0x7FFFFFFF)
    private void POPS(PacketEvent.Receive receive) {
        if (receive.packet instanceof EntityStatusS2CPacket) {
            EntityStatusS2CPacket EntityStatusS2CPacket2 = (EntityStatusS2CPacket)receive.packet;
            if (EntityStatusS2CPacket2.getStatus() != 35) {
                return;
            }
            Entity Entity2 = EntityStatusS2CPacket2.getEntity((World)this.mc.world);
            if (Entity2 == null || !Entity2.equals((Object)this.mc.player)) {
                return;
            }
            if (this.mc.currentScreen instanceof GenericContainerScreen) {
                this.CHTotem();
            }
            if (this.mc.currentScreen instanceof ShulkerBoxScreen) {
                this.SHTotem();
            }
            if (this.mc.currentScreen instanceof CraftingScreen) {
                this.CRTotem();
            }
            if (this.mc.currentScreen instanceof AnvilScreen) {
                this.ATotem();
            }
        }
    }

    private void SHTotem() {
        ShulkerBoxScreenHandler ShulkerBoxScreenHandler2 = (ShulkerBoxScreenHandler)((ShulkerBoxScreen)this.mc.currentScreen).getScreenHandler();
        if (ShulkerBoxScreenHandler2 == null) {
            return;
        }
        int n = -1;
        for (int i = 0; i < ShulkerBoxScreenHandler2.slots.size(); ++i) {
            if (((Slot)ShulkerBoxScreenHandler2.slots.get(i)).getStack().getItem() != Items.TOTEM_OF_UNDYING) continue;
            n = i;
            break;
        }
        if (n > -1) {
            this.MOVE_TOTEM(n);
            ((Slot)ShulkerBoxScreenHandler2.slots.get(n)).setStack(new ItemStack((ItemConvertible)Items.AIR));
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode NoAntiCheat;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode ForAntiCheat;

        static {
            ForAntiCheat = new Mode();
            NoAntiCheat = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        private static Mode[] $values() {
            return new Mode[]{ForAntiCheat, NoAntiCheat};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

