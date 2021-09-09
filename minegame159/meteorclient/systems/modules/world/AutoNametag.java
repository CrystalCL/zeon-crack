/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  net.minecraft.util.Hand
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.NameTagItem
 */
package minegame159.meteorclient.systems.modules.world;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.util.Hand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;

public class AutoNametag
extends Module {
    private final /* synthetic */ Setting<Double> distance;
    private final /* synthetic */ Setting<Object2BooleanMap<EntityType<?>>> entities;
    private /* synthetic */ boolean offHand;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ Entity entity;
    private /* synthetic */ int preSlot;
    private final /* synthetic */ Setting<Boolean> rotate;

    @Override
    public void onDeactivate() {
        lllllllllllllllllIIIlIIIlIIllIII.entity = null;
    }

    @EventHandler
    private void onTick(TickEvent.Pre lllllllllllllllllIIIlIIIlIIIlIIl) {
        AutoNametag lllllllllllllllllIIIlIIIlIIIlIII;
        lllllllllllllllllIIIlIIIlIIIlIII.entity = null;
        for (Entity lllllllllllllllllIIIlIIIlIIIlIll : lllllllllllllllllIIIlIIIlIIIlIII.mc.world.getEntities()) {
            boolean lllllllllllllllllIIIlIIIlIIIllII;
            if (!lllllllllllllllllIIIlIIIlIIIlIII.entities.get().getBoolean((Object)lllllllllllllllllIIIlIIIlIIIlIll.getType()) || lllllllllllllllllIIIlIIIlIIIlIll.hasCustomName() || (double)lllllllllllllllllIIIlIIIlIIIlIII.mc.player.distanceTo(lllllllllllllllllIIIlIIIlIIIlIll) > lllllllllllllllllIIIlIIIlIIIlIII.distance.get()) continue;
            boolean lllllllllllllllllIIIlIIIlIIIllIl = true;
            if (lllllllllllllllllIIIlIIIlIIIlIII.mc.player.inventory.getMainHandStack().getItem() instanceof NameTagItem) {
                lllllllllllllllllIIIlIIIlIIIllIl = false;
            } else if (((ItemStack)lllllllllllllllllIIIlIIIlIIIlIII.mc.player.inventory.offHand.get(0)).getItem() instanceof NameTagItem) {
                lllllllllllllllllIIIlIIIlIIIllIl = false;
                lllllllllllllllllIIIlIIIlIIIlIII.offHand = true;
            }
            boolean bl = lllllllllllllllllIIIlIIIlIIIllII = !lllllllllllllllllIIIlIIIlIIIllIl;
            if (lllllllllllllllllIIIlIIIlIIIllIl) {
                for (int lllllllllllllllllIIIlIIIlIIIlllI = 0; lllllllllllllllllIIIlIIIlIIIlllI < 9; ++lllllllllllllllllIIIlIIIlIIIlllI) {
                    ItemStack lllllllllllllllllIIIlIIIlIIIllll = lllllllllllllllllIIIlIIIlIIIlIII.mc.player.inventory.getStack(lllllllllllllllllIIIlIIIlIIIlllI);
                    if (!(lllllllllllllllllIIIlIIIlIIIllll.getItem() instanceof NameTagItem)) continue;
                    lllllllllllllllllIIIlIIIlIIIlIII.preSlot = lllllllllllllllllIIIlIIIlIIIlIII.mc.player.inventory.selectedSlot;
                    lllllllllllllllllIIIlIIIlIIIlIII.mc.player.inventory.selectedSlot = lllllllllllllllllIIIlIIIlIIIlllI;
                    lllllllllllllllllIIIlIIIlIIIllII = true;
                    break;
                }
            }
            if (!lllllllllllllllllIIIlIIIlIIIllII) continue;
            lllllllllllllllllIIIlIIIlIIIlIII.entity = lllllllllllllllllIIIlIIIlIIIlIll;
            if (lllllllllllllllllIIIlIIIlIIIlIII.rotate.get().booleanValue()) {
                Rotations.rotate(Rotations.getYaw(lllllllllllllllllIIIlIIIlIIIlIll), Rotations.getPitch(lllllllllllllllllIIIlIIIlIIIlIll), -100, lllllllllllllllllIIIlIIIlIIIlIII::interact);
            } else {
                lllllllllllllllllIIIlIIIlIIIlIII.interact();
            }
            return;
        }
    }

    public AutoNametag() {
        super(Categories.World, "auto-nametag", "Automatically uses nametags on entities without a nametag. WILL nametag ALL entities in the specified distance.");
        AutoNametag lllllllllllllllllIIIlIIIlIIllIll;
        lllllllllllllllllIIIlIIIlIIllIll.sgGeneral = lllllllllllllllllIIIlIIIlIIllIll.settings.getDefaultGroup();
        lllllllllllllllllIIIlIIIlIIllIll.entities = lllllllllllllllllIIIlIIIlIIllIll.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Which entities to nametag.").defaultValue((Object2BooleanMap<EntityType<?>>)new Object2BooleanOpenHashMap(0)).build());
        lllllllllllllllllIIIlIIIlIIllIll.distance = lllllllllllllllllIIIlIIIlIIllIll.sgGeneral.add(new DoubleSetting.Builder().name("distance").description("The maximum distance a nametagged entity can be to be nametagged.").min(0.0).defaultValue(5.0).build());
        lllllllllllllllllIIIlIIIlIIllIll.rotate = lllllllllllllllllIIIlIIIlIIllIll.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically faces towards the mob being nametagged.").defaultValue(true).build());
    }

    private void interact() {
        AutoNametag lllllllllllllllllIIIlIIIlIIIIIII;
        lllllllllllllllllIIIlIIIlIIIIIII.mc.interactionManager.interactEntity((PlayerEntity)lllllllllllllllllIIIlIIIlIIIIIII.mc.player, lllllllllllllllllIIIlIIIlIIIIIII.entity, lllllllllllllllllIIIlIIIlIIIIIII.offHand ? Hand.OFF_HAND : Hand.MAIN_HAND);
        lllllllllllllllllIIIlIIIlIIIIIII.mc.player.inventory.selectedSlot = lllllllllllllllllIIIlIIIlIIIIIII.preSlot;
    }
}

