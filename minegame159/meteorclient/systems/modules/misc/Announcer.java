/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.world.World
 *  net.minecraft.block.Block
 *  net.minecraft.client.gui.screen.ingame.HandledScreen
 */
package minegame159.meteorclient.systems.modules.misc;

import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.events.entity.DropItemsEvent;
import minegame159.meteorclient.events.entity.player.BreakBlockEvent;
import minegame159.meteorclient.events.entity.player.PickItemsEvent;
import minegame159.meteorclient.events.entity.player.PlaceBlockEvent;
import minegame159.meteorclient.events.game.OpenScreenEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.DoubleSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public class Announcer
extends Module {
    private static final /* synthetic */ double TICK = 0.05;
    private final /* synthetic */ Feature[] features;

    @Override
    public void onDeactivate() {
        Announcer lIlIIllIIlIIIl;
        for (Feature lIlIIllIIlIIll : lIlIIllIIlIIIl.features) {
            if (!lIlIIllIIlIIll.isEnabled()) continue;
            MeteorClient.EVENT_BUS.unsubscribe(lIlIIllIIlIIll);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post lIlIIllIIIIlIl) {
        Announcer lIlIIllIIIIlII;
        for (Feature lIlIIllIIIIlll : lIlIIllIIIIlII.features) {
            if (!lIlIIllIIIIlll.isEnabled()) continue;
            lIlIIllIIIIlll.tick();
        }
    }

    @Override
    public void onActivate() {
        Announcer lIlIIllIIllllI;
        for (Feature lIlIIllIIlllll : lIlIIllIIllllI.features) {
            if (!lIlIIllIIlllll.isEnabled()) continue;
            MeteorClient.EVENT_BUS.subscribe(lIlIIllIIlllll);
            lIlIIllIIlllll.reset();
        }
    }

    public Announcer() {
        super(Categories.Misc, "announcer", "Announces specified actions into chat.");
        Announcer lIlIIllIlIIllI;
        lIlIIllIlIIllI.features = new Feature[]{lIlIIllIlIIllI.new Moving(), lIlIIllIlIIllI.new Mining(), lIlIIllIlIIllI.new Placing(), lIlIIllIlIIllI.new DropItems(), lIlIIllIlIIllI.new PickItems(), lIlIIllIlIIllI.new OpenContainer()};
    }

    private class PickItems
    extends Feature {
        private /* synthetic */ double notPickedUpTimer;
        private /* synthetic */ int count;
        private final /* synthetic */ Setting<String> msg;
        private /* synthetic */ Item lastItem;

        PickItems() {
            PickItems llIIllIlIlllII;
            super("Pick Items", "pick-items-enabled", "Send msg how much items you pick up.");
            llIIllIlIlllII.msg = llIIllIlIlllII.sg.add(new StringSetting.Builder().name("pick-items-msg").description("The chat message for picking up items.").defaultValue("I just picked up {count} {item}!").build());
        }

        void sendMsg() {
            PickItems llIIllIlIIlIll;
            if (llIIllIlIIlIll.count > 0) {
                ((Announcer)llIIllIlIIlIll.Announcer.this).mc.player.sendChatMessage(llIIllIlIIlIll.msg.get().replace("{count}", Integer.toString(llIIllIlIIlIll.count)).replace("{item}", llIIllIlIIlIll.lastItem.getName().getString()));
                llIIllIlIIlIll.count = 0;
            }
        }

        @Override
        void tick() {
            PickItems llIIllIlIIllll;
            if (llIIllIlIIllll.notPickedUpTimer >= 1.0) {
                llIIllIlIIllll.sendMsg();
            } else {
                llIIllIlIIllll.notPickedUpTimer += 0.05;
            }
        }

        @Override
        void reset() {
            llIIllIlIllIII.lastItem = null;
            llIIllIlIllIII.count = 0;
            llIIllIlIllIII.notPickedUpTimer = 0.0;
        }

        @EventHandler
        private void onPickItems(PickItemsEvent llIIllIlIlIIIl) {
            PickItems llIIllIlIlIlII;
            if (llIIllIlIlIlII.lastItem != null && llIIllIlIlIlII.lastItem != llIIllIlIlIIIl.itemStack.getItem()) {
                llIIllIlIlIlII.sendMsg();
            }
            llIIllIlIlIlII.lastItem = llIIllIlIlIIIl.itemStack.getItem();
            llIIllIlIlIlII.count += llIIllIlIlIIIl.itemStack.getCount();
            llIIllIlIlIlII.notPickedUpTimer = 0.0;
        }
    }

    private class Mining
    extends Feature {
        private /* synthetic */ double notBrokenTimer;
        private final /* synthetic */ Setting<String> msg;
        private /* synthetic */ int count;
        private /* synthetic */ Block lastBlock;

        @EventHandler
        private void onBreakBlock(BreakBlockEvent lllllllllllllllllllIlllIIlllIIIl) {
            Mining lllllllllllllllllllIlllIIlllIlIl;
            Block lllllllllllllllllllIlllIIlllIIll = lllllllllllllllllllIlllIIlllIIIl.getBlockState((World)((Announcer)lllllllllllllllllllIlllIIlllIlIl.Announcer.this).mc.world).getBlock();
            if (lllllllllllllllllllIlllIIlllIlIl.lastBlock != null && lllllllllllllllllllIlllIIlllIlIl.lastBlock != lllllllllllllllllllIlllIIlllIIll) {
                lllllllllllllllllllIlllIIlllIlIl.sendMsg();
            }
            lllllllllllllllllllIlllIIlllIlIl.lastBlock = lllllllllllllllllllIlllIIlllIIll;
            ++lllllllllllllllllllIlllIIlllIlIl.count;
            lllllllllllllllllllIlllIIlllIlIl.notBrokenTimer = 0.0;
        }

        Mining() {
            Mining lllllllllllllllllllIlllIIlllllIl;
            super("Mining", "mining-enabled", "Send msg how much blocks you mined.");
            lllllllllllllllllllIlllIIlllllIl.msg = lllllllllllllllllllIlllIIlllllIl.sg.add(new StringSetting.Builder().name("mining-msg").description("The chat message for mining blocks.").defaultValue("I just mined {count} {block}!").build());
        }

        @Override
        void reset() {
            lllllllllllllllllllIlllIIllllIIl.lastBlock = null;
            lllllllllllllllllllIlllIIllllIIl.count = 0;
            lllllllllllllllllllIlllIIllllIIl.notBrokenTimer = 0.0;
        }

        void sendMsg() {
            Mining lllllllllllllllllllIlllIIllIlIll;
            if (lllllllllllllllllllIlllIIllIlIll.count > 0) {
                ((Announcer)lllllllllllllllllllIlllIIllIlIll.Announcer.this).mc.player.sendChatMessage(lllllllllllllllllllIlllIIllIlIll.msg.get().replace("{count}", Integer.toString(lllllllllllllllllllIlllIIllIlIll.count)).replace("{block}", lllllllllllllllllllIlllIIllIlIll.lastBlock.getName().getString()));
                lllllllllllllllllllIlllIIllIlIll.count = 0;
            }
        }

        @Override
        void tick() {
            Mining lllllllllllllllllllIlllIIllIlllI;
            if (lllllllllllllllllllIlllIIllIlllI.notBrokenTimer >= 2.0) {
                lllllllllllllllllllIlllIIllIlllI.sendMsg();
            } else {
                lllllllllllllllllllIlllIIllIlllI.notBrokenTimer += 0.05;
            }
        }
    }

    private abstract class Feature {
        private final /* synthetic */ Setting<Boolean> enabled;
        protected /* synthetic */ SettingGroup sg;

        boolean isEnabled() {
            Feature lIlllllIlllI;
            return lIlllllIlllI.enabled.get();
        }

        abstract void tick();

        abstract void reset();

        protected Feature(String lIllllllIlll, String lIllllllIIIl, String lIllllllIlIl) {
            Feature lIlllllllIII;
            lIlllllllIII.sg = lIlllllllIII.Announcer.this.settings.createGroup(lIllllllIlll);
            lIlllllllIII.enabled = lIlllllllIII.sg.add(new BoolSetting.Builder().name(lIllllllIIIl).description(lIllllllIlIl).defaultValue(true).onChanged(lIlllllIlIlI -> {
                Feature lIlllllIlIIl;
                if (lIlllllIlIIl.Announcer.this.isActive() && lIlllllIlIIl.isEnabled()) {
                    MeteorClient.EVENT_BUS.subscribe(lIlllllIlIIl);
                    lIlllllIlIIl.reset();
                } else if (lIlllllIlIIl.Announcer.this.isActive() && !lIlllllIlIIl.isEnabled()) {
                    MeteorClient.EVENT_BUS.unsubscribe(lIlllllIlIIl);
                }
            }).build());
        }
    }

    private class Placing
    extends Feature {
        private /* synthetic */ double notPlacedTimer;
        private /* synthetic */ int count;
        private /* synthetic */ Block lastBlock;
        private final /* synthetic */ Setting<String> msg;

        @Override
        void reset() {
            lllIIllllIIIllI.lastBlock = null;
            lllIIllllIIIllI.count = 0;
            lllIIllllIIIllI.notPlacedTimer = 0.0;
        }

        @Override
        void tick() {
            Placing lllIIlllIlllllI;
            if (lllIIlllIlllllI.notPlacedTimer >= 2.0) {
                lllIIlllIlllllI.sendMsg();
            } else {
                lllIIlllIlllllI.notPlacedTimer += 0.05;
            }
        }

        @EventHandler
        private void onPlaceBlock(PlaceBlockEvent lllIIllllIIIIII) {
            Placing lllIIllllIIIIIl;
            if (lllIIllllIIIIIl.lastBlock != null && lllIIllllIIIIIl.lastBlock != lllIIllllIIIIII.block) {
                lllIIllllIIIIIl.sendMsg();
            }
            lllIIllllIIIIIl.lastBlock = lllIIllllIIIIII.block;
            ++lllIIllllIIIIIl.count;
            lllIIllllIIIIIl.notPlacedTimer = 0.0;
        }

        Placing() {
            Placing lllIIllllIIlIll;
            super("Placing", "placing-enabled", "Send msg how much blocks you placed.");
            lllIIllllIIlIll.msg = lllIIllllIIlIll.sg.add(new StringSetting.Builder().name("placing-msg").description("The chat message for placing blocks.").defaultValue("I just placed {count} {block}!").build());
        }

        void sendMsg() {
            Placing lllIIlllIlllIll;
            if (lllIIlllIlllIll.count > 0) {
                ((Announcer)lllIIlllIlllIll.Announcer.this).mc.player.sendChatMessage(lllIIlllIlllIll.msg.get().replace("{count}", Integer.toString(lllIIlllIlllIll.count)).replace("{block}", lllIIlllIlllIll.lastBlock.getName().getString()));
                lllIIlllIlllIll.count = 0;
            }
        }
    }

    private class Moving
    extends Feature {
        private /* synthetic */ double lastX;
        private /* synthetic */ double timer;
        private final /* synthetic */ Setting<String> msg;
        private /* synthetic */ double dist;
        private /* synthetic */ boolean first;
        private final /* synthetic */ Setting<Double> delay;
        private final /* synthetic */ Setting<Double> minDist;
        private /* synthetic */ double lastZ;

        @Override
        void tick() {
            Moving llllIlllIllII;
            if (llllIlllIllII.first) {
                llllIlllIllII.first = false;
                llllIlllIllII.updateLastPos();
            }
            double llllIlllIlllI = ((Announcer)llllIlllIllII.Announcer.this).mc.player.getX() - llllIlllIllII.lastX;
            double llllIlllIllIl = ((Announcer)llllIlllIllII.Announcer.this).mc.player.getZ() - llllIlllIllII.lastZ;
            llllIlllIllII.dist += Math.sqrt(llllIlllIlllI * llllIlllIlllI + llllIlllIllIl * llllIlllIllIl);
            if (llllIlllIllII.timer >= llllIlllIllII.delay.get()) {
                llllIlllIllII.timer = 0.0;
                if (llllIlllIllII.dist >= llllIlllIllII.minDist.get()) {
                    llllIlllIllII.sendMsg();
                    llllIlllIllII.dist = 0.0;
                }
            } else {
                llllIlllIllII.timer += 0.05;
            }
            llllIlllIllII.updateLastPos();
        }

        void sendMsg() {
            Moving llllIlllIIlIl;
            ((Announcer)llllIlllIIlIl.Announcer.this).mc.player.sendChatMessage(llllIlllIIlIl.msg.get().replace("{dist}", String.format("%.1f", llllIlllIIlIl.dist)));
        }

        void updateLastPos() {
            Moving llllIlllIIlll;
            llllIlllIIlll.lastX = ((Announcer)llllIlllIIlll.Announcer.this).mc.player.getX();
            llllIlllIIlll.lastZ = ((Announcer)llllIlllIIlll.Announcer.this).mc.player.getZ();
        }

        Moving() {
            Moving llllIlllllIII;
            super("Moving", "moving-enabled", "Send msg how much you moved.");
            llllIlllllIII.msg = llllIlllllIII.sg.add(new StringSetting.Builder().name("moving-msg").description("The chat message for moving a certain amount of blocks.").defaultValue("I just moved {dist} blocks!").build());
            llllIlllllIII.delay = llllIlllllIII.sg.add(new DoubleSetting.Builder().name("moving-delay").description("The amount of delay between moving messages in seconds.").defaultValue(10.0).sliderMax(60.0).build());
            llllIlllllIII.minDist = llllIlllllIII.sg.add(new DoubleSetting.Builder().name("moving-min-dist").description("The minimum distance for a moving message to send into chat.").defaultValue(10.0).sliderMax(100.0).build());
        }

        @Override
        void reset() {
            llllIllllIIll.dist = 0.0;
            llllIllllIIll.timer = 0.0;
            llllIllllIIll.first = true;
        }
    }

    private class OpenContainer
    extends Feature {
        private final /* synthetic */ Setting<String> msg;

        @EventHandler
        private void onOpenScreen(OpenScreenEvent lllllllllllllllllIlIIIllIIlllIII) {
            String lllllllllllllllllIlIIIllIIlllIlI;
            if (lllllllllllllllllIlIIIllIIlllIII.screen instanceof HandledScreen && !(lllllllllllllllllIlIIIllIIlllIlI = lllllllllllllllllIlIIIllIIlllIII.screen.getTitle().getString()).isEmpty()) {
                OpenContainer lllllllllllllllllIlIIIllIIlllIIl;
                lllllllllllllllllIlIIIllIIlllIIl.sendMsg(lllllllllllllllllIlIIIllIIlllIlI);
            }
        }

        @Override
        void reset() {
        }

        public OpenContainer() {
            OpenContainer lllllllllllllllllIlIIIllIlIIIIIl;
            super("Open Container", "open-container-enabled", "Sends msg when you oopen containers.");
            lllllllllllllllllIlIIIllIlIIIIIl.msg = lllllllllllllllllIlIIIllIlIIIIIl.sg.add(new StringSetting.Builder().name("open-container-msg").description("The chat message for opening a container.").defaultValue("I just opened {name}!").build());
        }

        void sendMsg(String lllllllllllllllllIlIIIllIIlIllll) {
            OpenContainer lllllllllllllllllIlIIIllIIllIIlI;
            ((Announcer)lllllllllllllllllIlIIIllIIllIIlI.Announcer.this).mc.player.sendChatMessage(lllllllllllllllllIlIIIllIIllIIlI.msg.get().replace("{name}", lllllllllllllllllIlIIIllIIlIllll));
        }

        @Override
        void tick() {
        }
    }

    private class DropItems
    extends Feature {
        private /* synthetic */ int count;
        private final /* synthetic */ Setting<String> msg;
        private /* synthetic */ double notDroppedTimer;
        private /* synthetic */ Item lastItem;

        @Override
        void reset() {
            llllllllllllllllllIlllllllIllIll.lastItem = null;
            llllllllllllllllllIlllllllIllIll.count = 0;
            llllllllllllllllllIlllllllIllIll.notDroppedTimer = 0.0;
        }

        DropItems() {
            DropItems llllllllllllllllllIllllllllIIIII;
            super("Drop Items", "drop-items-enabled", "Send msg how much items you dropped.");
            llllllllllllllllllIllllllllIIIII.msg = llllllllllllllllllIllllllllIIIII.sg.add(new StringSetting.Builder().name("drop-items-msg").description("The chat message for dropping items.").defaultValue("I just dropped {count} {item}!").build());
        }

        @EventHandler
        private void onDropItems(DropItemsEvent llllllllllllllllllIlllllllIlIlll) {
            DropItems llllllllllllllllllIlllllllIlIllI;
            if (llllllllllllllllllIlllllllIlIllI.lastItem != null && llllllllllllllllllIlllllllIlIllI.lastItem != llllllllllllllllllIlllllllIlIlll.itemStack.getItem()) {
                llllllllllllllllllIlllllllIlIllI.sendMsg();
            }
            llllllllllllllllllIlllllllIlIllI.lastItem = llllllllllllllllllIlllllllIlIlll.itemStack.getItem();
            llllllllllllllllllIlllllllIlIllI.count += llllllllllllllllllIlllllllIlIlll.itemStack.getCount();
            llllllllllllllllllIlllllllIlIllI.notDroppedTimer = 0.0;
        }

        void sendMsg() {
            DropItems llllllllllllllllllIlllllllIlIIII;
            if (llllllllllllllllllIlllllllIlIIII.count > 0) {
                ((Announcer)llllllllllllllllllIlllllllIlIIII.Announcer.this).mc.player.sendChatMessage(llllllllllllllllllIlllllllIlIIII.msg.get().replace("{count}", Integer.toString(llllllllllllllllllIlllllllIlIIII.count)).replace("{item}", llllllllllllllllllIlllllllIlIIII.lastItem.getName().getString()));
                llllllllllllllllllIlllllllIlIIII.count = 0;
            }
        }

        @Override
        void tick() {
            DropItems llllllllllllllllllIlllllllIlIIlI;
            if (llllllllllllllllllIlllllllIlIIlI.notDroppedTimer >= 1.0) {
                llllllllllllllllllIlllllllIlIIlI.sendMsg();
            } else {
                llllllllllllllllllIlllllllIlIIlI.notDroppedTimer += 0.05;
            }
        }
    }
}

