/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.Screen
 */
package minegame159.meteorclient.gui.themes.meteor.widgets;

import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.themes.meteor.MeteorGuiTheme;
import minegame159.meteorclient.gui.themes.meteor.MeteorWidget;
import minegame159.meteorclient.gui.utils.AlignmentX;
import minegame159.meteorclient.gui.widgets.pressable.WPressable;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.client.gui.screen.Screen;

public class WMeteorModule
extends WPressable
implements MeteorWidget {
    private /* synthetic */ double animationProgress2;
    private final /* synthetic */ Module module;
    private /* synthetic */ double animationProgress1;
    private /* synthetic */ double titleWidth;

    @Override
    protected void onPressed(int llllllllllllllllIllIIIlIllIIllll) {
        WMeteorModule llllllllllllllllIllIIIlIllIlIIII;
        if (llllllllllllllllIllIIIlIllIIllll == 0) {
            llllllllllllllllIllIIIlIllIlIIII.module.doAction(Utils.canUpdate());
        } else if (llllllllllllllllIllIIIlIllIIllll == 1) {
            Utils.mc.openScreen((Screen)llllllllllllllllIllIIIlIllIlIIII.theme.moduleScreen(llllllllllllllllIllIIIlIllIlIIII.module));
        }
    }

    @Override
    protected void onRender(GuiRenderer llllllllllllllllIllIIIlIllIIIllI, double llllllllllllllllIllIIIlIllIIIlIl, double llllllllllllllllIllIIIlIllIIIlII, double llllllllllllllllIllIIIlIlIllllII) {
        WMeteorModule llllllllllllllllIllIIIlIlIlllllI;
        MeteorGuiTheme llllllllllllllllIllIIIlIllIIIIlI = llllllllllllllllIllIIIlIlIlllllI.theme();
        double llllllllllllllllIllIIIlIllIIIIIl = llllllllllllllllIllIIIlIlIlllllI.pad();
        llllllllllllllllIllIIIlIlIlllllI.animationProgress1 += llllllllllllllllIllIIIlIlIllllII * 4.0 * (double)(llllllllllllllllIllIIIlIlIlllllI.module.isActive() || llllllllllllllllIllIIIlIlIlllllI.mouseOver ? 1 : -1);
        llllllllllllllllIllIIIlIlIlllllI.animationProgress1 = Utils.clamp(llllllllllllllllIllIIIlIlIlllllI.animationProgress1, 0.0, 1.0);
        llllllllllllllllIllIIIlIlIlllllI.animationProgress2 += llllllllllllllllIllIIIlIlIllllII * 6.0 * (double)(llllllllllllllllIllIIIlIlIlllllI.module.isActive() ? 1 : -1);
        llllllllllllllllIllIIIlIlIlllllI.animationProgress2 = Utils.clamp(llllllllllllllllIllIIIlIlIlllllI.animationProgress2, 0.0, 1.0);
        if (llllllllllllllllIllIIIlIlIlllllI.animationProgress1 > 0.0) {
            llllllllllllllllIllIIIlIllIIIllI.quad(llllllllllllllllIllIIIlIlIlllllI.x, llllllllllllllllIllIIIlIlIlllllI.y, llllllllllllllllIllIIIlIlIlllllI.width * llllllllllllllllIllIIIlIlIlllllI.animationProgress1, llllllllllllllllIllIIIlIlIlllllI.height, llllllllllllllllIllIIIlIllIIIIlI.moduleBackground.get());
        }
        if (llllllllllllllllIllIIIlIlIlllllI.animationProgress2 > 0.0) {
            llllllllllllllllIllIIIlIllIIIllI.quad(llllllllllllllllIllIIIlIlIlllllI.x, llllllllllllllllIllIIIlIlIlllllI.y + llllllllllllllllIllIIIlIlIlllllI.height * (1.0 - llllllllllllllllIllIIIlIlIlllllI.animationProgress2), llllllllllllllllIllIIIlIllIIIIlI.scale(2.0), llllllllllllllllIllIIIlIlIlllllI.height * llllllllllllllllIllIIIlIlIlllllI.animationProgress2, llllllllllllllllIllIIIlIllIIIIlI.accentColor.get());
        }
        double llllllllllllllllIllIIIlIllIIIIII = llllllllllllllllIllIIIlIlIlllllI.x + llllllllllllllllIllIIIlIllIIIIIl;
        double llllllllllllllllIllIIIlIlIllllll = llllllllllllllllIllIIIlIlIlllllI.width - llllllllllllllllIllIIIlIllIIIIIl * 2.0;
        if (llllllllllllllllIllIIIlIllIIIIlI.moduleAlignment.get() == AlignmentX.Center) {
            llllllllllllllllIllIIIlIllIIIIII += llllllllllllllllIllIIIlIlIllllll / 2.0 - llllllllllllllllIllIIIlIlIlllllI.titleWidth / 2.0;
        } else if (llllllllllllllllIllIIIlIllIIIIlI.moduleAlignment.get() == AlignmentX.Right) {
            llllllllllllllllIllIIIlIllIIIIII += llllllllllllllllIllIIIlIlIllllll - llllllllllllllllIllIIIlIlIlllllI.titleWidth;
        }
        llllllllllllllllIllIIIlIllIIIllI.text(llllllllllllllllIllIIIlIlIlllllI.module.title, llllllllllllllllIllIIIlIllIIIIII, llllllllllllllllIllIIIlIlIlllllI.y + llllllllllllllllIllIIIlIllIIIIIl, llllllllllllllllIllIIIlIllIIIIlI.textColor.get(), false);
    }

    public WMeteorModule(Module llllllllllllllllIllIIIlIllIllllI) {
        WMeteorModule llllllllllllllllIllIIIlIllIlllll;
        llllllllllllllllIllIIIlIllIlllll.module = llllllllllllllllIllIIIlIllIllllI;
        if (llllllllllllllllIllIIIlIllIllllI.isActive()) {
            llllllllllllllllIllIIIlIllIlllll.animationProgress1 = 1.0;
            llllllllllllllllIllIIIlIllIlllll.animationProgress2 = 1.0;
        } else {
            llllllllllllllllIllIIIlIllIlllll.animationProgress1 = 0.0;
            llllllllllllllllIllIIIlIllIlllll.animationProgress2 = 0.0;
        }
    }

    @Override
    public double pad() {
        WMeteorModule llllllllllllllllIllIIIlIllIlllII;
        return llllllllllllllllIllIIIlIllIlllII.theme.scale(4.0);
    }

    @Override
    protected void onCalculateSize() {
        WMeteorModule llllllllllllllllIllIIIlIllIlIllI;
        double llllllllllllllllIllIIIlIllIlIlll = llllllllllllllllIllIIIlIllIlIllI.pad();
        if (llllllllllllllllIllIIIlIllIlIllI.titleWidth == 0.0) {
            llllllllllllllllIllIIIlIllIlIllI.titleWidth = llllllllllllllllIllIIIlIllIlIllI.theme.textWidth(llllllllllllllllIllIIIlIllIlIllI.module.title);
        }
        llllllllllllllllIllIIIlIllIlIllI.width = llllllllllllllllIllIIIlIllIlIlll + llllllllllllllllIllIIIlIllIlIllI.titleWidth + llllllllllllllllIllIIIlIllIlIlll;
        llllllllllllllllIllIIIlIllIlIllI.height = llllllllllllllllIllIIIlIllIlIlll + llllllllllllllllIllIIIlIllIlIllI.theme.textHeight() + llllllllllllllllIllIIIlIllIlIlll;
    }
}

