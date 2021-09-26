/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.block.BlockState;

public class HoleFiller
extends Module {
    private final Setting<Integer> verticalRadius;
    private final Setting<Integer> horizontalRadius;
    private final Setting<Boolean> rotate;
    private final Mutable blockPos;
    private final Setting<Integer> placeDelay;
    private final Setting<PlaceMode> mode;
    private final SettingGroup sgGeneral;
    private int tickDelayLeft;

    @Override
    public void onActivate() {
        this.tickDelayLeft = 0;
    }

    private void lambda$onTick$0(int n, BlockPos BlockPos2, BlockState BlockState2) {
        if (!BlockState2.getMaterial().isReplaceable()) {
            return;
        }
        this.blockPos.set((Vec3i)BlockPos2);
        Block Block2 = this.mc.world.getBlockState((BlockPos)this.add(0, -1, 0)).getBlock();
        if (Block2 != Blocks.BEDROCK && Block2 != Blocks.OBSIDIAN) {
            return;
        }
        Block Block3 = this.mc.world.getBlockState((BlockPos)this.add(0, 1, 1)).getBlock();
        if (Block3 != Blocks.BEDROCK && Block3 != Blocks.OBSIDIAN) {
            return;
        }
        Block Block4 = this.mc.world.getBlockState((BlockPos)this.add(0, 0, -2)).getBlock();
        if (Block4 != Blocks.BEDROCK && Block4 != Blocks.OBSIDIAN) {
            return;
        }
        Block Block5 = this.mc.world.getBlockState((BlockPos)this.add(1, 0, 1)).getBlock();
        if (Block5 != Blocks.BEDROCK && Block5 != Blocks.OBSIDIAN) {
            return;
        }
        Block Block6 = this.mc.world.getBlockState((BlockPos)this.add(-2, 0, 0)).getBlock();
        if (Block6 != Blocks.BEDROCK && Block6 != Blocks.OBSIDIAN) {
            return;
        }
        this.add(1, 0, 0);
        if (BlockUtils.place((BlockPos)this.blockPos, Hand.MAIN_HAND, n, this.rotate.get(), 0, true)) {
            BlockIterator.disableCurrent();
        }
    }

    public HoleFiller() {
        super(Categories.Combat, "hole-filler", "Fills holes with specified blocks.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.horizontalRadius = this.sgGeneral.add(new IntSetting.Builder().name("horizontal-radius").description("Horizontal radius in which to search for holes.").defaultValue(4).min(0).sliderMax(6).build());
        this.verticalRadius = this.sgGeneral.add(new IntSetting.Builder().name("vertical-radius").description("Vertical radius in which to search for holes.").defaultValue(4).min(0).sliderMax(6).build());
        this.placeDelay = this.sgGeneral.add(new IntSetting.Builder().name("place-delay").description("The delay in ticks in between placement.").defaultValue(1).min(0).sliderMax(10).build());
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("block").description("The blocks you use to fill holes with.").defaultValue(PlaceMode.Obsidian).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards the holes being filled.").defaultValue(true).build());
        this.blockPos = new Mutable();
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n = this.findSlot();
        if (n != -1 && this.tickDelayLeft <= 0) {
            this.tickDelayLeft = this.placeDelay.get();
            BlockIterator.register(this.horizontalRadius.get(), this.verticalRadius.get(), (arg_0, arg_1) -> this.lambda$onTick$0(n, arg_0, arg_1));
        }
        --this.tickDelayLeft;
    }

    private Mutable add(int n, int n2, int n3) {
        this.blockPos.setX(this.blockPos.getX() + n);
        this.blockPos.setY(this.blockPos.getY() + n2);
        this.blockPos.setZ(this.blockPos.getZ() + n3);
        return this.blockPos;
    }

    private int findSlot() {
        block6: for (int i = 0; i < 9; ++i) {
            ItemStack ItemStack2 = this.mc.player.inventory.getStack(i);
            switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$combat$HoleFiller$PlaceMode[this.mode.get().ordinal()]) {
                case 1: {
                    if (ItemStack2.getItem() != Items.OBSIDIAN && ItemStack2.getItem() != Items.CRYING_OBSIDIAN) continue block6;
                    return i;
                }
                case 2: {
                    if (ItemStack2.getItem() != Items.COBWEB) continue block6;
                    return i;
                }
                case 3: {
                    if (ItemStack2.getItem() != Items.COBWEB && ItemStack2.getItem() != Items.OBSIDIAN && ItemStack2.getItem() != Items.CRYING_OBSIDIAN) continue block6;
                    return i;
                }
                case 4: {
                    if (!(ItemStack2.getItem() instanceof BlockItem) || !((BlockItem)ItemStack2.getItem()).getBlock().getDefaultState().isFullCube((BlockView)this.mc.world, (BlockPos)this.blockPos)) continue block6;
                    return i;
                }
            }
            if (!false) continue;
            return 0;
        }
        return -1;
    }

    public static final class PlaceMode
    extends Enum<PlaceMode> {
        public static final /* enum */ PlaceMode Cobweb;
        public static final /* enum */ PlaceMode Any;
        private static final PlaceMode[] $VALUES;
        public static final /* enum */ PlaceMode Both;
        public static final /* enum */ PlaceMode Obsidian;

        public static PlaceMode[] values() {
            return (PlaceMode[])$VALUES.clone();
        }

        private static PlaceMode[] $values() {
            return new PlaceMode[]{Obsidian, Cobweb, Both, Any};
        }

        static {
            Obsidian = new PlaceMode();
            Cobweb = new PlaceMode();
            Both = new PlaceMode();
            Any = new PlaceMode();
            $VALUES = PlaceMode.$values();
        }

        public static PlaceMode valueOf(String string) {
            return Enum.valueOf(PlaceMode.class, string);
        }
    }
}

