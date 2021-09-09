/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.exceptions.AuthenticationUnavailableException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.util.Session
 */
package minegame159.meteorclient.systems.accounts.types;

import com.google.gson.Gson;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.mixin.MinecraftClientAccessor;
import minegame159.meteorclient.systems.accounts.Account;
import minegame159.meteorclient.systems.accounts.AccountType;
import minegame159.meteorclient.utils.misc.NbtException;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

public class PremiumAccount
extends Account<PremiumAccount> {
    private /* synthetic */ String password;
    private static final /* synthetic */ Gson GSON;

    @Override
    public boolean login() {
        PremiumAccount lllllllllllllllllllIlIllIIllllII;
        super.login();
        YggdrasilUserAuthentication lllllllllllllllllllIlIllIIlllIll = lllllllllllllllllllIlIllIIllllII.getAuth();
        try {
            lllllllllllllllllllIlIllIIlllIll.logIn();
            lllllllllllllllllllIlIllIIllllII.setSession(new Session(lllllllllllllllllllIlIllIIlllIll.getSelectedProfile().getName(), lllllllllllllllllllIlIllIIlllIll.getSelectedProfile().getId().toString(), lllllllllllllllllllIlIllIIlllIll.getAuthenticatedToken(), "mojang"));
            lllllllllllllllllllIlIllIIllllII.cache.username = lllllllllllllllllllIlIllIIlllIll.getSelectedProfile().getName();
            return true;
        }
        catch (AuthenticationUnavailableException lllllllllllllllllllIlIllIIlllllI) {
            MeteorClient.LOG.error("Failed to contact the authentication server.");
            return false;
        }
        catch (AuthenticationException lllllllllllllllllllIlIllIIllllIl) {
            if (lllllllllllllllllllIlIllIIllllIl.getMessage().contains("Invalid username or password") || lllllllllllllllllllIlIllIIllllIl.getMessage().contains("account migrated")) {
                MeteorClient.LOG.error("Wrong password.");
            } else {
                MeteorClient.LOG.error("Failed to contact the authentication server.");
            }
            return false;
        }
    }

    @Override
    public boolean fetchHead() {
        try {
            PremiumAccount lllllllllllllllllllIlIllIlIIIlII;
            return lllllllllllllllllllIlIllIlIIIlII.cache.makeHead(String.valueOf(new StringBuilder().append("https://crafatar.com/avatars/").append(lllllllllllllllllllIlIllIlIIIlII.cache.uuid).append("?size=8&overlay&default=MHF_Steve")));
        }
        catch (Exception lllllllllllllllllllIlIllIlIIIlIl) {
            return false;
        }
    }

    @Override
    public boolean fetchInfo() {
        PremiumAccount lllllllllllllllllllIlIllIlIIlIlI;
        YggdrasilUserAuthentication lllllllllllllllllllIlIllIlIIlIll = lllllllllllllllllllIlIllIlIIlIlI.getAuth();
        try {
            lllllllllllllllllllIlIllIlIIlIll.logIn();
            lllllllllllllllllllIlIllIlIIlIlI.cache.username = lllllllllllllllllllIlIllIlIIlIll.getSelectedProfile().getName();
            lllllllllllllllllllIlIllIlIIlIlI.cache.uuid = lllllllllllllllllllIlIllIlIIlIll.getSelectedProfile().getId().toString();
            return true;
        }
        catch (AuthenticationException lllllllllllllllllllIlIllIlIIllIl) {
            return false;
        }
    }

    @Override
    public PremiumAccount fromTag(NbtCompound lllllllllllllllllllIlIllIIlIIllI) {
        PremiumAccount lllllllllllllllllllIlIllIIlIIlll;
        super.fromTag(lllllllllllllllllllIlIllIIlIIllI);
        if (!lllllllllllllllllllIlIllIIlIIllI.contains("password")) {
            throw new NbtException();
        }
        lllllllllllllllllllIlIllIIlIIlll.password = lllllllllllllllllllIlIllIIlIIllI.getString("password");
        return lllllllllllllllllllIlIllIIlIIlll;
    }

    @Override
    public NbtCompound toTag() {
        PremiumAccount lllllllllllllllllllIlIllIIlIllIl;
        NbtCompound lllllllllllllllllllIlIllIIlIlllI = super.toTag();
        lllllllllllllllllllIlIllIIlIlllI.putString("password", lllllllllllllllllllIlIllIIlIllIl.password);
        return lllllllllllllllllllIlIllIIlIlllI;
    }

    public PremiumAccount(String lllllllllllllllllllIlIllIlIlIlIl, String lllllllllllllllllllIlIllIlIlIlII) {
        super(AccountType.Premium, lllllllllllllllllllIlIllIlIlIlIl);
        PremiumAccount lllllllllllllllllllIlIllIlIlIIll;
        lllllllllllllllllllIlIllIlIlIIll.password = lllllllllllllllllllIlIllIlIlIlII;
    }

    public boolean equals(Object lllllllllllllllllllIlIllIIlIIIlI) {
        PremiumAccount lllllllllllllllllllIlIllIIlIIIll;
        if (!(lllllllllllllllllllIlIllIIlIIIlI instanceof PremiumAccount)) {
            return false;
        }
        return ((PremiumAccount)lllllllllllllllllllIlIllIIlIIIlI).name.equals(lllllllllllllllllllIlIllIIlIIIll.name);
    }

    static {
        GSON = new Gson();
    }

    public YggdrasilUserAuthentication getAuth() {
        PremiumAccount lllllllllllllllllllIlIllIIllIlIl;
        YggdrasilUserAuthentication lllllllllllllllllllIlIllIIllIlII = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(((MinecraftClientAccessor)MinecraftClient.getInstance()).getProxy(), "").createUserAuthentication(Agent.MINECRAFT);
        lllllllllllllllllllIlIllIIllIlII.setUsername(lllllllllllllllllllIlIllIIllIlIl.name);
        lllllllllllllllllllIlIllIIllIlII.setPassword(lllllllllllllllllllIlIllIIllIlIl.password);
        return lllllllllllllllllllIlIllIIllIlII;
    }
}

