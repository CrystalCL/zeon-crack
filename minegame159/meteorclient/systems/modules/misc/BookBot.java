/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Items
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.text.Style
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket
 *  net.minecraft.client.gui.screen.ingame.HandledScreen
 */
package minegame159.meteorclient.systems.modules.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.stream.IntStream;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.TextHandlerAccessor;
import minegame159.meteorclient.mixininterface.IClientPlayerInteractionManager;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Style;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public class BookBot
extends Module {
    private /* synthetic */ NbtList pages;
    private /* synthetic */ PrimitiveIterator.OfInt stream;
    private final /* synthetic */ Setting<String> name;
    private final /* synthetic */ Setting<Integer> noOfBooks;
    private static final /* synthetic */ Random RANDOM;
    private /* synthetic */ int ticksLeft;
    private /* synthetic */ int booksLeft;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ boolean firstTime;
    private final /* synthetic */ Setting<Integer> delay;
    private static final /* synthetic */ int LINE_WIDTH;
    private final /* synthetic */ StringBuilder pageSb;
    private /* synthetic */ int nextChar;
    private /* synthetic */ String fileString;
    private /* synthetic */ boolean firstChar;
    private final /* synthetic */ Setting<Mode> mode;
    private final /* synthetic */ Setting<String> fileName;
    private final /* synthetic */ Setting<Integer> noOfPages;
    private final /* synthetic */ StringBuilder lineSb;

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllllllllIIlIIlIII) {
        BookBot lllllllllllllllllllllllIIlIIIlll;
        if (lllllllllllllllllllllllIIlIIIlll.mc.currentScreen instanceof HandledScreen) {
            return;
        }
        if (lllllllllllllllllllllllIIlIIIlll.booksLeft <= 0) {
            lllllllllllllllllllllllIIlIIIlll.toggle();
            return;
        }
        if (lllllllllllllllllllllllIIlIIIlll.mc.player.getMainHandStack().getItem() != Items.WRITABLE_BOOK) {
            InvUtils.FindItemResult lllllllllllllllllllllllIIlIlIIIl = InvUtils.findItemWithCount(Items.WRITABLE_BOOK);
            if (lllllllllllllllllllllllIIlIlIIIl.slot <= 8 && lllllllllllllllllllllllIIlIlIIIl.slot != -1) {
                lllllllllllllllllllllllIIlIIIlll.mc.player.inventory.selectedSlot = lllllllllllllllllllllllIIlIlIIIl.slot;
                ((IClientPlayerInteractionManager)lllllllllllllllllllllllIIlIIIlll.mc.interactionManager).syncSelectedSlot2();
            } else if (lllllllllllllllllllllllIIlIlIIIl.slot > 8) {
                InvUtils.move().from(lllllllllllllllllllllllIIlIlIIIl.slot).toHotbar(lllllllllllllllllllllllIIlIIIlll.mc.player.inventory.selectedSlot);
            } else {
                return;
            }
        }
        if (lllllllllllllllllllllllIIlIIIlll.ticksLeft > 0) {
            lllllllllllllllllllllllIIlIIIlll.ticksLeft -= 50;
            return;
        }
        lllllllllllllllllllllllIIlIIIlll.ticksLeft = lllllllllllllllllllllllIIlIIIlll.delay.get();
        if (lllllllllllllllllllllllIIlIIIlll.mode.get() == Mode.Random) {
            IntStream lllllllllllllllllllllllIIlIlIIII = RANDOM.ints(128, 1112063).map(lllllllllllllllllllllllIIIlIlIIl -> lllllllllllllllllllllllIIIlIlIIl < 55296 ? lllllllllllllllllllllllIIIlIlIIl : lllllllllllllllllllllllIIIlIlIIl + 2048);
            lllllllllllllllllllllllIIlIIIlll.stream = lllllllllllllllllllllllIIlIlIIII.limit(23000L).iterator();
            lllllllllllllllllllllllIIlIIIlll.firstChar = true;
            lllllllllllllllllllllllIIlIIIlll.writeBook();
        } else if (lllllllllllllllllllllllIIlIIIlll.mode.get() == Mode.Ascii) {
            IntStream lllllllllllllllllllllllIIlIIllll = RANDOM.ints(32, 127);
            lllllllllllllllllllllllIIlIIIlll.stream = lllllllllllllllllllllllIIlIIllll.limit(35000L).iterator();
            lllllllllllllllllllllllIIlIIIlll.firstChar = true;
            lllllllllllllllllllllllIIlIIIlll.writeBook();
        } else if (lllllllllllllllllllllllIIlIIIlll.mode.get() == Mode.File) {
            if (lllllllllllllllllllllllIIlIIIlll.firstTime) {
                File lllllllllllllllllllllllIIlIIlIlI = new File(MeteorClient.FOLDER, lllllllllllllllllllllllIIlIIIlll.fileName.get());
                if (!lllllllllllllllllllllllIIlIIlIlI.exists()) {
                    ChatUtils.moduleError(lllllllllllllllllllllllIIlIIIlll, "The file you specified doesn't exist in the meteor folder.", new Object[0]);
                    return;
                }
                try {
                    String lllllllllllllllllllllllIIlIIllII;
                    BufferedReader lllllllllllllllllllllllIIlIIlllI = new BufferedReader(new FileReader(lllllllllllllllllllllllIIlIIlIlI));
                    StringBuilder lllllllllllllllllllllllIIlIIllIl = new StringBuilder();
                    while ((lllllllllllllllllllllllIIlIIllII = lllllllllllllllllllllllIIlIIlllI.readLine()) != null) {
                        lllllllllllllllllllllllIIlIIllIl.append(lllllllllllllllllllllllIIlIIllII).append('\n');
                    }
                    lllllllllllllllllllllllIIlIIlllI.close();
                    lllllllllllllllllllllllIIlIIIlll.firstTime = false;
                    lllllllllllllllllllllllIIlIIIlll.fileString = String.valueOf(lllllllllllllllllllllllIIlIIllIl);
                    lllllllllllllllllllllllIIlIIIlll.stream = lllllllllllllllllllllllIIlIIIlll.fileString.chars().iterator();
                    lllllllllllllllllllllllIIlIIIlll.firstChar = true;
                    lllllllllllllllllllllllIIlIIIlll.writeBook();
                }
                catch (IOException lllllllllllllllllllllllIIlIIlIll) {
                    ChatUtils.moduleError(lllllllllllllllllllllllIIlIIIlll, "Failed to read the file.", new Object[0]);
                }
            } else if (lllllllllllllllllllllllIIlIIIlll.stream != null) {
                lllllllllllllllllllllllIIlIIIlll.writeBook();
            } else if (lllllllllllllllllllllllIIlIIIlll.booksLeft > 0) {
                lllllllllllllllllllllllIIlIIIlll.stream = lllllllllllllllllllllllIIlIIIlll.fileString.chars().iterator();
                lllllllllllllllllllllllIIlIIIlll.writeBook();
            }
        }
    }

    static {
        LINE_WIDTH = 113;
        RANDOM = new Random();
    }

    public BookBot() {
        super(Categories.Misc, "book-bot", "Writes an amount of books full of characters or from a file.");
        BookBot lllllllllllllllllllllllIIlIlllIl;
        lllllllllllllllllllllllIIlIlllIl.sgGeneral = lllllllllllllllllllllllIIlIlllIl.settings.getDefaultGroup();
        lllllllllllllllllllllllIIlIlllIl.mode = lllllllllllllllllllllllIIlIlllIl.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode of the book bot.").defaultValue(Mode.Ascii).build());
        lllllllllllllllllllllllIIlIlllIl.name = lllllllllllllllllllllllIIlIlllIl.sgGeneral.add(new StringSetting.Builder().name("name").description("The name you want to give your books.").defaultValue("Meteor on Crack!").build());
        lllllllllllllllllllllllIIlIlllIl.fileName = lllllllllllllllllllllllIIlIlllIl.sgGeneral.add(new StringSetting.Builder().name("file-name").description("The name of the text file (.txt NEEDED)").defaultValue("book.txt").build());
        lllllllllllllllllllllllIIlIlllIl.noOfPages = lllllllllllllllllllllllIIlIlllIl.sgGeneral.add(new IntSetting.Builder().name("no-of-pages").description("The number of pages to write per book.").defaultValue(100).min(1).max(100).sliderMax(100).build());
        lllllllllllllllllllllllIIlIlllIl.noOfBooks = lllllllllllllllllllllllIIlIlllIl.sgGeneral.add(new IntSetting.Builder().name("no-of-books").description("The number of books to make (or until the file runs out).").defaultValue(1).build());
        lllllllllllllllllllllllIIlIlllIl.delay = lllllllllllllllllllllllIIlIlllIl.sgGeneral.add(new IntSetting.Builder().name("delay").description("The amount of delay between writing books in milliseconds.").defaultValue(300).min(75).sliderMin(75).sliderMax(600).build());
        lllllllllllllllllllllllIIlIlllIl.pages = new NbtList();
        lllllllllllllllllllllllIIlIlllIl.ticksLeft = 0;
        lllllllllllllllllllllllIIlIlllIl.pageSb = new StringBuilder();
        lllllllllllllllllllllllIIlIlllIl.lineSb = new StringBuilder();
    }

    @Override
    public void onActivate() {
        BookBot lllllllllllllllllllllllIIlIllIlI;
        lllllllllllllllllllllllIIlIllIlI.booksLeft = lllllllllllllllllllllllIIlIllIlI.noOfBooks.get();
        lllllllllllllllllllllllIIlIllIlI.firstTime = true;
    }

    @Override
    public void onDeactivate() {
        lllllllllllllllllllllllIIlIlIlll.booksLeft = 0;
        lllllllllllllllllllllllIIlIlIlll.pages = new NbtList();
    }

    private void writeBook() {
        BookBot lllllllllllllllllllllllIIIllIlII;
        lllllllllllllllllllllllIIIllIlII.pages.clear();
        if (lllllllllllllllllllllllIIIllIlII.firstChar) {
            lllllllllllllllllllllllIIIllIlII.readChar();
            lllllllllllllllllllllllIIIllIlII.firstChar = false;
        }
        for (int lllllllllllllllllllllllIIIllIllI = 0; lllllllllllllllllllllllIIIllIllI < lllllllllllllllllllllllIIIllIlII.noOfPages.get(); ++lllllllllllllllllllllllIIIllIllI) {
            lllllllllllllllllllllllIIIllIlII.pageSb.setLength(0);
            boolean lllllllllllllllllllllllIIIllIlll = false;
            for (int lllllllllllllllllllllllIIIlllIII = 0; lllllllllllllllllllllllIIIlllIII < 13; ++lllllllllllllllllllllllIIIlllIII) {
                boolean lllllllllllllllllllllllIIIlllIIl;
                block6: {
                    lllllllllllllllllllllllIIIllIlII.lineSb.setLength(0);
                    float lllllllllllllllllllllllIIIlllIlI = 0.0f;
                    lllllllllllllllllllllllIIIlllIIl = false;
                    do {
                        float lllllllllllllllllllllllIIIlllIll = ((TextHandlerAccessor)lllllllllllllllllllllllIIIllIlII.mc.textRenderer.getTextHandler()).getWidthRetriever().getWidth(lllllllllllllllllllllllIIIllIlII.nextChar, Style.EMPTY);
                        if (lllllllllllllllllllllllIIIllIlII.nextChar == 10) {
                            if (!lllllllllllllllllllllllIIIllIlII.readChar()) {
                                lllllllllllllllllllllllIIIlllIIl = true;
                            }
                            break block6;
                        }
                        if (!(lllllllllllllllllllllllIIIlllIlI + lllllllllllllllllllllllIIIlllIll < 113.0f)) break block6;
                        lllllllllllllllllllllllIIIllIlII.lineSb.appendCodePoint(lllllllllllllllllllllllIIIllIlII.nextChar);
                        lllllllllllllllllllllllIIIlllIlI += lllllllllllllllllllllllIIIlllIll;
                    } while (lllllllllllllllllllllllIIIllIlII.readChar());
                    lllllllllllllllllllllllIIIlllIIl = true;
                }
                lllllllllllllllllllllllIIIllIlII.pageSb.append((CharSequence)lllllllllllllllllllllllIIIllIlII.lineSb).append('\n');
                if (!lllllllllllllllllllllllIIIlllIIl) continue;
                lllllllllllllllllllllllIIIllIlll = true;
                break;
            }
            lllllllllllllllllllllllIIIllIlII.pages.add((Object)NbtString.of((String)String.valueOf(lllllllllllllllllllllllIIIllIlII.pageSb)));
            if (lllllllllllllllllllllllIIIllIlll) break;
        }
        lllllllllllllllllllllllIIIllIlII.mc.player.getMainHandStack().putSubTag("pages", (NbtElement)lllllllllllllllllllllllIIIllIlII.pages);
        lllllllllllllllllllllllIIIllIlII.mc.player.getMainHandStack().putSubTag("author", (NbtElement)NbtString.of((String)"squidoodly"));
        lllllllllllllllllllllllIIIllIlII.mc.player.getMainHandStack().putSubTag("title", (NbtElement)NbtString.of((String)lllllllllllllllllllllllIIIllIlII.name.get()));
        lllllllllllllllllllllllIIIllIlII.mc.player.networkHandler.sendPacket((Packet)new BookUpdateC2SPacket(lllllllllllllllllllllllIIIllIlII.mc.player.getMainHandStack(), true, lllllllllllllllllllllllIIIllIlII.mc.player.inventory.selectedSlot));
        --lllllllllllllllllllllllIIIllIlII.booksLeft;
    }

    private boolean readChar() {
        BookBot lllllllllllllllllllllllIIIlIllII;
        if (!lllllllllllllllllllllllIIIlIllII.stream.hasNext()) {
            lllllllllllllllllllllllIIIlIllII.stream = null;
            return false;
        }
        lllllllllllllllllllllllIIIlIllII.nextChar = lllllllllllllllllllllllIIIlIllII.stream.nextInt();
        return true;
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Random;
        public static final /* synthetic */ /* enum */ Mode Ascii;
        public static final /* synthetic */ /* enum */ Mode File;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            File = new Mode();
            Random = new Mode();
            Ascii = new Mode();
            $VALUES = Mode.$values();
        }

        private Mode() {
            Mode lIIllIIIlIIlIlI;
        }

        public static Mode valueOf(String lIIllIIIlIIllll) {
            return Enum.valueOf(Mode.class, lIIllIIIlIIllll);
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{File, Random, Ascii};
        }
    }
}

