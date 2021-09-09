/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL30C
 */
package minegame159.meteorclient.rendering.gl;

import org.lwjgl.opengl.GL30C;

public class Mesh {
    private final /* synthetic */ int vbo;
    private final /* synthetic */ int vao;
    private final /* synthetic */ int ibo;
    private final /* synthetic */ int indicesCount;

    public void render() {
        Mesh lllllllllllllllllIllIlIlllIIIlIl;
        lllllllllllllllllIllIlIlllIIIlIl.bind();
        lllllllllllllllllIllIlIlllIIIlIl.renderMesh();
        lllllllllllllllllIllIlIlllIIIlIl.unbind();
    }

    public Mesh(float[] lllllllllllllllllIllIlIlllIlIlIl, int[] lllllllllllllllllIllIlIlllIllIlI, int ... lllllllllllllllllIllIlIlllIlIIll) {
        Mesh lllllllllllllllllIllIlIlllIlllII;
        lllllllllllllllllIllIlIlllIlllII.vao = GL30C.glGenVertexArrays();
        GL30C.glBindVertexArray((int)lllllllllllllllllIllIlIlllIlllII.vao);
        lllllllllllllllllIllIlIlllIlllII.vbo = GL30C.glGenBuffers();
        GL30C.glBindBuffer((int)34962, (int)lllllllllllllllllIllIlIlllIlllII.vbo);
        GL30C.glBufferData((int)34962, (float[])lllllllllllllllllIllIlIlllIlIlIl, (int)35044);
        int lllllllllllllllllIllIlIlllIllIII = 0;
        for (int lllllllllllllllllIllIlIlllIllllI : lllllllllllllllllIllIlIlllIlIIll) {
            lllllllllllllllllIllIlIlllIllIII += lllllllllllllllllIllIlIlllIllllI * 4;
        }
        int lllllllllllllllllIllIlIlllIlIlll = 0;
        for (int lllllllllllllllllIllIlIlllIlllIl = 0; lllllllllllllllllIllIlIlllIlllIl < lllllllllllllllllIllIlIlllIlIIll.length; ++lllllllllllllllllIllIlIlllIlllIl) {
            GL30C.glEnableVertexAttribArray((int)lllllllllllllllllIllIlIlllIlllIl);
            GL30C.glVertexAttribPointer((int)lllllllllllllllllIllIlIlllIlllIl, (int)lllllllllllllllllIllIlIlllIlIIll[lllllllllllllllllIllIlIlllIlllIl], (int)5126, (boolean)false, (int)lllllllllllllllllIllIlIlllIllIII, (long)lllllllllllllllllIllIlIlllIlIlll);
            lllllllllllllllllIllIlIlllIlIlll += lllllllllllllllllIllIlIlllIlIIll[lllllllllllllllllIllIlIlllIlllIl] * 4;
        }
        lllllllllllllllllIllIlIlllIlllII.ibo = GL30C.glGenBuffers();
        GL30C.glBindBuffer((int)34963, (int)lllllllllllllllllIllIlIlllIlllII.ibo);
        GL30C.glBufferData((int)34963, (int[])lllllllllllllllllIllIlIlllIllIlI, (int)35044);
        lllllllllllllllllIllIlIlllIlllII.indicesCount = lllllllllllllllllIllIlIlllIllIlI.length;
        GL30C.glBindVertexArray((int)0);
        GL30C.glBindBuffer((int)34962, (int)0);
        GL30C.glBindBuffer((int)34963, (int)0);
    }

    public void renderMesh() {
        Mesh lllllllllllllllllIllIlIlllIIlIII;
        GL30C.glDrawElements((int)4, (int)lllllllllllllllllIllIlIlllIIlIII.indicesCount, (int)5125, (long)0L);
    }

    public void unbind() {
        GL30C.glBindVertexArray((int)0);
        GL30C.glBindBuffer((int)34963, (int)0);
    }

    public void bind() {
        Mesh lllllllllllllllllIllIlIlllIIlIll;
        GL30C.glBindVertexArray((int)lllllllllllllllllIllIlIlllIIlIll.vao);
        GL30C.glBindBuffer((int)34963, (int)lllllllllllllllllIllIlIlllIIlIll.ibo);
    }
}

