/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.PlayerEntity
 */
package minegame159.meteorclient.systems.modules.player;

import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import net.minecraft.entity.player.PlayerEntity;

public class FakePlayer
extends Module {
    public final /* synthetic */ Setting<Integer> health;
    private final /* synthetic */ SettingGroup sgGeneral;
    public final /* synthetic */ Setting<Boolean> copyInv;
    public final /* synthetic */ Setting<Boolean> idInNametag;
    public final /* synthetic */ Setting<String> name;
    public final /* synthetic */ Setting<Boolean> glowing;

    public FakePlayer() {
        super(Categories.Player, "fake-player", "Spawns a client-side fake player for testing usages.");
        FakePlayer llllllllllllllllIllIIIIIIIllllll;
        llllllllllllllllIllIIIIIIIllllll.sgGeneral = llllllllllllllllIllIIIIIIIllllll.settings.getDefaultGroup();
        llllllllllllllllIllIIIIIIIllllll.name = llllllllllllllllIllIIIIIIIllllll.sgGeneral.add(new StringSetting.Builder().name("name").description("The name of the fake player.").defaultValue("seasnail8169").build());
        llllllllllllllllIllIIIIIIIllllll.copyInv = llllllllllllllllIllIIIIIIIllllll.sgGeneral.add(new BoolSetting.Builder().name("copy-inv").description("Copies your exact inventory to the fake player.").defaultValue(true).build());
        llllllllllllllllIllIIIIIIIllllll.glowing = llllllllllllllllIllIIIIIIIllllll.sgGeneral.add(new BoolSetting.Builder().name("glowing").description("Grants the fake player a glowing effect.").defaultValue(true).build());
        llllllllllllllllIllIIIIIIIllllll.health = llllllllllllllllIllIIIIIIIllllll.sgGeneral.add(new IntSetting.Builder().name("health").description("The fake player's default health.").defaultValue(20).min(1).sliderMax(100).build());
        llllllllllllllllIllIIIIIIIllllll.idInNametag = llllllllllllllllIllIIIIIIIllllll.sgGeneral.add(new BoolSetting.Builder().name("iD-in-nametag").description("Displays the fake player's ID inside its nametag.").defaultValue(true).build());
    }

    @Override
    public void onDeactivate() {
        FakePlayerUtils.ID = 0;
        FakePlayerUtils.clearFakePlayers();
    }

    @Override
    public String getInfoString() {
        if (FakePlayerUtils.getPlayers() != null) {
            return String.valueOf(FakePlayerUtils.getPlayers().size());
        }
        return null;
    }

    @Override
    public WWidget getWidget(GuiTheme llllllllllllllllIllIIIIIIIllIlll) {
        WHorizontalList llllllllllllllllIllIIIIIIIllIllI = llllllllllllllllIllIIIIIIIllIlll.horizontalList();
        WButton llllllllllllllllIllIIIIIIIllIlIl = llllllllllllllllIllIIIIIIIllIllI.add(llllllllllllllllIllIIIIIIIllIlll.button("Spawn")).widget();
        llllllllllllllllIllIIIIIIIllIlIl.action = FakePlayerUtils::spawnFakePlayer;
        WButton llllllllllllllllIllIIIIIIIllIlII = llllllllllllllllIllIIIIIIIllIllI.add(llllllllllllllllIllIIIIIIIllIlll.button("Clear")).widget();
        llllllllllllllllIllIIIIIIIllIlII.action = FakePlayerUtils::clearFakePlayers;
        return llllllllllllllllIllIIIIIIIllIllI;
    }

    @Override
    public void onActivate() {
        FakePlayerUtils.ID = 0;
    }

    public boolean showID(PlayerEntity llllllllllllllllIllIIIIIIIlIllII) {
        FakePlayer llllllllllllllllIllIIIIIIIlIlIll;
        return llllllllllllllllIllIIIIIIIlIlIll.isActive() && llllllllllllllllIllIIIIIIIlIlIll.idInNametag.get() != false && llllllllllllllllIllIIIIIIIlIllII instanceof FakePlayerEntity;
    }
}

