/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.misc;

import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friend;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.Formatting;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.client.network.PlayerListEntry;

public class BetterTab
extends Module {
    private final Setting<Integer> tabSize;
    private final Setting<Boolean> self;
    private final Setting<Boolean> friends;
    private final Setting<SettingColor> selfColor;
    private final SettingGroup sgDefault;

    public BetterTab() {
        super(Categories.Misc, "better-tab", "Various improvements to the tab list.");
        this.sgDefault = this.settings.getDefaultGroup();
        this.tabSize = this.sgDefault.add(new IntSetting.Builder().name("tablist-size").description("Bypasses the 80 player limit on the tablist.").defaultValue(100).min(1).sliderMin(1).build());
        this.friends = this.sgDefault.add(new BoolSetting.Builder().name("highlight-friends").description("Highlights friends in the tablist.").defaultValue(true).build());
        this.self = this.sgDefault.add(new BoolSetting.Builder().name("highlight-self").description("Highlights yourself in the tablist.").defaultValue(true).build());
        this.selfColor = this.sgDefault.add(new ColorSetting.Builder().name("self-color").description("The color to highlight your name with.").defaultValue(new SettingColor(250, 130, 30)).build());
    }

    public int getTabSize() {
        return this.isActive() ? this.tabSize.get() : 80;
    }

    public Text getPlayerName(PlayerListEntry PlayerListEntry2) {
        Object object;
        Color color = null;
        Text Text2 = PlayerListEntry2.getDisplayName();
        if (Text2 == null) {
            Text2 = new LiteralText(PlayerListEntry2.getProfile().getName());
        }
        if (PlayerListEntry2.getProfile().getId().toString().equals(this.mc.player.getGameProfile().getId().toString()) && this.self.get().booleanValue()) {
            color = this.selfColor.get();
        } else if (this.friends.get().booleanValue() && (object = Friends.get().get(PlayerListEntry2.getProfile().getName())) != null) {
            color = Friends.get().getFriendColor((Friend)object);
        }
        if (color != null) {
            object = Text2.getString();
            for (Formatting Schema1483 : Formatting.values()) {
                if (!Schema1483.isColor()) continue;
                object = ((String)object).replace(Schema1483.toString(), "");
                if (4 != 1) continue;
                return null;
            }
            Text2 = new LiteralText((String)object).setStyle(Text2.getStyle().withColor(new TextColor(color.getPacked())));
        }
        return Text2;
    }
}

