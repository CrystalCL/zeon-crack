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
import net.minecraft.util.Hand;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;

public class LiquidFiller
extends Module {
    private final Setting<List<Block>> whitelist;
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

    private boolean isSource(BlockState BlockState2) {
        return BlockState2.getFluidState().getLevel() == 8 && BlockState2.getFluidState().isStill();
    }

    private void lambda$onTick$0(int n, BlockPos BlockPos2, BlockState BlockState2) {
        if (this.isSource(BlockState2)) {
            Block Block2 = BlockState2.getBlock();
            PlaceIn placeIn = this.placeInLiquids.get();
            if ((placeIn == PlaceIn.Both || placeIn == PlaceIn.Lava && Block2 == Blocks.LAVA || placeIn == PlaceIn.Water && Block2 == Blocks.WATER) && BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n, this.rotate.get(), 0, true)) {
                BlockIterator.disableCurrent();
            }
        }
    }

    private List<Block> getDefaultWhitelist() {
        return Lists.newArrayList((Object[])new Block[]{Blocks.DIRT, Blocks.COBBLESTONE, Blocks.STONE, Blocks.NETHERRACK, Blocks.DIORITE, Blocks.GRANITE, Blocks.ANDESITE});
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
            Item Item2 = this.mc.player.inventory.getStack(i).getItem();
            if (!(Item2 instanceof BlockItem) || !this.whitelist.get().contains(((BlockItem)Item2).getBlock())) continue;
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

