/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtIo
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
    private static final /* synthetic */ List<GuiTheme> themes;
    private static /* synthetic */ GuiTheme theme;
    private static final /* synthetic */ File FILE;
    private static final /* synthetic */ File THEMES_FOLDER;
    private static final /* synthetic */ File FOLDER;

    private static void saveGlobal() {
        try {
            NbtCompound llllIlllIlIl = new NbtCompound();
            llllIlllIlIl.putString("currentTheme", GuiThemes.get().name);
            FOLDER.mkdirs();
            NbtIo.write((NbtCompound)llllIlllIlIl, (File)FILE);
        }
        catch (IOException llllIlllIlII) {
            llllIlllIlII.printStackTrace();
        }
    }

    public static String[] getNames() {
        String[] llllIlllllIl = new String[themes.size()];
        for (int llllIllllllI = 0; llllIllllllI < themes.size(); ++llllIllllllI) {
            llllIlllllIl[llllIllllllI] = GuiThemes.themes.get((int)llllIllllllI).name;
        }
        return llllIlllllIl;
    }

    public static void postInit() {
        if (FILE.exists()) {
            try {
                NbtCompound lllllIIlIlll = NbtIo.read((File)FILE);
                if (lllllIIlIlll != null) {
                    GuiThemes.select(lllllIIlIlll.getString("currentTheme"));
                }
            }
            catch (IOException lllllIIlIllI) {
                lllllIIlIllI.printStackTrace();
            }
        }
        if (theme == null) {
            GuiThemes.select("Meteor");
        }
    }

    public static void add(GuiTheme lllllIIlIIII) {
        Iterator<GuiTheme> lllllIIlIIlI = themes.iterator();
        while (lllllIIlIIlI.hasNext()) {
            if (!lllllIIlIIlI.next().name.equals(lllllIIlIIII.name)) continue;
            lllllIIlIIlI.remove();
            MeteorClient.LOG.error("Theme with the name '{}' has already been added.", (Object)lllllIIlIIII.name);
            break;
        }
        themes.add(lllllIIlIIII);
    }

    public static void init() {
        GuiThemes.add(new MeteorGuiTheme());
    }

    public static void select(String lllllIIIIlII) {
        GuiTheme lllllIIIIlIl = null;
        for (GuiTheme lllllIIIlIlI : themes) {
            if (!lllllIIIlIlI.name.equals(lllllIIIIlII)) continue;
            lllllIIIIlIl = lllllIIIlIlI;
            break;
        }
        if (lllllIIIIlIl != null) {
            GuiThemes.saveTheme();
            theme = lllllIIIIlIl;
            try {
                NbtCompound lllllIIIlIIl;
                File lllllIIIlIII = new File(THEMES_FOLDER, String.valueOf(new StringBuilder().append(GuiThemes.get().name).append(".nbt")));
                if (lllllIIIlIII.exists() && (lllllIIIlIIl = NbtIo.read((File)lllllIIIlIII)) != null) {
                    GuiThemes.get().fromTag(lllllIIIlIIl);
                }
            }
            catch (IOException lllllIIIIlll) {
                lllllIIIIlll.printStackTrace();
            }
            GuiThemes.saveGlobal();
        }
    }

    private static void saveTheme() {
        if (GuiThemes.get() != null) {
            try {
                NbtCompound llllIllllIIl = GuiThemes.get().toTag();
                THEMES_FOLDER.mkdirs();
                NbtIo.write((NbtCompound)llllIllllIIl, (File)new File(THEMES_FOLDER, String.valueOf(new StringBuilder().append(GuiThemes.get().name).append(".nbt"))));
            }
            catch (IOException llllIllllIII) {
                llllIllllIII.printStackTrace();
            }
        }
    }

    static {
        FOLDER = new File(MeteorClient.FOLDER, "gui");
        THEMES_FOLDER = new File(FOLDER, "themes");
        FILE = new File(FOLDER, "gui.nbt");
        themes = new ArrayList<GuiTheme>();
    }

    public GuiThemes() {
        GuiThemes lllllIIllIlI;
    }

    public static void save() {
        GuiThemes.saveTheme();
        GuiThemes.saveGlobal();
    }

    public static GuiTheme get() {
        return theme;
    }
}

