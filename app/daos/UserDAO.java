package daos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.UUID;


/**
 * Created by Sara on 10/5/2016.
 */
public class UserDAO {

    @Inject private static Provider<EntityManager> emProvider;

    @Transactional
    public boolean addUser(User user){
        boolean userAdded;
        EntityManager em = emProvider.get();
        em.getTransaction().begin();
        try {
            em.persist(user);
            em.getTransaction().commit();
            userAdded = true;
        } catch(PersistenceException e){
            userAdded = false;
        }
        return userAdded;
    }

    @Transactional
    public User getUser(String emailAddress){
        EntityManager em = emProvider.get();
        return em.find(User.class, emailAddress);
    }

    public User getUser(UUID userId){
        EntityManager em = emProvider.get();
        return em.find(User.class, userId);
    }

}
