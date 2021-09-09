/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService
 */
package minegame159.meteorclient.systems.accounts;

import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

public class AccountUtils {
    public static void setCheckUrl(YggdrasilMinecraftSessionService llllllllllllllllIlIlIlIIllIllIIl, String llllllllllllllllIlIlIlIIllIllIII) {
        try {
            Field llllllllllllllllIlIlIlIIllIllIll = llllllllllllllllIlIlIlIIllIllIIl.getClass().getDeclaredField("checkUrl");
            llllllllllllllllIlIlIlIIllIllIll.setAccessible(true);
            llllllllllllllllIlIlIlIIllIllIll.set((Object)llllllllllllllllIlIlIlIIllIllIIl, new URL(llllllllllllllllIlIlIlIIllIllIII));
        }
        catch (IllegalAccessException | NoSuchFieldException | MalformedURLException llllllllllllllllIlIlIlIIllIllIlI) {
            llllllllllllllllIlIlIlIIllIllIlI.printStackTrace();
        }
    }

    public AccountUtils() {
        AccountUtils llllllllllllllllIlIlIlIIllllIIll;
    }

    public static void setBaseUrl(YggdrasilMinecraftSessionService llllllllllllllllIlIlIlIIlllIlIll, String llllllllllllllllIlIlIlIIlllIlIlI) {
        try {
            Field llllllllllllllllIlIlIlIIlllIllll = llllllllllllllllIlIlIlIIlllIlIll.getClass().getDeclaredField("baseUrl");
            llllllllllllllllIlIlIlIIlllIllll.setAccessible(true);
            llllllllllllllllIlIlIlIIlllIllll.set((Object)llllllllllllllllIlIlIlIIlllIlIll, llllllllllllllllIlIlIlIIlllIlIlI);
        }
        catch (IllegalAccessException | NoSuchFieldException llllllllllllllllIlIlIlIIlllIlllI) {
            llllllllllllllllIlIlIlIIlllIlllI.printStackTrace();
        }
    }

    public static void setJoinUrl(YggdrasilMinecraftSessionService llllllllllllllllIlIlIlIIlllIIIll, String llllllllllllllllIlIlIlIIlllIIIII) {
        try {
            Field llllllllllllllllIlIlIlIIlllIIlIl = llllllllllllllllIlIlIlIIlllIIIll.getClass().getDeclaredField("joinUrl");
            llllllllllllllllIlIlIlIIlllIIlIl.setAccessible(true);
            llllllllllllllllIlIlIlIIlllIIlIl.set((Object)llllllllllllllllIlIlIlIIlllIIIll, new URL(llllllllllllllllIlIlIlIIlllIIIII));
        }
        catch (IllegalAccessException | NoSuchFieldException | MalformedURLException llllllllllllllllIlIlIlIIlllIIlII) {
            llllllllllllllllIlIlIlIIlllIIlII.printStackTrace();
        }
    }
}

