/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Keybind> keybind;
    private final List<FireworkRocketEntity> fireworks;
    private final Setting<Boolean> dontConsumeFirework;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> playSound;
    private final Setting<Integer> fireworkLevel;

    @Override
    public void onDeactivate() {
        this.fireworks.clear();
    }

    public boolean isFirework(FireworkRocketEntity FireworkRocketEntity2) {
        return this.isActive() && this.fireworks.contains(FireworkRocketEntity2);
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        this.fireworks.removeIf(ElytraBoost::lambda$onTick$0);
    }

    private static boolean lambda$onTick$0(FireworkRocketEntity FireworkRocketEntity2) {
        return FireworkRocketEntity2.removed;
    }

    public ElytraBoost() {
        super(Categories.Movement, "elytra-boost", "Boosts your elytra as if you used a firework.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.dontConsumeFirework = this.sgGeneral.add(new BoolSetting.Builder().name("anti-consume").description("Prevents fireworks from being consumed when using Elytra Boost.").defaultValue(true).build());
        this.fireworkLevel = this.sgGeneral.add(new IntSetting.Builder().name("firework-duration").description("The duration of the firework.").defaultValue(0).min(0).max(255).build());
        this.playSound = this.sgGeneral.add(new BoolSetting.Builder().name("play-sound").description("Plays the firework sound when a boost is triggered.").defaultValue(true).build());
        this.keybind = this.sgGeneral.add(new KeybindSetting.Builder().name("keybind").description("The keybind to boost.").action(this::boost).build());
        this.fireworks = new ArrayList<FireworkRocketEntity>();
    }

    private void boost() {
        if (!Utils.canUpdate()) {
            return;
        }
        if (this.mc.player.isFallFlying() && this.mc.currentScreen == null) {
            ItemStack ItemStack2 = Items.FIREWORK_ROCKET.getDefaultStack();
            ItemStack2.getOrCreateSubTag("Fireworks").putByte("Flight", this.fireworkLevel.get().byteValue());
            FireworkRocketEntity FireworkRocketEntity2 = new FireworkRocketEntity((World)this.mc.world, ItemStack2, (LivingEntity)this.mc.player);
            this.fireworks.add(FireworkRocketEntity2);
            if (this.playSound.get().booleanValue()) {
                this.mc.world.playSoundFromEntity((PlayerEntity)this.mc.player, (Entity)FireworkRocketEntity2, SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.AMBIENT, 3.0f, 1.0f);
            }
            this.mc.world.addEntity(FireworkRocketEntity2.getEntityId(), (Entity)FireworkRocketEntity2);
        }
    }

    @EventHandler
    private void onInteractItem(InteractItemEvent interactItemEvent) {
        ItemStack ItemStack2 = this.mc.player.getStackInHand(interactItemEvent.hand);
        if (ItemStack2.getItem() instanceof FireworkItem && this.dontConsumeFirework.get().booleanValue()) {
            interactItemEvent.toReturn = ActionResult.PASS;
            this.boost();
        }
    }
}

