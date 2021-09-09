/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.world.BlockView
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Direction$class_2351
 *  net.minecraft.network.Packet
 *  net.minecraft.util.shape.VoxelShape
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action
 *  net.minecraft.network.packet.c2s.play.HandSwingC2SPacket
 */
package minegame159.meteorclient.systems.modules.world;

import java.util.ArrayList;
import java.util.List;
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
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.Pool;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.Hand;
import net.minecraft.world.BlockView;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.network.Packet;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;

public class PacketMine
extends Module {
    private final /* synthetic */ List<MyBlock> blocks;
    private final /* synthetic */ SettingGroup sgRender;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Integer> delay;
    private final /* synthetic */ Pool<MyBlock> blockPool;

    @Override
    public void onDeactivate() {
        PacketMine lIlIIllIlIlIIII;
        for (MyBlock lIlIIllIlIlIIIl : lIlIIllIlIlIIII.blocks) {
            lIlIIllIlIlIIII.blockPool.free(lIlIIllIlIlIIIl);
        }
        lIlIIllIlIlIIII.blocks.clear();
    }

    public PacketMine() {
        super(Categories.World, "packet-mine", "Sends packets to mine blocks without the mining animation.");
        PacketMine lIlIIllIlIlIllI;
        lIlIIllIlIlIllI.sgGeneral = lIlIIllIlIlIllI.settings.getDefaultGroup();
        lIlIIllIlIlIllI.sgRender = lIlIIllIlIlIllI.settings.createGroup("Render");
        lIlIIllIlIlIllI.delay = lIlIIllIlIlIllI.sgGeneral.add(new IntSetting.Builder().name("delay").description("Delay between mining blocks in ticks.").defaultValue(1).min(0).sliderMax(10).build());
        lIlIIllIlIlIllI.rotate = lIlIIllIlIlIllI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when mining.").defaultValue(true).build());
        lIlIIllIlIlIllI.render = lIlIIllIlIlIllI.sgRender.add(new BoolSetting.Builder().name("render").description("Whether or not to render the block being mined.").defaultValue(true).build());
        lIlIIllIlIlIllI.shapeMode = lIlIIllIlIlIllI.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        lIlIIllIlIlIllI.sideColor = lIlIIllIlIlIllI.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        lIlIIllIlIlIllI.lineColor = lIlIIllIlIlIllI.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        lIlIIllIlIlIllI.blockPool = new Pool<MyBlock>(() -> {
            PacketMine lIlIIllIIlIlIIl;
            return lIlIIllIIlIlIIl.new MyBlock();
        });
        lIlIIllIlIlIllI.blocks = new ArrayList<MyBlock>();
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent lIlIIllIIllllII) {
        PacketMine lIlIIllIIllllIl;
        lIlIIllIIllllII.cancel();
        if (lIlIIllIIllllIl.mc.world.getBlockState(lIlIIllIIllllII.blockPos).getHardness((BlockView)lIlIIllIIllllIl.mc.world, lIlIIllIIllllII.blockPos) < 0.0f) {
            return;
        }
        if (!lIlIIllIIllllIl.isMiningBlock(lIlIIllIIllllII.blockPos)) {
            MyBlock lIlIIllIIlllllI = lIlIIllIIllllIl.blockPool.get();
            lIlIIllIIlllllI.set(lIlIIllIIllllII);
            lIlIIllIIllllIl.blocks.add(lIlIIllIIlllllI);
        }
    }

    private boolean isMiningBlock(BlockPos lIlIIllIlIIIlII) {
        PacketMine lIlIIllIlIIIlll;
        for (MyBlock lIlIIllIlIIlIII : lIlIIllIlIIIlll.blocks) {
            if (!lIlIIllIlIIlIII.blockPos.equals((Object)lIlIIllIlIIIlII)) continue;
            return true;
        }
        return false;
    }

    @EventHandler
    private void onRender(RenderEvent lIlIIllIIlIllll) {
        PacketMine lIlIIllIIllIIII;
        if (lIlIIllIIllIIII.render.get().booleanValue()) {
            for (MyBlock lIlIIllIIllIIIl : lIlIIllIIllIIII.blocks) {
                lIlIIllIIllIIIl.render();
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre lIlIIllIIllIllI) {
        PacketMine lIlIIllIIllIlll;
        lIlIIllIIllIlll.blocks.removeIf(MyBlock::shouldRemove);
        if (!lIlIIllIIllIlll.blocks.isEmpty()) {
            lIlIIllIIllIlll.blocks.get(0).mine();
        }
    }

    private class MyBlock {
        public /* synthetic */ BlockPos blockPos;
        public /* synthetic */ Direction direction;
        public /* synthetic */ boolean mining;
        public /* synthetic */ int timer;
        public /* synthetic */ Block originalBlock;

        private void sendMinePackets() {
            MyBlock llIIIlIIlIIlII;
            if (llIIIlIIlIIlII.timer <= 0) {
                if (!llIIIlIIlIIlII.mining) {
                    llIIIlIIlIIlII.PacketMine.this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, llIIIlIIlIIlII.blockPos, llIIIlIIlIIlII.direction));
                    llIIIlIIlIIlII.PacketMine.this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, llIIIlIIlIIlII.blockPos, llIIIlIIlIIlII.direction));
                    llIIIlIIlIIlII.mining = true;
                }
            } else {
                --llIIIlIIlIIlII.timer;
            }
        }

        public void set(StartBreakingBlockEvent llIIIlIIllIIlI) {
            MyBlock llIIIlIIllIIll;
            llIIIlIIllIIll.blockPos = llIIIlIIllIIlI.blockPos;
            llIIIlIIllIIll.direction = llIIIlIIllIIlI.direction;
            llIIIlIIllIIll.originalBlock = ((PacketMine)llIIIlIIllIIll.PacketMine.this).mc.world.getBlockState(llIIIlIIllIIll.blockPos).getBlock();
            llIIIlIIllIIll.timer = (Integer)llIIIlIIllIIll.PacketMine.this.delay.get();
            llIIIlIIllIIll.mining = false;
        }

        public boolean shouldRemove() {
            MyBlock llIIIlIIlIllIl;
            boolean llIIIlIIlIllII;
            boolean bl = llIIIlIIlIllII = ((PacketMine)llIIIlIIlIllIl.PacketMine.this).mc.world.getBlockState(llIIIlIIlIllIl.blockPos).getBlock() != llIIIlIIlIllIl.originalBlock || Utils.distance(((PacketMine)llIIIlIIlIllIl.PacketMine.this).mc.player.getX() - 0.5, ((PacketMine)llIIIlIIlIllIl.PacketMine.this).mc.player.getY() + (double)((PacketMine)llIIIlIIlIllIl.PacketMine.this).mc.player.getEyeHeight(((PacketMine)llIIIlIIlIllIl.PacketMine.this).mc.player.getPose()), ((PacketMine)llIIIlIIlIllIl.PacketMine.this).mc.player.getZ() - 0.5, llIIIlIIlIllIl.blockPos.getX() + llIIIlIIlIllIl.direction.getOffsetX(), llIIIlIIlIllIl.blockPos.getY() + llIIIlIIlIllIl.direction.getOffsetY(), llIIIlIIlIllIl.blockPos.getZ() + llIIIlIIlIllIl.direction.getOffsetZ()) > (double)((PacketMine)llIIIlIIlIllIl.PacketMine.this).mc.interactionManager.getReachDistance();
            if (llIIIlIIlIllII) {
                llIIIlIIlIllIl.PacketMine.this.mc.getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, llIIIlIIlIllIl.blockPos, llIIIlIIlIllIl.direction));
                llIIIlIIlIllIl.PacketMine.this.mc.getNetworkHandler().sendPacket((Packet)new HandSwingC2SPacket(Hand.MAIN_HAND));
            }
            return llIIIlIIlIllII;
        }

        private MyBlock() {
            MyBlock llIIIlIIllIlll;
        }

        public void mine() {
            MyBlock llIIIlIIlIIlll;
            if (((Boolean)llIIIlIIlIIlll.PacketMine.this.rotate.get()).booleanValue()) {
                Rotations.rotate(Rotations.getYaw(llIIIlIIlIIlll.blockPos), Rotations.getPitch(llIIIlIIlIIlll.blockPos), 50, llIIIlIIlIIlll::sendMinePackets);
            } else {
                llIIIlIIlIIlll.sendMinePackets();
            }
        }

        public void render() {
            MyBlock llIIIlIIIlIIll;
            VoxelShape llIIIlIIIllIlI = ((PacketMine)llIIIlIIIlIIll.PacketMine.this).mc.world.getBlockState(llIIIlIIIlIIll.blockPos).getOutlineShape((BlockView)((PacketMine)llIIIlIIIlIIll.PacketMine.this).mc.world, llIIIlIIIlIIll.blockPos);
            double llIIIlIIIllIIl = llIIIlIIIlIIll.blockPos.getX();
            double llIIIlIIIllIII = llIIIlIIIlIIll.blockPos.getY();
            double llIIIlIIIlIlll = llIIIlIIIlIIll.blockPos.getZ();
            double llIIIlIIIlIllI = llIIIlIIIlIIll.blockPos.getX() + 1;
            double llIIIlIIIlIlIl = llIIIlIIIlIIll.blockPos.getY() + 1;
            double llIIIlIIIlIlII = llIIIlIIIlIIll.blockPos.getZ() + 1;
            if (!llIIIlIIIllIlI.isEmpty()) {
                llIIIlIIIllIIl = (double)llIIIlIIIlIIll.blockPos.getX() + llIIIlIIIllIlI.getMin(Direction.class_2351.X);
                llIIIlIIIllIII = (double)llIIIlIIIlIIll.blockPos.getY() + llIIIlIIIllIlI.getMin(Direction.class_2351.Y);
                llIIIlIIIlIlll = (double)llIIIlIIIlIIll.blockPos.getZ() + llIIIlIIIllIlI.getMin(Direction.class_2351.Z);
                llIIIlIIIlIllI = (double)llIIIlIIIlIIll.blockPos.getX() + llIIIlIIIllIlI.getMax(Direction.class_2351.X);
                llIIIlIIIlIlIl = (double)llIIIlIIIlIIll.blockPos.getY() + llIIIlIIIllIlI.getMax(Direction.class_2351.Y);
                llIIIlIIIlIlII = (double)llIIIlIIIlIIll.blockPos.getZ() + llIIIlIIIllIlI.getMax(Direction.class_2351.Z);
            }
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llIIIlIIIllIIl, llIIIlIIIllIII, llIIIlIIIlIlll, llIIIlIIIlIllI, llIIIlIIIlIlIl, llIIIlIIIlIlII, (Color)llIIIlIIIlIIll.PacketMine.this.sideColor.get(), (Color)llIIIlIIIlIIll.PacketMine.this.lineColor.get(), (ShapeMode)((Object)llIIIlIIIlIIll.PacketMine.this.shapeMode.get()), 0);
        }
    }
}

