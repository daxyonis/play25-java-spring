/**
 * 
 */
package security;

import org.springframework.stereotype.Component;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;
import controllers.routes;

/**
 * @author Eva
 *
 */
@Component
public class Secured extends Authenticator {	
	
	public static final String CKIE_REQUEST_URI_KEY = "finalURI";
	public static final String CKIE_USERNAME_KEY = "email";	

	/* (non-Javadoc)
	 * @see play.mvc.Security.Authenticator#getUsername(play.mvc.Http.Context)
	 */
	@Override
	public String getUsername(Context ctx) {
		
		//Save current request URI into session
		if(ctx.session().containsKey(CKIE_REQUEST_URI_KEY)){
			ctx.session().remove(CKIE_REQUEST_URI_KEY);
		}		
		ctx.session().put(CKIE_REQUEST_URI_KEY, ctx.request().uri());
		
		return ctx.session().get(CKIE_USERNAME_KEY);
	}

	/* (non-Javadoc)
	 * @see play.mvc.Security.Authenticator#onUnauthorized(play.mvc.Http.Context)
	 */
	@Override
	public Result onUnauthorized(Context ctx) {		
		return redirect(routes.SecurityController.login());
	}

}
