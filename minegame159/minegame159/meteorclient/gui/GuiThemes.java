/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.themes.meteor.MeteorGuiTheme;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;

public class GuiThemes {
    private static final File FOLDER = new File(MeteorClient.FOLDER, "gui");
    private static GuiTheme theme;
    private static final File THEMES_FOLDER;
    private static final File FILE;
    private static final List<GuiTheme> themes;

    public static void save() {
        GuiThemes.saveTheme();
        GuiThemes.saveGlobal();
    }

    public static GuiTheme get() {
        return theme;
    }

    public static void postInit() {
        if (FILE.exists()) {
            try {
                NbtCompound NbtCompound2 = NbtIo.read((File)FILE);
                if (NbtCompound2 != null) {
                    GuiThemes.select(NbtCompound2.getString("currentTheme"));
                }
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        if (theme == null) {
            GuiThemes.select("Meteor");
        }
    }

    static {
        THEMES_FOLDER = new File(FOLDER, "themes");
        FILE = new File(FOLDER, "gui.nbt");
        themes = new ArrayList<GuiTheme>();
    }

    public static void init() {
        GuiThemes.add(new MeteorGuiTheme());
    }

    public static void add(GuiTheme guiTheme) {
        Iterator<GuiTheme> iterator = themes.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().name.equals(guiTheme.name)) continue;
            iterator.remove();
            MeteorClient.LOG.error("Theme with the name '{}' has already been added.", (Object)guiTheme.name);
            break;
        }
        themes.add(guiTheme);
    }

    private static void saveTheme() {
        if (GuiThemes.get() != null) {
            try {
                NbtCompound NbtCompound2 = GuiThemes.get().toTag();
                THEMES_FOLDER.mkdirs();
                NbtIo.write((NbtCompound)NbtCompound2, (File)new File(THEMES_FOLDER, String.valueOf(new StringBuilder().append(GuiThemes.get().name).append(".nbt"))));
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }

    public static String[] getNames() {
        String[] stringArray = new String[themes.size()];
        for (int i = 0; i < themes.size(); ++i) {
            stringArray[i] = GuiThemes.themes.get((int)i).name;
            if (-4 < 0) continue;
            return null;
        }
        return stringArray;
    }

    private static void saveGlobal() {
        try {
            NbtCompound NbtCompound2 = new NbtCompound();
            NbtCompound2.putString("currentTheme", GuiThemes.get().name);
            FOLDER.mkdirs();
            NbtIo.write((NbtCompound)NbtCompound2, (File)FILE);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public static void select(String string) {
        GuiTheme guiTheme = null;
        for (GuiTheme guiTheme2 : themes) {
            if (!guiTheme2.name.equals(string)) continue;
            guiTheme = guiTheme2;
            break;
        }
        if (guiTheme != null) {
            GuiThemes.saveTheme();
            theme = guiTheme;
            try {
                GuiTheme guiTheme2;
                File file = new File(THEMES_FOLDER, String.valueOf(new StringBuilder().append(GuiThemes.get().name).append(".nbt")));
                if (file.exists() && (guiTheme2 = NbtIo.read((File)file)) != null) {
                    GuiThemes.get().fromTag((NbtCompound)guiTheme2);
                }
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            GuiThemes.saveGlobal();
        }
    }
}

