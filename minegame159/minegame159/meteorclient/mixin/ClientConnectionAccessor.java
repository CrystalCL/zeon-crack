/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import io.netty.channel.Channel;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ClientConnection.class})
public interface ClientConnectionAccessor {
    @Accessor(value="channel")
    public Channel getChannel();
}

