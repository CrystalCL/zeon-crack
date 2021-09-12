/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BlockListSetting;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1739;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1829;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_1893;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import net.minecraft.class_3965;
import net.minecraft.class_480;
import net.minecraft.class_488;

public class Auto32K
extends Module {
    private final SettingGroup sgGeneral;
    private class_2338 bestBlock;
    private final Setting<Double> placeRange;
    private int phase;
    private final Setting<Mode> mode;
    private int z;
    private final Setting<List<class_2248>> throwawayItems;
    private final Setting<Boolean> fillHopper;
    private final Setting<Boolean> autoMove;
    private int x;

    private List<class_2248> setDefaultBlocks() {
        ArrayList<class_2248> arrayList = new ArrayList<class_2248>();
        arrayList.add(class_2246.field_10540);
        arrayList.add(class_2246.field_10445);
        return arrayList;
    }

    private boolean isValidSlot(int n) {
        return n == -1 || n >= 9;
    }

    private double lambda$onTick$0(class_2338 class_23382) {
        return this.mc.field_1724.method_5649((double)class_23382.method_10263(), (double)class_23382.method_10264(), (double)class_23382.method_10260());
    }

    @Override
    public void onActivate() {
        this.bestBlock = this.findValidBlocksDispenser();
    }

    private double lambda$findValidBlocksDispenser$1(class_2338 class_23382) {
        return this.mc.field_1724.method_5649((double)class_23382.method_10263(), (double)class_23382.method_10264(), (double)class_23382.method_10260());
    }

    public Auto32K() {
        super(Categories.Combat, "auto-32k", "Automatically attacks other players with a 32k weapon.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The bypass mode used.").defaultValue(Mode.Dispenser).build());
        this.placeRange = this.sgGeneral.add(new DoubleSetting.Builder().name("place-range").description("The distance in a single direction the shulker is placed.").defaultValue(3.0).min(0.0).sliderMax(5.0).build());
        this.fillHopper = this.sgGeneral.add(new BoolSetting.Builder().name("fill-hopper").description("Fills all slots of the hopper except one for the 32k.").defaultValue(true).build());
        this.throwawayItems = this.sgGeneral.add(new BlockListSetting.Builder().name("throwaway-blocks").description("Whitelisted blocks to use to fill the hopper.").defaultValue(this.setDefaultBlocks()).build());
        this.autoMove = this.sgGeneral.add(new BoolSetting.Builder().name("auto-move").description("Moves the 32K into your inventory automatically.").defaultValue(true).build());
        this.phase = 0;
    }

    @Override
    public void onDeactivate() {
        this.phase = 0;
    }

    private List<class_2338> findValidBlocksHopper() {
        Iterator<class_2338> iterator = this.getRange(this.mc.field_1724.method_24515(), this.placeRange.get()).iterator();
        ArrayList<class_2338> arrayList = new ArrayList<class_2338>();
        class_2338 class_23382 = null;
        while (iterator.hasNext()) {
            if (class_23382 != null && !this.mc.field_1687.method_8320(class_23382).method_26207().method_15800() && this.mc.field_1687.method_8320(class_23382.method_10084()).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10084().method_10263(), (double)class_23382.method_10084().method_10264(), (double)class_23382.method_10084().method_10260(), (double)class_23382.method_10084().method_10263() + 1.0, (double)class_23382.method_10084().method_10264() + 2.0, (double)class_23382.method_10084().method_10260() + 1.0)).isEmpty() && this.mc.field_1687.method_8320(class_23382.method_10086(2)).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10086(2).method_10263(), (double)class_23382.method_10086(2).method_10264(), (double)class_23382.method_10086(2).method_10260(), (double)class_23382.method_10086(2).method_10263() + 1.0, (double)class_23382.method_10086(2).method_10264() + 2.0, (double)class_23382.method_10086(2).method_10260() + 1.0)).isEmpty()) {
                arrayList.add(class_23382);
            }
            class_23382 = iterator.next();
        }
        return arrayList;
    }

    private List<class_2338> getRange(class_2338 class_23382, double d) {
        ArrayList<class_2338> arrayList = new ArrayList<class_2338>();
        for (double d2 = (double)class_23382.method_10263() - d; d2 < (double)class_23382.method_10263() + d; d2 += 1.0) {
            for (double d3 = (double)class_23382.method_10260() - d; d3 < (double)class_23382.method_10260() + d; d3 += 1.0) {
                for (int i = class_23382.method_10264() - 3; i < class_23382.method_10264() + 3; ++i) {
                    class_2338 class_23383 = new class_2338(d2, (double)i, d3);
                    arrayList.add(class_23383);
                    if (null == null) continue;
                    return null;
                }
            }
        }
        return arrayList;
    }

    private class_2338 findValidBlocksDispenser() {
        List<class_2338> list = this.getRange(this.mc.field_1724.method_24515(), this.placeRange.get());
        list.sort(Comparator.comparingDouble(this::lambda$findValidBlocksDispenser$1));
        Iterator<class_2338> iterator = list.iterator();
        class_2338 class_23382 = null;
        while (iterator.hasNext()) {
            if (class_23382 != null && !this.mc.field_1687.method_8320(class_23382).method_26207().method_15800() && this.mc.field_1687.method_8320(class_23382.method_10084()).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10084().method_10263(), (double)class_23382.method_10084().method_10264(), (double)class_23382.method_10084().method_10260(), (double)class_23382.method_10084().method_10263() + 1.0, (double)class_23382.method_10084().method_10264() + 2.0, (double)class_23382.method_10084().method_10260() + 1.0)).isEmpty() && this.mc.field_1687.method_8320(class_23382.method_10086(2)).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10086(2).method_10263(), (double)class_23382.method_10086(2).method_10264(), (double)class_23382.method_10086(2).method_10260(), (double)class_23382.method_10086(2).method_10263() + 1.0, (double)class_23382.method_10086(2).method_10264() + 2.0, (double)class_23382.method_10086(2).method_10260() + 1.0)).isEmpty() && this.mc.field_1687.method_8320(class_23382.method_10086(3)).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10086(3).method_10263(), (double)class_23382.method_10086(3).method_10264(), (double)class_23382.method_10086(3).method_10260(), (double)class_23382.method_10086(2).method_10263() + 1.0, (double)class_23382.method_10086(2).method_10264() + 2.0, (double)class_23382.method_10086(2).method_10260() + 1.0)).isEmpty()) {
                if (this.mc.field_1687.method_8320(class_23382.method_10069(-1, 1, 0)).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10069(-1, 1, 0).method_10263(), (double)class_23382.method_10069(-1, 1, 0).method_10264(), (double)class_23382.method_10069(-1, 1, 0).method_10260(), (double)class_23382.method_10069(-1, 1, 0).method_10263() + 1.0, (double)class_23382.method_10069(-1, 1, 0).method_10264() + 2.0, (double)class_23382.method_10069(-1, 1, 0).method_10260() + 1.0)).isEmpty() && this.mc.field_1687.method_8320(class_23382.method_10069(-1, 0, 0)).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10069(-1, 0, 0).method_10263(), (double)class_23382.method_10069(-1, 0, 0).method_10264(), (double)class_23382.method_10069(-1, 0, 0).method_10260(), (double)class_23382.method_10069(-1, 0, 0).method_10263() + 1.0, (double)class_23382.method_10069(-1, 0, 0).method_10264() + 2.0, (double)class_23382.method_10069(-1, 0, 0).method_10260() + 1.0)).isEmpty()) {
                    this.x = -1;
                    this.z = 0;
                    return class_23382;
                }
                if (this.mc.field_1687.method_8320(class_23382.method_10069(1, 1, 0)).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10069(1, 1, 0).method_10263(), (double)class_23382.method_10069(1, 1, 0).method_10264(), (double)class_23382.method_10069(1, 1, 0).method_10260(), (double)class_23382.method_10069(1, 1, 0).method_10263() + 1.0, (double)class_23382.method_10069(1, 1, 0).method_10264() + 2.0, (double)class_23382.method_10069(1, 1, 0).method_10260() + 1.0)).isEmpty() && this.mc.field_1687.method_8320(class_23382.method_10069(1, 0, 0)).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10069(1, 0, 0).method_10263(), (double)class_23382.method_10069(1, 0, 0).method_10264(), (double)class_23382.method_10069(1, 0, 0).method_10260(), (double)class_23382.method_10069(1, 0, 0).method_10263() + 1.0, (double)class_23382.method_10069(1, 0, 0).method_10264() + 2.0, (double)class_23382.method_10069(1, 0, 0).method_10260() + 1.0)).isEmpty()) {
                    this.x = 1;
                    this.z = 0;
                    return class_23382;
                }
                if (this.mc.field_1687.method_8320(class_23382.method_10069(0, 1, -1)).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10069(0, 1, -1).method_10263(), (double)class_23382.method_10069(0, 1, -1).method_10264(), (double)class_23382.method_10069(0, 1, -1).method_10260(), (double)class_23382.method_10069(0, 1, -1).method_10263() + 1.0, (double)class_23382.method_10069(0, 1, -1).method_10264() + 2.0, (double)class_23382.method_10069(0, 1, -1).method_10260() + 1.0)).isEmpty() && this.mc.field_1687.method_8320(class_23382.method_10069(0, 0, -1)).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10069(0, 0, -1).method_10263(), (double)class_23382.method_10069(0, 0, -1).method_10264(), (double)class_23382.method_10069(0, 0, -1).method_10260(), (double)class_23382.method_10069(0, 0, -1).method_10263() + 1.0, (double)class_23382.method_10069(0, 0, -1).method_10264() + 2.0, (double)class_23382.method_10069(0, 0, -1).method_10260() + 1.0)).isEmpty()) {
                    this.x = 0;
                    this.z = -1;
                    return class_23382;
                }
                if (this.mc.field_1687.method_8320(class_23382.method_10069(0, 1, 1)).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10069(0, 1, 1).method_10263(), (double)class_23382.method_10069(0, 1, 1).method_10264(), (double)class_23382.method_10069(0, 1, 1).method_10260(), (double)class_23382.method_10069(0, 1, 1).method_10263() + 1.0, (double)class_23382.method_10069(0, 1, 1).method_10264() + 2.0, (double)class_23382.method_10069(0, 1, 1).method_10260() + 1.0)).isEmpty() && this.mc.field_1687.method_8320(class_23382.method_10069(0, 0, 1)).method_26204() == class_2246.field_10124 && this.mc.field_1687.method_8335(null, new class_238((double)class_23382.method_10069(0, 0, 1).method_10263(), (double)class_23382.method_10069(0, 0, 1).method_10264(), (double)class_23382.method_10069(0, 0, 1).method_10260(), (double)class_23382.method_10069(0, 0, 1).method_10263() + 1.0, (double)class_23382.method_10069(0, 0, 1).method_10264() + 2.0, (double)class_23382.method_10069(0, 0, 1).method_10260() + 1.0)).isEmpty()) {
                    this.x = 0;
                    this.z = 1;
                    return class_23382;
                }
            }
            class_23382 = iterator.next();
        }
        return null;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.phase <= 7) {
            if (this.mode.get() == Mode.Hopper) {
                int n = InvUtils.findItemWithCount((class_1792)class_1802.field_8545).slot;
                int n2 = InvUtils.findItemWithCount((class_1792)class_1802.field_8239).slot;
                if (this.isValidSlot(n) || this.isValidSlot(n2)) {
                    return;
                }
                List<class_2338> list = this.findValidBlocksHopper();
                list.sort(Comparator.comparingDouble(this::lambda$onTick$0));
                Iterator<class_2338> iterator = list.iterator();
                class_2338 class_23382 = null;
                if (iterator.hasNext()) {
                    class_23382 = iterator.next();
                }
                if (class_23382 != null) {
                    this.mc.field_1724.field_7514.field_7545 = n2;
                    while (!PlayerUtils.placeBlock(class_23382, class_1268.field_5808) && iterator.hasNext()) {
                        class_23382 = iterator.next().method_10084();
                    }
                    this.mc.field_1724.method_5660(true);
                    this.mc.field_1724.field_7514.field_7545 = n;
                    if (!PlayerUtils.placeBlock(class_23382.method_10084(), class_1268.field_5808)) {
                        Utils.sendMessage("#redFailed to place.", new Object[0]);
                        this.toggle();
                        return;
                    }
                    this.mc.field_1724.method_5660(false);
                    this.mc.field_1761.method_2896(this.mc.field_1724, this.mc.field_1687, class_1268.field_5808, new class_3965(this.mc.field_1724.method_19538(), this.mc.field_1724.method_5735(), class_23382.method_10084(), false));
                    this.phase = 8;
                }
            } else if (this.mode.get() == Mode.Dispenser) {
                int n = InvUtils.findItemWithCount((class_1792)class_1802.field_8545).slot;
                int n3 = InvUtils.findItemWithCount((class_1792)class_1802.field_8239).slot;
                int n4 = InvUtils.findItemWithCount((class_1792)class_1802.field_8357).slot;
                int n5 = InvUtils.findItemWithCount((class_1792)class_1802.field_8793).slot;
                if (this.isValidSlot(n) && this.mode.get() == Mode.Hopper || this.isValidSlot(n3) || this.isValidSlot(n4) || this.isValidSlot(n5)) {
                    return;
                }
                if (this.phase == 0) {
                    this.bestBlock = this.findValidBlocksDispenser();
                    this.mc.field_1724.field_7514.field_7545 = n3;
                    if (this.bestBlock == null) {
                        return;
                    }
                    if (!PlayerUtils.placeBlock(this.bestBlock.method_10069(this.x, 0, this.z), class_1268.field_5808)) {
                        Utils.sendMessage("#redFailed to place.", new Object[0]);
                        this.toggle();
                        return;
                    }
                    ++this.phase;
                } else if (this.phase == 1) {
                    this.mc.field_1724.field_7514.field_7545 = n4;
                    if (this.x == -1) {
                        this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2831(-90.0f, this.mc.field_1724.field_5965, this.mc.field_1724.method_24828()));
                    } else if (this.x == 1) {
                        this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2831(90.0f, this.mc.field_1724.field_5965, this.mc.field_1724.method_24828()));
                    } else if (this.z == -1) {
                        this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2831(1.0f, this.mc.field_1724.field_5965, this.mc.field_1724.method_24828()));
                    } else if (this.z == 1) {
                        this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2831(179.0f, this.mc.field_1724.field_5965, this.mc.field_1724.method_24828()));
                    }
                    ++this.phase;
                } else if (this.phase == 2) {
                    this.mc.field_1761.method_2896(this.mc.field_1724, this.mc.field_1687, class_1268.field_5808, new class_3965(this.mc.field_1724.method_19538(), class_2350.field_11036, this.bestBlock, false));
                    ++this.phase;
                } else if (this.phase == 3) {
                    this.mc.field_1761.method_2896(this.mc.field_1724, this.mc.field_1687, class_1268.field_5808, new class_3965(this.mc.field_1724.method_19538(), this.mc.field_1724.method_5735().method_10153(), this.bestBlock.method_10084(), false));
                    ++this.phase;
                } else if (this.phase == 4 && this.mc.field_1755 instanceof class_480) {
                    this.mc.field_1724.method_3118();
                    InvUtils.move().from(n).toId(4);
                    ++this.phase;
                } else if (this.phase == 5 && this.mc.field_1755 instanceof class_480) {
                    this.mc.field_1724.method_7346();
                    ++this.phase;
                } else if (this.phase == 6) {
                    this.mc.field_1724.field_7514.field_7545 = n5;
                    this.mc.field_1724.method_5660(true);
                    this.mc.field_1761.method_2896(this.mc.field_1724, this.mc.field_1687, class_1268.field_5808, new class_3965(this.mc.field_1724.method_19538(), this.mc.field_1724.method_5735().method_10153(), this.bestBlock.method_10086(2), false));
                    this.mc.field_1724.method_5660(false);
                    ++this.phase;
                } else if (this.phase == 7) {
                    this.mc.field_1761.method_2896(this.mc.field_1724, this.mc.field_1687, class_1268.field_5808, new class_3965(this.mc.field_1724.method_19538(), this.mc.field_1724.method_5735().method_10153(), this.bestBlock.method_10069(this.x, 0, this.z), false));
                    ++this.phase;
                }
            }
        } else if (this.phase == 8) {
            if (this.mc.field_1755 instanceof class_488) {
                int n;
                int n6;
                int n7;
                if (this.fillHopper.get().booleanValue() && !this.throwawayItems.get().isEmpty()) {
                    n7 = -1;
                    n6 = 0;
                    Iterator<class_2248> iterator = this.throwawayItems.get().iterator();
                    class_1792 class_17922 = iterator.next().method_8389();
                    while (iterator.hasNext()) {
                        for (int i = 5; i <= 40; ++i) {
                            class_1799 class_17992 = this.mc.field_1724.field_7514.method_5438(i);
                            if (class_17992.method_7909() != class_17922 || class_17992.method_7947() < 4) continue;
                            n7 = i;
                            n6 = class_17992.method_7947();
                            break;
                        }
                        if (n6 >= 4) break;
                        class_17922 = iterator.next().method_8389();
                    }
                    for (n = 1; n < 5; ++n) {
                        if (!(this.mc.field_1724.field_7512.method_7611(n).method_7677().method_7909() instanceof class_1739)) continue;
                        InvUtils.move().from(n7 - 4).toId(n);
                        if (null == null) continue;
                        return;
                    }
                }
                n7 = 1;
                n6 = -1;
                int n8 = -1;
                for (n = 32; n < 41; ++n) {
                    if (class_1890.method_8225((class_1887)class_1893.field_9118, (class_1799)this.mc.field_1724.field_7512.method_7611(n).method_7677()) > 5) {
                        n7 = 0;
                        n6 = n;
                        break;
                    }
                    if (!(this.mc.field_1724.field_7512.method_7611(n).method_7677().method_7909() instanceof class_1829) || class_1890.method_8225((class_1887)class_1893.field_9118, (class_1799)this.mc.field_1724.field_7512.method_7611(n).method_7677()) > 5) continue;
                    n8 = n;
                    if (-1 < 1) continue;
                    return;
                }
                if (n8 != -1) {
                    InvUtils.drop().slot(n8);
                }
                if (this.autoMove.get().booleanValue() && n7 != 0) {
                    n = this.mc.field_1724.field_7514.method_7376();
                    if (n < 9 && n != -1 && class_1890.method_8225((class_1887)class_1893.field_9118, (class_1799)this.mc.field_1724.field_7512.method_7611(0).method_7677()) > 5) {
                        InvUtils.move().fromId(0).to(n - 4);
                    } else if (class_1890.method_8225((class_1887)class_1893.field_9118, (class_1799)this.mc.field_1724.field_7512.method_7611(0).method_7677()) <= 5 && this.mc.field_1724.field_7512.method_7611(0).method_7677().method_7909() != class_1802.field_8162) {
                        InvUtils.drop().slotId(0);
                    }
                }
                if (n6 != -1) {
                    this.mc.field_1724.field_7514.field_7545 = n6 - 32;
                }
            } else {
                this.toggle();
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Dispenser;
        public static final /* enum */ Mode Hopper;

        private static Mode[] $values() {
            return new Mode[]{Hopper, Dispenser};
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        static {
            Hopper = new Mode();
            Dispenser = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

