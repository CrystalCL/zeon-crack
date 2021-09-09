/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.entity.EntityType
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.command.CommandSource
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket
 *  net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket
 *  net.minecraft.sound.SoundEvents
 */
package minegame159.meteorclient.systems.commands.commands;

import baritone.api.BaritoneAPI;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Arrays;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.command.CommandSource;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;

public class LocateCommand
extends Command {
    private /* synthetic */ Vec3d secondStart;
    private /* synthetic */ Vec3d secondEnd;
    private final /* synthetic */ List<Block> netherFortressBlocks;
    private /* synthetic */ Vec3d firstStart;
    private final /* synthetic */ List<Block> monumentBlocks;
    private /* synthetic */ Vec3d firstEnd;
    private final /* synthetic */ List<Block> strongholdBlocks;

    private void firstPosition(double lIlllIIlIIllll, double lIlllIIlIIlllI, double lIlllIIlIIllIl) {
        LocateCommand lIlllIIlIlIIII;
        Vec3d lIlllIIlIIllII = new Vec3d(lIlllIIlIIllll, lIlllIIlIIlllI, lIlllIIlIIllIl);
        if (lIlllIIlIlIIII.firstStart == null) {
            lIlllIIlIlIIII.firstStart = lIlllIIlIIllII;
        } else {
            lIlllIIlIlIIII.secondStart = lIlllIIlIIllII;
        }
    }

    public LocateCommand() {
        super("locate", "Locates structures", new String[0]);
        LocateCommand lIlllIIlllIIII;
        lIlllIIlllIIII.netherFortressBlocks = Arrays.asList(new Block[]{Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_WART});
        lIlllIIlllIIII.monumentBlocks = Arrays.asList(new Block[]{Blocks.PRISMARINE_BRICKS, Blocks.SEA_LANTERN, Blocks.DARK_PRISMARINE});
        lIlllIIlllIIII.strongholdBlocks = Arrays.asList(new Block[]{Blocks.END_PORTAL_FRAME});
    }

    private void lastPosition(double lIlllIIlIIIIII, double lIlllIIIlllIlI, double lIlllIIIlllIIl) {
        LocateCommand lIlllIIlIIIIIl;
        ChatUtils.prefixInfo("Locate", "%s Eye of Ender's trajectory saved.", lIlllIIlIIIIIl.firstEnd == null ? "First" : "Second");
        Vec3d lIlllIIIllllIl = new Vec3d(lIlllIIlIIIIII, lIlllIIIlllIlI, lIlllIIIlllIIl);
        if (lIlllIIlIIIIIl.firstEnd == null) {
            lIlllIIlIIIIIl.firstEnd = lIlllIIIllllIl;
            ChatUtils.prefixInfo("Locate", "Please throw the second Eye Of Ender from a different location.", new Object[0]);
        } else {
            lIlllIIlIIIIIl.secondEnd = lIlllIIIllllIl;
            lIlllIIlIIIIIl.findStronghold();
        }
    }

    @EventHandler
    private void onReadPacket(PacketEvent.Receive lIlllIIlIlIlll) {
        PlaySoundS2CPacket lIlllIIlIllIll;
        LocateCommand lIlllIIlIllIII;
        EntitySpawnS2CPacket lIlllIIlIlllII;
        if (lIlllIIlIlIlll.packet instanceof EntitySpawnS2CPacket && (lIlllIIlIlllII = (EntitySpawnS2CPacket)lIlllIIlIlIlll.packet).getEntityTypeId() == EntityType.EYE_OF_ENDER) {
            lIlllIIlIllIII.firstPosition(lIlllIIlIlllII.getX(), lIlllIIlIlllII.getY(), lIlllIIlIlllII.getZ());
        }
        if (lIlllIIlIlIlll.packet instanceof PlaySoundS2CPacket && (lIlllIIlIllIll = (PlaySoundS2CPacket)lIlllIIlIlIlll.packet).getSound() == SoundEvents.ENTITY_ENDER_EYE_DEATH) {
            lIlllIIlIllIII.lastPosition(lIlllIIlIllIll.getX(), lIlllIIlIllIll.getY(), lIlllIIlIllIll.getZ());
        }
    }

    private double[] calcIntersection(double[] lIlllIIIIllIll, double[] lIlllIIIIlIIIl) {
        double lIlllIIIIllIIl = lIlllIIIIllIll[3] - lIlllIIIIllIll[1];
        double lIlllIIIIllIII = lIlllIIIIllIll[0] - lIlllIIIIllIll[2];
        double lIlllIIIIlIlll = lIlllIIIIllIIl * lIlllIIIIllIll[0] + lIlllIIIIllIII * lIlllIIIIllIll[1];
        double lIlllIIIIlIllI = lIlllIIIIlIIIl[3] - lIlllIIIIlIIIl[1];
        double lIlllIIIIlIlIl = lIlllIIIIlIIIl[0] - lIlllIIIIlIIIl[2];
        double lIlllIIIIlIlII = lIlllIIIIlIllI * lIlllIIIIlIIIl[0] + lIlllIIIIlIlIl * lIlllIIIIlIIIl[1];
        double lIlllIIIIlIIll = lIlllIIIIllIIl * lIlllIIIIlIlIl - lIlllIIIIlIllI * lIlllIIIIllIII;
        return new double[]{(lIlllIIIIlIlIl * lIlllIIIIlIlll - lIlllIIIIllIII * lIlllIIIIlIlII) / lIlllIIIIlIIll, (lIlllIIIIllIIl * lIlllIIIIlIlII - lIlllIIIIlIllI * lIlllIIIIlIlll) / lIlllIIIIlIIll};
    }

    private void findStronghold() {
        LocateCommand lIlllIIIllIIIl;
        if (lIlllIIIllIIIl.firstStart == null || lIlllIIIllIIIl.firstEnd == null || lIlllIIIllIIIl.secondStart == null || lIlllIIIllIIIl.secondEnd == null) {
            ChatUtils.prefixError("Locate", "Missing position data", new Object[0]);
            lIlllIIIllIIIl.cancel();
            return;
        }
        double[] lIlllIIIllIIII = new double[]{lIlllIIIllIIIl.secondStart.x, lIlllIIIllIIIl.secondStart.z, lIlllIIIllIIIl.secondEnd.x, lIlllIIIllIIIl.secondEnd.z};
        double[] lIlllIIIlIllll = new double[]{lIlllIIIllIIIl.firstStart.x, lIlllIIIllIIIl.firstStart.z, lIlllIIIllIIIl.firstEnd.x, lIlllIIIllIIIl.firstEnd.z};
        double[] lIlllIIIlIlllI = lIlllIIIllIIIl.calcIntersection(lIlllIIIllIIII, lIlllIIIlIllll);
        if (Double.isNaN(lIlllIIIlIlllI[0]) || Double.isNaN(lIlllIIIlIlllI[1]) || Double.isInfinite(lIlllIIIlIlllI[0]) || Double.isInfinite(lIlllIIIlIlllI[1])) {
            ChatUtils.prefixError("Locate", "Lines are parallel", new Object[0]);
            lIlllIIIllIIIl.cancel();
            return;
        }
        BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("stop");
        MeteorClient.EVENT_BUS.unsubscribe(lIlllIIIllIIIl);
        Vec3d lIlllIIIlIllIl = new Vec3d(lIlllIIIlIlllI[0], 0.0, lIlllIIIlIlllI[1]);
        LiteralText lIlllIIIlIllII = new LiteralText("Stronghold roughly located at ");
        lIlllIIIlIllII.append((Text)ChatUtils.formatCoords(lIlllIIIlIllIl));
        lIlllIIIlIllII.append(".");
        ChatUtils.info("Locate", (Text)lIlllIIIlIllII);
    }

    private Vec3d findByBlockList(List<Block> lIlllIIllIIIIl) {
        List lIlllIIllIIIlI = BaritoneAPI.getProvider().getWorldScanner().scanChunkRadius(BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext(), lIlllIIllIIIIl, 64, 10, 32);
        if (lIlllIIllIIIlI.isEmpty()) {
            return null;
        }
        if (lIlllIIllIIIlI.size() < 3) {
            ChatUtils.prefixWarning("Locate", "Only %d block(s) found. This search might be a false positive.", lIlllIIllIIIlI.size());
        }
        return new Vec3d((double)((BlockPos)lIlllIIllIIIlI.get(0)).getX(), (double)((BlockPos)lIlllIIllIIIlI.get(0)).getY(), (double)((BlockPos)lIlllIIllIIIlI.get(0)).getZ());
    }

    private void cancel() {
        LocateCommand lIlllIIllIlIII;
        ChatUtils.prefixWarning("Locate", "Locate canceled", new Object[0]);
        MeteorClient.EVENT_BUS.unsubscribe(lIlllIIllIlIII);
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lIlllIIllIllII) {
        LocateCommand lIlllIIllIllIl;
        lIlllIIllIllII.then(LocateCommand.literal("buried_treasure").executes(lIllIlllIIIIII -> {
            ItemStack lIllIllIllllll = LocateCommand.mc.player.inventory.getMainHandStack();
            if (lIllIllIllllll.getItem() != Items.FILLED_MAP) {
                ChatUtils.prefixError("Locate", "You need to hold a treasure map first", new Object[0]);
                return 1;
            }
            NbtCompound lIllIllIlllllI = lIllIllIllllll.getTag();
            NbtList lIllIllIllllIl = (NbtList)lIllIllIlllllI.get("Decorations");
            if (lIllIllIllllIl == null) {
                ChatUtils.prefixError("Locate", "Couldn't locate the cross. Are you holding a (highlight)treasure map(default)?", new Object[0]);
                return 1;
            }
            NbtCompound lIllIllIllllII = lIllIllIllllIl.getCompound(0);
            if (lIllIllIllllII == null) {
                ChatUtils.prefixError("Locate", "Couldn't locate the cross. Are you holding a (highlight)treasure map(default)?", new Object[0]);
                return 1;
            }
            Vec3d lIllIllIlllIll = new Vec3d(lIllIllIllllII.getDouble("x"), lIllIllIllllII.getDouble("y"), lIllIllIllllII.getDouble("z"));
            LiteralText lIllIllIlllIlI = new LiteralText("Buried Treasure located at ");
            lIllIllIlllIlI.append((Text)ChatUtils.formatCoords(lIllIllIlllIll));
            lIllIllIlllIlI.append(".");
            ChatUtils.info("Locate", (Text)lIllIllIlllIlI);
            return 1;
        }));
        lIlllIIllIllII.then(LocateCommand.literal("mansion").executes(lIllIlllIlIIll -> {
            ItemStack lIllIlllIlIIlI = LocateCommand.mc.player.inventory.getMainHandStack();
            if (lIllIlllIlIIlI.getItem() != Items.FILLED_MAP) {
                ChatUtils.prefixError("Locate", "You need to hold a woodland explorer map first", new Object[0]);
                return 1;
            }
            NbtCompound lIllIlllIlIIIl = lIllIlllIlIIlI.getTag();
            NbtList lIllIlllIlIIII = (NbtList)lIllIlllIlIIIl.get("Decorations");
            if (lIllIlllIlIIII == null) {
                ChatUtils.prefixError("Locate", "Couldn't locate the mansion. Are you holding a (highlight)woodland explorer map(default)?", new Object[0]);
                return 1;
            }
            NbtCompound lIllIlllIIllll = lIllIlllIlIIII.getCompound(0);
            if (lIllIlllIIllll == null) {
                ChatUtils.prefixError("Locate", "Couldn't locate the mansion. Are you holding a (highlight)woodland explorer map(default)?", new Object[0]);
                return 1;
            }
            Vec3d lIllIlllIIlllI = new Vec3d(lIllIlllIIllll.getDouble("x"), lIllIlllIIllll.getDouble("y"), lIllIlllIIllll.getDouble("z"));
            LiteralText lIllIlllIIllIl = new LiteralText("Mansion located at ");
            lIllIlllIIllIl.append((Text)ChatUtils.formatCoords(lIllIlllIIlllI));
            lIllIlllIIllIl.append(".");
            ChatUtils.info("Locate", (Text)lIllIlllIIllIl);
            return 1;
        }));
        lIlllIIllIllII.then(LocateCommand.literal("stronghold").executes(lIllIlllIlllIl -> {
            LocateCommand lIllIlllIlllII;
            if (InvUtils.findItemInHotbar(Items.ENDER_EYE) >= 0) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("follow entity minecraft:eye_of_ender");
                lIllIlllIlllII.firstStart = null;
                lIllIlllIlllII.firstEnd = null;
                lIllIlllIlllII.secondStart = null;
                lIllIlllIlllII.secondEnd = null;
                MeteorClient.EVENT_BUS.subscribe(lIllIlllIlllII);
                ChatUtils.prefixInfo("Locate", "Please throw the first Eye of Ender", new Object[0]);
                return 1;
            }
            Vec3d lIllIllllIIIII = lIllIlllIlllII.findByBlockList(lIllIlllIlllII.strongholdBlocks);
            if (lIllIllllIIIII == null) {
                ChatUtils.prefixError("Locate", "No stronghold found nearby. You can use (highlight)Ender Eyes(default) for more success.", new Object[0]);
                return 1;
            }
            LiteralText lIllIlllIlllll = new LiteralText("Stronghold located at ");
            lIllIlllIlllll.append((Text)ChatUtils.formatCoords(lIllIllllIIIII));
            lIllIlllIlllll.append(".");
            ChatUtils.info("Locate", (Text)lIllIlllIlllll);
            return 1;
        }));
        lIlllIIllIllII.then(LocateCommand.literal("nether_fortress").executes(lIllIllllIlIIl -> {
            LocateCommand lIllIllllIlIlI;
            Vec3d lIllIllllIlIII = lIllIllllIlIlI.findByBlockList(lIllIllllIlIlI.netherFortressBlocks);
            if (lIllIllllIlIII == null) {
                ChatUtils.prefixError("Locate", "No nether fortress found.", new Object[0]);
                return 1;
            }
            LiteralText lIllIllllIIlll = new LiteralText("Nether fortress located at ");
            lIllIllllIIlll.append((Text)ChatUtils.formatCoords(lIllIllllIlIII));
            lIllIllllIIlll.append(".");
            ChatUtils.info("Locate", (Text)lIllIllllIIlll);
            return 1;
        }));
        lIlllIIllIllII.then(LocateCommand.literal("monument").executes(lIllIllllllIII -> {
            LocateCommand lIllIlllllIlII;
            NbtCompound lIllIlllllllII;
            NbtCompound lIllIllllllIll;
            NbtList lIllIllllllIlI;
            ItemStack lIllIlllllIlll = LocateCommand.mc.player.inventory.getMainHandStack();
            if (lIllIlllllIlll.getItem() == Items.FILLED_MAP && (lIllIllllllIlI = (NbtList)(lIllIllllllIll = lIllIlllllIlll.getTag()).get("Decorations")) != null && (lIllIlllllllII = lIllIllllllIlI.getCompound(0)) != null) {
                Vec3d lIllIllllllllI = new Vec3d(lIllIlllllllII.getDouble("x"), lIllIlllllllII.getDouble("y"), lIllIlllllllII.getDouble("z"));
                LiteralText lIllIlllllllIl = new LiteralText("Monument located at ");
                lIllIlllllllIl.append((Text)ChatUtils.formatCoords(lIllIllllllllI));
                lIllIlllllllIl.append(".");
                ChatUtils.info("Locate", (Text)lIllIlllllllIl);
                return 1;
            }
            Vec3d lIllIlllllIllI = lIllIlllllIlII.findByBlockList(lIllIlllllIlII.monumentBlocks);
            if (lIllIlllllIllI == null) {
                ChatUtils.prefixError("Locate", "No monument found. You can try using a (highlight)Ocean explorer map(default) for more success.", new Object[0]);
                return 1;
            }
            LiteralText lIllIlllllIlIl = new LiteralText("Monument located at ");
            lIllIlllllIlIl.append((Text)ChatUtils.formatCoords(lIllIlllllIllI));
            lIllIlllllIlIl.append(".");
            ChatUtils.info("Locate", (Text)lIllIlllllIlIl);
            return 1;
        }));
        lIlllIIllIllII.then(LocateCommand.literal("cancel").executes(lIlllIIIIIIlll -> {
            LocateCommand lIlllIIIIIIllI;
            lIlllIIIIIIllI.cancel();
            return 1;
        }));
    }
}

