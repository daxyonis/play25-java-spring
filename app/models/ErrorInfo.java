/**
 * 
 */
package models;

import java.util.Date;

/**
 * @author Eva
 *
 */
public class ErrorInfo {
	
	// Possible values for  
	//public static enum ExceptionTypeEnum {UNKNOWN, RUNTIME, EXCEPTION}
	
	private int id;
	private String appVersion;
	private String usager;
	private Date dateTime;
	private String callingMethod;
	private String message;
	private String localizedMessage;
	//private ExceptionTypeEnum type;
	
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
	 * @return the appVersion
	 */
	public String getAppVersion() {
		return appVersion;
	}
	/**
	 * @param appVersion the appVersion to set
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	/**
	 * @return the user
	 */
	public String getUsager() {
		return usager;
	}
	/**
	 * @param user the user to set
	 */
	public void setUsager(String user) {
		this.usager = user;
	}
	/**
	 * @return the dateTime
	 */
	public Date getDateTime() {
		return dateTime;
	}
	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	/**
	 * @return the callingMethod
	 */
	public String getCallingMethod() {
		return callingMethod;
	}
	/**
	 * @param callingMethod the callingMethod to set
	 */
	public void setCallingMethod(String callingMethod) {
		this.callingMethod = callingMethod;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the localizedMessage
	 */
	public String getLocalizedMessage() {
		return localizedMessage;
	}
	/**
	 * @param localizedMessage the localizedMessage to set
	 */
	public void setLocalizedMessage(String localizedMessage) {
		this.localizedMessage = localizedMessage;
	}
	
//	/**
//	 * 
//	 * @return the type
//	 */
//	public ExceptionTypeEnum getType() {
//		return type;
//	}
//	
//	/**
//	 * 
//	 * @param type	the type to set
//	 */
//	public void setType(ExceptionTypeEnum type) {
//		this.type = type;
//	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ErrorInfo [appVersion=" + appVersion + ", usager=" + usager
				+ ", dateTime=" + dateTime + ", callingMethod=" + callingMethod
				+ ", message=" + message + ", localizedMessage="
				+ localizedMessage + "]";
	}	

	
}
