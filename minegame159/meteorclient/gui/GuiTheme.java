/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.texture.AbstractTexture
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.client.gui.screen.Screen
 */
package minegame159.meteorclient.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import minegame159.meteorclient.gui.WidgetScreen;
import minegame159.meteorclient.gui.renderer.packer.GuiTexture;
import minegame159.meteorclient.gui.screens.AccountsScreen;
import minegame159.meteorclient.gui.screens.ModuleScreen;
import minegame159.meteorclient.gui.screens.ModulesScreen;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.utils.CharFilter;
import minegame159.meteorclient.gui.utils.SettingsWidgetFactory;
import minegame159.meteorclient.gui.utils.WindowConfig;
import minegame159.meteorclient.gui.widgets.WAccount;
import minegame159.meteorclient.gui.widgets.WHorizontalSeparator;
import minegame159.meteorclient.gui.widgets.WItem;
import minegame159.meteorclient.gui.widgets.WItemWithLabel;
import minegame159.meteorclient.gui.widgets.WKeybind;
import minegame159.meteorclient.gui.widgets.WLabel;
import minegame159.meteorclient.gui.widgets.WQuad;
import minegame159.meteorclient.gui.widgets.WTexture;
import minegame159.meteorclient.gui.widgets.WTooltip;
import minegame159.meteorclient.gui.widgets.WTopBar;
import minegame159.meteorclient.gui.widgets.WVerticalSeparator;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.containers.WSection;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.containers.WVerticalList;
import minegame159.meteorclient.gui.widgets.containers.WView;
import minegame159.meteorclient.gui.widgets.containers.WWindow;
import minegame159.meteorclient.gui.widgets.input.WDoubleEdit;
import minegame159.meteorclient.gui.widgets.input.WDropdown;
import minegame159.meteorclient.gui.widgets.input.WIntEdit;
import minegame159.meteorclient.gui.widgets.input.WSlider;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.gui.widgets.pressable.WCheckbox;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.gui.widgets.pressable.WPlus;
import minegame159.meteorclient.gui.widgets.pressable.WTriangle;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.settings.Settings;
import minegame159.meteorclient.systems.accounts.Account;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.misc.Keybind;
import minegame159.meteorclient.utils.misc.Names;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.client.gui.screen.Screen;

public abstract class GuiTheme
implements ISerializable<GuiTheme> {
    protected /* synthetic */ SettingsWidgetFactory settingsFactory;
    public final /* synthetic */ String name;
    protected final /* synthetic */ Map<String, WindowConfig> windowConfigs;
    public final /* synthetic */ Settings settings;
    public /* synthetic */ boolean disableHoverColor;
    public static final /* synthetic */ double TITLE_TEXT_SCALE = 1.25;

    public boolean isModulesScreen(Screen lllllllllllllllllIIIIllllIIIllII) {
        return lllllllllllllllllIIIIllllIIIllII instanceof ModulesScreen;
    }

    public WItemWithLabel itemWithLabel(ItemStack lllllllllllllllllIIIIlllllIllIll, String lllllllllllllllllIIIIlllllIllIlI) {
        GuiTheme lllllllllllllllllIIIIlllllIllIIl;
        return lllllllllllllllllIIIIlllllIllIIl.w(new WItemWithLabel(lllllllllllllllllIIIIlllllIllIll, lllllllllllllllllIIIIlllllIllIlI));
    }

    public abstract double scale(double var1);

    public WIntEdit intEdit(int lllllllllllllllllIIIIllllIlllIII, int lllllllllllllllllIIIIllllIlllIll, int lllllllllllllllllIIIIllllIllIllI) {
        GuiTheme lllllllllllllllllIIIIllllIlllIIl;
        return lllllllllllllllllIIIIllllIlllIIl.w(new WIntEdit(lllllllllllllllllIIIIllllIlllIII, lllllllllllllllllIIIIllllIlllIll, lllllllllllllllllIIIIllllIllIllI));
    }

    public abstract WQuad quad(Color var1);

    public WTexture texture(double lllllllllllllllllIIIIlllllIIlIlI, double lllllllllllllllllIIIIlllllIIlIIl, double lllllllllllllllllIIIIlllllIIlIII, AbstractTexture lllllllllllllllllIIIIlllllIIIlll) {
        GuiTheme lllllllllllllllllIIIIlllllIIIllI;
        return lllllllllllllllllIIIIlllllIIIllI.w(new WTexture(lllllllllllllllllIIIIlllllIIlIlI, lllllllllllllllllIIIIlllllIIlIIl, lllllllllllllllllIIIIlllllIIlIII, lllllllllllllllllIIIIlllllIIIlll));
    }

    public abstract WWidget module(Module var1);

    public abstract TextRenderer textRenderer();

    public WLabel label(String lllllllllllllllllIIIlIIIIIllIIlI, boolean lllllllllllllllllIIIlIIIIIllIlII) {
        GuiTheme lllllllllllllllllIIIlIIIIIllIIll;
        return lllllllllllllllllIIIlIIIIIllIIll.label(lllllllllllllllllIIIlIIIIIllIIlI, lllllllllllllllllIIIlIIIIIllIlII, 0.0);
    }

    public WindowConfig getWindowConfig(String lllllllllllllllllIIIIlllIlIlllll) {
        GuiTheme lllllllllllllllllIIIIlllIllIIIII;
        WindowConfig lllllllllllllllllIIIIlllIlIllllI = lllllllllllllllllIIIIlllIllIIIII.windowConfigs.get(lllllllllllllllllIIIIlllIlIlllll);
        if (lllllllllllllllllIIIIlllIlIllllI != null) {
            return lllllllllllllllllIIIIlllIlIllllI;
        }
        lllllllllllllllllIIIIlllIlIllllI = new WindowConfig();
        lllllllllllllllllIIIIlllIllIIIII.windowConfigs.put(lllllllllllllllllIIIIlllIlIlllll, lllllllllllllllllIIIIlllIlIllllI);
        return lllllllllllllllllIIIIlllIlIllllI;
    }

    public WKeybind keybind(Keybind lllllllllllllllllIIIIllllIlIIIlI, boolean lllllllllllllllllIIIIllllIlIIlII) {
        GuiTheme lllllllllllllllllIIIIllllIlIIIll;
        return lllllllllllllllllIIIIllllIlIIIll.w(new WKeybind(lllllllllllllllllIIIIllllIlIIIlI, lllllllllllllllllIIIIllllIlIIlII));
    }

    public abstract WView view();

    public double textWidth(String lllllllllllllllllIIIIlllIlllIIII) {
        GuiTheme lllllllllllllllllIIIIlllIlllIIIl;
        return lllllllllllllllllIIIIlllIlllIIIl.textWidth(lllllllllllllllllIIIIlllIlllIIII, lllllllllllllllllIIIIlllIlllIIII.length(), false);
    }

    public WItem item(ItemStack lllllllllllllllllIIIIllllllIIIII) {
        GuiTheme lllllllllllllllllIIIIllllllIIIIl;
        return lllllllllllllllllIIIIllllllIIIIl.w(new WItem(lllllllllllllllllIIIIllllllIIIII));
    }

    public double textHeight(boolean lllllllllllllllllIIIIlllIllIllII) {
        GuiTheme lllllllllllllllllIIIIlllIllIllIl;
        return lllllllllllllllllIIIIlllIllIllIl.scale(lllllllllllllllllIIIIlllIllIllIl.textRenderer().getHeight() * (lllllllllllllllllIIIIlllIllIllII ? 1.25 : 1.0));
    }

    public abstract WTriangle triangle();

    public WLabel label(String lllllllllllllllllIIIlIIIIIlIIlII) {
        GuiTheme lllllllllllllllllIIIlIIIIIlIIIll;
        return lllllllllllllllllIIIlIIIIIlIIIll.label(lllllllllllllllllIIIlIIIIIlIIlII, false);
    }

    public abstract boolean categoryIcons();

    public double pad() {
        GuiTheme lllllllllllllllllIIIIlllIllIIlIl;
        return lllllllllllllllllIIIIlllIllIIlIl.scale(6.0);
    }

    public WWidget settings(Settings lllllllllllllllllIIIIllllIIlllII, String lllllllllllllllllIIIIllllIIllIII) {
        GuiTheme lllllllllllllllllIIIIllllIIlllIl;
        return lllllllllllllllllIIIIllllIIlllIl.settingsFactory.create(lllllllllllllllllIIIIllllIIlllIl, lllllllllllllllllIIIIllllIIlllII, lllllllllllllllllIIIIllllIIllIII);
    }

    public GuiTheme(String lllllllllllllllllIIIlIIIIIllllIl) {
        GuiTheme lllllllllllllllllIIIlIIIIIlllllI;
        lllllllllllllllllIIIlIIIIIlllllI.settings = new Settings();
        lllllllllllllllllIIIlIIIIIlllllI.windowConfigs = new HashMap<String, WindowConfig>();
        lllllllllllllllllIIIlIIIIIlllllI.name = lllllllllllllllllIIIlIIIIIllllIl;
    }

    public WLabel label(String lllllllllllllllllIIIlIIIIIlIllII, double lllllllllllllllllIIIlIIIIIlIlIII) {
        GuiTheme lllllllllllllllllIIIlIIIIIlIllIl;
        return lllllllllllllllllIIIlIIIIIlIllIl.label(lllllllllllllllllIIIlIIIIIlIllII, false, lllllllllllllllllIIIlIIIIIlIlIII);
    }

    public WDoubleEdit doubleEdit(double lllllllllllllllllIIIIllllIlIllII, double lllllllllllllllllIIIIllllIlIllll, double lllllllllllllllllIIIIllllIlIlIlI) {
        GuiTheme lllllllllllllllllIIIIllllIllIIIl;
        return lllllllllllllllllIIIIllllIllIIIl.w(new WDoubleEdit(lllllllllllllllllIIIIllllIlIllII, lllllllllllllllllIIIIllllIlIllll, lllllllllllllllllIIIIllllIlIlIlI));
    }

    protected <T extends WWidget> T w(T lllllllllllllllllIIIIlllIlIlIlll) {
        GuiTheme lllllllllllllllllIIIIlllIlIllIII;
        lllllllllllllllllIIIIlllIlIlIlll.theme = lllllllllllllllllIIIIlllIlIllIII;
        return lllllllllllllllllIIIIlllIlIlIlll;
    }

    protected abstract WButton button(String var1, GuiTexture var2);

    public WHorizontalSeparator horizontalSeparator() {
        GuiTheme lllllllllllllllllIIIlIIIIIIlllll;
        return lllllllllllllllllIIIlIIIIIIlllll.horizontalSeparator(null);
    }

    public abstract WPlus plus();

    public abstract WSlider slider(double var1, double var3, double var5);

    public abstract WSection section(String var1, boolean var2, WWidget var3);

    public WTable table() {
        GuiTheme lllllllllllllllllIIIIlllllllIllI;
        return lllllllllllllllllIIIIlllllllIllI.w(new WTable());
    }

    public WSection section(String lllllllllllllllllIIIIlllllllIIII, boolean lllllllllllllllllIIIIllllllIllII) {
        GuiTheme lllllllllllllllllIIIIllllllIlllI;
        return lllllllllllllllllIIIIllllllIlllI.section(lllllllllllllllllIIIIlllllllIIII, lllllllllllllllllIIIIllllllIllII, null);
    }

    public abstract <T> WDropdown<T> dropdown(T[] var1, T var2);

    public abstract WLabel label(String var1, boolean var2, double var3);

    public WTextBox textBox(String lllllllllllllllllIIIlIIIIIIIllll) {
        GuiTheme lllllllllllllllllIIIlIIIIIIlIIII;
        return lllllllllllllllllIIIlIIIIIIlIIII.textBox(lllllllllllllllllIIIlIIIIIIIllll, (lllllllllllllllllIIIIlllIIllIIll, lllllllllllllllllIIIIlllIIllIIlI) -> true);
    }

    public void beforeRender() {
        lllllllllllllllllIIIlIIIIIlllIll.disableHoverColor = false;
    }

    public TabScreen modulesScreen() {
        GuiTheme lllllllllllllllllIIIIllllIIlIIII;
        return new ModulesScreen(lllllllllllllllllIIIIllllIIlIIII);
    }

    @Override
    public NbtCompound toTag() {
        GuiTheme lllllllllllllllllIIIIlllIlIIlIll;
        NbtCompound lllllllllllllllllIIIIlllIlIIllIl = new NbtCompound();
        lllllllllllllllllIIIIlllIlIIllIl.putString("name", lllllllllllllllllIIIIlllIlIIlIll.name);
        lllllllllllllllllIIIIlllIlIIllIl.put("settings", (NbtElement)lllllllllllllllllIIIIlllIlIIlIll.settings.toTag());
        NbtCompound lllllllllllllllllIIIIlllIlIIllII = new NbtCompound();
        for (String lllllllllllllllllIIIIlllIlIIllll : lllllllllllllllllIIIIlllIlIIlIll.windowConfigs.keySet()) {
            lllllllllllllllllIIIIlllIlIIllII.put(lllllllllllllllllIIIIlllIlIIllll, (NbtElement)lllllllllllllllllIIIIlllIlIIlIll.windowConfigs.get(lllllllllllllllllIIIIlllIlIIllll).toTag());
        }
        lllllllllllllllllIIIIlllIlIIllIl.put("windowConfigs", (NbtElement)lllllllllllllllllIIIIlllIlIIllII);
        return lllllllllllllllllIIIIlllIlIIllIl;
    }

    public abstract WCheckbox checkbox(boolean var1);

    public abstract WMinus minus();

    public WItemWithLabel itemWithLabel(ItemStack lllllllllllllllllIIIIlllllIlIIIl) {
        GuiTheme lllllllllllllllllIIIIlllllIlIlII;
        return lllllllllllllllllIIIIlllllIlIlII.itemWithLabel(lllllllllllllllllIIIIlllllIlIIIl, Names.get(lllllllllllllllllIIIIlllllIlIIIl.getItem()));
    }

    public double textHeight() {
        GuiTheme lllllllllllllllllIIIIlllIllIlIII;
        return lllllllllllllllllIIIIlllIllIlIII.textHeight(false);
    }

    public abstract WHorizontalSeparator horizontalSeparator(String var1);

    public WHorizontalList horizontalList() {
        GuiTheme lllllllllllllllllIIIIllllllllIIl;
        return lllllllllllllllllIIIIllllllllIIl.w(new WHorizontalList());
    }

    public WWidget settings(Settings lllllllllllllllllIIIIllllIIlIIlI) {
        GuiTheme lllllllllllllllllIIIIllllIIlIlIl;
        return lllllllllllllllllIIIIllllIIlIlIl.settings(lllllllllllllllllIIIIllllIIlIIlI, "");
    }

    public abstract WWindow window(String var1);

    public abstract WTooltip tooltip(String var1);

    public abstract boolean blur();

    public WVerticalList verticalList() {
        GuiTheme lllllllllllllllllIIIIllllllllIll;
        return lllllllllllllllllIIIIllllllllIll.w(new WVerticalList());
    }

    public abstract WTextBox textBox(String var1, CharFilter var2);

    @Override
    public GuiTheme fromTag(NbtCompound lllllllllllllllllIIIIlllIIllllll) {
        GuiTheme lllllllllllllllllIIIIlllIlIIIIII;
        lllllllllllllllllIIIIlllIlIIIIII.settings.fromTag(lllllllllllllllllIIIIlllIIllllll.getCompound("settings"));
        NbtCompound lllllllllllllllllIIIIlllIIlllllI = lllllllllllllllllIIIIlllIIllllll.getCompound("windowConfigs");
        for (String lllllllllllllllllIIIIlllIlIIIIIl : lllllllllllllllllIIIIlllIIlllllI.getKeys()) {
            lllllllllllllllllIIIIlllIlIIIIII.windowConfigs.put(lllllllllllllllllIIIIlllIlIIIIIl, (WindowConfig)new WindowConfig().fromTag(lllllllllllllllllIIIIlllIIlllllI.getCompound(lllllllllllllllllIIIIlllIlIIIIIl)));
        }
        return lllllllllllllllllIIIIlllIlIIIIII;
    }

    public abstract WTopBar topBar();

    public abstract WVerticalSeparator verticalSeparator();

    public double textWidth(String lllllllllllllllllIIIIlllIlllllII, int lllllllllllllllllIIIIlllIllllIll, boolean lllllllllllllllllIIIIlllIlllIllI) {
        GuiTheme lllllllllllllllllIIIIlllIlllllIl;
        return lllllllllllllllllIIIIlllIlllllIl.scale(lllllllllllllllllIIIIlllIlllllIl.textRenderer().getWidth(lllllllllllllllllIIIIlllIlllllII, lllllllllllllllllIIIIlllIllllIll) * (lllllllllllllllllIIIIlllIlllIllI ? 1.25 : 1.0));
    }

    public abstract WAccount account(WidgetScreen var1, Account<?> var2);

    public WButton button(GuiTexture lllllllllllllllllIIIlIIIIIIlIlIl) {
        GuiTheme lllllllllllllllllIIIlIIIIIIlIlII;
        return lllllllllllllllllIIIlIIIIIIlIlII.button(null, lllllllllllllllllIIIlIIIIIIlIlIl);
    }

    public WidgetScreen accountsScreen() {
        GuiTheme lllllllllllllllllIIIIllllIIIIIll;
        return new AccountsScreen(lllllllllllllllllIIIIllllIIIIIll);
    }

    public <T extends Enum<?>> WDropdown<T> dropdown(T lllllllllllllllllIIIlIIIIIIIIIIl) {
        GuiTheme lllllllllllllllllIIIlIIIIIIIIllI;
        Class<?> lllllllllllllllllIIIlIIIIIIIIlII = lllllllllllllllllIIIlIIIIIIIIIIl.getClass();
        Enum[] lllllllllllllllllIIIlIIIIIIIIIll = null;
        try {
            lllllllllllllllllIIIlIIIIIIIIIll = (Enum[])lllllllllllllllllIIIlIIIIIIIIlII.getDeclaredMethod("values", new Class[0]).invoke(null, new Object[0]);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException lllllllllllllllllIIIlIIIIIIIIlll) {
            lllllllllllllllllIIIlIIIIIIIIlll.printStackTrace();
        }
        return lllllllllllllllllIIIlIIIIIIIIllI.dropdown(lllllllllllllllllIIIlIIIIIIIIIll, lllllllllllllllllIIIlIIIIIIIIIIl);
    }

    public WidgetScreen moduleScreen(Module lllllllllllllllllIIIIllllIIIIlll) {
        GuiTheme lllllllllllllllllIIIIllllIIIIllI;
        return new ModuleScreen(lllllllllllllllllIIIIllllIIIIllI, lllllllllllllllllIIIIllllIIIIlll);
    }

    public WSection section(String lllllllllllllllllIIIIllllllIlIII) {
        GuiTheme lllllllllllllllllIIIIllllllIlIIl;
        return lllllllllllllllllIIIIllllllIlIIl.section(lllllllllllllllllIIIIllllllIlIII, true);
    }

    public WButton button(String lllllllllllllllllIIIlIIIIIIllIIl) {
        GuiTheme lllllllllllllllllIIIlIIIIIIllIlI;
        return lllllllllllllllllIIIlIIIIIIllIlI.button(lllllllllllllllllIIIlIIIIIIllIIl, null);
    }
}

