/*
 * Decompiled with CFR 0.151.
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PremiumAccount
extends Account<PremiumAccount> {
    private static final Gson GSON = new Gson();
    private String password;

    @Override
    public boolean fetchInfo() {
        YggdrasilUserAuthentication yggdrasilUserAuthentication = this.getAuth();
        try {
            yggdrasilUserAuthentication.logIn();
            this.cache.username = yggdrasilUserAuthentication.getSelectedProfile().getName();
            this.cache.uuid = yggdrasilUserAuthentication.getSelectedProfile().getId().toString();
            return true;
        }
        catch (AuthenticationException authenticationException) {
            return false;
        }
    }

    @Override
    public boolean fetchHead() {
        try {
            return this.cache.makeHead(String.valueOf(new StringBuilder().append("https://crafatar.com/avatars/").append(this.cache.uuid).append("?size=8&overlay&default=MHF_Steve")));
        }
        catch (Exception exception) {
            return false;
        }
    }

    public YggdrasilUserAuthentication getAuth() {
        YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(((MinecraftClientAccessor)MinecraftClient.getInstance()).getProxy(), "").createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(this.name);
        yggdrasilUserAuthentication.setPassword(this.password);
        return yggdrasilUserAuthentication;
    }

    public PremiumAccount(String string, String string2) {
        super(AccountType.Premium, string);
        this.password = string2;
    }

    @Override
    public Account fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = super.toTag();
        NbtCompound2.putString("password", this.password);
        return NbtCompound2;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public PremiumAccount fromTag(NbtCompound NbtCompound2) {
        super.fromTag(NbtCompound2);
        if (!NbtCompound2.contains("password")) {
            throw new NbtException();
        }
        this.password = NbtCompound2.getString("password");
        return this;
    }

    @Override
    public boolean login() {
        super.login();
        YggdrasilUserAuthentication yggdrasilUserAuthentication = this.getAuth();
        try {
            yggdrasilUserAuthentication.logIn();
            this.setSession(new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang"));
            this.cache.username = yggdrasilUserAuthentication.getSelectedProfile().getName();
            return true;
        }
        catch (AuthenticationUnavailableException authenticationUnavailableException) {
            MeteorClient.LOG.error("Failed to contact the authentication server.");
            return false;
        }
        catch (AuthenticationException authenticationException) {
            if (authenticationException.getMessage().contains("Invalid username or password") || authenticationException.getMessage().contains("account migrated")) {
                MeteorClient.LOG.error("Wrong password.");
            } else {
                MeteorClient.LOG.error("Failed to contact the authentication server.");
            }
            return false;
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof PremiumAccount)) {
            return false;
        }
        return ((PremiumAccount)object).name.equals(this.name);
    }
}

