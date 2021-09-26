/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.item.PickaxeItem;

public class NoMiningTrace
extends Module {
    private final Setting<Boolean> onlyWhenHoldingPickaxe;
    private final SettingGroup sgGeneral;

    public NoMiningTrace() {
        super(Categories.Player, "no-mining-trace", "Allows you to mine blocks through entities.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.onlyWhenHoldingPickaxe = this.sgGeneral.add(new BoolSetting.Builder().name("only-when-holding-a-pickaxe").description("Whether or not to work only when holding a pickaxe.").defaultValue(true).build());
    }

    public boolean canWork() {
        if (!this.isActive()) {
            return false;
        }
        if (this.onlyWhenHoldingPickaxe.get().booleanValue()) {
            return this.mc.player.getMainHandStack().getItem() instanceof PickaxeItem || this.mc.player.getOffHandStack().getItem() instanceof PickaxeItem;
        }
        return true;
    }
}

