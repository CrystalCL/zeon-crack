/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.particle.ParticleEffect
 *  net.minecraft.particle.ParticleType
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
    public ParticleTypeListSettingScreen(GuiTheme lIlIlllIIIIllII, Setting<List<ParticleType<?>>> lIlIlllIIIIlIll) {
        super(lIlIlllIIIIllII, "Select particles", lIlIlllIIIIlIll, Registry.PARTICLE_TYPE);
        ParticleTypeListSettingScreen lIlIlllIIIIlIlI;
    }

    @Override
    protected boolean skipValue(ParticleType<?> lIlIllIlllllIlI) {
        return !(lIlIllIlllllIlI instanceof ParticleEffect);
    }

    @Override
    protected WWidget getValueWidget(ParticleType<?> lIlIlllIIIIIIlI) {
        ParticleTypeListSettingScreen lIlIlllIIIIIIll;
        return lIlIlllIIIIIIll.theme.label(lIlIlllIIIIIIll.getValueName(lIlIlllIIIIIIlI));
    }

    @Override
    protected String getValueName(ParticleType<?> lIlIllIllllllll) {
        return Names.get(lIlIllIllllllll);
    }
}

