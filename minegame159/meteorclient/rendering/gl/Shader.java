/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  net.minecraft.util.Identifier
 *  net.minecraft.client.MinecraftClient
 *  org.lwjgl.opengl.GL30C
 */
package minegame159.meteorclient.rendering.gl;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import minegame159.meteorclient.MeteorClient;
import minegame159.meteorclient.utils.render.color.Color;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.opengl.GL30C;

public class Shader {
    private /* synthetic */ int id;
    private final /* synthetic */ Object2IntMap<String> locations;

    public void bind() {
        Shader lIlIIIllIIlllII;
        GL30C.glUseProgram((int)lIlIIIllIIlllII.id);
    }

    public void set(String lIlIIIlIlIlllII, Color lIlIIIlIlIllIII) {
        Shader lIlIIIlIlIlllIl;
        lIlIIIlIlIlllIl.set(lIlIIIlIlIlllII, (float)lIlIIIlIlIllIII.r / 255.0f, (float)lIlIIIlIlIllIII.g / 255.0f, (float)lIlIIIlIlIllIII.b / 255.0f, (float)lIlIIIlIlIllIII.a / 255.0f);
    }

    public void unbind() {
        GL30C.glUseProgram((int)0);
    }

    private int getLocation(String lIlIIIllIIlIlII) {
        Shader lIlIIIllIIlIIll;
        if (!lIlIIIllIIlIIll.locations.containsKey((Object)lIlIIIllIIlIlII)) {
            int lIlIIIllIIlIllI = GL30C.glGetUniformLocation((int)lIlIIIllIIlIIll.id, (CharSequence)lIlIIIllIIlIlII);
            lIlIIIllIIlIIll.locations.put((Object)lIlIIIllIIlIlII, lIlIIIllIIlIllI);
            return lIlIIIllIIlIllI;
        }
        return lIlIIIllIIlIIll.locations.getInt((Object)lIlIIIllIIlIlII);
    }

    public void set(String lIlIIIllIIIllII, int lIlIIIllIIIlIII) {
        Shader lIlIIIllIIIlIlI;
        GL30C.glUniform1i((int)lIlIIIllIIIlIlI.getLocation(lIlIIIllIIIllII), (int)lIlIIIllIIIlIII);
    }

    public Shader(String lIlIIIllIlIIIll, String lIlIIIllIlIlIII) {
        boolean lIlIIIllIlIIllI;
        Shader lIlIIIllIlIlIlI;
        lIlIIIllIlIlIlI.locations = new Object2IntOpenHashMap();
        int lIlIIIllIlIIlll = GL30C.glCreateShader((int)35633);
        GL30C.glShaderSource((int)lIlIIIllIlIIlll, (CharSequence)lIlIIIllIlIlIlI.read(lIlIIIllIlIIIll));
        GL30C.glCompileShader((int)lIlIIIllIlIIlll);
        boolean bl = lIlIIIllIlIIllI = GL30C.glGetShaderi((int)lIlIIIllIlIIlll, (int)35713) == 1;
        if (!lIlIIIllIlIIllI) {
            String lIlIIIllIlIllII = GL30C.glGetShaderInfoLog((int)lIlIIIllIlIIlll);
            MeteorClient.LOG.error("Failed to compile vertex shader ({}):\n{}", (Object)lIlIIIllIlIIIll, (Object)lIlIIIllIlIllII);
            return;
        }
        int lIlIIIllIlIIlIl = GL30C.glCreateShader((int)35632);
        GL30C.glShaderSource((int)lIlIIIllIlIIlIl, (CharSequence)lIlIIIllIlIlIlI.read(lIlIIIllIlIlIII));
        GL30C.glCompileShader((int)lIlIIIllIlIIlIl);
        boolean bl2 = lIlIIIllIlIIllI = GL30C.glGetShaderi((int)lIlIIIllIlIIlIl, (int)35713) == 1;
        if (!lIlIIIllIlIIllI) {
            String lIlIIIllIlIlIll = GL30C.glGetShaderInfoLog((int)lIlIIIllIlIIlIl);
            MeteorClient.LOG.error("Failed to compile fragment shader ({}):\n{}", (Object)lIlIIIllIlIlIII, (Object)lIlIIIllIlIlIll);
            return;
        }
        lIlIIIllIlIlIlI.id = GL30C.glCreateProgram();
        GL30C.glAttachShader((int)lIlIIIllIlIlIlI.id, (int)lIlIIIllIlIIlll);
        GL30C.glAttachShader((int)lIlIIIllIlIlIlI.id, (int)lIlIIIllIlIIlIl);
        GL30C.glLinkProgram((int)lIlIIIllIlIlIlI.id);
        GL30C.glDeleteShader((int)lIlIIIllIlIIlll);
        GL30C.glDeleteShader((int)lIlIIIllIlIIlIl);
    }

    public void set(String lIlIIIlIllllIIl, float lIlIIIlIlllIlII, float lIlIIIlIlllIIll) {
        Shader lIlIIIlIlllIllI;
        GL30C.glUniform2f((int)lIlIIIlIlllIllI.getLocation(lIlIIIlIllllIIl), (float)lIlIIIlIlllIlII, (float)lIlIIIlIlllIIll);
    }

    private String read(String lIlIIIlIlIIllII) {
        try {
            InputStream lIlIIIlIlIlIIII = MinecraftClient.getInstance().getResourceManager().getResource(new Identifier("meteor-client", lIlIIIlIlIIllII)).getInputStream();
            StringBuilder lIlIIIlIlIIllll = new StringBuilder();
            try (BufferedReader lIlIIIlIlIlIIIl = new BufferedReader(new InputStreamReader(lIlIIIlIlIlIIII));){
                lIlIIIlIlIlIIIl.lines().forEach(lIlIIIlIlIIIIlI -> lIlIIIlIlIIllll.append((String)lIlIIIlIlIIIIlI).append('\n'));
            }
            return String.valueOf(lIlIIIlIlIIllll);
        }
        catch (IOException lIlIIIlIlIIlllI) {
            lIlIIIlIlIIlllI.printStackTrace();
            return "";
        }
    }

    public void set(String lIlIIIllIIIIIll, float lIlIIIllIIIIIlI) {
        Shader lIlIIIllIIIIIIl;
        GL30C.glUniform1f((int)lIlIIIllIIIIIIl.getLocation(lIlIIIllIIIIIll), (float)lIlIIIllIIIIIlI);
    }

    public void set(String lIlIIIlIllIIlIl, float lIlIIIlIllIIlII, float lIlIIIlIllIIIll, float lIlIIIlIllIlIII, float lIlIIIlIllIIIIl) {
        Shader lIlIIIlIllIllII;
        GL30C.glUniform4f((int)lIlIIIlIllIllII.getLocation(lIlIIIlIllIIlIl), (float)lIlIIIlIllIIlII, (float)lIlIIIlIllIIIll, (float)lIlIIIlIllIlIII, (float)lIlIIIlIllIIIIl);
    }
}

