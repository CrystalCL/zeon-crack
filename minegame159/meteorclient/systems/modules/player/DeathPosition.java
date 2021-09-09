/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  baritone.api.pathing.goals.Goal
 *  baritone.api.pathing.goals.GoalXZ
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;

public class DeathPosition
extends Module {
    private final /* synthetic */ Setting<Boolean> createWaypoint;
    private /* synthetic */ Waypoint waypoint;
    private final /* synthetic */ SettingGroup sgGeneral;
    private /* synthetic */ Vec3d dmgPos;
    private final /* synthetic */ Map<String, Double> deathPos;
    private final /* synthetic */ SimpleDateFormat dateFormat;
    private final /* synthetic */ Setting<Boolean> showTimestamp;
    private /* synthetic */ String labelText;

    private void path() {
        DeathPosition lIIlIlllIIlIII;
        if (lIIlIlllIIlIII.deathPos.isEmpty() && lIIlIlllIIlIII.mc.player != null) {
            ChatUtils.moduleWarning(lIIlIlllIIlIII, "No latest death found.", new Object[0]);
        } else if (lIIlIlllIIlIII.mc.world != null) {
            double lIIlIlllIIlIlI = lIIlIlllIIlIII.dmgPos.x;
            double lIIlIlllIIlIIl = lIIlIlllIIlIII.dmgPos.z;
            if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
            }
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)new GoalXZ((int)lIIlIlllIIlIlI, (int)lIIlIlllIIlIIl));
        }
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive lIIlIllllIlIlI) {
        HealthUpdateS2CPacket lIIlIllllIlllI;
        if (lIIlIllllIlIlI.packet instanceof HealthUpdateS2CPacket && (lIIlIllllIlllI = (HealthUpdateS2CPacket)lIIlIllllIlIlI.packet).getHealth() <= 0.0f) {
            DeathPosition lIIlIllllIlIll;
            lIIlIllllIlIll.onDeath();
        }
    }

    public DeathPosition() {
        super(Categories.Player, "death-position", "Sends you the coordinates to your latest death.");
        DeathPosition lIIlIlllllIIll;
        lIIlIlllllIIll.sgGeneral = lIIlIlllllIIll.settings.getDefaultGroup();
        lIIlIlllllIIll.createWaypoint = lIIlIlllllIIll.sgGeneral.add(new BoolSetting.Builder().name("create-waypoint").description("Creates a waypoint when you die.").defaultValue(true).build());
        lIIlIlllllIIll.showTimestamp = lIIlIlllllIIll.sgGeneral.add(new BoolSetting.Builder().name("show-timestamp").description("Show timestamp in chat.").defaultValue(true).build());
        lIIlIlllllIIll.dateFormat = new SimpleDateFormat("HH:mm:ss");
        lIIlIlllllIIll.deathPos = new HashMap<String, Double>();
        lIIlIlllllIIll.labelText = "No latest death";
    }

    @Override
    public WWidget getWidget(GuiTheme lIIlIllllIIIIl) {
        DeathPosition lIIlIllllIIIlI;
        WHorizontalList lIIlIllllIIIII = lIIlIllllIIIIl.horizontalList();
        WLabel lIIlIlllIlllll = lIIlIllllIIIII.add(lIIlIllllIIIIl.label(lIIlIllllIIIlI.labelText)).expandCellX().widget();
        WButton lIIlIlllIllllI = lIIlIllllIIIII.add(lIIlIllllIIIIl.button("Path")).widget();
        lIIlIlllIllllI.action = lIIlIllllIIIlI::path;
        WButton lIIlIlllIlllIl = lIIlIllllIIIII.add(lIIlIllllIIIIl.button("Clear")).widget();
        lIIlIlllIlllIl.action = () -> {
            DeathPosition lIIlIlllIIIIlI;
            Waypoints.get().remove(lIIlIlllIIIIlI.waypoint);
            lIIlIlllIIIIlI.labelText = "No latest death";
            lIIlIlllIlllll.set(lIIlIlllIIIIlI.labelText);
        };
        return lIIlIllllIIIII;
    }

    private void onDeath() {
        DeathPosition lIIlIlllIlIIII;
        lIIlIlllIlIIII.dmgPos = lIIlIlllIlIIII.mc.player.getPos();
        lIIlIlllIlIIII.deathPos.put("x", lIIlIlllIlIIII.dmgPos.x);
        lIIlIlllIlIIII.deathPos.put("z", lIIlIlllIlIIII.dmgPos.z);
        lIIlIlllIlIIII.labelText = String.format("Latest death: %.1f, %.1f, %.1f", lIIlIlllIlIIII.dmgPos.x, lIIlIlllIlIIII.dmgPos.y, lIIlIlllIlIIII.dmgPos.z);
        String lIIlIlllIlIIlI = lIIlIlllIlIIII.dateFormat.format(new Date());
        LiteralText lIIlIlllIlIIIl = new LiteralText("Died at ");
        lIIlIlllIlIIIl.append((Text)ChatUtils.formatCoords(lIIlIlllIlIIII.dmgPos));
        lIIlIlllIlIIIl.append(lIIlIlllIlIIII.showTimestamp.get().booleanValue() ? String.format(" on %s.", lIIlIlllIlIIlI) : ".");
        ChatUtils.moduleInfo(lIIlIlllIlIIII, (Text)lIIlIlllIlIIIl);
        if (lIIlIlllIlIIII.createWaypoint.get().booleanValue()) {
            lIIlIlllIlIIII.waypoint = new Waypoint();
            lIIlIlllIlIIII.waypoint.name = String.valueOf(new StringBuilder().append("Death ").append(lIIlIlllIlIIlI));
            lIIlIlllIlIIII.waypoint.x = (int)lIIlIlllIlIIII.dmgPos.x;
            lIIlIlllIlIIII.waypoint.y = (int)lIIlIlllIlIIII.dmgPos.y + 2;
            lIIlIlllIlIIII.waypoint.z = (int)lIIlIlllIlIIII.dmgPos.z;
            lIIlIlllIlIIII.waypoint.maxVisibleDistance = Integer.MAX_VALUE;
            lIIlIlllIlIIII.waypoint.actualDimension = Utils.getDimension();
            switch (Utils.getDimension()) {
                case Overworld: {
                    lIIlIlllIlIIII.waypoint.overworld = true;
                    break;
                }
                case Nether: {
                    lIIlIlllIlIIII.waypoint.nether = true;
                    break;
                }
                case End: {
                    lIIlIlllIlIIII.waypoint.end = true;
                }
            }
            Waypoints.get().add(lIIlIlllIlIIII.waypoint);
        }
    }
}

