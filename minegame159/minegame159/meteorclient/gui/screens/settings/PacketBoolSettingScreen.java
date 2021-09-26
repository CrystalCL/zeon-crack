/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.screens.settings;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WPressable;
import minegame159.meteorclient.settings.PacketBoolSetting;
import minegame159.meteorclient.utils.network.PacketUtils;
import net.minecraft.network.Packet;
import org.apache.commons.lang3.StringUtils;

public class PacketBoolSettingScreen
extends WindowScreen {
    private WHorizontalList list;
    private final PacketBoolSetting setting;
    private String filterText = "";
    private final WTextBox filter;

    private void widget(WTable wTable, Class<? extends Packet<?>> clazz, String string, boolean bl) {
        wTable.add(this.theme.label(string)).expandCellX();
        WPressable wPressable = wTable.add(bl ? this.theme.plus() : this.theme.minus()).widget();
        wPressable.action = () -> this.lambda$widget$1(bl, clazz);
        wTable.row();
    }

    public PacketBoolSettingScreen(GuiTheme guiTheme, PacketBoolSetting packetBoolSetting) {
        super(guiTheme, "Select packets");
        this.setting = packetBoolSetting;
        this.filter = this.add(guiTheme.textBox("")).minWidth(400.0).expandX().widget();
        this.filter.setFocused(true);
        this.filter.action = this::lambda$new$0;
        this.list = this.add(guiTheme.horizontalList()).expandX().widget();
        this.initWidgets();
    }

    private void lambda$widget$1(boolean bl, Class clazz) {
        if (bl) {
            ((Object2BooleanMap)this.setting.get()).put((Object)clazz, true);
        } else {
            ((Object2BooleanMap)this.setting.get()).removeBoolean((Object)clazz);
        }
        this.setting.changed();
        this.list.clear();
        this.initWidgets();
    }

    private void initWidgets() {
        ArrayList<Class> arrayList = new ArrayList<Class>((Collection<Class>)((Object2BooleanMap)this.setting.get()).keySet());
        arrayList.sort(Comparator.comparing(PacketUtils::getName));
        Cell<WTable> cell = this.list.add(this.theme.table()).top();
        WTable wTable = cell.widget();
        this.list.add(this.theme.verticalSeparator()).expandWidgetY();
        Cell<WTable> cell2 = this.list.add(this.theme.table()).top();
        WTable wTable2 = cell2.widget();
        for (Class clazz : arrayList) {
            String string = PacketUtils.getName(clazz);
            if (!StringUtils.containsIgnoreCase((CharSequence)string, (CharSequence)this.filterText)) continue;
            if (((Object2BooleanMap)this.setting.get()).getBoolean((Object)clazz)) {
                this.widget(wTable2, clazz, string, false);
                continue;
            }
            this.widget(wTable, clazz, string, true);
        }
        if (wTable.cells.size() > 0) {
            cell.expandX();
        }
        if (wTable2.cells.size() > 0) {
            cell2.expandX();
        }
    }

    private void lambda$new$0() {
        this.filterText = this.filter.get().trim();
        this.list.clear();
        this.initWidgets();
    }
}

