/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.util.Hand
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockState
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
import net.minecraft.block.BlockState;

public class LiquidFiller
extends Module {
    private final /* synthetic */ Setting<Integer> horizontalRadius;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<List<Block>> whitelist;
    private final /* synthetic */ Setting<Integer> verticalRadius;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<PlaceIn> placeInLiquids;
    private final /* synthetic */ Setting<Integer> delay;
    private /* synthetic */ int timer;

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllllIIlIlIllIlIIl) {
        LiquidFiller lllllllllllllllllllIIlIlIllIlIlI;
        if (lllllllllllllllllllIIlIlIllIlIlI.timer < lllllllllllllllllllIIlIlIllIlIlI.delay.get()) {
            ++lllllllllllllllllllIIlIlIllIlIlI.timer;
            return;
        }
        lllllllllllllllllllIIlIlIllIlIlI.timer = 0;
        int lllllllllllllllllllIIlIlIllIlIII = lllllllllllllllllllIIlIlIllIlIlI.findSlot();
        if (lllllllllllllllllllIIlIlIllIlIII == -1) {
            return;
        }
        BlockIterator.register(lllllllllllllllllllIIlIlIllIlIlI.horizontalRadius.get(), lllllllllllllllllllIIlIlIllIlIlI.verticalRadius.get(), (lllllllllllllllllllIIlIlIlIIlIlI, lllllllllllllllllllIIlIlIlIIlIIl) -> {
            LiquidFiller lllllllllllllllllllIIlIlIlIIllII;
            if (lllllllllllllllllllIIlIlIlIIllII.isSource((BlockState)lllllllllllllllllllIIlIlIlIIlIIl)) {
                Block lllllllllllllllllllIIlIlIlIIlllI = lllllllllllllllllllIIlIlIlIIlIIl.getBlock();
                PlaceIn lllllllllllllllllllIIlIlIlIIllIl = lllllllllllllllllllIIlIlIlIIllII.placeInLiquids.get();
                if ((lllllllllllllllllllIIlIlIlIIllIl == PlaceIn.Both || lllllllllllllllllllIIlIlIlIIllIl == PlaceIn.Lava && lllllllllllllllllllIIlIlIlIIlllI == Blocks.LAVA || lllllllllllllllllllIIlIlIlIIllIl == PlaceIn.Water && lllllllllllllllllllIIlIlIlIIlllI == Blocks.WATER) && BlockUtils.place(lllllllllllllllllllIIlIlIlIIlIlI, Hand.MAIN_HAND, lllllllllllllllllllIIlIlIllIlIII, lllllllllllllllllllIIlIlIlIIllII.rotate.get(), 0, true)) {
                    BlockIterator.disableCurrent();
                }
            }
        });
    }

    @Override
    public void onActivate() {
        lllllllllllllllllllIIlIlIllIlllI.timer = 0;
    }

    private List<Block> getDefaultWhitelist() {
        return Lists.newArrayList((Object[])new Block[]{Blocks.DIRT, Blocks.COBBLESTONE, Blocks.STONE, Blocks.NETHERRACK, Blocks.DIORITE, Blocks.GRANITE, Blocks.ANDESITE});
    }

    private int findSlot() {
        int lllllllllllllllllllIIlIlIlIllIlI = -1;
        for (int lllllllllllllllllllIIlIlIlIlllII = 0; lllllllllllllllllllIIlIlIlIlllII < 9; ++lllllllllllllllllllIIlIlIlIlllII) {
            LiquidFiller lllllllllllllllllllIIlIlIlIllIll;
            Item lllllllllllllllllllIIlIlIlIlllIl = lllllllllllllllllllIIlIlIlIllIll.mc.player.inventory.getStack(lllllllllllllllllllIIlIlIlIlllII).getItem();
            if (!(lllllllllllllllllllIIlIlIlIlllIl instanceof BlockItem) || !lllllllllllllllllllIIlIlIlIllIll.whitelist.get().contains((Object)((BlockItem)lllllllllllllllllllIIlIlIlIlllIl).getBlock())) continue;
            lllllllllllllllllllIIlIlIlIllIlI = lllllllllllllllllllIIlIlIlIlllII;
            break;
        }
        return lllllllllllllllllllIIlIlIlIllIlI;
    }

    public LiquidFiller() {
        super(Categories.World, "liquid-filler", "Places blocks inside of liquid source blocks within range of you.");
        LiquidFiller lllllllllllllllllllIIlIlIlllIIIl;
        lllllllllllllllllllIIlIlIlllIIIl.sgGeneral = lllllllllllllllllllIIlIlIlllIIIl.settings.getDefaultGroup();
        lllllllllllllllllllIIlIlIlllIIIl.placeInLiquids = lllllllllllllllllllIIlIlIlllIIIl.sgGeneral.add(new EnumSetting.Builder().name("place-in").description("What type of liquids to place in.").defaultValue(PlaceIn.Lava).build());
        lllllllllllllllllllIIlIlIlllIIIl.horizontalRadius = lllllllllllllllllllIIlIlIlllIIIl.sgGeneral.add(new IntSetting.Builder().name("horizontal-radius").description("Horizontal radius in which to search for liquids.").defaultValue(4).min(0).sliderMax(6).build());
        lllllllllllllllllllIIlIlIlllIIIl.verticalRadius = lllllllllllllllllllIIlIlIlllIIIl.sgGeneral.add(new IntSetting.Builder().name("vertical-radius").description("Vertical radius in which to search for liquids.").defaultValue(4).min(0).sliderMax(6).build());
        lllllllllllllllllllIIlIlIlllIIIl.delay = lllllllllllllllllllIIlIlIlllIIIl.sgGeneral.add(new IntSetting.Builder().name("delay").description("Delay between actions in ticks.").defaultValue(1).min(0).build());
        lllllllllllllllllllIIlIlIlllIIIl.whitelist = lllllllllllllllllllIIlIlIlllIIIl.sgGeneral.add(new BlockListSetting.Builder().name("block-whitelist").description("The allowed blocks that it will use to fill up the liquid.").defaultValue(lllllllllllllllllllIIlIlIlllIIIl.getDefaultWhitelist()).build());
        lllllllllllllllllllIIlIlIlllIIIl.rotate = lllllllllllllllllllIIlIlIlllIIIl.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards the space targeted for filling.").defaultValue(true).build());
    }

    private boolean isSource(BlockState lllllllllllllllllllIIlIlIllIIIll) {
        return lllllllllllllllllllIIlIlIllIIIll.getFluidState().getLevel() == 8 && lllllllllllllllllllIIlIlIllIIIll.getFluidState().isStill();
    }

    public static final class PlaceIn
    extends Enum<PlaceIn> {
        private static final /* synthetic */ PlaceIn[] $VALUES;
        public static final /* synthetic */ /* enum */ PlaceIn Lava;
        public static final /* synthetic */ /* enum */ PlaceIn Both;
        public static final /* synthetic */ /* enum */ PlaceIn Water;

        private static /* synthetic */ PlaceIn[] $values() {
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

        private PlaceIn() {
            PlaceIn lllllllllllllllllIlIllllIlIIlIIl;
        }

        public static PlaceIn valueOf(String lllllllllllllllllIlIllllIlIIlllI) {
            return Enum.valueOf(PlaceIn.class, lllllllllllllllllIlIllllIlIIlllI);
        }
    }
}

