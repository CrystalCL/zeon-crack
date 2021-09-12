/*
 * Decompiled with CFR 0.151.
 */
package meteordevelopment.orbit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface EventHandler {
    public int priority() default 0;
}

