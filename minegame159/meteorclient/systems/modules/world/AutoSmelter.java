/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.screen.AbstractFurnaceScreenHandler
 *  net.minecraft.screen.slot.Slot
 */
package minegame159.meteorclient.systems.modules.world;

import minegame159.meteorclient.mixin.AbstractFurnaceScreenHandlerAccessor;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.slot.Slot;

public class AutoSmelter
extends Module {
    private /* synthetic */ boolean waitingForItemsToSmelt;
    private /* synthetic */ int timer;
    private /* synthetic */ boolean first;
    private /* synthetic */ int step;

    @Override
    public void onActivate() {
        llIlIlIIllIlll.first = true;
        llIlIlIIllIlll.waitingForItemsToSmelt = false;
    }

    private boolean insertItems(AbstractFurnaceScreenHandler llIlIlIIlIIllI) {
        if (!((Slot)llIlIlIIlIIllI.slots.get(0)).getStack().isEmpty()) {
            return true;
        }
        int llIlIlIIlIIlIl = -1;
        for (int llIlIlIIlIlIII = 3; llIlIlIIlIlIII < llIlIlIIlIIllI.slots.size(); ++llIlIlIIlIlIII) {
            if (!((AbstractFurnaceScreenHandlerAccessor)llIlIlIIlIIllI).isSmeltable(((Slot)llIlIlIIlIIllI.slots.get(llIlIlIIlIlIII)).getStack())) continue;
            llIlIlIIlIIlIl = llIlIlIIlIlIII;
            break;
        }
        if (llIlIlIIlIIlIl == -1) {
            AutoSmelter llIlIlIIlIIlll;
            ChatUtils.moduleError(llIlIlIIlIIlll, "You do not have any items in your inventory that can be smelted... disabling.", new Object[0]);
            llIlIlIIlIIlll.toggle();
            return true;
        }
        InvUtils.move().fromId(llIlIlIIlIIlIl).toId(0);
        return false;
    }

    public void onFurnaceClose() {
        llIlIlIIllIIll.first = true;
        llIlIlIIllIIll.waitingForItemsToSmelt = false;
    }

    private boolean checkFuel(AbstractFurnaceScreenHandler llIlIlIIIllIIl) {
        if (llIlIlIIIllIIl.getFuelProgress() <= 1 && !((AbstractFurnaceScreenHandlerAccessor)llIlIlIIIllIIl).isFuel(((Slot)llIlIlIIIllIIl.slots.get(1)).getStack())) {
            AutoSmelter llIlIlIIIllIlI;
            if (!((Slot)llIlIlIIIllIIl.slots.get(1)).getStack().isEmpty()) {
                InvUtils.quickMove().slotId(1);
                if (!((Slot)llIlIlIIIllIIl.slots.get(1)).getStack().isEmpty()) {
                    ChatUtils.moduleError(llIlIlIIIllIlI, "Your inventory is currently full... disabling.", new Object[0]);
                    llIlIlIIIllIlI.toggle();
                    return true;
                }
            }
            int llIlIlIIIllIll = -1;
            for (int llIlIlIIIlllII = 3; llIlIlIIIlllII < llIlIlIIIllIIl.slots.size(); ++llIlIlIIIlllII) {
                if (!((AbstractFurnaceScreenHandlerAccessor)llIlIlIIIllIIl).isFuel(((Slot)llIlIlIIIllIIl.slots.get(llIlIlIIIlllII)).getStack())) continue;
                llIlIlIIIllIll = llIlIlIIIlllII;
                break;
            }
            if (llIlIlIIIllIll == -1) {
                ChatUtils.moduleError(llIlIlIIIllIlI, "You do not have any fuel in your inventory... disabling.", new Object[0]);
                llIlIlIIIllIlI.toggle();
                return true;
            }
            InvUtils.move().fromId(llIlIlIIIllIll).toId(1);
        }
        return false;
    }

    public void tick(AbstractFurnaceScreenHandler llIlIlIIlIllll) {
        AutoSmelter llIlIlIIlIlllI;
        ++llIlIlIIlIlllI.timer;
        if (!llIlIlIIlIlllI.first) {
            llIlIlIIlIlllI.first = true;
            llIlIlIIlIlllI.step = 0;
            llIlIlIIlIlllI.timer = 0;
        }
        if (llIlIlIIlIlllI.checkFuel(llIlIlIIlIllll)) {
            return;
        }
        if (llIlIlIIlIllll.getCookProgress() != 0 || llIlIlIIlIlllI.timer < 5) {
            return;
        }
        if (llIlIlIIlIlllI.step == 0) {
            if (llIlIlIIlIlllI.takeResults(llIlIlIIlIllll)) {
                return;
            }
            ++llIlIlIIlIlllI.step;
            llIlIlIIlIlllI.timer = 0;
        } else if (llIlIlIIlIlllI.step == 1) {
            if (llIlIlIIlIlllI.waitingForItemsToSmelt) {
                if (((Slot)llIlIlIIlIllll.slots.get(0)).getStack().isEmpty()) {
                    llIlIlIIlIlllI.step = 0;
                    llIlIlIIlIlllI.timer = 0;
                    llIlIlIIlIlllI.waitingForItemsToSmelt = false;
                }
                return;
            }
            if (llIlIlIIlIlllI.insertItems(llIlIlIIlIllll)) {
                return;
            }
            llIlIlIIlIlllI.waitingForItemsToSmelt = true;
        }
    }

    public AutoSmelter() {
        super(Categories.World, "auto-smelter", "Automatically smelts all items in your inventory that can be smelted.");
        AutoSmelter llIlIlIIlllIlI;
    }

    private boolean takeResults(AbstractFurnaceScreenHandler llIlIlIIIIllll) {
        InvUtils.quickMove().slotId(2);
        if (!((Slot)llIlIlIIIIllll.slots.get(2)).getStack().isEmpty()) {
            AutoSmelter llIlIlIIIlIIlI;
            ChatUtils.moduleError(llIlIlIIIlIIlI, "Your inventory is full... disabling.", new Object[0]);
            llIlIlIIIlIIlI.toggle();
            return true;
        }
        return false;
    }
}

