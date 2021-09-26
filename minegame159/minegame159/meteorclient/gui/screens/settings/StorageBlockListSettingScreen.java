/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui.screens.settings;

import java.util.List;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.screens.settings.LeftRightListSettingScreen;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.StorageBlockListSetting;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.block.entity.BlockEntityType;

public class StorageBlockListSettingScreen
extends LeftRightListSettingScreen<BlockEntityType<?>> {
    @Override
    protected String getValueName(Object object) {
        return this.getValueName((BlockEntityType)object);
    }

    public StorageBlockListSettingScreen(GuiTheme guiTheme, Setting<List<BlockEntityType<?>>> setting) {
        super(guiTheme, "Select storage blocks", setting, StorageBlockListSetting.REGISTRY);
    }

    @Override
    protected WWidget getValueWidget(Object object) {
        return this.getValueWidget((BlockEntityType)object);
    }

    @Override
    protected String getValueName(BlockEntityType<?> BlockEntityType2) {
        String string = "Unknown";
        if (BlockEntityType2 == BlockEntityType.FURNACE) {
            string = "Furnace";
        } else if (BlockEntityType2 == BlockEntityType.CHEST) {
            string = "Chest";
        } else if (BlockEntityType2 == BlockEntityType.TRAPPED_CHEST) {
            string = "Trapped Chest";
        } else if (BlockEntityType2 == BlockEntityType.ENDER_CHEST) {
            string = "Ender Chest";
        } else if (BlockEntityType2 == BlockEntityType.DISPENSER) {
            string = "Dispenser";
        } else if (BlockEntityType2 == BlockEntityType.DROPPER) {
            string = "Dropper";
        } else if (BlockEntityType2 == BlockEntityType.HOPPER) {
            string = "Hopper";
        } else if (BlockEntityType2 == BlockEntityType.SHULKER_BOX) {
            string = "Shulker Box";
        } else if (BlockEntityType2 == BlockEntityType.BARREL) {
            string = "Barrel";
        } else if (BlockEntityType2 == BlockEntityType.SMOKER) {
            string = "Smoker";
        } else if (BlockEntityType2 == BlockEntityType.BLAST_FURNACE) {
            string = "Blast Furnace";
        }
        return string;
    }

    @Override
    protected WWidget getValueWidget(BlockEntityType<?> BlockEntityType2) {
        Item Item2 = Items.BARRIER;
        if (BlockEntityType2 == BlockEntityType.FURNACE) {
            Item2 = Items.FURNACE;
        } else if (BlockEntityType2 == BlockEntityType.CHEST) {
            Item2 = Items.CHEST;
        } else if (BlockEntityType2 == BlockEntityType.TRAPPED_CHEST) {
            Item2 = Items.TRAPPED_CHEST;
        } else if (BlockEntityType2 == BlockEntityType.ENDER_CHEST) {
            Item2 = Items.ENDER_CHEST;
        } else if (BlockEntityType2 == BlockEntityType.DISPENSER) {
            Item2 = Items.DISPENSER;
        } else if (BlockEntityType2 == BlockEntityType.DROPPER) {
            Item2 = Items.DROPPER;
        } else if (BlockEntityType2 == BlockEntityType.HOPPER) {
            Item2 = Items.HOPPER;
        } else if (BlockEntityType2 == BlockEntityType.SHULKER_BOX) {
            Item2 = Items.SHULKER_BOX;
        } else if (BlockEntityType2 == BlockEntityType.BARREL) {
            Item2 = Items.BARREL;
        } else if (BlockEntityType2 == BlockEntityType.SMOKER) {
            Item2 = Items.SMOKER;
        } else if (BlockEntityType2 == BlockEntityType.BLAST_FURNACE) {
            Item2 = Items.BLAST_FURNACE;
        }
        return this.theme.itemWithLabel(Item2.getDefaultStack(), this.getValueName(BlockEntityType2));
    }
}

