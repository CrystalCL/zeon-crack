/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.passive.PigEntity
 *  net.minecraft.entity.passive.DonkeyEntity
 *  net.minecraft.entity.passive.HorseEntity
 *  net.minecraft.entity.passive.MuleEntity
 *  net.minecraft.entity.passive.LlamaEntity
 *  net.minecraft.entity.mob.SkeletonHorseEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.entity.vehicle.BoatEntity
 *  net.minecraft.entity.vehicle.MinecartEntity
 *  net.minecraft.item.SpawnEggItem
 */
package minegame159.meteorclient.systems.modules.world;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.DonkeyEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.MuleEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.SpawnEggItem;

public class AutoMount
extends Module {
    private final /* synthetic */ SettingGroup sgMount;
    private final /* synthetic */ Setting<Boolean> checkSaddle;
    private final /* synthetic */ Setting<Boolean> horses;
    private final /* synthetic */ Setting<Boolean> donkeys;
    private final /* synthetic */ Setting<Boolean> skeletonHorse;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> pigs;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Boolean> mules;
    private final /* synthetic */ Setting<Boolean> llamas;
    private final /* synthetic */ Setting<Boolean> minecarts;
    private final /* synthetic */ Setting<Boolean> boats;

    public AutoMount() {
        super(Categories.World, "auto-mount", "Automatically mounts entities.");
        AutoMount llIIlIllllIlIII;
        llIIlIllllIlIII.sgGeneral = llIIlIllllIlIII.settings.getDefaultGroup();
        llIIlIllllIlIII.sgMount = llIIlIllllIlIII.settings.createGroup("Mount");
        llIIlIllllIlIII.checkSaddle = llIIlIllllIlIII.sgGeneral.add(new BoolSetting.Builder().name("check-saddle").description("Checks if the entity contains a saddle before mounting.").defaultValue(false).build());
        llIIlIllllIlIII.rotate = llIIlIllllIlIII.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Faces the entity you mount.").defaultValue(true).build());
        llIIlIllllIlIII.horses = llIIlIllllIlIII.sgMount.add(new BoolSetting.Builder().name("horse").description("Horse").defaultValue(false).build());
        llIIlIllllIlIII.donkeys = llIIlIllllIlIII.sgMount.add(new BoolSetting.Builder().name("donkey").description("Donkey").defaultValue(false).build());
        llIIlIllllIlIII.mules = llIIlIllllIlIII.sgMount.add(new BoolSetting.Builder().name("mule").description("Mule").defaultValue(false).build());
        llIIlIllllIlIII.skeletonHorse = llIIlIllllIlIII.sgMount.add(new BoolSetting.Builder().name("skeleton-horse").description("Skeleton Horse").defaultValue(false).build());
        llIIlIllllIlIII.llamas = llIIlIllllIlIII.sgMount.add(new BoolSetting.Builder().name("llama").description("Llama").defaultValue(false).build());
        llIIlIllllIlIII.pigs = llIIlIllllIlIII.sgMount.add(new BoolSetting.Builder().name("pig").description("Pig").defaultValue(false).build());
        llIIlIllllIlIII.boats = llIIlIllllIlIII.sgMount.add(new BoolSetting.Builder().name("boat").description("Boat").defaultValue(false).build());
        llIIlIllllIlIII.minecarts = llIIlIllllIlIII.sgMount.add(new BoolSetting.Builder().name("minecart").description("Minecart").defaultValue(false).build());
    }

    private void interact(Entity llIIlIlllIllIll) {
        AutoMount llIIlIlllIllIlI;
        if (llIIlIlllIllIlI.rotate.get().booleanValue()) {
            Rotations.rotate(Rotations.getYaw(llIIlIlllIllIll), Rotations.getPitch(llIIlIlllIllIll), -100, () -> {
                AutoMount llIIlIlllIlIlII;
                llIIlIlllIlIlII.mc.interactionManager.interactEntity((PlayerEntity)llIIlIlllIlIlII.mc.player, llIIlIlllIllIll, Hand.MAIN_HAND);
            });
        } else {
            llIIlIlllIllIlI.mc.interactionManager.interactEntity((PlayerEntity)llIIlIlllIllIlI.mc.player, llIIlIlllIllIll, Hand.MAIN_HAND);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre llIIlIllllIIIlI) {
        AutoMount llIIlIllllIIIll;
        if (llIIlIllllIIIll.mc.player.hasVehicle()) {
            return;
        }
        for (Entity llIIlIllllIIlII : llIIlIllllIIIll.mc.world.getEntities()) {
            if (llIIlIllllIIIll.mc.player.distanceTo(llIIlIllllIIlII) > 4.0f) continue;
            if (llIIlIllllIIIll.mc.player.getMainHandStack().getItem() instanceof SpawnEggItem) {
                return;
            }
            if (llIIlIllllIIIll.donkeys.get().booleanValue() && llIIlIllllIIlII instanceof DonkeyEntity && (!llIIlIllllIIIll.checkSaddle.get().booleanValue() || ((DonkeyEntity)llIIlIllllIIlII).isSaddled())) {
                llIIlIllllIIIll.interact(llIIlIllllIIlII);
                continue;
            }
            if (llIIlIllllIIIll.llamas.get().booleanValue() && llIIlIllllIIlII instanceof LlamaEntity) {
                llIIlIllllIIIll.interact(llIIlIllllIIlII);
                continue;
            }
            if (llIIlIllllIIIll.boats.get().booleanValue() && llIIlIllllIIlII instanceof BoatEntity) {
                llIIlIllllIIIll.interact(llIIlIllllIIlII);
                continue;
            }
            if (llIIlIllllIIIll.minecarts.get().booleanValue() && llIIlIllllIIlII instanceof MinecartEntity) {
                llIIlIllllIIIll.interact(llIIlIllllIIlII);
                continue;
            }
            if (llIIlIllllIIIll.horses.get().booleanValue() && llIIlIllllIIlII instanceof HorseEntity && (!llIIlIllllIIIll.checkSaddle.get().booleanValue() || ((HorseEntity)llIIlIllllIIlII).isSaddled())) {
                llIIlIllllIIIll.interact(llIIlIllllIIlII);
                continue;
            }
            if (llIIlIllllIIIll.pigs.get().booleanValue() && llIIlIllllIIlII instanceof PigEntity && ((PigEntity)llIIlIllllIIlII).isSaddled()) {
                llIIlIllllIIIll.interact(llIIlIllllIIlII);
                continue;
            }
            if (llIIlIllllIIIll.mules.get().booleanValue() && llIIlIllllIIlII instanceof MuleEntity && (!llIIlIllllIIIll.checkSaddle.get().booleanValue() || ((MuleEntity)llIIlIllllIIlII).isSaddled())) {
                llIIlIllllIIIll.interact(llIIlIllllIIlII);
                continue;
            }
            if (!llIIlIllllIIIll.skeletonHorse.get().booleanValue() || !(llIIlIllllIIlII instanceof SkeletonHorseEntity) || llIIlIllllIIIll.checkSaddle.get().booleanValue() && !((SkeletonHorseEntity)llIIlIllllIIlII).isSaddled()) continue;
            llIIlIllllIIIll.interact(llIIlIllllIIlII);
        }
    }
}

