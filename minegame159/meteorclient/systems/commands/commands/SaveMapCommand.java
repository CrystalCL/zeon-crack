/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  net.minecraft.client.texture.NativeImage
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.FilledMapItem
 *  net.minecraft.world.World
 *  net.minecraft.command.CommandSource
 *  net.minecraft.item.map.MapState
 *  net.minecraft.text.LiteralText
 *  net.minecraft.block.MapColor
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.system.MemoryUtil
 *  org.lwjgl.util.tinyfd.TinyFileDialogs
 */
package minegame159.meteorclient.systems.commands.commands;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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
    private static final /* synthetic */ SimpleCommandExceptionType MAP_NOT_FOUND;
    private static final /* synthetic */ SimpleCommandExceptionType OOPS;
    private final /* synthetic */ PointerBuffer filters;

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> llllllllllllllllIllIlIIIIIIllIll) {
        SaveMapCommand llllllllllllllllIllIlIIIIIIllIlI;
        llllllllllllllllIllIlIIIIIIllIll.executes(llllllllllllllllIllIlIIIIIIIIlll -> {
            SaveMapCommand llllllllllllllllIllIlIIIIIIIlIII;
            ItemStack llllllllllllllllIllIlIIIIIIIIllI = llllllllllllllllIllIlIIIIIIIlIII.getMap();
            if (llllllllllllllllIllIlIIIIIIIIllI == null) {
                throw MAP_NOT_FOUND.create();
            }
            MapState llllllllllllllllIllIlIIIIIIIIlIl = FilledMapItem.getMapState((ItemStack)llllllllllllllllIllIlIIIIIIIIllI, (World)SaveMapCommand.mc.world);
            if (llllllllllllllllIllIlIIIIIIIIlIl == null) {
                throw MAP_NOT_FOUND.create();
            }
            String llllllllllllllllIllIlIIIIIIIIlII = TinyFileDialogs.tinyfd_saveFileDialog((CharSequence)"Save image", null, (PointerBuffer)llllllllllllllllIllIlIIIIIIIlIII.filters, null);
            if (llllllllllllllllIllIlIIIIIIIIlII == null) {
                throw OOPS.create();
            }
            if (!llllllllllllllllIllIlIIIIIIIIlII.endsWith(".png")) {
                llllllllllllllllIllIlIIIIIIIIlII = String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIIIIIIIIlII).append(".png"));
            }
            NativeImage llllllllllllllllIllIlIIIIIIIIIll = new NativeImage(128, 128, true);
            for (int llllllllllllllllIllIlIIIIIIIlIlI = 0; llllllllllllllllIllIlIIIIIIIlIlI < 128; ++llllllllllllllllIllIlIIIIIIIlIlI) {
                for (int llllllllllllllllIllIlIIIIIIIlIll = 0; llllllllllllllllIllIlIIIIIIIlIll < 128; ++llllllllllllllllIllIlIIIIIIIlIll) {
                    int llllllllllllllllIllIlIIIIIIIllII = llllllllllllllllIllIlIIIIIIIIlIl.colors[llllllllllllllllIllIlIIIIIIIlIlI + llllllllllllllllIllIlIIIIIIIlIll * 128] & 0xFF;
                    if (llllllllllllllllIllIlIIIIIIIllII / 4 == 0) {
                        llllllllllllllllIllIlIIIIIIIIIll.setPixelColor(llllllllllllllllIllIlIIIIIIIlIlI, llllllllllllllllIllIlIIIIIIIlIll, 0);
                        continue;
                    }
                    llllllllllllllllIllIlIIIIIIIIIll.setPixelColor(llllllllllllllllIllIlIIIIIIIlIlI, llllllllllllllllIllIlIIIIIIIlIll, MapColor.COLORS[llllllllllllllllIllIlIIIIIIIllII / 4].getRenderColor(llllllllllllllllIllIlIIIIIIIllII & 3));
                }
            }
            try {
                llllllllllllllllIllIlIIIIIIIIIll.writeFile(new File(llllllllllllllllIllIlIIIIIIIIlII));
                llllllllllllllllIllIlIIIIIIIIIll.close();
            }
            catch (IOException llllllllllllllllIllIlIIIIIIIlIIl) {
                llllllllllllllllIllIlIIIIIIIlIIl.printStackTrace();
            }
            return 1;
        });
    }

    private ItemStack getMap() {
        ItemStack llllllllllllllllIllIlIIIIIIlIllI = SaveMapCommand.mc.player.getMainHandStack();
        if (llllllllllllllllIllIlIIIIIIlIllI.getItem() == Items.FILLED_MAP) {
            return llllllllllllllllIllIlIIIIIIlIllI;
        }
        llllllllllllllllIllIlIIIIIIlIllI = SaveMapCommand.mc.player.getOffHandStack();
        if (llllllllllllllllIllIlIIIIIIlIllI.getItem() == Items.FILLED_MAP) {
            return llllllllllllllllIllIlIIIIIIlIllI;
        }
        return null;
    }

    public SaveMapCommand() {
        super("save-map", "Saves a map to an image.", "sm");
        SaveMapCommand llllllllllllllllIllIlIIIIIlIIIlI;
        llllllllllllllllIllIlIIIIIlIIIlI.filters = BufferUtils.createPointerBuffer((int)1);
        ByteBuffer llllllllllllllllIllIlIIIIIlIIIIl = MemoryUtil.memASCII((CharSequence)"*.png");
        llllllllllllllllIllIlIIIIIlIIIlI.filters.put(llllllllllllllllIllIlIIIIIlIIIIl);
        llllllllllllllllIllIlIIIIIlIIIlI.filters.rewind();
    }

    static {
        MAP_NOT_FOUND = new SimpleCommandExceptionType((Message)new LiteralText("You must be holding a filled map."));
        OOPS = new SimpleCommandExceptionType((Message)new LiteralText("Something went wrong."));
    }
}

