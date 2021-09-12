/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.modules.render.hud.modules;

import minegame159.meteorclient.systems.modules.render.hud.HUD;
import minegame159.meteorclient.systems.modules.render.hud.modules.DoubleTextHudElement;

public class RotationHud
extends DoubleTextHudElement {
    public RotationHud(HUD hUD) {
        super(hUD, "rotation", "Displays your rotation.", "");
    }

    @Override
    protected String getRight() {
        float f = this.mc.field_1773.method_19418().method_19330() % 360.0f;
        float f2 = this.mc.field_1773.method_19418().method_19329() % 360.0f;
        if (f < 0.0f) {
            f += 360.0f;
        }
        if (f2 < 0.0f) {
            f2 += 360.0f;
        }
        Direction direction = (double)f >= 337.5 || (double)f < 22.5 ? Direction.South : ((double)f >= 22.5 && (double)f < 67.5 ? Direction.SouthWest : ((double)f >= 67.5 && (double)f < 112.5 ? Direction.West : ((double)f >= 112.5 && (double)f < 157.5 ? Direction.NorthWest : ((double)f >= 157.5 && (double)f < 202.5 ? Direction.North : ((double)f >= 202.5 && (double)f < 247.5 ? Direction.NorthEast : ((double)f >= 247.5 && (double)f < 292.5 ? Direction.East : ((double)f >= 292.5 && (double)f < 337.5 ? Direction.SouthEast : Direction.NaN)))))));
        if (f > 180.0f) {
            f -= 360.0f;
        }
        if (f2 > 180.0f) {
            f2 -= 360.0f;
        }
        this.setLeft(String.format("%s %s ", direction.name, direction.axis));
        return String.format("(%.1f, %.1f)", Float.valueOf(f), Float.valueOf(f2));
    }

    private static final class Direction
    extends Enum<Direction> {
        public String axis;
        public static final /* enum */ Direction SouthEast;
        public static final /* enum */ Direction South;
        public static final /* enum */ Direction SouthWest;
        public static final /* enum */ Direction NorthEast;
        public static final /* enum */ Direction NaN;
        public String name;
        public static final /* enum */ Direction West;
        public static final /* enum */ Direction North;
        public static final /* enum */ Direction East;
        public static final /* enum */ Direction NorthWest;
        private static final Direction[] $VALUES;

        public static Direction[] values() {
            return (Direction[])$VALUES.clone();
        }

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

        private Direction(String string2, String string3) {
            this.axis = string3;
            this.name = string2;
        }

        private static Direction[] $values() {
            return new Direction[]{South, SouthEast, West, NorthWest, North, NorthEast, East, SouthWest, NaN};
        }

        public static Direction valueOf(String string) {
            return Enum.valueOf(Direction.class, string);
        }
    }
}

