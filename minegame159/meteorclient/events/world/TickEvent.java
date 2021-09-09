/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.events.world;

public class TickEvent {
    public TickEvent() {
        TickEvent lllllllllllllllllIlIllIllIIllIlI;
    }

    public static class Pre
    extends TickEvent {
        private static final /* synthetic */ Pre INSTANCE;

        static {
            INSTANCE = new Pre();
        }

        public static Pre get() {
            return INSTANCE;
        }

        public Pre() {
            Pre llIlllIllIIIIll;
        }
    }

    public static class Post
    extends TickEvent {
        private static final /* synthetic */ Post INSTANCE;

        static {
            INSTANCE = new Post();
        }

        public Post() {
            Post lllllllllllllllllllllIIIIlllllIl;
        }

        public static Post get() {
            return INSTANCE;
        }
    }
}

