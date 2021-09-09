/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud;

import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.render.AlignmentX;
import minegame159.meteorclient.utils.render.AlignmentY;

public class HudElementLayer {
    private final /* synthetic */ int xOffset;
    private final /* synthetic */ List<HudElement> allElements;
    private final /* synthetic */ HudRenderer renderer;
    private final /* synthetic */ AlignmentX xAlign;
    private final /* synthetic */ int yOffset;
    private final /* synthetic */ List<HudElement> elements;
    private final /* synthetic */ AlignmentY yAlign;

    public void align() {
        HudElementLayer llIIlIlIIIlllll;
        double llIIlIlIIIllllI = llIIlIlIIIlllll.xOffset * (llIIlIlIIIlllll.xAlign == AlignmentX.Right ? -1 : 1);
        double llIIlIlIIIlllIl = llIIlIlIIIlllll.yOffset * (llIIlIlIIIlllll.yAlign == AlignmentY.Bottom ? -1 : 1);
        for (HudElement llIIlIlIIlIIIII : llIIlIlIIIlllll.elements) {
            llIIlIlIIlIIIII.update(llIIlIlIIIlllll.renderer);
            llIIlIlIIlIIIII.box.x = llIIlIlIIIlllll.xAlign;
            llIIlIlIIlIIIII.box.y = llIIlIlIIIlllll.yAlign;
            llIIlIlIIlIIIII.box.xOffset = (int)Math.round(llIIlIlIIIllllI);
            llIIlIlIIlIIIII.box.yOffset = (int)Math.round(llIIlIlIIIlllIl);
            if (llIIlIlIIIlllll.yAlign == AlignmentY.Bottom) {
                llIIlIlIIIlllIl -= llIIlIlIIlIIIII.box.height;
                continue;
            }
            llIIlIlIIIlllIl += llIIlIlIIlIIIII.box.height;
        }
    }

    public void add(HudElement llIIlIlIIlIIllI) {
        HudElementLayer llIIlIlIIlIIlll;
        llIIlIlIIlIIlll.allElements.add(llIIlIlIIlIIllI);
        llIIlIlIIlIIlll.elements.add(llIIlIlIIlIIllI);
        llIIlIlIIlIIllI.settings.registerColorSettings(null);
    }

    public HudElementLayer(HudRenderer llIIlIlIIllIIIl, List<HudElement> llIIlIlIIllIIII, AlignmentX llIIlIlIIllIllI, AlignmentY llIIlIlIIllIlIl, int llIIlIlIIllIlII, int llIIlIlIIllIIll) {
        HudElementLayer llIIlIlIIllIIlI;
        llIIlIlIIllIIlI.renderer = llIIlIlIIllIIIl;
        llIIlIlIIllIIlI.allElements = llIIlIlIIllIIII;
        llIIlIlIIllIIlI.elements = new ArrayList<HudElement>();
        llIIlIlIIllIIlI.xAlign = llIIlIlIIllIllI;
        llIIlIlIIllIIlI.yAlign = llIIlIlIIllIlIl;
        llIIlIlIIllIIlI.xOffset = llIIlIlIIllIlII;
        llIIlIlIIllIIlI.yOffset = llIIlIlIIllIIll;
    }
}

