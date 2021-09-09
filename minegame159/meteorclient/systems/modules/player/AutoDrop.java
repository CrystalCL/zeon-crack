/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.client.gui.screen.ingame.HandledScreen
 */
package minegame159.meteorclient.systems.modules.player;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ItemListSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.item.Item;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public class AutoDrop
extends Module {
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> excludeHotbar;
    private final /* synthetic */ Setting<List<Item>> items;

    @EventHandler
    private void onTick(TickEvent.Post llIlllIllIIl) {
        int llIlllIllIll;
        AutoDrop llIlllIllIII;
        if (llIlllIllIII.mc.currentScreen instanceof HandledScreen) {
            return;
        }
        int n = llIlllIllIll = llIlllIllIII.excludeHotbar.get() != false ? 9 : 0;
        while (llIlllIllIll < llIlllIllIII.mc.player.inventory.size()) {
            if (llIlllIllIII.items.get().contains((Object)llIlllIllIII.mc.player.inventory.getStack(llIlllIllIll).getItem())) {
                InvUtils.drop().slot(llIlllIllIll);
            }
            ++llIlllIllIll;
        }
    }

    public AutoDrop() {
        super(Categories.Player, "auto-drop", "Automatically drops specified items.");
        AutoDrop llIlllIllllI;
        llIlllIllllI.sgGeneral = llIlllIllllI.settings.getDefaultGroup();
        llIlllIllllI.items = llIlllIllllI.sgGeneral.add(new ItemListSetting.Builder().name("items").description("Items to drop.").defaultValue(new ArrayList<Item>(0)).build());
        llIlllIllllI.excludeHotbar = llIlllIllllI.sgGeneral.add(new BoolSetting.Builder().name("exclude-hotbar").description("Whether or not to drop items from your hotbar.").defaultValue(false).build());
    }
}

