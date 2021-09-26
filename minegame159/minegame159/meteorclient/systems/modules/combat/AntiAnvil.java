/*
 * Decompiled with CFR 0.151.
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
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> rotate;

    private void place(int n, int n2) {
        BlockUtils.place(this.mc.player.getBlockPos().add(0, n - 2, 0), Hand.MAIN_HAND, n2 == -1 ? 0 : n2, this.rotate.get(), 15, true, true, n2 != -1, n2 != -1);
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n = 2;
        while ((float)n <= this.mc.interactionManager.getReachDistance() + 2.0f) {
            if (this.mc.world.getBlockState(this.mc.player.getBlockPos().add(0, n, 0)).getBlock() == Blocks.ANVIL && this.mc.world.getBlockState(this.mc.player.getBlockPos().add(0, n - 1, 0)).isAir()) {
                int n2 = InvUtils.findItemWithCount((Item)Items.OBSIDIAN).slot;
                boolean bl = false;
                if (n2 != 1 && n2 < 9) {
                    this.place(n, n2);
                    bl = true;
                } else if (this.mc.player.getOffHandStack().getItem() == Items.OBSIDIAN) {
                    this.place(n, -1);
                    bl = true;
                }
                if (bl) break;
            }
            ++n;
            if (4 != 0) continue;
            return;
        }
    }

    public AntiAnvil() {
        super(Categories.Combat, "anti-anvil", "Automatically prevents Auto Anvil by placing obsidian above you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate upwards when placing obsidian above you.").defaultValue(true).build());
    }
}

