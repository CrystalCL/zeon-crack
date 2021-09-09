/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.Items
 *  net.minecraft.util.math.BlockPos
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.SortPriority;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

public class SelfWeb
extends Module {
    private final /* synthetic */ Setting<Boolean> turnOff;
    private final /* synthetic */ Setting<Boolean> rotate;
    private final /* synthetic */ Setting<Mode> mode;
    private final /* synthetic */ Setting<Integer> range;
    private final /* synthetic */ Setting<Boolean> doubles;
    private final /* synthetic */ SettingGroup sgGeneral;

    public SelfWeb() {
        super(Categories.Combat, "self-web", "Automatically places webs on you.");
        SelfWeb lIIIIIIlIIll;
        lIIIIIIlIIll.sgGeneral = lIIIIIIlIIll.settings.getDefaultGroup();
        lIIIIIIlIIll.mode = lIIIIIIlIIll.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode to use for selfweb.").defaultValue(Mode.Normal).build());
        lIIIIIIlIIll.doubles = lIIIIIIlIIll.sgGeneral.add(new BoolSetting.Builder().name("double-place").description("Places webs in your upper hitbox as well.").defaultValue(false).build());
        lIIIIIIlIIll.turnOff = lIIIIIIlIIll.sgGeneral.add(new BoolSetting.Builder().name("auto-toggle").description("Toggles off after placing the webs.").defaultValue(true).build());
        lIIIIIIlIIll.range = lIIIIIIlIIll.sgGeneral.add(new IntSetting.Builder().name("range").description("How far away the player has to be from you to place webs. Requires Mode to Smart.").defaultValue(3).min(1).sliderMax(7).build());
        lIIIIIIlIIll.rotate = lIIIIIIlIIll.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate downwards when placing webs.").defaultValue(true).build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre lIIIIIIIllll) {
        SelfWeb lIIIIIIIlllI;
        switch (lIIIIIIIlllI.mode.get()) {
            case Normal: {
                lIIIIIIIlllI.placeWeb();
                break;
            }
            case Smart: {
                if (EntityUtils.getPlayerTarget(lIIIIIIIlllI.range.get().intValue(), SortPriority.LowestDistance, false) == null) break;
                lIIIIIIIlllI.placeWeb();
            }
        }
    }

    private void placeWeb() {
        SelfWeb lIIIIIIIlIlI;
        int lIIIIIIIlIIl = InvUtils.findItemInHotbar(Items.COBWEB);
        BlockPos lIIIIIIIlIII = lIIIIIIIlIlI.mc.player.getBlockPos();
        BlockUtils.place(lIIIIIIIlIII, Hand.MAIN_HAND, lIIIIIIIlIIl, lIIIIIIIlIlI.rotate.get(), 0, false);
        if (lIIIIIIIlIlI.doubles.get().booleanValue()) {
            lIIIIIIIlIII = lIIIIIIIlIlI.mc.player.getBlockPos().add(0, 1, 0);
            BlockUtils.place(lIIIIIIIlIII, Hand.MAIN_HAND, lIIIIIIIlIIl, lIIIIIIIlIlI.rotate.get(), 0, false);
        }
        if (lIIIIIIIlIlI.turnOff.get().booleanValue()) {
            lIIIIIIIlIlI.toggle();
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Smart;
        public static final /* synthetic */ /* enum */ Mode Normal;
        private static final /* synthetic */ Mode[] $VALUES;

        static {
            Normal = new Mode();
            Smart = new Mode();
            $VALUES = Mode.$values();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Normal, Smart};
        }

        public static Mode valueOf(String llllllllllllllllIlllIlIlIIIIllII) {
            return Enum.valueOf(Mode.class, llllllllllllllllIlllIlIlIIIIllII);
        }

        private Mode() {
            Mode llllllllllllllllIlllIlIlIIIIIlll;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }
}

