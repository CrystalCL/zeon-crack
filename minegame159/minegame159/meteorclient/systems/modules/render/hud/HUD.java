/*
 * Decompiled with CFR 0.151.
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class HUD
extends Module {
    private final HudElementLayer bottomCenter;
    private final HudElementLayer topCenter;
    public final Setting<Double> scale;
    private final HudElementLayer topLeft;
    public final List<HudElement> elements;
    private final HudElementLayer topRight;
    private final HudElementLayer bottomRight;
    private static final HudRenderer RENDERER = new HudRenderer();
    private final SettingGroup sgEditor;
    private final HudElementLayer bottomLeft;
    public final Setting<Integer> snappingRange;
    public final Setting<SettingColor> secondaryColor;
    private final SettingGroup sgGeneral;
    public final Setting<SettingColor> primaryColor;

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = super.toTag();
        NbtList NbtList2 = new NbtList();
        for (HudElement hudElement : this.elements) {
            NbtList2.add((Object)hudElement.toTag());
        }
        NbtCompound2.put("modules", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    private HudElement getModule(String string) {
        for (HudElement hudElement : this.elements) {
            if (!hudElement.name.equals(string)) continue;
            return hudElement;
        }
        return null;
    }

    @Override
    public Module fromTag(NbtCompound NbtCompound2) {
        if (NbtCompound2.contains("modules")) {
            NbtList NbtList2 = NbtCompound2.getList("modules", 10);
            for (NbtElement NbtElement2 : NbtList2) {
                NbtCompound NbtCompound3 = (NbtCompound)NbtElement2;
                HudElement hudElement = this.getModule(NbtCompound3.getString("name"));
                if (hudElement == null) continue;
                hudElement.fromTag(NbtCompound3);
            }
        }
        return super.fromTag(NbtCompound2);
    }

    @Override
    public WWidget getWidget(GuiTheme guiTheme) {
        WHorizontalList wHorizontalList = guiTheme.horizontalList();
        WButton wButton = wHorizontalList.add(guiTheme.button("Reset")).widget();
        wButton.action = this::align;
        wHorizontalList.add(guiTheme.label("Resets positions (do this after changing scale)."));
        return wHorizontalList;
    }

    public HUD() {
        super(Categories.Render, "HUD", "In game overlay.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgEditor = this.settings.createGroup("Editor");
        this.scale = this.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("Scale of the HUD.").defaultValue(1.0).min(1.0).max(3.0).sliderMin(1.0).sliderMax(3.0).build());
        this.primaryColor = this.sgGeneral.add(new ColorSetting.Builder().name("primary-color").description("Primary color of text.").defaultValue(new SettingColor(255, 255, 255)).build());
        this.secondaryColor = this.sgGeneral.add(new ColorSetting.Builder().name("secondary-color").description("Secondary color of text.").defaultValue(new SettingColor(175, 175, 175)).build());
        this.snappingRange = this.sgEditor.add(new IntSetting.Builder().name("snapping-range").description("Snapping range in editor.").defaultValue(6).build());
        this.elements = new ArrayList<HudElement>();
        this.topLeft = new HudElementLayer(RENDERER, this.elements, AlignmentX.Left, AlignmentY.Top, 2, 2);
        this.topLeft.add(new WatermarkHud(this));
        this.topLeft.add(new FpsHud(this));
        this.topLeft.add(new PingHud(this));
        this.topLeft.add(new TpsHud(this));
        this.topLeft.add(new SpeedHud(this));
        this.topLeft.add(new BiomeHud(this));
        this.topLeft.add(new TimeHud(this));
        this.topLeft.add(new ServerHud(this));
        this.topLeft.add(new DurabilityHud(this));
        this.topLeft.add(new BreakingBlockHud(this));
        this.topLeft.add(new LookingAtHud(this));
        this.topLeft.add(new ModuleInfoHud(this));
        this.topLeft.add(new TextRadarHud(this));
        this.topCenter = new HudElementLayer(RENDERER, this.elements, AlignmentX.Center, AlignmentY.Top, 0, 2);
        this.topCenter.add(new InventoryViewerHud(this));
        this.topCenter.add(new WelcomeHud(this));
        this.topCenter.add(new LagNotifierHud(this));
        this.topRight = new HudElementLayer(RENDERER, this.elements, AlignmentX.Right, AlignmentY.Top, 2, 2);
        this.topRight.add(new ActiveModulesHud(this));
        this.bottomLeft = new HudElementLayer(RENDERER, this.elements, AlignmentX.Left, AlignmentY.Bottom, 2, 2);
        this.bottomLeft.add(new PlayerModelHud(this));
        this.bottomCenter = new HudElementLayer(RENDERER, this.elements, AlignmentX.Center, AlignmentY.Bottom, 48, 64);
        this.bottomCenter.add(new ArmorHud(this));
        this.bottomCenter.add(new CompassHud(this));
        this.bottomCenter.add(new TotemHud(this));
        this.bottomCenter.add(new CrystalHud(this));
        this.bottomCenter.add(new ObsidianHud(this));
        this.bottomRight = new HudElementLayer(RENDERER, this.elements, AlignmentX.Right, AlignmentY.Bottom, 2, 2);
        this.bottomRight.add(new PositionHud(this));
        this.bottomRight.add(new RotationHud(this));
        this.bottomRight.add(new PotionTimersHud(this));
        this.bottomRight.add(new CombatHud(this));
        this.align();
    }

    @EventHandler
    public void onRender(Render2DEvent render2DEvent) {
        if (this.mc.options.debugEnabled) {
            return;
        }
        RENDERER.begin(this.scale.get(), render2DEvent.tickDelta, false);
        for (HudElement hudElement : this.elements) {
            if (!hudElement.active && !HudTab.INSTANCE.isScreen(this.mc.currentScreen) && !(this.mc.currentScreen instanceof HudElementScreen)) continue;
            hudElement.update(RENDERER);
            hudElement.render(RENDERER);
        }
        RENDERER.end();
    }

    private void align() {
        RENDERER.begin(this.scale.get(), 0.0, true);
        this.topLeft.align();
        this.topCenter.align();
        this.topRight.align();
        this.bottomLeft.align();
        this.bottomCenter.align();
        this.bottomRight.align();
        RENDERER.end();
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }
}

