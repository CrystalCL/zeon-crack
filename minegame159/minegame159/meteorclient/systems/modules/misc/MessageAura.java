/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.misc;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.EntityAddedEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.entity.player.PlayerEntity;

public class MessageAura
extends Module {
    private final Setting<String> message;
    private final Setting<Boolean> ignoreFriends;
    private final SettingGroup sgGeneral;

    @EventHandler
    private void onEntityAdded(EntityAddedEvent entityAddedEvent) {
        if (!(entityAddedEvent.entity instanceof PlayerEntity) || entityAddedEvent.entity.getUuid().equals(this.mc.player.getUuid())) {
            return;
        }
        if (!this.ignoreFriends.get().booleanValue() || this.ignoreFriends.get().booleanValue() && !Friends.get().contains(new Friend((PlayerEntity)entityAddedEvent.entity))) {
            this.mc.player.sendChatMessage(String.valueOf(new StringBuilder().append("/msg ").append(((PlayerEntity)entityAddedEvent.entity).getGameProfile().getName()).append(" ").append(this.message.get())));
        }
    }

    public MessageAura() {
        super(Categories.Misc, "message-aura", "Sends a specified message to any player that enters render distance.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.message = this.sgGeneral.add(new StringSetting.Builder().name("message").description("The specified message sent to the player.").defaultValue("Meteor on Crack!").build());
        this.ignoreFriends = this.sgGeneral.add(new BoolSetting.Builder().name("ignore-friends").description("Will not send any messages to people friended.").defaultValue(false).build());
    }
}

