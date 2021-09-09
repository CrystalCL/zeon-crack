/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.io.FastByteArrayOutputStream
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.widget.ClickableWidget
 *  net.minecraft.client.gui.screen.ingame.BookScreen
 *  net.minecraft.client.gui.screen.ingame.BookScreen$class_3931
 *  net.minecraft.client.gui.widget.ButtonWidget
 *  net.minecraft.client.gui.screen.Screen
 *  org.lwjgl.glfw.GLFW
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
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

    public BookScreenMixin(Text title) {
        super(title);
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void onInit(CallbackInfo info) {
        this.addButton((ClickableWidget)new ButtonWidget(4, 4, 70, 16, (Text)new LiteralText("Copy"), button -> {
            NbtList listTag = new NbtList();
            for (int i = 0; i < this.contents.getPageCount(); ++i) {
                listTag.add((Object)NbtString.of((String)this.contents.getPage(i).getString()));
            }
            NbtCompound tag = new NbtCompound();
            tag.put("pages", (NbtElement)listTag);
            tag.putInt("currentPage", this.pageIndex);
            FastByteArrayOutputStream bytes = new FastByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream((OutputStream)bytes);
            try {
                NbtIo.write((NbtCompound)tag, (DataOutput)out);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            GLFW.glfwSetClipboardString((long)MinecraftClient.getInstance().getWindow().getHandle(), (CharSequence)Base64.getEncoder().encodeToString(bytes.array));
        }));
    }
}

