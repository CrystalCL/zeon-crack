/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.player.PlayerEntity
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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;

public class AntiHit
extends Module {
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final /* synthetic */ Setting<Boolean> antiFriendHit;
    private final /* synthetic */ SettingGroup sgGeneral;

    public AntiHit() {
        super(Categories.Combat, "anti-hit", "Cancels out attacks on certain entities.");
        AntiHit llllllllllllllllIllIIIllIlIlIlll;
        llllllllllllllllIllIIIllIlIlIlll.sgGeneral = llllllllllllllllIllIIIllIlIlIlll.settings.getDefaultGroup();
        llllllllllllllllIllIIIllIlIlIlll.antiFriendHit = llllllllllllllllIllIIIllIlIlIlll.sgGeneral.add(new BoolSetting.Builder().name("anti-friend-hit").description("Doesn't allow friends to be attacked.").defaultValue(true).build());
        llllllllllllllllIllIIIllIlIlIlll.entities = llllllllllllllllIllIIIllIlIlIlll.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Entities to avoid attacking.").defaultValue((Object2BooleanMap<EntityType<?>>)new Object2BooleanOpenHashMap(0)).onlyAttackable().build());
    }

    @EventHandler(priority=100)
    private void onAttackEntity(AttackEntityEvent llllllllllllllllIllIIIllIlIlIIII) {
        AntiHit llllllllllllllllIllIIIllIlIlIIIl;
        if (llllllllllllllllIllIIIllIlIlIIIl.antiFriendHit.get().booleanValue() && llllllllllllllllIllIIIllIlIlIIII.entity instanceof PlayerEntity && !Friends.get().attack((PlayerEntity)llllllllllllllllIllIIIllIlIlIIII.entity)) {
            llllllllllllllllIllIIIllIlIlIIII.cancel();
        }
        if (Modules.get().isActive(AntiHit.class) && llllllllllllllllIllIIIllIlIlIIIl.entities.get().containsKey((Object)llllllllllllllllIllIIIllIlIlIIII.entity.getType())) {
            llllllllllllllllIllIIIllIlIlIIII.cancel();
        }
    }
}

