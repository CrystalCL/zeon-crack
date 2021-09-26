/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.client.MinecraftClient;

public class PacketMine
extends Module {
    private final List<MyBlock> blocks;
    private final SettingGroup sgRender;
    private final Pool<MyBlock> blockPool;
    private final Setting<Boolean> render;
    private final Setting<SettingColor> lineColor;
    private final Setting<ShapeMode> shapeMode;
    private final SettingGroup sgGeneral;
    private final Setting<SettingColor> sideColor;
    private final Setting<Integer> delay;
    private final Setting<Boolean> rotate;

    public PacketMine() {
        super(Categories.World, "packet-mine", "Sends packets to mine blocks without the mining animation.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.delay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("Delay between mining blocks in ticks.").defaultValue(1).min(0).sliderMax(10).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Sends rotation packets to the server when mining.").defaultValue(true).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Whether or not to render the block being mined.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        this.blockPool = new Pool<MyBlock>(this::lambda$new$0);
        this.blocks = new ArrayList<MyBlock>();
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        this.blocks.removeIf(MyBlock::shouldRemove);
        if (!this.blocks.isEmpty()) {
            this.blocks.get(0).mine();
        }
    }

    static Setting access$1100(PacketMine packetMine) {
        return packetMine.rotate;
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (this.render.get().booleanValue()) {
            for (MyBlock myBlock : this.blocks) {
                myBlock.render();
            }
        }
    }

    static MinecraftClient access$900(PacketMine packetMine) {
        return packetMine.mc;
    }

    static Setting access$1600(PacketMine packetMine) {
        return packetMine.sideColor;
    }

    static MinecraftClient access$1000(PacketMine packetMine) {
        return packetMine.mc;
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent startBreakingBlockEvent) {
        startBreakingBlockEvent.cancel();
        if (this.mc.world.getBlockState(startBreakingBlockEvent.blockPos).getHardness((BlockView)this.mc.world, startBreakingBlockEvent.blockPos) < 0.0f) {
            return;
        }
        if (!this.isMiningBlock(startBreakingBlockEvent.blockPos)) {
            MyBlock myBlock = this.blockPool.get();
            myBlock.set(startBreakingBlockEvent);
            this.blocks.add(myBlock);
        }
    }

    static MinecraftClient access$1400(PacketMine packetMine) {
        return packetMine.mc;
    }

    static MinecraftClient access$300(PacketMine packetMine) {
        return packetMine.mc;
    }

    static Setting access$100(PacketMine packetMine) {
        return packetMine.delay;
    }

    static Setting access$1800(PacketMine packetMine) {
        return packetMine.shapeMode;
    }

    static MinecraftClient access$1300(PacketMine packetMine) {
        return packetMine.mc;
    }

    static MinecraftClient access$600(PacketMine packetMine) {
        return packetMine.mc;
    }

    static MinecraftClient access$200(PacketMine packetMine) {
        return packetMine.mc;
    }

    static MinecraftClient access$400(PacketMine packetMine) {
        return packetMine.mc;
    }

    static MinecraftClient access$700(PacketMine packetMine) {
        return packetMine.mc;
    }

    static MinecraftClient access$000(PacketMine packetMine) {
        return packetMine.mc;
    }

    static MinecraftClient access$1200(PacketMine packetMine) {
        return packetMine.mc;
    }

    static MinecraftClient access$500(PacketMine packetMine) {
        return packetMine.mc;
    }

    private MyBlock lambda$new$0() {
        return new MyBlock(this, null);
    }

    static Setting access$1700(PacketMine packetMine) {
        return packetMine.lineColor;
    }

    private boolean isMiningBlock(BlockPos BlockPos2) {
        for (MyBlock myBlock : this.blocks) {
            if (!myBlock.blockPos.equals((Object)BlockPos2)) continue;
            return true;
        }
        return false;
    }

    static MinecraftClient access$800(PacketMine packetMine) {
        return packetMine.mc;
    }

    static MinecraftClient access$1500(PacketMine packetMine) {
        return packetMine.mc;
    }

    @Override
    public void onDeactivate() {
        for (MyBlock myBlock : this.blocks) {
            this.blockPool.free(myBlock);
        }
        this.blocks.clear();
    }

    private class MyBlock {
        final PacketMine this$0;
        public BlockPos blockPos;
        public Direction direction;
        public boolean mining;
        public Block originalBlock;
        public int timer;

        public void render() {
            VoxelShape VoxelShape2 = PacketMine.access$1500((PacketMine)this.this$0).world.getBlockState(this.blockPos).getOutlineShape((BlockView)PacketMine.access$1400((PacketMine)this.this$0).world, this.blockPos);
            double d = this.blockPos.getX();
            double d2 = this.blockPos.getY();
            double d3 = this.blockPos.getZ();
            double d4 = this.blockPos.getX() + 1;
            double d5 = this.blockPos.getY() + 1;
            double d6 = this.blockPos.getZ() + 1;
            if (!VoxelShape2.isEmpty()) {
                d = (double)this.blockPos.getX() + VoxelShape2.getMin(Direction.class_2351.X);
                d2 = (double)this.blockPos.getY() + VoxelShape2.getMin(Direction.class_2351.Y);
                d3 = (double)this.blockPos.getZ() + VoxelShape2.getMin(Direction.class_2351.Z);
                d4 = (double)this.blockPos.getX() + VoxelShape2.getMax(Direction.class_2351.X);
                d5 = (double)this.blockPos.getY() + VoxelShape2.getMax(Direction.class_2351.Y);
                d6 = (double)this.blockPos.getZ() + VoxelShape2.getMax(Direction.class_2351.Z);
            }
            Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, d, d2, d3, d4, d5, d6, (Color)PacketMine.access$1600(this.this$0).get(), (Color)PacketMine.access$1700(this.this$0).get(), (ShapeMode)((Object)PacketMine.access$1800(this.this$0).get()), 0);
        }

        public boolean shouldRemove() {
            boolean bl;
            boolean bl2 = bl = PacketMine.access$200((PacketMine)this.this$0).world.getBlockState(this.blockPos).getBlock() != this.originalBlock || Utils.distance(PacketMine.access$300((PacketMine)this.this$0).player.getX() - 0.5, PacketMine.access$400((PacketMine)this.this$0).player.getY() + (double)PacketMine.access$600((PacketMine)this.this$0).player.getEyeHeight(PacketMine.access$500((PacketMine)this.this$0).player.getPose()), PacketMine.access$700((PacketMine)this.this$0).player.getZ() - 0.5, this.blockPos.getX() + this.direction.getOffsetX(), this.blockPos.getY() + this.direction.getOffsetY(), this.blockPos.getZ() + this.direction.getOffsetZ()) > (double)PacketMine.access$800((PacketMine)this.this$0).interactionManager.getReachDistance();
            if (bl) {
                PacketMine.access$900(this.this$0).getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, this.blockPos, this.direction));
                PacketMine.access$1000(this.this$0).getNetworkHandler().sendPacket((Packet)new HandSwingC2SPacket(Hand.MAIN_HAND));
            }
            return bl;
        }

        MyBlock(PacketMine packetMine, 1 var2_2) {
            this(packetMine);
        }

        private void sendMinePackets() {
            if (this.timer <= 0) {
                if (!this.mining) {
                    PacketMine.access$1200(this.this$0).getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, this.blockPos, this.direction));
                    PacketMine.access$1300(this.this$0).getNetworkHandler().sendPacket((Packet)new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, this.blockPos, this.direction));
                    this.mining = true;
                }
            } else {
                --this.timer;
            }
        }

        private MyBlock(PacketMine packetMine) {
            this.this$0 = packetMine;
        }

        public void mine() {
            if (((Boolean)PacketMine.access$1100(this.this$0).get()).booleanValue()) {
                Rotations.rotate(Rotations.getYaw(this.blockPos), Rotations.getPitch(this.blockPos), 50, this::sendMinePackets);
            } else {
                this.sendMinePackets();
            }
        }

        public void set(StartBreakingBlockEvent startBreakingBlockEvent) {
            this.blockPos = startBreakingBlockEvent.blockPos;
            this.direction = startBreakingBlockEvent.direction;
            this.originalBlock = PacketMine.access$000((PacketMine)this.this$0).world.getBlockState(this.blockPos).getBlock();
            this.timer = (Integer)PacketMine.access$100(this.this$0).get();
            this.mining = false;
        }
    }
}

