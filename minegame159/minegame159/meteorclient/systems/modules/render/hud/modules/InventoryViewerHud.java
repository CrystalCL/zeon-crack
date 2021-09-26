/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.Matrices;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.render.RenderUtils;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ItemConvertible;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class InventoryViewerHud
extends HudElement {
    private static final Identifier TEXTURE_DARK_TRANSPARENT;
    private final ItemStack[] editorInv;
    private static final Identifier TEXTURE_LIGHT;
    private static final Identifier TEXTURE_LIGHT_TRANSPARENT;
    private static final Identifier TEXTURE_DARK;
    private final Setting<Background> background;
    private final SettingGroup sgGeneral;
    private final Setting<SettingColor> flatColor;
    private final Setting<Double> scale;

    @Override
    public void update(HudRenderer hudRenderer) {
        this.box.setSize(176.0 * this.scale.get(), 67.0 * this.scale.get());
    }

    private ItemStack getStack(int n) {
        if (this.isInEditor()) {
            return this.editorInv[n - 9];
        }
        return this.mc.player.inventory.getStack(n);
    }

    private void drawBackground(int n, int n2) {
        int n3 = (int)this.box.width;
        int n4 = (int)this.box.height;
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$render$hud$modules$InventoryViewerHud$Background[this.background.get().ordinal()]) {
            case 1: {
                RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.mc.getTextureManager().bindTexture(TEXTURE_LIGHT);
                DrawableHelper.drawTexture((MatrixStack)Matrices.getMatrixStack(), (int)n, (int)n2, (int)0, (float)0.0f, (float)0.0f, (int)n3, (int)n4, (int)n4, (int)n3);
                break;
            }
            case 2: {
                RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.mc.getTextureManager().bindTexture(TEXTURE_LIGHT_TRANSPARENT);
                DrawableHelper.drawTexture((MatrixStack)Matrices.getMatrixStack(), (int)n, (int)n2, (int)0, (float)0.0f, (float)0.0f, (int)n3, (int)n4, (int)n4, (int)n3);
                break;
            }
            case 3: {
                RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.mc.getTextureManager().bindTexture(TEXTURE_DARK);
                DrawableHelper.drawTexture((MatrixStack)Matrices.getMatrixStack(), (int)n, (int)n2, (int)0, (float)0.0f, (float)0.0f, (int)n3, (int)n4, (int)n4, (int)n3);
                break;
            }
            case 4: {
                RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.mc.getTextureManager().bindTexture(TEXTURE_DARK_TRANSPARENT);
                DrawableHelper.drawTexture((MatrixStack)Matrices.getMatrixStack(), (int)n, (int)n2, (int)0, (float)0.0f, (float)0.0f, (int)n3, (int)n4, (int)n4, (int)n3);
                break;
            }
            case 5: {
                Renderer.NORMAL.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
                Renderer.NORMAL.quad(n, n2, n3, n4, this.flatColor.get());
                Renderer.NORMAL.end();
            }
        }
    }

    static {
        TEXTURE_LIGHT = new Identifier("meteor-client", "container_3x9.png");
        TEXTURE_LIGHT_TRANSPARENT = new Identifier("meteor-client", "container_3x9-transparent.png");
        TEXTURE_DARK = new Identifier("meteor-client", "container_3x9-dark.png");
        TEXTURE_DARK_TRANSPARENT = new Identifier("meteor-client", "container_3x9-dark-transparent.png");
    }

    public InventoryViewerHud(HUD hUD) {
        super(hUD, "inventory-viewer", "Displays your inventory.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.scale = this.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of inventory viewer.").defaultValue(2.0).min(1.0).max(4.0).sliderMin(1.0).sliderMax(4.0).build());
        this.background = this.sgGeneral.add(new EnumSetting.Builder().name("background").description("Background of inventory viewer.").defaultValue(Background.Light).build());
        this.flatColor = this.sgGeneral.add(new ColorSetting.Builder().name("flat-mode-color").description("Color of background on Flat mode.").defaultValue(new SettingColor(0, 0, 0, 64)).build());
        this.editorInv = new ItemStack[27];
        this.editorInv[0] = Items.TOTEM_OF_UNDYING.getDefaultStack();
        this.editorInv[5] = new ItemStack((ItemConvertible)Items.ENCHANTED_GOLDEN_APPLE, 6);
        this.editorInv[19] = new ItemStack((ItemConvertible)Items.OBSIDIAN, 64);
        this.editorInv[this.editorInv.length - 1] = Items.NETHERITE_AXE.getDefaultStack();
    }

    @Override
    public void render(HudRenderer hudRenderer) {
        double d = this.box.getX();
        double d2 = this.box.getY();
        this.drawBackground((int)d, (int)d2);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                ItemStack ItemStack2 = this.getStack(9 + i * 9 + j);
                if (ItemStack2 == null) continue;
                RenderUtils.drawItem(ItemStack2, (int)(d / this.scale.get() + 8.0 + (double)(j * 18)), (int)(d2 / this.scale.get() + 7.0 + (double)(i * 18)), this.scale.get(), true);
                if (true >= true) continue;
                return;
            }
            if (!false) continue;
            return;
        }
    }

    public static final class Background
    extends Enum<Background> {
        public static final /* enum */ Background Light;
        public static final /* enum */ Background Flat;
        public static final /* enum */ Background LightTransparent;
        private static final Background[] $VALUES;
        public static final /* enum */ Background DarkTransparent;
        public static final /* enum */ Background None;
        public static final /* enum */ Background Dark;

        public static Background[] values() {
            return (Background[])$VALUES.clone();
        }

        static {
            None = new Background();
            Light = new Background();
            LightTransparent = new Background();
            Dark = new Background();
            DarkTransparent = new Background();
            Flat = new Background();
            $VALUES = Background.$values();
        }

        public static Background valueOf(String string) {
            return Enum.valueOf(Background.class, string);
        }

        private static Background[] $values() {
            return new Background[]{None, Light, LightTransparent, Dark, DarkTransparent, Flat};
        }
    }
}

