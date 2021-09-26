/*
 * Decompiled with CFR 0.151.
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
    private File file;

    public System(String string) {
        if (string != null) {
            this.file = new File(MeteorClient.FOLDER, String.valueOf(new StringBuilder().append(string).append(".nbt")));
        }
    }

    public void load(File file) {
        File file2 = this.getFile();
        if (file2 == null) {
            return;
        }
        try {
            if (file != null) {
                file2 = new File(file, file2.getName());
            }
            if (file2.exists()) {
                this.fromTag(NbtIo.read((File)file2));
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    @Override
    public T fromTag(NbtCompound NbtCompound2) {
        return null;
    }

    public void init() {
    }

    @Override
    public NbtCompound toTag() {
        return null;
    }

    public void load() {
        this.load(null);
    }

    public File getFile() {
        return this.file;
    }

    public void save(File file) {
        File file2 = this.getFile();
        if (file2 == null) {
            return;
        }
        NbtCompound NbtCompound2 = this.toTag();
        if (NbtCompound2 == null) {
            return;
        }
        try {
            File file3 = File.createTempFile("meteor-client", file2.getName());
            NbtIo.write((NbtCompound)NbtCompound2, (File)file3);
            if (file != null) {
                file2 = new File(file, file2.getName());
            }
            file2.getParentFile().mkdirs();
            StreamUtils.copy(file3, file2);
            file3.delete();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public void save() {
        this.save(null);
    }
}

