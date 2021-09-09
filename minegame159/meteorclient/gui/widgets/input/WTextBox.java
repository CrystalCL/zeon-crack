/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.doubles.DoubleArrayList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package minegame159.meteorclient.gui.widgets.input;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import minegame159.meteorclient.gui.GuiKeyEvents;
import minegame159.meteorclient.gui.utils.CharFilter;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.utils.Utils;

public abstract class WTextBox
extends WWidget {
    protected /* synthetic */ DoubleList textWidths;
    public /* synthetic */ Runnable action;
    public /* synthetic */ Runnable actionOnUnfocused;
    protected /* synthetic */ int cursor;
    protected /* synthetic */ double textStart;
    protected /* synthetic */ String text;
    protected /* synthetic */ boolean focused;
    protected /* synthetic */ CharFilter filter;

    protected double getCursorTextWidth() {
        WTextBox llllllllllllllllIllllIIIIllllIlI;
        return llllllllllllllllIllllIIIIllllIlI.getCursorTextWidth(0);
    }

    private void cursorChanged() {
        WTextBox llllllllllllllllIllllIIIlIIIIlll;
        double llllllllllllllllIllllIIIlIIIlIII = llllllllllllllllIllllIIIlIIIIlll.getCursorTextWidth(-2);
        if (llllllllllllllllIllllIIIlIIIlIII < llllllllllllllllIllllIIIlIIIIlll.textStart) {
            llllllllllllllllIllllIIIlIIIIlll.textStart -= llllllllllllllllIllllIIIlIIIIlll.textStart - llllllllllllllllIllllIIIlIIIlIII;
        }
        if ((llllllllllllllllIllllIIIlIIIlIII = llllllllllllllllIllllIIIlIIIIlll.getCursorTextWidth(2)) > llllllllllllllllIllllIIIlIIIIlll.textStart + llllllllllllllllIllllIIIlIIIIlll.maxTextWidth()) {
            llllllllllllllllIllllIIIlIIIIlll.textStart += llllllllllllllllIllllIIIlIIIlIII - (llllllllllllllllIllllIIIlIIIIlll.textStart + llllllllllllllllIllllIIIlIIIIlll.maxTextWidth());
        }
        llllllllllllllllIllllIIIlIIIIlll.textStart = Utils.clamp(llllllllllllllllIllllIIIlIIIIlll.textStart, 0.0, Math.max(llllllllllllllllIllllIIIlIIIIlll.textWidth() - llllllllllllllllIllllIIIlIIIIlll.maxTextWidth(), 0.0));
        llllllllllllllllIllllIIIlIIIIlll.onCursorChanged();
    }

    protected void onCursorChanged() {
    }

    public void setFocused(boolean llllllllllllllllIllllIIIIllIIllI) {
        WTextBox llllllllllllllllIllllIIIIllIIlll;
        if (!llllllllllllllllIllllIIIIllIIlll.focused && llllllllllllllllIllllIIIIllIIllI) {
            GuiKeyEvents.setPostKeyEvents(true);
        } else if (llllllllllllllllIllllIIIIllIIlll.focused && !llllllllllllllllIllllIIIIllIIllI) {
            GuiKeyEvents.setPostKeyEvents(false);
        }
        if (llllllllllllllllIllllIIIIllIIlll.focused && !llllllllllllllllIllllIIIIllIIllI && llllllllllllllllIllllIIIIllIIlll.actionOnUnfocused != null) {
            llllllllllllllllIllllIIIIllIIlll.actionOnUnfocused.run();
        }
        llllllllllllllllIllllIIIIllIIlll.focused = llllllllllllllllIllllIIIIllIIllI;
    }

    private void calculateTextWidths() {
        WTextBox llllllllllllllllIllllIIIlIIlIIll;
        llllllllllllllllIllllIIIlIIlIIll.textWidths.clear();
        for (int llllllllllllllllIllllIIIlIIlIlIl = 0; llllllllllllllllIllllIIIlIIlIlIl <= llllllllllllllllIllllIIIlIIlIIll.text.length(); ++llllllllllllllllIllllIIIlIIlIlIl) {
            llllllllllllllllIllllIIIlIIlIIll.textWidths.add(llllllllllllllllIllllIIIlIIlIIll.theme.textWidth(llllllllllllllllIllllIIIlIIlIIll.text, llllllllllllllllIllllIIIlIIlIlIl, false));
        }
    }

    public void set(String llllllllllllllllIllllIIIIllIllll) {
        WTextBox llllllllllllllllIllllIIIIlllIIII;
        llllllllllllllllIllllIIIIlllIIII.text = llllllllllllllllIllllIIIIllIllll;
        llllllllllllllllIllllIIIIlllIIII.cursor = Utils.clamp(llllllllllllllllIllllIIIIlllIIII.cursor, 0, llllllllllllllllIllllIIIIllIllll.length());
        llllllllllllllllIllllIIIIlllIIII.calculateTextWidths();
        llllllllllllllllIllllIIIIlllIIII.cursorChanged();
    }

    private int countToNextSpace(boolean llllllllllllllllIllllIIIlIIlllII) {
        WTextBox llllllllllllllllIllllIIIlIIlllIl;
        int llllllllllllllllIllllIIIlIIlllll = 0;
        boolean llllllllllllllllIllllIIIlIIllllI = false;
        int llllllllllllllllIllllIIIlIlIIIlI = llllllllllllllllIllllIIIlIIlllIl.cursor;
        while (llllllllllllllllIllllIIIlIIlllII ? llllllllllllllllIllllIIIlIlIIIlI >= 0 : llllllllllllllllIllllIIIlIlIIIlI < llllllllllllllllIllllIIIlIIlllIl.text.length()) {
            int llllllllllllllllIllllIIIlIlIIIll = llllllllllllllllIllllIIIlIlIIIlI;
            if (llllllllllllllllIllllIIIlIIlllII) {
                --llllllllllllllllIllllIIIlIlIIIll;
            }
            if (llllllllllllllllIllllIIIlIlIIIll < llllllllllllllllIllllIIIlIIlllIl.text.length()) {
                if (llllllllllllllllIllllIIIlIlIIIll < 0 || llllllllllllllllIllllIIIlIIllllI && llllllllllllllllIllllIIIlIIlllIl.text.charAt(llllllllllllllllIllllIIIlIlIIIll) == ' ') break;
                if (llllllllllllllllIllllIIIlIIlllIl.text.charAt(llllllllllllllllIllllIIIlIlIIIll) != ' ') {
                    llllllllllllllllIllllIIIlIIllllI = true;
                }
                ++llllllllllllllllIllllIIIlIIlllll;
            }
            llllllllllllllllIllllIIIlIlIIIlI += llllllllllllllllIllllIIIlIIlllII ? -1 : 1;
        }
        return llllllllllllllllIllllIIIlIIlllll;
    }

    private double textWidth() {
        WTextBox llllllllllllllllIllllIIIlIIIllIl;
        return llllllllllllllllIllllIIIlIIIllIl.textWidths.isEmpty() ? 0.0 : llllllllllllllllIllllIIIlIIIllIl.textWidths.getDouble(llllllllllllllllIllllIIIlIIIllIl.textWidths.size() - 1);
    }

    protected double getCursorTextWidth(int llllllllllllllllIllllIIIIlllllIl) {
        WTextBox llllllllllllllllIllllIIIlIIIIIIl;
        if (llllllllllllllllIllllIIIlIIIIIIl.textWidths.isEmpty()) {
            return 0.0;
        }
        int llllllllllllllllIllllIIIIlllllll = llllllllllllllllIllllIIIlIIIIIIl.cursor + llllllllllllllllIllllIIIIlllllIl;
        if (llllllllllllllllIllllIIIIlllllll < 0) {
            llllllllllllllllIllllIIIIlllllll = 0;
        } else if (llllllllllllllllIllllIIIIlllllll >= llllllllllllllllIllllIIIlIIIIIIl.textWidths.size()) {
            llllllllllllllllIllllIIIIlllllll = llllllllllllllllIllllIIIlIIIIIIl.textWidths.size() - 1;
        }
        return llllllllllllllllIllllIIIlIIIIIIl.textWidths.getDouble(llllllllllllllllIllllIIIIlllllll);
    }

    protected double maxTextWidth() {
        WTextBox llllllllllllllllIllllIIlIIIIIlIl;
        return llllllllllllllllIllllIIlIIIIIlIl.width - llllllllllllllllIllllIIlIIIIIlIl.pad() * 2.0;
    }

    @Override
    public boolean onKeyRepeated(int llllllllllllllllIllllIIIlIlllIlI, int llllllllllllllllIllllIIIlIlllIIl) {
        boolean llllllllllllllllIllllIIIlIllIlll;
        WTextBox llllllllllllllllIllllIIIlIlllIll;
        if (!llllllllllllllllIllllIIIlIlllIll.focused) {
            return false;
        }
        boolean llllllllllllllllIllllIIIlIlllIII = llllllllllllllllIllllIIIlIlllIIl == 2 || llllllllllllllllIllllIIIlIlllIIl == 8;
        boolean bl = llllllllllllllllIllllIIIlIllIlll = llllllllllllllllIllllIIIlIlllIIl == 1;
        if (llllllllllllllllIllllIIIlIlllIlI == 259) {
            if (llllllllllllllllIllllIIIlIlllIll.cursor > 0) {
                String llllllllllllllllIllllIIIlIllllll = llllllllllllllllIllllIIIlIlllIll.text;
                int llllllllllllllllIllllIIIlIlllllI = llllllllllllllllIllllIIIlIlllIII ? llllllllllllllllIllllIIIlIlllIll.countToNextSpace(true) : 1;
                llllllllllllllllIllllIIIlIlllIll.text = String.valueOf(new StringBuilder().append(llllllllllllllllIllllIIIlIlllIll.text.substring(0, llllllllllllllllIllllIIIlIlllIll.cursor - llllllllllllllllIllllIIIlIlllllI)).append(llllllllllllllllIllllIIIlIlllIll.text.substring(llllllllllllllllIllllIIIlIlllIll.cursor)));
                llllllllllllllllIllllIIIlIlllIll.cursor -= llllllllllllllllIllllIIIlIlllllI;
                if (!llllllllllllllllIllllIIIlIlllIll.text.equals(llllllllllllllllIllllIIIlIllllll)) {
                    llllllllllllllllIllllIIIlIlllIll.runAction();
                }
            }
            return true;
        }
        if (llllllllllllllllIllllIIIlIlllIlI == 261) {
            if (llllllllllllllllIllllIIIlIlllIll.cursor < llllllllllllllllIllllIIIlIlllIll.text.length()) {
                String llllllllllllllllIllllIIIlIllllIl = llllllllllllllllIllllIIIlIlllIll.text;
                int llllllllllllllllIllllIIIlIllllII = llllllllllllllllIllllIIIlIlllIII ? llllllllllllllllIllllIIIlIlllIll.countToNextSpace(false) : 1;
                llllllllllllllllIllllIIIlIlllIll.text = String.valueOf(new StringBuilder().append(llllllllllllllllIllllIIIlIlllIll.text.substring(0, llllllllllllllllIllllIIIlIlllIll.cursor)).append(llllllllllllllllIllllIIIlIlllIll.text.substring(llllllllllllllllIllllIIIlIlllIll.cursor + llllllllllllllllIllllIIIlIllllII)));
                if (!llllllllllllllllIllllIIIlIlllIll.text.equals(llllllllllllllllIllllIIIlIllllIl)) {
                    llllllllllllllllIllllIIIlIlllIll.runAction();
                }
            }
            return true;
        }
        if (llllllllllllllllIllllIIIlIlllIlI == 263 || llllllllllllllllIllllIIIlIllIlll && llllllllllllllllIllllIIIlIlllIlI == 324) {
            if (llllllllllllllllIllllIIIlIlllIll.cursor > 0) {
                llllllllllllllllIllllIIIlIlllIll.cursor -= llllllllllllllllIllllIIIlIlllIII ? llllllllllllllllIllllIIIlIlllIll.countToNextSpace(true) : 1;
                llllllllllllllllIllllIIIlIlllIll.cursorChanged();
            }
            return true;
        }
        if (llllllllllllllllIllllIIIlIlllIlI == 262 || llllllllllllllllIllllIIIlIllIlll && llllllllllllllllIllllIIIlIlllIlI == 326) {
            if (llllllllllllllllIllllIIIlIlllIll.cursor < llllllllllllllllIllllIIIlIlllIll.text.length()) {
                llllllllllllllllIllllIIIlIlllIll.cursor += llllllllllllllllIllllIIIlIlllIII ? llllllllllllllllIllllIIIlIlllIll.countToNextSpace(false) : 1;
                llllllllllllllllIllllIIIlIlllIll.cursorChanged();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onCalculateSize() {
        WTextBox llllllllllllllllIllllIIlIIIIllIl;
        double llllllllllllllllIllllIIlIIIIllII = llllllllllllllllIllllIIlIIIIllIl.pad();
        double llllllllllllllllIllllIIlIIIIlIll = llllllllllllllllIllllIIlIIIIllIl.theme.textHeight();
        llllllllllllllllIllllIIlIIIIllIl.width = llllllllllllllllIllllIIlIIIIllII + llllllllllllllllIllllIIlIIIIlIll + llllllllllllllllIllllIIlIIIIllII;
        llllllllllllllllIllllIIlIIIIllIl.height = llllllllllllllllIllllIIlIIIIllII + llllllllllllllllIllllIIlIIIIlIll + llllllllllllllllIllllIIlIIIIllII;
        llllllllllllllllIllllIIlIIIIllIl.calculateTextWidths();
    }

    public String get() {
        WTextBox llllllllllllllllIllllIIIIlllIIll;
        return llllllllllllllllIllllIIIIlllIIll.text;
    }

    private void runAction() {
        WTextBox llllllllllllllllIllllIIIlIIlIIII;
        llllllllllllllllIllllIIIlIIlIIII.calculateTextWidths();
        llllllllllllllllIllllIIIlIIlIIII.cursorChanged();
        if (llllllllllllllllIllllIIIlIIlIIII.action != null) {
            llllllllllllllllIllllIIIlIIlIIII.action.run();
        }
    }

    @Override
    public boolean onKeyPressed(int llllllllllllllllIllllIIIllIlIIll, int llllllllllllllllIllllIIIllIIlllI) {
        boolean llllllllllllllllIllllIIIllIlIIIl;
        WTextBox llllllllllllllllIllllIIIllIlIlII;
        if (!llllllllllllllllIllllIIIllIlIlII.focused) {
            return false;
        }
        boolean bl = llllllllllllllllIllllIIIllIlIIIl = llllllllllllllllIllllIIIllIIlllI == 2 || llllllllllllllllIllllIIIllIIlllI == 8;
        if (llllllllllllllllIllllIIIllIlIIll == 86 && llllllllllllllllIllllIIIllIlIIIl) {
            String llllllllllllllllIllllIIIllIllIIl = llllllllllllllllIllllIIIllIlIlII.text;
            String llllllllllllllllIllllIIIllIllIII = Utils.mc.keyboard.getClipboard();
            int llllllllllllllllIllllIIIllIlIlll = 0;
            StringBuilder llllllllllllllllIllllIIIllIlIllI = new StringBuilder(llllllllllllllllIllllIIIllIlIlII.text.length() + llllllllllllllllIllllIIIllIllIII.length());
            llllllllllllllllIllllIIIllIlIllI.append(llllllllllllllllIllllIIIllIlIlII.text, 0, llllllllllllllllIllllIIIllIlIlII.cursor);
            for (int llllllllllllllllIllllIIIllIllIlI = 0; llllllllllllllllIllllIIIllIllIlI < llllllllllllllllIllllIIIllIllIII.length(); ++llllllllllllllllIllllIIIllIllIlI) {
                char llllllllllllllllIllllIIIllIllIll = llllllllllllllllIllllIIIllIllIII.charAt(llllllllllllllllIllllIIIllIllIlI);
                if (!llllllllllllllllIllllIIIllIlIlII.filter.filter(llllllllllllllllIllllIIIllIlIlII.text, llllllllllllllllIllllIIIllIllIll)) continue;
                llllllllllllllllIllllIIIllIlIllI.append(llllllllllllllllIllllIIIllIllIll);
                ++llllllllllllllllIllllIIIllIlIlll;
            }
            llllllllllllllllIllllIIIllIlIllI.append(llllllllllllllllIllllIIIllIlIlII.text, llllllllllllllllIllllIIIllIlIlII.cursor, llllllllllllllllIllllIIIllIlIlII.text.length());
            llllllllllllllllIllllIIIllIlIlII.text = String.valueOf(llllllllllllllllIllllIIIllIlIllI);
            llllllllllllllllIllllIIIllIlIlII.cursor += llllllllllllllllIllllIIIllIlIlll;
            if (!llllllllllllllllIllllIIIllIlIlII.text.equals(llllllllllllllllIllllIIIllIllIIl)) {
                llllllllllllllllIllllIIIllIlIlII.runAction();
            }
            return true;
        }
        if (llllllllllllllllIllllIIIllIlIIll == 67 && llllllllllllllllIllllIIIllIlIIIl) {
            Utils.mc.keyboard.setClipboard(llllllllllllllllIllllIIIllIlIlII.text);
            return true;
        }
        if (llllllllllllllllIllllIIIllIlIIll == 88 && llllllllllllllllIllllIIIllIlIIIl) {
            String llllllllllllllllIllllIIIllIlIlIl = llllllllllllllllIllllIIIllIlIlII.text;
            Utils.mc.keyboard.setClipboard(llllllllllllllllIllllIIIllIlIlII.text);
            llllllllllllllllIllllIIIllIlIlII.text = "";
            llllllllllllllllIllllIIIllIlIlII.cursor = 0;
            if (!llllllllllllllllIllllIIIllIlIlII.text.equals(llllllllllllllllIllllIIIllIlIlIl)) {
                llllllllllllllllIllllIIIllIlIlII.runAction();
            }
            return true;
        }
        if (llllllllllllllllIllllIIIllIlIIll == 257 || llllllllllllllllIllllIIIllIlIIll == 335) {
            llllllllllllllllIllllIIIllIlIlII.focused = false;
            if (llllllllllllllllIllllIIIllIlIlII.actionOnUnfocused != null) {
                llllllllllllllllIllllIIIllIlIlII.actionOnUnfocused.run();
            }
            return true;
        }
        return llllllllllllllllIllllIIIllIlIlII.onKeyRepeated(llllllllllllllllIllllIIIllIlIIll, llllllllllllllllIllllIIIllIIlllI);
    }

    @Override
    public boolean onMouseClicked(double llllllllllllllllIllllIIIllllIIll, double llllllllllllllllIllllIIIllllIIlI, int llllllllllllllllIllllIIIlllIllII, boolean llllllllllllllllIllllIIIllllIIII) {
        WTextBox llllllllllllllllIllllIIIlllIllll;
        if (llllllllllllllllIllllIIIlllIllll.mouseOver && !llllllllllllllllIllllIIIllllIIII) {
            if (llllllllllllllllIllllIIIlllIllII == 1) {
                if (!llllllllllllllllIllllIIIlllIllll.text.isEmpty()) {
                    llllllllllllllllIllllIIIlllIllll.text = "";
                    llllllllllllllllIllllIIIlllIllll.cursor = 0;
                    llllllllllllllllIllllIIIlllIllll.runAction();
                }
            } else {
                double llllllllllllllllIllllIIIlllllIII = llllllllllllllllIllllIIIlllIllll.getOverflowWidthForRender();
                double llllllllllllllllIllllIIIllllIlll = llllllllllllllllIllllIIIllllIIll - llllllllllllllllIllllIIIlllIllll.x + llllllllllllllllIllllIIIlllllIII;
                double llllllllllllllllIllllIIIllllIllI = llllllllllllllllIllllIIIlllIllll.pad();
                double llllllllllllllllIllllIIIllllIlIl = Double.MAX_VALUE;
                llllllllllllllllIllllIIIlllIllll.cursor = 0;
                for (int llllllllllllllllIllllIIIlllllIIl = 0; llllllllllllllllIllllIIIlllllIIl < llllllllllllllllIllllIIIlllIllll.textWidths.size(); ++llllllllllllllllIllllIIIlllllIIl) {
                    double llllllllllllllllIllllIIIlllllIlI = Math.abs(llllllllllllllllIllllIIIlllIllll.textWidths.getDouble(llllllllllllllllIllllIIIlllllIIl) + llllllllllllllllIllllIIIllllIllI - llllllllllllllllIllllIIIllllIlll);
                    if (!(llllllllllllllllIllllIIIlllllIlI < llllllllllllllllIllllIIIllllIlIl)) continue;
                    llllllllllllllllIllllIIIllllIlIl = llllllllllllllllIllllIIIlllllIlI;
                    llllllllllllllllIllllIIIlllIllll.cursor = llllllllllllllllIllllIIIlllllIIl;
                }
                llllllllllllllllIllllIIIlllIllll.cursorChanged();
            }
            llllllllllllllllIllllIIIlllIllll.setFocused(true);
            return true;
        }
        if (llllllllllllllllIllllIIIlllIllll.focused) {
            llllllllllllllllIllllIIIlllIllll.setFocused(false);
        }
        return false;
    }

    @Override
    public boolean onCharTyped(char llllllllllllllllIllllIIIlIlIllII) {
        WTextBox llllllllllllllllIllllIIIlIlIlIll;
        if (!llllllllllllllllIllllIIIlIlIlIll.focused) {
            return false;
        }
        if (llllllllllllllllIllllIIIlIlIlIll.filter.filter(llllllllllllllllIllllIIIlIlIlIll.text, llllllllllllllllIllllIIIlIlIllII)) {
            llllllllllllllllIllllIIIlIlIlIll.text = String.valueOf(new StringBuilder().append(llllllllllllllllIllllIIIlIlIlIll.text.substring(0, llllllllllllllllIllllIIIlIlIlIll.cursor)).append(llllllllllllllllIllllIIIlIlIllII).append(llllllllllllllllIllllIIIlIlIlIll.text.substring(llllllllllllllllIllllIIIlIlIlIll.cursor)));
            ++llllllllllllllllIllllIIIlIlIlIll.cursor;
            llllllllllllllllIllllIIIlIlIlIll.runAction();
            return true;
        }
        return false;
    }

    protected double getOverflowWidthForRender() {
        WTextBox llllllllllllllllIllllIIIIlllIlll;
        return llllllllllllllllIllllIIIIlllIlll.textStart;
    }

    public WTextBox(String llllllllllllllllIllllIIlIIIlIIlI, CharFilter llllllllllllllllIllllIIlIIIlIIIl) {
        WTextBox llllllllllllllllIllllIIlIIIlIIll;
        llllllllllllllllIllllIIlIIIlIIll.textWidths = new DoubleArrayList();
        llllllllllllllllIllllIIlIIIlIIll.text = llllllllllllllllIllllIIlIIIlIIlI;
        llllllllllllllllIllllIIlIIIlIIll.filter = llllllllllllllllIllllIIlIIIlIIIl;
    }

    public boolean isFocused() {
        WTextBox llllllllllllllllIllllIIIIllIlIll;
        return llllllllllllllllIllllIIIIllIlIll.focused;
    }
}

