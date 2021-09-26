/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Orientation> orientation;
    private final SettingGroup sgGeneral;
    private final Setting<Double> scale;
    private final Setting<Boolean> flipOrder;
    private final Setting<Durability> durability;

    private ItemStack getItem(int n) {
        if (this.isInEditor()) {
            switch (n) {
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
        return this.mc.player.inventory.getArmorStack(n);
    }

    public ArmorHud(HUD hUD) {
        super(hUD, "armor", "Displays information about your armor.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.flipOrder = this.sgGeneral.add(new BoolSetting.Builder().name("flip-order").description("Flips the order of armor items.").defaultValue(true).build());
        this.orientation = this.sgGeneral.add(new EnumSetting.Builder().name("orientation").description("How to display armor.").defaultValue(Orientation.Horizontal).build());
        this.durability = this.sgGeneral.add(new EnumSetting.Builder().name("durability").description("How to display armor durability.").defaultValue(Durability.Default).build());
        this.scale = this.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of armor.").defaultValue(2.0).min(1.0).sliderMin(1.0).sliderMax(5.0).build());
    }

    @Override
    public void render(HudRenderer hudRenderer) {
        double d = this.box.getX();
        double d2 = this.box.getY();
        int n = this.flipOrder.get() != false ? 3 : 0;
        for (int i = 0; i < 4; ++i) {
            double d3;
            double d4;
            ItemStack ItemStack2 = this.getItem(n);
            RenderSystem.pushMatrix();
            RenderSystem.scaled((double)this.scale.get(), (double)this.scale.get(), (double)1.0);
            if (this.orientation.get() == Orientation.Vertical) {
                d4 = d / this.scale.get();
                d3 = d2 / this.scale.get() + (double)(i * 18);
            } else {
                d4 = d / this.scale.get() + (double)(i * 18);
                d3 = d2 / this.scale.get();
            }
            RenderUtils.drawItem(ItemStack2, (int)d4, (int)d3, ItemStack2.isDamageable() && this.durability.get() == Durability.Default);
            if (ItemStack2.isDamageable() && !this.isInEditor() && this.durability.get() != Durability.Default && this.durability.get() != Durability.None) {
                String string = "err";
                switch (this.durability.get()) {
                    case Numbers: {
                        string = Integer.toString(ItemStack2.getMaxDamage() - ItemStack2.getDamage());
                        break;
                    }
                    case Percentage: {
                        string = Integer.toString(Math.round((float)(ItemStack2.getMaxDamage() - ItemStack2.getDamage()) * 100.0f / (float)ItemStack2.getMaxDamage()));
                    }
                }
                double d5 = hudRenderer.textWidth(string);
                if (this.orientation.get() == Orientation.Vertical) {
                    d4 = d + 8.0 * this.scale.get() - d5 / 2.0;
                    d3 = d2 + (double)(18 * i) * this.scale.get() + (18.0 * this.scale.get() - hudRenderer.textHeight());
                } else {
                    d4 = d + (double)(18 * i) * this.scale.get() + 8.0 * this.scale.get() - d5 / 2.0;
                    d3 = d2 + (this.box.height - hudRenderer.textHeight());
                }
                hudRenderer.text(string, d4, d3, this.hud.primaryColor.get());
            }
            RenderSystem.popMatrix();
            if (this.flipOrder.get().booleanValue()) {
                --n;
                continue;
            }
            ++n;
        }
    }

    @Override
    public void update(HudRenderer hudRenderer) {
        switch (this.orientation.get()) {
            case Horizontal: {
                this.box.setSize(16.0 * this.scale.get() * 4.0 + 8.0, 16.0 * this.scale.get());
                break;
            }
            case Vertical: {
                this.box.setSize(16.0 * this.scale.get(), 16.0 * this.scale.get() * 4.0 + 8.0);
            }
        }
    }

    public static final class Durability
    extends Enum<Durability> {
        public static final /* enum */ Durability None = new Durability();
        private static final Durability[] $VALUES;
        public static final /* enum */ Durability Numbers;
        public static final /* enum */ Durability Default;
        public static final /* enum */ Durability Percentage;

        public static Durability[] values() {
            return (Durability[])$VALUES.clone();
        }

        private static Durability[] $values() {
            return new Durability[]{None, Default, Numbers, Percentage};
        }

        static {
            Default = new Durability();
            Numbers = new Durability();
            Percentage = new Durability();
            $VALUES = Durability.$values();
        }

        public static Durability valueOf(String string) {
            return Enum.valueOf(Durability.class, string);
        }
    }

    public static final class Orientation
    extends Enum<Orientation> {
        public static final /* enum */ Orientation Horizontal = new Orientation();
        private static final Orientation[] $VALUES;
        public static final /* enum */ Orientation Vertical;

        public static Orientation valueOf(String string) {
            return Enum.valueOf(Orientation.class, string);
        }

        static {
            Vertical = new Orientation();
            $VALUES = Orientation.$values();
        }

        private static Orientation[] $values() {
            return new Orientation[]{Horizontal, Vertical};
        }

        public static Orientation[] values() {
            return (Orientation[])$VALUES.clone();
        }
    }
}

