package utilities;

//import org.jasypt.util.password;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import play.Play;

public class Encryption {
	
	private StandardPBEStringEncryptor encryptor;
	//private static final String filename = "/public/files/mailConfig.txt";
	private String username,
				password;
	
	public Encryption() {			
		// I have encrypted my gmail gbo username and application-specific password
		// Because dev and test mode have a different application secret
		// than production, we need to have 2 differently encrypted username and password
		if(play.Play.isDev() || play.Play.isTest()){
			this.username="O+Hf5eAHPlHc80esZLdItJuWt5YmhpGT";
			this.password = "XdD0tftgEsz+X5SlU6fYb9EJCVfMfPwLjjQGCYr+lGo=";		
		}
		else  { // prod mode
			this.username = "qXteuoPZ47MbZAzJhSSL7uhFHiqV4GCc";
			this.password = "GshNx3NvR/Y7Yjg96hYAtVjlv3l1rmXhpwD/XFN2q48=";
		}
		this.encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(Play.application().configuration().getString("application.secret"));		
	}

			
	public String DecryptPassword(){				
		return encryptor.decrypt(password);	
	}
	
	public String DecryptUsername(){
		return encryptor.decrypt(username);
	}
}
