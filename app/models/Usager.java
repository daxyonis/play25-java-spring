/**
 * 
 */
package models;

import play.data.validation.Constraints;

import java.util.Date;

import security.PermissionLevel;
import security.MyCrypto;

/**
 * Defines application user information that is needed
 * for authentication and authorization.
 *  
 * @author Eva
 *
 */
public class Usager {

    //*******************************
    //* Class members
    //*******************************
    private int id;	

    @Constraints.Required
    @Constraints.MaxLength(256)    
    @Constraints.Email
    private String emailAddress;	// length=256, unique, non-null
    
    @SuppressWarnings("unused")    
    private String password;		// only for the form

    private byte[] shaPassword;		// length=64, non-null      

    @Constraints.Required
    @Constraints.MinLength(2)
    @Constraints.MaxLength(256)
    private String fullName;

    private Date creationDate;

    @Constraints.Required
    private PermissionLevel level;

    private byte[] salt;			// length=32
    private Date lastModifDate;
    private String lastModifUser;

    //*******************************
    //* Class methods
    //*******************************

    public Usager() {
	this.id = 0;
	this.creationDate = new Date();
	this.lastModifDate = this.creationDate;
    }        

    // security: never return clear text password
    public String getPassword(){
	return new String("");
    }

    // security: never set clear text password
    public void setPassword(String password) {    	
	salt = MyCrypto.generateSalt();    	
	shaPassword =  MyCrypto.encryptHMAC(salt, password);                
    }

    public String getEmailAddress() {
	return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
	this.emailAddress = emailAddress.toLowerCase();
    }    


    /**
     * @return the id
     */
    public int getId() {
	return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
	return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
	this.fullName = fullName;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
	return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
	this.creationDate = creationDate;
    }

    /**
     * @return the shaPassword
     */
    public byte[] getShaPassword() {
	return shaPassword;
    }

    /**
     * @param shaPassword the shaPassword to set
     */
    public void setShaPassword(byte[] shaPassword) {
	this.shaPassword = shaPassword;
    }

    /**
     * @return the level
     */
    public PermissionLevel getLevel() {
	return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(PermissionLevel level) {
	this.level = level;
    }

    public byte[] getSalt() {
	return salt;
    }

    public void setSalt(byte[] salt) {
	this.salt = salt;
    }

    public Date getLastModifDate() {
	return lastModifDate;
    }

    public void setLastModifDate(Date lastModifDate) {
	this.lastModifDate = lastModifDate;
    }

    public String getLastModifUser() {
	return lastModifUser;
    }

    public void setLastModifUser(String lastModifUser) {
	this.lastModifUser = lastModifUser;
    }


}
