/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtElement
 */
package minegame159.meteorclient.systems.modules.render.hud;

import java.util.ArrayList;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.Render2DEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.screens.HudElementScreen;
import minegame159.meteorclient.gui.tabs.builtin.HudTab;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.render.hud.HudElementLayer;
import minegame159.meteorclient.systems.modules.render.hud.HudRenderer;
import minegame159.meteorclient.systems.modules.render.hud.modules.ActiveModulesHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.ArmorHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.BiomeHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.BreakingBlockHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.CombatHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.CompassHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.CrystalHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.DurabilityHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.FpsHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.systems.modules.render.hud.modules.InventoryViewerHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.LagNotifierHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.LookingAtHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.ModuleInfoHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.ObsidianHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.PingHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.PlayerModelHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.PositionHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.PotionTimersHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.RotationHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.ServerHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.SpeedHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.TextRadarHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.TimeHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.TotemHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.TpsHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.WatermarkHud;
import minegame159.meteorclient.systems.modules.render.hud.modules.WelcomeHud;
import minegame159.meteorclient.utils.render.AlignmentX;
import minegame159.meteorclient.utils.render.AlignmentY;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtElement;

public class HUD
extends Module {
    public final /* synthetic */ Setting<SettingColor> primaryColor;
    public final /* synthetic */ Setting<SettingColor> secondaryColor;
    private static final /* synthetic */ HudRenderer RENDERER;
    private final /* synthetic */ HudElementLayer topLeft;
    public final /* synthetic */ List<HudElement> elements;
    public final /* synthetic */ Setting<Double> scale;
    public final /* synthetic */ Setting<Integer> snappingRange;
    private final /* synthetic */ SettingGroup sgEditor;
    private final /* synthetic */ HudElementLayer bottomCenter;
    private final /* synthetic */ HudElementLayer topRight;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ HudElementLayer topCenter;
    private final /* synthetic */ HudElementLayer bottomLeft;
    private final /* synthetic */ HudElementLayer bottomRight;

    @Override
    public Module fromTag(NbtCompound llllllllllllllllllIlIlIIlllIIIll) {
        HUD llllllllllllllllllIlIlIIlllIIllI;
        if (llllllllllllllllllIlIlIIlllIIIll.contains("modules")) {
            NbtList llllllllllllllllllIlIlIIlllIIlll = llllllllllllllllllIlIlIIlllIIIll.getList("modules", 10);
            for (NbtElement llllllllllllllllllIlIlIIlllIlIII : llllllllllllllllllIlIlIIlllIIlll) {
                NbtCompound llllllllllllllllllIlIlIIlllIlIlI = (NbtCompound)llllllllllllllllllIlIlIIlllIlIII;
                HudElement llllllllllllllllllIlIlIIlllIlIIl = llllllllllllllllllIlIlIIlllIIllI.getModule(llllllllllllllllllIlIlIIlllIlIlI.getString("name"));
                if (llllllllllllllllllIlIlIIlllIlIIl == null) continue;
                llllllllllllllllllIlIlIIlllIlIIl.fromTag(llllllllllllllllllIlIlIIlllIlIlI);
            }
        }
        return super.fromTag(llllllllllllllllllIlIlIIlllIIIll);
    }

    @Override
    public WWidget getWidget(GuiTheme llllllllllllllllllIlIlIlIIIIIllI) {
        HUD llllllllllllllllllIlIlIlIIIIIlll;
        WHorizontalList llllllllllllllllllIlIlIlIIIIIlIl = llllllllllllllllllIlIlIlIIIIIllI.horizontalList();
        WButton llllllllllllllllllIlIlIlIIIIIlII = llllllllllllllllllIlIlIlIIIIIlIl.add(llllllllllllllllllIlIlIlIIIIIllI.button("Reset")).widget();
        llllllllllllllllllIlIlIlIIIIIlII.action = llllllllllllllllllIlIlIlIIIIIlll::align;
        llllllllllllllllllIlIlIlIIIIIlIl.add(llllllllllllllllllIlIlIlIIIIIllI.label("Resets positions (do this after changing scale)."));
        return llllllllllllllllllIlIlIlIIIIIlIl;
    }

    static {
        RENDERER = new HudRenderer();
    }

    private void align() {
        HUD llllllllllllllllllIlIlIlIIIlIlll;
        RENDERER.begin(llllllllllllllllllIlIlIlIIIlIlll.scale.get(), 0.0, true);
        llllllllllllllllllIlIlIlIIIlIlll.topLeft.align();
        llllllllllllllllllIlIlIlIIIlIlll.topCenter.align();
        llllllllllllllllllIlIlIlIIIlIlll.topRight.align();
        llllllllllllllllllIlIlIlIIIlIlll.bottomLeft.align();
        llllllllllllllllllIlIlIlIIIlIlll.bottomCenter.align();
        llllllllllllllllllIlIlIlIIIlIlll.bottomRight.align();
        RENDERER.end();
    }

    @Override
    public NbtCompound toTag() {
        HUD llllllllllllllllllIlIlIIlllllIIl;
        NbtCompound llllllllllllllllllIlIlIIlllllIII = super.toTag();
        NbtList llllllllllllllllllIlIlIIllllIlll = new NbtList();
        for (HudElement llllllllllllllllllIlIlIIlllllIlI : llllllllllllllllllIlIlIIlllllIIl.elements) {
            llllllllllllllllllIlIlIIllllIlll.add((Object)llllllllllllllllllIlIlIIlllllIlI.toTag());
        }
        llllllllllllllllllIlIlIIlllllIII.put("modules", (NbtElement)llllllllllllllllllIlIlIIllllIlll);
        return llllllllllllllllllIlIlIIlllllIII;
    }

    public HUD() {
        super(Categories.Render, "HUD", "In game overlay.");
        HUD llllllllllllllllllIlIlIlIIIllIlI;
        llllllllllllllllllIlIlIlIIIllIlI.sgGeneral = llllllllllllllllllIlIlIlIIIllIlI.settings.getDefaultGroup();
        llllllllllllllllllIlIlIlIIIllIlI.sgEditor = llllllllllllllllllIlIlIlIIIllIlI.settings.createGroup("Editor");
        llllllllllllllllllIlIlIlIIIllIlI.scale = llllllllllllllllllIlIlIlIIIllIlI.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of the HUD.").defaultValue(1.0).min(1.0).max(3.0).sliderMin(1.0).sliderMax(3.0).build());
        llllllllllllllllllIlIlIlIIIllIlI.primaryColor = llllllllllllllllllIlIlIlIIIllIlI.sgGeneral.add(new ColorSetting.Builder().name("primary-color").description("Primary color of text.").defaultValue(new SettingColor(255, 255, 255)).build());
        llllllllllllllllllIlIlIlIIIllIlI.secondaryColor = llllllllllllllllllIlIlIlIIIllIlI.sgGeneral.add(new ColorSetting.Builder().name("secondary-color").description("Secondary color of text.").defaultValue(new SettingColor(175, 175, 175)).build());
        llllllllllllllllllIlIlIlIIIllIlI.snappingRange = llllllllllllllllllIlIlIlIIIllIlI.sgEditor.add(new IntSetting.Builder().name("snapping-range").description("Snapping range in editor.").defaultValue(6).build());
        llllllllllllllllllIlIlIlIIIllIlI.elements = new ArrayList<HudElement>();
        llllllllllllllllllIlIlIlIIIllIlI.topLeft = new HudElementLayer(RENDERER, llllllllllllllllllIlIlIlIIIllIlI.elements, AlignmentX.Left, AlignmentY.Top, 2, 2);
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new WatermarkHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new FpsHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new PingHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new TpsHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new SpeedHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new BiomeHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new TimeHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new ServerHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new DurabilityHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new BreakingBlockHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new LookingAtHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new ModuleInfoHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topLeft.add(new TextRadarHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topCenter = new HudElementLayer(RENDERER, llllllllllllllllllIlIlIlIIIllIlI.elements, AlignmentX.Center, AlignmentY.Top, 0, 2);
        llllllllllllllllllIlIlIlIIIllIlI.topCenter.add(new InventoryViewerHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topCenter.add(new WelcomeHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topCenter.add(new LagNotifierHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.topRight = new HudElementLayer(RENDERER, llllllllllllllllllIlIlIlIIIllIlI.elements, AlignmentX.Right, AlignmentY.Top, 2, 2);
        llllllllllllllllllIlIlIlIIIllIlI.topRight.add(new ActiveModulesHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.bottomLeft = new HudElementLayer(RENDERER, llllllllllllllllllIlIlIlIIIllIlI.elements, AlignmentX.Left, AlignmentY.Bottom, 2, 2);
        llllllllllllllllllIlIlIlIIIllIlI.bottomLeft.add(new PlayerModelHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.bottomCenter = new HudElementLayer(RENDERER, llllllllllllllllllIlIlIlIIIllIlI.elements, AlignmentX.Center, AlignmentY.Bottom, 48, 64);
        llllllllllllllllllIlIlIlIIIllIlI.bottomCenter.add(new ArmorHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.bottomCenter.add(new CompassHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.bottomCenter.add(new TotemHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.bottomCenter.add(new CrystalHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.bottomCenter.add(new ObsidianHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.bottomRight = new HudElementLayer(RENDERER, llllllllllllllllllIlIlIlIIIllIlI.elements, AlignmentX.Right, AlignmentY.Bottom, 2, 2);
        llllllllllllllllllIlIlIlIIIllIlI.bottomRight.add(new PositionHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.bottomRight.add(new RotationHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.bottomRight.add(new PotionTimersHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.bottomRight.add(new CombatHud(llllllllllllllllllIlIlIlIIIllIlI));
        llllllllllllllllllIlIlIlIIIllIlI.align();
    }

    @EventHandler
    public void onRender(Render2DEvent llllllllllllllllllIlIlIlIIIlIIII) {
        HUD llllllllllllllllllIlIlIlIIIlIIIl;
        if (llllllllllllllllllIlIlIlIIIlIIIl.mc.options.debugEnabled) {
            return;
        }
        RENDERER.begin(llllllllllllllllllIlIlIlIIIlIIIl.scale.get(), llllllllllllllllllIlIlIlIIIlIIII.tickDelta, false);
        for (HudElement llllllllllllllllllIlIlIlIIIlIIlI : llllllllllllllllllIlIlIlIIIlIIIl.elements) {
            if (!llllllllllllllllllIlIlIlIIIlIIlI.active && !HudTab.INSTANCE.isScreen(llllllllllllllllllIlIlIlIIIlIIIl.mc.currentScreen) && !(llllllllllllllllllIlIlIlIIIlIIIl.mc.currentScreen instanceof HudElementScreen)) continue;
            llllllllllllllllllIlIlIlIIIlIIlI.update(RENDERER);
            llllllllllllllllllIlIlIlIIIlIIlI.render(RENDERER);
        }
        RENDERER.end();
    }

    private HudElement getModule(String llllllllllllllllllIlIlIIllIlIlIl) {
        HUD llllllllllllllllllIlIlIIllIlIllI;
        for (HudElement llllllllllllllllllIlIlIIllIllIIl : llllllllllllllllllIlIlIIllIlIllI.elements) {
            if (!llllllllllllllllllIlIlIIllIllIIl.name.equals(llllllllllllllllllIlIlIIllIlIlIl)) continue;
            return llllllllllllllllllIlIlIIllIllIIl;
        }
        return null;
    }
}

