/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Lifecycle
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.block.entity.BlockEntityType
 *  net.minecraft.util.Identifier
 *  net.minecraft.util.registry.RegistryKey
 *  org.apache.logging.log4j.core.util.ObjectArrayIterator
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
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
    public static final /* synthetic */ BlockEntityType<?>[] STORAGE_BLOCKS;
    public static final /* synthetic */ Registry<BlockEntityType<?>> REGISTRY;

    static {
        STORAGE_BLOCKS = new BlockEntityType[]{BlockEntityType.FURNACE, BlockEntityType.CHEST, BlockEntityType.TRAPPED_CHEST, BlockEntityType.ENDER_CHEST, BlockEntityType.DISPENSER, BlockEntityType.DROPPER, BlockEntityType.HOPPER, BlockEntityType.SHULKER_BOX, BlockEntityType.BARREL, BlockEntityType.SMOKER, BlockEntityType.BLAST_FURNACE};
        REGISTRY = new SRegistry();
    }

    @Override
    public List<BlockEntityType<?>> fromTag(NbtCompound llIlIlIlIlIllIl) {
        StorageBlockListSetting llIlIlIlIllIIIl;
        ((List)llIlIlIlIllIIIl.get()).clear();
        NbtList llIlIlIlIlIllll = llIlIlIlIlIllIl.getList("value", 8);
        for (NbtElement llIlIlIlIllIIlI : llIlIlIlIlIllll) {
            BlockEntityType llIlIlIlIllIIll = (BlockEntityType)Registry.BLOCK_ENTITY_TYPE.get(new Identifier(llIlIlIlIllIIlI.asString()));
            if (llIlIlIlIllIIll == null) continue;
            ((List)llIlIlIlIllIIIl.get()).add(llIlIlIlIllIIll);
        }
        llIlIlIlIllIIIl.changed();
        return (List)llIlIlIlIllIIIl.get();
    }

    @Override
    public void reset(boolean llIlIlIlllIIlII) {
        StorageBlockListSetting llIlIlIlllIIlll;
        llIlIlIlllIIlll.value = new ArrayList((Collection)llIlIlIlllIIlll.defaultValue);
        if (llIlIlIlllIIlII) {
            llIlIlIlllIIlll.changed();
        }
    }

    @Override
    protected List<BlockEntityType<?>> parseImpl(String llIlIlIllIllIII) {
        String[] llIlIlIllIlIlll = llIlIlIllIllIII.split(",");
        ArrayList llIlIlIllIlIllI = new ArrayList(llIlIlIllIlIlll.length);
        try {
            for (String llIlIlIllIllIlI : llIlIlIllIlIlll) {
                BlockEntityType llIlIlIllIllIll = (BlockEntityType)StorageBlockListSetting.parseId(Registry.BLOCK_ENTITY_TYPE, llIlIlIllIllIlI);
                if (llIlIlIllIllIll == null) continue;
                llIlIlIllIlIllI.add(llIlIlIllIllIll);
            }
        }
        catch (Exception llIlIlIllIlIIlI) {
            // empty catch block
        }
        return llIlIlIllIlIllI;
    }

    @Override
    public NbtCompound toTag() {
        StorageBlockListSetting llIlIlIlIllllll;
        NbtCompound llIlIlIllIIIIIl = llIlIlIlIllllll.saveGeneral();
        NbtList llIlIlIllIIIIII = new NbtList();
        for (BlockEntityType llIlIlIllIIIIll : (List)llIlIlIlIllllll.get()) {
            Identifier llIlIlIllIIIlII = Registry.BLOCK_ENTITY_TYPE.getId((Object)llIlIlIllIIIIll);
            if (llIlIlIllIIIlII == null) continue;
            llIlIlIllIIIIII.add((Object)NbtString.of((String)llIlIlIllIIIlII.toString()));
        }
        llIlIlIllIIIIIl.put("value", (NbtElement)llIlIlIllIIIIII);
        return llIlIlIllIIIIIl;
    }

    @Override
    protected boolean isValueValid(List<BlockEntityType<?>> llIlIlIllIIllII) {
        return true;
    }

    public StorageBlockListSetting(String llIlIlIllllIlII, String llIlIlIlllIllIl, List<BlockEntityType<?>> llIlIlIlllIllII, Consumer<List<BlockEntityType<?>>> llIlIlIllllIIIl, Consumer<Setting<List<BlockEntityType<?>>>> llIlIlIllllIIII) {
        super(llIlIlIllllIlII, llIlIlIlllIllIl, llIlIlIlllIllII, llIlIlIllllIIIl, llIlIlIllllIIII);
        StorageBlockListSetting llIlIlIllllIlIl;
        llIlIlIllllIlIl.value = new ArrayList(llIlIlIlllIllII);
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.BLOCK_ENTITY_TYPE.getIds();
    }

    public static class Builder {
        private /* synthetic */ Consumer<Setting<List<BlockEntityType<?>>>> onModuleActivated;
        private /* synthetic */ Consumer<List<BlockEntityType<?>>> onChanged;
        private /* synthetic */ String name;
        private /* synthetic */ String description;
        private /* synthetic */ List<BlockEntityType<?>> defaultValue;

        public Builder name(String llIIlIllIIlIIlI) {
            Builder llIIlIllIIlIIll;
            llIIlIllIIlIIll.name = llIIlIllIIlIIlI;
            return llIIlIllIIlIIll;
        }

        public Builder() {
            Builder llIIlIllIIlIllI;
            llIIlIllIIlIllI.name = "undefined";
            llIIlIllIIlIllI.description = "";
        }

        public Builder defaultValue(List<BlockEntityType<?>> llIIlIllIIIIllI) {
            Builder llIIlIllIIIIlIl;
            llIIlIllIIIIlIl.defaultValue = llIIlIllIIIIllI;
            return llIIlIllIIIIlIl;
        }

        public Builder onChanged(Consumer<List<BlockEntityType<?>>> llIIlIllIIIIIII) {
            Builder llIIlIllIIIIIIl;
            llIIlIllIIIIIIl.onChanged = llIIlIllIIIIIII;
            return llIIlIllIIIIIIl;
        }

        public Builder onModuleActivated(Consumer<Setting<List<BlockEntityType<?>>>> llIIlIlIllllIlI) {
            Builder llIIlIlIllllIll;
            llIIlIlIllllIll.onModuleActivated = llIIlIlIllllIlI;
            return llIIlIlIllllIll;
        }

        public Builder description(String llIIlIllIIIlIlI) {
            Builder llIIlIllIIIlIll;
            llIIlIllIIIlIll.description = llIIlIllIIIlIlI;
            return llIIlIllIIIlIll;
        }

        public StorageBlockListSetting build() {
            Builder llIIlIlIlllIllI;
            return new StorageBlockListSetting(llIIlIlIlllIllI.name, llIIlIlIlllIllI.description, llIIlIlIlllIllI.defaultValue, llIIlIlIlllIllI.onChanged, llIIlIlIlllIllI.onModuleActivated);
        }
    }

    private static class SRegistry
    extends Registry<BlockEntityType<?>> {
        @Nullable
        public BlockEntityType<?> get(@Nullable RegistryKey<BlockEntityType<?>> llllllllllllllllllIIIlIlllIIlIll) {
            return null;
        }

        protected Lifecycle getEntryLifecycle(BlockEntityType<?> llllllllllllllllllIIIlIlllIIIlll) {
            return null;
        }

        public int getRawId(@Nullable BlockEntityType<?> llllllllllllllllllIIIlIlllIIllIl) {
            return 0;
        }

        public boolean containsId(Identifier llllllllllllllllllIIIlIlllIIIIlI) {
            return false;
        }

        @Nullable
        public BlockEntityType<?> get(@Nullable Identifier llllllllllllllllllIIIlIlllIIlIIl) {
            return null;
        }

        public Lifecycle getLifecycle() {
            return null;
        }

        public Set<Map.Entry<RegistryKey<BlockEntityType<?>>, BlockEntityType<?>>> getEntries() {
            return null;
        }

        @Nullable
        public BlockEntityType<?> get(int llllllllllllllllllIIIlIlllIIIIII) {
            return null;
        }

        public SRegistry() {
            super(RegistryKey.ofRegistry((Identifier)new Identifier("meteor-client", "storage-blocks")), Lifecycle.stable());
            SRegistry llllllllllllllllllIIIlIlllIlIIll;
        }

        @Nullable
        public Identifier getId(BlockEntityType<?> llllllllllllllllllIIIlIlllIlIIIl) {
            return null;
        }

        public Set<Identifier> getIds() {
            return null;
        }

        public Optional<RegistryKey<BlockEntityType<?>>> getKey(BlockEntityType<?> llllllllllllllllllIIIlIlllIIllll) {
            return Optional.empty();
        }

        @NotNull
        public Iterator<BlockEntityType<?>> iterator() {
            return new ObjectArrayIterator((Object[])STORAGE_BLOCKS);
        }
    }
}

