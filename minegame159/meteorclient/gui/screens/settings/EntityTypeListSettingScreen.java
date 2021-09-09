/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.entity.EntityType
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.util.Pair
 */
package minegame159.meteorclient.gui.screens.settings;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.utils.Cell;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WSection;
import minegame159.meteorclient.gui.widgets.containers.WTable;
import minegame159.meteorclient.gui.widgets.containers.WVerticalList;
import minegame159.meteorclient.gui.widgets.input.WTextBox;
import minegame159.meteorclient.gui.widgets.pressable.WCheckbox;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.misc.Names;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Pair;

public class EntityTypeListSettingScreen
extends WindowScreen {
    private /* synthetic */ WSection misc;
    private /* synthetic */ WTable animalsT;
    /* synthetic */ int hasMonster;
    /* synthetic */ int hasAmbient;
    /* synthetic */ int hasMisc;
    private /* synthetic */ WTable miscT;
    private /* synthetic */ WSection waterAnimals;
    private final /* synthetic */ EntityTypeListSetting setting;
    private /* synthetic */ String filterText;
    private /* synthetic */ WSection animals;
    private /* synthetic */ WSection monsters;
    private /* synthetic */ WVerticalList list;
    /* synthetic */ int hasWaterAnimal;
    private /* synthetic */ WTable waterAnimalsT;
    private /* synthetic */ WTable ambientT;
    /* synthetic */ int hasAnimal;
    private /* synthetic */ WSection ambient;
    private /* synthetic */ WTable monstersT;
    private final /* synthetic */ WTextBox filter;

    @Override
    public <W extends WWidget> Cell<W> add(W llIlIIIIlllIIll) {
        EntityTypeListSettingScreen llIlIIIIlllIlII;
        return llIlIIIIlllIlII.list.add(llIlIIIIlllIIll);
    }

    public EntityTypeListSettingScreen(GuiTheme llIlIIIIllllIlI, EntityTypeListSetting llIlIIIIllllIIl) {
        super(llIlIIIIllllIlI, "Select entities");
        EntityTypeListSettingScreen llIlIIIIllllIll;
        llIlIIIIllllIll.filterText = "";
        llIlIIIIllllIll.hasAnimal = 0;
        llIlIIIIllllIll.hasWaterAnimal = 0;
        llIlIIIIllllIll.hasMonster = 0;
        llIlIIIIllllIll.hasAmbient = 0;
        llIlIIIIllllIll.hasMisc = 0;
        llIlIIIIllllIll.setting = llIlIIIIllllIIl;
        llIlIIIIllllIll.filter = super.add(llIlIIIIllllIlI.textBox("")).minWidth(400.0).expandX().widget();
        llIlIIIIllllIll.filter.setFocused(true);
        llIlIIIIllllIll.filter.action = () -> {
            EntityTypeListSettingScreen llIIlllIlllIlII;
            llIIlllIlllIlII.filterText = llIIlllIlllIlII.filter.get().trim();
            llIIlllIlllIlII.list.clear();
            llIIlllIlllIlII.initWidgets();
        };
        llIlIIIIllllIll.list = super.add(llIlIIIIllllIlI.verticalList()).expandX().widget();
        llIlIIIIllllIll.initWidgets();
    }

    private void initWidgets() {
        int llIlIIIIIIlIlll;
        EntityTypeListSettingScreen llIlIIIIIIlIllI;
        llIlIIIIIIlIllI.hasMisc = 0;
        llIlIIIIIIlIllI.hasAmbient = 0;
        llIlIIIIIIlIllI.hasMonster = 0;
        llIlIIIIIIlIllI.hasWaterAnimal = 0;
        llIlIIIIIIlIllI.hasAnimal = 0;
        for (EntityType llIlIIIIIlIlIll : ((Object2BooleanMap)llIlIIIIIIlIllI.setting.get()).keySet()) {
            if (!((Object2BooleanMap)llIlIIIIIIlIllI.setting.get()).getBoolean((Object)llIlIIIIIlIlIll) || llIlIIIIIIlIllI.setting.onlyAttackable && !EntityUtils.isAttackable(llIlIIIIIlIlIll)) continue;
            switch (llIlIIIIIlIlIll.getSpawnGroup()) {
                case CREATURE: {
                    ++llIlIIIIIIlIllI.hasAnimal;
                    break;
                }
                case WATER_CREATURE: {
                    ++llIlIIIIIIlIllI.hasWaterAnimal;
                    break;
                }
                case MONSTER: {
                    ++llIlIIIIIIlIllI.hasMonster;
                    break;
                }
                case AMBIENT: {
                    ++llIlIIIIIIlIllI.hasAmbient;
                    break;
                }
                case MISC: {
                    ++llIlIIIIIIlIllI.hasMisc;
                }
            }
        }
        ArrayList llIlIIIIIlIIlll = new ArrayList();
        WCheckbox llIlIIIIIlIIllI = llIlIIIIIIlIllI.theme.checkbox(llIlIIIIIIlIllI.hasAnimal > 0);
        llIlIIIIIIlIllI.animals = llIlIIIIIIlIllI.theme.section("Animals", llIlIIIIIIlIllI.animals != null && llIlIIIIIIlIllI.animals.isExpanded(), llIlIIIIIlIIllI);
        llIlIIIIIlIIllI.action = () -> {
            EntityTypeListSettingScreen llIIlllIllllIIl;
            llIIlllIllllIIl.tableChecked(llIlIIIIIlIIlll, llIIlllIllllIlI.checked);
        };
        Cell<WSection> llIlIIIIIlIIlIl = llIlIIIIIIlIllI.add(llIlIIIIIIlIllI.animals).expandX();
        llIlIIIIIIlIllI.animalsT = llIlIIIIIIlIllI.animals.add(llIlIIIIIIlIllI.theme.table()).expandX().widget();
        ArrayList llIlIIIIIlIIlII = new ArrayList();
        WCheckbox llIlIIIIIlIIIll = llIlIIIIIIlIllI.theme.checkbox(llIlIIIIIIlIllI.hasWaterAnimal > 0);
        llIlIIIIIIlIllI.waterAnimals = llIlIIIIIIlIllI.theme.section("Water Animals", llIlIIIIIIlIllI.waterAnimals != null && llIlIIIIIIlIllI.waterAnimals.isExpanded(), llIlIIIIIlIIIll);
        llIlIIIIIlIIIll.action = () -> {
            EntityTypeListSettingScreen llIIllllIIIIIlI;
            llIIllllIIIIIlI.tableChecked(llIlIIIIIlIIlII, llIIllllIIIIIll.checked);
        };
        Cell<WSection> llIlIIIIIlIIIlI = llIlIIIIIIlIllI.add(llIlIIIIIIlIllI.waterAnimals).expandX();
        llIlIIIIIIlIllI.waterAnimalsT = llIlIIIIIIlIllI.waterAnimals.add(llIlIIIIIIlIllI.theme.table()).expandX().widget();
        ArrayList llIlIIIIIlIIIIl = new ArrayList();
        WCheckbox llIlIIIIIlIIIII = llIlIIIIIIlIllI.theme.checkbox(llIlIIIIIIlIllI.hasMonster > 0);
        llIlIIIIIIlIllI.monsters = llIlIIIIIIlIllI.theme.section("Monsters", llIlIIIIIIlIllI.monsters != null && llIlIIIIIIlIllI.monsters.isExpanded(), llIlIIIIIlIIIII);
        llIlIIIIIlIIIII.action = () -> {
            EntityTypeListSettingScreen llIIllllIIIlllI;
            llIIllllIIIlllI.tableChecked(llIlIIIIIlIIIIl, llIIllllIIIllII.checked);
        };
        Cell<WSection> llIlIIIIIIlllll = llIlIIIIIIlIllI.add(llIlIIIIIIlIllI.monsters).expandX();
        llIlIIIIIIlIllI.monstersT = llIlIIIIIIlIllI.monsters.add(llIlIIIIIIlIllI.theme.table()).expandX().widget();
        ArrayList llIlIIIIIIllllI = new ArrayList();
        WCheckbox llIlIIIIIIlllIl = llIlIIIIIIlIllI.theme.checkbox(llIlIIIIIIlIllI.hasAmbient > 0);
        llIlIIIIIIlIllI.ambient = llIlIIIIIIlIllI.theme.section("Ambient", llIlIIIIIIlIllI.ambient != null && llIlIIIIIIlIllI.ambient.isExpanded(), llIlIIIIIIlllIl);
        llIlIIIIIIlllIl.action = () -> {
            EntityTypeListSettingScreen llIIllllIIlIlll;
            llIIllllIIlIlll.tableChecked(llIlIIIIIIllllI, llIIllllIIlIlIl.checked);
        };
        Cell<WSection> llIlIIIIIIlllII = llIlIIIIIIlIllI.add(llIlIIIIIIlIllI.ambient).expandX();
        llIlIIIIIIlIllI.ambientT = llIlIIIIIIlIllI.ambient.add(llIlIIIIIIlIllI.theme.table()).expandX().widget();
        ArrayList llIlIIIIIIllIll = new ArrayList();
        WCheckbox llIlIIIIIIllIlI = llIlIIIIIIlIllI.theme.checkbox(llIlIIIIIIlIllI.hasMisc > 0);
        llIlIIIIIIlIllI.misc = llIlIIIIIIlIllI.theme.section("Misc", llIlIIIIIIlIllI.misc != null && llIlIIIIIIlIllI.misc.isExpanded(), llIlIIIIIIllIlI);
        llIlIIIIIIllIlI.action = () -> {
            EntityTypeListSettingScreen llIIllllIIlllIl;
            llIIllllIIlllIl.tableChecked(llIlIIIIIIllIll, llIIllllIIllIll.checked);
        };
        Cell<WSection> llIlIIIIIIllIIl = llIlIIIIIIlIllI.add(llIlIIIIIIlIllI.misc).expandX();
        llIlIIIIIIlIllI.miscT = llIlIIIIIIlIllI.misc.add(llIlIIIIIIlIllI.theme.table()).expandX().widget();
        Consumer<EntityType> llIlIIIIIIllIII = llIIllllIllIIII -> {
            EntityTypeListSettingScreen llIIllllIlIllll;
            if (!llIIllllIlIllll.setting.onlyAttackable || EntityUtils.isAttackable(llIIllllIllIIII)) {
                switch (llIIllllIllIIII.getSpawnGroup()) {
                    case CREATURE: {
                        llIlIIIIIlIIlll.add(llIIllllIllIIII);
                        llIIllllIlIllll.addEntityType(llIIllllIlIllll.animalsT, llIlIIIIIlIIllI, (EntityType<?>)llIIllllIllIIII);
                        break;
                    }
                    case WATER_CREATURE: {
                        llIlIIIIIlIIlII.add(llIIllllIllIIII);
                        llIIllllIlIllll.addEntityType(llIIllllIlIllll.waterAnimalsT, llIlIIIIIlIIIll, (EntityType<?>)llIIllllIllIIII);
                        break;
                    }
                    case MONSTER: {
                        llIlIIIIIlIIIIl.add(llIIllllIllIIII);
                        llIIllllIlIllll.addEntityType(llIIllllIlIllll.monstersT, llIlIIIIIlIIIII, (EntityType<?>)llIIllllIllIIII);
                        break;
                    }
                    case AMBIENT: {
                        llIlIIIIIIllllI.add(llIIllllIllIIII);
                        llIIllllIlIllll.addEntityType(llIIllllIlIllll.ambientT, llIlIIIIIIlllIl, (EntityType<?>)llIIllllIllIIII);
                        break;
                    }
                    case MISC: {
                        llIlIIIIIIllIll.add(llIIllllIllIIII);
                        llIIllllIlIllll.addEntityType(llIIllllIlIllll.miscT, llIlIIIIIIllIlI, (EntityType<?>)llIIllllIllIIII);
                    }
                }
            }
        };
        if (llIlIIIIIIlIllI.filterText.isEmpty()) {
            Registry.ENTITY_TYPE.forEach(llIlIIIIIIllIII);
        } else {
            ArrayList<Pair> llIlIIIIIlIlIIl = new ArrayList<Pair>();
            Registry.ENTITY_TYPE.forEach(llIIlllllIIllIl -> {
                EntityTypeListSettingScreen llIIlllllIIllll;
                int llIIlllllIIllII = Utils.search(Names.get(llIIlllllIIllIl), llIIlllllIIllll.filterText);
                if (llIIlllllIIllII > 0) {
                    llIlIIIIIlIlIIl.add(new Pair(llIIlllllIIllIl, (Object)llIIlllllIIllII));
                }
            });
            llIlIIIIIlIlIIl.sort(Comparator.comparingInt(llIIlllllIlIlIl -> -((Integer)llIIlllllIlIlIl.getRight()).intValue()));
            for (Pair llIlIIIIIlIlIlI : llIlIIIIIlIlIIl) {
                llIlIIIIIIllIII.accept((EntityType)llIlIIIIIlIlIlI.getLeft());
            }
        }
        if (llIlIIIIIIlIllI.animalsT.cells.size() == 0) {
            llIlIIIIIIlIllI.list.cells.remove(llIlIIIIIlIIlIl);
        }
        if (llIlIIIIIIlIllI.waterAnimalsT.cells.size() == 0) {
            llIlIIIIIIlIllI.list.cells.remove(llIlIIIIIlIIIlI);
        }
        if (llIlIIIIIIlIllI.monstersT.cells.size() == 0) {
            llIlIIIIIIlIllI.list.cells.remove(llIlIIIIIIlllll);
        }
        if (llIlIIIIIIlIllI.ambientT.cells.size() == 0) {
            llIlIIIIIIlIllI.list.cells.remove(llIlIIIIIIlllII);
        }
        if (llIlIIIIIIlIllI.miscT.cells.size() == 0) {
            llIlIIIIIIlIllI.list.cells.remove(llIlIIIIIIllIIl);
        }
        if ((llIlIIIIIIlIlll = (llIlIIIIIIlIllI.hasWaterAnimal + llIlIIIIIIlIllI.waterAnimals.cells.size() + llIlIIIIIIlIllI.monsters.cells.size() + llIlIIIIIIlIllI.ambient.cells.size() + llIlIIIIIIlIllI.misc.cells.size()) / 2) <= 20) {
            if (llIlIIIIIIlIllI.animalsT.cells.size() > 0) {
                llIlIIIIIIlIllI.animals.setExpanded(true);
            }
            if (llIlIIIIIIlIllI.waterAnimalsT.cells.size() > 0) {
                llIlIIIIIIlIllI.waterAnimals.setExpanded(true);
            }
            if (llIlIIIIIIlIllI.monstersT.cells.size() > 0) {
                llIlIIIIIIlIllI.monsters.setExpanded(true);
            }
            if (llIlIIIIIIlIllI.ambientT.cells.size() > 0) {
                llIlIIIIIIlIllI.ambient.setExpanded(true);
            }
            if (llIlIIIIIIlIllI.miscT.cells.size() > 0) {
                llIlIIIIIIlIllI.misc.setExpanded(true);
            }
        } else {
            if (llIlIIIIIIlIllI.animalsT.cells.size() > 0) {
                llIlIIIIIIlIllI.animals.setExpanded(false);
            }
            if (llIlIIIIIIlIllI.waterAnimalsT.cells.size() > 0) {
                llIlIIIIIIlIllI.waterAnimals.setExpanded(false);
            }
            if (llIlIIIIIIlIllI.monstersT.cells.size() > 0) {
                llIlIIIIIIlIllI.monsters.setExpanded(false);
            }
            if (llIlIIIIIIlIllI.ambientT.cells.size() > 0) {
                llIlIIIIIIlIllI.ambient.setExpanded(false);
            }
            if (llIlIIIIIIlIllI.miscT.cells.size() > 0) {
                llIlIIIIIIlIllI.misc.setExpanded(false);
            }
        }
    }

    private void tableChecked(List<EntityType<?>> llIIllllllllIlI, boolean llIIllllllllIIl) {
        EntityTypeListSettingScreen llIIllllllllIll;
        boolean llIIllllllllIII = false;
        for (EntityType<?> llIIlllllllllII : llIIllllllllIlI) {
            if (llIIllllllllIIl) {
                ((Object2BooleanMap)llIIllllllllIll.setting.get()).put(llIIlllllllllII, true);
                llIIllllllllIII = true;
                continue;
            }
            if (!((Object2BooleanMap)llIIllllllllIll.setting.get()).removeBoolean(llIIlllllllllII)) continue;
            llIIllllllllIII = true;
        }
        if (llIIllllllllIII) {
            llIIllllllllIll.list.clear();
            llIIllllllllIll.initWidgets();
            llIIllllllllIll.setting.changed();
        }
    }

    private void addEntityType(WTable llIIllllllIIllI, WCheckbox llIIllllllIIlIl, EntityType<?> llIIllllllIIlII) {
        EntityTypeListSettingScreen llIIllllllIllII;
        llIIllllllIIllI.add(llIIllllllIllII.theme.label(Names.get(llIIllllllIIlII)));
        WCheckbox llIIllllllIlIII = llIIllllllIIllI.add(llIIllllllIllII.theme.checkbox(((Object2BooleanMap)llIIllllllIllII.setting.get()).getBoolean(llIIllllllIIlII))).expandCellX().right().widget();
        llIIllllllIlIII.action = () -> {
            EntityTypeListSettingScreen llIIlllllIllIlI;
            if (llIIlllllIlllIl.checked) {
                ((Object2BooleanMap)llIIlllllIllIlI.setting.get()).put((Object)llIIllllllIIlII, true);
                switch (llIIllllllIIlII.getSpawnGroup()) {
                    case CREATURE: {
                        if (llIIlllllIllIlI.hasAnimal == 0) {
                            llIIlllllIlIlll.checked = true;
                        }
                        ++llIIlllllIllIlI.hasAnimal;
                        break;
                    }
                    case WATER_CREATURE: {
                        if (llIIlllllIllIlI.hasWaterAnimal == 0) {
                            llIIlllllIlIlll.checked = true;
                        }
                        ++llIIlllllIllIlI.hasWaterAnimal;
                        break;
                    }
                    case MONSTER: {
                        if (llIIlllllIllIlI.hasMonster == 0) {
                            llIIlllllIlIlll.checked = true;
                        }
                        ++llIIlllllIllIlI.hasMonster;
                        break;
                    }
                    case AMBIENT: {
                        if (llIIlllllIllIlI.hasAmbient == 0) {
                            llIIlllllIlIlll.checked = true;
                        }
                        ++llIIlllllIllIlI.hasAmbient;
                        break;
                    }
                    case MISC: {
                        if (llIIlllllIllIlI.hasMisc == 0) {
                            llIIlllllIlIlll.checked = true;
                        }
                        ++llIIlllllIllIlI.hasMisc;
                    }
                }
            } else if (((Object2BooleanMap)llIIlllllIllIlI.setting.get()).removeBoolean((Object)llIIllllllIIlII)) {
                switch (llIIllllllIIlII.getSpawnGroup()) {
                    case CREATURE: {
                        --llIIlllllIllIlI.hasAnimal;
                        if (llIIlllllIllIlI.hasAnimal != 0) break;
                        llIIlllllIlIlll.checked = false;
                        break;
                    }
                    case WATER_CREATURE: {
                        --llIIlllllIllIlI.hasWaterAnimal;
                        if (llIIlllllIllIlI.hasWaterAnimal != 0) break;
                        llIIlllllIlIlll.checked = false;
                        break;
                    }
                    case MONSTER: {
                        --llIIlllllIllIlI.hasMonster;
                        if (llIIlllllIllIlI.hasMonster != 0) break;
                        llIIlllllIlIlll.checked = false;
                        break;
                    }
                    case AMBIENT: {
                        --llIIlllllIllIlI.hasAmbient;
                        if (llIIlllllIllIlI.hasAmbient != 0) break;
                        llIIlllllIlIlll.checked = false;
                        break;
                    }
                    case MISC: {
                        --llIIlllllIllIlI.hasMisc;
                        if (llIIlllllIllIlI.hasMisc != 0) break;
                        llIIlllllIlIlll.checked = false;
                    }
                }
            }
            llIIlllllIllIlI.setting.changed();
        };
        llIIllllllIIllI.row();
    }
}

