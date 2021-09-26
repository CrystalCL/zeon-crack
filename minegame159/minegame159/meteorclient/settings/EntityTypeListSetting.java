/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.entity.EntityUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

public class EntityTypeListSetting
extends Setting<Object2BooleanMap<EntityType<?>>> {
    public final boolean onlyAttackable;

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((Object2BooleanMap)object);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtList NbtList2 = new NbtList();
        for (EntityType EntityType2 : ((Object2BooleanMap)this.get()).keySet()) {
            if (!((Object2BooleanMap)this.get()).getBoolean((Object)EntityType2)) continue;
            NbtList2.add((Object)NbtString.of((String)Registry.ENTITY_TYPE.getId((Object)EntityType2).toString()));
        }
        NbtCompound2.put("value", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    public EntityTypeListSetting(String string, String string2, Object2BooleanMap<EntityType<?>> object2BooleanMap, Consumer<Object2BooleanMap<EntityType<?>>> consumer, Consumer<Setting<Object2BooleanMap<EntityType<?>>>> consumer2, boolean bl) {
        super(string, string2, object2BooleanMap, consumer, consumer2);
        this.onlyAttackable = bl;
        this.value = new Object2BooleanOpenHashMap(object2BooleanMap);
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    public void reset(boolean bl) {
        this.value = new Object2BooleanOpenHashMap((Object2BooleanMap)this.defaultValue);
        if (bl) {
            this.changed();
        }
    }

    @Override
    protected boolean isValueValid(Object2BooleanMap<EntityType<?>> object2BooleanMap) {
        return true;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public Object2BooleanMap<EntityType<?>> fromTag(NbtCompound NbtCompound2) {
        ((Object2BooleanMap)this.get()).clear();
        NbtList NbtList2 = NbtCompound2.getList("value", 8);
        for (NbtElement NbtElement2 : NbtList2) {
            EntityType EntityType2 = (EntityType)Registry.ENTITY_TYPE.get(new Identifier(NbtElement2.asString()));
            if (this.onlyAttackable && !EntityUtils.isAttackable(EntityType2)) continue;
            ((Object2BooleanMap)this.get()).put((Object)EntityType2, true);
        }
        this.changed();
        return (Object2BooleanMap)this.get();
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.ENTITY_TYPE.getIds();
    }

    @Override
    protected Object2BooleanMap<EntityType<?>> parseImpl(String string) {
        String[] stringArray = string.split(",");
        Object2BooleanOpenHashMap object2BooleanOpenHashMap = new Object2BooleanOpenHashMap(stringArray.length);
        try {
            for (String string2 : stringArray) {
                EntityType EntityType2 = (EntityType)EntityTypeListSetting.parseId(Registry.ENTITY_TYPE, string2);
                if (EntityType2 == null) continue;
                object2BooleanOpenHashMap.put((Object)EntityType2, true);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return object2BooleanOpenHashMap;
    }

    public static class Builder {
        private String name = "undefined";
        private Object2BooleanMap<EntityType<?>> defaultValue;
        private boolean onlyAttackable = false;
        private Consumer<Setting<Object2BooleanMap<EntityType<?>>>> onModuleActivated;
        private String description = "";
        private Consumer<Object2BooleanMap<EntityType<?>>> onChanged;

        public Builder onlyAttackable() {
            this.onlyAttackable = true;
            return this;
        }

        public Builder onChanged(Consumer<Object2BooleanMap<EntityType<?>>> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<Object2BooleanMap<EntityType<?>>>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public EntityTypeListSetting build() {
            return new EntityTypeListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.onlyAttackable);
        }

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder defaultValue(Object2BooleanMap<EntityType<?>> object2BooleanMap) {
            this.defaultValue = object2BooleanMap;
            return this;
        }
    }
}

