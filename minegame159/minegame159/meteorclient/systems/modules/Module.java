/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules;

import java.util.Objects;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.meteor.ModuleVisibilityChangedEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.settings.Settings;
import minegame159.meteorclient.systems.config.Config;
import minegame159.meteorclient.systems.modules.Category;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.misc.Keybind;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.class_124;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_310;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class Module
implements ISerializable<Module> {
    public boolean serialize = true;
    private boolean active;
    public boolean toggleOnBindRelease = false;
    protected final class_310 mc;
    public final String description;
    public final String title;
    public final Keybind keybind;
    private boolean visible = true;
    public final Color color;
    public final String name;
    public final Category category;
    public final Settings settings = new Settings();

    public String getInfoString() {
        return null;
    }

    public void toggle(boolean bl) {
        if (!this.active) {
            this.active = true;
            Modules.get().addActive(this);
            this.settings.onActivated();
            if (bl) {
                MeteorClient.EVENT_BUS.subscribe(this);
                this.onActivate();
            }
        } else {
            this.active = false;
            Modules.get().removeActive(this);
            if (bl) {
                MeteorClient.EVENT_BUS.unsubscribe(this);
                this.onDeactivate();
            }
        }
    }

    public void sendToggledMsg() {
        if (Config.get().chatCommandsInfo) {
            ChatUtils.info(42069, "Toggled (highlight)%s(default) %s(default).", this.title, this.isActive() ? String.valueOf(new StringBuilder().append(class_124.field_1060).append("on")) : String.valueOf(new StringBuilder().append(class_124.field_1061).append("off")));
        }
    }

    @Override
    public class_2487 toTag() {
        if (!this.serialize) {
            return null;
        }
        class_2487 class_24872 = new class_2487();
        class_24872.method_10582("name", this.name);
        class_24872.method_10566("keybind", (class_2520)this.keybind.toTag());
        class_24872.method_10556("toggleOnKeyRelease", this.toggleOnBindRelease);
        class_24872.method_10566("settings", (class_2520)this.settings.toTag());
        class_24872.method_10556("active", this.active);
        class_24872.method_10556("visible", this.visible);
        return class_24872;
    }

    public void doAction() {
        this.doAction(true);
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public Object fromTag(class_2487 class_24872) {
        return this.fromTag(class_24872);
    }

    @Override
    public Module fromTag(class_2487 class_24872) {
        boolean bl;
        if (class_24872.method_10545("key")) {
            this.keybind.set(true, class_24872.method_10550("key"));
        } else {
            this.keybind.fromTag(class_24872.method_10562("keybind"));
        }
        this.toggleOnBindRelease = class_24872.method_10577("toggleOnKeyRelease");
        class_2520 class_25202 = class_24872.method_10580("settings");
        if (class_25202 instanceof class_2487) {
            this.settings.fromTag((class_2487)class_25202);
        }
        if ((bl = class_24872.method_10577("active")) != this.isActive()) {
            this.toggle(Utils.canUpdate());
        }
        this.setVisible(class_24872.method_10577("visible"));
        return this;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Module module = (Module)object;
        return Objects.equals(this.name, module.name);
    }

    public int hashCode() {
        return Objects.hash(this.name);
    }

    public void onDeactivate() {
    }

    public void toggle() {
        this.toggle(true);
    }

    public Module(Category category, String string, String string2) {
        this.keybind = Keybind.fromKey(-1);
        this.mc = class_310.method_1551();
        this.category = category;
        this.name = string;
        this.title = Utils.nameToTitle(string);
        this.description = string2;
        this.color = Color.fromHsv(Utils.random(0.0, 360.0), 0.35, 1.0);
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void doAction(boolean bl) {
        this.toggle(bl);
    }

    public void onActivate() {
    }

    public WWidget getWidget(GuiTheme guiTheme) {
        return null;
    }

    public void setVisible(boolean bl) {
        this.visible = bl;
        MeteorClient.EVENT_BUS.post(ModuleVisibilityChangedEvent.get(this));
    }
}

