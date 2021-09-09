/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtIo
 */
package minegame159.meteorclient.systems;

import java.io.File;
import java.io.IOException;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.utils.files.StreamUtils;
import minegame159.meteorclient.utils.misc.ISerializable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;

public abstract class System<T>
implements ISerializable<T> {
    private /* synthetic */ File file;

    public File getFile() {
        System llllllllllllllllIllIlIIllIIlllIl;
        return llllllllllllllllIllIlIIllIIlllIl.file;
    }

    public void load(File llllllllllllllllIllIlIIllIlIIlll) {
        System llllllllllllllllIllIlIIllIlIlIII;
        File llllllllllllllllIllIlIIllIlIIllI = llllllllllllllllIllIlIIllIlIlIII.getFile();
        if (llllllllllllllllIllIlIIllIlIIllI == null) {
            return;
        }
        try {
            if (llllllllllllllllIllIlIIllIlIIlll != null) {
                llllllllllllllllIllIlIIllIlIIllI = new File(llllllllllllllllIllIlIIllIlIIlll, llllllllllllllllIllIlIIllIlIIllI.getName());
            }
            if (llllllllllllllllIllIlIIllIlIIllI.exists()) {
                llllllllllllllllIllIlIIllIlIlIII.fromTag(NbtIo.read((File)llllllllllllllllIllIlIIllIlIIllI));
            }
        }
        catch (IOException llllllllllllllllIllIlIIllIlIlIIl) {
            llllllllllllllllIllIlIIllIlIlIIl.printStackTrace();
        }
    }

    public void init() {
    }

    public System(String llllllllllllllllIllIlIIlllIIIlII) {
        System llllllllllllllllIllIlIIlllIIIIll;
        if (llllllllllllllllIllIlIIlllIIIlII != null) {
            llllllllllllllllIllIlIIlllIIIIll.file = new File(MeteorClient.FOLDER, String.valueOf(new StringBuilder().append(llllllllllllllllIllIlIIlllIIIlII).append(".nbt")));
        }
    }

    public void load() {
        System llllllllllllllllIllIlIIllIlIIIII;
        llllllllllllllllIllIlIIllIlIIIII.load(null);
    }

    @Override
    public NbtCompound toTag() {
        return null;
    }

    public void save() {
        System llllllllllllllllIllIlIIllIlIllll;
        llllllllllllllllIllIlIIllIlIllll.save(null);
    }

    @Override
    public T fromTag(NbtCompound llllllllllllllllIllIlIIllIIllIIl) {
        return null;
    }

    public void save(File llllllllllllllllIllIlIIllIlllIII) {
        System llllllllllllllllIllIlIIllIllIlIl;
        File llllllllllllllllIllIlIIllIllIlll = llllllllllllllllIllIlIIllIllIlIl.getFile();
        if (llllllllllllllllIllIlIIllIllIlll == null) {
            return;
        }
        NbtCompound llllllllllllllllIllIlIIllIllIllI = llllllllllllllllIllIlIIllIllIlIl.toTag();
        if (llllllllllllllllIllIlIIllIllIllI == null) {
            return;
        }
        try {
            File llllllllllllllllIllIlIIllIlllIll = File.createTempFile("meteor-client", llllllllllllllllIllIlIIllIllIlll.getName());
            NbtIo.write((NbtCompound)llllllllllllllllIllIlIIllIllIllI, (File)llllllllllllllllIllIlIIllIlllIll);
            if (llllllllllllllllIllIlIIllIlllIII != null) {
                llllllllllllllllIllIlIIllIllIlll = new File(llllllllllllllllIllIlIIllIlllIII, llllllllllllllllIllIlIIllIllIlll.getName());
            }
            llllllllllllllllIllIlIIllIllIlll.getParentFile().mkdirs();
            StreamUtils.copy(llllllllllllllllIllIlIIllIlllIll, llllllllllllllllIllIlIIllIllIlll);
            llllllllllllllllIllIlIIllIlllIll.delete();
        }
        catch (IOException llllllllllllllllIllIlIIllIlllIlI) {
            llllllllllllllllIllIlIIllIlllIlI.printStackTrace();
        }
    }
}

