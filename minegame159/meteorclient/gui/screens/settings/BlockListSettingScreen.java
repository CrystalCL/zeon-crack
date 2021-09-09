/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.util.Identifier
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
    private static final /* synthetic */ Identifier ID;

    public BlockListSettingScreen(GuiTheme lllllllllllllllllIIlllIllIlllllI, Setting<List<Block>> lllllllllllllllllIIlllIllIllllIl) {
        super(lllllllllllllllllIIlllIllIlllllI, "Select Blocks", lllllllllllllllllIIlllIllIllllIl, Registry.BLOCK);
        BlockListSettingScreen lllllllllllllllllIIlllIlllIIIIlI;
    }

    @Override
    protected WWidget getValueWidget(Block lllllllllllllllllIIlllIllIllIIll) {
        BlockListSettingScreen lllllllllllllllllIIlllIllIllIlII;
        return lllllllllllllllllIIlllIllIllIlII.theme.itemWithLabel(lllllllllllllllllIIlllIllIllIIll.asItem().getDefaultStack(), lllllllllllllllllIIlllIllIllIlII.getValueName(lllllllllllllllllIIlllIllIllIIll));
    }

    @Override
    protected Block getAdditionalValue(Block lllllllllllllllllIIlllIllIlIIlIl) {
        String lllllllllllllllllIIlllIllIlIIllI = Registry.BLOCK.getId((Object)lllllllllllllllllIIlllIllIlIIlIl).getPath();
        if (!lllllllllllllllllIIlllIllIlIIllI.endsWith("_banner")) {
            return null;
        }
        ((IdentifierAccessor)ID).setPath(String.valueOf(new StringBuilder().append(lllllllllllllllllIIlllIllIlIIllI.substring(0, lllllllllllllllllIIlllIllIlIIllI.length() - 6)).append("wall_banner")));
        return (Block)Registry.BLOCK.get(ID);
    }

    @Override
    protected boolean includeValue(Block lllllllllllllllllIIlllIllIlllIlI) {
        return lllllllllllllllllIIlllIllIlllIlI != Blocks.AIR;
    }

    @Override
    protected boolean skipValue(Block lllllllllllllllllIIlllIllIlIlIll) {
        return Registry.BLOCK.getId((Object)lllllllllllllllllIIlllIllIlIlIll).getPath().endsWith("_wall_banner");
    }

    @Override
    protected String getValueName(Block lllllllllllllllllIIlllIllIllIIII) {
        return Names.get(lllllllllllllllllIIlllIllIllIIII);
    }

    static {
        ID = new Identifier("minecraft", "");
    }
}

