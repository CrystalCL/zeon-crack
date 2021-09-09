/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Formatting
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.text.TextColor
 *  net.minecraft.client.network.PlayerListEntry
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
    private final /* synthetic */ SettingGroup sgDefault;
    private final /* synthetic */ Setting<Boolean> self;
    private final /* synthetic */ Setting<Integer> tabSize;
    private final /* synthetic */ Setting<Boolean> friends;
    private final /* synthetic */ Setting<SettingColor> selfColor;

    public Text getPlayerName(PlayerListEntry llllllIlllIlIlI) {
        Friend llllllIlllIlllI;
        BetterTab llllllIlllIIlll;
        Color llllllIlllIlIII = null;
        Text llllllIlllIlIIl = llllllIlllIlIlI.getDisplayName();
        if (llllllIlllIlIIl == null) {
            llllllIlllIlIIl = new LiteralText(llllllIlllIlIlI.getProfile().getName());
        }
        if (llllllIlllIlIlI.getProfile().getId().toString().equals(llllllIlllIIlll.mc.player.getGameProfile().getId().toString()) && llllllIlllIIlll.self.get().booleanValue()) {
            llllllIlllIlIII = llllllIlllIIlll.selfColor.get();
        } else if (llllllIlllIIlll.friends.get().booleanValue() && (llllllIlllIlllI = Friends.get().get(llllllIlllIlIlI.getProfile().getName())) != null) {
            llllllIlllIlIII = Friends.get().getFriendColor(llllllIlllIlllI);
        }
        if (llllllIlllIlIII != null) {
            String llllllIlllIllII = llllllIlllIlIIl.getString();
            for (Formatting llllllIlllIllIl : Formatting.values()) {
                if (!llllllIlllIllIl.isColor()) continue;
                llllllIlllIllII = llllllIlllIllII.replace(llllllIlllIllIl.toString(), "");
            }
            llllllIlllIlIIl = new LiteralText(llllllIlllIllII).setStyle(llllllIlllIlIIl.getStyle().withColor(new TextColor(llllllIlllIlIII.getPacked())));
        }
        return llllllIlllIlIIl;
    }

    public int getTabSize() {
        BetterTab llllllIlllllIIl;
        return llllllIlllllIIl.isActive() ? llllllIlllllIIl.tabSize.get() : 80;
    }

    public BetterTab() {
        super(Categories.Misc, "better-tab", "Various improvements to the tab list.");
        BetterTab llllllIllllllII;
        llllllIllllllII.sgDefault = llllllIllllllII.settings.getDefaultGroup();
        llllllIllllllII.tabSize = llllllIllllllII.sgDefault.add(new IntSetting.Builder().name("tablist-size").description("Bypasses the 80 player limit on the tablist.").defaultValue(100).min(1).sliderMin(1).build());
        llllllIllllllII.friends = llllllIllllllII.sgDefault.add(new BoolSetting.Builder().name("highlight-friends").description("Highlights friends in the tablist.").defaultValue(true).build());
        llllllIllllllII.self = llllllIllllllII.sgDefault.add(new BoolSetting.Builder().name("highlight-self").description("Highlights yourself in the tablist.").defaultValue(true).build());
        llllllIllllllII.selfColor = llllllIllllllII.sgDefault.add(new ColorSetting.Builder().name("self-color").description("The color to highlight your name with.").defaultValue(new SettingColor(250, 130, 30)).build());
    }
}

