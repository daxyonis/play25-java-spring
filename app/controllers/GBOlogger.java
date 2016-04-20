/**
 * 
 */
package controllers;

import play.Logger;
import play.mvc.Http.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import models.ErrorInfo;
import services.dao.ErrorInfoDAO;
import services.dao.ErrorInfoDAOImpl;
import utilities.Encryption;
import utilities.GoogleMail;
import security.Secured;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * Customized class for error logging
 * 
 * @author Eva
 * 
 */
@Component
public class GBOlogger {		
	
	public static ErrorInfoDAO errInfoDAO;
	public static final String LOG_EMAIL = "emaciejko@gmail.com";
	public static play.Configuration playConfig;
	
	@Autowired
	public void setErrorInfoDAO(ErrorInfoDAOImpl errInfoDAOImpl) {
	    GBOlogger.errInfoDAO = errInfoDAOImpl;
	}

	@Autowired
	public void setPlayConfiguration(play.Configuration playConf){
	    GBOlogger.playConfig = playConf;
	}
	
	/**
	 * Errors are persisted into database AND an email is sent
	 * @param callingMethod		the name of the method that is logging the error
	 * @param e					the exception as a Throwable object
	 * @param ctx				the play context
	 */
	public static void LogError(String callingMethod, Throwable e, Context ctx) {
		
		//Log the exception		
		String user = "unspecified";
		if(ctx != null){
			user = ctx.session().containsKey(Secured.CKIE_USERNAME_KEY) ? 
				   ctx.session().get(Secured.CKIE_USERNAME_KEY) : "undefined";
		}
		
		ErrorInfo err = new ErrorInfo();
		err.setAppVersion(playConfig.getString("app.version"));
		err.setUsager(user);		
		err.setCallingMethod(callingMethod.substring(0, Math.min(callingMethod.length(), 250)-1));
		err.setDateTime(new java.util.Date()); // now
		err.setMessage(e.getMessage());
		err.setLocalizedMessage(e.getLocalizedMessage());				
		
		// Log the error
		Logger.debug("Error caught by GBOLogger", e);
					
		// Send error info by email to developer
		try{
			sendMail(LOG_EMAIL, err);
		}
		catch(Throwable t){
			Logger.debug("Attempt to send email from GBOLogger failed.");
		}
			
		// Persist in DB
		try{
			errInfoDAO.add(err);
		}
		catch(Throwable t){
			Logger.debug("Attempt to persist in ErrorInfo failed.", t);
		}
		
	}
	
	/**
	 * Send an email with the error info
	 * @param recipientEmail	the email to which send the error info
	 * @param err				an ErrorInfo object containing the relevant error data
	 * @return					true if the email was sent; false otherwise
	 */
	public static boolean sendMail(String recipientEmail, ErrorInfo err){
		try {
			Encryption Enc = new Encryption();
            final String username = Enc.DecryptUsername();            
            final String password = Enc.DecryptPassword();
            GoogleMail.Send(username, password, recipientEmail, "Error Info Log", err.toString());
            return true;
        } catch (AddressException exp) {
        	Logger.info("AddressException in send mail.", exp);        	
        } catch (MessagingException exp) {
        	Logger.info("MessagingException in send mail.", exp);
        }
		return false;
            
	}

}
