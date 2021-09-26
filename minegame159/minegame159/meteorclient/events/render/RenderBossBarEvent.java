/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.events.render;

import java.util.Iterator;
import minegame159.meteorclient.events.Cancellable;
import net.minecraft.text.Text;
import net.minecraft.client.gui.hud.ClientBossBar;

public class RenderBossBarEvent
extends Cancellable {

    public static class BossText {
        private static final BossText INSTANCE = new BossText();
        public Text name;
        public ClientBossBar bossBar;

        public static BossText get(ClientBossBar ClientBossBar2, Text Text2) {
            BossText.INSTANCE.bossBar = ClientBossBar2;
            BossText.INSTANCE.name = Text2;
            return INSTANCE;
        }
    }

    public static class BossSpacing {
        public int spacing;
        private static final BossSpacing INSTANCE = new BossSpacing();

        public static BossSpacing get(int n) {
            BossSpacing.INSTANCE.spacing = n;
            return INSTANCE;
        }
    }

    public static class BossIterator {
        public Iterator<ClientBossBar> iterator;
        private static final BossIterator INSTANCE = new BossIterator();

        public static BossIterator get(Iterator<ClientBossBar> iterator) {
            BossIterator.INSTANCE.iterator = iterator;
            return INSTANCE;
        }
    }
}

