/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> horses;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> donkeys;
    private final SettingGroup sgMount;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> skeletonHorse;
    private final Setting<Boolean> boats;
    private final Setting<Boolean> pigs;
    private final Setting<Boolean> checkSaddle;
    private final Setting<Boolean> mules;
    private final Setting<Boolean> llamas;
    private final Setting<Boolean> minecarts;

    public AutoMount() {
        super(Categories.World, "auto-mount", "Automatically mounts entities.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgMount = this.settings.createGroup("Mount");
        this.checkSaddle = this.sgGeneral.add(new BoolSetting.Builder().name("check-saddle").description("Checks if the entity contains a saddle before mounting.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Faces the entity you mount.").defaultValue(true).build());
        this.horses = this.sgMount.add(new BoolSetting.Builder().name("horse").description("Horse").defaultValue(false).build());
        this.donkeys = this.sgMount.add(new BoolSetting.Builder().name("donkey").description("Donkey").defaultValue(false).build());
        this.mules = this.sgMount.add(new BoolSetting.Builder().name("mule").description("Mule").defaultValue(false).build());
        this.skeletonHorse = this.sgMount.add(new BoolSetting.Builder().name("skeleton-horse").description("Skeleton Horse").defaultValue(false).build());
        this.llamas = this.sgMount.add(new BoolSetting.Builder().name("llama").description("Llama").defaultValue(false).build());
        this.pigs = this.sgMount.add(new BoolSetting.Builder().name("pig").description("Pig").defaultValue(false).build());
        this.boats = this.sgMount.add(new BoolSetting.Builder().name("boat").description("Boat").defaultValue(false).build());
        this.minecarts = this.sgMount.add(new BoolSetting.Builder().name("minecart").description("Minecart").defaultValue(false).build());
    }

    private void interact(Entity Entity2) {
        if (this.rotate.get().booleanValue()) {
            Rotations.rotate(Rotations.getYaw(Entity2), Rotations.getPitch(Entity2), -100, () -> this.lambda$interact$0(Entity2));
        } else {
            this.mc.interactionManager.interactEntity((PlayerEntity)this.mc.player, Entity2, Hand.MAIN_HAND);
        }
    }

    private void lambda$interact$0(Entity Entity2) {
        this.mc.interactionManager.interactEntity((PlayerEntity)this.mc.player, Entity2, Hand.MAIN_HAND);
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.mc.player.hasVehicle()) {
            return;
        }
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (this.mc.player.distanceTo(Entity2) > 4.0f) continue;
            if (this.mc.player.getMainHandStack().getItem() instanceof SpawnEggItem) {
                return;
            }
            if (this.donkeys.get().booleanValue() && Entity2 instanceof DonkeyEntity && (!this.checkSaddle.get().booleanValue() || ((DonkeyEntity)Entity2).isSaddled())) {
                this.interact(Entity2);
                continue;
            }
            if (this.llamas.get().booleanValue() && Entity2 instanceof LlamaEntity) {
                this.interact(Entity2);
                continue;
            }
            if (this.boats.get().booleanValue() && Entity2 instanceof BoatEntity) {
                this.interact(Entity2);
                continue;
            }
            if (this.minecarts.get().booleanValue() && Entity2 instanceof MinecartEntity) {
                this.interact(Entity2);
                continue;
            }
            if (this.horses.get().booleanValue() && Entity2 instanceof HorseEntity && (!this.checkSaddle.get().booleanValue() || ((HorseEntity)Entity2).isSaddled())) {
                this.interact(Entity2);
                continue;
            }
            if (this.pigs.get().booleanValue() && Entity2 instanceof PigEntity && ((PigEntity)Entity2).isSaddled()) {
                this.interact(Entity2);
                continue;
            }
            if (this.mules.get().booleanValue() && Entity2 instanceof MuleEntity && (!this.checkSaddle.get().booleanValue() || ((MuleEntity)Entity2).isSaddled())) {
                this.interact(Entity2);
                continue;
            }
            if (!this.skeletonHorse.get().booleanValue() || !(Entity2 instanceof SkeletonHorseEntity) || this.checkSaddle.get().booleanValue() && !((SkeletonHorseEntity)Entity2).isSaddled()) continue;
            this.interact(Entity2);
        }
    }
}

