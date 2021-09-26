/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BookEditScreen.class})
public abstract class BookEditScreenMixin
extends Screen {
    @Shadow
    @Final
    private List<String> pages;
    @Shadow
    private int currentPage;
    @Shadow
    private boolean dirty;

    @Shadow
    protected abstract void updateButtons();

    public BookEditScreenMixin(Text Text2) {
        super(Text2);
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onInit(CallbackInfo callbackInfo) {
        this.addButton((ClickableWidget)new ButtonWidget(4, 4, 70, 16, (Text)new LiteralText("Copy"), this::lambda$onInit$0));
        this.addButton((ClickableWidget)new ButtonWidget(4, 24, 70, 16, (Text)new LiteralText("Paste"), this::lambda$onInit$1));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void lambda$onInit$1(ButtonWidget ButtonWidget2) {
        byte[] byArray;
        String string = GLFW.glfwGetClipboardString((long)MinecraftClient.getInstance().getWindow().getHandle());
        if (string == null) {
            return;
        }
        try {
            byArray = Base64.getDecoder().decode(string);
        }
        catch (IllegalArgumentException exception) {
            return;
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
        {
            NbtCompound NbtCompound2 = NbtIo.read((DataInput)dataInputStream);
            NbtList NbtList2 = NbtCompound2.getList("pages", 8).copy();
            this.pages.clear();
            for (int i = 0; i < NbtList2.size(); ++i) {
                this.pages.add(NbtList2.getString(i));
            }
            if (this.pages.isEmpty()) {
                this.pages.add("");
            }
            this.currentPage = NbtCompound2.getInt("currentPage");
            this.dirty = true;
            this.updateButtons();
            return;
        }
    }

    private void lambda$onInit$0(ButtonWidget ButtonWidget2) {
        NbtList NbtList2 = new NbtList();
        Stream<NbtString> stream = this.pages.stream().map(NbtString::of);
        Objects.requireNonNull(NbtList2);
        stream.forEach(arg_0 -> NbtList2.add(arg_0));
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.put("pages", (NbtElement)NbtList2);
        NbtCompound2.putInt("currentPage", this.currentPage);
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)fastByteArrayOutputStream);
        try {
            NbtIo.write((NbtCompound)NbtCompound2, (DataOutput)dataOutputStream);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        try {
            GLFW.glfwSetClipboardString((long)MinecraftClient.getInstance().getWindow().getHandle(), (CharSequence)Base64.getEncoder().encodeToString(fastByteArrayOutputStream.array));
        }
        catch (OutOfMemoryError outOfMemoryError) {
            GLFW.glfwSetClipboardString((long)MinecraftClient.getInstance().getWindow().getHandle(), (CharSequence)outOfMemoryError.toString());
        }
    }
}

