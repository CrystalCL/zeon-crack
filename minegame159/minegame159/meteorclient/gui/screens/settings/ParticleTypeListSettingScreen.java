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
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class ParticleTypeListSettingScreen
extends LeftRightListSettingScreen<ParticleType<?>> {
    @Override
    protected boolean skipValue(Object object) {
        return this.skipValue((ParticleType)object);
    }

    @Override
    protected WWidget getValueWidget(Object object) {
        return this.getValueWidget((ParticleType)object);
    }

    @Override
    protected WWidget getValueWidget(ParticleType<?> ParticleType2) {
        return this.theme.label(this.getValueName(ParticleType2));
    }

    @Override
    protected String getValueName(Object object) {
        return this.getValueName((ParticleType)object);
    }

    @Override
    protected String getValueName(ParticleType<?> ParticleType2) {
        return Names.get(ParticleType2);
    }

    @Override
    protected boolean skipValue(ParticleType<?> ParticleType2) {
        return !(ParticleType2 instanceof ParticleEffect);
    }

    public ParticleTypeListSettingScreen(GuiTheme guiTheme, Setting<List<ParticleType<?>>> setting) {
        super(guiTheme, "Select particles", setting, Registry.PARTICLE_TYPE);
    }
}

