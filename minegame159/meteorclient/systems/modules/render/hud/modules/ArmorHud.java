/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.render.RenderUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ArmorHud
extends HudElement {
    private final /* synthetic */ Setting<Durability> durability;
    private final /* synthetic */ Setting<Orientation> orientation;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Double> scale;
    private final /* synthetic */ Setting<Boolean> flipOrder;

    public ArmorHud(HUD lllllllllllllllllIllIIIIlllIlIlI) {
        super(lllllllllllllllllIllIIIIlllIlIlI, "armor", "Displays information about your armor.");
        ArmorHud lllllllllllllllllIllIIIIlllIllll;
        lllllllllllllllllIllIIIIlllIllll.sgGeneral = lllllllllllllllllIllIIIIlllIllll.settings.getDefaultGroup();
        lllllllllllllllllIllIIIIlllIllll.flipOrder = lllllllllllllllllIllIIIIlllIllll.sgGeneral.add(new BoolSetting.Builder().name("flip-order").description("Flips the order of armor items.").defaultValue(true).build());
        lllllllllllllllllIllIIIIlllIllll.orientation = lllllllllllllllllIllIIIIlllIllll.sgGeneral.add(new EnumSetting.Builder().name("orientation").description("How to display armor.").defaultValue(Orientation.Horizontal).build());
        lllllllllllllllllIllIIIIlllIllll.durability = lllllllllllllllllIllIIIIlllIllll.sgGeneral.add(new EnumSetting.Builder().name("durability").description("How to display armor durability.").defaultValue(Durability.Default).build());
        lllllllllllllllllIllIIIIlllIllll.scale = lllllllllllllllllIllIIIIlllIllll.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of armor.").defaultValue(2.0).min(1.0).sliderMin(1.0).sliderMax(5.0).build());
    }

    private ItemStack getItem(int lllllllllllllllllIllIIIIlIllllII) {
        ArmorHud lllllllllllllllllIllIIIIlIllllll;
        if (lllllllllllllllllIllIIIIlIllllll.isInEditor()) {
            switch (lllllllllllllllllIllIIIIlIllllII) {
                default: {
                    return Items.NETHERITE_BOOTS.getDefaultStack();
                }
                case 1: {
                    return Items.NETHERITE_LEGGINGS.getDefaultStack();
                }
                case 2: {
                    return Items.NETHERITE_CHESTPLATE.getDefaultStack();
                }
                case 3: 
            }
            return Items.NETHERITE_HELMET.getDefaultStack();
        }
        return lllllllllllllllllIllIIIIlIllllll.mc.player.inventory.getArmorStack(lllllllllllllllllIllIIIIlIllllII);
    }

    @Override
    public void update(HudRenderer lllllllllllllllllIllIIIIlllIIllI) {
        ArmorHud lllllllllllllllllIllIIIIlllIIlll;
        switch (lllllllllllllllllIllIIIIlllIIlll.orientation.get()) {
            case Horizontal: {
                lllllllllllllllllIllIIIIlllIIlll.box.setSize(16.0 * lllllllllllllllllIllIIIIlllIIlll.scale.get() * 4.0 + 8.0, 16.0 * lllllllllllllllllIllIIIIlllIIlll.scale.get());
                break;
            }
            case Vertical: {
                lllllllllllllllllIllIIIIlllIIlll.box.setSize(16.0 * lllllllllllllllllIllIIIIlllIIlll.scale.get(), 16.0 * lllllllllllllllllIllIIIIlllIIlll.scale.get() * 4.0 + 8.0);
            }
        }
    }

    @Override
    public void render(HudRenderer lllllllllllllllllIllIIIIllIIlIll) {
        ArmorHud lllllllllllllllllIllIIIIllIlIIIl;
        double lllllllllllllllllIllIIIIllIIllll = lllllllllllllllllIllIIIIllIlIIIl.box.getX();
        double lllllllllllllllllIllIIIIllIIlllI = lllllllllllllllllIllIIIIllIlIIIl.box.getY();
        int lllllllllllllllllIllIIIIllIIllIl = lllllllllllllllllIllIIIIllIlIIIl.flipOrder.get() != false ? 3 : 0;
        for (int lllllllllllllllllIllIIIIllIlIIlI = 0; lllllllllllllllllIllIIIIllIlIIlI < 4; ++lllllllllllllllllIllIIIIllIlIIlI) {
            double lllllllllllllllllIllIIIIllIlIIll;
            double lllllllllllllllllIllIIIIllIlIlII;
            ItemStack lllllllllllllllllIllIIIIllIlIlIl = lllllllllllllllllIllIIIIllIlIIIl.getItem(lllllllllllllllllIllIIIIllIIllIl);
            RenderSystem.pushMatrix();
            RenderSystem.scaled((double)lllllllllllllllllIllIIIIllIlIIIl.scale.get(), (double)lllllllllllllllllIllIIIIllIlIIIl.scale.get(), (double)1.0);
            if (lllllllllllllllllIllIIIIllIlIIIl.orientation.get() == Orientation.Vertical) {
                double lllllllllllllllllIllIIIIllIllIIl = lllllllllllllllllIllIIIIllIIllll / lllllllllllllllllIllIIIIllIlIIIl.scale.get();
                double lllllllllllllllllIllIIIIllIllIII = lllllllllllllllllIllIIIIllIIlllI / lllllllllllllllllIllIIIIllIlIIIl.scale.get() + (double)(lllllllllllllllllIllIIIIllIlIIlI * 18);
            } else {
                lllllllllllllllllIllIIIIllIlIlII = lllllllllllllllllIllIIIIllIIllll / lllllllllllllllllIllIIIIllIlIIIl.scale.get() + (double)(lllllllllllllllllIllIIIIllIlIIlI * 18);
                lllllllllllllllllIllIIIIllIlIIll = lllllllllllllllllIllIIIIllIIlllI / lllllllllllllllllIllIIIIllIlIIIl.scale.get();
            }
            RenderUtils.drawItem(lllllllllllllllllIllIIIIllIlIlIl, (int)lllllllllllllllllIllIIIIllIlIlII, (int)lllllllllllllllllIllIIIIllIlIIll, lllllllllllllllllIllIIIIllIlIlIl.isDamageable() && lllllllllllllllllIllIIIIllIlIIIl.durability.get() == Durability.Default);
            if (lllllllllllllllllIllIIIIllIlIlIl.isDamageable() && !lllllllllllllllllIllIIIIllIlIIIl.isInEditor() && lllllllllllllllllIllIIIIllIlIIIl.durability.get() != Durability.Default && lllllllllllllllllIllIIIIllIlIIIl.durability.get() != Durability.None) {
                String lllllllllllllllllIllIIIIllIlIlll = "err";
                switch (lllllllllllllllllIllIIIIllIlIIIl.durability.get()) {
                    case Numbers: {
                        lllllllllllllllllIllIIIIllIlIlll = Integer.toString(lllllllllllllllllIllIIIIllIlIlIl.getMaxDamage() - lllllllllllllllllIllIIIIllIlIlIl.getDamage());
                        break;
                    }
                    case Percentage: {
                        lllllllllllllllllIllIIIIllIlIlll = Integer.toString(Math.round((float)(lllllllllllllllllIllIIIIllIlIlIl.getMaxDamage() - lllllllllllllllllIllIIIIllIlIlIl.getDamage()) * 100.0f / (float)lllllllllllllllllIllIIIIllIlIlIl.getMaxDamage()));
                    }
                }
                double lllllllllllllllllIllIIIIllIlIllI = lllllllllllllllllIllIIIIllIIlIll.textWidth(lllllllllllllllllIllIIIIllIlIlll);
                if (lllllllllllllllllIllIIIIllIlIIIl.orientation.get() == Orientation.Vertical) {
                    lllllllllllllllllIllIIIIllIlIlII = lllllllllllllllllIllIIIIllIIllll + 8.0 * lllllllllllllllllIllIIIIllIlIIIl.scale.get() - lllllllllllllllllIllIIIIllIlIllI / 2.0;
                    lllllllllllllllllIllIIIIllIlIIll = lllllllllllllllllIllIIIIllIIlllI + (double)(18 * lllllllllllllllllIllIIIIllIlIIlI) * lllllllllllllllllIllIIIIllIlIIIl.scale.get() + (18.0 * lllllllllllllllllIllIIIIllIlIIIl.scale.get() - lllllllllllllllllIllIIIIllIIlIll.textHeight());
                } else {
                    lllllllllllllllllIllIIIIllIlIlII = lllllllllllllllllIllIIIIllIIllll + (double)(18 * lllllllllllllllllIllIIIIllIlIIlI) * lllllllllllllllllIllIIIIllIlIIIl.scale.get() + 8.0 * lllllllllllllllllIllIIIIllIlIIIl.scale.get() - lllllllllllllllllIllIIIIllIlIllI / 2.0;
                    lllllllllllllllllIllIIIIllIlIIll = lllllllllllllllllIllIIIIllIIlllI + (lllllllllllllllllIllIIIIllIlIIIl.box.height - lllllllllllllllllIllIIIIllIIlIll.textHeight());
                }
                lllllllllllllllllIllIIIIllIIlIll.text(lllllllllllllllllIllIIIIllIlIlll, lllllllllllllllllIllIIIIllIlIlII, lllllllllllllllllIllIIIIllIlIIll, lllllllllllllllllIllIIIIllIlIIIl.hud.primaryColor.get());
            }
            RenderSystem.popMatrix();
            if (lllllllllllllllllIllIIIIllIlIIIl.flipOrder.get().booleanValue()) {
                --lllllllllllllllllIllIIIIllIIllIl;
                continue;
            }
            ++lllllllllllllllllIllIIIIllIIllIl;
        }
    }

    public static final class Orientation
    extends Enum<Orientation> {
        public static final /* synthetic */ /* enum */ Orientation Vertical;
        private static final /* synthetic */ Orientation[] $VALUES;
        public static final /* synthetic */ /* enum */ Orientation Horizontal;

        public static Orientation[] values() {
            return (Orientation[])$VALUES.clone();
        }

        public static Orientation valueOf(String lllllllllllllllllllIIlIllIIlIllI) {
            return Enum.valueOf(Orientation.class, lllllllllllllllllllIIlIllIIlIllI);
        }

        private static /* synthetic */ Orientation[] $values() {
            return new Orientation[]{Horizontal, Vertical};
        }

        private Orientation() {
            Orientation lllllllllllllllllllIIlIllIIlIIIl;
        }

        static {
            Horizontal = new Orientation();
            Vertical = new Orientation();
            $VALUES = Orientation.$values();
        }
    }

    public static final class Durability
    extends Enum<Durability> {
        public static final /* synthetic */ /* enum */ Durability None;
        public static final /* synthetic */ /* enum */ Durability Default;
        public static final /* synthetic */ /* enum */ Durability Numbers;
        private static final /* synthetic */ Durability[] $VALUES;
        public static final /* synthetic */ /* enum */ Durability Percentage;

        private Durability() {
            Durability lIlIIIIIIlIlIIl;
        }

        public static Durability[] values() {
            return (Durability[])$VALUES.clone();
        }

        private static /* synthetic */ Durability[] $values() {
            return new Durability[]{None, Default, Numbers, Percentage};
        }

        static {
            None = new Durability();
            Default = new Durability();
            Numbers = new Durability();
            Percentage = new Durability();
            $VALUES = Durability.$values();
        }

        public static Durability valueOf(String lIlIIIIIIlIllIl) {
            return Enum.valueOf(Durability.class, lIlIIIIIIlIllIl);
        }
    }
}

