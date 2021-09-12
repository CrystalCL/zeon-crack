/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.combat;

import java.util.ArrayList;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_2199;
import net.minecraft.class_2231;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2269;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_471;

public class AutoAnvil
extends Module {
    private final Setting<Integer> startHeight;
    private final Setting<Integer> delay;
    private final Setting<Boolean> placeButton;
    private final Setting<Double> decrease;
    private final Setting<Boolean> toggleOnBreak;
    private final Setting<Boolean> rotate;
    private final Setting<Integer> blocksPerTick;
    private class_1657 target;
    private final ArrayList<class_243> antiStepStructure;
    private final Setting<Double> range;
    private int timer;
    private final SettingGroup sgPlace;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> antiStep;
    private final Setting<Integer> minHeight;

    public AutoAnvil() {
        super(Categories.Combat, "auto-anvil", "Automatically places anvils above players to destroy helmets.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgPlace = this.settings.createGroup("Place");
        this.antiStepStructure = new ArrayList<class_243>(this){
            final AutoAnvil this$0;
            {
                this.this$0 = autoAnvil;
                this.add(new class_243(1.0, 2.0, 0.0));
                this.add(new class_243(-1.0, 2.0, 0.0));
                this.add(new class_243(0.0, 2.0, 1.0));
                this.add(new class_243(0.0, 2.0, -1.0));
            }
        };
        this.toggleOnBreak = this.sgGeneral.add(new BoolSetting.Builder().name("toggle-on-break").description("Toggles when the target's helmet slot is empty.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Automatically rotates towards the position anvils/pressure plates/buttons are placed.").defaultValue(true).build());
        this.antiStep = this.sgGeneral.add(new BoolSetting.Builder().name("anti step").description("Place extra blocks for preventing the enemy from escaping").defaultValue(false).build());
        this.range = this.sgPlace.add(new DoubleSetting.Builder().name("range").description("How far away the target can be to be affected.").defaultValue(4.0).min(0.0).build());
        this.delay = this.sgPlace.add(new IntSetting.Builder().name("delay").description("The delay in between anvil placements.").min(0).defaultValue(0).sliderMax(50).build());
        this.startHeight = this.sgPlace.add(new IntSetting.Builder().name("start-Height").description("The height at beginning").defaultValue(5).min(0).max(10).sliderMin(0).sliderMax(10).build());
        this.minHeight = this.sgPlace.add(new IntSetting.Builder().name("min-Height").description("The minimum height accetable").defaultValue(1).min(0).max(5).sliderMin(0).sliderMax(5).build());
        this.decrease = this.sgPlace.add(new DoubleSetting.Builder().name("decrease").description("The distance where it will start decrease").defaultValue(1.4).min(0.0).max(4.0).sliderMin(0.0).sliderMax(4.0).build());
        this.blocksPerTick = this.sgGeneral.add(new IntSetting.Builder().name("blocks-per-tick").description("The number of blocks you can place every ticks").defaultValue(4).min(2).max(8).sliderMin(2).sliderMax(8).build());
        this.placeButton = this.sgPlace.add(new BoolSetting.Builder().name("place-at-feet").description("Automatically places a button or pressure plate at the targets feet to break the anvils.").defaultValue(true).build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.isActive() && this.toggleOnBreak.get().booleanValue() && this.target != null && this.target.field_7514.method_7372(3).method_7960()) {
            ChatUtils.moduleError(this, "Target head slot is empty... disabling.", new Object[0]);
            this.toggle();
            return;
        }
        if (this.target != null && ((double)this.mc.field_1724.method_5739((class_1297)this.target) > this.range.get() || !this.target.method_5805())) {
            this.target = null;
        }
        for (class_1657 object : this.mc.field_1687.method_18456()) {
            if (object == this.mc.field_1724 || !Friends.get().attack(object) || !object.method_5805() || (double)this.mc.field_1724.method_5739((class_1297)object) > this.range.get() || this.isHole(object.method_24515())) continue;
            if (this.target == null) {
                this.target = object;
                continue;
            }
            if (!(this.mc.field_1724.method_5739((class_1297)this.target) > this.mc.field_1724.method_5739((class_1297)object))) continue;
            this.target = object;
        }
        if (this.target == null) {
            for (FakePlayerEntity fakePlayerEntity : FakePlayerUtils.getPlayers().keySet()) {
                if (!Friends.get().attack((class_1657)fakePlayerEntity) || !fakePlayerEntity.method_5805() || (double)this.mc.field_1724.method_5739((class_1297)fakePlayerEntity) > this.range.get() || this.isHole(fakePlayerEntity.method_24515())) continue;
                if (this.target == null) {
                    this.target = fakePlayerEntity;
                    continue;
                }
                if (!(this.mc.field_1724.method_5739((class_1297)this.target) > this.mc.field_1724.method_5739((class_1297)fakePlayerEntity))) continue;
                this.target = fakePlayerEntity;
            }
        }
        if (this.target == null) {
            return;
        }
        int n = 0;
        if (this.timer >= this.delay.get() && this.target != null) {
            class_2338 class_23382;
            int n2;
            this.timer = 0;
            int n3 = this.getAnvilSlot();
            if (n3 == -1) {
                return;
            }
            if (this.placeButton.get().booleanValue()) {
                n2 = this.getFloorSlot();
                class_2338 class_23383 = this.target.method_24515();
                if (BlockUtils.place(class_23383, class_1268.field_5808, n2, this.rotate.get(), 0, false)) {
                    ++n;
                }
            }
            if (this.antiStep.get().booleanValue()) {
                n2 = InvUtils.findItemInHotbar(class_2246.field_10540.method_8389());
                if (n2 == -1) {
                    return;
                }
                for (class_243 class_2432 : this.antiStepStructure) {
                    class_23382 = this.target.method_24515().method_10080(class_2432.field_1352, class_2432.field_1351, class_2432.field_1350);
                    if (!BlockUtils.place(class_23382, class_1268.field_5808, n2, this.rotate.get(), 0, true) || ++n != this.blocksPerTick.get()) continue;
                    return;
                }
            }
            n2 = this.startHeight.get() + (int)(this.mc.field_1724.method_23318() - this.target.method_23318());
            for (double d = Math.sqrt(Math.pow(this.mc.field_1724.method_19538().field_1352 - this.target.method_23317(), 2.0) + Math.pow(this.mc.field_1724.method_19538().field_1350 - this.target.method_23321(), 2.0)); d > this.decrease.get(); d -= this.decrease.get().doubleValue()) {
                --n2;
            }
            if (n2 <= this.minHeight.get()) {
                ChatUtils.moduleError(this, "Target too far away... disabling.", new Object[0]);
                this.toggle();
                return;
            }
            class_23382 = this.target.method_24515().method_10084().method_10069(0, n2, 0);
            BlockUtils.place(class_23382, class_1268.field_5808, n3, this.rotate.get(), 0, true);
        } else {
            ++this.timer;
        }
    }

    @Override
    public String getInfoString() {
        if (this.target != null && this.target instanceof class_1657) {
            return this.target.method_5820();
        }
        if (this.target != null) {
            return this.target.method_5864().method_5897().getString();
        }
        return null;
    }

    public int getFloorSlot() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            class_1792 class_17922 = this.mc.field_1724.field_7514.method_5438(i).method_7909();
            class_2248 class_22482 = class_2248.method_9503((class_1792)class_17922);
            if (!(class_22482 instanceof class_2231) && !(class_22482 instanceof class_2269)) continue;
            n = i;
            break;
        }
        return n;
    }

    private boolean isHole(class_2338 class_23382) {
        class_2338.class_2339 class_23392 = new class_2338.class_2339(class_23382.method_10263(), class_23382.method_10264(), class_23382.method_10260());
        return this.mc.field_1687.method_8320(class_23392.method_10069(1, 0, 0)).method_26204().method_27839(class_2246.field_10124) || this.mc.field_1687.method_8320(class_23392.method_10069(-1, 0, 0)).method_26204().method_27839(class_2246.field_10124) || this.mc.field_1687.method_8320(class_23392.method_10069(0, 0, 1)).method_26204().method_27839(class_2246.field_10124) || this.mc.field_1687.method_8320(class_23392.method_10069(0, 0, -1)).method_26204().method_27839(class_2246.field_10124);
    }

    private int getAnvilSlot() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            class_1792 class_17922 = this.mc.field_1724.field_7514.method_5438(i).method_7909();
            class_2248 class_22482 = class_2248.method_9503((class_1792)class_17922);
            if (!(class_22482 instanceof class_2199)) continue;
            n = i;
            break;
        }
        return n;
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent openScreenEvent) {
        if (openScreenEvent.screen instanceof class_471) {
            this.mc.field_1724.method_3137();
        }
    }

    @Override
    public void onActivate() {
        this.timer = 0;
        this.target = null;
    }
}

