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
import net.minecraft.util.Hand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;

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
            this.mc.options.keyUse.setPressed(false);
            this.mc.player.inventory.selectedSlot = this.preSlot;
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
            if (this.mc.player.getMainHandStack().getItem() instanceof BowItem) {
                bl = BowItem.getPullProgress((int)this.mc.player.getItemUseTime()) < 1.0f;
            }
            this.mc.options.keyUse.setPressed(bl);
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
        this.preSlot = this.mc.player.inventory.selectedSlot;
        this.mc.player.inventory.selectedSlot = findItemResult.slot;
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$player$MiddleClickExtra$Type[Mode.access$100(this.mode.get()).ordinal()]) {
            case 1: {
                this.mc.interactionManager.interactItem((PlayerEntity)this.mc.player, (World)this.mc.world, Hand.MAIN_HAND);
                this.mc.player.inventory.selectedSlot = this.preSlot;
                break;
            }
            case 2: {
                this.mc.interactionManager.interactItem((PlayerEntity)this.mc.player, (World)this.mc.world, Hand.MAIN_HAND);
                break;
            }
            case 3: {
                this.mc.options.keyUse.setPressed(true);
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
        private final Item item;
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

        static Item access$000(Mode mode) {
            return mode.item;
        }

        static {
            Pearl = new Mode(Items.ENDER_PEARL, Type.Immediate);
            Rocket = new Mode(Items.FIREWORK_ROCKET, Type.Immediate);
            Rod = new Mode(Items.FISHING_ROD, Type.LongerSingleClick);
            Bow = new Mode(Items.BOW, Type.Longer);
            Gap = new Mode(Items.GOLDEN_APPLE, Type.Longer);
            EGap = new Mode(Items.ENCHANTED_GOLDEN_APPLE, Type.Longer);
            Chorus = new Mode(Items.CHORUS_FRUIT, Type.Longer);
            $VALUES = Mode.$values();
        }

        private Mode(Item Item2, Type type) {
            this.item = Item2;
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

