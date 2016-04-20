package controllers;

import org.springframework.stereotype.Component;

import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;
import security.Authorize;
import security.PermissionLevel;
import security.Secured;
import views.html.index;

@Component
public class Application extends Controller {

    public static Result index() {
        return ok(index.render(""));    	
    }    
    
    @play.mvc.Security.Authenticated(Secured.class)
    @Authorize(minLevel=PermissionLevel.USER)
    public static Result test(){
    	double i = Math.random();
    	if(i < 0.5){
    		throw new RuntimeException("Test Value was < 0.5 so we threw an exception !!");
    	}
    	return ok("Test Value did not return an exception... try again.");
    }
    
    /**
     * Javascript router action
     * @return
     */
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("jsRoutes",
                routes.javascript.Application.test()
            )
        );
    }
}
