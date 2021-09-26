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
import net.minecraft.util.Formatting;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.client.MinecraftClient;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class Module
implements ISerializable<Module> {
    public boolean serialize = true;
    private boolean active;
    public boolean toggleOnBindRelease = false;
    protected final MinecraftClient mc;
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
            ChatUtils.info(42069, "Toggled (highlight)%s(default) %s(default).", this.title, this.isActive() ? String.valueOf(new StringBuilder().append(Formatting.GREEN).append("on")) : String.valueOf(new StringBuilder().append(Formatting.RED).append("off")));
        }
    }

    @Override
    public NbtCompound toTag() {
        if (!this.serialize) {
            return null;
        }
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("name", this.name);
        NbtCompound2.put("keybind", (NbtElement)this.keybind.toTag());
        NbtCompound2.putBoolean("toggleOnKeyRelease", this.toggleOnBindRelease);
        NbtCompound2.put("settings", (NbtElement)this.settings.toTag());
        NbtCompound2.putBoolean("active", this.active);
        NbtCompound2.putBoolean("visible", this.visible);
        return NbtCompound2;
    }

    public void doAction() {
        this.doAction(true);
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public Module fromTag(NbtCompound NbtCompound2) {
        boolean bl;
        if (NbtCompound2.contains("key")) {
            this.keybind.set(true, NbtCompound2.getInt("key"));
        } else {
            this.keybind.fromTag(NbtCompound2.getCompound("keybind"));
        }
        this.toggleOnBindRelease = NbtCompound2.getBoolean("toggleOnKeyRelease");
        NbtElement NbtElement2 = NbtCompound2.get("settings");
        if (NbtElement2 instanceof NbtCompound) {
            this.settings.fromTag((NbtCompound)NbtElement2);
        }
        if ((bl = NbtCompound2.getBoolean("active")) != this.isActive()) {
            this.toggle(Utils.canUpdate());
        }
        this.setVisible(NbtCompound2.getBoolean("visible"));
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
        this.mc = MinecraftClient.getInstance();
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

