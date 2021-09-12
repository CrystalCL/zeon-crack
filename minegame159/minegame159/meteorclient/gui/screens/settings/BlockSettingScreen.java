/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.screens.settings;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.widgets.WItemWithLabel;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.settings.BlockSetting;
import net.minecraft.class_2248;
import net.minecraft.class_2378;
import org.apache.commons.lang3.StringUtils;

public class BlockSettingScreen
extends WindowScreen {
    private final BlockSetting setting;
    private String filterText = "";
    private WTable table;
    private final WTextBox filter;

    public BlockSettingScreen(GuiTheme guiTheme, BlockSetting blockSetting) {
        super(guiTheme, "Select blocks");
        this.setting = blockSetting;
        this.filter = this.add(guiTheme.textBox("")).minWidth(400.0).expandX().widget();
        this.filter.setFocused(true);
        this.filter.action = this::lambda$new$0;
        this.table = this.add(guiTheme.table()).expandX().widget();
        this.initWidgets();
    }

    private void lambda$new$0() {
        this.filterText = this.filter.get().trim();
        this.table.clear();
        this.initWidgets();
    }

    private void initWidgets() {
        for (class_2248 class_22482 : class_2378.field_11146) {
            WItemWithLabel wItemWithLabel = this.theme.itemWithLabel(class_22482.method_8389().method_7854());
            if (!this.filterText.isEmpty() && !StringUtils.containsIgnoreCase((CharSequence)wItemWithLabel.getLabelText(), (CharSequence)this.filterText)) continue;
            this.table.add(wItemWithLabel);
            WButton wButton = this.table.add(this.theme.button("Select")).expandCellX().right().widget();
            wButton.action = () -> this.lambda$initWidgets$1(class_22482);
            this.table.row();
        }
    }

    private void lambda$initWidgets$1(class_2248 class_22482) {
        this.setting.set(class_22482);
        this.method_25419();
    }
}

