/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
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

public class AntiAFK
extends Module {
    private final /* synthetic */ Setting<Boolean> randomMessage;
    private /* synthetic */ float prevYaw;
    private /* synthetic */ int timer;
    private final /* synthetic */ Setting<SpinMode> spinMode;
    private final /* synthetic */ Setting<Boolean> sendMessages;
    private final /* synthetic */ Random random;
    private final /* synthetic */ Setting<Integer> delay;
    private final /* synthetic */ Setting<Boolean> strafe;
    private final /* synthetic */ Setting<Boolean> disco;
    private final /* synthetic */ Setting<Boolean> click;
    private final /* synthetic */ SettingGroup sgActions;
    private final /* synthetic */ Setting<Double> pitch;
    private final /* synthetic */ Setting<Boolean> jump;
    private final /* synthetic */ List<String> messages;
    private /* synthetic */ int strafeTimer;
    private /* synthetic */ boolean direction;
    private final /* synthetic */ Setting<Boolean> spin;
    private /* synthetic */ int messageI;
    private final /* synthetic */ Setting<Integer> spinSpeed;
    private final /* synthetic */ SettingGroup sgMessages;

    @Override
    public void onDeactivate() {
        AntiAFK llIlIIlIIllIlII;
        if (llIlIIlIIllIlII.strafe.get().booleanValue()) {
            llIlIIlIIllIlII.mc.options.keyLeft.setPressed(false);
            llIlIIlIIllIlII.mc.options.keyRight.setPressed(false);
        }
    }

    @Override
    public Module fromTag(NbtCompound llIlIIIllllIIlI) {
        AntiAFK llIlIIIllllIIIl;
        llIlIIIllllIIIl.messages.clear();
        if (llIlIIIllllIIlI.contains("messages")) {
            NbtList llIlIIIllllIlII = llIlIIIllllIIlI.getList("messages", 8);
            for (NbtElement llIlIIIllllIlIl : llIlIIIllllIlII) {
                llIlIIIllllIIIl.messages.add(llIlIIIllllIlIl.asString());
            }
        } else {
            llIlIIIllllIIIl.messages.add("This is an AntiAFK message. Meteor on Crack!");
        }
        return super.fromTag(llIlIIIllllIIlI);
    }

    @EventHandler
    private void onTick(TickEvent.Pre llIlIIlIIlIllIl) {
        if (Utils.canUpdate()) {
            AntiAFK llIlIIlIIlIllII;
            if (llIlIIlIIlIllII.spin.get().booleanValue()) {
                llIlIIlIIlIllII.prevYaw += (float)llIlIIlIIlIllII.spinSpeed.get().intValue();
                switch (llIlIIlIIlIllII.spinMode.get()) {
                    case Client: {
                        llIlIIlIIlIllII.mc.player.yaw = llIlIIlIIlIllII.prevYaw;
                        break;
                    }
                    case Server: {
                        Rotations.rotate(llIlIIlIIlIllII.prevYaw, llIlIIlIIlIllII.pitch.get(), -15, null);
                    }
                }
            }
            if (llIlIIlIIlIllII.jump.get().booleanValue() && llIlIIlIIlIllII.mc.options.keyJump.isPressed()) {
                llIlIIlIIlIllII.mc.options.keyJump.setPressed(false);
            }
            if (llIlIIlIIlIllII.jump.get().booleanValue() && llIlIIlIIlIllII.mc.options.keySneak.isPressed()) {
                llIlIIlIIlIllII.mc.options.keySneak.setPressed(false);
            } else if (llIlIIlIIlIllII.jump.get().booleanValue() && llIlIIlIIlIllII.random.nextInt(99) + 1 == 50) {
                llIlIIlIIlIllII.mc.options.keyJump.setPressed(true);
            }
            if (llIlIIlIIlIllII.click.get().booleanValue() && llIlIIlIIlIllII.random.nextInt(99) + 1 == 45) {
                llIlIIlIIlIllII.mc.options.keyAttack.setPressed(true);
                Utils.leftClick();
                llIlIIlIIlIllII.mc.options.keyAttack.setPressed(false);
            }
            if (llIlIIlIIlIllII.disco.get().booleanValue() && llIlIIlIIlIllII.random.nextInt(24) + 1 == 15) {
                llIlIIlIIlIllII.mc.options.keySneak.setPressed(true);
            }
            if (llIlIIlIIlIllII.sendMessages.get().booleanValue() && !llIlIIlIIlIllII.messages.isEmpty()) {
                if (llIlIIlIIlIllII.timer <= 0) {
                    int llIlIIlIIlIllll;
                    if (llIlIIlIIlIllII.randomMessage.get().booleanValue()) {
                        int llIlIIlIIllIIII = Utils.random(0, llIlIIlIIlIllII.messages.size());
                    } else {
                        if (llIlIIlIIlIllII.messageI >= llIlIIlIIlIllII.messages.size()) {
                            llIlIIlIIlIllII.messageI = 0;
                        }
                        llIlIIlIIlIllll = llIlIIlIIlIllII.messageI++;
                    }
                    llIlIIlIIlIllII.mc.player.sendChatMessage(llIlIIlIIlIllII.messages.get(llIlIIlIIlIllll));
                    llIlIIlIIlIllII.timer = llIlIIlIIlIllII.delay.get() * 20;
                } else {
                    --llIlIIlIIlIllII.timer;
                }
            }
            if (llIlIIlIIlIllII.strafe.get().booleanValue() && llIlIIlIIlIllII.strafeTimer == 20) {
                llIlIIlIIlIllII.mc.options.keyLeft.setPressed(!llIlIIlIIlIllII.direction);
                llIlIIlIIlIllII.mc.options.keyRight.setPressed(llIlIIlIIlIllII.direction);
                llIlIIlIIlIllII.direction = !llIlIIlIIlIllII.direction;
                llIlIIlIIlIllII.strafeTimer = 0;
            } else {
                ++llIlIIlIIlIllII.strafeTimer;
            }
        }
    }

    private void fillTable(GuiTheme llIlIIlIIIlIIll, WTable llIlIIlIIIlIIlI) {
        AntiAFK llIlIIlIIIlIIII;
        llIlIIlIIIlIIlI.add(llIlIIlIIIlIIll.horizontalSeparator("Message List")).expandX();
        for (int llIlIIlIIIlIlIl = 0; llIlIIlIIIlIlIl < llIlIIlIIIlIIII.messages.size(); ++llIlIIlIIIlIlIl) {
            int llIlIIlIIIllIIl = llIlIIlIIIlIlIl;
            String llIlIIlIIIllIII = llIlIIlIIIlIIII.messages.get(llIlIIlIIIlIlIl);
            WTextBox llIlIIlIIIlIlll = llIlIIlIIIlIIlI.add(llIlIIlIIIlIIll.textBox(llIlIIlIIIllIII)).minWidth(100.0).expandX().widget();
            llIlIIlIIIlIlll.action = () -> {
                AntiAFK llIlIIIllIIllII;
                llIlIIIllIIllII.messages.set(llIlIIlIIIllIIl, llIlIIlIIIlIlll.get());
            };
            WMinus llIlIIlIIIlIllI = llIlIIlIIIlIIlI.add(llIlIIlIIIlIIll.minus()).widget();
            llIlIIlIIIlIllI.action = () -> {
                AntiAFK llIlIIIllIllIlI;
                llIlIIIllIllIlI.messages.remove(llIlIIlIIIllIIl);
                llIlIIlIIIlIIlI.clear();
                llIlIIIllIllIlI.fillTable(llIlIIlIIIlIIll, llIlIIlIIIlIIlI);
            };
            llIlIIlIIIlIIlI.row();
        }
        WPlus llIlIIlIIIlIIIl = llIlIIlIIIlIIlI.add(llIlIIlIIIlIIll.plus()).expandCellX().right().widget();
        llIlIIlIIIlIIIl.action = () -> {
            AntiAFK llIlIIIlllIIlII;
            llIlIIIlllIIlII.messages.add("");
            llIlIIlIIIlIIlI.clear();
            llIlIIIlllIIlII.fillTable(llIlIIlIIIlIIll, llIlIIlIIIlIIlI);
        };
    }

    @Override
    public void onActivate() {
        AntiAFK llIlIIlIIllIllI;
        llIlIIlIIllIllI.prevYaw = llIlIIlIIllIllI.mc.player.yaw;
        llIlIIlIIllIllI.timer = llIlIIlIIllIllI.delay.get() * 20;
    }

    @Override
    public NbtCompound toTag() {
        AntiAFK llIlIIlIIIIIIlI;
        NbtCompound llIlIIlIIIIIIIl = super.toTag();
        llIlIIlIIIIIIlI.messages.removeIf(String::isEmpty);
        NbtList llIlIIlIIIIIIII = new NbtList();
        for (String llIlIIlIIIIIIll : llIlIIlIIIIIIlI.messages) {
            llIlIIlIIIIIIII.add((Object)NbtString.of((String)llIlIIlIIIIIIll));
        }
        llIlIIlIIIIIIIl.put("messages", (NbtElement)llIlIIlIIIIIIII);
        return llIlIIlIIIIIIIl;
    }

    @Override
    public WWidget getWidget(GuiTheme llIlIIlIIlIIIll) {
        AntiAFK llIlIIlIIlIIlII;
        llIlIIlIIlIIlII.messages.removeIf(String::isEmpty);
        WTable llIlIIlIIlIIlIl = llIlIIlIIlIIIll.table();
        llIlIIlIIlIIlII.fillTable(llIlIIlIIlIIIll, llIlIIlIIlIIlIl);
        return llIlIIlIIlIIlIl;
    }

    public AntiAFK() {
        super(Categories.Player, "anti-afk", "Performs different actions to prevent getting kicked for AFK reasons.");
        AntiAFK llIlIIlIIlllIlI;
        llIlIIlIIlllIlI.sgActions = llIlIIlIIlllIlI.settings.createGroup("Actions");
        llIlIIlIIlllIlI.sgMessages = llIlIIlIIlllIlI.settings.createGroup("Messages");
        llIlIIlIIlllIlI.spin = llIlIIlIIlllIlI.sgActions.add(new BoolSetting.Builder().name("spin").description("Spins.").defaultValue(true).build());
        llIlIIlIIlllIlI.spinMode = llIlIIlIIlllIlI.sgActions.add(new EnumSetting.Builder().name("spin-mode").description("The method of rotating.").defaultValue(SpinMode.Server).build());
        llIlIIlIIlllIlI.spinSpeed = llIlIIlIIlllIlI.sgActions.add(new IntSetting.Builder().name("spin-speed").description("The speed to spin you.").defaultValue(7).build());
        llIlIIlIIlllIlI.pitch = llIlIIlIIlllIlI.sgActions.add(new DoubleSetting.Builder().name("pitch").description("The pitch to set in server mode.").defaultValue(-90.0).min(-90.0).max(90.0).sliderMin(-90.0).sliderMax(90.0).build());
        llIlIIlIIlllIlI.jump = llIlIIlIIlllIlI.sgActions.add(new BoolSetting.Builder().name("jump").description("Jumps.").defaultValue(true).build());
        llIlIIlIIlllIlI.click = llIlIIlIIlllIlI.sgActions.add(new BoolSetting.Builder().name("click").description("Clicks.").defaultValue(false).build());
        llIlIIlIIlllIlI.disco = llIlIIlIIlllIlI.sgActions.add(new BoolSetting.Builder().name("disco").description("Sneaks and unsneaks quickly.").defaultValue(false).build());
        llIlIIlIIlllIlI.strafe = llIlIIlIIlllIlI.sgActions.add(new BoolSetting.Builder().name("strafe").description("Strafe right and left").defaultValue(false).onChanged(llIlIIIllIIIlll -> {
            AntiAFK llIlIIIllIIIllI;
            llIlIIIllIIIllI.strafeTimer = 0;
            llIlIIIllIIIllI.direction = false;
            if (llIlIIIllIIIllI.isActive()) {
                llIlIIIllIIIllI.mc.options.keyLeft.setPressed(false);
                llIlIIIllIIIllI.mc.options.keyRight.setPressed(false);
            }
        }).build());
        llIlIIlIIlllIlI.sendMessages = llIlIIlIIlllIlI.sgMessages.add(new BoolSetting.Builder().name("send-messages").description("Sends messages to prevent getting kicked for AFK.").defaultValue(false).build());
        llIlIIlIIlllIlI.delay = llIlIIlIIlllIlI.sgMessages.add(new IntSetting.Builder().name("delay").description("The delay between specified messages in seconds.").defaultValue(2).min(0).sliderMax(20).build());
        llIlIIlIIlllIlI.randomMessage = llIlIIlIIlllIlI.sgMessages.add(new BoolSetting.Builder().name("random").description("Selects a random message from your message list.").defaultValue(false).build());
        llIlIIlIIlllIlI.messages = new ArrayList<String>();
        llIlIIlIIlllIlI.strafeTimer = 0;
        llIlIIlIIlllIlI.direction = false;
        llIlIIlIIlllIlI.random = new Random();
    }

    public static final class SpinMode
    extends Enum<SpinMode> {
        private static final /* synthetic */ SpinMode[] $VALUES;
        public static final /* synthetic */ /* enum */ SpinMode Server;
        public static final /* synthetic */ /* enum */ SpinMode Client;

        private static /* synthetic */ SpinMode[] $values() {
            return new SpinMode[]{Server, Client};
        }

        static {
            Server = new SpinMode();
            Client = new SpinMode();
            $VALUES = SpinMode.$values();
        }

        private SpinMode() {
            SpinMode lllllllllllllllllIIIIlIIIlIlllII;
        }

        public static SpinMode valueOf(String lllllllllllllllllIIIIlIIIllIIIlI) {
            return Enum.valueOf(SpinMode.class, lllllllllllllllllIIIIlIIIllIIIlI);
        }

        public static SpinMode[] values() {
            return (SpinMode[])$VALUES.clone();
        }
    }
}

