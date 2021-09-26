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
    private int YawCheck;
    private int prevSlot;
    private final Mutable blockPos;
    private final Setting<Build> buildPlacement;
    private final Setting<Boolean> selfToggle;
    private static String[] BUILD;
    private static List<String> s;
    private int South;
    private boolean return_;
    private final SettingGroup sgGeneral;
    private int West;
    private boolean sentMessage;
    private int East;
    private final Setting<Boolean> center;
    private int North;

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
        BUILD = new String[4];
        AutoBuild.BUILD[0] = "[NoMadHut]";
        AutoBuild.BUILD[1] = "[Penis]";
        AutoBuild.BUILD[2] = "[Swastika]";
    }

    private void setBlockPos(int n, int n2, int n3) {
        this.blockPos.set(this.mc.player.getX() + (double)n, this.mc.player.getY() + (double)n2, this.mc.player.getZ() + (double)n3);
    }

    private void resetSlot() {
        this.mc.player.inventory.selectedSlot = this.prevSlot;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        int n;
        int n2 = -1;
        for (n = 0; n < 9; ++n) {
            if (this.mc.player.inventory.getStack(n).getItem() != Blocks.OBSIDIAN.asItem()) continue;
            n2 = n;
            break;
        }
        if (n2 == -1 && this.selfToggle.get().booleanValue()) {
            if (!this.sentMessage) {
                ChatUtils.moduleError(this, "No obsidian found\u2026 disabling.", new Object[0]);
                this.sentMessage = true;
            }
            this.toggle();
            return;
        }
        if (n2 == -1) {
            return;
        }
        n = this.mc.player.inventory.selectedSlot;
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$Exclusive$AutoBuild$Build[this.buildPlacement.get().ordinal()]) {
            case 1: {
                this.mc.player.inventory.selectedSlot = n2;
                this.return_ = false;
                if (this.East > 0) {
                    ChatUtils.moduleInfo(this, "Building the NoMadHut", new Object[0]);
                    boolean bl = this.place(2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl2 = this.place(2, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl3 = this.place(1, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl4 = this.place(0, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl5 = this.place(-1, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl6 = this.place(-2, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl7 = this.place(-2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl8 = this.place(-2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl9 = this.place(-1, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl10 = this.place(0, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl11 = this.place(1, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl12 = this.place(2, 1, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl13 = this.place(2, 1, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl14 = this.place(1, 1, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl15 = this.place(-1, 1, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl16 = this.place(-2, 1, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl17 = this.place(-2, 1, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl18 = this.place(-1, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl19 = this.place(1, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl20 = this.place(2, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl21 = this.place(2, 2, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl22 = this.place(2, 2, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl23 = this.place(1, 2, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl24 = this.place(0, 2, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl25 = this.place(-1, 2, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl26 = this.place(-2, 2, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl27 = this.place(-2, 2, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl28 = this.place(-2, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl29 = this.place(-1, 2, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl30 = this.place(0, 2, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl31 = this.place(1, 2, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl32 = this.place(1, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl33 = this.place(1, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl34 = this.place(1, 3, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl35 = this.place(0, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl36 = this.place(0, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl37 = this.place(0, 3, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl38 = this.place(-1, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl39 = this.place(-1, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl40 = this.place(-1, 3, 1);
                    if (this.return_) {
                        return;
                    }
                    this.East = 0;
                    this.YawCheck = 0;
                    if (bl && bl2 && bl3 && bl4 && bl5 && bl6 && bl7 && bl8 && bl9 && bl10 && bl11 && bl12 && bl13 && bl14 && bl15 && bl16 && bl17 && bl18 && bl19 && bl20 && bl21 && bl22 && bl23 && bl24 && bl25 && bl26 && bl27 && bl28 && bl29 && bl30 && bl31 && bl32 && bl33 && bl34 && bl35 && bl36 && bl37 && bl38 && bl39 && bl40) {
                        this.toggle();
                        break;
                    }
                    return;
                }
                if (this.North > 0) {
                    ChatUtils.moduleInfo(this, "Building the NoMadHut", new Object[0]);
                    boolean bl = this.place(2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl41 = this.place(2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl42 = this.place(2, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl43 = this.place(1, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl44 = this.place(0, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl45 = this.place(-1, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl46 = this.place(-2, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl47 = this.place(-2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl48 = this.place(-2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl49 = this.place(-1, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl50 = this.place(1, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl51 = this.place(2, 1, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl52 = this.place(2, 1, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl53 = this.place(1, 1, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl54 = this.place(-1, 1, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl55 = this.place(-2, 1, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl56 = this.place(-2, 1, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl57 = this.place(-1, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl58 = this.place(1, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl59 = this.place(2, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl60 = this.place(2, 2, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl61 = this.place(2, 2, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl62 = this.place(1, 2, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl63 = this.place(0, 2, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl64 = this.place(-1, 2, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl65 = this.place(-2, 2, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl66 = this.place(-2, 2, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl67 = this.place(-2, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl68 = this.place(-1, 2, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl69 = this.place(0, 2, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl70 = this.place(1, 2, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl71 = this.place(1, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl72 = this.place(1, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl73 = this.place(1, 3, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl74 = this.place(0, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl75 = this.place(0, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl76 = this.place(0, 3, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl77 = this.place(-1, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl78 = this.place(-1, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl79 = this.place(-1, 3, 1);
                    if (this.return_) {
                        return;
                    }
                    this.North = 0;
                    this.YawCheck = 0;
                    if (bl && bl41 && bl42 && bl43 && bl44 && bl45 && bl46 && bl47 && bl48 && bl49 && bl50 && bl51 && bl52 && bl53 && bl54 && bl55 && bl56 && bl57 && bl58 && bl59 && bl60 && bl61 && bl62 && bl63 && bl64 && bl65 && bl66 && bl67 && bl68 && bl69 && bl70 && bl71 && bl72 && bl73 && bl74 && bl75 && bl76 && bl77 && bl78 && bl79) {
                        this.toggle();
                        break;
                    }
                    return;
                }
                if (this.West > 0) {
                    ChatUtils.moduleInfo(this, "Building the NoMadHut", new Object[0]);
                    boolean bl = this.place(2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl80 = this.place(2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl81 = this.place(2, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl82 = this.place(1, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl83 = this.place(0, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl84 = this.place(-1, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl85 = this.place(-2, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl86 = this.place(-2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl87 = this.place(-1, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl88 = this.place(1, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl89 = this.place(1, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl90 = this.place(2, 1, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl91 = this.place(2, 1, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl92 = this.place(1, 1, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl93 = this.place(-1, 1, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl94 = this.place(-2, 1, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl95 = this.place(-2, 1, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl96 = this.place(-1, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl97 = this.place(0, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl98 = this.place(1, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl99 = this.place(2, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl100 = this.place(2, 2, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl101 = this.place(2, 2, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl102 = this.place(1, 2, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl103 = this.place(0, 2, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl104 = this.place(-1, 2, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl105 = this.place(-2, 2, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl106 = this.place(-2, 2, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl107 = this.place(-2, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl108 = this.place(-1, 2, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl109 = this.place(0, 2, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl110 = this.place(1, 2, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl111 = this.place(1, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl112 = this.place(1, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl113 = this.place(1, 3, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl114 = this.place(0, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl115 = this.place(0, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl116 = this.place(0, 3, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl117 = this.place(-1, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl118 = this.place(-1, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl119 = this.place(-1, 3, 1);
                    if (this.return_) {
                        return;
                    }
                    this.West = 0;
                    this.YawCheck = 0;
                    if (bl && bl80 && bl81 && bl82 && bl83 && bl84 && bl85 && bl86 && bl87 && bl88 && bl89 && bl90 && bl91 && bl92 && bl93 && bl94 && bl95 && bl96 && bl98 && bl99 && bl100 && bl101 && bl102 && bl103 && bl104 && bl105 && bl106 && bl107 && bl108 && bl109 && bl110 && bl111 && bl112 && bl113 && bl114 && bl115 && bl116 && bl117 && bl118 && bl119 && bl97) {
                        this.toggle();
                        break;
                    }
                    return;
                }
                if (this.South <= 0) break;
                ChatUtils.moduleInfo(this, "Building the NoMadHut", new Object[0]);
                boolean bl = this.place(2, 0, -1);
                if (this.return_) {
                    return;
                }
                boolean bl120 = this.place(2, 0, 0);
                if (this.return_) {
                    return;
                }
                boolean bl121 = this.place(2, 0, 1);
                if (this.return_) {
                    return;
                }
                boolean bl122 = this.place(1, 0, 2);
                if (this.return_) {
                    return;
                }
                boolean bl123 = this.place(-1, 0, 2);
                if (this.return_) {
                    return;
                }
                boolean bl124 = this.place(-2, 0, 1);
                if (this.return_) {
                    return;
                }
                boolean bl125 = this.place(-2, 0, 0);
                if (this.return_) {
                    return;
                }
                boolean bl126 = this.place(-2, 0, -1);
                if (this.return_) {
                    return;
                }
                boolean bl127 = this.place(-1, 0, -2);
                if (this.return_) {
                    return;
                }
                boolean bl128 = this.place(0, 0, -2);
                if (this.return_) {
                    return;
                }
                boolean bl129 = this.place(1, 0, -2);
                if (this.return_) {
                    return;
                }
                boolean bl130 = this.place(2, 1, -1);
                if (this.return_) {
                    return;
                }
                boolean bl131 = this.place(2, 1, 1);
                if (this.return_) {
                    return;
                }
                boolean bl132 = this.place(1, 1, 2);
                if (this.return_) {
                    return;
                }
                boolean bl133 = this.place(-1, 1, 2);
                if (this.return_) {
                    return;
                }
                boolean bl134 = this.place(-2, 1, 1);
                if (this.return_) {
                    return;
                }
                boolean bl135 = this.place(-2, 1, -1);
                if (this.return_) {
                    return;
                }
                boolean bl136 = this.place(-1, 1, -2);
                if (this.return_) {
                    return;
                }
                boolean bl137 = this.place(1, 1, -2);
                if (this.return_) {
                    return;
                }
                boolean bl138 = this.place(2, 2, -1);
                if (this.return_) {
                    return;
                }
                boolean bl139 = this.place(2, 2, 0);
                if (this.return_) {
                    return;
                }
                boolean bl140 = this.place(2, 2, 1);
                if (this.return_) {
                    return;
                }
                boolean bl141 = this.place(1, 2, 2);
                if (this.return_) {
                    return;
                }
                boolean bl142 = this.place(0, 2, 2);
                if (this.return_) {
                    return;
                }
                boolean bl143 = this.place(-1, 2, 2);
                if (this.return_) {
                    return;
                }
                boolean bl144 = this.place(-2, 2, 1);
                if (this.return_) {
                    return;
                }
                boolean bl145 = this.place(-2, 2, 0);
                if (this.return_) {
                    return;
                }
                boolean bl146 = this.place(-2, 2, -1);
                if (this.return_) {
                    return;
                }
                boolean bl147 = this.place(-1, 2, -2);
                if (this.return_) {
                    return;
                }
                boolean bl148 = this.place(0, 2, -2);
                if (this.return_) {
                    return;
                }
                boolean bl149 = this.place(1, 2, -2);
                if (this.return_) {
                    return;
                }
                boolean bl150 = this.place(1, 3, -1);
                if (this.return_) {
                    return;
                }
                boolean bl151 = this.place(1, 3, 0);
                if (this.return_) {
                    return;
                }
                boolean bl152 = this.place(1, 3, 1);
                if (this.return_) {
                    return;
                }
                boolean bl153 = this.place(0, 3, -1);
                if (this.return_) {
                    return;
                }
                boolean bl154 = this.place(0, 3, 0);
                if (this.return_) {
                    return;
                }
                boolean bl155 = this.place(0, 3, 1);
                if (this.return_) {
                    return;
                }
                boolean bl156 = this.place(-1, 3, -1);
                if (this.return_) {
                    return;
                }
                boolean bl157 = this.place(-1, 3, 0);
                if (this.return_) {
                    return;
                }
                boolean bl158 = this.place(-1, 3, 1);
                if (this.return_) {
                    return;
                }
                this.South = 0;
                this.YawCheck = 0;
                if (bl && bl120 && bl121 && bl122 && bl123 && bl124 && bl125 && bl126 && bl127 && bl128 && bl129 && bl130 && bl131 && bl132 && bl133 && bl134 && bl135 && bl136 && bl137 && bl138 && bl139 && bl140 && bl141 && bl142 && bl143 && bl144 && bl145 && bl146 && bl147 && bl148 && bl149 && bl150 && bl151 && bl152 && bl153 && bl154 && bl155 && bl156 && bl157 && bl158) {
                    this.toggle();
                    break;
                }
                return;
            }
            case 2: {
                this.mc.player.inventory.selectedSlot = n2;
                this.return_ = false;
                if (this.East > 0) {
                    ChatUtils.moduleInfo(this, "Building the Penis", new Object[0]);
                    boolean bl = this.place(1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl159 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl160 = this.place(1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl161 = this.place(1, 1, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl162 = this.place(1, 2, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl163 = this.place(1, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    this.East = 0;
                    this.YawCheck = 0;
                    if (bl && bl159 && bl160 && bl161 && bl162 && bl163) {
                        this.toggle();
                        break;
                    }
                    return;
                }
                if (this.North > 0) {
                    ChatUtils.moduleInfo(this, "Building the Penis", new Object[0]);
                    boolean bl = this.place(-1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl164 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl165 = this.place(1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl166 = this.place(0, 1, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl167 = this.place(0, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl168 = this.place(0, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    this.North = 0;
                    this.YawCheck = 0;
                    if (bl && bl164 && bl165 && bl166 && bl167 && bl168) {
                        this.toggle();
                        break;
                    }
                    return;
                }
                if (this.West > 0) {
                    ChatUtils.moduleInfo(this, "Building the Penis", new Object[0]);
                    boolean bl = this.place(-1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl169 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl170 = this.place(-1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl171 = this.place(-1, 1, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl172 = this.place(-1, 2, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl173 = this.place(-1, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    this.West = 0;
                    this.YawCheck = 0;
                    if (bl && bl169 && bl170 && bl171 && bl172 && bl173) {
                        this.toggle();
                        break;
                    }
                    return;
                }
                if (this.South <= 0) break;
                ChatUtils.moduleInfo(this, "Building the Penis", new Object[0]);
                boolean bl = this.place(-1, 0, 1);
                if (this.return_) {
                    return;
                }
                boolean bl174 = this.place(0, 0, 1);
                if (this.return_) {
                    return;
                }
                boolean bl175 = this.place(1, 0, 1);
                if (this.return_) {
                    return;
                }
                boolean bl176 = this.place(0, 1, 1);
                if (this.return_) {
                    return;
                }
                boolean bl177 = this.place(0, 2, 1);
                if (this.return_) {
                    return;
                }
                boolean bl178 = this.place(0, 3, 1);
                if (this.return_) {
                    return;
                }
                this.South = 0;
                this.YawCheck = 0;
                if (bl && bl174 && bl175 && bl176 && bl177 && bl178) {
                    this.toggle();
                    break;
                }
                return;
            }
            case 3: {
                this.mc.player.inventory.selectedSlot = n2;
                this.return_ = false;
                if (this.East > 0) {
                    ChatUtils.moduleInfo(this, "building the Swastika", new Object[0]);
                    boolean bl = this.place(1, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl179 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl180 = this.place(1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl181 = this.place(1, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl182 = this.place(1, 1, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl183 = this.place(1, 1, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl184 = this.place(1, 2, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl185 = this.place(1, 2, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl186 = this.place(1, 2, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl187 = this.place(1, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl188 = this.place(1, 2, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl189 = this.place(1, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl190 = this.place(1, 3, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl191 = this.place(1, 4, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl192 = this.place(1, 4, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl193 = this.place(1, 4, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl194 = this.place(1, 4, -2);
                    if (this.return_) {
                        return;
                    }
                    this.East = 0;
                    this.YawCheck = 0;
                    if (bl && bl179 && bl180 && bl181 && bl182 && bl183 && bl184 && bl185 && bl186 && bl187 && bl188 && bl189 && bl190 && bl191 && bl192 && bl193 && bl194) {
                        this.toggle();
                        break;
                    }
                    return;
                }
                if (this.North > 0) {
                    ChatUtils.moduleInfo(this, "building the Swastika", new Object[0]);
                    boolean bl = this.place(2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl195 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl196 = this.place(-1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl197 = this.place(-2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl198 = this.place(2, 1, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl199 = this.place(0, 1, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl200 = this.place(2, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl201 = this.place(1, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl202 = this.place(0, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl203 = this.place(-1, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl204 = this.place(-2, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl205 = this.place(0, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl206 = this.place(-2, 3, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl207 = this.place(2, 4, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl208 = this.place(1, 4, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl209 = this.place(0, 4, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl210 = this.place(-2, 4, -1);
                    if (this.return_) {
                        return;
                    }
                    this.North = 0;
                    this.YawCheck = 0;
                    if (bl && bl195 && bl196 && bl197 && bl198 && bl199 && bl200 && bl201 && bl202 && bl203 && bl204 && bl205 && bl206 && bl207 && bl208 && bl209 && bl210) {
                        this.toggle();
                        break;
                    }
                    return;
                }
                if (this.West > 0) {
                    ChatUtils.moduleInfo(this, "building the Swastika", new Object[0]);
                    boolean bl = this.place(-1, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl211 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl212 = this.place(-1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl213 = this.place(-1, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl214 = this.place(-1, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl215 = this.place(-1, 1, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl216 = this.place(-1, 2, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl217 = this.place(-1, 2, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl218 = this.place(-1, 2, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl219 = this.place(-1, 2, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl220 = this.place(-1, 2, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl221 = this.place(-1, 3, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl222 = this.place(-1, 3, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl223 = this.place(-1, 4, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl224 = this.place(-1, 4, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl225 = this.place(-1, 4, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl226 = this.place(-1, 4, 2);
                    if (this.return_) {
                        return;
                    }
                    this.West = 0;
                    this.YawCheck = 0;
                    if (bl && bl211 && bl212 && bl213 && bl214 && bl215 && bl216 && bl217 && bl218 && bl219 && bl220 && bl221 && bl222 && bl223 && bl224 && bl225 && bl226) {
                        this.toggle();
                        break;
                    }
                    return;
                }
                if (this.South <= 0) break;
                ChatUtils.moduleInfo(this, "building the Swastika", new Object[0]);
                boolean bl = this.place(-2, 0, 1);
                if (this.return_) {
                    return;
                }
                boolean bl227 = this.place(0, 0, 1);
                if (this.return_) {
                    return;
                }
                boolean bl228 = this.place(1, 0, 1);
                if (this.return_) {
                    return;
                }
                boolean bl229 = this.place(2, 0, 1);
                if (this.return_) {
                    return;
                }
                boolean bl230 = this.place(-2, 1, 1);
                if (this.return_) {
                    return;
                }
                boolean bl231 = this.place(0, 1, 1);
                if (this.return_) {
                    return;
                }
                boolean bl232 = this.place(-2, 2, 1);
                if (this.return_) {
                    return;
                }
                boolean bl233 = this.place(-1, 2, 1);
                if (this.return_) {
                    return;
                }
                boolean bl234 = this.place(0, 2, 1);
                if (this.return_) {
                    return;
                }
                boolean bl235 = this.place(1, 2, 1);
                if (this.return_) {
                    return;
                }
                boolean bl236 = this.place(2, 2, 1);
                if (this.return_) {
                    return;
                }
                boolean bl237 = this.place(0, 3, 1);
                if (this.return_) {
                    return;
                }
                boolean bl238 = this.place(2, 3, 1);
                if (this.return_) {
                    return;
                }
                boolean bl239 = this.place(-2, 4, 1);
                if (this.return_) {
                    return;
                }
                boolean bl240 = this.place(-1, 4, 1);
                if (this.return_) {
                    return;
                }
                boolean bl241 = this.place(0, 4, 1);
                if (this.return_) {
                    return;
                }
                boolean bl242 = this.place(2, 4, 1);
                if (this.return_) {
                    return;
                }
                this.South = 0;
                this.YawCheck = 0;
                if (bl && bl227 && bl228 && bl229 && bl230 && bl231 && bl232 && bl233 && bl234 && bl235 && bl236 && bl237 && bl238 && bl239 && bl240 && bl241 && bl242) {
                    this.toggle();
                    break;
                }
                return;
            }
        }
        this.mc.player.inventory.selectedSlot = n;
    }

    @Override
    public String getInfoString() {
        if (this.buildPlacement.get() == Build.NomadHut) {
            return BUILD[0];
        }
        if (this.buildPlacement.get() == Build.Penis) {
            return BUILD[1];
        }
        if (this.buildPlacement.get() == Build.Swastika) {
            return BUILD[2];
        }
        return "None";
    }

    public AutoBuild() {
        super(Categories.Exclusive, "auto-build", "Places build that you choose.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.buildPlacement = this.sgGeneral.add(new EnumSetting.Builder().name("structure").description("Which structure gonna build.").defaultValue(Build.NomadHut).build());
        this.center = this.sgGeneral.add(new BoolSetting.Builder().name("center").description("Moves you to the center of the block.").defaultValue(true).build());
        this.selfToggle = this.sgGeneral.add(new BoolSetting.Builder().name("self-toggle").description("Toggles when you run out of obsidian.").defaultValue(false).build());
        this.sentMessage = false;
        this.blockPos = new Mutable();
        this.YawCheck = 0;
        this.East = 0;
        this.North = 0;
        this.West = 0;
        this.South = 0;
    }

    @Override
    public void onActivate() {
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
            if (2 == 2) continue;
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
        if (this.center.get().booleanValue()) {
            double d = (double)MathHelper.floor((double)this.mc.player.getX()) + 0.5;
            double d2 = (double)MathHelper.floor((double)this.mc.player.getZ()) + 0.5;
            this.mc.player.setPosition(d, this.mc.player.getY(), d2);
            this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2829(this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ(), this.mc.player.isOnGround()));
        }
        float f = this.mc.player.yaw > 0.0f ? (float)(((double)(this.mc.player.yaw / 360.0f) - Math.floor(this.mc.player.yaw / 360.0f)) * 360.0) : (this.mc.player.yaw < 0.0f ? (float)(360.0 + ((double)(this.mc.player.yaw / 360.0f) - Math.ceil(this.mc.player.yaw / 360.0f)) * 360.0) : 0.0f);
        if (this.YawCheck == 0) {
            if (f >= 225.0f && f < 315.0f) {
                ++this.East;
            } else if (f >= 135.0f && f < 225.0f) {
                ++this.North;
            } else if (f >= 45.0f && f < 135.0f) {
                ++this.West;
            } else {
                ++this.South;
            }
            ++this.YawCheck;
        }
    }

    private boolean findSlot() {
        this.prevSlot = this.mc.player.inventory.selectedSlot;
        for (int i = 0; i < 9; ++i) {
            Item Item2 = this.mc.player.inventory.getStack(i).getItem();
            if (!(Item2 instanceof BlockItem) || Item2 != Items.OBSIDIAN) continue;
            this.mc.player.inventory.selectedSlot = i;
            return true;
        }
        return false;
    }

    private boolean place(int n, int n2, int n3) {
        boolean bl;
        this.setBlockPos(n, n2, n3);
        BlockState BlockState2 = this.mc.world.getBlockState((BlockPos)this.blockPos);
        boolean bl2 = BlockState2.getBlock() == Blocks.OBSIDIAN;
        boolean bl3 = bl = !BlockState2.getMaterial().isReplaceable();
        if (!bl && this.findSlot()) {
            boolean bl4;
            bl = PlayerUtils.placeBlock((BlockPos)this.blockPos, Hand.MAIN_HAND);
            this.resetSlot();
            boolean bl5 = bl4 = this.mc.world.getBlockState((BlockPos)this.blockPos).getBlock() == Blocks.OBSIDIAN;
            if (!bl2 && bl4) {
                this.return_ = true;
            }
        }
        return bl;
    }

    public static final class Build
    extends Enum<Build> {
        public static final /* enum */ Build Penis;
        public static final /* enum */ Build Swastika;
        private static final Build[] $VALUES;
        public static final /* enum */ Build NomadHut;

        private static Build[] $values() {
            return new Build[]{NomadHut, Penis, Swastika};
        }

        public static Build valueOf(String string) {
            return Enum.valueOf(Build.class, string);
        }

        public static Build[] values() {
            return (Build[])$VALUES.clone();
        }

        static {
            NomadHut = new Build();
            Penis = new Build();
            Swastika = new Build();
            $VALUES = Build.$values();
        }
    }
}

