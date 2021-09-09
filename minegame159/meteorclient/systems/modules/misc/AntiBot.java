/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
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
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> removeInvisible;

    public AntiBot() {
        super(Categories.Misc, "anti-bot", "Detects and removes bots.");
        AntiBot llllllllllllllllllllIllIIlIIIIlI;
        llllllllllllllllllllIllIIlIIIIlI.sgGeneral = llllllllllllllllllllIllIIlIIIIlI.settings.getDefaultGroup();
        llllllllllllllllllllIllIIlIIIIlI.removeInvisible = llllllllllllllllllllIllIIlIIIIlI.sgGeneral.add(new BoolSetting.Builder().name("remove-invisible").description("Removes bot only if they are invisible.").defaultValue(true).build());
    }

    @EventHandler
    public void onTick(TickEvent.Post llllllllllllllllllllIllIIIlllIll) {
        AntiBot llllllllllllllllllllIllIIIlllIlI;
        for (Entity llllllllllllllllllllIllIIIllllIl : llllllllllllllllllllIllIIIlllIlI.mc.world.getEntities()) {
            if (llllllllllllllllllllIllIIIlllIlI.removeInvisible.get().booleanValue() && !llllllllllllllllllllIllIIIllllIl.isInvisible() || !llllllllllllllllllllIllIIIlllIlI.isBot(llllllllllllllllllllIllIIIllllIl)) continue;
            llllllllllllllllllllIllIIIllllIl.remove();
        }
    }

    private boolean isBot(Entity llllllllllllllllllllIllIIIllIlIl) {
        if (llllllllllllllllllllIllIIIllIlIl == null) {
            return false;
        }
        if (!(llllllllllllllllllllIllIIIllIlIl instanceof PlayerEntity)) {
            return false;
        }
        return EntityUtils.getGameMode((PlayerEntity)llllllllllllllllllllIllIIIllIlIl) == null;
    }
}

