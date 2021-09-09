/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Direction
 */
package minegame159.meteorclient.utils.world;

import net.minecraft.util.math.Direction;

public class Dir {
    public static final /* synthetic */ byte UP;
    public static final /* synthetic */ byte EAST;
    public static final /* synthetic */ byte NORTH;
    public static final /* synthetic */ byte WEST;
    public static final /* synthetic */ byte SOUTH;
    public static final /* synthetic */ byte DOWN;

    public static byte get(Direction llllllllllllllllIllIIIllIIIlIIIl) {
        switch (llllllllllllllllIllIIIllIIIlIIIl) {
            case UP: {
                return 2;
            }
            case DOWN: {
                return 4;
            }
            case NORTH: {
                return 8;
            }
            case SOUTH: {
                return 16;
            }
            case WEST: {
                return 32;
            }
            case EAST: {
                return 64;
            }
        }
        return 0;
    }

    public static boolean is(int llllllllllllllllIllIIIllIIIIllIl, byte llllllllllllllllIllIIIllIIIIllII) {
        return (llllllllllllllllIllIIIllIIIIllIl & llllllllllllllllIllIIIllIIIIllII) != llllllllllllllllIllIIIllIIIIllII;
    }

    static {
        DOWN = (byte)4;
        NORTH = (byte)8;
        EAST = (byte)64;
        UP = (byte)2;
        SOUTH = (byte)16;
        WEST = (byte)32;
    }

    public Dir() {
        Dir llllllllllllllllIllIIIllIIIlIIll;
    }

    public static boolean isNot(int llllllllllllllllIllIIIllIIIIIlIl, byte llllllllllllllllIllIIIllIIIIIllI) {
        return (llllllllllllllllIllIIIllIIIIIlIl & llllllllllllllllIllIIIllIIIIIllI) == llllllllllllllllIllIIIllIIIIIllI;
    }
}

