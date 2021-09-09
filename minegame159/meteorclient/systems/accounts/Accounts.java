/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtElement
 */
package minegame159.meteorclient.systems.accounts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import minegame159.meteorclient.systems.System;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.accounts.Account;
import minegame159.meteorclient.systems.accounts.AccountType;
import minegame159.meteorclient.systems.accounts.types.CrackedAccount;
import minegame159.meteorclient.systems.accounts.types.PremiumAccount;
import minegame159.meteorclient.systems.accounts.types.TheAlteningAccount;
import minegame159.meteorclient.utils.misc.NbtException;
import minegame159.meteorclient.utils.misc.NbtUtils;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class Accounts
extends System<Accounts>
implements Iterable<Account<?>> {
    private /* synthetic */ List<Account<?>> accounts;

    public Accounts() {
        super("accounts");
        Accounts llllIIllIlII;
        llllIIllIlII.accounts = new ArrayList();
    }

    public void remove(Account<?> llllIIlIIIIl) {
        Accounts llllIIlIIIlI;
        if (llllIIlIIIlI.accounts.remove(llllIIlIIIIl)) {
            llllIIlIIIlI.save();
        }
    }

    @Override
    public Iterator<Account<?>> iterator() {
        Accounts llllIIIllIll;
        return llllIIIllIll.accounts.iterator();
    }

    public static Accounts get() {
        return Systems.get(Accounts.class);
    }

    public int size() {
        Accounts llllIIIlllll;
        return llllIIIlllll.accounts.size();
    }

    @Override
    public Accounts fromTag(NbtCompound llllIIIlIIIl) {
        Accounts llllIIIlIIII;
        MeteorExecutor.execute(() -> {
            llllIIIIIlIl.accounts = NbtUtils.listFromTag(llllIIIlIIIl.getList("accounts", 10), lllIllllllIl -> {
                NbtCompound lllIllllllII = (NbtCompound)lllIllllllIl;
                if (!lllIllllllII.contains("type")) {
                    return null;
                }
                AccountType lllIlllllIll = AccountType.valueOf(lllIllllllII.getString("type"));
                try {
                    Object lllIllllllll = null;
                    if (lllIlllllIll == AccountType.Cracked) {
                        lllIllllllll = new CrackedAccount(null).fromTag(lllIllllllII);
                    } else if (lllIlllllIll == AccountType.Premium) {
                        lllIllllllll = new PremiumAccount(null, null).fromTag(lllIllllllII);
                    } else if (lllIlllllIll == AccountType.TheAltening) {
                        lllIllllllll = new TheAlteningAccount(null).fromTag(lllIllllllII);
                    }
                    if (((Account)lllIllllllll).fetchHead()) {
                        return lllIllllllll;
                    }
                }
                catch (NbtException lllIlllllllI) {
                    return null;
                }
                return null;
            });
        });
        return llllIIIlIIII;
    }

    @Override
    public NbtCompound toTag() {
        Accounts llllIIIlIllI;
        NbtCompound llllIIIlIlll = new NbtCompound();
        llllIIIlIlll.put("accounts", (NbtElement)NbtUtils.listToTag(llllIIIlIllI.accounts));
        return llllIIIlIlll;
    }

    public boolean exists(Account<?> llllIIlIlIIl) {
        Accounts llllIIlIlIlI;
        return llllIIlIlIlI.accounts.contains(llllIIlIlIIl);
    }

    @Override
    public void init() {
    }

    public void add(Account<?> llllIIlIllll) {
        Accounts llllIIllIIII;
        llllIIllIIII.accounts.add(llllIIlIllll);
        llllIIllIIII.save();
    }
}

