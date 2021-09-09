/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Formatting
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.client.MinecraftClient
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

public abstract class Module
implements ISerializable<Module> {
    public final /* synthetic */ String title;
    public final /* synthetic */ String description;
    private /* synthetic */ boolean active;
    protected final /* synthetic */ MinecraftClient mc;
    public final /* synthetic */ String name;
    public final /* synthetic */ Settings settings;
    public /* synthetic */ boolean serialize;
    public final /* synthetic */ Keybind keybind;
    public final /* synthetic */ Category category;
    public /* synthetic */ boolean toggleOnBindRelease;
    private /* synthetic */ boolean visible;
    public final /* synthetic */ Color color;

    public void toggle() {
        Module lllllllllllllllllIlIllIlIIIIIlII;
        lllllllllllllllllIlIllIlIIIIIlII.toggle(true);
    }

    public boolean equals(Object lllllllllllllllllIlIllIIllIlllIl) {
        Module lllllllllllllllllIlIllIIllIllllI;
        if (lllllllllllllllllIlIllIIllIllllI == lllllllllllllllllIlIllIIllIlllIl) {
            return true;
        }
        if (lllllllllllllllllIlIllIIllIlllIl == null || lllllllllllllllllIlIllIIllIllllI.getClass() != lllllllllllllllllIlIllIIllIlllIl.getClass()) {
            return false;
        }
        Module lllllllllllllllllIlIllIIllIlllII = (Module)lllllllllllllllllIlIllIIllIlllIl;
        return Objects.equals(lllllllllllllllllIlIllIIllIllllI.name, lllllllllllllllllIlIllIIllIlllII.name);
    }

    public void setVisible(boolean lllllllllllllllllIlIllIIlllIllIl) {
        Module lllllllllllllllllIlIllIIlllIllII;
        lllllllllllllllllIlIllIIlllIllII.visible = lllllllllllllllllIlIllIIlllIllIl;
        MeteorClient.EVENT_BUS.post(ModuleVisibilityChangedEvent.get(lllllllllllllllllIlIllIIlllIllII));
    }

    public WWidget getWidget(GuiTheme lllllllllllllllllIlIllIlIIIllIII) {
        return null;
    }

    public int hashCode() {
        Module lllllllllllllllllIlIllIIllIlIllI;
        return Objects.hash(lllllllllllllllllIlIllIIllIlIllI.name);
    }

    public void onActivate() {
    }

    public void sendToggledMsg() {
        if (Config.get().chatCommandsInfo) {
            Module lllllllllllllllllIlIllIIlllIIIll;
            ChatUtils.info(42069, "Toggled (highlight)%s(default) %s(default).", lllllllllllllllllIlIllIIlllIIIll.title, lllllllllllllllllIlIllIIlllIIIll.isActive() ? String.valueOf(new StringBuilder().append((Object)Formatting.GREEN).append("on")) : String.valueOf(new StringBuilder().append((Object)Formatting.RED).append("off")));
        }
    }

    public void doAction(boolean lllllllllllllllllIlIllIlIIIlIIlI) {
        Module lllllllllllllllllIlIllIlIIIlIlIl;
        lllllllllllllllllIlIllIlIIIlIlIl.toggle(lllllllllllllllllIlIllIlIIIlIIlI);
    }

    public void toggle(boolean lllllllllllllllllIlIllIlIIIIIlll) {
        Module lllllllllllllllllIlIllIlIIIIlIII;
        if (!lllllllllllllllllIlIllIlIIIIlIII.active) {
            lllllllllllllllllIlIllIlIIIIlIII.active = true;
            Modules.get().addActive(lllllllllllllllllIlIllIlIIIIlIII);
            lllllllllllllllllIlIllIlIIIIlIII.settings.onActivated();
            if (lllllllllllllllllIlIllIlIIIIIlll) {
                MeteorClient.EVENT_BUS.subscribe(lllllllllllllllllIlIllIlIIIIlIII);
                lllllllllllllllllIlIllIlIIIIlIII.onActivate();
            }
        } else {
            lllllllllllllllllIlIllIlIIIIlIII.active = false;
            Modules.get().removeActive(lllllllllllllllllIlIllIlIIIIlIII);
            if (lllllllllllllllllIlIllIlIIIIIlll) {
                MeteorClient.EVENT_BUS.unsubscribe(lllllllllllllllllIlIllIlIIIIlIII);
                lllllllllllllllllIlIllIlIIIIlIII.onDeactivate();
            }
        }
    }

    public Module(Category lllllllllllllllllIlIllIlIIIlllII, String lllllllllllllllllIlIllIlIIIllIll, String lllllllllllllllllIlIllIlIIIllllI) {
        Module lllllllllllllllllIlIllIlIIIlllIl;
        lllllllllllllllllIlIllIlIIIlllIl.settings = new Settings();
        lllllllllllllllllIlIllIlIIIlllIl.visible = true;
        lllllllllllllllllIlIllIlIIIlllIl.serialize = true;
        lllllllllllllllllIlIllIlIIIlllIl.keybind = Keybind.fromKey(-1);
        lllllllllllllllllIlIllIlIIIlllIl.toggleOnBindRelease = false;
        lllllllllllllllllIlIllIlIIIlllIl.mc = MinecraftClient.getInstance();
        lllllllllllllllllIlIllIlIIIlllIl.category = lllllllllllllllllIlIllIlIIIlllII;
        lllllllllllllllllIlIllIlIIIlllIl.name = lllllllllllllllllIlIllIlIIIllIll;
        lllllllllllllllllIlIllIlIIIlllIl.title = Utils.nameToTitle(lllllllllllllllllIlIllIlIIIllIll);
        lllllllllllllllllIlIllIlIIIlllIl.description = lllllllllllllllllIlIllIlIIIllllI;
        lllllllllllllllllIlIllIlIIIlllIl.color = Color.fromHsv(Utils.random(0.0, 360.0), 0.35, 1.0);
    }

    @Override
    public Module fromTag(NbtCompound lllllllllllllllllIlIllIIllllIlll) {
        boolean lllllllllllllllllIlIllIIllllIlIl;
        Module lllllllllllllllllIlIllIIlllllIII;
        if (lllllllllllllllllIlIllIIllllIlll.contains("key")) {
            lllllllllllllllllIlIllIIlllllIII.keybind.set(true, lllllllllllllllllIlIllIIllllIlll.getInt("key"));
        } else {
            lllllllllllllllllIlIllIIlllllIII.keybind.fromTag(lllllllllllllllllIlIllIIllllIlll.getCompound("keybind"));
        }
        lllllllllllllllllIlIllIIlllllIII.toggleOnBindRelease = lllllllllllllllllIlIllIIllllIlll.getBoolean("toggleOnKeyRelease");
        NbtElement lllllllllllllllllIlIllIIllllIllI = lllllllllllllllllIlIllIIllllIlll.get("settings");
        if (lllllllllllllllllIlIllIIllllIllI instanceof NbtCompound) {
            lllllllllllllllllIlIllIIlllllIII.settings.fromTag((NbtCompound)lllllllllllllllllIlIllIIllllIllI);
        }
        if ((lllllllllllllllllIlIllIIllllIlIl = lllllllllllllllllIlIllIIllllIlll.getBoolean("active")) != lllllllllllllllllIlIllIIlllllIII.isActive()) {
            lllllllllllllllllIlIllIIlllllIII.toggle(Utils.canUpdate());
        }
        lllllllllllllllllIlIllIIlllllIII.setVisible(lllllllllllllllllIlIllIIllllIlll.getBoolean("visible"));
        return lllllllllllllllllIlIllIIlllllIII;
    }

    public String getInfoString() {
        return null;
    }

    public void onDeactivate() {
    }

    public void doAction() {
        Module lllllllllllllllllIlIllIlIIIIllll;
        lllllllllllllllllIlIllIlIIIIllll.doAction(true);
    }

    @Override
    public NbtCompound toTag() {
        Module lllllllllllllllllIlIllIIlllllllI;
        if (!lllllllllllllllllIlIllIIlllllllI.serialize) {
            return null;
        }
        NbtCompound lllllllllllllllllIlIllIIllllllll = new NbtCompound();
        lllllllllllllllllIlIllIIllllllll.putString("name", lllllllllllllllllIlIllIIlllllllI.name);
        lllllllllllllllllIlIllIIllllllll.put("keybind", (NbtElement)lllllllllllllllllIlIllIIlllllllI.keybind.toTag());
        lllllllllllllllllIlIllIIllllllll.putBoolean("toggleOnKeyRelease", lllllllllllllllllIlIllIIlllllllI.toggleOnBindRelease);
        lllllllllllllllllIlIllIIllllllll.put("settings", (NbtElement)lllllllllllllllllIlIllIIlllllllI.settings.toTag());
        lllllllllllllllllIlIllIIllllllll.putBoolean("active", lllllllllllllllllIlIllIIlllllllI.active);
        lllllllllllllllllIlIllIIllllllll.putBoolean("visible", lllllllllllllllllIlIllIIlllllllI.visible);
        return lllllllllllllllllIlIllIIllllllll;
    }

    public boolean isVisible() {
        Module lllllllllllllllllIlIllIIlllIlIII;
        return lllllllllllllllllIlIllIIlllIlIII.visible;
    }

    public boolean isActive() {
        Module lllllllllllllllllIlIllIIlllIIlIl;
        return lllllllllllllllllIlIllIIlllIIlIl.active;
    }
}

