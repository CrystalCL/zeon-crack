/*
 * Decompiled with CFR 0.151.
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
    protected WWidget getValueWidget(SoundEvent SoundEvent2) {
        return this.theme.label(this.getValueName(SoundEvent2));
    }

    @Override
    protected WWidget getValueWidget(Object object) {
        return this.getValueWidget((SoundEvent)object);
    }

    @Override
    protected String getValueName(Object object) {
        return this.getValueName((SoundEvent)object);
    }

    public SoundEventListSettingScreen(GuiTheme guiTheme, Setting<List<SoundEvent>> setting) {
        super(guiTheme, "Select sounds", setting, Registry.SOUND_EVENT);
    }

    @Override
    protected String getValueName(SoundEvent SoundEvent2) {
        return Names.getSoundName(SoundEvent2.getId());
    }
}

