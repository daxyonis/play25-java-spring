/**
 * 
 */
package security;

import java.lang.annotation.*;
import play.mvc.*;

/**
 * Define annotation for Authorization of actions
 * 
 * For example, a controller action may be annotated to 
 * indicate its level of required autorization by using
 * @@Authorize(minLevel=ADMIN) for admin permission level actions
 * 
 * @author Eva
 *
 */
@With(AuthorizedAction.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
	PermissionLevel minLevel() default PermissionLevel.GUEST;
}
