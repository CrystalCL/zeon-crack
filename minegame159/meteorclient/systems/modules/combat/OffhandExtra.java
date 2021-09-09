/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.AxeItem
 *  net.minecraft.item.EnchantedGoldenAppleItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.item.SwordItem
 *  net.minecraft.client.gui.screen.ingame.HandledScreen
 *  net.minecraft.client.gui.screen.ingame.InventoryScreen
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
    private final /* synthetic */ Setting<Boolean> hotBar;
    private final /* synthetic */ Setting<Boolean> offhandCA;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> offhandCrystal;
    private final /* synthetic */ SettingGroup sgExtra;
    private final /* synthetic */ Setting<Boolean> sword;
    private final /* synthetic */ Setting<Boolean> asimov;
    private final /* synthetic */ Setting<Integer> health;
    private /* synthetic */ boolean sentMessage;
    private /* synthetic */ boolean noTotems;
    private final /* synthetic */ Setting<Boolean> selfToggle;
    private /* synthetic */ Mode currentMode;
    private /* synthetic */ boolean isClicking;
    private final /* synthetic */ Setting<Mode> mode;
    private final /* synthetic */ Setting<Boolean> replace;

    public void setTotems(boolean llllllllllllllllIllIlllIlIllIIII) {
        llllllllllllllllIllIlllIlIllIIIl.noTotems = llllllllllllllllIllIlllIlIllIIII;
    }

    @Override
    public void onActivate() {
        OffhandExtra llllllllllllllllIllIlllIllIlIllI;
        llllllllllllllllIllIlllIllIlIllI.currentMode = llllllllllllllllIllIlllIllIlIllI.mode.get();
    }

    @Override
    public void onDeactivate() {
        OffhandExtra llllllllllllllllIllIlllIllIlIIlI;
        if (llllllllllllllllIllIlllIllIlIIlI.mc.world == null || llllllllllllllllIllIlllIllIlIIlI.mc.player == null) {
            return;
        }
        if (Modules.get().isActive(AutoTotem.class) && llllllllllllllllIllIlllIllIlIIlI.mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
            InvUtils.FindItemResult llllllllllllllllIllIlllIllIlIIll = InvUtils.findItemWithCount(Items.TOTEM_OF_UNDYING);
            if (llllllllllllllllIllIlllIllIlIIll.slot != -1) {
                InvUtils.move().from(llllllllllllllllIllIlllIllIlIIll.slot).toOffhand();
            }
        }
    }

    @Override
    public String getInfoString() {
        OffhandExtra llllllllllllllllIllIlllIlIlIIIIl;
        return llllllllllllllllIllIlllIlIlIIIIl.mode.get().name();
    }

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllIllIlllIllIIlIlI) {
        int llllllllllllllllIllIlllIllIIllII;
        OffhandExtra llllllllllllllllIllIlllIllIIlIIl;
        assert (llllllllllllllllIllIlllIllIIlIIl.mc.player != null);
        llllllllllllllllIllIlllIllIIlIIl.currentMode = llllllllllllllllIllIlllIllIIlIIl.mode.get();
        if (!(llllllllllllllllIllIlllIllIIlIIl.mc.currentScreen == null || (llllllllllllllllIllIlllIllIIlIIl.mc.currentScreen instanceof InventoryScreen || llllllllllllllllIllIlllIllIIlIIl.mc.currentScreen instanceof WidgetScreen) && llllllllllllllllIllIlllIllIIlIIl.asimov.get().booleanValue())) {
            return;
        }
        if (!llllllllllllllllIllIlllIllIIlIIl.mc.player.isUsingItem()) {
            llllllllllllllllIllIlllIllIIlIIl.isClicking = false;
        }
        if (Modules.get().get(AutoTotem.class).getLocked()) {
            return;
        }
        if ((llllllllllllllllIllIlllIllIIlIIl.mc.player.getMainHandStack().getItem() instanceof SwordItem || llllllllllllllllIllIlllIllIIlIIl.mc.player.getMainHandStack().getItem() instanceof AxeItem) && llllllllllllllllIllIlllIllIIlIIl.sword.get().booleanValue()) {
            llllllllllllllllIllIlllIllIIlIIl.currentMode = Mode.EGap;
        } else if (llllllllllllllllIllIlllIllIIlIIl.mc.player.getMainHandStack().getItem() instanceof EnchantedGoldenAppleItem && llllllllllllllllIllIlllIllIIlIIl.offhandCrystal.get().booleanValue()) {
            llllllllllllllllIllIlllIllIIlIIl.currentMode = Mode.Crystal;
        } else if (Modules.get().isActive(CrystalAura.class) && llllllllllllllllIllIlllIllIIlIIl.offhandCA.get().booleanValue()) {
            llllllllllllllllIllIlllIllIIlIIl.currentMode = Mode.Crystal;
        }
        if ((llllllllllllllllIllIlllIllIIlIIl.asimov.get().booleanValue() || llllllllllllllllIllIlllIllIIlIIl.noTotems) && llllllllllllllllIllIlllIllIIlIIl.mc.player.getOffHandStack().getItem() != llllllllllllllllIllIlllIllIIlIIl.getItem()) {
            int llllllllllllllllIllIlllIllIIllIl = llllllllllllllllIllIlllIllIIlIIl.findSlot(llllllllllllllllIllIlllIllIIlIIl.getItem());
            if (llllllllllllllllIllIlllIllIIllIl == -1 && llllllllllllllllIllIlllIllIIlIIl.mc.player.getOffHandStack().getItem() != llllllllllllllllIllIlllIllIIlIIl.getItem()) {
                if (llllllllllllllllIllIlllIllIIlIIl.currentMode != llllllllllllllllIllIlllIllIIlIIl.mode.get()) {
                    llllllllllllllllIllIlllIllIIlIIl.currentMode = llllllllllllllllIllIlllIllIIlIIl.mode.get();
                    if (llllllllllllllllIllIlllIllIIlIIl.mc.player.getOffHandStack().getItem() != llllllllllllllllIllIlllIllIIlIIl.getItem() && (llllllllllllllllIllIlllIllIIllIl = llllllllllllllllIllIlllIllIIlIIl.findSlot(llllllllllllllllIllIlllIllIIlIIl.getItem())) != -1) {
                        InvUtils.move().from(llllllllllllllllIllIlllIllIIllIl).toOffhand();
                        return;
                    }
                }
                if (!llllllllllllllllIllIlllIllIIlIIl.sentMessage) {
                    ChatUtils.moduleWarning(llllllllllllllllIllIlllIllIIlIIl, String.valueOf(new StringBuilder().append("Chosen item not found.").append(llllllllllllllllIllIlllIllIIlIIl.selfToggle.get() != false ? " Disabling." : "")), new Object[0]);
                    llllllllllllllllIllIlllIllIIlIIl.sentMessage = true;
                }
                if (llllllllllllllllIllIlllIllIIlIIl.selfToggle.get().booleanValue()) {
                    llllllllllllllllIllIlllIllIIlIIl.toggle();
                }
                return;
            }
            if (llllllllllllllllIllIlllIllIIlIIl.mc.player.getOffHandStack().getItem() != llllllllllllllllIllIlllIllIIlIIl.getItem() && llllllllllllllllIllIlllIllIIlIIl.replace.get().booleanValue()) {
                InvUtils.move().from(llllllllllllllllIllIlllIllIIllIl).toOffhand();
                llllllllllllllllIllIlllIllIIlIIl.sentMessage = false;
            }
        } else if (!llllllllllllllllIllIlllIllIIlIIl.asimov.get().booleanValue() && !llllllllllllllllIllIlllIllIIlIIl.isClicking && llllllllllllllllIllIlllIllIIlIIl.mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING && (llllllllllllllllIllIlllIllIIllII = llllllllllllllllIllIlllIllIIlIIl.findSlot(Items.TOTEM_OF_UNDYING)) != -1) {
            InvUtils.move().from(llllllllllllllllIllIlllIllIIllII).toOffhand();
        }
    }

    private Item getItem() {
        OffhandExtra llllllllllllllllIllIlllIlIlllIIl;
        Item llllllllllllllllIllIlllIlIlllIII = Items.TOTEM_OF_UNDYING;
        switch (llllllllllllllllIllIlllIlIlllIIl.currentMode) {
            case EGap: {
                llllllllllllllllIllIlllIlIlllIII = Items.ENCHANTED_GOLDEN_APPLE;
                break;
            }
            case EXP: {
                llllllllllllllllIllIlllIlIlllIII = Items.EXPERIENCE_BOTTLE;
                break;
            }
            case Gap: {
                llllllllllllllllIllIlllIlIlllIII = Items.GOLDEN_APPLE;
                break;
            }
            case Crystal: {
                llllllllllllllllIllIlllIlIlllIII = Items.END_CRYSTAL;
                break;
            }
            case Shield: {
                llllllllllllllllIllIlllIlIlllIII = Items.SHIELD;
            }
        }
        return llllllllllllllllIllIlllIlIlllIII;
    }

    private boolean canMove() {
        OffhandExtra llllllllllllllllIllIlllIlIlIlllI;
        assert (llllllllllllllllIllIlllIlIlIlllI.mc.player != null);
        return llllllllllllllllIllIlllIlIlIlllI.mc.player.getMainHandStack().getItem() != Items.BOW && llllllllllllllllIllIlllIlIlIlllI.mc.player.getMainHandStack().getItem() != Items.TRIDENT && llllllllllllllllIllIlllIlIlIlllI.mc.player.getMainHandStack().getItem() != Items.CROSSBOW && !llllllllllllllllIllIlllIlIlIlllI.mc.player.getMainHandStack().getItem().isFood();
    }

    public OffhandExtra() {
        super(Categories.Combat, "offhand-extra", "Allows you to use specified items in your offhand. REQUIRES AutoTotem to be on smart mode.");
        OffhandExtra llllllllllllllllIllIlllIllIllIIl;
        llllllllllllllllIllIlllIllIllIIl.sgGeneral = llllllllllllllllIllIlllIllIllIIl.settings.getDefaultGroup();
        llllllllllllllllIllIlllIllIllIIl.sgExtra = llllllllllllllllIllIlllIllIllIIl.settings.createGroup("Extras");
        llllllllllllllllIllIlllIllIllIIl.mode = llllllllllllllllIllIlllIllIllIIl.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Changes what item that will go into your offhand.").defaultValue(Mode.EGap).onChanged(llllllllllllllllIllIlllIlIIlllIl -> {
            llllllllllllllllIllIlllIlIIlllII.currentMode = llllllllllllllllIllIlllIlIIlllIl;
        }).build());
        llllllllllllllllIllIlllIllIllIIl.replace = llllllllllllllllIllIlllIllIllIIl.sgGeneral.add(new BoolSetting.Builder().name("replace").description("Replaces your offhand or waits until your offhand is empty.").defaultValue(true).build());
        llllllllllllllllIllIlllIllIllIIl.asimov = llllllllllllllllIllIlllIllIllIIl.sgGeneral.add(new BoolSetting.Builder().name("asimov").description("Always holds the item specified in your offhand.").defaultValue(false).build());
        llllllllllllllllIllIlllIllIllIIl.health = llllllllllllllllIllIlllIllIllIIl.sgGeneral.add(new IntSetting.Builder().name("health").description("The health at which Offhand Extra stops working.").defaultValue(10).min(0).sliderMax(20).build());
        llllllllllllllllIllIlllIllIllIIl.selfToggle = llllllllllllllllIllIlllIllIllIIl.sgGeneral.add(new BoolSetting.Builder().name("self-toggle").description("Toggles when you run out of the item you chose.").defaultValue(false).build());
        llllllllllllllllIllIlllIllIllIIl.hotBar = llllllllllllllllIllIlllIllIllIIl.sgGeneral.add(new BoolSetting.Builder().name("search-hotbar").description("Whether to take items out of your hotbar or not.").defaultValue(false).build());
        llllllllllllllllIllIlllIllIllIIl.sword = llllllllllllllllIllIlllIllIllIIl.sgExtra.add(new BoolSetting.Builder().name("sword-gap").description("Changes the mode to EGap if you are holding a sword in your main hand.").defaultValue(false).build());
        llllllllllllllllIllIlllIllIllIIl.offhandCrystal = llllllllllllllllIllIlllIllIllIIl.sgExtra.add(new BoolSetting.Builder().name("offhand-crystal-on-gap").description("Changes the mode to Crystal if you are holding an enchanted golden apple in your main hand.").defaultValue(false).build());
        llllllllllllllllIllIlllIllIllIIl.offhandCA = llllllllllllllllIllIlllIllIllIIl.sgExtra.add(new BoolSetting.Builder().name("offhand-crystal-on-ca").description("Changes the mode to Crystal when Crystal Aura is on.").defaultValue(false).build());
        llllllllllllllllIllIlllIllIllIIl.isClicking = false;
        llllllllllllllllIllIlllIllIllIIl.sentMessage = false;
        llllllllllllllllIllIlllIllIllIIl.noTotems = false;
        llllllllllllllllIllIlllIllIllIIl.currentMode = llllllllllllllllIllIlllIllIllIIl.mode.get();
    }

    private int findSlot(Item llllllllllllllllIllIlllIlIlIIlll) {
        OffhandExtra llllllllllllllllIllIlllIlIlIlIII;
        assert (llllllllllllllllIllIlllIlIlIlIII.mc.player != null);
        for (int llllllllllllllllIllIlllIlIlIlIIl = 9; llllllllllllllllIllIlllIlIlIlIIl < llllllllllllllllIllIlllIlIlIlIII.mc.player.inventory.size(); ++llllllllllllllllIllIlllIlIlIlIIl) {
            if (llllllllllllllllIllIlllIlIlIlIII.mc.player.inventory.getStack(llllllllllllllllIllIlllIlIlIlIIl).getItem() != llllllllllllllllIllIlllIlIlIIlll) continue;
            return llllllllllllllllIllIlllIlIlIlIIl;
        }
        if (llllllllllllllllIllIlllIlIlIlIII.hotBar.get().booleanValue()) {
            return InvUtils.findItemWithCount((Item)llllllllllllllllIllIlllIlIlIIlll).slot;
        }
        return -1;
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent llllllllllllllllIllIlllIlIlllllI) {
        OffhandExtra llllllllllllllllIllIlllIllIIIIIl;
        if (llllllllllllllllIllIlllIlIlllllI.action != KeyAction.Press || llllllllllllllllIllIlllIlIlllllI.button != 1) {
            return;
        }
        if (llllllllllllllllIllIlllIllIIIIIl.mc.currentScreen != null) {
            return;
        }
        if (Modules.get().get(AutoTotem.class).getLocked() || !llllllllllllllllIllIlllIllIIIIIl.canMove()) {
            return;
        }
        if (llllllllllllllllIllIlllIllIIIIIl.mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING || llllllllllllllllIllIlllIllIIIIIl.mc.player.getHealth() + llllllllllllllllIllIlllIllIIIIIl.mc.player.getAbsorptionAmount() > (float)llllllllllllllllIllIlllIllIIIIIl.health.get().intValue() && llllllllllllllllIllIlllIllIIIIIl.mc.player.getOffHandStack().getItem() != llllllllllllllllIllIlllIllIIIIIl.getItem() && !(llllllllllllllllIllIlllIllIIIIIl.mc.currentScreen instanceof HandledScreen)) {
            if (llllllllllllllllIllIlllIllIIIIIl.mc.player.getMainHandStack().getItem() instanceof SwordItem && llllllllllllllllIllIlllIllIIIIIl.sword.get().booleanValue()) {
                llllllllllllllllIllIlllIllIIIIIl.currentMode = Mode.EGap;
            } else if (llllllllllllllllIllIlllIllIIIIIl.mc.player.getMainHandStack().getItem() instanceof EnchantedGoldenAppleItem && llllllllllllllllIllIlllIllIIIIIl.offhandCrystal.get().booleanValue()) {
                llllllllllllllllIllIlllIllIIIIIl.currentMode = Mode.Crystal;
            } else if (Modules.get().isActive(CrystalAura.class) && llllllllllllllllIllIlllIllIIIIIl.offhandCA.get().booleanValue()) {
                llllllllllllllllIllIlllIllIIIIIl.currentMode = Mode.Crystal;
            }
            if (llllllllllllllllIllIlllIllIIIIIl.mc.player.getOffHandStack().getItem() == llllllllllllllllIllIlllIllIIIIIl.getItem()) {
                return;
            }
            llllllllllllllllIllIlllIllIIIIIl.isClicking = true;
            Item llllllllllllllllIllIlllIllIIIIll = llllllllllllllllIllIlllIllIIIIIl.getItem();
            int llllllllllllllllIllIlllIllIIIIlI = llllllllllllllllIllIlllIllIIIIIl.findSlot(llllllllllllllllIllIlllIllIIIIll);
            if (llllllllllllllllIllIlllIllIIIIlI == -1 && llllllllllllllllIllIlllIllIIIIIl.mc.player.getOffHandStack().getItem() != llllllllllllllllIllIlllIllIIIIIl.getItem()) {
                if (!llllllllllllllllIllIlllIllIIIIIl.sentMessage) {
                    ChatUtils.moduleWarning(llllllllllllllllIllIlllIllIIIIIl, String.valueOf(new StringBuilder().append("Chosen item not found.").append(llllllllllllllllIllIlllIllIIIIIl.selfToggle.get() != false ? " Disabling." : "")), new Object[0]);
                    llllllllllllllllIllIlllIllIIIIIl.sentMessage = true;
                }
                if (llllllllllllllllIllIlllIllIIIIIl.selfToggle.get().booleanValue()) {
                    llllllllllllllllIllIlllIllIIIIIl.toggle();
                }
                return;
            }
            if (llllllllllllllllIllIlllIllIIIIIl.mc.player.getOffHandStack().getItem() != llllllllllllllllIllIlllIllIIIIll && llllllllllllllllIllIlllIllIIIIIl.mc.player.getMainHandStack().getItem() != llllllllllllllllIllIlllIllIIIIll && llllllllllllllllIllIlllIllIIIIIl.replace.get().booleanValue()) {
                InvUtils.move().from(llllllllllllllllIllIlllIllIIIIlI).toOffhand();
                llllllllllllllllIllIlllIllIIIIIl.sentMessage = false;
            }
            llllllllllllllllIllIlllIllIIIIIl.currentMode = llllllllllllllllIllIlllIllIIIIIl.mode.get();
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode EGap;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Crystal;
        public static final /* synthetic */ /* enum */ Mode Shield;
        public static final /* synthetic */ /* enum */ Mode Gap;
        public static final /* synthetic */ /* enum */ Mode EXP;

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{EGap, Gap, EXP, Crystal, Shield};
        }

        private Mode() {
            Mode lllllllllllllllllIIIIIIIIIIllllI;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            EGap = new Mode();
            Gap = new Mode();
            EXP = new Mode();
            Crystal = new Mode();
            Shield = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String lllllllllllllllllIIIIIIIIIlIIlII) {
            return Enum.valueOf(Mode.class, lllllllllllllllllIIIIIIIIIlIIlII);
        }
    }
}

