/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Items
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionOnly
 *  org.apache.commons.io.FileUtils
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
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
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Exclusive.Ezz;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.apache.commons.io.FileUtils;

public class ExtraSurround
extends Module {
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Boolean> disableOnJump;
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Setting<SurrMode> mode;
    private final /* synthetic */ Setting<ecenter> center;
    /* synthetic */ BlockPos pos;
    private /* synthetic */ int ticks;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<antcry> anti;
    private final /* synthetic */ Setting<Boolean> onlyOnGround;
    private final /* synthetic */ Setting<Integer> tickDelay;

    @EventHandler
    private void onTick(TickEvent.Pre llllllllllllllllIllllIlIIllIllII) {
        ExtraSurround llllllllllllllllIllllIlIIllIlIll;
        if (llllllllllllllllIllllIlIIllIlIll.ticks >= llllllllllllllllIllllIlIIllIlIll.tickDelay.get()) {
            llllllllllllllllIllllIlIIllIlIll.ticks = 0;
            if (llllllllllllllllIllllIlIIllIlIll.center.get() == ecenter.legit) {
                double llllllllllllllllIllllIlIIlllIIII = 0.0;
                double llllllllllllllllIllllIlIIllIllll = 0.0;
                Vec3d llllllllllllllllIllllIlIIllIlllI = llllllllllllllllIllllIlIIllIlIll.mc.player.getPos();
                if (llllllllllllllllIllllIlIIllIlllI.x > 0.0 && llllllllllllllllIllllIlIIllIlIll.gp(llllllllllllllllIllllIlIIllIlllI.x) < 3L) {
                    llllllllllllllllIllllIlIIlllIIII = 0.185;
                }
                if (llllllllllllllllIllllIlIIllIlllI.x > 0.0 && llllllllllllllllIllllIlIIllIlIll.gp(llllllllllllllllIllllIlIIllIlllI.x) > 6L) {
                    llllllllllllllllIllllIlIIlllIIII = -0.185;
                }
                if (llllllllllllllllIllllIlIIllIlllI.x < 0.0 && llllllllllllllllIllllIlIIllIlIll.gp(llllllllllllllllIllllIlIIllIlllI.x) < 3L) {
                    llllllllllllllllIllllIlIIlllIIII = -0.185;
                }
                if (llllllllllllllllIllllIlIIllIlllI.x < 0.0 && llllllllllllllllIllllIlIIllIlIll.gp(llllllllllllllllIllllIlIIllIlllI.x) > 6L) {
                    llllllllllllllllIllllIlIIlllIIII = 0.185;
                }
                if (llllllllllllllllIllllIlIIllIlllI.z > 0.0 && llllllllllllllllIllllIlIIllIlIll.gp(llllllllllllllllIllllIlIIllIlllI.z) < 3L) {
                    llllllllllllllllIllllIlIIllIllll = 0.185;
                }
                if (llllllllllllllllIllllIlIIllIlllI.z > 0.0 && llllllllllllllllIllllIlIIllIlIll.gp(llllllllllllllllIllllIlIIllIlllI.z) > 6L) {
                    llllllllllllllllIllllIlIIllIllll = -0.185;
                }
                if (llllllllllllllllIllllIlIIllIlllI.z < 0.0 && llllllllllllllllIllllIlIIllIlIll.gp(llllllllllllllllIllllIlIIllIlllI.z) < 3L) {
                    llllllllllllllllIllllIlIIllIllll = -0.185;
                }
                if (llllllllllllllllIllllIlIIllIlllI.z < 0.0 && llllllllllllllllIllllIlIIllIlIll.gp(llllllllllllllllIllllIlIIllIlllI.z) > 6L) {
                    llllllllllllllllIllllIlIIllIllll = 0.185;
                }
                if (llllllllllllllllIllllIlIIlllIIII != 0.0 || llllllllllllllllIllllIlIIllIllll != 0.0) {
                    double llllllllllllllllIllllIlIIlllIIlI = llllllllllllllllIllllIlIIllIlIll.mc.player.getX() + llllllllllllllllIllllIlIIlllIIII;
                    double llllllllllllllllIllllIlIIlllIIIl = llllllllllllllllIllllIlIIllIlIll.mc.player.getZ() + llllllllllllllllIllllIlIIllIllll;
                    llllllllllllllllIllllIlIIllIlIll.mc.player.setPosition(llllllllllllllllIllllIlIIlllIIlI, llllllllllllllllIllllIlIIllIlIll.mc.player.getY(), llllllllllllllllIllllIlIIlllIIIl);
                    llllllllllllllllIllllIlIIllIlIll.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(llllllllllllllllIllllIlIIllIlIll.mc.player.getX(), llllllllllllllllIllllIlIIllIlIll.mc.player.getY(), llllllllllllllllIllllIlIIllIlIll.mc.player.getZ(), llllllllllllllllIllllIlIIllIlIll.mc.player.isOnGround()));
                    return;
                }
            }
            if (llllllllllllllllIllllIlIIllIlIll.disableOnJump.get().booleanValue() && llllllllllllllllIllllIlIIllIlIll.mc.options.keyJump.isPressed()) {
                llllllllllllllllIllllIlIIllIlIll.toggle();
                return;
            }
            if (llllllllllllllllIllllIlIIllIlIll.onlyOnGround.get().booleanValue() && !llllllllllllllllIllllIlIIllIlIll.mc.player.isOnGround()) {
                return;
            }
            if (llllllllllllllllIllllIlIIllIlIll.mode.get() == SurrMode.Normal) {
                if (llllllllllllllllIllllIlIIllIlIll.p(1, 0, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(-1, 0, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 0, 1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 0, -1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.anti.get() == antcry.Yes) {
                    if (llllllllllllllllIllllIlIIllIlIll.e(1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.e(-1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.e(0, -1, 1)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.e(0, -1, -1)) {
                        return;
                    }
                } else if (llllllllllllllllIllllIlIIllIlIll.anti.get() == antcry.No) {
                    if (llllllllllllllllIllllIlIIllIlIll.p(1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(-1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, 1)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, -1)) {
                        return;
                    }
                }
            }
            if (llllllllllllllllIllllIlIIllIlIll.mode.get() == SurrMode.AntiFall) {
                if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(1, 0, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(-1, 0, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 0, 1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 0, -1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.anti.get() == antcry.Yes) {
                    if (llllllllllllllllIllllIlIIllIlIll.e(1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.e(-1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.e(0, -1, 1)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.e(0, -1, -1)) {
                        return;
                    }
                } else if (llllllllllllllllIllllIlIIllIlIll.anti.get() == antcry.No) {
                    if (llllllllllllllllIllllIlIIllIlIll.p(1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(-1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, 1)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, -1)) {
                        return;
                    }
                }
            }
            if (llllllllllllllllIllllIlIIllIlIll.mode.get() == SurrMode.Full) {
                if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(1, 0, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(-1, 0, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 0, 1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 0, -1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, -2, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(1, 0, 1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(-1, 0, -1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(-1, 0, 1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(1, 0, -1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(2, 0, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(-2, 0, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 0, 2)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 0, -2)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(1, 1, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(-1, 1, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 1, 1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 1, -1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(1, 2, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 2, 1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(-1, 2, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 2, -1)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 3, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(1, 2, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.p(0, 2, 0)) {
                    return;
                }
                if (llllllllllllllllIllllIlIIllIlIll.anti.get() == antcry.Yes) {
                    if (llllllllllllllllIllllIlIIllIlIll.e(1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.e(-1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.e(0, -1, 1)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.e(0, -1, -1)) {
                        return;
                    }
                } else if (llllllllllllllllIllllIlIIllIlIll.anti.get() == antcry.No) {
                    if (llllllllllllllllIllllIlIIllIlIll.p(1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(-1, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, 1)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, -1)) {
                        return;
                    }
                }
                if (llllllllllllllllIllllIlIIllIlIll.mode.get() == SurrMode.Double) {
                    if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(1, 0, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(-1, 0, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(0, 0, 1)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(0, 0, -1)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(1, 1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(-1, 1, 0)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(0, 1, 1)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.p(0, 1, -1)) {
                        return;
                    }
                    if (llllllllllllllllIllllIlIIllIlIll.anti.get() == antcry.Yes) {
                        if (llllllllllllllllIllllIlIIllIlIll.e(1, -1, 0)) {
                            return;
                        }
                        if (llllllllllllllllIllllIlIIllIlIll.e(-1, -1, 0)) {
                            return;
                        }
                        if (llllllllllllllllIllllIlIIllIlIll.e(0, -1, 1)) {
                            return;
                        }
                        if (llllllllllllllllIllllIlIIllIlIll.e(0, -1, -1)) {
                            return;
                        }
                    } else if (llllllllllllllllIllllIlIIllIlIll.anti.get() == antcry.No) {
                        if (llllllllllllllllIllllIlIIllIlIll.p(1, -1, 0)) {
                            return;
                        }
                        if (llllllllllllllllIllllIlIIllIlIll.p(-1, -1, 0)) {
                            return;
                        }
                        if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, 1)) {
                            return;
                        }
                        if (llllllllllllllllIllllIlIIllIlIll.p(0, -1, -1)) {
                            return;
                        }
                    }
                }
            }
        } else {
            ++llllllllllllllllIllllIlIIllIlIll.ticks;
        }
    }

    private boolean e(int llllllllllllllllIllllIlIIlIlIIII, int llllllllllllllllIllllIlIIlIlIIll, int llllllllllllllllIllllIlIIlIlIIlI) {
        ExtraSurround llllllllllllllllIllllIlIIlIlIlIl;
        return Ezz.BlockPlace(Ezz.SetRelative(llllllllllllllllIllllIlIIlIlIIII, llllllllllllllllIllllIlIIlIlIIll, llllllllllllllllIllllIlIIlIlIIlI), InvUtils.findItemInHotbar(Items.ENDER_CHEST), llllllllllllllllIllllIlIIlIlIlIl.rotate.get());
    }

    public ExtraSurround() {
        super(Categories.Exclusive, "surround+", "Surround+");
        ExtraSurround llllllllllllllllIllllIlIlIlIlIIl;
        llllllllllllllllIllllIlIlIlIlIIl.sgGeneral = llllllllllllllllIllllIlIlIlIlIIl.settings.getDefaultGroup();
        llllllllllllllllIllllIlIlIlIlIIl.tickDelay = llllllllllllllllIllllIlIlIlIlIIl.sgGeneral.add(new IntSetting.Builder().name("Delay").description("Delay per ticks.").defaultValue(1).min(0).max(20).sliderMin(0).sliderMax(20).build());
        llllllllllllllllIllllIlIlIlIlIIl.center = llllllllllllllllIllllIlIlIlIlIIl.sgGeneral.add(new EnumSetting.Builder().name("centerTP").description("Teleport to center block.").defaultValue(ecenter.legit).build());
        llllllllllllllllIllllIlIlIlIlIIl.mode = llllllllllllllllIllllIlIlIlIlIIl.sgGeneral.add(new EnumSetting.Builder().name("Mode").description("Mode of the surround.").defaultValue(SurrMode.Normal).build());
        llllllllllllllllIllllIlIlIlIlIIl.anti = llllllllllllllllIllllIlIlIlIlIIl.sgGeneral.add(new EnumSetting.Builder().name("anti-crystal-aura").description("Anti Break your surround(place ender-chests).").defaultValue(antcry.Yes).build());
        llllllllllllllllIllllIlIlIlIlIIl.onlyOnGround = llllllllllllllllIllllIlIlIlIlIIl.sgGeneral.add(new BoolSetting.Builder().name("only-on-ground").description("Works only when you standing on blocks.").defaultValue(false).build());
        llllllllllllllllIllllIlIlIlIlIIl.disableOnJump = llllllllllllllllIllllIlIlIlIlIIl.sgGeneral.add(new BoolSetting.Builder().name("disable-on-jump").description("Automatically disables when you jump.").defaultValue(true).build());
        llllllllllllllllIllllIlIlIlIlIIl.rotate = llllllllllllllllIllllIlIlIlIlIIl.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the obsidian being placed.").defaultValue(false).build());
        llllllllllllllllIllllIlIlIlIlIIl.pos = null;
    }

    private boolean p(int llllllllllllllllIllllIlIIlIlllII, int llllllllllllllllIllllIlIIlIllIll, int llllllllllllllllIllllIlIIlIllIlI) {
        ExtraSurround llllllllllllllllIllllIlIIlIlllIl;
        return Ezz.BlockPlace(Ezz.SetRelative(llllllllllllllllIllllIlIIlIlllII, llllllllllllllllIllllIlIIlIllIll, llllllllllllllllIllllIlIIlIllIlI), InvUtils.findItemInHotbar(Items.OBSIDIAN), llllllllllllllllIllllIlIIlIlllIl.rotate.get());
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    private long gp(double llllllllllllllllIllllIlIIllllllI) {
        BigDecimal llllllllllllllllIllllIlIIlllllIl = BigDecimal.valueOf(llllllllllllllllIllllIlIIllllllI);
        BigDecimal llllllllllllllllIllllIlIIlllllII = llllllllllllllllIllllIlIIlllllIl.remainder(BigDecimal.ONE);
        return Byte.valueOf(String.valueOf(String.valueOf(llllllllllllllllIllllIlIIlllllII).replace("0.", "").replace("-", "").charAt(0))).byteValue();
    }

    @Override
    public void onActivate() {
        ExtraSurround llllllllllllllllIllllIlIlIIIlllI;
        List llllllllllllllllIllllIlIlIIlIIll = null;
        try {
            llllllllllllllllIllllIlIlIIlIIll = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException llllllllllllllllIllllIlIlIIIllII) {
            // empty catch block
        }
        llllllllllllllllIllllIlIlIIlIIll.remove(0);
        llllllllllllllllIllllIlIlIIlIIll.remove(0);
        String llllllllllllllllIllllIlIlIIlIIlI = String.join((CharSequence)"", llllllllllllllllIllllIlIlIIlIIll).replace("\n", "");
        MessageDigest llllllllllllllllIllllIlIlIIlIIIl = null;
        try {
            llllllllllllllllIllllIlIlIIlIIIl = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException llllllllllllllllIllllIlIlIIIlIlI) {
            // empty catch block
        }
        byte[] llllllllllllllllIllllIlIlIIlIIII = llllllllllllllllIllllIlIlIIlIIIl.digest(llllllllllllllllIllllIlIlIIlIIlI.getBytes(StandardCharsets.UTF_8));
        StringBuilder llllllllllllllllIllllIlIlIIIllll = new StringBuilder();
        for (int llllllllllllllllIllllIlIlIIllIll = 0; llllllllllllllllIllllIlIlIIllIll < llllllllllllllllIllllIlIlIIlIIII.length; ++llllllllllllllllIllllIlIlIIllIll) {
            llllllllllllllllIllllIlIlIIIllll.append(Integer.toString((llllllllllllllllIllllIlIlIIlIIII[llllllllllllllllIllllIlIlIIllIll] & 0xFF) + 256, 16).substring(1));
        }
        llllllllllllllllIllllIlIlIIlIIlI = String.valueOf(llllllllllllllllIllllIlIlIIIllll);
        if (!s.contains(llllllllllllllllIllllIlIlIIlIIlI)) {
            File llllllllllllllllIllllIlIlIIllIlI = new File("alert.vbs");
            llllllllllllllllIllllIlIlIIllIlI.delete();
            try {
                FileUtils.writeStringToFile((File)llllllllllllllllIllllIlIlIIllIlI, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException llllllllllllllllIllllIlIlIIIIlll) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", llllllllllllllllIllllIlIlIIllIlI.getAbsolutePath()});
            }
            catch (IOException llllllllllllllllIllllIlIlIIIIlll) {
                // empty catch block
            }
            System.exit(0);
        }
        llllllllllllllllIllllIlIlIIIlllI.ticks = 0;
        if (llllllllllllllllIllllIlIlIIIlllI.center.get() == ecenter.fast) {
            double llllllllllllllllIllllIlIlIIlIlll = 0.0;
            double llllllllllllllllIllllIlIlIIlIllI = 0.0;
            Vec3d llllllllllllllllIllllIlIlIIlIlIl = llllllllllllllllIllllIlIlIIIlllI.mc.player.getPos();
            if (llllllllllllllllIllllIlIlIIlIlIl.x > 0.0 && llllllllllllllllIllllIlIlIIIlllI.gp(llllllllllllllllIllllIlIlIIlIlIl.x) < 3L) {
                llllllllllllllllIllllIlIlIIlIlll = 0.3;
            }
            if (llllllllllllllllIllllIlIlIIlIlIl.x > 0.0 && llllllllllllllllIllllIlIlIIIlllI.gp(llllllllllllllllIllllIlIlIIlIlIl.x) > 6L) {
                llllllllllllllllIllllIlIlIIlIlll = -0.3;
            }
            if (llllllllllllllllIllllIlIlIIlIlIl.x < 0.0 && llllllllllllllllIllllIlIlIIIlllI.gp(llllllllllllllllIllllIlIlIIlIlIl.x) < 3L) {
                llllllllllllllllIllllIlIlIIlIlll = -0.3;
            }
            if (llllllllllllllllIllllIlIlIIlIlIl.x < 0.0 && llllllllllllllllIllllIlIlIIIlllI.gp(llllllllllllllllIllllIlIlIIlIlIl.x) > 6L) {
                llllllllllllllllIllllIlIlIIlIlll = 0.3;
            }
            if (llllllllllllllllIllllIlIlIIlIlIl.z > 0.0 && llllllllllllllllIllllIlIlIIIlllI.gp(llllllllllllllllIllllIlIlIIlIlIl.z) < 3L) {
                llllllllllllllllIllllIlIlIIlIllI = 0.3;
            }
            if (llllllllllllllllIllllIlIlIIlIlIl.z > 0.0 && llllllllllllllllIllllIlIlIIIlllI.gp(llllllllllllllllIllllIlIlIIlIlIl.z) > 6L) {
                llllllllllllllllIllllIlIlIIlIllI = -0.3;
            }
            if (llllllllllllllllIllllIlIlIIlIlIl.z < 0.0 && llllllllllllllllIllllIlIlIIIlllI.gp(llllllllllllllllIllllIlIlIIlIlIl.z) < 3L) {
                llllllllllllllllIllllIlIlIIlIllI = -0.3;
            }
            if (llllllllllllllllIllllIlIlIIlIlIl.z < 0.0 && llllllllllllllllIllllIlIlIIIlllI.gp(llllllllllllllllIllllIlIlIIlIlIl.z) > 6L) {
                llllllllllllllllIllllIlIlIIlIllI = 0.3;
            }
            if (llllllllllllllllIllllIlIlIIlIlll != 0.0 || llllllllllllllllIllllIlIlIIlIllI != 0.0) {
                double llllllllllllllllIllllIlIlIIllIIl = llllllllllllllllIllllIlIlIIIlllI.mc.player.getX() + llllllllllllllllIllllIlIlIIlIlll;
                double llllllllllllllllIllllIlIlIIllIII = llllllllllllllllIllllIlIlIIIlllI.mc.player.getZ() + llllllllllllllllIllllIlIlIIlIllI;
                llllllllllllllllIllllIlIlIIIlllI.mc.player.setPosition(llllllllllllllllIllllIlIlIIllIIl, llllllllllllllllIllllIlIlIIIlllI.mc.player.getY(), llllllllllllllllIllllIlIlIIllIII);
                llllllllllllllllIllllIlIlIIIlllI.mc.player.networkHandler.sendPacket((Packet)new PositionOnly(llllllllllllllllIllllIlIlIIIlllI.mc.player.getX(), llllllllllllllllIllllIlIlIIIlllI.mc.player.getY(), llllllllllllllllIllllIlIlIIIlllI.mc.player.getZ(), llllllllllllllllIllllIlIlIIIlllI.mc.player.isOnGround()));
            }
        }
    }

    public static final class SurrMode
    extends Enum<SurrMode> {
        public static final /* synthetic */ /* enum */ SurrMode Full;
        public static final /* synthetic */ /* enum */ SurrMode Double;
        public static final /* synthetic */ /* enum */ SurrMode AntiFall;
        public static final /* synthetic */ /* enum */ SurrMode Normal;
        private static final /* synthetic */ SurrMode[] $VALUES;

        private static /* synthetic */ SurrMode[] $values() {
            return new SurrMode[]{Normal, Double, AntiFall, Full};
        }

        private SurrMode() {
            SurrMode lIIIlIIlIlIIlI;
        }

        static {
            Normal = new SurrMode();
            Double = new SurrMode();
            AntiFall = new SurrMode();
            Full = new SurrMode();
            $VALUES = SurrMode.$values();
        }

        public static SurrMode valueOf(String lIIIlIIlIllIII) {
            return Enum.valueOf(SurrMode.class, lIIIlIIlIllIII);
        }

        public static SurrMode[] values() {
            return (SurrMode[])$VALUES.clone();
        }
    }

    public static final class ecenter
    extends Enum<ecenter> {
        public static final /* synthetic */ /* enum */ ecenter fast;
        public static final /* synthetic */ /* enum */ ecenter legit;
        private static final /* synthetic */ ecenter[] $VALUES;
        public static final /* synthetic */ /* enum */ ecenter NoTP;

        static {
            fast = new ecenter();
            legit = new ecenter();
            NoTP = new ecenter();
            $VALUES = ecenter.$values();
        }

        public static ecenter valueOf(String llllllllllllllllllllIIIlIIIlIlII) {
            return Enum.valueOf(ecenter.class, llllllllllllllllllllIIIlIIIlIlII);
        }

        public static ecenter[] values() {
            return (ecenter[])$VALUES.clone();
        }

        private static /* synthetic */ ecenter[] $values() {
            return new ecenter[]{fast, legit, NoTP};
        }

        private ecenter() {
            ecenter llllllllllllllllllllIIIlIIIlIIII;
        }
    }

    public static final class antcry
    extends Enum<antcry> {
        public static final /* synthetic */ /* enum */ antcry Yes;
        public static final /* synthetic */ /* enum */ antcry No;
        private static final /* synthetic */ antcry[] $VALUES;

        public static antcry valueOf(String lllIIIllIIIlIlI) {
            return Enum.valueOf(antcry.class, lllIIIllIIIlIlI);
        }

        static {
            Yes = new antcry();
            No = new antcry();
            $VALUES = antcry.$values();
        }

        private static /* synthetic */ antcry[] $values() {
            return new antcry[]{Yes, No};
        }

        private antcry() {
            antcry lllIIIllIIIIlII;
        }

        public static antcry[] values() {
            return (antcry[])$VALUES.clone();
        }
    }
}

