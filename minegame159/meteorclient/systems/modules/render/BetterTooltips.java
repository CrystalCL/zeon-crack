/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Formatting
 *  net.minecraft.entity.effect.StatusEffect
 *  net.minecraft.entity.effect.StatusEffectUtil
 *  net.minecraft.entity.effect.StatusEffectInstance
 *  net.minecraft.item.BlockItem
 *  net.minecraft.util.DyeColor
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.block.Block
 *  net.minecraft.block.ShulkerBoxBlock
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.text.LiteralText
 *  net.minecraft.text.TranslatableText
 *  net.minecraft.item.FoodComponent
 *  net.minecraft.text.MutableText
 */
package minegame159.meteorclient.systems.modules.render;

import java.io.DataOutput;
import java.io.IOException;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.GetTooltipEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.KeybindSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.ByteCountDataOutput;
import minegame159.meteorclient.utils.misc.Keybind;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.Formatting;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.util.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.item.FoodComponent;
import net.minecraft.text.MutableText;

public class BetterTooltips
extends Module {
    private final /* synthetic */ Setting<Boolean> shulkerColorFromType;
    public final /* synthetic */ Setting<Integer> mapsScale;
    private final /* synthetic */ Setting<Boolean> beehive;
    public final /* synthetic */ Setting<Boolean> echest;
    private final /* synthetic */ Setting<Boolean> shulkers;
    private final /* synthetic */ Setting<Boolean> useKbIfBigEnoughEnabled;
    public final /* synthetic */ Setting<Boolean> showVanilla;
    private final /* synthetic */ SettingGroup sgOther;
    private final /* synthetic */ SettingGroup sgByteSize;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Boolean> maps;
    private final /* synthetic */ Setting<Keybind> keybind;
    public final /* synthetic */ Setting<SettingColor> echestColor;
    private final /* synthetic */ SettingGroup sgMap;
    private final /* synthetic */ Setting<Boolean> statusEffects;
    private final /* synthetic */ SettingGroup sgEChest;
    public final /* synthetic */ Setting<Boolean> middleClickOpen;
    private final /* synthetic */ Setting<DisplayWhen> displayWhen;
    private final /* synthetic */ SettingGroup sgShulker;
    public final /* synthetic */ Setting<Boolean> byteSize;
    private final /* synthetic */ Setting<SettingColor> shulkersColor;

    public boolean previewMaps() {
        BetterTooltips lIllllIIlllIlIl;
        return lIllllIIlllIlIl.isActive() && lIllllIIlllIlIl.isPressed() && lIllllIIlllIlIl.maps.get() != false;
    }

    @EventHandler
    private void modifyTooltip(GetTooltipEvent.Modify lIllllIIIlIllll) {
        BetterTooltips lIllllIIIllIIII;
        if (BetterTooltips.hasItems(lIllllIIIlIllll.itemStack) && lIllllIIIllIIII.shulkers.get().booleanValue() && lIllllIIIllIIII.previewShulkers() || lIllllIIIlIllll.itemStack.getItem() == Items.ENDER_CHEST && lIllllIIIllIIII.echest.get().booleanValue() && lIllllIIIllIIII.previewEChest()) {
            for (int lIllllIIIllIIIl = 0; lIllllIIIllIIIl < lIllllIIIlIllll.list.size(); ++lIllllIIIllIIIl) {
                lIllllIIIlIllll.y -= 10;
            }
            lIllllIIIlIllll.y -= 4;
        }
    }

    public BetterTooltips() {
        super(Categories.Render, "better-tooltips", "Displays more useful tooltips for certain items.");
        BetterTooltips lIllllIIllllllI;
        lIllllIIllllllI.sgGeneral = lIllllIIllllllI.settings.getDefaultGroup();
        lIllllIIllllllI.sgShulker = lIllllIIllllllI.settings.createGroup("Shulker");
        lIllllIIllllllI.sgEChest = lIllllIIllllllI.settings.createGroup("EChest");
        lIllllIIllllllI.sgMap = lIllllIIllllllI.settings.createGroup("Map");
        lIllllIIllllllI.sgByteSize = lIllllIIllllllI.settings.createGroup("Byte Size");
        lIllllIIllllllI.sgOther = lIllllIIllllllI.settings.createGroup("Other");
        lIllllIIllllllI.displayWhen = lIllllIIllllllI.sgGeneral.add(new EnumSetting.Builder().name("display-when").description("When to display previews.").defaultValue(DisplayWhen.Keybind).build());
        lIllllIIllllllI.keybind = lIllllIIllllllI.sgGeneral.add(new KeybindSetting.Builder().name("keybind").description("The bind for keybind mode.").defaultValue(Keybind.fromKey(342)).build());
        lIllllIIllllllI.showVanilla = lIllllIIllllllI.sgGeneral.add(new BoolSetting.Builder().name("show-vanilla").description("Displays the vanilla tooltip as well as the preview.").defaultValue(true).build());
        lIllllIIllllllI.middleClickOpen = lIllllIIllllllI.sgGeneral.add(new BoolSetting.Builder().name("middle-click-open").description("Opens a GUI window with the inventory of the storage block when you middle click the item.").defaultValue(true).build());
        lIllllIIllllllI.shulkers = lIllllIIllllllI.sgShulker.add(new BoolSetting.Builder().name("shulker-preview").description("Shows a preview of a shulker box when hovering over it in an inventory.").defaultValue(true).build());
        lIllllIIllllllI.shulkersColor = lIllllIIllllllI.sgShulker.add(new ColorSetting.Builder().name("container-color").description("The color of the preview in container mode.").defaultValue(new SettingColor(255, 255, 255)).build());
        lIllllIIllllllI.shulkerColorFromType = lIllllIIllllllI.sgShulker.add(new BoolSetting.Builder().name("color-from-type").description("Color shulker preview according to the shulkers color.").defaultValue(true).build());
        lIllllIIllllllI.echest = lIllllIIllllllI.sgEChest.add(new BoolSetting.Builder().name("echest-preview").description("Shows a preview of your echest when hovering over it in an inventory.").defaultValue(true).build());
        lIllllIIllllllI.echestColor = lIllllIIllllllI.sgEChest.add(new ColorSetting.Builder().name("container-color").description("The color of the echest preview in container mode.").defaultValue(new SettingColor(0, 50, 50)).build());
        lIllllIIllllllI.maps = lIllllIIllllllI.sgMap.add(new BoolSetting.Builder().name("map-preview").description("Shows a preview of a map when hovering over it in an inventory.").defaultValue(true).build());
        lIllllIIllllllI.mapsScale = lIllllIIllllllI.sgMap.add(new IntSetting.Builder().name("scale").description("The scale of the map preview.").defaultValue(100).min(1).sliderMax(500).build());
        lIllllIIllllllI.byteSize = lIllllIIllllllI.sgByteSize.add(new BoolSetting.Builder().name("byte-size").description("Displays an item's size in bytes in the tooltip.").defaultValue(true).build());
        lIllllIIllllllI.useKbIfBigEnoughEnabled = lIllllIIllllllI.sgByteSize.add(new BoolSetting.Builder().name("use-kb-if-big-enough-enabled").description("Uses KB instead of bytes if your item's size is larger or equal to 1KB.").defaultValue(true).build());
        lIllllIIllllllI.statusEffects = lIllllIIllllllI.sgOther.add(new BoolSetting.Builder().name("status-effects").description("Adds list of status effects to tooltips of food items.").defaultValue(true).build());
        lIllllIIllllllI.beehive = lIllllIIllllllI.sgOther.add(new BoolSetting.Builder().name("beehive").description("Displays information about a beehive or bee nest.").defaultValue(true).build());
    }

    public boolean previewEChest() {
        BetterTooltips lIllllIIllllIII;
        return lIllllIIllllIII.isActive() && lIllllIIllllIII.isPressed() && lIllllIIllllIII.echest.get() != false;
    }

    private MutableText getStatusText(StatusEffectInstance lIllllIIIlIlIII) {
        TranslatableText lIllllIIIlIIlll = new TranslatableText(lIllllIIIlIlIII.getTranslationKey());
        if (lIllllIIIlIlIII.getAmplifier() != 0) {
            lIllllIIIlIIlll.append(String.format(" %d (%s)", lIllllIIIlIlIII.getAmplifier() + 1, StatusEffectUtil.durationToString((StatusEffectInstance)lIllllIIIlIlIII, (float)1.0f)));
        } else {
            lIllllIIIlIIlll.append(String.format(" (%s)", StatusEffectUtil.durationToString((StatusEffectInstance)lIllllIIIlIlIII, (float)1.0f)));
        }
        if (lIllllIIIlIlIII.getEffectType().isBeneficial()) {
            return lIllllIIIlIIlll.formatted(Formatting.BLUE);
        }
        return lIllllIIIlIIlll.formatted(Formatting.RED);
    }

    public boolean previewShulkers() {
        BetterTooltips lIllllIIlllllII;
        return lIllllIIlllllII.isActive() && lIllllIIlllllII.isPressed() && lIllllIIlllllII.shulkers.get() != false;
    }

    @EventHandler
    private void appendTooltip(GetTooltipEvent.Append lIllllIIIlllllI) {
        NbtCompound lIllllIIlIIIIII;
        BetterTooltips lIllllIIIllllll;
        if (lIllllIIIllllll.byteSize.get().booleanValue()) {
            try {
                String lIllllIIlIIlllI;
                lIllllIIIlllllI.itemStack.writeNbt(new NbtCompound()).write((DataOutput)ByteCountDataOutput.INSTANCE);
                int lIllllIIlIIllll = ByteCountDataOutput.INSTANCE.getCount();
                ByteCountDataOutput.INSTANCE.reset();
                if (lIllllIIIllllll.useKbIfBigEnoughEnabled.get().booleanValue() && lIllllIIlIIllll >= 1024) {
                    String lIllllIIlIlIIII = String.format("%.2f kb", Float.valueOf((float)lIllllIIlIIllll / 1024.0f));
                } else {
                    lIllllIIlIIlllI = String.format("%d bytes", lIllllIIlIIllll);
                }
                lIllllIIIlllllI.list.add(new LiteralText(String.valueOf(new StringBuilder().append((Object)Formatting.GRAY).append(lIllllIIlIIlllI))));
            }
            catch (IOException lIllllIIlIIllIl) {
                lIllllIIlIIllIl.printStackTrace();
            }
        }
        if (lIllllIIIllllll.statusEffects.get().booleanValue()) {
            FoodComponent lIllllIIlIIIlIl;
            if (lIllllIIIlllllI.itemStack.getItem() == Items.SUSPICIOUS_STEW) {
                NbtList lIllllIIlIIIlll;
                NbtCompound lIllllIIlIIIllI = lIllllIIIlllllI.itemStack.getTag();
                if (lIllllIIlIIIllI != null && (lIllllIIlIIIlll = lIllllIIlIIIllI.getList("Effects", 10)) != null) {
                    for (int lIllllIIlIIlIII = 0; lIllllIIlIIlIII < lIllllIIlIIIlll.size(); ++lIllllIIlIIlIII) {
                        NbtCompound lIllllIIlIIllII = lIllllIIlIIIlll.getCompound(lIllllIIlIIlIII);
                        byte lIllllIIlIIlIll = lIllllIIlIIllII.getByte("EffectId");
                        int lIllllIIlIIlIlI = lIllllIIlIIllII.contains("EffectDuration") ? lIllllIIlIIllII.getInt("EffectDuration") : 160;
                        StatusEffectInstance lIllllIIlIIlIIl = new StatusEffectInstance(StatusEffect.byRawId((int)lIllllIIlIIlIll), lIllllIIlIIlIlI, 0);
                        lIllllIIIlllllI.list.add(1, lIllllIIIllllll.getStatusText(lIllllIIlIIlIIl));
                    }
                }
            } else if (lIllllIIIlllllI.itemStack.getItem().isFood() && (lIllllIIlIIIlIl = lIllllIIIlllllI.itemStack.getItem().getFoodComponent()) != null) {
                lIllllIIlIIIlIl.getStatusEffects().forEach(lIllllIIIIllllI -> {
                    BetterTooltips lIllllIIIIlllII;
                    StatusEffectInstance lIllllIIIIlllIl = (StatusEffectInstance)lIllllIIIIllllI.getFirst();
                    lIllllIIIIllIll.list.add(1, lIllllIIIIlllII.getStatusText(lIllllIIIIlllIl));
                });
            }
        }
        if (lIllllIIIllllll.beehive.get().booleanValue() && (lIllllIIIlllllI.itemStack.getItem() == Items.BEEHIVE || lIllllIIIlllllI.itemStack.getItem() == Items.BEE_NEST) && (lIllllIIlIIIIII = lIllllIIIlllllI.itemStack.getTag()) != null) {
            NbtCompound lIllllIIlIIIIIl;
            NbtCompound lIllllIIlIIIIlI = lIllllIIlIIIIII.getCompound("BlockStateTag");
            if (lIllllIIlIIIIlI != null) {
                int lIllllIIlIIIlII = lIllllIIlIIIIlI.getInt("honey_level");
                lIllllIIIlllllI.list.add(1, new LiteralText(String.format("%sHoney level: %s%d%s.", new Object[]{Formatting.GRAY, Formatting.YELLOW, lIllllIIlIIIlII, Formatting.GRAY})));
            }
            if ((lIllllIIlIIIIIl = lIllllIIlIIIIII.getCompound("BlockEntityTag")) != null) {
                NbtList lIllllIIlIIIIll = lIllllIIlIIIIIl.getList("Bees", 10);
                lIllllIIIlllllI.list.add(1, new LiteralText(String.format("%sBees: %s%d%s.", new Object[]{Formatting.GRAY, Formatting.YELLOW, lIllllIIlIIIIll.size(), Formatting.GRAY})));
            }
        }
        if (BetterTooltips.hasItems(lIllllIIIlllllI.itemStack) && lIllllIIIllllll.shulkers.get() != false && !lIllllIIIllllll.previewShulkers() || lIllllIIIlllllI.itemStack.getItem() == Items.ENDER_CHEST && lIllllIIIllllll.echest.get() != false && !lIllllIIIllllll.previewEChest() || lIllllIIIlllllI.itemStack.getItem() == Items.FILLED_MAP && lIllllIIIllllll.maps.get().booleanValue() && !lIllllIIIllllll.previewMaps()) {
            lIllllIIIlllllI.list.add(new LiteralText(""));
            lIllllIIIlllllI.list.add(new LiteralText(String.valueOf(new StringBuilder().append("Hold ").append((Object)Formatting.YELLOW).append(lIllllIIIllllll.keybind).append((Object)Formatting.RESET).append(" to preview"))));
        }
    }

    private boolean isPressed() {
        BetterTooltips lIllllIIlllIIlI;
        return lIllllIIlllIIlI.keybind.get().isPressed() && lIllllIIlllIIlI.displayWhen.get() == DisplayWhen.Keybind || lIllllIIlllIIlI.displayWhen.get() == DisplayWhen.Always;
    }

    public static boolean hasItems(ItemStack lIllllIIlIllIll) {
        NbtCompound lIllllIIlIlllII = lIllllIIlIllIll.getSubTag("BlockEntityTag");
        return lIllllIIlIlllII != null && lIllllIIlIlllII.contains("Items", 9);
    }

    public Color getShulkerColor(ItemStack lIllllIIllIIlII) {
        BetterTooltips lIllllIIllIIlll;
        if (lIllllIIllIIlll.shulkerColorFromType.get().booleanValue()) {
            if (!(lIllllIIllIIlII.getItem() instanceof BlockItem)) {
                return lIllllIIllIIlll.shulkersColor.get();
            }
            Block lIllllIIllIlIll = ((BlockItem)lIllllIIllIIlII.getItem()).getBlock();
            if (!(lIllllIIllIlIll instanceof ShulkerBoxBlock)) {
                return lIllllIIllIIlll.shulkersColor.get();
            }
            ShulkerBoxBlock lIllllIIllIlIlI = (ShulkerBoxBlock)ShulkerBoxBlock.getBlockFromItem((Item)lIllllIIllIIlII.getItem());
            DyeColor lIllllIIllIlIIl = lIllllIIllIlIlI.getColor();
            if (lIllllIIllIlIIl == null) {
                return lIllllIIllIIlll.shulkersColor.get();
            }
            float[] lIllllIIllIlIII = lIllllIIllIlIIl.getColorComponents();
            return new Color(lIllllIIllIlIII[0], lIllllIIllIlIII[1], lIllllIIllIlIII[2], 1.0f);
        }
        return lIllllIIllIIlll.shulkersColor.get();
    }

    public static final class DisplayWhen
    extends Enum<DisplayWhen> {
        public static final /* synthetic */ /* enum */ DisplayWhen Always;
        public static final /* synthetic */ /* enum */ DisplayWhen Keybind;
        private static final /* synthetic */ DisplayWhen[] $VALUES;

        private static /* synthetic */ DisplayWhen[] $values() {
            return new DisplayWhen[]{Keybind, Always};
        }

        public static DisplayWhen valueOf(String llllllllllllllllllIlllIlllIIlIlI) {
            return Enum.valueOf(DisplayWhen.class, llllllllllllllllllIlllIlllIIlIlI);
        }

        private DisplayWhen() {
            DisplayWhen llllllllllllllllllIlllIlllIIIlIl;
        }

        public static DisplayWhen[] values() {
            return (DisplayWhen[])$VALUES.clone();
        }

        static {
            Keybind = new DisplayWhen();
            Always = new DisplayWhen();
            $VALUES = DisplayWhen.$values();
        }
    }
}

