package daos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import model.Category;
import model.Story;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Sara on 3/28/2017.
 */
public class CategoryDAO {

    @Inject private static Provider<EntityManager> emProvider;

    @Transactional
    public List<Category> getCategories(int number){
        EntityManager entityManager = emProvider.get();
        return entityManager
                .createQuery("FROM Category c WHERE C.order <= :order", Category.class)
                .setParameter("order", number)
                .getResultList();
    }

}
