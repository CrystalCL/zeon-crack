/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.block.BlockState
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
    private final /* synthetic */ Setting<Boolean> disableOnJump;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Mutable blockPos;
    private /* synthetic */ boolean return_;
    private final /* synthetic */ Setting<Boolean> antiHead;
    private final /* synthetic */ Setting<Boolean> fullHeight;
    private final /* synthetic */ Setting<Boolean> doubleHeight;
    private final /* synthetic */ Setting<Boolean> onlyWhenSneaking;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Boolean> antiFastKill;
    private final /* synthetic */ Setting<Boolean> turnOff;
    private final /* synthetic */ Setting<Boolean> HelpUp;
    private final /* synthetic */ Setting<Boolean> onlyOnGround;
    private final /* synthetic */ Setting<Boolean> center;

    private void setBlockPos(int llllllllIIIlll, int llllllllIIIllI, int llllllllIIIlIl) {
        Surround llllllllIIlIII;
        llllllllIIlIII.blockPos.set(llllllllIIlIII.mc.player.getX() + (double)llllllllIIIlll, llllllllIIlIII.mc.player.getY() + (double)llllllllIIIllI, llllllllIIlIII.mc.player.getZ() + (double)llllllllIIIlIl);
    }

    public Surround() {
        super(Categories.Combat, "surround", "Custom Surround.");
        Surround lIIIIIIIlIIIIll;
        lIIIIIIIlIIIIll.sgGeneral = lIIIIIIIlIIIIll.settings.getDefaultGroup();
        lIIIIIIIlIIIIll.doubleHeight = lIIIIIIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("double-height").description("Places obsidian on top of the original surround blocks to prevent people from face-placing you.").defaultValue(false).build());
        lIIIIIIIlIIIIll.fullHeight = lIIIIIIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("full-mode").description("Full Mode for Surround. Don't have fake-blocks!").defaultValue(false).build());
        lIIIIIIIlIIIIll.antiHead = lIIIIIIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("anti-crystal-head").description("Anti Crystal Head.").defaultValue(false).build());
        lIIIIIIIlIIIIll.antiFastKill = lIIIIIIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("anti-fast-kill").description("Anti FastKill.").defaultValue(false).build());
        lIIIIIIIlIIIIll.HelpUp = lIIIIIIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("help-up").description("Help Up").defaultValue(false).build());
        lIIIIIIIlIIIIll.onlyOnGround = lIIIIIIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("only-on-ground").description("Works only when you standing on blocks.").defaultValue(true).build());
        lIIIIIIIlIIIIll.onlyWhenSneaking = lIIIIIIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("only-when-sneaking").description("Places blocks only after sneaking.").defaultValue(false).build());
        lIIIIIIIlIIIIll.turnOff = lIIIIIIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("turn-off").description("Toggles off when all blocks are placed.").defaultValue(false).build());
        lIIIIIIIlIIIIll.center = lIIIIIIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("center").description("Teleports you to the center of the block.").defaultValue(true).build());
        lIIIIIIIlIIIIll.disableOnJump = lIIIIIIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("disable-on-jump").description("Automatically disables when you jump.").defaultValue(true).build());
        lIIIIIIIlIIIIll.rotate = lIIIIIIIlIIIIll.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the obsidian being placed.").defaultValue(true).build());
        lIIIIIIIlIIIIll.blockPos = new Mutable();
    }

    @Override
    public void onActivate() {
        Surround lIIIIIIIlIIIIII;
        if (lIIIIIIIlIIIIII.center.get().booleanValue()) {
            PlayerUtils.centerPlayer();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre lIIIIIIIIIIIlll) {
        Surround llllllllllllII;
        if (llllllllllllII.disableOnJump.get().booleanValue() && (llllllllllllII.mc.options.keyJump.isPressed() || llllllllllllII.mc.player.input.jumping) || llllllllllllII.mc.player.prevY < llllllllllllII.mc.player.getY()) {
            llllllllllllII.toggle();
            return;
        }
        if (llllllllllllII.onlyOnGround.get().booleanValue() && !llllllllllllII.mc.player.isOnGround()) {
            return;
        }
        if (llllllllllllII.onlyWhenSneaking.get().booleanValue() && !llllllllllllII.mc.options.keySneak.isPressed()) {
            return;
        }
        llllllllllllII.return_ = false;
        boolean lIIIIIIIIIIIllI = llllllllllllII.place(0, -1, 0);
        if (llllllllllllII.return_) {
            return;
        }
        boolean lIIIIIIIIIIIlIl = llllllllllllII.place(1, 0, 0);
        if (llllllllllllII.return_) {
            return;
        }
        boolean lIIIIIIIIIIIlII = llllllllllllII.place(-1, 0, 0);
        if (llllllllllllII.return_) {
            return;
        }
        boolean lIIIIIIIIIIIIll = llllllllllllII.place(0, 0, 1);
        if (llllllllllllII.return_) {
            return;
        }
        boolean lIIIIIIIIIIIIlI = llllllllllllII.place(0, 0, -1);
        if (llllllllllllII.return_) {
            return;
        }
        boolean lIIIIIIIIIIIIIl = false;
        if (llllllllllllII.doubleHeight.get().booleanValue()) {
            boolean lIIIIIIIIlIIlII = llllllllllllII.place(1, 1, 0);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIlIIIll = llllllllllllII.place(-1, 1, 0);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIlIIIlI = llllllllllllII.place(0, 1, 1);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIlIIIIl = llllllllllllII.place(0, 1, -1);
            if (llllllllllllII.return_) {
                return;
            }
            if (lIIIIIIIIlIIlII && lIIIIIIIIlIIIll && lIIIIIIIIlIIIlI && lIIIIIIIIlIIIIl) {
                lIIIIIIIIIIIIIl = true;
            }
        }
        boolean lIIIIIIIIIIIIII = false;
        if (llllllllllllII.fullHeight.get().booleanValue()) {
            boolean lIIIIIIIIlIIIII = llllllllllllII.place(0, -1, 0);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIlllll = llllllllllllII.place(-1, -1, 0);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIllllI = llllllllllllII.place(0, -1, -1);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIlllIl = llllllllllllII.place(1, -1, 0);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIlllII = llllllllllllII.place(0, -1, 1);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIllIll = llllllllllllII.place(1, 1, 0);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIllIlI = llllllllllllII.place(-1, 1, 0);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIllIIl = llllllllllllII.place(0, 1, 1);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIllIII = llllllllllllII.place(0, 1, -1);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIlIlll = llllllllllllII.place(1, 0, 1);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIlIllI = llllllllllllII.place(-1, 0, -1);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIlIlIl = llllllllllllII.place(1, 0, -1);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIlIlII = llllllllllllII.place(-1, 0, 1);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIlIIll = llllllllllllII.place(-2, 0, 0);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIlIIlI = llllllllllllII.place(0, 0, -2);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIlIIIl = llllllllllllII.place(2, 0, 0);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIlIIII = llllllllllllII.place(0, 0, 2);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIIllll = llllllllllllII.place(0, 2, 0);
            if (llllllllllllII.return_) {
                return;
            }
            if (lIIIIIIIIlIIIII && lIIIIIIIIIlllll && lIIIIIIIIIllllI && lIIIIIIIIIlllIl && lIIIIIIIIIlllII && lIIIIIIIIIllIll && lIIIIIIIIIllIlI && lIIIIIIIIIllIIl && lIIIIIIIIIllIII && lIIIIIIIIIlIlll && lIIIIIIIIIlIllI && lIIIIIIIIIlIlIl && lIIIIIIIIIlIlII && lIIIIIIIIIlIIll && lIIIIIIIIIlIIlI && lIIIIIIIIIlIIIl && lIIIIIIIIIlIIII && lIIIIIIIIIIllll) {
                lIIIIIIIIIIIIII = true;
            }
        }
        boolean llllllllllllll = false;
        if (llllllllllllII.antiHead.get().booleanValue()) {
            boolean lIIIIIIIIIIlllI = llllllllllllII.place(0, 3, 0);
            if (llllllllllllII.return_) {
                return;
            }
            if (lIIIIIIIIIIlllI) {
                llllllllllllll = true;
            }
        }
        boolean lllllllllllllI = false;
        if (llllllllllllII.antiFastKill.get().booleanValue()) {
            boolean lIIIIIIIIIIllIl = llllllllllllII.place(0, 2, 1);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIIllII = llllllllllllII.place(-1, 2, 0);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIIlIll = llllllllllllII.place(0, 2, -1);
            if (llllllllllllII.return_) {
                return;
            }
            boolean lIIIIIIIIIIlIlI = llllllllllllII.place(1, 2, 0);
            if (llllllllllllII.return_) {
                return;
            }
            if (lIIIIIIIIIIllIl && lIIIIIIIIIIllII && lIIIIIIIIIIlIll && lIIIIIIIIIIlIlI) {
                lllllllllllllI = true;
            }
        }
        boolean llllllllllllIl = false;
        if (llllllllllllII.HelpUp.get().booleanValue() && lIIIIIIIIIIIIIl) {
            boolean lIIIIIIIIIIlIIl = llllllllllllII.place(1, 2, 0);
            if (llllllllllllII.return_) {
                return;
            }
            if (lIIIIIIIIIIlIIl) {
                llllllllllllIl = true;
            }
        }
        if (llllllllllllII.turnOff.get().booleanValue() && lIIIIIIIIIIIllI && lIIIIIIIIIIIlIl && lIIIIIIIIIIIlII && lIIIIIIIIIIIIll && lIIIIIIIIIIIIlI) {
            if (lIIIIIIIIIIIIIl || !llllllllllllII.doubleHeight.get().booleanValue()) {
                llllllllllllII.toggle();
            }
            if (lIIIIIIIIIIIIII || !llllllllllllII.fullHeight.get().booleanValue()) {
                llllllllllllII.toggle();
            }
            if (llllllllllllll || !llllllllllllII.antiHead.get().booleanValue()) {
                llllllllllllII.toggle();
            }
            if (lllllllllllllI || !llllllllllllII.antiFastKill.get().booleanValue()) {
                llllllllllllII.toggle();
            }
            if (llllllllllllIl || !llllllllllllII.HelpUp.get().booleanValue()) {
                llllllllllllII.toggle();
            }
        }
    }

    private int findSlot() {
        for (int llllllllIIIIII = 0; llllllllIIIIII < 9; ++llllllllIIIIII) {
            Surround lllllllIlllllI;
            Item llllllllIIIIIl = lllllllIlllllI.mc.player.inventory.getStack(llllllllIIIIII).getItem();
            if (!(llllllllIIIIIl instanceof BlockItem) || llllllllIIIIIl != Items.OBSIDIAN) continue;
            return llllllllIIIIII;
        }
        return -1;
    }

    private boolean place(int llllllllIlIlIl, int llllllllIlIlII, int llllllllIllIIl) {
        Surround llllllllIlllII;
        llllllllIlllII.setBlockPos(llllllllIlIlIl, llllllllIlIlII, llllllllIllIIl);
        BlockState llllllllIllIII = llllllllIlllII.mc.world.getBlockState((BlockPos)llllllllIlllII.blockPos);
        if (!llllllllIllIII.getMaterial().isReplaceable()) {
            return true;
        }
        int llllllllIlIlll = llllllllIlllII.findSlot();
        if (BlockUtils.place((BlockPos)llllllllIlllII.blockPos, Hand.MAIN_HAND, llllllllIlIlll, llllllllIlllII.rotate.get(), 100, true)) {
            llllllllIlllII.return_ = true;
        }
        return false;
    }
}

