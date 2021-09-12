/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.player;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalXZ;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.packets.PacketEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.widgets.WLabel;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.waypoints.Waypoint;
import minegame159.meteorclient.systems.waypoints.Waypoints;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2585;
import net.minecraft.class_2749;

public class DeathPosition
extends Module {
    private class_243 dmgPos;
    private String labelText;
    private final SimpleDateFormat dateFormat;
    private final Setting<Boolean> showTimestamp;
    private final SettingGroup sgGeneral;
    private final Map<String, Double> deathPos;
    private Waypoint waypoint;
    private final Setting<Boolean> createWaypoint;

    private void lambda$getWidget$0(WLabel wLabel) {
        Waypoints.get().remove(this.waypoint);
        this.labelText = "No latest death";
        wLabel.set(this.labelText);
    }

    private void onDeath() {
        this.dmgPos = this.mc.field_1724.method_19538();
        this.deathPos.put("x", this.dmgPos.field_1352);
        this.deathPos.put("z", this.dmgPos.field_1350);
        this.labelText = String.format("Latest death: %.1f, %.1f, %.1f", this.dmgPos.field_1352, this.dmgPos.field_1351, this.dmgPos.field_1350);
        String string = this.dateFormat.format(new Date());
        class_2585 class_25852 = new class_2585("Died at ");
        class_25852.method_10852((class_2561)ChatUtils.formatCoords(this.dmgPos));
        class_25852.method_27693(this.showTimestamp.get() != false ? String.format(" on %s.", string) : ".");
        ChatUtils.moduleInfo(this, (class_2561)class_25852);
        if (this.createWaypoint.get().booleanValue()) {
            this.waypoint = new Waypoint();
            this.waypoint.name = String.valueOf(new StringBuilder().append("Death ").append(string));
            this.waypoint.x = (int)this.dmgPos.field_1352;
            this.waypoint.y = (int)this.dmgPos.field_1351 + 2;
            this.waypoint.z = (int)this.dmgPos.field_1350;
            this.waypoint.maxVisibleDistance = Integer.MAX_VALUE;
            this.waypoint.actualDimension = Utils.getDimension();
            switch (1.$SwitchMap$minegame159$meteorclient$utils$world$Dimension[Utils.getDimension().ordinal()]) {
                case 1: {
                    this.waypoint.overworld = true;
                    break;
                }
                case 2: {
                    this.waypoint.nether = true;
                    break;
                }
                case 3: {
                    this.waypoint.end = true;
                }
            }
            Waypoints.get().add(this.waypoint);
        }
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive receive) {
        class_2749 class_27492;
        if (receive.packet instanceof class_2749 && (class_27492 = (class_2749)receive.packet).method_11833() <= 0.0f) {
            this.onDeath();
        }
    }

    public DeathPosition() {
        super(Categories.Player, "death-position", "Sends you the coordinates to your latest death.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.createWaypoint = this.sgGeneral.add(new BoolSetting.Builder().name("create-waypoint").description("Creates a waypoint when you die.").defaultValue(true).build());
        this.showTimestamp = this.sgGeneral.add(new BoolSetting.Builder().name("show-timestamp").description("Show timestamp in chat.").defaultValue(true).build());
        this.dateFormat = new SimpleDateFormat("HH:mm:ss");
        this.deathPos = new HashMap<String, Double>();
        this.labelText = "No latest death";
    }

    @Override
    public WWidget getWidget(GuiTheme guiTheme) {
        WHorizontalList wHorizontalList = guiTheme.horizontalList();
        WLabel wLabel = wHorizontalList.add(guiTheme.label(this.labelText)).expandCellX().widget();
        WButton wButton = wHorizontalList.add(guiTheme.button("Path")).widget();
        wButton.action = this::path;
        WButton wButton2 = wHorizontalList.add(guiTheme.button("Clear")).widget();
        wButton2.action = () -> this.lambda$getWidget$0(wLabel);
        return wHorizontalList;
    }

    private void path() {
        if (this.deathPos.isEmpty() && this.mc.field_1724 != null) {
            ChatUtils.moduleWarning(this, "No latest death found.", new Object[0]);
        } else if (this.mc.field_1687 != null) {
            double d = this.dmgPos.field_1352;
            double d2 = this.dmgPos.field_1350;
            if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
            }
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)new GoalXZ((int)d, (int)d2));
        }
    }
}

