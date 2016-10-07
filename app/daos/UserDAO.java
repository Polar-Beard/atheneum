package daos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import model.User;

import javax.persistence.EntityManager;

/**
 * Created by Sara on 10/5/2016.
 */
public class UserDAO {

    @Inject private static Provider<EntityManager> emProvider;

    @Transactional
    public boolean addUser(User user){
        EntityManager em = emProvider.get();

        //Add user so long as the email address is not already stored in database
        boolean userAdded = false;
        if(em.find(User.class, user.getEmailAddress()) == null) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            userAdded = true;
        }
        return userAdded;
    }

    @Transactional
    public User getUser(String emailAddress){
        EntityManager em = emProvider.get();
        return em.find(User.class, emailAddress);
    }

}
