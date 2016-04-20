/**
 * 
 */
package services.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.ErrorInfo;

/**
 * @author Eva
 *
 */
@Repository
public class ErrorInfoDAOImpl implements ErrorInfoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplateObject;
	
	/* (non-Javadoc)
	 * @see services.simple.ErrorInfoDAO#add(models.ErrorInfo)
	 */
	public void add(ErrorInfo err) {
		
		String SQL = "INSERT INTO ErrorInfo (AppVersion, Usager, DateTime, CallingMethod, Message, LocalizedMessage) values " + 
				  "(?, ?, ?, ?, ?, ?)";

		jdbcTemplateObject.update( SQL,
					  err.getAppVersion(), err.getUsager(), err.getDateTime(), err.getCallingMethod(),
					  err.getMessage(), err.getLocalizedMessage());
		return;

	}

}
