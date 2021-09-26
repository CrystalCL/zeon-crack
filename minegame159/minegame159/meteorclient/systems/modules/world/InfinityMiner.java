/*
 * Decompiled with CFR 0.151.
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
    private int playerX;
    private final SettingGroup sgGeneral;
    public final Setting<Boolean> smartModuleToggle;
    private Boolean BLOCKER;
    private int playerZ;
    private final HashMap<String, Boolean> originalSettings;
    private boolean baritoneRunning;
    private final SettingGroup sgAutoToggles;
    private int playerY;
    public final Setting<Boolean> autoLogOut;
    public final Setting<Block> repairBlock;
    private Mode currentMode;
    private Mode secondaryMode;
    public final Setting<Boolean> autoWalkHome;
    public final Setting<Block> targetBlock;
    public final Setting<Double> durabilityThreshold;
    private final SettingGroup sgExtras;

    private void requestLogout(Mode mode) {
        if (this.mc.player != null) {
            if (mode == Mode.Home) {
                this.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText("Infinity Miner: Inventory is Full and You Are Home")));
            } else {
                this.mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket((Text)new LiteralText("Infinity Miner: Inventory is Full")));
            }
        }
    }

    @Override
    public void onActivate() {
        if (this.smartModuleToggle.get().booleanValue()) {
            this.BLOCKER = true;
            for (Module module : this.getToggleModules()) {
                this.originalSettings.put(module.name, module.isActive());
                if (module.isActive()) continue;
                module.toggle();
            }
            this.BLOCKER = false;
        }
        if (this.mc.player != null) {
            this.playerX = (int)this.mc.player.getX();
            this.playerY = (int)this.mc.player.getY();
            this.playerZ = (int)this.mc.player.getZ();
        }
    }

    private boolean isTool() {
        return this.mc.player != null && this.mc.player.getMainHandStack() != null && this.mc.player.getMainHandStack().getItem() instanceof ToolItem;
    }

    private void baritoneRequestStop() {
        BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
        this.baritoneRunning = false;
        this.currentMode = Mode.Still;
    }

    public int[] getHomeCoords() {
        return new int[]{this.playerX, this.playerY, this.playerX};
    }

    private void baritoneRequestMineRepairBlock() {
        try {
            BaritoneAPI.getProvider().getPrimaryBaritone().getMineProcess().mine(new Block[]{this.repairBlock.get()});
            this.baritoneRunning = true;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @EventHandler
    private void onGameJoin(GameJoinedEvent gameJoinedEvent) {
        this.baritoneRequestStop();
        if (!((Boolean)BaritoneAPI.getSettings().mineScanDroppedItems.value).booleanValue()) {
            BaritoneAPI.getSettings().mineScanDroppedItems.value = true;
        }
        if (this.isActive()) {
            this.toggle();
        }
    }

    private List<Module> getToggleModules() {
        return Lists.newArrayList((Object[])new Module[]{Modules.get().get(Jesus.class), Modules.get().get(NoBreakDelay.class), Modules.get().get(AntiHunger.class), Modules.get().get(AutoEat.class), Modules.get().get(NoFall.class), Modules.get().get(AutoLog.class), Modules.get().get(AutoTool.class), Modules.get().get(AutoDrop.class), Modules.get().get(GUIMove.class)});
    }

    public Mode getMode() {
        return this.currentMode;
    }

    @EventHandler
    private void onGameDisconnect(GameLeftEvent gameLeftEvent) {
        this.baritoneRequestStop();
        if (!((Boolean)BaritoneAPI.getSettings().mineScanDroppedItems.value).booleanValue()) {
            BaritoneAPI.getSettings().mineScanDroppedItems.value = true;
        }
        if (this.isActive()) {
            this.toggle();
        }
    }

    @EventHandler
    private void moduleChange(ActiveModulesChangedEvent activeModulesChangedEvent) {
        if (!this.BLOCKER.booleanValue()) {
            for (Module module : this.getToggleModules()) {
                if (module == null || module.isActive()) continue;
                this.originalSettings.remove(module.name);
            }
        }
    }

    private void baritoneRequestPathHome() {
        if (this.autoWalkHome.get().booleanValue()) {
            this.baritoneRequestStop();
            this.secondaryMode = Mode.Home;
            this.currentMode = Mode.Home;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)new GoalBlock(this.playerX, this.playerY, this.playerZ));
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        try {
            if (this.mc.player == null) {
                return;
            }
            if (!this.baritoneRunning && this.currentMode == Mode.Still) {
                if (this.autoWalkHome.get().booleanValue() && this.isInventoryFull().booleanValue() && this.secondaryMode != Mode.Home) {
                    this.baritoneRequestPathHome();
                    return;
                }
                Mode mode = this.currentMode = this.isTool() && (double)this.getCurrentDamage() <= this.durabilityThreshold.get() ? Mode.Repair : Mode.Target;
                if (this.currentMode == Mode.Repair) {
                    this.baritoneRequestMineRepairBlock();
                } else {
                    this.baritoneRequestMineTargetBlock();
                }
            } else if (this.autoWalkHome.get().booleanValue() && this.isInventoryFull().booleanValue() && this.secondaryMode != Mode.Home) {
                this.baritoneRequestPathHome();
            } else if (!this.autoWalkHome.get().booleanValue() && this.isInventoryFull().booleanValue() && this.autoLogOut.get().booleanValue()) {
                this.toggle();
                this.requestLogout(this.currentMode);
            } else if (this.currentMode == Mode.Repair) {
                int n = 15;
                if (((Boolean)BaritoneAPI.getSettings().mineScanDroppedItems.value).booleanValue()) {
                    BaritoneAPI.getSettings().mineScanDroppedItems.value = false;
                }
                if (this.isTool() && this.getCurrentDamage() >= this.mc.player.getMainHandStack().getMaxDamage() - n) {
                    if (this.secondaryMode != Mode.Home) {
                        this.currentMode = Mode.Target;
                        this.baritoneRequestMineTargetBlock();
                    } else {
                        this.currentMode = Mode.Home;
                        this.baritoneRequestPathHome();
                    }
                }
            } else if (this.currentMode == Mode.Target) {
                if (!((Boolean)BaritoneAPI.getSettings().mineScanDroppedItems.value).booleanValue()) {
                    BaritoneAPI.getSettings().mineScanDroppedItems.value = true;
                }
                if (this.isTool() && (double)this.getCurrentDamage() <= this.durabilityThreshold.get() * (double)this.mc.player.getMainHandStack().getMaxDamage()) {
                    this.currentMode = Mode.Repair;
                    this.baritoneRequestMineRepairBlock();
                } else if (this.autoWalkHome.get().booleanValue() && this.isInventoryFull().booleanValue()) {
                    this.baritoneRequestPathHome();
                } else if (!this.autoWalkHome.get().booleanValue() && this.isInventoryFull().booleanValue() && this.autoWalkHome.get().booleanValue()) {
                    this.requestLogout(this.currentMode);
                }
            } else if (this.currentMode == Mode.Home) {
                if (Math.abs(this.mc.player.getY() - (double)this.playerY) <= 0.5 && Math.abs(this.mc.player.getX() - (double)this.playerX) <= 0.5 && Math.abs(this.mc.player.getZ() - (double)this.playerZ) <= 0.5) {
                    if (this.autoLogOut.get().booleanValue()) {
                        this.requestLogout(this.currentMode);
                    }
                    this.toggle();
                } else if (this.isTool() && (double)this.getCurrentDamage() <= this.durabilityThreshold.get()) {
                    this.currentMode = Mode.Repair;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private Boolean isInventoryFull() {
        if (this.mc.player == null) {
            return false;
        }
        if (this.mc.player.inventory.getEmptySlot() != -1) {
            return false;
        }
        for (int i = 0; i < this.mc.player.inventory.size(); ++i) {
            if (this.mc.player.inventory.getStack(i).getItem() != this.targetBlock.get().asItem() || this.mc.player.inventory.getStack(i).getCount() >= this.targetBlock.get().asItem().getMaxCount()) continue;
            return false;
        }
        return true;
    }

    public Block getCurrentTarget() {
        return this.currentMode == Mode.Repair ? this.repairBlock.get() : this.targetBlock.get();
    }

    @Override
    public void onDeactivate() {
        if (this.smartModuleToggle.get().booleanValue()) {
            this.BLOCKER = true;
            try {
                for (Module module : this.getToggleModules()) {
                    if (module == null || this.originalSettings.get(module.name).booleanValue() == module.isActive()) continue;
                    module.toggle();
                }
            }
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
            this.originalSettings.clear();
            this.BLOCKER = false;
        }
        if (!((Boolean)BaritoneAPI.getSettings().mineScanDroppedItems.value).booleanValue()) {
            BaritoneAPI.getSettings().mineScanDroppedItems.value = true;
        }
        this.baritoneRequestStop();
        this.baritoneRunning = false;
        this.currentMode = Mode.Still;
        this.secondaryMode = null;
    }

    @Override
    public String getInfoString() {
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$world$InfinityMiner$Mode[this.getMode().ordinal()]) {
            case 1: {
                int[] nArray = this.getHomeCoords();
                return String.valueOf(new StringBuilder().append("Heading Home: ").append(nArray[0]).append(" ").append(nArray[1]).append(" ").append(nArray[2]));
            }
            case 2: {
                return String.valueOf(new StringBuilder().append("Mining: ").append(this.getCurrentTarget().getName().getString()));
            }
            case 3: {
                return String.valueOf(new StringBuilder().append("Repair-Mining: ").append(this.getCurrentTarget().getName().getString()));
            }
            case 4: {
                return "Resting";
            }
        }
        return "";
    }

    public InfinityMiner() {
        super(Categories.World, "infinity-miner", "Allows you to essentially mine forever.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgAutoToggles = this.settings.createGroup("Auto Toggles");
        this.sgExtras = this.settings.createGroup("Extras");
        this.targetBlock = this.sgGeneral.add(new BlockSetting.Builder().name("target-block").description("The target block to mine.").defaultValue(Blocks.ANCIENT_DEBRIS).build());
        this.repairBlock = this.sgGeneral.add(new BlockSetting.Builder().name("repair-block").description("The block mined to repair your pickaxe.").defaultValue(Blocks.NETHER_QUARTZ_ORE).build());
        this.durabilityThreshold = this.sgGeneral.add(new DoubleSetting.Builder().name("durability-threshold").description("The durability at which to start repairing as a percent of maximum durability.").defaultValue(0.15).max(0.95).min(0.05).sliderMin(0.05).sliderMax(0.95).build());
        this.smartModuleToggle = this.sgAutoToggles.add(new BoolSetting.Builder().name("smart-module-toggle").description("Will automatically enable helpful modules.").defaultValue(true).build());
        this.autoWalkHome = this.sgExtras.add(new BoolSetting.Builder().name("walk-home").description("Will walk 'home' when your inventory is full.").defaultValue(false).build());
        this.autoLogOut = this.sgExtras.add(new BoolSetting.Builder().name("log-out").description("Logs out when your inventory is full. Will walk home FIRST if walk home is enabled.").defaultValue(false).build());
        this.currentMode = Mode.Still;
        this.baritoneRunning = false;
        this.originalSettings = new HashMap();
        this.BLOCKER = false;
    }

    private int getCurrentDamage() {
        return this.mc.player != null ? this.mc.player.getMainHandStack().getItem().getMaxDamage() - this.mc.player.getMainHandStack().getDamage() : -1;
    }

    private void baritoneRequestMineTargetBlock() {
        try {
            BaritoneAPI.getProvider().getPrimaryBaritone().getMineProcess().mine(new Block[]{this.targetBlock.get()});
            this.baritoneRunning = true;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Still;
        public static final /* enum */ Mode Repair;
        public static final /* enum */ Mode Home;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Target;

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            Target = new Mode();
            Repair = new Mode();
            Still = new Mode();
            Home = new Mode();
            $VALUES = Mode.$values();
        }

        private static Mode[] $values() {
            return new Mode[]{Target, Repair, Still, Home};
        }
    }
}

