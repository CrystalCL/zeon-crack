/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.ItemConvertible
 *  net.minecraft.client.render.VertexFormats
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.gui.DrawableHelper
 *  net.minecraft.client.util.math.MatrixStack
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
    private final /* synthetic */ SettingGroup sgGeneral;
    private static final /* synthetic */ Identifier TEXTURE_LIGHT;
    private final /* synthetic */ ItemStack[] editorInv;
    private final /* synthetic */ Setting<Background> background;
    private static final /* synthetic */ Identifier TEXTURE_LIGHT_TRANSPARENT;
    private final /* synthetic */ Setting<Double> scale;
    private static final /* synthetic */ Identifier TEXTURE_DARK;
    private final /* synthetic */ Setting<SettingColor> flatColor;
    private static final /* synthetic */ Identifier TEXTURE_DARK_TRANSPARENT;

    public InventoryViewerHud(HUD llllllllllllllllllllllIlIlIIIlll) {
        super(llllllllllllllllllllllIlIlIIIlll, "inventory-viewer", "Displays your inventory.");
        InventoryViewerHud llllllllllllllllllllllIlIlIIlIII;
        llllllllllllllllllllllIlIlIIlIII.sgGeneral = llllllllllllllllllllllIlIlIIlIII.settings.getDefaultGroup();
        llllllllllllllllllllllIlIlIIlIII.scale = llllllllllllllllllllllIlIlIIlIII.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of inventory viewer.").defaultValue(2.0).min(1.0).max(4.0).sliderMin(1.0).sliderMax(4.0).build());
        llllllllllllllllllllllIlIlIIlIII.background = llllllllllllllllllllllIlIlIIlIII.sgGeneral.add(new EnumSetting.Builder().name("background").description("Background of inventory viewer.").defaultValue(Background.Light).build());
        llllllllllllllllllllllIlIlIIlIII.flatColor = llllllllllllllllllllllIlIlIIlIII.sgGeneral.add(new ColorSetting.Builder().name("flat-mode-color").description("Color of background on Flat mode.").defaultValue(new SettingColor(0, 0, 0, 64)).build());
        llllllllllllllllllllllIlIlIIlIII.editorInv = new ItemStack[27];
        llllllllllllllllllllllIlIlIIlIII.editorInv[0] = Items.TOTEM_OF_UNDYING.getDefaultStack();
        llllllllllllllllllllllIlIlIIlIII.editorInv[5] = new ItemStack((ItemConvertible)Items.ENCHANTED_GOLDEN_APPLE, 6);
        llllllllllllllllllllllIlIlIIlIII.editorInv[19] = new ItemStack((ItemConvertible)Items.OBSIDIAN, 64);
        llllllllllllllllllllllIlIlIIlIII.editorInv[llllllllllllllllllllllIlIlIIlIII.editorInv.length - 1] = Items.NETHERITE_AXE.getDefaultStack();
    }

    static {
        TEXTURE_LIGHT = new Identifier("meteor-client", "container_3x9.png");
        TEXTURE_LIGHT_TRANSPARENT = new Identifier("meteor-client", "container_3x9-transparent.png");
        TEXTURE_DARK = new Identifier("meteor-client", "container_3x9-dark.png");
        TEXTURE_DARK_TRANSPARENT = new Identifier("meteor-client", "container_3x9-dark-transparent.png");
    }

    private void drawBackground(int llllllllllllllllllllllIlIIIlllII, int llllllllllllllllllllllIlIIlIIIII) {
        InventoryViewerHud llllllllllllllllllllllIlIIlIIIlI;
        int llllllllllllllllllllllIlIIIlllll = (int)llllllllllllllllllllllIlIIlIIIlI.box.width;
        int llllllllllllllllllllllIlIIIllllI = (int)llllllllllllllllllllllIlIIlIIIlI.box.height;
        switch (llllllllllllllllllllllIlIIlIIIlI.background.get()) {
            case Light: {
                RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                llllllllllllllllllllllIlIIlIIIlI.mc.getTextureManager().bindTexture(TEXTURE_LIGHT);
                DrawableHelper.drawTexture((MatrixStack)Matrices.getMatrixStack(), (int)llllllllllllllllllllllIlIIIlllII, (int)llllllllllllllllllllllIlIIlIIIII, (int)0, (float)0.0f, (float)0.0f, (int)llllllllllllllllllllllIlIIIlllll, (int)llllllllllllllllllllllIlIIIllllI, (int)llllllllllllllllllllllIlIIIllllI, (int)llllllllllllllllllllllIlIIIlllll);
                break;
            }
            case LightTransparent: {
                RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                llllllllllllllllllllllIlIIlIIIlI.mc.getTextureManager().bindTexture(TEXTURE_LIGHT_TRANSPARENT);
                DrawableHelper.drawTexture((MatrixStack)Matrices.getMatrixStack(), (int)llllllllllllllllllllllIlIIIlllII, (int)llllllllllllllllllllllIlIIlIIIII, (int)0, (float)0.0f, (float)0.0f, (int)llllllllllllllllllllllIlIIIlllll, (int)llllllllllllllllllllllIlIIIllllI, (int)llllllllllllllllllllllIlIIIllllI, (int)llllllllllllllllllllllIlIIIlllll);
                break;
            }
            case Dark: {
                RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                llllllllllllllllllllllIlIIlIIIlI.mc.getTextureManager().bindTexture(TEXTURE_DARK);
                DrawableHelper.drawTexture((MatrixStack)Matrices.getMatrixStack(), (int)llllllllllllllllllllllIlIIIlllII, (int)llllllllllllllllllllllIlIIlIIIII, (int)0, (float)0.0f, (float)0.0f, (int)llllllllllllllllllllllIlIIIlllll, (int)llllllllllllllllllllllIlIIIllllI, (int)llllllllllllllllllllllIlIIIllllI, (int)llllllllllllllllllllllIlIIIlllll);
                break;
            }
            case DarkTransparent: {
                RenderSystem.color4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                llllllllllllllllllllllIlIIlIIIlI.mc.getTextureManager().bindTexture(TEXTURE_DARK_TRANSPARENT);
                DrawableHelper.drawTexture((MatrixStack)Matrices.getMatrixStack(), (int)llllllllllllllllllllllIlIIIlllII, (int)llllllllllllllllllllllIlIIlIIIII, (int)0, (float)0.0f, (float)0.0f, (int)llllllllllllllllllllllIlIIIlllll, (int)llllllllllllllllllllllIlIIIllllI, (int)llllllllllllllllllllllIlIIIllllI, (int)llllllllllllllllllllllIlIIIlllll);
                break;
            }
            case Flat: {
                Renderer.NORMAL.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
                Renderer.NORMAL.quad(llllllllllllllllllllllIlIIIlllII, llllllllllllllllllllllIlIIlIIIII, llllllllllllllllllllllIlIIIlllll, llllllllllllllllllllllIlIIIllllI, llllllllllllllllllllllIlIIlIIIlI.flatColor.get());
                Renderer.NORMAL.end();
            }
        }
    }

    private ItemStack getStack(int llllllllllllllllllllllIlIIlIlIlI) {
        InventoryViewerHud llllllllllllllllllllllIlIIlIlIIl;
        if (llllllllllllllllllllllIlIIlIlIIl.isInEditor()) {
            return llllllllllllllllllllllIlIIlIlIIl.editorInv[llllllllllllllllllllllIlIIlIlIlI - 9];
        }
        return llllllllllllllllllllllIlIIlIlIIl.mc.player.inventory.getStack(llllllllllllllllllllllIlIIlIlIlI);
    }

    @Override
    public void render(HudRenderer llllllllllllllllllllllIlIIllIllI) {
        InventoryViewerHud llllllllllllllllllllllIlIIllIlll;
        double llllllllllllllllllllllIlIIllIlIl = llllllllllllllllllllllIlIIllIlll.box.getX();
        double llllllllllllllllllllllIlIIllIlII = llllllllllllllllllllllIlIIllIlll.box.getY();
        llllllllllllllllllllllIlIIllIlll.drawBackground((int)llllllllllllllllllllllIlIIllIlIl, (int)llllllllllllllllllllllIlIIllIlII);
        for (int llllllllllllllllllllllIlIIlllIII = 0; llllllllllllllllllllllIlIIlllIII < 3; ++llllllllllllllllllllllIlIIlllIII) {
            for (int llllllllllllllllllllllIlIIlllIIl = 0; llllllllllllllllllllllIlIIlllIIl < 9; ++llllllllllllllllllllllIlIIlllIIl) {
                ItemStack llllllllllllllllllllllIlIIlllIlI = llllllllllllllllllllllIlIIllIlll.getStack(9 + llllllllllllllllllllllIlIIlllIII * 9 + llllllllllllllllllllllIlIIlllIIl);
                if (llllllllllllllllllllllIlIIlllIlI == null) continue;
                RenderUtils.drawItem(llllllllllllllllllllllIlIIlllIlI, (int)(llllllllllllllllllllllIlIIllIlIl / llllllllllllllllllllllIlIIllIlll.scale.get() + 8.0 + (double)(llllllllllllllllllllllIlIIlllIIl * 18)), (int)(llllllllllllllllllllllIlIIllIlII / llllllllllllllllllllllIlIIllIlll.scale.get() + 7.0 + (double)(llllllllllllllllllllllIlIIlllIII * 18)), llllllllllllllllllllllIlIIllIlll.scale.get(), true);
            }
        }
    }

    @Override
    public void update(HudRenderer llllllllllllllllllllllIlIlIIIIlI) {
        InventoryViewerHud llllllllllllllllllllllIlIlIIIIll;
        llllllllllllllllllllllIlIlIIIIll.box.setSize(176.0 * llllllllllllllllllllllIlIlIIIIll.scale.get(), 67.0 * llllllllllllllllllllllIlIlIIIIll.scale.get());
    }

    public static final class Background
    extends Enum<Background> {
        public static final /* synthetic */ /* enum */ Background LightTransparent;
        public static final /* synthetic */ /* enum */ Background Flat;
        public static final /* synthetic */ /* enum */ Background Light;
        public static final /* synthetic */ /* enum */ Background None;
        public static final /* synthetic */ /* enum */ Background Dark;
        private static final /* synthetic */ Background[] $VALUES;
        public static final /* synthetic */ /* enum */ Background DarkTransparent;

        static {
            None = new Background();
            Light = new Background();
            LightTransparent = new Background();
            Dark = new Background();
            DarkTransparent = new Background();
            Flat = new Background();
            $VALUES = Background.$values();
        }

        private static /* synthetic */ Background[] $values() {
            return new Background[]{None, Light, LightTransparent, Dark, DarkTransparent, Flat};
        }

        public static Background[] values() {
            return (Background[])$VALUES.clone();
        }

        private Background() {
            Background llllllllllllllllllllllllIIIIlIIl;
        }

        public static Background valueOf(String llllllllllllllllllllllllIIIIllll) {
            return Enum.valueOf(Background.class, llllllllllllllllllllllllIIIIllll);
        }
    }
}

