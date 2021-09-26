/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.render.Render2DEvent;
import minegame159.meteorclient.rendering.DrawMode;
import minegame159.meteorclient.rendering.Renderer;
import minegame159.meteorclient.rendering.text.TextRenderer;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.ColorSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.EnchListSetting;
import minegame159.meteorclient.settings.EntityTypeListSetting;
import minegame159.meteorclient.settings.EnumSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.friends.Friends;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.player.FakePlayer;
import minegame159.meteorclient.systems.modules.player.NameProtect;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.entity.EntityUtils;
import minegame159.meteorclient.utils.entity.FakePlayerEntity;
import minegame159.meteorclient.utils.entity.FakePlayerUtils;
import minegame159.meteorclient.utils.misc.MeteorPlayers;
import minegame159.meteorclient.utils.misc.Vec3;
import minegame159.meteorclient.utils.render.NametagUtils;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.render.color.SettingColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.world.GameMode;
import net.minecraft.util.registry.Registry;
import net.minecraft.client.render.VertexFormats;
import org.lwjgl.opengl.GL11;

public class Nametags
extends Module {
    private final Setting<SettingColor> gmColor;
    private final Setting<SettingColor> distanceColor;
    private final Setting<SettingColor> otherHealthStage2;
    private final Setting<SettingColor> enchantmentTextColor;
    private final Setting<Boolean> displayDistance;
    private final Setting<SettingColor> healthStage3;
    private final Setting<List<Enchantment>> displayedEnchantments;
    private final double[] itemWidths;
    private final Setting<SettingColor> background;
    private final Map<Enchantment, Integer> enchantmentsToShowScale;
    private final Setting<SettingColor> otherHealthStage3;
    private final Setting<SettingColor> healthStage1;
    private final Setting<SettingColor> pingColor;
    private final Setting<Boolean> displayItems;
    private final Setting<Double> itemSpacing;
    private final Setting<Boolean> displayPing;
    private final Setting<Boolean> displayItemEnchants;
    private final Setting<Double> scale;
    private final Setting<Boolean> itemCount;
    private final Setting<Boolean> displayGameMode;
    private final Setting<SettingColor> meteorColor;
    private final Setting<Position> enchantPos;
    private final Setting<Integer> enchantLength;
    private final Vec3 pos;
    private final Color RED;
    private final SettingGroup sgOther;
    private final Setting<SettingColor> normalName;
    private final Setting<SettingColor> itemCountColor;
    private final Setting<SettingColor> itemNameColor;
    private final Setting<Boolean> displayMeteor;
    private final SettingGroup sgPlayers;
    private final Setting<SettingColor> healthStage2;
    private final Setting<SettingColor> otherHealthStage1;
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> yourself;
    private final Setting<Double> enchantTextScale;
    private final Setting<SettingColor> otherNameColor;
    private final SettingGroup sgItems;
    private final Setting<Object2BooleanMap<EntityType<?>>> entities;
    private final Setting<Boolean> ignoreEmpty;

    private void renderGenericNametag(LivingEntity LivingEntity2) {
        TextRenderer textRenderer = TextRenderer.get();
        NametagUtils.begin(this.pos);
        String string = LivingEntity2.getType().getName().getString();
        string = String.valueOf(new StringBuilder().append(string).append(" "));
        float f = LivingEntity2.getAbsorptionAmount();
        int n = Math.round(LivingEntity2.getHealth() + f);
        double d = (float)n / (LivingEntity2.getMaxHealth() + f);
        String string2 = String.valueOf(n);
        Color color = d <= 0.333 ? (Color)this.otherHealthStage3.get() : (d <= 0.666 ? (Color)this.otherHealthStage2.get() : (Color)this.otherHealthStage1.get());
        double d2 = textRenderer.getWidth(string);
        double d3 = textRenderer.getWidth(string2);
        double d4 = textRenderer.getHeight();
        double d5 = d2 + d3;
        double d6 = d5 / 2.0;
        this.drawBg(-d6, -d4, d5, d4);
        textRenderer.beginBig();
        double d7 = -d6;
        double d8 = -d4;
        d7 = textRenderer.render(string, d7, d8, this.otherNameColor.get());
        textRenderer.render(string2, d7, d8, color);
        textRenderer.end();
        NametagUtils.end();
    }

    private void renderNametagPlayer(PlayerEntity PlayerEntity2) {
        TextRenderer textRenderer = TextRenderer.get();
        NametagUtils.begin(this.pos);
        String string = "";
        if (this.displayMeteor.get().booleanValue() && MeteorPlayers.get(PlayerEntity2)) {
            string = "M ";
        }
        GameMode GameMode2 = EntityUtils.getGameMode(PlayerEntity2);
        String string2 = "ERR";
        if (GameMode2 != null) {
            switch (GameMode2) {
                case SPECTATOR: {
                    string2 = "Sp";
                    break;
                }
                case SURVIVAL: {
                    string2 = "S";
                    break;
                }
                case CREATIVE: {
                    string2 = "C";
                    break;
                }
                case ADVENTURE: {
                    string2 = "A";
                }
            }
        }
        string2 = String.valueOf(new StringBuilder().append("[").append(string2).append("] "));
        Color color = Friends.get().getFriendColor(PlayerEntity2);
        String string3 = PlayerEntity2 == this.mc.player ? Modules.get().get(NameProtect.class).getName(PlayerEntity2.getGameProfile().getName()) : PlayerEntity2.getGameProfile().getName();
        if (Modules.get().get(FakePlayer.class).showID(PlayerEntity2)) {
            string3 = String.valueOf(new StringBuilder().append(string3).append(" [").append(FakePlayerUtils.getID((FakePlayerEntity)PlayerEntity2)).append("]"));
        }
        string3 = String.valueOf(new StringBuilder().append(string3).append(" "));
        float f = PlayerEntity2.getAbsorptionAmount();
        int n = Math.round(PlayerEntity2.getHealth() + f);
        double d = (float)n / (PlayerEntity2.getMaxHealth() + f);
        String string4 = String.valueOf(n);
        Color color2 = d <= 0.333 ? (Color)this.healthStage3.get() : (d <= 0.666 ? (Color)this.healthStage2.get() : (Color)this.healthStage1.get());
        int n2 = EntityUtils.getPing(PlayerEntity2);
        String string5 = String.valueOf(new StringBuilder().append(" [").append(n2).append("ms]"));
        double d2 = (double)Math.round(Utils.distanceToCamera((Entity)PlayerEntity2) * 10.0) / 10.0;
        String string6 = String.valueOf(new StringBuilder().append(" ").append(d2).append("m"));
        double d3 = textRenderer.getWidth(string);
        double d4 = textRenderer.getWidth(string2);
        double d5 = textRenderer.getWidth(string3);
        double d6 = textRenderer.getWidth(string4);
        double d7 = textRenderer.getWidth(string5);
        double d8 = textRenderer.getWidth(string6);
        double d9 = d5 + d6;
        if (this.displayMeteor.get().booleanValue()) {
            d9 += d3;
        }
        if (this.displayGameMode.get().booleanValue()) {
            d9 += d4;
        }
        if (this.displayPing.get().booleanValue()) {
            d9 += d7;
        }
        if (this.displayDistance.get().booleanValue()) {
            d9 += d8;
        }
        double d10 = d9 / 2.0;
        double d11 = textRenderer.getHeight();
        this.drawBg(-d10, -d11, d9, d11);
        textRenderer.beginBig();
        double d12 = -d10;
        double d13 = -d11;
        if (this.displayMeteor.get().booleanValue()) {
            d12 = textRenderer.render(string, d12, d13, this.meteorColor.get());
        }
        if (this.displayGameMode.get().booleanValue()) {
            d12 = textRenderer.render(string2, d12, d13, this.gmColor.get());
        }
        d12 = textRenderer.render(string3, d12, d13, color != null ? color : (Color)this.normalName.get());
        d12 = textRenderer.render(string4, d12, d13, color2);
        if (this.displayPing.get().booleanValue()) {
            d12 = textRenderer.render(string5, d12, d13, this.pingColor.get());
        }
        if (this.displayDistance.get().booleanValue()) {
            textRenderer.render(string6, d12, d13, this.distanceColor.get());
        }
        textRenderer.end();
        if (this.displayItems.get().booleanValue()) {
            Arrays.fill(this.itemWidths, 0.0);
            boolean bl = false;
            int n3 = 0;
            for (int i = 0; i < 6; ++i) {
                ItemStack ItemStack2 = this.getItem(PlayerEntity2, i);
                if (!(this.itemWidths[i] != 0.0 || this.ignoreEmpty.get().booleanValue() && ItemStack2.isEmpty())) {
                    this.itemWidths[i] = 32.0 + this.itemSpacing.get();
                }
                if (!ItemStack2.isEmpty()) {
                    bl = true;
                }
                if (!this.displayItemEnchants.get().booleanValue()) continue;
                Map map = EnchantmentHelper.get((ItemStack)ItemStack2);
                this.enchantmentsToShowScale.clear();
                for (Enchantment d17 : this.displayedEnchantments.get()) {
                    if (!map.containsKey(d17)) continue;
                    this.enchantmentsToShowScale.put(d17, (Integer)map.get(d17));
                }
                for (Enchantment Enchantment2 : this.enchantmentsToShowScale.keySet()) {
                    String string7 = String.valueOf(new StringBuilder().append(Utils.getEnchantSimpleName(Enchantment2, this.enchantLength.get())).append(" ").append(this.enchantmentsToShowScale.get(Enchantment2)));
                    this.itemWidths[i] = Math.max(this.itemWidths[i], textRenderer.getWidth(string7) / 2.0);
                }
                n3 = Math.max(n3, this.enchantmentsToShowScale.size());
            }
            double d14 = bl ? 32 : 0;
            double d15 = 0.0;
            for (double d16 : this.itemWidths) {
                d15 += d16;
                if (-2 < 0) continue;
                return;
            }
            double d17 = d15 / 2.0;
            double d18 = -d11 - 7.0 - d14;
            double d19 = -d17;
            for (int i = 0; i < 6; ++i) {
                ItemStack ItemStack2 = this.getItem(PlayerEntity2, i);
                GL11.glPushMatrix();
                GL11.glScaled((double)2.0, (double)2.0, (double)1.0);
                this.mc.getItemRenderer().renderGuiItemIcon(ItemStack2, (int)(d19 / 2.0), (int)(d18 / 2.0));
                this.mc.getItemRenderer().renderGuiItemOverlay(this.mc.textRenderer, ItemStack2, (int)(d19 / 2.0), (int)(d18 / 2.0));
                GL11.glPopMatrix();
                if (n3 > 0 && this.displayItemEnchants.get().booleanValue()) {
                    textRenderer.begin(0.5 * this.enchantTextScale.get(), false, true);
                    Map map = EnchantmentHelper.get((ItemStack)ItemStack2);
                    HashMap<Enchantment, Integer> hashMap = new HashMap<Enchantment, Integer>();
                    for (Enchantment Enchantment3 : this.displayedEnchantments.get()) {
                        if (!map.containsKey(Enchantment3)) continue;
                        hashMap.put(Enchantment3, (Integer)map.get(Enchantment3));
                    }
                    double d20 = this.itemWidths[i];
                    double d21 = 0.0;
                    double d22 = 0.0;
                    switch (this.enchantPos.get()) {
                        case Above: {
                            d22 = -((double)(hashMap.size() + 1) * textRenderer.getHeight());
                            break;
                        }
                        case OnTop: {
                            d22 = (d14 - (double)hashMap.size() * textRenderer.getHeight()) / 2.0;
                        }
                    }
                    double d23 = d19;
                    for (Enchantment Enchantment4 : hashMap.keySet()) {
                        String string8 = String.valueOf(new StringBuilder().append(Utils.getEnchantSimpleName(Enchantment4, this.enchantLength.get())).append(" ").append(hashMap.get(Enchantment4)));
                        Color color3 = this.enchantmentTextColor.get();
                        if (Enchantment4.isCursed()) {
                            color3 = this.RED;
                        }
                        switch (this.enchantPos.get()) {
                            case Above: {
                                d23 = d19 + d20 / 2.0 - textRenderer.getWidth(string8) / 2.0;
                                break;
                            }
                            case OnTop: {
                                d23 = d19 + (d20 - textRenderer.getWidth(string8)) / 2.0;
                            }
                        }
                        textRenderer.render(string8, d23, d18 + d22 + d21, color3);
                        d21 += textRenderer.getHeight();
                    }
                    textRenderer.end();
                }
                d19 += this.itemWidths[i];
                if (1 < 2) continue;
                return;
            }
        } else if (this.displayItemEnchants.get().booleanValue()) {
            this.displayItemEnchants.set(false);
        }
        NametagUtils.end();
    }

    private static String ticksToTime(int n) {
        if (n > 72000) {
            int n2 = n / 20 / 3600;
            return String.valueOf(new StringBuilder().append(n2).append(" h"));
        }
        if (n > 1200) {
            int n3 = n / 20 / 60;
            return String.valueOf(new StringBuilder().append(n3).append(" m"));
        }
        int n4 = n / 20;
        int n5 = n % 20 / 2;
        return String.valueOf(new StringBuilder().append(n4).append(".").append(n5).append(" s"));
    }

    private List<Enchantment> setDefaultList() {
        ArrayList<Enchantment> arrayList = new ArrayList<Enchantment>();
        for (Enchantment Enchantment2 : Registry.ENCHANTMENT) {
            arrayList.add(Enchantment2);
        }
        return arrayList;
    }

    @EventHandler
    private void onRender2D(Render2DEvent render2DEvent) {
        boolean bl = !Modules.get().isActive(Freecam.class);
        for (Entity Entity2 : this.mc.world.getEntities()) {
            EntityType EntityType2;
            if (!this.entities.get().containsKey((Object)Entity2.getType()) || (EntityType2 = Entity2.getType()) == EntityType.PLAYER && (bl && Entity2 == this.mc.cameraEntity || !this.yourself.get().booleanValue() && Entity2 == this.mc.player)) continue;
            this.pos.set(Entity2, render2DEvent.tickDelta);
            this.pos.add(0.0, this.getHeight(Entity2), 0.0);
            if (!NametagUtils.to2D(this.pos, this.scale.get())) continue;
            if (EntityType2 == EntityType.PLAYER) {
                this.renderNametagPlayer((PlayerEntity)Entity2);
                continue;
            }
            if (EntityType2 == EntityType.ITEM) {
                this.renderNametagItem(((ItemEntity)Entity2).getStack());
                continue;
            }
            if (EntityType2 == EntityType.ITEM_FRAME) {
                this.renderNametagItem(((ItemFrameEntity)Entity2).getHeldItemStack());
                continue;
            }
            if (EntityType2 == EntityType.TNT) {
                this.renderTntNametag((TntEntity)Entity2);
                continue;
            }
            if (!(Entity2 instanceof LivingEntity)) continue;
            this.renderGenericNametag((LivingEntity)Entity2);
        }
    }

    private void drawBg(double d, double d2, double d3, double d4) {
        Renderer.NORMAL.begin(null, DrawMode.Triangles, VertexFormats.POSITION_COLOR);
        Renderer.NORMAL.quad(d - 1.0, d2 - 1.0, d3 + 2.0, d4 + 2.0, this.background.get());
        Renderer.NORMAL.end();
    }

    private void renderTntNametag(TntEntity TntEntity2) {
        TextRenderer textRenderer = TextRenderer.get();
        NametagUtils.begin(this.pos);
        String string = Nametags.ticksToTime(TntEntity2.getFuseTimer());
        double d = textRenderer.getWidth(string);
        double d2 = textRenderer.getHeight();
        double d3 = d / 2.0;
        this.drawBg(-d3, -d2, d, d2);
        textRenderer.beginBig();
        double d4 = -d3;
        double d5 = -d2;
        textRenderer.render(string, d4, d5, this.otherNameColor.get());
        textRenderer.end();
        NametagUtils.end();
    }

    public Nametags() {
        super(Categories.Render, "nametags", "Displays customizable nametags above players.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.sgPlayers = this.settings.createGroup("Players");
        this.sgItems = this.settings.createGroup("Items");
        this.sgOther = this.settings.createGroup("Other");
        this.entities = this.sgGeneral.add(new EntityTypeListSetting.Builder().name("entities").description("Select entities to draw nametags on.").defaultValue((Object2BooleanMap<EntityType<?>>)Utils.asObject2BooleanOpenHashMap(EntityType.PLAYER, EntityType.ITEM)).build());
        this.scale = this.sgGeneral.add(new DoubleSetting.Builder().name("scale").description("The scale of the nametag.").defaultValue(1.5).min(0.1).build());
        this.yourself = this.sgGeneral.add(new BoolSetting.Builder().name("self-nametag").description("Displays a nametag on your player if you're in Freecam.").defaultValue(true).build());
        this.background = this.sgGeneral.add(new ColorSetting.Builder().name("background").description("The color of the nametag background.").defaultValue(new SettingColor(0, 0, 0, 75)).build());
        this.displayItems = this.sgPlayers.add(new BoolSetting.Builder().name("display-items").description("Displays armor and hand items above the name tags.").defaultValue(true).build());
        this.itemSpacing = this.sgPlayers.add(new DoubleSetting.Builder().name("item-spacing").description("The spacing between items.").defaultValue(2.0).min(0.0).sliderMax(5.0).max(10.0).build());
        this.ignoreEmpty = this.sgPlayers.add(new BoolSetting.Builder().name("ignore-empty").description("Doesn't add spacing where an empty item stack would be.").defaultValue(true).build());
        this.displayItemEnchants = this.sgPlayers.add(new BoolSetting.Builder().name("display-enchants").description("Displays item enchantments on the items.").defaultValue(true).build());
        this.enchantPos = this.sgPlayers.add(new EnumSetting.Builder().name("enchantment-position").description("Where the enchantments are rendered.").defaultValue(Position.Above).build());
        this.enchantLength = this.sgPlayers.add(new IntSetting.Builder().name("enchant-name-length").description("The length enchantment names are trimmed to.").defaultValue(3).min(1).max(5).sliderMax(5).build());
        this.displayedEnchantments = this.sgPlayers.add(new EnchListSetting.Builder().name("displayed-enchantments").description("The enchantments that are shown on nametags.").defaultValue(this.setDefaultList()).build());
        this.enchantTextScale = this.sgPlayers.add(new DoubleSetting.Builder().name("enchant-text-scale").description("The scale of the enchantment text.").defaultValue(1.0).min(0.1).max(2.0).sliderMin(0.1).sliderMax(2.0).build());
        this.displayMeteor = this.sgPlayers.add(new BoolSetting.Builder().name("meteor").description("Shows if the player is using Meteor.").defaultValue(true).build());
        this.displayGameMode = this.sgPlayers.add(new BoolSetting.Builder().name("gamemode").description("Shows the player's GameMode.").defaultValue(true).build());
        this.displayPing = this.sgPlayers.add(new BoolSetting.Builder().name("ping").description("Shows the player's ping.").defaultValue(true).build());
        this.displayDistance = this.sgPlayers.add(new BoolSetting.Builder().name("distance").description("Shows the distance between you and the player.").defaultValue(true).build());
        this.normalName = this.sgPlayers.add(new ColorSetting.Builder().name("normal-color").description("The color of people not in your Friends List.").defaultValue(new SettingColor(255, 255, 255)).build());
        this.meteorColor = this.sgPlayers.add(new ColorSetting.Builder().name("meteor-color").description("The color of M when the player is using Meteor.").defaultValue(new SettingColor(135, 0, 255)).build());
        this.gmColor = this.sgPlayers.add(new ColorSetting.Builder().name("gamemode-color").description("The color of the gamemode text.").defaultValue(new SettingColor(232, 185, 35)).build());
        this.pingColor = this.sgPlayers.add(new ColorSetting.Builder().name("ping-color").description("The color of the ping text.").defaultValue(new SettingColor(150, 150, 150)).build());
        this.distanceColor = this.sgPlayers.add(new ColorSetting.Builder().name("distance-color").description("The color of the distance text.").defaultValue(new SettingColor(150, 150, 150)).build());
        this.healthStage1 = this.sgPlayers.add(new ColorSetting.Builder().name("health-stage-1").description("The color if a player is full health.").defaultValue(new SettingColor(25, 252, 25)).build());
        this.healthStage2 = this.sgPlayers.add(new ColorSetting.Builder().name("health-stage-2").description("The color if a player is at two-thirds health.").defaultValue(new SettingColor(255, 105, 25)).build());
        this.healthStage3 = this.sgPlayers.add(new ColorSetting.Builder().name("health-stage-3").description("The color of a player if they are at one-third health.").defaultValue(new SettingColor(255, 25, 25)).build());
        this.enchantmentTextColor = this.sgPlayers.add(new ColorSetting.Builder().name("enchantment-text-color").description("The color of the enchantment text.").defaultValue(new SettingColor(255, 255, 255)).build());
        this.itemNameColor = this.sgItems.add(new ColorSetting.Builder().name("name-color").description("The color of the name of the item.").defaultValue(new SettingColor(255, 255, 255)).build());
        this.itemCount = this.sgItems.add(new BoolSetting.Builder().name("count-on-items").description("Shows the number of items in an item entities nametag.").defaultValue(true).build());
        this.itemCountColor = this.sgItems.add(new ColorSetting.Builder().name("item-count-color").description("The color of the item count.").defaultValue(new SettingColor(232, 185, 35)).build());
        this.otherNameColor = this.sgOther.add(new ColorSetting.Builder().name("name-color").description("The color of the name of the entity.").defaultValue(new SettingColor(255, 255, 255)).build());
        this.otherHealthStage1 = this.sgOther.add(new ColorSetting.Builder().name("health-stage-1").description("The color of a mobs health if it's full health.").defaultValue(new SettingColor(25, 252, 25)).build());
        this.otherHealthStage2 = this.sgOther.add(new ColorSetting.Builder().name("health-stage-2").description("The color of a mobs health if it's at two-thirds health.").defaultValue(new SettingColor(255, 105, 25)).build());
        this.otherHealthStage3 = this.sgOther.add(new ColorSetting.Builder().name("health-stage-3").description("The color of a mobs health if they are at one-third health.").defaultValue(new SettingColor(255, 25, 25)).build());
        this.pos = new Vec3();
        this.itemWidths = new double[6];
        this.RED = new Color(255, 15, 15);
        this.enchantmentsToShowScale = new HashMap<Enchantment, Integer>();
    }

    private ItemStack getItem(PlayerEntity PlayerEntity2, int n) {
        switch (n) {
            case 0: {
                return PlayerEntity2.getMainHandStack();
            }
            case 1: {
                return (ItemStack)PlayerEntity2.inventory.armor.get(3);
            }
            case 2: {
                return (ItemStack)PlayerEntity2.inventory.armor.get(2);
            }
            case 3: {
                return (ItemStack)PlayerEntity2.inventory.armor.get(1);
            }
            case 4: {
                return (ItemStack)PlayerEntity2.inventory.armor.get(0);
            }
            case 5: {
                return PlayerEntity2.getOffHandStack();
            }
        }
        return ItemStack.EMPTY;
    }

    private double getHeight(Entity Entity2) {
        double d = Entity2.getEyeHeight(Entity2.getPose());
        d = Entity2.getType() == EntityType.ITEM || Entity2.getType() == EntityType.ITEM_FRAME ? (d += 0.2) : (d += 0.5);
        return d;
    }

    private void renderNametagItem(ItemStack ItemStack2) {
        TextRenderer textRenderer = TextRenderer.get();
        NametagUtils.begin(this.pos);
        String string = ItemStack2.getName().getString();
        String string2 = String.valueOf(new StringBuilder().append(" x").append(ItemStack2.getCount()));
        double d = textRenderer.getWidth(string);
        double d2 = textRenderer.getWidth(string2);
        double d3 = textRenderer.getHeight();
        double d4 = d;
        if (this.itemCount.get().booleanValue()) {
            d4 += d2;
        }
        double d5 = d4 / 2.0;
        this.drawBg(-d5, -d3, d4, d3);
        textRenderer.beginBig();
        double d6 = -d5;
        double d7 = -d3;
        d6 = textRenderer.render(string, d6, d7, this.itemNameColor.get());
        if (this.itemCount.get().booleanValue()) {
            textRenderer.render(string2, d6, d7, this.itemCountColor.get());
        }
        textRenderer.end();
        NametagUtils.end();
    }

    public static final class Position
    extends Enum<Position> {
        public static final /* enum */ Position Above = new Position();
        private static final Position[] $VALUES;
        public static final /* enum */ Position OnTop;

        static {
            OnTop = new Position();
            $VALUES = Position.$values();
        }

        public static Position valueOf(String string) {
            return Enum.valueOf(Position.class, string);
        }

        private static Position[] $values() {
            return new Position[]{Above, OnTop};
        }

        public static Position[] values() {
            return (Position[])$VALUES.clone();
        }
    }
}

