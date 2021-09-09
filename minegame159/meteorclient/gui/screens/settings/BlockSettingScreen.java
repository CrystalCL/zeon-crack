/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.util.registry.Registry
 *  org.apache.commons.lang3.StringUtils
 */
package minegame159.meteorclient.gui.screens.settings;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.widgets.WItemWithLabel;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.settings.BlockSetting;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.StringUtils;

public class BlockSettingScreen
extends WindowScreen {
    private final /* synthetic */ BlockSetting setting;
    private final /* synthetic */ WTextBox filter;
    private /* synthetic */ WTable table;
    private /* synthetic */ String filterText;

    public BlockSettingScreen(GuiTheme lIIIlIIlIllIIll, BlockSetting lIIIlIIlIllIlIl) {
        super(lIIIlIIlIllIIll, "Select blocks");
        BlockSettingScreen lIIIlIIlIllIlII;
        lIIIlIIlIllIlII.filterText = "";
        lIIIlIIlIllIlII.setting = lIIIlIIlIllIlIl;
        lIIIlIIlIllIlII.filter = lIIIlIIlIllIlII.add(lIIIlIIlIllIIll.textBox("")).minWidth(400.0).expandX().widget();
        lIIIlIIlIllIlII.filter.setFocused(true);
        lIIIlIIlIllIlII.filter.action = () -> {
            BlockSettingScreen lIIIlIIlIIllIll;
            lIIIlIIlIIllIll.filterText = lIIIlIIlIIllIll.filter.get().trim();
            lIIIlIIlIIllIll.table.clear();
            lIIIlIIlIIllIll.initWidgets();
        };
        lIIIlIIlIllIlII.table = lIIIlIIlIllIlII.add(lIIIlIIlIllIIll.table()).expandX().widget();
        lIIIlIIlIllIlII.initWidgets();
    }

    private void initWidgets() {
        for (Block lIIIlIIlIlIlIlI : Registry.BLOCK) {
            BlockSettingScreen lIIIlIIlIlIlIII;
            WItemWithLabel lIIIlIIlIlIllII = lIIIlIIlIlIlIII.theme.itemWithLabel(lIIIlIIlIlIlIlI.asItem().getDefaultStack());
            if (!lIIIlIIlIlIlIII.filterText.isEmpty() && !StringUtils.containsIgnoreCase((CharSequence)lIIIlIIlIlIllII.getLabelText(), (CharSequence)lIIIlIIlIlIlIII.filterText)) continue;
            lIIIlIIlIlIlIII.table.add(lIIIlIIlIlIllII);
            WButton lIIIlIIlIlIlIll = lIIIlIIlIlIlIII.table.add(lIIIlIIlIlIlIII.theme.button("Select")).expandCellX().right().widget();
            lIIIlIIlIlIlIll.action = () -> {
                BlockSettingScreen lIIIlIIlIlIIIIl;
                lIIIlIIlIlIIIIl.setting.set(lIIIlIIlIlIlIlI);
                lIIIlIIlIlIIIIl.onClose();
            };
            lIIIlIIlIlIlIII.table.row();
        }
    }
}

