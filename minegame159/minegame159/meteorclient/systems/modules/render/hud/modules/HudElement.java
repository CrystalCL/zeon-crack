/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.gui.screens.HudElementScreen;
import minegame159.meteorclient.gui.tabs.builtin.HudTab;
import minegame159.meteorclient.settings.Settings;
import minegame159.meteorclient.systems.modules.render.hud.BoundingBox;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.ISerializable;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_310;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class HudElement
implements ISerializable<HudElement> {
    public final String name;
    public final Settings settings = new Settings();
    public boolean active = true;
    protected final class_310 mc;
    protected final HUD hud;
    public final BoundingBox box = new BoundingBox();
    public final String description;
    public final String title;

    @Override
    public Object fromTag(class_2487 class_24872) {
        return this.fromTag(class_24872);
    }

    public abstract void update(HudRenderer var1);

    @Override
    public class_2487 toTag() {
        class_2487 class_24872 = new class_2487();
        class_24872.method_10582("name", this.name);
        class_24872.method_10556("active", this.active);
        class_24872.method_10566("settings", (class_2520)this.settings.toTag());
        class_24872.method_10566("box", (class_2520)this.box.toTag());
        return class_24872;
    }

    public void toggle() {
        this.active = !this.active;
    }

    public HudElement(HUD hUD, String string, String string2) {
        this.hud = hUD;
        this.name = string;
        this.title = Utils.nameToTitle(string);
        this.description = string2;
        this.mc = class_310.method_1551();
    }

    public abstract void render(HudRenderer var1);

    @Override
    public HudElement fromTag(class_2487 class_24872) {
        this.active = class_24872.method_10577("active");
        if (class_24872.method_10545("settings")) {
            this.settings.fromTag(class_24872.method_10562("settings"));
        }
        this.box.fromTag(class_24872.method_10562("box"));
        return this;
    }

    protected boolean isInEditor() {
        return HudTab.INSTANCE.isScreen(this.mc.field_1755) || this.mc.field_1755 instanceof HudElementScreen || !Utils.canUpdate();
    }
}

