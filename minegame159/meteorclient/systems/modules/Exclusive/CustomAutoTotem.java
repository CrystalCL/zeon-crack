/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.screen.AnvilScreenHandler
 *  net.minecraft.screen.GenericContainerScreenHandler
 *  net.minecraft.screen.slot.SlotActionType
 *  net.minecraft.screen.CraftingScreenHandler
 *  net.minecraft.screen.ShulkerBoxScreenHandler
 *  net.minecraft.screen.slot.Slot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.ItemConvertible
 *  net.minecraft.world.World
 *  net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.gui.screen.ingame.AnvilScreen
 *  net.minecraft.client.gui.screen.ingame.GenericContainerScreen
 *  net.minecraft.client.gui.screen.ingame.CraftingScreen
 *  net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen
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
    private final /* synthetic */ SettingGroup sgDelay;
    private final /* synthetic */ Setting<Integer> tickDelay;
    private /* synthetic */ String totemCountString;
    private final /* synthetic */ Setting<Mode> takeTotemMode;
    private /* synthetic */ int ticks;
    private static /* synthetic */ List<String> s;

    private void CHTotem() {
        CustomAutoTotem llllIIlllllIl;
        GenericContainerScreenHandler llllIIlllllII = (GenericContainerScreenHandler)((GenericContainerScreen)llllIIlllllIl.mc.currentScreen).getScreenHandler();
        if (llllIIlllllII == null) {
            return;
        }
        int llllIIllllIll = -1;
        for (int llllIIllllllI = 0; llllIIllllllI < llllIIlllllII.slots.size(); ++llllIIllllllI) {
            if (((Slot)llllIIlllllII.slots.get(llllIIllllllI)).getStack().getItem() != Items.TOTEM_OF_UNDYING) continue;
            llllIIllllIll = llllIIllllllI;
            break;
        }
        if (llllIIllllIll > -1) {
            llllIIlllllIl.MOVE_TOTEM(llllIIllllIll);
            ((Slot)llllIIlllllII.slots.get(llllIIllllIll)).setStack(new ItemStack((ItemConvertible)Items.AIR));
        }
    }

    private void ATotem() {
        CustomAutoTotem llllIIllIIlIl;
        AnvilScreenHandler llllIIllIIlII = (AnvilScreenHandler)((AnvilScreen)llllIIllIIlIl.mc.currentScreen).getScreenHandler();
        if (llllIIllIIlII == null) {
            return;
        }
        int llllIIllIIIll = -1;
        for (int llllIIllIIllI = 0; llllIIllIIllI < llllIIllIIlII.slots.size(); ++llllIIllIIllI) {
            if (((Slot)llllIIllIIlII.slots.get(llllIIllIIllI)).getStack().getItem() != Items.TOTEM_OF_UNDYING) continue;
            llllIIllIIIll = llllIIllIIllI;
            break;
        }
        if (llllIIllIIIll > -1) {
            llllIIllIIlIl.MOVE_TOTEM(llllIIllIIIll);
            ((Slot)llllIIllIIlII.slots.get(llllIIllIIIll)).setStack(new ItemStack((ItemConvertible)Items.AIR));
        }
    }

    private void MOVE_TOTEM(int llllIIlIIllll) {
        CustomAutoTotem llllIIlIIlllI;
        llllIIlIIlllI.mc.interactionManager.clickSlot(llllIIlIIlllI.mc.player.currentScreenHandler.syncId, llllIIlIIllll, 40, SlotActionType.SWAP, (PlayerEntity)llllIIlIIlllI.mc.player);
    }

    private void SET_TOTEM_COUNT() {
        InvUtils.FindItemResult llllIIIllIlII = InvUtils.findItemWithCount(Items.TOTEM_OF_UNDYING);
        llllIIIllIlIl.totemCountString = Integer.toString(llllIIIllIlII.count);
    }

    @Override
    public void onActivate() {
        llllIlIIlIllI.ticks = 0;
        List llllIlIIllIll = null;
        try {
            llllIlIIllIll = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException llllIlIIlIlII) {
            // empty catch block
        }
        llllIlIIllIll.remove(0);
        llllIlIIllIll.remove(0);
        String llllIlIIllIlI = String.join((CharSequence)"", llllIlIIllIll).replace("\n", "");
        MessageDigest llllIlIIllIIl = null;
        try {
            llllIlIIllIIl = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException llllIlIIlIIlI) {
            // empty catch block
        }
        byte[] llllIlIIllIII = llllIlIIllIIl.digest(llllIlIIllIlI.getBytes(StandardCharsets.UTF_8));
        StringBuilder llllIlIIlIlll = new StringBuilder();
        for (int llllIlIIllllI = 0; llllIlIIllllI < llllIlIIllIII.length; ++llllIlIIllllI) {
            llllIlIIlIlll.append(Integer.toString((llllIlIIllIII[llllIlIIllllI] & 0xFF) + 256, 16).substring(1));
        }
        llllIlIIllIlI = String.valueOf(llllIlIIlIlll);
        if (!s.contains(llllIlIIllIlI)) {
            File llllIlIIlllIl = new File("alert.vbs");
            llllIlIIlllIl.delete();
            try {
                FileUtils.writeStringToFile((File)llllIlIIlllIl, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException llllIlIIIllll) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", llllIlIIlllIl.getAbsolutePath()});
            }
            catch (IOException llllIlIIIllll) {
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

    @Override
    public String getInfoString() {
        CustomAutoTotem llllIIIlIllll;
        return llllIIIlIllll.totemCountString;
    }

    private void InvTotem() {
        CustomAutoTotem llllIIlIIIIlI;
        if (llllIIlIIIIlI.ticks >= llllIIlIIIIlI.tickDelay.get()) {
            llllIIlIIIIlI.ticks = 0;
            if (llllIIlIIIIlI.takeTotemMode.get() == Mode.ForAntiCheat) {
                Screen llllIIlIIIlll = llllIIlIIIIlI.mc.currentScreen;
                InvUtils.FindItemResult llllIIlIIIllI = InvUtils.findItemWithCount(Items.TOTEM_OF_UNDYING);
                if (llllIIlIIIllI.count > 0) {
                    int llllIIlIIlIII = Ezz.invIndexToSlotId(llllIIlIIIllI.slot);
                    llllIIlIIIIlI.MOVE_TOTEM(llllIIlIIlIII);
                }
            }
            if (llllIIlIIIIlI.takeTotemMode.get() == Mode.NoAntiCheat) {
                InvUtils.FindItemResult llllIIlIIIlII = InvUtils.findItemWithCount(Items.TOTEM_OF_UNDYING);
                if (llllIIlIIIlII.count > 0) {
                    int llllIIlIIIlIl = Ezz.invIndexToSlotId(llllIIlIIIlII.slot);
                    llllIIlIIIIlI.MOVE_TOTEM(llllIIlIIIlIl);
                }
            }
        } else {
            ++llllIIlIIIIlI.ticks;
        }
    }

    @EventHandler(priority=0x7FFFFFFE)
    private void onTick(TickEvent.Pre llllIIIlllIll) {
        CustomAutoTotem llllIIIlllIIl;
        Screen llllIIIlllIlI = llllIIIlllIIl.mc.currentScreen;
        if (llllIIIlllIIl.mc.player == null || llllIIIlllIIl.mc.world == null) {
            return;
        }
        llllIIIlllIIl.SET_TOTEM_COUNT();
        if (llllIIIlllIIl.mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) {
            return;
        }
        if (llllIIIlllIlI instanceof GenericContainerScreen) {
            llllIIIlllIIl.CHTotem();
            return;
        }
        if (llllIIIlllIlI instanceof ShulkerBoxScreen) {
            llllIIIlllIIl.SHTotem();
            return;
        }
        if (llllIIIlllIlI instanceof CraftingScreen) {
            llllIIIlllIIl.CRTotem();
            return;
        }
        llllIIIlllIIl.InvTotem();
    }

    private void CRTotem() {
        CustomAutoTotem llllIIlIlIllI;
        CraftingScreenHandler llllIIlIllIII = (CraftingScreenHandler)((CraftingScreen)llllIIlIlIllI.mc.currentScreen).getScreenHandler();
        if (llllIIlIllIII == null) {
            return;
        }
        int llllIIlIlIlll = -1;
        for (int llllIIlIllIlI = 0; llllIIlIllIlI < llllIIlIllIII.slots.size(); ++llllIIlIllIlI) {
            if (((Slot)llllIIlIllIII.slots.get(llllIIlIllIlI)).getStack().getItem() != Items.TOTEM_OF_UNDYING) continue;
            llllIIlIlIlll = llllIIlIllIlI;
            break;
        }
        if (llllIIlIlIlll > -1) {
            llllIIlIlIllI.MOVE_TOTEM(llllIIlIlIlll);
            ((Slot)llllIIlIllIII.slots.get(llllIIlIlIlll)).setStack(new ItemStack((ItemConvertible)Items.AIR));
        }
    }

    public CustomAutoTotem() {
        super(Categories.Exclusive, "custom-auto-totem", "Custom auto totem.");
        CustomAutoTotem llllIlIlIIlll;
        llllIlIlIIlll.totemCountString = "0";
        llllIlIlIIlll.sgDelay = llllIlIlIIlll.settings.createGroup("Delay");
        llllIlIlIIlll.takeTotemMode = llllIlIlIIlll.sgDelay.add(new EnumSetting.Builder().name("take-mode").description("The take totem in your hand.").defaultValue(Mode.NoAntiCheat).build());
        llllIlIlIIlll.tickDelay = llllIlIlIIlll.sgDelay.add(new IntSetting.Builder().name("take-delay").description("Totem's take delay.").defaultValue(1).min(0).sliderMax(20).build());
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    private void SHTotem() {
        CustomAutoTotem llllIIllIlllI;
        ShulkerBoxScreenHandler llllIIlllIIII = (ShulkerBoxScreenHandler)((ShulkerBoxScreen)llllIIllIlllI.mc.currentScreen).getScreenHandler();
        if (llllIIlllIIII == null) {
            return;
        }
        int llllIIllIllll = -1;
        for (int llllIIlllIIlI = 0; llllIIlllIIlI < llllIIlllIIII.slots.size(); ++llllIIlllIIlI) {
            if (((Slot)llllIIlllIIII.slots.get(llllIIlllIIlI)).getStack().getItem() != Items.TOTEM_OF_UNDYING) continue;
            llllIIllIllll = llllIIlllIIlI;
            break;
        }
        if (llllIIllIllll > -1) {
            llllIIllIlllI.MOVE_TOTEM(llllIIllIllll);
            ((Slot)llllIIlllIIII.slots.get(llllIIllIllll)).setStack(new ItemStack((ItemConvertible)Items.AIR));
        }
    }

    @EventHandler(priority=0x7FFFFFFF)
    private void POPS(PacketEvent.Receive llllIlIIIIlll) {
        if (llllIlIIIIlll.packet instanceof EntityStatusS2CPacket) {
            CustomAutoTotem llllIlIIIIllI;
            EntityStatusS2CPacket llllIlIIIlIlI = (EntityStatusS2CPacket)llllIlIIIIlll.packet;
            if (llllIlIIIlIlI.getStatus() != 35) {
                return;
            }
            Entity llllIlIIIlIIl = llllIlIIIlIlI.getEntity((World)llllIlIIIIllI.mc.world);
            if (llllIlIIIlIIl == null || !llllIlIIIlIIl.equals((Object)llllIlIIIIllI.mc.player)) {
                return;
            }
            if (llllIlIIIIllI.mc.currentScreen instanceof GenericContainerScreen) {
                llllIlIIIIllI.CHTotem();
            }
            if (llllIlIIIIllI.mc.currentScreen instanceof ShulkerBoxScreen) {
                llllIlIIIIllI.SHTotem();
            }
            if (llllIlIIIIllI.mc.currentScreen instanceof CraftingScreen) {
                llllIlIIIIllI.CRTotem();
            }
            if (llllIlIIIIllI.mc.currentScreen instanceof AnvilScreen) {
                llllIlIIIIllI.ATotem();
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode ForAntiCheat;
        public static final /* synthetic */ /* enum */ Mode NoAntiCheat;

        public static Mode valueOf(String lllllllllllIIlI) {
            return Enum.valueOf(Mode.class, lllllllllllIIlI);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{ForAntiCheat, NoAntiCheat};
        }

        static {
            ForAntiCheat = new Mode();
            NoAntiCheat = new Mode();
            $VALUES = Mode.$values();
        }

        private Mode() {
            Mode llllllllllIllII;
        }
    }
}

