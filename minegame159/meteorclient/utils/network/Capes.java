/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.texture.NativeImage
 *  net.minecraft.client.texture.NativeImageBackedTexture
 *  net.minecraft.client.texture.AbstractTexture
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 */
package minegame159.meteorclient.utils.network;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
    private static final /* synthetic */ List<Cape> TO_REGISTER;
    private static final /* synthetic */ Map<UUID, String> OWNERS;
    private static final /* synthetic */ String CAPE_OWNERS_URL;
    private static final /* synthetic */ List<Cape> TO_REMOVE;
    private static final /* synthetic */ String CAPES_URL;
    private static final /* synthetic */ Map<String, Cape> TEXTURES;
    private static final /* synthetic */ List<Cape> TO_RETRY;
    private static final /* synthetic */ Map<String, String> URLS;

    public static void init() {
        MeteorExecutor.execute(() -> HttpUtils.getLines("http://meteorclient.com/api/capeowners", lllllllllllllllllIIIlIlllIIIllII -> {
            String[] lllllllllllllllllIIIlIlllIIIllIl = lllllllllllllllllIIIlIlllIIIllII.split(" ");
            if (lllllllllllllllllIIIlIlllIIIllIl.length >= 2) {
                OWNERS.put(UUID.fromString(lllllllllllllllllIIIlIlllIIIllIl[0]), lllllllllllllllllIIIlIlllIIIllIl[1]);
                if (!TEXTURES.containsKey(lllllllllllllllllIIIlIlllIIIllIl[1])) {
                    TEXTURES.put(lllllllllllllllllIIIlIlllIIIllIl[1], new Cape(lllllllllllllllllIIIlIlllIIIllIl[1]));
                }
            }
        }));
        MeteorExecutor.execute(() -> HttpUtils.getLines("http://meteorclient.com/api/capes", lllllllllllllllllIIIlIlllIIlIIll -> {
            String[] lllllllllllllllllIIIlIlllIIllIlI = lllllllllllllllllIIIlIlllIIlIIll.split(" ");
            if (lllllllllllllllllIIIlIlllIIllIlI.length >= 2 && !URLS.containsKey(lllllllllllllllllIIIlIlllIIllIlI[0])) {
                URLS.put(lllllllllllllllllIIIlIlllIIllIlI[0], lllllllllllllllllIIIlIlllIIllIlI[1]);
            }
        }));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void tick() {
        List<Cape> lllllllllllllllllIIIlIlllIlIlIII = TO_REGISTER;
        synchronized (lllllllllllllllllIIIlIlllIlIlIII) {
            for (Cape lllllllllllllllllIIIlIlllIlIllII : TO_REGISTER) {
                lllllllllllllllllIIIlIlllIlIllII.register();
            }
            TO_REGISTER.clear();
        }
        lllllllllllllllllIIIlIlllIlIlIII = TO_RETRY;
        synchronized (lllllllllllllllllIIIlIlllIlIlIII) {
            TO_RETRY.removeIf(Cape::tick);
        }
        lllllllllllllllllIIIlIlllIlIlIII = TO_REMOVE;
        synchronized (lllllllllllllllllIIIlIlllIlIlIII) {
            for (Cape lllllllllllllllllIIIlIlllIlIlIIl : TO_REMOVE) {
                URLS.remove(lllllllllllllllllIIIlIlllIlIlIIl.name);
                TEXTURES.remove(lllllllllllllllllIIIlIlllIlIlIIl.name);
                TO_REGISTER.remove((Object)lllllllllllllllllIIIlIlllIlIlIIl);
                TO_RETRY.remove((Object)lllllllllllllllllIIIlIlllIlIlIIl);
            }
            TO_REMOVE.clear();
        }
    }

    public static Identifier get(PlayerEntity lllllllllllllllllIIIlIlllIllllll) {
        String lllllllllllllllllIIIlIlllIlllllI = OWNERS.get(lllllllllllllllllIIIlIlllIllllll.getUuid());
        if (lllllllllllllllllIIIlIlllIlllllI != null) {
            Cape lllllllllllllllllIIIlIllllIIIIII = TEXTURES.get(lllllllllllllllllIIIlIlllIlllllI);
            if (lllllllllllllllllIIIlIllllIIIIII == null) {
                return null;
            }
            if (lllllllllllllllllIIIlIllllIIIIII.isDownloaded()) {
                return lllllllllllllllllIIIlIllllIIIIII;
            }
            lllllllllllllllllIIIlIllllIIIIII.download();
            return null;
        }
        return null;
    }

    static {
        CAPE_OWNERS_URL = "http://meteorclient.com/api/capeowners";
        CAPES_URL = "http://meteorclient.com/api/capes";
        OWNERS = new HashMap<UUID, String>();
        URLS = new HashMap<String, String>();
        TEXTURES = new HashMap<String, Cape>();
        TO_REGISTER = new ArrayList<Cape>();
        TO_RETRY = new ArrayList<Cape>();
        TO_REMOVE = new ArrayList<Cape>();
    }

    public Capes() {
        Capes lllllllllllllllllIIIlIllllIIIlII;
    }

    private static class Cape
    extends Identifier {
        private /* synthetic */ NativeImage img;
        private /* synthetic */ boolean downloaded;
        private /* synthetic */ int retryTimer;
        private final /* synthetic */ String name;
        private /* synthetic */ boolean downloading;

        public Cape(String llIIIIIIlllIIIl) {
            super("meteor-client", String.valueOf(new StringBuilder().append("capes/").append(llIIIIIIlllIIIl)));
            Cape llIIIIIIlllIIII;
            llIIIIIIlllIIII.name = llIIIIIIlllIIIl;
        }

        public boolean tick() {
            Cape llIIIIIIllIIllI;
            if (llIIIIIIllIIllI.retryTimer > 0) {
                --llIIIIIIllIIllI.retryTimer;
            } else {
                llIIIIIIllIIllI.download();
                return true;
            }
            return false;
        }

        public void download() {
            Cape llIIIIIIllIllIl;
            if (llIIIIIIllIllIl.downloaded || llIIIIIIllIllIl.downloading || llIIIIIIllIllIl.retryTimer > 0) {
                return;
            }
            llIIIIIIllIllIl.downloading = true;
            MeteorExecutor.execute(() -> {
                try {
                    Cape llIIIIIIlIlIlII;
                    String llIIIIIIlIlIlll = (String)URLS.get(llIIIIIIlIlIlII.name);
                    if (llIIIIIIlIlIlll == null) {
                        List llIIIIIIlIlIIIl = TO_RETRY;
                        synchronized (llIIIIIIlIlIIIl) {
                            TO_REMOVE.add(llIIIIIIlIlIlII);
                            llIIIIIIlIlIlII.downloading = false;
                            return;
                        }
                    }
                    InputStream llIIIIIIlIlIllI = HttpUtils.get(llIIIIIIlIlIlll);
                    if (llIIIIIIlIlIllI == null) {
                        List llIIIIIIlIlIIII = TO_RETRY;
                        synchronized (llIIIIIIlIlIIII) {
                            TO_RETRY.add(llIIIIIIlIlIlII);
                            llIIIIIIlIlIlII.retryTimer = 200;
                            llIIIIIIlIlIlII.downloading = false;
                            return;
                        }
                    }
                    llIIIIIIlIlIlII.img = NativeImage.read((InputStream)llIIIIIIlIlIllI);
                    List llIIIIIIlIlIIII = TO_REGISTER;
                    synchronized (llIIIIIIlIlIIII) {
                        TO_REGISTER.add(llIIIIIIlIlIlII);
                    }
                }
                catch (IOException llIIIIIIlIlIlIl) {
                    llIIIIIIlIlIlIl.printStackTrace();
                }
            });
        }

        public boolean isDownloaded() {
            Cape llIIIIIIllIIlII;
            return llIIIIIIllIIlII.downloaded;
        }

        public void register() {
            Cape llIIIIIIllIlIlI;
            MinecraftClient.getInstance().getTextureManager().registerTexture((Identifier)llIIIIIIllIlIlI, (AbstractTexture)new NativeImageBackedTexture(llIIIIIIllIlIlI.img));
            llIIIIIIllIlIlI.img = null;
            llIIIIIIllIlIlI.downloading = false;
            llIIIIIIllIlIlI.downloaded = true;
        }
    }
}

