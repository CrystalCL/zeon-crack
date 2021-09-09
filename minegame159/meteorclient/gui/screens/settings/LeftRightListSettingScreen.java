/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.util.Pair
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
    protected final /* synthetic */ Setting<List<T>> setting;
    private /* synthetic */ WTable table;
    private final /* synthetic */ WTextBox filter;
    private /* synthetic */ String filterText;

    protected abstract String getValueName(T var1);

    private void addValue(Registry<T> lllllllllllllllllIIlIlIIllIlIIII, T lllllllllllllllllIIlIlIIllIlIIlI) {
        LeftRightListSettingScreen lllllllllllllllllIIlIlIIllIlIIIl;
        if (!lllllllllllllllllIIlIlIIllIlIIIl.setting.get().contains(lllllllllllllllllIIlIlIIllIlIIlI)) {
            lllllllllllllllllIIlIlIIllIlIIIl.setting.get().add(lllllllllllllllllIIlIlIIllIlIIlI);
            lllllllllllllllllIIlIlIIllIlIIIl.setting.changed();
            lllllllllllllllllIIlIlIIllIlIIIl.table.clear();
            lllllllllllllllllIIlIlIIllIlIIIl.initWidgets(lllllllllllllllllIIlIlIIllIlIIII);
        }
    }

    private WTable abc(Consumer<List<Pair<T, Integer>>> lllllllllllllllllIIlIlIIlIlllIIl, boolean lllllllllllllllllIIlIlIIlIlllIII, Consumer<T> lllllllllllllllllIIlIlIIlIllIlll) {
        LeftRightListSettingScreen lllllllllllllllllIIlIlIIlIllIIlI;
        Cell<WTable> lllllllllllllllllIIlIlIIlIllIllI = lllllllllllllllllIIlIlIIlIllIIlI.table.add(lllllllllllllllllIIlIlIIlIllIIlI.theme.table()).top();
        WTable lllllllllllllllllIIlIlIIlIllIlIl = lllllllllllllllllIIlIlIIlIllIllI.widget();
        Consumer<Object> lllllllllllllllllIIlIlIIlIllIlII = lllllllllllllllllIIlIlIIlIIIllll -> {
            LeftRightListSettingScreen lllllllllllllllllIIlIlIIlIIllIIl;
            if (!lllllllllllllllllIIlIlIIlIIllIIl.includeValue(lllllllllllllllllIIlIlIIlIIIllll)) {
                return;
            }
            lllllllllllllllllIIlIlIIlIllIlIl.add(lllllllllllllllllIIlIlIIlIIllIIl.getValueWidget(lllllllllllllllllIIlIlIIlIIIllll));
            WPressable lllllllllllllllllIIlIlIIlIIlIlII = lllllllllllllllllIIlIlIIlIllIlIl.add(lllllllllllllllllIIlIlIIlIlllIII ? lllllllllllllllllIIlIlIIlIIllIIl.theme.plus() : lllllllllllllllllIIlIlIIlIIllIIl.theme.minus()).expandCellX().right().widget();
            lllllllllllllllllIIlIlIIlIIlIlII.action = () -> lllllllllllllllllIIlIlIIlIllIlll.accept(lllllllllllllllllIIlIlIIlIIIllll);
            lllllllllllllllllIIlIlIIlIllIlIl.row();
        };
        ArrayList<Pair> lllllllllllllllllIIlIlIIlIllIIll = new ArrayList<Pair>();
        lllllllllllllllllIIlIlIIlIlllIIl.accept(lllllllllllllllllIIlIlIIlIllIIll);
        if (!lllllllllllllllllIIlIlIIlIllIIlI.filterText.isEmpty()) {
            lllllllllllllllllIIlIlIIlIllIIll.sort(Comparator.comparingInt(lllllllllllllllllIIlIlIIlIlIIIII -> -((Integer)lllllllllllllllllIIlIlIIlIlIIIII.getRight()).intValue()));
        }
        for (Pair lllllllllllllllllIIlIlIIlIlllIll : lllllllllllllllllIIlIlIIlIllIIll) {
            lllllllllllllllllIIlIlIIlIllIlII.accept(lllllllllllllllllIIlIlIIlIlllIll.getLeft());
        }
        if (lllllllllllllllllIIlIlIIlIllIlIl.cells.size() > 0) {
            lllllllllllllllllIIlIlIIlIllIllI.expandX();
        }
        return lllllllllllllllllIIlIlIIlIllIlIl;
    }

    protected boolean includeValue(T lllllllllllllllllIIlIlIIlIlIIlll) {
        return true;
    }

    private void initWidgets(Registry<T> lllllllllllllllllIIlIlIIllIllIIl) {
        LeftRightListSettingScreen lllllllllllllllllIIlIlIIllIlllIl;
        WTable lllllllllllllllllIIlIlIIllIllIll = lllllllllllllllllIIlIlIIllIlllIl.abc(lllllllllllllllllIIlIlIIIlIlllII -> {
            LeftRightListSettingScreen lllllllllllllllllIIlIlIIIlIllllI;
            lllllllllllllllllIIlIlIIllIllIIl.forEach(lllllllllllllllllIIlIlIIIlIlIIlI -> {
                LeftRightListSettingScreen lllllllllllllllllIIlIlIIIlIlIIII;
                if (lllllllllllllllllIIlIlIIIlIlIIII.skipValue(lllllllllllllllllIIlIlIIIlIlIIlI) || lllllllllllllllllIIlIlIIIlIlIIII.setting.get().contains(lllllllllllllllllIIlIlIIIlIlIIlI)) {
                    return;
                }
                int lllllllllllllllllIIlIlIIIlIlIIIl = Utils.search(lllllllllllllllllIIlIlIIIlIlIIII.getValueName(lllllllllllllllllIIlIlIIIlIlIIlI), lllllllllllllllllIIlIlIIIlIlIIII.filterText);
                if (lllllllllllllllllIIlIlIIIlIlIIIl > 0) {
                    lllllllllllllllllIIlIlIIIlIlllII.add(new Pair(lllllllllllllllllIIlIlIIIlIlIIlI, (Object)lllllllllllllllllIIlIlIIIlIlIIIl));
                }
            });
        }, true, lllllllllllllllllIIlIlIIIllIIIll -> {
            LeftRightListSettingScreen lllllllllllllllllIIlIlIIIllIlIIl;
            lllllllllllllllllIIlIlIIIllIlIIl.addValue(lllllllllllllllllIIlIlIIllIllIIl, lllllllllllllllllIIlIlIIIllIIIll);
            Object lllllllllllllllllIIlIlIIIllIIllI = lllllllllllllllllIIlIlIIIllIlIIl.getAdditionalValue(lllllllllllllllllIIlIlIIIllIIIll);
            if (lllllllllllllllllIIlIlIIIllIIllI != null) {
                lllllllllllllllllIIlIlIIIllIlIIl.addValue(lllllllllllllllllIIlIlIIllIllIIl, lllllllllllllllllIIlIlIIIllIIllI);
            }
        });
        if (lllllllllllllllllIIlIlIIllIllIll.cells.size() > 0) {
            lllllllllllllllllIIlIlIIllIlllIl.table.add(lllllllllllllllllIIlIlIIllIlllIl.theme.verticalSeparator()).expandWidgetY();
        }
        lllllllllllllllllIIlIlIIllIlllIl.abc(lllllllllllllllllIIlIlIIIlllIIll -> {
            LeftRightListSettingScreen lllllllllllllllllIIlIlIIIlllIlII;
            for (T lllllllllllllllllIIlIlIIIlllIlIl : lllllllllllllllllIIlIlIIIlllIlII.setting.get()) {
                int lllllllllllllllllIIlIlIIIlllIllI;
                if (lllllllllllllllllIIlIlIIIlllIlII.skipValue(lllllllllllllllllIIlIlIIIlllIlIl) || (lllllllllllllllllIIlIlIIIlllIllI = Utils.search(lllllllllllllllllIIlIlIIIlllIlII.getValueName(lllllllllllllllllIIlIlIIIlllIlIl), lllllllllllllllllIIlIlIIIlllIlII.filterText)) <= 0) continue;
                lllllllllllllllllIIlIlIIIlllIIll.add(new Pair(lllllllllllllllllIIlIlIIIlllIlIl, (Object)lllllllllllllllllIIlIlIIIlllIllI));
            }
        }, false, lllllllllllllllllIIlIlIIIlllllIl -> {
            LeftRightListSettingScreen lllllllllllllllllIIlIlIIIlllllll;
            lllllllllllllllllIIlIlIIIlllllll.removeValue(lllllllllllllllllIIlIlIIllIllIIl, lllllllllllllllllIIlIlIIIlllllIl);
            Object lllllllllllllllllIIlIlIIlIIIIIII = lllllllllllllllllIIlIlIIIlllllll.getAdditionalValue(lllllllllllllllllIIlIlIIIlllllIl);
            if (lllllllllllllllllIIlIlIIlIIIIIII != null) {
                lllllllllllllllllIIlIlIIIlllllll.removeValue(lllllllllllllllllIIlIlIIllIllIIl, lllllllllllllllllIIlIlIIlIIIIIII);
            }
        });
    }

    public LeftRightListSettingScreen(GuiTheme lllllllllllllllllIIlIlIIlllIlIIl, String lllllllllllllllllIIlIlIIlllIIIll, Setting<List<T>> lllllllllllllllllIIlIlIIlllIIIlI, Registry<T> lllllllllllllllllIIlIlIIlllIIllI) {
        super(lllllllllllllllllIIlIlIIlllIlIIl, lllllllllllllllllIIlIlIIlllIIIll);
        LeftRightListSettingScreen lllllllllllllllllIIlIlIIlllIIlIl;
        lllllllllllllllllIIlIlIIlllIIlIl.filterText = "";
        lllllllllllllllllIIlIlIIlllIIlIl.setting = lllllllllllllllllIIlIlIIlllIIIlI;
        lllllllllllllllllIIlIlIIlllIIlIl.filter = lllllllllllllllllIIlIlIIlllIIlIl.add(lllllllllllllllllIIlIlIIlllIlIIl.textBox("")).minWidth(400.0).expandX().widget();
        lllllllllllllllllIIlIlIIlllIIlIl.filter.setFocused(true);
        lllllllllllllllllIIlIlIIlllIIlIl.filter.action = () -> {
            LeftRightListSettingScreen lllllllllllllllllIIlIlIIIlIIlIII;
            lllllllllllllllllIIlIlIIIlIIlIII.filterText = lllllllllllllllllIIlIlIIIlIIlIII.filter.get().trim();
            lllllllllllllllllIIlIlIIIlIIlIII.table.clear();
            lllllllllllllllllIIlIlIIIlIIlIII.initWidgets(lllllllllllllllllIIlIlIIlllIIllI);
        };
        lllllllllllllllllIIlIlIIlllIIlIl.table = lllllllllllllllllIIlIlIIlllIIlIl.add(lllllllllllllllllIIlIlIIlllIlIIl.table()).expandX().widget();
        lllllllllllllllllIIlIlIIlllIIlIl.initWidgets(lllllllllllllllllIIlIlIIlllIIllI);
    }

    private void removeValue(Registry<T> lllllllllllllllllIIlIlIIllIIIlll, T lllllllllllllllllIIlIlIIllIIIllI) {
        LeftRightListSettingScreen lllllllllllllllllIIlIlIIllIIlIll;
        if (lllllllllllllllllIIlIlIIllIIlIll.setting.get().remove(lllllllllllllllllIIlIlIIllIIIllI)) {
            lllllllllllllllllIIlIlIIllIIlIll.setting.changed();
            lllllllllllllllllIIlIlIIllIIlIll.table.clear();
            lllllllllllllllllIIlIlIIllIIlIll.initWidgets(lllllllllllllllllIIlIlIIllIIIlll);
        }
    }

    protected abstract WWidget getValueWidget(T var1);

    protected T getAdditionalValue(T lllllllllllllllllIIlIlIIlIlIIIll) {
        return null;
    }

    protected boolean skipValue(T lllllllllllllllllIIlIlIIlIlIIlIl) {
        return false;
    }
}

