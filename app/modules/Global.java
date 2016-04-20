package modules;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CompletableFuture;

import javax.inject.*;

import play.db.DBApi;
import play.Configuration;
import play.cache.DefaultCacheApi;
import play.inject.ApplicationLifecycle;
import play.Environment;
import play.data.FormFactory;

import configs.AppConfig;
import configs.DataConfig;

@Singleton
public class Global {

    private AnnotationConfigApplicationContext ctx;

    @Inject	
    public Global(DBApi databaseAPI, 
	    DefaultCacheApi cacheApi,
	    Configuration configuration,
	    ApplicationLifecycle lifecycle,
	    Environment environment,
	    FormFactory formFactory,
	    play.DefaultApplication playApp) {

	ctx = new AnnotationConfigApplicationContext();		
	ctx.getBeanFactory().registerSingleton("dataSource", databaseAPI.getDatabase("default").getDataSource());	
	ctx.getBeanFactory().registerSingleton("configuration", configuration);
	ctx.getBeanFactory().registerSingleton("cacheApi", cacheApi);
	ctx.getBeanFactory().registerSingleton("environment", environment);
	ctx.getBeanFactory().registerSingleton("formFactory",formFactory);
	ctx.getBeanFactory().registerSingleton("playApp",playApp);
	ctx.register(DataConfig.class, AppConfig.class);
	ctx.refresh(); 
	ctx.registerShutdownHook();

	lifecycle.addStopHook(() -> {	    
	    ctx.close();
	    return CompletableFuture.completedFuture(null);
	});

    }

    public <A> A getBean(Class<A> clazz) {
	return ctx.getBean(clazz);
    }

}