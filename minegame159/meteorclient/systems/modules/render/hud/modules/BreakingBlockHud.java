/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.mixin.ClientPlayerInteractionManagerAccessor;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;

public class BreakingBlockHud
extends DoubleTextHudElement {
    public BreakingBlockHud(HUD llllllllllllllllllIllIlllIIlIlIl) {
        super(llllllllllllllllllIllIlllIIlIlIl, "breaking-block", "Displays percentage of the block you are breaking.", "Breaking Block: ");
        BreakingBlockHud llllllllllllllllllIllIlllIIlIlII;
    }

    @Override
    protected String getRight() {
        BreakingBlockHud llllllllllllllllllIllIlllIIlIIII;
        if (llllllllllllllllllIllIlllIIlIIII.isInEditor()) {
            return "0%";
        }
        return String.format("%.0f%%", Float.valueOf(((ClientPlayerInteractionManagerAccessor)llllllllllllllllllIllIlllIIlIIII.mc.interactionManager).getBreakingProgress() * 100.0f));
    }
}

