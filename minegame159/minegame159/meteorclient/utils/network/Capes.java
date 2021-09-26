/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import minegame159.meteorclient.utils.network.HttpUtils;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;

public class Capes {
    private static final String CAPE_OWNERS_URL;
    private static final List<Cape> TO_REMOVE;
    private static final List<Cape> TO_REGISTER;
    private static final Map<String, String> URLS;
    private static final Map<UUID, String> OWNERS;
    private static final String CAPES_URL;
    private static final List<Cape> TO_RETRY;
    private static final Map<String, Cape> TEXTURES;

    private static void lambda$init$2(String string) {
        String[] stringArray = string.split(" ");
        if (stringArray.length >= 2 && !URLS.containsKey(stringArray[0])) {
            URLS.put(stringArray[0], stringArray[1]);
        }
    }

    private static void lambda$init$1() {
        HttpUtils.getLines("http://meteorclient.com/api/capeowners", Capes::lambda$init$0);
    }

    public static void init() {
        MeteorExecutor.execute(Capes::lambda$init$1);
        MeteorExecutor.execute(Capes::lambda$init$3);
    }

    private static void lambda$init$3() {
        HttpUtils.getLines("http://meteorclient.com/api/capes", Capes::lambda$init$2);
    }

    static List access$300() {
        return TO_REMOVE;
    }

    static List access$200() {
        return TO_RETRY;
    }

    static {
        CAPES_URL = "http://meteorclient.com/api/capes";
        CAPE_OWNERS_URL = "http://meteorclient.com/api/capeowners";
        OWNERS = new HashMap<UUID, String>();
        URLS = new HashMap<String, String>();
        TEXTURES = new HashMap<String, Cape>();
        TO_REGISTER = new ArrayList<Cape>();
        TO_RETRY = new ArrayList<Cape>();
        TO_REMOVE = new ArrayList<Cape>();
    }

    static List access$400() {
        return TO_REGISTER;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void tick() {
        List<Cape> list = TO_REGISTER;
        synchronized (list) {
            for (Cape cape : TO_REGISTER) {
                cape.register();
            }
            TO_REGISTER.clear();
        }
        list = TO_RETRY;
        synchronized (list) {
            TO_RETRY.removeIf(Cape::tick);
        }
        list = TO_REMOVE;
        synchronized (list) {
            Iterator<Cape> iterator = TO_REMOVE.iterator();
            while (true) {
                Cape cape;
                if (!iterator.hasNext()) {
                    TO_REMOVE.clear();
                    return;
                }
                cape = iterator.next();
                URLS.remove(Cape.access$000(cape));
                TEXTURES.remove(Cape.access$000(cape));
                TO_REGISTER.remove((Object)cape);
                TO_RETRY.remove((Object)cape);
            }
        }
    }

    private static void lambda$init$0(String string) {
        String[] stringArray = string.split(" ");
        if (stringArray.length >= 2) {
            OWNERS.put(UUID.fromString(stringArray[0]), stringArray[1]);
            if (!TEXTURES.containsKey(stringArray[1])) {
                TEXTURES.put(stringArray[1], new Cape(stringArray[1]));
            }
        }
    }

    public static Identifier get(PlayerEntity PlayerEntity2) {
        String string = OWNERS.get(PlayerEntity2.getUuid());
        if (string != null) {
            Cape cape = TEXTURES.get(string);
            if (cape == null) {
                return null;
            }
            if (cape.isDownloaded()) {
                return cape;
            }
            cape.download();
            return null;
        }
        return null;
    }

    static Map access$100() {
        return URLS;
    }

    private static class Cape
    extends Identifier {
        private NativeImage img;
        private final String name;
        private int retryTimer;
        private boolean downloaded;
        private boolean downloading;

        static String access$000(Cape cape) {
            return cape.name;
        }

        public void register() {
            MinecraftClient.getInstance().getTextureManager().registerTexture((Identifier)this, (AbstractTexture)new NativeImageBackedTexture(this.img));
            this.img = null;
            this.downloading = false;
            this.downloaded = true;
        }

        public boolean isDownloaded() {
            return this.downloaded;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void lambda$download$0() {
            var1_1 = (String)Capes.access$100().get(this.name);
            if (var1_1 != null) ** GOTO lbl-1000
            var2_2 = Capes.access$200();
            synchronized (var2_2) {
                Capes.access$300().add(this);
                this.downloading = false;
                return;
            }
lbl-1000:
            // 1 sources

            {
                var2_3 = HttpUtils.get(var1_1);
                if (var2_3 == null) {
                    var3_4 = Capes.access$200();
                    synchronized (var3_4) {
                        Capes.access$200().add(this);
                        this.retryTimer = 200;
                        this.downloading = false;
                        return;
                    }
                }
                this.img = NativeImage.read((InputStream)var2_3);
                var3_5 = Capes.access$400();
                synchronized (var3_5) {
                    Capes.access$400().add(this);
                    return;
                }
            }
        }

        public boolean tick() {
            if (this.retryTimer > 0) {
                --this.retryTimer;
            } else {
                this.download();
                return true;
            }
            return false;
        }

        public Cape(String string) {
            super("meteor-client", String.valueOf(new StringBuilder().append("capes/").append(string)));
            this.name = string;
        }

        public void download() {
            if (this.downloaded || this.downloading || this.retryTimer > 0) {
                return;
            }
            this.downloading = true;
            MeteorExecutor.execute(this::lambda$download$0);
        }

        public int compareTo(Object object) {
            return super.compareTo((Identifier)object);
        }
    }
}

