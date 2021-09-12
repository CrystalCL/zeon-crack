/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.misc;

import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class BypassDeathScreen
extends Module {
    public boolean shouldBypass = false;

    @Override
    public void onDeactivate() {
        this.shouldBypass = false;
        super.onDeactivate();
    }

    public BypassDeathScreen() {
        super(Categories.Misc, "bypass-death-screen", "Lets you spy on people after death.");
    }
}

