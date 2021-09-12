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
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_2382;
import net.minecraft.class_2680;
import net.minecraft.class_3532;
import org.apache.commons.io.FileUtils;

public class AutoHighway
extends Module {
    private final Setting<Boolean> disableOnJump;
    private final Setting<Boolean> rotate;
    private Direction direction;
    private int ticks;
    private final SettingGroup sgGeneral;
    private boolean return_;
    private final class_2338.class_2339 blockPos;
    private final Setting<Integer> HighwaySize;
    private int highwaySize;
    private final Setting<Integer> tickDelay;
    private static List<String> s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));

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
            if (-1 < 1) continue;
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
        this.ticks = 0;
        this.direction = this.getDirection((class_1657)this.mc.field_1724);
        this.blockPos.method_10101((class_2382)this.mc.field_1724.method_24515());
        this.changeBlockPos(0, -1, 0);
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.disableOnJump.get().booleanValue() && this.mc.field_1690.field_1903.method_1434()) {
            this.toggle();
            return;
        }
        if (InvUtils.findItemInHotbar(class_1802.field_8281) == -1) {
            return;
        }
        this.highwaySize = this.getSize();
        this.return_ = false;
        if (this.getDistance((class_1657)this.mc.field_1724) > 12) {
            return;
        }
        if (this.ticks >= this.tickDelay.get()) {
            this.ticks = 0;
            if (this.direction == Direction.SOUTH) {
                boolean bl;
                boolean bl2;
                boolean bl3;
                boolean bl4;
                boolean bl5;
                if (this.highwaySize == 3) {
                    bl5 = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    bl4 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    bl3 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    bl2 = this.place(-2, 1, 0);
                    if (this.return_) {
                        return;
                    }
                    bl = this.place(2, 1, 0);
                    if (bl5 && bl4 && bl3 && bl2 && bl) {
                        this.nextLayer();
                    }
                }
                if (this.highwaySize == 4) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                }
                if (this.highwaySize == 6) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 5) {
                    bl5 = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    bl4 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    bl3 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    bl2 = this.place(-2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    bl = this.place(2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl6 = this.place(-3, 1, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl7 = this.place(3, 1, 0);
                    if (bl5 && bl4 && bl3 && bl2 && bl && bl6 && bl7) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 7) {
                    bl5 = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    bl4 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    bl3 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    bl2 = this.place(-2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    bl = this.place(2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl8 = this.place(-3, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl9 = this.place(3, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl10 = this.place(-4, 1, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl11 = this.place(4, 1, 0);
                    if (bl5 && bl4 && bl3 && bl2 && bl && bl8 && bl9 && bl10 && bl11) {
                        this.nextLayer();
                    }
                }
            } else if (this.direction == Direction.WEST) {
                if (this.highwaySize == 3) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl12 = this.place(0, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl13 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl14 = this.place(0, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl15 = this.place(0, 1, 2);
                    if (bl && bl12 && bl13 && bl14 && bl15) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 4) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 6) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 5) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl16 = this.place(0, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl17 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl18 = this.place(0, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl19 = this.place(0, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl20 = this.place(0, 1, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl21 = this.place(0, 1, 3);
                    if (bl && bl16 && bl17 && bl18 && bl19 && bl20 && bl21) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 7) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl22 = this.place(0, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl23 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl24 = this.place(0, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl25 = this.place(0, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl26 = this.place(0, 0, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl27 = this.place(0, 0, 3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl28 = this.place(0, 1, -4);
                    if (this.return_) {
                        return;
                    }
                    boolean bl29 = this.place(0, 1, 4);
                    if (bl && bl22 && bl23 && bl24 && bl25 && bl26 && bl27 && bl28 && bl29) {
                        this.nextLayer();
                    }
                }
            } else if (this.direction == Direction.NORTH) {
                if (this.highwaySize == 3) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl30 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl31 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl32 = this.place(-2, 1, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl33 = this.place(2, 1, 0);
                    if (bl && bl30 && bl31 && bl32 && bl33) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 4) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 6) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 5) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl34 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl35 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl36 = this.place(-2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl37 = this.place(2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl38 = this.place(-3, 1, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl39 = this.place(3, 1, 0);
                    if (bl && bl34 && bl35 && bl36 && bl37 && bl38 && bl39) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 7) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl40 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl41 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl42 = this.place(-2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl43 = this.place(2, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl44 = this.place(-3, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl45 = this.place(3, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl46 = this.place(-4, 1, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl47 = this.place(4, 1, 0);
                    if (bl && bl40 && bl41 && bl42 && bl43 && bl44 && bl45 && bl46 && bl47) {
                        this.nextLayer();
                    }
                }
            } else if (this.direction == Direction.EAST) {
                if (this.highwaySize == 3) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl48 = this.place(0, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl49 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl50 = this.place(0, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl51 = this.place(0, 1, 2);
                    if (bl && bl48 && bl49 && bl50 && bl51) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 4) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 6) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 5) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl52 = this.place(0, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl53 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl54 = this.place(0, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl55 = this.place(0, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl56 = this.place(0, 1, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl57 = this.place(0, 1, 3);
                    if (bl && bl52 && bl53 && bl54 && bl55 && bl56 && bl57) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 7) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl58 = this.place(0, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl59 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl60 = this.place(0, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl61 = this.place(0, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl62 = this.place(0, 0, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl63 = this.place(0, 0, 3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl64 = this.place(0, 1, -4);
                    if (this.return_) {
                        return;
                    }
                    boolean bl65 = this.place(0, 1, 4);
                    if (bl && bl58 && bl59 && bl60 && bl61 && bl62 && bl63 && bl64 && bl65) {
                        this.nextLayer();
                    }
                }
            } else if (this.direction == Direction.EAST_SOUTH) {
                if (this.highwaySize == 3) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl66 = this.place(1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl67 = this.place(-1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl68 = this.place(1, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl69 = this.place(-2, 1, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl70 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl71 = this.place(0, 0, 1);
                    if (bl && bl66 && bl67 && bl68 && bl69 && bl70 && bl71) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 4) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 6) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 5) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl72 = this.place(1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl73 = this.place(-1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl74 = this.place(2, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl75 = this.place(-2, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl76 = this.place(2, 1, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl77 = this.place(-3, 1, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl78 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl79 = this.place(0, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl80 = this.place(2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl81 = this.place(-1, 0, 2);
                    if (bl && bl72 && bl73 && bl74 && bl75 && bl76 && bl77 && bl78 && bl79 && bl80 && bl81) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 7) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl82 = this.place(1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl83 = this.place(-1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl84 = this.place(2, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl85 = this.place(-2, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl86 = this.place(3, 0, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl87 = this.place(-3, 0, 3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl88 = this.place(3, 1, -4);
                    if (this.return_) {
                        return;
                    }
                    boolean bl89 = this.place(-4, 1, 3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl90 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl91 = this.place(0, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl92 = this.place(2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl93 = this.place(-1, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl94 = this.place(3, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl95 = this.place(-2, 0, 3);
                    if (bl && bl82 && bl83 && bl84 && bl85 && bl86 && bl87 && bl88 && bl89 && bl90 && bl91 && bl92 && bl93 && bl94 && bl95) {
                        this.nextLayer();
                    }
                }
            } else if (this.direction == Direction.SOUTH_WEST) {
                if (this.highwaySize == 3) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl96 = this.place(-1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl97 = this.place(1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl98 = this.place(-1, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl99 = this.place(2, 1, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl100 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl101 = this.place(0, 0, 1);
                    if (bl && bl96 && bl97 && bl98 && bl99 && bl100 && bl101) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 4) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 6) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 5) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl102 = this.place(-1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl103 = this.place(1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl104 = this.place(-2, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl105 = this.place(2, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl106 = this.place(-2, 1, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl107 = this.place(3, 1, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl108 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl109 = this.place(0, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl110 = this.place(-2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl111 = this.place(1, 0, 2);
                    if (bl && bl102 && bl103 && bl104 && bl105 && bl106 && bl107 && bl108 && bl109 && bl110 && bl111) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 7) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl112 = this.place(-1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl113 = this.place(1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl114 = this.place(-2, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl115 = this.place(2, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl116 = this.place(-3, 0, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl117 = this.place(3, 0, 3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl118 = this.place(-3, 1, -4);
                    if (this.return_) {
                        return;
                    }
                    boolean bl119 = this.place(4, 1, 3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl120 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl121 = this.place(0, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl122 = this.place(-2, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl123 = this.place(1, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl124 = this.place(-3, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl125 = this.place(2, 0, 3);
                    if (bl && bl112 && bl113 && bl114 && bl115 && bl116 && bl117 && bl118 && bl119 && bl120 && bl121 && bl122 && bl123 && bl124 && bl125) {
                        this.nextLayer();
                    }
                }
            } else if (this.direction == Direction.WEST_NORTH) {
                if (this.highwaySize == 3) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl126 = this.place(-1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl127 = this.place(1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl128 = this.place(-1, 1, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl129 = this.place(2, 1, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl130 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl131 = this.place(0, 0, -1);
                    if (bl && bl126 && bl127 && bl128 && bl129 && bl130 && bl131) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 4) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 6) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 5) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl132 = this.place(-1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl133 = this.place(1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl134 = this.place(-2, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl135 = this.place(2, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl136 = this.place(-2, 1, 3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl137 = this.place(3, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl138 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl139 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl140 = this.place(-2, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl141 = this.place(1, 0, -2);
                    if (bl && bl132 && bl133 && bl134 && bl135 && bl136 && bl137 && bl138 && bl139 && bl140 && bl141) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 7) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl142 = this.place(-1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl143 = this.place(1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl144 = this.place(-2, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl145 = this.place(2, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl146 = this.place(-3, 0, 3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl147 = this.place(3, 0, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl148 = this.place(-3, 1, 4);
                    if (this.return_) {
                        return;
                    }
                    boolean bl149 = this.place(4, 1, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl150 = this.place(-1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl151 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl152 = this.place(-2, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl153 = this.place(1, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl154 = this.place(-3, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl155 = this.place(2, 0, -3);
                    if (bl && bl142 && bl143 && bl144 && bl145 && bl146 && bl147 && bl148 && bl149 && bl150 && bl151 && bl152 && bl153 && bl154 && bl155) {
                        this.nextLayer();
                    }
                }
            } else if (this.direction == Direction.NORTH_EAST) {
                if (this.highwaySize == 3) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl156 = this.place(1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl157 = this.place(-1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl158 = this.place(1, 1, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl159 = this.place(-2, 1, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl160 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl161 = this.place(0, 0, -1);
                    if (bl && bl156 && bl157 && bl158 && bl159 && bl160 && bl161) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 4) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 6) {
                    ChatUtils.moduleError(this, "This format is not supported.", new Object[0]);
                    this.toggle();
                } else if (this.highwaySize == 5) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl162 = this.place(1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl163 = this.place(-1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl164 = this.place(2, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl165 = this.place(-2, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl166 = this.place(2, 1, 3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl167 = this.place(-3, 1, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl168 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl169 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl170 = this.place(2, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl171 = this.place(-1, 0, -2);
                    if (bl && bl162 && bl163 && bl164 && bl165 && bl166 && bl167 && bl168 && bl169 && bl170 && bl171) {
                        this.nextLayer();
                    }
                } else if (this.highwaySize == 7) {
                    boolean bl = this.place(0, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl172 = this.place(1, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl173 = this.place(-1, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl174 = this.place(2, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl175 = this.place(-2, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl176 = this.place(3, 0, 3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl177 = this.place(-3, 0, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl178 = this.place(3, 1, 4);
                    if (this.return_) {
                        return;
                    }
                    boolean bl179 = this.place(-4, 1, -3);
                    if (this.return_) {
                        return;
                    }
                    boolean bl180 = this.place(1, 0, 0);
                    if (this.return_) {
                        return;
                    }
                    boolean bl181 = this.place(0, 0, -1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl182 = this.place(2, 0, 1);
                    if (this.return_) {
                        return;
                    }
                    boolean bl183 = this.place(-1, 0, -2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl184 = this.place(3, 0, 2);
                    if (this.return_) {
                        return;
                    }
                    boolean bl185 = this.place(-2, 0, -3);
                    if (bl && bl172 && bl173 && bl174 && bl175 && bl176 && bl177 && bl178 && bl179 && bl180 && bl181 && bl182 && bl183 && bl184 && bl185) {
                        this.nextLayer();
                    }
                }
            }
        } else {
            ++this.ticks;
        }
    }

    private int findSlot() {
        for (int i = 0; i < 9; ++i) {
            class_1792 class_17922 = this.mc.field_1724.field_7514.method_5438(i).method_7909();
            if (!(class_17922 instanceof class_1747) || class_17922 != class_1802.field_8281) continue;
            return i;
        }
        return -1;
    }

    private Direction getDirection(class_1657 class_16572) {
        double d = class_16572.field_6031;
        if (d == 0.0) {
            return Direction.SOUTH;
        }
        if (d < 0.0) {
            if ((d -= (double)(class_3532.method_15384((double)(d / 360.0)) * 360)) < -180.0) {
                d = 360.0 + d;
            }
        } else if ((d -= (double)(class_3532.method_15357((double)(d / 360.0)) * 360)) > 180.0) {
            d = -360.0 + d;
        }
        if (d >= 157.5 || d < -157.5) {
            return Direction.NORTH;
        }
        if (d >= -157.5 && d < -112.5) {
            return Direction.NORTH_EAST;
        }
        if (d >= -112.5 && d < -67.5) {
            return Direction.EAST;
        }
        if (d >= -67.5 && d < -22.5) {
            return Direction.EAST_SOUTH;
        }
        if (d >= -22.5 && d <= 0.0 || d > 0.0 && d < 22.5) {
            return Direction.SOUTH;
        }
        if (d >= 22.5 && d < 67.5) {
            return Direction.SOUTH_WEST;
        }
        if (d >= 67.5 && d < 112.5) {
            return Direction.WEST;
        }
        if (d >= 112.5 && d < 157.5) {
            return Direction.WEST_NORTH;
        }
        return Direction.SOUTH;
    }

    private class_2338 setBlockPos(int n, int n2, int n3) {
        return new class_2338(this.blockPos.method_10263() + n, this.blockPos.method_10264() + n2, this.blockPos.method_10260() + n3);
    }

    private int getDistance(class_1657 class_16572) {
        return (int)Math.round(class_16572.method_5649((double)this.blockPos.method_10263(), (double)((float)this.blockPos.method_10264() - class_16572.method_5751()), (double)this.blockPos.method_10260()));
    }

    private boolean place(int n, int n2, int n3) {
        class_2338 class_23382 = this.setBlockPos(n, n2, n3);
        class_2680 class_26802 = this.mc.field_1687.method_8320(class_23382);
        if (!class_26802.method_26207().method_15800()) {
            return true;
        }
        int n4 = this.findSlot();
        if (BlockUtils.place(class_23382, class_1268.field_5808, n4, this.rotate.get(), 10, true)) {
            this.return_ = true;
        }
        return false;
    }

    private int getSize() {
        if (this.HighwaySize.get() % 2 == 0) {
            return this.HighwaySize.get() - 1;
        }
        return this.HighwaySize.get();
    }

    private void nextLayer() {
        if (this.direction == Direction.SOUTH) {
            this.changeBlockPos(0, 0, 1);
        } else if (this.direction == Direction.WEST) {
            this.changeBlockPos(-1, 0, 0);
        } else if (this.direction == Direction.NORTH) {
            this.changeBlockPos(0, 0, -1);
        } else if (this.direction == Direction.EAST) {
            this.changeBlockPos(1, 0, 0);
        } else if (this.direction == Direction.EAST_SOUTH) {
            this.changeBlockPos(1, 0, 1);
        } else if (this.direction == Direction.SOUTH_WEST) {
            this.changeBlockPos(-1, 0, 1);
        } else if (this.direction == Direction.WEST_NORTH) {
            this.changeBlockPos(-1, 0, -1);
        } else if (this.direction == Direction.NORTH_EAST) {
            this.changeBlockPos(1, 0, -1);
        }
    }

    private void changeBlockPos(int n, int n2, int n3) {
        this.blockPos.method_10103(this.blockPos.method_10263() + n, this.blockPos.method_10264() + n2, this.blockPos.method_10260() + n3);
    }

    public AutoHighway() {
        super(Categories.Exclusive, "auto-highway", "Automatically build highway.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.tickDelay = this.sgGeneral.add(new IntSetting.Builder().name("Delay").description("Block per ticks.").defaultValue(1).min(0).max(20).sliderMin(0).sliderMax(20).build());
        this.HighwaySize = this.sgGeneral.add(new IntSetting.Builder().name("size").description("Highway's size.").defaultValue(3).min(3).sliderMin(3).max(7).sliderMax(7).build());
        this.disableOnJump = this.sgGeneral.add(new BoolSetting.Builder().name("disable-on-jump").description("Disable when you jump.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("See on the placing block.").defaultValue(true).build());
        this.blockPos = new class_2338.class_2339();
    }

    private static final class Direction
    extends Enum<Direction> {
        private static final Direction[] $VALUES;
        public static final /* enum */ Direction NORTH;
        public static final /* enum */ Direction WEST_NORTH;
        public static final /* enum */ Direction WEST;
        public static final /* enum */ Direction EAST_SOUTH;
        public static final /* enum */ Direction SOUTH_WEST;
        public static final /* enum */ Direction EAST;
        public static final /* enum */ Direction NORTH_EAST;
        public static final /* enum */ Direction SOUTH;

        public static Direction[] values() {
            return (Direction[])$VALUES.clone();
        }

        static {
            SOUTH = new Direction();
            SOUTH_WEST = new Direction();
            WEST = new Direction();
            WEST_NORTH = new Direction();
            NORTH = new Direction();
            NORTH_EAST = new Direction();
            EAST = new Direction();
            EAST_SOUTH = new Direction();
            $VALUES = Direction.$values();
        }

        private static Direction[] $values() {
            return new Direction[]{SOUTH, SOUTH_WEST, WEST, WEST_NORTH, NORTH, NORTH_EAST, EAST, EAST_SOUTH};
        }

        public static Direction valueOf(String string) {
            return Enum.valueOf(Direction.class, string);
        }
    }
}

