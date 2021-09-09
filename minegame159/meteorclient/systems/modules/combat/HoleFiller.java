/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.world.BlockView
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Vec3i
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.BlockView;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class HoleFiller
extends Module {
    private final /* synthetic */ Setting<PlaceMode> mode;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Integer> placeDelay;
    private final /* synthetic */ Mutable blockPos;
    private /* synthetic */ int tickDelayLeft;
    private final /* synthetic */ Setting<Integer> verticalRadius;
    private final /* synthetic */ Setting<Integer> horizontalRadius;
    private final /* synthetic */ SettingGroup sgGeneral;

    @Override
    public void onActivate() {
        lllllllllllllllllIllIllllIlIlllI.tickDelayLeft = 0;
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIllIllllIlIlIlI) {
        HoleFiller lllllllllllllllllIllIllllIlIlIII;
        int lllllllllllllllllIllIllllIlIlIIl = lllllllllllllllllIllIllllIlIlIII.findSlot();
        if (lllllllllllllllllIllIllllIlIlIIl != -1 && lllllllllllllllllIllIllllIlIlIII.tickDelayLeft <= 0) {
            lllllllllllllllllIllIllllIlIlIII.tickDelayLeft = lllllllllllllllllIllIllllIlIlIII.placeDelay.get();
            BlockIterator.register(lllllllllllllllllIllIllllIlIlIII.horizontalRadius.get(), lllllllllllllllllIllIllllIlIlIII.verticalRadius.get(), (lllllllllllllllllIllIlllIlllllIl, lllllllllllllllllIllIllllIIIIlIl) -> {
                HoleFiller lllllllllllllllllIllIlllIlllllll;
                if (!lllllllllllllllllIllIllllIIIIlIl.getMaterial().isReplaceable()) {
                    return;
                }
                lllllllllllllllllIllIlllIlllllll.blockPos.set((Vec3i)lllllllllllllllllIllIlllIlllllIl);
                Block lllllllllllllllllIllIllllIIIIlII = lllllllllllllllllIllIlllIlllllll.mc.world.getBlockState((BlockPos)lllllllllllllllllIllIlllIlllllll.add(0, -1, 0)).getBlock();
                if (lllllllllllllllllIllIllllIIIIlII != Blocks.BEDROCK && lllllllllllllllllIllIllllIIIIlII != Blocks.OBSIDIAN) {
                    return;
                }
                Block lllllllllllllllllIllIllllIIIIIll = lllllllllllllllllIllIlllIlllllll.mc.world.getBlockState((BlockPos)lllllllllllllllllIllIlllIlllllll.add(0, 1, 1)).getBlock();
                if (lllllllllllllllllIllIllllIIIIIll != Blocks.BEDROCK && lllllllllllllllllIllIllllIIIIIll != Blocks.OBSIDIAN) {
                    return;
                }
                Block lllllllllllllllllIllIllllIIIIIlI = lllllllllllllllllIllIlllIlllllll.mc.world.getBlockState((BlockPos)lllllllllllllllllIllIlllIlllllll.add(0, 0, -2)).getBlock();
                if (lllllllllllllllllIllIllllIIIIIlI != Blocks.BEDROCK && lllllllllllllllllIllIllllIIIIIlI != Blocks.OBSIDIAN) {
                    return;
                }
                Block lllllllllllllllllIllIllllIIIIIIl = lllllllllllllllllIllIlllIlllllll.mc.world.getBlockState((BlockPos)lllllllllllllllllIllIlllIlllllll.add(1, 0, 1)).getBlock();
                if (lllllllllllllllllIllIllllIIIIIIl != Blocks.BEDROCK && lllllllllllllllllIllIllllIIIIIIl != Blocks.OBSIDIAN) {
                    return;
                }
                Block lllllllllllllllllIllIllllIIIIIII = lllllllllllllllllIllIlllIlllllll.mc.world.getBlockState((BlockPos)lllllllllllllllllIllIlllIlllllll.add(-2, 0, 0)).getBlock();
                if (lllllllllllllllllIllIllllIIIIIII != Blocks.BEDROCK && lllllllllllllllllIllIllllIIIIIII != Blocks.OBSIDIAN) {
                    return;
                }
                lllllllllllllllllIllIlllIlllllll.add(1, 0, 0);
                if (BlockUtils.place((BlockPos)lllllllllllllllllIllIlllIlllllll.blockPos, Hand.MAIN_HAND, lllllllllllllllllIllIllllIlIlIIl, lllllllllllllllllIllIlllIlllllll.rotate.get(), 0, true)) {
                    BlockIterator.disableCurrent();
                }
            });
        }
        --lllllllllllllllllIllIllllIlIlIII.tickDelayLeft;
    }

    private Mutable add(int lllllllllllllllllIllIllllIIlIlII, int lllllllllllllllllIllIllllIIlIlll, int lllllllllllllllllIllIllllIIlIIlI) {
        HoleFiller lllllllllllllllllIllIllllIIllIIl;
        lllllllllllllllllIllIllllIIllIIl.blockPos.setX(lllllllllllllllllIllIllllIIllIIl.blockPos.getX() + lllllllllllllllllIllIllllIIlIlII);
        lllllllllllllllllIllIllllIIllIIl.blockPos.setY(lllllllllllllllllIllIllllIIllIIl.blockPos.getY() + lllllllllllllllllIllIllllIIlIlll);
        lllllllllllllllllIllIllllIIllIIl.blockPos.setZ(lllllllllllllllllIllIllllIIllIIl.blockPos.getZ() + lllllllllllllllllIllIllllIIlIIlI);
        return lllllllllllllllllIllIllllIIllIIl.blockPos;
    }

    private int findSlot() {
        block6: for (int lllllllllllllllllIllIllllIlIIIlI = 0; lllllllllllllllllIllIllllIlIIIlI < 9; ++lllllllllllllllllIllIllllIlIIIlI) {
            HoleFiller lllllllllllllllllIllIllllIlIIIII;
            ItemStack lllllllllllllllllIllIllllIlIIIll = lllllllllllllllllIllIllllIlIIIII.mc.player.inventory.getStack(lllllllllllllllllIllIllllIlIIIlI);
            switch (lllllllllllllllllIllIllllIlIIIII.mode.get()) {
                case Obsidian: {
                    if (lllllllllllllllllIllIllllIlIIIll.getItem() != Items.OBSIDIAN && lllllllllllllllllIllIllllIlIIIll.getItem() != Items.CRYING_OBSIDIAN) continue block6;
                    return lllllllllllllllllIllIllllIlIIIlI;
                }
                case Cobweb: {
                    if (lllllllllllllllllIllIllllIlIIIll.getItem() != Items.COBWEB) continue block6;
                    return lllllllllllllllllIllIllllIlIIIlI;
                }
                case Both: {
                    if (lllllllllllllllllIllIllllIlIIIll.getItem() != Items.COBWEB && lllllllllllllllllIllIllllIlIIIll.getItem() != Items.OBSIDIAN && lllllllllllllllllIllIllllIlIIIll.getItem() != Items.CRYING_OBSIDIAN) continue block6;
                    return lllllllllllllllllIllIllllIlIIIlI;
                }
                case Any: {
                    if (!(lllllllllllllllllIllIllllIlIIIll.getItem() instanceof BlockItem) || !((BlockItem)lllllllllllllllllIllIllllIlIIIll.getItem()).getBlock().getDefaultState().isFullCube((BlockView)lllllllllllllllllIllIllllIlIIIII.mc.world, (BlockPos)lllllllllllllllllIllIllllIlIIIII.blockPos)) continue block6;
                    return lllllllllllllllllIllIllllIlIIIlI;
                }
            }
        }
        return -1;
    }

    public HoleFiller() {
        super(Categories.Combat, "hole-filler", "Fills holes with specified blocks.");
        HoleFiller lllllllllllllllllIllIllllIllIIIl;
        lllllllllllllllllIllIllllIllIIIl.sgGeneral = lllllllllllllllllIllIllllIllIIIl.settings.getDefaultGroup();
        lllllllllllllllllIllIllllIllIIIl.horizontalRadius = lllllllllllllllllIllIllllIllIIIl.sgGeneral.add(new IntSetting.Builder().name("horizontal-radius").description("Horizontal radius in which to search for holes.").defaultValue(4).min(0).sliderMax(6).build());
        lllllllllllllllllIllIllllIllIIIl.verticalRadius = lllllllllllllllllIllIllllIllIIIl.sgGeneral.add(new IntSetting.Builder().name("vertical-radius").description("Vertical radius in which to search for holes.").defaultValue(4).min(0).sliderMax(6).build());
        lllllllllllllllllIllIllllIllIIIl.placeDelay = lllllllllllllllllIllIllllIllIIIl.sgGeneral.add(new IntSetting.Builder().name("place-delay").description("The delay in ticks in between placement.").defaultValue(1).min(0).sliderMax(10).build());
        lllllllllllllllllIllIllllIllIIIl.mode = lllllllllllllllllIllIllllIllIIIl.sgGeneral.add(new EnumSetting.Builder().name("block").description("The blocks you use to fill holes with.").defaultValue(PlaceMode.Obsidian).build());
        lllllllllllllllllIllIllllIllIIIl.rotate = lllllllllllllllllIllIllllIllIIIl.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards the holes being filled.").defaultValue(true).build());
        lllllllllllllllllIllIllllIllIIIl.blockPos = new Mutable();
    }

    public static final class PlaceMode
    extends Enum<PlaceMode> {
        private static final /* synthetic */ PlaceMode[] $VALUES;
        public static final /* synthetic */ /* enum */ PlaceMode Obsidian;
        public static final /* synthetic */ /* enum */ PlaceMode Both;
        public static final /* synthetic */ /* enum */ PlaceMode Any;
        public static final /* synthetic */ /* enum */ PlaceMode Cobweb;

        static {
            Obsidian = new PlaceMode();
            Cobweb = new PlaceMode();
            Both = new PlaceMode();
            Any = new PlaceMode();
            $VALUES = PlaceMode.$values();
        }

        private static /* synthetic */ PlaceMode[] $values() {
            return new PlaceMode[]{Obsidian, Cobweb, Both, Any};
        }

        public static PlaceMode[] values() {
            return (PlaceMode[])$VALUES.clone();
        }

        private PlaceMode() {
            PlaceMode lIIIIIlIlIIIlI;
        }

        public static PlaceMode valueOf(String lIIIIIlIlIlllI) {
            return Enum.valueOf(PlaceMode.class, lIIIIIlIlIlllI);
        }
    }
}

