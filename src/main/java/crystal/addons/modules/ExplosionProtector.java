package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.entity.EntityAddedEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;

public class ExplosionProtector extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Boolean> tnt = sgGeneral.add(new BoolSetting.Builder().name("anti-tnt-aura").description("Break near tnt blocks").defaultValue(true).build());
    private final Setting<Boolean> crystalHead = sgGeneral.add(new BoolSetting.Builder().name("anti-crystal-head").defaultValue(true).build());
    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Look at block or crystal.").defaultValue(false).build());

    public ExplosionProtector() {
        super(CrystalCL.Exc, "explosion-protector", "Explosion protection.");
    }

    @EventHandler
    private void a(PacketEvent.Receive e) {
        if (online()) {
            BlockPos p;
            List safe;
            if (crystalHead.get() && e.packet instanceof BlockBreakingProgressS2CPacket) {
                BlockBreakingProgressS2CPacket w = (BlockBreakingProgressS2CPacket) e.packet;
                p = mc.player.getBlockPos();
                safe = Arrays.asList(p.up(), p.up(2), p.up(3));
                if (safe.contains(w.getPos())) {
                    safe.forEach((s) -> place_obsidian((BlockPos) s));
                    List finalSafe = safe;
                    mc.world.getEntities().forEach((s) -> {
                        if (s instanceof EndCrystalEntity && finalSafe.contains(s.getBlockPos())) {
                            kill(s);
                        }

                    });
                }
            }

            if (tnt.get() && e.packet instanceof BlockUpdateS2CPacket) {
                BlockUpdateS2CPacket w = (BlockUpdateS2CPacket) e.packet;
                if (tnt.get() && w.getState().getBlock() instanceof TntBlock) {
                    p = mc.player.getBlockPos();
                    safe = Arrays.asList(p, p.down(), p.up(), p.up(2), p.up(3), p.east(), p.west(), p.north(), p.south(), p.up().east(), p.up().west(), p.up().north(), p.up().south(), p.up(2).east(), p.up(2).west(), p.up(2).north(), p.up(2).south());
                    BlockPos a = w.getPos();
                    if (safe.contains(a)) {
                        look(a);
                        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, a, Direction.UP));
                        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, a, Direction.UP));
                        place_obsidian(a);
                    }
                }
            }
        }
    }

    @EventHandler
    private void a(EntityAddedEvent e) {
        if (online() && crystalHead.get() && e.entity instanceof EndCrystalEntity) {
            BlockPos p = mc.player.getBlockPos();
            List<BlockPos> safe = Arrays.asList(p.up(2), p.up(3), p.up(4), p.up(2).east(), p.up(2).west(), p.up(2).north(), p.up(2).south());
            BlockPos a = e.entity.getBlockPos();
            if (safe.contains(a)) {
                place_obsidian(a.down());
                kill(e.entity);
                place_obsidian(a);
                place_obsidian(a.up());
            }
        }
    }

    private boolean online() {
        return mc.world != null && mc.player != null && mc.world.getPlayers().size() > 1;
    }

    private void kill(Entity a) {
        look(a.getBlockPos());
        mc.interactionManager.attackEntity(mc.player, a);
        a.remove(Entity.RemovalReason.KILLED);
    }

    private void place_obsidian(BlockPos a) {
        if (BlockUtils.canPlace(a)) {
            if (mc.player.getBlockPos().getY() - a.getY() <= 2) {
                int obsidian = InvUtils.findInHotbar(new Item[]{Items.OBSIDIAN}).getSlot();
                if (obsidian > -1) {
                    look(a);
                    int pre = mc.player.getInventory().selectedSlot;
                    swap(obsidian);
                    mc.getNetworkHandler().sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, new BlockHitResult(mc.player.getPos(), Direction.DOWN, a, true)));
                    swap(pre);
                }

            }
        }
    }

    private void swap(int a) {
        if (a != mc.player.getInventory().selectedSlot) {
            mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(a));
            mc.player.getInventory().selectedSlot = a;
        }
    }

    private void look(BlockPos a) {
        if (rotate.get()) {
            Vec3d hitPos = new Vec3d(0.0D, 0.0D, 0.0D);
            ((IVec3d) hitPos).set(a.getX() + 0.5D, a.getY() + 0.5D, a.getZ() + 0.5D);
            Rotations.rotate(Rotations.getYaw(hitPos), Rotations.getPitch(hitPos));
        }
    }
}
