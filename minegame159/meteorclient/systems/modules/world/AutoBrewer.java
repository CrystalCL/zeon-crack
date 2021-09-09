/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.screen.BrewingStandScreenHandler
 *  net.minecraft.screen.slot.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionUtil
 *  net.minecraft.potion.Potions
 */
package minegame159.meteorclient.systems.modules.world;

import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.PotionSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.MyPotion;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;

public class AutoBrewer
extends Module {
    private /* synthetic */ int ingredientI;
    private /* synthetic */ boolean first;
    private final /* synthetic */ Setting<Modifier> modifier;
    private final /* synthetic */ Setting<MyPotion> potion;
    private /* synthetic */ int timer;
    private final /* synthetic */ SettingGroup sgGeneral;

    private void moveOneItem(BrewingStandScreenHandler llIlIIIlIllIll, int llIlIIIlIllIlI, int llIlIIIlIllIIl) {
        InvUtils.move().fromId(llIlIIIlIllIlI).toId(llIlIIIlIllIIl);
    }

    private boolean checkFuel(BrewingStandScreenHandler llIlIIIllIIIIl) {
        if (llIlIIIllIIIIl.getFuel() == 0) {
            AutoBrewer llIlIIIllIIIlI;
            int llIlIIIllIIlIl = -1;
            for (int llIlIIIllIIllI = 5; llIlIIIllIIllI < llIlIIIllIIIIl.slots.size(); ++llIlIIIllIIllI) {
                if (((Slot)llIlIIIllIIIIl.slots.get(llIlIIIllIIllI)).getStack().getItem() != Items.BLAZE_POWDER) continue;
                llIlIIIllIIlIl = llIlIIIllIIllI;
                break;
            }
            if (llIlIIIllIIlIl == -1) {
                ChatUtils.moduleError(llIlIIIllIIIlI, "You do not have a sufficient amount of blaze powder to use as fuel for the brew... disabling.", new Object[0]);
                llIlIIIllIIIlI.toggle();
                return true;
            }
            llIlIIIllIIIlI.moveOneItem(llIlIIIllIIIIl, llIlIIIllIIlIl, 4);
        }
        return false;
    }

    private boolean insertIngredient(BrewingStandScreenHandler llIlIIIllIlllI, Item llIlIIIllIllIl) {
        AutoBrewer llIlIIIlllIIll;
        int llIlIIIlllIIII = -1;
        for (int llIlIIIlllIlII = 5; llIlIIIlllIlII < llIlIIIllIlllI.slots.size(); ++llIlIIIlllIlII) {
            if (((Slot)llIlIIIllIlllI.slots.get(llIlIIIlllIlII)).getStack().getItem() != llIlIIIllIllIl) continue;
            llIlIIIlllIIII = llIlIIIlllIlII;
            break;
        }
        if (llIlIIIlllIIII == -1) {
            ChatUtils.moduleError(llIlIIIlllIIll, "You do not have any %s left in your inventory... disabling.", llIlIIIllIllIl.getName().getString());
            llIlIIIlllIIll.toggle();
            return true;
        }
        llIlIIIlllIIll.moveOneItem(llIlIIIllIlllI, llIlIIIlllIIII, 3);
        return false;
    }

    private boolean takePotions(BrewingStandScreenHandler llIlIIIIllllIl) {
        for (int llIlIIIlIIIIIl = 0; llIlIIIlIIIIIl < 3; ++llIlIIIlIIIIIl) {
            AutoBrewer llIlIIIIlllllI;
            InvUtils.quickMove().slotId(llIlIIIlIIIIIl);
            if (((Slot)llIlIIIIllllIl.slots.get(llIlIIIlIIIIIl)).getStack().isEmpty()) continue;
            ChatUtils.moduleError(llIlIIIIlllllI, "You do not have a sufficient amount of inventory space... disabling.", new Object[0]);
            llIlIIIIlllllI.toggle();
            return true;
        }
        return false;
    }

    @Override
    public void onActivate() {
        llIlIIlIIlIIll.first = false;
    }

    private boolean insertWaterBottles(BrewingStandScreenHandler llIlIIIlIIlIIl) {
        for (int llIlIIIlIIllIl = 0; llIlIIIlIIllIl < 3; ++llIlIIIlIIllIl) {
            int llIlIIIlIIlllI = -1;
            for (int llIlIIIlIIllll = 5; llIlIIIlIIllll < llIlIIIlIIlIIl.slots.size(); ++llIlIIIlIIllll) {
                Potion llIlIIIlIlIIII;
                if (((Slot)llIlIIIlIIlIIl.slots.get(llIlIIIlIIllll)).getStack().getItem() != Items.POTION || (llIlIIIlIlIIII = PotionUtil.getPotion((ItemStack)((Slot)llIlIIIlIIlIIl.slots.get(llIlIIIlIIllll)).getStack())) != Potions.WATER) continue;
                llIlIIIlIIlllI = llIlIIIlIIllll;
                break;
            }
            if (llIlIIIlIIlllI == -1) {
                AutoBrewer llIlIIIlIIllII;
                ChatUtils.moduleError(llIlIIIlIIllII, "You do not have a sufficient amount of water bottles to complete this brew... disabling.", new Object[0]);
                llIlIIIlIIllII.toggle();
                return true;
            }
            InvUtils.move().fromId(llIlIIIlIIlllI).toId(llIlIIIlIIllIl);
        }
        return false;
    }

    private boolean applyModifier(BrewingStandScreenHandler llIlIIIlllllIl) {
        AutoBrewer llIlIIIllllllI;
        if (llIlIIIllllllI.modifier.get() != Modifier.None) {
            Item llIlIIlIIIIIlI;
            if (llIlIIIllllllI.modifier.get() == Modifier.Splash) {
                Item llIlIIlIIIIlII = Items.GUNPOWDER;
            } else {
                llIlIIlIIIIIlI = Items.DRAGON_BREATH;
            }
            int llIlIIlIIIIIIl = -1;
            for (int llIlIIlIIIIIll = 5; llIlIIlIIIIIll < llIlIIIlllllIl.slots.size(); ++llIlIIlIIIIIll) {
                if (((Slot)llIlIIIlllllIl.slots.get(llIlIIlIIIIIll)).getStack().getItem() != llIlIIlIIIIIlI) continue;
                llIlIIlIIIIIIl = llIlIIlIIIIIll;
                break;
            }
            if (llIlIIlIIIIIIl == -1) {
                ChatUtils.moduleError(llIlIIIllllllI, "You do not have any %s left in your inventory... disabling.", llIlIIlIIIIIlI.getName().getString());
                llIlIIIllllllI.toggle();
                return true;
            }
            llIlIIIllllllI.moveOneItem(llIlIIIlllllIl, llIlIIlIIIIIIl, 3);
        }
        return false;
    }

    public void tick(BrewingStandScreenHandler llIlIIlIIIllII) {
        AutoBrewer llIlIIlIIIlIll;
        ++llIlIIlIIIlIll.timer;
        if (!llIlIIlIIIlIll.first) {
            llIlIIlIIIlIll.first = true;
            llIlIIlIIIlIll.ingredientI = -2;
            llIlIIlIIIlIll.timer = 0;
        }
        if (llIlIIlIIIllII.getBrewTime() != 0 || llIlIIlIIIlIll.timer < 5) {
            return;
        }
        if (llIlIIlIIIlIll.ingredientI == -2) {
            if (llIlIIlIIIlIll.takePotions(llIlIIlIIIllII)) {
                return;
            }
            ++llIlIIlIIIlIll.ingredientI;
            llIlIIlIIIlIll.timer = 0;
        } else if (llIlIIlIIIlIll.ingredientI == -1) {
            if (llIlIIlIIIlIll.insertWaterBottles(llIlIIlIIIllII)) {
                return;
            }
            ++llIlIIlIIIlIll.ingredientI;
            llIlIIlIIIlIll.timer = 0;
        } else if (llIlIIlIIIlIll.ingredientI < llIlIIlIIIlIll.potion.get().ingredients.length) {
            if (llIlIIlIIIlIll.checkFuel(llIlIIlIIIllII)) {
                return;
            }
            if (llIlIIlIIIlIll.insertIngredient(llIlIIlIIIllII, llIlIIlIIIlIll.potion.get().ingredients[llIlIIlIIIlIll.ingredientI])) {
                return;
            }
            ++llIlIIlIIIlIll.ingredientI;
            llIlIIlIIIlIll.timer = 0;
        } else if (llIlIIlIIIlIll.ingredientI == llIlIIlIIIlIll.potion.get().ingredients.length) {
            if (llIlIIlIIIlIll.applyModifier(llIlIIlIIIllII)) {
                return;
            }
            ++llIlIIlIIIlIll.ingredientI;
            llIlIIlIIIlIll.timer = 0;
        } else {
            llIlIIlIIIlIll.ingredientI = -2;
            llIlIIlIIIlIll.timer = 0;
        }
    }

    public void onBrewingStandClose() {
        llIlIIlIIlIIII.first = false;
    }

    public AutoBrewer() {
        super(Categories.World, "auto-brewer", "Automatically brews specified potions.");
        AutoBrewer llIlIIlIIlIlll;
        llIlIIlIIlIlll.sgGeneral = llIlIIlIIlIlll.settings.getDefaultGroup();
        llIlIIlIIlIlll.potion = llIlIIlIIlIlll.sgGeneral.add(new PotionSetting.Builder().name("potion").description("The type of potion to brew.").defaultValue(MyPotion.Strength).build());
        llIlIIlIIlIlll.modifier = llIlIIlIIlIlll.sgGeneral.add(new EnumSetting.Builder().name("modifier").description("The modifier for the specified potion.").defaultValue(Modifier.None).build());
    }

    public static final class Modifier
    extends Enum<Modifier> {
        private static final /* synthetic */ Modifier[] $VALUES;
        public static final /* synthetic */ /* enum */ Modifier Splash;
        public static final /* synthetic */ /* enum */ Modifier Lingering;
        public static final /* synthetic */ /* enum */ Modifier None;

        private static /* synthetic */ Modifier[] $values() {
            return new Modifier[]{None, Splash, Lingering};
        }

        static {
            None = new Modifier();
            Splash = new Modifier();
            Lingering = new Modifier();
            $VALUES = Modifier.$values();
        }

        private Modifier() {
            Modifier lllllllllllllllllIlIlllllIlIIIlI;
        }

        public static Modifier[] values() {
            return (Modifier[])$VALUES.clone();
        }

        public static Modifier valueOf(String lllllllllllllllllIlIlllllIlIllll) {
            return Enum.valueOf(Modifier.class, lllllllllllllllllIlIlllllIlIllll);
        }
    }
}

