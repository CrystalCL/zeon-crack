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
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import org.lwjgl.opengl.GL11;

public class Chams
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Object2BooleanMap<class_1299<?>>> entities;

    public Chams() {
        super(Categories.Render, "chams", "Renders entities through walls.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Select entities to show through walls.").defaultValue((Object2BooleanMap<class_1299<?>>)Utils.asObject2BooleanOpenHashMap(class_1299.field_6097)).build());
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

    public boolean shouldRender(class_1297 class_12972) {
        return this.isActive() && this.entities.get().getBoolean((Object)class_12972.method_5864());
    }
}

