/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.gui.widgets.pressable.WPlus;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AntiAFK
extends Module {
    private final Setting<Boolean> spin;
    private float prevYaw;
    private final Setting<Double> pitch;
    private int messageI;
    private final Random random;
    private final Setting<Boolean> strafe;
    private final SettingGroup sgActions;
    private final SettingGroup sgMessages;
    private final Setting<Boolean> click;
    private int strafeTimer;
    private final List<String> messages;
    private final Setting<Boolean> sendMessages;
    private final Setting<Integer> spinSpeed;
    private final Setting<Boolean> randomMessage;
    private final Setting<Integer> delay;
    private final Setting<Boolean> disco;
    private int timer;
    private final Setting<SpinMode> spinMode;
    private boolean direction;
    private final Setting<Boolean> jump;

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (Utils.canUpdate()) {
            if (this.spin.get().booleanValue()) {
                this.prevYaw += (float)this.spinSpeed.get().intValue();
                switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$player$AntiAFK$SpinMode[this.spinMode.get().ordinal()]) {
                    case 1: {
                        this.mc.player.yaw = this.prevYaw;
                        break;
                    }
                    case 2: {
                        Rotations.rotate(this.prevYaw, this.pitch.get(), -15, null);
                    }
                }
            }
            if (this.jump.get().booleanValue() && this.mc.options.keyJump.isPressed()) {
                this.mc.options.keyJump.setPressed(false);
            }
            if (this.jump.get().booleanValue() && this.mc.options.keySneak.isPressed()) {
                this.mc.options.keySneak.setPressed(false);
            } else if (this.jump.get().booleanValue() && this.random.nextInt(99) + 1 == 50) {
                this.mc.options.keyJump.setPressed(true);
            }
            if (this.click.get().booleanValue() && this.random.nextInt(99) + 1 == 45) {
                this.mc.options.keyAttack.setPressed(true);
                Utils.leftClick();
                this.mc.options.keyAttack.setPressed(false);
            }
            if (this.disco.get().booleanValue() && this.random.nextInt(24) + 1 == 15) {
                this.mc.options.keySneak.setPressed(true);
            }
            if (this.sendMessages.get().booleanValue() && !this.messages.isEmpty()) {
                if (this.timer <= 0) {
                    int n;
                    if (this.randomMessage.get().booleanValue()) {
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
            if (this.strafe.get().booleanValue() && this.strafeTimer == 20) {
                this.mc.options.keyLeft.setPressed(!this.direction);
                this.mc.options.keyRight.setPressed(this.direction);
                this.direction = !this.direction;
                this.strafeTimer = 0;
            } else {
                ++this.strafeTimer;
            }
        }
    }

    private void fillTable(GuiTheme guiTheme, WTable wTable) {
        wTable.add(guiTheme.horizontalSeparator("Message List")).expandX();
        for (int i = 0; i < this.messages.size(); ++i) {
            int n = i;
            String string = this.messages.get(i);
            WTextBox wTextBox = wTable.add(guiTheme.textBox(string)).minWidth(100.0).expandX().widget();
            wTextBox.action = () -> this.lambda$fillTable$1(n, wTextBox);
            WMinus wMinus = wTable.add(guiTheme.minus()).widget();
            wMinus.action = () -> this.lambda$fillTable$2(n, wTable, guiTheme);
            wTable.row();
        }
        WPlus wPlus = wTable.add(guiTheme.plus()).expandCellX().right().widget();
        wPlus.action = () -> this.lambda$fillTable$3(wTable, guiTheme);
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
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
            this.messages.add("This is an AntiAFK message. Meteor on Crack!");
        }
        return super.fromTag(NbtCompound2);
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

    private void lambda$new$0(Boolean bl) {
        this.strafeTimer = 0;
        this.direction = false;
        if (this.isActive()) {
            this.mc.options.keyLeft.setPressed(false);
            this.mc.options.keyRight.setPressed(false);
        }
    }

    private void lambda$fillTable$2(int n, WTable wTable, GuiTheme guiTheme) {
        this.messages.remove(n);
        wTable.clear();
        this.fillTable(guiTheme, wTable);
    }

    public AntiAFK() {
        super(Categories.Player, "anti-afk", "Performs different actions to prevent getting kicked for AFK reasons.");
        this.sgActions = this.settings.createGroup("Actions");
        this.sgMessages = this.settings.createGroup("Messages");
        this.spin = this.sgActions.add(new BoolSetting.Builder().name("spin").description("Spins.").defaultValue(true).build());
        this.spinMode = this.sgActions.add(new EnumSetting.Builder().name("spin-mode").description("The method of rotating.").defaultValue(SpinMode.Server).build());
        this.spinSpeed = this.sgActions.add(new IntSetting.Builder().name("spin-speed").description("The speed to spin you.").defaultValue(7).build());
        this.pitch = this.sgActions.add(new DoubleSetting.Builder().name("pitch").description("The pitch to set in server mode.").defaultValue(-90.0).min(-90.0).max(90.0).sliderMin(-90.0).sliderMax(90.0).build());
        this.jump = this.sgActions.add(new BoolSetting.Builder().name("jump").description("Jumps.").defaultValue(true).build());
        this.click = this.sgActions.add(new BoolSetting.Builder().name("click").description("Clicks.").defaultValue(false).build());
        this.disco = this.sgActions.add(new BoolSetting.Builder().name("disco").description("Sneaks and unsneaks quickly.").defaultValue(false).build());
        this.strafe = this.sgActions.add(new BoolSetting.Builder().name("strafe").description("Strafe right and left").defaultValue(false).onChanged(this::lambda$new$0).build());
        this.sendMessages = this.sgMessages.add(new BoolSetting.Builder().name("send-messages").description("Sends messages to prevent getting kicked for AFK.").defaultValue(false).build());
        this.delay = this.sgMessages.add(new IntSetting.Builder().name("delay").description("The delay between specified messages in seconds.").defaultValue(2).min(0).sliderMax(20).build());
        this.randomMessage = this.sgMessages.add(new BoolSetting.Builder().name("random").description("Selects a random message from your message list.").defaultValue(false).build());
        this.messages = new ArrayList<String>();
        this.strafeTimer = 0;
        this.direction = false;
        this.random = new Random();
    }

    @Override
    public void onActivate() {
        this.prevYaw = this.mc.player.yaw;
        this.timer = this.delay.get() * 20;
    }

    @Override
    public void onDeactivate() {
        if (this.strafe.get().booleanValue()) {
            this.mc.options.keyLeft.setPressed(false);
            this.mc.options.keyRight.setPressed(false);
        }
    }

    private void lambda$fillTable$3(WTable wTable, GuiTheme guiTheme) {
        this.messages.add("");
        wTable.clear();
        this.fillTable(guiTheme, wTable);
    }

    @Override
    public WWidget getWidget(GuiTheme guiTheme) {
        this.messages.removeIf(String::isEmpty);
        WTable wTable = guiTheme.table();
        this.fillTable(guiTheme, wTable);
        return wTable;
    }

    private void lambda$fillTable$1(int n, WTextBox wTextBox) {
        this.messages.set(n, wTextBox.get());
    }

    public static final class SpinMode
    extends Enum<SpinMode> {
        public static final /* enum */ SpinMode Server = new SpinMode();
        public static final /* enum */ SpinMode Client = new SpinMode();
        private static final SpinMode[] $VALUES = SpinMode.$values();

        private static SpinMode[] $values() {
            return new SpinMode[]{Server, Client};
        }

        public static SpinMode[] values() {
            return (SpinMode[])$VALUES.clone();
        }

        public static SpinMode valueOf(String string) {
            return Enum.valueOf(SpinMode.class, string);
        }
    }
}

