/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.screens.settings;

import java.util.List;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.screens.settings.LeftRightListSettingScreen;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.mixin.IdentifierAccessor;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.misc.Names;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockListSettingScreen
extends LeftRightListSettingScreen<Block> {
    private static final Identifier ID = new Identifier("minecraft", "");

    @Override
    protected WWidget getValueWidget(Object object) {
        return this.getValueWidget((Block)object);
    }

    public BlockListSettingScreen(GuiTheme guiTheme, Setting<List<Block>> setting) {
        super(guiTheme, "Select Blocks", setting, Registry.BLOCK);
    }

    @Override
    protected boolean skipValue(Object object) {
        return this.skipValue((Block)object);
    }

    @Override
    protected Object getAdditionalValue(Object object) {
        return this.getAdditionalValue((Block)object);
    }

    @Override
    protected String getValueName(Object object) {
        return this.getValueName((Block)object);
    }

    @Override
    protected Block getAdditionalValue(Block Block2) {
        String string = Registry.BLOCK.getId((Object)Block2).getPath();
        if (!string.endsWith("_banner")) {
            return null;
        }
        ((IdentifierAccessor)ID).setPath(String.valueOf(new StringBuilder().append(string.substring(0, string.length() - 6)).append("wall_banner")));
        return (Block)Registry.BLOCK.get(ID);
    }

    @Override
    protected boolean includeValue(Object object) {
        return this.includeValue((Block)object);
    }

    @Override
    protected boolean skipValue(Block Block2) {
        return Registry.BLOCK.getId((Object)Block2).getPath().endsWith("_wall_banner");
    }

    @Override
    protected String getValueName(Block Block2) {
        return Names.get(Block2);
    }

    @Override
    protected boolean includeValue(Block Block2) {
        return Block2 != Blocks.AIR;
    }

    @Override
    protected WWidget getValueWidget(Block Block2) {
        return this.theme.itemWithLabel(Block2.asItem().getDefaultStack(), this.getValueName(Block2));
    }
}

