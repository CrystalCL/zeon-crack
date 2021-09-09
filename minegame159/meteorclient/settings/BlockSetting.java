/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.util.Identifier
 */
package minegame159.meteorclient.settings;

import java.util.function.Consumer;
import minegame159.meteorclient.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class BlockSetting
extends Setting<Block> {
    @Override
    protected boolean isValueValid(Block lllllllllllllllllIIIIlIIIIlIlIII) {
        return true;
    }

    public BlockSetting(String lllllllllllllllllIIIIlIIIIlllIII, String lllllllllllllllllIIIIlIIIIllIlll, Block lllllllllllllllllIIIIlIIIIllIIII, Consumer<Block> lllllllllllllllllIIIIlIIIIlIllll, Consumer<Setting<Block>> lllllllllllllllllIIIIlIIIIllIlII) {
        super(lllllllllllllllllIIIIlIIIIlllIII, lllllllllllllllllIIIIlIIIIllIlll, lllllllllllllllllIIIIlIIIIllIIII, lllllllllllllllllIIIIlIIIIlIllll, lllllllllllllllllIIIIlIIIIllIlII);
        BlockSetting lllllllllllllllllIIIIlIIIIllIIll;
    }

    @Override
    public NbtCompound toTag() {
        BlockSetting lllllllllllllllllIIIIlIIIIlIIlII;
        NbtCompound lllllllllllllllllIIIIlIIIIlIIIll = new NbtCompound();
        lllllllllllllllllIIIIlIIIIlIIIll.putString("value", Registry.BLOCK.getId((Object)((Block)lllllllllllllllllIIIIlIIIIlIIlII.get())).toString());
        return lllllllllllllllllIIIIlIIIIlIIIll;
    }

    @Override
    protected Block parseImpl(String lllllllllllllllllIIIIlIIIIlIlIll) {
        return (Block)BlockSetting.parseId(Registry.BLOCK, lllllllllllllllllIIIIlIIIIlIlIll);
    }

    @Override
    public Block fromTag(NbtCompound lllllllllllllllllIIIIlIIIIIllIll) {
        BlockSetting lllllllllllllllllIIIIlIIIIIlllII;
        lllllllllllllllllIIIIlIIIIIlllII.value = Registry.BLOCK.get(new Identifier(lllllllllllllllllIIIIlIIIIIllIll.getString("value")));
        lllllllllllllllllIIIIlIIIIIlllII.changed();
        return (Block)lllllllllllllllllIIIIlIIIIIlllII.get();
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.BLOCK.getIds();
    }

    public static class Builder {
        private /* synthetic */ Block defaultValue;
        private /* synthetic */ Consumer<Block> onChanged;
        private /* synthetic */ String description;
        private /* synthetic */ Consumer<Setting<Block>> onModuleActivated;
        private /* synthetic */ String name;

        public Builder description(String lllIIIlllIlll) {
            Builder lllIIIlllIllI;
            lllIIIlllIllI.description = lllIIIlllIlll;
            return lllIIIlllIllI;
        }

        public Builder onModuleActivated(Consumer<Setting<Block>> lllIIIllIIlIl) {
            Builder lllIIIllIIlII;
            lllIIIllIIlII.onModuleActivated = lllIIIllIIlIl;
            return lllIIIllIIlII;
        }

        public Builder() {
            Builder lllIIlIIIIIIl;
            lllIIlIIIIIIl.name = "undefined";
            lllIIlIIIIIIl.description = "";
        }

        public Builder onChanged(Consumer<Block> lllIIIllIlIll) {
            Builder lllIIIllIllII;
            lllIIIllIllII.onChanged = lllIIIllIlIll;
            return lllIIIllIllII;
        }

        public BlockSetting build() {
            Builder lllIIIllIIIII;
            return new BlockSetting(lllIIIllIIIII.name, lllIIIllIIIII.description, lllIIIllIIIII.defaultValue, lllIIIllIIIII.onChanged, lllIIIllIIIII.onModuleActivated);
        }

        public Builder name(String lllIIIlllllIl) {
            Builder lllIIIlllllII;
            lllIIIlllllII.name = lllIIIlllllIl;
            return lllIIIlllllII;
        }

        public Builder defaultValue(Block lllIIIlllIIIl) {
            Builder lllIIIlllIIII;
            lllIIIlllIIII.defaultValue = lllIIIlllIIIl;
            return lllIIIlllIIII;
        }
    }
}

