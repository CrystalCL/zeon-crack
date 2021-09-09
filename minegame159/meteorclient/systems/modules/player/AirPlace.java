/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Hand
 *  net.minecraft.item.BlockItem
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.hit.BlockHitResult
 */
package minegame159.meteorclient.systems.modules.player;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.RenderEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.ShapeMode;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.render.color.SettingColor;
import minegame159.meteorclient.utils.world.BlockUtils;
import net.minecraft.util.Hand;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.hit.BlockHitResult;

public class AirPlace
extends Module {
    private final /* synthetic */ Setting<Boolean> render;
    private final /* synthetic */ Setting<ShapeMode> shapeMode;
    private final /* synthetic */ Setting<Place> placeWhen;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<SettingColor> lineColor;
    private /* synthetic */ BlockPos target;
    private final /* synthetic */ Setting<SettingColor> sideColor;
    private final /* synthetic */ SettingGroup sgRender;

    @EventHandler
    private void onRender(RenderEvent llllllllllllllllIllIlIlIIIlIlIIl) {
        AirPlace llllllllllllllllIllIlIlIIIlIlIII;
        if (!(llllllllllllllllIllIlIlIIIlIlIII.mc.crosshairTarget instanceof BlockHitResult && llllllllllllllllIllIlIlIIIlIlIII.mc.world.getBlockState(llllllllllllllllIllIlIlIIIlIlIII.target).isAir() && llllllllllllllllIllIlIlIIIlIlIII.mc.player.getMainHandStack().getItem() instanceof BlockItem && llllllllllllllllIllIlIlIIIlIlIII.render.get().booleanValue())) {
            return;
        }
        Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, llllllllllllllllIllIlIlIIIlIlIII.target, llllllllllllllllIllIlIlIIIlIlIII.sideColor.get(), llllllllllllllllIllIlIlIIIlIlIII.lineColor.get(), llllllllllllllllIllIlIlIIIlIlIII.shapeMode.get(), 0);
    }

    @EventHandler
    private void onTick(TickEvent.Post llllllllllllllllIllIlIlIIIlIllIl) {
        AirPlace llllllllllllllllIllIlIlIIIlIlllI;
        if (!(llllllllllllllllIllIlIlIIIlIlllI.mc.crosshairTarget instanceof BlockHitResult) || !(llllllllllllllllIllIlIlIIIlIlllI.mc.player.getMainHandStack().getItem() instanceof BlockItem)) {
            return;
        }
        llllllllllllllllIllIlIlIIIlIlllI.target = ((BlockHitResult)llllllllllllllllIllIlIlIIIlIlllI.mc.crosshairTarget).getBlockPos();
        if (!llllllllllllllllIllIlIlIIIlIlllI.mc.world.getBlockState(llllllllllllllllIllIlIlIIIlIlllI.target).isAir()) {
            return;
        }
        if (llllllllllllllllIllIlIlIIIlIlllI.placeWhen.get() == Place.Always || llllllllllllllllIllIlIlIIIlIlllI.placeWhen.get() == Place.OnClick && (llllllllllllllllIllIlIlIIIlIlllI.mc.options.keyUse.wasPressed() || llllllllllllllllIllIlIlIIIlIlllI.mc.options.keyUse.isPressed())) {
            BlockUtils.place(llllllllllllllllIllIlIlIIIlIlllI.target, Hand.MAIN_HAND, 0, false, 0, true, true, false, false);
        }
    }

    @Override
    public void onActivate() {
        AirPlace llllllllllllllllIllIlIlIIIllIIII;
        llllllllllllllllIllIlIlIIIllIIII.target = llllllllllllllllIllIlIlIIIllIIII.mc.player.getBlockPos().add(4, 2, 0);
    }

    public AirPlace() {
        super(Categories.Player, "air-place", "Places a block where your crosshair is pointing at.");
        AirPlace llllllllllllllllIllIlIlIIIllIIll;
        llllllllllllllllIllIlIlIIIllIIll.sgGeneral = llllllllllllllllIllIlIlIIIllIIll.settings.getDefaultGroup();
        llllllllllllllllIllIlIlIIIllIIll.sgRender = llllllllllllllllIllIlIlIIIllIIll.settings.createGroup("Render");
        llllllllllllllllIllIlIlIIIllIIll.placeWhen = llllllllllllllllIllIlIlIIIllIIll.sgGeneral.add(new EnumSetting.Builder().name("place-when").description("Decides when it should place.").defaultValue(Place.OnClick).build());
        llllllllllllllllIllIlIlIIIllIIll.render = llllllllllllllllIllIlIlIIIllIIll.sgRender.add(new BoolSetting.Builder().name("render").description("Renders a block overlay where the obsidian will be placed.").defaultValue(true).build());
        llllllllllllllllIllIlIlIIIllIIll.shapeMode = llllllllllllllllIllIlIlIIIllIIll.sgRender.add(new EnumSetting.Builder().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
        llllllllllllllllIllIlIlIIIllIIll.sideColor = llllllllllllllllIllIlIlIIIllIIll.sgRender.add(new ColorSetting.Builder().name("side-color").description("The color of the sides of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 10)).build());
        llllllllllllllllIllIlIlIIIllIIll.lineColor = llllllllllllllllIllIlIlIIIllIIll.sgRender.add(new ColorSetting.Builder().name("line-color").description("The color of the lines of the blocks being rendered.").defaultValue(new SettingColor(204, 0, 0, 255)).build());
    }

    public static final class Place
    extends Enum<Place> {
        public static final /* synthetic */ /* enum */ Place OnClick;
        private static final /* synthetic */ Place[] $VALUES;
        public static final /* synthetic */ /* enum */ Place Always;

        private Place() {
            Place lllIIIlIlIllllI;
        }

        public static Place valueOf(String lllIIIlIllIIIll) {
            return Enum.valueOf(Place.class, lllIIIlIllIIIll);
        }

        public static Place[] values() {
            return (Place[])$VALUES.clone();
        }

        private static /* synthetic */ Place[] $values() {
            return new Place[]{OnClick, Always};
        }

        static {
            OnClick = new Place();
            Always = new Place();
            $VALUES = Place.$values();
        }
    }
}

