package daos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import model.Author;
import model.Story;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Sara on 10/6/2016.
 */
public class StoryDAO{

   @Inject private static Provider<EntityManager> emProvider;

    @Transactional
    public void addStory(String emailAddress, Story story){
        EntityManager em = emProvider.get();
        UserDAO userDAO = new UserDAO();
        Author author = userDAO.getUser(emailAddress).getAuthor();
        story.setAuthorId(author.getAuthorId());
        //author.getStories().add(story);
        em.getTransaction().begin();
        em.persist(story);
        em.getTransaction().commit();
    }

    @Transactional
    public Story getStoryById(Long id){
        EntityManager em = emProvider.get();
        return em.find(Story.class, id);
    }

    @Transactional
    public List<Story> getStories(int n){
        EntityManager em = emProvider.get();
        em.getTransaction().begin();
        List<Story> stories = em.createQuery("SELECT s FROM Story s", Story.class).setMaxResults(n).getResultList();
        em.getTransaction().commit();
        return stories;
    }

    /*@Transactional
    public List<Story> getStoriesByAuthor(Long authorId){
        EntityManager em = emProvider.get();
        Author author = em.find(Author.class, authorId);
        return author.getStories();
    }*/
}
