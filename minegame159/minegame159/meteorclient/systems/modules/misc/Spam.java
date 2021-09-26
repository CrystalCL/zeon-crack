/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.misc;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.gui.widgets.pressable.WPlus;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Spam
extends Module {
    private final Setting<Integer> delay;
    private final SettingGroup sgGeneral;
    private int timer;
    private final List<String> messages;
    private int messageI;
    private final Setting<Boolean> random;

    private void lambda$fillTable$2(WTable wTable, GuiTheme guiTheme) {
        this.messages.add("");
        wTable.clear();
        this.fillTable(guiTheme, wTable);
    }

    private void lambda$fillTable$1(int n, WTable wTable, GuiTheme guiTheme) {
        this.messages.remove(n);
        wTable.clear();
        this.fillTable(guiTheme, wTable);
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.messages.isEmpty()) {
            return;
        }
        if (this.timer <= 0) {
            int n;
            if (this.random.get().booleanValue()) {
                n = Utils.random(0, this.messages.size());
            } else {
                if (this.messageI >= this.messages.size()) {
                    this.messageI = 0;
                }
                n = this.messageI++;
            }
            this.mc.player.sendChatMessage(this.messages.get(n));
            this.timer = this.delay.get() * 20;
        } else {
            --this.timer;
        }
    }

    @Override
    public void onActivate() {
        this.timer = this.delay.get() * 20;
        this.messageI = 0;
    }

    private void lambda$fillTable$0(int n, WTextBox wTextBox) {
        this.messages.set(n, wTextBox.get());
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = super.toTag();
        this.messages.removeIf(String::isEmpty);
        NbtList NbtList2 = new NbtList();
        for (String string : this.messages) {
            NbtList2.add((Object)NbtString.of((String)string));
        }
        NbtCompound2.put("messages", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    private void fillTable(GuiTheme guiTheme, WTable wTable) {
        wTable.add(guiTheme.horizontalSeparator("Messages")).expandX();
        wTable.row();
        for (int i = 0; i < this.messages.size(); ++i) {
            int n = i;
            String string = this.messages.get(i);
            WTextBox wTextBox = wTable.add(guiTheme.textBox(string)).minWidth(100.0).expandX().widget();
            wTextBox.action = () -> this.lambda$fillTable$0(n, wTextBox);
            WMinus wMinus = wTable.add(guiTheme.minus()).widget();
            wMinus.action = () -> this.lambda$fillTable$1(n, wTable, guiTheme);
            wTable.row();
            if (3 >= 1) continue;
            return;
        }
        WPlus wPlus = wTable.add(guiTheme.plus()).expandCellX().right().widget();
        wPlus.action = () -> this.lambda$fillTable$2(wTable, guiTheme);
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public Spam() {
        super(Categories.Misc, "spam", "Spams specified messages in chat.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.delay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("The delay between specified messages in seconds.").defaultValue(2).min(0).sliderMax(20).build());
        this.random = this.sgGeneral.add(new BoolSetting.Builder().name("random").description("Selects a random message from your spam message list.").defaultValue(false).build());
        this.messages = new ArrayList<String>();
    }

    @Override
    public WWidget getWidget(GuiTheme guiTheme) {
        this.messages.removeIf(String::isEmpty);
        WTable wTable = guiTheme.table();
        this.fillTable(guiTheme, wTable);
        return wTable;
    }

    @Override
    public Module fromTag(NbtCompound NbtCompound2) {
        this.messages.clear();
        if (NbtCompound2.contains("messages")) {
            NbtList NbtList2 = NbtCompound2.getList("messages", 8);
            for (NbtElement NbtElement2 : NbtList2) {
                this.messages.add(NbtElement2.asString());
            }
        } else {
            this.messages.add("Meteor on Crack!");
        }
        return super.fromTag(NbtCompound2);
    }
}

