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
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2663;

public class TotemPopNotifier
extends Module {
    private final Setting<Boolean> friends;
    private final Setting<Boolean> own;
    private final Object2IntMap<UUID> totemPops;
    private final Object2IntMap<UUID> chatIds;
    private final Random random;
    private final Setting<Boolean> others;
    private final SettingGroup sgGeneral;

    private int getChatId(class_1297 class_12972) {
        return this.chatIds.computeIntIfAbsent((Object)class_12972.method_5667(), this::lambda$getChatId$0);
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
        if (!(receive.packet instanceof class_2663)) {
            return;
        }
        class_2663 class_26632 = (class_2663)receive.packet;
        if (class_26632.method_11470() != 35) {
            return;
        }
        class_1297 class_12972 = class_26632.method_11469((class_1937)this.mc.field_1687);
        if (class_12972 == null) return;
        if (class_12972.equals((Object)this.mc.field_1724)) {
            if (this.own.get() == false) return;
        }
        if (Friends.get().attack((class_1657)class_12972)) {
            if (this.others.get() == false) return;
        }
        if (!Friends.get().attack((class_1657)class_12972) && !this.friends.get().booleanValue()) {
            return;
        }
        Object2IntMap<UUID> object2IntMap = this.totemPops;
        synchronized (object2IntMap) {
            int n = this.totemPops.getOrDefault((Object)class_12972.method_5667(), 0);
            this.totemPops.put((Object)class_12972.method_5667(), ++n);
            ChatUtils.info(this.getChatId(class_12972), "(highlight)%s (default)popped (highlight)%d (default)%s.", ((class_1657)class_12972).method_7334().getName(), n, n == 1 ? "totem" : "totems");
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
            Iterator iterator = this.mc.field_1687.method_18456().iterator();
            while (iterator.hasNext()) {
                class_1657 class_16572 = (class_1657)iterator.next();
                if (!this.totemPops.containsKey((Object)class_16572.method_5667()) || class_16572.field_6213 <= 0 && !(class_16572.method_6032() <= 0.0f)) continue;
                int n = this.totemPops.removeInt((Object)class_16572.method_5667());
                ChatUtils.info(this.getChatId((class_1297)class_16572), "(highlight)%s (default)died after popping (highlight)%d (default)%s.", class_16572.method_7334().getName(), n, n == 1 ? "totem" : "totems");
                this.chatIds.removeInt((Object)class_16572.method_5667());
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

