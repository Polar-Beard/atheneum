/**
 * Created by Sara on 10/5/2016.
 */

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

import daos.StoryDAO;
import daos.UserDAO;
import services.IndexInitializer;


/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure(){
        install(new JpaPersistModule("me-atheneum-pu"));
        bind(JPAInitializer.class).asEagerSingleton();
        bind(IndexInitializer.class).asEagerSingleton();
        requestStaticInjection(StoryDAO.class);
        requestStaticInjection(UserDAO.class);
    }

    @Singleton
    public static class JPAInitializer {

        @Inject
        public JPAInitializer(final PersistService service){
            service.start();
        }
    }
}
