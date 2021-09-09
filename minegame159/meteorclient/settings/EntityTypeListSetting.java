/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  net.minecraft.entity.EntityType
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.util.Identifier
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
    public final /* synthetic */ boolean onlyAttackable;

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.ENTITY_TYPE.getIds();
    }

    @Override
    public NbtCompound toTag() {
        EntityTypeListSetting lllllllllllllllllIlIIIIlIlIllIll;
        NbtCompound lllllllllllllllllIlIIIIlIlIllIlI = lllllllllllllllllIlIIIIlIlIllIll.saveGeneral();
        NbtList lllllllllllllllllIlIIIIlIlIllIIl = new NbtList();
        for (EntityType lllllllllllllllllIlIIIIlIlIlllII : ((Object2BooleanMap)lllllllllllllllllIlIIIIlIlIllIll.get()).keySet()) {
            if (!((Object2BooleanMap)lllllllllllllllllIlIIIIlIlIllIll.get()).getBoolean((Object)lllllllllllllllllIlIIIIlIlIlllII)) continue;
            lllllllllllllllllIlIIIIlIlIllIIl.add((Object)NbtString.of((String)Registry.ENTITY_TYPE.getId((Object)lllllllllllllllllIlIIIIlIlIlllII).toString()));
        }
        lllllllllllllllllIlIIIIlIlIllIlI.put("value", (NbtElement)lllllllllllllllllIlIIIIlIlIllIIl);
        return lllllllllllllllllIlIIIIlIlIllIlI;
    }

    @Override
    public void reset(boolean lllllllllllllllllIlIIIIlIllllIll) {
        EntityTypeListSetting lllllllllllllllllIlIIIIlIllllllI;
        lllllllllllllllllIlIIIIlIllllllI.value = new Object2BooleanOpenHashMap((Object2BooleanMap)lllllllllllllllllIlIIIIlIllllllI.defaultValue);
        if (lllllllllllllllllIlIIIIlIllllIll) {
            lllllllllllllllllIlIIIIlIllllllI.changed();
        }
    }

    @Override
    public Object2BooleanMap<EntityType<?>> fromTag(NbtCompound lllllllllllllllllIlIIIIlIlIIIlll) {
        EntityTypeListSetting lllllllllllllllllIlIIIIlIlIIlIII;
        ((Object2BooleanMap)lllllllllllllllllIlIIIIlIlIIlIII.get()).clear();
        NbtList lllllllllllllllllIlIIIIlIlIIlIIl = lllllllllllllllllIlIIIIlIlIIIlll.getList("value", 8);
        for (NbtElement lllllllllllllllllIlIIIIlIlIIllII : lllllllllllllllllIlIIIIlIlIIlIIl) {
            EntityType lllllllllllllllllIlIIIIlIlIIllIl = (EntityType)Registry.ENTITY_TYPE.get(new Identifier(lllllllllllllllllIlIIIIlIlIIllII.asString()));
            if (lllllllllllllllllIlIIIIlIlIIlIII.onlyAttackable && !EntityUtils.isAttackable(lllllllllllllllllIlIIIIlIlIIllIl)) continue;
            ((Object2BooleanMap)lllllllllllllllllIlIIIIlIlIIlIII.get()).put((Object)lllllllllllllllllIlIIIIlIlIIllIl, true);
        }
        lllllllllllllllllIlIIIIlIlIIlIII.changed();
        return (Object2BooleanMap)lllllllllllllllllIlIIIIlIlIIlIII.get();
    }

    @Override
    protected boolean isValueValid(Object2BooleanMap<EntityType<?>> lllllllllllllllllIlIIIIlIllIIIll) {
        return true;
    }

    public EntityTypeListSetting(String lllllllllllllllllIlIIIIllIIIIllI, String lllllllllllllllllIlIIIIllIIIIlIl, Object2BooleanMap<EntityType<?>> lllllllllllllllllIlIIIIllIIIIlII, Consumer<Object2BooleanMap<EntityType<?>>> lllllllllllllllllIlIIIIllIIIIIll, Consumer<Setting<Object2BooleanMap<EntityType<?>>>> lllllllllllllllllIlIIIIllIIIlIIl, boolean lllllllllllllllllIlIIIIllIIIlIII) {
        super(lllllllllllllllllIlIIIIllIIIIllI, lllllllllllllllllIlIIIIllIIIIlIl, lllllllllllllllllIlIIIIllIIIIlII, lllllllllllllllllIlIIIIllIIIIIll, lllllllllllllllllIlIIIIllIIIlIIl);
        EntityTypeListSetting lllllllllllllllllIlIIIIllIIIlllI;
        lllllllllllllllllIlIIIIllIIIlllI.onlyAttackable = lllllllllllllllllIlIIIIllIIIlIII;
        lllllllllllllllllIlIIIIllIIIlllI.value = new Object2BooleanOpenHashMap(lllllllllllllllllIlIIIIllIIIIlII);
    }

    @Override
    protected Object2BooleanMap<EntityType<?>> parseImpl(String lllllllllllllllllIlIIIIlIllIllII) {
        String[] lllllllllllllllllIlIIIIlIllIlllI = lllllllllllllllllIlIIIIlIllIllII.split(",");
        Object2BooleanOpenHashMap lllllllllllllllllIlIIIIlIllIllIl = new Object2BooleanOpenHashMap(lllllllllllllllllIlIIIIlIllIlllI.length);
        try {
            for (String lllllllllllllllllIlIIIIlIlllIIIl : lllllllllllllllllIlIIIIlIllIlllI) {
                EntityType lllllllllllllllllIlIIIIlIlllIIlI = (EntityType)EntityTypeListSetting.parseId(Registry.ENTITY_TYPE, lllllllllllllllllIlIIIIlIlllIIIl);
                if (lllllllllllllllllIlIIIIlIlllIIlI == null) continue;
                lllllllllllllllllIlIIIIlIllIllIl.put((Object)lllllllllllllllllIlIIIIlIlllIIlI, true);
            }
        }
        catch (Exception lllllllllllllllllIlIIIIlIllIlIIl) {
            // empty catch block
        }
        return lllllllllllllllllIlIIIIlIllIllIl;
    }

    public static class Builder {
        private /* synthetic */ String name;
        private /* synthetic */ boolean onlyAttackable;
        private /* synthetic */ Consumer<Object2BooleanMap<EntityType<?>>> onChanged;
        private /* synthetic */ Object2BooleanMap<EntityType<?>> defaultValue;
        private /* synthetic */ String description;
        private /* synthetic */ Consumer<Setting<Object2BooleanMap<EntityType<?>>>> onModuleActivated;

        public Builder onChanged(Consumer<Object2BooleanMap<EntityType<?>>> llIIIIlIIlII) {
            Builder llIIIIlIIlll;
            llIIIIlIIlll.onChanged = llIIIIlIIlII;
            return llIIIIlIIlll;
        }

        public Builder onlyAttackable() {
            Builder llIIIIIlllII;
            llIIIIIlllII.onlyAttackable = true;
            return llIIIIIlllII;
        }

        public Builder name(String llIIIIlllIII) {
            Builder llIIIIlllIIl;
            llIIIIlllIIl.name = llIIIIlllIII;
            return llIIIIlllIIl;
        }

        public EntityTypeListSetting build() {
            Builder llIIIIIllIII;
            return new EntityTypeListSetting(llIIIIIllIII.name, llIIIIIllIII.description, llIIIIIllIII.defaultValue, llIIIIIllIII.onChanged, llIIIIIllIII.onModuleActivated, llIIIIIllIII.onlyAttackable);
        }

        public Builder defaultValue(Object2BooleanMap<EntityType<?>> llIIIIlIlIlI) {
            Builder llIIIIlIllIl;
            llIIIIlIllIl.defaultValue = llIIIIlIlIlI;
            return llIIIIlIllIl;
        }

        public Builder description(String llIIIIllIIlI) {
            Builder llIIIIllIIIl;
            llIIIIllIIIl.description = llIIIIllIIlI;
            return llIIIIllIIIl;
        }

        public Builder() {
            Builder llIIIIllllIl;
            llIIIIllllIl.name = "undefined";
            llIIIIllllIl.description = "";
            llIIIIllllIl.onlyAttackable = false;
        }

        public Builder onModuleActivated(Consumer<Setting<Object2BooleanMap<EntityType<?>>>> llIIIIlIIIII) {
            Builder llIIIIIlllll;
            llIIIIIlllll.onModuleActivated = llIIIIlIIIII;
            return llIIIIIlllll;
        }
    }
}

