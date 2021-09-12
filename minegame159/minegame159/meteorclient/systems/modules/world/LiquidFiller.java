/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import com.google.common.collect.Lists;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BlockListSetting;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.world.BlockIterator;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2680;

public class LiquidFiller
extends Module {
    private final Setting<List<class_2248>> whitelist;
    private final SettingGroup sgGeneral;
    private final Setting<PlaceIn> placeInLiquids;
    private final Setting<Boolean> rotate;
    private final Setting<Integer> verticalRadius;
    private int timer;
    private final Setting<Integer> delay;
    private final Setting<Integer> horizontalRadius;

    @Override
    public void onActivate() {
        this.timer = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.timer < this.delay.get()) {
            ++this.timer;
            return;
        }
        this.timer = 0;
        int n = this.findSlot();
        if (n == -1) {
            return;
        }
        BlockIterator.register(this.horizontalRadius.get(), this.verticalRadius.get(), (arg_0, arg_1) -> this.lambda$onTick$0(n, arg_0, arg_1));
    }

    private boolean isSource(class_2680 class_26802) {
        return class_26802.method_26227().method_15761() == 8 && class_26802.method_26227().method_15771();
    }

    private void lambda$onTick$0(int n, class_2338 class_23382, class_2680 class_26802) {
        if (this.isSource(class_26802)) {
            class_2248 class_22482 = class_26802.method_26204();
            PlaceIn placeIn = this.placeInLiquids.get();
            if ((placeIn == PlaceIn.Both || placeIn == PlaceIn.Lava && class_22482 == class_2246.field_10164 || placeIn == PlaceIn.Water && class_22482 == class_2246.field_10382) && BlockUtils.place(class_23382, class_1268.field_5808, n, this.rotate.get(), 0, true)) {
                BlockIterator.disableCurrent();
            }
        }
    }

    private List<class_2248> getDefaultWhitelist() {
        return Lists.newArrayList((Object[])new class_2248[]{class_2246.field_10566, class_2246.field_10445, class_2246.field_10340, class_2246.field_10515, class_2246.field_10508, class_2246.field_10474, class_2246.field_10115});
    }

    public LiquidFiller() {
        super(Categories.World, "liquid-filler", "Places blocks inside of liquid source blocks within range of you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.placeInLiquids = this.sgGeneral.add(new EnumSetting.Builder().name("place-in").description("What type of liquids to place in.").defaultValue(PlaceIn.Lava).build());
        this.horizontalRadius = this.sgGeneral.add(new IntSetting.Builder().name("horizontal-radius").description("Horizontal radius in which to search for liquids.").defaultValue(4).min(0).sliderMax(6).build());
        this.verticalRadius = this.sgGeneral.add(new IntSetting.Builder().name("vertical-radius").description("Vertical radius in which to search for liquids.").defaultValue(4).min(0).sliderMax(6).build());
        this.delay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("Delay between actions in ticks.").defaultValue(1).min(0).build());
        this.whitelist = this.sgGeneral.add(new BlockListSetting.Builder().name("block-whitelist").description("The allowed blocks that it will use to fill up the liquid.").defaultValue(this.getDefaultWhitelist()).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards the space targeted for filling.").defaultValue(true).build());
    }

    private int findSlot() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            class_1792 class_17922 = this.mc.field_1724.field_7514.method_5438(i).method_7909();
            if (!(class_17922 instanceof class_1747) || !this.whitelist.get().contains(((class_1747)class_17922).method_7711())) continue;
            n = i;
            break;
        }
        return n;
    }

    public static final class PlaceIn
    extends Enum<PlaceIn> {
        private static final PlaceIn[] $VALUES;
        public static final /* enum */ PlaceIn Lava;
        public static final /* enum */ PlaceIn Both;
        public static final /* enum */ PlaceIn Water;

        public static PlaceIn valueOf(String string) {
            return Enum.valueOf(PlaceIn.class, string);
        }

        private static PlaceIn[] $values() {
            return new PlaceIn[]{Lava, Water, Both};
        }

        public static PlaceIn[] values() {
            return (PlaceIn[])$VALUES.clone();
        }

        static {
            Lava = new PlaceIn();
            Water = new PlaceIn();
            Both = new PlaceIn();
            $VALUES = PlaceIn.$values();
        }
    }
}

