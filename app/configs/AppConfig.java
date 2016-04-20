package configs;

import modules.Global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import security.MyCrypto;
import security.AuthorizedAction;

@Configuration
@ComponentScan({"controllers", "services"})
public class AppConfig {
	
	// Note : the config object is already inside the Spring container
	// because was registered in Global constructor
	@Bean	
	public MyCrypto myCrypto(play.Configuration playConfig){
		return new MyCrypto(playConfig);
	}
		
	@Bean
	@Scope("prototype")
	public play.mvc.Security.AuthenticatedAction security$AuthenticatedAction(play.DefaultApplication playApp) {
	    	return playApp.injector().instanceOf(play.mvc.Security.AuthenticatedAction.class);		
	}		
	
	@Bean
	@Scope("prototype")	
	public AuthorizedAction authorizedAction(Global global) {
		return new security.AuthorizedAction(global);
	}

}