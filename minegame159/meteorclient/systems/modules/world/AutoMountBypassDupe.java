/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.passive.AbstractDonkeyEntity
 *  net.minecraft.entity.passive.LlamaEntity
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.block.ShulkerBoxBlock
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookOnly
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket$class_2849
 *  net.minecraft.client.gui.screen.ingame.HorseScreen
 *  org.lwjgl.glfw.GLFW
 */
package minegame159.meteorclient.systems.modules.world;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.MountBypass;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.client.gui.screen.ingame.HorseScreen;
import org.lwjgl.glfw.GLFW;

public class AutoMountBypassDupe
extends Module {
    private final /* synthetic */ List<Integer> slotsToThrow;
    private /* synthetic */ AbstractDonkeyEntity entity;
    private /* synthetic */ boolean sneak;
    private /* synthetic */ boolean noCancel;
    private final /* synthetic */ Setting<Boolean> shulkersOnly;
    private final /* synthetic */ Setting<Boolean> faceDown;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ List<Integer> slotsToMove;
    private /* synthetic */ int timer;
    private final /* synthetic */ Setting<Integer> delay;

    @EventHandler
    private void onSendPacket(PacketEvent.Send lllllllllllllllllIIlIllIIlllllll) {
        AutoMountBypassDupe lllllllllllllllllIIlIllIlIIIIIII;
        if (lllllllllllllllllIIlIllIlIIIIIII.noCancel) {
            return;
        }
        Modules.get().get(MountBypass.class).onSendPacket(lllllllllllllllllIIlIllIIlllllll);
    }

    private boolean isDupeTime() {
        AutoMountBypassDupe lllllllllllllllllIIlIllIIllIIlII;
        if (lllllllllllllllllIIlIllIIllIIlII.mc.player.getVehicle() != lllllllllllllllllIIlIllIIllIIlII.entity || lllllllllllllllllIIlIllIIllIIlII.entity.hasChest() || lllllllllllllllllIIlIllIIllIIlII.mc.player.currentScreenHandler.getStacks().size() == 46) {
            return false;
        }
        if (lllllllllllllllllIIlIllIIllIIlII.mc.player.currentScreenHandler.getStacks().size() > 38) {
            for (int lllllllllllllllllIIlIllIIllIIlIl = 2; lllllllllllllllllIIlIllIIllIIlIl < lllllllllllllllllIIlIllIIllIIlII.getDupeSize() + 1; ++lllllllllllllllllIIlIllIIllIIlIl) {
                if (!lllllllllllllllllIIlIllIIllIIlII.mc.player.currentScreenHandler.getSlot(lllllllllllllllllIIlIllIIllIIlIl).hasStack()) continue;
                return true;
            }
        }
        return false;
    }

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllIIlIllIIlllIIIl) {
        AutoMountBypassDupe lllllllllllllllllIIlIllIIlllIIlI;
        if (GLFW.glfwGetKey((long)lllllllllllllllllIIlIllIIlllIIlI.mc.getWindow().getHandle(), (int)256) == 1) {
            lllllllllllllllllIIlIllIIlllIIlI.toggle();
            lllllllllllllllllIIlIllIIlllIIlI.mc.player.closeHandledScreen();
            return;
        }
        if (lllllllllllllllllIIlIllIIlllIIlI.timer > 0) {
            --lllllllllllllllllIIlIllIIlllIIlI.timer;
            return;
        }
        lllllllllllllllllIIlIllIIlllIIlI.timer = lllllllllllllllllIIlIllIIlllIIlI.delay.get();
        int lllllllllllllllllIIlIllIIlllIIII = lllllllllllllllllIIlIllIIlllIIlI.getInvSize(lllllllllllllllllIIlIllIIlllIIlI.mc.player.getVehicle());
        for (Entity lllllllllllllllllIIlIllIIllllIlI : lllllllllllllllllIIlIllIIlllIIlI.mc.world.getEntities()) {
            if (!(lllllllllllllllllIIlIllIIllllIlI.distanceTo((Entity)lllllllllllllllllIIlIllIIlllIIlI.mc.player) < 5.0f) || !(lllllllllllllllllIIlIllIIllllIlI instanceof AbstractDonkeyEntity) || !((AbstractDonkeyEntity)lllllllllllllllllIIlIllIIllllIlI).isTame()) continue;
            lllllllllllllllllIIlIllIIlllIIlI.entity = (AbstractDonkeyEntity)lllllllllllllllllIIlIllIIllllIlI;
        }
        if (lllllllllllllllllIIlIllIIlllIIlI.entity == null) {
            return;
        }
        if (lllllllllllllllllIIlIllIIlllIIlI.sneak) {
            lllllllllllllllllIIlIllIIlllIIlI.mc.player.networkHandler.sendPacket((Packet)new ClientCommandC2SPacket((Entity)lllllllllllllllllIIlIllIIlllIIlI.mc.player, ClientCommandC2SPacket.class_2849.RELEASE_SHIFT_KEY));
            lllllllllllllllllIIlIllIIlllIIlI.mc.player.setSneaking(false);
            lllllllllllllllllIIlIllIIlllIIlI.sneak = false;
            return;
        }
        if (lllllllllllllllllIIlIllIIlllIIII == -1) {
            if (lllllllllllllllllIIlIllIIlllIIlI.entity.hasChest() || lllllllllllllllllIIlIllIIlllIIlI.mc.player.getMainHandStack().getItem() == Items.CHEST) {
                lllllllllllllllllIIlIllIIlllIIlI.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractEntityC2SPacket((Entity)lllllllllllllllllIIlIllIIlllIIlI.entity, Hand.MAIN_HAND, lllllllllllllllllIIlIllIIlllIIlI.mc.player.isSneaking()));
            } else {
                int lllllllllllllllllIIlIllIIllllIIl = InvUtils.findItemWithCount((Item)Items.CHEST).slot;
                if (lllllllllllllllllIIlIllIIllllIIl != -1 && lllllllllllllllllIIlIllIIllllIIl < 9) {
                    lllllllllllllllllIIlIllIIlllIIlI.mc.player.inventory.selectedSlot = lllllllllllllllllIIlIllIIllllIIl;
                } else {
                    ChatUtils.moduleError(lllllllllllllllllIIlIllIIlllIIlI, "Cannot find chest in your hotbar... disabling.", new Object[0]);
                    lllllllllllllllllIIlIllIIlllIIlI.toggle();
                }
            }
        } else if (lllllllllllllllllIIlIllIIlllIIII == 0) {
            if (lllllllllllllllllIIlIllIIlllIIlI.isDupeTime()) {
                if (!lllllllllllllllllIIlIllIIlllIIlI.slotsToThrow.isEmpty()) {
                    if (lllllllllllllllllIIlIllIIlllIIlI.faceDown.get().booleanValue()) {
                        lllllllllllllllllIIlIllIIlllIIlI.mc.player.networkHandler.sendPacket((Packet)new LookOnly(lllllllllllllllllIIlIllIIlllIIlI.mc.player.yaw, 90.0f, lllllllllllllllllIIlIllIIlllIIlI.mc.player.isOnGround()));
                    }
                    for (int lllllllllllllllllIIlIllIIllllIII : lllllllllllllllllIIlIllIIlllIIlI.slotsToThrow) {
                        InvUtils.drop().slotId(lllllllllllllllllIIlIllIIllllIII);
                    }
                    lllllllllllllllllIIlIllIIlllIIlI.slotsToThrow.clear();
                } else {
                    for (int lllllllllllllllllIIlIllIIlllIlll = 2; lllllllllllllllllIIlIllIIlllIlll < lllllllllllllllllIIlIllIIlllIIlI.getDupeSize() + 1; ++lllllllllllllllllIIlIllIIlllIlll) {
                        lllllllllllllllllIIlIllIIlllIIlI.slotsToThrow.add(lllllllllllllllllIIlIllIIlllIlll);
                    }
                }
            } else {
                lllllllllllllllllIIlIllIIlllIIlI.mc.player.closeHandledScreen();
                lllllllllllllllllIIlIllIIlllIIlI.mc.player.networkHandler.sendPacket((Packet)new ClientCommandC2SPacket((Entity)lllllllllllllllllIIlIllIIlllIIlI.mc.player, ClientCommandC2SPacket.class_2849.PRESS_SHIFT_KEY));
                lllllllllllllllllIIlIllIIlllIIlI.mc.player.setSneaking(true);
                lllllllllllllllllIIlIllIIlllIIlI.sneak = true;
            }
        } else if (!(lllllllllllllllllIIlIllIIlllIIlI.mc.currentScreen instanceof HorseScreen)) {
            lllllllllllllllllIIlIllIIlllIIlI.mc.player.openRidingInventory();
        } else if (lllllllllllllllllIIlIllIIlllIIII > 0) {
            if (lllllllllllllllllIIlIllIIlllIIlI.slotsToMove.isEmpty()) {
                boolean lllllllllllllllllIIlIllIIlllIlII = true;
                for (int lllllllllllllllllIIlIllIIlllIllI = 2; lllllllllllllllllIIlIllIIlllIllI <= lllllllllllllllllIIlIllIIlllIIII; ++lllllllllllllllllIIlIllIIlllIllI) {
                    if (((ItemStack)lllllllllllllllllIIlIllIIlllIIlI.mc.player.currentScreenHandler.getStacks().get(lllllllllllllllllIIlIllIIlllIllI)).isEmpty()) continue;
                    lllllllllllllllllIIlIllIIlllIlII = false;
                    break;
                }
                if (lllllllllllllllllIIlIllIIlllIlII) {
                    for (int lllllllllllllllllIIlIllIIlllIlIl = lllllllllllllllllIIlIllIIlllIIII + 2; lllllllllllllllllIIlIllIIlllIlIl < lllllllllllllllllIIlIllIIlllIIlI.mc.player.currentScreenHandler.getStacks().size(); ++lllllllllllllllllIIlIllIIlllIlIl) {
                        if (((ItemStack)lllllllllllllllllIIlIllIIlllIIlI.mc.player.currentScreenHandler.getStacks().get(lllllllllllllllllIIlIllIIlllIlIl)).isEmpty() || lllllllllllllllllIIlIllIIlllIIlI.mc.player.currentScreenHandler.getSlot(lllllllllllllllllIIlIllIIlllIlIl).getStack().getItem() == Items.CHEST || (!(lllllllllllllllllIIlIllIIlllIIlI.mc.player.currentScreenHandler.getSlot(lllllllllllllllllIIlIllIIlllIlIl).getStack().getItem() instanceof BlockItem) || !(((BlockItem)lllllllllllllllllIIlIllIIlllIIlI.mc.player.currentScreenHandler.getSlot(lllllllllllllllllIIlIllIIlllIlIl).getStack().getItem()).getBlock() instanceof ShulkerBoxBlock)) && lllllllllllllllllIIlIllIIlllIIlI.shulkersOnly.get().booleanValue()) continue;
                        lllllllllllllllllIIlIllIIlllIIlI.slotsToMove.add(lllllllllllllllllIIlIllIIlllIlIl);
                        if (lllllllllllllllllIIlIllIIlllIIlI.slotsToMove.size() < lllllllllllllllllIIlIllIIlllIIII) {
                            continue;
                        }
                        break;
                    }
                } else {
                    lllllllllllllllllIIlIllIIlllIIlI.noCancel = true;
                    lllllllllllllllllIIlIllIIlllIIlI.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractEntityC2SPacket((Entity)lllllllllllllllllIIlIllIIlllIIlI.entity, Hand.MAIN_HAND, lllllllllllllllllIIlIllIIlllIIlI.entity.getPos().add((double)(lllllllllllllllllIIlIllIIlllIIlI.entity.getWidth() / 2.0f), (double)(lllllllllllllllllIIlIllIIlllIIlI.entity.getHeight() / 2.0f), (double)(lllllllllllllllllIIlIllIIlllIIlI.entity.getWidth() / 2.0f)), lllllllllllllllllIIlIllIIlllIIlI.mc.player.isSneaking()));
                    lllllllllllllllllIIlIllIIlllIIlI.noCancel = false;
                    return;
                }
            }
            if (!lllllllllllllllllIIlIllIIlllIIlI.slotsToMove.isEmpty()) {
                for (int lllllllllllllllllIIlIllIIlllIIll : lllllllllllllllllIIlIllIIlllIIlI.slotsToMove) {
                    InvUtils.quickMove().slotId(lllllllllllllllllIIlIllIIlllIIll);
                }
                lllllllllllllllllIIlIllIIlllIIlI.slotsToMove.clear();
            }
        }
    }

    public AutoMountBypassDupe() {
        super(Categories.World, "auto-mount-bypass-dupe", "Does the mount bypass dupe for you. Disable with esc.");
        AutoMountBypassDupe lllllllllllllllllIIlIllIlIIIlIII;
        lllllllllllllllllIIlIllIlIIIlIII.sgGeneral = lllllllllllllllllIIlIllIlIIIlIII.settings.getDefaultGroup();
        lllllllllllllllllIIlIllIlIIIlIII.shulkersOnly = lllllllllllllllllIIlIllIlIIIlIII.sgGeneral.add(new BoolSetting.Builder().name("shulker-only").description("Only moves shulker boxes into the inventory").defaultValue(true).build());
        lllllllllllllllllIIlIllIlIIIlIII.faceDown = lllllllllllllllllIIlIllIlIIIlIII.sgGeneral.add(new BoolSetting.Builder().name("rotate-down").description("Faces down when dropping items.").defaultValue(true).build());
        lllllllllllllllllIIlIllIlIIIlIII.delay = lllllllllllllllllIIlIllIlIIIlIII.sgGeneral.add(new IntSetting.Builder().name("delay").description("The delay in ticks between actions.").defaultValue(4).min(0).build());
        lllllllllllllllllIIlIllIlIIIlIII.slotsToMove = new ArrayList<Integer>();
        lllllllllllllllllIIlIllIlIIIlIII.slotsToThrow = new ArrayList<Integer>();
        lllllllllllllllllIIlIllIlIIIlIII.noCancel = false;
        lllllllllllllllllIIlIllIlIIIlIII.sneak = false;
    }

    @Override
    public void onActivate() {
        lllllllllllllllllIIlIllIlIIIIlIl.timer = 0;
    }

    private int getDupeSize() {
        AutoMountBypassDupe lllllllllllllllllIIlIllIIlIlllll;
        if (lllllllllllllllllIIlIllIIlIlllll.mc.player.getVehicle() != lllllllllllllllllIIlIllIIlIlllll.entity || lllllllllllllllllIIlIllIIlIlllll.entity.hasChest() || lllllllllllllllllIIlIllIIlIlllll.mc.player.currentScreenHandler.getStacks().size() == 46) {
            return 0;
        }
        return lllllllllllllllllIIlIllIIlIlllll.mc.player.currentScreenHandler.getStacks().size() - 38;
    }

    private int getInvSize(Entity lllllllllllllllllIIlIllIIllIlIIl) {
        if (!(lllllllllllllllllIIlIllIIllIlIIl instanceof AbstractDonkeyEntity)) {
            return -1;
        }
        if (!((AbstractDonkeyEntity)lllllllllllllllllIIlIllIIllIlIIl).hasChest()) {
            return 0;
        }
        if (lllllllllllllllllIIlIllIIllIlIIl instanceof LlamaEntity) {
            return 3 * ((LlamaEntity)lllllllllllllllllIIlIllIIllIlIIl).getStrength();
        }
        return 15;
    }
}

