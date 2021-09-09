/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerEntity
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
    private void onMouseButton(MouseButtonEvent lIIIIIIIIIIIIl) {
        MiddleClickFriend lIIIIIIIIIIIII;
        if (lIIIIIIIIIIIIl.action == KeyAction.Press && lIIIIIIIIIIIIl.button == 2 && lIIIIIIIIIIIII.mc.currentScreen == null && lIIIIIIIIIIIII.mc.targetedEntity instanceof PlayerEntity) {
            Friends.get().addOrRemove(new Friend((PlayerEntity)lIIIIIIIIIIIII.mc.targetedEntity));
        }
    }

    public MiddleClickFriend() {
        super(Categories.Misc, "middle-click-friend", "Adds or removes a player as a friend via middle click.");
        MiddleClickFriend lIIIIIIIIIIlIl;
    }
}

