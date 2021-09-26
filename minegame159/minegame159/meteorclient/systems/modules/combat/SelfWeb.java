/*
 * Decompiled with CFR 0.151.
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
    private final Setting<Boolean> doubles;
    private final Setting<Boolean> turnOff;
    private final SettingGroup sgGeneral;
    private final Setting<Mode> mode;
    private final Setting<Boolean> rotate;
    private final Setting<Integer> range;

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$combat$SelfWeb$Mode[this.mode.get().ordinal()]) {
            case 1: {
                this.placeWeb();
                break;
            }
            case 2: {
                if (EntityUtils.getPlayerTarget(this.range.get().intValue(), SortPriority.LowestDistance, false) == null) break;
                this.placeWeb();
            }
        }
    }

    public SelfWeb() {
        super(Categories.Combat, "self-web", "Automatically places webs on you.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("The mode to use for selfweb.").defaultValue(Mode.Normal).build());
        this.doubles = this.sgGeneral.add(new BoolSetting.Builder().name("double-place").description("Places webs in your upper hitbox as well.").defaultValue(false).build());
        this.turnOff = this.sgGeneral.add(new BoolSetting.Builder().name("auto-toggle").description("Toggles off after placing the webs.").defaultValue(true).build());
        this.range = this.sgGeneral.add(new IntSetting.Builder().name("range").description("How far away the player has to be from you to place webs. Requires Mode to Smart.").defaultValue(3).min(1).sliderMax(7).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Forces you to rotate downwards when placing webs.").defaultValue(true).build());
    }

    private void placeWeb() {
        int n = InvUtils.findItemInHotbar(Items.COBWEB);
        BlockPos BlockPos2 = this.mc.player.getBlockPos();
        BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n, this.rotate.get(), 0, false);
        if (this.doubles.get().booleanValue()) {
            BlockPos2 = this.mc.player.getBlockPos().add(0, 1, 0);
            BlockUtils.place(BlockPos2, Hand.MAIN_HAND, n, this.rotate.get(), 0, false);
        }
        if (this.turnOff.get().booleanValue()) {
            this.toggle();
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode Smart;
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Normal;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            Normal = new Mode();
            Smart = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        private static Mode[] $values() {
            return new Mode[]{Normal, Smart};
        }
    }
}

