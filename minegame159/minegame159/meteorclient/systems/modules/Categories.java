/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules;

import minegame159.meteorclient.systems.modules.Category;
import minegame159.meteorclient.systems.modules.Modules;
import net.minecraft.item.Items;

public class Categories {
    public static final Category Player;
    public static final Category Misc;
    public static final Category Exclusive;
    public static final Category World;
    public static final Category Combat;
    public static final Category Render;
    public static final Category Movement;

    public static void register() {
        Modules.registerCategory(Combat);
        Modules.registerCategory(Player);
        Modules.registerCategory(Movement);
        Modules.registerCategory(Render);
        Modules.registerCategory(World);
        Modules.registerCategory(Misc);
        Modules.registerCategory(Exclusive);
    }

    static {
        Combat = new Category("Combat", Items.GOLDEN_SWORD.getDefaultStack());
        Player = new Category("Player", Items.ARMOR_STAND.getDefaultStack());
        Movement = new Category("Movement", Items.DIAMOND_BOOTS.getDefaultStack());
        Render = new Category("Render", Items.GLASS.getDefaultStack());
        World = new Category("World", Items.GRASS_BLOCK.getDefaultStack());
        Misc = new Category("Misc", Items.LAVA_BUCKET.getDefaultStack());
        Exclusive = new Category("Exclusive", Items.END_CRYSTAL.getDefaultStack());
    }
}

