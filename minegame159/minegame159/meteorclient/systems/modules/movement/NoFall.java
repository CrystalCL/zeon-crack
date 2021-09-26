/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.movement;

import baritone.api.BaritoneAPI;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.PlayerMoveC2SPacketAccessor;
import minegame159.meteorclient.mixininterface.IPlayerMoveC2SPacket;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraft.util.hit.HitResult;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;

public class NoFall
extends Module {
    private final Setting<Double> height;
    private final Setting<Boolean> baritone;
    private final Setting<Mode> mode;
    private final Setting<PlaceMode> placeMode;
    private final Setting<Boolean> elytra;
    private boolean placedWater;
    private final SettingGroup sgGeneral;
    private int fallHeightBaritone;

    private void lambda$useBucket$1(int n, boolean bl) {
        int n2 = this.mc.player.inventory.selectedSlot;
        this.mc.player.inventory.selectedSlot = n;
        this.mc.interactionManager.interactItem((PlayerEntity)this.mc.player, (World)this.mc.world, Hand.MAIN_HAND);
        this.mc.player.inventory.selectedSlot = n2;
        this.placedWater = bl;
    }

    private static boolean lambda$onTick$0(ItemStack ItemStack2) {
        return ItemStack2.getItem() instanceof BlockItem;
    }

    @Override
    public String getInfoString() {
        return this.mode.get().toString();
    }

    private void useBucket(int n, boolean bl) {
        Rotations.rotate(this.mc.player.yaw, 90.0, 10, true, () -> this.lambda$useBucket$1(n, bl));
    }

    public NoFall() {
        super(Categories.Movement, "no-fall", "Prevents you from taking fall damage.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The way you are saved from fall damage.").defaultValue(Mode.Packet).build());
        this.elytra = this.sgGeneral.add(new BoolSetting.Builder().name("elytra-compatibility").description("Stops No Fall from working when using an elytra.").defaultValue(true).build());
        this.baritone = this.sgGeneral.add(new BoolSetting.Builder().name("baritone-compatibility").description("Makes baritone assume you can fall 255 blocks without damage.").defaultValue(true).build());
        this.height = this.sgGeneral.add(new DoubleSetting.Builder().name("height").description("How high you have to be off the ground for this to toggle on.").defaultValue(0.5).min(0.1).sliderMax(1.0).build());
        this.placeMode = this.sgGeneral.add(new EnumSetting.Builder().name("place-mode").description("Whether place mode places before you die or before you take damage.").defaultValue(PlaceMode.BeforeDeath).build());
    }

    @Override
    public void onActivate() {
        if (this.baritone.get().booleanValue()) {
            this.fallHeightBaritone = (Integer)BaritoneAPI.getSettings().maxFallHeightNoWater.get();
            BaritoneAPI.getSettings().maxFallHeightNoWater.value = 255;
        }
        this.placedWater = false;
    }

    @Override
    public void onDeactivate() {
        if (this.baritone.get().booleanValue()) {
            BaritoneAPI.getSettings().maxFallHeightNoWater.value = this.fallHeightBaritone;
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.mc.player.abilities.creativeMode) {
            return;
        }
        if (this.mode.get() == Mode.AirPlace && (this.placeMode.get() == PlaceMode.BeforeDamage && this.mc.player.fallDistance > 2.0f || this.placeMode.get() == PlaceMode.BeforeDeath && this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() < this.mc.player.fallDistance)) {
            int n = InvUtils.findItemInHotbar(NoFall::lambda$onTick$0);
            if (n != -1) {
                BlockUtils.place(this.mc.player.getBlockPos().down(), Hand.MAIN_HAND, n, true, 10, true);
            }
        } else if (this.mode.get() == Mode.Bucket) {
            BlockHitResult BlockHitResult2;
            int n;
            if (this.placedWater) {
                int n2 = InvUtils.findItemInHotbar(Items.BUCKET);
                if (n2 != -1 && this.mc.player.getBlockState().getFluidState().getFluid() == Fluids.WATER) {
                    this.useBucket(n2, false);
                }
            } else if (this.mc.player.fallDistance > 3.0f && !EntityUtils.isAboveWater((Entity)this.mc.player) && (n = InvUtils.findItemInHotbar(Items.WATER_BUCKET)) != -1 && (BlockHitResult2 = this.mc.world.raycast(new RaycastContext(this.mc.player.getPos(), this.mc.player.getPos().subtract(0.0, 5.0, 0.0), RaycastContext.class_3960.OUTLINE, RaycastContext.class_242.NONE, (Entity)this.mc.player))) != null && BlockHitResult2.getType() == HitResult.class_240.BLOCK) {
                this.useBucket(n, true);
            }
        }
        if (this.mc.player.fallDistance == 0.0f) {
            this.placedWater = false;
        }
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (this.mc.player != null && this.mc.player.abilities.creativeMode) {
            return;
        }
        if (send.packet instanceof PlayerMoveC2SPacket) {
            if (this.elytra.get().booleanValue() && (this.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA && this.mc.options.keyJump.isPressed() || this.mc.player.isFallFlying())) {
                int n = 0;
                while ((double)n <= Math.ceil(this.height.get())) {
                    if (!this.mc.world.getBlockState(this.mc.player.getBlockPos().add(0, -n, 0)).getMaterial().isReplaceable() && (double)(this.mc.player.getBlockPos().add(0, -n, 0).getY() + 1) + this.height.get() >= this.mc.player.getPos().getY()) {
                        ((PlayerMoveC2SPacketAccessor)send.packet).setOnGround(true);
                        return;
                    }
                    ++n;
                }
            } else if (this.mode.get() == Mode.Packet && ((IPlayerMoveC2SPacket)send.packet).getTag() != 1337) {
                ((PlayerMoveC2SPacketAccessor)send.packet).setOnGround(true);
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode AirPlace;
        public static final /* enum */ Mode Packet;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Bucket;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            Packet = new Mode();
            AirPlace = new Mode();
            Bucket = new Mode();
            $VALUES = Mode.$values();
        }

        private static Mode[] $values() {
            return new Mode[]{Packet, AirPlace, Bucket};
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }
    }

    public static final class PlaceMode
    extends Enum<PlaceMode> {
        private static final PlaceMode[] $VALUES;
        public static final /* enum */ PlaceMode BeforeDeath;
        public static final /* enum */ PlaceMode BeforeDamage;

        private static PlaceMode[] $values() {
            return new PlaceMode[]{BeforeDeath, BeforeDamage};
        }

        public static PlaceMode[] values() {
            return (PlaceMode[])$VALUES.clone();
        }

        static {
            BeforeDeath = new PlaceMode();
            BeforeDamage = new PlaceMode();
            $VALUES = PlaceMode.$values();
        }

        public static PlaceMode valueOf(String string) {
            return Enum.valueOf(PlaceMode.class, string);
        }
    }
}

