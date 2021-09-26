/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> entityPosition;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> blockPosition;

    public LookingAtHud(HUD hUD) {
        super(hUD, "looking-at", "Displays what entity or block you are looking at.", "Looking At: ");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.blockPosition = this.sgGeneral.add(new BoolSetting.Builder().name("block-position").description("Displays block's position.").defaultValue(true).build());
        this.entityPosition = this.sgGeneral.add(new BoolSetting.Builder().name("entity-position").description("Displays entity's position.").defaultValue(true).build());
    }

    @Override
    protected String getRight() {
        if (this.isInEditor()) {
            return this.blockPosition.get() != false ? "Obsidian [0, 0, 0]" : "Obsidian";
        }
        if (this.mc.crosshairTarget.getType() == HitResult.class_240.BLOCK) {
            String string = this.mc.world.getBlockState(((BlockHitResult)this.mc.crosshairTarget).getBlockPos()).getBlock().getName().getString();
            Vec3d Vec3d2 = this.mc.crosshairTarget.getPos();
            return this.blockPosition.get() != false ? String.format("%s [%d, %d, %d]", string, (int)Vec3d2.x, (int)Vec3d2.y, (int)Vec3d2.z) : string;
        }
        if (this.mc.crosshairTarget.getType() == HitResult.class_240.ENTITY) {
            String string = ((EntityHitResult)this.mc.crosshairTarget).getEntity().getDisplayName().getString();
            Vec3d Vec3d3 = this.mc.crosshairTarget.getPos();
            return this.entityPosition.get() != false ? String.format("%s [%d, %d, %d]", string, (int)Vec3d3.x, (int)Vec3d3.y, (int)Vec3d3.z) : string;
        }
        return "";
    }
}

