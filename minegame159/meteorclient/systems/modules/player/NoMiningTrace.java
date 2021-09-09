/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.PickaxeItem
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
    private final /* synthetic */ Setting<Boolean> onlyWhenHoldingPickaxe;
    private final /* synthetic */ SettingGroup sgGeneral;

    public NoMiningTrace() {
        super(Categories.Player, "no-mining-trace", "Allows you to mine blocks through entities.");
        NoMiningTrace lIlIlIIllIllllI;
        lIlIlIIllIllllI.sgGeneral = lIlIlIIllIllllI.settings.getDefaultGroup();
        lIlIlIIllIllllI.onlyWhenHoldingPickaxe = lIlIlIIllIllllI.sgGeneral.add(new BoolSetting.Builder().name("only-when-holding-a-pickaxe").description("Whether or not to work only when holding a pickaxe.").defaultValue(true).build());
    }

    public boolean canWork() {
        NoMiningTrace lIlIlIIllIlllII;
        if (!lIlIlIIllIlllII.isActive()) {
            return false;
        }
        if (lIlIlIIllIlllII.onlyWhenHoldingPickaxe.get().booleanValue()) {
            return lIlIlIIllIlllII.mc.player.getMainHandStack().getItem() instanceof PickaxeItem || lIlIlIIllIlllII.mc.player.getOffHandStack().getItem() instanceof PickaxeItem;
        }
        return true;
    }
}

