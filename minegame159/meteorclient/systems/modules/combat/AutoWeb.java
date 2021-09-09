/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Items
 *  net.minecraft.util.math.BlockPos
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

public class AutoWeb
extends Module {
    private final /* synthetic */ Setting<Boolean> rotate;
    private /* synthetic */ PlayerEntity target;
    private final /* synthetic */ Setting<Boolean> doubles;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Double> range;

    @EventHandler
    private void onTick(TickEvent.Pre llllllllllllllllIlIlllIIlllIlIII) {
        AutoWeb llllllllllllllllIlIlllIIlllIIllI;
        int llllllllllllllllIlIlllIIlllIIlll = InvUtils.findItemInHotbar(Items.COBWEB);
        if (llllllllllllllllIlIlllIIlllIIlll == -1) {
            return;
        }
        if (llllllllllllllllIlIlllIIlllIIllI.target != null && ((double)llllllllllllllllIlIlllIIlllIIllI.mc.player.distanceTo((Entity)llllllllllllllllIlIlllIIlllIIllI.target) > llllllllllllllllIlIlllIIlllIIllI.range.get() || !llllllllllllllllIlIlllIIlllIIllI.target.isAlive())) {
            llllllllllllllllIlIlllIIlllIIllI.target = null;
        }
        for (PlayerEntity llllllllllllllllIlIlllIIlllIllII : llllllllllllllllIlIlllIIlllIIllI.mc.world.getPlayers()) {
            if (llllllllllllllllIlIlllIIlllIllII == llllllllllllllllIlIlllIIlllIIllI.mc.player || !Friends.get().attack(llllllllllllllllIlIlllIIlllIllII) || !llllllllllllllllIlIlllIIlllIllII.isAlive() || (double)llllllllllllllllIlIlllIIlllIIllI.mc.player.distanceTo((Entity)llllllllllllllllIlIlllIIlllIllII) > llllllllllllllllIlIlllIIlllIIllI.range.get()) continue;
            if (llllllllllllllllIlIlllIIlllIIllI.target == null) {
                llllllllllllllllIlIlllIIlllIIllI.target = llllllllllllllllIlIlllIIlllIllII;
                continue;
            }
            if (!(llllllllllllllllIlIlllIIlllIIllI.mc.player.distanceTo((Entity)llllllllllllllllIlIlllIIlllIIllI.target) > llllllllllllllllIlIlllIIlllIIllI.mc.player.distanceTo((Entity)llllllllllllllllIlIlllIIlllIllII))) continue;
            llllllllllllllllIlIlllIIlllIIllI.target = llllllllllllllllIlIlllIIlllIllII;
        }
        if (llllllllllllllllIlIlllIIlllIIllI.target == null) {
            for (FakePlayerEntity llllllllllllllllIlIlllIIlllIlIll : FakePlayerUtils.getPlayers().keySet()) {
                if (llllllllllllllllIlIlllIIlllIlIll.getHealth() <= 0.0f || !Friends.get().attack((PlayerEntity)llllllllllllllllIlIlllIIlllIlIll) || !llllllllllllllllIlIlllIIlllIlIll.isAlive()) continue;
                if (llllllllllllllllIlIlllIIlllIIllI.target == null) {
                    llllllllllllllllIlIlllIIlllIIllI.target = llllllllllllllllIlIlllIIlllIlIll;
                    continue;
                }
                if (!(llllllllllllllllIlIlllIIlllIIllI.mc.player.distanceTo((Entity)llllllllllllllllIlIlllIIlllIlIll) < llllllllllllllllIlIlllIIlllIIllI.mc.player.distanceTo((Entity)llllllllllllllllIlIlllIIlllIIllI.target))) continue;
                llllllllllllllllIlIlllIIlllIIllI.target = llllllllllllllllIlIlllIIlllIlIll;
            }
        }
        if (llllllllllllllllIlIlllIIlllIIllI.target != null) {
            BlockPos llllllllllllllllIlIlllIIlllIlIlI = llllllllllllllllIlIlllIIlllIIllI.target.getBlockPos();
            BlockUtils.place(llllllllllllllllIlIlllIIlllIlIlI, Hand.MAIN_HAND, llllllllllllllllIlIlllIIlllIIlll, llllllllllllllllIlIlllIIlllIIllI.rotate.get(), 0, false);
            if (llllllllllllllllIlIlllIIlllIIllI.doubles.get().booleanValue()) {
                llllllllllllllllIlIlllIIlllIlIlI = llllllllllllllllIlIlllIIlllIlIlI.add(0, 1, 0);
                BlockUtils.place(llllllllllllllllIlIlllIIlllIlIlI, Hand.MAIN_HAND, InvUtils.findItemInHotbar(Items.COBWEB), llllllllllllllllIlIlllIIlllIIllI.rotate.get(), 0, false);
            }
        }
    }

    public AutoWeb() {
        super(Categories.Combat, "auto-web", "Automatically places webs on other players.");
        AutoWeb llllllllllllllllIlIlllIIllllIIlI;
        llllllllllllllllIlIlllIIllllIIlI.sgGeneral = llllllllllllllllIlIlllIIllllIIlI.settings.getDefaultGroup();
        llllllllllllllllIlIlllIIllllIIlI.range = llllllllllllllllIlIlllIIllllIIlI.sgGeneral.add(new DoubleSetting.Builder().name("range").description("The maximum distance to be able to place webs.").defaultValue(4.0).min(0.0).build());
        llllllllllllllllIlIlllIIllllIIlI.doubles = llllllllllllllllIlIlllIIllllIIlI.sgGeneral.add(new BoolSetting.Builder().name("doubles").description("Places webs in the target's upper hitbox as well as the lower hitbox.").defaultValue(false).build());
        llllllllllllllllIlIlllIIllllIIlI.rotate = llllllllllllllllIlIlllIIllllIIlI.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Rotates towards the webs when placing.").defaultValue(true).build());
        llllllllllllllllIlIlllIIllllIIlI.target = null;
    }
}

