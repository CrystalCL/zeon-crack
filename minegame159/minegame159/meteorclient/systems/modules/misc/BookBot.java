/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PrimitiveIterator;
import java.util.Random;
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
    private final Setting<Integer> delay;
    private final Setting<Integer> noOfBooks;
    private NbtList pages;
    private boolean firstChar;
    private final Setting<String> fileName;
    private PrimitiveIterator.OfInt stream;
    private int ticksLeft;
    private static final Random RANDOM;
    private static final int LINE_WIDTH;
    private final StringBuilder lineSb;
    private final Setting<Integer> noOfPages;
    private boolean firstTime;
    private final StringBuilder pageSb;
    private String fileString;
    private int nextChar;
    private final Setting<Mode> mode;
    private int booksLeft;
    private final Setting<String> name;
    private final SettingGroup sgGeneral;

    private void writeBook() {
        this.pages.clear();
        if (this.firstChar) {
            this.readChar();
            this.firstChar = false;
        }
        for (int i = 0; i < this.noOfPages.get(); ++i) {
            this.pageSb.setLength(0);
            boolean bl = false;
            for (int j = 0; j < 13; ++j) {
                boolean bl2;
                block6: {
                    this.lineSb.setLength(0);
                    float f = 0.0f;
                    bl2 = false;
                    do {
                        float f2 = ((TextHandlerAccessor)this.mc.textRenderer.getTextHandler()).getWidthRetriever().getWidth(this.nextChar, Style.EMPTY);
                        if (this.nextChar == 10) {
                            if (!this.readChar()) {
                                bl2 = true;
                            }
                            break block6;
                        }
                        if (!(f + f2 < 113.0f)) break block6;
                        this.lineSb.appendCodePoint(this.nextChar);
                        f += f2;
                    } while (this.readChar());
                    bl2 = true;
                }
                this.pageSb.append((CharSequence)this.lineSb).append('\n');
                if (!bl2) continue;
                bl = true;
                break;
            }
            this.pages.add((Object)NbtString.of((String)String.valueOf(this.pageSb)));
            if (bl) break;
        }
        this.mc.player.getMainHandStack().putSubTag("pages", (NbtElement)this.pages);
        this.mc.player.getMainHandStack().putSubTag("author", (NbtElement)NbtString.of((String)"squidoodly"));
        this.mc.player.getMainHandStack().putSubTag("title", (NbtElement)NbtString.of((String)this.name.get()));
        this.mc.player.networkHandler.sendPacket((Packet)new BookUpdateC2SPacket(this.mc.player.getMainHandStack(), true, this.mc.player.inventory.selectedSlot));
        --this.booksLeft;
    }

    static {
        LINE_WIDTH = 113;
        RANDOM = new Random();
    }

    public BookBot() {
        super(Categories.Misc, "book-bot", "Writes an amount of books full of characters or from a file.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode of the book bot.").defaultValue(Mode.Ascii).build());
        this.name = this.sgGeneral.add(new StringSetting.Builder().name("name").description("The name you want to give your books.").defaultValue("Meteor on Crack!").build());
        this.fileName = this.sgGeneral.add(new StringSetting.Builder().name("file-name").description("The name of the text file (.txt NEEDED)").defaultValue("book.txt").build());
        this.noOfPages = this.sgGeneral.add(new IntSetting.Builder().name("no-of-pages").description("The number of pages to write per book.").defaultValue(100).min(1).max(100).sliderMax(100).build());
        this.noOfBooks = this.sgGeneral.add(new IntSetting.Builder().name("no-of-books").description("The number of books to make (or until the file runs out).").defaultValue(1).build());
        this.delay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("The amount of delay between writing books in milliseconds.").defaultValue(300).min(75).sliderMin(75).sliderMax(600).build());
        this.pages = new NbtList();
        this.ticksLeft = 0;
        this.pageSb = new StringBuilder();
        this.lineSb = new StringBuilder();
    }

    @Override
    public void onDeactivate() {
        this.booksLeft = 0;
        this.pages = new NbtList();
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        Object object;
        if (this.mc.currentScreen instanceof HandledScreen) {
            return;
        }
        if (this.booksLeft <= 0) {
            this.toggle();
            return;
        }
        if (this.mc.player.getMainHandStack().getItem() != Items.WRITABLE_BOOK) {
            object = InvUtils.findItemWithCount(Items.WRITABLE_BOOK);
            if (((InvUtils.FindItemResult)object).slot <= 8 && ((InvUtils.FindItemResult)object).slot != -1) {
                this.mc.player.inventory.selectedSlot = ((InvUtils.FindItemResult)object).slot;
                ((IClientPlayerInteractionManager)this.mc.interactionManager).syncSelectedSlot2();
            } else if (((InvUtils.FindItemResult)object).slot > 8) {
                InvUtils.move().from(((InvUtils.FindItemResult)object).slot).toHotbar(this.mc.player.inventory.selectedSlot);
            } else {
                return;
            }
        }
        if (this.ticksLeft > 0) {
            this.ticksLeft -= 50;
            return;
        }
        this.ticksLeft = this.delay.get();
        if (this.mode.get() == Mode.Random) {
            object = RANDOM.ints(128, 1112063).map(BookBot::lambda$onTick$0);
            this.stream = object.limit(23000L).iterator();
            this.firstChar = true;
            this.writeBook();
        } else if (this.mode.get() == Mode.Ascii) {
            object = RANDOM.ints(32, 127);
            this.stream = object.limit(35000L).iterator();
            this.firstChar = true;
            this.writeBook();
        } else if (this.mode.get() == Mode.File) {
            if (this.firstTime) {
                object = new File(MeteorClient.FOLDER, this.fileName.get());
                if (!((File)object).exists()) {
                    ChatUtils.moduleError(this, "The file you specified doesn't exist in the meteor folder.", new Object[0]);
                    return;
                }
                try {
                    String string;
                    BufferedReader bufferedReader = new BufferedReader(new FileReader((File)object));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((string = bufferedReader.readLine()) != null) {
                        stringBuilder.append(string).append('\n');
                    }
                    bufferedReader.close();
                    this.firstTime = false;
                    this.fileString = String.valueOf(stringBuilder);
                    this.stream = this.fileString.chars().iterator();
                    this.firstChar = true;
                    this.writeBook();
                }
                catch (IOException iOException) {
                    ChatUtils.moduleError(this, "Failed to read the file.", new Object[0]);
                }
            } else if (this.stream != null) {
                this.writeBook();
            } else if (this.booksLeft > 0) {
                this.stream = this.fileString.chars().iterator();
                this.writeBook();
            }
        }
    }

    private boolean readChar() {
        if (!this.stream.hasNext()) {
            this.stream = null;
            return false;
        }
        this.nextChar = this.stream.nextInt();
        return true;
    }

    @Override
    public void onActivate() {
        this.booksLeft = this.noOfBooks.get();
        this.firstTime = true;
    }

    private static int lambda$onTick$0(int n) {
        return n < 55296 ? n : n + 2048;
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Ascii;
        public static final /* enum */ Mode File;
        public static final /* enum */ Mode Random;
        private static final Mode[] $VALUES;

        static {
            File = new Mode();
            Random = new Mode();
            Ascii = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static Mode[] $values() {
            return new Mode[]{File, Random, Ascii};
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }
    }
}

