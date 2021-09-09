/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.Items
 *  net.minecraft.item.PickaxeItem
 *  net.minecraft.world.BlockView
 *  net.minecraft.block.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action
 *  net.minecraft.network.packet.c2s.play.HandSwingC2SPacket
 *  net.minecraft.util.hit.BlockHitResult
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
    private final /* synthetic */ Setting<Boolean> pick;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Boolean> crystal;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Mutable blockPos;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private /* synthetic */ boolean shouldMine;
    private /* synthetic */ Direction direction;
    private final /* synthetic */ Setting<Integer> tickDelay;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private /* synthetic */ int ticks;

    @EventHandler
    private void onRender(RenderEvent lIllllIIIlllI) {
        InstaMine lIllllIIIllll;
        if (!(lIllllIIIllll.render.get().booleanValue() && lIllllIIIllll.shouldMine() && lIllllIIIllll.shouldMine && lIllllIIIllll.blockPos.getY() != -1)) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (BlockPos)lIllllIIIllll.blockPos, lIllllIIIllll.sideColor.get(), lIllllIIIllll.lineColor.get(), lIllllIIIllll.shapeMode.get(), 0);
    }

    @Override
    public void onActivate() {
        InstaMine lIllllIlIIlIl;
        lIllllIlIIlIl.ticks = 0;
        lIllllIlIIlIl.blockPos.set(0, -1, 0);
        lIllllIlIIlIl.shouldMine = false;
    }

    public InstaMine() {
        super(Categories.World, "insta-mine", "Attempts to instantly mine blocks.");
        InstaMine lIllllIlIlIII;
        lIllllIlIlIII.sgGeneral = lIllllIlIlIII.settings.getDefaultGroup();
        lIllllIlIlIII.sgRender = lIllllIlIlIII.settings.createGroup("Render");
        lIllllIlIlIII.tickDelay = lIllllIlIlIII.sgGeneral.add(new IntSetting.Builder().name("delay").description("The delay between breaks.").defaultValue(0).min(0).sliderMax(20).build());
        lIllllIlIlIII.pick = lIllllIlIlIII.sgGeneral.add(new BoolSetting.Builder().name("only-pick").description("Only tries to mine the block if you are holding a pickaxe.").defaultValue(true).build());
        lIllllIlIlIII.crystal = lIllllIlIlIII.sgGeneral.add(new BoolSetting.Builder().name("crystal").description("Places an end crystal above the block getting mined.").defaultValue(false).build());
        lIllllIlIlIII.rotate = lIllllIlIlIII.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Faces the blocks being mined server side.").defaultValue(true).build());
        lIllllIlIlIII.render = lIllllIlIlIII.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        lIllllIlIlIII.shapeMode = lIllllIlIlIII.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lIllllIlIlIII.sideColor = lIllllIlIlIII.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        lIllllIlIlIII.lineColor = lIllllIlIlIII.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        lIllllIlIlIII.blockPos = new Mutable(0, -1, 0);
    }

    private boolean shouldMine() {
        InstaMine lIllllIIIlIlI;
        if (!lIllllIIIlIlI.pick.get().booleanValue()) {
            return true;
        }
        return lIllllIIIlIlI.pick.get() != false && lIllllIIIlIlI.mc.player.inventory.getMainHandStack().getItem() instanceof PickaxeItem && (lIllllIIIlIlI.mc.player.getMainHandStack().getItem() == Items.DIAMOND_PICKAXE || lIllllIIIlIlI.mc.player.getMainHandStack().getItem() == Items.NETHERITE_PICKAXE);
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent lIllllIIllllI) {
        InstaMine lIllllIlIIIIl;
        lIllllIlIIIIl.direction = lIllllIIllllI.direction;
        lIllllIlIIIIl.blockPos.set((Vec3i)lIllllIIllllI.blockPos);
        lIllllIlIIIIl.shouldMine = lIllllIlIIIIl.mc.world.getBlockState(lIllllIIllllI.blockPos).getHardness((BlockView)lIllllIlIIIIl.mc.world, lIllllIIllllI.blockPos) >= 0.0f;
    }

    @EventHandler
    private void onTick(TickEvent.Pre lIllllIIlIlIl) {
        InstaMine lIllllIIlIlII;
        if (lIllllIIlIlII.ticks >= lIllllIIlIlII.tickDelay.get()) {
            lIllllIIlIlII.ticks = 0;
            if (lIllllIIlIlII.shouldMine() && lIllllIIlIlII.shouldMine && !lIllllIIlIlII.mc.world.getBlockState((BlockPos)lIllllIIlIlII.blockPos).isAir()) {
                if (lIllllIIlIlII.crystal.get().booleanValue() && (lIllllIIlIlII.mc.world.getBlockState((BlockPos)lIllllIIlIlII.blockPos).getBlock().is(Blocks.OBSIDIAN) || lIllllIIlIlII.mc.world.getBlockState((BlockPos)lIllllIIlIlII.blockPos).getBlock().is(Blocks.BEDROCK))) {
                    Hand lIllllIIllIIl = InvUtils.getHand(Items.END_CRYSTAL);
                    int lIllllIIllIII = InvUtils.findItemInHotbar(Items.END_CRYSTAL);
                    int lIllllIIlIlll = lIllllIIlIlII.mc.player.inventory.selectedSlot;
                    if (lIllllIIllIIl != Hand.OFF_HAND && lIllllIIllIII != -1) {
                        lIllllIIlIlII.mc.player.inventory.selectedSlot = lIllllIIllIII;
                    }
                    lIllllIIlIlII.mc.interactionManager.interactBlock(lIllllIIlIlII.mc.player, lIllllIIlIlII.mc.world, lIllllIIllIIl, new BlockHitResult(lIllllIIlIlII.mc.player.getPos(), lIllllIIlIlII.direction, (BlockPos)lIllllIIlIlII.blockPos, false));
                    if (lIllllIIllIIl != Hand.OFF_HAND) {
                        lIllllIIlIlII.mc.player.inventory.selectedSlot = lIllllIIlIlll;
                    }
                }
                if (lIllllIIlIlII.rotate.get().booleanValue()) {
                    Rotations.rotate(Rotations.getYaw((BlockPos)lIllllIIlIlII.blockPos), Rotations.getPitch((BlockPos)lIllllIIlIlII.blockPos), () -> {
                        InstaMine lIllllIIIIlll;
                        lIllllIIIIlll.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, (BlockPos)lIllllIIIIlll.blockPos, lIllllIIIIlll.direction));
                    });
                } else {
                    lIllllIIlIlII.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, (BlockPos)lIllllIIlIlII.blockPos, lIllllIIlIlII.direction));
                }
                lIllllIIlIlII.mc.getNetworkHandler().sendPacket((Packet)new HandSwingC2SPacket(Hand.MAIN_HAND));
            }
        } else {
            ++lIllllIIlIlII.ticks;
        }
    }
}

