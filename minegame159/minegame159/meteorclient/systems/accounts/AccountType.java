/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.accounts;

public final class AccountType
extends Enum<AccountType> {
    public static final /* enum */ AccountType TheAltening;
    public static final /* enum */ AccountType Cracked;
    private static final AccountType[] $VALUES;
    public static final /* enum */ AccountType Premium;

    public static AccountType[] values() {
        return (AccountType[])$VALUES.clone();
    }

    static {
        Cracked = new AccountType();
        Premium = new AccountType();
        TheAltening = new AccountType();
        $VALUES = AccountType.$values();
    }

    private static AccountType[] $values() {
        return new AccountType[]{Cracked, Premium, TheAltening};
    }

    public static AccountType valueOf(String string) {
        return Enum.valueOf(AccountType.class, string);
    }
}

