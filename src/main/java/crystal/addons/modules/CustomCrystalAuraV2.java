package crystal.addons.modules;

import com.google.common.collect.Streams;
import crystal.addons.CrystalCL;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import meteordevelopment.meteorclient.events.render.Render2DEvent;
import meteordevelopment.meteorclient.events.world.PlaySoundEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.renderer.text.TextRenderer;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.misc.Pool;
import meteordevelopment.meteorclient.utils.misc.Vec3;
import meteordevelopment.meteorclient.utils.player.DamageUtils;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.render.NametagUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import meteordevelopment.meteorclient.utils.world.TickRate;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class CustomCrystalAuraV2 extends Module {
    private final SettingGroup sgPlace = settings.createGroup("Place");
    private final SettingGroup sgBreak = settings.createGroup("Break");
    private final SettingGroup sgTarget = settings.createGroup("Target");
    private final SettingGroup sgPause = settings.createGroup("Pause");
    private final SettingGroup sgRotations = settings.createGroup("Rotations");
    private final SettingGroup sgMisc = settings.createGroup("Misc");
    private final SettingGroup sgExtra = settings.createGroup("Extra");
    private final SettingGroup sgRender = settings.createGroup("Render");
    private final Setting<Boolean> place = sgPlace.add(new BoolSetting.Builder().name("place").description("Allows Crystal Aura to place crystals.").defaultValue(true).build());    private final Setting<Boolean> allowSuicide = sgPlace.add(((((new BoolSetting.Builder()).name("allow-suicide")).description("Allows suicide mode for CA.")).defaultValue(false)).build());
    private final Setting<Integer> range = sgPlace.add(new IntSetting.Builder().name("range").description("The radius for a mode.").defaultValue(5).sliderMin(0).sliderMax(10).build());
    private final Setting<PlaceMode> PMode = sgPlace.add(new EnumSetting.Builder<PlaceMode>().name("mode").description("The placement mode for crystals.").defaultValue(PlaceMode.Fast).build());
    private final Setting<Double> placeRange = sgPlace.add(new DoubleSetting.Builder().name("place-range").description("The radius in which crystals can be placed in.").defaultValue(5.0D).min(0.0D).sliderMax(7.0D).build());
    private final Setting<Double> placeWallsRange = sgPlace.add(new DoubleSetting.Builder().name("place-walls-range").description("The radius in which crystals can be placed through walls.").defaultValue(5.0D).min(0.0D).sliderMax(7.0D).build());
    private final Setting<Boolean> multiPlace = sgPlace.add(new BoolSetting.Builder().name("multi-target").description("Allows Crystal Aura to place multiple crystals.").defaultValue(false).build());
    private final Setting<Boolean> rayTrace = sgPlace.add(new BoolSetting.Builder().name("ignore-walls").description("Whether or not to place through walls.").defaultValue(false).build());
    private final Setting<Double> minDamage = sgPlace.add(new DoubleSetting.Builder().name("min-damage").description("The minimum damage the crystal will place.").defaultValue(3.5D).build());
    private final Setting<Double> minHealth = sgPlace.add(new DoubleSetting.Builder().name("min-health").description("The minimum health you have to be for it to place.").defaultValue(4.0D).build());
    private final Setting<Boolean> surroundBreak = sgPlace.add(new BoolSetting.Builder().name("surround-break").description("Places a crystal next to a surrounded player and keeps it there so they cannot use Surround again.").defaultValue(true).build());
    private final Setting<Boolean> surroundHold = sgPlace.add(new BoolSetting.Builder().name("surround-hold").description("Places a crystal next to a player so they cannot use Surround.").defaultValue(true).build());
    private final Setting<Boolean> oldPlace = sgPlace.add(new BoolSetting.Builder().name("old-place").description("Won't place in one block holes to help compatibility with some servers.").defaultValue(false).build());
    private final Setting<Boolean> spamface = sgPlace.add(new BoolSetting.Builder().name("face-place").description("Places crystal next to the head for armor broken.").defaultValue(true).build());
    private final Setting<FaceMode> facemode = sgPlace.add(new EnumSetting.Builder<FaceMode>().name("face-place-mode").description("The method of rotating when using Crystal Aura.").defaultValue(FaceMode.Vanilla).build());
    private final Setting<Double> faceplacehealth = sgPlace.add(new DoubleSetting.Builder().name("face-place-health").description("The health required to face-place.").defaultValue(8.0D).min(1.0D).max(36.0D).build());
    private final Setting<SupportMode> support = sgPlace.add(new EnumSetting.Builder<SupportMode>().name("support-mode").description("Support mode.").defaultValue(SupportMode.Plus).build());
    private final Setting<Integer> supportDelay = sgPlace.add(new IntSetting.Builder().name("support-delay").description("The delay between support blocks being placed.").defaultValue(0).min(0).sliderMax(10).build());
    private final Setting<PMode1> mm = sgBreak.add(new EnumSetting.Builder<PMode1>().name("break-mode").description("The mode for a breaking crystals.").defaultValue(PMode1.ignor_immunity).build());
    private final Setting<BMode> breakMode = sgBreak.add(new EnumSetting.Builder<BMode>().name("break-mode").description("The type of break mode for crystals.").defaultValue(BMode.MinSelfDmg).build());
    private final Setting<Double> breakRange = sgBreak.add(new DoubleSetting.Builder().name("break-range").description("The maximum range that crystals can be to be broken.").defaultValue(6.0D).min(0.0D).sliderMax(7.0D).build());
    private final Setting<Boolean> ignoreWalls = sgBreak.add(new BoolSetting.Builder().name("ignoer-walls").description("Whether or not to break through walls.").defaultValue(false).build());
    private final Setting<CancelCrystalMode> cancelCrystalMode = sgBreak.add(new EnumSetting.Builder<CancelCrystalMode>().name("cancel-crystal").description("Mode to use for the crystals to be removed from the world.").defaultValue(CancelCrystalMode.Hit).build());
    private final Setting<TrgMode> targetMode = sgTarget.add(new EnumSetting.Builder<TrgMode>().name("target-mode").description("The way you target multiple targets.").defaultValue(TrgMode.HugeDmg).build());
    private final Setting<Object2BooleanMap<EntityType<?>>> entities = sgTarget.add(new EntityTypeListSetting.Builder().name("entities").description("Select entities to draw nametags on.").defaultValue(Utils.asO2BMap(new EntityType[]{EntityType.PLAYER})).build());
    private final Setting<Double> targetRange = sgTarget.add(new DoubleSetting.Builder().name("target-range").description("The maximum range the entity can be to be targeted.").defaultValue(7.0D).min(0.0D).sliderMax(10.0D).build());
    private final Setting<Integer> numberOfDamages = sgTarget.add(new IntSetting.Builder().name("number-of-damages").description("The number to replace 'x' with in HighestXDamages.").defaultValue(3).min(2).sliderMax(10).build());
    private final Setting<Boolean> multiTarget = sgTarget.add(new BoolSetting.Builder().name("multi-targeting").description("Will calculate damage for all entities and pick a block based on target mode.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnEat = sgPause.add(new BoolSetting.Builder().name("pause-on-eat").description("Pauses Crystal Aura while eating.").defaultValue(true).build());
    private final Setting<Boolean> pauseOnDrink = sgPause.add(new BoolSetting.Builder().name("pause-on-drink").description("Pauses Crystal Aura while drinking a potion.").defaultValue(false).build());
    private final Setting<Boolean> pauseOnMine = sgPause.add(new BoolSetting.Builder().name("pause-on-mine").description("Pauses Crystal Aura while mining blocks.").defaultValue(false).build());
    private final Setting<RotationMode> rotationMode = sgRotations.add(new EnumSetting.Builder<RotationMode>().name("rotation-mode").description("The method of rotating when using Crystal Aura.").defaultValue(RotationMode.Place).build());
    private final Setting<Boolean> strictLook = sgRotations.add(new BoolSetting.Builder().name("strict-look").description("Looks at exactly where you're placing.").defaultValue(true).build());
    private final Setting<Boolean> resetRotations = sgRotations.add(new BoolSetting.Builder().name("reset-rotations").description("Resets rotations once Crystal Aura is disabled.").defaultValue(false).build());
    private final Setting<SwitchMode> switchMode = sgMisc.add(new EnumSetting.Builder<SwitchMode>().name("switch-mode").description("How to switch items.").defaultValue(SwitchMode.Auto).build());
    private final Setting<Boolean> switchBack = sgMisc.add(new BoolSetting.Builder().name("switch-back").description("Switches back to your previous slot when disabling Crystal Aura.").defaultValue(true).build());
    private final Setting<Double> verticalRange = sgMisc.add(new DoubleSetting.Builder().name("vertical-range").description("The maximum vertical range for placing/breaking end crystals. May kill performance if this value is higher than 3.").min(0.0D).defaultValue(3.0D).max(7.0D).build());
    private final Setting<Double> maxDamage = sgMisc.add(new DoubleSetting.Builder().name("max-damage").description("The maximum self-damage allowed.").defaultValue(3.0D).build());
    private final Setting<Boolean> smartDelay = sgMisc.add(new BoolSetting.Builder().name("smart-delay").description("Reduces crystal consumption when doing large amounts of damage. (Can tank performance on lower-end PCs).").defaultValue(false).build());
    private final Setting<Double> healthDifference = sgMisc.add(new DoubleSetting.Builder().name("damage-increase").description("The damage increase for smart delay to work.").defaultValue(5.0D).min(0.0D).max(20.0D).build());
    private final Setting<Boolean> antiWeakness = sgMisc.add(new BoolSetting.Builder().name("anti-weakness").description("Switches to tools to break crystals instead of your fist.").defaultValue(true).build());
    private final Setting<Boolean> antifriend = sgExtra.add(new BoolSetting.Builder().name("anti-friend-pop").description("Dont break crystal, when you can pop friend.").defaultValue(false).build());
    private final Setting<Boolean> antiown = sgExtra.add(new BoolSetting.Builder().name("anti-own-pop").description("Dont break crystal, when you can pop you.").defaultValue(false).build());
    private final Setting<Boolean> ignoritems = sgExtra.add(new BoolSetting.Builder().name("ignore-items").description("Ignore the items and placing block into it.").defaultValue(false).build());
    private final Setting<Boolean> moment = sgExtra.add(new BoolSetting.Builder().name("fast-place").description("Place crystals ignore the delay.").defaultValue(false).build());
    private final Setting<Boolean> pingCalc = sgPlace.add(new BoolSetting.Builder().name("ping-calc (crashing (artik moment))").description("Allows artificial intelligence for CA that calculates your ping and places depending on it .").defaultValue(false).build());
    private final Setting<Boolean> swing = sgRender.add(new BoolSetting.Builder().name("swing").description("Renders your swing client-side.").defaultValue(true).build());
    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder().name("render").description("Renders the block under where it is placing a crystal.").defaultValue(true).build());
    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Lines).build());
    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder().name("side-color").description("The side color.").defaultValue(new SettingColor(255, 255, 255, 75)).build());
    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder().name("line-color").description("The line color.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
    private final Setting<Boolean> renderDamage = sgRender.add(new BoolSetting.Builder().name("render-damage").description("Renders the damage of the crystal where it is placing.").defaultValue(true).build());
    private final Setting<Integer> roundDamage = sgRender.add(new IntSetting.Builder().name("round-damage").description("Round damage to x decimal places.").defaultValue(2).min(0).max(3).sliderMax(3).build());
    private final Setting<Double> damageScale = sgRender.add(new DoubleSetting.Builder().name("damage-scale").description("The scale of the damage text.").defaultValue(1.4D).min(0.0D).sliderMax(5.0D).build());
    private final Setting<SettingColor> damageColor = sgRender.add(new ColorSetting.Builder().name("damage-color").description("The color of the damage text.").defaultValue(new SettingColor(255, 255, 255, 255)).build());
    private final Setting<Integer> renderTimer = sgRender.add(new IntSetting.Builder().name("timer").description("The amount of time between changing the block render.").defaultValue(0).min(0).sliderMax(10).build());

    private int preSlot;
    private int placeDelayLeft = 0;
    private int breakDelayLeft;
    private Vec3d bestBlock;
    private double bestDamage = 0.0D;
    private double lastDamage = 0.0D;
    private EndCrystalEntity heldCrystal = null;
    private PlayerEntity target;
    private boolean locked = false;
    private boolean canSupport;
    private int supportSlot = 0;
    private int supportDelayLeft = supportDelay.get();
    private final Map<EndCrystalEntity, List<Double>> crystalMap = new HashMap();
    private final List<Double> crystalList = new ArrayList();
    private final List<Integer> removalQueue = new ArrayList();
    private List<BlockPos> placePositions = new ArrayList();
    private List<BlockPos> FacePlacePositions = new ArrayList();
    private EndCrystalEntity bestBreak = null;
    private PlayerEntity target1;
    private boolean placed;
    private final Pool<RenderBlock> renderBlockPool = new Pool(RenderBlock::new);
    private final List<RenderBlock> renderBlocks = new ArrayList();
    private boolean broken = false;
    private static final Vec3 pos = new Vec3();

    public CustomCrystalAuraV2() {
        super(CrystalCL.Exc, "crystal-aura-v2", "Custom CA.");
    }

    public void onActivate() {
        preSlot = -1;
        placeDelayLeft = 0;
        if (mm.get() == PMode1.ignor_immunity) {
            breakDelayLeft = 0;
        } else if (mm.get() == PMode1.immunity) {
            breakDelayLeft = 5;
        } else if (mm.get() == PMode1.anti_auto_totem) {
            breakDelayLeft = 0;
        }

        heldCrystal = null;
        locked = false;
        broken = false;
    }

    @EventHandler
    private void a(TickEvent.Pre event) {
        if (PMode.get() == PlaceMode.Fast) {
            target = CityUtils.getPlayerTarget(targetRange.get());
            if (target == null || mc.player.distanceTo(target) > (float) range.get()) {
                return;
            }

            placed = false;
            placePositions.clear();
            int cry;
            Hand hand;
            if (mc.player.getOffHandStack().getItem() instanceof EndCrystalItem) {
                cry = mc.player.getInventory().selectedSlot;
                hand = Hand.OFF_HAND;
            } else {
                cry = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                hand = Hand.MAIN_HAND;
            }

            if (cry == -1) {
                return;
            }

            findPlacePos(target1);

            for (int x = 0; x < placePositions.size(); x++) {
                BlockPos blockPos = (BlockPos) placePositions.get(placePositions.size() - 1);
                if (BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, true, false, true)) {
                    placePositions.remove(blockPos);
                    if (moment.get() && ignoritems.get()) {
                        BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, true, false, true);
                        placePositions.remove(blockPos);
                    }

                    placed = true;
                }
            }
        }
    }

    @EventHandler
    private void b(TickEvent.Pre event) {
        if (PMode.get() == PlaceMode.Fast) {
            target = CityUtils.getPlayerTarget(targetRange.get());
            if (target == null || mc.player.distanceTo(target) > (float) range.get()) {
                return;
            }

            placed = false;
            placePositions.clear();
            int cry;
            Hand hand;
            if (mc.player.getOffHandStack().getItem() instanceof EndCrystalItem) {
                cry = mc.player.getInventory().selectedSlot;
                hand = Hand.OFF_HAND;
            } else {
                cry = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                hand = Hand.MAIN_HAND;
            }

            if (cry == -1) {
                return;
            }

            findPlacePos1(target1);

            for (int x = 0; x < placePositions.size(); x++) {
                BlockPos blockPos = (BlockPos) placePositions.get(placePositions.size() - 1);
                if (BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, true, false, true)) {
                    placePositions.remove(blockPos);
                    if (moment.get() && ignoritems.get()) {
                        BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, true, false, true);
                        placePositions.remove(blockPos);
                    }

                    placed = true;
                }
            }
        }
    }

    @EventHandler
    private void c(TickEvent.Pre event) {
        if (PMode.get() == PlaceMode.Fast) {
            target = CityUtils.getPlayerTarget(targetRange.get());
            if (target == null || mc.player.distanceTo(target) > (float) range.get()) {
                return;
            }

            placed = false;
            placePositions.clear();
            int cry;
            Hand hand;
            if (mc.player.getOffHandStack().getItem() instanceof EndCrystalItem) {
                cry = mc.player.getInventory().selectedSlot;
                hand = Hand.OFF_HAND;
            } else {
                cry = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                hand = Hand.MAIN_HAND;
            }

            if (cry == -1) {
                return;
            }

            findPlacePos2(target1);

            for (int x = 0; x < placePositions.size(); x++) {
                BlockPos blockPos = (BlockPos) placePositions.get(placePositions.size() - 1);
                if (BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, true, false, true)) {
                    placePositions.remove(blockPos);
                    if (moment.get() && ignoritems.get()) {
                        BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, true, false, true);
                        placePositions.remove(blockPos);
                    }

                    placed = true;
                }
            }
        }
    }

    @EventHandler
    private void d(TickEvent.Pre event) {
        if (PMode.get() == PlaceMode.Fast) {
            target = CityUtils.getPlayerTarget(targetRange.get());
            if (target == null || mc.player.distanceTo(target) > (float) range.get()) {
                return;
            }

            placed = false;
            placePositions.clear();
            int cry;
            Hand hand;
            if (mc.player.getOffHandStack().getItem() instanceof EndCrystalItem) {
                cry = mc.player.getInventory().selectedSlot;
                hand = Hand.OFF_HAND;
            } else {
                cry = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                hand = Hand.MAIN_HAND;
            }

            if (cry == -1) {
                return;
            }

            findPlacePos3(target1);

            for (int x = 0; x < placePositions.size(); x++) {
                BlockPos blockPos = (BlockPos) placePositions.get(placePositions.size() - 1);
                if (BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, true, false, true)) {
                    placePositions.remove(blockPos);
                    if (moment.get() && ignoritems.get()) {
                        BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, true, false, true);
                        placePositions.remove(blockPos);
                    }

                    placed = true;
                }
            }
        }
    }

    @EventHandler
    private void Post(TickEvent.Post event) {
        if (PMode.get() == PlaceMode.Fast) {
            target = CityUtils.getPlayerTarget(placeRange.get());
            if (target == null || mc.player.distanceTo(target) > (float) range.get()) {
                return;
            }

            placed = false;
            placePositions.clear();
            int cry;
            Hand hand;
            if (mc.player.getOffHandStack().getItem() instanceof EndCrystalItem) {
                cry = mc.player.getInventory().selectedSlot;
                hand = Hand.OFF_HAND;
            } else {
                cry = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                hand = Hand.MAIN_HAND;
            }

            if (cry == -1) {
                return;
            }

            findPlacePos(target1);

            for (int x = 0; x < placePositions.size(); x++) {
                BlockPos blockPos = (BlockPos) placePositions.get(placePositions.size() - 1);
                if (BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, true, false, true)) {
                    placePositions.remove(blockPos);
                    if (moment.get() && ignoritems.get()) {
                        BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, true, false, true);
                        placePositions.remove(blockPos);
                    }

                    placed = true;
                }
            }
        }
    }

    public void onDeactivate() {
        assert mc.player != null;

        if (switchBack.get() && preSlot != -1) {
            mc.player.getInventory().selectedSlot = preSlot;
        }

        for (RenderBlock renderBlock : renderBlocks) {
            renderBlockPool.free(renderBlock);
        }

        renderBlocks.clear();
        if (target != null && resetRotations.get() && (rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place || rotationMode.get() == RotationMode.Break)) {
            Rotations.rotate(mc.player.getYaw(), mc.player.getPitch());
        }
    }

    @EventHandler(priority = 100)
    private void onTick(TickEvent.Post event) {
        if (cancelCrystalMode.get() == CancelCrystalMode.Hit) {
            removalQueue.forEach((id) -> {
                mc.world.removeEntity(id, Entity.RemovalReason.KILLED);
            });
            removalQueue.clear();
        }
    }

    @EventHandler(priority = 100)
    private void onTick(meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent.Pre event) {
        Iterator it = renderBlocks.iterator();

        while (it.hasNext()) {
            RenderBlock renderBlock = (RenderBlock) it.next();
            if (renderBlock.shouldRemove()) {
                it.remove();
                renderBlockPool.free(renderBlock);
            }
        }

        --placeDelayLeft;
        --breakDelayLeft;
        --supportDelayLeft;
        if (target == null) {
            heldCrystal = null;
            locked = false;
        }

        if ((!mc.player.isUsingItem() || !mc.player.getMainHandStack().getItem().isFood() && !mc.player.getOffHandStack().getItem().isFood() || !pauseOnEat.get()) && (!mc.interactionManager.isBreakingBlock() || !pauseOnMine.get()) && (!mc.player.isUsingItem() || !(mc.player.getMainHandStack().getItem() instanceof PotionItem) && !(mc.player.getOffHandStack().getItem() instanceof PotionItem) || !pauseOnDrink.get())) {
            if (locked && heldCrystal != null && (!surroundBreak.get() && target.getBlockPos().getSquaredDistance(new Vec3i(heldCrystal.getX(), heldCrystal.getY(), heldCrystal.getZ())) == 4.0D || !surroundHold.get() && target.getBlockPos().getSquaredDistance(new Vec3i(heldCrystal.getX(), heldCrystal.getY(), heldCrystal.getZ())) == 2.0D)) {
                heldCrystal = null;
                locked = false;
            }

            if (heldCrystal != null && mc.player.distanceTo(heldCrystal) > breakRange.get()) {
                heldCrystal = null;
                locked = false;
            }

            boolean isThere = false;
            if (heldCrystal != null) {

                for (Entity entity : mc.world.getEntities()) {
                    if (entity instanceof EndCrystalEntity && heldCrystal != null && entity.getBlockPos().equals(heldCrystal.getBlockPos())) {
                        isThere = true;
                        break;
                    }
                }

                if (!isThere) {
                    heldCrystal = null;
                    locked = false;
                }
            }

            boolean shouldFacePlace = false;
            if (!(getTotalHealth(mc.player) <= minHealth.get()) || allowSuicide.get()) {
                if (target != null && heldCrystal != null && placeDelayLeft <= 0 && mc.world.raycast(new RaycastContext(target.getPos(), heldCrystal.getPos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, target)).getType() == HitResult.Type.MISS) {
                    locked = false;
                }

                if (heldCrystal == null) {
                    locked = false;
                }

                if (!locked) {
                    if (!multiTarget.get()) {
                        findTarget();
                        if (target == null) {
                            return;
                        }

                        if (breakDelayLeft <= 0) {
                            singleBreak();
                        }
                    } else if (breakDelayLeft <= 0) {
                        multiBreak();
                    }

                    if (broken) {
                        broken = false;
                    } else if (smartDelay.get() || placeDelayLeft <= 0 || (surroundHold.get() || target == null || surroundBreak.get() && isSurrounded(target)) && heldCrystal == null || spamface.get()) {
                        if (switchMode.get() != SwitchMode.None || mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL || mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL) {
                            if (place.get()) {
                                if (target == null) {
                                    return;
                                }

                                if (!multiPlace.get() && getCrystalStream().count() > 0L) {
                                    return;
                                }

                                int slot;
                                if (surroundHold.get() && heldCrystal == null) {
                                    slot = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                                    if (slot != -1 && slot < 9 || mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL) {
                                        bestBlock = findOpen(target);
                                        if (bestBlock != null) {
                                            doHeldCrystal();
                                            return;
                                        }
                                    }
                                }

                                if (surroundBreak.get() && heldCrystal == null && isSurrounded(target)) {
                                    slot = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                                    if (slot != -1 && slot < 9 || mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL) {
                                        bestBlock = findOpenSurround(target);
                                        if (bestBlock != null) {
                                            doHeldCrystal();
                                            return;
                                        }
                                    }
                                }

                                slot = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                                if ((slot == -1 || slot > 9) && mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
                                    return;
                                }

                                findValidBlocks(target);
                                if (bestBlock == null) {
                                    findFacePlace(target);
                                }

                                if (bestBlock == null) {
                                    return;
                                }

                                if (spamface.get() && Math.sqrt(target.squaredDistanceTo(bestBlock)) <= 2.0D) {
                                    if ((target.getHealth() + target.getAbsorptionAmount()) < faceplacehealth.get()) {
                                        if (facemode.get() == FaceMode.Vanilla) {
                                            shouldFacePlace = true;
                                        }
                                    } else if (facemode.get() == FaceMode.Custom) {
                                        target = CityUtils.getPlayerTarget(placeRange.get());
                                        if (target == null || mc.player.distanceTo(target) > (float) range.get()) {
                                            return;
                                        }

                                        placed = false;
                                        FacePlacePositions.clear();
                                        int cry;
                                        Hand hand;
                                        if (mc.player.getOffHandStack().getItem() instanceof EndCrystalItem) {
                                            cry = mc.player.getInventory().selectedSlot;
                                            hand = Hand.OFF_HAND;
                                        } else {
                                            cry = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
                                            hand = Hand.MAIN_HAND;
                                        }

                                        if (cry == -1) {
                                            return;
                                        }

                                        findFacePlacePos(target1);

                                        for (int x = 0; x < FacePlacePositions.size(); x++) {
                                            BlockPos blockPos = (BlockPos) FacePlacePositions.get(FacePlacePositions.size() - 1);
                                            if (BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, false, false, false)) {
                                                if (moment.get() && ignoritems.get()) {
                                                    BlockUtils.place(blockPos, hand, cry, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, false, false, false);
                                                    FacePlacePositions.remove(blockPos);
                                                }

                                                FacePlacePositions.remove(blockPos);
                                                placed = true;
                                            }
                                        }
                                    }
                                }

                                if (bestBlock != null && (bestDamage >= minDamage.get() && !locked || shouldFacePlace)) {
                                    if (switchMode.get() != SwitchMode.None) {
                                        doSwitch();
                                    }

                                    if (mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
                                        return;
                                    }

                                    if (!smartDelay.get()) {
                                        placeDelayLeft = 0;
                                        placeBlock(bestBlock, getHand());
                                    } else if (smartDelay.get() && (placeDelayLeft <= 0 || bestDamage - lastDamage > healthDifference.get() || spamface.get() && shouldFacePlace)) {
                                        lastDamage = bestDamage;
                                        placeBlock(bestBlock, getHand());
                                        if (placeDelayLeft <= 0) {
                                            placeDelayLeft = 10;
                                        }
                                    }
                                }

                                if (switchMode.get() == SwitchMode.Spoof && preSlot != mc.player.getInventory().selectedSlot && preSlot != -1) {
                                    mc.player.getInventory().selectedSlot = preSlot;
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    @EventHandler
    private void onRender2D(Render2DEvent event) {
        if (render.get()) {

            for (RenderBlock renderBlock : renderBlocks) {
                renderBlock.render2D();
            }
        }
    }

    @EventHandler(priority = 100)
    private void onPlaySound(PlaySoundEvent event) {
        if (event.sound.getCategory().getName().equals(SoundCategory.BLOCKS.getName()) && event.sound.getId().getPath().equals("entity.generic.explode") && cancelCrystalMode.get() == CancelCrystalMode.Sound) {
            removalQueue.forEach((id) -> mc.world.removeEntity(id, Entity.RemovalReason.KILLED));
            removalQueue.clear();
        }
    }

    private Stream<Entity> getCrystalStream() {
        return Streams.stream(mc.world.getEntities())
            .filter((entity) -> entity instanceof EndCrystalEntity)
            .filter((entity) -> entity.distanceTo(mc.player) <= breakRange.get())
            .filter(Entity::isAlive)
            .filter((entity) -> shouldBreak((EndCrystalEntity) entity))
            .filter((entity) -> !ignoreWalls.get() || mc.player.canSee(entity))
            .filter((entity) -> isSafe(entity.getPos()));
    }

    private void singleBreak() {
        assert mc.player != null;

        assert mc.world != null;

        getCrystalStream().max(Comparator.comparingDouble((o) -> DamageUtils.crystalDamage(target, o.getPos()))).ifPresent((entity) -> hitCrystal((EndCrystalEntity) entity));
    }

    private void multiBreak() {
        assert mc.world != null;

        assert mc.player != null;

        crystalMap.clear();
        crystalList.clear();
        getCrystalStream().forEach((entity) -> {
            Iterator var2 = mc.world.getEntities().iterator();

            while (true) {
                Entity target;
                do {
                    do {
                        do {
                            do {
                                do {
                                    do {
                                        if (!var2.hasNext()) {
                                            if (!crystalList.isEmpty()) {
                                                crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
                                                crystalMap.put((EndCrystalEntity) entity, new ArrayList(crystalList));
                                                crystalList.clear();
                                            }

                                            return;
                                        }

                                        target = (Entity) var2.next();
                                    } while (target == mc.player);
                                } while (!(entities.get()).getBoolean(target.getType()));
                            } while (!(mc.player.distanceTo(target) <= targetRange.get()));
                        } while (!target.isAlive());
                    } while (!(target instanceof PlayerEntity));
                } while (!Friends.get().shouldAttack((PlayerEntity) target));

                crystalList.add(DamageUtils.crystalDamage((PlayerEntity) target, entity.getPos()));
            }
        });
        EndCrystalEntity crystal = findBestCrystal(crystalMap);
        if (crystal != null) {
            hitCrystal(crystal);
        }
    }

    private EndCrystalEntity findBestCrystal(Map<EndCrystalEntity, List<Double>> map) {
        bestDamage = 0.0D;
        double currentDamage = 0.0D;
        Iterator var4;
        Entry entry;
        int i;
        if (targetMode.get() == TrgMode.HugeDmg) {
            for (var4 = map.entrySet().iterator(); var4.hasNext(); currentDamage = 0.0D) {
                entry = (Entry) var4.next();

                for (i = 0; i < ((List) entry.getValue()).size() && i < numberOfDamages.get(); i++) {
                    currentDamage += (double) ((List) entry.getValue()).get(i);
                }

                if (bestDamage < currentDamage) {
                    bestDamage = currentDamage;
                    bestBreak = (EndCrystalEntity) entry.getKey();
                }
            }
        } else if (targetMode.get() == TrgMode.BigDmg) {
            for (var4 = map.entrySet().iterator(); var4.hasNext(); currentDamage = 0.0D) {
                entry = (Entry) var4.next();

                for (i = 0; i < ((List) entry.getValue()).size(); i++) {
                    currentDamage += (double) ((List) entry.getValue()).get(i);
                }

                if (bestDamage < currentDamage) {
                    bestDamage = currentDamage;
                    bestBreak = (EndCrystalEntity) entry.getKey();
                }
            }
        }

        return bestBreak;
    }

    private void hitCrystal(EndCrystalEntity entity) {
        assert mc.player != null;

        assert mc.world != null;

        assert mc.interactionManager != null;

        int preSlot = mc.player.getInventory().selectedSlot;
        if (mc.player.getActiveStatusEffects().containsKey(StatusEffects.WEAKNESS) && antiWeakness.get()) {
            for (int i = 0; i < 9; i++) {
                if (mc.player.getInventory().getStack(i).getItem() instanceof SwordItem || mc.player.getInventory().getStack(i).getItem() instanceof AxeItem) {
                    mc.player.getInventory().selectedSlot = i;
                    break;
                }
            }
        }

        if (rotationMode.get() != RotationMode.Break && rotationMode.get() != RotationMode.PlaceBreak) {
            attackCrystal(entity, preSlot);
        } else {
            float[] rotation = PlayerUtils.calculateAngle(entity.getPos());
            Rotations.rotate(rotation[0], rotation[1], 30, () -> {
                attackCrystal(entity, preSlot);
            });
        }

        broken = true;
        if (mm.get() == PMode1.ignor_immunity) {
            breakDelayLeft = 0;
        } else if (mm.get() == PMode1.immunity) {
            breakDelayLeft = 5;
        } else if (mm.get() == PMode1.anti_auto_totem) {
            breakDelayLeft = 0;
        }
    }

    private void attackCrystal(EndCrystalEntity entity, int preSlot) {
        if (pingCalc.get()) {
            String stringA = String.format("%.1f", TickRate.INSTANCE.getTickRate());
            double d = Double.parseDouble(stringA);
            String stringB = null;
            if (mc.player != null && mc.getNetworkHandler() != null) {
                PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
                stringB = Integer.toString(playerListEntry.getLatency());
            }

            double d1 = Double.parseDouble(stringB);
            double Result1 = d1 * 2.0D;
            double Full = Result1 * d;
            double FullResult = Full / 1000.0D;
            if (FullResult > 0.0D) {
                --FullResult;
            } else {
                mc.interactionManager.attackEntity(mc.player, entity);
                removalQueue.add(entity.getId());
                if (swing.get()) {
                    mc.player.swingHand(getHand());
                } else {
                    mc.player.networkHandler.sendPacket(new HandSwingC2SPacket(getHand()));
                }

                mc.player.getInventory().selectedSlot = preSlot;
                if (heldCrystal != null && entity.getBlockPos().equals(heldCrystal.getBlockPos())) {
                    heldCrystal = null;
                    locked = false;
                }
            }
        } else {
            mc.interactionManager.attackEntity(mc.player, entity);
            removalQueue.add(entity.getId());
            if (swing.get()) {
                mc.player.swingHand(getHand());
            } else {
                mc.player.networkHandler.sendPacket(new HandSwingC2SPacket(getHand()));
            }

            mc.player.getInventory().selectedSlot = preSlot;
            if (heldCrystal != null && entity.getBlockPos().equals(heldCrystal.getBlockPos())) {
                heldCrystal = null;
                locked = false;
            }
        }
    }

    private void findTarget() {
        assert mc.world != null;

        Optional<LivingEntity> livingEntity = Streams
            .stream(mc.world.getEntities()).filter(Entity::isAlive)
            .filter((entity) -> entity != mc.player)
            .filter((entity) -> !(entity instanceof PlayerEntity) || Friends.get().shouldAttack((PlayerEntity) entity))
            .filter((entity) -> entity instanceof LivingEntity)
            .filter((entity) -> (entities.get()).getBoolean(entity.getType()))
            .filter((entity) -> entity.distanceTo(mc.player) <= targetRange.get() * 2.0D)
            .min(Comparator.comparingDouble((o) -> o.distanceTo(mc.player)))
            .map((entity) -> (LivingEntity) entity);
        target = (PlayerEntity) livingEntity.orElse(null);
    }

    private void doSwitch() {
        assert mc.player != null;

        if (mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
            int slot = InvUtils.findInHotbar(Items.END_CRYSTAL).getSlot();
            if (slot != -1 && slot < 9) {
                preSlot = mc.player.getInventory().selectedSlot;
                mc.player.getInventory().selectedSlot = slot;
            }
        }
    }

    private void doHeldCrystal() {
        if (pingCalc.get()) {
            String stringA = String.format("%.1f", TickRate.INSTANCE.getTickRate());
            double d = Double.parseDouble(stringA);
            String stringB = null;
            if (mc.player != null && mc.getNetworkHandler() != null) {
                PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
                stringB = Integer.toString(playerListEntry.getLatency());
            }

            double d1 = Double.parseDouble(stringB);
            double Result1 = d1 * 2.0D;
            double Full = Result1 * d;
            double FullResult = Full / 1000.0D;
            if (FullResult > 0.0D) {
                --FullResult;
            } else {
                assert mc.player != null;

                if (switchMode.get() != SwitchMode.None) {
                    doSwitch();
                }

                if (mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
                    return;
                }

                bestDamage = DamageUtils.crystalDamage(target, bestBlock.add(0.0D, 1.0D, 0.0D));
                heldCrystal = new EndCrystalEntity(mc.world, bestBlock.x, bestBlock.y + 1.0D, bestBlock.z);
                locked = true;
                if (!smartDelay.get()) {
                    placeDelayLeft = 0;
                } else {
                    lastDamage = bestDamage;
                    if (placeDelayLeft <= 0) {
                        placeDelayLeft = 10;
                    }
                }

                placeBlock(bestBlock, getHand());
            }
        } else {
            assert mc.player != null;

            if (switchMode.get() != SwitchMode.None) {
                doSwitch();
            }

            if (mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
                return;
            }

            bestDamage = DamageUtils.crystalDamage(target, bestBlock.add(0.0D, 1.0D, 0.0D));
            heldCrystal = new EndCrystalEntity(mc.world, bestBlock.x, bestBlock.y + 1.0D, bestBlock.z);
            locked = true;
            if (!smartDelay.get()) {
                placeDelayLeft = 0;
            } else {
                lastDamage = bestDamage;
                if (placeDelayLeft <= 0) {
                    placeDelayLeft = 10;
                }
            }

            placeBlock(bestBlock, getHand());
        }
    }

    private void placeBlock(Vec3d block, Hand hand) {
        if (PMode.get() != PlaceMode.Fast) {
            assert mc.player != null;

            assert mc.interactionManager != null;

            assert mc.world != null;

            if (mc.world.isAir(new BlockPos(block))) {
                if (support.get() == SupportMode.Normal) {
                    BlockUtils.place(new BlockPos(block), Hand.MAIN_HAND, supportSlot, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, false, false, false);
                    supportDelayLeft = supportDelay.get();
                } else if (support.get() == SupportMode.Plus) {
                    BlockUtils.place(new BlockPos(block), Hand.MAIN_HAND, supportSlot, rotationMode.get() == RotationMode.PlaceBreak || rotationMode.get() == RotationMode.Place, 50, false, false, false);
                    supportDelayLeft = supportDelay.get();
                }
            }

            BlockPos blockPos = new BlockPos(block);
            Direction direction = rayTraceCheck(blockPos, true);
            if (rotationMode.get() != RotationMode.Place && rotationMode.get() != RotationMode.PlaceBreak) {
                mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, new BlockHitResult(mc.player.getPos(), direction, new BlockPos(block), false)));
                if (swing.get()) {
                    mc.player.swingHand(hand);
                } else {
                    mc.player.networkHandler.sendPacket(new HandSwingC2SPacket(hand));
                }
            } else {
                float[] rotation = PlayerUtils.calculateAngle(strictLook.get() ? new Vec3d(blockPos.getX() + 0.5D + direction.getVector().getX() * 1.0D / 2.0D, blockPos.getY() + 0.5D + direction.getVector().getY() * 1.0D / 2.0D, blockPos.getZ() + 0.5D + direction.getVector().getZ() * 1.0D / 2.0D) : block.add(0.5D, 1.0D, 0.5D));
                Rotations.rotate(rotation[0], rotation[1], 25, () -> {
                    mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, new BlockHitResult(mc.player.getPos(), direction, new BlockPos(block), false)));
                    if (swing.get()) {
                        mc.player.swingHand(hand);
                    } else {
                        mc.player.networkHandler.sendPacket(new HandSwingC2SPacket(hand));
                    }

                });
            }

            if (render.get()) {
                RenderBlock renderBlock = (RenderBlock) renderBlockPool.get();
                renderBlock.reset(block);
                renderBlock.damage = DamageUtils.crystalDamage(target, bestBlock.add(0.5D, 1.0D, 0.5D));
                renderBlocks.add(renderBlock);
            }
        }
    }

    private void findValidBlocks(PlayerEntity target) {
        assert mc.player != null;

        assert mc.world != null;

        bestBlock = new Vec3d(0.0D, 0.0D, 0.0D);
        bestDamage = 0.0D;
        Vec3d bestSupportBlock = new Vec3d(0.0D, 0.0D, 0.0D);
        double bestSupportDamage = 0.0D;
        BlockPos playerPos = mc.player.getBlockPos();
        canSupport = false;
        crystalMap.clear();
        crystalList.clear();
        if (support.get() == SupportMode.Normal || support.get() == SupportMode.Plus) {
            for (int i = 0; i < 9; i++) {
                if (mc.player.getInventory().getStack(i).getItem() == Items.OBSIDIAN) {
                    canSupport = true;
                    supportSlot = i;
                    break;
                }
            }
        }

        for (double i = playerPos.getX() - placeRange.get(); i < playerPos.getX() + placeRange.get(); i++) {
            for (double j = playerPos.getZ() - placeRange.get(); j < playerPos.getZ() + placeRange.get(); j++) {
                label144:
                for (double k = playerPos.getY() - verticalRange.get(); k < playerPos.getY() + verticalRange.get(); ++k) {
                    Vec3d pos = new Vec3d(Math.floor(i), Math.floor(k), Math.floor(j));
                    if (isValid(new BlockPos(pos)) && getDamagePlace((new BlockPos(pos)).up()) && (!oldPlace.get() || isEmpty(new BlockPos(pos.add(0.0D, 2.0D, 0.0D)))) && (!rayTrace.get() || pos.distanceTo(new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ())) <= placeWallsRange.get() || rayTraceCheck(new BlockPos(pos), false) != null)) {
                        if (!multiTarget.get()) {
                            if (isEmpty(new BlockPos(pos)) && bestSupportDamage < DamageUtils.crystalDamage(target, pos.add(0.5D, 1.0D, 0.5D))) {
                                bestSupportBlock = pos;
                                bestSupportDamage = DamageUtils.crystalDamage(target, pos.add(0.5D, 1.0D, 0.5D));
                            } else if (!isEmpty(new BlockPos(pos)) && bestDamage < DamageUtils.crystalDamage(target, pos.add(0.5D, 1.0D, 0.5D))) {
                                bestBlock = pos;
                                bestDamage = DamageUtils.crystalDamage(target, bestBlock.add(0.5D, 1.0D, 0.5D));
                            }
                        } else {
                            Iterator var13 = mc.world.getEntities().iterator();

                            while (true) {
                                Entity entity;
                                do {
                                    do {
                                        do {
                                            do {
                                                do {
                                                    do {
                                                        if (!var13.hasNext()) {
                                                            if (!crystalList.isEmpty()) {
                                                                crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
                                                                crystalMap.put(new EndCrystalEntity(mc.world, pos.x, pos.y, pos.z), new ArrayList(crystalList));
                                                                crystalList.clear();
                                                            }
                                                            continue label144;
                                                        }

                                                        entity = (Entity) var13.next();
                                                    } while (entity == mc.player);
                                                } while (!(entities.get()).getBoolean(entity.getType()));
                                            } while (!(mc.player.distanceTo(entity) <= targetRange.get()));
                                        } while (!entity.isAlive());
                                    } while (!(entity instanceof LivingEntity));
                                } while (entity instanceof PlayerEntity && !Friends.get().shouldAttack((PlayerEntity) entity));

                                assert entity instanceof PlayerEntity;
                                crystalList.add(DamageUtils.crystalDamage((PlayerEntity) entity, pos.add(0.5D, 1.0D, 0.5D)));
                            }
                        }
                    }
                }
            }
        }

        if (multiTarget.get()) {
            EndCrystalEntity entity = findBestCrystal(crystalMap);
            if (entity != null && bestDamage > minDamage.get()) {
                bestBlock = entity.getPos();
            } else {
                bestBlock = null;
            }
        } else if (bestDamage < minDamage.get()) {
            bestBlock = null;
        }

        if (support.get() == SupportMode.Normal || support.get() == SupportMode.Plus && (bestBlock == null || bestDamage < bestSupportDamage)) {
            bestBlock = bestSupportBlock;
        }
    }

    private void findFacePlace(LivingEntity target) {
        assert mc.world != null;

        assert mc.player != null;

        BlockPos targetBlockPos = target.getBlockPos();
        if (mc.world.getBlockState(targetBlockPos.add(1, 1, 0)).isAir() && Math.sqrt(mc.player.getBlockPos().getSquaredDistance(targetBlockPos.add(1, 1, 0))) <= placeRange.get() && getDamagePlace(targetBlockPos.add(1, 1, 0))) {
            bestBlock = target.getPos().add(1.0D, 0.0D, 0.0D);
        } else if (mc.world.getBlockState(targetBlockPos.add(-1, 1, 0)).isAir() && Math.sqrt(mc.player.getBlockPos().getSquaredDistance(targetBlockPos.add(-1, 1, 0))) <= placeRange.get() && getDamagePlace(targetBlockPos.add(-1, 1, 0))) {
            bestBlock = target.getPos().add(-1.0D, 0.0D, 0.0D);
        } else if (mc.world.getBlockState(targetBlockPos.add(0, 1, 1)).isAir() && Math.sqrt(mc.player.getBlockPos().getSquaredDistance(targetBlockPos.add(0, 1, 1))) <= placeRange.get() && getDamagePlace(targetBlockPos.add(0, 1, 1))) {
            bestBlock = target.getPos().add(0.0D, 0.0D, 1.0D);
        } else if (mc.world.getBlockState(targetBlockPos.add(0, 1, -1)).isAir() && Math.sqrt(mc.player.getBlockPos().getSquaredDistance(targetBlockPos.add(0, 1, -1))) <= placeRange.get() && getDamagePlace(targetBlockPos.add(0, 1, -1))) {
            bestBlock = target.getPos().add(0.0D, 0.0D, -1.0D);
        }
    }

    private boolean getDamagePlace(BlockPos pos) {
        assert mc.player != null;

        return allowSuicide.get() || DamageUtils.crystalDamage(mc.player, new Vec3d(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D)) <= maxDamage.get() && getTotalHealth(mc.player) - DamageUtils.crystalDamage(mc.player, new Vec3d(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D)) >= minHealth.get();
    }

    private Vec3d findOpen(LivingEntity target) {
        assert mc.player != null;

        int x = 0;
        int z = 0;
        if (isValid(target.getBlockPos().add(1, -1, 0)) && Math.sqrt(mc.player.getBlockPos().getSquaredDistance(new Vec3i(target.getBlockPos().getX() + 1, target.getBlockPos().getY() - 1, target.getBlockPos().getZ()))) < placeRange.get()) {
            x = 1;
        } else if (isValid(target.getBlockPos().add(-1, -1, 0)) && Math.sqrt(mc.player.getBlockPos().getSquaredDistance(new Vec3i(target.getBlockPos().getX() - 1, target.getBlockPos().getY() - 1, target.getBlockPos().getZ()))) < placeRange.get()) {
            x = -1;
        } else if (isValid(target.getBlockPos().add(0, -1, 1)) && Math.sqrt(mc.player.getBlockPos().getSquaredDistance(new Vec3i(target.getBlockPos().getX(), target.getBlockPos().getY() - 1, target.getBlockPos().getZ() + 1))) < placeRange.get()) {
            z = 1;
        } else if (isValid(target.getBlockPos().add(0, -1, -1)) && Math.sqrt(mc.player.getBlockPos().getSquaredDistance(new Vec3i(target.getBlockPos().getX(), target.getBlockPos().getY() - 1, target.getBlockPos().getZ() - 1))) < placeRange.get()) {
            z = -1;
        }

        return x == 0 && z == 0 ? null : new Vec3d(target.getBlockPos().getX() + 0.5D + x, (target.getBlockPos().getY() - 1), target.getBlockPos().getZ() + 0.5D + z);
    }

    private Vec3d findOpenSurround(LivingEntity target) {
        assert mc.player != null;

        assert mc.world != null;

        int x = 0;
        int z = 0;
        if (validSurroundBreak(target, 2, 0)) {
            x = 2;
        } else if (validSurroundBreak(target, -2, 0)) {
            x = -2;
        } else if (validSurroundBreak(target, 0, 2)) {
            z = 2;
        } else if (validSurroundBreak(target, 0, -2)) {
            z = -2;
        }

        return x == 0 && z == 0 ? null : new Vec3d(target.getBlockPos().getX() + 0.5D + x, (target.getBlockPos().getY() - 1), target.getBlockPos().getZ() + 0.5D + z);
    }

    private boolean isValid(BlockPos blockPos) {
        assert mc.world != null;

        return (canSupport && isEmpty(blockPos) && blockPos.getY() - target.getBlockPos().getY() == -1 && supportDelayLeft <= 0 || mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && isEmpty(blockPos.add(0, 1, 0));
    }

    private Direction rayTraceCheck(BlockPos pos, boolean forceReturn) {
        Vec3d eyesPos = new Vec3d(mc.player.getX(), mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()), mc.player.getZ());
        Direction[] var4 = Direction.values();
        int var5 = var4.length;

        for (Direction direction : var4) {
            RaycastContext raycastContext = new RaycastContext(eyesPos, new Vec3d(pos.getX() + 0.5D + direction.getVector().getX() * 0.5D, pos.getY() + 0.5D + direction.getVector().getY() * 0.5D, pos.getZ() + 0.5D + direction.getVector().getZ() * 0.5D), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player);
            BlockHitResult result = mc.world.raycast(raycastContext);
            if (result != null && result.getType() == HitResult.Type.BLOCK && result.getBlockPos().equals(pos)) {
                return direction;
            }
        }

        if (forceReturn) {
            if (pos.getY() > eyesPos.y) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        } else {
            return null;
        }
    }

    private boolean validSurroundBreak(LivingEntity target, int x, int z) {
        assert mc.world != null;

        assert mc.player != null;

        Vec3d crystalPos = new Vec3d(target.getBlockPos().getX() + 0.5D, target.getBlockPos().getY(), target.getBlockPos().getZ() + 0.5D);
        return isValid(target.getBlockPos().add(x, -1, z)) && mc.world.getBlockState(target.getBlockPos().add(x / 2, 0, z / 2)).getBlock() != Blocks.BEDROCK && isSafe(crystalPos.add(x, 0.0D, z)) && Math.sqrt(mc.player.getBlockPos().getSquaredDistance(new Vec3i(target.getBlockPos().getX() + x, target.getBlockPos().getY() - 1, target.getBlockPos().getZ() + z))) < placeRange.get() && mc.world.raycast(new RaycastContext(target.getPos(), target.getPos().add(x, 0.0D, z), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, target)).getType() != HitResult.Type.MISS;
    }

    private boolean isSafe(Vec3d crystalPos) {
        assert mc.player != null;

        return breakMode.get() != BMode.MinSelfDmg || getTotalHealth(mc.player) - DamageUtils.crystalDamage(mc.player, crystalPos) > minHealth.get() && DamageUtils.crystalDamage(mc.player, crystalPos) < maxDamage.get();
    }

    private float getTotalHealth(PlayerEntity target) {
        return target.getHealth() + target.getAbsorptionAmount();
    }

    private boolean isEmpty(BlockPos pos) {
        assert mc.world != null;

        return mc.world.getBlockState(pos).isAir() && mc.world.getOtherEntities(null, new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 2.0D, pos.getZ() + 1.0D)).isEmpty();
    }

    private boolean shouldBreak(EndCrystalEntity entity) {
        assert mc.world != null;

        return heldCrystal == null || !surroundHold.get() && !surroundBreak.get() || placeDelayLeft <= 0 && (!heldCrystal.getBlockPos().equals(entity.getBlockPos()) || mc.world.raycast(new RaycastContext(target.getPos(), heldCrystal.getPos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, target)).getType() == HitResult.Type.MISS || target.distanceTo(heldCrystal) > 1.5D && !isSurrounded(target));
    }

    private boolean isSurrounded(LivingEntity target) {
        assert mc.world != null;

        return !mc.world.getBlockState(target.getBlockPos().add(1, 0, 0)).isAir() && !mc.world.getBlockState(target.getBlockPos().add(-1, 0, 0)).isAir() && !mc.world.getBlockState(target.getBlockPos().add(0, 0, 1)).isAir() && !mc.world.getBlockState(target.getBlockPos().add(0, 0, -1)).isAir();
    }

    public Hand getHand() {
        assert mc.player != null;

        Hand hand = Hand.MAIN_HAND;
        if (mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL && mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL) {
            hand = Hand.OFF_HAND;
        }

        return hand;
    }

    public String getInfoString() {
        if (target != null) {
            return target.getEntityName();
        } else {
            return target != null ? target.getType().getName().getString() : null;
        }
    }

    private void add(BlockPos blockPos1) {
        if (!placePositions.contains(blockPos1) && mc.world.getBlockState(blockPos1).getMaterial().isReplaceable() && mc.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), blockPos1, ShapeContext.absent())) {
            placePositions.add(blockPos1);
        }
    }

    private void findPlacePos(PlayerEntity target1) {
        placePositions.clear();
        BlockPos targetPos = target.getBlockPos();
        add(targetPos.add(1, 0, 0));
    }

    private void findPlacePos1(PlayerEntity target1) {
        placePositions.clear();
        BlockPos targetPos = target.getBlockPos();
        add(targetPos.add(-1, 0, 0));
    }

    private void findPlacePos2(PlayerEntity target1) {
        placePositions.clear();
        BlockPos targetPos = target.getBlockPos();
        add(targetPos.add(0, 0, 1));
    }

    private void findPlacePos3(PlayerEntity target1) {
        placePositions.clear();
        BlockPos targetPos = target.getBlockPos();
        add(targetPos.add(0, 0, -1));
    }

    private void addFP(BlockPos blockPos1) {
        if (!FacePlacePositions.contains(blockPos1) && mc.world.getBlockState(blockPos1).getMaterial().isReplaceable() && mc.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), blockPos1, ShapeContext.absent())) {
            FacePlacePositions.add(blockPos1);
        }
    }

    private void findFacePlacePos(PlayerEntity target1) {
        FacePlacePositions.clear();
        BlockPos targetPos = target.getBlockPos();
        addFP(targetPos.add(1, 0, 0));
        addFP(targetPos.add(-1, 0, 0));
        addFP(targetPos.add(0, 0, 1));
        addFP(targetPos.add(0, 0, -1));
        addFP(targetPos.add(1, 1, 0));
        addFP(targetPos.add(-1, 1, 0));
        addFP(targetPos.add(0, 1, 1));
        addFP(targetPos.add(0, 1, -1));
    }

    public enum PlaceMode {
        Fast,
        Normal
    }

    public enum FaceMode {
        Vanilla,
        Custom
    }

    public enum SupportMode {
        Normal,
        Plus,
        None
    }

    public enum PMode1 {
        immunity,
        ignor_immunity,
        anti_auto_totem
    }

    public enum BMode {
        MinSelfDmg,
        MaxSelfDmg
    }

    public enum CancelCrystalMode {
        Sound,
        Hit
    }

    public enum TrgMode {
        BigDmg,
        HugeDmg
    }

    public enum RotationMode {
        Place,
        Break,
        PlaceBreak,
        None
    }

    public enum SwitchMode {
        Auto,
        Spoof,
        None
    }

    private class RenderBlock {
        private double x;
        private double y;
        private double z;
        private int timer;
        private double damage;

        public void reset(Vec3d pos) {
            x = MathHelper.floor(pos.getX());
            y = MathHelper.floor(pos.getY());
            z = MathHelper.floor(pos.getZ());
            timer = renderTimer.get();
        }

        public boolean shouldRemove() {
            if (timer <= 0) {
                return true;
            } else {
                --timer;
                return false;
            }
        }

        public void render2D() {
            if (renderDamage.get()) {
                pos.set(x + 0.5D, y + 0.5D, z + 0.5D);
                if (NametagUtils.to2D(pos, damageScale.get())) {
                    NametagUtils.begin(pos);
                    TextRenderer.get().begin(1.0D, false, true);
                    String damageText = switch (roundDamage.get()) {
                        case 1 -> String.valueOf(Math.round(damage * 10.0D) / 10.0D);
                        case 2 -> String.valueOf(Math.round(damage * 100.0D) / 100.0D);
                        case 3 -> String.valueOf(Math.round(damage * 1000.0D) / 1000.0D);
                        default -> String.valueOf(Math.round(damage));
                    };

                    double w = TextRenderer.get().getWidth(damageText) / 2.0D;
                    TextRenderer.get().render(damageText, -w, 0.0D, damageColor.get());
                    TextRenderer.get().end();
                    NametagUtils.end();
                }
            }
        }
    }

    public enum BreakMode1 {
        normal,
        fast,
        none
    }
}
