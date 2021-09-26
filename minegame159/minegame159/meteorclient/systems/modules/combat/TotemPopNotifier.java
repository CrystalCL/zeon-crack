/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.GameJoinedEvent;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

public class TotemPopNotifier
extends Module {
    private final Setting<Boolean> friends;
    private final Setting<Boolean> own;
    private final Object2IntMap<UUID> totemPops;
    private final Object2IntMap<UUID> chatIds;
    private final Random random;
    private final Setting<Boolean> others;
    private final SettingGroup sgGeneral;

    private int getChatId(Entity Entity2) {
        return this.chatIds.computeIntIfAbsent((Object)Entity2.getUuid(), this::lambda$getChatId$0);
    }

    @EventHandler
    private void onGameJoin(GameJoinedEvent gameJoinedEvent) {
        this.totemPops.clear();
        this.chatIds.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @EventHandler
    private void onReceivePacket(PacketEvent.Receive receive) {
        if (!(receive.packet instanceof EntityStatusS2CPacket)) {
            return;
        }
        EntityStatusS2CPacket EntityStatusS2CPacket2 = (EntityStatusS2CPacket)receive.packet;
        if (EntityStatusS2CPacket2.getStatus() != 35) {
            return;
        }
        Entity Entity2 = EntityStatusS2CPacket2.getEntity((World)this.mc.world);
        if (Entity2 == null) return;
        if (Entity2.equals((Object)this.mc.player)) {
            if (this.own.get() == false) return;
        }
        if (Friends.get().attack((PlayerEntity)Entity2)) {
            if (this.others.get() == false) return;
        }
        if (!Friends.get().attack((PlayerEntity)Entity2) && !this.friends.get().booleanValue()) {
            return;
        }
        Object2IntMap<UUID> object2IntMap = this.totemPops;
        synchronized (object2IntMap) {
            int n = this.totemPops.getOrDefault((Object)Entity2.getUuid(), 0);
            this.totemPops.put((Object)Entity2.getUuid(), ++n);
            ChatUtils.info(this.getChatId(Entity2), "(highlight)%s (default)popped (highlight)%d (default)%s.", ((PlayerEntity)Entity2).getGameProfile().getName(), n, n == 1 ? "totem" : "totems");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @EventHandler
    private void onTick(TickEvent.Post post) {
        Object2IntMap<UUID> object2IntMap = this.totemPops;
        synchronized (object2IntMap) {
            Iterator iterator = this.mc.world.getPlayers().iterator();
            while (iterator.hasNext()) {
                PlayerEntity PlayerEntity2 = (PlayerEntity)iterator.next();
                if (!this.totemPops.containsKey((Object)PlayerEntity2.getUuid()) || PlayerEntity2.deathTime <= 0 && !(PlayerEntity2.getHealth() <= 0.0f)) continue;
                int n = this.totemPops.removeInt((Object)PlayerEntity2.getUuid());
                ChatUtils.info(this.getChatId((Entity)PlayerEntity2), "(highlight)%s (default)died after popping (highlight)%d (default)%s.", PlayerEntity2.getGameProfile().getName(), n, n == 1 ? "totem" : "totems");
                this.chatIds.removeInt((Object)PlayerEntity2.getUuid());
            }
            return;
        }
    }

    @Override
    public void onActivate() {
        this.totemPops.clear();
        this.chatIds.clear();
    }

    private int lambda$getChatId$0(UUID uUID) {
        return this.random.nextInt();
    }

    public TotemPopNotifier() {
        super(Categories.Combat, "totem-pop-notifier", "Sends a chat message when a player either pops a totem or dies.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.own = this.sgGeneral.add(new BoolSetting.Builder().name("own").description("Notifies you of your own totem pops.").defaultValue(false).build());
        this.friends = this.sgGeneral.add(new BoolSetting.Builder().name("friends").description("Notifies you of your friends totem pops.").defaultValue(true).build());
        this.others = this.sgGeneral.add(new BoolSetting.Builder().name("others").description("Notifies you of other players totem pops.").defaultValue(true).build());
        this.totemPops = new Object2IntOpenHashMap();
        this.chatIds = new Object2IntOpenHashMap();
        this.random = new Random();
    }
}

