/*
 * Decompiled with CFR 0.150.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;

public class RotationHud
extends DoubleTextHudElement {
    public RotationHud(HUD lllllllllllllllllIlIIIlIllllIIIl) {
        super(lllllllllllllllllIlIIIlIllllIIIl, "rotation", "Displays your rotation.", "");
        RotationHud lllllllllllllllllIlIIIlIllllIIlI;
    }

    @Override
    protected String getRight() {
        Direction lllllllllllllllllIlIIIlIlllIIIIl;
        RotationHud lllllllllllllllllIlIIIlIllIllllI;
        float lllllllllllllllllIlIIIlIlllIIIII = lllllllllllllllllIlIIIlIllIllllI.mc.gameRenderer.getCamera().getYaw() % 360.0f;
        float lllllllllllllllllIlIIIlIllIlllll = lllllllllllllllllIlIIIlIllIllllI.mc.gameRenderer.getCamera().getPitch() % 360.0f;
        if (lllllllllllllllllIlIIIlIlllIIIII < 0.0f) {
            lllllllllllllllllIlIIIlIlllIIIII += 360.0f;
        }
        if (lllllllllllllllllIlIIIlIllIlllll < 0.0f) {
            lllllllllllllllllIlIIIlIllIlllll += 360.0f;
        }
        if ((double)lllllllllllllllllIlIIIlIlllIIIII >= 337.5 || (double)lllllllllllllllllIlIIIlIlllIIIII < 22.5) {
            Direction lllllllllllllllllIlIIIlIlllIlIlI = Direction.South;
        } else if ((double)lllllllllllllllllIlIIIlIlllIIIII >= 22.5 && (double)lllllllllllllllllIlIIIlIlllIIIII < 67.5) {
            Direction lllllllllllllllllIlIIIlIlllIlIIl = Direction.SouthWest;
        } else if ((double)lllllllllllllllllIlIIIlIlllIIIII >= 67.5 && (double)lllllllllllllllllIlIIIlIlllIIIII < 112.5) {
            Direction lllllllllllllllllIlIIIlIlllIlIII = Direction.West;
        } else if ((double)lllllllllllllllllIlIIIlIlllIIIII >= 112.5 && (double)lllllllllllllllllIlIIIlIlllIIIII < 157.5) {
            Direction lllllllllllllllllIlIIIlIlllIIlll = Direction.NorthWest;
        } else if ((double)lllllllllllllllllIlIIIlIlllIIIII >= 157.5 && (double)lllllllllllllllllIlIIIlIlllIIIII < 202.5) {
            Direction lllllllllllllllllIlIIIlIlllIIllI = Direction.North;
        } else if ((double)lllllllllllllllllIlIIIlIlllIIIII >= 202.5 && (double)lllllllllllllllllIlIIIlIlllIIIII < 247.5) {
            Direction lllllllllllllllllIlIIIlIlllIIlIl = Direction.NorthEast;
        } else if ((double)lllllllllllllllllIlIIIlIlllIIIII >= 247.5 && (double)lllllllllllllllllIlIIIlIlllIIIII < 292.5) {
            Direction lllllllllllllllllIlIIIlIlllIIlII = Direction.East;
        } else if ((double)lllllllllllllllllIlIIIlIlllIIIII >= 292.5 && (double)lllllllllllllllllIlIIIlIlllIIIII < 337.5) {
            Direction lllllllllllllllllIlIIIlIlllIIIll = Direction.SouthEast;
        } else {
            lllllllllllllllllIlIIIlIlllIIIIl = Direction.NaN;
        }
        if (lllllllllllllllllIlIIIlIlllIIIII > 180.0f) {
            lllllllllllllllllIlIIIlIlllIIIII -= 360.0f;
        }
        if (lllllllllllllllllIlIIIlIllIlllll > 180.0f) {
            lllllllllllllllllIlIIIlIllIlllll -= 360.0f;
        }
        lllllllllllllllllIlIIIlIllIllllI.setLeft(String.format("%s %s ", lllllllllllllllllIlIIIlIlllIIIIl.name, lllllllllllllllllIlIIIlIlllIIIIl.axis));
        return String.format("(%.1f, %.1f)", Float.valueOf(lllllllllllllllllIlIIIlIlllIIIII), Float.valueOf(lllllllllllllllllIlIIIlIllIlllll));
    }

    private static final class Direction
    extends Enum<Direction> {
        public /* synthetic */ String name;
        public /* synthetic */ String axis;
        public static final /* synthetic */ /* enum */ Direction East;
        public static final /* synthetic */ /* enum */ Direction SouthEast;
        public static final /* synthetic */ /* enum */ Direction SouthWest;
        public static final /* synthetic */ /* enum */ Direction West;
        public static final /* synthetic */ /* enum */ Direction NaN;
        public static final /* synthetic */ /* enum */ Direction NorthEast;
        public static final /* synthetic */ /* enum */ Direction North;
        private static final /* synthetic */ Direction[] $VALUES;
        public static final /* synthetic */ /* enum */ Direction NorthWest;
        public static final /* synthetic */ /* enum */ Direction South;

        static {
            South = new Direction("South", "Z+");
            SouthEast = new Direction("South East", "Z+ X+");
            West = new Direction("West", "X-");
            NorthWest = new Direction("North West", "Z- X-");
            North = new Direction("North", "Z-");
            NorthEast = new Direction("North East", "Z- X+");
            East = new Direction("East", "X+");
            SouthWest = new Direction("South West", "Z+ X-");
            NaN = new Direction("NaN", "NaN");
            $VALUES = Direction.$values();
        }

        public static Direction[] values() {
            return (Direction[])$VALUES.clone();
        }

        private static /* synthetic */ Direction[] $values() {
            return new Direction[]{South, SouthEast, West, NorthWest, North, NorthEast, East, SouthWest, NaN};
        }

        private Direction(String llllllIIIlIIllI, String llllllIIIlIlIlI) {
            Direction llllllIIIlIllII;
            llllllIIIlIllII.axis = llllllIIIlIlIlI;
            llllllIIIlIllII.name = llllllIIIlIIllI;
        }

        public static Direction valueOf(String llllllIIIllIIll) {
            return Enum.valueOf(Direction.class, llllllIIIllIIll);
        }
    }
}

