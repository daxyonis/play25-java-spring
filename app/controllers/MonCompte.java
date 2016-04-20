package controllers;

import java.util.Date;

import play.mvc.Controller;
import play.mvc.Result;
import security.Authorize;
import security.PermissionLevel;
import security.Secured;
import services.UsagerService;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import models.Usager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MonCompte extends Controller {

    private static UsagerService userService;        
    private static FormFactory playFormFactory;
    private static Form<PasswordChange> pwdForm;

    @Autowired
    public void setUsagerService(UsagerService userService){
	MonCompte.userService = userService;
    }

    @Autowired
    public void setFormFactory(FormFactory ff){
	MonCompte.playFormFactory = ff;
	MonCompte.pwdForm = playFormFactory.form(PasswordChange.class);
    }
    
    /**
     * inner PasswordChange class
     * @author Eva
     *
     */
    public static class PasswordChange {

	@Constraints.Required(message="Requis")        
	public String oldPassword;

	@Constraints.Required(message="Requis")
	@Constraints.MinLength(value=8, message="Au moins 8 caractères")		
	public String newPassword;

	@Constraints.Required(message="Requis")		
	public String newPasswordAgain;

	public String getOldPassword() {
	    return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
	    this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
	    return newPassword;
	}

	public void setNewPassword(String newPassword) {
	    this.newPassword = newPassword;
	}

	public String getNewPasswordAgain() {
	    return newPasswordAgain;
	}

	public void setNewPasswordAgain(String newPasswordAgain) {
	    this.newPasswordAgain = newPasswordAgain;
	}		

    }

    /**
     * GET method
     * Show the account
     * @return  the account management view
     */
    @play.mvc.Security.Authenticated(Secured.class)
    @Authorize(minLevel=PermissionLevel.USER)
    public static Result account(){
	String email = session().get(Secured.CKIE_USERNAME_KEY);
	return ok(views.html.account.account.render(email));
    }

    /**
     * GET method
     * Show the password change page
     * @return  password change view
     */
    @play.mvc.Security.Authenticated(Secured.class)
    @Authorize(minLevel=PermissionLevel.USER)
    public static Result pwd(){
	String email = session().get(Secured.CKIE_USERNAME_KEY);
	return ok(views.html.account.pwd.render(MonCompte.pwdForm, email));

    }

    /**
     * POST method
     * Action method for the password change form
     * @return  the password form view with errors if any; otherwise return to the account view
     */
    @play.mvc.Security.Authenticated(Secured.class)
    @Authorize(minLevel=PermissionLevel.USER)
    public static Result pwdSave(){

	String email = session().get(Secured.CKIE_USERNAME_KEY);
	Form<PasswordChange> pwdForm = MonCompte.pwdForm.bindFromRequest();
	if (pwdForm.hasErrors()) {
	    return badRequest(views.html.account.pwd.render(pwdForm, email)); 	
	}
	PasswordChange pwdChange = pwdForm.get();
	if(!pwdChange.newPassword.equals(pwdChange.newPasswordAgain)){
	    pwdForm.reject("newPasswordAgain","Le nouveau mot de passe n'a pas été entré pareillement les deux fois.");
	    return badRequest(views.html.account.pwd.render(pwdForm, email)); 	
	}
	Usager user = userService.findByEmailAddressAndPassword(email, pwdChange.oldPassword);
	if (user == null) {
	    pwdForm.reject("oldPassword","Mot de passe incorrect.");
	    return badRequest(views.html.account.pwd.render(pwdForm, email)); 
	}
	else {
	    user.setPassword(pwdChange.newPassword);
	    user.setLastModifDate(new Date());
	    user.setLastModifUser(email);
	    if(userService.update(user))
		flash("success","Le mot de passe a été changé.");
	    else
		flash("error", "Le changement n'a pu être effectué.");
	    return redirect("/");
	}

    }

}
