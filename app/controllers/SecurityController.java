package controllers;

import models.Usager;
import services.UsagerService;
import security.*;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.i18n.Lang;
import play.mvc.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Eva
 *
 */
@Component
public class SecurityController extends Controller {
		
	private static UsagerService userService;   
	private static FormFactory playFormFactory;
    
	@Autowired
	public void setUsagerService(UsagerService userService){
		SecurityController.userService = userService;
	}
	
	@Autowired
	public void setFormFactory(FormFactory ff){
	    SecurityController.playFormFactory = ff;
	}
		
	/**
     * inner Login class
     * @author Eva
     *
     */
    public static class Login {

        @Constraints.Required
        @Constraints.Email
        public String emailAddress;

        @Constraints.Required
        public String password;

	public String getEmailAddress() {
	    return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
	    this.emailAddress = emailAddress;
	}
	public String getPassword() {
	    return password;
	}
	public void setPassword(String password) {
	    this.password = password;
	}                
    }
    
	/**
	 * GET method 
	 * Displays the login form
	 * @return	the login view
	 */
    public static Result login(){
    	return ok(views.html.login.render(playFormFactory.form(Login.class)));
    }

    /**
     * POST method
     * Authenticates the credentials from login form
     * @return	on form error : again the form with error messages
     * 			unauthorized if credentials are not recognized
     * 			redirects to final URI if credentials are ok
     */
    public static Result authenticate() {
        Form<Login> loginForm = playFormFactory.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(views.html.login.render(loginForm)); 	
        }
        Login login = loginForm.get();
    	
        Usager user = userService.findByEmailAddressAndPassword(login.emailAddress, login.password);

        if (user == null) {
            flash("error","La connexion a échoué.");
            return redirect("/");
        }
        else {
        	if(session().containsKey(Secured.CKIE_USERNAME_KEY)){
        		session().remove(Secured.CKIE_USERNAME_KEY);
        	}
        	session().put(Secured.CKIE_USERNAME_KEY, user.getEmailAddress());
        	if(session().containsKey(Secured.CKIE_REQUEST_URI_KEY)){
        		return redirect(session().get(Secured.CKIE_REQUEST_URI_KEY));
        	}
        	
        	// Set the language for the whole session
        	ctx().changeLang(new Lang(Lang.forCode("fr")));
        	
        	return redirect("/");
            }
    }

    /**
     * POST method
     * Logout
     * @return	redirects to main app page (/)
     */
    @Security.Authenticated(Secured.class)
    public static Result logout() {
    	session().clear();
        return redirect("/");
    }

    
    
}
