/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.SortPriority;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.DamageCalcUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.PlayerUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.player.Safety;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.hit.BlockHitResult;

public class AnchorAura
extends Module {
    private final Setting<Double> targetRange;
    private int placeDelayLeft;
    private final Setting<Safety> breakMode;
    private final Setting<Boolean> pauseOnDrink;
    private final Setting<SettingColor> sideColor;
    private final Setting<Double> maxDamage;
    private final Setting<Boolean> place;
    private final SettingGroup sgPlace;
    private final Setting<PlaceMode> placePositions;
    private final Setting<Boolean> pauseOnMine;
    private int breakDelayLeft;
    private final Setting<Boolean> renderPlace;
    private final SettingGroup sgRender;
    private PlayerEntity target;
    private final Setting<Boolean> renderBreak;
    private final Setting<Integer> breakDelay;
    private final Setting<SettingColor> lineColor;
    private final Setting<RotationMode> rotationMode;
    private final Setting<Double> breakRange;
    private final Setting<SettingColor> breakLineColor;
    private final SettingGroup sgMisc;
    private final Setting<SortPriority> priority;
    private final Setting<Boolean> pauseOnEat;
    private final SettingGroup sgBreak;
    private final Setting<Safety> placeMode;
    private final Setting<Double> minHealth;
    private final Setting<Integer> placeDelay;
    private final Setting<ShapeMode> shapeMode;
    private final SettingGroup sgPause;
    private final Setting<SettingColor> breakSideColor;
    private final Setting<Double> placeRange;

    @EventHandler
    private void onTick(TickEvent.Post post) {
        BlockPos BlockPos2;
        if (this.mc.world.getDimension().isRespawnAnchorWorking()) {
            ChatUtils.moduleError(this, "You are in the Nether... disabling.", new Object[0]);
            this.toggle();
            return;
        }
        if (PlayerUtils.shouldPause(this.pauseOnMine.get(), this.pauseOnEat.get(), this.pauseOnDrink.get())) {
            return;
        }
        if ((double)EntityUtils.getTotalHealth((PlayerEntity)this.mc.player) <= this.minHealth.get()) {
            return;
        }
        if (EntityUtils.isInvalid(this.target, this.targetRange.get())) {
            this.target = EntityUtils.getPlayerTarget(this.targetRange.get(), this.priority.get(), false);
        }
        if (this.target == null) {
            return;
        }
        int n = InvUtils.findItemInHotbar(Items.RESPAWN_ANCHOR);
        int n2 = InvUtils.findItemInHotbar(Items.GLOWSTONE);
        if (n == -1 || n2 == -1) {
            return;
        }
        if (this.breakDelayLeft >= this.breakDelay.get() && (BlockPos2 = this.findBreakPos(this.target.getBlockPos())) != null) {
            this.mc.player.setSneaking(false);
            this.mc.options.keySneak.setPressed(false);
            if (this.rotationMode.get() == RotationMode.Both || this.rotationMode.get() == RotationMode.Break) {
                Rotations.rotate(Rotations.getYaw(BlockPos2), Rotations.getPitch(BlockPos2), 50, () -> this.lambda$onTick$0(BlockPos2, n2, n));
            } else {
                this.breakAnchor(BlockPos2, n2, n);
            }
            this.breakDelayLeft = 0;
        }
        if (this.placeDelayLeft >= this.placeDelay.get() && this.place.get().booleanValue() && (BlockPos2 = this.findPlacePos(this.target.getBlockPos())) != null) {
            this.mc.player.setSneaking(false);
            this.mc.options.keySneak.setPressed(false);
            BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n, this.rotationMode.get() == RotationMode.Place || this.rotationMode.get() == RotationMode.Both, 50, false);
            this.placeDelayLeft = 0;
        }
        ++this.placeDelayLeft;
        ++this.breakDelayLeft;
    }

    private BlockPos findBreakPos(BlockPos BlockPos2) {
        if (this.isValidBreak(BlockPos2.down())) {
            return BlockPos2.down();
        }
        if (this.isValidBreak(BlockPos2.up(2))) {
            return BlockPos2.up(2);
        }
        if (this.isValidBreak(BlockPos2.add(1, 0, 0))) {
            return BlockPos2.add(1, 0, 0);
        }
        if (this.isValidBreak(BlockPos2.add(-1, 0, 0))) {
            return BlockPos2.add(-1, 0, 0);
        }
        if (this.isValidBreak(BlockPos2.add(0, 0, 1))) {
            return BlockPos2.add(0, 0, 1);
        }
        if (this.isValidBreak(BlockPos2.add(0, 0, -1))) {
            return BlockPos2.add(0, 0, -1);
        }
        if (this.isValidBreak(BlockPos2.add(1, 1, 0))) {
            return BlockPos2.add(1, 1, 0);
        }
        if (this.isValidBreak(BlockPos2.add(-1, -1, 0))) {
            return BlockPos2.add(-1, -1, 0);
        }
        if (this.isValidBreak(BlockPos2.add(0, 1, 1))) {
            return BlockPos2.add(0, 1, 1);
        }
        if (this.isValidBreak(BlockPos2.add(0, 0, -1))) {
            return BlockPos2.add(0, 0, -1);
        }
        return null;
    }

    private void breakAnchor(BlockPos BlockPos2, int n, int n2) {
        if (BlockPos2 == null || this.mc.world.getBlockState(BlockPos2).getBlock() != Blocks.RESPAWN_ANCHOR) {
            return;
        }
        if (n != -1 && n2 != -1) {
            int n3 = this.mc.player.inventory.selectedSlot;
            this.mc.player.inventory.selectedSlot = n;
            this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand.MAIN_HAND, new BlockHitResult(new Vec3d((double)BlockPos2.getX() + 0.5, (double)BlockPos2.getY() + 0.5, (double)BlockPos2.getZ() + 0.5), Direction.UP, BlockPos2, true));
            this.mc.player.inventory.selectedSlot = n2;
            this.mc.interactionManager.interactBlock(this.mc.player, this.mc.world, Hand.MAIN_HAND, new BlockHitResult(new Vec3d((double)BlockPos2.getX() + 0.5, (double)BlockPos2.getY() + 0.5, (double)BlockPos2.getZ() + 0.5), Direction.UP, BlockPos2, true));
            this.mc.player.inventory.selectedSlot = n3;
        }
    }

    private boolean isValidPlace(BlockPos BlockPos2) {
        return this.mc.world.getBlockState(BlockPos2).isAir() && Math.sqrt(this.mc.player.getBlockPos().getSquaredDistance((Vec3i)BlockPos2)) <= this.placeRange.get() && this.getDamagePlace(BlockPos2);
    }

    private BlockPos findPlacePos(BlockPos BlockPos2) {
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$combat$AnchorAura$PlaceMode[this.placePositions.get().ordinal()]) {
            case 1: {
                if (this.isValidPlace(BlockPos2.down())) {
                    return BlockPos2.down();
                }
                if (this.isValidPlace(BlockPos2.up(2))) {
                    return BlockPos2.up(2);
                }
                if (this.isValidPlace(BlockPos2.add(1, 0, 0))) {
                    return BlockPos2.add(1, 0, 0);
                }
                if (this.isValidPlace(BlockPos2.add(-1, 0, 0))) {
                    return BlockPos2.add(-1, 0, 0);
                }
                if (this.isValidPlace(BlockPos2.add(0, 0, 1))) {
                    return BlockPos2.add(0, 0, 1);
                }
                if (this.isValidPlace(BlockPos2.add(0, 0, -1))) {
                    return BlockPos2.add(0, 0, -1);
                }
                if (this.isValidPlace(BlockPos2.add(1, 1, 0))) {
                    return BlockPos2.add(1, 1, 0);
                }
                if (this.isValidPlace(BlockPos2.add(-1, -1, 0))) {
                    return BlockPos2.add(-1, -1, 0);
                }
                if (this.isValidPlace(BlockPos2.add(0, 1, 1))) {
                    return BlockPos2.add(0, 1, 1);
                }
                if (!this.isValidPlace(BlockPos2.add(0, 0, -1))) break;
                return BlockPos2.add(0, 0, -1);
            }
            case 2: {
                if (!this.isValidPlace(BlockPos2.up(2))) break;
                return BlockPos2.up(2);
            }
            case 3: {
                if (this.isValidPlace(BlockPos2.down())) {
                    return BlockPos2.down();
                }
                if (!this.isValidPlace(BlockPos2.up(2))) break;
                return BlockPos2.up(2);
            }
        }
        return null;
    }

    @Override
    public void onDeactivate() {
        this.target = null;
    }

    private boolean getDamageBreak(BlockPos BlockPos2) {
        return this.breakMode.get() == Safety.Suicide || DamageCalcUtils.anchorDamage((LivingEntity)this.mc.player, Utils.vec3d(BlockPos2.add(0.5, 0.5, 0.5))) <= this.maxDamage.get();
    }

    private boolean getDamagePlace(BlockPos BlockPos2) {
        return this.placeMode.get() == Safety.Suicide || DamageCalcUtils.bedDamage((LivingEntity)this.mc.player, Utils.vec3d(BlockPos2.add(0.5, 0.5, 0.5))) <= this.maxDamage.get();
    }

    private void lambda$onTick$0(BlockPos BlockPos2, int n, int n2) {
        this.breakAnchor(BlockPos2, n, n2);
    }

    private boolean isValidBreak(BlockPos BlockPos2) {
        return this.mc.world.getBlockState(BlockPos2).getBlock() == Blocks.RESPAWN_ANCHOR && Math.sqrt(this.mc.player.getBlockPos().getSquaredDistance((Vec3i)BlockPos2)) <= this.breakRange.get() && this.getDamageBreak(BlockPos2);
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (this.target != null) {
            BlockPos BlockPos2;
            if (this.renderPlace.get().booleanValue() && (BlockPos2 = this.findPlacePos(this.target.getBlockPos())) != null) {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, BlockPos2.getX(), BlockPos2.getY(), BlockPos2.getZ(), 1.0, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
            }
            if (this.renderBreak.get().booleanValue() && (BlockPos2 = this.findBreakPos(this.target.getBlockPos())) != null) {
                Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, BlockPos2.getX(), BlockPos2.getY(), BlockPos2.getZ(), 1.0, this.breakSideColor.get(), this.breakLineColor.get(), this.shapeMode.get(), 0);
            }
        }
    }

    @Override
    public void onActivate() {
        this.placeDelayLeft = 0;
        this.breakDelayLeft = 0;
        this.target = null;
    }

    public AnchorAura() {
        super(Categories.Combat, "anchor-aura", "Automatically places and breaks Respawn Anchors to harm entities.");
        this.sgPlace = this.settings.createGroup("Place");
        this.sgBreak = this.settings.createGroup("Break");
        this.sgPause = this.settings.createGroup("Pause");
        this.sgMisc = this.settings.createGroup("Misc");
        this.sgRender = this.settings.createGroup("Render");
        this.placeDelay = this.sgPlace.add(new IntSetting.Builder().name("place-delay").description("The tick delay between placing anchors.").defaultValue(2).min(0).max(20).build());
        this.placeMode = this.sgPlace.add(new EnumSetting.Builder().name("place-mode").description("The way anchors are allowed to be placed near you.").defaultValue(Safety.Safe).build());
        this.placeRange = this.sgPlace.add(new DoubleSetting.Builder().name("place-range").description("The radius in which anchors are placed in.").defaultValue(3.0).min(0.0).sliderMax(5.0).build());
        this.placePositions = this.sgPlace.add(new EnumSetting.Builder().name("placement-positions").description("Where the Anchors will be placed on the entity.").defaultValue(PlaceMode.AboveAndBelow).build());
        this.place = this.sgPlace.add(new BoolSetting.Builder().name("place").description("Allows Anchor Aura to place anchors.").defaultValue(true).build());
        this.breakDelay = this.sgBreak.add(new IntSetting.Builder().name("break-delay").description("The tick delay between breaking anchors.").defaultValue(10).min(0).max(10).build());
        this.breakMode = this.sgBreak.add(new EnumSetting.Builder().name("break-mode").description("The way anchors are allowed to be broken near you.").defaultValue(Safety.Safe).build());
        this.breakRange = this.sgBreak.add(new DoubleSetting.Builder().name("break-range").description("The radius in which anchors are broken in.").defaultValue(3.0).min(0.0).sliderMax(5.0).build());
        this.pauseOnEat = this.sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses while eating.").defaultValue(false).build());
        this.pauseOnDrink = this.sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses while drinking potions.").defaultValue(false).build());
        this.pauseOnMine = this.sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses while mining blocks.").defaultValue(false).build());
        this.rotationMode = this.sgMisc.add(new EnumSetting.Builder().name("rotation-mode").description("The mode to rotate you server-side.").defaultValue(RotationMode.Both).build());
        this.targetRange = this.sgMisc.add(new DoubleSetting.Builder().name("target-range").description("The radius in which players get targeted.").defaultValue(4.0).min(0.0).sliderMax(5.0).build());
        this.priority = this.sgMisc.add(new EnumSetting.Builder().name("priority").description("How to select the player to target.").defaultValue(SortPriority.LowestHealth).build());
        this.maxDamage = this.sgMisc.add(new DoubleSetting.Builder().name("max-self-damage").description("The maximum self-damage allowed.").defaultValue(8.0).build());
        this.minHealth = this.sgMisc.add(new DoubleSetting.Builder().name("min-health").description("The minimum health you have to be for Anchor Aura to work.").defaultValue(15.0).build());
        this.renderPlace = this.sgRender.add(new BoolSetting.Builder().name("render-place").description("Renders the block where it is placing an anchor.").defaultValue(true).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("place-side-color").description("The side color for positions to be placed.").defaultValue(new SettingColor(255, 0, 0, 75)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("place-line-color").description("The line color for positions to be placed.").defaultValue(new SettingColor(255, 0, 0, 255)).build());
        this.renderBreak = this.sgRender.add(new BoolSetting.Builder().name("render-break").description("Renders the block where it is breaking an anchor.").defaultValue(true).build());
        this.breakSideColor = this.sgRender.add(new ColorSetting.Builder().name("break-side-color").description("The side color for anchors to be broken.").defaultValue(new SettingColor(255, 0, 0, 75)).build());
        this.breakLineColor = this.sgRender.add(new ColorSetting.Builder().name("break-line-color").description("The line color for anchors to be broken.").defaultValue(new SettingColor(255, 0, 0, 255)).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
    }

    @Override
    public String getInfoString() {
        if (this.target != null) {
            return this.target.getEntityName();
        }
        return null;
    }

    public static final class PlaceMode
    extends Enum<PlaceMode> {
        private static final PlaceMode[] $VALUES;
        public static final /* enum */ PlaceMode Above;
        public static final /* enum */ PlaceMode All;
        public static final /* enum */ PlaceMode AboveAndBelow;

        public static PlaceMode[] values() {
            return (PlaceMode[])$VALUES.clone();
        }

        private static PlaceMode[] $values() {
            return new PlaceMode[]{Above, AboveAndBelow, All};
        }

        static {
            Above = new PlaceMode();
            AboveAndBelow = new PlaceMode();
            All = new PlaceMode();
            $VALUES = PlaceMode.$values();
        }

        public static PlaceMode valueOf(String string) {
            return Enum.valueOf(PlaceMode.class, string);
        }
    }

    public static final class RotationMode
    extends Enum<RotationMode> {
        public static final /* enum */ RotationMode Place = new RotationMode();
        public static final /* enum */ RotationMode None;
        private static final RotationMode[] $VALUES;
        public static final /* enum */ RotationMode Both;
        public static final /* enum */ RotationMode Break;

        public static RotationMode[] values() {
            return (RotationMode[])$VALUES.clone();
        }

        private static RotationMode[] $values() {
            return new RotationMode[]{Place, Break, Both, None};
        }

        public static RotationMode valueOf(String string) {
            return Enum.valueOf(RotationMode.class, string);
        }

        static {
            Break = new RotationMode();
            Both = new RotationMode();
            None = new RotationMode();
            $VALUES = RotationMode.$values();
        }
    }
}

