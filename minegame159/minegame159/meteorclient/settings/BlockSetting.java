/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.settings;

import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BlockSetting
extends Setting<Block> {
    @Override
    protected boolean isValueValid(Block Block2) {
        return true;
    }

    public BlockSetting(String string, String string2, Block Block2, Consumer<Block> consumer, Consumer<Setting<Block>> consumer2) {
        super(string, string2, Block2, consumer, consumer2);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("value", Registry.BLOCK.getId((Object)((Block)this.get())).toString());
        return NbtCompound2;
    }

    @Override
    protected Block parseImpl(String string) {
        return (Block)BlockSetting.parseId(Registry.BLOCK, string);
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.BLOCK.getIds();
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    protected Object parseImpl(String string) {
        return this.parseImpl(string);
    }

    @Override
    public Block fromTag(NbtCompound NbtCompound2) {
        this.value = Registry.BLOCK.get(new Identifier(NbtCompound2.getString("value")));
        this.changed();
        return (Block)this.get();
    }

    @Override
    protected boolean isValueValid(Object object) {
        return this.isValueValid((Block)object);
    }

    public static class Builder {
        private Consumer<Setting<Block>> onModuleActivated;
        private Block defaultValue;
        private String name = "undefined";
        private String description = "";
        private Consumer<Block> onChanged;

        public Builder description(String string) {
            this.description = string;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder onModuleActivated(Consumer<Setting<Block>> consumer) {
            this.onModuleActivated = consumer;
            return this;
        }

        public Builder defaultValue(Block Block2) {
            this.defaultValue = Block2;
            return this;
        }

        public Builder onChanged(Consumer<Block> consumer) {
            this.onChanged = consumer;
            return this;
        }

        public BlockSetting build() {
            return new BlockSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated);
        }
    }
}

