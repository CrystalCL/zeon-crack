/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.misc;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.meteor.MouseButtonEvent;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import net.minecraft.entity.player.PlayerEntity;

public class MiddleClickFriend
extends Module {
    @EventHandler
    private void onMouseButton(MouseButtonEvent mouseButtonEvent) {
        if (mouseButtonEvent.action == KeyAction.Press && mouseButtonEvent.button == 2 && this.mc.currentScreen == null && this.mc.targetedEntity instanceof PlayerEntity) {
            Friends.get().addOrRemove(new Friend((PlayerEntity)this.mc.targetedEntity));
        }
    }

    public MiddleClickFriend() {
        super(Categories.Misc, "middle-click-friend", "Adds or removes a player as a friend via middle click.");
    }
}

