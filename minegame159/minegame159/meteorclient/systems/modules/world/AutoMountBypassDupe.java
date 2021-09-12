/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.MountBypass;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1492;
import net.minecraft.class_1501;
import net.minecraft.class_1747;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2480;
import net.minecraft.class_2596;
import net.minecraft.class_2824;
import net.minecraft.class_2828;
import net.minecraft.class_2848;
import net.minecraft.class_491;
import org.lwjgl.glfw.GLFW;

public class AutoMountBypassDupe
extends Module {
    private boolean noCancel;
    private int timer;
    private final List<Integer> slotsToThrow;
    private final Setting<Boolean> shulkersOnly;
    private class_1492 entity;
    private boolean sneak;
    private final List<Integer> slotsToMove;
    private final Setting<Boolean> faceDown;
    private final Setting<Integer> delay;
    private final SettingGroup sgGeneral;

    private int getInvSize(class_1297 class_12972) {
        if (!(class_12972 instanceof class_1492)) {
            return -1;
        }
        if (!((class_1492)class_12972).method_6703()) {
            return 0;
        }
        if (class_12972 instanceof class_1501) {
            return 3 * ((class_1501)class_12972).method_6803();
        }
        return 15;
    }

    private boolean isDupeTime() {
        if (this.mc.field_1724.method_5854() != this.entity || this.entity.method_6703() || this.mc.field_1724.field_7512.method_7602().size() == 46) {
            return false;
        }
        if (this.mc.field_1724.field_7512.method_7602().size() > 38) {
            for (int i = 2; i < this.getDupeSize() + 1; ++i) {
                if (!this.mc.field_1724.field_7512.method_7611(i).method_7681()) continue;
                return true;
            }
        }
        return false;
    }

    public AutoMountBypassDupe() {
        super(Categories.World, "auto-mount-bypass-dupe", "Does the mount bypass dupe for you. Disable with esc.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.shulkersOnly = this.sgGeneral.add(new BoolSetting.Builder().name("shulker-only").description("Only moves shulker boxes into the inventory").defaultValue(true).build());
        this.faceDown = this.sgGeneral.add(new BoolSetting.Builder().name("rotate-down").description("Faces down when dropping items.").defaultValue(true).build());
        this.delay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("The delay in ticks between actions.").defaultValue(4).min(0).build());
        this.slotsToMove = new ArrayList<Integer>();
        this.slotsToThrow = new ArrayList<Integer>();
        this.noCancel = false;
        this.sneak = false;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        Iterator<Object> iterator;
        if (GLFW.glfwGetKey((long)this.mc.method_22683().method_4490(), (int)256) == 1) {
            this.toggle();
            this.mc.field_1724.method_7346();
            return;
        }
        if (this.timer > 0) {
            --this.timer;
            return;
        }
        this.timer = this.delay.get();
        int n = this.getInvSize(this.mc.field_1724.method_5854());
        for (class_1297 class_12972 : this.mc.field_1687.method_18112()) {
            if (!(class_12972.method_5739((class_1297)this.mc.field_1724) < 5.0f) || !(class_12972 instanceof class_1492) || !((class_1492)class_12972).method_6727()) continue;
            this.entity = (class_1492)class_12972;
        }
        if (this.entity == null) {
            return;
        }
        if (this.sneak) {
            this.mc.field_1724.field_3944.method_2883((class_2596)new class_2848((class_1297)this.mc.field_1724, class_2848.class_2849.field_12984));
            this.mc.field_1724.method_5660(false);
            this.sneak = false;
            return;
        }
        if (n == -1) {
            if (this.entity.method_6703() || this.mc.field_1724.method_6047().method_7909() == class_1802.field_8106) {
                this.mc.field_1724.field_3944.method_2883((class_2596)new class_2824((class_1297)this.entity, class_1268.field_5808, this.mc.field_1724.method_5715()));
            } else {
                int n2 = InvUtils.findItemWithCount((class_1792)class_1802.field_8106).slot;
                if (n2 != -1 && n2 < 9) {
                    this.mc.field_1724.field_7514.field_7545 = n2;
                } else {
                    ChatUtils.moduleError(this, "Cannot find chest in your hotbar... disabling.", new Object[0]);
                    this.toggle();
                }
            }
        } else if (n == 0) {
            if (this.isDupeTime()) {
                if (!this.slotsToThrow.isEmpty()) {
                    if (this.faceDown.get().booleanValue()) {
                        this.mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2831(this.mc.field_1724.field_6031, 90.0f, this.mc.field_1724.method_24828()));
                    }
                    iterator = this.slotsToThrow.iterator();
                    while (iterator.hasNext()) {
                        int n3 = (Integer)iterator.next();
                        InvUtils.drop().slotId(n3);
                    }
                    this.slotsToThrow.clear();
                } else {
                    for (int i = 2; i < this.getDupeSize() + 1; ++i) {
                        this.slotsToThrow.add(i);
                        if (4 > -1) continue;
                        return;
                    }
                }
            } else {
                this.mc.field_1724.method_7346();
                this.mc.field_1724.field_3944.method_2883((class_2596)new class_2848((class_1297)this.mc.field_1724, class_2848.class_2849.field_12979));
                this.mc.field_1724.method_5660(true);
                this.sneak = true;
            }
        } else if (!(this.mc.field_1755 instanceof class_491)) {
            this.mc.field_1724.method_3132();
        } else if (n > 0) {
            if (this.slotsToMove.isEmpty()) {
                int n4;
                boolean bl = true;
                for (n4 = 2; n4 <= n; ++n4) {
                    if (((class_1799)this.mc.field_1724.field_7512.method_7602().get(n4)).method_7960()) continue;
                    bl = false;
                    break;
                }
                if (bl) {
                    for (n4 = n + 2; n4 < this.mc.field_1724.field_7512.method_7602().size(); ++n4) {
                        if (((class_1799)this.mc.field_1724.field_7512.method_7602().get(n4)).method_7960() || this.mc.field_1724.field_7512.method_7611(n4).method_7677().method_7909() == class_1802.field_8106 || (!(this.mc.field_1724.field_7512.method_7611(n4).method_7677().method_7909() instanceof class_1747) || !(((class_1747)this.mc.field_1724.field_7512.method_7611(n4).method_7677().method_7909()).method_7711() instanceof class_2480)) && this.shulkersOnly.get().booleanValue()) continue;
                        this.slotsToMove.add(n4);
                        if (this.slotsToMove.size() < n) {
                            if (-1 < 0) continue;
                            return;
                        }
                        break;
                    }
                } else {
                    this.noCancel = true;
                    this.mc.field_1724.field_3944.method_2883((class_2596)new class_2824((class_1297)this.entity, class_1268.field_5808, this.entity.method_19538().method_1031((double)(this.entity.method_17681() / 2.0f), (double)(this.entity.method_17682() / 2.0f), (double)(this.entity.method_17681() / 2.0f)), this.mc.field_1724.method_5715()));
                    this.noCancel = false;
                    return;
                }
            }
            if (!this.slotsToMove.isEmpty()) {
                iterator = this.slotsToMove.iterator();
                while (iterator.hasNext()) {
                    int n5 = (Integer)iterator.next();
                    InvUtils.quickMove().slotId(n5);
                }
                this.slotsToMove.clear();
            }
        }
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send send) {
        if (this.noCancel) {
            return;
        }
        Modules.get().get(MountBypass.class).onSendPacket(send);
    }

    private int getDupeSize() {
        if (this.mc.field_1724.method_5854() != this.entity || this.entity.method_6703() || this.mc.field_1724.field_7512.method_7602().size() == 46) {
            return 0;
        }
        return this.mc.field_1724.field_7512.method_7602().size() - 38;
    }

    @Override
    public void onActivate() {
        this.timer = 0;
    }
}

