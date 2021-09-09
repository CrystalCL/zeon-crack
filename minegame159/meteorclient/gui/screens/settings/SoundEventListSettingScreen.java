/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.sound.SoundEvent
 */
package minegame159.meteorclient.gui.screens.settings;

import java.util.List;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.screens.settings.LeftRightListSettingScreen;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.utils.misc.Names;
import net.minecraft.util.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class SoundEventListSettingScreen
extends LeftRightListSettingScreen<SoundEvent> {
    @Override
    protected String getValueName(SoundEvent llllllllllllllllllIIllIllllIlIII) {
        return Names.getSoundName(llllllllllllllllllIIllIllllIlIII.getId());
    }

    @Override
    protected WWidget getValueWidget(SoundEvent llllllllllllllllllIIllIllllIlllI) {
        SoundEventListSettingScreen llllllllllllllllllIIllIllllIllIl;
        return llllllllllllllllllIIllIllllIllIl.theme.label(llllllllllllllllllIIllIllllIllIl.getValueName(llllllllllllllllllIIllIllllIlllI));
    }

    public SoundEventListSettingScreen(GuiTheme llllllllllllllllllIIllIlllllIllI, Setting<List<SoundEvent>> llllllllllllllllllIIllIlllllIIlI) {
        super(llllllllllllllllllIIllIlllllIllI, "Select sounds", llllllllllllllllllIIllIlllllIIlI, Registry.SOUND_EVENT);
        SoundEventListSettingScreen llllllllllllllllllIIllIlllllIlII;
    }
}

