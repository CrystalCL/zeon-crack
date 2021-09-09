/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Util
 */
package minegame159.meteorclient.gui.screens;

import com.g00fy2.versioncompare.Version;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.systems.config.Config;
import net.minecraft.util.Util;

public class NewUpdateScreen
extends WindowScreen {
    public NewUpdateScreen(GuiTheme llIIIllIlIllI, Version llIIIllIllIlI) {
        super(llIIIllIlIllI, "New Update");
        NewUpdateScreen llIIIllIlIlll;
        llIIIllIlIlll.add(llIIIllIlIllI.label("A new version of Meteor has been released."));
        llIIIllIlIlll.add(llIIIllIlIllI.horizontalSeparator()).expandX();
        WTable llIIIllIllIIl = llIIIllIlIlll.add(llIIIllIlIllI.table()).widget();
        llIIIllIllIIl.add(llIIIllIlIllI.label("Your version:"));
        llIIIllIllIIl.add(llIIIllIlIllI.label(Config.get().version.getOriginalString()));
        llIIIllIllIIl.row();
        llIIIllIllIIl.add(llIIIllIlIllI.label("Latest version"));
        llIIIllIllIIl.add(llIIIllIlIllI.label(llIIIllIllIlI.getOriginalString()));
        llIIIllIlIlll.add(llIIIllIlIllI.horizontalSeparator()).expandX();
        WHorizontalList llIIIllIllIII = llIIIllIlIlll.add(llIIIllIlIllI.horizontalList()).widget();
        llIIIllIllIII.add(llIIIllIlIllI.button((String)String.valueOf((Object)new StringBuilder().append((String)"Download ").append((String)llIIIllIllIlI.getOriginalString())))).expandX().widget().action = () -> Util.getOperatingSystem().open("http://meteorclient.com/");
        llIIIllIllIII.add(llIIIllIlIllI.button((String)"OK")).expandX().widget().action = llIIIllIlIlll::onClose;
    }
}

