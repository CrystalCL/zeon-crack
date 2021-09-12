/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.events.world.BlockActivateEvent;
import net.minecraft.class_1263;
import net.minecraft.class_1707;
import net.minecraft.class_1799;
import net.minecraft.class_2336;
import net.minecraft.class_2371;
import net.minecraft.class_310;
import net.minecraft.class_476;

public class EChestMemory {
    private static final class_310 MC;
    private static int echestOpenedState;
    public static final class_2371<class_1799> ITEMS;

    @EventHandler
    private static void onOpenScreenEvent(OpenScreenEvent openScreenEvent) {
        if (echestOpenedState == 1 && openScreenEvent.screen instanceof class_476) {
            echestOpenedState = 2;
            return;
        }
        if (echestOpenedState == 0) {
            return;
        }
        if (!(EChestMemory.MC.field_1755 instanceof class_476)) {
            return;
        }
        class_1707 class_17072 = (class_1707)((class_476)EChestMemory.MC.field_1755).method_17577();
        if (class_17072 == null) {
            return;
        }
        class_1263 class_12632 = class_17072.method_7629();
        for (int i = 0; i < 27; ++i) {
            ITEMS.set(i, (Object)class_12632.method_5438(i));
            if (-4 <= 0) continue;
            return;
        }
        echestOpenedState = 0;
    }

    @EventHandler
    private static void onBlockActivate(BlockActivateEvent blockActivateEvent) {
        if (blockActivateEvent.blockState.method_26204() instanceof class_2336 && echestOpenedState == 0) {
            echestOpenedState = 1;
        }
    }

    public static void init() {
        MeteorClient.EVENT_BUS.subscribe(EChestMemory.class);
    }

    static {
        ITEMS = class_2371.method_10213((int)27, (Object)class_1799.field_8037);
        MC = class_310.method_1551();
    }
}

