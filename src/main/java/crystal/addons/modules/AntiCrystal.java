package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.entity.EntityAddedEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;

public class AntiCrystal extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>().name("Mode").description("Mode.").defaultValue(Mode.Obsidian).build());
    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Look at block or crystal.").defaultValue(false).build());

    public AntiCrystal() {
        super(CrystalCL.Exc, "anti-crystal", "Anti Crystal break surround.");
    }

    @EventHandler
    private void a(PacketEvent.Receive e) {
        if (online()) {
            if (mode.get() == Mode.Obsidian && e.packet instanceof BlockBreakingProgressS2CPacket w) {
                BlockPos p = mc.player.getBlockPos();
                List<BlockPos> safe = Arrays.asList(p.east(), p.south(), p.north(), p.west(), p.east().east(), p.south().south(), p.north().north(), p.west().west(), p.east().north(), p.east().south(), p.west().north(), p.west().south(), p.up().east(), p.up().west(), p.up().north(), p.up().south());
                if (safe.contains(w.getPos())) {
                    safe.forEach(this::place_obsidian);
                    mc.world.getEntities().forEach((s) -> {
                        if (s instanceof EndCrystalEntity && safe.contains(s.getBlockPos())) {
                            kill(s);
                        }
                    });
                }
            }
        }
    }

    @EventHandler
    private void b(PacketEvent.Receive e) {
        if (online()) {
            if (mode.get() == Mode.Button && e.packet instanceof BlockBreakingProgressS2CPacket) {
                BlockBreakingProgressS2CPacket w = (BlockBreakingProgressS2CPacket) e.packet;
                BlockPos p = mc.player.getBlockPos();
                List<BlockPos> safe = Arrays.asList(p.east(), p.south(), p.north(), p.west());
                if (safe.contains(w.getPos())) {
                    safe.forEach(this::place_button);
                    mc.world.getEntities().forEach((s) -> {
                        if (s instanceof EndCrystalEntity && safe.contains(s.getBlockPos())) {
                            kill(s);
                        }

                    });
                }
            }
        }
    }

    @EventHandler
    private void onTick(EntityAddedEvent e) {
        if (online() && mode.get() != Mode.Obsidian && e.entity instanceof EndCrystalEntity) {
            BlockPos p = mc.player.getBlockPos();
            List<BlockPos> safe = Arrays.asList(p.east(), p.south(), p.north(), p.west(), p.east().east(), p.south().south(), p.north().north(), p.west().west(), p.east().north(), p.east().south(), p.west().north(), p.west().south(), p.up().east(), p.up().west(), p.up().north(), p.up().south());
            BlockPos a = e.entity.getBlockPos();
            if (safe.contains(a)) {
                place_obsidian(a.down());
                kill(e.entity);
                place_obsidian(a);
                place_obsidian(a.up());
            }
        }
    }

    @EventHandler
    private void b(EntityAddedEvent e) {
        if (online() && mode.get() != Mode.Button && e.entity instanceof EndCrystalEntity) {
            BlockPos p = mc.player.getBlockPos();
            List<BlockPos> safe = Arrays.asList(p.east(), p.south(), p.north(), p.west());
            BlockPos a = e.entity.getBlockPos();
            if (safe.contains(a)) {
                place_obsidian(a.down());
                kill(e.entity);
                place_button(a);
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
                int obsidian = InvUtils.findInHotbar(Items.OBSIDIAN).getSlot();
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

    private void place_button(BlockPos a) {
        if (BlockUtils.canPlace(a)) {
            if (mc.player.getBlockPos().getY() - a.getY() <= 2) {
                int button = InvUtils.findInHotbar(Items.STONE_BUTTON).getSlot();
                if (button > -1) {
                    look(a);
                    int pre = mc.player.getInventory().selectedSlot;
                    swap(button);
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

    public enum Mode {
        Obsidian,
        Button
    }
}
