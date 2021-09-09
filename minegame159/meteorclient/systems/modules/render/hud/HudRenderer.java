/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud;

import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.utils.render.color.Color;

public class HudRenderer {
    public /* synthetic */ double delta;
    private final /* synthetic */ List<Runnable> postTasks;

    public void begin(double llllllllllllllllIlIlIllllIlIllIl, double llllllllllllllllIlIlIllllIlIlIII, boolean llllllllllllllllIlIlIllllIlIlIll) {
        TextRenderer.get().begin(llllllllllllllllIlIlIllllIlIllIl, llllllllllllllllIlIlIllllIlIlIll, false);
        llllllllllllllllIlIlIllllIlIlllI.delta = llllllllllllllllIlIlIllllIlIlIII;
    }

    public void end() {
        HudRenderer llllllllllllllllIlIlIllllIlIIIIl;
        TextRenderer.get().end();
        for (Runnable llllllllllllllllIlIlIllllIlIIIll : llllllllllllllllIlIlIllllIlIIIIl.postTasks) {
            llllllllllllllllIlIlIllllIlIIIll.run();
        }
        llllllllllllllllIlIlIllllIlIIIIl.postTasks.clear();
    }

    public double textHeight() {
        return TextRenderer.get().getHeight();
    }

    public void text(String llllllllllllllllIlIlIllllIIlIlIl, double llllllllllllllllIlIlIllllIIllIII, double llllllllllllllllIlIlIllllIIlIIll, Color llllllllllllllllIlIlIllllIIlIIlI) {
        TextRenderer.get().render(llllllllllllllllIlIlIllllIIlIlIl, llllllllllllllllIlIlIllllIIllIII, llllllllllllllllIlIlIllllIIlIIll, llllllllllllllllIlIlIllllIIlIIlI, true);
    }

    public void addPostTask(Runnable llllllllllllllllIlIlIllllIIIIlll) {
        HudRenderer llllllllllllllllIlIlIllllIIIlIlI;
        llllllllllllllllIlIlIllllIIIlIlI.postTasks.add(llllllllllllllllIlIlIllllIIIIlll);
    }

    public double textWidth(String llllllllllllllllIlIlIllllIIIlllI) {
        return TextRenderer.get().getWidth(llllllllllllllllIlIlIllllIIIlllI);
    }

    public HudRenderer() {
        HudRenderer llllllllllllllllIlIlIllllIllIIll;
        llllllllllllllllIlIlIllllIllIIll.postTasks = new ArrayList<Runnable>();
    }
}

