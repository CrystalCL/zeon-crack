/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.accounts;

import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import minegame159.meteorclient.mixin.MinecraftClientAccessor;
import minegame159.meteorclient.systems.accounts.AccountCache;
import minegame159.meteorclient.systems.accounts.AccountType;
import minegame159.meteorclient.systems.accounts.AccountUtils;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.misc.NbtException;
import net.minecraft.class_2487;
import net.minecraft.class_2520;
import net.minecraft.class_310;
import net.minecraft.class_320;

public abstract class Account<T extends Account<?>>
implements ISerializable<T> {
    protected String name;
    protected AccountType type;
    protected final AccountCache cache;

    @Override
    public Object fromTag(class_2487 class_24872) {
        return this.fromTag(class_24872);
    }

    @Override
    public class_2487 toTag() {
        class_2487 class_24872 = new class_2487();
        class_24872.method_10582("type", this.type.name());
        class_24872.method_10582("name", this.name);
        class_24872.method_10566("cache", (class_2520)this.cache.toTag());
        return class_24872;
    }

    public AccountCache getCache() {
        return this.cache;
    }

    @Override
    public T fromTag(class_2487 class_24872) {
        if (!class_24872.method_10545("name") || !class_24872.method_10545("cache")) {
            throw new NbtException();
        }
        this.name = class_24872.method_10558("name");
        this.cache.fromTag(class_24872.method_10562("cache"));
        return (T)this;
    }

    protected void setSession(class_320 class_3202) {
        ((MinecraftClientAccessor)class_310.method_1551()).setSession(class_3202);
        class_310.method_1551().method_1539().clear();
    }

    public String getUsername() {
        if (this.cache.username.isEmpty()) {
            return this.name;
        }
        return this.cache.username;
    }

    public AccountType getType() {
        return this.type;
    }

    public abstract boolean fetchHead();

    public abstract boolean fetchInfo();

    public Account(AccountType accountType, String string) {
        this.type = accountType;
        this.name = string;
        this.cache = new AccountCache();
    }

    public boolean login() {
        YggdrasilMinecraftSessionService yggdrasilMinecraftSessionService = (YggdrasilMinecraftSessionService)class_310.method_1551().method_1495();
        AccountUtils.setBaseUrl(yggdrasilMinecraftSessionService, String.valueOf(new StringBuilder().append(YggdrasilEnvironment.PROD.getSessionHost()).append("/session/minecraft/")));
        AccountUtils.setJoinUrl(yggdrasilMinecraftSessionService, String.valueOf(new StringBuilder().append(YggdrasilEnvironment.PROD.getSessionHost()).append("/session/minecraft/join")));
        AccountUtils.setCheckUrl(yggdrasilMinecraftSessionService, String.valueOf(new StringBuilder().append(YggdrasilEnvironment.PROD.getSessionHost()).append("/session/minecraft/hasJoined")));
        return true;
    }
}

