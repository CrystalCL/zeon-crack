/*
 * Decompiled with CFR 0.151.
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
import java.util.Iterator;
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
    private static final Random random;
    public static boolean isReleasingTrident;
    public static boolean firstTimeTitleScreen;
    private static final DecimalFormat df;
    public static MinecraftClient mc;
    public static final Color WHITE;

    public static double distance(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d4 - d;
        double d8 = d5 - d2;
        double d9 = d6 - d3;
        return Math.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
    }

    public static int random(int n, int n2) {
        return random.nextInt(n2 - n) + n;
    }

    public static void addEnchantment(ItemStack ItemStack2, Enchantment Enchantment2, int n) {
        NbtList NbtList2;
        NbtCompound NbtCompound2 = ItemStack2.getOrCreateTag();
        if (!NbtCompound2.contains("Enchantments", 9)) {
            NbtList2 = new NbtList();
            NbtCompound2.put("Enchantments", (NbtElement)NbtList2);
        } else {
            NbtList2 = NbtCompound2.getList("Enchantments", 10);
        }
        String string = Registry.ENCHANTMENT.getId((Object)Enchantment2).toString();
        for (NbtElement NbtElement2 : NbtList2) {
            NbtCompound NbtCompound3 = (NbtCompound)NbtElement2;
            if (!NbtCompound3.getString("id").equals(string)) continue;
            NbtCompound3.putShort("lvl", (short)n);
            return;
        }
        Iterator iterator = new NbtCompound();
        iterator.putString("id", string);
        iterator.putShort("lvl", (short)n);
        NbtList2.add((Object)iterator);
    }

    public static boolean isThrowable(Item Item2) {
        return Item2 instanceof ExperienceBottleItem || Item2 instanceof BowItem || Item2 instanceof CrossbowItem || Item2 instanceof SnowballItem || Item2 instanceof EggItem || Item2 instanceof EnderPearlItem || Item2 instanceof SplashPotionItem || Item2 instanceof LingeringPotionItem || Item2 instanceof FishingRodItem || Item2 instanceof TridentItem;
    }

    public static float clamp(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        if (f > f3) {
            return f3;
        }
        return f;
    }

    public static double clamp(double d, double d2, double d3) {
        if (d < d2) {
            return d2;
        }
        if (d > d3) {
            return d3;
        }
        return d;
    }

    public static int unpackLong4(long l) {
        return (int)(l & 0xFFFFL);
    }

    public static String getEnchantSimpleName(Enchantment Enchantment2, int n) {
        return Enchantment2.getName(0).getString().substring(0, n);
    }

    public static void getItemsInContainerItem(ItemStack ItemStack2, ItemStack[] ItemStackArray) {
        NbtCompound NbtCompound2;
        Arrays.fill(ItemStackArray, ItemStack.EMPTY);
        NbtCompound NbtCompound3 = ItemStack2.getTag();
        if (NbtCompound3 != null && NbtCompound3.contains("BlockEntityTag") && (NbtCompound2 = NbtCompound3.getCompound("BlockEntityTag")).contains("Items")) {
            NbtList NbtList2 = (NbtList)NbtCompound2.get("Items");
            for (int i = 0; i < NbtList2.size(); ++i) {
                ItemStackArray[NbtList2.getCompound((int)i).getByte((String)"Slot")] = ItemStack.fromNbt((NbtCompound)NbtList2.getCompound(i));
            }
        }
    }

    public static Object2IntMap<StatusEffect> createStatusEffectMap() {
        Object2IntArrayMap object2IntArrayMap = new Object2IntArrayMap(Registry.STATUS_EFFECT.getIds().size());
        Registry.STATUS_EFFECT.forEach(arg_0 -> Utils.lambda$createStatusEffectMap$0((Object2IntMap)object2IntArrayMap, arg_0));
        return object2IntArrayMap;
    }

    public static int getWindowHeight() {
        return mc.getWindow().getFramebufferHeight();
    }

    public static String getKeyName(int n) {
        switch (n) {
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
        String string = GLFW.glfwGetKeyName((int)n, (int)0);
        if (string == null) {
            return "Unknown";
        }
        return StringUtils.capitalize((String)string);
    }

    public static byte[] readBytes(File file) {
        try {
            int n;
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] byArray = new byte[256];
            while ((n = ((InputStream)fileInputStream).read(byArray)) > 0) {
                byteArrayOutputStream.write(byArray, 0, n);
            }
            ((InputStream)fileInputStream).close();
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return new byte[0];
        }
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

    public static int unpackLong1(long l) {
        return (int)(l >> 48 & 0xFFFFL);
    }

    @SafeVarargs
    public static <T> Object2BooleanOpenHashMap<T> asObject2BooleanOpenHashMap(T ... TArray) {
        HashMap<T, Boolean> hashMap = new HashMap<T, Boolean>();
        for (T t : TArray) {
            hashMap.put(t, true);
            if (1 >= 0) continue;
            return null;
        }
        return new Object2BooleanOpenHashMap(hashMap);
    }

    public static int search(String string, String string2) {
        String[] stringArray;
        int n = 0;
        for (String string3 : stringArray = string2.split(" ")) {
            if (!StringUtils.containsIgnoreCase((CharSequence)string, (CharSequence)string3)) continue;
            ++n;
            if (!false) continue;
            return 0;
        }
        return n;
    }

    public static void sendMessage(String string, Object ... objectArray) {
        if (Utils.mc.player == null) {
            return;
        }
        string = String.format(string, objectArray);
        string = string.replaceAll("#yellow", Formatting.YELLOW.toString());
        string = string.replaceAll("#white", Formatting.WHITE.toString());
        string = string.replaceAll("#red", Formatting.RED.toString());
        string = string.replaceAll("#blue", Formatting.BLUE.toString());
        string = string.replaceAll("#pink", Formatting.LIGHT_PURPLE.toString());
        string = string.replaceAll("#gray", Formatting.GRAY.toString());
        Utils.mc.player.sendMessage((Text)new LiteralText(string), false);
    }

    public static void leftClick() {
        Utils.mc.options.keyAttack.setPressed(true);
        ((MinecraftClientAccessor)mc).leftClick();
        Utils.mc.options.keyAttack.setPressed(false);
    }

    public static String getButtonName(int n) {
        switch (n) {
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
        return String.valueOf(new StringBuilder().append("Mouse ").append(n));
    }

    public static double random(double d, double d2) {
        return d + (d2 - d) * random.nextDouble();
    }

    public static void unscaledProjection() {
        RenderSystem.matrixMode((int)5889);
        RenderSystem.loadIdentity();
        RenderSystem.ortho((double)0.0, (double)MinecraftClient.getInstance().getWindow().getFramebufferWidth(), (double)MinecraftClient.getInstance().getWindow().getFramebufferHeight(), (double)0.0, (double)1000.0, (double)3000.0);
        RenderSystem.matrixMode((int)5888);
        RenderSystem.loadIdentity();
        RenderSystem.translatef((float)0.0f, (float)0.0f, (float)-2000.0f);
    }

    public static double squaredDistance(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d4 - d;
        double d8 = d5 - d2;
        double d9 = d6 - d3;
        return d7 * d7 + d8 * d8 + d9 * d9;
    }

    public static int unpackLong3(long l) {
        return (int)(l >> 16 & 0xFFFFL);
    }

    public static Vec3d vec3d(BlockPos BlockPos2) {
        return new Vec3d((double)BlockPos2.getX(), (double)BlockPos2.getY(), (double)BlockPos2.getZ());
    }

    public static int clamp(int n, int n2, int n3) {
        if (n < n2) {
            return n2;
        }
        if (n > n3) {
            return n3;
        }
        return n;
    }

    public static String getWorldName() {
        String string;
        if (mc.isInSingleplayer()) {
            File file = ((MinecraftServerAccessor)mc.getServer()).getSession().getWorldDirectory(Utils.mc.world.getRegistryKey());
            if (file.toPath().relativize(Utils.mc.runDirectory.toPath()).getNameCount() != 2) {
                file = file.getParentFile();
            }
            return file.getName();
        }
        String string2 = string = mc.isConnectedToRealms() ? "realms" : Utils.mc.getCurrentServerEntry().address;
        if (SystemUtils.IS_OS_WINDOWS) {
            string = string.replace(":", "_");
        }
        return string;
    }

    public static int getWindowWidth() {
        return mc.getWindow().getFramebufferWidth();
    }

    public static String floatToString(float f) {
        if (f % 1.0f == 0.0f) {
            return Integer.toString((int)f);
        }
        return Float.toString(f);
    }

    public static long packLong(int n, int n2, int n3, int n4) {
        return ((long)n << 48) + ((long)n2 << 32) + ((long)n3 << 16) + (long)n4;
    }

    public static void scaledProjection() {
        RenderSystem.matrixMode((int)5889);
        RenderSystem.loadIdentity();
        RenderSystem.ortho((double)0.0, (double)((double)MinecraftClient.getInstance().getWindow().getFramebufferWidth() / MinecraftClient.getInstance().getWindow().getScaleFactor()), (double)((double)MinecraftClient.getInstance().getWindow().getFramebufferHeight() / MinecraftClient.getInstance().getWindow().getScaleFactor()), (double)0.0, (double)1000.0, (double)3000.0);
        RenderSystem.matrixMode((int)5888);
        RenderSystem.loadIdentity();
        RenderSystem.translatef((float)0.0f, (float)0.0f, (float)-2000.0f);
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

    public static int unpackLong2(long l) {
        return (int)(l >> 32 & 0xFFFFL);
    }

    public static boolean isShulker(Item Item2) {
        return Item2 == Items.SHULKER_BOX || Item2 == Items.WHITE_SHULKER_BOX || Item2 == Items.ORANGE_SHULKER_BOX || Item2 == Items.MAGENTA_SHULKER_BOX || Item2 == Items.LIGHT_BLUE_SHULKER_BOX || Item2 == Items.YELLOW_SHULKER_BOX || Item2 == Items.LIME_SHULKER_BOX || Item2 == Items.PINK_SHULKER_BOX || Item2 == Items.GRAY_SHULKER_BOX || Item2 == Items.LIGHT_GRAY_SHULKER_BOX || Item2 == Items.CYAN_SHULKER_BOX || Item2 == Items.PURPLE_SHULKER_BOX || Item2 == Items.BLUE_SHULKER_BOX || Item2 == Items.BROWN_SHULKER_BOX || Item2 == Items.GREEN_SHULKER_BOX || Item2 == Items.RED_SHULKER_BOX || Item2 == Items.BLACK_SHULKER_BOX;
    }

    public static String nameToTitle(String string) {
        return Arrays.stream(string.split("-")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
    }

    static {
        WHITE = new Color(255, 255, 255);
        random = new Random();
        firstTimeTitleScreen = true;
        df = new DecimalFormat("0");
        df.setMaximumFractionDigits(340);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(decimalFormatSymbols);
    }

    public static double distanceToCamera(Entity Entity2) {
        return Utils.distanceToCamera(Entity2.getX(), Entity2.getY(), Entity2.getZ());
    }

    public static void addMeteorPvpToServerList() {
        ServerList ServerList2 = new ServerList(mc);
        ServerList2.loadFile();
        boolean bl = false;
        for (int i = 0; i < ServerList2.size(); ++i) {
            ServerInfo ServerInfo2 = ServerList2.get(i);
            if (!ServerInfo2.address.contains("pvp.meteorclient.com")) continue;
            bl = true;
            break;
        }
        if (!bl) {
            ServerList2.add(new ServerInfo("Meteor Pvp", "pvp.meteorclient.com", false));
            ServerList2.saveFile();
        }
    }

    public static double distanceToCamera(double d, double d2, double d3) {
        Camera Camera2 = Utils.mc.gameRenderer.getCamera();
        return Math.sqrt(Camera2.getPos().squaredDistanceTo(d, d2, d3));
    }

    private static void lambda$createStatusEffectMap$0(Object2IntMap object2IntMap, StatusEffect StatusEffect2) {
        object2IntMap.put((Object)StatusEffect2, 0);
    }

    public static void rightClick() {
        ((IMinecraftClient)mc).rightClick();
    }

    public static String doubleToString(double d) {
        if (d % 1.0 == 0.0) {
            return Integer.toString((int)d);
        }
        return df.format(d);
    }

    public static boolean canUpdate() {
        return mc != null && (Utils.mc.world != null || Utils.mc.player != null);
    }
}

