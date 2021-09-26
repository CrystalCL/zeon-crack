/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import baritone.api.BaritoneAPI;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
    private final List<Block> netherFortressBlocks = Arrays.asList(Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_WART);
    private final List<Block> strongholdBlocks;
    private Vec3d firstEnd;
    private Vec3d secondStart;
    private Vec3d secondEnd;
    private final List<Block> monumentBlocks = Arrays.asList(Blocks.PRISMARINE_BRICKS, Blocks.SEA_LANTERN, Blocks.DARK_PRISMARINE);
    private Vec3d firstStart;

    private void firstPosition(double d, double d2, double d3) {
        Vec3d Vec3d2 = new Vec3d(d, d2, d3);
        if (this.firstStart == null) {
            this.firstStart = Vec3d2;
        } else {
            this.secondStart = Vec3d2;
        }
    }

    private void findStronghold() {
        if (this.firstStart == null || this.firstEnd == null || this.secondStart == null || this.secondEnd == null) {
            ChatUtils.prefixError("Locate", "Missing position data", new Object[0]);
            this.cancel();
            return;
        }
        double[] dArray = new double[]{this.secondStart.x, this.secondStart.z, this.secondEnd.x, this.secondEnd.z};
        double[] dArray2 = new double[]{this.firstStart.x, this.firstStart.z, this.firstEnd.x, this.firstEnd.z};
        double[] dArray3 = this.calcIntersection(dArray, dArray2);
        if (Double.isNaN(dArray3[0]) || Double.isNaN(dArray3[1]) || Double.isInfinite(dArray3[0]) || Double.isInfinite(dArray3[1])) {
            ChatUtils.prefixError("Locate", "Lines are parallel", new Object[0]);
            this.cancel();
            return;
        }
        BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("stop");
        MeteorClient.EVENT_BUS.unsubscribe(this);
        Vec3d Vec3d2 = new Vec3d(dArray3[0], 0.0, dArray3[1]);
        LiteralText LiteralText2 = new LiteralText("Stronghold roughly located at ");
        LiteralText2.append((Text)ChatUtils.formatCoords(Vec3d2));
        LiteralText2.append(".");
        ChatUtils.info("Locate", (Text)LiteralText2);
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(LocateCommand.literal("buried_treasure").executes(LocateCommand::lambda$build$0));
        literalArgumentBuilder.then(LocateCommand.literal("mansion").executes(LocateCommand::lambda$build$1));
        literalArgumentBuilder.then(LocateCommand.literal("stronghold").executes(this::lambda$build$2));
        literalArgumentBuilder.then(LocateCommand.literal("nether_fortress").executes(this::lambda$build$3));
        literalArgumentBuilder.then(LocateCommand.literal("monument").executes(this::lambda$build$4));
        literalArgumentBuilder.then(LocateCommand.literal("cancel").executes(this::lambda$build$5));
    }

    private void cancel() {
        ChatUtils.prefixWarning("Locate", "Locate canceled", new Object[0]);
        MeteorClient.EVENT_BUS.unsubscribe(this);
    }

    private double[] calcIntersection(double[] dArray, double[] dArray2) {
        double d = dArray[3] - dArray[1];
        double d2 = dArray[0] - dArray[2];
        double d3 = d * dArray[0] + d2 * dArray[1];
        double d4 = dArray2[3] - dArray2[1];
        double d5 = dArray2[0] - dArray2[2];
        double d6 = d4 * dArray2[0] + d5 * dArray2[1];
        double d7 = d * d5 - d4 * d2;
        return new double[]{(d5 * d3 - d2 * d6) / d7, (d * d6 - d4 * d3) / d7};
    }

    private Vec3d findByBlockList(List<Block> list) {
        List list2 = BaritoneAPI.getProvider().getWorldScanner().scanChunkRadius(BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext(), list, 64, 10, 32);
        if (list2.isEmpty()) {
            return null;
        }
        if (list2.size() < 3) {
            ChatUtils.prefixWarning("Locate", "Only %d block(s) found. This search might be a false positive.", list2.size());
        }
        return new Vec3d((double)((BlockPos)list2.get(0)).getX(), (double)((BlockPos)list2.get(0)).getY(), (double)((BlockPos)list2.get(0)).getZ());
    }

    private int lambda$build$3(CommandContext commandContext) throws CommandSyntaxException {
        Vec3d Vec3d2 = this.findByBlockList(this.netherFortressBlocks);
        if (Vec3d2 == null) {
            ChatUtils.prefixError("Locate", "No nether fortress found.", new Object[0]);
            return 1;
        }
        LiteralText LiteralText2 = new LiteralText("Nether fortress located at ");
        LiteralText2.append((Text)ChatUtils.formatCoords(Vec3d2));
        LiteralText2.append(".");
        ChatUtils.info("Locate", (Text)LiteralText2);
        return 1;
    }

    private static int lambda$build$1(CommandContext commandContext) throws CommandSyntaxException {
        ItemStack ItemStack2 = LocateCommand.mc.player.inventory.getMainHandStack();
        if (ItemStack2.getItem() != Items.FILLED_MAP) {
            ChatUtils.prefixError("Locate", "You need to hold a woodland explorer map first", new Object[0]);
            return 1;
        }
        NbtCompound NbtCompound2 = ItemStack2.getTag();
        NbtList NbtList2 = (NbtList)NbtCompound2.get("Decorations");
        if (NbtList2 == null) {
            ChatUtils.prefixError("Locate", "Couldn't locate the mansion. Are you holding a (highlight)woodland explorer map(default)?", new Object[0]);
            return 1;
        }
        NbtCompound NbtCompound3 = NbtList2.getCompound(0);
        if (NbtCompound3 == null) {
            ChatUtils.prefixError("Locate", "Couldn't locate the mansion. Are you holding a (highlight)woodland explorer map(default)?", new Object[0]);
            return 1;
        }
        Vec3d Vec3d2 = new Vec3d(NbtCompound3.getDouble("x"), NbtCompound3.getDouble("y"), NbtCompound3.getDouble("z"));
        LiteralText LiteralText2 = new LiteralText("Mansion located at ");
        LiteralText2.append((Text)ChatUtils.formatCoords(Vec3d2));
        LiteralText2.append(".");
        ChatUtils.info("Locate", (Text)LiteralText2);
        return 1;
    }

    @EventHandler
    private void onReadPacket(PacketEvent.Receive receive) {
        EntitySpawnS2CPacket EntitySpawnS2CPacket2;
        if (receive.packet instanceof EntitySpawnS2CPacket && (EntitySpawnS2CPacket2 = (EntitySpawnS2CPacket)receive.packet).getEntityTypeId() == EntityType.EYE_OF_ENDER) {
            this.firstPosition(EntitySpawnS2CPacket2.getX(), EntitySpawnS2CPacket2.getY(), EntitySpawnS2CPacket2.getZ());
        }
        if (receive.packet instanceof PlaySoundS2CPacket && (EntitySpawnS2CPacket2 = (PlaySoundS2CPacket)receive.packet).getSound() == SoundEvents.ENTITY_ENDER_EYE_DEATH) {
            this.lastPosition(EntitySpawnS2CPacket2.getX(), EntitySpawnS2CPacket2.getY(), EntitySpawnS2CPacket2.getZ());
        }
    }

    public LocateCommand() {
        super("locate", "Locates structures", new String[0]);
        this.strongholdBlocks = Arrays.asList(Blocks.END_PORTAL_FRAME);
    }

    private int lambda$build$5(CommandContext commandContext) throws CommandSyntaxException {
        this.cancel();
        return 1;
    }

    private int lambda$build$4(CommandContext commandContext) throws CommandSyntaxException {
        NbtCompound NbtCompound2;
        NbtCompound NbtCompound3;
        NbtList NbtList2;
        ItemStack ItemStack2 = LocateCommand.mc.player.inventory.getMainHandStack();
        if (ItemStack2.getItem() == Items.FILLED_MAP && (NbtList2 = (NbtList)(NbtCompound3 = ItemStack2.getTag()).get("Decorations")) != null && (NbtCompound2 = NbtList2.getCompound(0)) != null) {
            Vec3d Vec3d2 = new Vec3d(NbtCompound2.getDouble("x"), NbtCompound2.getDouble("y"), NbtCompound2.getDouble("z"));
            LiteralText LiteralText2 = new LiteralText("Monument located at ");
            LiteralText2.append((Text)ChatUtils.formatCoords(Vec3d2));
            LiteralText2.append(".");
            ChatUtils.info("Locate", (Text)LiteralText2);
            return 1;
        }
        NbtCompound3 = this.findByBlockList(this.monumentBlocks);
        if (NbtCompound3 == null) {
            ChatUtils.prefixError("Locate", "No monument found. You can try using a (highlight)Ocean explorer map(default) for more success.", new Object[0]);
            return 1;
        }
        NbtList2 = new LiteralText("Monument located at ");
        NbtList2.append((Text)ChatUtils.formatCoords((Vec3d)NbtCompound3));
        NbtList2.append(".");
        ChatUtils.info("Locate", (Text)NbtList2);
        return 1;
    }

    private void lastPosition(double d, double d2, double d3) {
        ChatUtils.prefixInfo("Locate", "%s Eye of Ender's trajectory saved.", this.firstEnd == null ? "First" : "Second");
        Vec3d Vec3d2 = new Vec3d(d, d2, d3);
        if (this.firstEnd == null) {
            this.firstEnd = Vec3d2;
            ChatUtils.prefixInfo("Locate", "Please throw the second Eye Of Ender from a different location.", new Object[0]);
        } else {
            this.secondEnd = Vec3d2;
            this.findStronghold();
        }
    }

    private int lambda$build$2(CommandContext commandContext) throws CommandSyntaxException {
        if (InvUtils.findItemInHotbar(Items.ENDER_EYE) >= 0) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("follow entity minecraft:eye_of_ender");
            this.firstStart = null;
            this.firstEnd = null;
            this.secondStart = null;
            this.secondEnd = null;
            MeteorClient.EVENT_BUS.subscribe(this);
            ChatUtils.prefixInfo("Locate", "Please throw the first Eye of Ender", new Object[0]);
            return 1;
        }
        Vec3d Vec3d2 = this.findByBlockList(this.strongholdBlocks);
        if (Vec3d2 == null) {
            ChatUtils.prefixError("Locate", "No stronghold found nearby. You can use (highlight)Ender Eyes(default) for more success.", new Object[0]);
            return 1;
        }
        LiteralText LiteralText2 = new LiteralText("Stronghold located at ");
        LiteralText2.append((Text)ChatUtils.formatCoords(Vec3d2));
        LiteralText2.append(".");
        ChatUtils.info("Locate", (Text)LiteralText2);
        return 1;
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        ItemStack ItemStack2 = LocateCommand.mc.player.inventory.getMainHandStack();
        if (ItemStack2.getItem() != Items.FILLED_MAP) {
            ChatUtils.prefixError("Locate", "You need to hold a treasure map first", new Object[0]);
            return 1;
        }
        NbtCompound NbtCompound2 = ItemStack2.getTag();
        NbtList NbtList2 = (NbtList)NbtCompound2.get("Decorations");
        if (NbtList2 == null) {
            ChatUtils.prefixError("Locate", "Couldn't locate the cross. Are you holding a (highlight)treasure map(default)?", new Object[0]);
            return 1;
        }
        NbtCompound NbtCompound3 = NbtList2.getCompound(0);
        if (NbtCompound3 == null) {
            ChatUtils.prefixError("Locate", "Couldn't locate the cross. Are you holding a (highlight)treasure map(default)?", new Object[0]);
            return 1;
        }
        Vec3d Vec3d2 = new Vec3d(NbtCompound3.getDouble("x"), NbtCompound3.getDouble("y"), NbtCompound3.getDouble("z"));
        LiteralText LiteralText2 = new LiteralText("Buried Treasure located at ");
        LiteralText2.append((Text)ChatUtils.formatCoords(Vec3d2));
        LiteralText2.append(".");
        ChatUtils.info("Locate", (Text)LiteralText2);
        return 1;
    }
}

