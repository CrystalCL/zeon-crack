/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.block.Blocks
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;

public class AntiAnvil
extends Module {
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> rotate;

    public AntiAnvil() {
        super(Categories.Combat, "anti-anvil", "Automatically prevents Auto Anvil by placing obsidian above you.");
        AntiAnvil lIIllIlllIllllI;
        lIIllIlllIllllI.sgGeneral = lIIllIlllIllllI.settings.getDefaultGroup();
        lIIllIlllIllllI.rotate = lIIllIlllIllllI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate upwards when placing obsidian above you.").defaultValue(true).build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre lIIllIlllIlIlII) {
        AntiAnvil lIIllIlllIlIlIl;
        int lIIllIlllIlIllI = 2;
        while ((float)lIIllIlllIlIllI <= lIIllIlllIlIlIl.mc.interactionManager.getReachDistance() + 2.0f) {
            if (lIIllIlllIlIlIl.mc.world.getBlockState(lIIllIlllIlIlIl.mc.player.getBlockPos().add(0, lIIllIlllIlIllI, 0)).getBlock() == Blocks.ANVIL && lIIllIlllIlIlIl.mc.world.getBlockState(lIIllIlllIlIlIl.mc.player.getBlockPos().add(0, lIIllIlllIlIllI - 1, 0)).isAir()) {
                int lIIllIlllIllIII = InvUtils.findItemWithCount((Item)Items.OBSIDIAN).slot;
                boolean lIIllIlllIlIlll = false;
                if (lIIllIlllIllIII != 1 && lIIllIlllIllIII < 9) {
                    lIIllIlllIlIlIl.place(lIIllIlllIlIllI, lIIllIlllIllIII);
                    lIIllIlllIlIlll = true;
                } else if (lIIllIlllIlIlIl.mc.player.getOffHandStack().getItem() == Items.OBSIDIAN) {
                    lIIllIlllIlIlIl.place(lIIllIlllIlIllI, -1);
                    lIIllIlllIlIlll = true;
                }
                if (lIIllIlllIlIlll) break;
            }
            ++lIIllIlllIlIllI;
        }
    }

    private void place(int lIIllIlllIIlIll, int lIIllIlllIIlIlI) {
        AntiAnvil lIIllIlllIIlIIl;
        BlockUtils.place(lIIllIlllIIlIIl.mc.player.getBlockPos().add(0, lIIllIlllIIlIll - 2, 0), Hand.MAIN_HAND, lIIllIlllIIlIlI == -1 ? 0 : lIIllIlllIIlIlI, lIIllIlllIIlIIl.rotate.get(), 15, true, true, lIIllIlllIIlIlI != -1, lIIllIlllIIlIlI != -1);
    }
}

