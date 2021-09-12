/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.class_124;
import net.minecraft.class_2172;
import net.minecraft.class_2561;
import net.minecraft.class_2568;
import net.minecraft.class_2585;

public class ModulesCommand
extends Command {
    @Override
    public void build(LiteralArgumentBuilder<class_2172> literalArgumentBuilder) {
        literalArgumentBuilder.executes(ModulesCommand::lambda$build$0);
    }

    private static int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        ChatUtils.info("--- All (highlight)%d(default) Modules ---", Modules.get().getCount());
        class_2585 class_25852 = new class_2585("");
        for (Module module : Modules.get().getList()) {
            class_2585 class_25853 = new class_2585("");
            class_25853.method_10852((class_2561)new class_2585(module.title).method_27695(new class_124[]{class_124.field_1078, class_124.field_1067})).method_27693("\n");
            class_25853.method_10852((class_2561)new class_2585(module.name).method_27692(class_124.field_1080)).method_27693("\n\n");
            class_25853.method_10852((class_2561)new class_2585(module.description).method_27692(class_124.field_1068));
            class_2585 class_25854 = new class_2585(module.title);
            if (module != Modules.get().getList().get(Modules.get().getAll().size() - 1)) {
                class_25852.method_10852((class_2561)new class_2585(", ").method_27692(class_124.field_1080));
            }
            class_25854.method_10862(class_25854.method_10866().method_10949(new class_2568(class_2568.class_5247.field_24342, (Object)class_25853)));
            class_25852.method_10852((class_2561)class_25854);
        }
        ChatUtils.info((class_2561)class_25852);
        return 1;
    }

    public ModulesCommand() {
        super("modules", "Displays a list of all modules.", "features");
    }
}

