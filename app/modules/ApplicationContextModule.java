package modules;

import com.google.inject.AbstractModule;

public class ApplicationContextModule extends AbstractModule {

    @Override
    protected void configure() {
        bind( Global.class ).asEagerSingleton();
    }

}