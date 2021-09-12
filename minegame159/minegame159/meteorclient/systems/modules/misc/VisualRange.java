/*
 * Decompiled with CFR 0.151.
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
import net.minecraft.class_1657;

public class VisualRange
extends Module {
    private final Setting<String> enterMessage;
    private final Setting<String> leaveMessage;
    private final Setting<Boolean> ignoreFriends;
    private final Setting<Boolean> ignoreFakes;
    private final SettingGroup sgGeneral;

    @EventHandler
    private void onEntityRemoved(EntityRemovedEvent entityRemovedEvent) {
        if (entityRemovedEvent.entity.equals((Object)this.mc.field_1724) || !(entityRemovedEvent.entity instanceof class_1657) || !Friends.get().attack((class_1657)entityRemovedEvent.entity) && this.ignoreFriends.get().booleanValue() || entityRemovedEvent.entity instanceof FakePlayerEntity && this.ignoreFakes.get().booleanValue()) {
            return;
        }
        String string = this.leaveMessage.get().replace("{player}", ((class_1657)entityRemovedEvent.entity).method_7334().getName());
        ChatUtils.moduleInfo(this, string, new Object[0]);
    }

    public VisualRange() {
        super(Categories.Misc, "visual-range", "Notifies you when a player enters/leaves your visual range.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.ignoreFriends = this.sgGeneral.add(new BoolSetting.Builder().name("ignore-friends").description("Ignores friends.").defaultValue(true).build());
        this.ignoreFakes = this.sgGeneral.add(new BoolSetting.Builder().name("ignore-fakeplayers").description("Ignores fake players.").defaultValue(true).build());
        this.enterMessage = this.sgGeneral.add(new StringSetting.Builder().name("enter-message").description("The message for when a player enters your visual range.").defaultValue("{player} has entered your visual range.").build());
        this.leaveMessage = this.sgGeneral.add(new StringSetting.Builder().name("leave-message").description("The message for when a player leaves your visual range.").defaultValue("{player} has left your visual range.").build());
    }

    @EventHandler
    private void onEntityAdded(EntityAddedEvent entityAddedEvent) {
        if (entityAddedEvent.entity.equals((Object)this.mc.field_1724) || !(entityAddedEvent.entity instanceof class_1657) || !Friends.get().attack((class_1657)entityAddedEvent.entity) && this.ignoreFriends.get().booleanValue() || entityAddedEvent.entity instanceof FakePlayerEntity && this.ignoreFakes.get().booleanValue()) {
            return;
        }
        String string = this.enterMessage.get().replace("{player}", ((class_1657)entityAddedEvent.entity).method_7334().getName());
        ChatUtils.moduleInfo(this, string, new Object[0]);
    }
}

