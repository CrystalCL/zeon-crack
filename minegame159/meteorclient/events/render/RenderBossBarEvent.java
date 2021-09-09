/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.text.Text
 *  net.minecraft.client.gui.hud.ClientBossBar
 */
package minegame159.meteorclient.events.render;

import java.util.Iterator;
import minegame159.meteorclient.events.Cancellable;
import net.minecraft.text.Text;
import net.minecraft.client.gui.hud.ClientBossBar;

public class RenderBossBarEvent
extends Cancellable {
    public RenderBossBarEvent() {
        RenderBossBarEvent llllllllIIllllI;
    }

    public static class BossIterator {
        private static final /* synthetic */ BossIterator INSTANCE;
        public /* synthetic */ Iterator<ClientBossBar> iterator;

        public static BossIterator get(Iterator<ClientBossBar> llllllllllllllllllIllIllIlIlIIll) {
            BossIterator.INSTANCE.iterator = llllllllllllllllllIllIllIlIlIIll;
            return INSTANCE;
        }

        public BossIterator() {
            BossIterator llllllllllllllllllIllIllIlIlIlIl;
        }

        static {
            INSTANCE = new BossIterator();
        }
    }

    public static class BossSpacing {
        private static final /* synthetic */ BossSpacing INSTANCE;
        public /* synthetic */ int spacing;

        public static BossSpacing get(int llllllllllllllllIllIIIlIlllIIlII) {
            BossSpacing.INSTANCE.spacing = llllllllllllllllIllIIIlIlllIIlII;
            return INSTANCE;
        }

        static {
            INSTANCE = new BossSpacing();
        }

        public BossSpacing() {
            BossSpacing llllllllllllllllIllIIIlIlllIlIII;
        }
    }

    public static class BossText {
        private static final /* synthetic */ BossText INSTANCE;
        public /* synthetic */ ClientBossBar bossBar;
        public /* synthetic */ Text name;

        static {
            INSTANCE = new BossText();
        }

        public BossText() {
            BossText lllllllllllllllllIllIlIllllIllll;
        }

        public static BossText get(ClientBossBar lllllllllllllllllIllIlIllllIlIll, Text lllllllllllllllllIllIlIllllIlIlI) {
            BossText.INSTANCE.bossBar = lllllllllllllllllIllIlIllllIlIll;
            BossText.INSTANCE.name = lllllllllllllllllIllIlIllllIlIlI;
            return INSTANCE;
        }
    }
}

