/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.BowItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.world.World
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
    private final /* synthetic */ Setting<Boolean> notify;
    private /* synthetic */ boolean isUsing;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ int preSlot;
    private final /* synthetic */ Setting<Mode> mode;

    @EventHandler
    private void onMouseButton(MouseButtonEvent lllllllllllllllllIIIIIIIlllllllI) {
        MiddleClickExtra lllllllllllllllllIIIIIIIllllllII;
        if (lllllllllllllllllIIIIIIIlllllllI.action != KeyAction.Press || lllllllllllllllllIIIIIIIlllllllI.button != 2) {
            return;
        }
        InvUtils.FindItemResult lllllllllllllllllIIIIIIIllllllIl = InvUtils.findItemWithCount(lllllllllllllllllIIIIIIIllllllII.mode.get().item);
        if (lllllllllllllllllIIIIIIIllllllIl.slot == -1 || lllllllllllllllllIIIIIIIllllllIl.slot > 8) {
            if (lllllllllllllllllIIIIIIIllllllII.notify.get().booleanValue()) {
                ChatUtils.moduleWarning(lllllllllllllllllIIIIIIIllllllII, "Unable to find specified item.", new Object[0]);
            }
            return;
        }
        lllllllllllllllllIIIIIIIllllllII.preSlot = lllllllllllllllllIIIIIIIllllllII.mc.player.inventory.selectedSlot;
        lllllllllllllllllIIIIIIIllllllII.mc.player.inventory.selectedSlot = lllllllllllllllllIIIIIIIllllllIl.slot;
        switch (lllllllllllllllllIIIIIIIllllllII.mode.get().type) {
            case Immediate: {
                lllllllllllllllllIIIIIIIllllllII.mc.interactionManager.interactItem((PlayerEntity)lllllllllllllllllIIIIIIIllllllII.mc.player, (World)lllllllllllllllllIIIIIIIllllllII.mc.world, Hand.MAIN_HAND);
                lllllllllllllllllIIIIIIIllllllII.mc.player.inventory.selectedSlot = lllllllllllllllllIIIIIIIllllllII.preSlot;
                break;
            }
            case LongerSingleClick: {
                lllllllllllllllllIIIIIIIllllllII.mc.interactionManager.interactItem((PlayerEntity)lllllllllllllllllIIIIIIIllllllII.mc.player, (World)lllllllllllllllllIIIIIIIllllllII.mc.world, Hand.MAIN_HAND);
                break;
            }
            case Longer: {
                lllllllllllllllllIIIIIIIllllllII.mc.options.keyUse.setPressed(true);
                lllllllllllllllllIIIIIIIllllllII.isUsing = true;
            }
        }
    }

    @EventHandler
    private void onFinishUsingItem(FinishUsingItem lllllllllllllllllIIIIIIIllllIIII) {
        MiddleClickExtra lllllllllllllllllIIIIIIIlllIllll;
        lllllllllllllllllIIIIIIIlllIllll.stopIfUsing();
    }

    public MiddleClickExtra() {
        super(Categories.Player, "middle-click-extra", "Lets you use items when you middle click. Works the same as Middle Click Friend.");
        MiddleClickExtra lllllllllllllllllIIIIIIlIIIIIllI;
        lllllllllllllllllIIIIIIlIIIIIllI.sgGeneral = lllllllllllllllllIIIIIIlIIIIIllI.settings.getDefaultGroup();
        lllllllllllllllllIIIIIIlIIIIIllI.mode = lllllllllllllllllIIIIIIlIIIIIllI.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Which item to use when you middle click.").defaultValue(Mode.Pearl).build());
        lllllllllllllllllIIIIIIlIIIIIllI.notify = lllllllllllllllllIIIIIIlIIIIIllI.sgGeneral.add(new BoolSetting.Builder().name("notify").description("Notifies you when you do not have the specified item in your hotbar.").defaultValue(true).build());
    }

    private void stopIfUsing() {
        MiddleClickExtra lllllllllllllllllIIIIIIIlllIlIII;
        if (lllllllllllllllllIIIIIIIlllIlIII.isUsing) {
            lllllllllllllllllIIIIIIIlllIlIII.mc.options.keyUse.setPressed(false);
            lllllllllllllllllIIIIIIIlllIlIII.mc.player.inventory.selectedSlot = lllllllllllllllllIIIIIIIlllIlIII.preSlot;
            lllllllllllllllllIIIIIIIlllIlIII.isUsing = false;
        }
    }

    @Override
    public void onDeactivate() {
        MiddleClickExtra lllllllllllllllllIIIIIIlIIIIIIll;
        lllllllllllllllllIIIIIIlIIIIIIll.stopIfUsing();
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIIIIIIIllllIlIl) {
        MiddleClickExtra lllllllllllllllllIIIIIIIllllIlII;
        if (lllllllllllllllllIIIIIIIllllIlII.isUsing) {
            boolean lllllllllllllllllIIIIIIIllllIlll = true;
            if (lllllllllllllllllIIIIIIIllllIlII.mc.player.getMainHandStack().getItem() instanceof BowItem) {
                lllllllllllllllllIIIIIIIllllIlll = BowItem.getPullProgress((int)lllllllllllllllllIIIIIIIllllIlII.mc.player.getItemUseTime()) < 1.0f;
            }
            lllllllllllllllllIIIIIIIllllIlII.mc.options.keyUse.setPressed(lllllllllllllllllIIIIIIIllllIlll);
        }
    }

    @EventHandler
    private void onStoppedUsingItem(StoppedUsingItemEvent lllllllllllllllllIIIIIIIlllIllII) {
        MiddleClickExtra lllllllllllllllllIIIIIIIlllIllIl;
        lllllllllllllllllIIIIIIIlllIllIl.stopIfUsing();
    }

    private static final class Type
    extends Enum<Type> {
        public static final /* synthetic */ /* enum */ Type Immediate;
        public static final /* synthetic */ /* enum */ Type LongerSingleClick;
        public static final /* synthetic */ /* enum */ Type Longer;
        private static final /* synthetic */ Type[] $VALUES;

        static {
            Immediate = new Type();
            LongerSingleClick = new Type();
            Longer = new Type();
            $VALUES = Type.$values();
        }

        private static /* synthetic */ Type[] $values() {
            return new Type[]{Immediate, LongerSingleClick, Longer};
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        private Type() {
            Type llllllllllllllllIlIllIIllIIllIII;
        }

        public static Type valueOf(String llllllllllllllllIlIllIIllIIlllIl) {
            return Enum.valueOf(Type.class, llllllllllllllllIlIllIIllIIlllIl);
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Chorus;
        public static final /* synthetic */ /* enum */ Mode Bow;
        public static final /* synthetic */ /* enum */ Mode Rod;
        public static final /* synthetic */ /* enum */ Mode Pearl;
        public static final /* synthetic */ /* enum */ Mode EGap;
        public static final /* synthetic */ /* enum */ Mode Gap;
        private final /* synthetic */ Item item;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Rocket;
        private final /* synthetic */ Type type;

        private Mode(Item llllllllllllllllIllIllIIlIIllIIl, Type llllllllllllllllIllIllIIlIIllIII) {
            Mode llllllllllllllllIllIllIIlIIlIlll;
            llllllllllllllllIllIllIIlIIlIlll.item = llllllllllllllllIllIllIIlIIllIIl;
            llllllllllllllllIllIllIIlIIlIlll.type = llllllllllllllllIllIllIIlIIllIII;
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

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String llllllllllllllllIllIllIIlIlIIIIl) {
            return Enum.valueOf(Mode.class, llllllllllllllllIllIllIIlIlIIIIl);
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Pearl, Rocket, Rod, Bow, Gap, EGap, Chorus};
        }
    }
}

