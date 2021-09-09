/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Items
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
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> pause;
    private final /* synthetic */ Setting<Integer> speed;
    private /* synthetic */ short count;

    @EventHandler
    public void onTick(TickEvent.Post lllllllllllllllllIIIIIIIIlllIIII) {
        SevilaMode lllllllllllllllllIIIIIIIIlllIIIl;
        if (lllllllllllllllllIIIIIIIIlllIIIl.pause.get().booleanValue() && (lllllllllllllllllIIIIIIIIlllIIIl.mc.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE || lllllllllllllllllIIIIIIIIlllIIIl.mc.player.getMainHandStack().getItem() == Items.ENDER_PEARL || lllllllllllllllllIIIIIIIIlllIIIl.mc.player.getOffHandStack().getItem() == Items.EXPERIENCE_BOTTLE || lllllllllllllllllIIIIIIIIlllIIIl.mc.player.getOffHandStack().getItem() == Items.ENDER_PEARL)) {
            return;
        }
        lllllllllllllllllIIIIIIIIlllIIIl.count = (short)(lllllllllllllllllIIIIIIIIlllIIIl.count + lllllllllllllllllIIIIIIIIlllIIIl.speed.get());
        if (lllllllllllllllllIIIIIIIIlllIIIl.count > 180) {
            lllllllllllllllllIIIIIIIIlllIIIl.count = (short)-180;
        }
        Rotations.rotate(lllllllllllllllllIIIIIIIIlllIIIl.count, 0.0);
    }

    public SevilaMode() {
        super(Categories.Exclusive, "sevila-mode", "Many, very many rotate...");
        SevilaMode lllllllllllllllllIIIIIIIIllIllII;
        lllllllllllllllllIIIIIIIIllIllII.sgGeneral = lllllllllllllllllIIIIIIIIllIllII.settings.createGroup("General");
        lllllllllllllllllIIIIIIIIllIllII.count = 0;
        lllllllllllllllllIIIIIIIIllIllII.speed = lllllllllllllllllIIIIIIIIllIllII.sgGeneral.add(new IntSetting.Builder().name("speed").description("The speed at which you rotate.").defaultValue(5).min(0).sliderMax(100).build());
        lllllllllllllllllIIIIIIIIllIllII.pause = lllllllllllllllllIIIIIIIIllIllII.sgGeneral.add(new BoolSetting.Builder().name("pause").description("Stop rotate when you use Exp and Pearl.").defaultValue(true).build());
    }
}

