/*
 * Decompiled with CFR 0.151.
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
    private final Setting<MyPotion> potion;
    private boolean first;
    private int ingredientI;
    private int timer;
    private final SettingGroup sgGeneral;
    private final Setting<Modifier> modifier;

    private boolean takePotions(BrewingStandScreenHandler BrewingStandScreenHandler2) {
        for (int i = 0; i < 3; ++i) {
            InvUtils.quickMove().slotId(i);
            if (((Slot)BrewingStandScreenHandler2.slots.get(i)).getStack().isEmpty()) continue;
            ChatUtils.moduleError(this, "You do not have a sufficient amount of inventory space... disabling.", new Object[0]);
            this.toggle();
            return true;
        }
        return false;
    }

    private boolean insertIngredient(BrewingStandScreenHandler BrewingStandScreenHandler2, Item Item2) {
        int n = -1;
        for (int i = 5; i < BrewingStandScreenHandler2.slots.size(); ++i) {
            if (((Slot)BrewingStandScreenHandler2.slots.get(i)).getStack().getItem() != Item2) continue;
            n = i;
            break;
        }
        if (n == -1) {
            ChatUtils.moduleError(this, "You do not have any %s left in your inventory... disabling.", Item2.getName().getString());
            this.toggle();
            return true;
        }
        this.moveOneItem(BrewingStandScreenHandler2, n, 3);
        return false;
    }

    private boolean applyModifier(BrewingStandScreenHandler BrewingStandScreenHandler2) {
        if (this.modifier.get() != Modifier.None) {
            Item Item2 = this.modifier.get() == Modifier.Splash ? Items.GUNPOWDER : Items.DRAGON_BREATH;
            int n = -1;
            for (int i = 5; i < BrewingStandScreenHandler2.slots.size(); ++i) {
                if (((Slot)BrewingStandScreenHandler2.slots.get(i)).getStack().getItem() != Item2) continue;
                n = i;
                break;
            }
            if (n == -1) {
                ChatUtils.moduleError(this, "You do not have any %s left in your inventory... disabling.", Item2.getName().getString());
                this.toggle();
                return true;
            }
            this.moveOneItem(BrewingStandScreenHandler2, n, 3);
        }
        return false;
    }

    private boolean insertWaterBottles(BrewingStandScreenHandler BrewingStandScreenHandler2) {
        for (int i = 0; i < 3; ++i) {
            int n = -1;
            for (int j = 5; j < BrewingStandScreenHandler2.slots.size(); ++j) {
                Potion Potion2;
                if (((Slot)BrewingStandScreenHandler2.slots.get(j)).getStack().getItem() != Items.POTION || (Potion2 = PotionUtil.getPotion((ItemStack)((Slot)BrewingStandScreenHandler2.slots.get(j)).getStack())) != Potions.WATER) continue;
                n = j;
                break;
            }
            if (n == -1) {
                ChatUtils.moduleError(this, "You do not have a sufficient amount of water bottles to complete this brew... disabling.", new Object[0]);
                this.toggle();
                return true;
            }
            InvUtils.move().fromId(n).toId(i);
            if (2 > -1) continue;
            return false;
        }
        return false;
    }

    private boolean checkFuel(BrewingStandScreenHandler BrewingStandScreenHandler2) {
        if (BrewingStandScreenHandler2.getFuel() == 0) {
            int n = -1;
            for (int i = 5; i < BrewingStandScreenHandler2.slots.size(); ++i) {
                if (((Slot)BrewingStandScreenHandler2.slots.get(i)).getStack().getItem() != Items.BLAZE_POWDER) continue;
                n = i;
                break;
            }
            if (n == -1) {
                ChatUtils.moduleError(this, "You do not have a sufficient amount of blaze powder to use as fuel for the brew... disabling.", new Object[0]);
                this.toggle();
                return true;
            }
            this.moveOneItem(BrewingStandScreenHandler2, n, 4);
        }
        return false;
    }

    public void onBrewingStandClose() {
        this.first = false;
    }

    public void tick(BrewingStandScreenHandler BrewingStandScreenHandler2) {
        ++this.timer;
        if (!this.first) {
            this.first = true;
            this.ingredientI = -2;
            this.timer = 0;
        }
        if (BrewingStandScreenHandler2.getBrewTime() != 0 || this.timer < 5) {
            return;
        }
        if (this.ingredientI == -2) {
            if (this.takePotions(BrewingStandScreenHandler2)) {
                return;
            }
            ++this.ingredientI;
            this.timer = 0;
        } else if (this.ingredientI == -1) {
            if (this.insertWaterBottles(BrewingStandScreenHandler2)) {
                return;
            }
            ++this.ingredientI;
            this.timer = 0;
        } else if (this.ingredientI < this.potion.get().ingredients.length) {
            if (this.checkFuel(BrewingStandScreenHandler2)) {
                return;
            }
            if (this.insertIngredient(BrewingStandScreenHandler2, this.potion.get().ingredients[this.ingredientI])) {
                return;
            }
            ++this.ingredientI;
            this.timer = 0;
        } else if (this.ingredientI == this.potion.get().ingredients.length) {
            if (this.applyModifier(BrewingStandScreenHandler2)) {
                return;
            }
            ++this.ingredientI;
            this.timer = 0;
        } else {
            this.ingredientI = -2;
            this.timer = 0;
        }
    }

    private void moveOneItem(BrewingStandScreenHandler BrewingStandScreenHandler2, int n, int n2) {
        InvUtils.move().fromId(n).toId(n2);
    }

    public AutoBrewer() {
        super(Categories.World, "auto-brewer", "Automatically brews specified potions.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.potion = this.sgGeneral.add(new PotionSetting.Builder().name("potion").description("The type of potion to brew.").defaultValue(MyPotion.Strength).build());
        this.modifier = this.sgGeneral.add(new EnumSetting.Builder().name("modifier").description("The modifier for the specified potion.").defaultValue(Modifier.None).build());
    }

    @Override
    public void onActivate() {
        this.first = false;
    }

    public static final class Modifier
    extends Enum<Modifier> {
        public static final /* enum */ Modifier None = new Modifier();
        public static final /* enum */ Modifier Splash = new Modifier();
        public static final /* enum */ Modifier Lingering = new Modifier();
        private static final Modifier[] $VALUES = Modifier.$values();

        public static Modifier valueOf(String string) {
            return Enum.valueOf(Modifier.class, string);
        }

        private static Modifier[] $values() {
            return new Modifier[]{None, Splash, Lingering};
        }

        public static Modifier[] values() {
            return (Modifier[])$VALUES.clone();
        }
    }
}

