/*
 * Decompiled with CFR 0.151.
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AccountCache
implements ISerializable<AccountCache> {
    public String uuid = "";
    public String username = "";
    private ByteTexture headTexture;

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("username", this.username);
        NbtCompound2.putString("uuid", this.uuid);
        return NbtCompound2;
    }

    public boolean makeHead(String string) {
        try {
            byte[] byArray = new byte[192];
            int[] nArray = new int[4];
            BufferedImage bufferedImage = string.equals("steve") ? ImageIO.read(MinecraftClient.getInstance().getResourceManager().getResource(new Identifier("meteor-client", "steve.png")).getInputStream()) : ImageIO.read(new URL(string));
            int n = 0;
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    bufferedImage.getData().getPixel(i, j, nArray);
                    for (int k = 0; k < 3; ++k) {
                        byArray[n] = (byte)nArray[k];
                        ++n;
                        if (1 >= 0) continue;
                        return false;
                    }
                    if (2 != 0) continue;
                    return false;
                }
            }
            this.headTexture = new ByteTexture(8, 8, byArray, ByteTexture.Format.RGB, ByteTexture.Filter.Nearest, ByteTexture.Filter.Nearest);
            return true;
        }
        catch (Exception exception) {
            MeteorClient.LOG.error(String.valueOf(new StringBuilder().append("Failed to read skin url. (").append(string).append(")")));
            return false;
        }
    }

    public ByteTexture getHeadTexture() {
        return this.headTexture;
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @Override
    public AccountCache fromTag(NbtCompound NbtCompound2) {
        if (!NbtCompound2.contains("username") || !NbtCompound2.contains("uuid")) {
            throw new NbtException();
        }
        this.username = NbtCompound2.getString("username");
        this.uuid = NbtCompound2.getString("uuid");
        return this;
    }
}

