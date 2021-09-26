/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import java.util.ArrayList;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;

public class AutoAnvil
extends Module {
    private final Setting<Integer> startHeight;
    private final Setting<Integer> delay;
    private final Setting<Boolean> placeButton;
    private final Setting<Double> decrease;
    private final Setting<Boolean> toggleOnBreak;
    private final Setting<Boolean> rotate;
    private final Setting<Integer> blocksPerTick;
    private PlayerEntity target;
    private final ArrayList<Vec3d> antiStepStructure;
    private final Setting<Double> range;
    private int timer;
    private final SettingGroup sgPlace;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> antiStep;
    private final Setting<Integer> minHeight;

    public AutoAnvil() {
        super(Categories.Combat, "auto-anvil", "Automatically places anvils above players to destroy helmets.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgPlace = this.settings.createGroup("Place");
        this.antiStepStructure = new ArrayList<Vec3d>(this){
            final AutoAnvil this$0;
            {
                this.this$0 = autoAnvil;
                this.add(new Vec3d(1.0, 2.0, 0.0));
                this.add(new Vec3d(-1.0, 2.0, 0.0));
                this.add(new Vec3d(0.0, 2.0, 1.0));
                this.add(new Vec3d(0.0, 2.0, -1.0));
            }
        };
        this.toggleOnBreak = this.sgGeneral.add(new BoolSetting.Builder().name("toggle-on-break").description("Toggles when the target's helmet slot is empty.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards the position anvils/pressure plates/buttons are placed.").defaultValue(true).build());
        this.antiStep = this.sgGeneral.add(new BoolSetting.Builder().name("anti step").description("Place extra blocks for preventing the enemy from escaping").defaultValue(false).build());
        this.range = this.sgPlace.add(new DoubleSetting.Builder().name("range").description("How far away the target can be to be affected.").defaultValue(4.0).min(0.0).build());
        this.delay = this.sgPlace.add(new IntSetting.Builder().name("delay").description("The delay in between anvil placements.").min(0).defaultValue(0).sliderMax(50).build());
        this.startHeight = this.sgPlace.add(new IntSetting.Builder().name("start-Height").description("The height at beginning").defaultValue(5).min(0).max(10).sliderMin(0).sliderMax(10).build());
        this.minHeight = this.sgPlace.add(new IntSetting.Builder().name("min-Height").description("The minimum height accetable").defaultValue(1).min(0).max(5).sliderMin(0).sliderMax(5).build());
        this.decrease = this.sgPlace.add(new DoubleSetting.Builder().name("decrease").description("The distance where it will start decrease").defaultValue(1.4).min(0.0).max(4.0).sliderMin(0.0).sliderMax(4.0).build());
        this.blocksPerTick = this.sgGeneral.add(new IntSetting.Builder().name("blocks-per-tick").description("The number of blocks you can place every ticks").defaultValue(4).min(2).max(8).sliderMin(2).sliderMax(8).build());
        this.placeButton = this.sgPlace.add(new BoolSetting.Builder().name("place-at-feet").description("Automatically places a button or pressure plate at the targets feet to break the anvils.").defaultValue(true).build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.isActive() && this.toggleOnBreak.get().booleanValue() && this.target != null && this.target.inventory.getArmorStack(3).isEmpty()) {
            ChatUtils.moduleError(this, "Target head slot is empty... disabling.", new Object[0]);
            this.toggle();
            return;
        }
        if (this.target != null && ((double)this.mc.player.distanceTo((Entity)this.target) > this.range.get() || !this.target.isAlive())) {
            this.target = null;
        }
        for (PlayerEntity object : this.mc.world.getPlayers()) {
            if (object == this.mc.player || !Friends.get().attack(object) || !object.isAlive() || (double)this.mc.player.distanceTo((Entity)object) > this.range.get() || this.isHole(object.getBlockPos())) continue;
            if (this.target == null) {
                this.target = object;
                continue;
            }
            if (!(this.mc.player.distanceTo((Entity)this.target) > this.mc.player.distanceTo((Entity)object))) continue;
            this.target = object;
        }
        if (this.target == null) {
            for (FakePlayerEntity fakePlayerEntity : FakePlayerUtils.getPlayers().keySet()) {
                if (!Friends.get().attack((PlayerEntity)fakePlayerEntity) || !fakePlayerEntity.isAlive() || (double)this.mc.player.distanceTo((Entity)fakePlayerEntity) > this.range.get() || this.isHole(fakePlayerEntity.getBlockPos())) continue;
                if (this.target == null) {
                    this.target = fakePlayerEntity;
                    continue;
                }
                if (!(this.mc.player.distanceTo((Entity)this.target) > this.mc.player.distanceTo((Entity)fakePlayerEntity))) continue;
                this.target = fakePlayerEntity;
            }
        }
        if (this.target == null) {
            return;
        }
        int n = 0;
        if (this.timer >= this.delay.get() && this.target != null) {
            BlockPos BlockPos2;
            int n2;
            this.timer = 0;
            int n3 = this.getAnvilSlot();
            if (n3 == -1) {
                return;
            }
            if (this.placeButton.get().booleanValue()) {
                n2 = this.getFloorSlot();
                BlockPos BlockPos3 = this.target.getBlockPos();
                if (BlockUtils.place(BlockPos3, Hand.MAIN_HAND, n2, this.rotate.get(), 0, false)) {
                    ++n;
                }
            }
            if (this.antiStep.get().booleanValue()) {
                n2 = InvUtils.findItemInHotbar(Blocks.OBSIDIAN.asItem());
                if (n2 == -1) {
                    return;
                }
                for (Vec3d Vec3d2 : this.antiStepStructure) {
                    BlockPos2 = this.target.getBlockPos().add(Vec3d2.x, Vec3d2.y, Vec3d2.z);
                    if (!BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n2, this.rotate.get(), 0, true) || ++n != this.blocksPerTick.get()) continue;
                    return;
                }
            }
            n2 = this.startHeight.get() + (int)(this.mc.player.getY() - this.target.getY());
            for (double d = Math.sqrt(Math.pow(this.mc.player.getPos().x - this.target.getX(), 2.0) + Math.pow(this.mc.player.getPos().z - this.target.getZ(), 2.0)); d > this.decrease.get(); d -= this.decrease.get().doubleValue()) {
                --n2;
            }
            if (n2 <= this.minHeight.get()) {
                ChatUtils.moduleError(this, "Target too far away... disabling.", new Object[0]);
                this.toggle();
                return;
            }
            BlockPos2 = this.target.getBlockPos().up().add(0, n2, 0);
            BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n3, this.rotate.get(), 0, true);
        } else {
            ++this.timer;
        }
    }

    @Override
    public String getInfoString() {
        if (this.target != null && this.target instanceof PlayerEntity) {
            return this.target.getEntityName();
        }
        if (this.target != null) {
            return this.target.getType().getName().getString();
        }
        return null;
    }

    public int getFloorSlot() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Item Item2 = this.mc.player.inventory.getStack(i).getItem();
            Block Block2 = Block.getBlockFromItem((Item)Item2);
            if (!(Block2 instanceof AbstractPressurePlateBlock) && !(Block2 instanceof AbstractButtonBlock)) continue;
            n = i;
            break;
        }
        return n;
    }

    private boolean isHole(BlockPos BlockPos2) {
        Mutable class_23392 = new Mutable(BlockPos2.getX(), BlockPos2.getY(), BlockPos2.getZ());
        return this.mc.world.getBlockState(class_23392.add(1, 0, 0)).getBlock().is(Blocks.AIR) || this.mc.world.getBlockState(class_23392.add(-1, 0, 0)).getBlock().is(Blocks.AIR) || this.mc.world.getBlockState(class_23392.add(0, 0, 1)).getBlock().is(Blocks.AIR) || this.mc.world.getBlockState(class_23392.add(0, 0, -1)).getBlock().is(Blocks.AIR);
    }

    private int getAnvilSlot() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Item Item2 = this.mc.player.inventory.getStack(i).getItem();
            Block Block2 = Block.getBlockFromItem((Item)Item2);
            if (!(Block2 instanceof AnvilBlock)) continue;
            n = i;
            break;
        }
        return n;
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent openScreenEvent) {
        if (openScreenEvent.screen instanceof AnvilScreen) {
            this.mc.player.closeScreen();
        }
    }

    @Override
    public void onActivate() {
        this.timer = 0;
        this.target = null;
    }
}

