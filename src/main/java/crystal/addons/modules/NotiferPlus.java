package crystal.addons.modules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import crystal.addons.CrystalCL;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.AnvilBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NotiferPlus extends Module {
    private final SettingGroup sgSBreak = settings.createGroup("SurroundBreak");

    private final SettingGroup sgABreak = settings.createGroup("ArmorBreak");

    private final SettingGroup sgCoord = settings.createGroup("Coord");
    private final SettingGroup sgExtra = settings.createGroup("Extra");
    private final Setting<Boolean> surroundbreak = sgSBreak.add(new BoolSetting.Builder().name("surround-break").defaultValue(true).build());
    private final Setting<Boolean> head = sgSBreak.add(new BoolSetting.Builder().name("head-break").defaultValue(true).build());
    private final Setting<Boolean> face = sgSBreak.add(new BoolSetting.Builder().name("face-break").defaultValue(true).build());
    private final Setting<Boolean> legs = sgSBreak.add(new BoolSetting.Builder().name("legs-break").defaultValue(true).build());
    private final Setting<Boolean> breakg = sgABreak.add(new BoolSetting.Builder().name("armor-break").defaultValue(true).build());
    private final Setting<Integer> seconds = sgABreak.add(new IntSetting.Builder().name("show-seconds").defaultValue(10).min(1).sliderMin(1).sliderMax(500).build());
    private final Setting<String> separator = sgABreak.add(new StringSetting.Builder().name("separator").defaultValue("&7 | ").build());
    private final Setting<Boolean> helmet = sgABreak.add(new BoolSetting.Builder().name("helmet").defaultValue(true).build());
    private final Setting<Boolean> chest = sgABreak.add(new BoolSetting.Builder().name("chest").defaultValue(true).build());
    private final Setting<Boolean> leegs = sgABreak.add(new BoolSetting.Builder().name("legs").defaultValue(true).build());
    private final Setting<Boolean> feet = sgABreak.add(new BoolSetting.Builder().name("feet").defaultValue(true).build());
    private final Setting<Boolean> coord = sgCoord.add(new BoolSetting.Builder().name("coord-logger").defaultValue(true).build());
    private final Setting<Boolean> players = sgCoord.add(new BoolSetting.Builder().name("players").description("Logs player teleports.").defaultValue(true).build());
    private final Setting<Boolean> wolves = sgCoord.add(new BoolSetting.Builder().name("wolves").description("Logs wolf teleports.").defaultValue(false).build());
    private final Setting<Double> minDistance = sgCoord.add(new DoubleSetting.Builder().name("minimum-distance").description("Minimum movement distance to log as teleport.").min(5.0D).max(100.0D).sliderMin(5.0D).sliderMax(100.0D).defaultValue(10.0D).build());
    private final Setting<Boolean> lighting = sgCoord.add(new BoolSetting.Builder().name("lighting").description("Logs lightings.").defaultValue(false).build());
    private final Setting<Boolean> enderDragons = sgCoord.add(new BoolSetting.Builder().name("ender-dragons").description("Logs killed ender dragons.").defaultValue(false).build());
    private final Setting<Boolean> endPortals = sgCoord.add(new BoolSetting.Builder().name("end-portals").description("Logs opened end portals.").defaultValue(false).build());
    private final Setting<Boolean> withers = sgCoord.add(new BoolSetting.Builder().name("withers").description("Logs wither spawns.").defaultValue(false).build());
    private final Setting<Boolean> antiv = sgExtra.add(new BoolSetting.Builder().name("anti-vanish").defaultValue(true).build());
    private final Setting<Boolean> render = sgExtra.add(new BoolSetting.Builder().name("render-burrow").defaultValue(true).build());
    private final Setting<ShapeMode> shapeMode = sgExtra.add(new EnumSetting.Builder<ShapeMode>().name("shape-mode").description("How the shapes are rendered.").defaultValue(ShapeMode.Both).build());
    private final Setting<SettingColor> sideColor = sgExtra.add(new ColorSetting.Builder().name("side-color").description("The side color of the target block rendering.").defaultValue(new SettingColor(197, 137, 232, 10)).build());
    private final Setting<SettingColor> lineColor = sgExtra.add(new ColorSetting.Builder().name("line-color").description("The line color of the target block rendering.").defaultValue(new SettingColor(197, 137, 232)).build());

    private final Queue<UUID> toLookup = new ConcurrentLinkedQueue();
    private long lastTick = 0L;
    Set<PlayerEntity> playersBur = new HashSet();
    private String m = " starting break ";
    private int timer = 0;

    public NotiferPlus() {
        super(CrystalCL.Exc, "notifer+", "Notifer+");
    }

    public void onActivate() {
        if (render.get()) {
            playersBur.clear();
        }

        if (breakg.get()) {
            timer = 0;
        }
    }

    public void onDeactivate() {
        toLookup.clear();
    }

    @EventHandler
    public void onLeave(GameLeftEvent event) {
        toLookup.clear();
    }

    @EventHandler
    private void PacketEvent(PacketEvent.Receive e) {
        if (breakg.get()) {
            Packet var3 = e.packet;
            if (var3 instanceof EntityStatusS2CPacket) {
                EntityStatusS2CPacket p = (EntityStatusS2CPacket) var3;
                if (p.getStatus() == 2 && p.getEntity(mc.world) == mc.player) {
                    timer = seconds.get() * 20;
                }
            }
        }
    }

    @EventHandler
    private void ag(TickEvent.Pre e) {
        if (timer > 0) {
            --timer;
            BaseText text = new LiteralText("");
            List<BaseText> list = new ArrayList();
            LiteralText a;
            if (helmet.get() && !mc.player.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                a = new LiteralText(percent(EquipmentSlot.HEAD));
                a.setStyle(a.getStyle().withColor(color(EquipmentSlot.HEAD)));
                list.add(a);
            }

            if (chest.get() && !mc.player.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
                a = new LiteralText(String.format("%s", mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem().equals(Items.ELYTRA) ? "elytra " : "chest ", percent(EquipmentSlot.CHEST)));
                a.setStyle(a.getStyle().withColor(color(EquipmentSlot.CHEST)));
                list.add(a);
            }

            if (leegs.get() && !mc.player.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) {
                a = new LiteralText(percent(EquipmentSlot.LEGS));
                a.setStyle(a.getStyle().withColor(color(EquipmentSlot.LEGS)));
                list.add(a);
            }

            if (feet.get() && !mc.player.getEquippedStack(EquipmentSlot.FEET).isEmpty()) {
                a = new LiteralText(percent(EquipmentSlot.FEET));
                a.setStyle(a.getStyle().withColor(color(EquipmentSlot.FEET)));
                list.add(a);
            }

            mc.inGameHud.setOverlayMessage(text, true);
        }
    }

    @EventHandler
    private void a(PacketEvent.Receive event) {
        if (surroundbreak.get() && event.packet instanceof BlockBreakingProgressS2CPacket) {
            BlockBreakingProgressS2CPacket w = (BlockBreakingProgressS2CPacket) event.packet;
            if (w.getProgress() != 0) {
                return;
            }

            String player = mc.world.getEntityById(w.getEntityId()).getName().asString();
            BlockPos p = mc.player.getBlockPos();
            BlockPos brpos = w.getPos();
            BlockPos[] var6;
            int var7;
            int var8;
            BlockPos a;
            if (legs.get()) {
                var6 = new BlockPos[]{p.east(), p.west(), p.south(), p.north()};
                var7 = var6.length;

                for (var8 = 0; var8 < var7; ++var8) {
                    a = var6[var8];
                    if (a.equals(brpos)) {
                        info(player + m + "legs");
                    }
                }
            }

            if (face.get()) {
                var6 = new BlockPos[]{p.up().east(), p.up().west(), p.up().south(), p.up().north()};
                var7 = var6.length;

                for (var8 = 0; var8 < var7; ++var8) {
                    a = var6[var8];
                    if (a.equals(brpos)) {
                        info(player + m + "face");
                    }
                }
            }

            if (head.get() && p.up(2).equals(brpos)) {
                info(player + m + "head");
            }
        }

        if (coord.get()) {
            if (event.packet instanceof EntityPositionS2CPacket packet) {
                try {
                    Entity entity = mc.world.getEntityById(packet.getId());
                    Vec3d packetPosition;
                    Vec3d wolfPosition;
                    if (entity.getType().equals(EntityType.PLAYER) && players.get()) {
                        packetPosition = new Vec3d(packet.getX(), packet.getY(), packet.getZ());
                        wolfPosition = entity.getPos();
                        if (wolfPosition.distanceTo(packetPosition) >= minDistance.get()) {
                            info(formatMessage("Player '" + entity.getEntityName() + "' has teleported to ", packetPosition));
                            return;
                        }
                    }

                    if (entity.getType().equals(EntityType.WOLF) && wolves.get()) {
                        packetPosition = new Vec3d(packet.getX(), packet.getY(), packet.getZ());
                        wolfPosition = entity.getPos();
                        UUID ownerUuid = ((TameableEntity) entity).getOwnerUuid();
                        if (ownerUuid != null && wolfPosition.distanceTo(packetPosition) >= minDistance.get()) {
                            info(formatMessage("Wolf has teleported to ", packetPosition));
                            return;
                        }
                    }
                } catch (NullPointerException ignored) {
                    ignored.fillInStackTrace();
                }
            }

            if (event.packet instanceof PlaySoundS2CPacket) {
                double var10001;
                if (((PlaySoundS2CPacket) event.packet).getSound() == SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER && lighting.get()) {
                    var10001 = ((PlaySoundS2CPacket) event.packet).getX();
                    info("Thunder sound detected -> " + var10001 + ", " + ((PlaySoundS2CPacket) event.packet).getY() + ", " + ((PlaySoundS2CPacket) event.packet).getZ());
                }

                if (((PlaySoundS2CPacket) event.packet).getSound() == SoundEvents.BLOCK_END_PORTAL_SPAWN && endPortals.get()) {
                    var10001 = ((PlaySoundS2CPacket) event.packet).getX();
                    info("End portal sound detected -> " + var10001 + ", " + ((PlaySoundS2CPacket) event.packet).getY() + ", " + ((PlaySoundS2CPacket) event.packet).getZ());
                }

                if (((PlaySoundS2CPacket) event.packet).getSound() == SoundEvents.ENTITY_WITHER_SPAWN && endPortals.get()) {
                    var10001 = ((PlaySoundS2CPacket) event.packet).getX();
                    info("Wither sound detected -> " + var10001 + ", " + ((PlaySoundS2CPacket) event.packet).getY() + ", " + ((PlaySoundS2CPacket) event.packet).getZ());
                }
            }
        }

        if (antiv.get() && event.packet instanceof PlayerListS2CPacket packet) {
            if (packet.getAction() == PlayerListS2CPacket.Action.UPDATE_LATENCY) {
                try {
                    for (PlayerListS2CPacket.Entry entry : packet.getEntries()) {
                        if (mc.getNetworkHandler().getPlayerListEntry(entry.getProfile().getId()) == null) {
                            toLookup.add(entry.getProfile().getId());
                        }
                    }
                } catch (Exception ignored) {
                    ignored.fillInStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (antiv.get()) {
            long time = mc.world.getTime();
            UUID lookup;
            if (Math.abs(lastTick - time) > 100L && (lookup = (UUID) toLookup.poll()) != null) {
                try {
                    String name = getPlayerNameFromUUID(lookup);
                    if (name != null) {
                        error(name + " using /vanish.");
                    }
                } catch (Exception ignored) {
                    ignored.fillInStackTrace();
                }

                lastTick = time;
            }
        }

        if (render.get()) {
            if (mc.world == null || mc.player == null) {
                return;
            }

            Iterator var7 = (new ArrayList(playersBur)).iterator();

            label63:
            while (true) {
                PlayerEntity p;
                do {
                    if (!var7.hasNext()) {
                        var7 = mc.world.getPlayers().iterator();

                        while (var7.hasNext()) {
                            p = (PlayerEntity) var7.next();
                            if (!p.equals(mc.player) && !playersBur.contains(p) && p.isOnGround() && Friends.get().shouldAttack(p) && inBlock(p)) {
                                BaseText t = new LiteralText("Player ");
                                BaseText t2 = (BaseText) p.getName();
                                t2.setStyle(t2.getStyle().withColor(TextColor.fromRgb(16711680)));
                                t.append(t2);
                                t.append(" burrowed in ");
                                t.append(new TranslatableText(mc.world.getBlockState(p.getBlockPos()).getBlock().getTranslationKey()));
                                info(t);
                                playersBur.add(p);
                            }
                        }
                        break label63;
                    }

                    p = (PlayerEntity) var7.next();
                } while (mc.world.getPlayers().contains(p) && inBlock(p));

                playersBur.remove(p);
            }
        }
    }

    public String getPlayerNameFromUUID(UUID id) {
        try {
            NameLookup process = new NameLookup(id, mc);
            Thread thread = new Thread(process);
            thread.start();
            thread.join();
            return process.getName();
        } catch (Exception var4) {
            return null;
        }
    }

    @EventHandler
    private void RenderEvent(Render3DEvent e) {
        if (render.get()) {
            for (PlayerEntity p : playersBur) {
                if (mc.world.getPlayers().contains(p)) {
                    e.renderer.box(p.getBlockPos(), sideColor.get(), lineColor.get(), (ShapeMode) shapeMode.get(), 0);
                }
            }
        }
    }

    private boolean inBlock(PlayerEntity p) {
        return mc.world.getBlockState(p.getBlockPos()).isFullCube(mc.world, p.getBlockPos()) || mc.world.getBlockState(p.getBlockPos()).hasBlockEntity() && mc.world.getBlockState(p.getBlockPos()).getHardness(mc.world, p.getBlockPos()) > 10.0F || mc.world.getBlockState(p.getBlockPos()).getBlock() instanceof AnvilBlock;
    }

    public BaseText formatMessage(String message, Vec3d coords) {
        BaseText text = new LiteralText(message);
        text.append(ChatUtils.formatCoords(coords));
        text.append(Formatting.GRAY.toString() + ".");
        return text;
    }

    public BaseText formatMessage(String message, BlockPos coords) {
        return formatMessage(message, new Vec3d(coords.getX(), coords.getY(), coords.getZ()));
    }

    private String percent(EquipmentSlot slot) {
        ItemStack s = mc.player.getEquippedStack(slot);
        int var10000 = Math.round((float) (s.getMaxDamage() - s.getDamage()) * 100.0F / (float) s.getMaxDamage());
        return var10000 + "%";
    }

    private TextColor color(EquipmentSlot slot) {
        int current = mc.player.getEquippedStack(slot).getDamage();
        int max = mc.player.getEquippedStack(slot).getMaxDamage();
        int r = 255 - Math.round((float) ((max - current) * 255 / max));
        int g = Math.round((float) ((max - current) * 255 / max));
        return TextColor.fromRgb((r & 255) << 16 | (g & 255) << 8);
    }

    public static class NameLookup implements Runnable {
        private final String uuidstr;
        private final UUID uuid;
        private final MinecraftClient mc = MinecraftClient.getInstance();
        private volatile String name;

        public NameLookup(String input, MinecraftClient mc) {
            uuidstr = input;
            uuid = UUID.fromString(input);
            mc = mc;
        }

        public NameLookup(UUID input, MinecraftClient mc) {
            uuid = input;
            uuidstr = input.toString();
            mc = mc;
        }

        public void run() {
            name = lookUpName();
        }

        public String lookUpName() {
            PlayerEntity player = null;
            if (mc.world != null) {
                player = mc.world.getPlayerByUuid(uuid);
            }

            if (player == null) {
                String url = "https://api.mojang.com/user/profiles/" + uuidstr.replace("-", "") + "/names";

                try {
                    JsonParser parser = new JsonParser();
                    String nameJson = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
                    JsonElement nameElement = parser.parse(nameJson);
                    JsonArray nameArray = nameElement.getAsJsonArray();
                    String playerSlot = nameArray.get(nameArray.size() - 1).toString();
                    JsonObject nameObject = parser.parse(playerSlot).getAsJsonObject();
                    return nameObject.get("name").toString();
                } catch (Exception var9) {
                    return null;
                }
            } else {
                return player.getName().asString();
            }
        }

        public String getName() {
            return name;
        }
    }
}
