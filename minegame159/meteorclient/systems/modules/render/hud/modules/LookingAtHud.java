/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.hit.HitResult.Type
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.hit.BlockHitResult
 *  net.minecraft.util.hit.EntityHitResult
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public class LookingAtHud
extends DoubleTextHudElement {
    private final /* synthetic */ Setting<Boolean> entityPosition;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> blockPosition;

    public LookingAtHud(HUD llllllllllllllllllIlllllIIllllII) {
        super(llllllllllllllllllIlllllIIllllII, "looking-at", "Displays what entity or block you are looking at.", "Looking At: ");
        LookingAtHud llllllllllllllllllIlllllIIllllIl;
        llllllllllllllllllIlllllIIllllIl.sgGeneral = llllllllllllllllllIlllllIIllllIl.settings.getDefaultGroup();
        llllllllllllllllllIlllllIIllllIl.blockPosition = llllllllllllllllllIlllllIIllllIl.sgGeneral.add(new BoolSetting.Builder().name("block-position").description("Displays block's position.").defaultValue(true).build());
        llllllllllllllllllIlllllIIllllIl.entityPosition = llllllllllllllllllIlllllIIllllIl.sgGeneral.add(new BoolSetting.Builder().name("entity-position").description("Displays entity's position.").defaultValue(true).build());
    }

    @Override
    protected String getRight() {
        LookingAtHud llllllllllllllllllIlllllIIllIIlI;
        if (llllllllllllllllllIlllllIIllIIlI.isInEditor()) {
            return llllllllllllllllllIlllllIIllIIlI.blockPosition.get() != false ? "Obsidian [0, 0, 0]" : "Obsidian";
        }
        if (llllllllllllllllllIlllllIIllIIlI.mc.crosshairTarget.getType() == Type.BLOCK) {
            String llllllllllllllllllIlllllIIllIllI = llllllllllllllllllIlllllIIllIIlI.mc.world.getBlockState(((BlockHitResult)llllllllllllllllllIlllllIIllIIlI.mc.crosshairTarget).getBlockPos()).getBlock().getName().getString();
            Vec3d llllllllllllllllllIlllllIIllIlIl = llllllllllllllllllIlllllIIllIIlI.mc.crosshairTarget.getPos();
            return llllllllllllllllllIlllllIIllIIlI.blockPosition.get().booleanValue() ? String.format("%s [%d, %d, %d]", llllllllllllllllllIlllllIIllIllI, (int)llllllllllllllllllIlllllIIllIlIl.x, (int)llllllllllllllllllIlllllIIllIlIl.y, (int)llllllllllllllllllIlllllIIllIlIl.z) : llllllllllllllllllIlllllIIllIllI;
        }
        if (llllllllllllllllllIlllllIIllIIlI.mc.crosshairTarget.getType() == Type.ENTITY) {
            String llllllllllllllllllIlllllIIllIlII = ((EntityHitResult)llllllllllllllllllIlllllIIllIIlI.mc.crosshairTarget).getEntity().getDisplayName().getString();
            Vec3d llllllllllllllllllIlllllIIllIIll = llllllllllllllllllIlllllIIllIIlI.mc.crosshairTarget.getPos();
            return llllllllllllllllllIlllllIIllIIlI.entityPosition.get().booleanValue() ? String.format("%s [%d, %d, %d]", llllllllllllllllllIlllllIIllIlII, (int)llllllllllllllllllIlllllIIllIIll.x, (int)llllllllllllllllllIlllllIIllIIll.y, (int)llllllllllllllllllIlllllIIllIIll.z) : llllllllllllllllllIlllllIIllIlII;
        }
        return "";
    }
}

