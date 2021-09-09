/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
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

public class Spam
extends Module {
    private final /* synthetic */ Setting<Integer> delay;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ int messageI;
    private final /* synthetic */ Setting<Boolean> random;
    private final /* synthetic */ List<String> messages;
    private /* synthetic */ int timer;

    @Override
    public Module fromTag(NbtCompound llllllllllllllllllIIlIlIllIlIIlI) {
        Spam llllllllllllllllllIIlIlIllIlIlIl;
        llllllllllllllllllIIlIlIllIlIlIl.messages.clear();
        if (llllllllllllllllllIIlIlIllIlIIlI.contains("messages")) {
            NbtList llllllllllllllllllIIlIlIllIlIllI = llllllllllllllllllIIlIlIllIlIIlI.getList("messages", 8);
            for (NbtElement llllllllllllllllllIIlIlIllIlIlll : llllllllllllllllllIIlIlIllIlIllI) {
                llllllllllllllllllIIlIlIllIlIlIl.messages.add(llllllllllllllllllIIlIlIllIlIlll.asString());
            }
        } else {
            llllllllllllllllllIIlIlIllIlIlIl.messages.add("Meteor on Crack!");
        }
        return super.fromTag(llllllllllllllllllIIlIlIllIlIIlI);
    }

    public Spam() {
        super(Categories.Misc, "spam", "Spams specified messages in chat.");
        Spam llllllllllllllllllIIlIllIIIllIII;
        llllllllllllllllllIIlIllIIIllIII.sgGeneral = llllllllllllllllllIIlIllIIIllIII.settings.getDefaultGroup();
        llllllllllllllllllIIlIllIIIllIII.delay = llllllllllllllllllIIlIllIIIllIII.sgGeneral.add(new IntSetting.Builder().name("delay").description("The delay between specified messages in seconds.").defaultValue(2).min(0).sliderMax(20).build());
        llllllllllllllllllIIlIllIIIllIII.random = llllllllllllllllllIIlIllIIIllIII.sgGeneral.add(new BoolSetting.Builder().name("random").description("Selects a random message from your spam message list.").defaultValue(false).build());
        llllllllllllllllllIIlIllIIIllIII.messages = new ArrayList<String>();
    }

    private void fillTable(GuiTheme llllllllllllllllllIIlIlIllllIlIl, WTable llllllllllllllllllIIlIlIllllIlII) {
        Spam llllllllllllllllllIIlIlIllllIIlI;
        llllllllllllllllllIIlIlIllllIlII.add(llllllllllllllllllIIlIlIllllIlIl.horizontalSeparator("Messages")).expandX();
        llllllllllllllllllIIlIlIllllIlII.row();
        for (int llllllllllllllllllIIlIlIllllIlll = 0; llllllllllllllllllIIlIlIllllIlll < llllllllllllllllllIIlIlIllllIIlI.messages.size(); ++llllllllllllllllllIIlIlIllllIlll) {
            int llllllllllllllllllIIlIlIlllllIll = llllllllllllllllllIIlIlIllllIlll;
            String llllllllllllllllllIIlIlIlllllIlI = llllllllllllllllllIIlIlIllllIIlI.messages.get(llllllllllllllllllIIlIlIllllIlll);
            WTextBox llllllllllllllllllIIlIlIlllllIIl = llllllllllllllllllIIlIlIllllIlII.add(llllllllllllllllllIIlIlIllllIlIl.textBox(llllllllllllllllllIIlIlIlllllIlI)).minWidth(100.0).expandX().widget();
            llllllllllllllllllIIlIlIlllllIIl.action = () -> {
                Spam llllllllllllllllllIIlIlIlIlIlllI;
                llllllllllllllllllIIlIlIlIlIlllI.messages.set(llllllllllllllllllIIlIlIlllllIll, llllllllllllllllllIIlIlIlllllIIl.get());
            };
            WMinus llllllllllllllllllIIlIlIlllllIII = llllllllllllllllllIIlIlIllllIlII.add(llllllllllllllllllIIlIlIllllIlIl.minus()).widget();
            llllllllllllllllllIIlIlIlllllIII.action = () -> {
                Spam llllllllllllllllllIIlIlIlIlllIII;
                llllllllllllllllllIIlIlIlIlllIII.messages.remove(llllllllllllllllllIIlIlIlllllIll);
                llllllllllllllllllIIlIlIllllIlII.clear();
                llllllllllllllllllIIlIlIlIlllIII.fillTable(llllllllllllllllllIIlIlIllllIlIl, llllllllllllllllllIIlIlIllllIlII);
            };
            llllllllllllllllllIIlIlIllllIlII.row();
        }
        WPlus llllllllllllllllllIIlIlIllllIIll = llllllllllllllllllIIlIlIllllIlII.add(llllllllllllllllllIIlIlIllllIlIl.plus()).expandCellX().right().widget();
        llllllllllllllllllIIlIlIllllIIll.action = () -> {
            Spam llllllllllllllllllIIlIlIllIIIIll;
            llllllllllllllllllIIlIlIllIIIIll.messages.add("");
            llllllllllllllllllIIlIlIllllIlII.clear();
            llllllllllllllllllIIlIlIllIIIIll.fillTable(llllllllllllllllllIIlIlIllllIlIl, llllllllllllllllllIIlIlIllllIlII);
        };
    }

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllllIIlIllIIIIllll) {
        Spam llllllllllllllllllIIlIllIIIlIIII;
        if (llllllllllllllllllIIlIllIIIlIIII.messages.isEmpty()) {
            return;
        }
        if (llllllllllllllllllIIlIllIIIlIIII.timer <= 0) {
            int llllllllllllllllllIIlIllIIIlIIIl;
            if (llllllllllllllllllIIlIllIIIlIIII.random.get().booleanValue()) {
                int llllllllllllllllllIIlIllIIIlIIlI = Utils.random(0, llllllllllllllllllIIlIllIIIlIIII.messages.size());
            } else {
                if (llllllllllllllllllIIlIllIIIlIIII.messageI >= llllllllllllllllllIIlIllIIIlIIII.messages.size()) {
                    llllllllllllllllllIIlIllIIIlIIII.messageI = 0;
                }
                llllllllllllllllllIIlIllIIIlIIIl = llllllllllllllllllIIlIllIIIlIIII.messageI++;
            }
            llllllllllllllllllIIlIllIIIlIIII.mc.player.sendChatMessage(llllllllllllllllllIIlIllIIIlIIII.messages.get(llllllllllllllllllIIlIllIIIlIIIl));
            llllllllllllllllllIIlIllIIIlIIII.timer = llllllllllllllllllIIlIllIIIlIIII.delay.get() * 20;
        } else {
            --llllllllllllllllllIIlIllIIIlIIII.timer;
        }
    }

    @Override
    public WWidget getWidget(GuiTheme llllllllllllllllllIIlIllIIIIlIII) {
        Spam llllllllllllllllllIIlIllIIIIIllI;
        llllllllllllllllllIIlIllIIIIIllI.messages.removeIf(String::isEmpty);
        WTable llllllllllllllllllIIlIllIIIIIlll = llllllllllllllllllIIlIllIIIIlIII.table();
        llllllllllllllllllIIlIllIIIIIllI.fillTable(llllllllllllllllllIIlIllIIIIlIII, llllllllllllllllllIIlIllIIIIIlll);
        return llllllllllllllllllIIlIllIIIIIlll;
    }

    @Override
    public NbtCompound toTag() {
        Spam llllllllllllllllllIIlIlIlllIIIIl;
        NbtCompound llllllllllllllllllIIlIlIlllIIIll = super.toTag();
        llllllllllllllllllIIlIlIlllIIIIl.messages.removeIf(String::isEmpty);
        NbtList llllllllllllllllllIIlIlIlllIIIlI = new NbtList();
        for (String llllllllllllllllllIIlIlIlllIIlIl : llllllllllllllllllIIlIlIlllIIIIl.messages) {
            llllllllllllllllllIIlIlIlllIIIlI.add((Object)NbtString.of((String)llllllllllllllllllIIlIlIlllIIlIl));
        }
        llllllllllllllllllIIlIlIlllIIIll.put("messages", (NbtElement)llllllllllllllllllIIlIlIlllIIIlI);
        return llllllllllllllllllIIlIlIlllIIIll;
    }

    @Override
    public void onActivate() {
        Spam llllllllllllllllllIIlIllIIIlIlIl;
        llllllllllllllllllIIlIllIIIlIlIl.timer = llllllllllllllllllIIlIllIIIlIlIl.delay.get() * 20;
        llllllllllllllllllIIlIllIIIlIlIl.messageI = 0;
    }
}

