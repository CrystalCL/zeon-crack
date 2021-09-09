/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  org.lwjgl.opengl.GL11
 */
package minegame159.meteorclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.RenderEntityEvent;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.lwjgl.opengl.GL11;

public class Chams
extends Module {
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final /* synthetic */ SettingGroup sgGeneral;

    public Chams() {
        super(Categories.Render, "chams", "Renders entities through walls.");
        Chams lllIlIlIIllll;
        lllIlIlIIllll.sgGeneral = lllIlIlIIllll.settings.getDefaultGroup();
        lllIlIlIIllll.entities = lllIlIlIIllll.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Select entities to show through walls.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(new EntityType[]{EntityType.PLAYER})).build());
    }

    @EventHandler
    private void onPostRender(RenderEntityEvent.Post lllIlIIllllII) {
        Chams lllIlIIllllIl;
        if (lllIlIIllllIl.shouldRender(lllIlIIllllII.entity)) {
            GL11.glPolygonOffset((float)1.0f, (float)1000000.0f);
            GL11.glDisable((int)32823);
        }
    }

    public boolean shouldRender(Entity lllIlIlIIlIlI) {
        Chams lllIlIlIIlIIl;
        return lllIlIlIIlIIl.isActive() && lllIlIlIIlIIl.entities.get().getBoolean((Object)lllIlIlIIlIlI.getType());
    }

    @EventHandler
    private void onPreRender(RenderEntityEvent.Pre lllIlIlIIIlII) {
        Chams lllIlIlIIIIll;
        if (lllIlIlIIIIll.shouldRender(lllIlIlIIIlII.entity)) {
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)-1000000.0f);
        }
    }
}

