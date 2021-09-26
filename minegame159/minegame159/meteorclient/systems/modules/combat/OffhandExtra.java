/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.meteor.MouseButtonEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.AutoTotem;
import minegame159.meteorclient.systems.modules.combat.CrystalAura;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.item.AxeItem;
import net.minecraft.item.EnchantedGoldenAppleItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

public class OffhandExtra
extends Module {
    private final SettingGroup sgExtra;
    static final boolean $assertionsDisabled = !OffhandExtra.class.desiredAssertionStatus();
    private boolean noTotems;
    private final Setting<Boolean> selfToggle;
    private boolean isClicking;
    private final Setting<Boolean> replace;
    private final Setting<Boolean> offhandCrystal;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> offhandCA;
    private final Setting<Boolean> hotBar;
    private final Setting<Integer> health;
    private final Setting<Boolean> asimov;
    private final Setting<Mode> mode;
    private final Setting<Boolean> sword;
    private Mode currentMode;
    private boolean sentMessage;

    public OffhandExtra() {
        super(Categories.Combat, "offhand-extra", "Allows you to use specified items in your offhand. REQUIRES AutoTotem to be on smart mode.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgExtra = this.settings.createGroup("Extras");
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Changes what item that will go into your offhand.").defaultValue(Mode.EGap).onChanged(this::lambda$new$0).build());
        this.replace = this.sgGeneral.add(new BoolSetting.Builder().name("replace").description("Replaces your offhand or waits until your offhand is empty.").defaultValue(true).build());
        this.asimov = this.sgGeneral.add(new BoolSetting.Builder().name("asimov").description("Always holds the item specified in your offhand.").defaultValue(false).build());
        this.health = this.sgGeneral.add(new IntSetting.Builder().name("health").description("The health at which Offhand Extra stops working.").defaultValue(10).min(0).sliderMax(20).build());
        this.selfToggle = this.sgGeneral.add(new BoolSetting.Builder().name("self-toggle").description("Toggles when you run out of the item you chose.").defaultValue(false).build());
        this.hotBar = this.sgGeneral.add(new BoolSetting.Builder().name("search-hotbar").description("Whether to take items out of your hotbar or not.").defaultValue(false).build());
        this.sword = this.sgExtra.add(new BoolSetting.Builder().name("sword-gap").description("Changes the mode to EGap if you are holding a sword in your main hand.").defaultValue(false).build());
        this.offhandCrystal = this.sgExtra.add(new BoolSetting.Builder().name("offhand-crystal-on-gap").description("Changes the mode to Crystal if you are holding an enchanted golden apple in your main hand.").defaultValue(false).build());
        this.offhandCA = this.sgExtra.add(new BoolSetting.Builder().name("offhand-crystal-on-ca").description("Changes the mode to Crystal when Crystal Aura is on.").defaultValue(false).build());
        this.isClicking = false;
        this.sentMessage = false;
        this.noTotems = false;
        this.currentMode = this.mode.get();
    }

    private int findSlot(Item Item2) {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        for (int i = 9; i < this.mc.player.inventory.size(); ++i) {
            if (this.mc.player.inventory.getStack(i).getItem() != Item2) continue;
            return i;
        }
        if (this.hotBar.get().booleanValue()) {
            return InvUtils.findItemWithCount((Item)Item2).slot;
        }
        return -1;
    }

    private void lambda$new$0(Mode mode) {
        this.currentMode = mode;
    }

    @Override
    public void onDeactivate() {
        if (this.mc.world == null || this.mc.player == null) {
            return;
        }
        if (Modules.get().isActive(AutoTotem.class) && this.mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
            InvUtils.FindItemResult findItemResult = InvUtils.findItemWithCount(Items.TOTEM_OF_UNDYING);
            if (findItemResult.slot != -1) {
                InvUtils.move().from(findItemResult.slot).toOffhand();
            }
        }
    }

    private boolean canMove() {
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        return this.mc.player.getMainHandStack().getItem() != Items.BOW && this.mc.player.getMainHandStack().getItem() != Items.TRIDENT && this.mc.player.getMainHandStack().getItem() != Items.CROSSBOW && !this.mc.player.getMainHandStack().getItem().isFood();
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent mouseButtonEvent) {
        if (mouseButtonEvent.action != KeyAction.Press || mouseButtonEvent.button != 1) {
            return;
        }
        if (this.mc.currentScreen != null) {
            return;
        }
        if (Modules.get().get(AutoTotem.class).getLocked() || !this.canMove()) {
            return;
        }
        if (this.mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING || this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() > (float)this.health.get().intValue() && this.mc.player.getOffHandStack().getItem() != this.getItem() && !(this.mc.currentScreen instanceof HandledScreen)) {
            if (this.mc.player.getMainHandStack().getItem() instanceof SwordItem && this.sword.get().booleanValue()) {
                this.currentMode = Mode.EGap;
            } else if (this.mc.player.getMainHandStack().getItem() instanceof EnchantedGoldenAppleItem && this.offhandCrystal.get().booleanValue()) {
                this.currentMode = Mode.Crystal;
            } else if (Modules.get().isActive(CrystalAura.class) && this.offhandCA.get().booleanValue()) {
                this.currentMode = Mode.Crystal;
            }
            if (this.mc.player.getOffHandStack().getItem() == this.getItem()) {
                return;
            }
            this.isClicking = true;
            Item Item2 = this.getItem();
            int n = this.findSlot(Item2);
            if (n == -1 && this.mc.player.getOffHandStack().getItem() != this.getItem()) {
                if (!this.sentMessage) {
                    ChatUtils.moduleWarning(this, String.valueOf(new StringBuilder().append("Chosen item not found.").append(this.selfToggle.get() != false ? " Disabling." : "")), new Object[0]);
                    this.sentMessage = true;
                }
                if (this.selfToggle.get().booleanValue()) {
                    this.toggle();
                }
                return;
            }
            if (this.mc.player.getOffHandStack().getItem() != Item2 && this.mc.player.getMainHandStack().getItem() != Item2 && this.replace.get().booleanValue()) {
                InvUtils.move().from(n).toOffhand();
                this.sentMessage = false;
            }
            this.currentMode = this.mode.get();
        }
    }

    @Override
    public void onActivate() {
        this.currentMode = this.mode.get();
    }

    private Item getItem() {
        Item Item2 = Items.TOTEM_OF_UNDYING;
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$combat$OffhandExtra$Mode[this.currentMode.ordinal()]) {
            case 1: {
                Item2 = Items.ENCHANTED_GOLDEN_APPLE;
                break;
            }
            case 2: {
                Item2 = Items.EXPERIENCE_BOTTLE;
                break;
            }
            case 3: {
                Item2 = Items.GOLDEN_APPLE;
                break;
            }
            case 4: {
                Item2 = Items.END_CRYSTAL;
                break;
            }
            case 5: {
                Item2 = Items.SHIELD;
            }
        }
        return Item2;
    }

    @Override
    public String getInfoString() {
        return this.mode.get().name();
    }

    public void setTotems(boolean bl) {
        this.noTotems = bl;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        int n;
        if (!$assertionsDisabled && this.mc.player == null) {
            throw new AssertionError();
        }
        this.currentMode = this.mode.get();
        if (!(this.mc.currentScreen == null || (this.mc.currentScreen instanceof InventoryScreen || this.mc.currentScreen instanceof WidgetScreen) && this.asimov.get().booleanValue())) {
            return;
        }
        if (!this.mc.player.isUsingItem()) {
            this.isClicking = false;
        }
        if (Modules.get().get(AutoTotem.class).getLocked()) {
            return;
        }
        if ((this.mc.player.getMainHandStack().getItem() instanceof SwordItem || this.mc.player.getMainHandStack().getItem() instanceof AxeItem) && this.sword.get().booleanValue()) {
            this.currentMode = Mode.EGap;
        } else if (this.mc.player.getMainHandStack().getItem() instanceof EnchantedGoldenAppleItem && this.offhandCrystal.get().booleanValue()) {
            this.currentMode = Mode.Crystal;
        } else if (Modules.get().isActive(CrystalAura.class) && this.offhandCA.get().booleanValue()) {
            this.currentMode = Mode.Crystal;
        }
        if ((this.asimov.get().booleanValue() || this.noTotems) && this.mc.player.getOffHandStack().getItem() != this.getItem()) {
            int n2 = this.findSlot(this.getItem());
            if (n2 == -1 && this.mc.player.getOffHandStack().getItem() != this.getItem()) {
                if (this.currentMode != this.mode.get()) {
                    this.currentMode = this.mode.get();
                    if (this.mc.player.getOffHandStack().getItem() != this.getItem() && (n2 = this.findSlot(this.getItem())) != -1) {
                        InvUtils.move().from(n2).toOffhand();
                        return;
                    }
                }
                if (!this.sentMessage) {
                    ChatUtils.moduleWarning(this, String.valueOf(new StringBuilder().append("Chosen item not found.").append(this.selfToggle.get() != false ? " Disabling." : "")), new Object[0]);
                    this.sentMessage = true;
                }
                if (this.selfToggle.get().booleanValue()) {
                    this.toggle();
                }
                return;
            }
            if (this.mc.player.getOffHandStack().getItem() != this.getItem() && this.replace.get().booleanValue()) {
                InvUtils.move().from(n2).toOffhand();
                this.sentMessage = false;
            }
        } else if (!this.asimov.get().booleanValue() && !this.isClicking && this.mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING && (n = this.findSlot(Items.TOTEM_OF_UNDYING)) != -1) {
            InvUtils.move().from(n).toOffhand();
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Crystal;
        public static final /* enum */ Mode EXP;
        public static final /* enum */ Mode Shield;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode EGap;
        public static final /* enum */ Mode Gap;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        private static Mode[] $values() {
            return new Mode[]{EGap, Gap, EXP, Crystal, Shield};
        }

        static {
            EGap = new Mode();
            Gap = new Mode();
            EXP = new Mode();
            Crystal = new Mode();
            Shield = new Mode();
            $VALUES = Mode.$values();
        }
    }
}

