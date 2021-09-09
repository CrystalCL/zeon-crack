/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.render.VertexFormats
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.screen.Screen
 *  net.minecraft.client.util.math.MatrixStack
 */
package minegame159.meteorclient.gui.tabs.builtin;

import java.util.ArrayList;
import java.util.List;
import minegame159.meteorclient.events.render.Render2DEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.screens.HudElementScreen;
import minegame159.meteorclient.gui.tabs.Tab;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.tabs.WindowTabScreen;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.HudElement;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

public class HudTab
extends Tab {
    public static /* synthetic */ HudTab INSTANCE;

    public HudTab() {
        super("HUD");
        HudTab llllllllllllllllllIIlIIIIlIIlIlI;
        INSTANCE = llllllllllllllllllIIlIIIIlIIlIlI;
    }

    @Override
    public void openScreen(GuiTheme llllllllllllllllllIIlIIIIIllllll) {
        HudTab llllllllllllllllllIIlIIIIlIIIIlI;
        Utils.mc.openScreen((Screen)llllllllllllllllllIIlIIIIlIIIIlI.createScreen(llllllllllllllllllIIlIIIIIllllll));
    }

    @Override
    public boolean isScreen(Screen llllllllllllllllllIIlIIIIIllIIlI) {
        return llllllllllllllllllIIlIIIIIllIIlI instanceof HudScreen;
    }

    @Override
    public TabScreen createScreen(GuiTheme llllllllllllllllllIIlIIIIIllIlll) {
        HudTab llllllllllllllllllIIlIIIIIllIllI;
        return new HudScreen(llllllllllllllllllIIlIIIIIllIlll, llllllllllllllllllIIlIIIIIllIllI);
    }

    private static class HudScreen
    extends WindowTabScreen {
        private /* synthetic */ double mouseStartY;
        private /* synthetic */ double lastMouseX;
        private /* synthetic */ double lastMouseY;
        private final /* synthetic */ Color HOVER_OL_COLOR;
        private final /* synthetic */ List<HudElement> selectedElements;
        private /* synthetic */ boolean selecting;
        private final /* synthetic */ Color HOVER_BG_COLOR;
        private /* synthetic */ boolean dragged;
        private final /* synthetic */ Color INACTIVE_OL_COLOR;
        private final /* synthetic */ HUD hud;
        private /* synthetic */ double mouseStartX;
        private /* synthetic */ HudElement hoveredModule;
        private final /* synthetic */ Color INACTIVE_BG_COLOR;
        private /* synthetic */ boolean dragging;

        @Override
        public void mouseMoved(double lIIIlIIIIIIIIll, double lIIIlIIIIIIIIlI) {
            HudScreen lIIIlIIIIIIlIII;
            double lIIIlIIIIIIIlIl = MinecraftClient.getInstance().getWindow().getScaleFactor();
            lIIIlIIIIIIIIll *= lIIIlIIIIIIIlIl;
            lIIIlIIIIIIIIlI *= lIIIlIIIIIIIlIl;
            if (lIIIlIIIIIIlIII.selecting) {
                lIIIlIIIIIIlIII.selectedElements.clear();
                for (HudElement lIIIlIIIIlIIIIl : lIIIlIIIIIIlIII.hud.elements) {
                    double lIIIlIIIIlIIlIl = lIIIlIIIIlIIIIl.box.getX();
                    double lIIIlIIIIlIIlII = lIIIlIIIIlIIIIl.box.getY();
                    double lIIIlIIIIlIIIll = lIIIlIIIIlIIIIl.box.width;
                    double lIIIlIIIIlIIIlI = lIIIlIIIIlIIIIl.box.height;
                    if (!lIIIlIIIIIIlIII.isInSelection(lIIIlIIIIIIIIll, lIIIlIIIIIIIIlI, lIIIlIIIIlIIlIl, lIIIlIIIIlIIlII) && !lIIIlIIIIIIlIII.isInSelection(lIIIlIIIIIIIIll, lIIIlIIIIIIIIlI, lIIIlIIIIlIIlIl + lIIIlIIIIlIIIll, lIIIlIIIIlIIlII) && !lIIIlIIIIIIlIII.isInSelection(lIIIlIIIIIIIIll, lIIIlIIIIIIIIlI, lIIIlIIIIlIIlIl, lIIIlIIIIlIIlII + lIIIlIIIIlIIIlI) && !lIIIlIIIIIIlIII.isInSelection(lIIIlIIIIIIIIll, lIIIlIIIIIIIIlI, lIIIlIIIIlIIlIl + lIIIlIIIIlIIIll, lIIIlIIIIlIIlII + lIIIlIIIIlIIIlI)) continue;
                    lIIIlIIIIIIlIII.selectedElements.add(lIIIlIIIIlIIIIl);
                }
            } else if (lIIIlIIIIIIlIII.dragging) {
                for (HudElement lIIIlIIIIlIIIII : lIIIlIIIIIIlIII.selectedElements) {
                    lIIIlIIIIlIIIII.box.addPos(lIIIlIIIIIIIIll - lIIIlIIIIIIlIII.lastMouseX, lIIIlIIIIIIIIlI - lIIIlIIIIIIlIII.lastMouseY);
                }
                double lIIIlIIIIIIlIIl = lIIIlIIIIIIlIII.hud.snappingRange.get().intValue();
                if (lIIIlIIIIIIlIIl > 0.0) {
                    double lIIIlIIIIIIllll = Double.MAX_VALUE;
                    double lIIIlIIIIIIlllI = Double.MAX_VALUE;
                    double lIIIlIIIIIIllIl = 0.0;
                    double lIIIlIIIIIIllII = 0.0;
                    for (HudElement lIIIlIIIIIlllll : lIIIlIIIIIIlIII.selectedElements) {
                        lIIIlIIIIIIllll = Math.min(lIIIlIIIIIIllll, lIIIlIIIIIlllll.box.getX());
                        lIIIlIIIIIIlllI = Math.min(lIIIlIIIIIIlllI, lIIIlIIIIIlllll.box.getY());
                    }
                    for (HudElement lIIIlIIIIIllllI : lIIIlIIIIIIlIII.selectedElements) {
                        lIIIlIIIIIIllIl = Math.max(lIIIlIIIIIIllIl, lIIIlIIIIIllllI.box.getX() - lIIIlIIIIIIllll + lIIIlIIIIIllllI.box.width);
                        lIIIlIIIIIIllII = Math.max(lIIIlIIIIIIllII, lIIIlIIIIIllllI.box.getY() - lIIIlIIIIIIlllI + lIIIlIIIIIllllI.box.height);
                    }
                    boolean lIIIlIIIIIIlIll = false;
                    boolean lIIIlIIIIIIlIlI = false;
                    for (HudElement lIIIlIIIIIlIIII : lIIIlIIIIIIlIII.hud.elements) {
                        if (lIIIlIIIIIIlIII.selectedElements.contains(lIIIlIIIIIlIIII)) continue;
                        double lIIIlIIIIIllIII = lIIIlIIIIIlIIII.box.getX();
                        double lIIIlIIIIIlIlll = lIIIlIIIIIlIIII.box.getY();
                        double lIIIlIIIIIlIllI = lIIIlIIIIIlIIII.box.width;
                        double lIIIlIIIIIlIlIl = lIIIlIIIIIlIIII.box.height;
                        boolean lIIIlIIIIIlIlII = lIIIlIIIIIIlIII.isPointBetween(lIIIlIIIIIIllll, lIIIlIIIIIIllIl, lIIIlIIIIIllIII) || lIIIlIIIIIIlIII.isPointBetween(lIIIlIIIIIIllll, lIIIlIIIIIIllIl, lIIIlIIIIIllIII + lIIIlIIIIIlIllI) || lIIIlIIIIIIlIII.isPointBetween(lIIIlIIIIIllIII, lIIIlIIIIIlIllI, lIIIlIIIIIIllll) || lIIIlIIIIIIlIII.isPointBetween(lIIIlIIIIIllIII, lIIIlIIIIIlIllI, lIIIlIIIIIIllll + lIIIlIIIIIIllIl);
                        boolean lIIIlIIIIIlIIll = lIIIlIIIIIIlIII.isPointBetween(lIIIlIIIIIIlllI, lIIIlIIIIIIllII, lIIIlIIIIIlIlll) || lIIIlIIIIIIlIII.isPointBetween(lIIIlIIIIIIlllI, lIIIlIIIIIIllII, lIIIlIIIIIlIlll + lIIIlIIIIIlIlIl) || lIIIlIIIIIIlIII.isPointBetween(lIIIlIIIIIlIlll, lIIIlIIIIIlIlIl, lIIIlIIIIIIlllI) || lIIIlIIIIIIlIII.isPointBetween(lIIIlIIIIIlIlll, lIIIlIIIIIlIlIl, lIIIlIIIIIIlllI + lIIIlIIIIIIllII);
                        double lIIIlIIIIIlIIlI = 0.0;
                        double lIIIlIIIIIlIIIl = 0.0;
                        if (!lIIIlIIIIIIlIll && lIIIlIIIIIlIIll) {
                            double lIIIlIIIIIlllIl = lIIIlIIIIIIllll + lIIIlIIIIIIllIl;
                            double lIIIlIIIIIlllII = lIIIlIIIIIllIII + lIIIlIIIIIlIllI;
                            if (Math.abs(lIIIlIIIIIllIII - lIIIlIIIIIIllll) < lIIIlIIIIIIlIIl) {
                                lIIIlIIIIIlIIlI = lIIIlIIIIIllIII - lIIIlIIIIIIllll;
                            } else if (Math.abs(lIIIlIIIIIlllII - lIIIlIIIIIlllIl) <= lIIIlIIIIIIlIIl) {
                                lIIIlIIIIIlIIlI = lIIIlIIIIIlllII - lIIIlIIIIIlllIl;
                            } else if (Math.abs(lIIIlIIIIIlllII - lIIIlIIIIIIllll) <= lIIIlIIIIIIlIIl) {
                                lIIIlIIIIIlIIlI = lIIIlIIIIIlllII - lIIIlIIIIIIllll;
                            } else if (Math.abs(lIIIlIIIIIllIII - lIIIlIIIIIlllIl) <= lIIIlIIIIIIlIIl) {
                                lIIIlIIIIIlIIlI = lIIIlIIIIIllIII - lIIIlIIIIIlllIl;
                            }
                        }
                        if (!lIIIlIIIIIIlIlI && lIIIlIIIIIlIlII) {
                            double lIIIlIIIIIllIll = lIIIlIIIIIIlllI + lIIIlIIIIIIllII;
                            double lIIIlIIIIIllIlI = lIIIlIIIIIlIlll + lIIIlIIIIIlIlIl;
                            if (Math.abs(lIIIlIIIIIlIlll - lIIIlIIIIIIlllI) <= lIIIlIIIIIIlIIl) {
                                lIIIlIIIIIlIIIl = lIIIlIIIIIlIlll - lIIIlIIIIIIlllI;
                            } else if (Math.abs(lIIIlIIIIIllIlI - lIIIlIIIIIllIll) <= lIIIlIIIIIIlIIl) {
                                lIIIlIIIIIlIIIl = lIIIlIIIIIllIlI - lIIIlIIIIIllIll;
                            } else if (Math.abs(lIIIlIIIIIllIlI - lIIIlIIIIIIlllI) <= lIIIlIIIIIIlIIl) {
                                lIIIlIIIIIlIIIl = lIIIlIIIIIllIlI - lIIIlIIIIIIlllI;
                            } else if (Math.abs(lIIIlIIIIIlIlll - lIIIlIIIIIllIll) <= lIIIlIIIIIIlIIl) {
                                lIIIlIIIIIlIIIl = lIIIlIIIIIlIlll - lIIIlIIIIIllIll;
                            }
                        }
                        if (lIIIlIIIIIlIIlI != 0.0 || lIIIlIIIIIlIIIl != 0.0) {
                            for (HudElement lIIIlIIIIIllIIl : lIIIlIIIIIIlIII.selectedElements) {
                                lIIIlIIIIIllIIl.box.addPos(lIIIlIIIIIlIIlI, lIIIlIIIIIlIIIl);
                            }
                            if (lIIIlIIIIIlIIlI != 0.0) {
                                lIIIlIIIIIIlIll = true;
                            }
                            if (lIIIlIIIIIlIIIl != 0.0) {
                                lIIIlIIIIIIlIlI = true;
                            }
                        }
                        if (!lIIIlIIIIIIlIll || !lIIIlIIIIIIlIlI) continue;
                        break;
                    }
                    lIIIlIIIIIIlIII.dragged = true;
                }
            }
            lIIIlIIIIIIlIII.lastMouseX = lIIIlIIIIIIIIll;
            lIIIlIIIIIIlIII.lastMouseY = lIIIlIIIIIIIIlI;
        }

        @Override
        public void render(MatrixStack lIIIIllllIIlIIl, int lIIIIllllIIlIII, int lIIIIllllIIllIl, float lIIIIllllIIllII) {
            HudScreen lIIIIllllIlIIII;
            double lIIIIllllIIlIll = MinecraftClient.getInstance().getWindow().getScaleFactor();
            lIIIIllllIIlIII = (int)((double)lIIIIllllIIlIII * lIIIIllllIIlIll);
            lIIIIllllIIllIl = (int)((double)lIIIIllllIIllIl * lIIIIllllIIlIll);
            if (!Utils.canUpdate()) {
                lIIIIllllIlIIII.renderBackground(lIIIIllllIIlIIl);
                Utils.unscaledProjection();
                lIIIIllllIlIIII.hud.onRender(Render2DEvent.get(0, 0, lIIIIllllIIllII));
            } else {
                Utils.unscaledProjection();
            }
            Renderer.NORMAL.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
            for (HudElement lIIIIllllIlIIll : lIIIIllllIlIIII.hud.elements) {
                if (lIIIIllllIlIIll.active) continue;
                lIIIIllllIlIIII.renderElement(lIIIIllllIlIIll, lIIIIllllIlIIII.INACTIVE_BG_COLOR, lIIIIllllIlIIII.INACTIVE_OL_COLOR);
            }
            for (HudElement lIIIIllllIlIIlI : lIIIIllllIlIIII.selectedElements) {
                lIIIIllllIlIIII.renderElement(lIIIIllllIlIIlI, lIIIIllllIlIIII.HOVER_BG_COLOR, lIIIIllllIlIIII.HOVER_OL_COLOR);
            }
            if (!lIIIIllllIlIIII.dragging) {
                lIIIIllllIlIIII.hoveredModule = null;
                for (HudElement lIIIIllllIlIIIl : lIIIIllllIlIIII.hud.elements) {
                    if (!lIIIIllllIlIIIl.box.isOver(lIIIIllllIIlIII, lIIIIllllIIllIl)) continue;
                    if (!lIIIIllllIlIIII.selectedElements.contains(lIIIIllllIlIIIl)) {
                        lIIIIllllIlIIII.renderElement(lIIIIllllIlIIIl, lIIIIllllIlIIII.HOVER_BG_COLOR, lIIIIllllIlIIII.HOVER_OL_COLOR);
                    }
                    lIIIIllllIlIIII.hoveredModule = lIIIIllllIlIIIl;
                    break;
                }
                if (lIIIIllllIlIIII.selecting) {
                    lIIIIllllIlIIII.renderQuad(lIIIIllllIlIIII.mouseStartX, lIIIIllllIlIIII.mouseStartY, (double)lIIIIllllIIlIII - lIIIIllllIlIIII.mouseStartX, (double)lIIIIllllIIllIl - lIIIIllllIlIIII.mouseStartY, lIIIIllllIlIIII.HOVER_BG_COLOR, lIIIIllllIlIIII.HOVER_OL_COLOR);
                }
            }
            Renderer.NORMAL.end();
            Utils.scaledProjection();
        }

        public HudScreen(GuiTheme lIIIlIIIllIlllI, Tab lIIIlIIIllIllIl) {
            super(lIIIlIIIllIlllI, lIIIlIIIllIllIl);
            HudScreen lIIIlIIIlllIIlI;
            lIIIlIIIlllIIlI.HOVER_BG_COLOR = new Color(200, 200, 200, 50);
            lIIIlIIIlllIIlI.HOVER_OL_COLOR = new Color(200, 200, 200, 200);
            lIIIlIIIlllIIlI.INACTIVE_BG_COLOR = new Color(200, 25, 25, 50);
            lIIIlIIIlllIIlI.INACTIVE_OL_COLOR = new Color(200, 25, 25, 200);
            lIIIlIIIlllIIlI.selectedElements = new ArrayList<HudElement>();
            lIIIlIIIlllIIlI.hud = Modules.get().get(HUD.class);
        }

        private boolean isPointBetween(double lIIIIlllllIIlII, double lIIIIlllllIIIll, double lIIIIlllllIIlIl) {
            return lIIIIlllllIIlIl >= lIIIIlllllIIlII && lIIIIlllllIIlIl <= lIIIIlllllIIlII + lIIIIlllllIIIll;
        }

        private void renderElement(HudElement lIIIIlllIllllIl, Color lIIIIlllIllllII, Color lIIIIlllIllIlll) {
            HudScreen lIIIIlllIlllllI;
            lIIIIlllIlllllI.renderQuad(lIIIIlllIllllIl.box.getX(), lIIIIlllIllllIl.box.getY(), lIIIIlllIllllIl.box.width, lIIIIlllIllllIl.box.height, lIIIIlllIllllII, lIIIIlllIllIlll);
        }

        @Override
        public boolean mouseClicked(double lIIIlIIIllIIllI, double lIIIlIIIllIIIII, int lIIIlIIIllIIlII) {
            HudScreen lIIIlIIIllIIlll;
            if (lIIIlIIIllIIlll.hoveredModule != null) {
                if (lIIIlIIIllIIlII == 1) {
                    if (!lIIIlIIIllIIlll.selectedElements.isEmpty()) {
                        lIIIlIIIllIIlll.selectedElements.clear();
                    }
                    MinecraftClient.getInstance().openScreen((Screen)new HudElementScreen(lIIIlIIIllIIlll.theme, lIIIlIIIllIIlll.hoveredModule));
                } else {
                    lIIIlIIIllIIlll.dragging = true;
                    lIIIlIIIllIIlll.dragged = false;
                    if (!lIIIlIIIllIIlll.selectedElements.contains(lIIIlIIIllIIlll.hoveredModule)) {
                        lIIIlIIIllIIlll.selectedElements.clear();
                        lIIIlIIIllIIlll.selectedElements.add(lIIIlIIIllIIlll.hoveredModule);
                    }
                }
                return true;
            }
            double lIIIlIIIllIIIll = MinecraftClient.getInstance().getWindow().getScaleFactor();
            lIIIlIIIllIIlll.selecting = true;
            lIIIlIIIllIIlll.mouseStartX = lIIIlIIIllIIllI * lIIIlIIIllIIIll;
            lIIIlIIIllIIlll.mouseStartY = lIIIlIIIllIIIII * lIIIlIIIllIIIll;
            if (!lIIIlIIIllIIlll.selectedElements.isEmpty()) {
                lIIIlIIIllIIlll.selectedElements.clear();
                return true;
            }
            return false;
        }

        @Override
        public boolean mouseReleased(double lIIIIllllIlllll, double lIIIIllllIllllI, int lIIIIllllIlllIl) {
            HudScreen lIIIIllllIlllII;
            if (lIIIIllllIlllII.dragging) {
                lIIIIllllIlllII.dragging = false;
                if (!lIIIIllllIlllII.dragged && lIIIIllllIlllII.hoveredModule != null) {
                    if (!lIIIIllllIlllII.selectedElements.isEmpty()) {
                        lIIIIllllIlllII.selectedElements.clear();
                    }
                    lIIIIllllIlllII.hoveredModule.toggle();
                }
                if (lIIIIllllIlllII.selectedElements.size() <= 1) {
                    lIIIIllllIlllII.selectedElements.clear();
                }
                return true;
            }
            if (lIIIIllllIlllII.selecting) {
                lIIIIllllIlllII.selecting = false;
                return true;
            }
            return false;
        }

        private boolean isInSelection(double lIIIlIIIlIIllll, double lIIIlIIIlIIlllI, double lIIIlIIIlIIIlII, double lIIIlIIIlIIIIll) {
            double lIIIlIIIlIIlIII;
            double lIIIlIIIlIIlIlI;
            double lIIIlIIIlIIlIIl;
            double lIIIlIIIlIIlIll;
            HudScreen lIIIlIIIlIlIIII;
            if (lIIIlIIIlIIllll >= lIIIlIIIlIlIIII.mouseStartX) {
                double lIIIlIIIlIlIlII = lIIIlIIIlIlIIII.mouseStartX;
                double lIIIlIIIlIlIIll = lIIIlIIIlIIllll - lIIIlIIIlIlIIII.mouseStartX;
            } else {
                lIIIlIIIlIIlIll = lIIIlIIIlIIllll;
                lIIIlIIIlIIlIIl = lIIIlIIIlIlIIII.mouseStartX - lIIIlIIIlIIllll;
            }
            if (lIIIlIIIlIIlllI >= lIIIlIIIlIlIIII.mouseStartY) {
                double lIIIlIIIlIlIIlI = lIIIlIIIlIlIIII.mouseStartY;
                double lIIIlIIIlIlIIIl = lIIIlIIIlIIlllI - lIIIlIIIlIlIIII.mouseStartY;
            } else {
                lIIIlIIIlIIlIlI = lIIIlIIIlIIlllI;
                lIIIlIIIlIIlIII = lIIIlIIIlIlIIII.mouseStartY - lIIIlIIIlIIlllI;
            }
            return lIIIlIIIlIIIlII >= lIIIlIIIlIIlIll && lIIIlIIIlIIIlII <= lIIIlIIIlIIlIll + lIIIlIIIlIIlIIl && lIIIlIIIlIIIIll >= lIIIlIIIlIIlIlI && lIIIlIIIlIIIIll <= lIIIlIIIlIIlIlI + lIIIlIIIlIIlIII;
        }

        private void renderQuad(double lIIIIlllIlIlIIl, double lIIIIlllIlIlIII, double lIIIIlllIlIIlll, double lIIIIlllIlIllII, Color lIIIIlllIlIIlIl, Color lIIIIlllIlIlIlI) {
            Renderer.NORMAL.quad(lIIIIlllIlIlIIl, lIIIIlllIlIlIII, lIIIIlllIlIIlll, lIIIIlllIlIllII, lIIIIlllIlIIlIl);
            Renderer.NORMAL.quad(lIIIIlllIlIlIIl - 1.0, lIIIIlllIlIlIII - 1.0, lIIIIlllIlIIlll + 2.0, 1.0, lIIIIlllIlIlIlI);
            Renderer.NORMAL.quad(lIIIIlllIlIlIIl - 1.0, lIIIIlllIlIlIII + lIIIIlllIlIllII - 1.0, lIIIIlllIlIIlll + 2.0, 1.0, lIIIIlllIlIlIlI);
            Renderer.NORMAL.quad(lIIIIlllIlIlIIl - 1.0, lIIIIlllIlIlIII, 1.0, lIIIIlllIlIllII, lIIIIlllIlIlIlI);
            Renderer.NORMAL.quad(lIIIIlllIlIlIIl + lIIIIlllIlIIlll, lIIIIlllIlIlIII, 1.0, lIIIIlllIlIllII, lIIIIlllIlIlIlI);
        }
    }
}

