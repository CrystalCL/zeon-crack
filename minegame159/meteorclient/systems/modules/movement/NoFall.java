/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EquipmentSlot
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Items
 *  net.minecraft.world.World
 *  net.minecraft.util.hit.HitResult.Type
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
 *  net.minecraft.fluid.Fluids
 *  net.minecraft.world.RaycastContext
 *  net.minecraft.world.RaycastContext$class_242
 *  net.minecraft.world.RaycastContext$class_3960
 *  net.minecraft.util.hit.BlockHitResult
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
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraft.util.hit.HitResult;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;

public class NoFall
extends Module {
    private final /* synthetic */ Setting<PlaceMode> placeMode;
    private final /* synthetic */ Setting<Mode> mode;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ boolean placedWater;
    private /* synthetic */ int fallHeightBaritone;
    private final /* synthetic */ Setting<Boolean> baritone;
    private final /* synthetic */ Setting<Double> height;
    private final /* synthetic */ Setting<Boolean> elytra;

    @EventHandler
    private void onTick(TickEvent.Pre lIIIIIIIIIllI) {
        NoFall lIIIIIIIIIlIl;
        if (lIIIIIIIIIlIl.mc.player.abilities.creativeMode) {
            return;
        }
        if (lIIIIIIIIIlIl.mode.get() == Mode.AirPlace && (lIIIIIIIIIlIl.placeMode.get() == PlaceMode.BeforeDamage && lIIIIIIIIIlIl.mc.player.fallDistance > 2.0f || lIIIIIIIIIlIl.placeMode.get() == PlaceMode.BeforeDeath && lIIIIIIIIIlIl.mc.player.getHealth() + lIIIIIIIIIlIl.mc.player.getAbsorptionAmount() < lIIIIIIIIIlIl.mc.player.fallDistance)) {
            int lIIIIIIIIlIll = InvUtils.findItemInHotbar(lllllllIlIII -> lllllllIlIII.getItem() instanceof BlockItem);
            if (lIIIIIIIIlIll != -1) {
                BlockUtils.place(lIIIIIIIIIlIl.mc.player.getBlockPos().down(), Hand.MAIN_HAND, lIIIIIIIIlIll, true, 10, true);
            }
        } else if (lIIIIIIIIIlIl.mode.get() == Mode.Bucket) {
            BlockHitResult lIIIIIIIIlIIl;
            int lIIIIIIIIlIII;
            if (lIIIIIIIIIlIl.placedWater) {
                int lIIIIIIIIlIlI = InvUtils.findItemInHotbar(Items.BUCKET);
                if (lIIIIIIIIlIlI != -1 && lIIIIIIIIIlIl.mc.player.getBlockState().getFluidState().getFluid() == Fluids.WATER) {
                    lIIIIIIIIIlIl.useBucket(lIIIIIIIIlIlI, false);
                }
            } else if (lIIIIIIIIIlIl.mc.player.fallDistance > 3.0f && !EntityUtils.isAboveWater((Entity)lIIIIIIIIIlIl.mc.player) && (lIIIIIIIIlIII = InvUtils.findItemInHotbar(Items.WATER_BUCKET)) != -1 && (lIIIIIIIIlIIl = lIIIIIIIIIlIl.mc.world.raycast(new RaycastContext(lIIIIIIIIIlIl.mc.player.getPos(), lIIIIIIIIIlIl.mc.player.getPos().subtract(0.0, 5.0, 0.0), RaycastContext.class_3960.OUTLINE, RaycastContext.class_242.NONE, (Entity)lIIIIIIIIIlIl.mc.player))) != null && lIIIIIIIIlIIl.getType() == Type.BLOCK) {
                lIIIIIIIIIlIl.useBucket(lIIIIIIIIlIII, true);
            }
        }
        if (lIIIIIIIIIlIl.mc.player.fallDistance == 0.0f) {
            lIIIIIIIIIlIl.placedWater = false;
        }
    }

    private void useBucket(int lllllllllIll, boolean lllllllllIlI) {
        NoFall llllllllllII;
        Rotations.rotate(llllllllllII.mc.player.yaw, 90.0, 10, true, () -> {
            NoFall lllllllIlllI;
            int lllllllIllll = lllllllIlllI.mc.player.inventory.selectedSlot;
            lllllllIlllI.mc.player.inventory.selectedSlot = lllllllllIll;
            lllllllIlllI.mc.interactionManager.interactItem((PlayerEntity)lllllllIlllI.mc.player, (World)lllllllIlllI.mc.world, Hand.MAIN_HAND);
            lllllllIlllI.mc.player.inventory.selectedSlot = lllllllIllll;
            lllllllIlllI.placedWater = lllllllllIlI;
        });
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send lIIIIIIIlIIII) {
        NoFall lIIIIIIIlIIIl;
        if (lIIIIIIIlIIIl.mc.player != null && lIIIIIIIlIIIl.mc.player.abilities.creativeMode) {
            return;
        }
        if (lIIIIIIIlIIII.packet instanceof PlayerMoveC2SPacket) {
            if (lIIIIIIIlIIIl.elytra.get().booleanValue() && (lIIIIIIIlIIIl.mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA && lIIIIIIIlIIIl.mc.options.keyJump.isPressed() || lIIIIIIIlIIIl.mc.player.isFallFlying())) {
                int lIIIIIIIlIlII = 0;
                while ((double)lIIIIIIIlIlII <= Math.ceil(lIIIIIIIlIIIl.height.get())) {
                    if (!lIIIIIIIlIIIl.mc.world.getBlockState(lIIIIIIIlIIIl.mc.player.getBlockPos().add(0, -lIIIIIIIlIlII, 0)).getMaterial().isReplaceable() && (double)(lIIIIIIIlIIIl.mc.player.getBlockPos().add(0, -lIIIIIIIlIlII, 0).getY() + 1) + lIIIIIIIlIIIl.height.get() >= lIIIIIIIlIIIl.mc.player.getPos().getY()) {
                        ((PlayerMoveC2SPacketAccessor)lIIIIIIIlIIII.packet).setOnGround(true);
                        return;
                    }
                    ++lIIIIIIIlIlII;
                }
            } else if (lIIIIIIIlIIIl.mode.get() == Mode.Packet && ((IPlayerMoveC2SPacket)lIIIIIIIlIIII.packet).getTag() != 1337) {
                ((PlayerMoveC2SPacketAccessor)lIIIIIIIlIIII.packet).setOnGround(true);
            }
        }
    }

    public NoFall() {
        super(Categories.Movement, "no-fall", "Prevents you from taking fall damage.");
        NoFall lIIIIIIIllllI;
        lIIIIIIIllllI.sgGeneral = lIIIIIIIllllI.settings.getDefaultGroup();
        lIIIIIIIllllI.mode = lIIIIIIIllllI.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The way you are saved from fall damage.").defaultValue(Mode.Packet).build());
        lIIIIIIIllllI.elytra = lIIIIIIIllllI.sgGeneral.add(new BoolSetting.Builder().name("elytra-compatibility").description("Stops No Fall from working when using an elytra.").defaultValue(true).build());
        lIIIIIIIllllI.baritone = lIIIIIIIllllI.sgGeneral.add(new BoolSetting.Builder().name("baritone-compatibility").description("Makes baritone assume you can fall 255 blocks without damage.").defaultValue(true).build());
        lIIIIIIIllllI.height = lIIIIIIIllllI.sgGeneral.add(new DoubleSetting.Builder().name("height").description("How high you have to be off the ground for this to toggle on.").defaultValue(0.5).min(0.1).sliderMax(1.0).build());
        lIIIIIIIllllI.placeMode = lIIIIIIIllllI.sgGeneral.add(new EnumSetting.Builder().name("place-mode").description("Whether place mode places before you die or before you take damage.").defaultValue(PlaceMode.BeforeDeath).build());
    }

    @Override
    public void onActivate() {
        NoFall lIIIIIIIlllII;
        if (lIIIIIIIlllII.baritone.get().booleanValue()) {
            lIIIIIIIlllII.fallHeightBaritone = (Integer)BaritoneAPI.getSettings().maxFallHeightNoWater.get();
            BaritoneAPI.getSettings().maxFallHeightNoWater.value = 255;
        }
        lIIIIIIIlllII.placedWater = false;
    }

    @Override
    public void onDeactivate() {
        NoFall lIIIIIIIllIII;
        if (lIIIIIIIllIII.baritone.get().booleanValue()) {
            BaritoneAPI.getSettings().maxFallHeightNoWater.value = lIIIIIIIllIII.fallHeightBaritone;
        }
    }

    @Override
    public String getInfoString() {
        NoFall lllllllllIII;
        return lllllllllIII.mode.get().toString();
    }

    public static final class PlaceMode
    extends Enum<PlaceMode> {
        public static final /* synthetic */ /* enum */ PlaceMode BeforeDeath;
        private static final /* synthetic */ PlaceMode[] $VALUES;
        public static final /* synthetic */ /* enum */ PlaceMode BeforeDamage;

        public static PlaceMode valueOf(String llllllllllllllllllIlIlIlIllIlIII) {
            return Enum.valueOf(PlaceMode.class, llllllllllllllllllIlIlIlIllIlIII);
        }

        static {
            BeforeDeath = new PlaceMode();
            BeforeDamage = new PlaceMode();
            $VALUES = PlaceMode.$values();
        }

        public static PlaceMode[] values() {
            return (PlaceMode[])$VALUES.clone();
        }

        private static /* synthetic */ PlaceMode[] $values() {
            return new PlaceMode[]{BeforeDeath, BeforeDamage};
        }

        private PlaceMode() {
            PlaceMode llllllllllllllllllIlIlIlIllIIIlI;
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Bucket;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Packet;
        public static final /* synthetic */ /* enum */ Mode AirPlace;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Packet, AirPlace, Bucket};
        }

        public static Mode valueOf(String llIIllllllIlII) {
            return Enum.valueOf(Mode.class, llIIllllllIlII);
        }

        private Mode() {
            Mode llIIlllllIllll;
        }

        static {
            Packet = new Mode();
            AirPlace = new Mode();
            Bucket = new Mode();
            $VALUES = Mode.$values();
        }
    }
}

