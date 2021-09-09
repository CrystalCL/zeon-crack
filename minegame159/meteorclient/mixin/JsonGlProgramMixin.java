/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gl.JsonEffectGlShader
 *  net.minecraft.util.Identifier
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
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
    private Identifier onInitNewIdentifierModifyVariable(Identifier identifier) {
        if (Outlines.loadingOutlineShader && identifier.getPath().equals("shaders/program/my_entity_outline.json")) {
            return new Identifier("meteor-client", identifier.getPath());
        }
        return identifier;
    }

    @ModifyVariable(method={"getShader"}, at=@At(value="STORE"))
    private static Identifier onGetShaderNewIdentifierModifyVariable(Identifier identifier) {
        if (Outlines.loadingOutlineShader && identifier.getPath().equals("shaders/program/my_entity_sobel.fsh")) {
            return new Identifier("meteor-client", identifier.getPath());
        }
        return identifier;
    }
}

