/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.player.PlayerEntity
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
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> friends;
    private final /* synthetic */ Setting<Boolean> playerNames;
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;

    public EntityLogger() {
        super(Categories.World, "entity-logger", "Sends a client-side chat alert if a specified entity appears in render distance.");
        EntityLogger lllllllllllllllllllIlIlIIIIIIIII;
        lllllllllllllllllllIlIlIIIIIIIII.sgGeneral = lllllllllllllllllllIlIlIIIIIIIII.settings.getDefaultGroup();
        lllllllllllllllllllIlIlIIIIIIIII.entities = lllllllllllllllllllIlIlIIIIIIIII.sgGeneral.add(new EntityTypeListSetting.Builder().name("entites").description("Select specific entities.").defaultValue((Object2BooleanMap<EntityType<?>>)new Object2BooleanOpenHashMap(0)).build());
        lllllllllllllllllllIlIlIIIIIIIII.playerNames = lllllllllllllllllllIlIlIIIIIIIII.sgGeneral.add(new BoolSetting.Builder().name("player-names").description("Shows the player's name.").defaultValue(true).build());
        lllllllllllllllllllIlIlIIIIIIIII.friends = lllllllllllllllllllIlIlIIIIIIIII.sgGeneral.add(new BoolSetting.Builder().name("friends").description("Logs friends.").defaultValue(true).build());
    }

    @EventHandler
    private void onEntityAdded(EntityAddedEvent lllllllllllllllllllIlIIlllllIlll) {
        EntityLogger lllllllllllllllllllIlIIllllllIII;
        if (lllllllllllllllllllIlIIlllllIlll.entity.getUuid().equals(lllllllllllllllllllIlIIllllllIII.mc.player.getUuid())) {
            return;
        }
        if (lllllllllllllllllllIlIIllllllIII.entities.get().getBoolean((Object)lllllllllllllllllllIlIIlllllIlll.entity.getType())) {
            String lllllllllllllllllllIlIIllllllIll;
            if (lllllllllllllllllllIlIIlllllIlll.entity instanceof PlayerEntity && !lllllllllllllllllllIlIIllllllIII.friends.get().booleanValue() && Friends.get().get((PlayerEntity)lllllllllllllllllllIlIIlllllIlll.entity) != null) {
                return;
            }
            if (lllllllllllllllllllIlIIllllllIII.playerNames.get().booleanValue() && lllllllllllllllllllIlIIlllllIlll.entity instanceof PlayerEntity) {
                String lllllllllllllllllllIlIIlllllllII = String.valueOf(new StringBuilder().append(((PlayerEntity)lllllllllllllllllllIlIIlllllIlll.entity).getGameProfile().getName()).append(" (Player)"));
            } else {
                lllllllllllllllllllIlIIllllllIll = lllllllllllllllllllIlIIlllllIlll.entity.getType().getName().getString();
            }
            ChatUtils.moduleInfo(lllllllllllllllllllIlIIllllllIII, "(highlight)%s (default)has spawned at (highlight)%.0f(default), (highlight)%.0f(default), (highlight)%.0f(default).", lllllllllllllllllllIlIIllllllIll, lllllllllllllllllllIlIIlllllIlll.entity.getX(), lllllllllllllllllllIlIIlllllIlll.entity.getY(), lllllllllllllllllllIlIIlllllIlll.entity.getZ());
        }
    }
}

