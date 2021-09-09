/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  net.minecraft.entity.effect.StatusEffect
 *  org.apache.commons.lang3.StringUtils
 */
package minegame159.meteorclient.gui.screens.settings;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WIntEdit;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.misc.Names;
import net.minecraft.entity.effect.StatusEffect;
import org.apache.commons.lang3.StringUtils;

public class StatusEffectSettingScreen
extends WindowScreen {
    private final /* synthetic */ WTextBox filter;
    private /* synthetic */ WTable table;
    private final /* synthetic */ Setting<Object2IntMap<StatusEffect>> setting;
    private /* synthetic */ String filterText;

    public StatusEffectSettingScreen(GuiTheme lllllllllllllllllIIlIlllllIIIIIl, Setting<Object2IntMap<StatusEffect>> lllllllllllllllllIIlIlllllIIIIll) {
        super(lllllllllllllllllIIlIlllllIIIIIl, "Select potions");
        StatusEffectSettingScreen lllllllllllllllllIIlIlllllIIIlIl;
        lllllllllllllllllIIlIlllllIIIlIl.filterText = "";
        lllllllllllllllllIIlIlllllIIIlIl.setting = lllllllllllllllllIIlIlllllIIIIll;
        lllllllllllllllllIIlIlllllIIIlIl.filter = lllllllllllllllllIIlIlllllIIIlIl.add(lllllllllllllllllIIlIlllllIIIIIl.textBox("")).minWidth(400.0).expandX().widget();
        lllllllllllllllllIIlIlllllIIIlIl.filter.setFocused(true);
        lllllllllllllllllIIlIlllllIIIlIl.filter.action = () -> {
            StatusEffectSettingScreen lllllllllllllllllIIlIllllIlIIIll;
            lllllllllllllllllIIlIllllIlIIIll.filterText = lllllllllllllllllIIlIllllIlIIIll.filter.get().trim();
            lllllllllllllllllIIlIllllIlIIIll.table.clear();
            lllllllllllllllllIIlIllllIlIIIll.initWidgets();
        };
        lllllllllllllllllIIlIlllllIIIlIl.table = lllllllllllllllllIIlIlllllIIIlIl.add(lllllllllllllllllIIlIlllllIIIIIl.table()).expandX().widget();
        lllllllllllllllllIIlIlllllIIIlIl.initWidgets();
    }

    private void initWidgets() {
        StatusEffectSettingScreen lllllllllllllllllIIlIllllIllIlII;
        ArrayList<StatusEffect> lllllllllllllllllIIlIllllIllIlIl = new ArrayList<StatusEffect>((Collection<StatusEffect>)lllllllllllllllllIIlIllllIllIlII.setting.get().keySet());
        lllllllllllllllllIIlIllllIllIlIl.sort(Comparator.comparing(Names::get));
        for (StatusEffect lllllllllllllllllIIlIllllIllIlll : lllllllllllllllllIIlIllllIllIlIl) {
            String lllllllllllllllllIIlIllllIlllIIl = Names.get(lllllllllllllllllIIlIllllIllIlll);
            if (!StringUtils.containsIgnoreCase((CharSequence)lllllllllllllllllIIlIllllIlllIIl, (CharSequence)lllllllllllllllllIIlIllllIllIlII.filterText)) continue;
            lllllllllllllllllIIlIllllIllIlII.table.add(lllllllllllllllllIIlIllllIllIlII.theme.label(lllllllllllllllllIIlIllllIlllIIl)).expandCellX();
            WIntEdit lllllllllllllllllIIlIllllIlllIII = lllllllllllllllllIIlIllllIllIlII.theme.intEdit(lllllllllllllllllIIlIllllIllIlII.setting.get().getInt((Object)lllllllllllllllllIIlIllllIllIlll), 0, 0);
            lllllllllllllllllIIlIllllIlllIII.hasSlider = false;
            lllllllllllllllllIIlIllllIlllIII.action = () -> {
                StatusEffectSettingScreen lllllllllllllllllIIlIllllIlIlIll;
                lllllllllllllllllIIlIllllIlIlIll.setting.get().put((Object)lllllllllllllllllIIlIllllIllIlll, lllllllllllllllllIIlIllllIlllIII.get());
                lllllllllllllllllIIlIllllIlIlIll.setting.changed();
            };
            lllllllllllllllllIIlIllllIllIlII.table.add(lllllllllllllllllIIlIllllIlllIII).minWidth(50.0);
            lllllllllllllllllIIlIllllIllIlII.table.row();
        }
    }
}

