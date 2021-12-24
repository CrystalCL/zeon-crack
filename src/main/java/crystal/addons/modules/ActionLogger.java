package crystal.addons.modules;

import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
import meteordevelopment.meteorclient.gui.widgets.pressable.WMinus;
import meteordevelopment.meteorclient.gui.widgets.pressable.WPlus;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.BaseText;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.world.GameMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class ActionLogger extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Boolean> joinleave = sgGeneral.add(new BoolSetting.Builder().name("join-leave").defaultValue(true).build());
    private final Setting<Boolean> gamemode = sgGeneral.add(new BoolSetting.Builder().name("game-mode-change").defaultValue(true).build());

    private final ArrayList<String> players = new ArrayList<String>();
    HashMap<String, GameMode> state = new HashMap();

    public ActionLogger() {
        super(CrystalCL.PvE, "action-logger", "Send message on player action.");
    }

    public void onActivate() {
        state.clear();
        if (players.isEmpty()) {
            toggle();
        } else {
            ArrayList<PlayerListEntry> list = (ArrayList<PlayerListEntry>) mc.getNetworkHandler().getPlayerList();

            for (PlayerListEntry o : list) {
                if (players.contains(o.getProfile().getName())) {
                    state.put(o.getProfile().getName(), o.getGameMode());
                }
            }
        }
    }

    private BaseText getMode(GameMode m) {
        String tmode = "Survival";
        int color = 16777215;
        if (m == GameMode.CREATIVE) {
            tmode = "Creative";
            color = 10053324;
        }

        if (m == GameMode.ADVENTURE) {
            tmode = "Adventure";
            color = 7855591;
        }

        if (m == GameMode.SPECTATOR) {
            tmode = "Spectator";
            color = 16720896;
        }

        BaseText mode = new LiteralText(tmode);
        mode.setStyle(mode.getStyle().withColor(TextColor.fromRgb(1)));
        return mode;
    }

    private BaseText getText(String s) {
        BaseText text = new LiteralText("[ActionLogger] " + s);
        text.setStyle(text.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText((new SimpleDateFormat("yyyy.MM.dd HH:mm:ss")).format(new Date(System.currentTimeMillis()))))));
        return text;
    }

    private void send(BaseText s) {
        mc.inGameHud.getChatHud().addMessage(s);
    }

    private void sayModeChange(String s, GameMode m) {
        BaseText text = getText(s);
        text.append(getMode(m));
        send(text);
    }

    private void sayMode(String s, GameMode m) {
        BaseText text = getText(s);
        if (m != null) {
            text.append(getMode(m));
        }
        send(text);
    }

    @EventHandler
    private void onTick(TickEvent.Post e) {
        if (players.isEmpty()) {
            toggle();
        } else {
            ArrayList<PlayerListEntry> list = (ArrayList<PlayerListEntry>) mc.getNetworkHandler().getPlayerList();
            HashMap<String, GameMode> newstate = new HashMap();
            Iterator var4 = list.iterator();

            while (var4.hasNext()) {
                PlayerListEntry p = (PlayerListEntry) var4.next();
                if (players.contains(p.getProfile().getName())) {
                    newstate.put(p.getProfile().getName(), p.getGameMode());
                }
            }

            var4 = players.iterator();


            while (var4.hasNext()) {
                String p = (String) var4.next();
                if (joinleave.get()) {
                    if (state.containsKey(p)) {
                        if (!newstate.containsKey(p)) {
                            sayMode(p + " left the game", null);
                            continue;
                        }
                    } else if (newstate.containsKey(p)) {
                        GameMode mode = null;
                        if (newstate.get(p) != GameMode.SURVIVAL) {
                            mode = newstate.get(p);
                        }

                        sayMode(p + " joined the game", mode);
                        continue;
                    }
                }

                if (gamemode.get() && state.containsKey(p) && newstate.containsKey(p) && state.get(p) != newstate.get(p)) {
                    sayModeChange(p + " changed the game mode to ", newstate.get(p));
                }
            }

            state = newstate;
            return;
        }
    }

    public WWidget getWidget(GuiTheme theme) {
        players.removeIf(String::isEmpty);
        WTable table = theme.table();
        fillTable(theme, table);
        return table;
    }

    private void fillTable(GuiTheme theme, WTable table) {
        table.add(theme.horizontalSeparator("Players")).expandX();
        table.row();

        for (int i = 0; i < players.size(); i++) {
            final int i2 = i;
            String player = players.get(i);
            WTextBox textBox = table.add(theme.textBox(String.valueOf(player))).minWidth(100.0D).expandX().widget();
            textBox.action = () -> players.set(i2, textBox.get());
            WMinus delete = table.add(theme.minus()).widget();
            delete.action = () -> {
                players.remove(i2);
                table.clear();
                fillTable(theme, table);
            };
            table.row();
        }

        WPlus add = table.add(theme.plus()).expandCellX().right().widget();
        add.action = () -> {
            players.add("");
            table.clear();
            fillTable(theme, table);
        };
    }

    public NbtCompound toTag() {
        NbtCompound tag = super.toTag();
        players.removeIf(String::isEmpty);
        NbtList playersTag = new NbtList();

        for (String player : players) {
            playersTag.add(NbtString.of(player));
        }

        tag.put("players", playersTag);
        return tag;
    }

    public Module fromTag(NbtCompound tag) {
        players.clear();
        if (tag.contains("players")) {
            NbtList playersTag = tag.getList("players", 8);

            for (NbtElement playerTag : playersTag) {
                players.add(playerTag.asString());
            }
        } else {
            players.add("nag1bator228");
        }

        return super.fromTag(tag);
    }
}
