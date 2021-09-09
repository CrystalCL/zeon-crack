/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.client.MinecraftClient
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

public abstract class HudElement
implements ISerializable<HudElement> {
    public final /* synthetic */ String name;
    public final /* synthetic */ BoundingBox box;
    public final /* synthetic */ String title;
    public final /* synthetic */ String description;
    public /* synthetic */ boolean active;
    public final /* synthetic */ Settings settings;
    protected final /* synthetic */ HUD hud;
    protected final /* synthetic */ MinecraftClient mc;

    public HudElement(HUD llIlIllIIIllll, String llIlIllIIlIIlI, String llIlIllIIIllIl) {
        HudElement llIlIllIIlIlII;
        llIlIllIIlIlII.active = true;
        llIlIllIIlIlII.settings = new Settings();
        llIlIllIIlIlII.box = new BoundingBox();
        llIlIllIIlIlII.hud = llIlIllIIIllll;
        llIlIllIIlIlII.name = llIlIllIIlIIlI;
        llIlIllIIlIlII.title = Utils.nameToTitle(llIlIllIIlIIlI);
        llIlIllIIlIlII.description = llIlIllIIIllIl;
        llIlIllIIlIlII.mc = MinecraftClient.getInstance();
    }

    @Override
    public NbtCompound toTag() {
        HudElement llIlIllIIIIlII;
        NbtCompound llIlIllIIIIIll = new NbtCompound();
        llIlIllIIIIIll.putString("name", llIlIllIIIIlII.name);
        llIlIllIIIIIll.putBoolean("active", llIlIllIIIIlII.active);
        llIlIllIIIIIll.put("settings", (NbtElement)llIlIllIIIIlII.settings.toTag());
        llIlIllIIIIIll.put("box", (NbtElement)llIlIllIIIIlII.box.toTag());
        return llIlIllIIIIIll;
    }

    public void toggle() {
        HudElement llIlIllIIIlIll;
        llIlIllIIIlIll.active = !llIlIllIIIlIll.active;
    }

    public abstract void render(HudRenderer var1);

    @Override
    public HudElement fromTag(NbtCompound llIlIlIlllllIl) {
        HudElement llIlIlIlllllII;
        llIlIlIlllllII.active = llIlIlIlllllIl.getBoolean("active");
        if (llIlIlIlllllIl.contains("settings")) {
            llIlIlIlllllII.settings.fromTag(llIlIlIlllllIl.getCompound("settings"));
        }
        llIlIlIlllllII.box.fromTag(llIlIlIlllllIl.getCompound("box"));
        return llIlIlIlllllII;
    }

    public abstract void update(HudRenderer var1);

    protected boolean isInEditor() {
        HudElement llIlIllIIIIlll;
        return HudTab.INSTANCE.isScreen(llIlIllIIIIlll.mc.currentScreen) || llIlIllIIIIlll.mc.currentScreen instanceof HudElementScreen || !Utils.canUpdate();
    }
}

