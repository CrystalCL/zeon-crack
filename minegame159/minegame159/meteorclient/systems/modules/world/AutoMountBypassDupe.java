/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import java.util.ArrayList;
import java.util.Iterator;
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
    private boolean noCancel;
    private int timer;
    private final List<Integer> slotsToThrow;
    private final Setting<Boolean> shulkersOnly;
    private AbstractDonkeyEntity entity;
    private boolean sneak;
    private final List<Integer> slotsToMove;
    private final Setting<Boolean> faceDown;
    private final Setting<Integer> delay;
    private final SettingGroup sgGeneral;

    private int getInvSize(Entity Entity2) {
        if (!(Entity2 instanceof AbstractDonkeyEntity)) {
            return -1;
        }
        if (!((AbstractDonkeyEntity)Entity2).hasChest()) {
            return 0;
        }
        if (Entity2 instanceof LlamaEntity) {
            return 3 * ((LlamaEntity)Entity2).getStrength();
        }
        return 15;
    }

    private boolean isDupeTime() {
        if (this.mc.player.getVehicle() != this.entity || this.entity.hasChest() || this.mc.player.currentScreenHandler.getStacks().size() == 46) {
            return false;
        }
        if (this.mc.player.currentScreenHandler.getStacks().size() > 38) {
            for (int i = 2; i < this.getDupeSize() + 1; ++i) {
                if (!this.mc.player.currentScreenHandler.getSlot(i).hasStack()) continue;
                return true;
            }
        }
        return false;
    }

    public AutoMountBypassDupe() {
        super(Categories.World, "auto-mount-bypass-dupe", "Does the mount bypass dupe for you. Disable with esc.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.shulkersOnly = this.sgGeneral.add(new BoolSetting.Builder().name("shulker-only").description("Only moves shulker boxes into the inventory").defaultValue(true).build());
        this.faceDown = this.sgGeneral.add(new BoolSetting.Builder().name("rotate-down").description("Faces down when dropping items.").defaultValue(true).build());
        this.delay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("The delay in ticks between actions.").defaultValue(4).min(0).build());
        this.slotsToMove = new ArrayList<Integer>();
        this.slotsToThrow = new ArrayList<Integer>();
        this.noCancel = false;
        this.sneak = false;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        Iterator<Object> iterator;
        if (GLFW.glfwGetKey((long)this.mc.getWindow().getHandle(), (int)256) == 1) {
            this.toggle();
            this.mc.player.closeHandledScreen();
            return;
        }
        if (this.timer > 0) {
            --this.timer;
            return;
        }
        this.timer = this.delay.get();
        int n = this.getInvSize(this.mc.player.getVehicle());
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (!(Entity2.distanceTo((Entity)this.mc.player) < 5.0f) || !(Entity2 instanceof AbstractDonkeyEntity) || !((AbstractDonkeyEntity)Entity2).isTame()) continue;
            this.entity = (AbstractDonkeyEntity)Entity2;
        }
        if (this.entity == null) {
            return;
        }
        if (this.sneak) {
            this.mc.player.networkHandler.sendPacket((Packet)new ClientCommandC2SPacket((Entity)this.mc.player, ClientCommandC2SPacket.class_2849.RELEASE_SHIFT_KEY));
            this.mc.player.setSneaking(false);
            this.sneak = false;
            return;
        }
        if (n == -1) {
            if (this.entity.hasChest() || this.mc.player.getMainHandStack().getItem() == Items.CHEST) {
                this.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractEntityC2SPacket((Entity)this.entity, Hand.MAIN_HAND, this.mc.player.isSneaking()));
            } else {
                int n2 = InvUtils.findItemWithCount((Item)Items.CHEST).slot;
                if (n2 != -1 && n2 < 9) {
                    this.mc.player.inventory.selectedSlot = n2;
                } else {
                    ChatUtils.moduleError(this, "Cannot find chest in your hotbar... disabling.", new Object[0]);
                    this.toggle();
                }
            }
        } else if (n == 0) {
            if (this.isDupeTime()) {
                if (!this.slotsToThrow.isEmpty()) {
                    if (this.faceDown.get().booleanValue()) {
                        this.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.class_2831(this.mc.player.yaw, 90.0f, this.mc.player.isOnGround()));
                    }
                    iterator = this.slotsToThrow.iterator();
                    while (iterator.hasNext()) {
                        int n3 = (Integer)iterator.next();
                        InvUtils.drop().slotId(n3);
                    }
                    this.slotsToThrow.clear();
                } else {
                    for (int i = 2; i < this.getDupeSize() + 1; ++i) {
                        this.slotsToThrow.add(i);
                        if (4 > -1) continue;
                        return;
                    }
                }
            } else {
                this.mc.player.closeHandledScreen();
                this.mc.player.networkHandler.sendPacket((Packet)new ClientCommandC2SPacket((Entity)this.mc.player, ClientCommandC2SPacket.class_2849.PRESS_SHIFT_KEY));
                this.mc.player.setSneaking(true);
                this.sneak = true;
            }
        } else if (!(this.mc.currentScreen instanceof HorseScreen)) {
            this.mc.player.openRidingInventory();
        } else if (n > 0) {
            if (this.slotsToMove.isEmpty()) {
                int n4;
                boolean bl = true;
                for (n4 = 2; n4 <= n; ++n4) {
                    if (((ItemStack)this.mc.player.currentScreenHandler.getStacks().get(n4)).isEmpty()) continue;
                    bl = false;
                    break;
                }
                if (bl) {
                    for (n4 = n + 2; n4 < this.mc.player.currentScreenHandler.getStacks().size(); ++n4) {
                        if (((ItemStack)this.mc.player.currentScreenHandler.getStacks().get(n4)).isEmpty() || this.mc.player.currentScreenHandler.getSlot(n4).getStack().getItem() == Items.CHEST || (!(this.mc.player.currentScreenHandler.getSlot(n4).getStack().getItem() instanceof BlockItem) || !(((BlockItem)this.mc.player.currentScreenHandler.getSlot(n4).getStack().getItem()).getBlock() instanceof ShulkerBoxBlock)) && this.shulkersOnly.get().booleanValue()) continue;
                        this.slotsToMove.add(n4);
                        if (this.slotsToMove.size() < n) {
                            if (-1 < 0) continue;
                            return;
                        }
                        break;
                    }
                } else {
                    this.noCancel = true;
                    this.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractEntityC2SPacket((Entity)this.entity, Hand.MAIN_HAND, this.entity.getPos().add((double)(this.entity.getWidth() / 2.0f), (double)(this.entity.getHeight() / 2.0f), (double)(this.entity.getWidth() / 2.0f)), this.mc.player.isSneaking()));
                    this.noCancel = false;
                    return;
                }
            }
            if (!this.slotsToMove.isEmpty()) {
                iterator = this.slotsToMove.iterator();
                while (iterator.hasNext()) {
                    int n5 = (Integer)iterator.next();
                    InvUtils.quickMove().slotId(n5);
                }
                this.slotsToMove.clear();
            }
        }
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (this.noCancel) {
            return;
        }
        Modules.get().get(MountBypass.class).onSendPacket(send);
    }

    private int getDupeSize() {
        if (this.mc.player.getVehicle() != this.entity || this.entity.hasChest() || this.mc.player.currentScreenHandler.getStacks().size() == 46) {
            return 0;
        }
        return this.mc.player.currentScreenHandler.getStacks().size() - 38;
    }

    @Override
    public void onActivate() {
        this.timer = 0;
    }
}

