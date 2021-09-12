/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.HashMap;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.mixin.ClientPlayerInteractionManagerAccessor;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_3191;

public class BreakIndicators
extends Module {
    private final Setting<SettingColor> gradientColor2Sides;
    private final Setting<SettingColor> gradientColor1Sides;
    private final Setting<ShapeMode> shapeMode;
    public final Map<Integer, class_3191> blocks;
    private final Setting<SettingColor> gradientColor2Lines;
    private final SettingGroup sgGeneral;
    public final Setting<Boolean> hideVanillaIndicators;
    private final Color cLines;
    private final SettingGroup sgRender;
    private final Setting<Boolean> smoothAnim;
    public final Setting<Boolean> multiple;
    private final Color cSides;
    private final Setting<SettingColor> gradientColor1Lines;

    public BreakIndicators() {
        super(Categories.Render, "break-indicators", "Renders the progress of a block being broken.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.multiple = this.sgGeneral.add(new BoolSetting.Builder().name("multiple").description("Renders block breaking from other players.").defaultValue(true).build());
        this.hideVanillaIndicators = this.sgGeneral.add(new BoolSetting.Builder().name("hide-vanilla-indicators").description("Hides the vanilla (or resource pack) break indicators.").defaultValue(true).build());
        this.smoothAnim = this.sgGeneral.add(new BoolSetting.Builder().name("smooth-animation").description("Renders a smooth animation at block you break.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.gradientColor1Sides = this.sgRender.add(new ColorSetting.Builder().name("gradient-color-1-sides").description("The side color for the non-broken block.").defaultValue(new SettingColor(25, 252, 25, 25)).build());
        this.gradientColor1Lines = this.sgRender.add(new ColorSetting.Builder().name("gradient-color-1-lines").description("The line color for the non-broken block.").defaultValue(new SettingColor(25, 252, 25, 100)).build());
        this.gradientColor2Sides = this.sgRender.add(new ColorSetting.Builder().name("gradient-color-2-sides").description("The side color for the fully-broken block.").defaultValue(new SettingColor(255, 25, 25, 100)).build());
        this.gradientColor2Lines = this.sgRender.add(new ColorSetting.Builder().name("gradient-color-2-lines").description("The line color for the fully-broken block.").defaultValue(new SettingColor(255, 25, 25, 100)).build());
        this.blocks = new HashMap<Integer, class_3191>();
        this.cSides = new Color();
        this.cLines = new Color();
    }

    private static boolean lambda$onRender$0(class_2338 class_23382, class_3191 class_31912) {
        return class_31912.method_13991().equals((Object)class_23382);
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        boolean bl;
        ClientPlayerInteractionManagerAccessor clientPlayerInteractionManagerAccessor;
        if (this.smoothAnim.get().booleanValue()) {
            clientPlayerInteractionManagerAccessor = (ClientPlayerInteractionManagerAccessor)this.mc.field_1761;
            class_2338 class_23382 = clientPlayerInteractionManagerAccessor.getCurrentBreakingBlockPos();
            boolean bl2 = bl = class_23382 != null && clientPlayerInteractionManagerAccessor.getBreakingProgress() > 0.0f;
            if (bl && this.blocks.values().stream().noneMatch(arg_0 -> BreakIndicators.lambda$onRender$0(class_23382, arg_0))) {
                this.blocks.put(this.mc.field_1724.method_5628(), new class_3191(this.mc.field_1724.method_5628(), class_23382));
            }
        } else {
            clientPlayerInteractionManagerAccessor = null;
            bl = false;
        }
        this.blocks.values().forEach(arg_0 -> this.lambda$onRender$1(bl, clientPlayerInteractionManagerAccessor, arg_0));
    }

    private void lambda$onRender$1(boolean bl, ClientPlayerInteractionManagerAccessor clientPlayerInteractionManagerAccessor, class_3191 class_31912) {
        class_238 class_2383;
        class_2338 class_23382 = class_31912.method_13991();
        int n = class_31912.method_13988();
        class_2680 class_26802 = this.mc.field_1687.method_8320(class_23382);
        class_265 class_2652 = class_26802.method_26218((class_1922)this.mc.field_1687, class_23382);
        if (class_2652.method_1110()) {
            return;
        }
        class_238 class_2384 = class_2383 = class_2652.method_1107();
        double d = bl && clientPlayerInteractionManagerAccessor.getCurrentBreakingBlockPos().equals((Object)class_23382) ? 1.0 - (double)clientPlayerInteractionManagerAccessor.getBreakingProgress() : (double)(9 - (n + 1)) / 9.0;
        double d2 = 1.0 - d;
        class_2384 = class_2384.method_1002(class_2384.method_17939() * d, class_2384.method_17940() * d, class_2384.method_17941() * d);
        double d3 = class_2383.method_17939() * d / 2.0;
        double d4 = class_2383.method_17940() * d / 2.0;
        double d5 = class_2383.method_17941() * d / 2.0;
        double d6 = (double)class_23382.method_10263() + class_2384.field_1323 + d3;
        double d7 = (double)class_23382.method_10264() + class_2384.field_1322 + d4;
        double d8 = (double)class_23382.method_10260() + class_2384.field_1321 + d5;
        double d9 = (double)class_23382.method_10263() + class_2384.field_1320 + d3;
        double d10 = (double)class_23382.method_10264() + class_2384.field_1325 + d4;
        double d11 = (double)class_23382.method_10260() + class_2384.field_1324 + d5;
        Color color = this.gradientColor1Sides.get();
        Color color2 = this.gradientColor2Sides.get();
        this.cSides.set((int)Math.round((double)color.r + (double)(color2.r - color.r) * d2), (int)Math.round((double)color.g + (double)(color2.g - color.g) * d2), (int)Math.round((double)color.b + (double)(color2.b - color.b) * d2), (int)Math.round((double)color.a + (double)(color2.a - color.a) * d2));
        Color color3 = this.gradientColor1Lines.get();
        Color color4 = this.gradientColor2Lines.get();
        this.cLines.set((int)Math.round((double)color3.r + (double)(color4.r - color3.r) * d2), (int)Math.round((double)color3.g + (double)(color4.g - color3.g) * d2), (int)Math.round((double)color3.b + (double)(color4.b - color3.b) * d2), (int)Math.round((double)color3.a + (double)(color4.a - color3.a) * d2));
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, d6, d7, d8, d9, d10, d11, this.cSides, this.cLines, this.shapeMode.get(), 0);
    }

    @Override
    public void onDeactivate() {
        this.blocks.clear();
    }
}

