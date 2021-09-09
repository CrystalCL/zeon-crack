/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  baritone.api.pathing.goals.Goal
 *  baritone.api.pathing.goals.GoalBlock
 *  com.google.common.collect.Lists
 *  net.minecraft.item.ToolItem
 *  net.minecraft.block.Blocks
 *  net.minecraft.block.Block
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.network.packet.s2c.play.DisconnectS2CPacket
 */
package minegame159.meteorclient.systems.modules.world;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.GameJoinedEvent;
import minegame159.meteorclient.events.game.GameLeftEvent;
import minegame159.meteorclient.events.meteor.ActiveModulesChangedEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BlockSetting;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.combat.AutoLog;
import minegame159.meteorclient.systems.modules.movement.GUIMove;
import minegame159.meteorclient.systems.modules.movement.Jesus;
import minegame159.meteorclient.systems.modules.movement.NoFall;
import minegame159.meteorclient.systems.modules.player.AntiHunger;
import minegame159.meteorclient.systems.modules.player.AutoDrop;
import minegame159.meteorclient.systems.modules.player.AutoEat;
import minegame159.meteorclient.systems.modules.player.AutoTool;
import minegame159.meteorclient.systems.modules.player.NoBreakDelay;
import net.minecraft.item.ToolItem;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;

public class InfinityMiner
extends Module {
    public final /* synthetic */ Setting<Block> repairBlock;
    public final /* synthetic */ Setting<Boolean> smartModuleToggle;
    public final /* synthetic */ Setting<Double> durabilityThreshold;
    private volatile /* synthetic */ Boolean BLOCKER;
    public final /* synthetic */ Setting<Boolean> autoWalkHome;
    private /* synthetic */ int playerY;
    private /* synthetic */ Mode currentMode;
    private /* synthetic */ int playerX;
    private /* synthetic */ boolean baritoneRunning;
    private final /* synthetic */ HashMap<String, Boolean> originalSettings;
    private final /* synthetic */ SettingGroup sgAutoToggles;
    public final /* synthetic */ Setting<Boolean> autoLogOut;
    private /* synthetic */ Mode secondaryMode;
    private /* synthetic */ int playerZ;
    public final /* synthetic */ Setting<Block> targetBlock;
    private final /* synthetic */ SettingGroup sgExtras;
    private final /* synthetic */ SettingGroup sgGeneral;

    private void baritoneRequestStop() {
        BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
        lIIIIllIlIllllI.baritoneRunning = false;
        lIIIIllIlIllllI.currentMode = Mode.Still;
    }

    public InfinityMiner() {
        super(Categories.World, "infinity-miner", "Allows you to essentially mine forever.");
        InfinityMiner lIIIIlllIIIllII;
        lIIIIlllIIIllII.sgGeneral = lIIIIlllIIIllII.settings.getDefaultGroup();
        lIIIIlllIIIllII.sgAutoToggles = lIIIIlllIIIllII.settings.createGroup("Auto Toggles");
        lIIIIlllIIIllII.sgExtras = lIIIIlllIIIllII.settings.createGroup("Extras");
        lIIIIlllIIIllII.targetBlock = lIIIIlllIIIllII.sgGeneral.add(new BlockSetting.Builder().name("target-block").description("The target block to mine.").defaultValue(Blocks.ANCIENT_DEBRIS).build());
        lIIIIlllIIIllII.repairBlock = lIIIIlllIIIllII.sgGeneral.add(new BlockSetting.Builder().name("repair-block").description("The block mined to repair your pickaxe.").defaultValue(Blocks.NETHER_QUARTZ_ORE).build());
        lIIIIlllIIIllII.durabilityThreshold = lIIIIlllIIIllII.sgGeneral.add(new DoubleSetting.Builder().name("durability-threshold").description("The durability at which to start repairing as a percent of maximum durability.").defaultValue(0.15).max(0.95).min(0.05).sliderMin(0.05).sliderMax(0.95).build());
        lIIIIlllIIIllII.smartModuleToggle = lIIIIlllIIIllII.sgAutoToggles.add(new BoolSetting.Builder().name("smart-module-toggle").description("Will automatically enable helpful modules.").defaultValue(true).build());
        lIIIIlllIIIllII.autoWalkHome = lIIIIlllIIIllII.sgExtras.add(new BoolSetting.Builder().name("walk-home").description("Will walk 'home' when your inventory is full.").defaultValue(false).build());
        lIIIIlllIIIllII.autoLogOut = lIIIIlllIIIllII.sgExtras.add(new BoolSetting.Builder().name("log-out").description("Logs out when your inventory is full. Will walk home FIRST if walk home is enabled.").defaultValue(false).build());
        lIIIIlllIIIllII.currentMode = Mode.Still;
        lIIIIlllIIIllII.baritoneRunning = false;
        lIIIIlllIIIllII.originalSettings = new HashMap();
        lIIIIlllIIIllII.BLOCKER = false;
    }

    @Override
    public String getInfoString() {
        InfinityMiner lIIIIllIIllIIll;
        switch (lIIIIllIIllIIll.getMode()) {
            case Home: {
                int[] lIIIIllIIllIlII = lIIIIllIIllIIll.getHomeCoords();
                return String.valueOf(new StringBuilder().append("Heading Home: ").append(lIIIIllIIllIlII[0]).append(" ").append(lIIIIllIIllIlII[1]).append(" ").append(lIIIIllIIllIlII[2]));
            }
            case Target: {
                return String.valueOf(new StringBuilder().append("Mining: ").append(lIIIIllIIllIIll.getCurrentTarget().getName().getString()));
            }
            case Repair: {
                return String.valueOf(new StringBuilder().append("Repair-Mining: ").append(lIIIIllIIllIIll.getCurrentTarget().getName().getString()));
            }
            case Still: {
                return "Resting";
            }
        }
        return "";
    }

    private void baritoneRequestMineTargetBlock() {
        try {
            InfinityMiner lIIIIllIllIlIII;
            BaritoneAPI.getProvider().getPrimaryBaritone().getMineProcess().mine(new Block[]{lIIIIllIllIlIII.targetBlock.get()});
            lIIIIllIllIlIII.baritoneRunning = true;
        }
        catch (Exception lIIIIllIllIIllI) {
            // empty catch block
        }
    }

    private void baritoneRequestMineRepairBlock() {
        try {
            InfinityMiner lIIIIllIllIIIlI;
            BaritoneAPI.getProvider().getPrimaryBaritone().getMineProcess().mine(new Block[]{lIIIIllIllIIIlI.repairBlock.get()});
            lIIIIllIllIIIlI.baritoneRunning = true;
        }
        catch (Exception lIIIIllIllIIIIl) {
            // empty catch block
        }
    }

    public int[] getHomeCoords() {
        InfinityMiner lIIIIllIIllllIl;
        return new int[]{lIIIIllIIllllIl.playerX, lIIIIllIIllllIl.playerY, lIIIIllIIllllIl.playerX};
    }

    private void baritoneRequestPathHome() {
        InfinityMiner lIIIIllIlIllIll;
        if (lIIIIllIlIllIll.autoWalkHome.get().booleanValue()) {
            lIIIIllIlIllIll.baritoneRequestStop();
            lIIIIllIlIllIll.secondaryMode = Mode.Home;
            lIIIIllIlIllIll.currentMode = Mode.Home;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)new GoalBlock(lIIIIllIlIllIll.playerX, lIIIIllIlIllIll.playerY, lIIIIllIlIllIll.playerZ));
        }
    }

    public Mode getMode() {
        InfinityMiner lIIIIllIlIIIIll;
        return lIIIIllIlIIIIll.currentMode;
    }

    private int getCurrentDamage() {
        InfinityMiner lIIIIllIIllIlll;
        return lIIIIllIIllIlll.mc.player != null ? lIIIIllIIllIlll.mc.player.getMainHandStack().getItem().getMaxDamage() - lIIIIllIIllIlll.mc.player.getMainHandStack().getDamage() : -1;
    }

    private List<Module> getToggleModules() {
        return Lists.newArrayList((Object[])new Module[]{Modules.get().get(Jesus.class), Modules.get().get(NoBreakDelay.class), Modules.get().get(AntiHunger.class), Modules.get().get(AutoEat.class), Modules.get().get(NoFall.class), Modules.get().get(AutoLog.class), Modules.get().get(AutoTool.class), Modules.get().get(AutoDrop.class), Modules.get().get(GUIMove.class)});
    }

    @Override
    public void onActivate() {
        InfinityMiner lIIIIlllIIIIllI;
        if (lIIIIlllIIIIllI.smartModuleToggle.get().booleanValue()) {
            lIIIIlllIIIIllI.BLOCKER = true;
            for (Module lIIIIlllIIIIlll : lIIIIlllIIIIllI.getToggleModules()) {
                lIIIIlllIIIIllI.originalSettings.put(lIIIIlllIIIIlll.name, lIIIIlllIIIIlll.isActive());
                if (lIIIIlllIIIIlll.isActive()) continue;
                lIIIIlllIIIIlll.toggle();
            }
            lIIIIlllIIIIllI.BLOCKER = false;
        }
        if (lIIIIlllIIIIllI.mc.player != null) {
            lIIIIlllIIIIllI.playerX = (int)lIIIIlllIIIIllI.mc.player.getX();
            lIIIIlllIIIIllI.playerY = (int)lIIIIlllIIIIllI.mc.player.getY();
            lIIIIlllIIIIllI.playerZ = (int)lIIIIlllIIIIllI.mc.player.getZ();
        }
    }

    @EventHandler
    private void onGameDisconnect(GameLeftEvent lIIIIllIlIIlIll) {
        InfinityMiner lIIIIllIlIIlIlI;
        lIIIIllIlIIlIlI.baritoneRequestStop();
        if (!((Boolean)BaritoneAPI.getSettings().mineScanDroppedItems.value).booleanValue()) {
            BaritoneAPI.getSettings().mineScanDroppedItems.value = true;
        }
        if (lIIIIllIlIIlIlI.isActive()) {
            lIIIIllIlIIlIlI.toggle();
        }
    }

    private Boolean isInventoryFull() {
        InfinityMiner lIIIIllIlIlIllI;
        if (lIIIIllIlIlIllI.mc.player == null) {
            return false;
        }
        if (lIIIIllIlIlIllI.mc.player.inventory.getEmptySlot() != -1) {
            return false;
        }
        for (int lIIIIllIlIllIII = 0; lIIIIllIlIllIII < lIIIIllIlIlIllI.mc.player.inventory.size(); ++lIIIIllIlIllIII) {
            if (lIIIIllIlIlIllI.mc.player.inventory.getStack(lIIIIllIlIllIII).getItem() != lIIIIllIlIlIllI.targetBlock.get().asItem() || lIIIIllIlIlIllI.mc.player.inventory.getStack(lIIIIllIlIllIII).getCount() >= lIIIIllIlIlIllI.targetBlock.get().asItem().getMaxCount()) continue;
            return false;
        }
        return true;
    }

    @EventHandler
    private void onTick(TickEvent.Post lIIIIllIlllIllI) {
        try {
            InfinityMiner lIIIIllIlllIlIl;
            if (lIIIIllIlllIlIl.mc.player == null) {
                return;
            }
            if (!lIIIIllIlllIlIl.baritoneRunning && lIIIIllIlllIlIl.currentMode == Mode.Still) {
                if (lIIIIllIlllIlIl.autoWalkHome.get().booleanValue() && lIIIIllIlllIlIl.isInventoryFull().booleanValue() && lIIIIllIlllIlIl.secondaryMode != Mode.Home) {
                    lIIIIllIlllIlIl.baritoneRequestPathHome();
                    return;
                }
                Mode mode = lIIIIllIlllIlIl.currentMode = lIIIIllIlllIlIl.isTool() && (double)lIIIIllIlllIlIl.getCurrentDamage() <= lIIIIllIlllIlIl.durabilityThreshold.get() ? Mode.Repair : Mode.Target;
                if (lIIIIllIlllIlIl.currentMode == Mode.Repair) {
                    lIIIIllIlllIlIl.baritoneRequestMineRepairBlock();
                } else {
                    lIIIIllIlllIlIl.baritoneRequestMineTargetBlock();
                }
            } else if (lIIIIllIlllIlIl.autoWalkHome.get().booleanValue() && lIIIIllIlllIlIl.isInventoryFull().booleanValue() && lIIIIllIlllIlIl.secondaryMode != Mode.Home) {
                lIIIIllIlllIlIl.baritoneRequestPathHome();
            } else if (!lIIIIllIlllIlIl.autoWalkHome.get().booleanValue() && lIIIIllIlllIlIl.isInventoryFull().booleanValue() && lIIIIllIlllIlIl.autoLogOut.get().booleanValue()) {
                lIIIIllIlllIlIl.toggle();
                lIIIIllIlllIlIl.requestLogout(lIIIIllIlllIlIl.currentMode);
            } else if (lIIIIllIlllIlIl.currentMode == Mode.Repair) {
                int lIIIIllIllllIII = 15;
                if (((Boolean)BaritoneAPI.getSettings().mineScanDroppedItems.value).booleanValue()) {
                    BaritoneAPI.getSettings().mineScanDroppedItems.value = false;
                }
                if (lIIIIllIlllIlIl.isTool() && lIIIIllIlllIlIl.getCurrentDamage() >= lIIIIllIlllIlIl.mc.player.getMainHandStack().getMaxDamage() - lIIIIllIllllIII) {
                    if (lIIIIllIlllIlIl.secondaryMode != Mode.Home) {
                        lIIIIllIlllIlIl.currentMode = Mode.Target;
                        lIIIIllIlllIlIl.baritoneRequestMineTargetBlock();
                    } else {
                        lIIIIllIlllIlIl.currentMode = Mode.Home;
                        lIIIIllIlllIlIl.baritoneRequestPathHome();
                    }
                }
            } else if (lIIIIllIlllIlIl.currentMode == Mode.Target) {
                if (!((Boolean)BaritoneAPI.getSettings().mineScanDroppedItems.value).booleanValue()) {
                    BaritoneAPI.getSettings().mineScanDroppedItems.value = true;
                }
                if (lIIIIllIlllIlIl.isTool() && (double)lIIIIllIlllIlIl.getCurrentDamage() <= lIIIIllIlllIlIl.durabilityThreshold.get() * (double)lIIIIllIlllIlIl.mc.player.getMainHandStack().getMaxDamage()) {
                    lIIIIllIlllIlIl.currentMode = Mode.Repair;
                    lIIIIllIlllIlIl.baritoneRequestMineRepairBlock();
                } else if (lIIIIllIlllIlIl.autoWalkHome.get().booleanValue() && lIIIIllIlllIlIl.isInventoryFull().booleanValue()) {
                    lIIIIllIlllIlIl.baritoneRequestPathHome();
                } else if (!lIIIIllIlllIlIl.autoWalkHome.get().booleanValue() && lIIIIllIlllIlIl.isInventoryFull().booleanValue() && lIIIIllIlllIlIl.autoWalkHome.get().booleanValue()) {
                    lIIIIllIlllIlIl.requestLogout(lIIIIllIlllIlIl.currentMode);
                }
            } else if (lIIIIllIlllIlIl.currentMode == Mode.Home) {
                if (Math.abs(lIIIIllIlllIlIl.mc.player.getY() - (double)lIIIIllIlllIlIl.playerY) <= 0.5 && Math.abs(lIIIIllIlllIlIl.mc.player.getX() - (double)lIIIIllIlllIlIl.playerX) <= 0.5 && Math.abs(lIIIIllIlllIlIl.mc.player.getZ() - (double)lIIIIllIlllIlIl.playerZ) <= 0.5) {
                    if (lIIIIllIlllIlIl.autoLogOut.get().booleanValue()) {
                        lIIIIllIlllIlIl.requestLogout(lIIIIllIlllIlIl.currentMode);
                    }
                    lIIIIllIlllIlIl.toggle();
                } else if (lIIIIllIlllIlIl.isTool() && (double)lIIIIllIlllIlIl.getCurrentDamage() <= lIIIIllIlllIlIl.durabilityThreshold.get()) {
                    lIIIIllIlllIlIl.currentMode = Mode.Repair;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @EventHandler
    private void onGameJoin(GameJoinedEvent lIIIIllIlIIIlll) {
        InfinityMiner lIIIIllIlIIlIII;
        lIIIIllIlIIlIII.baritoneRequestStop();
        if (!((Boolean)BaritoneAPI.getSettings().mineScanDroppedItems.value).booleanValue()) {
            BaritoneAPI.getSettings().mineScanDroppedItems.value = true;
        }
        if (lIIIIllIlIIlIII.isActive()) {
            lIIIIllIlIIlIII.toggle();
        }
    }

    @Override
    public void onDeactivate() {
        InfinityMiner lIIIIllIllllllI;
        if (lIIIIllIllllllI.smartModuleToggle.get().booleanValue()) {
            lIIIIllIllllllI.BLOCKER = true;
            try {
                for (Module lIIIIllIlllllll : lIIIIllIllllllI.getToggleModules()) {
                    if (lIIIIllIlllllll == null || lIIIIllIllllllI.originalSettings.get(lIIIIllIlllllll.name).booleanValue() == lIIIIllIlllllll.isActive()) continue;
                    lIIIIllIlllllll.toggle();
                }
            }
            catch (NullPointerException lIIIIllIlllllII) {
                // empty catch block
            }
            lIIIIllIllllllI.originalSettings.clear();
            lIIIIllIllllllI.BLOCKER = false;
        }
        if (!((Boolean)BaritoneAPI.getSettings().mineScanDroppedItems.value).booleanValue()) {
            BaritoneAPI.getSettings().mineScanDroppedItems.value = true;
        }
        lIIIIllIllllllI.baritoneRequestStop();
        lIIIIllIllllllI.baritoneRunning = false;
        lIIIIllIllllllI.currentMode = Mode.Still;
        lIIIIllIllllllI.secondaryMode = null;
    }

    public Block getCurrentTarget() {
        InfinityMiner lIIIIllIlIIIIIl;
        return lIIIIllIlIIIIIl.currentMode == Mode.Repair ? lIIIIllIlIIIIIl.repairBlock.get() : lIIIIllIlIIIIIl.targetBlock.get();
    }

    private void requestLogout(Mode lIIIIllIlIlIIII) {
        InfinityMiner lIIIIllIlIIllll;
        if (lIIIIllIlIIllll.mc.player != null) {
            if (lIIIIllIlIlIIII == Mode.Home) {
                lIIIIllIlIIllll.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText("Infinity Miner: Inventory is Full and You Are Home")));
            } else {
                lIIIIllIlIIllll.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText("Infinity Miner: Inventory is Full")));
            }
        }
    }

    @EventHandler
    private void moduleChange(ActiveModulesChangedEvent lIIIIllIllIlllI) {
        InfinityMiner lIIIIllIllIllIl;
        if (!lIIIIllIllIllIl.BLOCKER.booleanValue()) {
            for (Module lIIIIllIlllIIII : lIIIIllIllIllIl.getToggleModules()) {
                if (lIIIIllIlllIIII == null || lIIIIllIlllIIII.isActive()) continue;
                lIIIIllIllIllIl.originalSettings.remove(lIIIIllIlllIIII.name);
            }
        }
    }

    private boolean isTool() {
        InfinityMiner lIIIIllIIlllIll;
        return lIIIIllIIlllIll.mc.player != null && lIIIIllIIlllIll.mc.player.getMainHandStack() != null && lIIIIllIIlllIll.mc.player.getMainHandStack().getItem() instanceof ToolItem;
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Target;
        public static final /* synthetic */ /* enum */ Mode Home;
        public static final /* synthetic */ /* enum */ Mode Still;
        public static final /* synthetic */ /* enum */ Mode Repair;

        private Mode() {
            Mode llIlIIlllllIllI;
        }

        static {
            Target = new Mode();
            Repair = new Mode();
            Still = new Mode();
            Home = new Mode();
            $VALUES = Mode.$values();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Target, Repair, Still, Home};
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String llIlIIlllllllII) {
            return Enum.valueOf(Mode.class, llIlIIlllllllII);
        }
    }
}

