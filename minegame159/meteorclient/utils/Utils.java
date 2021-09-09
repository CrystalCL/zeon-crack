/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntArrayMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  net.minecraft.util.Formatting
 *  net.minecraft.entity.effect.StatusEffect
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.BowItem
 *  net.minecraft.item.CrossbowItem
 *  net.minecraft.item.EggItem
 *  net.minecraft.item.EnderPearlItem
 *  net.minecraft.item.ExperienceBottleItem
 *  net.minecraft.item.FishingRodItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.LingeringPotionItem
 *  net.minecraft.item.SnowballItem
 *  net.minecraft.item.SplashPotionItem
 *  net.minecraft.item.TridentItem
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.text.Text
 *  net.minecraft.text.LiteralText
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.render.Camera
 *  net.minecraft.client.gui.screen.TitleScreen
 *  net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen
 *  net.minecraft.client.gui.screen.world.SelectWorldScreen
 *  net.minecraft.client.option.ServerList
 *  net.minecraft.client.network.ServerInfo
 *  org.apache.commons.io.output.ByteArrayOutputStream
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.SystemUtils
 *  org.lwjgl.glfw.GLFW
 */
package minegame159.meteorclient.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;
import minegame159.meteorclient.mixin.MinecraftClientAccessor;
import minegame159.meteorclient.mixin.MinecraftServerAccessor;
import minegame159.meteorclient.mixininterface.IMinecraftClient;
import minegame159.meteorclient.utils.render.color.Color;
import minegame159.meteorclient.utils.world.Dimension;
import net.minecraft.util.Formatting;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.Entity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.EggItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.item.SnowballItem;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.item.TridentItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.option.ServerList;
import net.minecraft.client.network.ServerInfo;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.lwjgl.glfw.GLFW;

public class Utils {
    public static /* synthetic */ boolean firstTimeTitleScreen;
    private static final /* synthetic */ DecimalFormat df;
    public static /* synthetic */ boolean isReleasingTrident;
    private static final /* synthetic */ Random random;
    public static /* synthetic */ MinecraftClient mc;
    public static final /* synthetic */ Color WHITE;

    public static int unpackLong4(long llIlIlIIlII) {
        return (int)(llIlIlIIlII & 0xFFFFL);
    }

    public static boolean isWhitelistedScreen() {
        if (Utils.mc.currentScreen instanceof TitleScreen) {
            return true;
        }
        if (Utils.mc.currentScreen instanceof MultiplayerScreen) {
            return true;
        }
        return Utils.mc.currentScreen instanceof SelectWorldScreen;
    }

    public static String getEnchantSimpleName(Enchantment llllIIllIll, int llllIIllIlI) {
        return llllIIllIll.getName(0).getString().substring(0, llllIIllIlI);
    }

    public static void addMeteorPvpToServerList() {
        ServerList lllllIIIIIl = new ServerList(mc);
        lllllIIIIIl.loadFile();
        boolean lllllIIIIII = false;
        for (int lllllIIIIlI = 0; lllllIIIIlI < lllllIIIIIl.size(); ++lllllIIIIlI) {
            ServerInfo lllllIIIIll = lllllIIIIIl.get(lllllIIIIlI);
            if (!lllllIIIIll.address.contains("pvp.meteorclient.com")) continue;
            lllllIIIIII = true;
            break;
        }
        if (!lllllIIIIII) {
            lllllIIIIIl.add(new ServerInfo("Meteor Pvp", "pvp.meteorclient.com", false));
            lllllIIIIIl.saveFile();
        }
    }

    public static Vec3d vec3d(BlockPos llllIllIllI) {
        return new Vec3d((double)llllIllIllI.getX(), (double)llllIllIllI.getY(), (double)llllIllIllI.getZ());
    }

    public static double random(double lllIIIlIlIl, double lllIIIlIllI) {
        return lllIIIlIlIl + (lllIIIlIllI - lllIIIlIlIl) * random.nextDouble();
    }

    public static boolean isShulker(Item lllIIIIllII) {
        return lllIIIIllII == Items.SHULKER_BOX || lllIIIIllII == Items.WHITE_SHULKER_BOX || lllIIIIllII == Items.ORANGE_SHULKER_BOX || lllIIIIllII == Items.MAGENTA_SHULKER_BOX || lllIIIIllII == Items.LIGHT_BLUE_SHULKER_BOX || lllIIIIllII == Items.YELLOW_SHULKER_BOX || lllIIIIllII == Items.LIME_SHULKER_BOX || lllIIIIllII == Items.PINK_SHULKER_BOX || lllIIIIllII == Items.GRAY_SHULKER_BOX || lllIIIIllII == Items.LIGHT_GRAY_SHULKER_BOX || lllIIIIllII == Items.CYAN_SHULKER_BOX || lllIIIIllII == Items.PURPLE_SHULKER_BOX || lllIIIIllII == Items.BLUE_SHULKER_BOX || lllIIIIllII == Items.BROWN_SHULKER_BOX || lllIIIIllII == Items.GREEN_SHULKER_BOX || lllIIIIllII == Items.RED_SHULKER_BOX || lllIIIIllII == Items.BLACK_SHULKER_BOX;
    }

    public static int unpackLong1(long llIlIlIlllI) {
        return (int)(llIlIlIlllI >> 48 & 0xFFFFL);
    }

    public static String getKeyName(int lllIlIIIIll) {
        switch (lllIlIIIIll) {
            case -1: {
                return "Unknown";
            }
            case 256: {
                return "Esc";
            }
            case 283: {
                return "Print Screen";
            }
            case 284: {
                return "Pause";
            }
            case 260: {
                return "Insert";
            }
            case 261: {
                return "Delete";
            }
            case 268: {
                return "Home";
            }
            case 266: {
                return "Page Up";
            }
            case 267: {
                return "Page Down";
            }
            case 269: {
                return "End";
            }
            case 258: {
                return "Tab";
            }
            case 341: {
                return "Left Control";
            }
            case 345: {
                return "Right Control";
            }
            case 342: {
                return "Left Alt";
            }
            case 346: {
                return "Right Alt";
            }
            case 340: {
                return "Left Shift";
            }
            case 344: {
                return "Right Shift";
            }
            case 265: {
                return "Arrow Up";
            }
            case 264: {
                return "Arrow Down";
            }
            case 263: {
                return "Arrow Left";
            }
            case 262: {
                return "Arrow Right";
            }
            case 39: {
                return "Apostrophe";
            }
            case 259: {
                return "Backspace";
            }
            case 280: {
                return "Caps Lock";
            }
            case 348: {
                return "Menu";
            }
            case 343: {
                return "Left Super";
            }
            case 347: {
                return "Right Super";
            }
            case 257: {
                return "Enter";
            }
            case 282: {
                return "Num Lock";
            }
            case 32: {
                return "Space";
            }
            case 290: {
                return "F1";
            }
            case 291: {
                return "F2";
            }
            case 292: {
                return "F3";
            }
            case 293: {
                return "F4";
            }
            case 294: {
                return "F5";
            }
            case 295: {
                return "F6";
            }
            case 296: {
                return "F7";
            }
            case 297: {
                return "F8";
            }
            case 298: {
                return "F9";
            }
            case 299: {
                return "F10";
            }
            case 300: {
                return "F11";
            }
            case 301: {
                return "F12";
            }
            case 302: {
                return "F13";
            }
            case 303: {
                return "F14";
            }
            case 304: {
                return "F15";
            }
            case 305: {
                return "F16";
            }
            case 306: {
                return "F17";
            }
            case 307: {
                return "F18";
            }
            case 308: {
                return "F19";
            }
            case 309: {
                return "F20";
            }
            case 310: {
                return "F21";
            }
            case 311: {
                return "F22";
            }
            case 312: {
                return "F23";
            }
            case 313: {
                return "F24";
            }
            case 314: {
                return "F25";
            }
        }
        String lllIlIIIlIl = GLFW.glfwGetKeyName((int)lllIlIIIIll, (int)0);
        if (lllIlIIIlIl == null) {
            return "Unknown";
        }
        return StringUtils.capitalize((String)lllIlIIIlIl);
    }

    public static double distance(double lllIllIIIII, double lllIlIlllll, double lllIlIlIlII, double lllIlIlllIl, double lllIlIlllII, double lllIlIlIIIl) {
        double lllIlIllIlI = lllIlIlllIl - lllIllIIIII;
        double lllIlIllIIl = lllIlIlllII - lllIlIlllll;
        double lllIlIllIII = lllIlIlIIIl - lllIlIlIlII;
        return Math.sqrt(lllIlIllIlI * lllIlIllIlI + lllIlIllIIl * lllIlIllIIl + lllIlIllIII * lllIlIllIII);
    }

    public static int search(String llllIIIllII, String llllIIIllll) {
        String[] llllIIIllIl;
        int llllIIIlllI = 0;
        for (String llllIIlIIIl : llllIIIllIl = llllIIIllll.split(" ")) {
            if (!StringUtils.containsIgnoreCase((CharSequence)llllIIIllII, (CharSequence)llllIIlIIIl)) continue;
            ++llllIIIlllI;
        }
        return llllIIIlllI;
    }

    public static void getItemsInContainerItem(ItemStack llllIlIlIll, ItemStack[] llllIlIlIlI) {
        NbtCompound llllIlIllII;
        Arrays.fill((Object[])llllIlIlIlI, (Object)ItemStack.EMPTY);
        NbtCompound llllIlIlIIl = llllIlIlIll.getTag();
        if (llllIlIlIIl != null && llllIlIlIIl.contains("BlockEntityTag") && (llllIlIllII = llllIlIlIIl.getCompound("BlockEntityTag")).contains("Items")) {
            NbtList llllIlIllIl = (NbtList)llllIlIllII.get("Items");
            for (int llllIlIlllI = 0; llllIlIlllI < llllIlIllIl.size(); ++llllIlIlllI) {
                llllIlIlIlI[llllIlIllIl.getCompound((int)llllIlIlllI).getByte((String)"Slot")] = ItemStack.fromNbt((NbtCompound)llllIlIllIl.getCompound(llllIlIlllI));
            }
        }
    }

    public static int clamp(int llIlllllllI, int llIlllllIlI, int llIlllllIIl) {
        if (llIlllllllI < llIlllllIlI) {
            return llIlllllIlI;
        }
        if (llIlllllllI > llIlllllIIl) {
            return llIlllllIIl;
        }
        return llIlllllllI;
    }

    public static String getButtonName(int lllIlIIIIII) {
        switch (lllIlIIIIII) {
            case -1: {
                return "Unknown";
            }
            case 0: {
                return "Mouse Left";
            }
            case 1: {
                return "Mouse Right";
            }
            case 2: {
                return "Mouse Middle";
            }
        }
        return String.valueOf(new StringBuilder().append("Mouse ").append(lllIlIIIIII));
    }

    public static void rightClick() {
        ((IMinecraftClient)mc).rightClick();
    }

    public static int unpackLong3(long llIlIlIlIII) {
        return (int)(llIlIlIlIII >> 16 & 0xFFFFL);
    }

    public static void unscaledProjection() {
        RenderSystem.matrixMode((int)5889);
        RenderSystem.loadIdentity();
        RenderSystem.ortho((double)0.0, (double)MinecraftClient.getInstance().getWindow().getFramebufferWidth(), (double)MinecraftClient.getInstance().getWindow().getFramebufferHeight(), (double)0.0, (double)1000.0, (double)3000.0);
        RenderSystem.matrixMode((int)5888);
        RenderSystem.loadIdentity();
        RenderSystem.translatef((float)0.0f, (float)0.0f, (float)-2000.0f);
    }

    public static int random(int lllIIIllIll, int lllIIIlllII) {
        return random.nextInt(lllIIIlllII - lllIIIllIll) + lllIIIllIll;
    }

    public static String floatToString(float lllIIIIIllI) {
        if (lllIIIIIllI % 1.0f == 0.0f) {
            return Integer.toString((int)lllIIIIIllI);
        }
        return Float.toString(lllIIIIIllI);
    }

    public static String getWorldName() {
        String lllIlIIllII;
        if (mc.isInSingleplayer()) {
            File lllIlIIllIl = ((MinecraftServerAccessor)mc.getServer()).getSession().getWorldDirectory(Utils.mc.world.getRegistryKey());
            if (lllIlIIllIl.toPath().relativize(Utils.mc.runDirectory.toPath()).getNameCount() != 2) {
                lllIlIIllIl = lllIlIIllIl.getParentFile();
            }
            return lllIlIIllIl.getName();
        }
        String string = lllIlIIllII = mc.isConnectedToRealms() ? "realms" : Utils.mc.getCurrentServerEntry().address;
        if (SystemUtils.IS_OS_WINDOWS) {
            lllIlIIllII = lllIlIIllII.replace(":", "_");
        }
        return lllIlIIllII;
    }

    public static String doubleToString(double lllIIIIIIlI) {
        if (lllIIIIIIlI % 1.0 == 0.0) {
            return Integer.toString((int)lllIIIIIIlI);
        }
        return df.format(lllIIIIIIlI);
    }

    public static Object2IntMap<StatusEffect> createStatusEffectMap() {
        Object2IntArrayMap llllIlIIIIl = new Object2IntArrayMap(Registry.STATUS_EFFECT.getIds().size());
        Registry.STATUS_EFFECT.forEach(arg_0 -> Utils.lambda$createStatusEffectMap$0((Object2IntMap)llllIlIIIIl, arg_0));
        return llllIlIIIIl;
    }

    public static void scaledProjection() {
        RenderSystem.matrixMode((int)5889);
        RenderSystem.loadIdentity();
        RenderSystem.ortho((double)0.0, (double)((double)MinecraftClient.getInstance().getWindow().getFramebufferWidth() / MinecraftClient.getInstance().getWindow().getScaleFactor()), (double)((double)MinecraftClient.getInstance().getWindow().getFramebufferHeight() / MinecraftClient.getInstance().getWindow().getScaleFactor()), (double)0.0, (double)1000.0, (double)3000.0);
        RenderSystem.matrixMode((int)5888);
        RenderSystem.loadIdentity();
        RenderSystem.translatef((float)0.0f, (float)0.0f, (float)-2000.0f);
    }

    public static void sendMessage(String lllIIIlIIIl, Object ... lllIIIIlllI) {
        if (Utils.mc.player == null) {
            return;
        }
        lllIIIlIIIl = String.format(lllIIIlIIIl, lllIIIIlllI);
        lllIIIlIIIl = lllIIIlIIIl.replaceAll("#yellow", Formatting.YELLOW.toString());
        lllIIIlIIIl = lllIIIlIIIl.replaceAll("#white", Formatting.WHITE.toString());
        lllIIIlIIIl = lllIIIlIIIl.replaceAll("#red", Formatting.RED.toString());
        lllIIIlIIIl = lllIIIlIIIl.replaceAll("#blue", Formatting.BLUE.toString());
        lllIIIlIIIl = lllIIIlIIIl.replaceAll("#pink", Formatting.LIGHT_PURPLE.toString());
        lllIIIlIIIl = lllIIIlIIIl.replaceAll("#gray", Formatting.GRAY.toString());
        Utils.mc.player.sendMessage((Text)new LiteralText(lllIIIlIIIl), false);
    }

    private static /* synthetic */ void lambda$createStatusEffectMap$0(Object2IntMap llIlIIlllll, StatusEffect llIlIlIIIII) {
        llIlIIlllll.put((Object)llIlIlIIIII, 0);
    }

    public static byte[] readBytes(File lllIIllIIll) {
        try {
            int lllIIllIllI;
            FileInputStream lllIIlllIIl = new FileInputStream(lllIIllIIll);
            ByteArrayOutputStream lllIIlllIII = new ByteArrayOutputStream();
            byte[] lllIIllIlll = new byte[256];
            while ((lllIIllIllI = ((InputStream)lllIIlllIIl).read(lllIIllIlll)) > 0) {
                lllIIlllIII.write(lllIIllIlll, 0, lllIIllIllI);
            }
            ((InputStream)lllIIlllIIl).close();
            return lllIIlllIII.toByteArray();
        }
        catch (IOException lllIIllIlIl) {
            lllIIllIlIl.printStackTrace();
            return new byte[0];
        }
    }

    public static int getWindowWidth() {
        return mc.getWindow().getFramebufferWidth();
    }

    public static double distanceToCamera(Entity lllIIlIIIII) {
        return Utils.distanceToCamera(lllIIlIIIII.getX(), lllIIlIIIII.getY(), lllIIlIIIII.getZ());
    }

    public static boolean canUpdate() {
        return mc != null && (Utils.mc.world != null || Utils.mc.player != null);
    }

    public static String nameToTitle(String lllIlIIlIIl) {
        return Arrays.stream(lllIlIIlIIl.split("-")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
    }

    public static float clamp(float llIllllIIlI, float llIllllIIIl, float llIllllIIII) {
        if (llIllllIIlI < llIllllIIIl) {
            return llIllllIIIl;
        }
        if (llIllllIIlI > llIllllIIII) {
            return llIllllIIII;
        }
        return llIllllIIlI;
    }

    public static void leftClick() {
        Utils.mc.options.keyAttack.setPressed(true);
        ((MinecraftClientAccessor)mc).leftClick();
        Utils.mc.options.keyAttack.setPressed(false);
    }

    public static Dimension getDimension() {
        switch (MinecraftClient.getInstance().world.getRegistryKey().getValue().getPath()) {
            case "the_nether": {
                return Dimension.Nether;
            }
            case "the_end": {
                return Dimension.End;
            }
        }
        return Dimension.Overworld;
    }

    @SafeVarargs
    public static <T> Object2BooleanOpenHashMap<T> asObject2BooleanOpenHashMap(T ... llIllIIIIll) {
        HashMap<T, Boolean> llIllIIIIlI = new HashMap<T, Boolean>();
        for (T llIllIIIlII : llIllIIIIll) {
            llIllIIIIlI.put(llIllIIIlII, true);
        }
        return new Object2BooleanOpenHashMap(llIllIIIIlI);
    }

    public static double distanceToCamera(double lllIIlIIllI, double lllIIlIlIIl, double lllIIlIlIII) {
        Camera lllIIlIIlll = Utils.mc.gameRenderer.getCamera();
        return Math.sqrt(lllIIlIIlll.getPos().squaredDistanceTo(lllIIlIIllI, lllIIlIlIIl, lllIIlIlIII));
    }

    public static void addEnchantment(ItemStack llIllIllIlI, Enchantment llIllIllIIl, int llIllIlIIIl) {
        NbtList llIllIlIllI;
        NbtCompound llIllIlIlll = llIllIllIlI.getOrCreateTag();
        if (!llIllIlIlll.contains("Enchantments", 9)) {
            NbtList llIllIlllIl = new NbtList();
            llIllIlIlll.put("Enchantments", (NbtElement)llIllIlllIl);
        } else {
            llIllIlIllI = llIllIlIlll.getList("Enchantments", 10);
        }
        String llIllIlIlIl = Registry.ENCHANTMENT.getId((Object)llIllIllIIl).toString();
        for (NbtElement llIllIllIll : llIllIlIllI) {
            NbtCompound llIllIlllII = (NbtCompound)llIllIllIll;
            if (!llIllIlllII.getString("id").equals(llIllIlIlIl)) continue;
            llIllIlllII.putShort("lvl", (short)llIllIlIIIl);
            return;
        }
        NbtCompound llIllIlIlII = new NbtCompound();
        llIllIlIlII.putString("id", llIllIlIlIl);
        llIllIlIlII.putShort("lvl", (short)llIllIlIIIl);
        llIllIlIllI.add((Object)llIllIlIlII);
    }

    public static int unpackLong2(long llIlIlIlIlI) {
        return (int)(llIlIlIlIlI >> 32 & 0xFFFFL);
    }

    public Utils() {
        Utils lllllIIlIIl;
    }

    public static boolean isThrowable(Item lllIIIIlIII) {
        return lllIIIIlIII instanceof ExperienceBottleItem || lllIIIIlIII instanceof BowItem || lllIIIIlIII instanceof CrossbowItem || lllIIIIlIII instanceof SnowballItem || lllIIIIlIII instanceof EggItem || lllIIIIlIII instanceof EnderPearlItem || lllIIIIlIII instanceof SplashPotionItem || lllIIIIlIII instanceof LingeringPotionItem || lllIIIIlIII instanceof FishingRodItem || lllIIIIlIII instanceof TridentItem;
    }

    public static int getWindowHeight() {
        return mc.getWindow().getFramebufferHeight();
    }

    static {
        WHITE = new Color(255, 255, 255);
        random = new Random();
        firstTimeTitleScreen = true;
        df = new DecimalFormat("0");
        df.setMaximumFractionDigits(340);
        DecimalFormatSymbols llIlIIlllII = new DecimalFormatSymbols();
        llIlIIlllII.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(llIlIIlllII);
    }

    public static double clamp(double llIlllIlIIl, double llIlllIlIll, double llIlllIlIlI) {
        if (llIlllIlIIl < llIlllIlIll) {
            return llIlllIlIll;
        }
        if (llIlllIlIIl > llIlllIlIlI) {
            return llIlllIlIlI;
        }
        return llIlllIlIIl;
    }

    public static long packLong(int llIlIllIlll, int llIlIllIllI, int llIlIllIIIl, int llIlIllIIII) {
        return ((long)llIlIllIlll << 48) + ((long)llIlIllIllI << 32) + ((long)llIlIllIIIl << 16) + (long)llIlIllIIII;
    }

    public static double squaredDistance(double lllIllllIll, double lllIllllIlI, double lllIllIllll, double lllIllllIII, double lllIlllIlll, double lllIlllIllI) {
        double lllIlllIlIl = lllIllllIII - lllIllllIll;
        double lllIlllIlII = lllIlllIlll - lllIllllIlI;
        double lllIlllIIll = lllIlllIllI - lllIllIllll;
        return lllIlllIlIl * lllIlllIlIl + lllIlllIlII * lllIlllIlII + lllIlllIIll * lllIlllIIll;
    }
}

