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
import net.minecraft.class_1297;
import net.minecraft.class_1657;

public class AntiBot
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> removeInvisible;

    private boolean isBot(class_1297 class_12972) {
        if (class_12972 == null) {
            return false;
        }
        if (!(class_12972 instanceof class_1657)) {
            return false;
        }
        return EntityUtils.getGameMode((class_1657)class_12972) == null;
    }

    @EventHandler
    public void onTick(TickEvent.Post post) {
        for (class_1297 class_12972 : this.mc.field_1687.method_18112()) {
            if (this.removeInvisible.get().booleanValue() && !class_12972.method_5767() || !this.isBot(class_12972)) continue;
            class_12972.method_5650();
        }
    }

    public AntiBot() {
        super(Categories.Misc, "anti-bot", "Detects and removes bots.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.removeInvisible = this.sgGeneral.add(new BoolSetting.Builder().name("remove-invisible").description("Removes bot only if they are invisible.").defaultValue(true).build());
    }
}

