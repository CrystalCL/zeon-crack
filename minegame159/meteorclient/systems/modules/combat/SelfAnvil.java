/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.client.gui.screen.ingame.AnvilScreen
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;

public class SelfAnvil
extends Module {
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ SettingGroup sgGeneral;

    private int findSlot() {
        for (int llIllIIllllIlII = 0; llIllIIllllIlII < 9; ++llIllIIllllIlII) {
            SelfAnvil llIllIIllllIIll;
            Item llIllIIllllIlIl = llIllIIllllIIll.mc.player.inventory.getStack(llIllIIllllIlII).getItem();
            if (llIllIIllllIlIl != Items.ANVIL && llIllIIllllIlIl != Items.CHIPPED_ANVIL && llIllIIllllIlIl != Items.DAMAGED_ANVIL) continue;
            return llIllIIllllIlII;
        }
        return -1;
    }

    @EventHandler
    private void onTick(TickEvent.Pre llIllIlIIIIIIlI) {
        SelfAnvil llIllIlIIIIIIll;
        int llIllIlIIIIIIIl = llIllIlIIIIIIll.findSlot();
        if (llIllIlIIIIIIIl == -1) {
            return;
        }
        BlockPos llIllIlIIIIIIII = llIllIlIIIIIIll.mc.player.getBlockPos().add(0, 2, 0);
        if (BlockUtils.place(llIllIlIIIIIIII, Hand.MAIN_HAND, llIllIlIIIIIIIl, llIllIlIIIIIIll.rotate.get(), 0, true)) {
            llIllIlIIIIIIll.toggle();
        }
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent llIllIIlllllIIl) {
        if (llIllIIlllllIIl.screen instanceof AnvilScreen) {
            llIllIIlllllIIl.cancel();
        }
    }

    public SelfAnvil() {
        super(Categories.Combat, "self-anvil", "Automatically places an anvil on you to prevent other players from going into your hole.");
        SelfAnvil llIllIlIIIIlIII;
        llIllIlIIIIlIII.sgGeneral = llIllIlIIIIlIII.settings.getDefaultGroup();
        llIllIlIIIIlIII.rotate = llIllIlIIIIlIII.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate upwards when placing the anvil.").defaultValue(true).build());
    }
}

