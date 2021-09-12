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
import net.minecraft.class_1708;
import net.minecraft.class_1735;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1842;
import net.minecraft.class_1844;
import net.minecraft.class_1847;

public class AutoBrewer
extends Module {
    private final Setting<MyPotion> potion;
    private boolean first;
    private int ingredientI;
    private int timer;
    private final SettingGroup sgGeneral;
    private final Setting<Modifier> modifier;

    private boolean takePotions(class_1708 class_17082) {
        for (int i = 0; i < 3; ++i) {
            InvUtils.quickMove().slotId(i);
            if (((class_1735)class_17082.field_7761.get(i)).method_7677().method_7960()) continue;
            ChatUtils.moduleError(this, "You do not have a sufficient amount of inventory space... disabling.", new Object[0]);
            this.toggle();
            return true;
        }
        return false;
    }

    private boolean insertIngredient(class_1708 class_17082, class_1792 class_17922) {
        int n = -1;
        for (int i = 5; i < class_17082.field_7761.size(); ++i) {
            if (((class_1735)class_17082.field_7761.get(i)).method_7677().method_7909() != class_17922) continue;
            n = i;
            break;
        }
        if (n == -1) {
            ChatUtils.moduleError(this, "You do not have any %s left in your inventory... disabling.", class_17922.method_7848().getString());
            this.toggle();
            return true;
        }
        this.moveOneItem(class_17082, n, 3);
        return false;
    }

    private boolean applyModifier(class_1708 class_17082) {
        if (this.modifier.get() != Modifier.None) {
            class_1792 class_17922 = this.modifier.get() == Modifier.Splash ? class_1802.field_8054 : class_1802.field_8613;
            int n = -1;
            for (int i = 5; i < class_17082.field_7761.size(); ++i) {
                if (((class_1735)class_17082.field_7761.get(i)).method_7677().method_7909() != class_17922) continue;
                n = i;
                break;
            }
            if (n == -1) {
                ChatUtils.moduleError(this, "You do not have any %s left in your inventory... disabling.", class_17922.method_7848().getString());
                this.toggle();
                return true;
            }
            this.moveOneItem(class_17082, n, 3);
        }
        return false;
    }

    private boolean insertWaterBottles(class_1708 class_17082) {
        for (int i = 0; i < 3; ++i) {
            int n = -1;
            for (int j = 5; j < class_17082.field_7761.size(); ++j) {
                class_1842 class_18422;
                if (((class_1735)class_17082.field_7761.get(j)).method_7677().method_7909() != class_1802.field_8574 || (class_18422 = class_1844.method_8063((class_1799)((class_1735)class_17082.field_7761.get(j)).method_7677())) != class_1847.field_8991) continue;
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

    private boolean checkFuel(class_1708 class_17082) {
        if (class_17082.method_17377() == 0) {
            int n = -1;
            for (int i = 5; i < class_17082.field_7761.size(); ++i) {
                if (((class_1735)class_17082.field_7761.get(i)).method_7677().method_7909() != class_1802.field_8183) continue;
                n = i;
                break;
            }
            if (n == -1) {
                ChatUtils.moduleError(this, "You do not have a sufficient amount of blaze powder to use as fuel for the brew... disabling.", new Object[0]);
                this.toggle();
                return true;
            }
            this.moveOneItem(class_17082, n, 4);
        }
        return false;
    }

    public void onBrewingStandClose() {
        this.first = false;
    }

    public void tick(class_1708 class_17082) {
        ++this.timer;
        if (!this.first) {
            this.first = true;
            this.ingredientI = -2;
            this.timer = 0;
        }
        if (class_17082.method_17378() != 0 || this.timer < 5) {
            return;
        }
        if (this.ingredientI == -2) {
            if (this.takePotions(class_17082)) {
                return;
            }
            ++this.ingredientI;
            this.timer = 0;
        } else if (this.ingredientI == -1) {
            if (this.insertWaterBottles(class_17082)) {
                return;
            }
            ++this.ingredientI;
            this.timer = 0;
        } else if (this.ingredientI < this.potion.get().ingredients.length) {
            if (this.checkFuel(class_17082)) {
                return;
            }
            if (this.insertIngredient(class_17082, this.potion.get().ingredients[this.ingredientI])) {
                return;
            }
            ++this.ingredientI;
            this.timer = 0;
        } else if (this.ingredientI == this.potion.get().ingredients.length) {
            if (this.applyModifier(class_17082)) {
                return;
            }
            ++this.ingredientI;
            this.timer = 0;
        } else {
            this.ingredientI = -2;
            this.timer = 0;
        }
    }

    private void moveOneItem(class_1708 class_17082, int n, int n2) {
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

