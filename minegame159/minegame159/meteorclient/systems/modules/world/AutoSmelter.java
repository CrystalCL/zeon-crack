/*
 * Decompiled with CFR 0.151.
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
    private int timer;
    private int step;
    private boolean waitingForItemsToSmelt;
    private boolean first;

    private boolean takeResults(AbstractFurnaceScreenHandler AbstractFurnaceScreenHandler2) {
        InvUtils.quickMove().slotId(2);
        if (!((Slot)AbstractFurnaceScreenHandler2.slots.get(2)).getStack().isEmpty()) {
            ChatUtils.moduleError(this, "Your inventory is full... disabling.", new Object[0]);
            this.toggle();
            return true;
        }
        return false;
    }

    public AutoSmelter() {
        super(Categories.World, "auto-smelter", "Automatically smelts all items in your inventory that can be smelted.");
    }

    @Override
    public void onActivate() {
        this.first = true;
        this.waitingForItemsToSmelt = false;
    }

    public void tick(AbstractFurnaceScreenHandler AbstractFurnaceScreenHandler2) {
        ++this.timer;
        if (!this.first) {
            this.first = true;
            this.step = 0;
            this.timer = 0;
        }
        if (this.checkFuel(AbstractFurnaceScreenHandler2)) {
            return;
        }
        if (AbstractFurnaceScreenHandler2.getCookProgress() != 0 || this.timer < 5) {
            return;
        }
        if (this.step == 0) {
            if (this.takeResults(AbstractFurnaceScreenHandler2)) {
                return;
            }
            ++this.step;
            this.timer = 0;
        } else if (this.step == 1) {
            if (this.waitingForItemsToSmelt) {
                if (((Slot)AbstractFurnaceScreenHandler2.slots.get(0)).getStack().isEmpty()) {
                    this.step = 0;
                    this.timer = 0;
                    this.waitingForItemsToSmelt = false;
                }
                return;
            }
            if (this.insertItems(AbstractFurnaceScreenHandler2)) {
                return;
            }
            this.waitingForItemsToSmelt = true;
        }
    }

    private boolean checkFuel(AbstractFurnaceScreenHandler AbstractFurnaceScreenHandler2) {
        if (AbstractFurnaceScreenHandler2.getFuelProgress() <= 1 && !((AbstractFurnaceScreenHandlerAccessor)AbstractFurnaceScreenHandler2).isFuel(((Slot)AbstractFurnaceScreenHandler2.slots.get(1)).getStack())) {
            if (!((Slot)AbstractFurnaceScreenHandler2.slots.get(1)).getStack().isEmpty()) {
                InvUtils.quickMove().slotId(1);
                if (!((Slot)AbstractFurnaceScreenHandler2.slots.get(1)).getStack().isEmpty()) {
                    ChatUtils.moduleError(this, "Your inventory is currently full... disabling.", new Object[0]);
                    this.toggle();
                    return true;
                }
            }
            int n = -1;
            for (int i = 3; i < AbstractFurnaceScreenHandler2.slots.size(); ++i) {
                if (!((AbstractFurnaceScreenHandlerAccessor)AbstractFurnaceScreenHandler2).isFuel(((Slot)AbstractFurnaceScreenHandler2.slots.get(i)).getStack())) continue;
                n = i;
                break;
            }
            if (n == -1) {
                ChatUtils.moduleError(this, "You do not have any fuel in your inventory... disabling.", new Object[0]);
                this.toggle();
                return true;
            }
            InvUtils.move().fromId(n).toId(1);
        }
        return false;
    }

    private boolean insertItems(AbstractFurnaceScreenHandler AbstractFurnaceScreenHandler2) {
        if (!((Slot)AbstractFurnaceScreenHandler2.slots.get(0)).getStack().isEmpty()) {
            return true;
        }
        int n = -1;
        for (int i = 3; i < AbstractFurnaceScreenHandler2.slots.size(); ++i) {
            if (!((AbstractFurnaceScreenHandlerAccessor)AbstractFurnaceScreenHandler2).isSmeltable(((Slot)AbstractFurnaceScreenHandler2.slots.get(i)).getStack())) continue;
            n = i;
            break;
        }
        if (n == -1) {
            ChatUtils.moduleError(this, "You do not have any items in your inventory that can be smelted... disabling.", new Object[0]);
            this.toggle();
            return true;
        }
        InvUtils.move().fromId(n).toId(0);
        return false;
    }

    public void onFurnaceClose() {
        this.first = true;
        this.waitingForItemsToSmelt = false;
    }
}

