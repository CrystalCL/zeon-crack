/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerEntity
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
    private final /* synthetic */ Setting<Boolean> ignoreFriends;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<String> message;

    @EventHandler
    private void onEntityAdded(EntityAddedEvent lIlIIIIllllIllI) {
        MessageAura lIlIIIIlllllIIl;
        if (!(lIlIIIIllllIllI.entity instanceof PlayerEntity) || lIlIIIIllllIllI.entity.getUuid().equals(lIlIIIIlllllIIl.mc.player.getUuid())) {
            return;
        }
        if (!lIlIIIIlllllIIl.ignoreFriends.get().booleanValue() || lIlIIIIlllllIIl.ignoreFriends.get().booleanValue() && !Friends.get().contains(new Friend((PlayerEntity)lIlIIIIllllIllI.entity))) {
            lIlIIIIlllllIIl.mc.player.sendChatMessage(String.valueOf(new StringBuilder().append("/msg ").append(((PlayerEntity)lIlIIIIllllIllI.entity).getGameProfile().getName()).append(" ").append(lIlIIIIlllllIIl.message.get())));
        }
    }

    public MessageAura() {
        super(Categories.Misc, "message-aura", "Sends a specified message to any player that enters render distance.");
        MessageAura lIlIIIIllllllII;
        lIlIIIIllllllII.sgGeneral = lIlIIIIllllllII.settings.getDefaultGroup();
        lIlIIIIllllllII.message = lIlIIIIllllllII.sgGeneral.add(new StringSetting.Builder().name("message").description("The specified message sent to the player.").defaultValue("Meteor on Crack!").build());
        lIlIIIIllllllII.ignoreFriends = lIlIIIIllllllII.sgGeneral.add(new BoolSetting.Builder().name("ignore-friends").description("Will not send any messages to people friended.").defaultValue(false).build());
    }
}

