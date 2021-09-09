/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.util.Identifier
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
    protected boolean isValueValid(List<Block> lIlIIIIlllllI) {
        return true;
    }

    @Override
    public Iterable<Identifier> getIdentifierSuggestions() {
        return Registry.BLOCK.getIds();
    }

    @Override
    public List<Block> fromTag(NbtCompound lIlIIIIIIlIIl) {
        BlockListSetting lIlIIIIIIllIl;
        ((List)lIlIIIIIIllIl.get()).clear();
        NbtList lIlIIIIIIlIll = lIlIIIIIIlIIl.getList("value", 8);
        for (NbtElement lIlIIIIIIlllI : lIlIIIIIIlIll) {
            ((List)lIlIIIIIIllIl.get()).add((Block)Registry.BLOCK.get(new Identifier(lIlIIIIIIlllI.asString())));
        }
        lIlIIIIIIllIl.changed();
        return (List)lIlIIIIIIllIl.get();
    }

    public BlockListSetting(String lIlIIIllIlIlI, String lIlIIIllIllll, List<Block> lIlIIIllIlIII, Consumer<List<Block>> lIlIIIllIllIl, Consumer<Setting<List<Block>>> lIlIIIllIIllI) {
        super(lIlIIIllIlIlI, lIlIIIllIllll, lIlIIIllIlIII, lIlIIIllIllIl, lIlIIIllIIllI);
        BlockListSetting lIlIIIllIlIll;
        lIlIIIllIlIll.value = new ArrayList<Block>(lIlIIIllIlIII);
    }

    @Override
    public NbtCompound toTag() {
        BlockListSetting lIlIIIIlIlllI;
        NbtCompound lIlIIIIlIllII = lIlIIIIlIlllI.saveGeneral();
        NbtList lIlIIIIlIlIlI = new NbtList();
        for (Block lIlIIIIlIllll : (List)lIlIIIIlIlllI.get()) {
            lIlIIIIlIlIlI.add((Object)NbtString.of((String)Registry.BLOCK.getId((Object)lIlIIIIlIllll).toString()));
        }
        lIlIIIIlIllII.put("value", (NbtElement)lIlIIIIlIlIlI);
        return lIlIIIIlIllII;
    }

    @Override
    protected List<Block> parseImpl(String lIlIIIlIIlIlI) {
        String[] lIlIIIlIIlIIl = lIlIIIlIIlIlI.split(",");
        ArrayList<Block> lIlIIIlIIlIII = new ArrayList<Block>(lIlIIIlIIlIIl.length);
        try {
            for (String lIlIIIlIIllII : lIlIIIlIIlIIl) {
                Block lIlIIIlIIllIl = (Block)BlockListSetting.parseId(Registry.BLOCK, lIlIIIlIIllII);
                if (lIlIIIlIIllIl == null) continue;
                lIlIIIlIIlIII.add(lIlIIIlIIllIl);
            }
        }
        catch (Exception lIlIIIlIIIlII) {
            // empty catch block
        }
        return lIlIIIlIIlIII;
    }

    @Override
    public void reset(boolean lIlIIIlIlIllI) {
        BlockListSetting lIlIIIlIllIIl;
        lIlIIIlIllIIl.value = new ArrayList((Collection)lIlIIIlIllIIl.defaultValue);
        if (lIlIIIlIlIllI) {
            lIlIIIlIllIIl.changed();
        }
    }

    public static class Builder {
        private /* synthetic */ String name;
        private /* synthetic */ Consumer<Setting<List<Block>>> onModuleActivated;
        private /* synthetic */ Consumer<List<Block>> onChanged;
        private /* synthetic */ List<Block> defaultValue;
        private /* synthetic */ String description;

        public Builder() {
            Builder lllllllllllllllllllIIIIIllIIllII;
            lllllllllllllllllllIIIIIllIIllII.name = "undefined";
            lllllllllllllllllllIIIIIllIIllII.description = "";
        }

        public Builder onModuleActivated(Consumer<Setting<List<Block>>> lllllllllllllllllllIIIIIlIlIllIl) {
            Builder lllllllllllllllllllIIIIIlIlIlllI;
            lllllllllllllllllllIIIIIlIlIlllI.onModuleActivated = lllllllllllllllllllIIIIIlIlIllIl;
            return lllllllllllllllllllIIIIIlIlIlllI;
        }

        public BlockListSetting build() {
            Builder lllllllllllllllllllIIIIIlIlIlIlI;
            return new BlockListSetting(lllllllllllllllllllIIIIIlIlIlIlI.name, lllllllllllllllllllIIIIIlIlIlIlI.description, lllllllllllllllllllIIIIIlIlIlIlI.defaultValue, lllllllllllllllllllIIIIIlIlIlIlI.onChanged, lllllllllllllllllllIIIIIlIlIlIlI.onModuleActivated);
        }

        public Builder defaultValue(List<Block> lllllllllllllllllllIIIIIlIlllIIl) {
            Builder lllllllllllllllllllIIIIIlIllllII;
            lllllllllllllllllllIIIIIlIllllII.defaultValue = lllllllllllllllllllIIIIIlIlllIIl;
            return lllllllllllllllllllIIIIIlIllllII;
        }

        public Builder description(String lllllllllllllllllllIIIIIlIllllll) {
            Builder lllllllllllllllllllIIIIIllIIIIlI;
            lllllllllllllllllllIIIIIllIIIIlI.description = lllllllllllllllllllIIIIIlIllllll;
            return lllllllllllllllllllIIIIIllIIIIlI;
        }

        public Builder name(String lllllllllllllllllllIIIIIllIIIlll) {
            Builder lllllllllllllllllllIIIIIllIIlIII;
            lllllllllllllllllllIIIIIllIIlIII.name = lllllllllllllllllllIIIIIllIIIlll;
            return lllllllllllllllllllIIIIIllIIlIII;
        }

        public Builder onChanged(Consumer<List<Block>> lllllllllllllllllllIIIIIlIllIlIl) {
            Builder lllllllllllllllllllIIIIIlIllIlII;
            lllllllllllllllllllIIIIIlIllIlII.onChanged = lllllllllllllllllllIIIIIlIllIlIl;
            return lllllllllllllllllllIIIIIlIllIlII;
        }
    }
}

