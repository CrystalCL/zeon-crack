/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.utils.world;

import net.minecraft.util.math.Direction;

public class Dir {
    public static final byte SOUTH;
    public static final byte NORTH;
    public static final byte EAST;
    public static final byte UP;
    public static final byte DOWN;
    public static final byte WEST;

    public static byte get(Direction Direction2) {
        switch (1.$SwitchMap$net$minecraft$util$math$Direction[Direction2.ordinal()]) {
            case 1: {
                return 2;
            }
            case 2: {
                return 4;
            }
            case 3: {
                return 8;
            }
            case 4: {
                return 16;
            }
            case 5: {
                return 32;
            }
            case 6: {
                return 64;
            }
        }
        return 0;
    }

    public static boolean is(int n, byte by) {
        return (n & by) != by;
    }

    public static boolean isNot(int n, byte by) {
        return (n & by) == by;
    }

    static {
        UP = (byte)2;
        SOUTH = (byte)16;
        EAST = (byte)64;
        WEST = (byte)32;
        DOWN = (byte)4;
        NORTH = (byte)8;
    }
}

