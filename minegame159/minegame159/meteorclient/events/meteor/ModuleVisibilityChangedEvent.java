/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.meteor;

import minegame159.meteorclient.systems.modules.Module;

public class ModuleVisibilityChangedEvent {
    public Module module;
    private static final ModuleVisibilityChangedEvent INSTANCE = new ModuleVisibilityChangedEvent();

    public static ModuleVisibilityChangedEvent get(Module module) {
        ModuleVisibilityChangedEvent.INSTANCE.module = module;
        return INSTANCE;
    }
}

