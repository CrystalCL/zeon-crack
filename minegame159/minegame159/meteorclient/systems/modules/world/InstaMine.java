/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.world;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.entity.player.StartBreakingBlockEvent;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.InvUtils;
import minegame159.meteorclient.utils.player.Rotations;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.class_1268;
import net.minecraft.class_1802;
import net.minecraft.class_1810;
import net.minecraft.class_1922;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_2596;
import net.minecraft.class_2846;
import net.minecraft.class_2879;
import net.minecraft.class_3965;

public class InstaMine
extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> crystal;
    private final class_2338.class_2339 blockPos;
    private final Setting<Boolean> rotate;
    private final Setting<ShapeMode> shapeMode;
    private final Setting<SettingColor> lineColor;
    private final SettingGroup sgRender;
    private class_2350 direction;
    private boolean shouldMine;
    private final Setting<Boolean> pick;
    private int ticks;
    private final Setting<Integer> tickDelay;
    private final Setting<Boolean> render;
    private final Setting<SettingColor> sideColor;

    @Override
    public void onActivate() {
        this.ticks = 0;
        this.blockPos.method_10103(0, -1, 0);
        this.shouldMine = false;
    }

    private void lambda$onTick$0() {
        this.mc.method_1562().method_2883((class_2596)new class_2846(class_2846.class_2847.field_12973, (class_2338)this.blockPos, this.direction));
    }

    @EventHandler
    private void onRender(RenderEvent renderEvent) {
        if (!(this.render.get().booleanValue() && this.shouldMine() && this.shouldMine && this.blockPos.method_10264() != -1)) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (class_2338)this.blockPos, this.sideColor.get(), this.lineColor.get(), this.shapeMode.get(), 0);
    }

    @EventHandler
    private void onTick(TickEvent.Pre pre) {
        if (this.ticks >= this.tickDelay.get()) {
            this.ticks = 0;
            if (this.shouldMine() && this.shouldMine && !this.mc.field_1687.method_8320((class_2338)this.blockPos).method_26215()) {
                if (this.crystal.get().booleanValue() && (this.mc.field_1687.method_8320((class_2338)this.blockPos).method_26204().method_27839(class_2246.field_10540) || this.mc.field_1687.method_8320((class_2338)this.blockPos).method_26204().method_27839(class_2246.field_9987))) {
                    class_1268 class_12682 = InvUtils.getHand(class_1802.field_8301);
                    int n = InvUtils.findItemInHotbar(class_1802.field_8301);
                    int n2 = this.mc.field_1724.field_7514.field_7545;
                    if (class_12682 != class_1268.field_5810 && n != -1) {
                        this.mc.field_1724.field_7514.field_7545 = n;
                    }
                    this.mc.field_1761.method_2896(this.mc.field_1724, this.mc.field_1687, class_12682, new class_3965(this.mc.field_1724.method_19538(), this.direction, (class_2338)this.blockPos, false));
                    if (class_12682 != class_1268.field_5810) {
                        this.mc.field_1724.field_7514.field_7545 = n2;
                    }
                }
                if (this.rotate.get().booleanValue()) {
                    Rotations.rotate(Rotations.getYaw((class_2338)this.blockPos), Rotations.getPitch((class_2338)this.blockPos), this::lambda$onTick$0);
                } else {
                    this.mc.method_1562().method_2883((class_2596)new class_2846(class_2846.class_2847.field_12973, (class_2338)this.blockPos, this.direction));
                }
                this.mc.method_1562().method_2883((class_2596)new class_2879(class_1268.field_5808));
            }
        } else {
            ++this.ticks;
        }
    }

    @EventHandler
    private void onStartBreakingBlock(StartBreakingBlockEvent startBreakingBlockEvent) {
        this.direction = startBreakingBlockEvent.direction;
        this.blockPos.method_10101((class_2382)startBreakingBlockEvent.blockPos);
        this.shouldMine = this.mc.field_1687.method_8320(startBreakingBlockEvent.blockPos).method_26214((class_1922)this.mc.field_1687, startBreakingBlockEvent.blockPos) >= 0.0f;
    }

    private boolean shouldMine() {
        if (!this.pick.get().booleanValue()) {
            return true;
        }
        return this.pick.get() != false && this.mc.field_1724.field_7514.method_7391().method_7909() instanceof class_1810 && (this.mc.field_1724.method_6047().method_7909() == class_1802.field_8377 || this.mc.field_1724.method_6047().method_7909() == class_1802.field_22024);
    }

    public InstaMine() {
        super(Categories.World, "insta-mine", "Attempts to instantly mine blocks.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgRender = this.settings.createGroup("Render");
        this.tickDelay = this.sgGeneral.add(new IntSetting.Builder().name("delay").description("The delay between breaks.").defaultValue(0).min(0).sliderMax(20).build());
        this.pick = this.sgGeneral.add(new BoolSetting.Builder().name("only-pick").description("Only tries to mine the block if you are holding a pickaxe.").defaultValue(true).build());
        this.crystal = this.sgGeneral.add(new BoolSetting.Builder().name("crystal").description("Places an end crystal above the block getting mined.").defaultValue(false).build());
        this.rotate = this.sgGeneral.add(new BoolSetting.Builder().name("rotate").description("Faces the blocks being mined server side.").defaultValue(true).build());
        this.render = this.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        this.shapeMode = this.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        this.sideColor = this.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        this.lineColor = this.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
        this.blockPos = new class_2338.class_2339(0, -1, 0);
    }
}

