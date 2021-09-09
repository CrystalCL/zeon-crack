/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.screen.ScreenHandler
 *  net.minecraft.screen.GenericContainerScreenHandler
 */
package minegame159.meteorclient.systems.modules.world;

import java.util.concurrent.ThreadLocalRandom;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import minegame159.meteorclient.utils.player.ChatUtils;
import minegame159.meteorclient.utils.player.InvUtils;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;

public class AutoSteal
extends Module {
    private final /* synthetic */ Setting<Boolean> stealButtonEnabled;
    private final /* synthetic */ Setting<Boolean> autoDumpEnabled;
    private final /* synthetic */ SettingGroup sgDelays;
    private final /* synthetic */ Setting<Boolean> autoStealEnabled;
    private final /* synthetic */ Setting<Integer> minimumDelay;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Integer> randomDelay;
    private final /* synthetic */ Setting<Boolean> dumpButtonEnabled;

    private void steal(ScreenHandler llllllllllllllllllIllIIIlIlIIIll) {
        AutoSteal llllllllllllllllllIllIIIlIlIIlII;
        llllllllllllllllllIllIIIlIlIIlII.moveSlots(llllllllllllllllllIllIIIlIlIIIll, 0, llllllllllllllllllIllIIIlIlIIlII.getRows(llllllllllllllllllIllIIIlIlIIIll) * 9);
    }

    private int getSleepTime() {
        AutoSteal llllllllllllllllllIllIIIllIIIIIl;
        return llllllllllllllllllIllIIIllIIIIIl.minimumDelay.get() + (llllllllllllllllllIllIIIllIIIIIl.randomDelay.get() > 0 ? ThreadLocalRandom.current().nextInt(0, llllllllllllllllllIllIIIllIIIIIl.randomDelay.get()) : 0);
    }

    public boolean getDumpButtonEnabled() {
        AutoSteal llllllllllllllllllIllIIIlIIIIlll;
        return llllllllllllllllllIllIIIlIIIIlll.dumpButtonEnabled.get();
    }

    public boolean getAutoStealEnabled() {
        AutoSteal llllllllllllllllllIllIIIlIIIIlII;
        return llllllllllllllllllIllIIIlIIIIlII.autoStealEnabled.get();
    }

    public boolean getStealButtonEnabled() {
        AutoSteal llllllllllllllllllIllIIIlIIIlIlI;
        return llllllllllllllllllIllIIIlIIIlIlI.stealButtonEnabled.get();
    }

    private void dump(ScreenHandler llllllllllllllllllIllIIIlIIllIIl) {
        AutoSteal llllllllllllllllllIllIIIlIIllIlI;
        int llllllllllllllllllIllIIIlIIllIll = llllllllllllllllllIllIIIlIIllIlI.getRows(llllllllllllllllllIllIIIlIIllIIl) * 9;
        llllllllllllllllllIllIIIlIIllIlI.moveSlots(llllllllllllllllllIllIIIlIIllIIl, llllllllllllllllllIllIIIlIIllIll, llllllllllllllllllIllIIIlIIllIll + 36);
    }

    private void moveSlots(ScreenHandler llllllllllllllllllIllIIIlIllIIII, int llllllllllllllllllIllIIIlIlIlIll, int llllllllllllllllllIllIIIlIlIlllI) {
        for (int llllllllllllllllllIllIIIlIllIIlI = llllllllllllllllllIllIIIlIlIlIll; llllllllllllllllllIllIIIlIllIIlI < llllllllllllllllllIllIIIlIlIlllI; ++llllllllllllllllllIllIIIlIllIIlI) {
            AutoSteal llllllllllllllllllIllIIIlIllIIIl;
            if (!llllllllllllllllllIllIIIlIllIIII.getSlot(llllllllllllllllllIllIIIlIllIIlI).hasStack()) continue;
            int llllllllllllllllllIllIIIlIllIIll = llllllllllllllllllIllIIIlIllIIIl.getSleepTime();
            if (llllllllllllllllllIllIIIlIllIIll > 0) {
                try {
                    Thread.sleep(llllllllllllllllllIllIIIlIllIIll);
                }
                catch (InterruptedException llllllllllllllllllIllIIIlIllIlII) {
                    llllllllllllllllllIllIIIlIllIlII.printStackTrace();
                }
            }
            if (llllllllllllllllllIllIIIlIllIIIl.mc.currentScreen == null) break;
            InvUtils.quickMove().slotId(llllllllllllllllllIllIIIlIllIIlI);
        }
    }

    public boolean getAutoDumpEnabled() {
        AutoSteal llllllllllllllllllIllIIIlIIIIIII;
        return llllllllllllllllllIllIIIlIIIIIII.autoDumpEnabled.get();
    }

    public void dumpAsync(ScreenHandler llllllllllllllllllIllIIIlIIIllII) {
        AutoSteal llllllllllllllllllIllIIIlIIIllIl;
        MeteorExecutor.execute(() -> {
            AutoSteal llllllllllllllllllIllIIIIllllIll;
            llllllllllllllllllIllIIIIllllIll.dump(llllllllllllllllllIllIIIlIIIllII);
        });
    }

    public AutoSteal() {
        super(Categories.World, "auto-steal", "Automatically dumps or steals from storage blocks.");
        AutoSteal llllllllllllllllllIllIIIllIIIllI;
        llllllllllllllllllIllIIIllIIIllI.sgGeneral = llllllllllllllllllIllIIIllIIIllI.settings.getDefaultGroup();
        llllllllllllllllllIllIIIllIIIllI.sgDelays = llllllllllllllllllIllIIIllIIIllI.settings.createGroup("Delay");
        llllllllllllllllllIllIIIllIIIllI.stealButtonEnabled = llllllllllllllllllIllIIIllIIIllI.sgGeneral.add(new BoolSetting.Builder().name("steal-button-enabled").description("Shows the Steal button on the container screen.").defaultValue(true).build());
        llllllllllllllllllIllIIIllIIIllI.autoStealEnabled = llllllllllllllllllIllIIIllIIIllI.sgGeneral.add(new BoolSetting.Builder().name("auto-steal-enabled").description("Starts the auto steal when a container open.").defaultValue(false).onChanged(llllllllllllllllllIllIIIIllIllIl -> {
            AutoSteal llllllllllllllllllIllIIIIllIllII;
            llllllllllllllllllIllIIIIllIllII.checkAutoSettings();
        }).build());
        llllllllllllllllllIllIIIllIIIllI.dumpButtonEnabled = llllllllllllllllllIllIIIllIIIllI.sgGeneral.add(new BoolSetting.Builder().name("dump-button-enabled").description("Shows the Dump button on the container screen.").defaultValue(true).build());
        llllllllllllllllllIllIIIllIIIllI.autoDumpEnabled = llllllllllllllllllIllIIIllIIIllI.sgGeneral.add(new BoolSetting.Builder().name("auto-dump-enabled").description("Start auto dump when a container opens.").defaultValue(false).onChanged(llllllllllllllllllIllIIIIlllIIIl -> {
            AutoSteal llllllllllllllllllIllIIIIlllIIII;
            llllllllllllllllllIllIIIIlllIIII.checkAutoSettings();
        }).build());
        llllllllllllllllllIllIIIllIIIllI.minimumDelay = llllllllllllllllllIllIIIllIIIllI.sgDelays.add(new IntSetting.Builder().name("min-delay").description("The minimum delay between stealing the next stack in milliseconds.").sliderMax(1000).defaultValue(180).build());
        llllllllllllllllllIllIIIllIIIllI.randomDelay = llllllllllllllllllIllIIIllIIIllI.sgDelays.add(new IntSetting.Builder().name("random-delay").description("Randomly adds a delay of up to the specified time in milliseconds. Helps avoid anti-cheats.").min(0).sliderMax(1000).defaultValue(50).build());
    }

    private int getRows(ScreenHandler llllllllllllllllllIllIIIlIllllII) {
        return llllllllllllllllllIllIIIlIllllII instanceof GenericContainerScreenHandler ? ((GenericContainerScreenHandler)llllllllllllllllllIllIIIlIllllII).getRows() : 3;
    }

    private void checkAutoSettings() {
        AutoSteal llllllllllllllllllIllIIIllIIIlII;
        if (llllllllllllllllllIllIIIllIIIlII.autoStealEnabled.get().booleanValue() && llllllllllllllllllIllIIIllIIIlII.autoDumpEnabled.get().booleanValue()) {
            ChatUtils.error("You can't enable Auto Steal and Auto Dump at the same time!", new Object[0]);
            llllllllllllllllllIllIIIllIIIlII.autoDumpEnabled.set(false);
        }
    }

    public void stealAsync(ScreenHandler llllllllllllllllllIllIIIlIIlIIlI) {
        AutoSteal llllllllllllllllllIllIIIlIIlIIll;
        MeteorExecutor.execute(() -> {
            AutoSteal llllllllllllllllllIllIIIIlllIlIl;
            llllllllllllllllllIllIIIIlllIlIl.steal(llllllllllllllllllIllIIIlIIlIIlI);
        });
    }
}

