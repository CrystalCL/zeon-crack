/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 */
package minegame159.meteorclient.utils.network;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class HttpUtils {
    private static final /* synthetic */ Gson GSON;

    public static void getLines(String lllllllllllllllllIlIlIIllIIIIlll, Consumer<String> lllllllllllllllllIlIlIIllIIIlIII) {
        try {
            String lllllllllllllllllIlIlIIllIIIlIll;
            InputStream lllllllllllllllllIlIlIIllIIIllIl = HttpUtils.get(lllllllllllllllllIlIlIIllIIIIlll);
            if (lllllllllllllllllIlIlIIllIIIllIl == null) {
                return;
            }
            BufferedReader lllllllllllllllllIlIlIIllIIIllII = new BufferedReader(new InputStreamReader(lllllllllllllllllIlIlIIllIIIllIl));
            while ((lllllllllllllllllIlIlIIllIIIlIll = lllllllllllllllllIlIlIIllIIIllII.readLine()) != null) {
                lllllllllllllllllIlIlIIllIIIlIII.accept(lllllllllllllllllIlIlIIllIIIlIll);
            }
            lllllllllllllllllIlIlIIllIIIllII.close();
        }
        catch (IOException lllllllllllllllllIlIlIIllIIIlIlI) {
            lllllllllllllllllIlIlIIllIIIlIlI.printStackTrace();
        }
    }

    private static InputStream request(String lllllllllllllllllIlIlIIllIlIIIll, String lllllllllllllllllIlIlIIllIlIIIlI, String lllllllllllllllllIlIlIIllIlIIlII) {
        try {
            HttpURLConnection lllllllllllllllllIlIlIIllIlIlIIl = (HttpURLConnection)new URL(lllllllllllllllllIlIlIIllIlIIIlI).openConnection();
            lllllllllllllllllIlIlIIllIlIlIIl.setRequestMethod(lllllllllllllllllIlIlIIllIlIIIll);
            lllllllllllllllllIlIlIIllIlIlIIl.setConnectTimeout(2500);
            lllllllllllllllllIlIlIIllIlIlIIl.setReadTimeout(2500);
            lllllllllllllllllIlIlIIllIlIlIIl.setRequestProperty("User-Agent", "Meteor Client");
            if (lllllllllllllllllIlIlIIllIlIIlII != null) {
                byte[] lllllllllllllllllIlIlIIllIlIlIlI = lllllllllllllllllIlIlIIllIlIIlII.getBytes(StandardCharsets.UTF_8);
                lllllllllllllllllIlIlIIllIlIlIIl.setRequestProperty("Content-Length", Integer.toString(lllllllllllllllllIlIlIIllIlIlIlI.length));
                lllllllllllllllllIlIlIIllIlIlIIl.setDoOutput(true);
                lllllllllllllllllIlIlIIllIlIlIIl.getOutputStream().write(lllllllllllllllllIlIlIIllIlIlIlI);
            }
            return lllllllllllllllllIlIlIIllIlIlIIl.getInputStream();
        }
        catch (SocketTimeoutException lllllllllllllllllIlIlIIllIlIlIII) {
            return null;
        }
        catch (IOException lllllllllllllllllIlIlIIllIlIIlll) {
            lllllllllllllllllIlIlIIllIlIIlll.printStackTrace();
            return null;
        }
    }

    static {
        GSON = new Gson();
    }

    public static <T> T get(String lllllllllllllllllIlIlIIlIlllIlll, Type lllllllllllllllllIlIlIIlIllllIII) {
        try {
            InputStream lllllllllllllllllIlIlIIlIlllllIl = HttpUtils.get(lllllllllllllllllIlIlIIlIlllIlll);
            if (lllllllllllllllllIlIlIIlIlllllIl == null) {
                return null;
            }
            BufferedReader lllllllllllllllllIlIlIIlIlllllII = new BufferedReader(new InputStreamReader(lllllllllllllllllIlIlIIlIlllllIl));
            Object lllllllllllllllllIlIlIIlIllllIll = GSON.fromJson((Reader)lllllllllllllllllIlIlIIlIlllllII, lllllllllllllllllIlIlIIlIllllIII);
            lllllllllllllllllIlIlIIlIlllllII.close();
            return (T)lllllllllllllllllIlIlIIlIllllIll;
        }
        catch (IOException lllllllllllllllllIlIlIIlIllllIlI) {
            lllllllllllllllllIlIlIIlIllllIlI.printStackTrace();
            return null;
        }
    }

    public static InputStream get(String lllllllllllllllllIlIlIIllIIlllIl) {
        return HttpUtils.request("GET", lllllllllllllllllIlIlIIllIIlllIl, null);
    }

    public HttpUtils() {
        HttpUtils lllllllllllllllllIlIlIIllIllIIII;
    }

    public static InputStream post(String lllllllllllllllllIlIlIIllIIllIIl, String lllllllllllllllllIlIlIIllIIllIII) {
        return HttpUtils.request("POST", lllllllllllllllllIlIlIIllIIllIIl, lllllllllllllllllIlIlIIllIIllIII);
    }

    public static InputStream post(String lllllllllllllllllIlIlIIllIIlIIll) {
        return HttpUtils.post(lllllllllllllllllIlIlIIllIIlIIll, null);
    }
}

