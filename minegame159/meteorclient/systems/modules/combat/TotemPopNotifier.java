/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.world.World
 *  net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket
 */
package minegame159.meteorclient.systems.modules.combat;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
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
    private final /* synthetic */ Setting<Boolean> own;
    private final /* synthetic */ Setting<Boolean> others;
    private final /* synthetic */ Random random;
    private final /* synthetic */ Object2IntMap<UUID> totemPops;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> friends;
    private final /* synthetic */ Object2IntMap<UUID> chatIds;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    private void onReceivePacket(PacketEvent.Receive llllllllllllllllIllIllIllIIIllII) {
        TotemPopNotifier llllllllllllllllIllIllIllIIIlIIl;
        if (!(llllllllllllllllIllIllIllIIIllII.packet instanceof EntityStatusS2CPacket)) {
            return;
        }
        EntityStatusS2CPacket llllllllllllllllIllIllIllIIIlIll = (EntityStatusS2CPacket)llllllllllllllllIllIllIllIIIllII.packet;
        if (llllllllllllllllIllIllIllIIIlIll.getStatus() != 35) {
            return;
        }
        Entity llllllllllllllllIllIllIllIIIlIlI = llllllllllllllllIllIllIllIIIlIll.getEntity((World)llllllllllllllllIllIllIllIIIlIIl.mc.world);
        if (llllllllllllllllIllIllIllIIIlIlI == null || llllllllllllllllIllIllIllIIIlIlI.equals((Object)llllllllllllllllIllIllIllIIIlIIl.mc.player) && llllllllllllllllIllIllIllIIIlIIl.own.get() == false || Friends.get().attack((PlayerEntity)llllllllllllllllIllIllIllIIIlIlI) && llllllllllllllllIllIllIllIIIlIIl.others.get() == false || !Friends.get().attack((PlayerEntity)llllllllllllllllIllIllIllIIIlIlI) && !llllllllllllllllIllIllIllIIIlIIl.friends.get().booleanValue()) {
            return;
        }
        Object2IntMap<UUID> llllllllllllllllIllIllIllIIIIlIl = llllllllllllllllIllIllIllIIIlIIl.totemPops;
        synchronized (llllllllllllllllIllIllIllIIIIlIl) {
            int llllllllllllllllIllIllIllIIIlllI = llllllllllllllllIllIllIllIIIlIIl.totemPops.getOrDefault((Object)llllllllllllllllIllIllIllIIIlIlI.getUuid(), 0);
            llllllllllllllllIllIllIllIIIlIIl.totemPops.put((Object)llllllllllllllllIllIllIllIIIlIlI.getUuid(), ++llllllllllllllllIllIllIllIIIlllI);
            ChatUtils.info(llllllllllllllllIllIllIllIIIlIIl.getChatId(llllllllllllllllIllIllIllIIIlIlI), "(highlight)%s (default)popped (highlight)%d (default)%s.", ((PlayerEntity)llllllllllllllllIllIllIllIIIlIlI).getGameProfile().getName(), llllllllllllllllIllIllIllIIIlllI, llllllllllllllllIllIllIllIIIlllI == 1 ? "totem" : "totems");
        }
    }

    public TotemPopNotifier() {
        super(Categories.Combat, "totem-pop-notifier", "Sends a chat message when a player either pops a totem or dies.");
        TotemPopNotifier llllllllllllllllIllIllIllIIllllI;
        llllllllllllllllIllIllIllIIllllI.sgGeneral = llllllllllllllllIllIllIllIIllllI.settings.getDefaultGroup();
        llllllllllllllllIllIllIllIIllllI.own = llllllllllllllllIllIllIllIIllllI.sgGeneral.add(new BoolSetting.Builder().name("own").description("Notifies you of your own totem pops.").defaultValue(false).build());
        llllllllllllllllIllIllIllIIllllI.friends = llllllllllllllllIllIllIllIIllllI.sgGeneral.add(new BoolSetting.Builder().name("friends").description("Notifies you of your friends totem pops.").defaultValue(true).build());
        llllllllllllllllIllIllIllIIllllI.others = llllllllllllllllIllIllIllIIllllI.sgGeneral.add(new BoolSetting.Builder().name("others").description("Notifies you of other players totem pops.").defaultValue(true).build());
        llllllllllllllllIllIllIllIIllllI.totemPops = new Object2IntOpenHashMap();
        llllllllllllllllIllIllIllIIllllI.chatIds = new Object2IntOpenHashMap();
        llllllllllllllllIllIllIllIIllllI.random = new Random();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllIllIllIlIllllIIl) {
        TotemPopNotifier llllllllllllllllIllIllIlIllllIlI;
        Object2IntMap<UUID> llllllllllllllllIllIllIlIlllIlll = llllllllllllllllIllIllIlIllllIlI.totemPops;
        synchronized (llllllllllllllllIllIllIlIlllIlll) {
            for (PlayerEntity llllllllllllllllIllIllIlIllllIll : llllllllllllllllIllIllIlIllllIlI.mc.world.getPlayers()) {
                if (!llllllllllllllllIllIllIlIllllIlI.totemPops.containsKey((Object)llllllllllllllllIllIllIlIllllIll.getUuid()) || llllllllllllllllIllIllIlIllllIll.deathTime <= 0 && !(llllllllllllllllIllIllIlIllllIll.getHealth() <= 0.0f)) continue;
                int llllllllllllllllIllIllIlIlllllII = llllllllllllllllIllIllIlIllllIlI.totemPops.removeInt((Object)llllllllllllllllIllIllIlIllllIll.getUuid());
                ChatUtils.info(llllllllllllllllIllIllIlIllllIlI.getChatId((Entity)llllllllllllllllIllIllIlIllllIll), "(highlight)%s (default)died after popping (highlight)%d (default)%s.", llllllllllllllllIllIllIlIllllIll.getGameProfile().getName(), llllllllllllllllIllIllIlIlllllII, llllllllllllllllIllIllIlIlllllII == 1 ? "totem" : "totems");
                llllllllllllllllIllIllIlIllllIlI.chatIds.removeInt((Object)llllllllllllllllIllIllIlIllllIll.getUuid());
            }
        }
    }

    @Override
    public void onActivate() {
        TotemPopNotifier llllllllllllllllIllIllIllIIllIll;
        llllllllllllllllIllIllIllIIllIll.totemPops.clear();
        llllllllllllllllIllIllIllIIllIll.chatIds.clear();
    }

    @EventHandler
    private void onGameJoin(GameJoinedEvent llllllllllllllllIllIllIllIIlIlll) {
        TotemPopNotifier llllllllllllllllIllIllIllIIlIllI;
        llllllllllllllllIllIllIllIIlIllI.totemPops.clear();
        llllllllllllllllIllIllIllIIlIllI.chatIds.clear();
    }

    private int getChatId(Entity llllllllllllllllIllIllIlIllIllll) {
        TotemPopNotifier llllllllllllllllIllIllIlIlllIIII;
        return llllllllllllllllIllIllIlIlllIIII.chatIds.computeIntIfAbsent((Object)llllllllllllllllIllIllIlIllIllll.getUuid(), llllllllllllllllIllIllIlIllIlIlI -> {
            TotemPopNotifier llllllllllllllllIllIllIlIllIlIll;
            return llllllllllllllllIllIllIlIllIlIll.random.nextInt();
        });
    }
}

