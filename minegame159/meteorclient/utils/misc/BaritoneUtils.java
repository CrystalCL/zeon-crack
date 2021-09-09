/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  baritone.api.BaritoneAPI
 *  baritone.api.utils.Rotation
 */
package minegame159.meteorclient.utils.misc;

import baritone.api.BaritoneAPI;
import baritone.api.utils.Rotation;
import java.lang.reflect.Field;

public class BaritoneUtils {
    private static /* synthetic */ Field targetField;

    private static void findField() {
        if (targetField != null) {
            return;
        }
        Class<?> llIIIlllIlIlIIl = BaritoneAPI.getProvider().getPrimaryBaritone().getLookBehavior().getClass();
        for (Field llIIIlllIlIlIlI : llIIIlllIlIlIIl.getDeclaredFields()) {
            if (llIIIlllIlIlIlI.getType() != Rotation.class) continue;
            targetField = llIIIlllIlIlIlI;
            break;
        }
    }

    public static Rotation getTarget() {
        BaritoneUtils.findField();
        if (targetField == null) {
            return null;
        }
        targetField.setAccessible(true);
        try {
            return (Rotation)targetField.get((Object)BaritoneAPI.getProvider().getPrimaryBaritone().getLookBehavior());
        }
        catch (IllegalAccessException llIIIlllIllIIIl) {
            llIIIlllIllIIIl.printStackTrace();
            return null;
        }
    }

    public BaritoneUtils() {
        BaritoneUtils llIIIlllIllIIll;
    }
}

