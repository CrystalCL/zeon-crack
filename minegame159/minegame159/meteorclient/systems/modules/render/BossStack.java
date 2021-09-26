/*
 * Decompiled with CFR 0.151.
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
    public final Setting<Boolean> stack;
    private final Setting<Double> spacing;
    public final Setting<Boolean> hideName;
    public static final WeakHashMap<ClientBossBar, Integer> barMap = new WeakHashMap();
    private final SettingGroup sgGeneral;

    @EventHandler
    private void onGetBars(RenderBossBarEvent.BossIterator bossIterator) {
        if (this.stack.get().booleanValue()) {
            HashMap hashMap = new HashMap();
            bossIterator.iterator.forEachRemaining(arg_0 -> BossStack.lambda$onGetBars$1(hashMap, arg_0));
            bossIterator.iterator = hashMap.values().iterator();
        }
    }

    @EventHandler
    private void onSpaceBars(RenderBossBarEvent.BossSpacing bossSpacing) {
        bossSpacing.spacing = this.spacing.get().intValue();
    }

    private static void lambda$onGetBars$1(HashMap hashMap, ClientBossBar ClientBossBar2) {
        String string = ClientBossBar2.getName().asString();
        if (hashMap.containsKey(string)) {
            barMap.compute((ClientBossBar)hashMap.get(string), BossStack::lambda$onGetBars$0);
        } else {
            hashMap.put(string, ClientBossBar2);
        }
    }

    public BossStack() {
        super(Categories.Render, "boss-stack", "Stacks boss bars to make your HUD less cluttered.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.stack = this.sgGeneral.add(new BoolSetting.Builder().name("stack").description("Stacks boss bars and adds a counter to the text.").defaultValue(true).build());
        this.hideName = this.sgGeneral.add(new BoolSetting.Builder().name("hide-name").description("Hides the names of boss bars.").defaultValue(false).build());
        this.spacing = this.sgGeneral.add(new DoubleSetting.Builder().name("bar-spacing").description("The spacing reduction between each boss bar.").defaultValue(10.0).min(0.0).sliderMax(10.0).build());
    }

    private static Integer lambda$onGetBars$0(ClientBossBar ClientBossBar2, Integer n) {
        return n == null ? 2 : n + 1;
    }

    @EventHandler
    private void onFetchText(RenderBossBarEvent.BossText bossText) {
        if (this.hideName.get().booleanValue()) {
            bossText.name = Text.of((String)"");
            return;
        }
        if (barMap.isEmpty() || !this.stack.get().booleanValue()) {
            return;
        }
        ClientBossBar ClientBossBar2 = bossText.bossBar;
        Integer n = barMap.get(ClientBossBar2);
        barMap.remove(ClientBossBar2);
        if (n != null && !this.hideName.get().booleanValue()) {
            bossText.name = bossText.name.copy().append(String.valueOf(new StringBuilder().append(" x").append(n)));
        }
    }
}

