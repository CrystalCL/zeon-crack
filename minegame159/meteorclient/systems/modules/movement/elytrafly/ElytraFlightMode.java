/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.world.World
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.network.Packet
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket$class_2849
 *  net.minecraft.client.MinecraftClient
 */
package minegame159.meteorclient.systems.modules.movement.elytrafly;

import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.movement.elytrafly.ElytraFlightModes;
import minegame159.meteorclient.systems.modules.movement.elytrafly.ElytraFly;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.client.MinecraftClient;

public class ElytraFlightMode {
    protected /* synthetic */ double velZ;
    protected final /* synthetic */ MinecraftClient mc;
    protected /* synthetic */ boolean lastJumpPressed;
    protected /* synthetic */ double velY;
    protected final /* synthetic */ ElytraFly settings;
    private final /* synthetic */ ElytraFlightModes type;
    protected /* synthetic */ boolean lastForwardPressed;
    protected /* synthetic */ int jumpTimer;
    protected /* synthetic */ boolean incrementJumpTimer;
    protected /* synthetic */ Vec3d forward;
    protected /* synthetic */ double velX;
    protected /* synthetic */ Vec3d right;
    protected /* synthetic */ double ticksLeft;

    public void onTick() {
        ItemStack lIlIIIlllIllII;
        ElytraFlightMode lIlIIIlllIlIlI;
        if (lIlIIIlllIlIlI.settings.replace.get().booleanValue() && (lIlIIIlllIllII = lIlIIIlllIlIlI.mc.player.inventory.getArmorStack(2)).getItem() == Items.ELYTRA && lIlIIIlllIllII.getMaxDamage() - lIlIIIlllIllII.getDamage() <= lIlIIIlllIlIlI.settings.replaceDurability.get()) {
            int lIlIIIlllIllIl = InvUtils.findItemInAll(Items.ELYTRA, lIlIIIlIlIIlII -> {
                ElytraFlightMode lIlIIIlIlIIlIl;
                return lIlIIIlIlIIlII.getMaxDamage() - lIlIIIlIlIIlII.getDamage() > lIlIIIlIlIIlIl.settings.replaceDurability.get();
            });
            InvUtils.move().from(lIlIIIlllIllIl).toArmor(2);
        }
    }

    public void handleFallMultiplier() {
        ElytraFlightMode lIlIIIlIllIIlI;
        if (lIlIIIlIllIIlI.velY < 0.0) {
            lIlIIIlIllIIlI.velY *= lIlIIIlIllIIlI.settings.fallMultiplier.get().doubleValue();
        } else if (lIlIIIlIllIIlI.velY > 0.0) {
            lIlIIIlIllIIlI.velY = 0.0;
        }
    }

    public void onDeactivate() {
    }

    public void handleHorizontalSpeed() {
        ElytraFlightMode lIlIIIlIlllllI;
        boolean lIlIIIllIIIIIl = false;
        boolean lIlIIIlIllllll = false;
        if (lIlIIIlIlllllI.mc.options.keyForward.isPressed()) {
            lIlIIIlIlllllI.velX += lIlIIIlIlllllI.forward.x * lIlIIIlIlllllI.settings.horizontalSpeed.get() * 10.0;
            lIlIIIlIlllllI.velZ += lIlIIIlIlllllI.forward.z * lIlIIIlIlllllI.settings.horizontalSpeed.get() * 10.0;
            lIlIIIllIIIIIl = true;
        } else if (lIlIIIlIlllllI.mc.options.keyBack.isPressed()) {
            lIlIIIlIlllllI.velX -= lIlIIIlIlllllI.forward.x * lIlIIIlIlllllI.settings.horizontalSpeed.get() * 10.0;
            lIlIIIlIlllllI.velZ -= lIlIIIlIlllllI.forward.z * lIlIIIlIlllllI.settings.horizontalSpeed.get() * 10.0;
            lIlIIIllIIIIIl = true;
        }
        if (lIlIIIlIlllllI.mc.options.keyRight.isPressed()) {
            lIlIIIlIlllllI.velX += lIlIIIlIlllllI.right.x * lIlIIIlIlllllI.settings.horizontalSpeed.get() * 10.0;
            lIlIIIlIlllllI.velZ += lIlIIIlIlllllI.right.z * lIlIIIlIlllllI.settings.horizontalSpeed.get() * 10.0;
            lIlIIIlIllllll = true;
        } else if (lIlIIIlIlllllI.mc.options.keyLeft.isPressed()) {
            lIlIIIlIlllllI.velX -= lIlIIIlIlllllI.right.x * lIlIIIlIlllllI.settings.horizontalSpeed.get() * 10.0;
            lIlIIIlIlllllI.velZ -= lIlIIIlIlllllI.right.z * lIlIIIlIlllllI.settings.horizontalSpeed.get() * 10.0;
            lIlIIIlIllllll = true;
        }
        if (lIlIIIllIIIIIl && lIlIIIlIllllll) {
            double lIlIIIllIIIlIl = 1.0 / Math.sqrt(2.0);
            lIlIIIlIlllllI.velX *= lIlIIIllIIIlIl;
            lIlIIIlIlllllI.velZ *= lIlIIIllIIIlIl;
        }
    }

    public String getHudString() {
        ElytraFlightMode lIlIIIlIlIlIlI;
        return lIlIIIlIlIlIlI.type.name();
    }

    public void autoTakeoff() {
        ElytraFlightMode lIlIIIllIllIll;
        if (lIlIIIllIllIll.incrementJumpTimer) {
            ++lIlIIIllIllIll.jumpTimer;
        }
        boolean lIlIIIllIllIlI = lIlIIIllIllIll.mc.options.keyJump.isPressed();
        if (lIlIIIllIllIll.settings.autoTakeOff.get().booleanValue() && lIlIIIllIllIlI) {
            if (!lIlIIIllIllIll.lastJumpPressed && !lIlIIIllIllIll.mc.player.isFallFlying()) {
                lIlIIIllIllIll.jumpTimer = 0;
                lIlIIIllIllIll.incrementJumpTimer = true;
            }
            if (lIlIIIllIllIll.jumpTimer >= 8) {
                lIlIIIllIllIll.jumpTimer = 0;
                lIlIIIllIllIll.incrementJumpTimer = false;
                lIlIIIllIllIll.mc.player.setJumping(false);
                lIlIIIllIllIll.mc.player.setSprinting(true);
                lIlIIIllIllIll.mc.player.jump();
                lIlIIIllIllIll.mc.getNetworkHandler().sendPacket((Packet)new ClientCommandC2SPacket((Entity)lIlIIIllIllIll.mc.player, ClientCommandC2SPacket.class_2849.START_FALL_FLYING));
            }
        }
        lIlIIIllIllIll.lastJumpPressed = lIlIIIllIllIlI;
    }

    public void handleAutopilot() {
        ElytraFlightMode lIlIIIllIIlllI;
        if (lIlIIIllIIlllI.settings.moveForward.get().booleanValue() && lIlIIIllIIlllI.mc.player.getY() > lIlIIIllIIlllI.settings.autoPilotMinimumHeight.get()) {
            lIlIIIllIIlllI.mc.options.keyForward.setPressed(true);
        }
        lIlIIIllIIlllI.lastForwardPressed = true;
        if (lIlIIIllIIlllI.settings.useFireworks.get().booleanValue()) {
            int lIlIIIllIlIIlI = InvUtils.findItemInHotbar(Items.FIREWORK_ROCKET);
            int lIlIIIllIlIIIl = lIlIIIllIIlllI.mc.player.inventory.selectedSlot;
            if (!lIlIIIllIIlllI.mc.player.isFallFlying()) {
                return;
            }
            if (lIlIIIllIlIIlI == -1 && lIlIIIllIIlllI.mc.player.getOffHandStack().getItem() != Items.FIREWORK_ROCKET) {
                return;
            }
            Hand lIlIIIllIlIIII = InvUtils.getHand(Items.FIREWORK_ROCKET);
            if (lIlIIIllIIlllI.ticksLeft <= 0.0) {
                lIlIIIllIIlllI.ticksLeft = lIlIIIllIIlllI.settings.autoPilotFireworkDelay.get() * 20.0;
                if (lIlIIIllIlIIlI != -1) {
                    lIlIIIllIIlllI.mc.player.inventory.selectedSlot = lIlIIIllIlIIlI;
                    lIlIIIllIIlllI.mc.interactionManager.interactItem((PlayerEntity)lIlIIIllIIlllI.mc.player, (World)lIlIIIllIIlllI.mc.world, lIlIIIllIlIIII);
                    lIlIIIllIIlllI.mc.player.swingHand(lIlIIIllIlIIII);
                    if (lIlIIIllIIlllI.settings.autoPilotFireworkGhosthand.get().booleanValue()) {
                        lIlIIIllIIlllI.mc.player.inventory.selectedSlot = lIlIIIllIlIIIl;
                    }
                } else if (lIlIIIllIIlllI.mc.player.getOffHandStack().getItem() == Items.FIREWORK_ROCKET) {
                    lIlIIIllIIlllI.mc.interactionManager.interactItem((PlayerEntity)lIlIIIllIIlllI.mc.player, (World)lIlIIIllIIlllI.mc.world, lIlIIIllIlIIII);
                    lIlIIIllIIlllI.mc.player.swingHand(lIlIIIllIlIIII);
                }
            }
            lIlIIIllIIlllI.ticksLeft -= 1.0;
        }
    }

    public void onActivate() {
        lIlIIIlllIIIlI.lastJumpPressed = false;
        lIlIIIlllIIIlI.jumpTimer = 0;
        lIlIIIlllIIIlI.ticksLeft = 0.0;
    }

    public void onPlayerMove() {
    }

    public ElytraFlightMode(ElytraFlightModes lIlIIIllllIIll) {
        ElytraFlightMode lIlIIIllllIlII;
        lIlIIIllllIlII.settings = Modules.get().get(ElytraFly.class);
        lIlIIIllllIlII.mc = MinecraftClient.getInstance();
        lIlIIIllllIlII.type = lIlIIIllllIIll;
    }

    public void onPacketSend(PacketEvent.Send lIlIIIlllIIllI) {
    }

    public void handleVerticalSpeed() {
        ElytraFlightMode lIlIIIlIllIllI;
        if (lIlIIIlIllIllI.mc.options.keyJump.isPressed()) {
            lIlIIIlIllIllI.velY += 0.5 * lIlIIIlIllIllI.settings.verticalSpeed.get();
        } else if (lIlIIIlIllIllI.mc.options.keySneak.isPressed()) {
            lIlIIIlIllIllI.velY -= 0.5 * lIlIIIlIllIllI.settings.verticalSpeed.get();
        }
    }
}

