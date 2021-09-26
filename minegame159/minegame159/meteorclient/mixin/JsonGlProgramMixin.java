/*
 * Decompiled with CFR 0.151.
 */
package minegame159.meteorclient.mixin;

import minegame159.meteorclient.utils.render.Outlines;
import net.minecraft.client.gl.JsonEffectGlShader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={JsonEffectGlShader.class})
public class JsonGlProgramMixin {
    @ModifyVariable(method={"<init>"}, at=@At(value="STORE"))
    private Identifier onInitNewIdentifierModifyVariable(Identifier Identifier2) {
        if (Outlines.loadingOutlineShader && Identifier2.getPath().equals("shaders/program/my_entity_outline.json")) {
            return new Identifier("meteor-client", Identifier2.getPath());
        }
        return Identifier2;
    }

    @ModifyVariable(method={"getShader"}, at=@At(value="STORE"))
    private static Identifier onGetShaderNewIdentifierModifyVariable(Identifier Identifier2) {
        if (Outlines.loadingOutlineShader && Identifier2.getPath().equals("shaders/program/my_entity_sobel.fsh")) {
            return new Identifier("meteor-client", Identifier2.getPath());
        }
        return Identifier2;
    }
}

