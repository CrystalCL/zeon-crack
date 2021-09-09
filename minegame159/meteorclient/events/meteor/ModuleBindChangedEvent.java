/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.meteor;

import minegame159.meteorclient.systems.modules.Module;

public class ModuleBindChangedEvent {
    public /* synthetic */ Module module;
    private static final /* synthetic */ ModuleBindChangedEvent INSTANCE;

    public static ModuleBindChangedEvent get(Module lllllllllllllllllIIIIlIlIIIlllIl) {
        ModuleBindChangedEvent.INSTANCE.module = lllllllllllllllllIIIIlIlIIIlllIl;
        return INSTANCE;
    }

    public ModuleBindChangedEvent() {
        ModuleBindChangedEvent lllllllllllllllllIIIIlIlIIlIIIII;
    }

    static {
        INSTANCE = new ModuleBindChangedEvent();
    }
}

