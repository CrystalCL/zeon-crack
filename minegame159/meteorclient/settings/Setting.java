/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.registry.Registry
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.util.Identifier
 *  org.apache.commons.lang3.StringUtils
 */
package minegame159.meteorclient.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.misc.Lazy;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

public abstract class Setting<T>
implements Lazy<T>,
ISerializable<T> {
    protected /* synthetic */ T value;
    protected final /* synthetic */ T defaultValue;
    private final /* synthetic */ Consumer<T> onChanged;
    public final /* synthetic */ Consumer<Setting<T>> onModuleActivated;
    public final /* synthetic */ String name;
    private static final /* synthetic */ List<String> NO_SUGGESTIONS;
    public final /* synthetic */ String title;
    public /* synthetic */ Module module;
    public final /* synthetic */ String description;

    public boolean set(T lllllllllllllllllIIlIIIlIlIlIIll) {
        Setting lllllllllllllllllIIlIIIlIlIlIllI;
        if (!lllllllllllllllllIIlIIIlIlIlIllI.isValueValid(lllllllllllllllllIIlIIIlIlIlIIll)) {
            return false;
        }
        lllllllllllllllllIIlIIIlIlIlIllI.value = lllllllllllllllllIIlIIIlIlIlIIll;
        lllllllllllllllllIIlIIIlIlIlIllI.changed();
        return true;
    }

    public void onActivated() {
        Setting lllllllllllllllllIIlIIIlIIlllIll;
        if (lllllllllllllllllIIlIIIlIIlllIll.onModuleActivated != null) {
            lllllllllllllllllIIlIIIlIIlllIll.onModuleActivated.accept(lllllllllllllllllIIlIIIlIIlllIll);
        }
    }

    public Setting(String lllllllllllllllllIIlIIIlIllIIIII, String lllllllllllllllllIIlIIIlIllIIlIl, T lllllllllllllllllIIlIIIlIllIIlII, Consumer<T> lllllllllllllllllIIlIIIlIlIlllIl, Consumer<Setting<T>> lllllllllllllllllIIlIIIlIlIlllII) {
        Setting lllllllllllllllllIIlIIIlIllIIIIl;
        lllllllllllllllllIIlIIIlIllIIIIl.name = lllllllllllllllllIIlIIIlIllIIIII;
        lllllllllllllllllIIlIIIlIllIIIIl.title = Arrays.stream(lllllllllllllllllIIlIIIlIllIIIII.split("-")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
        lllllllllllllllllIIlIIIlIllIIIIl.description = lllllllllllllllllIIlIIIlIllIIlIl;
        lllllllllllllllllIIlIIIlIllIIIIl.defaultValue = lllllllllllllllllIIlIIIlIllIIlII;
        lllllllllllllllllIIlIIIlIllIIIIl.reset(false);
        lllllllllllllllllIIlIIIlIllIIIIl.onChanged = lllllllllllllllllIIlIIIlIlIlllIl;
        lllllllllllllllllIIlIIIlIllIIIIl.onModuleActivated = lllllllllllllllllIIlIIIlIlIlllII;
    }

    protected abstract boolean isValueValid(T var1);

    public boolean parse(String lllllllllllllllllIIlIIIlIlIIIIlI) {
        Setting lllllllllllllllllIIlIIIlIlIIIllI;
        T lllllllllllllllllIIlIIIlIlIIIlII = lllllllllllllllllIIlIIIlIlIIIllI.parseImpl(lllllllllllllllllIIlIIIlIlIIIIlI);
        if (lllllllllllllllllIIlIIIlIlIIIlII != null && lllllllllllllllllIIlIIIlIlIIIllI.isValueValid(lllllllllllllllllIIlIIIlIlIIIlII)) {
            lllllllllllllllllIIlIIIlIlIIIllI.value = lllllllllllllllllIIlIIIlIlIIIlII;
            lllllllllllllllllIIlIIIlIlIIIllI.changed();
        }
        return lllllllllllllllllIIlIIIlIlIIIlII != null;
    }

    @Override
    public T get() {
        Setting lllllllllllllllllIIlIIIlIlIllIlI;
        return lllllllllllllllllIIlIIIlIlIllIlI.value;
    }

    public void reset(boolean lllllllllllllllllIIlIIIlIlIIllIl) {
        Setting lllllllllllllllllIIlIIIlIlIIlllI;
        lllllllllllllllllIIlIIIlIlIIlllI.value = lllllllllllllllllIIlIIIlIlIIlllI.defaultValue;
        if (lllllllllllllllllIIlIIIlIlIIllIl) {
            lllllllllllllllllIIlIIIlIlIIlllI.changed();
        }
    }

    protected abstract T parseImpl(String var1);

    public int hashCode() {
        Setting lllllllllllllllllIIlIIIlIIlIIlII;
        return Objects.hash(lllllllllllllllllIIlIIIlIIlIIlII.name);
    }

    public Iterable<Identifier> getIdentifierSuggestions() {
        return null;
    }

    public List<String> getSuggestions() {
        return NO_SUGGESTIONS;
    }

    protected NbtCompound saveGeneral() {
        Setting lllllllllllllllllIIlIIIlIIllIlII;
        NbtCompound lllllllllllllllllIIlIIIlIIllIlIl = new NbtCompound();
        lllllllllllllllllIIlIIIlIIllIlIl.putString("name", lllllllllllllllllIIlIIIlIIllIlII.name);
        return lllllllllllllllllIIlIIIlIIllIlIl;
    }

    public boolean equals(Object lllllllllllllllllIIlIIIlIIlIlIll) {
        Setting lllllllllllllllllIIlIIIlIIlIlIIl;
        if (lllllllllllllllllIIlIIIlIIlIlIIl == lllllllllllllllllIIlIIIlIIlIlIll) {
            return true;
        }
        if (lllllllllllllllllIIlIIIlIIlIlIll == null || lllllllllllllllllIIlIIIlIIlIlIIl.getClass() != lllllllllllllllllIIlIIIlIIlIlIll.getClass()) {
            return false;
        }
        Setting lllllllllllllllllIIlIIIlIIlIlIlI = (Setting)lllllllllllllllllIIlIIIlIIlIlIll;
        return Objects.equals(lllllllllllllllllIIlIIIlIIlIlIIl.name, lllllllllllllllllIIlIIIlIIlIlIlI.name);
    }

    public String toString() {
        Setting lllllllllllllllllIIlIIIlIIllIIII;
        return lllllllllllllllllIIlIIIlIIllIIII.value.toString();
    }

    public void changed() {
        Setting lllllllllllllllllIIlIIIlIIlllllI;
        if (lllllllllllllllllIIlIIIlIIlllllI.onChanged != null) {
            lllllllllllllllllIIlIIIlIIlllllI.onChanged.accept(lllllllllllllllllIIlIIIlIIlllllI.value);
        }
    }

    public void reset() {
        Setting lllllllllllllllllIIlIIIlIlIIlIll;
        lllllllllllllllllIIlIIIlIlIIlIll.reset(true);
    }

    static {
        NO_SUGGESTIONS = new ArrayList<String>(0);
    }

    public static <T> T parseId(Registry<T> lllllllllllllllllIIlIIIlIIIlllll, String lllllllllllllllllIIlIIIlIIIllllI) {
        Identifier lllllllllllllllllIIlIIIlIIIlllIl;
        if ((lllllllllllllllllIIlIIIlIIIllllI = lllllllllllllllllIIlIIIlIIIllllI.trim()).contains(":")) {
            Identifier lllllllllllllllllIIlIIIlIIlIIIII = new Identifier(lllllllllllllllllIIlIIIlIIIllllI);
        } else {
            lllllllllllllllllIIlIIIlIIIlllIl = new Identifier("minecraft", lllllllllllllllllIIlIIIlIIIllllI);
        }
        if (lllllllllllllllllIIlIIIlIIIlllll.containsId(lllllllllllllllllIIlIIIlIIIlllIl)) {
            return (T)lllllllllllllllllIIlIIIlIIIlllll.get(lllllllllllllllllIIlIIIlIIIlllIl);
        }
        return null;
    }
}

