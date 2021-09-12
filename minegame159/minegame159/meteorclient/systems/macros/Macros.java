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
import net.minecraft.class_2487;
import net.minecraft.class_2520;

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
    public Macros fromTag(class_2487 class_24872) {
        for (Macro macro : this.macros) {
            MeteorClient.EVENT_BUS.unsubscribe(macro);
        }
        this.macros = NbtUtils.listFromTag(class_24872.method_10554("macros", 10), Macros::lambda$fromTag$0);
        for (Macro macro : this.macros) {
            MeteorClient.EVENT_BUS.subscribe(macro);
        }
        return this;
    }

    public static Macros get() {
        return Systems.get(Macros.class);
    }

    @Override
    public class_2487 toTag() {
        class_2487 class_24872 = new class_2487();
        class_24872.method_10566("macros", (class_2520)NbtUtils.listToTag(this.macros));
        return class_24872;
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

    private static Macro lambda$fromTag$0(class_2520 class_25202) {
        return new Macro().fromTag((class_2487)class_25202);
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
    public Object fromTag(class_2487 class_24872) {
        return this.fromTag(class_24872);
    }
}

