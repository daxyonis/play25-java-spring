package controllers;

import java.util.Date;
import java.util.List;

import models.Usager;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import play.data.FormFactory;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import security.Authorize;
import security.PermissionLevel;
import security.Secured;
import services.UsagerService;
import views.html.usager.details;
import views.html.usager.list;

@Component
@play.mvc.Security.Authenticated(Secured.class)
@Authorize(minLevel=PermissionLevel.ADMIN)
public class UserController extends Controller {
    
    	
    	private static FormFactory playFormFactory;

	private static Form<Usager> userForm;
	private static UsagerService userService;        
    
	@Autowired
	public void setUsagerService(UsagerService userService){
		UserController.userService = userService;
	}	
	
	@Autowired
	public void setFormFactory(FormFactory ff){
	    UserController.playFormFactory = ff;
	    UserController.userForm = playFormFactory.form(Usager.class);
	}
    
	/**
     * GET method
     * Displays the users list
     * @return  the user list view
     */    
    public static Result ulist(){
	ctx().changeLang("fr");
    	List<Usager> usagers = userService.findAll();
    	return ok(list.render(usagers));
    }

    /**
     * GET method
     * Displays the empty user form 
     * @return  the user form view
     */    
    public static Result newUser(){
    	return ok(details.render(userForm));
    }

    /**
     * GET method
     * Displays the user form filled with a given user
     * @param id  the id of user
     * @return  the filled user form view
     */    
    public static Result edit(int id){
    	
    	final Usager user = userService.findById(id);
    	
    	if(user != null){
    		Form<Usager> filledForm = userForm.fill(user);
    		return ok(details.render(filledForm));
    	}
    	else{
    		return notFound("Cet usager n'existe pas.");
    	}
    }
    
        
    /**
     * POST method
     * Action method for the user form:
     * adds a new user or updates an existing one with form values
     * @return  the user form view with errors if any; otherwise return to the user list view
     */    
    public static Result save(){
    	Form<Usager> boundForm = userForm.bindFromRequest();
    	
    	if(boundForm.hasErrors()){
			flash("error", "Erreur dans le formulaire.");
			return badRequest(details.render(boundForm));
    	}
    	
    	Usager user = boundForm.get();    	    	
    	
    	if(user.getId() == 0){
    		// New user
    		user.setCreationDate(new Date());
    		user.setLastModifDate(user.getCreationDate());
    		user.setLastModifUser(session().get(security.Secured.CKIE_USERNAME_KEY));
    		userService.add(user);
    	}
    	else{
    		user.setLastModifDate(new Date());
    		user.setLastModifUser(session().get(security.Secured.CKIE_USERNAME_KEY));
    		userService.update(user);
    	}
    	
    	return redirect(routes.UserController.ulist());
    }
    
    /**
     * DELETE method
     * Removes a given user
     * @param id  id of user to delete
     * @return  the user list view
     */
    public static Result remove(int id) {
    	userService.remove(id);
    	return redirect(routes.UserController.ulist());
    }
}
