/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 */
package minegame159.meteorclient.systems.accounts;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.misc.NbtException;
import minegame159.meteorclient.utils.render.ByteTexture;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;

public class AccountCache
implements ISerializable<AccountCache> {
    public /* synthetic */ String username;
    private /* synthetic */ ByteTexture headTexture;
    public /* synthetic */ String uuid;

    public boolean makeHead(String llllllllllllllllIllllIIIIIIllIll) {
        try {
            BufferedImage llllllllllllllllIllllIIIIIlIIIll;
            byte[] llllllllllllllllIllllIIIIIlIIIlI = new byte[192];
            int[] llllllllllllllllIllllIIIIIlIIIIl = new int[4];
            if (llllllllllllllllIllllIIIIIIllIll.equals("steve")) {
                BufferedImage llllllllllllllllIllllIIIIIlIIlll = ImageIO.read(MinecraftClient.getInstance().getResourceManager().getResource(new Identifier("meteor-client", "steve.png")).getInputStream());
            } else {
                llllllllllllllllIllllIIIIIlIIIll = ImageIO.read(new URL(llllllllllllllllIllllIIIIIIllIll));
            }
            int llllllllllllllllIllllIIIIIlIIIII = 0;
            for (int llllllllllllllllIllllIIIIIlIIlII = 0; llllllllllllllllIllllIIIIIlIIlII < 8; ++llllllllllllllllIllllIIIIIlIIlII) {
                for (int llllllllllllllllIllllIIIIIlIIlIl = 0; llllllllllllllllIllllIIIIIlIIlIl < 8; ++llllllllllllllllIllllIIIIIlIIlIl) {
                    llllllllllllllllIllllIIIIIlIIIll.getData().getPixel(llllllllllllllllIllllIIIIIlIIlII, llllllllllllllllIllllIIIIIlIIlIl, llllllllllllllllIllllIIIIIlIIIIl);
                    for (int llllllllllllllllIllllIIIIIlIIllI = 0; llllllllllllllllIllllIIIIIlIIllI < 3; ++llllllllllllllllIllllIIIIIlIIllI) {
                        llllllllllllllllIllllIIIIIlIIIlI[llllllllllllllllIllllIIIIIlIIIII] = (byte)llllllllllllllllIllllIIIIIlIIIIl[llllllllllllllllIllllIIIIIlIIllI];
                        ++llllllllllllllllIllllIIIIIlIIIII;
                    }
                }
            }
            llllllllllllllllIllllIIIIIIlllII.headTexture = new ByteTexture(8, 8, llllllllllllllllIllllIIIIIlIIIlI, ByteTexture.Format.RGB, ByteTexture.Filter.Nearest, ByteTexture.Filter.Nearest);
            return true;
        }
        catch (Exception llllllllllllllllIllllIIIIIIlllll) {
            MeteorClient.LOG.error(String.valueOf(new StringBuilder().append("Failed to read skin url. (").append(llllllllllllllllIllllIIIIIIllIll).append(")")));
            return false;
        }
    }

    @Override
    public AccountCache fromTag(NbtCompound llllllllllllllllIllllIIIIIIIlIII) {
        AccountCache llllllllllllllllIllllIIIIIIIlIIl;
        if (!llllllllllllllllIllllIIIIIIIlIII.contains("username") || !llllllllllllllllIllllIIIIIIIlIII.contains("uuid")) {
            throw new NbtException();
        }
        llllllllllllllllIllllIIIIIIIlIIl.username = llllllllllllllllIllllIIIIIIIlIII.getString("username");
        llllllllllllllllIllllIIIIIIIlIIl.uuid = llllllllllllllllIllllIIIIIIIlIII.getString("uuid");
        return llllllllllllllllIllllIIIIIIIlIIl;
    }

    public ByteTexture getHeadTexture() {
        AccountCache llllllllllllllllIllllIIIIIllIIIl;
        return llllllllllllllllIllllIIIIIllIIIl.headTexture;
    }

    public AccountCache() {
        AccountCache llllllllllllllllIllllIIIIIllIlIl;
        llllllllllllllllIllllIIIIIllIlIl.username = "";
        llllllllllllllllIllllIIIIIllIlIl.uuid = "";
    }

    @Override
    public NbtCompound toTag() {
        AccountCache llllllllllllllllIllllIIIIIIlIIIl;
        NbtCompound llllllllllllllllIllllIIIIIIlIIII = new NbtCompound();
        llllllllllllllllIllllIIIIIIlIIII.putString("username", llllllllllllllllIllllIIIIIIlIIIl.username);
        llllllllllllllllIllllIIIIIIlIIII.putString("uuid", llllllllllllllllIllllIIIIIIlIIIl.uuid);
        return llllllllllllllllIllllIIIIIIlIIII;
    }
}

