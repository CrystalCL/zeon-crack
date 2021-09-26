/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.systems.macros;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import meteordevelopment.orbit.EventHandler;
import minegame159.meteorclient.events.meteor.KeyEvent;
import minegame159.meteorclient.events.meteor.MouseButtonEvent;
import minegame159.meteorclient.utils.misc.ISerializable;
import minegame159.meteorclient.utils.misc.Keybind;
import minegame159.meteorclient.utils.misc.NbtUtils;
import minegame159.meteorclient.utils.misc.input.KeyAction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtElement;
import net.minecraft.client.MinecraftClient;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Macro
implements ISerializable<Macro> {
    public List<String> messages = new ArrayList<String>(1);
    public String name = "";
    public Keybind keybind = Keybind.fromKey(-1);

    public int hashCode() {
        return Objects.hash(this.name);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Macro macro = (Macro)object;
        return Objects.equals(this.name, macro.name);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound NbtCompound2 = new NbtCompound();
        NbtCompound2.putString("name", this.name);
        NbtCompound2.put("keybind", (NbtElement)this.keybind.toTag());
        NbtList NbtList2 = new NbtList();
        for (String string : this.messages) {
            NbtList2.add((Object)NbtString.of((String)string));
        }
        NbtCompound2.put("messages", (NbtElement)NbtList2);
        return NbtCompound2;
    }

    @Override
    public Macro fromTag(NbtCompound NbtCompound2) {
        this.name = NbtCompound2.getString("name");
        if (NbtCompound2.contains("key")) {
            this.keybind.set(true, NbtCompound2.getInt("key"));
        } else {
            this.keybind.fromTag(NbtCompound2.getCompound("keybind"));
        }
        this.messages = NbtUtils.listFromTag(NbtCompound2.getList("messages", 8), NbtElement::asString);
        return this;
    }

    @EventHandler(priority=100)
    private void onButton(MouseButtonEvent mouseButtonEvent) {
        if (mouseButtonEvent.action != KeyAction.Release && this.onAction(false, mouseButtonEvent.button)) {
            mouseButtonEvent.cancel();
        }
    }

    public void addMessage(String string) {
        this.messages.add(string);
    }

    @Override
    public Object fromTag(NbtCompound NbtCompound2) {
        return this.fromTag(NbtCompound2);
    }

    @EventHandler(priority=100)
    private void onKey(KeyEvent keyEvent) {
        if (keyEvent.action != KeyAction.Release && this.onAction(true, keyEvent.key)) {
            keyEvent.cancel();
        }
    }

    public void removeMessage(int n) {
        this.messages.remove(n);
    }

    private boolean onAction(boolean bl, int n) {
        if (this.keybind.matches(bl, n) && MinecraftClient.getInstance().currentScreen == null) {
            for (String string : this.messages) {
                MinecraftClient.getInstance().player.sendChatMessage(string);
            }
            return true;
        }
        return false;
    }
}

