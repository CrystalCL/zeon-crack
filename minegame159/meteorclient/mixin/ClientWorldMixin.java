/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.block.BlockState
 *  net.minecraft.client.render.SkyProperties
 *  net.minecraft.client.render.SkyProperties.End
 *  net.minecraft.client.world.ClientWorld
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
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
    private void onAddEntityPrivate(int id, Entity entity, CallbackInfo info) {
        MeteorClient.EVENT_BUS.post(EntityAddedEvent.get(entity));
    }

    @Inject(method={"finishRemovingEntity"}, at={@At(value="TAIL")})
    private void onFinishRemovingEntity(Entity entity, CallbackInfo info) {
        MeteorClient.EVENT_BUS.post(EntityRemovedEvent.get(entity));
    }

    @Inject(method={"setBlockStateWithoutNeighborUpdates"}, at={@At(value="TAIL")})
    private void onSetBlockStateWithoutNeighborUpdates(BlockPos blockPos, BlockState blockState, CallbackInfo info) {
        Search search = Modules.get().get(Search.class);
        if (search.isActive()) {
            search.onBlockUpdate(blockPos, blockState);
        }
    }

    @Inject(method={"method_23777"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetSkyColor(BlockPos blockPos, float tickDelta, CallbackInfoReturnable<Vec3d> info) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeSkyColor.get().booleanValue()) {
            info.setReturnValue((Object)ambience.skyColor.get().getVec3d());
        }
    }

    @Inject(method={"getSkyProperties"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetSkyProperties(CallbackInfoReturnable<SkyProperties> info) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.enderMode.get().booleanValue()) {
            info.setReturnValue((Object)(ambience.enderCustomSkyColor.get() != false ? this.customSky : this.endSky));
        }
    }

    @Inject(method={"getCloudsColor"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetCloudsColor(float tickDelta, CallbackInfoReturnable<Vec3d> info) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isActive() && ambience.changeCloudColor.get().booleanValue()) {
            info.setReturnValue((Object)ambience.cloudColor.get().getVec3d());
        }
    }
}

