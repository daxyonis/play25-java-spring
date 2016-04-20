/**
 * 
 */
package security;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import modules.Global;
import controllers.GBOlogger;
import services.UsagerService;
import play.Environment;
import play.mvc.Http.Context;
import play.mvc.Result;
//import play.Logger;

/**
 * @author Eva
 *
 */
public class AuthorizedAction extends play.mvc.Action<Authorize> {
	
	private UsagerService userService;	
	private Environment playEnvironment;
	
	@Inject
	public AuthorizedAction(Global global){
		this.userService = global.getBean(UsagerService.class);
		this.playEnvironment = global.getBean(Environment.class);
	}

	/* (non-Javadoc)
	 * @see play.mvc.Action#call(play.mvc.Http.Context)
	 */
	public CompletionStage<Result> call(Context ctx){
		
		try{
			// The action annotated with @Authorize(minLevel=..)
			// requires a user from a certain level of permission 
			// to be executed
			PermissionLevel requiredLevel = configuration.minLevel();
			//Logger.debug("RequiredLevel = " + requiredLevel);
			
			// Get the permission level of user
			PermissionLevel currentUserLevel = userService.getUserLevel(ctx.session().get("email"));
			//Logger.debug("currentUserLevel = " + currentUserLevel);
			
			// Compare both : user level must be greater than or equal to required level
			if(currentUserLevel.compareTo(requiredLevel) >= 0){			
				return delegate.call(ctx);
			}
			
			// Return the promise of a simple result...			
			return CompletableFuture.supplyAsync(() -> {
			    	ctx.flash().put("error", "Accès refusé");
				return redirect("/");
			});
			
			
		}catch(Throwable e){			
			String httpCall = ctx.request().method() + " " + ctx.request().uri();			
			GBOlogger.LogError("AuthorizedAction.call() : " + httpCall, e, ctx);
			
			// In dev or test mode, we want to see the app crash
			if(playEnvironment.isDev() || playEnvironment.isTest()){
				throw e;
			}
			else{
				play.Logger.error("AuthorizedAction.call() : " + httpCall, e);
				return CompletableFuture.completedFuture(internalServerError("An error happened."));
			}
			
		}
	}

}
