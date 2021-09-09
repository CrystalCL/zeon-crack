/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.effect.StatusEffectUtil
 *  net.minecraft.entity.effect.StatusEffectInstance
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.potion.PotionUtil
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
    private /* synthetic */ WItem item;
    private final /* synthetic */ String name;
    private /* synthetic */ ItemStack itemStack;
    private /* synthetic */ WLabel label;

    public String getLabelText() {
        WItemWithLabel lIllIllIIIIIIII;
        return lIllIllIIIIIIII.label == null ? lIllIllIIIIIIII.name : lIllIllIIIIIIII.label.get();
    }

    public WItemWithLabel(ItemStack lIllIllIIIlllII, String lIllIllIIIllIll) {
        WItemWithLabel lIllIllIIIllIlI;
        lIllIllIIIllIlI.itemStack = lIllIllIIIlllII;
        lIllIllIIIllIlI.name = lIllIllIIIllIll;
    }

    private String getStringToAppend() {
        List lIllIllIIIIllll;
        WItemWithLabel lIllIllIIIIllII;
        String lIllIllIIIIllIl = "";
        if (lIllIllIIIIllII.itemStack.getItem() == Items.POTION && (lIllIllIIIIllll = PotionUtil.getPotion((ItemStack)lIllIllIIIIllII.itemStack).getEffects()).size() > 0) {
            lIllIllIIIIllIl = String.valueOf(new StringBuilder().append(lIllIllIIIIllIl).append(" "));
            StatusEffectInstance lIllIllIIIlIIII = (StatusEffectInstance)lIllIllIIIIllll.get(0);
            if (lIllIllIIIlIIII.getAmplifier() > 0) {
                lIllIllIIIIllIl = String.valueOf(new StringBuilder().append(lIllIllIIIIllIl).append(lIllIllIIIlIIII.getAmplifier() + 1).append(" "));
            }
            lIllIllIIIIllIl = String.valueOf(new StringBuilder().append(lIllIllIIIIllIl).append("(").append(StatusEffectUtil.durationToString((StatusEffectInstance)lIllIllIIIlIIII, (float)1.0f)).append(")"));
        }
        return lIllIllIIIIllIl;
    }

    @Override
    public void init() {
        WItemWithLabel lIllIllIIIlIllI;
        lIllIllIIIlIllI.item = lIllIllIIIlIllI.add(lIllIllIIIlIllI.theme.item(lIllIllIIIlIllI.itemStack)).widget();
        lIllIllIIIlIllI.label = lIllIllIIIlIllI.add(lIllIllIIIlIllI.theme.label(String.valueOf(new StringBuilder().append(lIllIllIIIlIllI.name).append(lIllIllIIIlIllI.getStringToAppend())))).widget();
    }

    public void set(ItemStack lIllIllIIIIIlIl) {
        WItemWithLabel lIllIllIIIIIllI;
        lIllIllIIIIIllI.itemStack = lIllIllIIIIIlIl;
        lIllIllIIIIIllI.item.itemStack = lIllIllIIIIIlIl;
        lIllIllIIIIIllI.label.set(String.valueOf(new StringBuilder().append(lIllIllIIIIIlIl.getName().getString()).append(lIllIllIIIIIllI.getStringToAppend())));
    }
}

