/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.player;

import minegame159.meteorclient.mixin.CreativeInventoryScreenAccessor;
import minegame159.meteorclient.mixin.HorseScreenHandlerAccessor;
import minegame159.meteorclient.utils.Utils;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.BeaconScreenHandler;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.BlastFurnaceScreenHandler;
import net.minecraft.screen.SmokerScreenHandler;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.CartographyTableScreenHandler;
import net.minecraft.screen.LecternScreenHandler;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;

public class SlotUtils {
    public static final int ARMOR_END;
    public static final int HOTBAR_START;
    public static final int OFFHAND;
    public static final int ARMOR_START;
    public static final int HOTBAR_END;
    public static final int MAIN_START;
    public static final int MAIN_END;

    private static int stonecutter(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 29 + n;
        }
        if (SlotUtils.isMain(n)) {
            return 2 + (n - 9);
        }
        return -1;
    }

    private static int brewingStand(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 32 + n;
        }
        if (SlotUtils.isMain(n)) {
            return 5 + (n - 9);
        }
        return -1;
    }

    static {
        MAIN_END = 35;
        MAIN_START = 9;
        ARMOR_END = 39;
        HOTBAR_START = 0;
        ARMOR_START = 36;
        HOTBAR_END = 8;
        OFFHAND = 45;
    }

    private static boolean isHotbar(int n) {
        return n >= 0 && n <= 8;
    }

    private static int enchantmentTable(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 29 + n;
        }
        if (SlotUtils.isMain(n)) {
            return 2 + (n - 9);
        }
        return -1;
    }

    private static int survivalInventory(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 36 + n;
        }
        if (SlotUtils.isArmor(n)) {
            return 5 + (n - 36);
        }
        return n;
    }

    private static int hopper(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 32 + n;
        }
        if (SlotUtils.isMain(n)) {
            return 5 + (n - 9);
        }
        return -1;
    }

    private static int furnace(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 30 + n;
        }
        if (SlotUtils.isMain(n)) {
            return 3 + (n - 9);
        }
        return -1;
    }

    private static int generic3x3(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 36 + n;
        }
        if (SlotUtils.isMain(n)) {
            return n;
        }
        return -1;
    }

    private static int anvil(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 30 + n;
        }
        if (SlotUtils.isMain(n)) {
            return 3 + (n - 9);
        }
        return -1;
    }

    private static int beacon(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 28 + n;
        }
        if (SlotUtils.isMain(n)) {
            return 1 + (n - 9);
        }
        return -1;
    }

    private static int craftingTable(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 37 + n;
        }
        if (SlotUtils.isMain(n)) {
            return n + 1;
        }
        return -1;
    }

    private static int villager(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 30 + n;
        }
        if (SlotUtils.isMain(n)) {
            return 3 + (n - 9);
        }
        return -1;
    }

    private static int creativeInventory(int n) {
        if (!(Utils.mc.currentScreen instanceof CreativeInventoryScreen) || ((CreativeInventoryScreenAccessor)Utils.mc.currentScreen).getSelectedTab() != ItemGroup.INVENTORY.getIndex()) {
            return -1;
        }
        return SlotUtils.survivalInventory(n);
    }

    public static int indexToId(int n) {
        if (Utils.mc.player == null) {
            return -1;
        }
        ScreenHandler ScreenHandler2 = Utils.mc.player.currentScreenHandler;
        if (ScreenHandler2 instanceof PlayerScreenHandler) {
            return SlotUtils.survivalInventory(n);
        }
        if (ScreenHandler2 instanceof CreativeInventoryScreen.class_483) {
            return SlotUtils.creativeInventory(n);
        }
        if (ScreenHandler2 instanceof GenericContainerScreenHandler) {
            return SlotUtils.genericContainer(n, ((GenericContainerScreenHandler)ScreenHandler2).getRows());
        }
        if (ScreenHandler2 instanceof CraftingScreenHandler) {
            return SlotUtils.craftingTable(n);
        }
        if (ScreenHandler2 instanceof FurnaceScreenHandler) {
            return SlotUtils.furnace(n);
        }
        if (ScreenHandler2 instanceof BlastFurnaceScreenHandler) {
            return SlotUtils.furnace(n);
        }
        if (ScreenHandler2 instanceof SmokerScreenHandler) {
            return SlotUtils.furnace(n);
        }
        if (ScreenHandler2 instanceof Generic3x3ContainerScreenHandler) {
            return SlotUtils.generic3x3(n);
        }
        if (ScreenHandler2 instanceof EnchantmentScreenHandler) {
            return SlotUtils.enchantmentTable(n);
        }
        if (ScreenHandler2 instanceof BrewingStandScreenHandler) {
            return SlotUtils.brewingStand(n);
        }
        if (ScreenHandler2 instanceof MerchantScreenHandler) {
            return SlotUtils.villager(n);
        }
        if (ScreenHandler2 instanceof BeaconScreenHandler) {
            return SlotUtils.beacon(n);
        }
        if (ScreenHandler2 instanceof AnvilScreenHandler) {
            return SlotUtils.anvil(n);
        }
        if (ScreenHandler2 instanceof HopperScreenHandler) {
            return SlotUtils.hopper(n);
        }
        if (ScreenHandler2 instanceof ShulkerBoxScreenHandler) {
            return SlotUtils.genericContainer(n, 3);
        }
        if (ScreenHandler2 instanceof HorseScreenHandler) {
            return SlotUtils.horse(ScreenHandler2, n);
        }
        if (ScreenHandler2 instanceof CartographyTableScreenHandler) {
            return SlotUtils.cartographyTable(n);
        }
        if (ScreenHandler2 instanceof GrindstoneScreenHandler) {
            return SlotUtils.grindstone(n);
        }
        if (ScreenHandler2 instanceof LecternScreenHandler) {
            return SlotUtils.lectern();
        }
        if (ScreenHandler2 instanceof LoomScreenHandler) {
            return SlotUtils.loom(n);
        }
        if (ScreenHandler2 instanceof StonecutterScreenHandler) {
            return SlotUtils.stonecutter(n);
        }
        return -1;
    }

    private static boolean isArmor(int n) {
        return n >= 36 && n <= 39;
    }

    private static int genericContainer(int n, int n2) {
        if (SlotUtils.isHotbar(n)) {
            return (n2 + 3) * 9 + n;
        }
        if (SlotUtils.isMain(n)) {
            return n2 * 9 + (n - 9);
        }
        return -1;
    }

    private static int cartographyTable(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 30 + n;
        }
        if (SlotUtils.isMain(n)) {
            return 3 + (n - 9);
        }
        return -1;
    }

    private static int grindstone(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 30 + n;
        }
        if (SlotUtils.isMain(n)) {
            return 3 + (n - 9);
        }
        return -1;
    }

    private static int horse(ScreenHandler ScreenHandler2, int n) {
        HorseBaseEntity HorseBaseEntity2 = ((HorseScreenHandlerAccessor)ScreenHandler2).getEntity();
        if (HorseBaseEntity2 instanceof LlamaEntity) {
            int n2 = ((LlamaEntity)HorseBaseEntity2).getStrength();
            if (SlotUtils.isHotbar(n)) {
                return 2 + 3 * n2 + 28 + n;
            }
            if (SlotUtils.isMain(n)) {
                return 2 + 3 * n2 + 1 + (n - 9);
            }
        } else if (HorseBaseEntity2 instanceof HorseEntity || HorseBaseEntity2 instanceof SkeletonHorseEntity || HorseBaseEntity2 instanceof ZombieHorseEntity) {
            if (SlotUtils.isHotbar(n)) {
                return 29 + n;
            }
            if (SlotUtils.isMain(n)) {
                return 2 + (n - 9);
            }
        } else if (HorseBaseEntity2 instanceof AbstractDonkeyEntity) {
            boolean bl = ((AbstractDonkeyEntity)HorseBaseEntity2).hasChest();
            if (SlotUtils.isHotbar(n)) {
                return (bl ? 44 : 29) + n;
            }
            if (SlotUtils.isMain(n)) {
                return (bl ? 17 : 2) + (n - 9);
            }
        }
        return -1;
    }

    private static int loom(int n) {
        if (SlotUtils.isHotbar(n)) {
            return 31 + n;
        }
        if (SlotUtils.isMain(n)) {
            return 4 + (n - 9);
        }
        return -1;
    }

    private static int lectern() {
        return -1;
    }

    private static boolean isMain(int n) {
        return n >= 9 && n <= 35;
    }
}

