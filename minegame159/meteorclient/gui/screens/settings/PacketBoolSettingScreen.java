/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.network.Packet
 *  org.apache.commons.lang3.StringUtils
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
    private /* synthetic */ String filterText;
    private final /* synthetic */ PacketBoolSetting setting;
    private /* synthetic */ WHorizontalList list;
    private final /* synthetic */ WTextBox filter;

    private void widget(WTable lIllIlIIllIlI, Class<? extends Packet<?>> lIllIlIIllIIl, String lIllIlIIlIIlI, boolean lIllIlIIlIlll) {
        PacketBoolSettingScreen lIllIlIIlIlIl;
        lIllIlIIllIlI.add(lIllIlIIlIlIl.theme.label(lIllIlIIlIIlI)).expandCellX();
        WPressable lIllIlIIlIllI = lIllIlIIllIlI.add(lIllIlIIlIlll ? lIllIlIIlIlIl.theme.plus() : lIllIlIIlIlIl.theme.minus()).widget();
        lIllIlIIlIllI.action = () -> {
            PacketBoolSettingScreen lIllIlIIIlIIl;
            if (lIllIlIIlIlll) {
                ((Object2BooleanMap)lIllIlIIIlIIl.setting.get()).put((Object)lIllIlIIllIIl, true);
            } else {
                ((Object2BooleanMap)lIllIlIIIlIIl.setting.get()).removeBoolean((Object)lIllIlIIllIIl);
            }
            lIllIlIIIlIIl.setting.changed();
            lIllIlIIIlIIl.list.clear();
            lIllIlIIIlIIl.initWidgets();
        };
        lIllIlIIllIlI.row();
    }

    public PacketBoolSettingScreen(GuiTheme lIllIllIIIIII, PacketBoolSetting lIllIlIllllII) {
        super(lIllIllIIIIII, "Select packets");
        PacketBoolSettingScreen lIllIlIlllllI;
        lIllIlIlllllI.filterText = "";
        lIllIlIlllllI.setting = lIllIlIllllII;
        lIllIlIlllllI.filter = lIllIlIlllllI.add(lIllIllIIIIII.textBox("")).minWidth(400.0).expandX().widget();
        lIllIlIlllllI.filter.setFocused(true);
        lIllIlIlllllI.filter.action = () -> {
            PacketBoolSettingScreen lIllIlIIIIlII;
            lIllIlIIIIlII.filterText = lIllIlIIIIlII.filter.get().trim();
            lIllIlIIIIlII.list.clear();
            lIllIlIIIIlII.initWidgets();
        };
        lIllIlIlllllI.list = lIllIlIlllllI.add(lIllIllIIIIII.horizontalList()).expandX().widget();
        lIllIlIlllllI.initWidgets();
    }

    private void initWidgets() {
        PacketBoolSettingScreen lIllIlIlIlIlI;
        ArrayList<Class> lIllIlIlIllll = new ArrayList<Class>((Collection<Class>)((Object2BooleanMap)lIllIlIlIlIlI.setting.get()).keySet());
        lIllIlIlIllll.sort(Comparator.comparing(PacketUtils::getName));
        Cell<WTable> lIllIlIlIlllI = lIllIlIlIlIlI.list.add(lIllIlIlIlIlI.theme.table()).top();
        WTable lIllIlIlIllIl = lIllIlIlIlllI.widget();
        lIllIlIlIlIlI.list.add(lIllIlIlIlIlI.theme.verticalSeparator()).expandWidgetY();
        Cell<WTable> lIllIlIlIllII = lIllIlIlIlIlI.list.add(lIllIlIlIlIlI.theme.table()).top();
        WTable lIllIlIlIlIll = lIllIlIlIllII.widget();
        for (Class lIllIlIllIIIl : lIllIlIlIllll) {
            String lIllIlIllIIlI = PacketUtils.getName(lIllIlIllIIIl);
            if (!StringUtils.containsIgnoreCase((CharSequence)lIllIlIllIIlI, (CharSequence)lIllIlIlIlIlI.filterText)) continue;
            if (((Object2BooleanMap)lIllIlIlIlIlI.setting.get()).getBoolean((Object)lIllIlIllIIIl)) {
                lIllIlIlIlIlI.widget(lIllIlIlIlIll, lIllIlIllIIIl, lIllIlIllIIlI, false);
                continue;
            }
            lIllIlIlIlIlI.widget(lIllIlIlIllIl, lIllIlIllIIIl, lIllIlIllIIlI, true);
        }
        if (lIllIlIlIllIl.cells.size() > 0) {
            lIllIlIlIlllI.expandX();
        }
        if (lIllIlIlIlIll.cells.size() > 0) {
            lIllIlIlIllII.expandX();
        }
    }
}

