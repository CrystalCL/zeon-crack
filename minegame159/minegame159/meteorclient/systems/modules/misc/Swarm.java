/*
 * Decompiled with CFR 0.151.
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
    public SwarmClient client;
    public SwarmServer server;
    private final SettingGroup sgGeneral;
    public BlockState targetBlock;
    private final Setting<String> ipAddress;
    private WLabel label;
    private final Setting<Integer> serverPort;
    public Mode currentMode;

    public void execute(@Nonnull String string) {
        try {
            Commands.get().dispatch(string);
        }
        catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
    }

    public void idle() {
        this.currentMode = Mode.Idle;
        if (Modules.get().isActive(InfinityMiner.class)) {
            Modules.get().get(InfinityMiner.class).toggle();
        }
        if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
        }
    }

    static List access$800(Swarm swarm) {
        return swarm.getSwarmGuideConfig();
    }

    public Swarm() {
        super(Categories.Misc, "Swarm", "I Am... The Swarm.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.ipAddress = this.sgGeneral.add(new StringSetting.Builder().name("iP-address").description("The IP address of the Queen.").defaultValue("localhost").build());
        this.serverPort = this.sgGeneral.add(new IntSetting.Builder().name("port").description("The port used for connections.").defaultValue(7777).sliderMin(1).sliderMax(65535).build());
        this.currentMode = Mode.Idle;
    }

    static List access$700(Swarm swarm) {
        return swarm.getSwarmGuideQueen();
    }

    @EventHandler
    private void gameJoinedEventListener(GameJoinedEvent gameJoinedEvent) {
        this.closeAllServerConnections();
        this.toggle();
    }

    private List<String> getSwarmGuideIntro() {
        return Arrays.asList("Welcome to Swarm!", "", "Swarm at its heart is a command tunnel which allows a controlling account, referred to as the queen account, to control other accounts by means of a background server.", "", "By default, Swarm is configured to work with multiple instances of Minecraft running on the, same computer however with some additional configuration it will work across your local network or the broader internet.", "", String.format("All swarm commands should be proceeded by \"%s\"", Commands.get().get(SwarmCommand.class).toString()));
    }

    private List<String> getSwarmGuideQueen() {
        return Arrays.asList("Setting up the Queen:", "Pick an instance of Minecraft to be your queen account. Ensure the swarm module is enabled. Then click the, button labeled 'Run Server(Q)' under the Swarm config menu.", "", String.format("You may also enter the command \"%s\".", Commands.get().get(SwarmCommand.class).toString("queen")));
    }

    private List<String> getSwarmGuideSlave() {
        return Arrays.asList("Connecting your slaves:", "For each slave account, assuming you correctly configured the ip and port in Step 1 simply press the button labeled 'Connect (S)'.", "", String.format("You may also enter the command \"%s\".", Commands.get().get(SwarmCommand.class).toString("slave")));
    }

    public void mine() {
        ChatUtils.moduleInfo(this, "Starting mining job.", new Object[0]);
        if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
        }
        BaritoneAPI.getProvider().getPrimaryBaritone().getMineProcess().mine(new Block[]{this.targetBlock.getBlock()});
        this.targetBlock = null;
    }

    private void lambda$getWidget$1(GuiTheme guiTheme) {
        this.mc.openScreen((Screen)new SwarmHelpScreen(this, guiTheme));
    }

    public void runClient() {
        if (this.client == null) {
            this.currentMode = Mode.Slave;
            this.setLabel();
            this.closeAllServerConnections();
            this.client = new SwarmClient(this);
        }
    }

    public void runServer() {
        if (this.server == null) {
            this.currentMode = Mode.Queen;
            this.setLabel();
            this.closeAllServerConnections();
            this.server = new SwarmServer(this);
        }
    }

    public void closeAllServerConnections() {
        try {
            if (this.server != null) {
                this.server.interrupt();
                this.server.close();
                SwarmServer.access$000(this.server).close();
                this.server = null;
            }
            if (this.client != null) {
                this.client.interrupt();
                this.client.disconnect();
                this.client.socket.close();
                this.client = null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void lambda$getWidget$0() {
        ChatUtils.moduleInfo(this, "Closing all connections.", new Object[0]);
        this.closeAllServerConnections();
        this.currentMode = Mode.Idle;
        this.setLabel();
    }

    private List<String> getSwarmGuideConfig() {
        return Arrays.asList("Localhost Connections:", "If the Queen and Slave accounts are all being run on the same computer, there is no need to change anything here if the configured port is not being used for anything else.", "", "Local Connections:", "If the Queen and Slave accounts are not on the same computer, but on the same WiFi/Ethernet network, you will need to change the ip-address on each Slave client to the IPv4/6 address of the computer the Queen instance is running on. To find your IPv4 address on Windows, open CMD and enter the command ipconfig.", "", "Broad-Internet Connections:", "If you are attempting to make a connection over the broader internet a port forward will be required on the queen account. I will not cover how to perform a port forward, look it up. You will need administrator access to your router. Route all traffic through your configured port to the IPv4 address of the computer which is hosting the queen account. After you have successfully port-forwarded on the queen instance, change the ip address of the slave accounts to the public-ip address of the queen account. To find your public-ip address just google 'what is my ip'. NEVER SHARE YOUR PUBLIC IP WITH ANYONE YOU DO NOT TRUST. Assuming you setup everything correctly, you may now proceed as usual.");
    }

    private void setLabel() {
        if (this.currentMode != null) {
            this.label.set(String.valueOf(new StringBuilder().append("Current Mode: ").append((Object)this.currentMode)));
        }
    }

    static Setting access$200(Swarm swarm) {
        return swarm.serverPort;
    }

    @EventHandler
    private void onTick(TickEvent.Post post) {
        if (this.targetBlock != null) {
            this.mine();
        }
    }

    @Override
    public WWidget getWidget(GuiTheme guiTheme) {
        WVerticalList wVerticalList = guiTheme.verticalList();
        this.label = wVerticalList.add(guiTheme.label("")).widget();
        this.setLabel();
        WHorizontalList wHorizontalList = wVerticalList.add(guiTheme.horizontalList()).expandX().widget();
        WButton wButton = wHorizontalList.add(guiTheme.button("Run Server (Q)")).expandX().widget();
        wButton.action = this::runServer;
        WButton wButton2 = wHorizontalList.add(guiTheme.button("Connect (S)")).expandX().widget();
        wButton2.action = this::runClient;
        WButton wButton3 = wHorizontalList.add(guiTheme.button("Rest")).expandX().widget();
        wButton3.action = this::lambda$getWidget$0;
        WButton wButton4 = wVerticalList.add(guiTheme.button("Guide")).expandX().widget();
        wButton4.action = () -> this.lambda$getWidget$1(guiTheme);
        return wVerticalList;
    }

    static List access$500(Swarm swarm) {
        return swarm.getSwarmGuideIntro();
    }

    @EventHandler
    private void gameLeftEventListener(GameLeftEvent gameLeftEvent) {
        this.closeAllServerConnections();
        this.toggle();
    }

    static Setting access$100(Swarm swarm) {
        return swarm.ipAddress;
    }

    @Override
    public void onDeactivate() {
        this.currentMode = Mode.Idle;
        this.closeAllServerConnections();
    }

    @Override
    public void onActivate() {
        this.currentMode = Mode.Idle;
        this.closeAllServerConnections();
    }

    static List access$600(Swarm swarm) {
        return swarm.getSwarmGuideSlave();
    }

    private class SwarmHelpScreen
    extends WindowScreen {
        final Swarm this$0;
        private final WVerticalList list;

        private void lambda$new$0() {
            this.fillTextList(Swarm.access$500(this.this$0));
        }

        public SwarmHelpScreen(Swarm swarm, GuiTheme guiTheme) {
            this.this$0 = swarm;
            super(guiTheme, "Swarm Help");
            WHorizontalList wHorizontalList = this.add(guiTheme.horizontalList()).expandX().widget();
            WButton wButton = wHorizontalList.add(guiTheme.button("(1) Introduction")).expandX().widget();
            wButton.action = this::lambda$new$0;
            WButton wButton2 = wHorizontalList.add(guiTheme.button("(2) Configuration")).expandX().widget();
            wButton2.action = this::lambda$new$1;
            WButton wButton3 = wHorizontalList.add(guiTheme.button("(3) Queen")).expandX().widget();
            wButton3.action = this::lambda$new$2;
            WButton wButton4 = wHorizontalList.add(guiTheme.button("(4) Slave")).expandX().widget();
            wButton4.action = this::lambda$new$3;
            this.list = this.add(guiTheme.verticalList()).expandX().widget();
            this.fillTextList(Swarm.access$500(swarm));
        }

        private void fillTextList(List<String> list) {
            this.list.clear();
            for (String string : list) {
                if (string.isEmpty()) {
                    this.list.add(this.theme.label(""));
                    continue;
                }
                this.list.add(this.theme.label(string, (double)Utils.getWindowWidth() / 2.0));
            }
        }

        private void lambda$new$1() {
            this.fillTextList(Swarm.access$800(this.this$0));
        }

        private void lambda$new$2() {
            this.fillTextList(Swarm.access$700(this.this$0));
        }

        private void lambda$new$3() {
            this.fillTextList(Swarm.access$600(this.this$0));
        }
    }

    public class SwarmServer
    extends Thread {
        private final SubServer[] clientConnections;
        public static final int MAX_CLIENTS = 50;
        private ServerSocket serverSocket;
        final Swarm this$0;

        public synchronized void sendMessage(@Nonnull String string) {
            MeteorExecutor.execute(() -> this.lambda$sendMessage$0(string));
        }

        static ServerSocket access$000(SwarmServer swarmServer) {
            return swarmServer.serverSocket;
        }

        private void lambda$sendMessage$0(String string) {
            try {
                for (SubServer subServer : this.clientConnections) {
                    if (subServer == null) continue;
                    SubServer.access$402(subServer, string);
                    if (2 != 0) continue;
                    return;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        public void close() {
            try {
                this.interrupt();
                for (SubServer subServer : this.clientConnections) {
                    if (subServer == null) continue;
                    subServer.close();
                    if (!false) continue;
                    return;
                }
                this.serverSocket.close();
            }
            catch (Exception exception) {
                ChatUtils.moduleInfo(Modules.get().get(Swarm.class), "Server closed.", new Object[0]);
            }
        }

        public SwarmServer(Swarm swarm) {
            this.this$0 = swarm;
            this.clientConnections = new SubServer[50];
            try {
                int n = (Integer)Swarm.access$200(swarm).get();
                this.serverSocket = new ServerSocket(n);
                ChatUtils.moduleInfo(Modules.get().get(Swarm.class), String.valueOf(new StringBuilder().append("New Server Opened On Port ").append(Swarm.access$200(swarm).get())), new Object[0]);
                this.start();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        public void closeAllClients() {
            try {
                for (SubServer subServer : this.clientConnections) {
                    if (SubServer.access$300(subServer) == null) continue;
                    subServer.close();
                    if (1 <= 3) continue;
                    return;
                }
            }
            catch (Exception exception) {
                this.this$0.closeAllServerConnections();
            }
        }

        @Override
        public void run() {
            try {
                ChatUtils.moduleInfo(Modules.get().get(Swarm.class), "Listening for incoming connections.", new Object[0]);
                while (!this.isInterrupted()) {
                    Socket socket = this.serverSocket.accept();
                    this.assignConnectionToSubServer(socket);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        public void assignConnectionToSubServer(Socket socket) {
            for (int i = 0; i < this.clientConnections.length; ++i) {
                if (this.clientConnections[i] != null) continue;
                this.clientConnections[i] = new SubServer(socket);
                ChatUtils.moduleInfo(Modules.get().get(Swarm.class), "New slave connected.", new Object[0]);
                break;
            }
        }
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final Mode[] $VALUES;
        public static final /* enum */ Mode Slave;
        public static final /* enum */ Mode Queen;
        public static final /* enum */ Mode Idle;

        public static Mode valueOf(String string) {
            return Enum.valueOf(Mode.class, string);
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

        private static Mode[] $values() {
            return new Mode[]{Queen, Slave, Idle};
        }
    }

    public class SwarmClient
    extends Thread {
        final Swarm this$0;
        public Socket socket;
        public String ipAddress;

        SwarmClient(Swarm swarm) {
            this.this$0 = swarm;
            this.ipAddress = (String)Swarm.access$100(swarm).get();
            this.start();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            try {
                while (this.socket == null && !this.isInterrupted()) {
                    try {
                        this.socket = new Socket(this.ipAddress, (int)((Integer)Swarm.access$200(this.this$0).get()));
                    }
                    catch (Exception exception) {
                        ChatUtils.moduleWarning(Modules.get().get(Swarm.class), "Server not found. Attempting to reconnect in 5 seconds.", new Object[0]);
                    }
                    if (this.socket != null) continue;
                    Thread.sleep(5000L);
                }
                if (this.socket == null) return;
                InputStream inputStream = this.socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                ChatUtils.moduleInfo(Modules.get().get(Swarm.class), "New Socket", new Object[0]);
                while (!this.isInterrupted()) {
                    String string;
                    if (this.socket == null || (string = dataInputStream.readUTF()).equals("")) continue;
                    ChatUtils.moduleInfo(Modules.get().get(Swarm.class), String.valueOf(new StringBuilder().append("New Command: ").append(string)), new Object[0]);
                    this.this$0.execute(string);
                }
                dataInputStream.close();
                inputStream.close();
                return;
            }
            catch (Exception exception) {
                ChatUtils.moduleError(Modules.get().get(Swarm.class), "There is an error in your connection to the server.", new Object[0]);
                this.disconnect();
                this.this$0.client = null;
                if (this.socket == null) return;
                try {
                    this.socket.close();
                    return;
                }
                catch (Exception exception2) {
                    ChatUtils.moduleError(Modules.get().get(Swarm.class), "There is an error in your connection to the server.", new Object[0]);
                    return;
                }
            }
            finally {
                if (this.socket != null) {
                    try {
                        this.socket.close();
                    }
                    catch (Exception exception) {
                        ChatUtils.moduleError(Modules.get().get(Swarm.class), "There is an error in your connection to the server.", new Object[0]);
                    }
                }
            }
        }

        public void disconnect() {
            if (this.socket != null) {
                try {
                    this.socket.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
    }

    public static class SubServer
    extends Thread {
        private String messageToSend;
        private final Socket connection;

        @Override
        public void run() {
            try {
                OutputStream outputStream = this.connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                while (!this.isInterrupted()) {
                    if (this.messageToSend == null) continue;
                    dataOutputStream.writeUTF(this.messageToSend);
                    dataOutputStream.flush();
                    this.messageToSend = null;
                }
                outputStream.close();
                dataOutputStream.close();
            }
            catch (Exception exception) {
                ChatUtils.moduleError(Modules.get().get(Swarm.class), "Error in subsystem.", new Object[0]);
            }
        }

        static String access$402(SubServer subServer, String string) {
            subServer.messageToSend = string;
            return string;
        }

        public SubServer(@Nonnull Socket socket) {
            this.connection = socket;
            this.start();
        }

        static Socket access$300(SubServer subServer) {
            return subServer.connection;
        }

        public void close() {
            try {
                this.interrupt();
                this.connection.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

