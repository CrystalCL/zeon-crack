/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.misc;

import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;

public class BypassDeathScreen
extends Module {
    public /* synthetic */ boolean shouldBypass;

    public BypassDeathScreen() {
        super(Categories.Misc, "bypass-death-screen", "Lets you spy on people after death.");
        BypassDeathScreen llllllllllllllllllllIIIIIIllIlII;
        llllllllllllllllllllIIIIIIllIlII.shouldBypass = false;
    }

    @Override
    public void onDeactivate() {
        BypassDeathScreen llllllllllllllllllllIIIIIIllIIIl;
        llllllllllllllllllllIIIIIIllIIIl.shouldBypass = false;
        super.onDeactivate();
    }
}

