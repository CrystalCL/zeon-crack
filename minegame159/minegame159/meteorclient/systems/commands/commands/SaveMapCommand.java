/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import minegame159.meteorclient.systems.commands.Command;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.FilledMapItem;
import net.minecraft.world.World;
import net.minecraft.command.CommandSource;
import net.minecraft.item.map.MapState;
import net.minecraft.text.LiteralText;
import net.minecraft.block.MapColor;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class SaveMapCommand
extends Command {
    private static final SimpleCommandExceptionType OOPS;
    private static final SimpleCommandExceptionType MAP_NOT_FOUND;
    private final PointerBuffer filters = BufferUtils.createPointerBuffer((int)1);

    static {
        MAP_NOT_FOUND = new SimpleCommandExceptionType((Message)new LiteralText("You must be holding a filled map."));
        OOPS = new SimpleCommandExceptionType((Message)new LiteralText("Something went wrong."));
    }

    public SaveMapCommand() {
        super("save-map", "Saves a map to an image.", "sm");
        ByteBuffer byteBuffer = MemoryUtil.memASCII((CharSequence)"*.png");
        this.filters.put(byteBuffer);
        this.filters.rewind();
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder) {
        literalArgumentBuilder.executes(this::lambda$build$0);
    }

    private int lambda$build$0(CommandContext commandContext) throws CommandSyntaxException {
        ItemStack ItemStack2 = this.getMap();
        if (ItemStack2 == null) {
            throw MAP_NOT_FOUND.create();
        }
        MapState MapState2 = FilledMapItem.getMapState((ItemStack)ItemStack2, (World)SaveMapCommand.mc.world);
        if (MapState2 == null) {
            throw MAP_NOT_FOUND.create();
        }
        String string = TinyFileDialogs.tinyfd_saveFileDialog((CharSequence)"Save image", null, (PointerBuffer)this.filters, null);
        if (string == null) {
            throw OOPS.create();
        }
        if (!string.endsWith(".png")) {
            string = String.valueOf(new StringBuilder().append(string).append(".png"));
        }
        NativeImage NativeImage2 = new NativeImage(128, 128, true);
        for (int i = 0; i < 128; ++i) {
            for (int j = 0; j < 128; ++j) {
                int n = MapState2.colors[i + j * 128] & 0xFF;
                if (n / 4 == 0) {
                    NativeImage2.setPixelColor(i, j, 0);
                    continue;
                }
                NativeImage2.setPixelColor(i, j, MapColor.COLORS[n / 4].getRenderColor(n & 3));
            }
        }
        try {
            NativeImage2.writeFile(new File(string));
            NativeImage2.close();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return 1;
    }

    private ItemStack getMap() {
        ItemStack ItemStack2 = SaveMapCommand.mc.player.getMainHandStack();
        if (ItemStack2.getItem() == Items.FILLED_MAP) {
            return ItemStack2;
        }
        ItemStack2 = SaveMapCommand.mc.player.getOffHandStack();
        if (ItemStack2.getItem() == Items.FILLED_MAP) {
            return ItemStack2;
        }
        return null;
    }
}

