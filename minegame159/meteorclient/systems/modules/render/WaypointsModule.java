/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  baritone.api.IBaritone
 *  baritone.api.pathing.goals.Goal
 *  baritone.api.pathing.goals.GoalGetToBlock
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.screen.Screen
 */
package minegame159.meteorclient.systems.modules.render;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalGetToBlock;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.renderer.packer.GuiTexture;
import minegame159.meteorclient.gui.screens.settings.ColorSettingScreen;
import minegame159.meteorclient.gui.widgets.WLabel;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WDoubleEdit;
import minegame159.meteorclient.gui.widgets.input.WDropdown;
import minegame159.meteorclient.gui.widgets.input.WIntEdit;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.gui.widgets.pressable.WCheckbox;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.waypoints.Waypoint;
import minegame159.meteorclient.systems.waypoints.Waypoints;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.world.Dimension;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class WaypointsModule
extends Module {
    private static final /* synthetic */ Color GRAY;

    public WaypointsModule() {
        super(Categories.Render, "waypoints", "Allows you to create waypoints.");
        WaypointsModule llllllllllllllllllllIIIIIIIIIlIl;
    }

    @Override
    public WWidget getWidget(GuiTheme lllllllllllllllllllIllllllllllII) {
        WaypointsModule llllllllllllllllllllIIIIIIIIIIII;
        if (!Utils.canUpdate()) {
            return lllllllllllllllllllIllllllllllII.label("You need to be in a world.");
        }
        WTable lllllllllllllllllllIlllllllllllI = lllllllllllllllllllIllllllllllII.table();
        llllllllllllllllllllIIIIIIIIIIII.fillTable(lllllllllllllllllllIllllllllllII, lllllllllllllllllllIlllllllllllI);
        return lllllllllllllllllllIlllllllllllI;
    }

    static {
        GRAY = new Color(200, 200, 200);
    }

    private void fillTable(GuiTheme lllllllllllllllllllIlllllllIIlII, WTable lllllllllllllllllllIllllllIlllll) {
        WaypointsModule lllllllllllllllllllIlllllllIIlIl;
        WButton lllllllllllllllllllIlllllllIIIlI = lllllllllllllllllllIllllllIlllll.add(lllllllllllllllllllIlllllllIIlII.button("Create")).expandX().widget();
        lllllllllllllllllllIlllllllIIIlI.action = () -> {
            WaypointsModule lllllllllllllllllllIlllllIlIIlII;
            lllllllllllllllllllIlllllIlIIlII.mc.openScreen((Screen)new EditWaypointScreen(lllllllllllllllllllIlllllllIIlII, null, () -> {
                WaypointsModule lllllllllllllllllllIlllllIIllIll;
                lllllllllllllllllllIllllllIlllll.clear();
                lllllllllllllllllllIlllllIIllIll.fillTable(lllllllllllllllllllIlllllllIIlII, lllllllllllllllllllIllllllIlllll);
            }));
        };
        lllllllllllllllllllIllllllIlllll.row();
        for (Waypoint lllllllllllllllllllIlllllllIIllI : Waypoints.get()) {
            lllllllllllllllllllIllllllIlllll.add(new WIcon(lllllllllllllllllllIlllllllIIllI));
            WLabel lllllllllllllllllllIlllllllIllII = lllllllllllllllllllIllllllIlllll.add(lllllllllllllllllllIlllllllIIlII.label(lllllllllllllllllllIlllllllIIllI.name)).expandCellX().widget();
            boolean lllllllllllllllllllIlllllllIlIll = false;
            Dimension lllllllllllllllllllIlllllllIlIlI = Utils.getDimension();
            if (lllllllllllllllllllIlllllllIIllI.overworld && lllllllllllllllllllIlllllllIlIlI == Dimension.Overworld) {
                lllllllllllllllllllIlllllllIlIll = true;
            } else if (lllllllllllllllllllIlllllllIIllI.nether && lllllllllllllllllllIlllllllIlIlI == Dimension.Nether) {
                lllllllllllllllllllIlllllllIlIll = true;
            } else if (lllllllllllllllllllIlllllllIIllI.end && lllllllllllllllllllIlllllllIlIlI == Dimension.End) {
                lllllllllllllllllllIlllllllIlIll = true;
            }
            if (!lllllllllllllllllllIlllllllIlIll) {
                lllllllllllllllllllIlllllllIllII.color = GRAY;
            }
            WCheckbox lllllllllllllllllllIlllllllIlIIl = lllllllllllllllllllIllllllIlllll.add(lllllllllllllllllllIlllllllIIlII.checkbox(lllllllllllllllllllIlllllllIIllI.visible)).widget();
            lllllllllllllllllllIlllllllIlIIl.action = () -> {
                lllllllllllllllllllIlllllIlIlllI.visible = lllllllllllllllllllIlllllIlIllIl.checked;
                Waypoints.get().save();
            };
            WButton lllllllllllllllllllIlllllllIlIII = lllllllllllllllllllIllllllIlllll.add(lllllllllllllllllllIlllllllIIlII.button(GuiRenderer.EDIT)).widget();
            lllllllllllllllllllIlllllllIlIII.action = () -> {
                WaypointsModule lllllllllllllllllllIlllllIllIIll;
                lllllllllllllllllllIlllllIllIIll.mc.openScreen((Screen)new EditWaypointScreen(lllllllllllllllllllIlllllllIIlII, lllllllllllllllllllIlllllllIIllI, null));
            };
            WMinus lllllllllllllllllllIlllllllIIlll = lllllllllllllllllllIllllllIlllll.add(lllllllllllllllllllIlllllllIIlII.minus()).widget();
            lllllllllllllllllllIlllllllIIlll.action = () -> {
                WaypointsModule lllllllllllllllllllIllllllIIIIIl;
                Waypoints.get().remove(lllllllllllllllllllIlllllllIIllI);
                lllllllllllllllllllIllllllIlllll.clear();
                lllllllllllllllllllIllllllIIIIIl.fillTable(lllllllllllllllllllIlllllllIIlII, lllllllllllllllllllIllllllIlllll);
            };
            if (lllllllllllllllllllIlllllllIIllI.actualDimension == lllllllllllllllllllIlllllllIlIlI) {
                WButton lllllllllllllllllllIlllllllIllIl = lllllllllllllllllllIllllllIlllll.add(lllllllllllllllllllIlllllllIIlII.button("Goto")).widget();
                lllllllllllllllllllIlllllllIllIl.action = () -> {
                    WaypointsModule lllllllllllllllllllIllllllIIlIlI;
                    if (lllllllllllllllllllIllllllIIlIlI.mc.player == null || lllllllllllllllllllIllllllIIlIlI.mc.world == null) {
                        return;
                    }
                    IBaritone lllllllllllllllllllIllllllIIllIl = BaritoneAPI.getProvider().getPrimaryBaritone();
                    if (lllllllllllllllllllIllllllIIllIl.getPathingBehavior().isPathing()) {
                        lllllllllllllllllllIllllllIIllIl.getPathingBehavior().cancelEverything();
                    }
                    Vec3d lllllllllllllllllllIllllllIIllII = Waypoints.get().getCoords(lllllllllllllllllllIlllllllIIllI);
                    BlockPos lllllllllllllllllllIllllllIIlIll = new BlockPos(lllllllllllllllllllIllllllIIllII.x, lllllllllllllllllllIllllllIIllII.y, lllllllllllllllllllIllllllIIllII.z);
                    lllllllllllllllllllIllllllIIllIl.getCustomGoalProcess().setGoalAndPath((Goal)new GoalGetToBlock(lllllllllllllllllllIllllllIIlIll));
                };
            }
            lllllllllllllllllllIllllllIlllll.row();
        }
    }

    private static class EditWaypointScreen
    extends WindowScreen {
        private final /* synthetic */ Waypoint waypoint;
        private final /* synthetic */ boolean newWaypoint;
        private final /* synthetic */ Runnable action;

        @Override
        protected void onClosed() {
            EditWaypointScreen lllllllllllllllllIllIIIIIIllIIll;
            if (lllllllllllllllllIllIIIIIIllIIll.action != null) {
                lllllllllllllllllIllIIIIIIllIIll.action.run();
            }
        }

        public EditWaypointScreen(GuiTheme lllllllllllllllllIllIIIIIllIIIlI, Waypoint lllllllllllllllllIllIIIIIllIIlIl, Runnable lllllllllllllllllIllIIIIIllIIIII) {
            super(lllllllllllllllllIllIIIIIllIIIlI, lllllllllllllllllIllIIIIIllIIlIl == null ? "New Waypoint" : "Edit Waypoint");
            EditWaypointScreen lllllllllllllllllIllIIIIIllIIlll;
            lllllllllllllllllIllIIIIIllIIlll.newWaypoint = lllllllllllllllllIllIIIIIllIIlIl == null;
            lllllllllllllllllIllIIIIIllIIlll.waypoint = lllllllllllllllllIllIIIIIllIIlll.newWaypoint ? new Waypoint() : lllllllllllllllllIllIIIIIllIIlIl;
            lllllllllllllllllIllIIIIIllIIlll.action = lllllllllllllllllIllIIIIIllIIIII;
            if (lllllllllllllllllIllIIIIIllIIlll.newWaypoint) {
                MinecraftClient lllllllllllllllllIllIIIIIllIlIII = MinecraftClient.getInstance();
                lllllllllllllllllIllIIIIIllIIlll.waypoint.x = (int)lllllllllllllllllIllIIIIIllIlIII.player.getX();
                lllllllllllllllllIllIIIIIllIIlll.waypoint.y = (int)lllllllllllllllllIllIIIIIllIlIII.player.getY() + 2;
                lllllllllllllllllIllIIIIIllIIlll.waypoint.z = (int)lllllllllllllllllIllIIIIIllIlIII.player.getZ();
                lllllllllllllllllIllIIIIIllIIlll.waypoint.actualDimension = Utils.getDimension();
                switch (Utils.getDimension()) {
                    case Overworld: {
                        lllllllllllllllllIllIIIIIllIIlll.waypoint.overworld = true;
                        break;
                    }
                    case Nether: {
                        lllllllllllllllllIllIIIIIllIIlll.waypoint.nether = true;
                        break;
                    }
                    case End: {
                        lllllllllllllllllIllIIIIIllIIlll.waypoint.end = true;
                    }
                }
            }
            lllllllllllllllllIllIIIIIllIIlll.initWidgets();
        }

        private void initWidgets() {
            EditWaypointScreen lllllllllllllllllIllIIIIIlIIIIlI;
            WTable lllllllllllllllllIllIIIIIlIIllll = lllllllllllllllllIllIIIIIlIIIIlI.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.table()).expandX().widget();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Name:"));
            WTextBox lllllllllllllllllIllIIIIIlIIlllI = lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.textBox(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.name)).minWidth(400.0).expandX().widget();
            lllllllllllllllllIllIIIIIlIIlllI.action = () -> {
                lllllllllllllllllIlIlllllllIIlIl.waypoint.name = lllllllllllllllllIllIIIIIlIIlllI.get().trim();
            };
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Icon:"));
            WHorizontalList lllllllllllllllllIllIIIIIlIIllIl = lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.horizontalList()).widget();
            lllllllllllllllllIllIIIIIlIIllIl.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.button((String)"<")).widget().action = lllllllllllllllllIllIIIIIlIIIIlI.waypoint::prevIcon;
            lllllllllllllllllIllIIIIIlIIllIl.add(new WIcon(lllllllllllllllllIllIIIIIlIIIIlI.waypoint));
            lllllllllllllllllIllIIIIIlIIllIl.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.button((String)">")).widget().action = lllllllllllllllllIllIIIIIlIIIIlI.waypoint::nextIcon;
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Color:"));
            lllllllllllllllllIllIIIIIlIIllIl = lllllllllllllllllIllIIIIIlIIIIlI.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.horizontalList()).widget();
            lllllllllllllllllIllIIIIIlIIllIl.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.quad(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.color));
            lllllllllllllllllIllIIIIIlIIllIl.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.button((GuiTexture)GuiRenderer.EDIT)).widget().action = () -> {
                EditWaypointScreen lllllllllllllllllIlIllllllllIIIl;
                MinecraftClient.getInstance().openScreen((Screen)new ColorSettingScreen(lllllllllllllllllIlIllllllllIIIl.theme, new ColorSetting("", "", lllllllllllllllllIlIllllllllIIIl.waypoint.color, lllllllllllllllllIlIlllllllIllII -> {
                    EditWaypointScreen lllllllllllllllllIlIlllllllIllIl;
                    lllllllllllllllllIlIlllllllIllIl.waypoint.color.set((Color)lllllllllllllllllIlIlllllllIllII);
                }, null)));
            };
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.horizontalSeparator()).expandX();
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("X:"));
            WIntEdit lllllllllllllllllIllIIIIIlIIllII = lllllllllllllllllIllIIIIIlIIIIlI.theme.intEdit(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.x, 0, 0);
            lllllllllllllllllIllIIIIIlIIllII.hasSlider = false;
            lllllllllllllllllIllIIIIIlIIllII.action = () -> {
                lllllllllllllllllIlIllllllllIlII.waypoint.x = lllllllllllllllllIllIIIIIlIIllII.get();
            };
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIllII).expandX();
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Y:"));
            WIntEdit lllllllllllllllllIllIIIIIlIIlIll = lllllllllllllllllIllIIIIIlIIIIlI.theme.intEdit(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.y, 0, 0);
            lllllllllllllllllIllIIIIIlIIlIll.hasSlider = false;
            lllllllllllllllllIllIIIIIlIIlIll.actionOnRelease = () -> {
                if (lllllllllllllllllIllIIIIIlIIlIll.get() < 0) {
                    lllllllllllllllllIllIIIIIlIIlIll.set(0);
                } else if (lllllllllllllllllIllIIIIIlIIlIll.get() > 255) {
                    lllllllllllllllllIllIIIIIlIIlIll.set(255);
                }
                lllllllllllllllllIlIlllllllllIlI.waypoint.y = lllllllllllllllllIllIIIIIlIIlIll.get();
            };
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIlIll).expandX();
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Z:"));
            WIntEdit lllllllllllllllllIllIIIIIlIIlIlI = lllllllllllllllllIllIIIIIlIIIIlI.theme.intEdit(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.z, 0, 0);
            lllllllllllllllllIllIIIIIlIIlIlI.action = () -> {
                lllllllllllllllllIllIIIIIIIIIIlI.waypoint.z = lllllllllllllllllIllIIIIIlIIlIlI.get();
            };
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIlIlI).expandX();
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.horizontalSeparator()).expandX();
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Visible:"));
            WCheckbox lllllllllllllllllIllIIIIIlIIlIIl = lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.checkbox(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.visible)).widget();
            lllllllllllllllllIllIIIIIlIIlIIl.action = () -> {
                lllllllllllllllllIllIIIIIIIIlIII.waypoint.visible = lllllllllllllllllIllIIIIIIIIIlIl.checked;
            };
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Max Visible Distance"));
            WIntEdit lllllllllllllllllIllIIIIIlIIlIII = lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.intEdit(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.maxVisibleDistance, 0, 10000)).expandX().widget();
            lllllllllllllllllIllIIIIIlIIlIII.action = () -> {
                lllllllllllllllllIllIIIIIIIIlllI.waypoint.maxVisibleDistance = lllllllllllllllllIllIIIIIlIIlIII.get();
            };
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Scale:"));
            WDoubleEdit lllllllllllllllllIllIIIIIlIIIlll = lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.doubleEdit(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.scale, 0.0, 4.0)).expandX().widget();
            lllllllllllllllllIllIIIIIlIIIlll.action = () -> {
                lllllllllllllllllIllIIIIIIIlIlII.waypoint.scale = lllllllllllllllllIllIIIIIlIIIlll.get();
            };
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.horizontalSeparator()).expandX();
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Actual Dimension:"));
            WDropdown<Dimension> lllllllllllllllllIllIIIIIlIIIllI = lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.dropdown(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.actualDimension)).widget();
            lllllllllllllllllIllIIIIIlIIIllI.action = () -> {
                lllllllllllllllllIllIIIIIIIllIII.waypoint.actualDimension = (Dimension)((Object)((Object)lllllllllllllllllIllIIIIIlIIIllI.get()));
            };
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Visible in Overworld:"));
            WCheckbox lllllllllllllllllIllIIIIIlIIIlIl = lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.checkbox(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.overworld)).widget();
            lllllllllllllllllIllIIIIIlIIIlIl.action = () -> {
                lllllllllllllllllIllIIIIIIlIIIII.waypoint.overworld = lllllllllllllllllIllIIIIIIIlllIl.checked;
            };
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Visible in Nether:"));
            WCheckbox lllllllllllllllllIllIIIIIlIIIlII = lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.checkbox(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.nether)).widget();
            lllllllllllllllllIllIIIIIlIIIlII.action = () -> {
                lllllllllllllllllIllIIIIIIlIIllI.waypoint.nether = lllllllllllllllllIllIIIIIIlIIlIl.checked;
            };
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.label("Visible in End:"));
            WCheckbox lllllllllllllllllIllIIIIIlIIIIll = lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.checkbox(lllllllllllllllllIllIIIIIlIIIIlI.waypoint.end)).widget();
            lllllllllllllllllIllIIIIIlIIIIll.action = () -> {
                lllllllllllllllllIllIIIIIIlIlIlI.waypoint.end = lllllllllllllllllIllIIIIIIlIlIIl.checked;
            };
            lllllllllllllllllIllIIIIIlIIllll.row();
            lllllllllllllllllIllIIIIIlIIllll.add(lllllllllllllllllIllIIIIIlIIIIlI.theme.button((String)"Save")).expandX().widget().action = () -> {
                EditWaypointScreen lllllllllllllllllIllIIIIIIllIIII;
                if (lllllllllllllllllIllIIIIIIllIIII.newWaypoint) {
                    Waypoints.get().add(lllllllllllllllllIllIIIIIIllIIII.waypoint);
                } else {
                    Waypoints.get().save();
                }
                lllllllllllllllllIllIIIIIIllIIII.onClose();
            };
        }
    }

    private static class WIcon
    extends WWidget {
        private final /* synthetic */ Waypoint waypoint;

        @Override
        protected void onRender(GuiRenderer llIIllIIlllIIII, double llIIllIIlllIlII, double llIIllIIlllIIll, double llIIllIIlllIIlI) {
            WIcon llIIllIIlllIIIl;
            llIIllIIlllIIII.post(() -> {
                WIcon llIIllIIllIllIl;
                llIIllIIllIllIl.waypoint.renderIcon(llIIllIIllIllIl.x, llIIllIIllIllIl.y, 0.0, 1.0, llIIllIIllIllIl.width);
            });
        }

        @Override
        protected void onCalculateSize() {
            WIcon llIIllIIllllIlI;
            double llIIllIIllllIll;
            llIIllIIllllIlI.width = llIIllIIllllIll = llIIllIIllllIlI.theme.scale(32.0);
            llIIllIIllllIlI.height = llIIllIIllllIll;
        }

        public WIcon(Waypoint llIIllIlIIIIIIl) {
            WIcon llIIllIlIIIIIlI;
            llIIllIlIIIIIlI.waypoint = llIIllIlIIIIIIl;
        }
    }
}

