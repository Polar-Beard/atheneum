package daos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import model.Author;
import model.Story;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sara on 10/6/2016.
 */
public class StoryDAO{

   @Inject private static Provider<EntityManager> emProvider;

    @Transactional
    public void addStory(String emailAddress, Story story){
        EntityManager em = emProvider.get();
        UserDAO userDAO = new UserDAO();
        UUID authorId = userDAO.getUser(emailAddress).getAuthorId();
        story.setAuthorId(authorId);
        //author.getStories().add(story);
        em.getTransaction().begin();
        em.persist(story);
        em.getTransaction().commit();
    }

    @Transactional
    public Story getStoryById(UUID id){
        EntityManager em = emProvider.get();
        return em.find(Story.class, id);
    }

    @Transactional
    public List<Story> getStories(int n){
        EntityManager em = emProvider.get();
        return em.createQuery("SELECT s FROM Story s", Story.class).setMaxResults(n).getResultList();
    }

    /*@Transactional
    public List<Story> getStoriesByAuthor(Long authorId){
        EntityManager em = emProvider.get();
        Author author = em.find(Author.class, authorId);
        return author.getStories();
    }*/
}
