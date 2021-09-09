/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Item
 *  net.minecraft.block.AnvilBlock
 *  net.minecraft.block.AbstractPressurePlateBlock
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.block.AbstractButtonBlock
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.client.gui.screen.ingame.AnvilScreen
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
    private final /* synthetic */ Setting<Boolean> toggleOnBreak;
    private final /* synthetic */ Setting<Integer> blocksPerTick;
    private final /* synthetic */ Setting<Integer> minHeight;
    private /* synthetic */ PlayerEntity target;
    private final /* synthetic */ Setting<Double> range;
    private /* synthetic */ int timer;
    private final /* synthetic */ Setting<Integer> delay;
    private final /* synthetic */ SettingGroup sgPlace;
    private final /* synthetic */ Setting<Double> decrease;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ ArrayList<Vec3d> antiStepStructure;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Boolean> antiStep;
    private final /* synthetic */ Setting<Integer> startHeight;
    private final /* synthetic */ Setting<Boolean> placeButton;

    public AutoAnvil() {
        super(Categories.Combat, "auto-anvil", "Automatically places anvils above players to destroy helmets.");
        AutoAnvil llllllllllllllllIlIlllllIIIllllI;
        llllllllllllllllIlIlllllIIIllllI.sgGeneral = llllllllllllllllIlIlllllIIIllllI.settings.getDefaultGroup();
        llllllllllllllllIlIlllllIIIllllI.sgPlace = llllllllllllllllIlIlllllIIIllllI.settings.createGroup("Place");
        llllllllllllllllIlIlllllIIIllllI.antiStepStructure = new ArrayList<Vec3d>(){
            {
                1 llIlllIIIIlllII;
                llIlllIIIIlllII.add(new Vec3d(1.0, 2.0, 0.0));
                llIlllIIIIlllII.add(new Vec3d(-1.0, 2.0, 0.0));
                llIlllIIIIlllII.add(new Vec3d(0.0, 2.0, 1.0));
                llIlllIIIIlllII.add(new Vec3d(0.0, 2.0, -1.0));
            }
        };
        llllllllllllllllIlIlllllIIIllllI.toggleOnBreak = llllllllllllllllIlIlllllIIIllllI.sgGeneral.add(new BoolSetting.Builder().name("toggle-on-break").description("Toggles when the target's helmet slot is empty.").defaultValue(false).build());
        llllllllllllllllIlIlllllIIIllllI.rotate = llllllllllllllllIlIlllllIIIllllI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards the position anvils/pressure plates/buttons are placed.").defaultValue(true).build());
        llllllllllllllllIlIlllllIIIllllI.antiStep = llllllllllllllllIlIlllllIIIllllI.sgGeneral.add(new BoolSetting.Builder().name("anti step").description("Place extra blocks for preventing the enemy from escaping").defaultValue(false).build());
        llllllllllllllllIlIlllllIIIllllI.range = llllllllllllllllIlIlllllIIIllllI.sgPlace.add(new DoubleSetting.Builder().name("range").description("How far away the target can be to be affected.").defaultValue(4.0).min(0.0).build());
        llllllllllllllllIlIlllllIIIllllI.delay = llllllllllllllllIlIlllllIIIllllI.sgPlace.add(new IntSetting.Builder().name("delay").description("The delay in between anvil placements.").min(0).defaultValue(0).sliderMax(50).build());
        llllllllllllllllIlIlllllIIIllllI.startHeight = llllllllllllllllIlIlllllIIIllllI.sgPlace.add(new IntSetting.Builder().name("start-Height").description("The height at beginning").defaultValue(5).min(0).max(10).sliderMin(0).sliderMax(10).build());
        llllllllllllllllIlIlllllIIIllllI.minHeight = llllllllllllllllIlIlllllIIIllllI.sgPlace.add(new IntSetting.Builder().name("min-Height").description("The minimum height accetable").defaultValue(1).min(0).max(5).sliderMin(0).sliderMax(5).build());
        llllllllllllllllIlIlllllIIIllllI.decrease = llllllllllllllllIlIlllllIIIllllI.sgPlace.add(new DoubleSetting.Builder().name("decrease").description("The distance where it will start decrease").defaultValue(1.4).min(0.0).max(4.0).sliderMin(0.0).sliderMax(4.0).build());
        llllllllllllllllIlIlllllIIIllllI.blocksPerTick = llllllllllllllllIlIlllllIIIllllI.sgGeneral.add(new IntSetting.Builder().name("blocks-per-tick").description("The number of blocks you can place every ticks").defaultValue(4).min(2).max(8).sliderMin(2).sliderMax(8).build());
        llllllllllllllllIlIlllllIIIllllI.placeButton = llllllllllllllllIlIlllllIIIllllI.sgPlace.add(new BoolSetting.Builder().name("place-at-feet").description("Automatically places a button or pressure plate at the targets feet to break the anvils.").defaultValue(true).build());
    }

    @Override
    public String getInfoString() {
        AutoAnvil llllllllllllllllIlIllllIllIIllll;
        if (llllllllllllllllIlIllllIllIIllll.target != null && llllllllllllllllIlIllllIllIIllll.target instanceof PlayerEntity) {
            return llllllllllllllllIlIllllIllIIllll.target.getEntityName();
        }
        if (llllllllllllllllIlIllllIllIIllll.target != null) {
            return llllllllllllllllIlIllllIllIIllll.target.getType().getName().getString();
        }
        return null;
    }

    public int getFloorSlot() {
        int llllllllllllllllIlIllllIlllIIllI = -1;
        for (int llllllllllllllllIlIllllIlllIlIII = 0; llllllllllllllllIlIllllIlllIlIII < 9; ++llllllllllllllllIlIllllIlllIlIII) {
            AutoAnvil llllllllllllllllIlIllllIlllIIlll;
            Item llllllllllllllllIlIllllIlllIlIlI = llllllllllllllllIlIllllIlllIIlll.mc.player.inventory.getStack(llllllllllllllllIlIllllIlllIlIII).getItem();
            Block llllllllllllllllIlIllllIlllIlIIl = Block.getBlockFromItem((Item)llllllllllllllllIlIllllIlllIlIlI);
            if (!(llllllllllllllllIlIllllIlllIlIIl instanceof AbstractPressurePlateBlock) && !(llllllllllllllllIlIllllIlllIlIIl instanceof AbstractButtonBlock)) continue;
            llllllllllllllllIlIllllIlllIIllI = llllllllllllllllIlIllllIlllIlIII;
            break;
        }
        return llllllllllllllllIlIllllIlllIIllI;
    }

    @EventHandler
    private void onTick(TickEvent.Pre llllllllllllllllIlIllllIlllllIII) {
        AutoAnvil llllllllllllllllIlIllllIlllllIIl;
        if (llllllllllllllllIlIllllIlllllIIl.isActive() && llllllllllllllllIlIllllIlllllIIl.toggleOnBreak.get().booleanValue() && llllllllllllllllIlIllllIlllllIIl.target != null && llllllllllllllllIlIllllIlllllIIl.target.inventory.getArmorStack(3).isEmpty()) {
            ChatUtils.moduleError(llllllllllllllllIlIllllIlllllIIl, "Target head slot is empty... disabling.", new Object[0]);
            llllllllllllllllIlIllllIlllllIIl.toggle();
            return;
        }
        if (llllllllllllllllIlIllllIlllllIIl.target != null && ((double)llllllllllllllllIlIllllIlllllIIl.mc.player.distanceTo((Entity)llllllllllllllllIlIllllIlllllIIl.target) > llllllllllllllllIlIllllIlllllIIl.range.get() || !llllllllllllllllIlIllllIlllllIIl.target.isAlive())) {
            llllllllllllllllIlIllllIlllllIIl.target = null;
        }
        for (PlayerEntity llllllllllllllllIlIlllllIIIIIlII : llllllllllllllllIlIllllIlllllIIl.mc.world.getPlayers()) {
            if (llllllllllllllllIlIlllllIIIIIlII == llllllllllllllllIlIllllIlllllIIl.mc.player || !Friends.get().attack(llllllllllllllllIlIlllllIIIIIlII) || !llllllllllllllllIlIlllllIIIIIlII.isAlive() || (double)llllllllllllllllIlIllllIlllllIIl.mc.player.distanceTo((Entity)llllllllllllllllIlIlllllIIIIIlII) > llllllllllllllllIlIllllIlllllIIl.range.get() || llllllllllllllllIlIllllIlllllIIl.isHole(llllllllllllllllIlIlllllIIIIIlII.getBlockPos())) continue;
            if (llllllllllllllllIlIllllIlllllIIl.target == null) {
                llllllllllllllllIlIllllIlllllIIl.target = llllllllllllllllIlIlllllIIIIIlII;
                continue;
            }
            if (!(llllllllllllllllIlIllllIlllllIIl.mc.player.distanceTo((Entity)llllllllllllllllIlIllllIlllllIIl.target) > llllllllllllllllIlIllllIlllllIIl.mc.player.distanceTo((Entity)llllllllllllllllIlIlllllIIIIIlII))) continue;
            llllllllllllllllIlIllllIlllllIIl.target = llllllllllllllllIlIlllllIIIIIlII;
        }
        if (llllllllllllllllIlIllllIlllllIIl.target == null) {
            for (FakePlayerEntity llllllllllllllllIlIlllllIIIIIIll : FakePlayerUtils.getPlayers().keySet()) {
                if (!Friends.get().attack((PlayerEntity)llllllllllllllllIlIlllllIIIIIIll) || !llllllllllllllllIlIlllllIIIIIIll.isAlive() || (double)llllllllllllllllIlIllllIlllllIIl.mc.player.distanceTo((Entity)llllllllllllllllIlIlllllIIIIIIll) > llllllllllllllllIlIllllIlllllIIl.range.get() || llllllllllllllllIlIllllIlllllIIl.isHole(llllllllllllllllIlIlllllIIIIIIll.getBlockPos())) continue;
                if (llllllllllllllllIlIllllIlllllIIl.target == null) {
                    llllllllllllllllIlIllllIlllllIIl.target = llllllllllllllllIlIlllllIIIIIIll;
                    continue;
                }
                if (!(llllllllllllllllIlIllllIlllllIIl.mc.player.distanceTo((Entity)llllllllllllllllIlIllllIlllllIIl.target) > llllllllllllllllIlIllllIlllllIIl.mc.player.distanceTo((Entity)llllllllllllllllIlIlllllIIIIIIll))) continue;
                llllllllllllllllIlIllllIlllllIIl.target = llllllllllllllllIlIlllllIIIIIIll;
            }
        }
        if (llllllllllllllllIlIllllIlllllIIl.target == null) {
            return;
        }
        int llllllllllllllllIlIllllIllllIlll = 0;
        if (llllllllllllllllIlIllllIlllllIIl.timer >= llllllllllllllllIlIllllIlllllIIl.delay.get() && llllllllllllllllIlIllllIlllllIIl.target != null) {
            llllllllllllllllIlIllllIlllllIIl.timer = 0;
            int llllllllllllllllIlIllllIllllllIl = llllllllllllllllIlIllllIlllllIIl.getAnvilSlot();
            if (llllllllllllllllIlIllllIllllllIl == -1) {
                return;
            }
            if (llllllllllllllllIlIllllIlllllIIl.placeButton.get().booleanValue()) {
                int llllllllllllllllIlIlllllIIIIIIlI = llllllllllllllllIlIllllIlllllIIl.getFloorSlot();
                BlockPos llllllllllllllllIlIlllllIIIIIIIl = llllllllllllllllIlIllllIlllllIIl.target.getBlockPos();
                if (BlockUtils.place(llllllllllllllllIlIlllllIIIIIIIl, Hand.MAIN_HAND, llllllllllllllllIlIlllllIIIIIIlI, llllllllllllllllIlIllllIlllllIIl.rotate.get(), 0, false)) {
                    ++llllllllllllllllIlIllllIllllIlll;
                }
            }
            if (llllllllllllllllIlIllllIlllllIIl.antiStep.get().booleanValue()) {
                int llllllllllllllllIlIllllIlllllllI = InvUtils.findItemInHotbar(Blocks.OBSIDIAN.asItem());
                if (llllllllllllllllIlIllllIlllllllI == -1) {
                    return;
                }
                for (Vec3d llllllllllllllllIlIllllIllllllll : llllllllllllllllIlIllllIlllllIIl.antiStepStructure) {
                    BlockPos llllllllllllllllIlIlllllIIIIIIII = llllllllllllllllIlIllllIlllllIIl.target.getBlockPos().add(llllllllllllllllIlIllllIllllllll.x, llllllllllllllllIlIllllIllllllll.y, llllllllllllllllIlIllllIllllllll.z);
                    if (!BlockUtils.place(llllllllllllllllIlIlllllIIIIIIII, Hand.MAIN_HAND, llllllllllllllllIlIllllIlllllllI, llllllllllllllllIlIllllIlllllIIl.rotate.get(), 0, true) || ++llllllllllllllllIlIllllIllllIlll != llllllllllllllllIlIllllIlllllIIl.blocksPerTick.get()) continue;
                    return;
                }
            }
            int llllllllllllllllIlIllllIllllllII = llllllllllllllllIlIllllIlllllIIl.startHeight.get() + (int)(llllllllllllllllIlIllllIlllllIIl.mc.player.getY() - llllllllllllllllIlIllllIlllllIIl.target.getY());
            for (double llllllllllllllllIlIllllIlllllIll = Math.sqrt(Math.pow(llllllllllllllllIlIllllIlllllIIl.mc.player.getPos().x - llllllllllllllllIlIllllIlllllIIl.target.getX(), 2.0) + Math.pow(llllllllllllllllIlIllllIlllllIIl.mc.player.getPos().z - llllllllllllllllIlIllllIlllllIIl.target.getZ(), 2.0)); llllllllllllllllIlIllllIlllllIll > llllllllllllllllIlIllllIlllllIIl.decrease.get(); llllllllllllllllIlIllllIlllllIll -= llllllllllllllllIlIllllIlllllIIl.decrease.get().doubleValue()) {
                --llllllllllllllllIlIllllIllllllII;
            }
            if (llllllllllllllllIlIllllIllllllII <= llllllllllllllllIlIllllIlllllIIl.minHeight.get()) {
                ChatUtils.moduleError(llllllllllllllllIlIllllIlllllIIl, "Target too far away... disabling.", new Object[0]);
                llllllllllllllllIlIllllIlllllIIl.toggle();
                return;
            }
            BlockPos llllllllllllllllIlIllllIlllllIlI = llllllllllllllllIlIllllIlllllIIl.target.getBlockPos().up().add(0, llllllllllllllllIlIllllIllllllII, 0);
            BlockUtils.place(llllllllllllllllIlIllllIlllllIlI, Hand.MAIN_HAND, llllllllllllllllIlIllllIllllllIl, llllllllllllllllIlIllllIlllllIIl.rotate.get(), 0, true);
        } else {
            ++llllllllllllllllIlIllllIlllllIIl.timer;
        }
    }

    @Override
    public void onActivate() {
        llllllllllllllllIlIlllllIIIlllII.timer = 0;
        llllllllllllllllIlIlllllIIIlllII.target = null;
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent llllllllllllllllIlIlllllIIIlIlll) {
        if (llllllllllllllllIlIlllllIIIlIlll.screen instanceof AnvilScreen) {
            AutoAnvil llllllllllllllllIlIlllllIIIlIllI;
            llllllllllllllllIlIlllllIIIlIllI.mc.player.closeScreen();
        }
    }

    private int getAnvilSlot() {
        int llllllllllllllllIlIllllIllIlIlll = -1;
        for (int llllllllllllllllIlIllllIllIllIIl = 0; llllllllllllllllIlIllllIllIllIIl < 9; ++llllllllllllllllIlIllllIllIllIIl) {
            AutoAnvil llllllllllllllllIlIllllIllIllIII;
            Item llllllllllllllllIlIllllIllIllIll = llllllllllllllllIlIllllIllIllIII.mc.player.inventory.getStack(llllllllllllllllIlIllllIllIllIIl).getItem();
            Block llllllllllllllllIlIllllIllIllIlI = Block.getBlockFromItem((Item)llllllllllllllllIlIllllIllIllIll);
            if (!(llllllllllllllllIlIllllIllIllIlI instanceof AnvilBlock)) continue;
            llllllllllllllllIlIllllIllIlIlll = llllllllllllllllIlIllllIllIllIIl;
            break;
        }
        return llllllllllllllllIlIllllIllIlIlll;
    }

    private boolean isHole(BlockPos llllllllllllllllIlIlllllIIIlIIII) {
        AutoAnvil llllllllllllllllIlIlllllIIIlIIIl;
        Mutable llllllllllllllllIlIlllllIIIIllll = new Mutable(llllllllllllllllIlIlllllIIIlIIII.getX(), llllllllllllllllIlIlllllIIIlIIII.getY(), llllllllllllllllIlIlllllIIIlIIII.getZ());
        return llllllllllllllllIlIlllllIIIlIIIl.mc.world.getBlockState(llllllllllllllllIlIlllllIIIIllll.add(1, 0, 0)).getBlock().is(Blocks.AIR) || llllllllllllllllIlIlllllIIIlIIIl.mc.world.getBlockState(llllllllllllllllIlIlllllIIIIllll.add(-1, 0, 0)).getBlock().is(Blocks.AIR) || llllllllllllllllIlIlllllIIIlIIIl.mc.world.getBlockState(llllllllllllllllIlIlllllIIIIllll.add(0, 0, 1)).getBlock().is(Blocks.AIR) || llllllllllllllllIlIlllllIIIlIIIl.mc.world.getBlockState(llllllllllllllllIlIlllllIIIIllll.add(0, 0, -1)).getBlock().is(Blocks.AIR);
    }
}

