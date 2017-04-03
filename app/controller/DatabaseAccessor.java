package controller;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;

/**
 * Created by Sara on 4/2/2017.
 */
public class DatabaseAccessor {

    @Inject
    public static Provider<EntityManager> emProvider;

    public EntityManager getEntityManager(){
        return emProvider.get();
    }

    public EntityManager beginTransaction(){
        EntityManager em = emProvider.get();
        em.getTransaction().begin();
        return em;
    }

    public void commitTransaction(EntityManager em){
       em.getTransaction().commit();
    }
}
