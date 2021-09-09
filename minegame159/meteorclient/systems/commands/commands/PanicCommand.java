/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.command.CommandSource
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.ArrayList;
import minegame159.meteorclient.systems.commands.Command;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import net.minecraft.command.CommandSource;

public class PanicCommand
extends Command {
    public PanicCommand() {
        super("panic", "Disables all modules. DOES NOT remove keybinds.", new String[0]);
        PanicCommand llllllllllllllllllIlllllIllIIIII;
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> llllllllllllllllllIlllllIlIllIll) {
        llllllllllllllllllIlllllIlIllIll.executes(llllllllllllllllllIlllllIlIllIlI -> {
            new ArrayList<Module>(Modules.get().getActive()).forEach(Module::toggle);
            return 1;
        });
    }
}

