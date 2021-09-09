/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ActionResult
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.entity.projectile.FireworkRocketEntity
 *  net.minecraft.item.FireworkItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.world.World
 *  net.minecraft.sound.SoundEvents
 *  net.minecraft.sound.SoundCategory
 */
package minegame159.meteorclient.systems.modules.movement;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.InteractItemEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.KeybindSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.Keybind;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;

public class ElytraBoost
extends Module {
    private final /* synthetic */ Setting<Integer> fireworkLevel;
    private final /* synthetic */ Setting<Boolean> dontConsumeFirework;
    private final /* synthetic */ List<FireworkRocketEntity> fireworks;
    private final /* synthetic */ Setting<Keybind> keybind;
    private final /* synthetic */ Setting<Boolean> playSound;
    private final /* synthetic */ SettingGroup sgGeneral;

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllllIlIIlIIIlIIIlI) {
        ElytraBoost llllllllllllllllllIlIIlIIIlIIIll;
        llllllllllllllllllIlIIlIIIlIIIll.fireworks.removeIf(llllllllllllllllllIlIIlIIIIlIIII -> llllllllllllllllllIlIIlIIIIlIIII.removed);
    }

    private void boost() {
        ElytraBoost llllllllllllllllllIlIIlIIIIllIll;
        if (!Utils.canUpdate()) {
            return;
        }
        if (llllllllllllllllllIlIIlIIIIllIll.mc.player.isFallFlying() && llllllllllllllllllIlIIlIIIIllIll.mc.currentScreen == null) {
            ItemStack llllllllllllllllllIlIIlIIIIlllIl = Items.FIREWORK_ROCKET.getDefaultStack();
            llllllllllllllllllIlIIlIIIIlllIl.getOrCreateSubTag("Fireworks").putByte("Flight", llllllllllllllllllIlIIlIIIIllIll.fireworkLevel.get().byteValue());
            FireworkRocketEntity llllllllllllllllllIlIIlIIIIlllII = new FireworkRocketEntity((World)llllllllllllllllllIlIIlIIIIllIll.mc.world, llllllllllllllllllIlIIlIIIIlllIl, (LivingEntity)llllllllllllllllllIlIIlIIIIllIll.mc.player);
            llllllllllllllllllIlIIlIIIIllIll.fireworks.add(llllllllllllllllllIlIIlIIIIlllII);
            if (llllllllllllllllllIlIIlIIIIllIll.playSound.get().booleanValue()) {
                llllllllllllllllllIlIIlIIIIllIll.mc.world.playSoundFromEntity((PlayerEntity)llllllllllllllllllIlIIlIIIIllIll.mc.player, (Entity)llllllllllllllllllIlIIlIIIIlllII, SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.AMBIENT, 3.0f, 1.0f);
            }
            llllllllllllllllllIlIIlIIIIllIll.mc.world.addEntity(llllllllllllllllllIlIIlIIIIlllII.getEntityId(), (Entity)llllllllllllllllllIlIIlIIIIlllII);
        }
    }

    public ElytraBoost() {
        super(Categories.Movement, "elytra-boost", "Boosts your elytra as if you used a firework.");
        ElytraBoost llllllllllllllllllIlIIlIIIllIIIl;
        llllllllllllllllllIlIIlIIIllIIIl.sgGeneral = llllllllllllllllllIlIIlIIIllIIIl.settings.getDefaultGroup();
        llllllllllllllllllIlIIlIIIllIIIl.dontConsumeFirework = llllllllllllllllllIlIIlIIIllIIIl.sgGeneral.add(new BoolSetting.Builder().name("anti-consume").description("Prevents fireworks from being consumed when using Elytra Boost.").defaultValue(true).build());
        llllllllllllllllllIlIIlIIIllIIIl.fireworkLevel = llllllllllllllllllIlIIlIIIllIIIl.sgGeneral.add(new IntSetting.Builder().name("firework-duration").description("The duration of the firework.").defaultValue(0).min(0).max(255).build());
        llllllllllllllllllIlIIlIIIllIIIl.playSound = llllllllllllllllllIlIIlIIIllIIIl.sgGeneral.add(new BoolSetting.Builder().name("play-sound").description("Plays the firework sound when a boost is triggered.").defaultValue(true).build());
        llllllllllllllllllIlIIlIIIllIIIl.keybind = llllllllllllllllllIlIIlIIIllIIIl.sgGeneral.add(new KeybindSetting.Builder().name("keybind").description("The keybind to boost.").action(llllllllllllllllllIlIIlIIIllIIIl::boost).build());
        llllllllllllllllllIlIIlIIIllIIIl.fireworks = new ArrayList<FireworkRocketEntity>();
    }

    @EventHandler
    private void onInteractItem(InteractItemEvent llllllllllllllllllIlIIlIIIlIIllI) {
        ElytraBoost llllllllllllllllllIlIIlIIIlIlIlI;
        ItemStack llllllllllllllllllIlIIlIIIlIlIII = llllllllllllllllllIlIIlIIIlIlIlI.mc.player.getStackInHand(llllllllllllllllllIlIIlIIIlIIllI.hand);
        if (llllllllllllllllllIlIIlIIIlIlIII.getItem() instanceof FireworkItem && llllllllllllllllllIlIIlIIIlIlIlI.dontConsumeFirework.get().booleanValue()) {
            llllllllllllllllllIlIIlIIIlIIllI.toReturn = ActionResult.PASS;
            llllllllllllllllllIlIIlIIIlIlIlI.boost();
        }
    }

    public boolean isFirework(FireworkRocketEntity llllllllllllllllllIlIIlIIIIlIlII) {
        ElytraBoost llllllllllllllllllIlIIlIIIIlIIll;
        return llllllllllllllllllIlIIlIIIIlIIll.isActive() && llllllllllllllllllIlIIlIIIIlIIll.fireworks.contains((Object)llllllllllllllllllIlIIlIIIIlIlII);
    }

    @Override
    public void onDeactivate() {
        ElytraBoost llllllllllllllllllIlIIlIIIlIllll;
        llllllllllllllllllIlIIlIIIlIllll.fireworks.clear();
    }
}

