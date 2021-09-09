/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.Nullable
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.util.Pair
 *  net.minecraft.util.registry.RegistryKey
 */
package minegame159.meteorclient.systems.modules;

import com.mojang.serialization.Lifecycle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.game.GameJoinedEvent;
import minegame159.meteorclient.events.game.GameLeftEvent;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.events.meteor.ActiveModulesChangedEvent;
import minegame159.meteorclient.events.meteor.KeyEvent;
import minegame159.meteorclient.events.meteor.ModuleBindChangedEvent;
import minegame159.meteorclient.events.meteor.MouseButtonEvent;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.systems.System;
import minegame159.meteorclient.systems.Systems;
import minegame159.meteorclient.systems.modules.Category;
import minegame159.meteorclient.systems.modules.Exclusive.ActionLogger;
import minegame159.meteorclient.systems.modules.Exclusive.AntiCrystal;
import minegame159.meteorclient.systems.modules.Exclusive.AntiSetHome;
import minegame159.meteorclient.systems.modules.Exclusive.AutoBuild;
import minegame159.meteorclient.systems.modules.Exclusive.AutoCrystalHead;
import minegame159.meteorclient.systems.modules.Exclusive.AutoEz;
import minegame159.meteorclient.systems.modules.Exclusive.AutoHighway;
import minegame159.meteorclient.systems.modules.Exclusive.AutoLeave;
import minegame159.meteorclient.systems.modules.Exclusive.BedAuraPlus;
import minegame159.meteorclient.systems.modules.Exclusive.BreakInfo;
import minegame159.meteorclient.systems.modules.Exclusive.BurrowDetect;
import minegame159.meteorclient.systems.modules.Exclusive.ButtonTrap;
import minegame159.meteorclient.systems.modules.Exclusive.CevBreaker;
import minegame159.meteorclient.systems.modules.Exclusive.CityExploit;
import minegame159.meteorclient.systems.modules.Exclusive.CoordExploit;
import minegame159.meteorclient.systems.modules.Exclusive.CustomAutoTotem;
import minegame159.meteorclient.systems.modules.Exclusive.CustomCrystalAura;
import minegame159.meteorclient.systems.modules.Exclusive.DiscordPrecencePlus;
import minegame159.meteorclient.systems.modules.Exclusive.ExplosionProtector;
import minegame159.meteorclient.systems.modules.Exclusive.ExtraSurround;
import minegame159.meteorclient.systems.modules.Exclusive.FastBreak;
import minegame159.meteorclient.systems.modules.Exclusive.FastKill;
import minegame159.meteorclient.systems.modules.Exclusive.HoleFillerPlus;
import minegame159.meteorclient.systems.modules.Exclusive.InstaAutoCity;
import minegame159.meteorclient.systems.modules.Exclusive.NewChunks;
import minegame159.meteorclient.systems.modules.Exclusive.Phase;
import minegame159.meteorclient.systems.modules.Exclusive.PortalGodMode;
import minegame159.meteorclient.systems.modules.Exclusive.SevilaMode;
import minegame159.meteorclient.systems.modules.Exclusive.TntTrap;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.combat.AimAssist;
import minegame159.meteorclient.systems.modules.combat.AnchorAura;
import minegame159.meteorclient.systems.modules.combat.AntiAnchor;
import minegame159.meteorclient.systems.modules.combat.AntiAnvil;
import minegame159.meteorclient.systems.modules.combat.AntiBed;
import minegame159.meteorclient.systems.modules.combat.AntiHit;
import minegame159.meteorclient.systems.modules.combat.ArrowDodge;
import minegame159.meteorclient.systems.modules.combat.Auto32K;
import minegame159.meteorclient.systems.modules.combat.AutoAnvil;
import minegame159.meteorclient.systems.modules.combat.AutoArmor;
import minegame159.meteorclient.systems.modules.combat.AutoCity;
import minegame159.meteorclient.systems.modules.combat.AutoLog;
import minegame159.meteorclient.systems.modules.combat.AutoTotem;
import minegame159.meteorclient.systems.modules.combat.AutoTrap;
import minegame159.meteorclient.systems.modules.combat.AutoWeapon;
import minegame159.meteorclient.systems.modules.combat.AutoWeb;
import minegame159.meteorclient.systems.modules.combat.BedAura;
import minegame159.meteorclient.systems.modules.combat.BowSpam;
import minegame159.meteorclient.systems.modules.combat.Burrow;
import minegame159.meteorclient.systems.modules.combat.Criticals;
import minegame159.meteorclient.systems.modules.combat.CrystalAura;
import minegame159.meteorclient.systems.modules.combat.Hitboxes;
import minegame159.meteorclient.systems.modules.combat.HoleFiller;
import minegame159.meteorclient.systems.modules.combat.KillAura;
import minegame159.meteorclient.systems.modules.combat.OffhandExtra;
import minegame159.meteorclient.systems.modules.combat.Quiver;
import minegame159.meteorclient.systems.modules.combat.SelfAnvil;
import minegame159.meteorclient.systems.modules.combat.SelfTrap;
import minegame159.meteorclient.systems.modules.combat.SelfWeb;
import minegame159.meteorclient.systems.modules.combat.SmartSurround;
import minegame159.meteorclient.systems.modules.combat.Surround;
import minegame159.meteorclient.systems.modules.combat.TotemPopNotifier;
import minegame159.meteorclient.systems.modules.combat.Trigger;
import minegame159.meteorclient.systems.modules.misc.Announcer;
import minegame159.meteorclient.systems.modules.misc.AntiBot;
import minegame159.meteorclient.systems.modules.misc.AntiPacketKick;
import minegame159.meteorclient.systems.modules.misc.AutoReconnect;
import minegame159.meteorclient.systems.modules.misc.BetterChat;
import minegame159.meteorclient.systems.modules.misc.BetterTab;
import minegame159.meteorclient.systems.modules.misc.BookBot;
import minegame159.meteorclient.systems.modules.misc.DiscordPresence;
import minegame159.meteorclient.systems.modules.misc.MessageAura;
import minegame159.meteorclient.systems.modules.misc.MiddleClickFriend;
import minegame159.meteorclient.systems.modules.misc.Notebot;
import minegame159.meteorclient.systems.modules.misc.OffhandCrash;
import minegame159.meteorclient.systems.modules.misc.PacketCanceller;
import minegame159.meteorclient.systems.modules.misc.SoundBlocker;
import minegame159.meteorclient.systems.modules.misc.Spam;
import minegame159.meteorclient.systems.modules.misc.Swarm;
import minegame159.meteorclient.systems.modules.misc.VanillaSpoof;
import minegame159.meteorclient.systems.modules.misc.VisualRange;
import minegame159.meteorclient.systems.modules.movement.AirJump;
import minegame159.meteorclient.systems.modules.movement.Anchor;
import minegame159.meteorclient.systems.modules.movement.AntiLevitation;
import minegame159.meteorclient.systems.modules.movement.AntiVoid;
import minegame159.meteorclient.systems.modules.movement.AutoJump;
import minegame159.meteorclient.systems.modules.movement.AutoWalk;
import minegame159.meteorclient.systems.modules.movement.Blink;
import minegame159.meteorclient.systems.modules.movement.BoatFly;
import minegame159.meteorclient.systems.modules.movement.ClickTP;
import minegame159.meteorclient.systems.modules.movement.ElytraBoost;
import minegame159.meteorclient.systems.modules.movement.EntityControl;
import minegame159.meteorclient.systems.modules.movement.EntitySpeed;
import minegame159.meteorclient.systems.modules.movement.FastClimb;
import minegame159.meteorclient.systems.modules.movement.Flight;
import minegame159.meteorclient.systems.modules.movement.GUIMove;
import minegame159.meteorclient.systems.modules.movement.HighJump;
import minegame159.meteorclient.systems.modules.movement.Jesus;
import minegame159.meteorclient.systems.modules.movement.NoFall;
import minegame159.meteorclient.systems.modules.movement.NoSlow;
import minegame159.meteorclient.systems.modules.movement.Parkour;
import minegame159.meteorclient.systems.modules.movement.ReverseStep;
import minegame159.meteorclient.systems.modules.movement.SafeWalk;
import minegame159.meteorclient.systems.modules.movement.Scaffold;
import minegame159.meteorclient.systems.modules.movement.Slippy;
import minegame159.meteorclient.systems.modules.movement.Spider;
import minegame159.meteorclient.systems.modules.movement.Sprint;
import minegame159.meteorclient.systems.modules.movement.Step;
import minegame159.meteorclient.systems.modules.movement.Velocity;
import minegame159.meteorclient.systems.modules.movement.elytrafly.ElytraFly;
import minegame159.meteorclient.systems.modules.movement.speed.Speed;
import minegame159.meteorclient.systems.modules.player.AirPlace;
import minegame159.meteorclient.systems.modules.player.AntiAFK;
import minegame159.meteorclient.systems.modules.player.AntiHunger;
import minegame159.meteorclient.systems.modules.player.AutoClicker;
import minegame159.meteorclient.systems.modules.player.AutoDrop;
import minegame159.meteorclient.systems.modules.player.AutoEat;
import minegame159.meteorclient.systems.modules.player.AutoFish;
import minegame159.meteorclient.systems.modules.player.AutoGap;
import minegame159.meteorclient.systems.modules.player.AutoMend;
import minegame159.meteorclient.systems.modules.player.AutoReplenish;
import minegame159.meteorclient.systems.modules.player.AutoRespawn;
import minegame159.meteorclient.systems.modules.player.AutoTool;
import minegame159.meteorclient.systems.modules.player.ChestSwap;
import minegame159.meteorclient.systems.modules.player.DeathPosition;
import minegame159.meteorclient.systems.modules.player.EXPThrower;
import minegame159.meteorclient.systems.modules.player.FakePlayer;
import minegame159.meteorclient.systems.modules.player.FastUse;
import minegame159.meteorclient.systems.modules.player.GhostHand;
import minegame159.meteorclient.systems.modules.player.LiquidInteract;
import minegame159.meteorclient.systems.modules.player.MiddleClickExtra;
import minegame159.meteorclient.systems.modules.player.NameProtect;
import minegame159.meteorclient.systems.modules.player.NoBreakDelay;
import minegame159.meteorclient.systems.modules.player.NoInteract;
import minegame159.meteorclient.systems.modules.player.NoMiningTrace;
import minegame159.meteorclient.systems.modules.player.NoRotate;
import minegame159.meteorclient.systems.modules.player.Portals;
import minegame159.meteorclient.systems.modules.player.PotionSpoof;
import minegame159.meteorclient.systems.modules.player.Reach;
import minegame159.meteorclient.systems.modules.player.Rotation;
import minegame159.meteorclient.systems.modules.player.SpeedMine;
import minegame159.meteorclient.systems.modules.player.XCarry;
import minegame159.meteorclient.systems.modules.render.BetterTooltips;
import minegame159.meteorclient.systems.modules.render.BlockSelection;
import minegame159.meteorclient.systems.modules.render.BossStack;
import minegame159.meteorclient.systems.modules.render.Breadcrumbs;
import minegame159.meteorclient.systems.modules.render.BreakIndicators;
import minegame159.meteorclient.systems.modules.render.CameraClip;
import minegame159.meteorclient.systems.modules.render.Chams;
import minegame159.meteorclient.systems.modules.render.CityESP;
import minegame159.meteorclient.systems.modules.render.CustomFOV;
import minegame159.meteorclient.systems.modules.render.ESP;
import minegame159.meteorclient.systems.modules.render.EntityOwner;
import minegame159.meteorclient.systems.modules.render.FreeRotate;
import minegame159.meteorclient.systems.modules.render.Freecam;
import minegame159.meteorclient.systems.modules.render.Fullbright;
import minegame159.meteorclient.systems.modules.render.HandView;
import minegame159.meteorclient.systems.modules.render.HoleESP;
import minegame159.meteorclient.systems.modules.render.ItemHighlight;
import minegame159.meteorclient.systems.modules.render.ItemPhysics;
import minegame159.meteorclient.systems.modules.render.LightOverlay;
import minegame159.meteorclient.systems.modules.render.LogoutSpots;
import minegame159.meteorclient.systems.modules.render.ModelTweaks;
import minegame159.meteorclient.systems.modules.render.Nametags;
import minegame159.meteorclient.systems.modules.render.NoRender;
import minegame159.meteorclient.systems.modules.render.ParticleBlocker;
import minegame159.meteorclient.systems.modules.render.Search;
import minegame159.meteorclient.systems.modules.render.StorageESP;
import minegame159.meteorclient.systems.modules.render.TimeChanger;
import minegame159.meteorclient.systems.modules.render.Tracers;
import minegame159.meteorclient.systems.modules.render.Trail;
import minegame159.meteorclient.systems.modules.render.Trajectories;
import minegame159.meteorclient.systems.modules.render.UnfocusedCPU;
import minegame159.meteorclient.systems.modules.render.VoidESP;
import minegame159.meteorclient.systems.modules.render.WallHack;
import minegame159.meteorclient.systems.modules.render.WaypointsModule;
import minegame159.meteorclient.systems.modules.render.Xray;
import minegame159.meteorclient.systems.modules.render.Zoom;
import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.world.Ambience;
import minegame159.meteorclient.systems.modules.world.AntiCactus;
import minegame159.meteorclient.systems.modules.world.AutoBreed;
import minegame159.meteorclient.systems.modules.world.AutoBrewer;
import minegame159.meteorclient.systems.modules.world.AutoMount;
import minegame159.meteorclient.systems.modules.world.AutoMountBypassDupe;
import minegame159.meteorclient.systems.modules.world.AutoNametag;
import minegame159.meteorclient.systems.modules.world.AutoShearer;
import minegame159.meteorclient.systems.modules.world.AutoSign;
import minegame159.meteorclient.systems.modules.world.AutoSmelter;
import minegame159.meteorclient.systems.modules.world.AutoSteal;
import minegame159.meteorclient.systems.modules.world.BuildHeight;
import minegame159.meteorclient.systems.modules.world.EChestFarmer;
import minegame159.meteorclient.systems.modules.world.EndermanLook;
import minegame159.meteorclient.systems.modules.world.EntityLogger;
import minegame159.meteorclient.systems.modules.world.Flamethrower;
import minegame159.meteorclient.systems.modules.world.InfinityMiner;
import minegame159.meteorclient.systems.modules.world.InstaMine;
import minegame159.meteorclient.systems.modules.world.LiquidFiller;
import minegame159.meteorclient.systems.modules.world.MountBypass;
import minegame159.meteorclient.systems.modules.world.Nuker;
import minegame159.meteorclient.systems.modules.world.PacketMine;
import minegame159.meteorclient.systems.modules.world.StashFinder;
import minegame159.meteorclient.systems.modules.world.Timer;
import minegame159.meteorclient.systems.modules.world.VeinMiner;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.misc.input.Input;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.RegistryKey;

public class Modules
extends System<Modules> {
    private static /* synthetic */ List<String> s;
    private final /* synthetic */ Map<Category, List<Module>> groups;
    private final /* synthetic */ Map<Class<? extends Module>, Module> moduleInstances;
    public static /* synthetic */ boolean REGISTERING_CATEGORIES;
    private static final /* synthetic */ List<Category> CATEGORIES;
    private /* synthetic */ Module moduleToBind;
    public static final /* synthetic */ ModuleRegistry REGISTRY;
    private final /* synthetic */ List<Module> active;
    private final /* synthetic */ List<Module> modules;

    private boolean onBinding(boolean llllllllllllllllllIIIlIIIIllIIIl, int llllllllllllllllllIIIlIIIIllIIll) {
        Modules llllllllllllllllllIIIlIIIIllIlIl;
        if (llllllllllllllllllIIIlIIIIllIlIl.moduleToBind != null && llllllllllllllllllIIIlIIIIllIlIl.moduleToBind.keybind.canBindTo(llllllllllllllllllIIIlIIIIllIIIl, llllllllllllllllllIIIlIIIIllIIll)) {
            llllllllllllllllllIIIlIIIIllIlIl.moduleToBind.keybind.set(llllllllllllllllllIIIlIIIIllIIIl, llllllllllllllllllIIIlIIIIllIIll);
            ChatUtils.prefixInfo("KeyBinds", "Module (highlight)%s (default)bound to (highlight)%s(default).", llllllllllllllllllIIIlIIIIllIlIl.moduleToBind.title, llllllllllllllllllIIIlIIIIllIlIl.moduleToBind.keybind);
            MeteorClient.EVENT_BUS.post(ModuleBindChangedEvent.get(llllllllllllllllllIIIlIIIIllIlIl.moduleToBind));
            llllllllllllllllllIIIlIIIIllIlIl.moduleToBind = null;
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        Modules llllllllllllllllllIIIlIIllIIllll;
        llllllllllllllllllIIIlIIllIIllll.initCombat();
        llllllllllllllllllIIIlIIllIIllll.initPlayer();
        llllllllllllllllllIIIlIIllIIllll.initMovement();
        llllllllllllllllllIIIlIIllIIllll.initRender();
        llllllllllllllllllIIIlIIllIIllll.initWorld();
        llllllllllllllllllIIIlIIllIIllll.initMisc();
        llllllllllllllllllIIIlIIllIIllll.initExclusive();
    }

    private void initMovement() {
        Modules llllllllllllllllllIIIIlllIlIIlII;
        llllllllllllllllllIIIIlllIlIIlII.add(new AirJump());
        llllllllllllllllllIIIIlllIlIIlII.add(new Anchor());
        llllllllllllllllllIIIIlllIlIIlII.add(new AntiLevitation());
        llllllllllllllllllIIIIlllIlIIlII.add(new AutoJump());
        llllllllllllllllllIIIIlllIlIIlII.add(new Sprint());
        llllllllllllllllllIIIIlllIlIIlII.add(new AutoWalk());
        llllllllllllllllllIIIIlllIlIIlII.add(new Blink());
        llllllllllllllllllIIIIlllIlIIlII.add(new BoatFly());
        llllllllllllllllllIIIIlllIlIIlII.add(new ClickTP());
        llllllllllllllllllIIIIlllIlIIlII.add(new ElytraBoost());
        llllllllllllllllllIIIIlllIlIIlII.add(new ElytraFly());
        llllllllllllllllllIIIIlllIlIIlII.add(new EntityControl());
        llllllllllllllllllIIIIlllIlIIlII.add(new EntitySpeed());
        llllllllllllllllllIIIIlllIlIIlII.add(new FastClimb());
        llllllllllllllllllIIIIlllIlIIlII.add(new Flight());
        llllllllllllllllllIIIIlllIlIIlII.add(new GUIMove());
        llllllllllllllllllIIIIlllIlIIlII.add(new HighJump());
        llllllllllllllllllIIIIlllIlIIlII.add(new Jesus());
        llllllllllllllllllIIIIlllIlIIlII.add(new NoFall());
        llllllllllllllllllIIIIlllIlIIlII.add(new NoSlow());
        llllllllllllllllllIIIIlllIlIIlII.add(new Parkour());
        llllllllllllllllllIIIIlllIlIIlII.add(new ReverseStep());
        llllllllllllllllllIIIIlllIlIIlII.add(new SafeWalk());
        llllllllllllllllllIIIIlllIlIIlII.add(new Scaffold());
        llllllllllllllllllIIIIlllIlIIlII.add(new Speed());
        llllllllllllllllllIIIIlllIlIIlII.add(new Spider());
        llllllllllllllllllIIIIlllIlIIlII.add(new Slippy());
        llllllllllllllllllIIIIlllIlIIlII.add(new Step());
        llllllllllllllllllIIIIlllIlIIlII.add(new Velocity());
        llllllllllllllllllIIIIlllIlIIlII.add(new AntiVoid());
    }

    @EventHandler(priority=100)
    private void onKey(KeyEvent llllllllllllllllllIIIlIIIIlIlIlI) {
        Modules llllllllllllllllllIIIlIIIIlIlIll;
        if (llllllllllllllllllIIIlIIIIlIlIlI.action == KeyAction.Repeat) {
            return;
        }
        llllllllllllllllllIIIlIIIIlIlIll.onAction(true, llllllllllllllllllIIIlIIIIlIlIlI.key, llllllllllllllllllIIIlIIIIlIlIlI.action == KeyAction.Press);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void disableAll() {
        Modules llllllllllllllllllIIIIlllllIIlll;
        List<Module> llllllllllllllllllIIIIlllllIIlIl = llllllllllllllllllIIIIlllllIIlll.active;
        synchronized (llllllllllllllllllIIIIlllllIIlIl) {
            for (Module llllllllllllllllllIIIIlllllIlIII : llllllllllllllllllIIIIlllllIIlll.active.toArray(new Module[0])) {
                llllllllllllllllllIIIIlllllIlIII.toggle(Utils.canUpdate());
            }
        }
    }

    @EventHandler(priority=200)
    private void onButtonBinding(MouseButtonEvent llllllllllllllllllIIIlIIIIlllIIl) {
        Modules llllllllllllllllllIIIlIIIIlllIlI;
        if (llllllllllllllllllIIIlIIIIlllIlI.onBinding(false, llllllllllllllllllIIIlIIIIlllIIl.button)) {
            llllllllllllllllllIIIlIIIIlllIIl.cancel();
        }
    }

    private void onAction(boolean llllllllllllllllllIIIlIIIIIllIll, int llllllllllllllllllIIIlIIIIIlIllI, boolean llllllllllllllllllIIIlIIIIIllIIl) {
        if (MinecraftClient.getInstance().currentScreen == null && !Input.isKeyPressed(292)) {
            Modules llllllllllllllllllIIIlIIIIIllIII;
            for (Module llllllllllllllllllIIIlIIIIIlllIl : llllllllllllllllllIIIlIIIIIllIII.moduleInstances.values()) {
                if (!llllllllllllllllllIIIlIIIIIlllIl.keybind.matches(llllllllllllllllllIIIlIIIIIllIll, llllllllllllllllllIIIlIIIIIlIllI) || !llllllllllllllllllIIIlIIIIIllIIl && !llllllllllllllllllIIIlIIIIIlllIl.toggleOnBindRelease) continue;
                llllllllllllllllllIIIlIIIIIlllIl.doAction();
                llllllllllllllllllIIIlIIIIIlllIl.sendToggledMsg();
            }
        }
    }

    public Module get(String llllllllllllllllllIIIlIIlIlIlllI) {
        Modules llllllllllllllllllIIIlIIlIlIllll;
        for (Module llllllllllllllllllIIIlIIlIllIIII : llllllllllllllllllIIIlIIlIlIllll.moduleInstances.values()) {
            if (!llllllllllllllllllIIIlIIlIllIIII.name.equalsIgnoreCase(llllllllllllllllllIIIlIIlIlIlllI)) continue;
            return llllllllllllllllllIIIlIIlIllIIII;
        }
        return null;
    }

    public void setModuleToBind(Module llllllllllllllllllIIIlIIIlIIIlll) {
        llllllllllllllllllIIIlIIIlIIlIII.moduleToBind = llllllllllllllllllIIIlIIIlIIIlll;
    }

    public <T extends Module> T get(Class<T> llllllllllllllllllIIIlIIlIllIlIl) {
        Modules llllllllllllllllllIIIlIIlIllIllI;
        return (T)llllllllllllllllllIIIlIIlIllIllI.moduleInstances.get(llllllllllllllllllIIIlIIlIllIlIl);
    }

    private void initRender() {
        Modules llllllllllllllllllIIIIlllIlIIIII;
        llllllllllllllllllIIIIlllIlIIIII.add(new BlockSelection());
        llllllllllllllllllIIIIlllIlIIIII.add(new Breadcrumbs());
        llllllllllllllllllIIIIlllIlIIIII.add(new BreakIndicators());
        llllllllllllllllllIIIIlllIlIIIII.add(new CameraClip());
        llllllllllllllllllIIIIlllIlIIIII.add(new Chams());
        llllllllllllllllllIIIIlllIlIIIII.add(new CityESP());
        llllllllllllllllllIIIIlllIlIIIII.add(new CustomFOV());
        llllllllllllllllllIIIIlllIlIIIII.add(new ESP());
        llllllllllllllllllIIIIlllIlIIIII.add(new EntityOwner());
        llllllllllllllllllIIIIlllIlIIIII.add(new FreeRotate());
        llllllllllllllllllIIIIlllIlIIIII.add(new Freecam());
        llllllllllllllllllIIIIlllIlIIIII.add(new Fullbright());
        llllllllllllllllllIIIIlllIlIIIII.add(new HUD());
        llllllllllllllllllIIIIlllIlIIIII.add(new HandView());
        llllllllllllllllllIIIIlllIlIIIII.add(new HoleESP());
        llllllllllllllllllIIIIlllIlIIIII.add(new ItemPhysics());
        llllllllllllllllllIIIIlllIlIIIII.add(new LogoutSpots());
        llllllllllllllllllIIIIlllIlIIIII.add(new Nametags());
        llllllllllllllllllIIIIlllIlIIIII.add(new NoRender());
        llllllllllllllllllIIIIlllIlIIIII.add(new ParticleBlocker());
        llllllllllllllllllIIIIlllIlIIIII.add(new Search());
        llllllllllllllllllIIIIlllIlIIIII.add(new StorageESP());
        llllllllllllllllllIIIIlllIlIIIII.add(new TimeChanger());
        llllllllllllllllllIIIIlllIlIIIII.add(new Tracers());
        llllllllllllllllllIIIIlllIlIIIII.add(new Trajectories());
        llllllllllllllllllIIIIlllIlIIIII.add(new UnfocusedCPU());
        llllllllllllllllllIIIIlllIlIIIII.add(new VoidESP());
        llllllllllllllllllIIIIlllIlIIIII.add(new Xray());
        llllllllllllllllllIIIIlllIlIIIII.add(new BossStack());
        llllllllllllllllllIIIIlllIlIIIII.add(new ItemHighlight());
        llllllllllllllllllIIIIlllIlIIIII.add(new ModelTweaks());
        llllllllllllllllllIIIIlllIlIIIII.add(new LightOverlay());
        llllllllllllllllllIIIIlllIlIIIII.add(new Zoom());
        llllllllllllllllllIIIIlllIlIIIII.add(new WallHack());
        llllllllllllllllllIIIIlllIlIIIII.add(new WaypointsModule());
        llllllllllllllllllIIIIlllIlIIIII.add(new BetterTooltips());
    }

    @Override
    public NbtCompound toTag() {
        Modules llllllllllllllllllIIIIllllIlIlll;
        NbtCompound llllllllllllllllllIIIIllllIlIllI = new NbtCompound();
        NbtList llllllllllllllllllIIIIllllIlIlIl = new NbtList();
        for (Module llllllllllllllllllIIIIllllIllIII : llllllllllllllllllIIIIllllIlIlll.getAll()) {
            NbtCompound llllllllllllllllllIIIIllllIllIIl = llllllllllllllllllIIIIllllIllIII.toTag();
            if (llllllllllllllllllIIIIllllIllIIl == null) continue;
            llllllllllllllllllIIIIllllIlIlIl.add((Object)llllllllllllllllllIIIIllllIllIIl);
        }
        llllllllllllllllllIIIIllllIlIllI.put("modules", (NbtElement)llllllllllllllllllIIIIllllIlIlIl);
        return llllllllllllllllllIIIIllllIlIllI;
    }

    public List<Pair<Module, Integer>> searchSettingTitles(String llllllllllllllllllIIIlIIIllIIlll) {
        Modules llllllllllllllllllIIIlIIIllIlIII;
        ArrayList<Pair<Module, Integer>> llllllllllllllllllIIIlIIIllIlIIl = new ArrayList<Pair<Module, Integer>>();
        for (Module llllllllllllllllllIIIlIIIllIllII : llllllllllllllllllIIIlIIIllIlIII.moduleInstances.values()) {
            block1: for (SettingGroup llllllllllllllllllIIIlIIIllIllIl : llllllllllllllllllIIIlIIIllIllII.settings) {
                for (Setting<?> llllllllllllllllllIIIlIIIllIlllI : llllllllllllllllllIIIlIIIllIllIl) {
                    int llllllllllllllllllIIIlIIIllIllll = Utils.search(llllllllllllllllllIIIlIIIllIlllI.title, llllllllllllllllllIIIlIIIllIIlll);
                    if (llllllllllllllllllIIIlIIIllIllll <= 0) continue;
                    llllllllllllllllllIIIlIIIllIlIIl.add((Pair<Module, Integer>)new Pair((Object)llllllllllllllllllIIIlIIIllIllII, (Object)llllllllllllllllllIIIlIIIllIllll));
                    continue block1;
                }
            }
        }
        llllllllllllllllllIIIlIIIllIlIIl.sort(Comparator.comparingInt(llllllllllllllllllIIIIlllIIIIlll -> -((Integer)llllllllllllllllllIIIIlllIIIIlll.getRight()).intValue()));
        return llllllllllllllllllIIIlIIIllIlIIl;
    }

    @Override
    public Modules fromTag(NbtCompound llllllllllllllllllIIIIllllIIIIll) {
        Modules llllllllllllllllllIIIIllllIIIIIl;
        llllllllllllllllllIIIIllllIIIIIl.disableAll();
        NbtList llllllllllllllllllIIIIllllIIIIlI = llllllllllllllllllIIIIllllIIIIll.getList("modules", 10);
        for (NbtElement llllllllllllllllllIIIIllllIIIlIl : llllllllllllllllllIIIIllllIIIIlI) {
            NbtCompound llllllllllllllllllIIIIllllIIIlll = (NbtCompound)llllllllllllllllllIIIIllllIIIlIl;
            Module llllllllllllllllllIIIIllllIIIllI = llllllllllllllllllIIIIllllIIIIIl.get(llllllllllllllllllIIIIllllIIIlll.getString("name"));
            if (llllllllllllllllllIIIIllllIIIllI == null) continue;
            llllllllllllllllllIIIIllllIIIllI.fromTag(llllllllllllllllllIIIIllllIIIlll);
        }
        return llllllllllllllllllIIIIllllIIIIIl;
    }

    public static Modules get() {
        return Systems.get(Modules.class);
    }

    public void sortModules() {
        Modules llllllllllllllllllIIIlIIllIIlIIl;
        for (List<Module> llllllllllllllllllIIIlIIllIIlIlI : llllllllllllllllllIIIlIIllIIlIIl.groups.values()) {
            llllllllllllllllllIIIlIIllIIlIlI.sort(Comparator.comparing(llllllllllllllllllIIIIllIlllllIl -> llllllllllllllllllIIIIllIlllllIl.title));
        }
        llllllllllllllllllIIIlIIllIIlIIl.modules.sort(Comparator.comparing(llllllllllllllllllIIIIllIlllllll -> llllllllllllllllllIIIIllIlllllll.title));
    }

    public List<Module> getGroup(Category llllllllllllllllllIIIlIIlIIlllIl) {
        Modules llllllllllllllllllIIIlIIlIIlllII;
        return llllllllllllllllllIIIlIIlIIlllII.groups.computeIfAbsent(llllllllllllllllllIIIlIIlIIlllIl, llllllllllllllllllIIIIlllIIIIIlI -> new ArrayList());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void addActive(Module llllllllllllllllllIIIlIIIlIlIlll) {
        Modules llllllllllllllllllIIIlIIIlIllIII;
        List<Module> llllllllllllllllllIIIlIIIlIlIllI = llllllllllllllllllIIIlIIIlIllIII.active;
        synchronized (llllllllllllllllllIIIlIIIlIlIllI) {
            if (!llllllllllllllllllIIIlIIIlIllIII.active.contains(llllllllllllllllllIIIlIIIlIlIlll)) {
                llllllllllllllllllIIIlIIIlIllIII.active.add(llllllllllllllllllIIIlIIIlIlIlll);
                MeteorClient.EVENT_BUS.post(ActiveModulesChangedEvent.get());
            }
        }
    }

    @EventHandler(priority=200)
    private void onKeyBinding(KeyEvent llllllllllllllllllIIIlIIIlIIIIIl) {
        Modules llllllllllllllllllIIIlIIIlIIIIII;
        if (llllllllllllllllllIIIlIIIlIIIIII.onBinding(true, llllllllllllllllllIIIlIIIlIIIIIl.key)) {
            llllllllllllllllllIIIlIIIlIIIIIl.cancel();
        }
    }

    @EventHandler(priority=100)
    private void onMouseButton(MouseButtonEvent llllllllllllllllllIIIlIIIIlIIlII) {
        Modules llllllllllllllllllIIIlIIIIlIIlll;
        if (llllllllllllllllllIIIlIIIIlIIlII.action == KeyAction.Repeat) {
            return;
        }
        llllllllllllllllllIIIlIIIIlIIlll.onAction(false, llllllllllllllllllIIIlIIIIlIIlII.button, llllllllllllllllllIIIlIIIIlIIlII.action == KeyAction.Press);
    }

    public static void registerCategory(Category llllllllllllllllllIIIlIIllIIIlII) {
        if (!REGISTERING_CATEGORIES) {
            throw new RuntimeException("Modules.registerCategory - Cannot register category outside of onRegisterCategories callback.");
        }
        CATEGORIES.add(llllllllllllllllllIIIlIIllIIIlII);
    }

    public List<Module> getList() {
        Modules llllllllllllllllllIIIlIIlIIlIlIl;
        return llllllllllllllllllIIIlIIlIIlIlIl.modules;
    }

    @Deprecated
    public void addModule(Module llllllllllllllllllIIIIlllIlIllII) {
        Modules llllllllllllllllllIIIIlllIlIllIl;
        llllllllllllllllllIIIIlllIlIllIl.add(llllllllllllllllllIIIIlllIlIllII);
    }

    @EventHandler(priority=201)
    private void onOpenScreen(OpenScreenEvent llllllllllllllllllIIIlIIIIIIllIl) {
        Modules llllllllllllllllllIIIlIIIIIIlllI;
        for (Module llllllllllllllllllIIIlIIIIIIllll : llllllllllllllllllIIIlIIIIIIlllI.moduleInstances.values()) {
            if (!llllllllllllllllllIIIlIIIIIIllll.toggleOnBindRelease || !llllllllllllllllllIIIlIIIIIIllll.isActive()) continue;
            llllllllllllllllllIIIlIIIIIIllll.toggle();
            llllllllllllllllllIIIlIIIIIIllll.sendToggledMsg();
        }
    }

    private void initExclusive() {
        Modules llllllllllllllllllIIIIlllIIllIII;
        llllllllllllllllllIIIIlllIIllIII.add(new ActionLogger());
        llllllllllllllllllIIIIlllIIllIII.add(new AntiCrystal());
        llllllllllllllllllIIIIlllIIllIII.add(new AntiSetHome());
        llllllllllllllllllIIIIlllIIllIII.add(new AutoBuild());
        llllllllllllllllllIIIIlllIIllIII.add(new AutoCrystalHead());
        llllllllllllllllllIIIIlllIIllIII.add(new AutoEz());
        llllllllllllllllllIIIIlllIIllIII.add(new AutoHighway());
        llllllllllllllllllIIIIlllIIllIII.add(new AutoLeave());
        llllllllllllllllllIIIIlllIIllIII.add(new BedAuraPlus());
        llllllllllllllllllIIIIlllIIllIII.add(new BreakInfo());
        llllllllllllllllllIIIIlllIIllIII.add(new BurrowDetect());
        llllllllllllllllllIIIIlllIIllIII.add(new ButtonTrap());
        llllllllllllllllllIIIIlllIIllIII.add(new CevBreaker());
        llllllllllllllllllIIIIlllIIllIII.add(new CityExploit());
        llllllllllllllllllIIIIlllIIllIII.add(new CoordExploit());
        llllllllllllllllllIIIIlllIIllIII.add(new CustomAutoTotem());
        llllllllllllllllllIIIIlllIIllIII.add(new CustomCrystalAura());
        llllllllllllllllllIIIIlllIIllIII.add(new DiscordPrecencePlus());
        llllllllllllllllllIIIIlllIIllIII.add(new ExplosionProtector());
        llllllllllllllllllIIIIlllIIllIII.add(new ExtraSurround());
        llllllllllllllllllIIIIlllIIllIII.add(new FastBreak());
        llllllllllllllllllIIIIlllIIllIII.add(new FastKill());
        llllllllllllllllllIIIIlllIIllIII.add(new HoleFillerPlus());
        llllllllllllllllllIIIIlllIIllIII.add(new InstaAutoCity());
        llllllllllllllllllIIIIlllIIllIII.add(new NewChunks());
        llllllllllllllllllIIIIlllIIllIII.add(new Phase());
        llllllllllllllllllIIIIlllIIllIII.add(new PortalGodMode());
        llllllllllllllllllIIIIlllIIllIII.add(new SevilaMode());
        llllllllllllllllllIIIIlllIIllIII.add(new TntTrap());
    }

    public Collection<Module> getAll() {
        Modules llllllllllllllllllIIIlIIlIIllIIl;
        return llllllllllllllllllIIIlIIlIIllIIl.moduleInstances.values();
    }

    private void initCombat() {
        Modules llllllllllllllllllIIIIlllIlIlIIl;
        llllllllllllllllllIIIIlllIlIlIIl.add(new AimAssist());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AnchorAura());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AntiAnvil());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AntiAnchor());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AntiBed());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AntiHit());
        llllllllllllllllllIIIIlllIlIlIIl.add(new ArrowDodge());
        llllllllllllllllllIIIIlllIlIlIIl.add(new Auto32K());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AutoAnvil());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AutoArmor());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AutoCity());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AutoLog());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AutoTotem());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AutoTrap());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AutoWeapon());
        llllllllllllllllllIIIIlllIlIlIIl.add(new AutoWeb());
        llllllllllllllllllIIIIlllIlIlIIl.add(new BedAura());
        llllllllllllllllllIIIIlllIlIlIIl.add(new BowSpam());
        llllllllllllllllllIIIIlllIlIlIIl.add(new Criticals());
        llllllllllllllllllIIIIlllIlIlIIl.add(new CrystalAura());
        llllllllllllllllllIIIIlllIlIlIIl.add(new Hitboxes());
        llllllllllllllllllIIIIlllIlIlIIl.add(new HoleFiller());
        llllllllllllllllllIIIIlllIlIlIIl.add(new KillAura());
        llllllllllllllllllIIIIlllIlIlIIl.add(new OffhandExtra());
        llllllllllllllllllIIIIlllIlIlIIl.add(new Quiver());
        llllllllllllllllllIIIIlllIlIlIIl.add(new SelfAnvil());
        llllllllllllllllllIIIIlllIlIlIIl.add(new SelfTrap());
        llllllllllllllllllIIIIlllIlIlIIl.add(new SelfWeb());
        llllllllllllllllllIIIIlllIlIlIIl.add(new SmartSurround());
        llllllllllllllllllIIIIlllIlIlIIl.add(new Surround());
        llllllllllllllllllIIIIlllIlIlIIl.add(new Swarm());
        llllllllllllllllllIIIIlllIlIlIIl.add(new TotemPopNotifier());
        llllllllllllllllllIIIIlllIlIlIIl.add(new Trigger());
        llllllllllllllllllIIIIlllIlIlIIl.add(new Burrow());
    }

    public boolean isActive(Class<? extends Module> llllllllllllllllllIIIlIIlIlIIIlI) {
        Modules llllllllllllllllllIIIlIIlIlIIllI;
        Module llllllllllllllllllIIIlIIlIlIIlII = llllllllllllllllllIIIlIIlIlIIllI.get(llllllllllllllllllIIIlIIlIlIIIlI);
        return llllllllllllllllllIIIlIIlIlIIlII != null && llllllllllllllllllIIIlIIlIlIIlII.isActive();
    }

    public Modules() {
        super("modules");
        Modules llllllllllllllllllIIIlIIllIlIIIl;
        llllllllllllllllllIIIlIIllIlIIIl.modules = new ArrayList<Module>();
        llllllllllllllllllIIIlIIllIlIIIl.moduleInstances = new HashMap<Class<? extends Module>, Module>();
        llllllllllllllllllIIIlIIllIlIIIl.groups = new HashMap<Category, List<Module>>();
        llllllllllllllllllIIIlIIllIlIIIl.active = new ArrayList<Module>();
    }

    private void initMisc() {
        Modules llllllllllllllllllIIIIlllIIllIll;
        llllllllllllllllllIIIIlllIIllIll.add(new Announcer());
        llllllllllllllllllIIIIlllIIllIll.add(new AntiPacketKick());
        llllllllllllllllllIIIIlllIIllIll.add(new AutoReconnect());
        llllllllllllllllllIIIIlllIIllIll.add(new BetterChat());
        llllllllllllllllllIIIIlllIIllIll.add(new BookBot());
        llllllllllllllllllIIIIlllIIllIll.add(new DiscordPresence());
        llllllllllllllllllIIIIlllIIllIll.add(new MessageAura());
        llllllllllllllllllIIIIlllIIllIll.add(new MiddleClickFriend());
        llllllllllllllllllIIIIlllIIllIll.add(new Notebot());
        llllllllllllllllllIIIIlllIIllIll.add(new OffhandCrash());
        llllllllllllllllllIIIIlllIIllIll.add(new PacketCanceller());
        llllllllllllllllllIIIIlllIIllIll.add(new SoundBlocker());
        llllllllllllllllllIIIIlllIIllIll.add(new Spam());
        llllllllllllllllllIIIIlllIIllIll.add(new VisualRange());
        llllllllllllllllllIIIIlllIIllIll.add(new VanillaSpoof());
        llllllllllllllllllIIIIlllIIllIll.add(new AntiBot());
        llllllllllllllllllIIIIlllIIllIll.add(new BetterTab());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    private void onGameJoined(GameJoinedEvent llllllllllllllllllIIIlIIIIIIIIlI) {
        Modules llllllllllllllllllIIIlIIIIIIIIll;
        List<Module> llllllllllllllllllIIIlIIIIIIIIII = llllllllllllllllllIIIlIIIIIIIIll.active;
        synchronized (llllllllllllllllllIIIlIIIIIIIIII) {
            for (Module llllllllllllllllllIIIlIIIIIIIlII : llllllllllllllllllIIIlIIIIIIIIll.active) {
                MeteorClient.EVENT_BUS.subscribe(llllllllllllllllllIIIlIIIIIIIlII);
                llllllllllllllllllIIIlIIIIIIIlII.onActivate();
            }
        }
    }

    private void initPlayer() {
        Modules llllllllllllllllllIIIIlllIlIIllI;
        llllllllllllllllllIIIIlllIlIIllI.add(new AirPlace());
        llllllllllllllllllIIIIlllIlIIllI.add(new AntiAFK());
        llllllllllllllllllIIIIlllIlIIllI.add(new AntiHunger());
        llllllllllllllllllIIIIlllIlIIllI.add(new AutoClicker());
        llllllllllllllllllIIIIlllIlIIllI.add(new AutoDrop());
        llllllllllllllllllIIIIlllIlIIllI.add(new AutoFish());
        llllllllllllllllllIIIIlllIlIIllI.add(new AutoMend());
        llllllllllllllllllIIIIlllIlIIllI.add(new AutoReplenish());
        llllllllllllllllllIIIIlllIlIIllI.add(new AutoRespawn());
        llllllllllllllllllIIIIlllIlIIllI.add(new AutoTool());
        llllllllllllllllllIIIIlllIlIIllI.add(new ChestSwap());
        llllllllllllllllllIIIIlllIlIIllI.add(new DeathPosition());
        llllllllllllllllllIIIIlllIlIIllI.add(new EXPThrower());
        llllllllllllllllllIIIIlllIlIIllI.add(new FakePlayer());
        llllllllllllllllllIIIIlllIlIIllI.add(new FastUse());
        llllllllllllllllllIIIIlllIlIIllI.add(new GhostHand());
        llllllllllllllllllIIIIlllIlIIllI.add(new LiquidInteract());
        llllllllllllllllllIIIIlllIlIIllI.add(new MiddleClickExtra());
        llllllllllllllllllIIIIlllIlIIllI.add(new NameProtect());
        llllllllllllllllllIIIIlllIlIIllI.add(new NoBreakDelay());
        llllllllllllllllllIIIIlllIlIIllI.add(new NoInteract());
        llllllllllllllllllIIIIlllIlIIllI.add(new NoMiningTrace());
        llllllllllllllllllIIIIlllIlIIllI.add(new NoRotate());
        llllllllllllllllllIIIIlllIlIIllI.add(new Portals());
        llllllllllllllllllIIIIlllIlIIllI.add(new PotionSpoof());
        llllllllllllllllllIIIIlllIlIIllI.add(new Reach());
        llllllllllllllllllIIIIlllIlIIllI.add(new Rotation());
        llllllllllllllllllIIIIlllIlIIllI.add(new SpeedMine());
        llllllllllllllllllIIIIlllIlIIllI.add(new Trail());
        llllllllllllllllllIIIIlllIlIIllI.add(new XCarry());
        llllllllllllllllllIIIIlllIlIIllI.add(new AutoGap());
        llllllllllllllllllIIIIlllIlIIllI.add(new AutoEat());
    }

    public static Category getCategoryByHash(int llllllllllllllllllIIIlIIlIlllllI) {
        for (Category llllllllllllllllllIIIlIIlIllllll : CATEGORIES) {
            if (llllllllllllllllllIIIlIIlIllllll.hashCode() != llllllllllllllllllIIIlIIlIlllllI) continue;
            return llllllllllllllllllIIIlIIlIllllll;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void removeActive(Module llllllllllllllllllIIIlIIIlIIllIl) {
        Modules llllllllllllllllllIIIlIIIlIIlllI;
        List<Module> llllllllllllllllllIIIlIIIlIIllII = llllllllllllllllllIIIlIIIlIIlllI.active;
        synchronized (llllllllllllllllllIIIlIIIlIIllII) {
            if (llllllllllllllllllIIIlIIIlIIlllI.active.remove(llllllllllllllllllIIIlIIIlIIllIl)) {
                MeteorClient.EVENT_BUS.post(ActiveModulesChangedEvent.get());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Module> getActive() {
        Modules llllllllllllllllllIIIlIIlIIIlllI;
        List<Module> llllllllllllllllllIIIlIIlIIIllII = llllllllllllllllllIIIlIIlIIIlllI.active;
        synchronized (llllllllllllllllllIIIlIIlIIIllII) {
            return llllllllllllllllllIIIlIIlIIIlllI.active;
        }
    }

    public int getCount() {
        Modules llllllllllllllllllIIIlIIlIIlIIll;
        return llllllllllllllllllIIIlIIlIIlIIll.moduleInstances.values().size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @EventHandler
    private void onGameLeft(GameLeftEvent llllllllllllllllllIIIIllllllIlIl) {
        Modules llllllllllllllllllIIIIllllllIllI;
        List<Module> llllllllllllllllllIIIIllllllIIll = llllllllllllllllllIIIIllllllIllI.active;
        synchronized (llllllllllllllllllIIIIllllllIIll) {
            for (Module llllllllllllllllllIIIIllllllIlll : llllllllllllllllllIIIIllllllIllI.active) {
                MeteorClient.EVENT_BUS.unsubscribe(llllllllllllllllllIIIIllllllIlll);
                llllllllllllllllllIIIIllllllIlll.onDeactivate();
            }
        }
    }

    public static Iterable<Category> loopCategories() {
        return CATEGORIES;
    }

    public void add(Module llllllllllllllllllIIIIlllIllIIll) {
        Modules llllllllllllllllllIIIIlllIllIlll;
        if (!CATEGORIES.contains(llllllllllllllllllIIIIlllIllIIll.category)) {
            throw new RuntimeException("Modules.addModule - Module's category was not registered.");
        }
        AtomicReference llllllllllllllllllIIIIlllIllIlIl = new AtomicReference();
        if (llllllllllllllllllIIIIlllIllIlll.moduleInstances.values().removeIf(llllllllllllllllllIIIIlllIIIlIIl -> {
            if (llllllllllllllllllIIIIlllIIIlIIl.name.equals(llllllllllllllllllIIIIlllIIIlllI.name)) {
                llllllllllllllllllIIIIlllIllIlIl.set(llllllllllllllllllIIIIlllIIIlIIl);
                llllllllllllllllllIIIIlllIIIlIIl.settings.unregisterColorSettings();
                return true;
            }
            return false;
        })) {
            llllllllllllllllllIIIIlllIllIlll.getGroup(((Module)llllllllllllllllllIIIIlllIllIlIl.get()).category).remove(llllllllllllllllllIIIIlllIllIlIl.get());
        }
        llllllllllllllllllIIIIlllIllIlll.moduleInstances.put(llllllllllllllllllIIIIlllIllIIll.getClass(), llllllllllllllllllIIIIlllIllIIll);
        llllllllllllllllllIIIIlllIllIlll.modules.add(llllllllllllllllllIIIIlllIllIIll);
        llllllllllllllllllIIIIlllIllIlll.getGroup(llllllllllllllllllIIIIlllIllIIll.category).add(llllllllllllllllllIIIIlllIllIIll);
        llllllllllllllllllIIIIlllIllIIll.settings.registerColorSettings(llllllllllllllllllIIIIlllIllIIll);
    }

    public List<Pair<Module, Integer>> searchTitles(String llllllllllllllllllIIIlIIIllllllI) {
        Modules llllllllllllllllllIIIlIIlIIIIIlI;
        ArrayList<Pair<Module, Integer>> llllllllllllllllllIIIlIIlIIIIIII = new ArrayList<Pair<Module, Integer>>();
        for (Module llllllllllllllllllIIIlIIlIIIIIll : llllllllllllllllllIIIlIIlIIIIIlI.moduleInstances.values()) {
            int llllllllllllllllllIIIlIIlIIIIlII = Utils.search(llllllllllllllllllIIIlIIlIIIIIll.title, llllllllllllllllllIIIlIIIllllllI);
            if (llllllllllllllllllIIIlIIlIIIIlII <= 0) continue;
            llllllllllllllllllIIIlIIlIIIIIII.add((Pair<Module, Integer>)new Pair((Object)llllllllllllllllllIIIlIIlIIIIIll, (Object)llllllllllllllllllIIIlIIlIIIIlII));
        }
        llllllllllllllllllIIIlIIlIIIIIII.sort(Comparator.comparingInt(llllllllllllllllllIIIIlllIIIIIll -> -((Integer)llllllllllllllllllIIIIlllIIIIIll.getRight()).intValue()));
        return llllllllllllllllllIIIlIIlIIIIIII;
    }

    static {
        REGISTRY = new ModuleRegistry();
        CATEGORIES = new ArrayList<Category>();
        s = new ArrayList<String>(Arrays.asList("7fc7444b84b47e7c1be9f65c8ebe0fe412f939c0ae2b57e3c0daa37553cfff7500092756c99bc9c95d8c47fa8a3f611ab17227f0cd25564af2b02f3f28be4128", "a515b8f491894a07243b27c43a0e7f4673fb99d37e9b67eaaebf1c67b74885dc82b0db97a9d64004bb20c7574a487234886a2cc26e839c602b2d215ee8614bb7", "ac1c43d764dc255d4b001e78c3dfd648301a72b61983dcb5b3a8d313d863b637a9e47ebc96fc8b44e16d6341ed2732b11e95ede532b158d310091922cac5209a", "a1ab6994314bf8781370742e57da9f66e77617c0d8ab1a6e6b0ae2597416aadd7ed409e0c29af688a3220e71eff0387367a23f3fc6806f2cf960a2c5faacc286", "588f8b178ed627ba1f13ae1028263a1a27172e48978e5afe5898d7b80e6e8d444e9042201814cf532c4352fca0ba784166e901dd132ae70541e2da992e554da4"));
    }

    private void initWorld() {
        Modules llllllllllllllllllIIIIlllIIlllIl;
        llllllllllllllllllIIIIlllIIlllIl.add(new Ambience());
        llllllllllllllllllIIIIlllIIlllIl.add(new AntiCactus());
        llllllllllllllllllIIIIlllIIlllIl.add(new AutoBreed());
        llllllllllllllllllIIIIlllIIlllIl.add(new AutoBrewer());
        llllllllllllllllllIIIIlllIIlllIl.add(new AutoMount());
        llllllllllllllllllIIIIlllIIlllIl.add(new AutoMountBypassDupe());
        llllllllllllllllllIIIIlllIIlllIl.add(new AutoNametag());
        llllllllllllllllllIIIIlllIIlllIl.add(new AutoShearer());
        llllllllllllllllllIIIIlllIIlllIl.add(new AutoSign());
        llllllllllllllllllIIIIlllIIlllIl.add(new AutoSmelter());
        llllllllllllllllllIIIIlllIIlllIl.add(new AutoSteal());
        llllllllllllllllllIIIIlllIIlllIl.add(new BuildHeight());
        llllllllllllllllllIIIIlllIIlllIl.add(new EChestFarmer());
        llllllllllllllllllIIIIlllIIlllIl.add(new EndermanLook());
        llllllllllllllllllIIIIlllIIlllIl.add(new EntityLogger());
        llllllllllllllllllIIIIlllIIlllIl.add(new Flamethrower());
        llllllllllllllllllIIIIlllIIlllIl.add(new InfinityMiner());
        llllllllllllllllllIIIIlllIIlllIl.add(new Nuker());
        llllllllllllllllllIIIIlllIIlllIl.add(new LiquidFiller());
        llllllllllllllllllIIIIlllIIlllIl.add(new MountBypass());
        llllllllllllllllllIIIIlllIIlllIl.add(new PacketMine());
        llllllllllllllllllIIIIlllIIlllIl.add(new StashFinder());
        llllllllllllllllllIIIIlllIIlllIl.add(new Timer());
        llllllllllllllllllIIIIlllIIlllIl.add(new VeinMiner());
        llllllllllllllllllIIIIlllIIlllIl.add(new InstaMine());
    }

    public static class ModuleRegistry
    extends Registry<Module> {
        public Set<Map.Entry<RegistryKey<Module>, Module>> getEntries() {
            return null;
        }

        @Nullable
        public Module get(@Nullable RegistryKey<Module> lIlIIlIIllIIll) {
            return null;
        }

        @Nullable
        public Identifier getId(Module lIlIIlIIlllIIl) {
            return null;
        }

        public Lifecycle getLifecycle() {
            return null;
        }

        public Optional<RegistryKey<Module>> getKey(Module lIlIIlIIllIlll) {
            return Optional.empty();
        }

        public int getRawId(@Nullable Module lIlIIlIIllIlIl) {
            return 0;
        }

        @Nullable
        public Module get(@Nullable Identifier lIlIIlIIllIIIl) {
            return null;
        }

        public Iterator<Module> iterator() {
            return new ToggleModuleIterator();
        }

        public ModuleRegistry() {
            super(RegistryKey.ofRegistry((Identifier)new Identifier("meteor-client", "modules")), Lifecycle.stable());
            ModuleRegistry lIlIIlIIllllII;
        }

        @Nullable
        public Module get(int lIlIIlIIlIlIII) {
            return null;
        }

        public Set<Identifier> getIds() {
            return null;
        }

        protected Lifecycle getEntryLifecycle(Module lIlIIlIIlIllll) {
            return null;
        }

        public boolean containsId(Identifier lIlIIlIIlIlIlI) {
            return false;
        }

        private static class ToggleModuleIterator
        implements Iterator<Module> {
            private final /* synthetic */ Iterator<Module> iterator;

            @Override
            public Module next() {
                ToggleModuleIterator lIIlIIllllllIl;
                return lIIlIIllllllIl.iterator.next();
            }

            @Override
            public boolean hasNext() {
                ToggleModuleIterator lIIlIlIIIIIIII;
                return lIIlIlIIIIIIII.iterator.hasNext();
            }

            private ToggleModuleIterator() {
                ToggleModuleIterator lIIlIlIIIIIIll;
                lIIlIlIIIIIIll.iterator = Modules.get().getAll().iterator();
            }
        }
    }
}

