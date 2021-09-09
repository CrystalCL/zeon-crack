/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.Item
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.block.SlabBlock
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket$class_2849
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
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class AntiAnchor
extends Module {
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> rotate;

    private void placeBlock(int llllllllllllllllIlllIlllIlllllll) {
        AntiAnchor llllllllllllllllIlllIllllIIIIIlI;
        BlockUtils.place(llllllllllllllllIlllIllllIIIIIlI.mc.player.getBlockPos().add(0, 1, 0), Hand.MAIN_HAND, llllllllllllllllIlllIlllIlllllll, llllllllllllllllIlllIllllIIIIIlI.rotate.get(), 0, false);
    }

    @EventHandler
    private void onTick(TickEvent.Pre llllllllllllllllIlllIllllIIIllIl) {
        int llllllllllllllllIlllIllllIIIllll;
        AntiAnchor llllllllllllllllIlllIllllIIIlllI;
        if (llllllllllllllllIlllIllllIIIlllI.mc.world.getBlockState(llllllllllllllllIlllIllllIIIlllI.mc.player.getBlockPos().add(0, 2, 0)).getBlock() == Blocks.RESPAWN_ANCHOR && llllllllllllllllIlllIllllIIIlllI.mc.world.getBlockState(llllllllllllllllIlllIllllIIIlllI.mc.player.getBlockPos().add(0, 1, 0)).getBlock() == Blocks.AIR && (llllllllllllllllIlllIllllIIIllll = InvUtils.findItemInHotbar(llllllllllllllllIlllIlllIlllIlll -> Block.getBlockFromItem((Item)llllllllllllllllIlllIlllIlllIlll.getItem()) instanceof SlabBlock)) != -1) {
            llllllllllllllllIlllIllllIIIlllI.mc.player.input.sneaking = true;
            llllllllllllllllIlllIllllIIIlllI.mc.player.networkHandler.sendPacket((Packet)new ClientCommandC2SPacket((Entity)llllllllllllllllIlllIllllIIIlllI.mc.player, ClientCommandC2SPacket.class_2849.PRESS_SHIFT_KEY));
            llllllllllllllllIlllIllllIIIlllI.rotateCheck(llllllllllllllllIlllIllllIIIllll);
        }
    }

    private void rotateCheck(int llllllllllllllllIlllIllllIIIIlIl) {
        AntiAnchor llllllllllllllllIlllIllllIIIlIII;
        if (llllllllllllllllIlllIllllIIIlIII.rotate.get().booleanValue()) {
            Rotations.rotate(llllllllllllllllIlllIllllIIIlIII.mc.player.yaw, -90.0, 15, () -> {
                AntiAnchor llllllllllllllllIlllIlllIllllIlI;
                llllllllllllllllIlllIlllIllllIlI.placeBlock(llllllllllllllllIlllIllllIIIIlIl);
            });
        } else {
            llllllllllllllllIlllIllllIIIlIII.placeBlock(llllllllllllllllIlllIllllIIIIlIl);
        }
    }

    public AntiAnchor() {
        super(Categories.Combat, "anti-anchor", "Automatically prevents Anchor Aura by placing a slab on your head.");
        AntiAnchor llllllllllllllllIlllIllllIIlIIlI;
        llllllllllllllllIlllIllllIIlIIlI.sgGeneral = llllllllllllllllIlllIllllIIlIIlI.settings.getDefaultGroup();
        llllllllllllllllIlllIllllIIlIIlI.rotate = llllllllllllllllIlllIllllIIlIIlI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate upwards when placing a slab above you.").defaultValue(true).build());
    }
}

