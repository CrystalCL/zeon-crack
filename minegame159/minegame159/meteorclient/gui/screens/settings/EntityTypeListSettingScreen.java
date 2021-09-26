/*
 * Decompiled with CFR 0.151.
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
    int hasMonster = 0;
    int hasMisc = 0;
    private String filterText = "";
    private final WTextBox filter;
    private final EntityTypeListSetting setting;
    private WSection ambient;
    int hasAmbient = 0;
    private WSection monsters;
    private WTable animalsT;
    private WTable monstersT;
    private WTable waterAnimalsT;
    private WTable ambientT;
    int hasAnimal = 0;
    private WSection misc;
    private WVerticalList list;
    int hasWaterAnimal = 0;
    private WSection waterAnimals;
    private WSection animals;
    private WTable miscT;

    private void lambda$initWidgets$7(List list, EntityType EntityType2) {
        int n = Utils.search(Names.get(EntityType2), this.filterText);
        if (n > 0) {
            list.add(new Pair((Object)EntityType2, (Object)n));
        }
    }

    private void lambda$initWidgets$6(List list, WCheckbox wCheckbox, List list2, WCheckbox wCheckbox2, List list3, WCheckbox wCheckbox3, List list4, WCheckbox wCheckbox4, List list5, WCheckbox wCheckbox5, EntityType EntityType2) {
        if (!this.setting.onlyAttackable || EntityUtils.isAttackable(EntityType2)) {
            switch (1.$SwitchMap$net$minecraft$entity$SpawnGroup[EntityType2.getSpawnGroup().ordinal()]) {
                case 1: {
                    list.add(EntityType2);
                    this.addEntityType(this.animalsT, wCheckbox, EntityType2);
                    break;
                }
                case 2: {
                    list2.add(EntityType2);
                    this.addEntityType(this.waterAnimalsT, wCheckbox2, EntityType2);
                    break;
                }
                case 3: {
                    list3.add(EntityType2);
                    this.addEntityType(this.monstersT, wCheckbox3, EntityType2);
                    break;
                }
                case 4: {
                    list4.add(EntityType2);
                    this.addEntityType(this.ambientT, wCheckbox4, EntityType2);
                    break;
                }
                case 5: {
                    list5.add(EntityType2);
                    this.addEntityType(this.miscT, wCheckbox5, EntityType2);
                }
            }
        }
    }

    private void lambda$initWidgets$4(List list, WCheckbox wCheckbox) {
        this.tableChecked(list, wCheckbox.checked);
    }

    private void lambda$addEntityType$9(WCheckbox wCheckbox, EntityType EntityType2, WCheckbox wCheckbox2) {
        if (wCheckbox.checked) {
            ((Object2BooleanMap)this.setting.get()).put((Object)EntityType2, true);
            switch (1.$SwitchMap$net$minecraft$entity$SpawnGroup[EntityType2.getSpawnGroup().ordinal()]) {
                case 1: {
                    if (this.hasAnimal == 0) {
                        wCheckbox2.checked = true;
                    }
                    ++this.hasAnimal;
                    break;
                }
                case 2: {
                    if (this.hasWaterAnimal == 0) {
                        wCheckbox2.checked = true;
                    }
                    ++this.hasWaterAnimal;
                    break;
                }
                case 3: {
                    if (this.hasMonster == 0) {
                        wCheckbox2.checked = true;
                    }
                    ++this.hasMonster;
                    break;
                }
                case 4: {
                    if (this.hasAmbient == 0) {
                        wCheckbox2.checked = true;
                    }
                    ++this.hasAmbient;
                    break;
                }
                case 5: {
                    if (this.hasMisc == 0) {
                        wCheckbox2.checked = true;
                    }
                    ++this.hasMisc;
                }
            }
        } else if (((Object2BooleanMap)this.setting.get()).removeBoolean((Object)EntityType2)) {
            switch (1.$SwitchMap$net$minecraft$entity$SpawnGroup[EntityType2.getSpawnGroup().ordinal()]) {
                case 1: {
                    --this.hasAnimal;
                    if (this.hasAnimal != 0) break;
                    wCheckbox2.checked = false;
                    break;
                }
                case 2: {
                    --this.hasWaterAnimal;
                    if (this.hasWaterAnimal != 0) break;
                    wCheckbox2.checked = false;
                    break;
                }
                case 3: {
                    --this.hasMonster;
                    if (this.hasMonster != 0) break;
                    wCheckbox2.checked = false;
                    break;
                }
                case 4: {
                    --this.hasAmbient;
                    if (this.hasAmbient != 0) break;
                    wCheckbox2.checked = false;
                    break;
                }
                case 5: {
                    --this.hasMisc;
                    if (this.hasMisc != 0) break;
                    wCheckbox2.checked = false;
                }
            }
        }
        this.setting.changed();
    }

    private void initWidgets() {
        int n;
        Object object2;
        this.hasMisc = 0;
        this.hasAmbient = 0;
        this.hasMonster = 0;
        this.hasWaterAnimal = 0;
        this.hasAnimal = 0;
        for (Object object2 : ((Object2BooleanMap)this.setting.get()).keySet()) {
            if (!((Object2BooleanMap)this.setting.get()).getBoolean(object2) || this.setting.onlyAttackable && !EntityUtils.isAttackable(object2)) continue;
            switch (1.$SwitchMap$net$minecraft$entity$SpawnGroup[object2.getSpawnGroup().ordinal()]) {
                case 1: {
                    ++this.hasAnimal;
                    break;
                }
                case 2: {
                    ++this.hasWaterAnimal;
                    break;
                }
                case 3: {
                    ++this.hasMonster;
                    break;
                }
                case 4: {
                    ++this.hasAmbient;
                    break;
                }
                case 5: {
                    ++this.hasMisc;
                }
            }
        }
        Object object3 = new ArrayList();
        object2 = this.theme.checkbox(this.hasAnimal > 0);
        this.animals = this.theme.section("Animals", this.animals != null && this.animals.isExpanded(), (WWidget)object2);
        object2.action = () -> this.lambda$initWidgets$1((List)object3, (WCheckbox)object2);
        Cell<WSection> cell = this.add(this.animals).expandX();
        this.animalsT = this.animals.add(this.theme.table()).expandX().widget();
        ArrayList arrayList = new ArrayList();
        WCheckbox wCheckbox = this.theme.checkbox(this.hasWaterAnimal > 0);
        this.waterAnimals = this.theme.section("Water Animals", this.waterAnimals != null && this.waterAnimals.isExpanded(), wCheckbox);
        wCheckbox.action = () -> this.lambda$initWidgets$2(arrayList, wCheckbox);
        Cell<WSection> cell2 = this.add(this.waterAnimals).expandX();
        this.waterAnimalsT = this.waterAnimals.add(this.theme.table()).expandX().widget();
        ArrayList arrayList2 = new ArrayList();
        WCheckbox wCheckbox2 = this.theme.checkbox(this.hasMonster > 0);
        this.monsters = this.theme.section("Monsters", this.monsters != null && this.monsters.isExpanded(), wCheckbox2);
        wCheckbox2.action = () -> this.lambda$initWidgets$3(arrayList2, wCheckbox2);
        Cell<WSection> cell3 = this.add(this.monsters).expandX();
        this.monstersT = this.monsters.add(this.theme.table()).expandX().widget();
        ArrayList arrayList3 = new ArrayList();
        WCheckbox wCheckbox3 = this.theme.checkbox(this.hasAmbient > 0);
        this.ambient = this.theme.section("Ambient", this.ambient != null && this.ambient.isExpanded(), wCheckbox3);
        wCheckbox3.action = () -> this.lambda$initWidgets$4(arrayList3, wCheckbox3);
        Cell<WSection> cell4 = this.add(this.ambient).expandX();
        this.ambientT = this.ambient.add(this.theme.table()).expandX().widget();
        ArrayList arrayList4 = new ArrayList();
        WCheckbox wCheckbox4 = this.theme.checkbox(this.hasMisc > 0);
        this.misc = this.theme.section("Misc", this.misc != null && this.misc.isExpanded(), wCheckbox4);
        wCheckbox4.action = () -> this.lambda$initWidgets$5(arrayList4, wCheckbox4);
        Cell<WSection> cell5 = this.add(this.misc).expandX();
        this.miscT = this.misc.add(this.theme.table()).expandX().widget();
        Consumer<EntityType> consumer = arg_0 -> this.lambda$initWidgets$6((List)object3, (WCheckbox)object2, arrayList, wCheckbox, arrayList2, wCheckbox2, arrayList3, wCheckbox3, arrayList4, wCheckbox4, arg_0);
        if (this.filterText.isEmpty()) {
            Registry.ENTITY_TYPE.forEach(consumer);
        } else {
            ArrayList<Pair> arrayList5 = new ArrayList<Pair>();
            Registry.ENTITY_TYPE.forEach(arg_0 -> this.lambda$initWidgets$7(arrayList5, arg_0));
            arrayList5.sort(Comparator.comparingInt(EntityTypeListSettingScreen::lambda$initWidgets$8));
            for (Pair Pair2 : arrayList5) {
                consumer.accept((EntityType)Pair2.getLeft());
            }
        }
        if (this.animalsT.cells.size() == 0) {
            this.list.cells.remove(cell);
        }
        if (this.waterAnimalsT.cells.size() == 0) {
            this.list.cells.remove(cell2);
        }
        if (this.monstersT.cells.size() == 0) {
            this.list.cells.remove(cell3);
        }
        if (this.ambientT.cells.size() == 0) {
            this.list.cells.remove(cell4);
        }
        if (this.miscT.cells.size() == 0) {
            this.list.cells.remove(cell5);
        }
        if ((n = (this.hasWaterAnimal + this.waterAnimals.cells.size() + this.monsters.cells.size() + this.ambient.cells.size() + this.misc.cells.size()) / 2) <= 20) {
            if (this.animalsT.cells.size() > 0) {
                this.animals.setExpanded(true);
            }
            if (this.waterAnimalsT.cells.size() > 0) {
                this.waterAnimals.setExpanded(true);
            }
            if (this.monstersT.cells.size() > 0) {
                this.monsters.setExpanded(true);
            }
            if (this.ambientT.cells.size() > 0) {
                this.ambient.setExpanded(true);
            }
            if (this.miscT.cells.size() > 0) {
                this.misc.setExpanded(true);
            }
        } else {
            if (this.animalsT.cells.size() > 0) {
                this.animals.setExpanded(false);
            }
            if (this.waterAnimalsT.cells.size() > 0) {
                this.waterAnimals.setExpanded(false);
            }
            if (this.monstersT.cells.size() > 0) {
                this.monsters.setExpanded(false);
            }
            if (this.ambientT.cells.size() > 0) {
                this.ambient.setExpanded(false);
            }
            if (this.miscT.cells.size() > 0) {
                this.misc.setExpanded(false);
            }
        }
    }

    @Override
    public <W extends WWidget> Cell<W> add(W w) {
        return this.list.add(w);
    }

    private void addEntityType(WTable wTable, WCheckbox wCheckbox, EntityType<?> EntityType2) {
        wTable.add(this.theme.label(Names.get(EntityType2)));
        WCheckbox wCheckbox2 = wTable.add(this.theme.checkbox(((Object2BooleanMap)this.setting.get()).getBoolean(EntityType2))).expandCellX().right().widget();
        wCheckbox2.action = () -> this.lambda$addEntityType$9(wCheckbox2, EntityType2, wCheckbox);
        wTable.row();
    }

    private static int lambda$initWidgets$8(Pair Pair2) {
        return -((Integer)Pair2.getRight()).intValue();
    }

    private void lambda$initWidgets$1(List list, WCheckbox wCheckbox) {
        this.tableChecked(list, wCheckbox.checked);
    }

    private void lambda$initWidgets$3(List list, WCheckbox wCheckbox) {
        this.tableChecked(list, wCheckbox.checked);
    }

    public EntityTypeListSettingScreen(GuiTheme guiTheme, EntityTypeListSetting entityTypeListSetting) {
        super(guiTheme, "Select entities");
        this.setting = entityTypeListSetting;
        this.filter = super.add(guiTheme.textBox("")).minWidth(400.0).expandX().widget();
        this.filter.setFocused(true);
        this.filter.action = this::lambda$new$0;
        this.list = super.add(guiTheme.verticalList()).expandX().widget();
        this.initWidgets();
    }

    private void lambda$initWidgets$5(List list, WCheckbox wCheckbox) {
        this.tableChecked(list, wCheckbox.checked);
    }

    private void lambda$initWidgets$2(List list, WCheckbox wCheckbox) {
        this.tableChecked(list, wCheckbox.checked);
    }

    private void lambda$new$0() {
        this.filterText = this.filter.get().trim();
        this.list.clear();
        this.initWidgets();
    }

    private void tableChecked(List<EntityType<?>> list, boolean bl) {
        boolean bl2 = false;
        for (EntityType<?> EntityType2 : list) {
            if (bl) {
                ((Object2BooleanMap)this.setting.get()).put(EntityType2, true);
                bl2 = true;
                continue;
            }
            if (!((Object2BooleanMap)this.setting.get()).removeBoolean(EntityType2)) continue;
            bl2 = true;
        }
        if (bl2) {
            this.list.clear();
            this.initWidgets();
            this.setting.changed();
        }
    }
}

