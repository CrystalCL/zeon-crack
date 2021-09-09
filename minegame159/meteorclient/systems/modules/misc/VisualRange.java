/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerEntity
 */
package minegame159.meteorclient.systems.modules.misc;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.EntityAddedEvent;
import minegame159.meteorclient.events.entity.EntityRemovedEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.entity.player.PlayerEntity;

public class VisualRange
extends Module {
    private final /* synthetic */ Setting<Boolean> ignoreFakes;
    private final /* synthetic */ Setting<String> enterMessage;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> ignoreFriends;
    private final /* synthetic */ Setting<String> leaveMessage;

    @EventHandler
    private void onEntityAdded(EntityAddedEvent lllIllIlIlIIll) {
        VisualRange lllIllIlIlIlII;
        if (lllIllIlIlIIll.entity.equals((Object)lllIllIlIlIlII.mc.player) || !(lllIllIlIlIIll.entity instanceof PlayerEntity) || !Friends.get().attack((PlayerEntity)lllIllIlIlIIll.entity) && lllIllIlIlIlII.ignoreFriends.get().booleanValue() || lllIllIlIlIIll.entity instanceof FakePlayerEntity && lllIllIlIlIlII.ignoreFakes.get().booleanValue()) {
            return;
        }
        String lllIllIlIlIIlI = lllIllIlIlIlII.enterMessage.get().replace("{player}", ((PlayerEntity)lllIllIlIlIIll.entity).getGameProfile().getName());
        ChatUtils.moduleInfo(lllIllIlIlIlII, lllIllIlIlIIlI, new Object[0]);
    }

    @EventHandler
    private void onEntityRemoved(EntityRemovedEvent lllIllIlIIlIlI) {
        VisualRange lllIllIlIIlIII;
        if (lllIllIlIIlIlI.entity.equals((Object)lllIllIlIIlIII.mc.player) || !(lllIllIlIIlIlI.entity instanceof PlayerEntity) || !Friends.get().attack((PlayerEntity)lllIllIlIIlIlI.entity) && lllIllIlIIlIII.ignoreFriends.get().booleanValue() || lllIllIlIIlIlI.entity instanceof FakePlayerEntity && lllIllIlIIlIII.ignoreFakes.get().booleanValue()) {
            return;
        }
        String lllIllIlIIlIIl = lllIllIlIIlIII.leaveMessage.get().replace("{player}", ((PlayerEntity)lllIllIlIIlIlI.entity).getGameProfile().getName());
        ChatUtils.moduleInfo(lllIllIlIIlIII, lllIllIlIIlIIl, new Object[0]);
    }

    public VisualRange() {
        super(Categories.Misc, "visual-range", "Notifies you when a player enters/leaves your visual range.");
        VisualRange lllIllIlIllIIl;
        lllIllIlIllIIl.sgGeneral = lllIllIlIllIIl.settings.getDefaultGroup();
        lllIllIlIllIIl.ignoreFriends = lllIllIlIllIIl.sgGeneral.add(new BoolSetting.Builder().name("ignore-friends").description("Ignores friends.").defaultValue(true).build());
        lllIllIlIllIIl.ignoreFakes = lllIllIlIllIIl.sgGeneral.add(new BoolSetting.Builder().name("ignore-fakeplayers").description("Ignores fake players.").defaultValue(true).build());
        lllIllIlIllIIl.enterMessage = lllIllIlIllIIl.sgGeneral.add(new StringSetting.Builder().name("enter-message").description("The message for when a player enters your visual range.").defaultValue("{player} has entered your visual range.").build());
        lllIllIlIllIIl.leaveMessage = lllIllIlIllIIl.sgGeneral.add(new StringSetting.Builder().name("leave-message").description("The message for when a player leaves your visual range.").defaultValue("{player} has left your visual range.").build());
    }
}

