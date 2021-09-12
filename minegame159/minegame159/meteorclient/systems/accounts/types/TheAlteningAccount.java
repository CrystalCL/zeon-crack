/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.accounts.types;

import com.google.gson.Gson;
import com.mojang.authlib.Agent;
import com.mojang.authlib.Environment;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.mixin.MinecraftClientAccessor;
import minegame159.meteorclient.systems.accounts.Account;
import minegame159.meteorclient.systems.accounts.AccountType;
import minegame159.meteorclient.systems.accounts.AccountUtils;
import net.minecraft.class_310;
import net.minecraft.class_320;

public class TheAlteningAccount
extends Account<TheAlteningAccount> {
    private static final String SESSION = "http://sessionserver.thealtening.com";
    private static final String AUTH;
    private static final Gson GSON;
    private static final String ACCOUNT;
    private static final String SERVICES;

    static {
        SERVICES = "https://api.minecraftservices.com";
        ACCOUNT = "https://api.mojang.com";
        AUTH = "http://authserver.thealtening.com";
        GSON = new Gson();
    }

    private YggdrasilUserAuthentication getAuth() {
        YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(((MinecraftClientAccessor)class_310.method_1551()).getProxy(), "", Environment.create((String)"http://authserver.thealtening.com", (String)"https://api.mojang.com", (String)"http://sessionserver.thealtening.com", (String)"https://api.minecraftservices.com", (String)"The Altening")).createUserAuthentication(Agent.MINECRAFT);
        yggdrasilUserAuthentication.setUsername(this.name);
        yggdrasilUserAuthentication.setPassword("Meteor on Crack!");
        return yggdrasilUserAuthentication;
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
    public boolean login() {
        YggdrasilMinecraftSessionService yggdrasilMinecraftSessionService = (YggdrasilMinecraftSessionService)class_310.method_1551().method_1495();
        AccountUtils.setBaseUrl(yggdrasilMinecraftSessionService, "http://sessionserver.thealtening.com/session/minecraft/");
        AccountUtils.setJoinUrl(yggdrasilMinecraftSessionService, "http://sessionserver.thealtening.com/session/minecraft/join");
        AccountUtils.setCheckUrl(yggdrasilMinecraftSessionService, "http://sessionserver.thealtening.com/session/minecraft/hasJoined");
        YggdrasilUserAuthentication yggdrasilUserAuthentication = this.getAuth();
        try {
            yggdrasilUserAuthentication.logIn();
            this.setSession(new class_320(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang"));
            this.cache.username = yggdrasilUserAuthentication.getSelectedProfile().getName();
            return true;
        }
        catch (AuthenticationException authenticationException) {
            MeteorClient.LOG.error("Failed to login with TheAltening.");
            return false;
        }
    }

    public TheAlteningAccount(String string) {
        super(AccountType.TheAltening, string);
    }
}

