/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.gui.themes.meteor;

import minegame159.meteorclient.gui.DefaultSettingsWidgetFactory;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.gui.renderer.packer.GuiTexture;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorAccount;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorHorizontalSeparator;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorLabel;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorModule;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorMultiLabel;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorQuad;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorSection;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorTooltip;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorTopBar;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorVerticalSeparator;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorView;
import minegame159.meteorclient.gui.themes.meteor.widgets.WMeteorWindow;
import minegame159.meteorclient.gui.themes.meteor.widgets.input.WMeteorDropdown;
import minegame159.meteorclient.gui.themes.meteor.widgets.input.WMeteorSlider;
import minegame159.meteorclient.gui.themes.meteor.widgets.input.WMeteorTextBox;
import minegame159.meteorclient.gui.themes.meteor.widgets.pressable.WMeteorButton;
import minegame159.meteorclient.gui.themes.meteor.widgets.pressable.WMeteorCheckbox;
import minegame159.meteorclient.gui.themes.meteor.widgets.pressable.WMeteorMinus;
import minegame159.meteorclient.gui.themes.meteor.widgets.pressable.WMeteorPlus;
import minegame159.meteorclient.gui.themes.meteor.widgets.pressable.WMeteorTriangle;
import minegame159.meteorclient.gui.utils.AlignmentX;
import minegame159.meteorclient.gui.utils.CharFilter;
import minegame159.meteorclient.gui.widgets.WAccount;
import minegame159.meteorclient.gui.widgets.WHorizontalSeparator;
import minegame159.meteorclient.gui.widgets.WLabel;
import minegame159.meteorclient.gui.widgets.WQuad;
import minegame159.meteorclient.gui.widgets.WTooltip;
import minegame159.meteorclient.gui.widgets.WTopBar;
import minegame159.meteorclient.gui.widgets.WVerticalSeparator;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WSection;
import minegame159.meteorclient.gui.widgets.containers.WView;
import minegame159.meteorclient.gui.widgets.containers.WWindow;
import minegame159.meteorclient.gui.widgets.input.WDropdown;
import minegame159.meteorclient.gui.widgets.input.WSlider;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.gui.widgets.pressable.WCheckbox;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.gui.widgets.pressable.WPlus;
import minegame159.meteorclient.gui.widgets.pressable.WTriangle;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.accounts.Account;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;

public class MeteorGuiTheme
extends GuiTheme {
    private final /* synthetic */ SettingGroup sgSeparator;
    public final /* synthetic */ Setting<Double> scale;
    public final /* synthetic */ Setting<SettingColor> loggedInColor;
    public final /* synthetic */ Setting<SettingColor> separatorEdges;
    private final /* synthetic */ SettingGroup sgOutline;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ SettingGroup sgTextColors;
    public final /* synthetic */ Setting<SettingColor> titleTextColor;
    public final /* synthetic */ Setting<SettingColor> checkboxColor;
    public final /* synthetic */ Setting<AlignmentX> moduleAlignment;
    private final /* synthetic */ SettingGroup sgSlider;
    private final /* synthetic */ SettingGroup sgScrollbar;
    public final /* synthetic */ Setting<SettingColor> separatorCenter;
    private final /* synthetic */ SettingGroup sgBackgroundColors;
    public final /* synthetic */ Setting<Boolean> categoryIcons;
    public final /* synthetic */ Setting<SettingColor> moduleBackground;
    public final /* synthetic */ Setting<SettingColor> plusColor;
    public final /* synthetic */ Setting<SettingColor> accountTypeColor;
    public final /* synthetic */ Setting<Boolean> blur;
    public final /* synthetic */ Setting<SettingColor> separatorText;
    public final /* synthetic */ Setting<SettingColor> accentColor;
    public final /* synthetic */ ThreeStateColorSetting sliderHandle;
    public final /* synthetic */ ThreeStateColorSetting scrollbarColor;
    public final /* synthetic */ Setting<SettingColor> sliderRight;
    public final /* synthetic */ Setting<SettingColor> minusColor;
    public final /* synthetic */ ThreeStateColorSetting outlineColor;
    public final /* synthetic */ Setting<SettingColor> sliderLeft;
    public final /* synthetic */ ThreeStateColorSetting backgroundColor;
    private final /* synthetic */ SettingGroup sgColors;
    public final /* synthetic */ Setting<SettingColor> textColor;

    @Override
    protected WButton button(String llllllllllllllllIllIlIIlIIlIlIll, GuiTexture llllllllllllllllIllIlIIlIIlIllIl) {
        MeteorGuiTheme llllllllllllllllIllIlIIlIIlIllII;
        return llllllllllllllllIllIlIIlIIlIllII.w(new WMeteorButton(llllllllllllllllIllIlIIlIIlIlIll, llllllllllllllllIllIlIIlIIlIllIl));
    }

    @Override
    public WSlider slider(double llllllllllllllllIllIlIIlIIIllIII, double llllllllllllllllIllIlIIlIIIlIIll, double llllllllllllllllIllIlIIlIIIlIllI) {
        MeteorGuiTheme llllllllllllllllIllIlIIlIIIlIlIl;
        return llllllllllllllllIllIlIIlIIIlIlIl.w(new WMeteorSlider(llllllllllllllllIllIlIIlIIIllIII, llllllllllllllllIllIlIIlIIIlIIll, llllllllllllllllIllIlIIlIIIlIllI));
    }

    @Override
    public boolean categoryIcons() {
        MeteorGuiTheme llllllllllllllllIllIlIIIllIIIlll;
        return llllllllllllllllIllIlIIIllIIIlll.categoryIcons.get();
    }

    @Override
    public WWindow window(String llllllllllllllllIllIlIIlIlIIlIII) {
        MeteorGuiTheme llllllllllllllllIllIlIIlIlIIlIIl;
        return llllllllllllllllIllIlIIlIlIIlIIl.w(new WMeteorWindow(llllllllllllllllIllIlIIlIlIIlIII));
    }

    @Override
    public WTopBar topBar() {
        MeteorGuiTheme llllllllllllllllIllIlIIIllIlIIIl;
        return llllllllllllllllIllIlIIIllIlIIIl.w(new WMeteorTopBar());
    }

    public MeteorGuiTheme() {
        super("Meteor");
        MeteorGuiTheme llllllllllllllllIllIlIIlIllIlIII;
        llllllllllllllllIllIlIIlIllIlIII.sgGeneral = llllllllllllllllIllIlIIlIllIlIII.settings.getDefaultGroup();
        llllllllllllllllIllIlIIlIllIlIII.sgColors = llllllllllllllllIllIlIIlIllIlIII.settings.createGroup("Colors");
        llllllllllllllllIllIlIIlIllIlIII.sgTextColors = llllllllllllllllIllIlIIlIllIlIII.settings.createGroup("Text");
        llllllllllllllllIllIlIIlIllIlIII.sgBackgroundColors = llllllllllllllllIllIlIIlIllIlIII.settings.createGroup("Background");
        llllllllllllllllIllIlIIlIllIlIII.sgOutline = llllllllllllllllIllIlIIlIllIlIII.settings.createGroup("Outline");
        llllllllllllllllIllIlIIlIllIlIII.sgSeparator = llllllllllllllllIllIlIIlIllIlIII.settings.createGroup("Separator");
        llllllllllllllllIllIlIIlIllIlIII.sgScrollbar = llllllllllllllllIllIlIIlIllIlIII.settings.createGroup("Scrollbar");
        llllllllllllllllIllIlIIlIllIlIII.sgSlider = llllllllllllllllIllIlIIlIllIlIII.settings.createGroup("Slider");
        llllllllllllllllIllIlIIlIllIlIII.scale = llllllllllllllllIllIlIIlIllIlIII.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of the GUI.").defaultValue(1.0).min(1.0).sliderMin(1.0).sliderMax(4.0).onSliderRelease().onChanged(llllllllllllllllIllIlIIIllIIIIlI -> {
            if (Utils.mc.currentScreen instanceof WidgetScreen) {
                ((WidgetScreen)Utils.mc.currentScreen).invalidate();
            }
        }).build());
        llllllllllllllllIllIlIIlIllIlIII.moduleAlignment = llllllllllllllllIllIlIIlIllIlIII.sgGeneral.add(new EnumSetting.Builder().name("module-alignment").description("How module titles are aligned.").defaultValue(AlignmentX.Center).build());
        llllllllllllllllIllIlIIlIllIlIII.categoryIcons = llllllllllllllllIllIlIIlIllIlIII.sgGeneral.add(new BoolSetting.Builder().name("category-icons").description("Adds item icons to module categories.").defaultValue(false).build());
        llllllllllllllllIllIlIIlIllIlIII.blur = llllllllllllllllIllIlIIlIllIlIII.sgGeneral.add(new BoolSetting.Builder().name("blur").description("Apply blur behind the GUI.").defaultValue(false).build());
        llllllllllllllllIllIlIIlIllIlIII.accentColor = llllllllllllllllIllIlIIlIllIlIII.color("accent", "Main color of the GUI.", new SettingColor(135, 0, 255));
        llllllllllllllllIllIlIIlIllIlIII.checkboxColor = llllllllllllllllIllIlIIlIllIlIII.color("checkbox", "Color of checkbox.", new SettingColor(135, 0, 255));
        llllllllllllllllIllIlIIlIllIlIII.plusColor = llllllllllllllllIllIlIIlIllIlIII.color("plus", "Color of plus button.", new SettingColor(255, 255, 255));
        llllllllllllllllIllIlIIlIllIlIII.minusColor = llllllllllllllllIllIlIIlIllIlIII.color("minus", "Color of minus button.", new SettingColor(255, 255, 255));
        llllllllllllllllIllIlIIlIllIlIII.textColor = llllllllllllllllIllIlIIlIllIlIII.color(llllllllllllllllIllIlIIlIllIlIII.sgTextColors, "text", "Color of text.", new SettingColor(255, 255, 255));
        llllllllllllllllIllIlIIlIllIlIII.titleTextColor = llllllllllllllllIllIlIIlIllIlIII.color(llllllllllllllllIllIlIIlIllIlIII.sgTextColors, "title-text", "Color of title text.", new SettingColor(255, 255, 255));
        llllllllllllllllIllIlIIlIllIlIII.loggedInColor = llllllllllllllllIllIlIIlIllIlIII.color(llllllllllllllllIllIlIIlIllIlIII.sgTextColors, "logged-in-text", "Color of logged in account name.", new SettingColor(45, 225, 45));
        llllllllllllllllIllIlIIlIllIlIII.accountTypeColor = llllllllllllllllIllIlIIlIllIlIII.color(llllllllllllllllIllIlIIlIllIlIII.sgTextColors, "account-type-text", "Color of account type text.", new SettingColor(150, 150, 150));
        llllllllllllllllIllIlIIlIllIlIII.backgroundColor = llllllllllllllllIllIlIIlIllIlIII.new ThreeStateColorSetting(llllllllllllllllIllIlIIlIllIlIII.sgBackgroundColors, "background", new SettingColor(20, 20, 20, 200), new SettingColor(30, 30, 30, 200), new SettingColor(40, 40, 40, 200));
        llllllllllllllllIllIlIIlIllIlIII.moduleBackground = llllllllllllllllIllIlIIlIllIlIII.color(llllllllllllllllIllIlIIlIllIlIII.sgBackgroundColors, "module-background", "Color of module background when active.", new SettingColor(50, 50, 50));
        llllllllllllllllIllIlIIlIllIlIII.outlineColor = llllllllllllllllIllIlIIlIllIlIII.new ThreeStateColorSetting(llllllllllllllllIllIlIIlIllIlIII.sgOutline, "outline", new SettingColor(0, 0, 0), new SettingColor(10, 10, 10), new SettingColor(20, 20, 20));
        llllllllllllllllIllIlIIlIllIlIII.separatorText = llllllllllllllllIllIlIIlIllIlIII.color(llllllllllllllllIllIlIIlIllIlIII.sgSeparator, "separator-text", "Color of separator text", new SettingColor(255, 255, 255));
        llllllllllllllllIllIlIIlIllIlIII.separatorCenter = llllllllllllllllIllIlIIlIllIlIII.color(llllllllllllllllIllIlIIlIllIlIII.sgSeparator, "separator-center", "Center color of separators.", new SettingColor(255, 255, 255));
        llllllllllllllllIllIlIIlIllIlIII.separatorEdges = llllllllllllllllIllIlIIlIllIlIII.color(llllllllllllllllIllIlIIlIllIlIII.sgSeparator, "separator-edges", "Color of separator edges.", new SettingColor(225, 225, 225, 150));
        llllllllllllllllIllIlIIlIllIlIII.scrollbarColor = llllllllllllllllIllIlIIlIllIlIII.new ThreeStateColorSetting(llllllllllllllllIllIlIIlIllIlIII.sgScrollbar, "Scrollbar", new SettingColor(30, 30, 30, 200), new SettingColor(40, 40, 40, 200), new SettingColor(50, 50, 50, 200));
        llllllllllllllllIllIlIIlIllIlIII.sliderHandle = llllllllllllllllIllIlIIlIllIlIII.new ThreeStateColorSetting(llllllllllllllllIllIlIIlIllIlIII.sgSlider, "slider-handle", new SettingColor(0, 255, 180), new SettingColor(0, 240, 165), new SettingColor(0, 225, 150));
        llllllllllllllllIllIlIIlIllIlIII.sliderLeft = llllllllllllllllIllIlIIlIllIlIII.color(llllllllllllllllIllIlIIlIllIlIII.sgSlider, "slider-left", "Color of slider left part.", new SettingColor(0, 150, 80));
        llllllllllllllllIllIlIIlIllIlIII.sliderRight = llllllllllllllllIllIlIIlIllIlIII.color(llllllllllllllllIllIlIIlIllIlIII.sgSlider, "slider-right", "Color of slider right part.", new SettingColor(50, 50, 50));
        llllllllllllllllIllIlIIlIllIlIII.settingsFactory = new DefaultSettingsWidgetFactory(llllllllllllllllIllIlIIlIllIlIII);
    }

    private Setting<SettingColor> color(SettingGroup llllllllllllllllIllIlIIlIlIlllIl, String llllllllllllllllIllIlIIlIllIIIII, String llllllllllllllllIllIlIIlIlIlllll, SettingColor llllllllllllllllIllIlIIlIlIllIlI) {
        return llllllllllllllllIllIlIIlIlIlllIl.add(new ColorSetting.Builder().name(String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIIlIllIIIII).append("-color"))).description(llllllllllllllllIllIlIIlIlIlllll).defaultValue(llllllllllllllllIllIlIIlIlIllIlI).build());
    }

    @Override
    public WMinus minus() {
        MeteorGuiTheme llllllllllllllllIllIlIIlIIlIlIII;
        return llllllllllllllllIllIlIIlIIlIlIII.w(new WMeteorMinus());
    }

    @Override
    public WView view() {
        MeteorGuiTheme llllllllllllllllIllIlIIIllllIlIl;
        return llllllllllllllllIllIlIIIllllIlIl.w(new WMeteorView());
    }

    @Override
    public WQuad quad(Color llllllllllllllllIllIlIIIllIlIlIl) {
        MeteorGuiTheme llllllllllllllllIllIlIIIllIlIllI;
        return llllllllllllllllIllIlIIIllIlIllI.w(new WMeteorQuad(llllllllllllllllIllIlIIIllIlIlIl));
    }

    @Override
    public TextRenderer textRenderer() {
        return TextRenderer.get();
    }

    @Override
    public WPlus plus() {
        MeteorGuiTheme llllllllllllllllIllIlIIlIIlIIlII;
        return llllllllllllllllIllIlIIlIIlIIlII.w(new WMeteorPlus());
    }

    @Override
    public <T> WDropdown<T> dropdown(T[] llllllllllllllllIllIlIIlIIIIIIIl, T llllllllllllllllIllIlIIlIIIIIIll) {
        MeteorGuiTheme llllllllllllllllIllIlIIlIIIIIlIl;
        return llllllllllllllllIllIlIIlIIIIIlIl.w(new WMeteorDropdown<T>(llllllllllllllllIllIlIIlIIIIIIIl, llllllllllllllllIllIlIIlIIIIIIll));
    }

    @Override
    public WHorizontalSeparator horizontalSeparator(String llllllllllllllllIllIlIIlIIlllIII) {
        MeteorGuiTheme llllllllllllllllIllIlIIlIIlllIIl;
        return llllllllllllllllIllIlIIlIIlllIIl.w(new WMeteorHorizontalSeparator(llllllllllllllllIllIlIIlIIlllIII));
    }

    @Override
    public WTriangle triangle() {
        MeteorGuiTheme llllllllllllllllIllIlIIIllllllIl;
        return llllllllllllllllIllIlIIIllllllIl.w(new WMeteorTriangle());
    }

    @Override
    public WWidget module(Module llllllllllllllllIllIlIIIllIllIll) {
        MeteorGuiTheme llllllllllllllllIllIlIIIllIllIlI;
        return llllllllllllllllIllIlIIIllIllIlI.w(new WMeteorModule(llllllllllllllllIllIlIIIllIllIll));
    }

    @Override
    public WAccount account(WidgetScreen llllllllllllllllIllIlIIIlllIIIll, Account<?> llllllllllllllllIllIlIIIllIlllll) {
        MeteorGuiTheme llllllllllllllllIllIlIIIlllIIIIl;
        return llllllllllllllllIllIlIIIlllIIIIl.w(new WMeteorAccount(llllllllllllllllIllIlIIIlllIIIll, llllllllllllllllIllIlIIIllIlllll));
    }

    @Override
    public WLabel label(String llllllllllllllllIllIlIIlIIlllllI, boolean llllllllllllllllIllIlIIlIIllllIl, double llllllllllllllllIllIlIIlIIllllII) {
        MeteorGuiTheme llllllllllllllllIllIlIIlIlIIIIll;
        if (llllllllllllllllIllIlIIlIIllllII == 0.0) {
            return llllllllllllllllIllIlIIlIlIIIIll.w(new WMeteorLabel(llllllllllllllllIllIlIIlIIlllllI, llllllllllllllllIllIlIIlIIllllIl));
        }
        return llllllllllllllllIllIlIIlIlIIIIll.w(new WMeteorMultiLabel(llllllllllllllllIllIlIIlIIlllllI, llllllllllllllllIllIlIIlIIllllIl, llllllllllllllllIllIlIIlIIllllII));
    }

    @Override
    public WTextBox textBox(String llllllllllllllllIllIlIIlIIIIllIl, CharFilter llllllllllllllllIllIlIIlIIIIlIIl) {
        MeteorGuiTheme llllllllllllllllIllIlIIlIIIIlIll;
        return llllllllllllllllIllIlIIlIIIIlIll.w(new WMeteorTextBox(llllllllllllllllIllIlIIlIIIIllIl, llllllllllllllllIllIlIIlIIIIlIIl));
    }

    @Override
    public WTooltip tooltip(String llllllllllllllllIllIlIIIllllIlll) {
        MeteorGuiTheme llllllllllllllllIllIlIIIlllllIII;
        return llllllllllllllllIllIlIIIlllllIII.w(new WMeteorTooltip(llllllllllllllllIllIlIIIllllIlll));
    }

    @Override
    public WSection section(String llllllllllllllllIllIlIIIlllIlllI, boolean llllllllllllllllIllIlIIIlllIlIIl, WWidget llllllllllllllllIllIlIIIlllIlIII) {
        MeteorGuiTheme llllllllllllllllIllIlIIIlllIlIll;
        return llllllllllllllllIllIlIIIlllIlIll.w(new WMeteorSection(llllllllllllllllIllIlIIIlllIlllI, llllllllllllllllIllIlIIIlllIlIIl, llllllllllllllllIllIlIIIlllIlIII));
    }

    @Override
    public double scale(double llllllllllllllllIllIlIIIllIIlIIl) {
        MeteorGuiTheme llllllllllllllllIllIlIIIllIIllII;
        return llllllllllllllllIllIlIIIllIIlIIl * llllllllllllllllIllIlIIIllIIllII.scale.get();
    }

    @Override
    public boolean blur() {
        MeteorGuiTheme llllllllllllllllIllIlIIIllIIIIll;
        return llllllllllllllllIllIlIIIllIIIIll.blur.get();
    }

    private Setting<SettingColor> color(String llllllllllllllllIllIlIIlIlIlIlII, String llllllllllllllllIllIlIIlIlIlIIll, SettingColor llllllllllllllllIllIlIIlIlIlIIlI) {
        MeteorGuiTheme llllllllllllllllIllIlIIlIlIlIIIl;
        return llllllllllllllllIllIlIIlIlIlIIIl.color(llllllllllllllllIllIlIIlIlIlIIIl.sgColors, llllllllllllllllIllIlIIlIlIlIlII, llllllllllllllllIllIlIIlIlIlIIll, llllllllllllllllIllIlIIlIlIlIIlI);
    }

    @Override
    public WVerticalSeparator verticalSeparator() {
        MeteorGuiTheme llllllllllllllllIllIlIIlIIllIIll;
        return llllllllllllllllIllIlIIlIIllIIll.w(new WMeteorVerticalSeparator());
    }

    @Override
    public WCheckbox checkbox(boolean llllllllllllllllIllIlIIlIIlIIIII) {
        MeteorGuiTheme llllllllllllllllIllIlIIlIIIlllll;
        return llllllllllllllllIllIlIIlIIIlllll.w(new WMeteorCheckbox(llllllllllllllllIllIlIIlIIlIIIII));
    }

    public class ThreeStateColorSetting {
        private final /* synthetic */ Setting<SettingColor> pressed;
        private final /* synthetic */ Setting<SettingColor> normal;
        private final /* synthetic */ Setting<SettingColor> hovered;

        public ThreeStateColorSetting(SettingGroup lllllllllllllllllIllIlIllIIlllII, String lllllllllllllllllIllIlIllIIlIlII, SettingColor lllllllllllllllllIllIlIllIIlIIll, SettingColor lllllllllllllllllIllIlIllIIllIIl, SettingColor lllllllllllllllllIllIlIllIIllIII) {
            ThreeStateColorSetting lllllllllllllllllIllIlIllIIlIlll;
            lllllllllllllllllIllIlIllIIlIlll.normal = lllllllllllllllllIllIlIllIIlIlll.MeteorGuiTheme.this.color(lllllllllllllllllIllIlIllIIlllII, lllllllllllllllllIllIlIllIIlIlII, String.valueOf(new StringBuilder().append("Color of ").append(lllllllllllllllllIllIlIllIIlIlII).append(".")), lllllllllllllllllIllIlIllIIlIIll);
            lllllllllllllllllIllIlIllIIlIlll.hovered = lllllllllllllllllIllIlIllIIlIlll.MeteorGuiTheme.this.color(lllllllllllllllllIllIlIllIIlllII, String.valueOf(new StringBuilder().append("hovered-").append(lllllllllllllllllIllIlIllIIlIlII)), String.valueOf(new StringBuilder().append("Color of ").append(lllllllllllllllllIllIlIllIIlIlII).append(" when hovered.")), lllllllllllllllllIllIlIllIIllIIl);
            lllllllllllllllllIllIlIllIIlIlll.pressed = lllllllllllllllllIllIlIllIIlIlll.MeteorGuiTheme.this.color(lllllllllllllllllIllIlIllIIlllII, String.valueOf(new StringBuilder().append("pressed-").append(lllllllllllllllllIllIlIllIIlIlII)), String.valueOf(new StringBuilder().append("Color of ").append(lllllllllllllllllIllIlIllIIlIlII).append(" when pressed.")), lllllllllllllllllIllIlIllIIllIII);
        }

        public SettingColor get(boolean lllllllllllllllllIllIlIlIllllIlI, boolean lllllllllllllllllIllIlIlIllllIIl) {
            ThreeStateColorSetting lllllllllllllllllIllIlIlIllllIll;
            return lllllllllllllllllIllIlIlIllllIll.get(lllllllllllllllllIllIlIlIllllIlI, lllllllllllllllllIllIlIlIllllIIl, false);
        }

        public SettingColor get(boolean lllllllllllllllllIllIlIllIIIIlII, boolean lllllllllllllllllIllIlIllIIIIIll, boolean lllllllllllllllllIllIlIllIIIIIlI) {
            ThreeStateColorSetting lllllllllllllllllIllIlIllIIIIlIl;
            if (lllllllllllllllllIllIlIllIIIIlII) {
                return lllllllllllllllllIllIlIllIIIIlIl.pressed.get();
            }
            return lllllllllllllllllIllIlIllIIIIIll && (lllllllllllllllllIllIlIllIIIIIlI || !lllllllllllllllllIllIlIllIIIIlIl.MeteorGuiTheme.this.disableHoverColor) ? lllllllllllllllllIllIlIllIIIIlIl.hovered.get() : lllllllllllllllllIllIlIllIIIIlIl.normal.get();
        }

        public SettingColor get() {
            ThreeStateColorSetting lllllllllllllllllIllIlIllIIIllll;
            return lllllllllllllllllIllIlIllIIIllll.normal.get();
        }
    }
}

