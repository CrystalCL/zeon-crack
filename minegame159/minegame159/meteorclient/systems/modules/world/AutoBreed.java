/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;

public class AutoBreed
extends Module {
    private final Setting<Boolean> ignoreBabies;
    private final Setting<Hand> hand;
    private final List<Entity> animalsFed;
    private final Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final SettingGroup sgGeneral;
    private final Setting<Double> range;

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (!(Entity2 instanceof AnimalEntity)) continue;
            AnimalEntity AnimalEntity2 = (AnimalEntity)Entity2;
            if (!this.entities.get().getBoolean((Object)AnimalEntity2.getType()) || AnimalEntity2.isBaby() && !this.ignoreBabies.get().booleanValue() || this.animalsFed.contains(AnimalEntity2) || (double)this.mc.player.distanceTo((Entity)AnimalEntity2) > this.range.get() || !AnimalEntity2.isBreedingItem(this.hand.get() == Hand.MAIN_HAND ? this.mc.player.getMainHandStack() : this.mc.player.getOffHandStack())) continue;
            Rotations.rotate(Rotations.getYaw(Entity2), Rotations.getPitch(Entity2), -100, () -> this.lambda$onTick$0(AnimalEntity2));
            return;
        }
    }

    @Override
    public void onActivate() {
        this.animalsFed.clear();
    }

    private void lambda$onTick$0(AnimalEntity AnimalEntity2) {
        this.mc.interactionManager.interactEntity((PlayerEntity)this.mc.player, (Entity)AnimalEntity2, this.hand.get());
        this.mc.player.swingHand(this.hand.get());
        this.animalsFed.add((Entity)AnimalEntity2);
    }

    public AutoBreed() {
        super(Categories.World, "auto-breed", "Automatically breeds specified animals.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Entities to breed.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(EntityType.HORSE, EntityType.DONKEY, EntityType.COW, EntityType.MOOSHROOM, EntityType.SHEEP, EntityType.PIG, EntityType.CHICKEN, EntityType.WOLF, EntityType.CAT, EntityType.OCELOT, EntityType.RABBIT, EntityType.LLAMA, EntityType.TURTLE, EntityType.PANDA, EntityType.FOX, EntityType.BEE, EntityType.STRIDER, EntityType.HOGLIN)).onlyAttackable().build());
        this.range = this.sgGeneral.add(new DoubleSetting.Builder().name("range").description("How far away the animals can be to be bred.").min(0.0).defaultValue(4.5).build());
        this.hand = this.sgGeneral.add(new EnumSetting.Builder().name("hand-for-breeding").description("The hand to use for breeding.").defaultValue(Hand.MAIN_HAND).build());
        this.ignoreBabies = this.sgGeneral.add(new BoolSetting.Builder().name("ignore-babies").description("Whether or not to ignore the baby variants of the specified entity.").defaultValue(true).build());
        this.animalsFed = new ArrayList<Entity>();
    }
}

