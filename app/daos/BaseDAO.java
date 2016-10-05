package daos;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Sara on 10/5/2016.
 */
public class BaseDAO {

    private static final String DB_PU = "me-atheneum-pu";
    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory(DB_PU);
        EntityManager em = emf.createEntityManager();
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        em.close();
    }

}
