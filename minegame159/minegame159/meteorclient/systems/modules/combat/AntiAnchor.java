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
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class AntiAnchor
extends Module {
    private final Setting<Boolean> rotate;
    private final SettingGroup sgGeneral;

    public AntiAnchor() {
        super(Categories.Combat, "anti-anchor", "Automatically prevents Anchor Aura by placing a slab on your head.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate upwards when placing a slab above you.").defaultValue(true).build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        int n;
        if (this.mc.world.getBlockState(this.mc.player.getBlockPos().add(0, 2, 0)).getBlock() == Blocks.RESPAWN_ANCHOR && this.mc.world.getBlockState(this.mc.player.getBlockPos().add(0, 1, 0)).getBlock() == Blocks.AIR && (n = InvUtils.findItemInHotbar(AntiAnchor::lambda$onTick$0)) != -1) {
            this.mc.player.input.sneaking = true;
            this.mc.player.networkHandler.sendPacket((Packet)new ClientCommandC2SPacket((Entity)this.mc.player, ClientCommandC2SPacket.class_2849.PRESS_SHIFT_KEY));
            this.rotateCheck(n);
        }
    }

    private void placeBlock(int n) {
        BlockUtils.place(this.mc.player.getBlockPos().add(0, 1, 0), Hand.MAIN_HAND, n, this.rotate.get(), 0, false);
    }

    private void lambda$rotateCheck$1(int n) {
        this.placeBlock(n);
    }

    private static boolean lambda$onTick$0(ItemStack ItemStack2) {
        return Block.getBlockFromItem((Item)ItemStack2.getItem()) instanceof SlabBlock;
    }

    private void rotateCheck(int n) {
        if (this.rotate.get().booleanValue()) {
            Rotations.rotate(this.mc.player.yaw, -90.0, 15, () -> this.lambda$rotateCheck$1(n));
        } else {
            this.placeBlock(n);
        }
    }
}

