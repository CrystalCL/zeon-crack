/*
 * Decompiled with CFR 0.151.
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
    private static final Gson GSON = new Gson();

    public static InputStream get(String string) {
        return HttpUtils.request("GET", string, null);
    }

    public static InputStream post(String string, String string2) {
        return HttpUtils.request("POST", string, string2);
    }

    private static InputStream request(String string, String string2, String string3) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(string2).openConnection();
            httpURLConnection.setRequestMethod(string);
            httpURLConnection.setConnectTimeout(2500);
            httpURLConnection.setReadTimeout(2500);
            httpURLConnection.setRequestProperty("User-Agent", "Meteor Client");
            if (string3 != null) {
                byte[] byArray = string3.getBytes(StandardCharsets.UTF_8);
                httpURLConnection.setRequestProperty("Content-Length", Integer.toString(byArray.length));
                httpURLConnection.setDoOutput(true);
                httpURLConnection.getOutputStream().write(byArray);
            }
            return httpURLConnection.getInputStream();
        }
        catch (IOException | SocketTimeoutException iOException) {
            return null;
        }
    }

    public static InputStream post(String string) {
        return HttpUtils.post(string, null);
    }

    public static <T> T get(String string, Type type) {
        try {
            InputStream inputStream = HttpUtils.get(string);
            if (inputStream == null) {
                return null;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Object object = GSON.fromJson((Reader)bufferedReader, type);
            bufferedReader.close();
            return (T)object;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return null;
        }
    }

    public static void getLines(String string, Consumer<String> consumer) {
        try {
            String string2;
            InputStream inputStream = HttpUtils.get(string);
            if (inputStream == null) {
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((string2 = bufferedReader.readLine()) != null) {
                consumer.accept(string2);
            }
            bufferedReader.close();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}

