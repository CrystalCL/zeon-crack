/*
 * Decompiled with CFR 0.151.
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
    private final SettingGroup sgGeneral;
    private final Setting<Object2BooleanMap<EntityType<?>>> entities;

    public Chams() {
        super(Categories.Render, "chams", "Renders entities through walls.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Select entities to show through walls.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(EntityType.PLAYER)).build());
    }

    @EventHandler
    private void onPostRender(RenderEntityEvent.Post post) {
        if (this.shouldRender(post.entity)) {
            GL11.glPolygonOffset((float)1.0f, (float)1000000.0f);
            GL11.glDisable((int)32823);
        }
    }

    @EventHandler
    private void onPreRender(RenderEntityEvent.Pre pre) {
        if (this.shouldRender(pre.entity)) {
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)-1000000.0f);
        }
    }

    public boolean shouldRender(Entity Entity2) {
        return this.isActive() && this.entities.get().getBoolean((Object)Entity2.getType());
    }
}

