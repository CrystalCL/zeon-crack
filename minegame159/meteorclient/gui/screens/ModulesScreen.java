/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Items
 *  net.minecraft.util.Pair
 */
package minegame159.meteorclient.gui.screens;

import java.util.List;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.tabs.TabScreen;
import minegame159.meteorclient.gui.tabs.Tabs;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WContainer;
import minegame159.meteorclient.gui.widgets.containers.WSection;
import minegame159.meteorclient.gui.widgets.containers.WVerticalList;
import minegame159.meteorclient.gui.widgets.containers.WWindow;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.systems.modules.Category;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.item.Items;
import net.minecraft.util.Pair;

public class ModulesScreen
extends TabScreen {
    protected void createSearchW(WContainer lIllIIIIlIlIIll, String lIllIIIIlIlIIlI) {
        if (!lIllIIIIlIlIIlI.isEmpty()) {
            ModulesScreen lIllIIIIlIlIlII;
            List<Pair<Module, Integer>> lIllIIIIlIlIlIl = Modules.get().searchTitles(lIllIIIIlIlIIlI);
            if (lIllIIIIlIlIlIl.size() > 0) {
                WSection lIllIIIIlIllIII = lIllIIIIlIlIIll.add(lIllIIIIlIlIlII.theme.section("Modules")).expandX().widget();
                lIllIIIIlIllIII.spacing = 0.0;
                for (Pair<Module, Integer> lIllIIIIlIllIIl : lIllIIIIlIlIlIl) {
                    lIllIIIIlIllIII.add(lIllIIIIlIlIlII.theme.module((Module)lIllIIIIlIllIIl.getLeft())).expandX();
                }
            }
            if ((lIllIIIIlIlIlIl = Modules.get().searchSettingTitles(lIllIIIIlIlIIlI)).size() > 0) {
                WSection lIllIIIIlIlIllI = lIllIIIIlIlIIll.add(lIllIIIIlIlIlII.theme.section("Settings")).expandX().widget();
                lIllIIIIlIlIllI.spacing = 0.0;
                for (Pair<Module, Integer> lIllIIIIlIlIlll : lIllIIIIlIlIlIl) {
                    lIllIIIIlIlIllI.add(lIllIIIIlIlIlII.theme.module((Module)lIllIIIIlIlIlll.getLeft())).expandX();
                }
            }
        }
    }

    protected WCategoryController createCategoryContainer() {
        ModulesScreen lIllIIIIlllIIlI;
        return lIllIIIIlllIIlI.new WCategoryController();
    }

    public ModulesScreen(GuiTheme lIllIIIIlllIllI) {
        super(lIllIIIIlllIllI, Tabs.get().get(0));
        ModulesScreen lIllIIIIllllIlI;
        lIllIIIIllllIlI.add(lIllIIIIllllIlI.createCategoryContainer());
        WVerticalList lIllIIIIllllIII = lIllIIIIllllIlI.add(lIllIIIIlllIllI.verticalList()).pad(4.0).bottom().widget();
        lIllIIIIllllIII.add(lIllIIIIlllIllI.label("Left click - Toggle module"));
        lIllIIIIllllIII.add(lIllIIIIlllIllI.label("Right click - Open module settings"));
    }

    protected void createSearch(WContainer lIllIIIIIllllll) {
        ModulesScreen lIllIIIIlIIIIII;
        WWindow lIllIIIIlIIIIll = lIllIIIIlIIIIII.theme.window("Search");
        lIllIIIIlIIIIll.id = "search";
        if (lIllIIIIlIIIIII.theme.categoryIcons()) {
            lIllIIIIlIIIIll.beforeHeaderInit = lIllIIIIIlIllll -> {
                ModulesScreen lIllIIIIIlIlllI;
                lIllIIIIIlIllll.add(lIllIIIIIlIlllI.theme.item(Items.COMPASS.getDefaultStack())).pad(2.0);
            };
        }
        lIllIIIIIllllll.add(lIllIIIIlIIIIll);
        lIllIIIIlIIIIll.view.scrollOnlyWhenMouseOver = true;
        lIllIIIIlIIIIll.view.hasScrollBar = false;
        lIllIIIIlIIIIll.view.maxHeight -= 18.0;
        WVerticalList lIllIIIIlIIIIlI = lIllIIIIlIIIIII.theme.verticalList();
        WTextBox lIllIIIIlIIIIIl = lIllIIIIlIIIIll.add(lIllIIIIlIIIIII.theme.textBox("")).minWidth(140.0).widget();
        lIllIIIIlIIIIIl.setFocused(true);
        lIllIIIIlIIIIIl.action = () -> {
            ModulesScreen lIllIIIIIlllIII;
            lIllIIIIlIIIIlI.clear();
            lIllIIIIIlllIII.createSearchW(lIllIIIIlIIIIlI, lIllIIIIlIIIIIl.get());
        };
        lIllIIIIlIIIIll.add(lIllIIIIlIIIIlI);
        lIllIIIIlIIIIII.createSearchW(lIllIIIIlIIIIlI, lIllIIIIlIIIIIl.get());
    }

    protected void createCategory(WContainer lIllIIIIllIlIIl, Category lIllIIIIllIIlII) {
        ModulesScreen lIllIIIIllIIllI;
        WWindow lIllIIIIllIIlll = lIllIIIIllIIllI.theme.window(lIllIIIIllIIlII.name);
        lIllIIIIllIIlll.id = lIllIIIIllIIlII.name;
        lIllIIIIllIIlll.padding = 0.0;
        lIllIIIIllIIlll.spacing = 0.0;
        if (lIllIIIIllIIllI.theme.categoryIcons()) {
            lIllIIIIllIIlll.beforeHeaderInit = lIllIIIIIlIIlII -> {
                ModulesScreen lIllIIIIIlIIllI;
                lIllIIIIIlIIlII.add(lIllIIIIIlIIllI.theme.item(lIllIIIIIlIlIII.icon)).pad(2.0);
            };
        }
        lIllIIIIllIlIIl.add(lIllIIIIllIIlll);
        lIllIIIIllIIlll.view.scrollOnlyWhenMouseOver = true;
        lIllIIIIllIIlll.view.hasScrollBar = false;
        lIllIIIIllIIlll.view.spacing = 0.0;
        for (Module lIllIIIIllIlIll : Modules.get().getGroup(lIllIIIIllIIlII)) {
            lIllIIIIllIIlll.add(lIllIIIIllIIllI.theme.module((Module)lIllIIIIllIlIll)).expandX().widget().tooltip = lIllIIIIllIlIll.description;
        }
    }

    protected class WCategoryController
    extends WContainer {
        @Override
        protected void onCalculateWidgetPositions() {
            WCategoryController lllIIIllIlllllI;
            double lllIIIlllIIIIlI = lllIIIllIlllllI.theme.scale(4.0);
            double lllIIIlllIIIIIl = lllIIIllIlllllI.theme.scale(40.0);
            double lllIIIlllIIIIII = lllIIIllIlllllI.x + lllIIIlllIIIIlI;
            double lllIIIllIllllll = lllIIIllIlllllI.y;
            for (Cell lllIIIlllIIIlII : lllIIIllIlllllI.cells) {
                double lllIIIlllIIIllI = Utils.getWindowWidth();
                double lllIIIlllIIIlIl = Utils.getWindowHeight();
                if (lllIIIlllIIIIII + lllIIIlllIIIlII.width > lllIIIlllIIIllI) {
                    lllIIIlllIIIIII += lllIIIlllIIIIlI;
                    lllIIIllIllllll += lllIIIlllIIIIIl;
                }
                if (lllIIIlllIIIIII > lllIIIlllIIIllI && (lllIIIlllIIIIII = lllIIIlllIIIllI / 2.0 - lllIIIlllIIIlII.width / 2.0) < 0.0) {
                    lllIIIlllIIIIII = 0.0;
                }
                if (lllIIIllIllllll > lllIIIlllIIIlIl && (lllIIIllIllllll = lllIIIlllIIIlIl / 2.0 - lllIIIlllIIIlII.height / 2.0) < 0.0) {
                    lllIIIllIllllll = 0.0;
                }
                lllIIIlllIIIlII.x = lllIIIlllIIIIII;
                lllIIIlllIIIlII.y = lllIIIllIllllll;
                lllIIIlllIIIlII.width = ((WWidget)lllIIIlllIIIlII.widget()).width;
                lllIIIlllIIIlII.height = ((WWidget)lllIIIlllIIIlII.widget()).height;
                lllIIIlllIIIlII.alignWidget();
                lllIIIlllIIIIII += lllIIIlllIIIlII.width + lllIIIlllIIIIlI;
            }
        }

        @Override
        public void init() {
            WCategoryController lllIIIlllIlIIlI;
            for (Category lllIIIlllIlIlII : Modules.loopCategories()) {
                lllIIIlllIlIIlI.ModulesScreen.this.createCategory(lllIIIlllIlIIlI, lllIIIlllIlIlII);
            }
            lllIIIlllIlIIlI.ModulesScreen.this.createSearch(lllIIIlllIlIIlI);
        }

        protected WCategoryController() {
            WCategoryController lllIIIlllIllIIl;
        }
    }
}

