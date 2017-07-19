package mechanics.system.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Created by Alex Storm on 02.06.2017.
 */
@Repeatable(Roles.class)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD, TYPE, CONSTRUCTOR})
public @interface Role {
    String role() default "[[[unassigned]]]";

    String roleContains() default "[[[unassigned]]]";

    boolean expectedStatus() default true;

    boolean runnable() default true;
}
