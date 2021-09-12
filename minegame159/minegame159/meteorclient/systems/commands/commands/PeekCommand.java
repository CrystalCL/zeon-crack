/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.class_1263;
import net.minecraft.class_1277;
import net.minecraft.class_1661;
import net.minecraft.class_1733;
import net.minecraft.class_1799;
import net.minecraft.class_2172;
import net.minecraft.class_2561;
import net.minecraft.class_2585;
import net.minecraft.class_495;

public class PeekCommand
extends Command {
    private static final SimpleCommandExceptionType NOT_HOLDING_SHULKER_BOX;
    private static final class_1799[] ITEMS;

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        class_1799 class_17992;
        if (Utils.isShulker(PeekCommand.mc.field_1724.method_6047().method_7909())) {
            class_17992 = PeekCommand.mc.field_1724.method_6047();
        } else if (Utils.isShulker(PeekCommand.mc.field_1724.method_6079().method_7909())) {
            class_17992 = PeekCommand.mc.field_1724.method_6079();
        } else {
            throw NOT_HOLDING_SHULKER_BOX.create();
        }
        Utils.getItemsInContainerItem(class_17992, ITEMS);
        MeteorClient.INSTANCE.screenToOpen = new PeekShulkerBoxScreen(new class_1733(0, PeekCommand.mc.field_1724.field_7514, (class_1263)new class_1277(ITEMS)), PeekCommand.mc.field_1724.field_7514, class_17992.method_7964());
        return 1;
    }

    static {
        ITEMS = new class_1799[27];
        NOT_HOLDING_SHULKER_BOX = new SimpleCommandExceptionType((Message)new class_2585("You must be holding a shulker box."));
    }

    @Override
    public void build(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        literalArgumentBuilder.executes(PeekCommand::lambda$build$0);
    }

    public PeekCommand() {
        super("peek", "Lets you see what's inside shulker boxes.", new String[0]);
    }

    public static class PeekShulkerBoxScreen
    extends class_495 {
        public boolean method_25406(double d, double d2, int n) {
            return false;
        }

        public boolean method_25402(double d, double d2, int n) {
            return false;
        }

        public PeekShulkerBoxScreen(class_1733 class_17332, class_1661 class_16612, class_2561 class_25612) {
            super(class_17332, class_16612, class_25612);
        }
    }
}

