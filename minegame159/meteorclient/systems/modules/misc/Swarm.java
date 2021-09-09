/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nonnull
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockState
 *  net.minecraft.client.gui.screen.Screen
 */
package minegame159.meteorclient.systems.modules.misc;

import baritone.api.BaritoneAPI;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.game.GameJoinedEvent;
import minegame159.meteorclient.events.game.GameLeftEvent;
import minegame159.meteorclient.events.world.TickEvent;
import minegame159.meteorclient.gui.GuiTheme;
import minegame159.meteorclient.gui.WindowScreen;
import minegame159.meteorclient.gui.widgets.WLabel;
import minegame159.meteorclient.gui.widgets.WWidget;
import minegame159.meteorclient.gui.widgets.containers.WHorizontalList;
import minegame159.meteorclient.gui.widgets.containers.WVerticalList;
import minegame159.meteorclient.gui.widgets.pressable.WButton;
import minegame159.meteorclient.settings.IntSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.commands.Commands;
import minegame159.meteorclient.systems.commands.commands.SwarmCommand;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.systems.modules.Modules;
import minegame159.meteorclient.systems.modules.world.InfinityMiner;
import minegame159.meteorclient.utils.Utils;
import minegame159.meteorclient.utils.network.MeteorExecutor;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;

public class Swarm
extends Module {
    private final /* synthetic */ Setting<String> ipAddress;
    public /* synthetic */ SwarmClient client;
    private /* synthetic */ WLabel label;
    private final /* synthetic */ SettingGroup sgGeneral;
    private final /* synthetic */ Setting<Integer> serverPort;
    public /* synthetic */ SwarmServer server;
    public /* synthetic */ BlockState targetBlock;
    public /* synthetic */ Mode currentMode;

    @EventHandler
    private void onTick(TickEvent.Post llllIIIIlllIlll) {
        Swarm llllIIIIllllIII;
        if (llllIIIIllllIII.targetBlock != null) {
            llllIIIIllllIII.mine();
        }
    }

    public void idle() {
        llllIIIIlllIlII.currentMode = Mode.Idle;
        if (Modules.get().isActive(InfinityMiner.class)) {
            Modules.get().get(InfinityMiner.class).toggle();
        }
        if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
        }
    }

    private List<String> getSwarmGuideIntro() {
        return Arrays.asList("Welcome to Swarm!", "", "Swarm at its heart is a command tunnel which allows a controlling account, referred to as the queen account, to control other accounts by means of a background server.", "", "By default, Swarm is configured to work with multiple instances of Minecraft running on the, same computer however with some additional configuration it will work across your local network or the broader internet.", "", String.format("All swarm commands should be proceeded by \"%s\"", Commands.get().get(SwarmCommand.class).toString()));
    }

    private List<String> getSwarmGuideQueen() {
        return Arrays.asList("Setting up the Queen:", "Pick an instance of Minecraft to be your queen account. Ensure the swarm module is enabled. Then click the, button labeled 'Run Server(Q)' under the Swarm config menu.", "", String.format("You may also enter the command \"%s\".", Commands.get().get(SwarmCommand.class).toString("queen")));
    }

    public void mine() {
        Swarm llllIIIIlllIIII;
        ChatUtils.moduleInfo(llllIIIIlllIIII, "Starting mining job.", new Object[0]);
        if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
        }
        BaritoneAPI.getProvider().getPrimaryBaritone().getMineProcess().mine(new Block[]{llllIIIIlllIIII.targetBlock.getBlock()});
        llllIIIIlllIIII.targetBlock = null;
    }

    @EventHandler
    private void gameJoinedEventListener(GameJoinedEvent llllIIIIllIIIll) {
        Swarm llllIIIIllIIlII;
        llllIIIIllIIlII.closeAllServerConnections();
        llllIIIIllIIlII.toggle();
    }

    private List<String> getSwarmGuideConfig() {
        return Arrays.asList("Localhost Connections:", "If the Queen and Slave accounts are all being run on the same computer, there is no need to change anything here if the configured port is not being used for anything else.", "", "Local Connections:", "If the Queen and Slave accounts are not on the same computer, but on the same WiFi/Ethernet network, you will need to change the ip-address on each Slave client to the IPv4/6 address of the computer the Queen instance is running on. To find your IPv4 address on Windows, open CMD and enter the command ipconfig.", "", "Broad-Internet Connections:", "If you are attempting to make a connection over the broader internet a port forward will be required on the queen account. I will not cover how to perform a port forward, look it up. You will need administrator access to your router. Route all traffic through your configured port to the IPv4 address of the computer which is hosting the queen account. After you have successfully port-forwarded on the queen instance, change the ip address of the slave accounts to the public-ip address of the queen account. To find your public-ip address just google 'what is my ip'. NEVER SHARE YOUR PUBLIC IP WITH ANYONE YOU DO NOT TRUST. Assuming you setup everything correctly, you may now proceed as usual.");
    }

    private void setLabel() {
        Swarm llllIIIIllllIll;
        if (llllIIIIllllIll.currentMode != null) {
            llllIIIIllllIll.label.set(String.valueOf(new StringBuilder().append("Current Mode: ").append((Object)llllIIIIllllIll.currentMode)));
        }
    }

    @EventHandler
    private void gameLeftEventListener(GameLeftEvent llllIIIIllIIlll) {
        Swarm llllIIIIllIlIII;
        llllIIIIllIlIII.closeAllServerConnections();
        llllIIIIllIlIII.toggle();
    }

    public void runServer() {
        Swarm llllIIIlIIIIllI;
        if (llllIIIlIIIIllI.server == null) {
            llllIIIlIIIIllI.currentMode = Mode.Queen;
            llllIIIlIIIIllI.setLabel();
            llllIIIlIIIIllI.closeAllServerConnections();
            llllIIIlIIIIllI.server = llllIIIlIIIIllI.new SwarmServer();
        }
    }

    @Override
    public WWidget getWidget(GuiTheme llllIIIlIIIlllI) {
        Swarm llllIIIlIIlIlll;
        WVerticalList llllIIIlIIlIlIl = llllIIIlIIIlllI.verticalList();
        llllIIIlIIlIlll.label = llllIIIlIIlIlIl.add(llllIIIlIIIlllI.label("")).widget();
        llllIIIlIIlIlll.setLabel();
        WHorizontalList llllIIIlIIlIlII = llllIIIlIIlIlIl.add(llllIIIlIIIlllI.horizontalList()).expandX().widget();
        WButton llllIIIlIIlIIll = llllIIIlIIlIlII.add(llllIIIlIIIlllI.button("Run Server (Q)")).expandX().widget();
        llllIIIlIIlIIll.action = llllIIIlIIlIlll::runServer;
        WButton llllIIIlIIlIIlI = llllIIIlIIlIlII.add(llllIIIlIIIlllI.button("Connect (S)")).expandX().widget();
        llllIIIlIIlIIlI.action = llllIIIlIIlIlll::runClient;
        WButton llllIIIlIIlIIIl = llllIIIlIIlIlII.add(llllIIIlIIIlllI.button("Rest")).expandX().widget();
        llllIIIlIIlIIIl.action = () -> {
            Swarm llllIIIIlIlIllI;
            ChatUtils.moduleInfo(llllIIIIlIlIllI, "Closing all connections.", new Object[0]);
            llllIIIIlIlIllI.closeAllServerConnections();
            llllIIIIlIlIllI.currentMode = Mode.Idle;
            llllIIIIlIlIllI.setLabel();
        };
        WButton llllIIIlIIlIIII = llllIIIlIIlIlIl.add(llllIIIlIIIlllI.button("Guide")).expandX().widget();
        llllIIIlIIlIIII.action = () -> {
            Swarm llllIIIIlIllIll;
            llllIIIIlIllIll.mc.openScreen((Screen)llllIIIIlIllIll.new SwarmHelpScreen(llllIIIlIIIlllI));
        };
        return llllIIIlIIlIlIl;
    }

    public void runClient() {
        Swarm llllIIIlIIIIIlI;
        if (llllIIIlIIIIIlI.client == null) {
            llllIIIlIIIIIlI.currentMode = Mode.Slave;
            llllIIIlIIIIIlI.setLabel();
            llllIIIlIIIIIlI.closeAllServerConnections();
            llllIIIlIIIIIlI.client = llllIIIlIIIIIlI.new SwarmClient();
        }
    }

    private List<String> getSwarmGuideSlave() {
        return Arrays.asList("Connecting your slaves:", "For each slave account, assuming you correctly configured the ip and port in Step 1 simply press the button labeled 'Connect (S)'.", "", String.format("You may also enter the command \"%s\".", Commands.get().get(SwarmCommand.class).toString("slave")));
    }

    @Override
    public void onDeactivate() {
        Swarm llllIIIlIlIIIIl;
        llllIIIlIlIIIIl.currentMode = Mode.Idle;
        llllIIIlIlIIIIl.closeAllServerConnections();
    }

    @Override
    public void onActivate() {
        Swarm llllIIIlIlIIIll;
        llllIIIlIlIIIll.currentMode = Mode.Idle;
        llllIIIlIlIIIll.closeAllServerConnections();
    }

    public void execute(@Nonnull String llllIIIIllIlIll) {
        try {
            Commands.get().dispatch(llllIIIIllIlIll);
        }
        catch (CommandSyntaxException llllIIIIllIlIlI) {
            // empty catch block
        }
    }

    public Swarm() {
        super(Categories.Misc, "Swarm", "I Am... The Swarm.");
        Swarm llllIIIlIlIIlll;
        llllIIIlIlIIlll.sgGeneral = llllIIIlIlIIlll.settings.getDefaultGroup();
        llllIIIlIlIIlll.ipAddress = llllIIIlIlIIlll.sgGeneral.add(new StringSetting.Builder().name("iP-address").description("The IP address of the Queen.").defaultValue("localhost").build());
        llllIIIlIlIIlll.serverPort = llllIIIlIlIIlll.sgGeneral.add(new IntSetting.Builder().name("port").description("The port used for connections.").defaultValue(7777).sliderMin(1).sliderMax(65535).build());
        llllIIIlIlIIlll.currentMode = Mode.Idle;
    }

    public void closeAllServerConnections() {
        try {
            Swarm llllIIIIllllllI;
            if (llllIIIIllllllI.server != null) {
                llllIIIIllllllI.server.interrupt();
                llllIIIIllllllI.server.close();
                llllIIIIllllllI.server.serverSocket.close();
                llllIIIIllllllI.server = null;
            }
            if (llllIIIIllllllI.client != null) {
                llllIIIIllllllI.client.interrupt();
                llllIIIIllllllI.client.disconnect();
                llllIIIIllllllI.client.socket.close();
                llllIIIIllllllI.client = null;
            }
        }
        catch (Exception llllIIIIlllllIl) {
            // empty catch block
        }
    }

    private class SwarmHelpScreen
    extends WindowScreen {
        private final /* synthetic */ WVerticalList list;

        private void fillTextList(List<String> llIIlIIlIIIlIll) {
            SwarmHelpScreen llIIlIIlIIIlllI;
            llIIlIIlIIIlllI.list.clear();
            for (String llIIlIIlIIIllll : llIIlIIlIIIlIll) {
                if (llIIlIIlIIIllll.isEmpty()) {
                    llIIlIIlIIIlllI.list.add(llIIlIIlIIIlllI.theme.label(""));
                    continue;
                }
                llIIlIIlIIIlllI.list.add(llIIlIIlIIIlllI.theme.label(llIIlIIlIIIllll, (double)Utils.getWindowWidth() / 2.0));
            }
        }

        public SwarmHelpScreen(GuiTheme llIIlIIlIIllIIl) {
            SwarmHelpScreen llIIlIIlIlIIIlI;
            super(llIIlIIlIIllIIl, "Swarm Help");
            WHorizontalList llIIlIIlIlIIIII = llIIlIIlIlIIIlI.add(llIIlIIlIIllIIl.horizontalList()).expandX().widget();
            WButton llIIlIIlIIlllll = llIIlIIlIlIIIII.add(llIIlIIlIIllIIl.button("(1) Introduction")).expandX().widget();
            llIIlIIlIIlllll.action = () -> {
                SwarmHelpScreen llIIlIIIllllllI;
                llIIlIIIllllllI.fillTextList(llIIlIIIllllllI.Swarm.this.getSwarmGuideIntro());
            };
            WButton llIIlIIlIIllllI = llIIlIIlIlIIIII.add(llIIlIIlIIllIIl.button("(2) Configuration")).expandX().widget();
            llIIlIIlIIllllI.action = () -> {
                SwarmHelpScreen llIIlIIlIIIIIIl;
                llIIlIIlIIIIIIl.fillTextList(llIIlIIlIIIIIIl.Swarm.this.getSwarmGuideConfig());
            };
            WButton llIIlIIlIIlllIl = llIIlIIlIlIIIII.add(llIIlIIlIIllIIl.button("(3) Queen")).expandX().widget();
            llIIlIIlIIlllIl.action = () -> {
                SwarmHelpScreen llIIlIIlIIIIIll;
                llIIlIIlIIIIIll.fillTextList(llIIlIIlIIIIIll.Swarm.this.getSwarmGuideQueen());
            };
            WButton llIIlIIlIIlllII = llIIlIIlIlIIIII.add(llIIlIIlIIllIIl.button("(4) Slave")).expandX().widget();
            llIIlIIlIIlllII.action = () -> {
                SwarmHelpScreen llIIlIIlIIIIlll;
                llIIlIIlIIIIlll.fillTextList(llIIlIIlIIIIlll.Swarm.this.getSwarmGuideSlave());
            };
            llIIlIIlIlIIIlI.list = llIIlIIlIlIIIlI.add(llIIlIIlIIllIIl.verticalList()).expandX().widget();
            llIIlIIlIlIIIlI.fillTextList(llIIlIIlIlIIIlI.Swarm.this.getSwarmGuideIntro());
        }
    }

    public class SwarmServer
    extends Thread {
        private final /* synthetic */ SubServer[] clientConnections;
        public static final /* synthetic */ int MAX_CLIENTS;
        private /* synthetic */ ServerSocket serverSocket;

        public synchronized void sendMessage(@Nonnull String lllllllllllllllllIIlIIIIIlIIllIl) {
            SwarmServer lllllllllllllllllIIlIIIIIlIIlllI;
            MeteorExecutor.execute(() -> {
                try {
                    SwarmServer lllllllllllllllllIIlIIIIIlIIIlIl;
                    for (SubServer lllllllllllllllllIIlIIIIIlIIIllI : lllllllllllllllllIIlIIIIIlIIIlIl.clientConnections) {
                        if (lllllllllllllllllIIlIIIIIlIIIllI == null) continue;
                        lllllllllllllllllIIlIIIIIlIIIllI.messageToSend = lllllllllllllllllIIlIIIIIlIIllIl;
                    }
                }
                catch (Exception lllllllllllllllllIIlIIIIIlIIIIIl) {
                    // empty catch block
                }
            });
        }

        @Override
        public void run() {
            try {
                SwarmServer lllllllllllllllllIIlIIIIIllllIII;
                ChatUtils.moduleInfo(Modules.get().get(Swarm.class), "Listening for incoming connections.", new Object[0]);
                while (!lllllllllllllllllIIlIIIIIllllIII.isInterrupted()) {
                    Socket lllllllllllllllllIIlIIIIIllllIIl = lllllllllllllllllIIlIIIIIllllIII.serverSocket.accept();
                    lllllllllllllllllIIlIIIIIllllIII.assignConnectionToSubServer(lllllllllllllllllIIlIIIIIllllIIl);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        public SwarmServer() {
            SwarmServer lllllllllllllllllIIlIIIIIllllllI;
            lllllllllllllllllIIlIIIIIllllllI.clientConnections = new SubServer[50];
            try {
                int lllllllllllllllllIIlIIIIlIIIIIIl = (Integer)lllllllllllllllllIIlIIIIIllllllI.Swarm.this.serverPort.get();
                lllllllllllllllllIIlIIIIIllllllI.serverSocket = new ServerSocket(lllllllllllllllllIIlIIIIlIIIIIIl);
                ChatUtils.moduleInfo(Modules.get().get(Swarm.class), String.valueOf(new StringBuilder().append("New Server Opened On Port ").append(lllllllllllllllllIIlIIIIIllllllI.Swarm.this.serverPort.get())), new Object[0]);
                lllllllllllllllllIIlIIIIIllllllI.start();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        public void assignConnectionToSubServer(Socket lllllllllllllllllIIlIIIIIllIlllI) {
            SwarmServer lllllllllllllllllIIlIIIIIlllIIIl;
            for (int lllllllllllllllllIIlIIIIIlllIIlI = 0; lllllllllllllllllIIlIIIIIlllIIlI < lllllllllllllllllIIlIIIIIlllIIIl.clientConnections.length; ++lllllllllllllllllIIlIIIIIlllIIlI) {
                if (lllllllllllllllllIIlIIIIIlllIIIl.clientConnections[lllllllllllllllllIIlIIIIIlllIIlI] != null) continue;
                lllllllllllllllllIIlIIIIIlllIIIl.clientConnections[lllllllllllllllllIIlIIIIIlllIIlI] = new SubServer(lllllllllllllllllIIlIIIIIllIlllI);
                ChatUtils.moduleInfo(Modules.get().get(Swarm.class), "New slave connected.", new Object[0]);
                break;
            }
        }

        public void closeAllClients() {
            SwarmServer lllllllllllllllllIIlIIIIIlIlIlll;
            try {
                for (SubServer lllllllllllllllllIIlIIIIIlIllIlI : lllllllllllllllllIIlIIIIIlIlIlll.clientConnections) {
                    if (lllllllllllllllllIIlIIIIIlIllIlI.connection == null) continue;
                    lllllllllllllllllIIlIIIIIlIllIlI.close();
                }
            }
            catch (Exception lllllllllllllllllIIlIIIIIlIllIIl) {
                lllllllllllllllllIIlIIIIIlIlIlll.Swarm.this.closeAllServerConnections();
            }
        }

        public void close() {
            try {
                SwarmServer lllllllllllllllllIIlIIIIIllIIlIl;
                lllllllllllllllllIIlIIIIIllIIlIl.interrupt();
                for (SubServer lllllllllllllllllIIlIIIIIllIIlll : lllllllllllllllllIIlIIIIIllIIlIl.clientConnections) {
                    if (lllllllllllllllllIIlIIIIIllIIlll == null) continue;
                    lllllllllllllllllIIlIIIIIllIIlll.close();
                }
                lllllllllllllllllIIlIIIIIllIIlIl.serverSocket.close();
            }
            catch (Exception lllllllllllllllllIIlIIIIIllIIllI) {
                ChatUtils.moduleInfo(Modules.get().get(Swarm.class), "Server closed.", new Object[0]);
            }
        }

        static {
            MAX_CLIENTS = 50;
        }
    }

    public static class SubServer
    extends Thread {
        private volatile /* synthetic */ String messageToSend;
        private final /* synthetic */ Socket connection;

        public void close() {
            try {
                SubServer lllllIllllIlII;
                lllllIllllIlII.interrupt();
                lllllIllllIlII.connection.close();
            }
            catch (Exception lllllIllllIIll) {
                // empty catch block
            }
        }

        @Override
        public void run() {
            try {
                SubServer lllllIlllllIll;
                OutputStream lllllIllllllll = lllllIlllllIll.connection.getOutputStream();
                DataOutputStream lllllIlllllllI = new DataOutputStream(lllllIllllllll);
                while (!lllllIlllllIll.isInterrupted()) {
                    if (lllllIlllllIll.messageToSend == null) continue;
                    lllllIlllllllI.writeUTF(lllllIlllllIll.messageToSend);
                    lllllIlllllllI.flush();
                    lllllIlllllIll.messageToSend = null;
                }
                lllllIllllllll.close();
                lllllIlllllllI.close();
            }
            catch (Exception lllllIllllllIl) {
                ChatUtils.moduleError(Modules.get().get(Swarm.class), "Error in subsystem.", new Object[0]);
            }
        }

        public SubServer(@Nonnull Socket llllllIIIIIllI) {
            SubServer llllllIIIIIlIl;
            llllllIIIIIlIl.connection = llllllIIIIIllI;
            llllllIIIIIlIl.start();
        }
    }

    public class SwarmClient
    extends Thread {
        public /* synthetic */ String ipAddress;
        public /* synthetic */ Socket socket;

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            SwarmClient llllllllllllllllllIlIIllllllllII;
            try {
                while (llllllllllllllllllIlIIllllllllII.socket == null && !llllllllllllllllllIlIIllllllllII.isInterrupted()) {
                    try {
                        llllllllllllllllllIlIIllllllllII.socket = new Socket(llllllllllllllllllIlIIllllllllII.ipAddress, (int)((Integer)llllllllllllllllllIlIIllllllllII.Swarm.this.serverPort.get()));
                    }
                    catch (Exception llllllllllllllllllIlIlIIIIIIIlII) {
                        ChatUtils.moduleWarning(Modules.get().get(Swarm.class), "Server not found. Attempting to reconnect in 5 seconds.", new Object[0]);
                    }
                    if (llllllllllllllllllIlIIllllllllII.socket != null) continue;
                    Thread.sleep(5000L);
                }
                if (llllllllllllllllllIlIIllllllllII.socket != null) {
                    InputStream llllllllllllllllllIlIlIIIIIIIIlI = llllllllllllllllllIlIIllllllllII.socket.getInputStream();
                    DataInputStream llllllllllllllllllIlIlIIIIIIIIIl = new DataInputStream(llllllllllllllllllIlIlIIIIIIIIlI);
                    ChatUtils.moduleInfo(Modules.get().get(Swarm.class), "New Socket", new Object[0]);
                    while (!llllllllllllllllllIlIIllllllllII.isInterrupted()) {
                        String llllllllllllllllllIlIlIIIIIIIIll;
                        if (llllllllllllllllllIlIIllllllllII.socket == null || (llllllllllllllllllIlIlIIIIIIIIll = llllllllllllllllllIlIlIIIIIIIIIl.readUTF()).equals("")) continue;
                        ChatUtils.moduleInfo(Modules.get().get(Swarm.class), String.valueOf(new StringBuilder().append("New Command: ").append(llllllllllllllllllIlIlIIIIIIIIll)), new Object[0]);
                        llllllllllllllllllIlIIllllllllII.Swarm.this.execute(llllllllllllllllllIlIlIIIIIIIIll);
                    }
                    llllllllllllllllllIlIlIIIIIIIIIl.close();
                    llllllllllllllllllIlIlIIIIIIIIlI.close();
                }
            }
            catch (Exception llllllllllllllllllIlIIllllllllll) {
                ChatUtils.moduleError(Modules.get().get(Swarm.class), "There is an error in your connection to the server.", new Object[0]);
                llllllllllllllllllIlIIllllllllII.disconnect();
                llllllllllllllllllIlIIllllllllII.Swarm.this.client = null;
            }
            finally {
                if (llllllllllllllllllIlIIllllllllII.socket != null) {
                    try {
                        llllllllllllllllllIlIIllllllllII.socket.close();
                    }
                    catch (Exception llllllllllllllllllIlIIlllllllllI) {
                        ChatUtils.moduleError(Modules.get().get(Swarm.class), "There is an error in your connection to the server.", new Object[0]);
                    }
                }
            }
        }

        SwarmClient() {
            SwarmClient llllllllllllllllllIlIlIIIIIIllII;
            llllllllllllllllllIlIlIIIIIIllII.ipAddress = (String)llllllllllllllllllIlIlIIIIIIllII.Swarm.this.ipAddress.get();
            llllllllllllllllllIlIlIIIIIIllII.start();
        }

        public void disconnect() {
            SwarmClient llllllllllllllllllIlIIllllllIIlI;
            if (llllllllllllllllllIlIIllllllIIlI.socket != null) {
                try {
                    llllllllllllllllllIlIIllllllIIlI.socket.close();
                }
                catch (IOException llllllllllllllllllIlIIllllllIIIl) {
                    // empty catch block
                }
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* synthetic */ /* enum */ Mode Slave;
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* synthetic */ /* enum */ Mode Idle;
        public static final /* synthetic */ /* enum */ Mode Queen;

        private Mode() {
            Mode lllllllllllllllllIIIIlIlIIIIIIll;
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        static {
            Queen = new Mode();
            Slave = new Mode();
            Idle = new Mode();
            $VALUES = Mode.$values();
        }

        private static /* synthetic */ Mode[] $values() {
            return new Mode[]{Queen, Slave, Idle};
        }

        public static Mode valueOf(String lllllllllllllllllIIIIlIlIIIIIlll) {
            return Enum.valueOf(Mode.class, lllllllllllllllllIIIIlIlIIIIIlll);
        }
    }
}

