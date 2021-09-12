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
import net.minecraft.class_2487;

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
    public Config fromTag(class_2487 class_24872) {
        Version version;
        Version version2;
        this.prefix = class_24872.method_10558("prefix");
        if (class_24872.method_10545("customFont")) {
            this.customFont = class_24872.method_10577("customFont");
        }
        this.chatCommandsInfo = !class_24872.method_10545("chatCommandsInfo") || class_24872.method_10577("chatCommandsInfo");
        this.deleteChatCommandsInfo = !class_24872.method_10545("deleteChatCommandsInfo") || class_24872.method_10577("deleteChatCommandsInfo");
        this.sendDataToApi = !class_24872.method_10545("sendDataToApi") || class_24872.method_10577("sendDataToApi");
        this.titleScreenCredits = !class_24872.method_10545("titleScreenCredits") || class_24872.method_10577("titleScreenCredits");
        this.windowTitle = !class_24872.method_10545("windowTitle") || class_24872.method_10577("windowTitle");
        Version version3 = new Version(class_24872.method_10558("version"));
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
    public Object fromTag(class_2487 class_24872) {
        return this.fromTag(class_24872);
    }

    @Override
    public class_2487 toTag() {
        class_2487 class_24872 = new class_2487();
        class_24872.method_10582("version", this.version.getOriginalString());
        class_24872.method_10582("prefix", this.prefix);
        class_24872.method_10556("customFont", this.customFont);
        class_24872.method_10556("chatCommandsInfo", this.chatCommandsInfo);
        class_24872.method_10556("deleteChatCommandsInfo", this.deleteChatCommandsInfo);
        class_24872.method_10556("sendDataToApi", this.sendDataToApi);
        class_24872.method_10556("titleScreenCredits", this.titleScreenCredits);
        class_24872.method_10556("windowTitle", this.windowTitle);
        return class_24872;
    }

    public Config() {
        super("config");
    }
}

