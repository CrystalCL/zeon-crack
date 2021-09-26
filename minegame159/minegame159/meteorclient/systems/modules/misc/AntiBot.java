/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.misc;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class AntiBot
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> removeInvisible;

    private boolean isBot(Entity Entity2) {
        if (Entity2 == null) {
            return false;
        }
        if (!(Entity2 instanceof PlayerEntity)) {
            return false;
        }
        return EntityUtils.getGameMode((PlayerEntity)Entity2) == null;
    }

    @EventHandler
    public void onTick(TickEvent.Post post) {
        for (Entity Entity2 : this.mc.world.getEntities()) {
            if (this.removeInvisible.get().booleanValue() && !Entity2.isInvisible() || !this.isBot(Entity2)) continue;
            Entity2.remove();
        }
    }

    public AntiBot() {
        super(Categories.Misc, "anti-bot", "Detects and removes bots.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.removeInvisible = this.sgGeneral.add(new BoolSetting.Builder().name("remove-invisible").description("Removes bot only if they are invisible.").defaultValue(true).build());
    }
}

