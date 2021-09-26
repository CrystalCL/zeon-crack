/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.config;

import com.g00fy2.versioncompare.Version;
import minegame159.meteorclient.rendering.Fonts;
import minegame159.meteorclient.systems.System;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.utils.Utils;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.nbt.NbtCompound;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Config
extends System<Config> {
    public boolean titleScreenCredits = true;
    public boolean windowTitle = false;
    public int rotationHoldTicks = 9;
    public boolean sendDataToApi = true;
    public final Version version = new Version("0.1.1");
    public String devBuild = ((ModContainer)FabricLoader.getInstance().getModContainer("meteor-client").get()).getMetadata().getCustomValue("meteor-client:devbuild").getAsString();
    public boolean chatCommandsInfo = true;
    public boolean customFont = true;
    private String prefix = ".";
    public boolean deleteChatCommandsInfo = true;

    public static Config get() {
        return Systems.get(Config.class);
    }

    @Override
    public Config fromTag(NbtCompound NbtCompound2) {
        Version version;
        Version version2;
        this.prefix = NbtCompound2.getString("prefix");
        if (NbtCompound2.contains("customFont")) {
            this.customFont = NbtCompound2.getBoolean("customFont");
        }
        this.chatCommandsInfo = !NbtCompound2.contains("chatCommandsInfo") || NbtCompound2.getBoolean("chatCommandsInfo");
        this.deleteChatCommandsInfo = !NbtCompound2.contains("deleteChatCommandsInfo") || NbtCompound2.getBoolean("deleteChatCommandsInfo");
        this.sendDataToApi = !NbtCompound2.contains("sendDataToApi") || NbtCompound2.getBoolean("sendDataToApi");
        this.titleScreenCredits = !NbtCompound2.contains("titleScreenCredits") || NbtCompound2.getBoolean("titleScreenCredits");
        this.windowTitle = !NbtCompound2.contains("windowTitle") || NbtCompound2.getBoolean("windowTitle");
        Version version3 = new Version(NbtCompound2.getString("version"));
        if (version3.isLowerThan(version2 = new Version("0.2.9")) && this.version.isAtLeast(version2)) {
            Fonts.reset();
        }
        if (version3.isLowerThan(version = new Version("0.3.7")) && this.version.isAtLeast(version)) {
            Utils.addMeteorPvpToServerList();
        }
        return this;
    }

    public void setPrefix(String string) {
        this.prefix = string;
        this.save();
    }

    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("version", this.version.getOriginalString());
        NbtCompound2.putString("prefix", this.prefix);
        NbtCompound2.putBoolean("customFont", this.customFont);
        NbtCompound2.putBoolean("chatCommandsInfo", this.chatCommandsInfo);
        NbtCompound2.putBoolean("deleteChatCommandsInfo", this.deleteChatCommandsInfo);
        NbtCompound2.putBoolean("sendDataToApi", this.sendDataToApi);
        NbtCompound2.putBoolean("titleScreenCredits", this.titleScreenCredits);
        NbtCompound2.putBoolean("windowTitle", this.windowTitle);
        return NbtCompound2;
    }

    public Config() {
        super("config");
    }
}

