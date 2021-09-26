/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.PlayerUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;

public class Surround
extends Module {
    private final Setting<Boolean> center;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> HelpUp;
    private final Setting<Boolean> fullHeight;
    private final Setting<Boolean> disableOnJump;
    private final Mutable blockPos;
    private final Setting<Boolean> antiFastKill;
    private final SettingGroup sgGeneral;
    private boolean return_;
    private final Setting<Boolean> onlyWhenSneaking;
    private final Setting<Boolean> doubleHeight;
    private final Setting<Boolean> turnOff;
    private final Setting<Boolean> antiHead;
    private final Setting<Boolean> onlyOnGround;

    private void setBlockPos(int n, int n2, int n3) {
        this.blockPos.set(this.mc.player.getX() + (double)n, this.mc.player.getY() + (double)n2, this.mc.player.getZ() + (double)n3);
    }

    public Surround() {
        super(Categories.Combat, "surround", "Custom Surround.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.doubleHeight = this.sgGeneral.add(new BoolSetting.Builder().name("double-height").description("Places obsidian on top of the original surround blocks to prevent people from face-placing you.").defaultValue(false).build());
        this.fullHeight = this.sgGeneral.add(new BoolSetting.Builder().name("full-mode").description("Full Mode for Surround. Don't have fake-blocks!").defaultValue(false).build());
        this.antiHead = this.sgGeneral.add(new BoolSetting.Builder().name("anti-crystal-head").description("Anti Crystal Head.").defaultValue(false).build());
        this.antiFastKill = this.sgGeneral.add(new BoolSetting.Builder().name("anti-fast-kill").description("Anti FastKill.").defaultValue(false).build());
        this.HelpUp = this.sgGeneral.add(new BoolSetting.Builder().name("help-up").description("Help Up").defaultValue(false).build());
        this.onlyOnGround = this.sgGeneral.add(new BoolSetting.Builder().name("only-on-ground").description("Works only when you standing on blocks.").defaultValue(true).build());
        this.onlyWhenSneaking = this.sgGeneral.add(new BoolSetting.Builder().name("only-when-sneaking").description("Places blocks only after sneaking.").defaultValue(false).build());
        this.turnOff = this.sgGeneral.add(new BoolSetting.Builder().name("turn-off").description("Toggles off when all blocks are placed.").defaultValue(false).build());
        this.center = this.sgGeneral.add(new BoolSetting.Builder().name("center").description("Teleports you to the center of the block.").defaultValue(true).build());
        this.disableOnJump = this.sgGeneral.add(new BoolSetting.Builder().name("disable-on-jump").description("Automatically disables when you jump.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the obsidian being placed.").defaultValue(true).build());
        this.blockPos = new Mutable();
    }

    private int findSlot() {
        for (int i = 0; i < 9; ++i) {
            Item Item2 = this.mc.player.inventory.getStack(i).getItem();
            if (!(Item2 instanceof BlockItem) || Item2 != Items.OBSIDIAN) continue;
            return i;
        }
        return -1;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        boolean bl;
        boolean bl2;
        boolean bl3;
        boolean bl4;
        boolean bl5;
        boolean bl6;
        boolean bl7;
        if (this.disableOnJump.get().booleanValue() && (this.mc.options.keyJump.isPressed() || this.mc.player.input.jumping) || this.mc.player.prevY < this.mc.player.getY()) {
            this.toggle();
            return;
        }
        if (this.onlyOnGround.get().booleanValue() && !this.mc.player.isOnGround()) {
            return;
        }
        if (this.onlyWhenSneaking.get().booleanValue() && !this.mc.options.keySneak.isPressed()) {
            return;
        }
        this.return_ = false;
        boolean bl8 = this.place(0, -1, 0);
        if (this.return_) {
            return;
        }
        boolean bl9 = this.place(1, 0, 0);
        if (this.return_) {
            return;
        }
        boolean bl10 = this.place(-1, 0, 0);
        if (this.return_) {
            return;
        }
        boolean bl11 = this.place(0, 0, 1);
        if (this.return_) {
            return;
        }
        boolean bl12 = this.place(0, 0, -1);
        if (this.return_) {
            return;
        }
        boolean bl13 = false;
        if (this.doubleHeight.get().booleanValue()) {
            bl7 = this.place(1, 1, 0);
            if (this.return_) {
                return;
            }
            bl6 = this.place(-1, 1, 0);
            if (this.return_) {
                return;
            }
            bl5 = this.place(0, 1, 1);
            if (this.return_) {
                return;
            }
            bl4 = this.place(0, 1, -1);
            if (this.return_) {
                return;
            }
            if (bl7 && bl6 && bl5 && bl4) {
                bl13 = true;
            }
        }
        bl7 = false;
        if (this.fullHeight.get().booleanValue()) {
            bl6 = this.place(0, -1, 0);
            if (this.return_) {
                return;
            }
            bl5 = this.place(-1, -1, 0);
            if (this.return_) {
                return;
            }
            bl4 = this.place(0, -1, -1);
            if (this.return_) {
                return;
            }
            bl3 = this.place(1, -1, 0);
            if (this.return_) {
                return;
            }
            bl2 = this.place(0, -1, 1);
            if (this.return_) {
                return;
            }
            bl = this.place(1, 1, 0);
            if (this.return_) {
                return;
            }
            boolean bl14 = this.place(-1, 1, 0);
            if (this.return_) {
                return;
            }
            boolean bl15 = this.place(0, 1, 1);
            if (this.return_) {
                return;
            }
            boolean bl16 = this.place(0, 1, -1);
            if (this.return_) {
                return;
            }
            boolean bl17 = this.place(1, 0, 1);
            if (this.return_) {
                return;
            }
            boolean bl18 = this.place(-1, 0, -1);
            if (this.return_) {
                return;
            }
            boolean bl19 = this.place(1, 0, -1);
            if (this.return_) {
                return;
            }
            boolean bl20 = this.place(-1, 0, 1);
            if (this.return_) {
                return;
            }
            boolean bl21 = this.place(-2, 0, 0);
            if (this.return_) {
                return;
            }
            boolean bl22 = this.place(0, 0, -2);
            if (this.return_) {
                return;
            }
            boolean bl23 = this.place(2, 0, 0);
            if (this.return_) {
                return;
            }
            boolean bl24 = this.place(0, 0, 2);
            if (this.return_) {
                return;
            }
            boolean bl25 = this.place(0, 2, 0);
            if (this.return_) {
                return;
            }
            if (bl6 && bl5 && bl4 && bl3 && bl2 && bl && bl14 && bl15 && bl16 && bl17 && bl18 && bl19 && bl20 && bl21 && bl22 && bl23 && bl24 && bl25) {
                bl7 = true;
            }
        }
        bl6 = false;
        if (this.antiHead.get().booleanValue()) {
            bl5 = this.place(0, 3, 0);
            if (this.return_) {
                return;
            }
            if (bl5) {
                bl6 = true;
            }
        }
        bl5 = false;
        if (this.antiFastKill.get().booleanValue()) {
            bl4 = this.place(0, 2, 1);
            if (this.return_) {
                return;
            }
            bl3 = this.place(-1, 2, 0);
            if (this.return_) {
                return;
            }
            bl2 = this.place(0, 2, -1);
            if (this.return_) {
                return;
            }
            bl = this.place(1, 2, 0);
            if (this.return_) {
                return;
            }
            if (bl4 && bl3 && bl2 && bl) {
                bl5 = true;
            }
        }
        bl4 = false;
        if (this.HelpUp.get().booleanValue() && bl13) {
            bl3 = this.place(1, 2, 0);
            if (this.return_) {
                return;
            }
            if (bl3) {
                bl4 = true;
            }
        }
        if (this.turnOff.get().booleanValue() && bl8 && bl9 && bl10 && bl11 && bl12) {
            if (bl13 || !this.doubleHeight.get().booleanValue()) {
                this.toggle();
            }
            if (bl7 || !this.fullHeight.get().booleanValue()) {
                this.toggle();
            }
            if (bl6 || !this.antiHead.get().booleanValue()) {
                this.toggle();
            }
            if (bl5 || !this.antiFastKill.get().booleanValue()) {
                this.toggle();
            }
            if (bl4 || !this.HelpUp.get().booleanValue()) {
                this.toggle();
            }
        }
    }

    private boolean place(int n, int n2, int n3) {
        this.setBlockPos(n, n2, n3);
        BlockState BlockState2 = this.mc.world.getBlockState((BlockPos)this.blockPos);
        if (!BlockState2.getMaterial().isReplaceable()) {
            return true;
        }
        int n4 = this.findSlot();
        if (BlockUtils.place((BlockPos)this.blockPos, Hand.MAIN_HAND, n4, this.rotate.get(), 100, true)) {
            this.return_ = true;
        }
        return false;
    }

    @Override
    public void onActivate() {
        if (this.center.get().booleanValue()) {
            PlayerUtils.centerPlayer();
        }
    }
}

