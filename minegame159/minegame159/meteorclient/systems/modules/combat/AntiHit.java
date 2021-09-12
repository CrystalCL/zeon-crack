/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.AttackEntityEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import net.minecraft.class_1299;
import net.minecraft.class_1657;

public class AntiHit
extends Module {
    private final Setting<Boolean> antiFriendHit;
    private final Setting<Object2BooleanMap<class_1299<?>>> entities;
    private final SettingGroup sgGeneral;

    public AntiHit() {
        super(Categories.Combat, "anti-hit", "Cancels out attacks on certain entities.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.antiFriendHit = this.sgGeneral.add(new BoolSetting.Builder().name("anti-friend-hit").description("Doesn't allow friends to be attacked.").defaultValue(true).build());
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Entities to avoid attacking.").defaultValue((Object2BooleanMap<class_1299<?>>)new Object2BooleanOpenHashMap(0)).onlyAttackable().build());
    }

    @EventHandler(priority=100)
    private void onAttackEntity(AttackEntityEvent attackEntityEvent) {
        if (this.antiFriendHit.get().booleanValue() && attackEntityEvent.entity instanceof class_1657 && !Friends.get().attack((class_1657)attackEntityEvent.entity)) {
            attackEntityEvent.cancel();
        }
        if (Modules.get().isActive(AntiHit.class) && this.entities.get().containsKey((Object)attackEntityEvent.entity.method_5864())) {
            attackEntityEvent.cancel();
        }
    }
}

