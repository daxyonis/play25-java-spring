/**
 * 
 */
package services.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import models.Usager;
import security.MyCrypto;
import security.PermissionLevel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @author Eva
 *
 */
@Repository
public class UsagerDAOImpl implements UsagerDAO {
	
	// The name of Table in the DB
	private final String TableName = "Usager";

	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public UsagerDAOImpl(JdbcTemplate jdbcTempl){
		this.jdbcTemplateObject = jdbcTempl;		
	}
	
	private class UsagerMapper implements RowMapper<Usager>{
		public Usager mapRow(ResultSet rs, int rowNum) throws SQLException{
			Usager user = new Usager();
			user.setId(rs.getInt("Id"));
			user.setEmailAddress(rs.getNString("EmailAddress"));			
			user.setShaPassword(rs.getBytes("ShaPassword"));
			user.setFullName(rs.getNString("FullName"));
			user.setCreationDate(rs.getDate("CreationDate"));	
			user.setLevel(Enum.valueOf(PermissionLevel.class, rs.getNString("PermissionLevel")));			
			user.setLastModifDate(rs.getDate("LastModifDate"));
			user.setLastModifUser(rs.getNString("LastModifUser"));
			user.setSalt(rs.getBytes("Salt"));
			return user;
		}
	}
	
	
	@Override
	public List<Usager> findAll() {
		String SQL = "SELECT * FROM " + TableName + " ORDER BY Id";
		List<Usager> usagers = jdbcTemplateObject.query(SQL, new UsagerMapper());
		return usagers;
	}

	public Usager findByEmailAddressAndPassword(String emailAddress, String password){		
		Usager user = findByEmail(emailAddress);			
		
		if(user != null){
			byte[] encryptedPassword = MyCrypto.encryptHMAC(user.getSalt(), password);
					
			if(Arrays.equals(user.getShaPassword(), encryptedPassword)){
				return user;
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	public PermissionLevel getUserLevel(String emailAddress){
		Usager user = findByEmail(emailAddress);
		if(user != null){
			return user.getLevel();
		}
		else{
			return null;
		}
		
	}
	
	private Usager findByEmail(String emailAddress){
		String SQL = "SELECT * FROM " + TableName + " WHERE EmailAddress = ?";
		
		Usager user;
		try{
			user = jdbcTemplateObject.queryForObject(SQL,		
						new Object[]{emailAddress.toLowerCase()}, new UsagerMapper());
		}
		catch(EmptyResultDataAccessException e){
			user = null;
		}
		return user;		
	}

	@Override
	public Usager findById(int id) {
		String SQL = "SELECT * FROM " + TableName + " WHERE Id = ?";
		Usager user;
		try{
			user = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new UsagerMapper());
		}
		catch(EmptyResultDataAccessException e){
			user = null;
		}
		return user;
	}	

	@Override
	public boolean add(Usager user) {
		String SQL = "INSERT INTO " + TableName + " "
				   + "(EmailAddress, ShaPassword, FullName, CreationDate, PermissionLevel,"
				   + "Salt, LastModifDate, LastModifUser) "
				   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		int numrows = jdbcTemplateObject.update( SQL, user.getEmailAddress(), user.getShaPassword(), 
						user.getFullName(),user.getCreationDate(), user.getLevel().name(),
						user.getSalt(), user.getLastModifDate(),user.getLastModifUser());
		
		return(numrows == 1);
	}

	@Override
	public boolean update(Usager user) {
		// We DO NOT update : Id, CreationDate
		String SQL = "UPDATE " + TableName 
				   + " SET EmailAddress=?, ShaPassword = ?, FullName = ?, PermissionLevel = ?,"
				   + " Salt=?, LastModifDate=?, LastModifUser=? "
				   + "WHERE Id = ?";
		
		int numrows = jdbcTemplateObject.update(SQL, user.getEmailAddress(), user.getShaPassword(), 
				user.getFullName(),user.getLevel().name(), user.getSalt(), user.getLastModifDate(),
				user.getLastModifUser(), user.getId());
		
		return (numrows == 1);
	}

	@Override
	public boolean remove(int id) {
		String SQL = "DELETE " + TableName + " WHERE Id=?";
		
		int numrows = jdbcTemplateObject.update(SQL, id);
		
		return (numrows == 1);
	}

}
