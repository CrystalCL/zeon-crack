/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> rotate;
    private final SettingGroup sgGeneral;

    public SelfAnvil() {
        super(Categories.Combat, "self-anvil", "Automatically places an anvil on you to prevent other players from going into your hole.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate upwards when placing the anvil.").defaultValue(true).build());
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent openScreenEvent) {
        if (openScreenEvent.screen instanceof AnvilScreen) {
            openScreenEvent.cancel();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n = this.findSlot();
        if (n == -1) {
            return;
        }
        BlockPos BlockPos2 = this.mc.player.getBlockPos().add(0, 2, 0);
        if (BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n, this.rotate.get(), 0, true)) {
            this.toggle();
        }
    }

    private int findSlot() {
        for (int i = 0; i < 9; ++i) {
            Item Item2 = this.mc.player.inventory.getStack(i).getItem();
            if (Item2 != Items.ANVIL && Item2 != Items.CHIPPED_ANVIL && Item2 != Items.DAMAGED_ANVIL) continue;
            return i;
        }
        return -1;
    }
}

