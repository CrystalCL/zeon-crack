/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.util.Formatting
 *  net.minecraft.item.ItemStack
 *  net.minecraft.command.CommandSource
 *  net.minecraft.command.argument.NbtPathArgumentType
 *  net.minecraft.command.argument.NbtPathArgumentType.NbtPath
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.text.ClickEvent
 *  net.minecraft.text.ClickEvent$class_2559
 *  net.minecraft.text.Text
 *  net.minecraft.text.HoverEvent
 *  net.minecraft.text.HoverEvent$class_5247
 *  net.minecraft.text.LiteralText
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
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
    @Override
    public void build(LiteralArgumentBuilder<CommandSource> lllllllllllllllllllIIllIIllllllI) {
        NbtCommand lllllllllllllllllllIIllIlIIIIIIl;
        lllllllllllllllllllIIllIIllllllI.then(NbtCommand.literal("add").then(NbtCommand.argument("nbt_data", CompoundNbtTagArgumentType.nbtTag()).executes(lllllllllllllllllllIIllIIIlIIllI -> {
            NbtCommand lllllllllllllllllllIIllIIIlIlIlI;
            ItemStack lllllllllllllllllllIIllIIIlIlIII = NbtCommand.mc.player.inventory.getMainHandStack();
            if (lllllllllllllllllllIIllIIIlIlIlI.validBasic(lllllllllllllllllllIIllIIIlIlIII)) {
                NbtCompound lllllllllllllllllllIIllIIIlIllII = CompoundNbtTagArgumentType.getTag(lllllllllllllllllllIIllIIIlIIllI, "nbt_data");
                NbtCompound lllllllllllllllllllIIllIIIlIlIll = lllllllllllllllllllIIllIIIlIlIII.getTag();
                if (lllllllllllllllllllIIllIIIlIllII != null && lllllllllllllllllllIIllIIIlIlIll != null) {
                    lllllllllllllllllllIIllIIIlIlIII.getTag().copyFrom(lllllllllllllllllllIIllIIIlIllII);
                    lllllllllllllllllllIIllIIIlIlIlI.setStack(lllllllllllllllllllIIllIIIlIlIII);
                } else {
                    ChatUtils.prefixError("NBT", String.valueOf(new StringBuilder().append("Some of the NBT data could not be found, try using: ").append(Config.get().getPrefix()).append("nbt set {nbt}")), new Object[0]);
                }
            }
            return 1;
        })));
        lllllllllllllllllllIIllIIllllllI.then(NbtCommand.literal("set").then(NbtCommand.argument("nbt_data", CompoundNbtTagArgumentType.nbtTag()).executes(lllllllllllllllllllIIllIIIllIlII -> {
            NbtCommand lllllllllllllllllllIIllIIIlllIII;
            ItemStack lllllllllllllllllllIIllIIIllIllI = NbtCommand.mc.player.inventory.getMainHandStack();
            if (lllllllllllllllllllIIllIIIlllIII.validBasic(lllllllllllllllllllIIllIIIllIllI)) {
                NbtCompound lllllllllllllllllllIIllIIIlllIIl = (NbtCompound)lllllllllllllllllllIIllIIIllIlII.getArgument("nbt_data", NbtCompound.class);
                lllllllllllllllllllIIllIIIllIllI.setTag(lllllllllllllllllllIIllIIIlllIIl);
                lllllllllllllllllllIIllIIIlllIII.setStack(lllllllllllllllllllIIllIIIllIllI);
            }
            return 1;
        })));
        lllllllllllllllllllIIllIIllllllI.then(NbtCommand.literal("remove").then(NbtCommand.argument("nbt_path", NbtPathArgumentType.nbtPath()).executes(lllllllllllllllllllIIllIIlIIIIll -> {
            NbtCommand lllllllllllllllllllIIllIIlIIIlII;
            ItemStack lllllllllllllllllllIIllIIlIIIIlI = NbtCommand.mc.player.inventory.getMainHandStack();
            if (lllllllllllllllllllIIllIIlIIIlII.validBasic(lllllllllllllllllllIIllIIlIIIIlI)) {
                NbtPath lllllllllllllllllllIIllIIlIIIlIl = (NbtPath)lllllllllllllllllllIIllIIlIIIIll.getArgument("nbt_path", NbtPath.class);
                lllllllllllllllllllIIllIIlIIIlIl.remove((NbtElement)lllllllllllllllllllIIllIIlIIIIlI.getTag());
            }
            return 1;
        })));
        lllllllllllllllllllIIllIIllllllI.then(NbtCommand.literal("get").executes(lllllllllllllllllllIIllIIlIlIIIl -> {
            ItemStack lllllllllllllllllllIIllIIlIlIIII = NbtCommand.mc.player.inventory.getMainHandStack();
            if (lllllllllllllllllllIIllIIlIlIIII == null) {
                ChatUtils.prefixError("NBT", "You must hold an item in your main hand.", new Object[0]);
            } else {
                NbtCommand lllllllllllllllllllIIllIIlIlIIlI;
                NbtCompound lllllllllllllllllllIIllIIlIlIllI = lllllllllllllllllllIIllIIlIlIIII.getTag();
                String lllllllllllllllllllIIllIIlIlIlIl = lllllllllllllllllllIIllIIlIlIllI == null ? "none" : lllllllllllllllllllIIllIIlIlIllI.asString();
                LiteralText lllllllllllllllllllIIllIIlIlIlII = new LiteralText("NBT");
                lllllllllllllllllllIIllIIlIlIlII.setStyle(lllllllllllllllllllIIllIIlIlIlII.getStyle().withFormatting(Formatting.UNDERLINE).withClickEvent(new ClickEvent(ClickEvent.class_2559.RUN_COMMAND, lllllllllllllllllllIIllIIlIlIIlI.toString("copy"))).withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)new LiteralText("Copy the NBT data to your clipboard."))));
                LiteralText lllllllllllllllllllIIllIIlIlIIll = new LiteralText("");
                lllllllllllllllllllIIllIIlIlIIll.append((Text)lllllllllllllllllllIIllIIlIlIlII);
                lllllllllllllllllllIIllIIlIlIIll.append((Text)new LiteralText(String.valueOf(new StringBuilder().append(": ").append(lllllllllllllllllllIIllIIlIlIlIl))));
                ChatUtils.info("NBT", (Text)lllllllllllllllllllIIllIIlIlIIll);
            }
            return 1;
        }));
        lllllllllllllllllllIIllIIllllllI.then(NbtCommand.literal("copy").executes(lllllllllllllllllllIIllIIllIIIlI -> {
            ItemStack lllllllllllllllllllIIllIIllIIIIl = NbtCommand.mc.player.inventory.getMainHandStack();
            if (lllllllllllllllllllIIllIIllIIIIl == null) {
                ChatUtils.prefixError("NBT", "You must hold an item in your main hand.", new Object[0]);
            } else {
                NbtCompound lllllllllllllllllllIIllIIllIIIll = lllllllllllllllllllIIllIIllIIIIl.getTag();
                if (lllllllllllllllllllIIllIIllIIIll == null) {
                    ChatUtils.prefixError("NBT", "No NBT data on this item.", new Object[0]);
                } else {
                    NbtCommand.mc.keyboard.setClipboard(lllllllllllllllllllIIllIIllIIIll.toString());
                    LiteralText lllllllllllllllllllIIllIIllIIlIl = new LiteralText("NBT");
                    lllllllllllllllllllIIllIIllIIlIl.setStyle(lllllllllllllllllllIIllIIllIIlIl.getStyle().withFormatting(Formatting.UNDERLINE).withHoverEvent(new HoverEvent(HoverEvent.class_5247.SHOW_TEXT, (Object)new LiteralText(lllllllllllllllllllIIllIIllIIIll.toString()))));
                    LiteralText lllllllllllllllllllIIllIIllIIlII = new LiteralText("");
                    lllllllllllllllllllIIllIIllIIlII.append((Text)lllllllllllllllllllIIllIIllIIlIl);
                    lllllllllllllllllllIIllIIllIIlII.append((Text)new LiteralText(" data copied!"));
                    ChatUtils.info("NBT", (Text)lllllllllllllllllllIIllIIllIIlII);
                }
            }
            return 1;
        }));
        lllllllllllllllllllIIllIIllllllI.then(NbtCommand.literal("count").then(NbtCommand.argument("count", IntegerArgumentType.integer((int)-127, (int)127)).executes(lllllllllllllllllllIIllIIllIllll -> {
            NbtCommand lllllllllllllllllllIIllIIllIllIl;
            ItemStack lllllllllllllllllllIIllIIllIlllI = NbtCommand.mc.player.inventory.getMainHandStack();
            if (lllllllllllllllllllIIllIIllIllIl.validBasic(lllllllllllllllllllIIllIIllIlllI)) {
                int lllllllllllllllllllIIllIIlllIIIl = IntegerArgumentType.getInteger((CommandContext)lllllllllllllllllllIIllIIllIllll, (String)"count");
                lllllllllllllllllllIIllIIllIlllI.setCount(lllllllllllllllllllIIllIIlllIIIl);
                lllllllllllllllllllIIllIIllIllIl.setStack(lllllllllllllllllllIIllIIllIlllI);
                ChatUtils.prefixInfo("NBT", String.valueOf(new StringBuilder().append("Set mainhand stack count to ").append(lllllllllllllllllllIIllIIlllIIIl).append(".")), new Object[0]);
            }
            return 1;
        })));
    }

    private void setStack(ItemStack lllllllllllllllllllIIllIIllllIll) {
        NbtCommand.mc.player.networkHandler.sendPacket((Packet)new CreativeInventoryActionC2SPacket(36 + NbtCommand.mc.player.inventory.selectedSlot, lllllllllllllllllllIIllIIllllIll));
    }

    public NbtCommand() {
        super("nbt", "Modifies NBT data for an item, example: .nbt add {display:{Name:'{\"text\":\"$cRed Name\"}'}}", new String[0]);
        NbtCommand lllllllllllllllllllIIllIlIIIIlIl;
    }

    private boolean validBasic(ItemStack lllllllllllllllllllIIllIIlllIlll) {
        if (!NbtCommand.mc.player.abilities.creativeMode) {
            ChatUtils.prefixError("NBT", "Creative mode only.", new Object[0]);
            return false;
        }
        if (lllllllllllllllllllIIllIIlllIlll == null) {
            ChatUtils.prefixError("NBT", "You must hold an item in your main hand.", new Object[0]);
            return false;
        }
        return true;
    }
}

