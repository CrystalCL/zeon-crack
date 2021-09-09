/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.block.entity.BlockEntityType
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
    protected String getValueName(BlockEntityType<?> lIIIIIllIllIll) {
        String lIIIIIllIllIlI = "Unknown";
        if (lIIIIIllIllIll == BlockEntityType.FURNACE) {
            lIIIIIllIllIlI = "Furnace";
        } else if (lIIIIIllIllIll == BlockEntityType.CHEST) {
            lIIIIIllIllIlI = "Chest";
        } else if (lIIIIIllIllIll == BlockEntityType.TRAPPED_CHEST) {
            lIIIIIllIllIlI = "Trapped Chest";
        } else if (lIIIIIllIllIll == BlockEntityType.ENDER_CHEST) {
            lIIIIIllIllIlI = "Ender Chest";
        } else if (lIIIIIllIllIll == BlockEntityType.DISPENSER) {
            lIIIIIllIllIlI = "Dispenser";
        } else if (lIIIIIllIllIll == BlockEntityType.DROPPER) {
            lIIIIIllIllIlI = "Dropper";
        } else if (lIIIIIllIllIll == BlockEntityType.HOPPER) {
            lIIIIIllIllIlI = "Hopper";
        } else if (lIIIIIllIllIll == BlockEntityType.SHULKER_BOX) {
            lIIIIIllIllIlI = "Shulker Box";
        } else if (lIIIIIllIllIll == BlockEntityType.BARREL) {
            lIIIIIllIllIlI = "Barrel";
        } else if (lIIIIIllIllIll == BlockEntityType.SMOKER) {
            lIIIIIllIllIlI = "Smoker";
        } else if (lIIIIIllIllIll == BlockEntityType.BLAST_FURNACE) {
            lIIIIIllIllIlI = "Blast Furnace";
        }
        return lIIIIIllIllIlI;
    }

    @Override
    protected WWidget getValueWidget(BlockEntityType<?> lIIIIIlllIIIII) {
        StorageBlockListSettingScreen lIIIIIlllIIIIl;
        Item lIIIIIlllIIIlI = Items.BARRIER;
        if (lIIIIIlllIIIII == BlockEntityType.FURNACE) {
            lIIIIIlllIIIlI = Items.FURNACE;
        } else if (lIIIIIlllIIIII == BlockEntityType.CHEST) {
            lIIIIIlllIIIlI = Items.CHEST;
        } else if (lIIIIIlllIIIII == BlockEntityType.TRAPPED_CHEST) {
            lIIIIIlllIIIlI = Items.TRAPPED_CHEST;
        } else if (lIIIIIlllIIIII == BlockEntityType.ENDER_CHEST) {
            lIIIIIlllIIIlI = Items.ENDER_CHEST;
        } else if (lIIIIIlllIIIII == BlockEntityType.DISPENSER) {
            lIIIIIlllIIIlI = Items.DISPENSER;
        } else if (lIIIIIlllIIIII == BlockEntityType.DROPPER) {
            lIIIIIlllIIIlI = Items.DROPPER;
        } else if (lIIIIIlllIIIII == BlockEntityType.HOPPER) {
            lIIIIIlllIIIlI = Items.HOPPER;
        } else if (lIIIIIlllIIIII == BlockEntityType.SHULKER_BOX) {
            lIIIIIlllIIIlI = Items.SHULKER_BOX;
        } else if (lIIIIIlllIIIII == BlockEntityType.BARREL) {
            lIIIIIlllIIIlI = Items.BARREL;
        } else if (lIIIIIlllIIIII == BlockEntityType.SMOKER) {
            lIIIIIlllIIIlI = Items.SMOKER;
        } else if (lIIIIIlllIIIII == BlockEntityType.BLAST_FURNACE) {
            lIIIIIlllIIIlI = Items.BLAST_FURNACE;
        }
        return lIIIIIlllIIIIl.theme.itemWithLabel(lIIIIIlllIIIlI.getDefaultStack(), lIIIIIlllIIIIl.getValueName(lIIIIIlllIIIII));
    }

    public StorageBlockListSettingScreen(GuiTheme lIIIIIlllIllII, Setting<List<BlockEntityType<?>>> lIIIIIlllIlIII) {
        super(lIIIIIlllIllII, "Select storage blocks", lIIIIIlllIlIII, StorageBlockListSetting.REGISTRY);
        StorageBlockListSettingScreen lIIIIIlllIllIl;
    }
}

