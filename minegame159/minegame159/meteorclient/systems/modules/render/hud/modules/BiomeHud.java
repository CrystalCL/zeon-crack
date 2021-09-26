/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import java.util.Arrays;
import java.util.stream.Collectors;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

public class BiomeHud
extends DoubleTextHudElement {
    private final Mutable blockPos = new Mutable();

    @Override
    protected String getRight() {
        if (this.isInEditor()) {
            return "Plains";
        }
        this.blockPos.set(this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ());
        Identifier Identifier2 = this.mc.world.getRegistryManager().get(Registry.BIOME_KEY).getId((Object)this.mc.world.getBiome((BlockPos)this.blockPos));
        if (Identifier2 == null) {
            return "Unknown";
        }
        return Arrays.stream(Identifier2.getPath().split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
    }

    public BiomeHud(HUD hUD) {
        super(hUD, "biome", "Displays the biome you are in.", "Biome: ");
    }
}

