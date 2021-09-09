/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.block.BlockState
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
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.io.FileUtils;

public class AutoHighway
extends Module {
    private static /* synthetic */ List<String> s;
    private /* synthetic */ int ticks;
    private /* synthetic */ boolean return_;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Mutable blockPos;
    private final /* synthetic */ Setting<Boolean> disableOnJump;
    private final /* synthetic */ Setting<Integer> HighwaySize;
    private /* synthetic */ int highwaySize;
    private final /* synthetic */ Setting<Boolean> rotate;
    private /* synthetic */ Direction direction;
    private final /* synthetic */ Setting<Integer> tickDelay;

    @EventHandler
    private void onTick(TickEvent.Pre lIIIIIIlllIlllI) {
        AutoHighway lIIIIIIlllIllIl;
        if (lIIIIIIlllIllIl.disableOnJump.get().booleanValue() && lIIIIIIlllIllIl.mc.options.keyJump.isPressed()) {
            lIIIIIIlllIllIl.toggle();
            return;
        }
        if (InvUtils.findItemInHotbar(Items.OBSIDIAN) == -1) {
            return;
        }
        lIIIIIIlllIllIl.highwaySize = lIIIIIIlllIllIl.getSize();
        lIIIIIIlllIllIl.return_ = false;
        if (lIIIIIIlllIllIl.getDistance((PlayerEntity)lIIIIIIlllIllIl.mc.player) > 12) {
            return;
        }
        if (lIIIIIIlllIllIl.ticks >= lIIIIIIlllIllIl.tickDelay.get()) {
            lIIIIIIlllIllIl.ticks = 0;
            if (lIIIIIIlllIllIl.direction == Direction.SOUTH) {
                if (lIIIIIIlllIllIl.highwaySize == 3) {
                    boolean lIIIIIlllIIIlll = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlllIIIllI = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlllIIIlIl = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlllIIIlII = lIIIIIIlllIllIl.place(-2, 1, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlllIIIIll = lIIIIIIlllIllIl.place(2, 1, 0);
                    if (lIIIIIlllIIIlll && lIIIIIlllIIIllI && lIIIIIlllIIIlIl && lIIIIIlllIIIlII && lIIIIIlllIIIIll) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                }
                if (lIIIIIIlllIllIl.highwaySize == 4) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                }
                if (lIIIIIIlllIllIl.highwaySize == 6) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 5) {
                    boolean lIIIIIlllIIIIlI = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlllIIIIIl = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlllIIIIII = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIllllll = lIIIIIIlllIllIl.place(-2, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlllllI = lIIIIIIlllIllIl.place(2, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIllllIl = lIIIIIIlllIllIl.place(-3, 1, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIllllII = lIIIIIIlllIllIl.place(3, 1, 0);
                    if (lIIIIIlllIIIIlI && lIIIIIlllIIIIIl && lIIIIIlllIIIIII && lIIIIIllIllllll && lIIIIIllIlllllI && lIIIIIllIllllIl && lIIIIIllIllllII) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 7) {
                    boolean lIIIIIllIlllIll = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlllIlI = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlllIIl = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlllIII = lIIIIIIlllIllIl.place(-2, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIllIlll = lIIIIIIlllIllIl.place(2, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIllIllI = lIIIIIIlllIllIl.place(-3, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIllIlIl = lIIIIIIlllIllIl.place(3, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIllIlII = lIIIIIIlllIllIl.place(-4, 1, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIllIIll = lIIIIIIlllIllIl.place(4, 1, 0);
                    if (lIIIIIllIlllIll && lIIIIIllIlllIlI && lIIIIIllIlllIIl && lIIIIIllIlllIII && lIIIIIllIllIlll && lIIIIIllIllIllI && lIIIIIllIllIlIl && lIIIIIllIllIlII && lIIIIIllIllIIll) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                }
            } else if (lIIIIIIlllIllIl.direction == Direction.WEST) {
                if (lIIIIIIlllIllIl.highwaySize == 3) {
                    boolean lIIIIIllIllIIlI = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIllIIIl = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIllIIII = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIllll = lIIIIIIlllIllIl.place(0, 1, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIlllI = lIIIIIIlllIllIl.place(0, 1, 2);
                    if (lIIIIIllIllIIlI && lIIIIIllIllIIIl && lIIIIIllIllIIII && lIIIIIllIlIllll && lIIIIIllIlIlllI) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 4) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 6) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 5) {
                    boolean lIIIIIllIlIllIl = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIllII = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIlIll = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIlIlI = lIIIIIIlllIllIl.place(0, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIlIIl = lIIIIIIlllIllIl.place(0, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIlIII = lIIIIIIlllIllIl.place(0, 1, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIIlll = lIIIIIIlllIllIl.place(0, 1, 3);
                    if (lIIIIIllIlIllIl && lIIIIIllIlIllII && lIIIIIllIlIlIll && lIIIIIllIlIlIlI && lIIIIIllIlIlIIl && lIIIIIllIlIlIII && lIIIIIllIlIIlll) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 7) {
                    boolean lIIIIIllIlIIllI = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIIlIl = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIIlII = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIIIll = lIIIIIIlllIllIl.place(0, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIIIlI = lIIIIIIlllIllIl.place(0, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIIIIl = lIIIIIIlllIllIl.place(0, 0, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIlIIIII = lIIIIIIlllIllIl.place(0, 0, 3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIlllll = lIIIIIIlllIllIl.place(0, 1, -4);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIllllI = lIIIIIIlllIllIl.place(0, 1, 4);
                    if (lIIIIIllIlIIllI && lIIIIIllIlIIlIl && lIIIIIllIlIIlII && lIIIIIllIlIIIll && lIIIIIllIlIIIlI && lIIIIIllIlIIIIl && lIIIIIllIlIIIII && lIIIIIllIIlllll && lIIIIIllIIllllI) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                }
            } else if (lIIIIIIlllIllIl.direction == Direction.NORTH) {
                if (lIIIIIIlllIllIl.highwaySize == 3) {
                    boolean lIIIIIllIIlllIl = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIlllII = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIllIll = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIllIlI = lIIIIIIlllIllIl.place(-2, 1, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIllIIl = lIIIIIIlllIllIl.place(2, 1, 0);
                    if (lIIIIIllIIlllIl && lIIIIIllIIlllII && lIIIIIllIIllIll && lIIIIIllIIllIlI && lIIIIIllIIllIIl) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 4) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 6) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 5) {
                    boolean lIIIIIllIIllIII = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIlIlll = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIlIllI = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIlIlIl = lIIIIIIlllIllIl.place(-2, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIlIlII = lIIIIIIlllIllIl.place(2, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIlIIll = lIIIIIIlllIllIl.place(-3, 1, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIlIIlI = lIIIIIIlllIllIl.place(3, 1, 0);
                    if (lIIIIIllIIllIII && lIIIIIllIIlIlll && lIIIIIllIIlIllI && lIIIIIllIIlIlIl && lIIIIIllIIlIlII && lIIIIIllIIlIIll && lIIIIIllIIlIIlI) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 7) {
                    boolean lIIIIIllIIlIIIl = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIlIIII = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIllll = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIlllI = lIIIIIIlllIllIl.place(-2, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIllIl = lIIIIIIlllIllIl.place(2, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIllII = lIIIIIIlllIllIl.place(-3, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIlIll = lIIIIIIlllIllIl.place(3, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIlIlI = lIIIIIIlllIllIl.place(-4, 1, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIlIIl = lIIIIIIlllIllIl.place(4, 1, 0);
                    if (lIIIIIllIIlIIIl && lIIIIIllIIlIIII && lIIIIIllIIIllll && lIIIIIllIIIlllI && lIIIIIllIIIllIl && lIIIIIllIIIllII && lIIIIIllIIIlIll && lIIIIIllIIIlIlI && lIIIIIllIIIlIIl) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                }
            } else if (lIIIIIIlllIllIl.direction == Direction.EAST) {
                if (lIIIIIIlllIllIl.highwaySize == 3) {
                    boolean lIIIIIllIIIlIII = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIIlll = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIIllI = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIIlIl = lIIIIIIlllIllIl.place(0, 1, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIIlII = lIIIIIIlllIllIl.place(0, 1, 2);
                    if (lIIIIIllIIIlIII && lIIIIIllIIIIlll && lIIIIIllIIIIllI && lIIIIIllIIIIlIl && lIIIIIllIIIIlII) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 4) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 6) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 5) {
                    boolean lIIIIIllIIIIIll = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIIIlI = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIIIIl = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIllIIIIIII = lIIIIIIlllIllIl.place(0, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlllllll = lIIIIIIlllIllIl.place(0, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllllllI = lIIIIIIlllIllIl.place(0, 1, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlllllIl = lIIIIIIlllIllIl.place(0, 1, 3);
                    if (lIIIIIllIIIIIll && lIIIIIllIIIIIlI && lIIIIIllIIIIIIl && lIIIIIllIIIIIII && lIIIIIlIlllllll && lIIIIIlIllllllI && lIIIIIlIlllllIl) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 7) {
                    boolean lIIIIIlIlllllII = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllllIll = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllllIlI = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllllIIl = lIIIIIIlllIllIl.place(0, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllllIII = lIIIIIIlllIllIl.place(0, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlllIlll = lIIIIIIlllIllIl.place(0, 0, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlllIllI = lIIIIIIlllIllIl.place(0, 0, 3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlllIlIl = lIIIIIIlllIllIl.place(0, 1, -4);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlllIlII = lIIIIIIlllIllIl.place(0, 1, 4);
                    if (lIIIIIlIlllllII && lIIIIIlIllllIll && lIIIIIlIllllIlI && lIIIIIlIllllIIl && lIIIIIlIllllIII && lIIIIIlIlllIlll && lIIIIIlIlllIllI && lIIIIIlIlllIlIl && lIIIIIlIlllIlII) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                }
            } else if (lIIIIIIlllIllIl.direction == Direction.EAST_SOUTH) {
                if (lIIIIIIlllIllIl.highwaySize == 3) {
                    boolean lIIIIIlIlllIIll = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlllIIlI = lIIIIIIlllIllIl.place(1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlllIIIl = lIIIIIIlllIllIl.place(-1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlllIIII = lIIIIIIlllIllIl.place(1, 1, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIllll = lIIIIIIlllIllIl.place(-2, 1, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIlllI = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIllIl = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIlIlllIIll && lIIIIIlIlllIIlI && lIIIIIlIlllIIIl && lIIIIIlIlllIIII && lIIIIIlIllIllll && lIIIIIlIllIlllI && lIIIIIlIllIllIl) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 4) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 6) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 5) {
                    boolean lIIIIIlIllIllII = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIlIll = lIIIIIIlllIllIl.place(1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIlIlI = lIIIIIIlllIllIl.place(-1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIlIIl = lIIIIIIlllIllIl.place(2, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIlIII = lIIIIIIlllIllIl.place(-2, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIIlll = lIIIIIIlllIllIl.place(2, 1, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIIllI = lIIIIIIlllIllIl.place(-3, 1, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIIlIl = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIIlII = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIIIll = lIIIIIIlllIllIl.place(2, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIIIlI = lIIIIIIlllIllIl.place(-1, 0, 2);
                    if (lIIIIIlIllIllII && lIIIIIlIllIlIll && lIIIIIlIllIlIlI && lIIIIIlIllIlIIl && lIIIIIlIllIlIII && lIIIIIlIllIIlll && lIIIIIlIllIIllI && lIIIIIlIllIIlIl && lIIIIIlIllIIlII && lIIIIIlIllIIIll && lIIIIIlIllIIIlI) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 7) {
                    boolean lIIIIIlIllIIIIl = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIllIIIII = lIIIIIIlllIllIl.place(1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIlllll = lIIIIIIlllIllIl.place(-1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIllllI = lIIIIIIlllIllIl.place(2, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIlllIl = lIIIIIIlllIllIl.place(-2, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIlllII = lIIIIIIlllIllIl.place(3, 0, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIllIll = lIIIIIIlllIllIl.place(-3, 0, 3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIllIlI = lIIIIIIlllIllIl.place(3, 1, -4);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIllIIl = lIIIIIIlllIllIl.place(-4, 1, 3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIllIII = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIlIlll = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIlIllI = lIIIIIIlllIllIl.place(2, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIlIlIl = lIIIIIIlllIllIl.place(-1, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIlIlII = lIIIIIIlllIllIl.place(3, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIlIIll = lIIIIIIlllIllIl.place(-2, 0, 3);
                    if (lIIIIIlIllIIIIl && lIIIIIlIllIIIII && lIIIIIlIlIlllll && lIIIIIlIlIllllI && lIIIIIlIlIlllIl && lIIIIIlIlIlllII && lIIIIIlIlIllIll && lIIIIIlIlIllIlI && lIIIIIlIlIllIIl && lIIIIIlIlIllIII && lIIIIIlIlIlIlll && lIIIIIlIlIlIllI && lIIIIIlIlIlIlIl && lIIIIIlIlIlIlII && lIIIIIlIlIlIIll) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                }
            } else if (lIIIIIIlllIllIl.direction == Direction.SOUTH_WEST) {
                if (lIIIIIIlllIllIl.highwaySize == 3) {
                    boolean lIIIIIlIlIlIIlI = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIlIIIl = lIIIIIIlllIllIl.place(-1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIlIIII = lIIIIIIlllIllIl.place(1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIllll = lIIIIIIlllIllIl.place(-1, 1, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIlllI = lIIIIIIlllIllIl.place(2, 1, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIllIl = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIllII = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIlIlIlIIlI && lIIIIIlIlIlIIIl && lIIIIIlIlIlIIII && lIIIIIlIlIIllll && lIIIIIlIlIIlllI && lIIIIIlIlIIllIl && lIIIIIlIlIIllII) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 4) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 6) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 5) {
                    boolean lIIIIIlIlIIlIll = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIlIlI = lIIIIIIlllIllIl.place(-1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIlIIl = lIIIIIIlllIllIl.place(1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIlIII = lIIIIIIlllIllIl.place(-2, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIIlll = lIIIIIIlllIllIl.place(2, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIIllI = lIIIIIIlllIllIl.place(-2, 1, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIIlIl = lIIIIIIlllIllIl.place(3, 1, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIIlII = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIIIll = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIIIlI = lIIIIIIlllIllIl.place(-2, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIlIIIIIl = lIIIIIIlllIllIl.place(1, 0, 2);
                    if (lIIIIIlIlIIlIll && lIIIIIlIlIIlIlI && lIIIIIlIlIIlIIl && lIIIIIlIlIIlIII && lIIIIIlIlIIIlll && lIIIIIlIlIIIllI && lIIIIIlIlIIIlIl && lIIIIIlIlIIIlII && lIIIIIlIlIIIIll && lIIIIIlIlIIIIlI && lIIIIIlIlIIIIIl) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 7) {
                    boolean lIIIIIlIlIIIIII = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIllllll = lIIIIIIlllIllIl.place(-1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlllllI = lIIIIIIlllIllIl.place(1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIllllIl = lIIIIIIlllIllIl.place(-2, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIllllII = lIIIIIIlllIllIl.place(2, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlllIll = lIIIIIIlllIllIl.place(-3, 0, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlllIlI = lIIIIIIlllIllIl.place(3, 0, 3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlllIIl = lIIIIIIlllIllIl.place(-3, 1, -4);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlllIII = lIIIIIIlllIllIl.place(4, 1, 3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIllIlll = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIllIllI = lIIIIIIlllIllIl.place(0, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIllIlIl = lIIIIIIlllIllIl.place(-2, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIllIlII = lIIIIIIlllIllIl.place(1, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIllIIll = lIIIIIIlllIllIl.place(-3, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIllIIlI = lIIIIIIlllIllIl.place(2, 0, 3);
                    if (lIIIIIlIlIIIIII && lIIIIIlIIllllll && lIIIIIlIIlllllI && lIIIIIlIIllllIl && lIIIIIlIIllllII && lIIIIIlIIlllIll && lIIIIIlIIlllIlI && lIIIIIlIIlllIIl && lIIIIIlIIlllIII && lIIIIIlIIllIlll && lIIIIIlIIllIllI && lIIIIIlIIllIlIl && lIIIIIlIIllIlII && lIIIIIlIIllIIll && lIIIIIlIIllIIlI) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                }
            } else if (lIIIIIIlllIllIl.direction == Direction.WEST_NORTH) {
                if (lIIIIIIlllIllIl.highwaySize == 3) {
                    boolean lIIIIIlIIllIIIl = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIllIIII = lIIIIIIlllIllIl.place(-1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIllll = lIIIIIIlllIllIl.place(1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIlllI = lIIIIIIlllIllIl.place(-1, 1, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIllIl = lIIIIIIlllIllIl.place(2, 1, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIllII = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIlIll = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIlIIllIIIl && lIIIIIlIIllIIII && lIIIIIlIIlIllll && lIIIIIlIIlIlllI && lIIIIIlIIlIllIl && lIIIIIlIIlIllII && lIIIIIlIIlIlIll) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 4) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 6) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 5) {
                    boolean lIIIIIlIIlIlIlI = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIlIIl = lIIIIIIlllIllIl.place(-1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIlIII = lIIIIIIlllIllIl.place(1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIIlll = lIIIIIIlllIllIl.place(-2, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIIllI = lIIIIIIlllIllIl.place(2, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIIlIl = lIIIIIIlllIllIl.place(-2, 1, 3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIIlII = lIIIIIIlllIllIl.place(3, 1, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIIIll = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIIIlI = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIIIIl = lIIIIIIlllIllIl.place(-2, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIlIIIII = lIIIIIIlllIllIl.place(1, 0, -2);
                    if (lIIIIIlIIlIlIlI && lIIIIIlIIlIlIIl && lIIIIIlIIlIlIII && lIIIIIlIIlIIlll && lIIIIIlIIlIIllI && lIIIIIlIIlIIlIl && lIIIIIlIIlIIlII && lIIIIIlIIlIIIll && lIIIIIlIIlIIIlI && lIIIIIlIIlIIIIl && lIIIIIlIIlIIIII) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 7) {
                    boolean lIIIIIlIIIlllll = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIllllI = lIIIIIIlllIllIl.place(-1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIlllIl = lIIIIIIlllIllIl.place(1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIlllII = lIIIIIIlllIllIl.place(-2, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIllIll = lIIIIIIlllIllIl.place(2, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIllIlI = lIIIIIIlllIllIl.place(-3, 0, 3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIllIIl = lIIIIIIlllIllIl.place(3, 0, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIllIII = lIIIIIIlllIllIl.place(-3, 1, 4);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIlIlll = lIIIIIIlllIllIl.place(4, 1, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIlIllI = lIIIIIIlllIllIl.place(-1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIlIlIl = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIlIlII = lIIIIIIlllIllIl.place(-2, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIlIIll = lIIIIIIlllIllIl.place(1, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIlIIlI = lIIIIIIlllIllIl.place(-3, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIlIIIl = lIIIIIIlllIllIl.place(2, 0, -3);
                    if (lIIIIIlIIIlllll && lIIIIIlIIIllllI && lIIIIIlIIIlllIl && lIIIIIlIIIlllII && lIIIIIlIIIllIll && lIIIIIlIIIllIlI && lIIIIIlIIIllIIl && lIIIIIlIIIllIII && lIIIIIlIIIlIlll && lIIIIIlIIIlIllI && lIIIIIlIIIlIlIl && lIIIIIlIIIlIlII && lIIIIIlIIIlIIll && lIIIIIlIIIlIIlI && lIIIIIlIIIlIIIl) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                }
            } else if (lIIIIIIlllIllIl.direction == Direction.NORTH_EAST) {
                if (lIIIIIIlllIllIl.highwaySize == 3) {
                    boolean lIIIIIlIIIlIIII = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIllll = lIIIIIIlllIllIl.place(1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIlllI = lIIIIIIlllIllIl.place(-1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIllIl = lIIIIIIlllIllIl.place(1, 1, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIllII = lIIIIIIlllIllIl.place(-2, 1, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIlIll = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIlIlI = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIlIIIlIIII && lIIIIIlIIIIllll && lIIIIIlIIIIlllI && lIIIIIlIIIIllIl && lIIIIIlIIIIllII && lIIIIIlIIIIlIll && lIIIIIlIIIIlIlI) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 4) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 6) {
                    ChatUtils.moduleError(lIIIIIIlllIllIl, "This format is not supported.", new Object[0]);
                    lIIIIIIlllIllIl.toggle();
                } else if (lIIIIIIlllIllIl.highwaySize == 5) {
                    boolean lIIIIIlIIIIlIIl = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIlIII = lIIIIIIlllIllIl.place(1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIIlll = lIIIIIIlllIllIl.place(-1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIIllI = lIIIIIIlllIllIl.place(2, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIIlIl = lIIIIIIlllIllIl.place(-2, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIIlII = lIIIIIIlllIllIl.place(2, 1, 3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIIIll = lIIIIIIlllIllIl.place(-3, 1, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIIIlI = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIIIIl = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIlIIIIIIII = lIIIIIIlllIllIl.place(2, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIllllllll = lIIIIIIlllIllIl.place(-1, 0, -2);
                    if (lIIIIIlIIIIlIIl && lIIIIIlIIIIlIII && lIIIIIlIIIIIlll && lIIIIIlIIIIIllI && lIIIIIlIIIIIlIl && lIIIIIlIIIIIlII && lIIIIIlIIIIIIll && lIIIIIlIIIIIIlI && lIIIIIlIIIIIIIl && lIIIIIlIIIIIIII && lIIIIIIllllllll) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                } else if (lIIIIIIlllIllIl.highwaySize == 7) {
                    boolean lIIIIIIlllllllI = lIIIIIIlllIllIl.place(0, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIllllllIl = lIIIIIIlllIllIl.place(1, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIllllllII = lIIIIIIlllIllIl.place(-1, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIlllllIll = lIIIIIIlllIllIl.place(2, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIlllllIlI = lIIIIIIlllIllIl.place(-2, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIlllllIIl = lIIIIIIlllIllIl.place(3, 0, 3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIlllllIII = lIIIIIIlllIllIl.place(-3, 0, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIllllIlll = lIIIIIIlllIllIl.place(3, 1, 4);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIllllIllI = lIIIIIIlllIllIl.place(-4, 1, -3);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIllllIlIl = lIIIIIIlllIllIl.place(1, 0, 0);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIllllIlII = lIIIIIIlllIllIl.place(0, 0, -1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIllllIIll = lIIIIIIlllIllIl.place(2, 0, 1);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIllllIIlI = lIIIIIIlllIllIl.place(-1, 0, -2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIllllIIIl = lIIIIIIlllIllIl.place(3, 0, 2);
                    if (lIIIIIIlllIllIl.return_) {
                        return;
                    }
                    boolean lIIIIIIllllIIII = lIIIIIIlllIllIl.place(-2, 0, -3);
                    if (lIIIIIIlllllllI && lIIIIIIllllllIl && lIIIIIIllllllII && lIIIIIIlllllIll && lIIIIIIlllllIlI && lIIIIIIlllllIIl && lIIIIIIlllllIII && lIIIIIIllllIlll && lIIIIIIllllIllI && lIIIIIIllllIlIl && lIIIIIIllllIlII && lIIIIIIllllIIll && lIIIIIIllllIIlI && lIIIIIIllllIIIl && lIIIIIIllllIIII) {
                        lIIIIIIlllIllIl.nextLayer();
                    }
                }
            }
        } else {
            ++lIIIIIIlllIllIl.ticks;
        }
    }

    private void nextLayer() {
        AutoHighway lIIIIIIlIlllllI;
        if (lIIIIIIlIlllllI.direction == Direction.SOUTH) {
            lIIIIIIlIlllllI.changeBlockPos(0, 0, 1);
        } else if (lIIIIIIlIlllllI.direction == Direction.WEST) {
            lIIIIIIlIlllllI.changeBlockPos(-1, 0, 0);
        } else if (lIIIIIIlIlllllI.direction == Direction.NORTH) {
            lIIIIIIlIlllllI.changeBlockPos(0, 0, -1);
        } else if (lIIIIIIlIlllllI.direction == Direction.EAST) {
            lIIIIIIlIlllllI.changeBlockPos(1, 0, 0);
        } else if (lIIIIIIlIlllllI.direction == Direction.EAST_SOUTH) {
            lIIIIIIlIlllllI.changeBlockPos(1, 0, 1);
        } else if (lIIIIIIlIlllllI.direction == Direction.SOUTH_WEST) {
            lIIIIIIlIlllllI.changeBlockPos(-1, 0, 1);
        } else if (lIIIIIIlIlllllI.direction == Direction.WEST_NORTH) {
            lIIIIIIlIlllllI.changeBlockPos(-1, 0, -1);
        } else if (lIIIIIIlIlllllI.direction == Direction.NORTH_EAST) {
            lIIIIIIlIlllllI.changeBlockPos(1, 0, -1);
        }
    }

    private int getDistance(PlayerEntity lIIIIIIllIllIlI) {
        AutoHighway lIIIIIIllIllIll;
        return (int)Math.round(lIIIIIIllIllIlI.squaredDistanceTo((double)lIIIIIIllIllIll.blockPos.getX(), (double)((float)lIIIIIIllIllIll.blockPos.getY() - lIIIIIIllIllIlI.getStandingEyeHeight()), (double)lIIIIIIllIllIll.blockPos.getZ()));
    }

    static {
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4", "66f39e0e0a30f1b92549b2002de842ed6667914a4088264304dbfb63489e3b93b621f4738561ee4d34924a27e7f5bedc2a9bc9995eb12e97e6af37bdb46de856", "4467f402ae533470cbb23cbf4be622c1050253ac4939d8afc31c0cfd463243e44d06ac5278f0f2470253d91299ab8c03834eea6d57a3792dec4e7c15c89cba73", "e3c8b9b4345ecc4e507058c3d013a80a4ce9c652ea96a716bd42821f58515e1a8b299060250c0d0cd0f72e34a506f500e659bf0dff81e365d18e0b42ad6cd468", "106bf5173aa80ddec866537648142a0d4aaa787db41fa86727b465ff02aa0e6cabf83e924410f6c1d038887840997155150436520cc5ee51f23c2201cd65304b"));
    }

    private void changeBlockPos(int lIIIIIIlIllIlll, int lIIIIIIlIllIIlI, int lIIIIIIlIllIIIl) {
        AutoHighway lIIIIIIlIlllIII;
        lIIIIIIlIlllIII.blockPos.set(lIIIIIIlIlllIII.blockPos.getX() + lIIIIIIlIllIlll, lIIIIIIlIlllIII.blockPos.getY() + lIIIIIIlIllIIlI, lIIIIIIlIlllIII.blockPos.getZ() + lIIIIIIlIllIIIl);
    }

    public AutoHighway() {
        super(Categories.Exclusive, "auto-highway", "Automatically build highway.");
        AutoHighway lIIIIIlllllIIIl;
        lIIIIIlllllIIIl.sgGeneral = lIIIIIlllllIIIl.settings.getDefaultGroup();
        lIIIIIlllllIIIl.tickDelay = lIIIIIlllllIIIl.sgGeneral.add(new IntSetting.Builder().name("Delay").description("Block per ticks.").defaultValue(1).min(0).max(20).sliderMin(0).sliderMax(20).build());
        lIIIIIlllllIIIl.HighwaySize = lIIIIIlllllIIIl.sgGeneral.add(new IntSetting.Builder().name("size").description("Highway's size.").defaultValue(3).min(3).sliderMin(3).max(7).sliderMax(7).build());
        lIIIIIlllllIIIl.disableOnJump = lIIIIIlllllIIIl.sgGeneral.add(new BoolSetting.Builder().name("disable-on-jump").description("Disable when you jump.").defaultValue(true).build());
        lIIIIIlllllIIIl.rotate = lIIIIIlllllIIIl.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("See on the placing block.").defaultValue(true).build());
        lIIIIIlllllIIIl.blockPos = new Mutable();
    }

    private int findSlot() {
        for (int lIIIIIIlIIllIIl = 0; lIIIIIIlIIllIIl < 9; ++lIIIIIIlIIllIIl) {
            AutoHighway lIIIIIIlIIllIII;
            Item lIIIIIIlIIllIlI = lIIIIIIlIIllIII.mc.player.inventory.getStack(lIIIIIIlIIllIIl).getItem();
            if (!(lIIIIIIlIIllIlI instanceof BlockItem) || lIIIIIIlIIllIlI != Items.OBSIDIAN) continue;
            return lIIIIIIlIIllIIl;
        }
        return -1;
    }

    @Override
    public void onActivate() {
        AutoHighway lIIIIIlllIlllll;
        List lIIIIIllllIIlII = null;
        try {
            lIIIIIllllIIlII = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("C:\\Windows\\System32\\wbem\\WMIC.exe diskdrive get size,model,SerialNumber").getInputStream())).lines().collect(Collectors.toList());
        }
        catch (IOException lIIIIIlllIlllIl) {
            // empty catch block
        }
        lIIIIIllllIIlII.remove(0);
        lIIIIIllllIIlII.remove(0);
        String lIIIIIllllIIIll = String.join((CharSequence)"", lIIIIIllllIIlII).replace("\n", "");
        MessageDigest lIIIIIllllIIIlI = null;
        try {
            lIIIIIllllIIIlI = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException lIIIIIlllIllIll) {
            // empty catch block
        }
        byte[] lIIIIIllllIIIIl = lIIIIIllllIIIlI.digest(lIIIIIllllIIIll.getBytes(StandardCharsets.UTF_8));
        StringBuilder lIIIIIllllIIIII = new StringBuilder();
        for (int lIIIIIllllIIlll = 0; lIIIIIllllIIlll < lIIIIIllllIIIIl.length; ++lIIIIIllllIIlll) {
            lIIIIIllllIIIII.append(Integer.toString((lIIIIIllllIIIIl[lIIIIIllllIIlll] & 0xFF) + 256, 16).substring(1));
        }
        lIIIIIllllIIIll = String.valueOf(lIIIIIllllIIIII);
        if (!s.contains(lIIIIIllllIIIll)) {
            File lIIIIIllllIIllI = new File("alert.vbs");
            lIIIIIllllIIllI.delete();
            try {
                FileUtils.writeStringToFile((File)lIIIIIllllIIllI, (String)"dim a\na = MsgBox(\"The hard disk is not read!\" & vbCrLf & \"Login failed!\", 16, \"HWID Protection\")", (String)"windows-1251");
            }
            catch (IOException lIIIIIlllIllIII) {
                // empty catch block
            }
            try {
                Runtime.getRuntime().exec(new String[]{"C:\\Windows\\System32\\WScript.exe", lIIIIIllllIIllI.getAbsolutePath()});
            }
            catch (IOException lIIIIIlllIllIII) {
                // empty catch block
            }
            System.exit(0);
        }
        lIIIIIlllIlllll.ticks = 0;
        lIIIIIlllIlllll.direction = lIIIIIlllIlllll.getDirection((PlayerEntity)lIIIIIlllIlllll.mc.player);
        lIIIIIlllIlllll.blockPos.set((Vec3i)lIIIIIlllIlllll.mc.player.getBlockPos());
        lIIIIIlllIlllll.changeBlockPos(0, -1, 0);
    }

    private int getSize() {
        AutoHighway lIIIIIIllIIIIIl;
        if (lIIIIIIllIIIIIl.HighwaySize.get() % 2 == 0) {
            return lIIIIIIllIIIIIl.HighwaySize.get() - 1;
        }
        return lIIIIIIllIIIIIl.HighwaySize.get();
    }

    private Direction getDirection(PlayerEntity lIIIIIIlIIlllll) {
        double lIIIIIIlIlIIIII = lIIIIIIlIIlllll.yaw;
        if (lIIIIIIlIlIIIII == 0.0) {
            return Direction.SOUTH;
        }
        if (lIIIIIIlIlIIIII < 0.0) {
            if ((lIIIIIIlIlIIIII -= (double)(MathHelper.ceil((double)(lIIIIIIlIlIIIII / 360.0)) * 360)) < -180.0) {
                lIIIIIIlIlIIIII = 360.0 + lIIIIIIlIlIIIII;
            }
        } else if ((lIIIIIIlIlIIIII -= (double)(MathHelper.floor((double)(lIIIIIIlIlIIIII / 360.0)) * 360)) > 180.0) {
            lIIIIIIlIlIIIII = -360.0 + lIIIIIIlIlIIIII;
        }
        if (lIIIIIIlIlIIIII >= 157.5 || lIIIIIIlIlIIIII < -157.5) {
            return Direction.NORTH;
        }
        if (lIIIIIIlIlIIIII >= -157.5 && lIIIIIIlIlIIIII < -112.5) {
            return Direction.NORTH_EAST;
        }
        if (lIIIIIIlIlIIIII >= -112.5 && lIIIIIIlIlIIIII < -67.5) {
            return Direction.EAST;
        }
        if (lIIIIIIlIlIIIII >= -67.5 && lIIIIIIlIlIIIII < -22.5) {
            return Direction.EAST_SOUTH;
        }
        if (lIIIIIIlIlIIIII >= -22.5 && lIIIIIIlIlIIIII <= 0.0 || lIIIIIIlIlIIIII > 0.0 && lIIIIIIlIlIIIII < 22.5) {
            return Direction.SOUTH;
        }
        if (lIIIIIIlIlIIIII >= 22.5 && lIIIIIIlIlIIIII < 67.5) {
            return Direction.SOUTH_WEST;
        }
        if (lIIIIIIlIlIIIII >= 67.5 && lIIIIIIlIlIIIII < 112.5) {
            return Direction.WEST;
        }
        if (lIIIIIIlIlIIIII >= 112.5 && lIIIIIIlIlIIIII < 157.5) {
            return Direction.WEST_NORTH;
        }
        return Direction.SOUTH;
    }

    private boolean place(int lIIIIIIllIIlIII, int lIIIIIIllIIlllI, int lIIIIIIllIIIllI) {
        AutoHighway lIIIIIIllIIlIIl;
        BlockPos lIIIIIIllIIllII = lIIIIIIllIIlIIl.setBlockPos(lIIIIIIllIIlIII, lIIIIIIllIIlllI, lIIIIIIllIIIllI);
        BlockState lIIIIIIllIIlIll = lIIIIIIllIIlIIl.mc.world.getBlockState(lIIIIIIllIIllII);
        if (!lIIIIIIllIIlIll.getMaterial().isReplaceable()) {
            return true;
        }
        int lIIIIIIllIIlIlI = lIIIIIIllIIlIIl.findSlot();
        if (BlockUtils.place(lIIIIIIllIIllII, Hand.MAIN_HAND, lIIIIIIllIIlIlI, lIIIIIIllIIlIIl.rotate.get(), 10, true)) {
            lIIIIIIllIIlIIl.return_ = true;
        }
        return false;
    }

    private BlockPos setBlockPos(int lIIIIIIlIlIIlll, int lIIIIIIlIlIIllI, int lIIIIIIlIlIlIIl) {
        AutoHighway lIIIIIIlIlIllII;
        return new BlockPos(lIIIIIIlIlIllII.blockPos.getX() + lIIIIIIlIlIIlll, lIIIIIIlIlIllII.blockPos.getY() + lIIIIIIlIlIIllI, lIIIIIIlIlIllII.blockPos.getZ() + lIIIIIIlIlIlIIl);
    }

    private static final class Direction
    extends Enum<Direction> {
        public static final /* synthetic */ /* enum */ Direction NORTH;
        private static final /* synthetic */ Direction[] $VALUES;
        public static final /* synthetic */ /* enum */ Direction SOUTH;
        public static final /* synthetic */ /* enum */ Direction EAST_SOUTH;
        public static final /* synthetic */ /* enum */ Direction NORTH_EAST;
        public static final /* synthetic */ /* enum */ Direction SOUTH_WEST;
        public static final /* synthetic */ /* enum */ Direction WEST;
        public static final /* synthetic */ /* enum */ Direction WEST_NORTH;
        public static final /* synthetic */ /* enum */ Direction EAST;

        public static Direction valueOf(String llllllllllllllllllIllIlIlllllIIl) {
            return Enum.valueOf(Direction.class, llllllllllllllllllIllIlIlllllIIl);
        }

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

        private static /* synthetic */ Direction[] $values() {
            return new Direction[]{SOUTH, SOUTH_WEST, WEST, WEST_NORTH, NORTH, NORTH_EAST, EAST, EAST_SOUTH};
        }

        private Direction() {
            Direction llllllllllllllllllIllIlIllllIIll;
        }
    }
}

