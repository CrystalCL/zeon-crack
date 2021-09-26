/*
 * Decompiled with CFR 0.151.
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Accounts
extends System<Accounts>
implements Iterable<Account<?>> {
    private List<Account<?>> accounts = new ArrayList();

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    public void add(Account<?> account) {
        this.accounts.add(account);
        this.save();
    }

    public Accounts() {
        super("accounts");
    }

    public static Accounts get() {
        return Systems.get(Accounts.class);
    }

    @Override
    public Iterator<Account<?>> iterator() {
        return this.accounts.iterator();
    }

    public boolean exists(Account<?> account) {
        return this.accounts.contains(account);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.put("accounts", (NbtElement)NbtUtils.listToTag(this.accounts));
        return NbtCompound2;
    }

    private void lambda$fromTag$1(NbtCompound NbtCompound2) {
        this.accounts = NbtUtils.listFromTag(NbtCompound2.getList("accounts", 10), Accounts::lambda$fromTag$0);
    }

    @Override
    public Accounts fromTag(NbtCompound NbtCompound2) {
        MeteorExecutor.execute(() -> this.lambda$fromTag$1(NbtCompound2));
        return this;
    }

    @Override
    public void init() {
    }

    public void remove(Account<?> account) {
        if (this.accounts.remove(account)) {
            this.save();
        }
    }

    private static Account lambda$fromTag$0(NbtElement NbtElement2) {
        NbtCompound NbtCompound2 = (NbtCompound)NbtElement2;
        if (!NbtCompound2.contains("type")) {
            return null;
        }
        AccountType accountType = AccountType.valueOf(NbtCompound2.getString("type"));
        try {
            Object object = null;
            if (accountType == AccountType.Cracked) {
                object = new CrackedAccount(null).fromTag(NbtCompound2);
            } else if (accountType == AccountType.Premium) {
                object = new PremiumAccount(null, null).fromTag(NbtCompound2);
            } else if (accountType == AccountType.TheAltening) {
                object = new TheAlteningAccount(null).fromTag(NbtCompound2);
            }
            if (((Account)object).fetchHead()) {
                return object;
            }
        }
        catch (NbtException nbtException) {
            return null;
        }
        return null;
    }

    public int size() {
        return this.accounts.size();
    }
}

