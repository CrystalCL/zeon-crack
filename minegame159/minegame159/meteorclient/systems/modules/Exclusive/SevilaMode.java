/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.Exclusive;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.Rotations;
import net.minecraft.item.Items;

public class SevilaMode
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> pause;
    private short count;
    private final Setting<Integer> speed;

    @EventHandler
    public void onTick(TickEvent.Post post) {
        if (this.pause.get().booleanValue() && (this.mc.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE || this.mc.player.getMainHandStack().getItem() == Items.ENDER_PEARL || this.mc.player.getOffHandStack().getItem() == Items.EXPERIENCE_BOTTLE || this.mc.player.getOffHandStack().getItem() == Items.ENDER_PEARL)) {
            return;
        }
        this.count = (short)(this.count + this.speed.get());
        if (this.count > 180) {
            this.count = (short)-180;
        }
        Rotations.rotate(this.count, 0.0);
    }

    public SevilaMode() {
        super(Categories.Exclusive, "sevila-mode", "Many, very many rotate...");
        this.sgGeneral = this.settings.createGroup("General");
        this.count = 0;
        this.speed = this.sgGeneral.add(new IntSetting.Builder().name("speed").description("The speed at which you rotate.").defaultValue(5).min(0).sliderMax(100).build());
        this.pause = this.sgGeneral.add(new BoolSetting.Builder().name("pause").description("Stop rotate when you use Exp and Pearl.").defaultValue(true).build());
    }
}

