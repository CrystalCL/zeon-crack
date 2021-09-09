/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  baritone.api.Settings
 *  baritone.api.Settings$Setting
 *  baritone.api.utils.SettingsUtil
 *  net.minecraft.client.gui.screen.Screen
 */
package minegame159.meteorclient.gui.tabs.builtin;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import baritone.api.utils.SettingsUtil;
import java.awt.Color;
import java.lang.reflect.Field;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.tabs.Tab;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.tabs.WindowTabScreen;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.Settings;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.client.gui.screen.Screen;

public class BaritoneTab
extends Tab {
    private static /* synthetic */ Settings settings;

    @Override
    public boolean isScreen(Screen llllllllllllllllllIIIlllIlIlIlll) {
        return llllllllllllllllllIIIlllIlIlIlll instanceof BaritoneScreen;
    }

    public BaritoneTab() {
        super("Baritone");
        BaritoneTab llllllllllllllllllIIIlllIllIIIIl;
    }

    private static Settings getSettings() {
        if (settings != null) {
            return settings;
        }
        settings = new Settings();
        SettingGroup llllllllllllllllllIIIlllIlIIIIlI = settings.createGroup("Checkboxes");
        SettingGroup llllllllllllllllllIIIlllIlIIIIIl = settings.createGroup("Numbers");
        SettingGroup llllllllllllllllllIIIlllIlIIIIII = settings.createGroup("Whole Numbers");
        SettingGroup llllllllllllllllllIIIlllIIllllll = settings.createGroup("Colors");
        try {
            Class<?> llllllllllllllllllIIIlllIlIIIlII = BaritoneAPI.getSettings().getClass();
            for (Field llllllllllllllllllIIIlllIlIIIlIl : llllllllllllllllllIIIlllIlIIIlII.getDeclaredFields()) {
                Object llllllllllllllllllIIIlllIlIIlIII = llllllllllllllllllIIIlllIlIIIlIl.get((Object)BaritoneAPI.getSettings());
                if (!(llllllllllllllllllIIIlllIlIIlIII instanceof Settings.Setting)) continue;
                Settings.Setting llllllllllllllllllIIIlllIlIIIlll = (Settings.Setting)llllllllllllllllllIIIlllIlIIlIII;
                Object llllllllllllllllllIIIlllIlIIIllI = llllllllllllllllllIIIlllIlIIIlll.value;
                if (llllllllllllllllllIIIlllIlIIIllI instanceof Boolean) {
                    llllllllllllllllllIIIlllIlIIIIlI.add(new BoolSetting.Builder().name(llllllllllllllllllIIIlllIlIIIlll.getName()).description(llllllllllllllllllIIIlllIlIIIlll.getName()).defaultValue((Boolean)llllllllllllllllllIIIlllIlIIIlll.defaultValue).onChanged(llllllllllllllllllIIIllIlllIlIlI -> {
                        llllllllllllllllllIIIllIlllIlIll.value = llllllllllllllllllIIIllIlllIlIlI;
                    }).onModuleActivated(llllllllllllllllllIIIllIllllIIlI -> llllllllllllllllllIIIllIllllIIlI.set((Boolean)llllllllllllllllllIIIllIllllIIIl.value)).build());
                    continue;
                }
                if (llllllllllllllllllIIIlllIlIIIllI instanceof Double) {
                    llllllllllllllllllIIIlllIlIIIIIl.add(new DoubleSetting.Builder().name(llllllllllllllllllIIIlllIlIIIlll.getName()).description(llllllllllllllllllIIIlllIlIIIlll.getName()).defaultValue((Double)llllllllllllllllllIIIlllIlIIIlll.defaultValue).onChanged(llllllllllllllllllIIIllIllllIllI -> {
                        llllllllllllllllllIIIllIlllllIIl.value = llllllllllllllllllIIIllIllllIllI;
                    }).onModuleActivated(llllllllllllllllllIIIllIlllllllI -> llllllllllllllllllIIIllIlllllllI.set((Double)llllllllllllllllllIIIllIllllllIl.value)).build());
                    continue;
                }
                if (llllllllllllllllllIIIlllIlIIIllI instanceof Float) {
                    llllllllllllllllllIIIlllIlIIIIIl.add(new DoubleSetting.Builder().name(llllllllllllllllllIIIlllIlIIIlll.getName()).description(llllllllllllllllllIIIlllIlIIIlll.getName()).defaultValue(((Float)llllllllllllllllllIIIlllIlIIIlll.defaultValue).doubleValue()).onChanged(llllllllllllllllllIIIlllIIIIIlII -> {
                        llllllllllllllllllIIIlllIIIIIlIl.value = Float.valueOf(llllllllllllllllllIIIlllIIIIIlII.floatValue());
                    }).onModuleActivated(llllllllllllllllllIIIlllIIIIlIlI -> llllllllllllllllllIIIlllIIIIlIlI.set(((Float)llllllllllllllllllIIIlllIIIIlIIl.value).doubleValue())).build());
                    continue;
                }
                if (llllllllllllllllllIIIlllIlIIIllI instanceof Integer) {
                    llllllllllllllllllIIIlllIlIIIIII.add(new IntSetting.Builder().name(llllllllllllllllllIIIlllIlIIIlll.getName()).description(llllllllllllllllllIIIlllIlIIIlll.getName()).defaultValue((Integer)llllllllllllllllllIIIlllIlIIIlll.defaultValue).onChanged(llllllllllllllllllIIIlllIIIIlllI -> {
                        llllllllllllllllllIIIlllIIIlIIIl.value = llllllllllllllllllIIIlllIIIIlllI;
                    }).onModuleActivated(llllllllllllllllllIIIlllIIIlIlII -> llllllllllllllllllIIIlllIIIlIlII.set((Integer)llllllllllllllllllIIIlllIIIlIlIl.value)).build());
                    continue;
                }
                if (llllllllllllllllllIIIlllIlIIIllI instanceof Long) {
                    llllllllllllllllllIIIlllIlIIIIII.add(new IntSetting.Builder().name(llllllllllllllllllIIIlllIlIIIlll.getName()).description(llllllllllllllllllIIIlllIlIIIlll.getName()).defaultValue(((Long)llllllllllllllllllIIIlllIlIIIlll.defaultValue).intValue()).onChanged(llllllllllllllllllIIIlllIIIlllII -> {
                        llllllllllllllllllIIIlllIIIlllIl.value = llllllllllllllllllIIIlllIIIlllII.longValue();
                    }).onModuleActivated(llllllllllllllllllIIIlllIIlIIIlI -> llllllllllllllllllIIIlllIIlIIIlI.set(((Long)llllllllllllllllllIIIlllIIlIIIll.value).intValue())).build());
                    continue;
                }
                if (!(llllllllllllllllllIIIlllIlIIIllI instanceof Color)) continue;
                Color llllllllllllllllllIIIlllIlIIlIIl = (Color)llllllllllllllllllIIIlllIlIIIlll.value;
                llllllllllllllllllIIIlllIIllllll.add(new ColorSetting.Builder().name(llllllllllllllllllIIIlllIlIIIlll.getName()).description(llllllllllllllllllIIIlllIlIIIlll.getName()).defaultValue(new SettingColor(llllllllllllllllllIIIlllIlIIlIIl.getRed(), llllllllllllllllllIIIlllIlIIlIIl.getGreen(), llllllllllllllllllIIIlllIlIIlIIl.getBlue(), llllllllllllllllllIIIlllIlIIlIIl.getAlpha())).onChanged(llllllllllllllllllIIIlllIIlIIllI -> {
                    llllllllllllllllllIIIlllIIlIIlll.value = new Color(llllllllllllllllllIIIlllIIlIIllI.r, llllllllllllllllllIIIlllIIlIIllI.g, llllllllllllllllllIIIlllIIlIIllI.b, llllllllllllllllllIIIlllIIlIIllI.a);
                }).onModuleActivated(llllllllllllllllllIIIlllIIlIllII -> llllllllllllllllllIIIlllIIlIllII.set(new SettingColor(llllllllllllllllllIIIlllIlIIlIIl.getRed(), llllllllllllllllllIIIlllIlIIlIIl.getGreen(), llllllllllllllllllIIIlllIlIIlIIl.getBlue(), llllllllllllllllllIIIlllIlIIlIIl.getAlpha()))).build());
            }
        }
        catch (IllegalAccessException llllllllllllllllllIIIlllIlIIIIll) {
            llllllllllllllllllIIIlllIlIIIIll.printStackTrace();
        }
        return settings;
    }

    @Override
    public TabScreen createScreen(GuiTheme llllllllllllllllllIIIlllIlIllIll) {
        BaritoneTab llllllllllllllllllIIIlllIlIlllII;
        return new BaritoneScreen(llllllllllllllllllIIIlllIlIllIll, llllllllllllllllllIIIlllIlIlllII);
    }

    private static class BaritoneScreen
    extends WindowTabScreen {
        @Override
        protected void onClosed() {
            SettingsUtil.save((baritone.api.Settings)BaritoneAPI.getSettings());
        }

        public BaritoneScreen(GuiTheme lllllllllllllllllIIIllllIIlllIIl, Tab lllllllllllllllllIIIllllIIlllIII) {
            super(lllllllllllllllllIIIllllIIlllIIl, lllllllllllllllllIIIllllIIlllIII);
            BaritoneScreen lllllllllllllllllIIIllllIIllIllI;
            WTextBox lllllllllllllllllIIIllllIIllIlll = lllllllllllllllllIIIllllIIllIllI.add(lllllllllllllllllIIIllllIIlllIIl.textBox("")).minWidth(400.0).expandX().widget();
            lllllllllllllllllIIIllllIIllIlll.setFocused(true);
            lllllllllllllllllIIIllllIIllIlll.action = () -> {
                BaritoneScreen lllllllllllllllllIIIllllIIlIIlIl;
                lllllllllllllllllIIIllllIIlIIlIl.clear();
                lllllllllllllllllIIIllllIIlIIlIl.add(lllllllllllllllllIIIllllIIllIlll);
                lllllllllllllllllIIIllllIIlIIlIl.add(lllllllllllllllllIIIllllIIlllIIl.settings(BaritoneTab.getSettings(), lllllllllllllllllIIIllllIIllIlll.get().trim())).expandX();
            };
            BaritoneTab.getSettings().onActivated();
            lllllllllllllllllIIIllllIIllIllI.add(lllllllllllllllllIIIllllIIlllIIl.settings(BaritoneTab.getSettings(), lllllllllllllllllIIIllllIIllIlll.get().trim())).expandX();
        }
    }
}

