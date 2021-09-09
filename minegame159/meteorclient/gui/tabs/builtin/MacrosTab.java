/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screen.Screen
 */
package minegame159.meteorclient.gui.tabs.builtin;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.meteor.KeyEvent;
import minegame159.meteorclient.events.meteor.MouseButtonEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.renderer.GuiRenderer;
import minegame159.meteorclient.gui.tabs.Tab;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.tabs.WindowTabScreen;
import minegame159.meteorclient.gui.widgets.WKeybind;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.gui.widgets.pressable.WMinus;
import minegame159.meteorclient.gui.widgets.pressable.WPlus;
import minegame159.meteorclient.systems.macros.Macro;
import minegame159.meteorclient.systems.macros.Macros;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.client.gui.screen.Screen;

public class MacrosTab
extends Tab {
    @Override
    public TabScreen createScreen(GuiTheme lIIIIIIlIIl) {
        MacrosTab lIIIIIIllII;
        return new MacrosScreen(lIIIIIIlIIl, lIIIIIIllII);
    }

    @Override
    public boolean isScreen(Screen lIIIIIIIllI) {
        return lIIIIIIIllI instanceof MacrosScreen;
    }

    public MacrosTab() {
        super("Macros");
        MacrosTab lIIIIIlIIII;
    }

    private static class MacroEditorScreen
    extends WindowScreen {
        private final /* synthetic */ Macro macro;
        private /* synthetic */ WKeybind keybind;
        private /* synthetic */ boolean binding;
        private final /* synthetic */ boolean isNewMacro;

        private void fillMessagesTable(WTable llllllllllllllllllIIllIlIlIlIllI) {
            MacroEditorScreen llllllllllllllllllIIllIlIlIllIIl;
            if (llllllllllllllllllIIllIlIlIllIIl.macro.messages.isEmpty()) {
                llllllllllllllllllIIllIlIlIllIIl.macro.addMessage("");
            }
            for (int llllllllllllllllllIIllIlIlIllIlI = 0; llllllllllllllllllIIllIlIlIllIlI < llllllllllllllllllIIllIlIlIllIIl.macro.messages.size(); ++llllllllllllllllllIIllIlIlIllIlI) {
                int llllllllllllllllllIIllIlIlIlllII = llllllllllllllllllIIllIlIlIllIlI;
                WTextBox llllllllllllllllllIIllIlIlIllIll = llllllllllllllllllIIllIlIlIlIllI.add(llllllllllllllllllIIllIlIlIllIIl.theme.textBox(llllllllllllllllllIIllIlIlIllIIl.macro.messages.get(llllllllllllllllllIIllIlIlIllIlI))).minWidth(400.0).expandX().widget();
                llllllllllllllllllIIllIlIlIllIll.action = () -> {
                    MacroEditorScreen llllllllllllllllllIIllIlIIllIIII;
                    llllllllllllllllllIIllIlIIllIIII.macro.messages.set(llllllllllllllllllIIllIlIlIlllII, llllllllllllllllllIIllIlIlIllIll.get().trim());
                };
                if (llllllllllllllllllIIllIlIlIllIlI != llllllllllllllllllIIllIlIlIllIIl.macro.messages.size() - 1) {
                    WMinus llllllllllllllllllIIllIlIlIllllI = llllllllllllllllllIIllIlIlIlIllI.add(llllllllllllllllllIIllIlIlIllIIl.theme.minus()).widget();
                    llllllllllllllllllIIllIlIlIllllI.action = () -> {
                        MacroEditorScreen llllllllllllllllllIIllIlIIllIlIl;
                        llllllllllllllllllIIllIlIIllIlIl.macro.removeMessage(llllllllllllllllllIIllIlIlIlllII);
                        llllllllllllllllllIIllIlIIllIlIl.clear();
                        llllllllllllllllllIIllIlIIllIlIl.initWidgets(llllllllllllllllllIIllIlIIllIlIl.macro);
                    };
                } else {
                    WPlus llllllllllllllllllIIllIlIlIlllIl = llllllllllllllllllIIllIlIlIlIllI.add(llllllllllllllllllIIllIlIlIllIIl.theme.plus()).widget();
                    llllllllllllllllllIIllIlIlIlllIl.action = () -> {
                        MacroEditorScreen llllllllllllllllllIIllIlIIlllIlI;
                        llllllllllllllllllIIllIlIIlllIlI.macro.addMessage("");
                        llllllllllllllllllIIllIlIIlllIlI.clear();
                        llllllllllllllllllIIllIlIIlllIlI.initWidgets(llllllllllllllllllIIllIlIIlllIlI.macro);
                    };
                }
                llllllllllllllllllIIllIlIlIlIllI.row();
            }
        }

        private void initWidgets(Macro llllllllllllllllllIIllIlIllIllll) {
            MacroEditorScreen llllllllllllllllllIIllIlIlllIIII;
            WTable llllllllllllllllllIIllIlIllIlllI = llllllllllllllllllIIllIlIlllIIII.add(llllllllllllllllllIIllIlIlllIIII.theme.table()).widget();
            llllllllllllllllllIIllIlIllIlllI.add(llllllllllllllllllIIllIlIlllIIII.theme.label("Name:"));
            WTextBox llllllllllllllllllIIllIlIllIllIl = llllllllllllllllllIIllIlIllIlllI.add(llllllllllllllllllIIllIlIlllIIII.theme.textBox(llllllllllllllllllIIllIlIllIllll == null ? "" : llllllllllllllllllIIllIlIlllIIII.macro.name)).minWidth(400.0).expandX().widget();
            llllllllllllllllllIIllIlIllIllIl.setFocused(true);
            llllllllllllllllllIIllIlIllIllIl.action = () -> {
                llllllllllllllllllIIllIlIIlIIIlI.macro.name = llllllllllllllllllIIllIlIllIllIl.get().trim();
            };
            llllllllllllllllllIIllIlIllIlllI.row();
            llllllllllllllllllIIllIlIllIlllI.add(llllllllllllllllllIIllIlIlllIIII.theme.label("Messages:")).padTop(4.0).top();
            WTable llllllllllllllllllIIllIlIllIllII = llllllllllllllllllIIllIlIllIlllI.add(llllllllllllllllllIIllIlIlllIIII.theme.table()).widget();
            llllllllllllllllllIIllIlIlllIIII.fillMessagesTable(llllllllllllllllllIIllIlIllIllII);
            llllllllllllllllllIIllIlIlllIIII.keybind = llllllllllllllllllIIllIlIlllIIII.add(llllllllllllllllllIIllIlIlllIIII.theme.keybind(llllllllllllllllllIIllIlIlllIIII.macro.keybind, true)).widget();
            llllllllllllllllllIIllIlIlllIIII.keybind.actionOnSet = () -> {
                llllllllllllllllllIIllIlIIlIIllI.binding = true;
            };
            WButton llllllllllllllllllIIllIlIllIlIll = llllllllllllllllllIIllIlIlllIIII.add(llllllllllllllllllIIllIlIlllIIII.theme.button(llllllllllllllllllIIllIlIlllIIII.isNewMacro ? "Add" : "Apply")).expandX().widget();
            llllllllllllllllllIIllIlIllIlIll.action = () -> {
                MacroEditorScreen llllllllllllllllllIIllIlIIlIlIII;
                if (llllllllllllllllllIIllIlIIlIlIII.isNewMacro) {
                    if (llllllllllllllllllIIllIlIIlIlIII.macro.name != null && !llllllllllllllllllIIllIlIIlIlIII.macro.name.isEmpty() && llllllllllllllllllIIllIlIIlIlIII.macro.messages.size() > 0 && llllllllllllllllllIIllIlIIlIlIII.macro.keybind.isSet()) {
                        Macros.get().add(llllllllllllllllllIIllIlIIlIlIII.macro);
                        llllllllllllllllllIIllIlIIlIlIII.onClose();
                    }
                } else {
                    Macros.get().save();
                    llllllllllllllllllIIllIlIIlIlIII.onClose();
                }
            };
        }

        @EventHandler(priority=200)
        private void onButton(MouseButtonEvent llllllllllllllllllIIllIlIlIIIllI) {
            MacroEditorScreen llllllllllllllllllIIllIlIlIIIlll;
            if (llllllllllllllllllIIllIlIlIIIlll.onAction(false, llllllllllllllllllIIllIlIlIIIllI.button)) {
                llllllllllllllllllIIllIlIlIIIllI.cancel();
            }
        }

        private boolean onAction(boolean llllllllllllllllllIIllIlIIlllllI, int llllllllllllllllllIIllIlIlIIIIII) {
            MacroEditorScreen llllllllllllllllllIIllIlIIllllll;
            if (llllllllllllllllllIIllIlIIllllll.binding) {
                llllllllllllllllllIIllIlIIllllll.keybind.onAction(llllllllllllllllllIIllIlIIlllllI, llllllllllllllllllIIllIlIlIIIIII);
                llllllllllllllllllIIllIlIIllllll.binding = false;
                return true;
            }
            return false;
        }

        @EventHandler(priority=200)
        private void onKey(KeyEvent llllllllllllllllllIIllIlIlIIlllI) {
            MacroEditorScreen llllllllllllllllllIIllIlIlIIllll;
            if (llllllllllllllllllIIllIlIlIIllll.onAction(true, llllllllllllllllllIIllIlIlIIlllI.key)) {
                llllllllllllllllllIIllIlIlIIlllI.cancel();
            }
        }

        public MacroEditorScreen(GuiTheme llllllllllllllllllIIllIlIllllIll, Macro llllllllllllllllllIIllIlIlllIlll) {
            super(llllllllllllllllllIIllIlIllllIll, llllllllllllllllllIIllIlIlllIlll == null ? "Create Macro" : "Edit Macro");
            MacroEditorScreen llllllllllllllllllIIllIlIlllllII;
            llllllllllllllllllIIllIlIlllllII.isNewMacro = llllllllllllllllllIIllIlIlllIlll == null;
            llllllllllllllllllIIllIlIlllllII.macro = llllllllllllllllllIIllIlIlllllII.isNewMacro ? new Macro() : llllllllllllllllllIIllIlIlllIlll;
            llllllllllllllllllIIllIlIlllllII.initWidgets(llllllllllllllllllIIllIlIlllIlll);
        }
    }

    private static class MacrosScreen
    extends WindowTabScreen {
        @Override
        protected void init() {
            MacrosScreen llllllllllllllllllIIIllIlIlllIll;
            super.init();
            llllllllllllllllllIIIllIlIlllIll.clear();
            llllllllllllllllllIIIllIlIlllIll.initWidgets();
        }

        public MacrosScreen(GuiTheme llllllllllllllllllIIIllIllIIIIIl, Tab llllllllllllllllllIIIllIllIIIIII) {
            super(llllllllllllllllllIIIllIllIIIIIl, llllllllllllllllllIIIllIllIIIIII);
            MacrosScreen llllllllllllllllllIIIllIllIIIIlI;
        }

        private void initWidgets() {
            MacrosScreen llllllllllllllllllIIIllIlIlIllIl;
            if (Macros.get().getAll().size() > 0) {
                WTable llllllllllllllllllIIIllIlIllIIII = llllllllllllllllllIIIllIlIlIllIl.add(llllllllllllllllllIIIllIlIlIllIl.theme.table()).expandX().widget();
                for (Macro llllllllllllllllllIIIllIlIllIIIl : Macros.get()) {
                    llllllllllllllllllIIIllIlIllIIII.add(llllllllllllllllllIIIllIlIlIllIl.theme.label(String.valueOf(new StringBuilder().append(llllllllllllllllllIIIllIlIllIIIl.name).append(" (").append(llllllllllllllllllIIIllIlIllIIIl.keybind).append(")"))));
                    WButton llllllllllllllllllIIIllIlIllIIll = llllllllllllllllllIIIllIlIllIIII.add(llllllllllllllllllIIIllIlIlIllIl.theme.button(GuiRenderer.EDIT)).expandCellX().right().widget();
                    llllllllllllllllllIIIllIlIllIIll.action = () -> {
                        MacrosScreen llllllllllllllllllIIIllIlIIllIlI;
                        Utils.mc.openScreen((Screen)new MacroEditorScreen(llllllllllllllllllIIIllIlIIllIlI.theme, llllllllllllllllllIIIllIlIllIIIl));
                    };
                    WMinus llllllllllllllllllIIIllIlIllIIlI = llllllllllllllllllIIIllIlIllIIII.add(llllllllllllllllllIIIllIlIlIllIl.theme.minus()).widget();
                    llllllllllllllllllIIIllIlIllIIlI.action = () -> {
                        MacrosScreen llllllllllllllllllIIIllIlIlIIIlI;
                        Macros.get().remove(llllllllllllllllllIIIllIlIllIIIl);
                        llllllllllllllllllIIIllIlIlIIIlI.clear();
                        llllllllllllllllllIIIllIlIlIIIlI.initWidgets();
                    };
                    llllllllllllllllllIIIllIlIllIIII.row();
                }
            }
            WButton llllllllllllllllllIIIllIlIlIlllI = llllllllllllllllllIIIllIlIlIllIl.add(llllllllllllllllllIIIllIlIlIllIl.theme.button("Create")).expandX().widget();
            llllllllllllllllllIIIllIlIlIlllI.action = () -> {
                MacrosScreen llllllllllllllllllIIIllIlIlIIllI;
                Utils.mc.openScreen((Screen)new MacroEditorScreen(llllllllllllllllllIIIllIlIlIIllI.theme, null));
            };
        }
    }
}

