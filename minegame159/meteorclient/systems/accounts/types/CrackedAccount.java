/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.util.Session
 */
package minegame159.meteorclient.systems.accounts.types;

import minegame159.meteorclient.systems.accounts.Account;
import minegame159.meteorclient.systems.accounts.AccountType;
import minegame159.meteorclient.systems.accounts.ProfileResponse;
import minegame159.meteorclient.utils.network.HttpUtils;
import net.minecraft.client.util.Session;

public class CrackedAccount
extends Account<CrackedAccount> {
    @Override
    public boolean login() {
        CrackedAccount lllllllllllllllllIlIlIlllIlIllll;
        super.login();
        lllllllllllllllllIlIlIlllIlIllll.setSession(new Session(lllllllllllllllllIlIlIlllIlIllll.name, "", "", "mojang"));
        return true;
    }

    @Override
    public boolean fetchHead() {
        CrackedAccount lllllllllllllllllIlIlIlllIllIIll;
        try {
            ProfileResponse lllllllllllllllllIlIlIlllIllIlIl = (ProfileResponse)HttpUtils.get(String.valueOf(new StringBuilder().append("https://api.mojang.com/users/profiles/minecraft/").append(lllllllllllllllllIlIlIlllIllIIll.cache.username)), ProfileResponse.class);
            return lllllllllllllllllIlIlIlllIllIIll.cache.makeHead(String.valueOf(new StringBuilder().append("https://crafatar.com/avatars/").append(lllllllllllllllllIlIlIlllIllIlIl.getId()).append("?size=8&overlay&default=MHF_Steve")));
        }
        catch (Exception lllllllllllllllllIlIlIlllIllIlII) {
            return lllllllllllllllllIlIlIlllIllIIll.cache.makeHead("steve");
        }
    }

    @Override
    public boolean fetchInfo() {
        CrackedAccount lllllllllllllllllIlIlIlllIlllIII;
        lllllllllllllllllIlIlIlllIlllIII.cache.username = lllllllllllllllllIlIlIlllIlllIII.name;
        return true;
    }

    public CrackedAccount(String lllllllllllllllllIlIlIlllIllllIl) {
        super(AccountType.Cracked, lllllllllllllllllIlIlIlllIllllIl);
        CrackedAccount lllllllllllllllllIlIlIlllIlllllI;
    }

    public boolean equals(Object lllllllllllllllllIlIlIlllIlIlIII) {
        CrackedAccount lllllllllllllllllIlIlIlllIlIlIIl;
        if (!(lllllllllllllllllIlIlIlllIlIlIII instanceof CrackedAccount)) {
            return false;
        }
        return ((CrackedAccount)lllllllllllllllllIlIlIlllIlIlIII).getUsername().equals(lllllllllllllllllIlIlIlllIlIlIIl.getUsername());
    }
}

