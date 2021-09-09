/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.Environment
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.util.Session
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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

public class TheAlteningAccount
extends Account<TheAlteningAccount> {
    private static final /* synthetic */ String AUTH;
    private static final /* synthetic */ String SERVICES;
    private static final /* synthetic */ String SESSION;
    private static final /* synthetic */ Gson GSON;
    private static final /* synthetic */ String ACCOUNT;

    @Override
    public boolean fetchInfo() {
        TheAlteningAccount lllIlIlllIIllI;
        YggdrasilUserAuthentication lllIlIlllIIlII = lllIlIlllIIllI.getAuth();
        try {
            lllIlIlllIIlII.logIn();
            lllIlIlllIIllI.cache.username = lllIlIlllIIlII.getSelectedProfile().getName();
            lllIlIlllIIllI.cache.uuid = lllIlIlllIIlII.getSelectedProfile().getId().toString();
            return true;
        }
        catch (AuthenticationException lllIlIlllIlIII) {
            return false;
        }
    }

    public TheAlteningAccount(String lllIlIlllIllll) {
        super(AccountType.TheAltening, lllIlIlllIllll);
        TheAlteningAccount lllIlIllllIIII;
    }

    @Override
    public boolean fetchHead() {
        try {
            TheAlteningAccount lllIlIllIIlIll;
            return lllIlIllIIlIll.cache.makeHead(String.valueOf(new StringBuilder().append("https://crafatar.com/avatars/").append(lllIlIllIIlIll.cache.uuid).append("?size=8&overlay&default=MHF_Steve")));
        }
        catch (Exception lllIlIllIIllIl) {
            return false;
        }
    }

    static {
        ACCOUNT = "https://api.mojang.com";
        SESSION = "http://sessionserver.thealtening.com";
        SERVICES = "https://api.minecraftservices.com";
        AUTH = "http://authserver.thealtening.com";
        GSON = new Gson();
    }

    @Override
    public boolean login() {
        TheAlteningAccount lllIlIllIIIIIl;
        YggdrasilMinecraftSessionService lllIlIllIIIIll = (YggdrasilMinecraftSessionService)MinecraftClient.getInstance().getSessionService();
        AccountUtils.setBaseUrl(lllIlIllIIIIll, "http://sessionserver.thealtening.com/session/minecraft/");
        AccountUtils.setJoinUrl(lllIlIllIIIIll, "http://sessionserver.thealtening.com/session/minecraft/join");
        AccountUtils.setCheckUrl(lllIlIllIIIIll, "http://sessionserver.thealtening.com/session/minecraft/hasJoined");
        YggdrasilUserAuthentication lllIlIllIIIIlI = lllIlIllIIIIIl.getAuth();
        try {
            lllIlIllIIIIlI.logIn();
            lllIlIllIIIIIl.setSession(new Session(lllIlIllIIIIlI.getSelectedProfile().getName(), lllIlIllIIIIlI.getSelectedProfile().getId().toString(), lllIlIllIIIIlI.getAuthenticatedToken(), "mojang"));
            lllIlIllIIIIIl.cache.username = lllIlIllIIIIlI.getSelectedProfile().getName();
            return true;
        }
        catch (AuthenticationException lllIlIllIIIlIl) {
            MeteorClient.LOG.error("Failed to login with TheAltening.");
            return false;
        }
    }

    private YggdrasilUserAuthentication getAuth() {
        TheAlteningAccount lllIlIlIlllIIl;
        YggdrasilUserAuthentication lllIlIlIlllIlI = (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(((MinecraftClientAccessor)MinecraftClient.getInstance()).getProxy(), "", Environment.create((String)"http://authserver.thealtening.com", (String)"https://api.mojang.com", (String)"http://sessionserver.thealtening.com", (String)"https://api.minecraftservices.com", (String)"The Altening")).createUserAuthentication(Agent.MINECRAFT);
        lllIlIlIlllIlI.setUsername(lllIlIlIlllIIl.name);
        lllIlIlIlllIlI.setPassword("Meteor on Crack!");
        return lllIlIlIlllIlI;
    }
}

