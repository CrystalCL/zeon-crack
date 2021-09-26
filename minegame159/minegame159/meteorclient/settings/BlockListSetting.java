/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

public class BlockListSetting
extends Setting<List<Block>> {
    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = this.saveGeneral();
        NbtList NbtList2 = new NbtList();
        for (Block Block2 : (List)this.get()) {
            NbtList2.add((Object)NbtString.of((String)Registry.BLOCK.getId((Object)Block2).toString()));
        }
        NbtCompound2.put("value", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    @Override
    protected List<Block> parseImpl(String string) {
        String[] stringArray = string.split(",");
        ArrayList<Block> arrayList = new ArrayList<Block>(stringArray.length);
        try {
            for (String string2 : stringArray) {
                Block Block2 = (Block)BlockListSetting.parseId(Registry.BLOCK, string2);
                if (Block2 == null) continue;
                arrayList.add(Block2);
                if (1 <= 2) continue;
                return null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return arrayList;
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.BLOCK.getIds();
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((List)object);
    }

    @Override
    public void reset(boolean bl) {
        this.value = new ArrayList((Collection)this.defaultValue);
        if (bl) {
            this.changed();
        }
    }

    public BlockListSetting(String string, String string2, List<Block> list, Consumer<List<Block>> consumer, Consumer<Setting<List<Block>>> consumer2) {
        super(string, string2, list, consumer, consumer2);
        this.value = new ArrayList<Block>(list);
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    public List<Block> fromTag(NbtCompound NbtCompound2) {
        ((List)this.get()).clear();
        NbtList NbtList2 = NbtCompound2.getList("value", 8);
        for (NbtElement NbtElement2 : NbtList2) {
            ((List)this.get()).add((Block)Registry.BLOCK.get(new Identifier(NbtElement2.asString())));
        }
        this.changed();
        return (List)this.get();
    }

    @Override
    protected boolean isValueValid(List<Block> list) {
        return true;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public static class Builder {
        private String description = "";
        private Consumer<List<Block>> onChanged;
        private Consumer<Setting<List<Block>>> onModuleActivated;
        private String name = "undefined";
        private List<Block> defaultValue;

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder defaultValue(List<Block> list) {
            this.defaultValue = list;
            return this;
        }

        public Builder onChanged(Consumer<List<Block>> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public BlockListSetting build() {
            return new BlockListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }

        public Builder onModuleActivated(Consumer<Setting<List<Block>>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }
    }
}

