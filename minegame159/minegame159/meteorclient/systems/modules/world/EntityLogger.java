/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.EntityAddedEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.class_1299;
import net.minecraft.class_1657;

public class EntityLogger
extends Module {
    private final Setting<Object2BooleanMap<class_1299<?>>> entities;
    private final Setting<Boolean> playerNames;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> friends;

    public EntityLogger() {
        super(Categories.World, "entity-logger", "Sends a client-side chat alert if a specified entity appears in render distance.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entites").description("Select specific entities.").defaultValue((Object2BooleanMap<class_1299<?>>)new Object2BooleanOpenHashMap(0)).build());
        this.playerNames = this.sgGeneral.add(new BoolSetting.Builder().name("player-names").description("Shows the player's name.").defaultValue(true).build());
        this.friends = this.sgGeneral.add(new BoolSetting.Builder().name("friends").description("Logs friends.").defaultValue(true).build());
    }

    @EventHandler
    private void onEntityAdded(EntityAddedEvent entityAddedEvent) {
        if (entityAddedEvent.entity.method_5667().equals(this.mc.field_1724.method_5667())) {
            return;
        }
        if (this.entities.get().getBoolean((Object)entityAddedEvent.entity.method_5864())) {
            if (entityAddedEvent.entity instanceof class_1657 && !this.friends.get().booleanValue() && Friends.get().get((class_1657)entityAddedEvent.entity) != null) {
                return;
            }
            String string = this.playerNames.get() != false && entityAddedEvent.entity instanceof class_1657 ? String.valueOf(new StringBuilder().append(((class_1657)entityAddedEvent.entity).method_7334().getName()).append(" (Player)")) : entityAddedEvent.entity.method_5864().method_5897().getString();
            ChatUtils.moduleInfo(this, "(highlight)%s (default)has spawned at (highlight)%.0f(default), (highlight)%.0f(default), (highlight)%.0f(default).", string, entityAddedEvent.entity.method_23317(), entityAddedEvent.entity.method_23318(), entityAddedEvent.entity.method_23321());
        }
    }
}

