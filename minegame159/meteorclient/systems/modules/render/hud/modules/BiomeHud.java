/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos.Mutable
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.util.Identifier
 *  org.apache.commons.lang3.StringUtils
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
    private final /* synthetic */ Mutable blockPos;

    public BiomeHud(HUD lllllllllllllllllllIIlIllllIIIIl) {
        super(lllllllllllllllllllIIlIllllIIIIl, "biome", "Displays the biome you are in.", "Biome: ");
        BiomeHud lllllllllllllllllllIIlIllllIIIlI;
        lllllllllllllllllllIIlIllllIIIlI.blockPos = new Mutable();
    }

    @Override
    protected String getRight() {
        BiomeHud lllllllllllllllllllIIlIlllIlllII;
        if (lllllllllllllllllllIIlIlllIlllII.isInEditor()) {
            return "Plains";
        }
        lllllllllllllllllllIIlIlllIlllII.blockPos.set(lllllllllllllllllllIIlIlllIlllII.mc.player.getX(), lllllllllllllllllllIIlIlllIlllII.mc.player.getY(), lllllllllllllllllllIIlIlllIlllII.mc.player.getZ());
        Identifier lllllllllllllllllllIIlIlllIllIll = lllllllllllllllllllIIlIlllIlllII.mc.world.getRegistryManager().get(Registry.BIOME_KEY).getId((Object)lllllllllllllllllllIIlIlllIlllII.mc.world.getBiome((BlockPos)lllllllllllllllllllIIlIlllIlllII.blockPos));
        if (lllllllllllllllllllIIlIlllIllIll == null) {
            return "Unknown";
        }
        return Arrays.stream(lllllllllllllllllllIIlIlllIllIll.getPath().split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
    }
}

