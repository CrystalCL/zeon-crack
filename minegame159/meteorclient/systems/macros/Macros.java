/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
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

public class Macros
extends System<Macros>
implements Iterable<Macro> {
    private /* synthetic */ List<Macro> macros;

    @Override
    public Macros fromTag(NbtCompound llIIlIIlllIIIII) {
        Macros llIIlIIlllIIIll;
        for (Macro llIIlIIlllIIlIl : llIIlIIlllIIIll.macros) {
            MeteorClient.EVENT_BUS.unsubscribe(llIIlIIlllIIlIl);
        }
        llIIlIIlllIIIll.macros = NbtUtils.listFromTag(llIIlIIlllIIIII.getList("macros", 10), llIIlIIllIlIlll -> new Macro().fromTag((NbtCompound)llIIlIIllIlIlll));
        for (Macro llIIlIIlllIIlII : llIIlIIlllIIIll.macros) {
            MeteorClient.EVENT_BUS.subscribe(llIIlIIlllIIlII);
        }
        return llIIlIIlllIIIll;
    }

    @Override
    public NbtCompound toTag() {
        Macros llIIlIIlllIllIl;
        NbtCompound llIIlIIlllIllII = new NbtCompound();
        llIIlIIlllIllII.put("macros", (NbtElement)NbtUtils.listToTag(llIIlIIlllIllIl.macros));
        return llIIlIIlllIllII;
    }

    @Override
    public Iterator<Macro> iterator() {
        Macros llIIlIIllllIIIl;
        return llIIlIIllllIIIl.macros.iterator();
    }

    public List<Macro> getAll() {
        Macros llIIlIIlllllIIl;
        return llIIlIIlllllIIl.macros;
    }

    public void remove(Macro llIIlIIllllIlIl) {
        Macros llIIlIIllllIllI;
        if (llIIlIIllllIllI.macros.remove(llIIlIIllllIlIl)) {
            MeteorClient.EVENT_BUS.unsubscribe(llIIlIIllllIlIl);
            llIIlIIllllIllI.save();
        }
    }

    public Macros() {
        super("macros");
        Macros llIIlIlIIIIIlII;
        llIIlIlIIIIIlII.macros = new ArrayList<Macro>();
    }

    public void add(Macro llIIlIIllllllII) {
        Macros llIIlIIllllllll;
        llIIlIIllllllll.macros.add(llIIlIIllllllII);
        MeteorClient.EVENT_BUS.subscribe(llIIlIIllllllII);
        llIIlIIllllllll.save();
    }

    @Override
    public void init() {
    }

    public static Macros get() {
        return Systems.get(Macros.class);
    }
}

