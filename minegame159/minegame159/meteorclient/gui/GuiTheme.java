/*
 * Decompiled with CFR 0.151.
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class GuiTheme
implements ISerializable<GuiTheme> {
    public final Settings settings = new Settings();
    public static final double TITLE_TEXT_SCALE = 1.25;
    protected SettingsWidgetFactory settingsFactory;
    public boolean disableHoverColor;
    protected final Map<String, WindowConfig> windowConfigs = new HashMap<String, WindowConfig>();
    public final String name;

    public WTable table() {
        return this.w(new WTable());
    }

    public abstract WQuad quad(Color var1);

    public abstract WPlus plus();

    public WItemWithLabel itemWithLabel(ItemStack ItemStack2, String string) {
        return this.w(new WItemWithLabel(ItemStack2, string));
    }

    public double textHeight() {
        return this.textHeight(false);
    }

    public WTextBox textBox(String string) {
        return this.textBox(string, GuiTheme::lambda$textBox$0);
    }

    public void beforeRender() {
        this.disableHoverColor = false;
    }

    public abstract WVerticalSeparator verticalSeparator();

    public abstract WAccount account(WidgetScreen var1, Account<?> var2);

    public abstract WTopBar topBar();

    public WidgetScreen accountsScreen() {
        return new AccountsScreen(this);
    }

    public abstract boolean blur();

    @Override
    public GuiTheme fromTag(NbtCompound NbtCompound2) {
        this.settings.fromTag(NbtCompound2.getCompound("settings"));
        NbtCompound NbtCompound3 = NbtCompound2.getCompound("windowConfigs");
        for (String string : NbtCompound3.getKeys()) {
            this.windowConfigs.put(string, new WindowConfig().fromTag(NbtCompound3.getCompound(string)));
        }
        return this;
    }

    public WidgetScreen moduleScreen(Module module) {
        return new ModuleScreen(this, module);
    }

    protected <T extends WWidget> T w(T t) {
        t.theme = this;
        return t;
    }

    public <T extends Enum<?>> WDropdown<T> dropdown(T t) {
        Class<?> clazz = t.getClass();
        Enum[] enumArray = null;
        try {
            enumArray = (Enum[])clazz.getDeclaredMethod("values", new Class[0]).invoke(null, new Object[0]);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
        }
        return this.dropdown(enumArray, t);
    }

    public double textWidth(String string) {
        return this.textWidth(string, string.length(), false);
    }

    public abstract WSection section(String var1, boolean var2, WWidget var3);

    public abstract double scale(double var1);

    public abstract WHorizontalSeparator horizontalSeparator(String var1);

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public abstract boolean categoryIcons();

    public GuiTheme(String string) {
        this.name = string;
    }

    public WSection section(String string) {
        return this.section(string, true);
    }

    public WIntEdit intEdit(int n, int n2, int n3) {
        return this.w(new WIntEdit(n, n2, n3));
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("name", this.name);
        NbtCompound2.put("settings", (NbtElement)this.settings.toTag());
        NbtCompound NbtCompound3 = new NbtCompound();
        for (String string : this.windowConfigs.keySet()) {
            NbtCompound3.put(string, (NbtElement)this.windowConfigs.get(string).toTag());
        }
        NbtCompound2.put("windowConfigs", (NbtElement)NbtCompound3);
        return NbtCompound2;
    }

    public abstract WTriangle triangle();

    public TabScreen modulesScreen() {
        return new ModulesScreen(this);
    }

    public WItemWithLabel itemWithLabel(ItemStack ItemStack2) {
        return this.itemWithLabel(ItemStack2, Names.get(ItemStack2.getItem()));
    }

    public WindowConfig getWindowConfig(String string) {
        WindowConfig windowConfig = this.windowConfigs.get(string);
        if (windowConfig != null) {
            return windowConfig;
        }
        windowConfig = new WindowConfig();
        this.windowConfigs.put(string, windowConfig);
        return windowConfig;
    }

    public boolean isModulesScreen(Screen Screen2) {
        return Screen2 instanceof ModulesScreen;
    }

    public WLabel label(String string, double d) {
        return this.label(string, false, d);
    }

    public double pad() {
        return this.scale(6.0);
    }

    public WItem item(ItemStack ItemStack2) {
        return this.w(new WItem(ItemStack2));
    }

    public WWidget settings(Settings settings) {
        return this.settings(settings, "");
    }

    public WTexture texture(double d, double d2, double d3, AbstractTexture AbstractTexture2) {
        return this.w(new WTexture(d, d2, d3, AbstractTexture2));
    }

    public double textHeight(boolean bl) {
        return this.scale(this.textRenderer().getHeight() * (bl ? 1.25 : 1.0));
    }

    public WWidget settings(Settings settings, String string) {
        return this.settingsFactory.create(this, settings, string);
    }

    public abstract WWidget module(Module var1);

    public abstract WSlider slider(double var1, double var3, double var5);

    public WSection section(String string, boolean bl) {
        return this.section(string, bl, null);
    }

    public WDoubleEdit doubleEdit(double d, double d2, double d3) {
        return this.w(new WDoubleEdit(d, d2, d3));
    }

    public WButton button(GuiTexture guiTexture) {
        return this.button(null, guiTexture);
    }

    public abstract WView view();

    public abstract WMinus minus();

    public abstract TextRenderer textRenderer();

    public abstract WCheckbox checkbox(boolean var1);

    public double textWidth(String string, int n, boolean bl) {
        return this.scale(this.textRenderer().getWidth(string, n) * (bl ? 1.25 : 1.0));
    }

    public WHorizontalSeparator horizontalSeparator() {
        return this.horizontalSeparator(null);
    }

    private static boolean lambda$textBox$0(String string, char c) {
        return true;
    }

    public WKeybind keybind(Keybind keybind, boolean bl) {
        return this.w(new WKeybind(keybind, bl));
    }

    public WButton button(String string) {
        return this.button(string, null);
    }

    public WLabel label(String string) {
        return this.label(string, false);
    }

    public abstract WTooltip tooltip(String var1);

    public abstract WLabel label(String var1, boolean var2, double var3);

    public WLabel label(String string, boolean bl) {
        return this.label(string, bl, 0.0);
    }

    public WHorizontalList horizontalList() {
        return this.w(new WHorizontalList());
    }

    public abstract WTextBox textBox(String var1, CharFilter var2);

    protected abstract WButton button(String var1, GuiTexture var2);

    public abstract <T> WDropdown<T> dropdown(T[] var1, T var2);

    public WVerticalList verticalList() {
        return this.w(new WVerticalList());
    }

    public abstract WWindow window(String var1);
}

