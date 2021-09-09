/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.passive.AbstractDonkeyEntity
 *  net.minecraft.entity.passive.HorseBaseEntity
 *  net.minecraft.entity.passive.HorseEntity
 *  net.minecraft.entity.passive.LlamaEntity
 *  net.minecraft.entity.mob.SkeletonHorseEntity
 *  net.minecraft.entity.mob.ZombieHorseEntity
 *  net.minecraft.screen.ScreenHandler
 *  net.minecraft.screen.BeaconScreenHandler
 *  net.minecraft.screen.AnvilScreenHandler
 *  net.minecraft.screen.GenericContainerScreenHandler
 *  net.minecraft.screen.BrewingStandScreenHandler
 *  net.minecraft.screen.CraftingScreenHandler
 *  net.minecraft.screen.Generic3x3ContainerScreenHandler
 *  net.minecraft.screen.EnchantmentScreenHandler
 *  net.minecraft.screen.HopperScreenHandler
 *  net.minecraft.screen.PlayerScreenHandler
 *  net.minecraft.screen.HorseScreenHandler
 *  net.minecraft.screen.LoomScreenHandler
 *  net.minecraft.screen.MerchantScreenHandler
 *  net.minecraft.screen.ShulkerBoxScreenHandler
 *  net.minecraft.item.ItemGroup
 *  net.minecraft.screen.BlastFurnaceScreenHandler
 *  net.minecraft.screen.SmokerScreenHandler
 *  net.minecraft.screen.GrindstoneScreenHandler
 *  net.minecraft.screen.FurnaceScreenHandler
 *  net.minecraft.screen.CartographyTableScreenHandler
 *  net.minecraft.screen.LecternScreenHandler
 *  net.minecraft.screen.StonecutterScreenHandler
 *  net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen
 *  net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen$class_483
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
    public static final /* synthetic */ int ARMOR_START;
    public static final /* synthetic */ int MAIN_END;
    public static final /* synthetic */ int HOTBAR_END;
    public static final /* synthetic */ int MAIN_START;
    public static final /* synthetic */ int ARMOR_END;
    public static final /* synthetic */ int OFFHAND;
    public static final /* synthetic */ int HOTBAR_START;

    private static boolean isArmor(int lllllllllllllllllIIIlllllIIIIIlI) {
        return lllllllllllllllllIIIlllllIIIIIlI >= 36 && lllllllllllllllllIIIlllllIIIIIlI <= 39;
    }

    public static int indexToId(int lllllllllllllllllIIIllllllIIllII) {
        if (Utils.mc.player == null) {
            return -1;
        }
        ScreenHandler lllllllllllllllllIIIllllllIIllIl = Utils.mc.player.currentScreenHandler;
        if (lllllllllllllllllIIIllllllIIllIl instanceof PlayerScreenHandler) {
            return SlotUtils.survivalInventory(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof CreativeInventoryScreen.class_483) {
            return SlotUtils.creativeInventory(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof GenericContainerScreenHandler) {
            return SlotUtils.genericContainer(lllllllllllllllllIIIllllllIIllII, ((GenericContainerScreenHandler)lllllllllllllllllIIIllllllIIllIl).getRows());
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof CraftingScreenHandler) {
            return SlotUtils.craftingTable(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof FurnaceScreenHandler) {
            return SlotUtils.furnace(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof BlastFurnaceScreenHandler) {
            return SlotUtils.furnace(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof SmokerScreenHandler) {
            return SlotUtils.furnace(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof Generic3x3ContainerScreenHandler) {
            return SlotUtils.generic3x3(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof EnchantmentScreenHandler) {
            return SlotUtils.enchantmentTable(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof BrewingStandScreenHandler) {
            return SlotUtils.brewingStand(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof MerchantScreenHandler) {
            return SlotUtils.villager(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof BeaconScreenHandler) {
            return SlotUtils.beacon(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof AnvilScreenHandler) {
            return SlotUtils.anvil(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof HopperScreenHandler) {
            return SlotUtils.hopper(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof ShulkerBoxScreenHandler) {
            return SlotUtils.genericContainer(lllllllllllllllllIIIllllllIIllII, 3);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof HorseScreenHandler) {
            return SlotUtils.horse(lllllllllllllllllIIIllllllIIllIl, lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof CartographyTableScreenHandler) {
            return SlotUtils.cartographyTable(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof GrindstoneScreenHandler) {
            return SlotUtils.grindstone(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof LecternScreenHandler) {
            return SlotUtils.lectern();
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof LoomScreenHandler) {
            return SlotUtils.loom(lllllllllllllllllIIIllllllIIllII);
        }
        if (lllllllllllllllllIIIllllllIIllIl instanceof StonecutterScreenHandler) {
            return SlotUtils.stonecutter(lllllllllllllllllIIIllllllIIllII);
        }
        return -1;
    }

    private static int hopper(int lllllllllllllllllIIIlllllIlIIlIl) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIlIIlIl)) {
            return 32 + lllllllllllllllllIIIlllllIlIIlIl;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIlIIlIl)) {
            return 5 + (lllllllllllllllllIIIlllllIlIIlIl - 9);
        }
        return -1;
    }

    private static int brewingStand(int lllllllllllllllllIIIlllllIllIIIl) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIllIIIl)) {
            return 32 + lllllllllllllllllIIIlllllIllIIIl;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIllIIIl)) {
            return 5 + (lllllllllllllllllIIIlllllIllIIIl - 9);
        }
        return -1;
    }

    public SlotUtils() {
        SlotUtils lllllllllllllllllIIIllllllIlIIIl;
    }

    private static int lectern() {
        return -1;
    }

    private static int loom(int lllllllllllllllllIIIlllllIIIlllI) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIIIlllI)) {
            return 31 + lllllllllllllllllIIIlllllIIIlllI;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIIIlllI)) {
            return 4 + (lllllllllllllllllIIIlllllIIIlllI - 9);
        }
        return -1;
    }

    private static int beacon(int lllllllllllllllllIIIlllllIlIlIlI) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIlIlIlI)) {
            return 28 + lllllllllllllllllIIIlllllIlIlIlI;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIlIlIlI)) {
            return 1 + (lllllllllllllllllIIIlllllIlIlIlI - 9);
        }
        return -1;
    }

    static {
        MAIN_END = 35;
        OFFHAND = 45;
        HOTBAR_START = 0;
        MAIN_START = 9;
        ARMOR_END = 39;
        HOTBAR_END = 8;
        ARMOR_START = 36;
    }

    private static boolean isHotbar(int lllllllllllllllllIIIlllllIIIlIII) {
        return lllllllllllllllllIIIlllllIIIlIII >= 0 && lllllllllllllllllIIIlllllIIIlIII <= 8;
    }

    private static int craftingTable(int lllllllllllllllllIIIlllllIllllII) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIllllII)) {
            return 37 + lllllllllllllllllIIIlllllIllllII;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIllllII)) {
            return lllllllllllllllllIIIlllllIllllII + 1;
        }
        return -1;
    }

    private static boolean isMain(int lllllllllllllllllIIIlllllIIIIlIl) {
        return lllllllllllllllllIIIlllllIIIIlIl >= 9 && lllllllllllllllllIIIlllllIIIIlIl <= 35;
    }

    private static int horse(ScreenHandler lllllllllllllllllIIIlllllIIllIlI, int lllllllllllllllllIIIlllllIIlllII) {
        HorseBaseEntity lllllllllllllllllIIIlllllIIllIll = ((HorseScreenHandlerAccessor)lllllllllllllllllIIIlllllIIllIlI).getEntity();
        if (lllllllllllllllllIIIlllllIIllIll instanceof LlamaEntity) {
            int lllllllllllllllllIIIlllllIIlllll = ((LlamaEntity)lllllllllllllllllIIIlllllIIllIll).getStrength();
            if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIIlllII)) {
                return 2 + 3 * lllllllllllllllllIIIlllllIIlllll + 28 + lllllllllllllllllIIIlllllIIlllII;
            }
            if (SlotUtils.isMain(lllllllllllllllllIIIlllllIIlllII)) {
                return 2 + 3 * lllllllllllllllllIIIlllllIIlllll + 1 + (lllllllllllllllllIIIlllllIIlllII - 9);
            }
        } else if (lllllllllllllllllIIIlllllIIllIll instanceof HorseEntity || lllllllllllllllllIIIlllllIIllIll instanceof SkeletonHorseEntity || lllllllllllllllllIIIlllllIIllIll instanceof ZombieHorseEntity) {
            if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIIlllII)) {
                return 29 + lllllllllllllllllIIIlllllIIlllII;
            }
            if (SlotUtils.isMain(lllllllllllllllllIIIlllllIIlllII)) {
                return 2 + (lllllllllllllllllIIIlllllIIlllII - 9);
            }
        } else if (lllllllllllllllllIIIlllllIIllIll instanceof AbstractDonkeyEntity) {
            boolean lllllllllllllllllIIIlllllIIllllI = ((AbstractDonkeyEntity)lllllllllllllllllIIIlllllIIllIll).hasChest();
            if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIIlllII)) {
                return (lllllllllllllllllIIIlllllIIllllI ? 44 : 29) + lllllllllllllllllIIIlllllIIlllII;
            }
            if (SlotUtils.isMain(lllllllllllllllllIIIlllllIIlllII)) {
                return (lllllllllllllllllIIIlllllIIllllI ? 17 : 2) + (lllllllllllllllllIIIlllllIIlllII - 9);
            }
        }
        return -1;
    }

    private static int creativeInventory(int lllllllllllllllllIIIllllllIIIllI) {
        if (!(Utils.mc.currentScreen instanceof CreativeInventoryScreen) || ((CreativeInventoryScreenAccessor)Utils.mc.currentScreen).getSelectedTab() != ItemGroup.INVENTORY.getIndex()) {
            return -1;
        }
        return SlotUtils.survivalInventory(lllllllllllllllllIIIllllllIIIllI);
    }

    private static int villager(int lllllllllllllllllIIIlllllIlIllIl) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIlIllIl)) {
            return 30 + lllllllllllllllllIIIlllllIlIllIl;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIlIllIl)) {
            return 3 + (lllllllllllllllllIIIlllllIlIllIl - 9);
        }
        return -1;
    }

    private static int generic3x3(int lllllllllllllllllIIIlllllIllIllI) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIllIllI)) {
            return 36 + lllllllllllllllllIIIlllllIllIllI;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIllIllI)) {
            return lllllllllllllllllIIIlllllIllIllI;
        }
        return -1;
    }

    private static int grindstone(int lllllllllllllllllIIIlllllIIlIIIl) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIIlIIIl)) {
            return 30 + lllllllllllllllllIIIlllllIIlIIIl;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIIlIIIl)) {
            return 3 + (lllllllllllllllllIIIlllllIIlIIIl - 9);
        }
        return -1;
    }

    private static int survivalInventory(int lllllllllllllllllIIIllllllIIlIII) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIllllllIIlIII)) {
            return 36 + lllllllllllllllllIIIllllllIIlIII;
        }
        if (SlotUtils.isArmor(lllllllllllllllllIIIllllllIIlIII)) {
            return 5 + (lllllllllllllllllIIIllllllIIlIII - 36);
        }
        return lllllllllllllllllIIIllllllIIlIII;
    }

    private static int anvil(int lllllllllllllllllIIIlllllIlIIlll) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIlIIlll)) {
            return 30 + lllllllllllllllllIIIlllllIlIIlll;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIlIIlll)) {
            return 3 + (lllllllllllllllllIIIlllllIlIIlll - 9);
        }
        return -1;
    }

    private static int cartographyTable(int lllllllllllllllllIIIlllllIIlIlII) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIIlIlII)) {
            return 30 + lllllllllllllllllIIIlllllIIlIlII;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIIlIlII)) {
            return 3 + (lllllllllllllllllIIIlllllIIlIlII - 9);
        }
        return -1;
    }

    private static int enchantmentTable(int lllllllllllllllllIIIlllllIllIIll) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIllIIll)) {
            return 29 + lllllllllllllllllIIIlllllIllIIll;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIllIIll)) {
            return 2 + (lllllllllllllllllIIIlllllIllIIll - 9);
        }
        return -1;
    }

    private static int genericContainer(int lllllllllllllllllIIIllllllIIIIlI, int lllllllllllllllllIIIllllllIIIIIl) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIllllllIIIIlI)) {
            return (lllllllllllllllllIIIllllllIIIIIl + 3) * 9 + lllllllllllllllllIIIllllllIIIIlI;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIllllllIIIIlI)) {
            return lllllllllllllllllIIIllllllIIIIIl * 9 + (lllllllllllllllllIIIllllllIIIIlI - 9);
        }
        return -1;
    }

    private static int stonecutter(int lllllllllllllllllIIIlllllIIIllII) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIIIllII)) {
            return 29 + lllllllllllllllllIIIlllllIIIllII;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIIIllII)) {
            return 2 + (lllllllllllllllllIIIlllllIIIllII - 9);
        }
        return -1;
    }

    private static int furnace(int lllllllllllllllllIIIlllllIlllIlI) {
        if (SlotUtils.isHotbar(lllllllllllllllllIIIlllllIlllIlI)) {
            return 30 + lllllllllllllllllIIIlllllIlllIlI;
        }
        if (SlotUtils.isMain(lllllllllllllllllIIIlllllIlllIlI)) {
            return 3 + (lllllllllllllllllIIIlllllIlllIlI - 9);
        }
        return -1;
    }
}

