/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.meteor;

import minegame159.meteorclient.systems.modules.Module;

public class ModuleVisibilityChangedEvent {
    private static final /* synthetic */ ModuleVisibilityChangedEvent INSTANCE;
    public /* synthetic */ Module module;

    public ModuleVisibilityChangedEvent() {
        ModuleVisibilityChangedEvent lIIIllIlllIlIll;
    }

    public static ModuleVisibilityChangedEvent get(Module lIIIllIlllIlIII) {
        ModuleVisibilityChangedEvent.INSTANCE.module = lIIIllIlllIlIII;
        return INSTANCE;
    }

    static {
        INSTANCE = new ModuleVisibilityChangedEvent();
    }
}

