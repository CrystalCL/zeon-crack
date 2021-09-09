/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.yggdrasil.YggdrasilEnvironment
 *  com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.util.Session
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
    protected /* synthetic */ String name;
    protected final /* synthetic */ AccountCache cache;
    protected /* synthetic */ AccountType type;

    public abstract boolean fetchInfo();

    @Override
    public NbtCompound toTag() {
        Account llllllllllllllllllIlllIlIIlllIll;
        NbtCompound llllllllllllllllllIlllIlIIlllIlI = new NbtCompound();
        llllllllllllllllllIlllIlIIlllIlI.putString("type", llllllllllllllllllIlllIlIIlllIll.type.name());
        llllllllllllllllllIlllIlIIlllIlI.putString("name", llllllllllllllllllIlllIlIIlllIll.name);
        llllllllllllllllllIlllIlIIlllIlI.put("cache", (NbtElement)llllllllllllllllllIlllIlIIlllIll.cache.toTag());
        return llllllllllllllllllIlllIlIIlllIlI;
    }

    public AccountType getType() {
        Account llllllllllllllllllIlllIlIlIIIllI;
        return llllllllllllllllllIlllIlIlIIIllI.type;
    }

    @Override
    public T fromTag(NbtCompound llllllllllllllllllIlllIlIIllIIlI) {
        Account llllllllllllllllllIlllIlIIllIIll;
        if (!llllllllllllllllllIlllIlIIllIIlI.contains("name") || !llllllllllllllllllIlllIlIIllIIlI.contains("cache")) {
            throw new NbtException();
        }
        llllllllllllllllllIlllIlIIllIIll.name = llllllllllllllllllIlllIlIIllIIlI.getString("name");
        llllllllllllllllllIlllIlIIllIIll.cache.fromTag(llllllllllllllllllIlllIlIIllIIlI.getCompound("cache"));
        return (T)llllllllllllllllllIlllIlIIllIIll;
    }

    public boolean login() {
        YggdrasilMinecraftSessionService llllllllllllllllllIlllIlIlIIllII = (YggdrasilMinecraftSessionService)MinecraftClient.getInstance().getSessionService();
        AccountUtils.setBaseUrl(llllllllllllllllllIlllIlIlIIllII, String.valueOf(new StringBuilder().append(YggdrasilEnvironment.PROD.getSessionHost()).append("/session/minecraft/")));
        AccountUtils.setJoinUrl(llllllllllllllllllIlllIlIlIIllII, String.valueOf(new StringBuilder().append(YggdrasilEnvironment.PROD.getSessionHost()).append("/session/minecraft/join")));
        AccountUtils.setCheckUrl(llllllllllllllllllIlllIlIlIIllII, String.valueOf(new StringBuilder().append(YggdrasilEnvironment.PROD.getSessionHost()).append("/session/minecraft/hasJoined")));
        return true;
    }

    public Account(AccountType llllllllllllllllllIlllIlIlIlIIll, String llllllllllllllllllIlllIlIlIlIIlI) {
        Account llllllllllllllllllIlllIlIlIlIIIl;
        llllllllllllllllllIlllIlIlIlIIIl.type = llllllllllllllllllIlllIlIlIlIIll;
        llllllllllllllllllIlllIlIlIlIIIl.name = llllllllllllllllllIlllIlIlIlIIlI;
        llllllllllllllllllIlllIlIlIlIIIl.cache = new AccountCache();
    }

    public String getUsername() {
        Account llllllllllllllllllIlllIlIlIIlIII;
        if (llllllllllllllllllIlllIlIlIIlIII.cache.username.isEmpty()) {
            return llllllllllllllllllIlllIlIlIIlIII.name;
        }
        return llllllllllllllllllIlllIlIlIIlIII.cache.username;
    }

    protected void setSession(Session llllllllllllllllllIlllIlIIlllllI) {
        ((MinecraftClientAccessor)MinecraftClient.getInstance()).setSession(llllllllllllllllllIlllIlIIlllllI);
        MinecraftClient.getInstance().getSessionProperties().clear();
    }

    public abstract boolean fetchHead();

    public AccountCache getCache() {
        Account llllllllllllllllllIlllIlIlIIIIll;
        return llllllllllllllllllIlllIlIlIIIIll.cache;
    }
}

