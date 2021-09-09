/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package minegame159.meteorclient.gui.renderer;

import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.utils.Utils;
import org.lwjgl.opengl.GL11;

public class Scissor {
    public /* synthetic */ int y;
    public /* synthetic */ int height;
    public /* synthetic */ int x;
    public final /* synthetic */ List<Runnable> postTasks;
    public /* synthetic */ int width;

    public Scissor set(double llllllllllllllllIlIllllIIlllIIll, double llllllllllllllllIlIllllIIlllIIlI, double llllllllllllllllIlIllllIIlllIIIl, double llllllllllllllllIlIllllIIlllIIII) {
        Scissor llllllllllllllllIlIllllIIllIllll;
        if (llllllllllllllllIlIllllIIlllIIIl < 0.0) {
            llllllllllllllllIlIllllIIlllIIIl = 0.0;
        }
        if (llllllllllllllllIlIllllIIlllIIII < 0.0) {
            llllllllllllllllIlIllllIIlllIIII = 0.0;
        }
        llllllllllllllllIlIllllIIllIllll.x = (int)Math.round(llllllllllllllllIlIllllIIlllIIll);
        llllllllllllllllIlIllllIIllIllll.y = (int)Math.round(llllllllllllllllIlIllllIIlllIIlI);
        llllllllllllllllIlIllllIIllIllll.width = (int)Math.round(llllllllllllllllIlIllllIIlllIIIl);
        llllllllllllllllIlIllllIIllIllll.height = (int)Math.round(llllllllllllllllIlIllllIIlllIIII);
        llllllllllllllllIlIllllIIllIllll.postTasks.clear();
        return llllllllllllllllIlIllllIIllIllll;
    }

    public Scissor() {
        Scissor llllllllllllllllIlIllllIIllllIll;
        llllllllllllllllIlIllllIIllllIll.postTasks = new ArrayList<Runnable>();
    }

    public void apply() {
        Scissor llllllllllllllllIlIllllIIllIlIIl;
        GL11.glScissor((int)llllllllllllllllIlIllllIIllIlIIl.x, (int)(Utils.getWindowHeight() - llllllllllllllllIlIllllIIllIlIIl.y - llllllllllllllllIlIllllIIllIlIIl.height), (int)llllllllllllllllIlIllllIIllIlIIl.width, (int)llllllllllllllllIlIllllIIllIlIIl.height);
    }
}

