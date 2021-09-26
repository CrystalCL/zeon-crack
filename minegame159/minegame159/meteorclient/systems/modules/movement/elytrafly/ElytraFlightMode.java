/*
 * Decompiled with CFR 0.151.
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
    protected final ElytraFly settings = Modules.get().get(ElytraFly.class);
    protected int jumpTimer;
    protected double ticksLeft;
    private final ElytraFlightModes type;
    protected Vec3d forward;
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    protected boolean lastJumpPressed;
    protected double velY;
    protected double velZ;
    protected boolean incrementJumpTimer;
    protected Vec3d right;
    protected double velX;
    protected boolean lastForwardPressed;

    public void onPlayerMove() {
    }

    public String getHudString() {
        return this.type.name();
    }

    public ElytraFlightMode(ElytraFlightModes elytraFlightModes) {
        this.type = elytraFlightModes;
    }

    public void onActivate() {
        this.lastJumpPressed = false;
        this.jumpTimer = 0;
        this.ticksLeft = 0.0;
    }

    private boolean lambda$onTick$0(ItemStack ItemStack2) {
        return ItemStack2.getMaxDamage() - ItemStack2.getDamage() > this.settings.replaceDurability.get();
    }

    public void handleHorizontalSpeed() {
        boolean bl = false;
        boolean bl2 = false;
        if (this.mc.options.keyForward.isPressed()) {
            this.velX += this.forward.x * this.settings.horizontalSpeed.get() * 10.0;
            this.velZ += this.forward.z * this.settings.horizontalSpeed.get() * 10.0;
            bl = true;
        } else if (this.mc.options.keyBack.isPressed()) {
            this.velX -= this.forward.x * this.settings.horizontalSpeed.get() * 10.0;
            this.velZ -= this.forward.z * this.settings.horizontalSpeed.get() * 10.0;
            bl = true;
        }
        if (this.mc.options.keyRight.isPressed()) {
            this.velX += this.right.x * this.settings.horizontalSpeed.get() * 10.0;
            this.velZ += this.right.z * this.settings.horizontalSpeed.get() * 10.0;
            bl2 = true;
        } else if (this.mc.options.keyLeft.isPressed()) {
            this.velX -= this.right.x * this.settings.horizontalSpeed.get() * 10.0;
            this.velZ -= this.right.z * this.settings.horizontalSpeed.get() * 10.0;
            bl2 = true;
        }
        if (bl && bl2) {
            double d = 1.0 / Math.sqrt(2.0);
            this.velX *= d;
            this.velZ *= d;
        }
    }

    public void handleVerticalSpeed() {
        if (this.mc.options.keyJump.isPressed()) {
            this.velY += 0.5 * this.settings.verticalSpeed.get();
        } else if (this.mc.options.keySneak.isPressed()) {
            this.velY -= 0.5 * this.settings.verticalSpeed.get();
        }
    }

    public void handleAutopilot() {
        if (this.settings.moveForward.get().booleanValue() && this.mc.player.getY() > this.settings.autoPilotMinimumHeight.get()) {
            this.mc.options.keyForward.setPressed(true);
        }
        this.lastForwardPressed = true;
        if (this.settings.useFireworks.get().booleanValue()) {
            int n = InvUtils.findItemInHotbar(Items.FIREWORK_ROCKET);
            int n2 = this.mc.player.inventory.selectedSlot;
            if (!this.mc.player.isFallFlying()) {
                return;
            }
            if (n == -1 && this.mc.player.getOffHandStack().getItem() != Items.FIREWORK_ROCKET) {
                return;
            }
            Hand Hand2 = InvUtils.getHand(Items.FIREWORK_ROCKET);
            if (this.ticksLeft <= 0.0) {
                this.ticksLeft = this.settings.autoPilotFireworkDelay.get() * 20.0;
                if (n != -1) {
                    this.mc.player.inventory.selectedSlot = n;
                    this.mc.interactionManager.interactItem((PlayerEntity)this.mc.player, (World)this.mc.world, Hand2);
                    this.mc.player.swingHand(Hand2);
                    if (this.settings.autoPilotFireworkGhosthand.get().booleanValue()) {
                        this.mc.player.inventory.selectedSlot = n2;
                    }
                } else if (this.mc.player.getOffHandStack().getItem() == Items.FIREWORK_ROCKET) {
                    this.mc.interactionManager.interactItem((PlayerEntity)this.mc.player, (World)this.mc.world, Hand2);
                    this.mc.player.swingHand(Hand2);
                }
            }
            this.ticksLeft -= 1.0;
        }
    }

    public void handleFallMultiplier() {
        if (this.velY < 0.0) {
            this.velY *= this.settings.fallMultiplier.get().doubleValue();
        } else if (this.velY > 0.0) {
            this.velY = 0.0;
        }
    }

    public void onTick() {
        ItemStack ItemStack2;
        if (this.settings.replace.get().booleanValue() && (ItemStack2 = this.mc.player.inventory.getArmorStack(2)).getItem() == Items.ELYTRA && ItemStack2.getMaxDamage() - ItemStack2.getDamage() <= this.settings.replaceDurability.get()) {
            int n = InvUtils.findItemInAll(Items.ELYTRA, this::lambda$onTick$0);
            InvUtils.move().from(n).toArmor(2);
        }
    }

    public void onDeactivate() {
    }

    public void onPacketSend(PacketEvent.Send send) {
    }

    public void autoTakeoff() {
        if (this.incrementJumpTimer) {
            ++this.jumpTimer;
        }
        boolean bl = this.mc.options.keyJump.isPressed();
        if (this.settings.autoTakeOff.get().booleanValue() && bl) {
            if (!this.lastJumpPressed && !this.mc.player.isFallFlying()) {
                this.jumpTimer = 0;
                this.incrementJumpTimer = true;
            }
            if (this.jumpTimer >= 8) {
                this.jumpTimer = 0;
                this.incrementJumpTimer = false;
                this.mc.player.setJumping(false);
                this.mc.player.setSprinting(true);
                this.mc.player.jump();
                this.mc.getNetworkHandler().sendPacket((Packet)new ClientCommandC2SPacket((Entity)this.mc.player, ClientCommandC2SPacket.class_2849.START_FALL_FLYING));
            }
        }
        this.lastJumpPressed = bl;
    }
}

