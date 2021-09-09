/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.text.Text
 *  net.minecraft.client.gui.hud.ClientBossBar
 */
package minegame159.meteorclient.systems.modules.render;

import java.util.HashMap;
import java.util.WeakHashMap;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderBossBarEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.text.Text;
import net.minecraft.client.gui.hud.ClientBossBar;

public class BossStack
extends Module {
    public final /* synthetic */ Setting<Boolean> hideName;
    private final /* synthetic */ Setting<Double> spacing;
    public static final /* synthetic */ WeakHashMap<ClientBossBar, Integer> barMap;
    public final /* synthetic */ Setting<Boolean> stack;
    private final /* synthetic */ SettingGroup sgGeneral;

    public BossStack() {
        super(Categories.Render, "boss-stack", "Stacks boss bars to make your HUD less cluttered.");
        BossStack llIIllIIIIlIII;
        llIIllIIIIlIII.sgGeneral = llIIllIIIIlIII.settings.getDefaultGroup();
        llIIllIIIIlIII.stack = llIIllIIIIlIII.sgGeneral.add(new BoolSetting.Builder().name("stack").description("Stacks boss bars and adds a counter to the text.").defaultValue(true).build());
        llIIllIIIIlIII.hideName = llIIllIIIIlIII.sgGeneral.add(new BoolSetting.Builder().name("hide-name").description("Hides the names of boss bars.").defaultValue(false).build());
        llIIllIIIIlIII.spacing = llIIllIIIIlIII.sgGeneral.add(new DoubleSetting.Builder().name("bar-spacing").description("The spacing reduction between each boss bar.").defaultValue(10.0).min(0.0).sliderMax(10.0).build());
    }

    @EventHandler
    private void onFetchText(RenderBossBarEvent.BossText llIIllIIIIIIlI) {
        BossStack llIIlIllllllll;
        if (llIIlIllllllll.hideName.get().booleanValue()) {
            llIIllIIIIIIlI.name = Text.of((String)"");
            return;
        }
        if (barMap.isEmpty() || !llIIlIllllllll.stack.get().booleanValue()) {
            return;
        }
        ClientBossBar llIIllIIIIIIIl = llIIllIIIIIIlI.bossBar;
        Integer llIIllIIIIIIII = barMap.get((Object)llIIllIIIIIIIl);
        barMap.remove((Object)llIIllIIIIIIIl);
        if (llIIllIIIIIIII != null && !llIIlIllllllll.hideName.get().booleanValue()) {
            llIIllIIIIIIlI.name = llIIllIIIIIIlI.name.copy().append(String.valueOf(new StringBuilder().append(" x").append(llIIllIIIIIIII)));
        }
    }

    @EventHandler
    private void onSpaceBars(RenderBossBarEvent.BossSpacing llIIlIlllllIII) {
        BossStack llIIlIlllllIIl;
        llIIlIlllllIII.spacing = llIIlIlllllIIl.spacing.get().intValue();
    }

    @EventHandler
    private void onGetBars(RenderBossBarEvent.BossIterator llIIlIlllIlllI) {
        BossStack llIIlIllllIIIl;
        if (llIIlIllllIIIl.stack.get().booleanValue()) {
            HashMap llIIlIllllIIlI = new HashMap();
            llIIlIlllIlllI.iterator.forEachRemaining(llIIlIlllIlIII -> {
                String llIIlIlllIIlll = llIIlIlllIlIII.getName().asString();
                if (llIIlIllllIIlI.containsKey(llIIlIlllIIlll)) {
                    barMap.compute((ClientBossBar)llIIlIllllIIlI.get(llIIlIlllIIlll), (llIIlIlllIIIlI, llIIlIlllIIIIl) -> llIIlIlllIIIIl == null ? 2 : llIIlIlllIIIIl + 1);
                } else {
                    llIIlIllllIIlI.put(llIIlIlllIIlll, llIIlIlllIlIII);
                }
            });
            llIIlIlllIlllI.iterator = llIIlIllllIIlI.values().iterator();
        }
    }

    static {
        barMap = new WeakHashMap();
    }
}

