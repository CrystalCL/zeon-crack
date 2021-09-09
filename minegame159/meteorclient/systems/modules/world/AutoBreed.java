/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.passive.AnimalEntity
 *  net.minecraft.entity.player.PlayerEntity
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
    private final /* synthetic */ Setting<Double> range;
    private final /* synthetic */ Setting<Hand> hand;
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> ignoreBabies;
    private final /* synthetic */ List<Entity> animalsFed;

    @Override
    public void onActivate() {
        AutoBreed llllllIlIIlllIl;
        llllllIlIIlllIl.animalsFed.clear();
    }

    @EventHandler
    private void onTick(TickEvent.Pre llllllIlIIlIlIl) {
        AutoBreed llllllIlIIlIllI;
        for (Entity llllllIlIIlIlll : llllllIlIIlIllI.mc.world.getEntities()) {
            if (!(llllllIlIIlIlll instanceof AnimalEntity)) continue;
            AnimalEntity llllllIlIIllIII = (AnimalEntity)llllllIlIIlIlll;
            if (!llllllIlIIlIllI.entities.get().getBoolean((Object)llllllIlIIllIII.getType()) || llllllIlIIllIII.isBaby() && !llllllIlIIlIllI.ignoreBabies.get().booleanValue() || llllllIlIIlIllI.animalsFed.contains((Object)llllllIlIIllIII) || (double)llllllIlIIlIllI.mc.player.distanceTo((Entity)llllllIlIIllIII) > llllllIlIIlIllI.range.get() || !llllllIlIIllIII.isBreedingItem(llllllIlIIlIllI.hand.get() == Hand.MAIN_HAND ? llllllIlIIlIllI.mc.player.getMainHandStack() : llllllIlIIlIllI.mc.player.getOffHandStack())) continue;
            Rotations.rotate(Rotations.getYaw(llllllIlIIlIlll), Rotations.getPitch(llllllIlIIlIlll), -100, () -> {
                AutoBreed llllllIlIIIllII;
                llllllIlIIIllII.mc.interactionManager.interactEntity((PlayerEntity)llllllIlIIIllII.mc.player, (Entity)llllllIlIIllIII, llllllIlIIIllII.hand.get());
                llllllIlIIIllII.mc.player.swingHand(llllllIlIIIllII.hand.get());
                llllllIlIIIllII.animalsFed.add((Entity)llllllIlIIllIII);
            });
            return;
        }
    }

    public AutoBreed() {
        super(Categories.World, "auto-breed", "Automatically breeds specified animals.");
        AutoBreed llllllIlIlIIIIl;
        llllllIlIlIIIIl.sgGeneral = llllllIlIlIIIIl.settings.getDefaultGroup();
        llllllIlIlIIIIl.entities = llllllIlIlIIIIl.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Entities to breed.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(new EntityType[]{EntityType.HORSE, EntityType.DONKEY, EntityType.COW, EntityType.MOOSHROOM, EntityType.SHEEP, EntityType.PIG, EntityType.CHICKEN, EntityType.WOLF, EntityType.CAT, EntityType.OCELOT, EntityType.RABBIT, EntityType.LLAMA, EntityType.TURTLE, EntityType.PANDA, EntityType.FOX, EntityType.BEE, EntityType.STRIDER, EntityType.HOGLIN})).onlyAttackable().build());
        llllllIlIlIIIIl.range = llllllIlIlIIIIl.sgGeneral.add(new DoubleSetting.Builder().name("range").description("How far away the animals can be to be bred.").min(0.0).defaultValue(4.5).build());
        llllllIlIlIIIIl.hand = llllllIlIlIIIIl.sgGeneral.add(new EnumSetting.Builder().name("hand-for-breeding").description("The hand to use for breeding.").defaultValue(Hand.MAIN_HAND).build());
        llllllIlIlIIIIl.ignoreBabies = llllllIlIlIIIIl.sgGeneral.add(new BoolSetting.Builder().name("ignore-babies").description("Whether or not to ignore the baby variants of the specified entity.").defaultValue(true).build());
        llllllIlIlIIIIl.animalsFed = new ArrayList<Entity>();
    }
}

