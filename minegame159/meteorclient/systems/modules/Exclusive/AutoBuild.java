/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.network.Packet
 *  net.minecraft.block.BlockState
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionOnly
 *  net.minecraft.util.math.MathHelper
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
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import net.minecraft.util.Hand;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.io.FileUtils;

public class AutoBuild
extends Module {
    private final /* synthetic */ Setting<Boolean> center;
    private /* synthetic */ int North;
    private final /* synthetic */ Mutable blockPos;
    private static /* synthetic */ String[] BUILD;
    private final /* synthetic */ Setting<Boolean> selfToggle;
    private /* synthetic */ boolean sentMessage;
    private /* synthetic */ int South;
    private static /* synthetic */ List<String> s;
    private /* synthetic */ int prevSlot;
    private /* synthetic */ int East;
    private /* synthetic */ int West;
    private /* synthetic */ int YawCheck;
    private /* synthetic */ boolean return_;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Build> buildPlacement;

    private boolean findSlot() {
        AutoBuild lIIllIllIlII;
        lIIllIllIlII.prevSlot = lIIllIllIlII.mc.player.inventory.selectedSlot;
        for (int lIIllIllIlIl = 0; lIIllIllIlIl < 9; ++lIIllIllIlIl) {
            Item lIIllIllIllI = lIIllIllIlII.mc.player.inventory.getStack(lIIllIllIlIl).getItem();
            if (!(lIIllIllIllI instanceof BlockItem) || lIIllIllIllI != Items.OBSIDIAN) continue;
            lIIllIllIlII.mc.player.inventory.selectedSlot = lIIllIllIlIl;
            return true;
        }
        return false;
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
        BUILD = new String[4];
        AutoBuild.BUILD[0] = "[NoMadHut]";
        AutoBuild.BUILD[1] = "[Penis]";
        AutoBuild.BUILD[2] = "[Swastika]";
    }

    private void setBlockPos(int lIIllIllllII, int lIIllIllllll, int lIIllIlllIlI) {
        AutoBuild lIIllIllllIl;
        lIIllIllllIl.blockPos.set(lIIllIllllIl.mc.player.getX() + (double)lIIllIllllII, lIIllIllllIl.mc.player.getY() + (double)lIIllIllllll, lIIllIllllIl.mc.player.getZ() + (double)lIIllIlllIlI);
    }

    @EventHandler
    private void onTick(TickEvent.Post lIlIIIIIllII) {
        AutoBuild lIlIIIIIlIIl;
        int lIlIIIIIlIll = -1;
        for (int lIllIIIIlIll = 0; lIllIIIIlIll < 9; ++lIllIIIIlIll) {
            if (lIlIIIIIlIIl.mc.player.inventory.getStack(lIllIIIIlIll).getItem() != Blocks.OBSIDIAN.asItem()) continue;
            lIlIIIIIlIll = lIllIIIIlIll;
            break;
        }
        if (lIlIIIIIlIll == -1 && lIlIIIIIlIIl.selfToggle.get().booleanValue()) {
            if (!lIlIIIIIlIIl.sentMessage) {
                ChatUtils.moduleError(lIlIIIIIlIIl, "No obsidian found\u2026 disabling.", new Object[0]);
                lIlIIIIIlIIl.sentMessage = true;
            }
            lIlIIIIIlIIl.toggle();
            return;
        }
        if (lIlIIIIIlIll == -1) {
            return;
        }
        int lIlIIIIIlIlI = lIlIIIIIlIIl.mc.player.inventory.selectedSlot;
        switch (lIlIIIIIlIIl.buildPlacement.get()) {
            case NomadHut: {
                lIlIIIIIlIIl.mc.player.inventory.selectedSlot = lIlIIIIIlIll;
                lIlIIIIIlIIl.return_ = false;
                if (lIlIIIIIlIIl.East > 0) {
                    ChatUtils.moduleInfo(lIlIIIIIlIIl, "Building the NoMadHut", new Object[0]);
                    boolean lIllIIIIlIlI = lIlIIIIIlIIl.place(2, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIllIIIIlIIl = lIlIIIIIlIIl.place(2, 0, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIllIIIIlIII = lIlIIIIIlIIl.place(1, 0, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIllIIIIIlll = lIlIIIIIlIIl.place(0, 0, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIllIIIIIllI = lIlIIIIIlIIl.place(-1, 0, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIllIIIIIlIl = lIlIIIIIlIIl.place(-2, 0, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIllIIIIIlII = lIlIIIIIlIIl.place(-2, 0, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIllIIIIIIll = lIlIIIIIlIIl.place(-2, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIllIIIIIIlI = lIlIIIIIlIIl.place(-1, 0, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIllIIIIIIIl = lIlIIIIIlIIl.place(0, 0, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIllIIIIIIII = lIlIIIIIlIIl.place(1, 0, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllllllll = lIlIIIIIlIIl.place(2, 1, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllllllI = lIlIIIIIlIIl.place(2, 1, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllllllIl = lIlIIIIIlIIl.place(1, 1, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllllllII = lIlIIIIIlIIl.place(-1, 1, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllllIll = lIlIIIIIlIIl.place(-2, 1, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllllIlI = lIlIIIIIlIIl.place(-2, 1, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllllIIl = lIlIIIIIlIIl.place(-1, 1, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllllIII = lIlIIIIIlIIl.place(1, 1, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllllIlll = lIlIIIIIlIIl.place(2, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllllIllI = lIlIIIIIlIIl.place(2, 2, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllllIlIl = lIlIIIIIlIIl.place(2, 2, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllllIlII = lIlIIIIIlIIl.place(1, 2, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllllIIll = lIlIIIIIlIIl.place(0, 2, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllllIIlI = lIlIIIIIlIIl.place(-1, 2, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllllIIIl = lIlIIIIIlIIl.place(-2, 2, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllllIIII = lIlIIIIIlIIl.place(-2, 2, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIllll = lIlIIIIIlIIl.place(-2, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIlllI = lIlIIIIIlIIl.place(-1, 2, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIllIl = lIlIIIIIlIIl.place(0, 2, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIllII = lIlIIIIIlIIl.place(1, 2, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIlIll = lIlIIIIIlIIl.place(1, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIlIlI = lIlIIIIIlIIl.place(1, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIlIIl = lIlIIIIIlIIl.place(1, 3, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIlIII = lIlIIIIIlIIl.place(0, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIIlll = lIlIIIIIlIIl.place(0, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIIllI = lIlIIIIIlIIl.place(0, 3, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIIlIl = lIlIIIIIlIIl.place(-1, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIIlII = lIlIIIIIlIIl.place(-1, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIIIll = lIlIIIIIlIIl.place(-1, 3, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    lIlIIIIIlIIl.East = 0;
                    lIlIIIIIlIIl.YawCheck = 0;
                    if (lIllIIIIlIlI && lIllIIIIlIIl && lIllIIIIlIII && lIllIIIIIlll && lIllIIIIIllI && lIllIIIIIlIl && lIllIIIIIlII && lIllIIIIIIll && lIllIIIIIIlI && lIllIIIIIIIl && lIllIIIIIIII && lIlIllllllll && lIlIlllllllI && lIlIllllllIl && lIlIllllllII && lIlIlllllIll && lIlIlllllIlI && lIlIlllllIIl && lIlIlllllIII && lIlIllllIlll && lIlIllllIllI && lIlIllllIlIl && lIlIllllIlII && lIlIllllIIll && lIlIllllIIlI && lIlIllllIIIl && lIlIllllIIII && lIlIlllIllll && lIlIlllIlllI && lIlIlllIllIl && lIlIlllIllII && lIlIlllIlIll && lIlIlllIlIlI && lIlIlllIlIIl && lIlIlllIlIII && lIlIlllIIlll && lIlIlllIIllI && lIlIlllIIlIl && lIlIlllIIlII && lIlIlllIIIll) {
                        lIlIIIIIlIIl.toggle();
                        break;
                    }
                    return;
                }
                if (lIlIIIIIlIIl.North > 0) {
                    ChatUtils.moduleInfo(lIlIIIIIlIIl, "Building the NoMadHut", new Object[0]);
                    boolean lIlIlllIIIlI = lIlIIIIIlIIl.place(2, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIIIIl = lIlIIIIIlIIl.place(2, 0, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlllIIIII = lIlIIIIIlIIl.place(2, 0, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIlllll = lIlIIIIIlIIl.place(1, 0, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIllllI = lIlIIIIIlIIl.place(0, 0, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIlllIl = lIlIIIIIlIIl.place(-1, 0, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIlllII = lIlIIIIIlIIl.place(-2, 0, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIllIll = lIlIIIIIlIIl.place(-2, 0, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIllIlI = lIlIIIIIlIIl.place(-2, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIllIIl = lIlIIIIIlIIl.place(-1, 0, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIllIII = lIlIIIIIlIIl.place(1, 0, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIlIlll = lIlIIIIIlIIl.place(2, 1, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIlIllI = lIlIIIIIlIIl.place(2, 1, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIlIlIl = lIlIIIIIlIIl.place(1, 1, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIlIlII = lIlIIIIIlIIl.place(-1, 1, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIlIIll = lIlIIIIIlIIl.place(-2, 1, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIlIIlI = lIlIIIIIlIIl.place(-2, 1, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIlIIIl = lIlIIIIIlIIl.place(-1, 1, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIlIIII = lIlIIIIIlIIl.place(1, 1, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIllll = lIlIIIIIlIIl.place(2, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIlllI = lIlIIIIIlIIl.place(2, 2, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIllIl = lIlIIIIIlIIl.place(2, 2, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIllII = lIlIIIIIlIIl.place(1, 2, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIlIll = lIlIIIIIlIIl.place(0, 2, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIlIlI = lIlIIIIIlIIl.place(-1, 2, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIlIIl = lIlIIIIIlIIl.place(-2, 2, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIlIII = lIlIIIIIlIIl.place(-2, 2, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIIlll = lIlIIIIIlIIl.place(-2, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIIllI = lIlIIIIIlIIl.place(-1, 2, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIIlIl = lIlIIIIIlIIl.place(0, 2, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIIlII = lIlIIIIIlIIl.place(1, 2, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIIIll = lIlIIIIIlIIl.place(1, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIIIlI = lIlIIIIIlIIl.place(1, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIIIIl = lIlIIIIIlIIl.place(1, 3, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIllIIIIII = lIlIIIIIlIIl.place(0, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIllllll = lIlIIIIIlIIl.place(0, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlllllI = lIlIIIIIlIIl.place(0, 3, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIllllIl = lIlIIIIIlIIl.place(-1, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIllllII = lIlIIIIIlIIl.place(-1, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlllIll = lIlIIIIIlIIl.place(-1, 3, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    lIlIIIIIlIIl.North = 0;
                    lIlIIIIIlIIl.YawCheck = 0;
                    if (lIlIlllIIIlI && lIlIlllIIIIl && lIlIlllIIIII && lIlIllIlllll && lIlIllIllllI && lIlIllIlllIl && lIlIllIlllII && lIlIllIllIll && lIlIllIllIlI && lIlIllIllIIl && lIlIllIllIII && lIlIllIlIlll && lIlIllIlIllI && lIlIllIlIlIl && lIlIllIlIlII && lIlIllIlIIll && lIlIllIlIIlI && lIlIllIlIIIl && lIlIllIlIIII && lIlIllIIllll && lIlIllIIlllI && lIlIllIIllIl && lIlIllIIllII && lIlIllIIlIll && lIlIllIIlIlI && lIlIllIIlIIl && lIlIllIIlIII && lIlIllIIIlll && lIlIllIIIllI && lIlIllIIIlIl && lIlIllIIIlII && lIlIllIIIIll && lIlIllIIIIlI && lIlIllIIIIIl && lIlIllIIIIII && lIlIlIllllll && lIlIlIlllllI && lIlIlIllllIl && lIlIlIllllII && lIlIlIlllIll) {
                        lIlIIIIIlIIl.toggle();
                        break;
                    }
                    return;
                }
                if (lIlIIIIIlIIl.West > 0) {
                    ChatUtils.moduleInfo(lIlIIIIIlIIl, "Building the NoMadHut", new Object[0]);
                    boolean lIlIlIlllIlI = lIlIIIIIlIIl.place(2, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlllIIl = lIlIIIIIlIIl.place(2, 0, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlllIII = lIlIIIIIlIIl.place(2, 0, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIllIlll = lIlIIIIIlIIl.place(1, 0, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIllIllI = lIlIIIIIlIIl.place(0, 0, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIllIlIl = lIlIIIIIlIIl.place(-1, 0, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIllIlII = lIlIIIIIlIIl.place(-2, 0, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIllIIll = lIlIIIIIlIIl.place(-2, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIllIIlI = lIlIIIIIlIIl.place(-1, 0, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIllIIIl = lIlIIIIIlIIl.place(1, 0, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIllIIII = lIlIIIIIlIIl.place(1, 0, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIllll = lIlIIIIIlIIl.place(2, 1, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIlllI = lIlIIIIIlIIl.place(2, 1, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIllIl = lIlIIIIIlIIl.place(1, 1, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIllII = lIlIIIIIlIIl.place(-1, 1, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIlIll = lIlIIIIIlIIl.place(-2, 1, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIlIlI = lIlIIIIIlIIl.place(-2, 1, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIlIIl = lIlIIIIIlIIl.place(-1, 1, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIlIII = lIlIIIIIlIIl.place(0, 1, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIIlll = lIlIIIIIlIIl.place(1, 1, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIIllI = lIlIIIIIlIIl.place(2, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIIlIl = lIlIIIIIlIIl.place(2, 2, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIIlII = lIlIIIIIlIIl.place(2, 2, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIIIll = lIlIIIIIlIIl.place(1, 2, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIIIlI = lIlIIIIIlIIl.place(0, 2, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIIIIl = lIlIIIIIlIIl.place(-1, 2, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIlIIIII = lIlIIIIIlIIl.place(-2, 2, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIlllll = lIlIIIIIlIIl.place(-2, 2, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIllllI = lIlIIIIIlIIl.place(-2, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIlllIl = lIlIIIIIlIIl.place(-1, 2, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIlllII = lIlIIIIIlIIl.place(0, 2, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIllIll = lIlIIIIIlIIl.place(1, 2, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIllIlI = lIlIIIIIlIIl.place(1, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIllIIl = lIlIIIIIlIIl.place(1, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIllIII = lIlIIIIIlIIl.place(1, 3, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIlIlll = lIlIIIIIlIIl.place(0, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIlIllI = lIlIIIIIlIIl.place(0, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIlIlIl = lIlIIIIIlIIl.place(0, 3, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIlIlII = lIlIIIIIlIIl.place(-1, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIlIIll = lIlIIIIIlIIl.place(-1, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIlIIlIIlI = lIlIIIIIlIIl.place(-1, 3, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    lIlIIIIIlIIl.West = 0;
                    lIlIIIIIlIIl.YawCheck = 0;
                    if (lIlIlIlllIlI && lIlIlIlllIIl && lIlIlIlllIII && lIlIlIllIlll && lIlIlIllIllI && lIlIlIllIlIl && lIlIlIllIlII && lIlIlIllIIll && lIlIlIllIIlI && lIlIlIllIIIl && lIlIlIllIIII && lIlIlIlIllll && lIlIlIlIlllI && lIlIlIlIllIl && lIlIlIlIllII && lIlIlIlIlIll && lIlIlIlIlIlI && lIlIlIlIlIIl && lIlIlIlIIlll && lIlIlIlIIllI && lIlIlIlIIlIl && lIlIlIlIIlII && lIlIlIlIIIll && lIlIlIlIIIlI && lIlIlIlIIIIl && lIlIlIlIIIII && lIlIlIIlllll && lIlIlIIllllI && lIlIlIIlllIl && lIlIlIIlllII && lIlIlIIllIll && lIlIlIIllIlI && lIlIlIIllIIl && lIlIlIIllIII && lIlIlIIlIlll && lIlIlIIlIllI && lIlIlIIlIlIl && lIlIlIIlIlII && lIlIlIIlIIll && lIlIlIIlIIlI && lIlIlIlIlIII) {
                        lIlIIIIIlIIl.toggle();
                        break;
                    }
                    return;
                }
                if (lIlIIIIIlIIl.South <= 0) break;
                ChatUtils.moduleInfo(lIlIIIIIlIIl, "Building the NoMadHut", new Object[0]);
                boolean lIlIlIIlIIIl = lIlIIIIIlIIl.place(2, 0, -1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIlIIII = lIlIIIIIlIIl.place(2, 0, 0);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIllll = lIlIIIIIlIIl.place(2, 0, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIlllI = lIlIIIIIlIIl.place(1, 0, 2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIllIl = lIlIIIIIlIIl.place(-1, 0, 2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIllII = lIlIIIIIlIIl.place(-2, 0, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIlIll = lIlIIIIIlIIl.place(-2, 0, 0);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIlIlI = lIlIIIIIlIIl.place(-2, 0, -1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIlIIl = lIlIIIIIlIIl.place(-1, 0, -2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIlIII = lIlIIIIIlIIl.place(0, 0, -2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIIlll = lIlIIIIIlIIl.place(1, 0, -2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIIllI = lIlIIIIIlIIl.place(2, 1, -1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIIlIl = lIlIIIIIlIIl.place(2, 1, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIIlII = lIlIIIIIlIIl.place(1, 1, 2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIIIll = lIlIIIIIlIIl.place(-1, 1, 2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIIIlI = lIlIIIIIlIIl.place(-2, 1, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIIIIl = lIlIIIIIlIIl.place(-2, 1, -1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIlIIIIIII = lIlIIIIIlIIl.place(-1, 1, -2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlllllll = lIlIIIIIlIIl.place(1, 1, -2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIllllllI = lIlIIIIIlIIl.place(2, 2, -1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlllllIl = lIlIIIIIlIIl.place(2, 2, 0);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlllllII = lIlIIIIIlIIl.place(2, 2, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIllllIll = lIlIIIIIlIIl.place(1, 2, 2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIllllIlI = lIlIIIIIlIIl.place(0, 2, 2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIllllIIl = lIlIIIIIlIIl.place(-1, 2, 2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIllllIII = lIlIIIIIlIIl.place(-2, 2, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlllIlll = lIlIIIIIlIIl.place(-2, 2, 0);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlllIllI = lIlIIIIIlIIl.place(-2, 2, -1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlllIlIl = lIlIIIIIlIIl.place(-1, 2, -2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlllIlII = lIlIIIIIlIIl.place(0, 2, -2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlllIIll = lIlIIIIIlIIl.place(1, 2, -2);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlllIIlI = lIlIIIIIlIIl.place(1, 3, -1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlllIIIl = lIlIIIIIlIIl.place(1, 3, 0);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlllIIII = lIlIIIIIlIIl.place(1, 3, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIllIllll = lIlIIIIIlIIl.place(0, 3, -1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIllIlllI = lIlIIIIIlIIl.place(0, 3, 0);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIllIllIl = lIlIIIIIlIIl.place(0, 3, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIllIllII = lIlIIIIIlIIl.place(-1, 3, -1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIllIlIll = lIlIIIIIlIIl.place(-1, 3, 0);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIllIlIlI = lIlIIIIIlIIl.place(-1, 3, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                lIlIIIIIlIIl.South = 0;
                lIlIIIIIlIIl.YawCheck = 0;
                if (lIlIlIIlIIIl && lIlIlIIlIIII && lIlIlIIIllll && lIlIlIIIlllI && lIlIlIIIllIl && lIlIlIIIllII && lIlIlIIIlIll && lIlIlIIIlIlI && lIlIlIIIlIIl && lIlIlIIIlIII && lIlIlIIIIlll && lIlIlIIIIllI && lIlIlIIIIlIl && lIlIlIIIIlII && lIlIlIIIIIll && lIlIlIIIIIlI && lIlIlIIIIIIl && lIlIlIIIIIII && lIlIIlllllll && lIlIIllllllI && lIlIIlllllIl && lIlIIlllllII && lIlIIllllIll && lIlIIllllIlI && lIlIIllllIIl && lIlIIllllIII && lIlIIlllIlll && lIlIIlllIllI && lIlIIlllIlIl && lIlIIlllIlII && lIlIIlllIIll && lIlIIlllIIlI && lIlIIlllIIIl && lIlIIlllIIII && lIlIIllIllll && lIlIIllIlllI && lIlIIllIllIl && lIlIIllIllII && lIlIIllIlIll && lIlIIllIlIlI) {
                    lIlIIIIIlIIl.toggle();
                    break;
                }
                return;
            }
            case Penis: {
                lIlIIIIIlIIl.mc.player.inventory.selectedSlot = lIlIIIIIlIll;
                lIlIIIIIlIIl.return_ = false;
                if (lIlIIIIIlIIl.East > 0) {
                    ChatUtils.moduleInfo(lIlIIIIIlIIl, "Building the Penis", new Object[0]);
                    boolean lIlIIllIlIIl = lIlIIIIIlIIl.place(1, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIllIlIII = lIlIIIIIlIIl.place(1, 0, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIllIIlll = lIlIIIIIlIIl.place(1, 0, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIllIIllI = lIlIIIIIlIIl.place(1, 1, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIllIIlIl = lIlIIIIIlIIl.place(1, 2, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIllIIlII = lIlIIIIIlIIl.place(1, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    lIlIIIIIlIIl.East = 0;
                    lIlIIIIIlIIl.YawCheck = 0;
                    if (lIlIIllIlIIl && lIlIIllIlIII && lIlIIllIIlll && lIlIIllIIllI && lIlIIllIIlIl && lIlIIllIIlII) {
                        lIlIIIIIlIIl.toggle();
                        break;
                    }
                    return;
                }
                if (lIlIIIIIlIIl.North > 0) {
                    ChatUtils.moduleInfo(lIlIIIIIlIIl, "Building the Penis", new Object[0]);
                    boolean lIlIIllIIIll = lIlIIIIIlIIl.place(-1, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIllIIIlI = lIlIIIIIlIIl.place(0, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIllIIIIl = lIlIIIIIlIIl.place(1, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIllIIIII = lIlIIIIIlIIl.place(0, 1, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIlllll = lIlIIIIIlIIl.place(0, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIllllI = lIlIIIIIlIIl.place(0, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    lIlIIIIIlIIl.North = 0;
                    lIlIIIIIlIIl.YawCheck = 0;
                    if (lIlIIllIIIll && lIlIIllIIIlI && lIlIIllIIIIl && lIlIIllIIIII && lIlIIlIlllll && lIlIIlIllllI) {
                        lIlIIIIIlIIl.toggle();
                        break;
                    }
                    return;
                }
                if (lIlIIIIIlIIl.West > 0) {
                    ChatUtils.moduleInfo(lIlIIIIIlIIl, "Building the Penis", new Object[0]);
                    boolean lIlIIlIlllIl = lIlIIIIIlIIl.place(-1, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIlllII = lIlIIIIIlIIl.place(-1, 0, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIllIll = lIlIIIIIlIIl.place(-1, 0, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIllIlI = lIlIIIIIlIIl.place(-1, 1, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIllIIl = lIlIIIIIlIIl.place(-1, 2, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIllIII = lIlIIIIIlIIl.place(-1, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    lIlIIIIIlIIl.West = 0;
                    lIlIIIIIlIIl.YawCheck = 0;
                    if (lIlIIlIlllIl && lIlIIlIlllII && lIlIIlIllIll && lIlIIlIllIlI && lIlIIlIllIIl && lIlIIlIllIII) {
                        lIlIIIIIlIIl.toggle();
                        break;
                    }
                    return;
                }
                if (lIlIIIIIlIIl.South <= 0) break;
                ChatUtils.moduleInfo(lIlIIIIIlIIl, "Building the Penis", new Object[0]);
                boolean lIlIIlIlIlll = lIlIIIIIlIIl.place(-1, 0, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlIlIllI = lIlIIIIIlIIl.place(0, 0, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlIlIlIl = lIlIIIIIlIIl.place(1, 0, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlIlIlII = lIlIIIIIlIIl.place(0, 1, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlIlIIll = lIlIIIIIlIIl.place(0, 2, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIlIlIIlI = lIlIIIIIlIIl.place(0, 3, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                lIlIIIIIlIIl.South = 0;
                lIlIIIIIlIIl.YawCheck = 0;
                if (lIlIIlIlIlll && lIlIIlIlIllI && lIlIIlIlIlIl && lIlIIlIlIlII && lIlIIlIlIIll && lIlIIlIlIIlI) {
                    lIlIIIIIlIIl.toggle();
                    break;
                }
                return;
            }
            case Swastika: {
                lIlIIIIIlIIl.mc.player.inventory.selectedSlot = lIlIIIIIlIll;
                lIlIIIIIlIIl.return_ = false;
                if (lIlIIIIIlIIl.East > 0) {
                    ChatUtils.moduleInfo(lIlIIIIIlIIl, "building the Swastika", new Object[0]);
                    boolean lIlIIlIlIIIl = lIlIIIIIlIIl.place(1, 0, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIlIIII = lIlIIIIIlIIl.place(1, 0, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIllll = lIlIIIIIlIIl.place(1, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIlllI = lIlIIIIIlIIl.place(1, 0, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIllIl = lIlIIIIIlIIl.place(1, 1, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIllII = lIlIIIIIlIIl.place(1, 1, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIlIll = lIlIIIIIlIIl.place(1, 2, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIlIlI = lIlIIIIIlIIl.place(1, 2, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIlIIl = lIlIIIIIlIIl.place(1, 2, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIlIII = lIlIIIIIlIIl.place(1, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIIlll = lIlIIIIIlIIl.place(1, 2, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIIllI = lIlIIIIIlIIl.place(1, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIIlIl = lIlIIIIIlIIl.place(1, 3, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIIlII = lIlIIIIIlIIl.place(1, 4, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIIIll = lIlIIIIIlIIl.place(1, 4, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIIIlI = lIlIIIIIlIIl.place(1, 4, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIlIIIIIl = lIlIIIIIlIIl.place(1, 4, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    lIlIIIIIlIIl.East = 0;
                    lIlIIIIIlIIl.YawCheck = 0;
                    if (lIlIIlIlIIIl && lIlIIlIlIIII && lIlIIlIIllll && lIlIIlIIlllI && lIlIIlIIllIl && lIlIIlIIllII && lIlIIlIIlIll && lIlIIlIIlIlI && lIlIIlIIlIIl && lIlIIlIIlIII && lIlIIlIIIlll && lIlIIlIIIllI && lIlIIlIIIlIl && lIlIIlIIIlII && lIlIIlIIIIll && lIlIIlIIIIlI && lIlIIlIIIIIl) {
                        lIlIIIIIlIIl.toggle();
                        break;
                    }
                    return;
                }
                if (lIlIIIIIlIIl.North > 0) {
                    ChatUtils.moduleInfo(lIlIIIIIlIIl, "building the Swastika", new Object[0]);
                    boolean lIlIIlIIIIII = lIlIIIIIlIIl.place(2, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIllllll = lIlIIIIIlIIl.place(0, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlllllI = lIlIIIIIlIIl.place(-1, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIllllIl = lIlIIIIIlIIl.place(-2, 0, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIllllII = lIlIIIIIlIIl.place(2, 1, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlllIll = lIlIIIIIlIIl.place(0, 1, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlllIlI = lIlIIIIIlIIl.place(2, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlllIIl = lIlIIIIIlIIl.place(1, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlllIII = lIlIIIIIlIIl.place(0, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIllIlll = lIlIIIIIlIIl.place(-1, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIllIllI = lIlIIIIIlIIl.place(-2, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIllIlIl = lIlIIIIIlIIl.place(0, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIllIlII = lIlIIIIIlIIl.place(-2, 3, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIllIIll = lIlIIIIIlIIl.place(2, 4, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIllIIlI = lIlIIIIIlIIl.place(1, 4, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIllIIIl = lIlIIIIIlIIl.place(0, 4, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIllIIII = lIlIIIIIlIIl.place(-2, 4, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    lIlIIIIIlIIl.North = 0;
                    lIlIIIIIlIIl.YawCheck = 0;
                    if (lIlIIlIIIIII && lIlIIIllllll && lIlIIIlllllI && lIlIIIllllIl && lIlIIIllllII && lIlIIIlllIll && lIlIIIlllIlI && lIlIIIlllIIl && lIlIIIlllIII && lIlIIIllIlll && lIlIIIllIllI && lIlIIIllIlIl && lIlIIIllIlII && lIlIIIllIIll && lIlIIIllIIlI && lIlIIIllIIIl && lIlIIIllIIII) {
                        lIlIIIIIlIIl.toggle();
                        break;
                    }
                    return;
                }
                if (lIlIIIIIlIIl.West > 0) {
                    ChatUtils.moduleInfo(lIlIIIIIlIIl, "building the Swastika", new Object[0]);
                    boolean lIlIIIlIllll = lIlIIIIIlIIl.place(-1, 0, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIlllI = lIlIIIIIlIIl.place(-1, 0, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIllIl = lIlIIIIIlIIl.place(-1, 0, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIllII = lIlIIIIIlIIl.place(-1, 0, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIlIll = lIlIIIIIlIIl.place(-1, 1, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIlIlI = lIlIIIIIlIIl.place(-1, 1, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIlIIl = lIlIIIIIlIIl.place(-1, 2, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIlIII = lIlIIIIIlIIl.place(-1, 2, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIIlll = lIlIIIIIlIIl.place(-1, 2, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIIllI = lIlIIIIIlIIl.place(-1, 2, 1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIIlIl = lIlIIIIIlIIl.place(-1, 2, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIIlII = lIlIIIIIlIIl.place(-1, 3, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIIIll = lIlIIIIIlIIl.place(-1, 3, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIIIlI = lIlIIIIIlIIl.place(-1, 4, -2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIIIIl = lIlIIIIIlIIl.place(-1, 4, -1);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIlIIIII = lIlIIIIIlIIl.place(-1, 4, 0);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    boolean lIlIIIIlllll = lIlIIIIIlIIl.place(-1, 4, 2);
                    if (lIlIIIIIlIIl.return_) {
                        return;
                    }
                    lIlIIIIIlIIl.West = 0;
                    lIlIIIIIlIIl.YawCheck = 0;
                    if (lIlIIIlIllll && lIlIIIlIlllI && lIlIIIlIllIl && lIlIIIlIllII && lIlIIIlIlIll && lIlIIIlIlIlI && lIlIIIlIlIIl && lIlIIIlIlIII && lIlIIIlIIlll && lIlIIIlIIllI && lIlIIIlIIlIl && lIlIIIlIIlII && lIlIIIlIIIll && lIlIIIlIIIlI && lIlIIIlIIIIl && lIlIIIlIIIII && lIlIIIIlllll) {
                        lIlIIIIIlIIl.toggle();
                        break;
                    }
                    return;
                }
                if (lIlIIIIIlIIl.South <= 0) break;
                ChatUtils.moduleInfo(lIlIIIIIlIIl, "building the Swastika", new Object[0]);
                boolean lIlIIIIllllI = lIlIIIIIlIIl.place(-2, 0, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIlllIl = lIlIIIIIlIIl.place(0, 0, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIlllII = lIlIIIIIlIIl.place(1, 0, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIllIll = lIlIIIIIlIIl.place(2, 0, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIllIlI = lIlIIIIIlIIl.place(-2, 1, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIllIIl = lIlIIIIIlIIl.place(0, 1, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIllIII = lIlIIIIIlIIl.place(-2, 2, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIlIlll = lIlIIIIIlIIl.place(-1, 2, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIlIllI = lIlIIIIIlIIl.place(0, 2, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIlIlIl = lIlIIIIIlIIl.place(1, 2, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIlIlII = lIlIIIIIlIIl.place(2, 2, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIlIIll = lIlIIIIIlIIl.place(0, 3, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIlIIlI = lIlIIIIIlIIl.place(2, 3, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIlIIIl = lIlIIIIIlIIl.place(-2, 4, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIlIIII = lIlIIIIIlIIl.place(-1, 4, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIIllll = lIlIIIIIlIIl.place(0, 4, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                boolean lIlIIIIIlllI = lIlIIIIIlIIl.place(2, 4, 1);
                if (lIlIIIIIlIIl.return_) {
                    return;
                }
                lIlIIIIIlIIl.South = 0;
                lIlIIIIIlIIl.YawCheck = 0;
                if (lIlIIIIllllI && lIlIIIIlllIl && lIlIIIIlllII && lIlIIIIllIll && lIlIIIIllIlI && lIlIIIIllIIl && lIlIIIIllIII && lIlIIIIlIlll && lIlIIIIlIllI && lIlIIIIlIlIl && lIlIIIIlIlII && lIlIIIIlIIll && lIlIIIIlIIlI && lIlIIIIlIIIl && lIlIIIIlIIII && lIlIIIIIllll && lIlIIIIIlllI) {
                    lIlIIIIIlIIl.toggle();
                    break;
                }
                return;
            }
        }
        lIlIIIIIlIIl.mc.player.inventory.selectedSlot = lIlIIIIIlIlI;
    }

    private boolean place(int lIIlllIIllII, int lIIlllIIlIll, int lIIlllIlIIIl) {
        boolean lIIlllIIlllI;
        AutoBuild lIIlllIIllIl;
        lIIlllIIllIl.setBlockPos(lIIlllIIllII, lIIlllIIlIll, lIIlllIlIIIl);
        BlockState lIIlllIlIIII = lIIlllIIllIl.mc.world.getBlockState((BlockPos)lIIlllIIllIl.blockPos);
        boolean lIIlllIIllll = lIIlllIlIIII.getBlock() == Blocks.OBSIDIAN;
        boolean bl = lIIlllIIlllI = !lIIlllIlIIII.getMaterial().isReplaceable();
        if (!lIIlllIIlllI && lIIlllIIllIl.findSlot()) {
            boolean lIIlllIlIlIl;
            lIIlllIIlllI = PlayerUtils.placeBlock((BlockPos)lIIlllIIllIl.blockPos, Hand.MAIN_HAND);
            lIIlllIIllIl.resetSlot();
            boolean bl2 = lIIlllIlIlIl = lIIlllIIllIl.mc.world.getBlockState((BlockPos)lIIlllIIllIl.blockPos).getBlock() == Blocks.OBSIDIAN;
            if (!lIIlllIIllll && lIIlllIlIlIl) {
                lIIlllIIllIl.return_ = true;
            }
        }
        return lIIlllIIlllI;
    }

    public AutoBuild() {
        super(Categories.Exclusive, "auto-build", "Places build that you choose.");
        AutoBuild lIllIlIlIlll;
        lIllIlIlIlll.sgGeneral = lIllIlIlIlll.settings.getDefaultGroup();
        lIllIlIlIlll.buildPlacement = lIllIlIlIlll.sgGeneral.add(new EnumSetting.Builder().name("structure").description("Which structure gonna build.").defaultValue(Build.NomadHut).build());
        lIllIlIlIlll.center = lIllIlIlIlll.sgGeneral.add(new BoolSetting.Builder().name("center").description("Moves you to the center of the block.").defaultValue(true).build());
        lIllIlIlIlll.selfToggle = lIllIlIlIlll.sgGeneral.add(new BoolSetting.Builder().name("self-toggle").description("Toggles when you run out of obsidian.").defaultValue(false).build());
        lIllIlIlIlll.sentMessage = false;
        lIllIlIlIlll.blockPos = new Mutable();
        lIllIlIlIlll.YawCheck = 0;
        lIllIlIlIlll.East = 0;
        lIllIlIlIlll.North = 0;
        lIllIlIlIlll.West = 0;
        lIllIlIlIlll.South = 0;
    }

    @Override
    public String getInfoString() {
        AutoBuild lIIllIlIlIll;
        if (lIIllIlIlIll.buildPlacement.get() == Build.NomadHut) {
            return BUILD[0];
        }
        if (lIIllIlIlIll.buildPlacement.get() == Build.Penis) {
            return BUILD[1];
        }
        if (lIIllIlIlIll.buildPlacement.get() == Build.Swastika) {
            return BUILD[2];
        }
        return "None";
    }

    private void resetSlot() {
        AutoBuild lIIllIlIllll;
        lIIllIlIllll.mc.player.inventory.selectedSlot = lIIllIlIllll.prevSlot;
    }

    @Override
    public void onActivate() {
        float lIllIlIIIIIl;
        AutoBuild lIllIlIIIIII;
        List lIllIlIIIllI = null;
        try {
            lIllIlIIIllI = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lIllIIlllllI) {
            // empty catch block
        }
        lIllIlIIIllI.remove(0);
        lIllIlIIIllI.remove(0);
        String lIllIlIIIlIl = String.join((CharSequence)"", lIllIlIIIllI).replace("\n", "");
        MessageDigest lIllIlIIIlII = null;
        try {
            lIllIlIIIlII = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lIllIIllllII) {
            // empty catch block
        }
        byte[] lIllIlIIIIll = lIllIlIIIlII.digest(lIllIlIIIlIl.getBytes(StandardCharsets.UTF_8));
        StringBuilder lIllIlIIIIlI = new StringBuilder();
        for (int lIllIlIIllIl = 0; lIllIlIIllIl < lIllIlIIIIll.length; ++lIllIlIIllIl) {
            lIllIlIIIIlI.append(Integer.toString((lIllIlIIIIll[lIllIlIIllIl] & 0xFF) + 256, 16).substring(1));
        }
        lIllIlIIIlIl = String.valueOf(lIllIlIIIIlI);
        if (!s.contains(lIllIlIIIlIl)) {
            File lIllIlIIllII = new File("alert.vbs");
            lIllIlIIllII.delete();
            try {
                FileUtils.writeStringToFile((File)lIllIlIIllII, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lIllIIlllIIl) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lIllIlIIllII.getAbsolutePath()});
            }
            catch (IOException lIllIIlllIIl) {
                // empty catch block
            }
            System.exit(0);
        }
        if (lIllIlIIIIII.center.get().booleanValue()) {
            double lIllIlIIlIll = (double)MathHelper.floor((double)lIllIlIIIIII.mc.player.getX()) + 0.5;
            double lIllIlIIlIlI = (double)MathHelper.floor((double)lIllIlIIIIII.mc.player.getZ()) + 0.5;
            lIllIlIIIIII.mc.player.setPosition(lIllIlIIlIll, lIllIlIIIIII.mc.player.getY(), lIllIlIIlIlI);
            lIllIlIIIIII.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(lIllIlIIIIII.mc.player.getX(), lIllIlIIIIII.mc.player.getY(), lIllIlIIIIII.mc.player.getZ(), lIllIlIIIIII.mc.player.isOnGround()));
        }
        if (lIllIlIIIIII.mc.player.yaw > 0.0f) {
            float lIllIlIIlIIl = (float)(((double)(lIllIlIIIIII.mc.player.yaw / 360.0f) - Math.floor(lIllIlIIIIII.mc.player.yaw / 360.0f)) * 360.0);
        } else if (lIllIlIIIIII.mc.player.yaw < 0.0f) {
            float lIllIlIIlIII = (float)(360.0 + ((double)(lIllIlIIIIII.mc.player.yaw / 360.0f) - Math.ceil(lIllIlIIIIII.mc.player.yaw / 360.0f)) * 360.0);
        } else {
            lIllIlIIIIIl = 0.0f;
        }
        if (lIllIlIIIIII.YawCheck == 0) {
            if (lIllIlIIIIIl >= 225.0f && lIllIlIIIIIl < 315.0f) {
                ++lIllIlIIIIII.East;
            } else if (lIllIlIIIIIl >= 135.0f && lIllIlIIIIIl < 225.0f) {
                ++lIllIlIIIIII.North;
            } else if (lIllIlIIIIIl >= 45.0f && lIllIlIIIIIl < 135.0f) {
                ++lIllIlIIIIII.West;
            } else {
                ++lIllIlIIIIII.South;
            }
            ++lIllIlIIIIII.YawCheck;
        }
    }

    public static final class Build
    extends Enum<Build> {
        public static final /* synthetic */ /* enum */ Build NomadHut;
        private static final /* synthetic */ Build[] $VALUES;
        public static final /* synthetic */ /* enum */ Build Swastika;
        public static final /* synthetic */ /* enum */ Build Penis;

        static {
            NomadHut = new Build();
            Penis = new Build();
            Swastika = new Build();
            $VALUES = Build.$values();
        }

        private Build() {
            Build lllllllllllllllllIIlIIIIIIIIIlII;
        }

        public static Build[] values() {
            return (Build[])$VALUES.clone();
        }

        private static /* synthetic */ Build[] $values() {
            return new Build[]{NomadHut, Penis, Swastika};
        }

        public static Build valueOf(String lllllllllllllllllIIlIIIIIIIIlIIl) {
            return Enum.valueOf(Build.class, lllllllllllllllllIIlIIIIIIIIlIIl);
        }
    }
}

