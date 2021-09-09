/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Items
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
    private final /* synthetic */ Setting<Boolean> blocks;
    private final /* synthetic */ Setting<Mode> mode;
    private final /* synthetic */ Setting<Boolean> exp;
    private final /* synthetic */ SettingGroup sgGeneral;

    @EventHandler
    private void onTick(TickEvent.Post lllllllllllllllllIlIlllIlIIlIIll) {
        FastUse lllllllllllllllllIlIlllIlIIlIlII;
        switch (lllllllllllllllllIlIlllIlIIlIlII.mode.get()) {
            case All: {
                ((MinecraftClientAccessor)lllllllllllllllllIlIlllIlIIlIlII.mc).setItemUseCooldown(0);
                break;
            }
            case Some: {
                if ((!lllllllllllllllllIlIlllIlIIlIlII.exp.get().booleanValue() || lllllllllllllllllIlIlllIlIIlIlII.mc.player.getMainHandStack().getItem() != Items.EXPERIENCE_BOTTLE && lllllllllllllllllIlIlllIlIIlIlII.mc.player.getOffHandStack().getItem() != Items.EXPERIENCE_BOTTLE) && (!lllllllllllllllllIlIlllIlIIlIlII.blocks.get().booleanValue() || !(lllllllllllllllllIlIlllIlIIlIlII.mc.player.getMainHandStack().getItem() instanceof BlockItem)) && !(lllllllllllllllllIlIlllIlIIlIlII.mc.player.getOffHandStack().getItem() instanceof BlockItem)) break;
                ((MinecraftClientAccessor)lllllllllllllllllIlIlllIlIIlIlII.mc).setItemUseCooldown(0);
            }
        }
    }

    public FastUse() {
        super(Categories.Player, "fast-use", "Allows you to use items at very high speeds.");
        FastUse lllllllllllllllllIlIlllIlIIlIllI;
        lllllllllllllllllIlIlllIlIIlIllI.sgGeneral = lllllllllllllllllIlIlllIlIIlIllI.settings.getDefaultGroup();
        lllllllllllllllllIlIlllIlIIlIllI.mode = lllllllllllllllllIlIlllIlIIlIllI.sgGeneral.add(new EnumSetting.Builder().name("mode").description("Which items to fast use.").defaultValue(Mode.All).build());
        lllllllllllllllllIlIlllIlIIlIllI.exp = lllllllllllllllllIlIlllIlIIlIllI.sgGeneral.add(new BoolSetting.Builder().name("xP").description("Fast-throws XP bottles if the mode is \"Some\".").defaultValue(false).build());
        lllllllllllllllllIlIlllIlIIlIllI.blocks = lllllllllllllllllIlIlllIlIIlIllI.sgGeneral.add(new BoolSetting.Builder().name("blocks").description("Fast-places blocks if the mode is \"Some\".").defaultValue(false).build());
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode All;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Some;

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{All, Some};
        }

        static {
            All = new Mode();
            Some = new Mode();
            $VALUES = Mode.$values();
        }

        public static Mode valueOf(String lllllllllllllllllIIIllIllllIIIlI) {
            return Enum.valueOf(Mode.class, lllllllllllllllllIIIllIllllIIIlI);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        private Mode() {
            Mode lllllllllllllllllIIIllIlllIlllII;
        }
    }
}

