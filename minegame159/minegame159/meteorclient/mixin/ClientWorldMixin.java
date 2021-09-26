/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.EntityAddedEvent;
import minegame159.meteorclient.events.entity.EntityRemovedEvent;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.Search;
import minegame159.meteorclient.systems.modules.world.Ambience;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ClientWorld.class})
public class ClientWorldMixin {
    @Unique
    private final SkyProperties endSky = new End();
    @Unique
    private final SkyProperties customSky = new Ambience.Custom();

    @Inject(method={"addEntityPrivate"}, at={@At(value="TAIL")})
    private void onAddEntityPrivate(int n, Entity Entity2, CallbackInfo callbackInfo) {
        MeteorClient.EVENT_BUS.post(EntityAddedEvent.get(Entity2));
    }

    @Inject(method={"finishRemovingEntity"}, at={@At(value="TAIL")})
    private void onFinishRemovingEntity(Entity Entity2, CallbackInfo callbackInfo) {
        MeteorClient.EVENT_BUS.post(EntityRemovedEvent.get(Entity2));
    }

    @Inject(method={"setBlockStateWithoutNeighborUpdates"}, at={@At(value="TAIL")})
    private void onSetBlockStateWithoutNeighborUpdates(BlockPos BlockPos2, BlockState BlockState2, CallbackInfo callbackInfo) {
        Search search = Modules.get().get(Search.class);
        if (search.isActive()) {
            search.onBlockUpdate(BlockPos2, BlockState2);
        }
    }

    @Inject(method={"method_23777"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetSkyColor(BlockPos BlockPos2, float f, CallbackInfoReturnable<Vec3d> callbackInfoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeSkyColor.get().booleanValue()) {
            callbackInfoReturnable.setReturnValue((Object)ambience.skyColor.get().getVec3d());
        }
    }

    @Inject(method={"getSkyProperties"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetSkyProperties(CallbackInfoReturnable<SkyProperties> callbackInfoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.enderMode.get().booleanValue()) {
            callbackInfoReturnable.setReturnValue((Object)(ambience.enderCustomSkyColor.get() != false ? this.customSky : this.endSky));
        }
    }

    @Inject(method={"getCloudsColor"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetCloudsColor(float f, CallbackInfoReturnable<Vec3d> callbackInfoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeCloudColor.get().booleanValue()) {
            callbackInfoReturnable.setReturnValue((Object)ambience.cloudColor.get().getVec3d());
        }
    }
}

