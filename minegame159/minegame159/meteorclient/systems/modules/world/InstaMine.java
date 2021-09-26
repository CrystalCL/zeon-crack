/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.StartBreakingBlockEvent;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.Hand;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.world.BlockView;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.hit.BlockHitResult;

public class InstaMine
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> crystal;
    private final Mutable blockPos;
    private final Setting<Boolean> rotate;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<SettingColor> lineColor;
    private final SettingGroup sgRender;
    private Direction direction;
    private boolean shouldMine;
    private final Setting<Boolean> pick;
    private int ticks;
    private final Setting<Integer> tickDelay;
    private final Setting<Boolean> render;
    private final Setting<SettingColor> sideColor;

    @Override
    public void onActivate() {
        this.ticks = 0;
        this.blockPos.set(0, -1, 0);
        this.shouldMine = false;
    }

    private void lambda$onTick$0() {
        this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, (BlockPos)this.blockPos, this.direction));
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (!(this.render.get().booleanValue() && this.shouldMine() && this.shouldMine && this.blockPos.getY() != -1)) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (BlockPos)this.blockPos, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.ticks >= this.tickDelay.get()) {
            this.ticks = 0;
            if (this.shouldMine() && this.shouldMine && !this.mc.world.getBlockState((BlockPos)this.blockPos).isAir()) {
                if (this.crystal.get().booleanValue() && (this.mc.world.getBlockState((BlockPos)this.blockPos).getBlock().is(Blocks.OBSIDIAN) || this.mc.world.getBlockState((BlockPos)this.blockPos).getBlock().is(Blocks.BEDROCK))) {
                    Hand Hand2 = InvUtils.getHand(Items.END_CRYSTAL);
                    int n = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
                    int n2 = this.mc.player.inventory.selectedSlot;
                    if (Hand2 != Hand.OFF_HAND && n != -1) {
                        this.mc.player.inventory.selectedSlot = n;
                    }
                    this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand2, new BlockHitResult(this.mc.player.getPos(), this.direction, (BlockPos)this.blockPos, false));
                    if (Hand2 != Hand.OFF_HAND) {
                        this.mc.player.inventory.selectedSlot = n2;
                    }
                }
                if (this.rotate.get().booleanValue()) {
                    Rotations.rotate(Rotations.getYaw((BlockPos)this.blockPos), Rotations.getPitch((BlockPos)this.blockPos), this::lambda$onTick$0);
                } else {
                    this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, (BlockPos)this.blockPos, this.direction));
                }
                this.mc.getNetworkHandler().sendPacket((Packet)new HandSwingC2SPacket(Hand.MAIN_HAND));
            }
        } else {
            ++this.ticks;
        }
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent startBreakingBlockEvent) {
        this.direction = startBreakingBlockEvent.direction;
        this.blockPos.set((Vec3i)startBreakingBlockEvent.blockPos);
        this.shouldMine = this.mc.world.getBlockState(startBreakingBlockEvent.blockPos).getHardness((BlockView)this.mc.world, startBreakingBlockEvent.blockPos) >= 0.0f;
    }

    private boolean shouldMine() {
        if (!this.pick.get().booleanValue()) {
            return true;
        }
        return this.pick.get() != false && this.mc.player.inventory.getMainHandStack().getItem() instanceof PickaxeItem && (this.mc.player.getMainHandStack().getItem() == Items.DIAMOND_PICKAXE || this.mc.player.getMainHandStack().getItem() == Items.NETHERITE_PICKAXE);
    }

    public InstaMine() {
        super(Categories.World, "insta-mine", "Attempts to instantly mine blocks.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.tickDelay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("The delay between breaks.").defaultValue(0).min(0).sliderMax(20).build());
        this.pick = this.sgGeneral.add(new BoolSetting.Builder().name("only-pick").description("Only tries to mine the block if you are holding a pickaxe.").defaultValue(true).build());
        this.crystal = this.sgGeneral.add(new BoolSetting.Builder().name("crystal").description("Places an end crystal above the block getting mined.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Faces the blocks being mined server side.").defaultValue(true).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        this.blockPos = new Mutable(0, -1, 0);
    }
}

