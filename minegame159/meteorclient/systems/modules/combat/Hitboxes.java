/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 */
package minegame159.meteorclient.systems.modules.combat;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public class Hitboxes
extends Module {
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Double> value;

    public Hitboxes() {
        super(Categories.Combat, "hitboxes", "Expands an entity's hitboxes.");
        Hitboxes llllllllllllllllllIIlIlIIllIlIll;
        llllllllllllllllllIIlIlIIllIlIll.sgGeneral = llllllllllllllllllIIlIlIIllIlIll.settings.getDefaultGroup();
        llllllllllllllllllIIlIlIIllIlIll.entities = llllllllllllllllllIIlIlIIllIlIll.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Which entities to target.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(new EntityType[]{EntityType.PLAYER})).build());
        llllllllllllllllllIIlIlIIllIlIll.value = llllllllllllllllllIIlIlIIllIlIll.sgGeneral.add(new DoubleSetting.Builder().name("expand").description("How much to expand the hitbox of the entity.").defaultValue(0.5).build());
    }

    public double getEntityValue(Entity llllllllllllllllllIIlIlIIllIIlIl) {
        Hitboxes llllllllllllllllllIIlIlIIllIlIII;
        if (!llllllllllllllllllIIlIlIIllIlIII.isActive()) {
            return 0.0;
        }
        if (llllllllllllllllllIIlIlIIllIlIII.entities.get().getBoolean((Object)llllllllllllllllllIIlIlIIllIIlIl.getType())) {
            return llllllllllllllllllIIlIlIIllIlIII.value.get();
        }
        return 0.0;
    }
}

