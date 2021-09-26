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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;

public class EntityLogger
extends Module {
    private final Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final Setting<Boolean> playerNames;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> friends;

    public EntityLogger() {
        super(Categories.World, "entity-logger", "Sends a client-side chat alert if a specified entity appears in render distance.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entites").description("Select specific entities.").defaultValue((Object2BooleanMap<EntityType<?>>)new Object2BooleanOpenHashMap(0)).build());
        this.playerNames = this.sgGeneral.add(new BoolSetting.Builder().name("player-names").description("Shows the player's name.").defaultValue(true).build());
        this.friends = this.sgGeneral.add(new BoolSetting.Builder().name("friends").description("Logs friends.").defaultValue(true).build());
    }

    @EventHandler
    private void onEntityAdded(EntityAddedEvent entityAddedEvent) {
        if (entityAddedEvent.entity.getUuid().equals(this.mc.player.getUuid())) {
            return;
        }
        if (this.entities.get().getBoolean((Object)entityAddedEvent.entity.getType())) {
            if (entityAddedEvent.entity instanceof PlayerEntity && !this.friends.get().booleanValue() && Friends.get().get((PlayerEntity)entityAddedEvent.entity) != null) {
                return;
            }
            String string = this.playerNames.get() != false && entityAddedEvent.entity instanceof PlayerEntity ? String.valueOf(new StringBuilder().append(((PlayerEntity)entityAddedEvent.entity).getGameProfile().getName()).append(" (Player)")) : entityAddedEvent.entity.getType().getName().getString();
            ChatUtils.moduleInfo(this, "(highlight)%s (default)has spawned at (highlight)%.0f(default), (highlight)%.0f(default), (highlight)%.0f(default).", string, entityAddedEvent.entity.getX(), entityAddedEvent.entity.getY(), entityAddedEvent.entity.getZ());
        }
    }
}

