/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.widgets;

import java.util.List;
import minegame159.meteorclient.gui.widgets.WItem;
import minegame159.meteorclient.gui.widgets.WLabel;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;

public class WItemWithLabel
extends WHorizontalList {
    private final String name;
    private WLabel label;
    private WItem item;
    private ItemStack itemStack;

    public void set(ItemStack ItemStack2) {
        this.itemStack = ItemStack2;
        this.item.itemStack = ItemStack2;
        this.label.set(String.valueOf(new StringBuilder().append(ItemStack2.getName().getString()).append(this.getStringToAppend())));
    }

    public String getLabelText() {
        return this.label == null ? this.name : this.label.get();
    }

    public WItemWithLabel(ItemStack ItemStack2, String string) {
        this.itemStack = ItemStack2;
        this.name = string;
    }

    private String getStringToAppend() {
        List list;
        String string = "";
        if (this.itemStack.getItem() == Items.POTION && (list = PotionUtil.getPotion((ItemStack)this.itemStack).getEffects()).size() > 0) {
            string = String.valueOf(new StringBuilder().append(string).append(" "));
            StatusEffectInstance StatusEffectInstance2 = (StatusEffectInstance)list.get(0);
            if (StatusEffectInstance2.getAmplifier() > 0) {
                string = String.valueOf(new StringBuilder().append(string).append(StatusEffectInstance2.getAmplifier() + 1).append(" "));
            }
            string = String.valueOf(new StringBuilder().append(string).append("(").append(StatusEffectUtil.durationToString((StatusEffectInstance)StatusEffectInstance2, (float)1.0f)).append(")"));
        }
        return string;
    }

    @Override
    public void init() {
        this.item = this.add(this.theme.item(this.itemStack)).widget();
        this.label = this.add(this.theme.label(String.valueOf(new StringBuilder().append(this.name).append(this.getStringToAppend())))).widget();
    }
}

