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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

public abstract class Account<T extends Account<?>>
implements ISerializable<T> {
    protected String name;
    protected AccountType type;
    protected final AccountCache cache;

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("type", this.type.name());
        NbtCompound2.putString("name", this.name);
        NbtCompound2.put("cache", (NbtElement)this.cache.toTag());
        return NbtCompound2;
    }

    public AccountCache getCache() {
        return this.cache;
    }

    @Override
    public T fromTag(NbtCompound NbtCompound2) {
        if (!NbtCompound2.contains("name") || !NbtCompound2.contains("cache")) {
            throw new NbtException();
        }
        this.name = NbtCompound2.getString("name");
        this.cache.fromTag(NbtCompound2.getCompound("cache"));
        return (T)this;
    }

    protected void setSession(Session Session2) {
        ((MinecraftClientAccessor)MinecraftClient.getInstance()).setSession(Session2);
        MinecraftClient.getInstance().getSessionProperties().clear();
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
        YggdrasilMinecraftSessionService yggdrasilMinecraftSessionService = (YggdrasilMinecraftSessionService)MinecraftClient.getInstance().getSessionService();
        AccountUtils.setBaseUrl(yggdrasilMinecraftSessionService, String.valueOf(new StringBuilder().append(YggdrasilEnvironment.PROD.getSessionHost()).append("/session/minecraft/")));
        AccountUtils.setJoinUrl(yggdrasilMinecraftSessionService, String.valueOf(new StringBuilder().append(YggdrasilEnvironment.PROD.getSessionHost()).append("/session/minecraft/join")));
        AccountUtils.setCheckUrl(yggdrasilMinecraftSessionService, String.valueOf(new StringBuilder().append(YggdrasilEnvironment.PROD.getSessionHost()).append("/session/minecraft/hasJoined")));
        return true;
    }
}

