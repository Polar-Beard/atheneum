package daos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
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
    public void addStory(Story story){
        EntityManager em = emProvider.get();
        em.getTransaction().begin();
        em.persist(story);
        em.getTransaction().commit();
    }

    @Transactional
    public Story getStoryById(UUID id){
        EntityManager em = emProvider.get();
        Story story = em.find(Story.class, id);
        return story;
    }

    @Transactional
    public List<Story> getStories(int n){
        EntityManager em = emProvider.get();
        List<Story> stories = em.createQuery("SELECT s FROM Story s", Story.class).setMaxResults(n).getResultList();
        return stories;
    }
}
