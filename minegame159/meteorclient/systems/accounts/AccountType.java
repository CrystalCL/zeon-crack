/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.accounts;

public final class AccountType
extends Enum<AccountType> {
    private static final /* synthetic */ AccountType[] $VALUES;
    public static final /* synthetic */ /* enum */ AccountType Cracked;
    public static final /* synthetic */ /* enum */ AccountType Premium;
    public static final /* synthetic */ /* enum */ AccountType TheAltening;

    static {
        Cracked = new AccountType();
        Premium = new AccountType();
        TheAltening = new AccountType();
        $VALUES = AccountType.$values();
    }

    public static AccountType valueOf(String llllllllllllllllIllllIllIIlIIlIl) {
        return Enum.valueOf(AccountType.class, llllllllllllllllIllllIllIIlIIlIl);
    }

    private static /* synthetic */ AccountType[] $values() {
        return new AccountType[]{Cracked, Premium, TheAltening};
    }

    private AccountType() {
        AccountType llllllllllllllllIllllIllIIlIIIII;
    }

    public static AccountType[] values() {
        return (AccountType[])$VALUES.clone();
    }
}

