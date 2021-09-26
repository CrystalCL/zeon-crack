/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.screens.settings;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WPressable;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Pair;

public abstract class LeftRightListSettingScreen<T>
extends WindowScreen {
    private final WTextBox filter;
    private String filterText = "";
    private WTable table;
    protected final Setting<List<T>> setting;

    private static int lambda$abc$8(Pair Pair2) {
        return -((Integer)Pair2.getRight()).intValue();
    }

    protected T getAdditionalValue(T t) {
        return null;
    }

    private static void lambda$abc$6(Consumer consumer, Object object) {
        consumer.accept(object);
    }

    private void lambda$initWidgets$1(List list, Object object) {
        if (this.skipValue(object) || this.setting.get().contains(object)) {
            return;
        }
        int n = Utils.search(this.getValueName(object), this.filterText);
        if (n > 0) {
            list.add(new Pair(object, (Object)n));
        }
    }

    private void lambda$initWidgets$5(Registry Registry2, Object object) {
        this.removeValue(Registry2, object);
        Object object2 = this.getAdditionalValue(object);
        if (object2 != null) {
            this.removeValue(Registry2, object2);
        }
    }

    private void lambda$new$0(Registry Registry2) {
        this.filterText = this.filter.get().trim();
        this.table.clear();
        this.initWidgets(Registry2);
    }

    private void lambda$initWidgets$3(Registry Registry2, Object object) {
        this.addValue(Registry2, object);
        Object object2 = this.getAdditionalValue(object);
        if (object2 != null) {
            this.addValue(Registry2, object2);
        }
    }

    private void lambda$initWidgets$4(List list) {
        for (T t : this.setting.get()) {
            int n;
            if (this.skipValue(t) || (n = Utils.search(this.getValueName(t), this.filterText)) <= 0) continue;
            list.add(new Pair(t, (Object)n));
        }
    }

    protected boolean includeValue(T t) {
        return true;
    }

    protected abstract WWidget getValueWidget(T var1);

    protected abstract String getValueName(T var1);

    private WTable abc(Consumer<List<Pair<T, Integer>>> consumer, boolean bl, Consumer<T> consumer2) {
        Cell<WTable> cell = this.table.add(this.theme.table()).top();
        WTable wTable = cell.widget();
        Consumer<Object> consumer3 = arg_0 -> this.lambda$abc$7(wTable, bl, consumer2, arg_0);
        ArrayList<Pair> arrayList = new ArrayList<Pair>();
        consumer.accept(arrayList);
        if (!this.filterText.isEmpty()) {
            arrayList.sort(Comparator.comparingInt(LeftRightListSettingScreen::lambda$abc$8));
        }
        for (Pair Pair2 : arrayList) {
            consumer3.accept(Pair2.getLeft());
        }
        if (wTable.cells.size() > 0) {
            cell.expandX();
        }
        return wTable;
    }

    protected boolean skipValue(T t) {
        return false;
    }

    private void addValue(Registry<T> Registry2, T t) {
        if (!this.setting.get().contains(t)) {
            this.setting.get().add(t);
            this.setting.changed();
            this.table.clear();
            this.initWidgets(Registry2);
        }
    }

    private void lambda$abc$7(WTable wTable, boolean bl, Consumer consumer, Object object) {
        if (!this.includeValue(object)) {
            return;
        }
        wTable.add(this.getValueWidget(object));
        WPressable wPressable = wTable.add(bl ? this.theme.plus() : this.theme.minus()).expandCellX().right().widget();
        wPressable.action = () -> LeftRightListSettingScreen.lambda$abc$6(consumer, object);
        wTable.row();
    }

    public LeftRightListSettingScreen(GuiTheme guiTheme, String string, Setting<List<T>> setting, Registry<T> Registry2) {
        super(guiTheme, string);
        this.setting = setting;
        this.filter = this.add(guiTheme.textBox("")).minWidth(400.0).expandX().widget();
        this.filter.setFocused(true);
        this.filter.action = () -> this.lambda$new$0(Registry2);
        this.table = this.add(guiTheme.table()).expandX().widget();
        this.initWidgets(Registry2);
    }

    private void removeValue(Registry<T> Registry2, T t) {
        if (this.setting.get().remove(t)) {
            this.setting.changed();
            this.table.clear();
            this.initWidgets(Registry2);
        }
    }

    private void initWidgets(Registry<T> Registry2) {
        WTable wTable = this.abc(arg_0 -> this.lambda$initWidgets$2(Registry2, arg_0), true, arg_0 -> this.lambda$initWidgets$3(Registry2, arg_0));
        if (wTable.cells.size() > 0) {
            this.table.add(this.theme.verticalSeparator()).expandWidgetY();
        }
        this.abc(this::lambda$initWidgets$4, false, arg_0 -> this.lambda$initWidgets$5(Registry2, arg_0));
    }

    private void lambda$initWidgets$2(Registry Registry2, List list) {
        Registry2.forEach(arg_0 -> this.lambda$initWidgets$1(list, arg_0));
    }
}

