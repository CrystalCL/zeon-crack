/*
 * Decompiled with CFR 0.151.
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
    public final Setting<String> name;
    public final Setting<Boolean> glowing;
    public final Setting<Integer> health;
    public final Setting<Boolean> idInNametag;
    public final Setting<Boolean> copyInv;
    private final SettingGroup sgGeneral;

    public boolean showID(PlayerEntity PlayerEntity2) {
        return this.isActive() && this.idInNametag.get() != false && PlayerEntity2 instanceof FakePlayerEntity;
    }

    @Override
    public WWidget getWidget(GuiTheme guiTheme) {
        WHorizontalList wHorizontalList = guiTheme.horizontalList();
        WButton wButton = wHorizontalList.add(guiTheme.button("Spawn")).widget();
        wButton.action = FakePlayerUtils::spawnFakePlayer;
        WButton wButton2 = wHorizontalList.add(guiTheme.button("Clear")).widget();
        wButton2.action = FakePlayerUtils::clearFakePlayers;
        return wHorizontalList;
    }

    public FakePlayer() {
        super(Categories.Player, "fake-player", "Spawns a client-side fake player for testing usages.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.name = this.sgGeneral.add(new StringSetting.Builder().name("name").description("The name of the fake player.").defaultValue("seasnail8169").build());
        this.copyInv = this.sgGeneral.add(new BoolSetting.Builder().name("copy-inv").description("Copies your exact inventory to the fake player.").defaultValue(true).build());
        this.glowing = this.sgGeneral.add(new BoolSetting.Builder().name("glowing").description("Grants the fake player a glowing effect.").defaultValue(true).build());
        this.health = this.sgGeneral.add(new IntSetting.Builder().name("health").description("The fake player's default health.").defaultValue(20).min(1).sliderMax(100).build());
        this.idInNametag = this.sgGeneral.add(new BoolSetting.Builder().name("iD-in-nametag").description("Displays the fake player's ID inside its nametag.").defaultValue(true).build());
    }

    @Override
    public void onActivate() {
        FakePlayerUtils.ID = 0;
    }

    @Override
    public String getInfoString() {
        if (FakePlayerUtils.getPlayers() != null) {
            return String.valueOf(FakePlayerUtils.getPlayers().size());
        }
        return null;
    }

    @Override
    public void onDeactivate() {
        FakePlayerUtils.ID = 0;
        FakePlayerUtils.clearFakePlayers();
    }
}

