/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.FinishUsingItem;
import minegame159.meteorclient.events.entity.player.StoppedUsingItemEvent;
import minegame159.meteorclient.events.meteor.MouseButtonEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1753;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_1937;

public class MiddleClickExtra
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Mode> mode;
    private int preSlot;
    private boolean isUsing;
    private final Setting<Boolean> notify;

    @Override
    public void onDeactivate() {
        this.stopIfUsing();
    }

    @EventHandler
    private void onFinishUsingItem(FinishUsingItem finishUsingItem) {
        this.stopIfUsing();
    }

    public MiddleClickExtra() {
        super(Categories.Player, "middle-click-extra", "Lets you use items when you middle click. Works the same as Middle Click Friend.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Which item to use when you middle click.").defaultValue(Mode.Pearl).build());
        this.notify = this.sgGeneral.add(new BoolSetting.Builder().name("notify").description("Notifies you when you do not have the specified item in your hotbar.").defaultValue(true).build());
    }

    private void stopIfUsing() {
        if (this.isUsing) {
            this.mc.field_1690.field_1904.method_23481(false);
            this.mc.field_1724.field_7514.field_7545 = this.preSlot;
            this.isUsing = false;
        }
    }

    @EventHandler
    private void onStoppedUsingItem(StoppedUsingItemEvent stoppedUsingItemEvent) {
        this.stopIfUsing();
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.isUsing) {
            boolean bl = true;
            if (this.mc.field_1724.method_6047().method_7909() instanceof class_1753) {
                bl = class_1753.method_7722((int)this.mc.field_1724.method_6048()) < 1.0f;
            }
            this.mc.field_1690.field_1904.method_23481(bl);
        }
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent mouseButtonEvent) {
        if (mouseButtonEvent.action != KeyAction.Press || mouseButtonEvent.button != 2) {
            return;
        }
        InvUtils.FindItemResult findItemResult = InvUtils.findItemWithCount(Mode.access$000(this.mode.get()));
        if (findItemResult.slot == -1 || findItemResult.slot > 8) {
            if (this.notify.get().booleanValue()) {
                ChatUtils.moduleWarning(this, "Unable to find specified item.", new Object[0]);
            }
            return;
        }
        this.preSlot = this.mc.field_1724.field_7514.field_7545;
        this.mc.field_1724.field_7514.field_7545 = findItemResult.slot;
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$player$MiddleClickExtra$Type[Mode.access$100(this.mode.get()).ordinal()]) {
            case 1: {
                this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, (class_1937)this.mc.field_1687, class_1268.field_5808);
                this.mc.field_1724.field_7514.field_7545 = this.preSlot;
                break;
            }
            case 2: {
                this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, (class_1937)this.mc.field_1687, class_1268.field_5808);
                break;
            }
            case 3: {
                this.mc.field_1690.field_1904.method_23481(true);
                this.isUsing = true;
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Rod;
        public static final /* enum */ Mode Gap;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Chorus;
        public static final /* enum */ Mode Rocket;
        public static final /* enum */ Mode EGap;
        public static final /* enum */ Mode Bow;
        private final class_1792 item;
        private final Type type;
        public static final /* enum */ Mode Pearl;

        private static Mode[] $values() {
            return new Mode[]{Pearl, Rocket, Rod, Bow, Gap, EGap, Chorus};
        }

        static Type access$100(Mode mode) {
            return mode.type;
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        static class_1792 access$000(Mode mode) {
            return mode.item;
        }

        static {
            Pearl = new Mode(class_1802.field_8634, Type.Immediate);
            Rocket = new Mode(class_1802.field_8639, Type.Immediate);
            Rod = new Mode(class_1802.field_8378, Type.LongerSingleClick);
            Bow = new Mode(class_1802.field_8102, Type.Longer);
            Gap = new Mode(class_1802.field_8463, Type.Longer);
            EGap = new Mode(class_1802.field_8367, Type.Longer);
            Chorus = new Mode(class_1802.field_8233, Type.Longer);
            $VALUES = Mode.$values();
        }

        private Mode(class_1792 class_17922, Type type) {
            this.item = class_17922;
            this.type = type;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }

    private static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type Longer;
        public static final /* enum */ Type Immediate;
        private static final Type[] $VALUES;
        public static final /* enum */ Type LongerSingleClick;

        static {
            Immediate = new Type();
            LongerSingleClick = new Type();
            Longer = new Type();
            $VALUES = Type.$values();
        }

        private static Type[] $values() {
            return new Type[]{Immediate, LongerSingleClick, Longer};
        }

        public static Type valueOf(String string) {
            return Enum.valueOf(Type.class, string);
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }
    }
}

