/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.commands.arguments.CompoundNbtTagArgumentType;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.util.Formatting;
import net.minecraft.item.ItemStack;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.NbtPathArgumentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;

public class NbtCommand
extends Command {
    private int lambda$build$3(CommandContext commandContext) throws CommandSyntaxException {
        ItemStack ItemStack2 = NbtCommand.mc.player.inventory.getMainHandStack();
        if (ItemStack2 == null) {
            ChatUtils.prefixError("NBT", "You must hold an item in your main hand.", new Object[0]);
        } else {
            NbtCompound NbtCompound2 = ItemStack2.getTag();
            String string = NbtCompound2 == null ? "none" : NbtCompound2.asString();
            LiteralText LiteralText2 = new LiteralText("NBT");
            LiteralText2.setStyle(LiteralText2.getStyle().withFormatting(Formatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.class_2559.RUN_COMMAND, this.toString("copy"))).withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)new LiteralText("Copy the NBT data to your clipboard."))));
            LiteralText LiteralText3 = new LiteralText("");
            LiteralText3.append((Text)LiteralText2);
            LiteralText3.append((Text)new LiteralText(String.valueOf(new StringBuilder().append(": ").append(string))));
            ChatUtils.info("NBT", (Text)LiteralText3);
        }
        return 1;
    }

    private int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        ItemStack ItemStack2 = NbtCommand.mc.player.inventory.getMainHandStack();
        if (this.validBasic(ItemStack2)) {
            NbtCompound NbtCompound2 = CompoundNbtTagArgumentType.getTag(commandContext, "nbt_data");
            NbtCompound NbtCompound3 = ItemStack2.getTag();
            if (NbtCompound2 != null && NbtCompound3 != null) {
                ItemStack2.getTag().copyFrom(NbtCompound2);
                this.setStack(ItemStack2);
            } else {
                ChatUtils.prefixError("NBT", String.valueOf(new StringBuilder().append("Some of the NBT data could not be found, try using: ").append(Config.get().getPrefix()).append("nbt set {nbt}")), new Object[0]);
            }
        }
        return 1;
    }

    public NbtCommand() {
        super("nbt", "Modifies NBT data for an item, example: .nbt add {display:{Name:'{\"text\":\"$cRed Name\"}'}}", new String[0]);
    }

    private int lambda$build$5(CommandContext commandContext) throws CommandSyntaxException {
        ItemStack ItemStack2 = NbtCommand.mc.player.inventory.getMainHandStack();
        if (this.validBasic(ItemStack2)) {
            int n = IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"count");
            ItemStack2.setCount(n);
            this.setStack(ItemStack2);
            ChatUtils.prefixInfo("NBT", String.valueOf(new StringBuilder().append("Set mainhand stack count to ").append(n).append(".")), new Object[0]);
        }
        return 1;
    }

    private int lambda$build$2(CommandContext commandContext) throws CommandSyntaxException {
        ItemStack ItemStack2 = NbtCommand.mc.player.inventory.getMainHandStack();
        if (this.validBasic(ItemStack2)) {
            NbtPath class_22092 = (NbtPath)commandContext.getArgument("nbt_path", NbtPath.class);
            class_22092.remove((NbtElement)ItemStack2.getTag());
        }
        return 1;
    }

    private static int lambda$build$4(CommandContext commandContext) throws CommandSyntaxException {
        ItemStack ItemStack2 = NbtCommand.mc.player.inventory.getMainHandStack();
        if (ItemStack2 == null) {
            ChatUtils.prefixError("NBT", "You must hold an item in your main hand.", new Object[0]);
        } else {
            NbtCompound NbtCompound2 = ItemStack2.getTag();
            if (NbtCompound2 == null) {
                ChatUtils.prefixError("NBT", "No NBT data on this item.", new Object[0]);
            } else {
                NbtCommand.mc.keyboard.setClipboard(NbtCompound2.toString());
                LiteralText LiteralText2 = new LiteralText("NBT");
                LiteralText2.setStyle(LiteralText2.getStyle().withFormatting(Formatting.UNDERLINE).withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)new LiteralText(NbtCompound2.toString()))));
                LiteralText LiteralText3 = new LiteralText("");
                LiteralText3.append((Text)LiteralText2);
                LiteralText3.append((Text)new LiteralText(" data copied!"));
                ChatUtils.info("NBT", (Text)LiteralText3);
            }
        }
        return 1;
    }

    private void setStack(ItemStack ItemStack2) {
        NbtCommand.mc.player.networkHandler.sendPacket((Packet)new CreativeInventoryActionC2SPacket(36 + NbtCommand.mc.player.inventory.selectedSlot, ItemStack2));
    }

    private boolean validBasic(ItemStack ItemStack2) {
        if (!NbtCommand.mc.player.abilities.creativeMode) {
            ChatUtils.prefixError("NBT", "Creative mode only.", new Object[0]);
            return false;
        }
        if (ItemStack2 == null) {
            ChatUtils.prefixError("NBT", "You must hold an item in your main hand.", new Object[0]);
            return false;
        }
        return true;
    }

    private int lambda$build$1(CommandContext commandContext) throws CommandSyntaxException {
        ItemStack ItemStack2 = NbtCommand.mc.player.inventory.getMainHandStack();
        if (this.validBasic(ItemStack2)) {
            NbtCompound NbtCompound2 = (NbtCompound)commandContext.getArgument("nbt_data", NbtCompound.class);
            ItemStack2.setTag(NbtCompound2);
            this.setStack(ItemStack2);
        }
        return 1;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.then(NbtCommand.literal("add").then(NbtCommand.argument("nbt_data", CompoundNbtTagArgumentType.nbtTag()).executes(this::lambda$build$0)));
        literalArgumentBuilder.then(NbtCommand.literal("set").then(NbtCommand.argument("nbt_data", CompoundNbtTagArgumentType.nbtTag()).executes(this::lambda$build$1)));
        literalArgumentBuilder.then(NbtCommand.literal("remove").then(NbtCommand.argument("nbt_path", NbtPathArgumentType.nbtPath()).executes(this::lambda$build$2)));
        literalArgumentBuilder.then(NbtCommand.literal("get").executes(this::lambda$build$3));
        literalArgumentBuilder.then(NbtCommand.literal("copy").executes(NbtCommand::lambda$build$4));
        literalArgumentBuilder.then(NbtCommand.literal("count").then(NbtCommand.argument("count", IntegerArgumentType.integer((int)-127, (int)127)).executes(this::lambda$build$5)));
    }
}

