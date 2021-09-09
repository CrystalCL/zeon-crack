/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.fabricmc.loader.api.FabricLoader
 *  net.fabricmc.loader.api.ModContainer
 *  net.minecraft.nbt.NbtCompound
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

public class Config
extends System<Config> {
    public final /* synthetic */ Version version;
    private /* synthetic */ String prefix;
    public /* synthetic */ String devBuild;
    public /* synthetic */ boolean deleteChatCommandsInfo;
    public /* synthetic */ boolean sendDataToApi;
    public /* synthetic */ boolean titleScreenCredits;
    public /* synthetic */ boolean chatCommandsInfo;
    public /* synthetic */ boolean customFont;
    public /* synthetic */ boolean windowTitle;
    public /* synthetic */ int rotationHoldTicks;

    public void setPrefix(String lllIllllllIIlIl) {
        Config lllIllllllIIllI;
        lllIllllllIIllI.prefix = lllIllllllIIlIl;
        lllIllllllIIllI.save();
    }

    public Config() {
        super("config");
        Config lllIllllllIlIIl;
        lllIllllllIlIIl.version = new Version("0.1.1");
        lllIllllllIlIIl.prefix = ".";
        lllIllllllIlIIl.customFont = true;
        lllIllllllIlIIl.chatCommandsInfo = true;
        lllIllllllIlIIl.deleteChatCommandsInfo = true;
        lllIllllllIlIIl.sendDataToApi = true;
        lllIllllllIlIIl.titleScreenCredits = true;
        lllIllllllIlIIl.rotationHoldTicks = 9;
        lllIllllllIlIIl.windowTitle = false;
        lllIllllllIlIIl.devBuild = ((ModContainer)FabricLoader.getInstance().getModContainer("meteor-client").get()).getMetadata().getCustomValue("meteor-client:devbuild").getAsString();
    }

    public static Config get() {
        return Systems.get(Config.class);
    }

    public String getPrefix() {
        Config lllIllllllIIIIl;
        return lllIllllllIIIIl.prefix;
    }

    @Override
    public NbtCompound toTag() {
        Config lllIlllllIllIll;
        NbtCompound lllIlllllIlllII = new NbtCompound();
        lllIlllllIlllII.putString("version", lllIlllllIllIll.version.getOriginalString());
        lllIlllllIlllII.putString("prefix", lllIlllllIllIll.prefix);
        lllIlllllIlllII.putBoolean("customFont", lllIlllllIllIll.customFont);
        lllIlllllIlllII.putBoolean("chatCommandsInfo", lllIlllllIllIll.chatCommandsInfo);
        lllIlllllIlllII.putBoolean("deleteChatCommandsInfo", lllIlllllIllIll.deleteChatCommandsInfo);
        lllIlllllIlllII.putBoolean("sendDataToApi", lllIlllllIllIll.sendDataToApi);
        lllIlllllIlllII.putBoolean("titleScreenCredits", lllIlllllIllIll.titleScreenCredits);
        lllIlllllIlllII.putBoolean("windowTitle", lllIlllllIllIll.windowTitle);
        return lllIlllllIlllII;
    }

    @Override
    public Config fromTag(NbtCompound lllIlllllIlIIll) {
        Version lllIlllllIlIIII;
        Config lllIlllllIlIlII;
        Version lllIlllllIlIIIl;
        lllIlllllIlIlII.prefix = lllIlllllIlIIll.getString("prefix");
        if (lllIlllllIlIIll.contains("customFont")) {
            lllIlllllIlIlII.customFont = lllIlllllIlIIll.getBoolean("customFont");
        }
        lllIlllllIlIlII.chatCommandsInfo = !lllIlllllIlIIll.contains("chatCommandsInfo") || lllIlllllIlIIll.getBoolean("chatCommandsInfo");
        lllIlllllIlIlII.deleteChatCommandsInfo = !lllIlllllIlIIll.contains("deleteChatCommandsInfo") || lllIlllllIlIIll.getBoolean("deleteChatCommandsInfo");
        lllIlllllIlIlII.sendDataToApi = !lllIlllllIlIIll.contains("sendDataToApi") || lllIlllllIlIIll.getBoolean("sendDataToApi");
        lllIlllllIlIlII.titleScreenCredits = !lllIlllllIlIIll.contains("titleScreenCredits") || lllIlllllIlIIll.getBoolean("titleScreenCredits");
        lllIlllllIlIlII.windowTitle = !lllIlllllIlIIll.contains("windowTitle") || lllIlllllIlIIll.getBoolean("windowTitle");
        Version lllIlllllIlIIlI = new Version(lllIlllllIlIIll.getString("version"));
        if (lllIlllllIlIIlI.isLowerThan(lllIlllllIlIIIl = new Version("0.2.9")) && lllIlllllIlIlII.version.isAtLeast(lllIlllllIlIIIl)) {
            Fonts.reset();
        }
        if (lllIlllllIlIIlI.isLowerThan(lllIlllllIlIIII = new Version("0.3.7")) && lllIlllllIlIlII.version.isAtLeast(lllIlllllIlIIII)) {
            Utils.addMeteorPvpToServerList();
        }
        return lllIlllllIlIlII;
    }
}

