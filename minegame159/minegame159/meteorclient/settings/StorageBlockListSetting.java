/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import com.mojang.serialization.Lifecycle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import org.apache.logging.log4j.core.util.ObjectArrayIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StorageBlockListSetting
extends Setting<List<BlockEntityType<?>>> {
    public static final Registry<BlockEntityType<?>> REGISTRY;
    public static final BlockEntityType<?>[] STORAGE_BLOCKS;

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtList NbtList2 = new NbtList();
        for (BlockEntityType BlockEntityType2 : (List)this.get()) {
            Identifier Identifier2 = Registry.BLOCK_ENTITY_TYPE.getId((Object)BlockEntityType2);
            if (Identifier2 == null) continue;
            NbtList2.add((Object)NbtString.of((String)Identifier2.toString()));
        }
        NbtCompound2.put("value", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    public StorageBlockListSetting(String string, String string2, List<BlockEntityType<?>> list, Consumer<List<BlockEntityType<?>>> consumer, Consumer<Setting<List<BlockEntityType<?>>>> consumer2) {
        super(string, string2, list, consumer, consumer2);
        this.value = new ArrayList(list);
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.BLOCK_ENTITY_TYPE.getIds();
    }

    @Override
    public void reset(boolean bl) {
        this.value = new ArrayList((Collection)this.defaultValue);
        if (bl) {
            this.changed();
        }
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((List)object);
    }

    @Override
    public List<BlockEntityType<?>> fromTag(NbtCompound NbtCompound2) {
        ((List)this.get()).clear();
        NbtList NbtList2 = NbtCompound2.getList("value", 8);
        for (NbtElement NbtElement2 : NbtList2) {
            BlockEntityType BlockEntityType2 = (BlockEntityType)Registry.BLOCK_ENTITY_TYPE.get(new Identifier(NbtElement2.asString()));
            if (BlockEntityType2 == null) continue;
            ((List)this.get()).add(BlockEntityType2);
        }
        this.changed();
        return (List)this.get();
    }

    static {
        STORAGE_BLOCKS = new BlockEntityType[]{BlockEntityType.FURNACE, BlockEntityType.CHEST, BlockEntityType.TRAPPED_CHEST, BlockEntityType.ENDER_CHEST, BlockEntityType.DISPENSER, BlockEntityType.DROPPER, BlockEntityType.HOPPER, BlockEntityType.SHULKER_BOX, BlockEntityType.BARREL, BlockEntityType.SMOKER, BlockEntityType.BLAST_FURNACE};
        REGISTRY = new SRegistry();
    }

    @Override
    protected List<BlockEntityType<?>> parseImpl(String string) {
        String[] stringArray = string.split(",");
        ArrayList arrayList = new ArrayList(stringArray.length);
        try {
            for (String string2 : stringArray) {
                BlockEntityType BlockEntityType2 = (BlockEntityType)StorageBlockListSetting.parseId(Registry.BLOCK_ENTITY_TYPE, string2);
                if (BlockEntityType2 == null) continue;
                arrayList.add(BlockEntityType2);
                if (null == null) continue;
                return null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return arrayList;
    }

    @Override
    protected boolean isValueValid(List<BlockEntityType<?>> list) {
        return true;
    }

    private static class SRegistry
    extends Registry<BlockEntityType<?>> {
        @Nullable
        public Object get(@Nullable RegistryKey RegistryKey2) {
            return this.get(RegistryKey2);
        }

        public int getRawId(@Nullable BlockEntityType<?> BlockEntityType2) {
            return 0;
        }

        public Set<Identifier> getIds() {
            return null;
        }

        public int getRawId(@Nullable Object object) {
            return this.getRawId((BlockEntityType)object);
        }

        @Nullable
        public BlockEntityType<?> get(@Nullable Identifier Identifier2) {
            return null;
        }

        @Nullable
        public Object get(int n) {
            return this.get(n);
        }

        public SRegistry() {
            super(RegistryKey.ofRegistry((Identifier)new Identifier("meteor-client", "storage-blocks")), Lifecycle.stable());
        }

        protected Lifecycle getEntryLifecycle(BlockEntityType<?> BlockEntityType2) {
            return null;
        }

        public boolean containsId(Identifier Identifier2) {
            return false;
        }

        public Lifecycle getLifecycle() {
            return null;
        }

        public Set<Map.Entry<RegistryKey<BlockEntityType<?>>, BlockEntityType<?>>> getEntries() {
            return null;
        }

        @Nullable
        public Identifier getId(Object object) {
            return this.getId((BlockEntityType)object);
        }

        @Nullable
        public Identifier getId(BlockEntityType<?> BlockEntityType2) {
            return null;
        }

        @NotNull
        public Iterator<BlockEntityType<?>> iterator() {
            return new ObjectArrayIterator((Object[])STORAGE_BLOCKS);
        }

        @Nullable
        public Object get(@Nullable Identifier Identifier2) {
            return this.get(Identifier2);
        }

        protected Lifecycle getEntryLifecycle(Object object) {
            return this.getEntryLifecycle((BlockEntityType)object);
        }

        @Nullable
        public BlockEntityType<?> get(int n) {
            return null;
        }

        public Optional<RegistryKey<BlockEntityType<?>>> getKey(BlockEntityType<?> BlockEntityType2) {
            return Optional.empty();
        }

        @Nullable
        public BlockEntityType<?> get(@Nullable RegistryKey<BlockEntityType<?>> RegistryKey2) {
            return null;
        }

        public Optional getKey(Object object) {
            return this.getKey((BlockEntityType)object);
        }
    }

    public static class Builder {
        private String description = "";
        private List<BlockEntityType<?>> defaultValue;
        private String name = "undefined";
        private Consumer<Setting<List<BlockEntityType<?>>>> onModuleActivated;
        private Consumer<List<BlockEntityType<?>>> onChanged;

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<List<BlockEntityType<?>>>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder onChanged(Consumer<List<BlockEntityType<?>>> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public Builder defaultValue(List<BlockEntityType<?>> list) {
            this.defaultValue = list;
            return this;
        }

        public StorageBlockListSetting build() {
            return new StorageBlockListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }
    }
}

