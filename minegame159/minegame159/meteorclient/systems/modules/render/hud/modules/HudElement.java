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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.client.MinecraftClient;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class HudElement
implements ISerializable<HudElement> {
    public final String name;
    public final Settings settings = new Settings();
    public boolean active = true;
    protected final MinecraftClient mc;
    protected final HUD hud;
    public final BoundingBox box = new BoundingBox();
    public final String description;
    public final String title;

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public abstract void update(HudRenderer var1);

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("name", this.name);
        NbtCompound2.putBoolean("active", this.active);
        NbtCompound2.put("settings", (NbtElement)this.settings.toTag());
        NbtCompound2.put("box", (NbtElement)this.box.toTag());
        return NbtCompound2;
    }

    public void toggle() {
        this.active = !this.active;
    }

    public HudElement(HUD hUD, String string, String string2) {
        this.hud = hUD;
        this.name = string;
        this.title = Utils.nameToTitle(string);
        this.description = string2;
        this.mc = MinecraftClient.getInstance();
    }

    public abstract void render(HudRenderer var1);

    @Override
    public HudElement fromTag(NbtCompound NbtCompound2) {
        this.active = NbtCompound2.getBoolean("active");
        if (NbtCompound2.contains("settings")) {
            this.settings.fromTag(NbtCompound2.getCompound("settings"));
        }
        this.box.fromTag(NbtCompound2.getCompound("box"));
        return this;
    }

    protected boolean isInEditor() {
        return HudTab.INSTANCE.isScreen(this.mc.currentScreen) || this.mc.currentScreen instanceof HudElementScreen || !Utils.canUpdate();
    }
}

