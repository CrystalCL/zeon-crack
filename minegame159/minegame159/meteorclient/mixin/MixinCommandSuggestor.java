/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.concurrent.CompletableFuture;
import minegame159.meteorclient.systems.commands.Commands;
import minegame159.meteorclient.systems.config.Config;
import net.minecraft.command.CommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.screen.CommandSuggestor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={CommandSuggestor.class})
public abstract class MixinCommandSuggestor {
    @Shadow
    private ParseResults<CommandSource> parse;
    @Shadow
    @Final
    private TextFieldWidget textField;
    @Shadow
    @Final
    private MinecraftClient client;
    @Shadow
    private boolean completingSuggestions;
    @Shadow
    private CompletableFuture<Suggestions> pendingSuggestions;
    @Shadow
    private SuggestionWindow window;
    static final boolean $assertionsDisabled = !MixinCommandSuggestor.class.desiredAssertionStatus();

    @Shadow
    protected abstract void show();

    @Inject(method={"refresh"}, at={@At(value="INVOKE", target="Lcom/mojang/brigadier/StringReader;canRead()Z", remap=false)}, cancellable=true, locals=LocalCapture.CAPTURE_FAILHARD)
    public void onRefresh(CallbackInfo callbackInfo, String string, StringReader stringReader) {
        String string2 = Config.get().getPrefix();
        int n = string2.length();
        if (stringReader.canRead(n) && stringReader.getString().startsWith(string2, stringReader.getCursor())) {
            int n2;
            stringReader.setCursor(stringReader.getCursor() + n);
            if (!$assertionsDisabled && this.client.player == null) {
                throw new AssertionError();
            }
            CommandDispatcher<CommandSource> commandDispatcher = Commands.get().getDispatcher();
            if (this.parse == null) {
                this.parse = commandDispatcher.parse(stringReader, (Object)Commands.get().getCommandSource());
            }
            if (!((n2 = this.textField.getCursor()) < 1 || this.window != null && this.completingSuggestions)) {
                this.pendingSuggestions = commandDispatcher.getCompletionSuggestions(this.parse, n2);
                this.pendingSuggestions.thenRun(this::lambda$onRefresh$0);
            }
            callbackInfo.cancel();
        }
    }

    private void lambda$onRefresh$0() {
        if (this.pendingSuggestions.isDone()) {
            this.show();
        }
    }
}

