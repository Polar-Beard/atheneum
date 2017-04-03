package daos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;


/**
 * Created by Sara on 10/5/2016.
 */
public class UserDAO {

    @Inject private static Provider<EntityManager> emProvider;

    @Transactional
    public boolean addUser(User user){
        //Check to see if this email address is already stored in database
        if(findUserByEmail(user.getEmailAddress()) != null){
            return false;
        } else {
            boolean userAdded;
            EntityManager em = emProvider.get();
            em.getTransaction().begin();
            try {
                em.persist(user);
                em.getTransaction().commit();
                userAdded = true;
            } catch (PersistenceException e) {
                userAdded = false;
            }
            return userAdded;
        }
    }

    @Transactional
    public User getUser(UUID userId){
        EntityManager em = emProvider.get();
        return em.find(User.class, userId);
    }

    @Transactional
    public User findUserByEmail(String emailAddress){
        EntityManager em = emProvider.get();
        return findUserByEmail(em,emailAddress);
    }

    public User findUserByEmail(EntityManager entityManager, String emailAddress){
        try{
            Query query = entityManager.createQuery("FROM User u WHERE u.emailAddress = :emailAddress", User.class);
            query.setParameter("emailAddress", emailAddress);
            List<User> results = query.getResultList();
            if(results.isEmpty()){
                System.out.println("It's empty");
                return null;
            }
            return results.get(0);
        } catch(NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void mergeUser(User user){
        EntityManager em = emProvider.get();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

}
