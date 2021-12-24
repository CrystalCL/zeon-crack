package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ExtraSurround extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgRender = settings.createGroup("Render");
    private final Setting<Integer> tickDelay = sgGeneral.add(new IntSetting.Builder().name("Delay").description("Delay per ticks.").defaultValue(1).min(0).max(20).sliderMin(0).sliderMax(20).build());
    private final Setting<ecenter> center = sgGeneral.add(new EnumSetting.Builder<ecenter>().name("centerTP").description("Teleport to center block.").defaultValue(ecenter.legit).build());
    private final Setting<SurrMode> mode = sgGeneral.add(new EnumSetting.Builder<SurrMode>().name("Mode").description("Mode of the surround.").defaultValue(SurrMode.Normal).build());
    private final Setting<Version> version = sgGeneral.add(new EnumSetting.Builder<Version>().name("version").description("Version of server where u will be pvp.").defaultValue(Version.Old).build());
    private final Setting<List<Block>> blocks = sgGeneral.add(new BlockListSetting.Builder().name("block").description("What blocks to use for surround.").defaultValue(Collections.singletonList(Blocks.OBSIDIAN)).filter(this::blockFilter).build());
    private final Setting<antcry> anti = sgGeneral.add(new EnumSetting.Builder<antcry>().name("anti-crystal-aura").description("Anti Break your surround(place ender-chests).").defaultValue(antcry.Yes).build());
    private final Setting<Boolean> selfProtector = sgGeneral.add(new BoolSetting.Builder().name("self-protector").description("Automatically breaks crystal near ur surround.").defaultValue(true).build());
    private final Setting<Boolean> anticev = sgGeneral.add(new BoolSetting.Builder().name("anti-cev-breaker").description("Placing block 2 blocks up from your head.").defaultValue(false).build());
    private final Setting<Boolean> onlyOnGround = sgGeneral.add(new BoolSetting.Builder().name("only-on-ground").description("Works only when you standing on blocks.").defaultValue(false).build());
    private final Setting<Boolean> disableOnJump = sgGeneral.add(new BoolSetting.Builder().name("disable-on-jump").description("Automatically disables when you jump.").defaultValue(true).build());
    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the obsidian being placed.").defaultValue(false).build());
    private final Setting<Boolean> render = sgGeneral.add(new BoolSetting.Builder().name("render").description("Render surround blocks.").defaultValue(true).build());
    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Lines).build());
    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder().name("side-color").description("The side color.").defaultValue(new SettingColor(255, 255, 255, 75)).build());
    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder().name("line-color").description("The line color.").defaultValue(new SettingColor(255, 255, 255, 255)).build());

    private static MinecraftClient mc = MinecraftClient.getInstance();
    private int ticks;
    private PlayerEntity target;
    BlockPos pos = null;
    private static final ArrayList<Vec3d> norm;
    private static final ArrayList<Vec3d> doub;
    private static final ArrayList<Vec3d> full;
    private static final BlockPos.Mutable blockPos = new BlockPos.Mutable();;

    public ExtraSurround() {
        super(CrystalCL.Exc, "surround+", "Surround+");
    }

    public void onActivate() {
        ticks = 0;
        if (center.get() == ecenter.fast) {
            double tx = 0.0D;
            double tz = 0.0D;
            Vec3d p = mc.player.getPos();
            if (p.x > 0.0D && gp(p.x) < 3L) {
                tx = 0.3D;
            }

            if (p.x > 0.0D && gp(p.x) > 6L) {
                tx = -0.3D;
            }

            if (p.x < 0.0D && gp(p.x) < 3L) {
                tx = -0.3D;
            }

            if (p.x < 0.0D && gp(p.x) > 6L) {
                tx = 0.3D;
            }

            if (p.z > 0.0D && gp(p.z) < 3L) {
                tz = 0.3D;
            }

            if (p.z > 0.0D && gp(p.z) > 6L) {
                tz = -0.3D;
            }

            if (p.z < 0.0D && gp(p.z) < 3L) {
                tz = -0.3D;
            }

            if (p.z < 0.0D && gp(p.z) > 6L) {
                tz = 0.3D;
            }

            if (tx != 0.0D || tz != 0.0D) {
                double posx = mc.player.getX() + tx;
                double posz = mc.player.getZ() + tz;
                mc.player.updatePosition(posx, mc.player.getY(), posz);
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.isOnGround()));
            }
        }
    }

    private long gp(double v) {
        BigDecimal v1 = BigDecimal.valueOf(v);
        BigDecimal v2 = v1.remainder(BigDecimal.ONE);
        return Byte.parseByte(String.valueOf(String.valueOf(v2).replace("0.", "").replace("-", "").charAt(0)));
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        BlockPos bb;
        if (ticks < tickDelay.get()) {
            ticks++;
        } else {
            ticks = 0;
            if (center.get() == ecenter.legit) {
                double tx = 0.0D;
                double tz = 0.0D;
                Vec3d p = mc.player.getPos();
                if (p.x > 0.0D && gp(p.x) < 3L) {
                    tx = 0.185D;
                }

                if (p.x > 0.0D && gp(p.x) > 6L) {
                    tx = -0.185D;
                }

                if (p.x < 0.0D && gp(p.x) < 3L) {
                    tx = -0.185D;
                }

                if (p.x < 0.0D && gp(p.x) > 6L) {
                    tx = 0.185D;
                }

                if (p.z > 0.0D && gp(p.z) < 3L) {
                    tz = 0.185D;
                }

                if (p.z > 0.0D && gp(p.z) > 6L) {
                    tz = -0.185D;
                }

                if (p.z < 0.0D && gp(p.z) < 3L) {
                    tz = -0.185D;
                }

                if (p.z < 0.0D && gp(p.z) > 6L) {
                    tz = 0.185D;
                }

                if (tx != 0.0D || tz != 0.0D) {
                    double posx = mc.player.getX() + tx;
                    double posz = mc.player.getZ() + tz;
                    mc.player.updatePosition(posx, mc.player.getY(), posz);
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.isOnGround()));
                    return;
                }
            }

            if (disableOnJump.get() && mc.options.keyJump.isPressed()) {
                toggle();
                return;
            }

            if (onlyOnGround.get() && !mc.player.isOnGround()) {
                return;
            }

            if (version.get() == Version.New) {
                if (mode.get() == SurrMode.Normal) {
                    if (p(0, -1, 0)) {
                        return;
                    }

                    if (p(1, 0, 0)) {
                        return;
                    }

                    if (p(-1, 0, 0)) {
                        return;
                    }

                    if (p(0, 0, 1)) {
                        return;
                    }

                    if (p(0, 0, -1)) {
                        return;
                    }

                    if (anticev.get()) {
                        if (p(1, 1, 0)) {
                            return;
                        }

                        if (p(0, 1, 1)) {
                            return;
                        }

                        if (p(-1, 1, 0)) {
                            return;
                        }

                        if (p(0, 1, -1)) {
                            return;
                        }

                        if (p(0, 2, 0)) {
                            return;
                        }

                        if (p(0, 3, 0)) {
                            return;
                        }
                    }

                    if (anti.get() == antcry.Yes) {
                        if (e(1, -1, 0)) {
                            return;
                        }

                        if (e(-1, -1, 0)) {
                            return;
                        }

                        if (e(0, -1, 1)) {
                            return;
                        }

                        if (e(0, -1, -1)) {
                            return;
                        }
                    } else if (anti.get() == antcry.No) {
                        if (p(1, -1, 0)) {
                            return;
                        }

                        if (p(-1, -1, 0)) {
                            return;
                        }

                        if (p(0, -1, 1)) {
                            return;
                        }

                        if (p(0, -1, -1)) {
                            return;
                        }
                    }
                }

                if (mode.get() == SurrMode.Full) {
                    if (p(0, -1, 0)) {
                        return;
                    }

                    if (p(1, 0, 0)) {
                        return;
                    }

                    if (p(-1, 0, 0)) {
                        return;
                    }

                    if (p(0, 0, 1)) {
                        return;
                    }

                    if (p(0, 0, -1)) {
                        return;
                    }

                    if (p(0, -1, 0)) {
                        return;
                    }

                    if (p(0, -2, 0)) {
                        return;
                    }

                    if (p(1, 0, 1)) {
                        return;
                    }

                    if (p(-1, 0, -1)) {
                        return;
                    }

                    if (p(-1, 0, 1)) {
                        return;
                    }

                    if (p(1, 0, -1)) {
                        return;
                    }

                    if (p(2, 0, 0)) {
                        return;
                    }

                    if (p(-2, 0, 0)) {
                        return;
                    }

                    if (p(0, 0, 2)) {
                        return;
                    }

                    if (p(0, 0, -2)) {
                        return;
                    }

                    if (p(1, 1, 0)) {
                        return;
                    }

                    if (p(-1, 1, 0)) {
                        return;
                    }

                    if (p(0, 1, 1)) {
                        return;
                    }

                    if (p(0, 1, -1)) {
                        return;
                    }

                    if (p(1, 2, 0)) {
                        return;
                    }

                    if (p(0, 2, 1)) {
                        return;
                    }

                    if (p(-1, 2, 0)) {
                        return;
                    }

                    if (p(0, 2, -1)) {
                        return;
                    }

                    if (p(0, 3, 0)) {
                        return;
                    }

                    if (p(1, 2, 0)) {
                        return;
                    }

                    if (p(0, 2, 0)) {
                        return;
                    }

                    if (anticev.get()) {
                        if (p(1, 1, 0)) {
                            return;
                        }

                        if (p(0, 1, 1)) {
                            return;
                        }

                        if (p(-1, 1, 0)) {
                            return;
                        }

                        if (p(0, 1, -1)) {
                            return;
                        }

                        if (p(0, 2, 0)) {
                            return;
                        }

                        if (p(0, 3, 0)) {
                            return;
                        }
                    }

                    if (anti.get() == antcry.Yes) {
                        if (e(1, -1, 0)) {
                            return;
                        }

                        if (e(-1, -1, 0)) {
                            return;
                        }

                        if (e(0, -1, 1)) {
                            return;
                        }

                        if (e(0, -1, -1)) {
                            return;
                        }
                    } else if (anti.get() == antcry.No) {
                        if (p(1, -1, 0)) {
                            return;
                        }

                        if (p(-1, -1, 0)) {
                            return;
                        }

                        if (p(0, -1, 1)) {
                            return;
                        }

                        if (p(0, -1, -1)) {
                            return;
                        }
                    }
                }

                if (mode.get() == SurrMode.Double) {
                    if (p(0, -1, 0)) {
                        return;
                    }

                    if (p(1, 0, 0)) {
                        return;
                    }

                    if (p(-1, 0, 0)) {
                        return;
                    }

                    if (p(0, 0, 1)) {
                        return;
                    }

                    if (p(0, 0, -1)) {
                        return;
                    }

                    if (p(1, 1, 0)) {
                        return;
                    }

                    if (p(-1, 1, 0)) {
                        return;
                    }

                    if (p(0, 1, 1)) {
                        return;
                    }

                    if (p(0, 1, -1)) {
                        return;
                    }

                    if (anticev.get()) {
                        if (p(1, 1, 0)) {
                            return;
                        }

                        if (p(0, 1, 1)) {
                            return;
                        }

                        if (p(-1, 1, 0)) {
                            return;
                        }

                        if (p(0, 1, -1)) {
                            return;
                        }

                        if (p(0, 2, 0)) {
                            return;
                        }

                        if (p(0, 3, 0)) {
                            return;
                        }
                    }

                    if (anti.get() == antcry.Yes) {
                        if (e(1, -1, 0)) {
                            return;
                        }

                        if (e(-1, -1, 0)) {
                            return;
                        }

                        if (e(0, -1, 1)) {
                            return;
                        }

                        if (e(0, -1, -1)) {
                            return;
                        }
                    } else if (anti.get() == antcry.No) {
                        if (p(1, -1, 0)) {
                            return;
                        }

                        if (p(-1, -1, 0)) {
                            return;
                        }

                        if (p(0, -1, 1)) {
                            return;
                        }

                        if (p(0, -1, -1)) {
                            return;
                        }
                    }
                }
            } else if (version.get() == Version.Old) {
                if (disableOnJump.get() && mc.options.keyJump.isPressed()) {
                    toggle();
                    return;
                }

                if (onlyOnGround.get() && !mc.player.isOnGround()) {
                    return;
                }

                if (!isVecComplete(getSurrDesign())) {
                    BlockPos ppos = mc.player.getBlockPos();

                    for (Vec3d b : getSurrDesign()) {
                        bb = ppos.add(b.x, b.y, b.z);
                        if (getBlock(bb) == Blocks.AIR && selfProtector.get()) {
                            BlockUtils.place(bb, InvUtils.findInHotbar((itemStack) -> (blocks.get()).contains(Block.getBlockFromItem(itemStack.getItem()))), rotate.get(), 100, false);
                        }
                    }
                }
            }
        }

        if (selfProtector.get()) {
            for (Entity entity : mc.world.getEntities()) {
                if (entity instanceof EndCrystalEntity) {
                    int slot1 = InvUtils.findInHotbar(Items.OBSIDIAN).getSlot();
                    bb = entity.getBlockPos();
                    if (isDangerousCrystal(bb)) {
                        mc.interactionManager.attackEntity(mc.player, entity);
                        entity.remove(Entity.RemovalReason.KILLED);
                        Ezz.BlockPlace(bb, slot1, rotate.get());
                        return;
                    }
                }
            }
        }
    }

    private ArrayList<Vec3d> getSurrDesign() {
        ArrayList<Vec3d> surrDesign = new ArrayList(norm);
        if (mode.get() == SurrMode.Double) {
            surrDesign.addAll(doub);
        }

        if (mode.get() == SurrMode.Full) {
            surrDesign.addAll(full);
        }

        return surrDesign;
    }

    @EventHandler
    private void onRender(Render3DEvent e) {
        if (render.get()) {
            if (mc.player.getBlockPos().south() != null) {
                e.renderer.box(mc.player.getBlockPos().south(), sideColor.get(), lineColor.get(), (ShapeMode) shapeMode.get(), 0);
            }

            if (mc.player.getBlockPos().west() != null) {
                e.renderer.box(mc.player.getBlockPos().west(), sideColor.get(), lineColor.get(), (ShapeMode) shapeMode.get(), 0);
            }

            if (mc.player.getBlockPos().north() != null) {
                e.renderer.box(mc.player.getBlockPos().north(), sideColor.get(), lineColor.get(), (ShapeMode) shapeMode.get(), 0);
            }

            if (mc.player.getBlockPos().east() != null) {
                e.renderer.box(mc.player.getBlockPos().east(), sideColor.get(), lineColor.get(), (ShapeMode) shapeMode.get(), 0);
            }
        }
    }

    private boolean isDangerousCrystal(BlockPos bp) {
        BlockPos ppos = mc.player.getBlockPos();
        Iterator var3 = getSurrDesign().iterator();

        BlockPos bb;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            Vec3d b = (Vec3d) var3.next();
            bb = ppos.add(b.x, b.y, b.z);
        } while (bp.equals(bb) || !(distanceBetween(bb, bp) <= 2.0D));

        return true;
    }

    private boolean p(int x, int y, int z) {
        return Ezz.BlockPlace(Ezz.SetRelative(x, y, z), InvUtils.findInHotbar(Items.OBSIDIAN).getSlot(), rotate.get());
    }

    private boolean e(int x, int y, int z) {
        return Ezz.BlockPlace(Ezz.SetRelative(x, y, z), InvUtils.findInHotbar(Items.ENDER_CHEST).getSlot(), rotate.get());
    }

    public static double distanceBetween(BlockPos pos1, BlockPos pos2) {
        double d = (pos1.getX() - pos2.getX());
        double e = (pos1.getY() - pos2.getY());
        double f = (pos1.getZ() - pos2.getZ());
        return MathHelper.sqrt((float) (d * d + e * e + f * f));
    }

    public static boolean isVecComplete(ArrayList<Vec3d> vlist) {
        BlockPos ppos = mc.player.getBlockPos();
        Iterator var2 = vlist.iterator();

        BlockPos bb;
        do {
            if (!var2.hasNext()) {
                return true;
            }

            Vec3d b = (Vec3d) var2.next();
            bb = ppos.add(b.x, b.y, b.z);
        } while (getBlock(bb) != Blocks.AIR);

        return false;
    }

    public static Block getBlock(BlockPos p) {
        return p == null ? null : mc.world.getBlockState(p).getBlock();
    }

    private boolean blockFilter(Block block) {
        return block == Blocks.OBSIDIAN;
    }

    static {
        norm = new ArrayList<Vec3d>() {
            {
                add(new Vec3d(0.0D, -1.0D, 0.0D));
                add(new Vec3d(1.0D, 0.0D, 0.0D));
                add(new Vec3d(-1.0D, 0.0D, 0.0D));
                add(new Vec3d(0.0D, 0.0D, 1.0D));
                add(new Vec3d(0.0D, 0.0D, -1.0D));
            }
        };
        doub = new ArrayList<Vec3d>() {
            {
                add(new Vec3d(0.0D, -1.0D, 0.0D));
                add(new Vec3d(1.0D, 0.0D, 0.0D));
                add(new Vec3d(-1.0D, 0.0D, 0.0D));
                add(new Vec3d(0.0D, 0.0D, 1.0D));
                add(new Vec3d(0.0D, 0.0D, -1.0D));
                add(new Vec3d(1.0D, 1.0D, 0.0D));
                add(new Vec3d(-1.0D, 1.0D, 0.0D));
                add(new Vec3d(0.0D, 1.0D, 1.0D));
                add(new Vec3d(0.0D, 1.0D, -1.0D));
            }
        };
        full = new ArrayList<Vec3d>() {
            {
                add(new Vec3d(0.0D, -1.0D, 0.0D));
                add(new Vec3d(1.0D, 0.0D, 0.0D));
                add(new Vec3d(-1.0D, 0.0D, 0.0D));
                add(new Vec3d(0.0D, 0.0D, 1.0D));
                add(new Vec3d(0.0D, 0.0D, -1.0D));
                add(new Vec3d(1.0D, 1.0D, 0.0D));
                add(new Vec3d(-1.0D, 1.0D, 0.0D));
                add(new Vec3d(0.0D, 1.0D, 1.0D));
                add(new Vec3d(0.0D, 1.0D, -1.0D));
                add(new Vec3d(1.0D, 0.0D, 1.0D));
                add(new Vec3d(-1.0D, 0.0D, 1.0D));
                add(new Vec3d(-1.0D, 0.0D, -1.0D));
                add(new Vec3d(1.0D, 0.0D, -1.0D));
                add(new Vec3d(2.0D, 0.0D, 0.0D));
                add(new Vec3d(0.0D, 0.0D, 2.0D));
                add(new Vec3d(-2.0D, 0.0D, 0.0D));
                add(new Vec3d(0.0D, 0.0D, -2.0D));
                add(new Vec3d(0.0D, 2.0D, 0.0D));
                add(new Vec3d(1.0D, 2.0D, 0.0D));
                add(new Vec3d(0.0D, 2.0D, 1.0D));
                add(new Vec3d(-1.0D, 2.0D, 0.0D));
                add(new Vec3d(0.0D, 2.0D, -1.0D));
                add(new Vec3d(0.0D, 3.0D, 0.0D));
            }
        };
    }

    public enum ecenter {
        fast,
        legit,
        NoTP
    }

    public enum SurrMode {
        Normal,
        Double,
        Full
    }

    public enum Version {
        Old,
        New
    }

    public enum antcry {
        Yes,
        No
    }
}
