/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.mixin.MinecraftClientAccessor;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Items;

public class FastUse
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Mode> mode;
    private final Setting<Boolean> exp;
    private final Setting<Boolean> blocks;

    public FastUse() {
        super(Categories.Player, "fast-use", "Allows you to use items at very high speeds.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.mode = this.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Which items to fast use.").defaultValue(Mode.All).build());
        this.exp = this.sgGeneral.add(new BoolSetting.Builder().name("xP").description("Fast-throws XP bottles if the mode is \"Some\".").defaultValue(false).build());
        this.blocks = this.sgGeneral.add(new BoolSetting.Builder().name("blocks").description("Fast-places blocks if the mode is \"Some\".").defaultValue(false).build());
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        switch (1.$SwitchMap$minegame159$meteorclient$systems$modules$player$FastUse$Mode[this.mode.get().ordinal()]) {
            case 1: {
                ((MinecraftClientAccessor)this.mc).setItemUseCooldown(0);
                break;
            }
            case 2: {
                if ((!this.exp.get().booleanValue() || this.mc.player.getMainHandStack().getItem() != Items.EXPERIENCE_BOTTLE && this.mc.player.getOffHandStack().getItem() != Items.EXPERIENCE_BOTTLE) && (!this.blocks.get().booleanValue() || !(this.mc.player.getMainHandStack().getItem() instanceof BlockItem)) && !(this.mc.player.getOffHandStack().getItem() instanceof BlockItem)) break;
                ((MinecraftClientAccessor)this.mc).setItemUseCooldown(0);
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Some;
        public static final /* enum */ Mode All;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
        }

        static {
            All = new Mode();
            Some = new Mode();
            $VALUES = Mode.$values();
        }

        private static Mode[] $values() {
            return new Mode[]{All, Some};
        }
    }
}

