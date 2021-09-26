/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BookScreen.class})
public class BookScreenMixin
extends Screen {
    @Shadow
    private BookScreen.class_3931 contents;
    @Shadow
    private int pageIndex;

    public BookScreenMixin(Text Text2) {
        super(Text2);
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onInit(CallbackInfo callbackInfo) {
        this.addButton((ClickableWidget)new ButtonWidget(4, 4, 70, 16, (Text)new LiteralText("Copy"), this::lambda$onInit$0));
    }

    private void lambda$onInit$0(ButtonWidget ButtonWidget2) {
        NbtList NbtList2 = new NbtList();
        for (int i = 0; i < this.contents.getPageCount(); ++i) {
            NbtList2.add((Object)NbtString.of((String)this.contents.getPage(i).getString()));
        }
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.put("pages", (NbtElement)NbtList2);
        NbtCompound2.putInt("currentPage", this.pageIndex);
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)fastByteArrayOutputStream);
        try {
            NbtIo.write((NbtCompound)NbtCompound2, (DataOutput)dataOutputStream);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        GLFW.glfwSetClipboardString((long)MinecraftClient.getInstance().getWindow().getHandle(), (CharSequence)Base64.getEncoder().encodeToString(fastByteArrayOutputStream.array));
    }
}

