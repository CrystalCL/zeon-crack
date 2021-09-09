/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NbtCompound
 *  net.minecraft.nbt.NbtList
 *  net.minecraft.nbt.NbtString
 *  net.minecraft.nbt.NbtElement
 *  net.minecraft.client.MinecraftClient
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

public class Macro
implements ISerializable<Macro> {
    public /* synthetic */ String name;
    public /* synthetic */ List<String> messages;
    public /* synthetic */ Keybind keybind;

    private boolean onAction(boolean llllllllllllllllIllllIIllIIIIllI, int llllllllllllllllIllllIIllIIIIlIl) {
        Macro llllllllllllllllIllllIIllIIIlIlI;
        if (llllllllllllllllIllllIIllIIIlIlI.keybind.matches(llllllllllllllllIllllIIllIIIIllI, llllllllllllllllIllllIIllIIIIlIl) && MinecraftClient.getInstance().currentScreen == null) {
            for (String llllllllllllllllIllllIIllIIIlIll : llllllllllllllllIllllIIllIIIlIlI.messages) {
                MinecraftClient.getInstance().player.sendChatMessage(llllllllllllllllIllllIIllIIIlIll);
            }
            return true;
        }
        return false;
    }

    @Override
    public NbtCompound toTag() {
        Macro llllllllllllllllIllllIIlIllllIIl;
        NbtCompound llllllllllllllllIllllIIlIllllIll = new NbtCompound();
        llllllllllllllllIllllIIlIllllIll.putString("name", llllllllllllllllIllllIIlIllllIIl.name);
        llllllllllllllllIllllIIlIllllIll.put("keybind", (NbtElement)llllllllllllllllIllllIIlIllllIIl.keybind.toTag());
        NbtList llllllllllllllllIllllIIlIllllIlI = new NbtList();
        for (String llllllllllllllllIllllIIlIlllllIl : llllllllllllllllIllllIIlIllllIIl.messages) {
            llllllllllllllllIllllIIlIllllIlI.add((Object)NbtString.of((String)llllllllllllllllIllllIIlIlllllIl));
        }
        llllllllllllllllIllllIIlIllllIll.put("messages", (NbtElement)llllllllllllllllIllllIIlIllllIlI);
        return llllllllllllllllIllllIIlIllllIll;
    }

    public void addMessage(String llllllllllllllllIllllIIllIlIIlIl) {
        Macro llllllllllllllllIllllIIllIlIIlII;
        llllllllllllllllIllllIIllIlIIlII.messages.add(llllllllllllllllIllllIIllIlIIlIl);
    }

    @EventHandler(priority=100)
    private void onButton(MouseButtonEvent llllllllllllllllIllllIIllIIlIIll) {
        Macro llllllllllllllllIllllIIllIIlIIlI;
        if (llllllllllllllllIllllIIllIIlIIll.action != KeyAction.Release && llllllllllllllllIllllIIllIIlIIlI.onAction(false, llllllllllllllllIllllIIllIIlIIll.button)) {
            llllllllllllllllIllllIIllIIlIIll.cancel();
        }
    }

    public Macro() {
        Macro llllllllllllllllIllllIIllIlIlIIl;
        llllllllllllllllIllllIIllIlIlIIl.name = "";
        llllllllllllllllIllllIIllIlIlIIl.messages = new ArrayList<String>(1);
        llllllllllllllllIllllIIllIlIlIIl.keybind = Keybind.fromKey(-1);
    }

    public boolean equals(Object llllllllllllllllIllllIIlIllIlIlI) {
        Macro llllllllllllllllIllllIIlIllIlIII;
        if (llllllllllllllllIllllIIlIllIlIII == llllllllllllllllIllllIIlIllIlIlI) {
            return true;
        }
        if (llllllllllllllllIllllIIlIllIlIlI == null || llllllllllllllllIllllIIlIllIlIII.getClass() != llllllllllllllllIllllIIlIllIlIlI.getClass()) {
            return false;
        }
        Macro llllllllllllllllIllllIIlIllIlIIl = (Macro)llllllllllllllllIllllIIlIllIlIlI;
        return Objects.equals(llllllllllllllllIllllIIlIllIlIII.name, llllllllllllllllIllllIIlIllIlIIl.name);
    }

    @Override
    public Macro fromTag(NbtCompound llllllllllllllllIllllIIlIlllIIIl) {
        Macro llllllllllllllllIllllIIlIlllIIlI;
        llllllllllllllllIllllIIlIlllIIlI.name = llllllllllllllllIllllIIlIlllIIIl.getString("name");
        if (llllllllllllllllIllllIIlIlllIIIl.contains("key")) {
            llllllllllllllllIllllIIlIlllIIlI.keybind.set(true, llllllllllllllllIllllIIlIlllIIIl.getInt("key"));
        } else {
            llllllllllllllllIllllIIlIlllIIlI.keybind.fromTag(llllllllllllllllIllllIIlIlllIIIl.getCompound("keybind"));
        }
        llllllllllllllllIllllIIlIlllIIlI.messages = NbtUtils.listFromTag(llllllllllllllllIllllIIlIlllIIIl.getList("messages", 8), NbtElement::asString);
        return llllllllllllllllIllllIIlIlllIIlI;
    }

    public void removeMessage(int llllllllllllllllIllllIIllIIlllll) {
        Macro llllllllllllllllIllllIIllIIllllI;
        llllllllllllllllIllllIIllIIllllI.messages.remove(llllllllllllllllIllllIIllIIlllll);
    }

    @EventHandler(priority=100)
    private void onKey(KeyEvent llllllllllllllllIllllIIllIIlIlll) {
        Macro llllllllllllllllIllllIIllIIllIII;
        if (llllllllllllllllIllllIIllIIlIlll.action != KeyAction.Release && llllllllllllllllIllllIIllIIllIII.onAction(true, llllllllllllllllIllllIIllIIlIlll.key)) {
            llllllllllllllllIllllIIllIIlIlll.cancel();
        }
    }

    public int hashCode() {
        Macro llllllllllllllllIllllIIlIllIIIll;
        return Objects.hash(llllllllllllllllIllllIIlIllIIIll.name);
    }
}

