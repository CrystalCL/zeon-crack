/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1746;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1822;
import net.minecraft.class_2248;
import net.minecraft.class_2323;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2482;
import net.minecraft.class_2517;
import net.minecraft.class_2533;
import net.minecraft.class_2571;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import net.minecraft.class_498;

public class AntiBed
extends Module {
    private final Setting<Boolean> autoToggle;
    private final Setting<Boolean> onlyOnGround;
    private int place;
    private boolean closeScreen;
    private final Setting<Boolean> rotate;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> autoCenter;

    public AntiBed() {
        super(Categories.Combat, "anti-bed", "Prevents people from placing beds where you are standing.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.autoToggle = this.sgGeneral.add(new BoolSetting.Builder().name("auto-toggle").description("Toggles AntiBed off when finished.").defaultValue(false).build());
        this.autoCenter = this.sgGeneral.add(new BoolSetting.Builder().name("auto-center").description("Teleports you to the center of the blocks.").defaultValue(true).build());
        this.onlyOnGround = this.sgGeneral.add(new BoolSetting.Builder().name("only-on-ground").description("Only toggles Anti Bed when you are standing on a block.").defaultValue(true).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards where the blocks are placed.").defaultValue(true).build());
        this.place = -1;
        this.closeScreen = false;
    }

    private class_2338 checkBlocks() {
        class_2338 class_23382 = null;
        if (!this.mc.field_1687.method_8320(this.mc.field_1724.method_24515().method_10069(0, 1, 1)).method_26215()) {
            class_23382 = this.mc.field_1724.method_24515().method_10069(0, 1, 1);
        } else if (!this.mc.field_1687.method_8320(this.mc.field_1724.method_24515().method_10069(0, 1, -1)).method_26215()) {
            class_23382 = this.mc.field_1724.method_24515().method_10069(0, 1, -1);
        } else if (!this.mc.field_1687.method_8320(this.mc.field_1724.method_24515().method_10069(1, 1, 0)).method_26215()) {
            class_23382 = this.mc.field_1724.method_24515().method_10069(1, 1, 0);
        } else if (!this.mc.field_1687.method_8320(this.mc.field_1724.method_24515().method_10069(-1, 1, 0)).method_26215()) {
            class_23382 = this.mc.field_1724.method_24515().method_10069(-1, 1, 0);
        }
        return class_23382;
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.closeScreen && this.mc.field_1755 instanceof class_498) {
            this.closeScreen = false;
            this.mc.field_1724.method_3137();
            return;
        }
        if (this.closeScreen) {
            return;
        }
        if (!this.mc.field_1687.method_8320(this.mc.field_1724.method_24515().method_10084()).method_26215()) {
            return;
        }
        if (this.onlyOnGround.get().booleanValue() && !this.mc.field_1724.method_24828()) {
            return;
        }
        if (this.place == 0) {
            --this.place;
            this.place(this.mc.field_1724.field_7514.field_7545, true);
        } else if (this.place > 0) {
            --this.place;
        }
        for (int i = 0; i < 9; ++i) {
            class_1799 class_17992 = this.mc.field_1724.field_7514.method_5438(i);
            class_1792 class_17922 = class_17992.method_7909();
            class_2248 class_22482 = class_2248.method_9503((class_1792)class_17922);
            if (class_17922 == class_1802.field_8276 || class_22482 instanceof class_2533 || class_17922 == class_1802.field_8786) {
                this.place(i, true);
                return;
            }
            if (class_22482 instanceof class_2482) {
                this.mc.field_1724.field_7514.field_7545 = i;
                this.mc.field_1690.field_1832.method_23481(true);
                if (this.place == -1) {
                    this.place = 2;
                }
                return;
            }
            if (class_22482 instanceof class_2323) {
                if (this.autoCenter.get().booleanValue()) {
                    class_243 class_2432 = Utils.vec3d(this.mc.field_1724.method_24515());
                    if (this.mc.field_1724.method_5735() == class_2350.field_11035) {
                        class_2432 = class_2432.method_1031(0.5, 0.0, 0.7);
                    } else if (this.mc.field_1724.method_5735() == class_2350.field_11043) {
                        class_2432 = class_2432.method_1031(0.5, 0.0, 0.3);
                    } else if (this.mc.field_1724.method_5735() == class_2350.field_11034) {
                        class_2432 = class_2432.method_1031(0.7, 0.0, 0.5);
                    } else if (this.mc.field_1724.method_5735() == class_2350.field_11039) {
                        class_2432 = class_2432.method_1031(0.3, 0.0, 0.5);
                    }
                    this.mc.field_1724.method_5814(class_2432.field_1352, class_2432.field_1351, class_2432.field_1350);
                    this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(class_2432.field_1352, class_2432.field_1351, class_2432.field_1350, this.mc.field_1724.method_24828()));
                }
                this.place(i, true);
                return;
            }
            if (class_17922 == class_1802.field_8121) {
                if (this.autoCenter.get().booleanValue()) {
                    class_243 class_2433 = Utils.vec3d(this.mc.field_1724.method_24515());
                    class_2338 class_23382 = this.checkBlocks();
                    if (class_23382 == null) {
                        return;
                    }
                    if (class_2433.method_1020((class_243)Utils.vec3d((class_2338)class_23382)).field_1352 > 0.0) {
                        class_2433 = class_2433.method_1031(0.7, 0.0, 0.5);
                    } else if (class_2433.method_1020((class_243)Utils.vec3d((class_2338)class_23382)).field_1352 < 0.0) {
                        class_2433 = class_2433.method_1031(0.3, 0.0, 0.5);
                    } else if (class_2433.method_1020((class_243)Utils.vec3d((class_2338)class_23382)).field_1350 > 0.0) {
                        class_2433 = class_2433.method_1031(0.5, 0.0, 0.7);
                    } else if (class_2433.method_1020((class_243)Utils.vec3d((class_2338)class_23382)).field_1350 < 0.0) {
                        class_2433 = class_2433.method_1031(0.5, 0.0, 0.3);
                    }
                    this.mc.field_1724.method_5814(class_2433.field_1352, class_2433.field_1351, class_2433.field_1350);
                    this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(class_2433.field_1352, class_2433.field_1351, class_2433.field_1350, this.mc.field_1724.method_24828()));
                }
                this.place(i, true);
                return;
            }
            if (class_17922 instanceof class_1746 || class_17922 == class_1802.field_8865 || class_17922 == class_1802.field_8810 || class_17922 == class_1802.field_8530 || class_17922 instanceof class_1822 || class_17922 == class_1802.field_8366 || class_22482 instanceof class_2517 || class_22482 instanceof class_2571) {
                this.place(i, true);
                if (class_17922 instanceof class_1822) {
                    this.closeScreen = true;
                }
                return;
            }
            if (class_17922 != class_1802.field_16482 || class_17992.method_7947() < 2) continue;
            this.place(i, false);
            this.place(i, true);
            return;
        }
    }

    @Override
    public void onDeactivate() {
        this.closeScreen = false;
    }

    private void place(int n, boolean bl) {
        class_2338 class_23382 = bl ? this.mc.field_1724.method_24515().method_10084() : this.mc.field_1724.method_24515();
        if (BlockUtils.place(class_23382, class_1268.field_5808, n, this.rotate.get(), 100, true) && this.autoToggle.get().booleanValue()) {
            this.toggle();
        }
    }
}

