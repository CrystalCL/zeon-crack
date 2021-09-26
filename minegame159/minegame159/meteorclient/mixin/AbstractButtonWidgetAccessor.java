/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import net.minecraft.text.Text;
import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ClickableWidget.class})
public interface AbstractButtonWidgetAccessor {
    @Accessor(value="message")
    public void setText(Text var1);
}

