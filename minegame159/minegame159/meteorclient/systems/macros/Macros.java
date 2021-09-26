/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.macros;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.systems.System;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.macros.Macro;
import minegame159.meteorclient.utils.misc.NbtUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Macros
extends System<Macros>
implements Iterable<Macro> {
    private List<Macro> macros = new ArrayList<Macro>();

    @Override
    public void init() {
    }

    @Override
    public Macros fromTag(NbtCompound NbtCompound2) {
        for (Macro macro : this.macros) {
            MeteorClient.EVENT_BUS.unsubscribe(macro);
        }
        this.macros = NbtUtils.listFromTag(NbtCompound2.getList("macros", 10), Macros::lambda$fromTag$0);
        for (Macro macro : this.macros) {
            MeteorClient.EVENT_BUS.subscribe(macro);
        }
        return this;
    }

    public static Macros get() {
        return Systems.get(Macros.class);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.put("macros", (NbtElement)NbtUtils.listToTag(this.macros));
        return NbtCompound2;
    }

    public List<Macro> getAll() {
        return this.macros;
    }

    public void remove(Macro macro) {
        if (this.macros.remove(macro)) {
            MeteorClient.EVENT_BUS.unsubscribe(macro);
            this.save();
        }
    }

    private static Macro lambda$fromTag$0(NbtElement NbtElement2) {
        return new Macro().fromTag((NbtCompound)NbtElement2);
    }

    @Override
    public Iterator<Macro> iterator() {
        return this.macros.iterator();
    }

    public void add(Macro macro) {
        this.macros.add(macro);
        MeteorClient.EVENT_BUS.subscribe(macro);
        this.save();
    }

    public Macros() {
        super("macros");
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }
}

