package services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import javax.persistence.EntityManager;

/**
 * Created by Sara on 10/5/2016.
 */

@Singleton
public class IndexInitializer {

    @Inject
    public IndexInitializer(Provider<EntityManager> emProvider) {
        EntityManager em = emProvider.get();
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        em.close();
    }
}
