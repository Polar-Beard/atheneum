package daos;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import model.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sara on 10/6/2016.
 */
public class StoryDAO{

   @Inject public static Provider<EntityManager> emProvider;

    @Transactional
    public void addStory(Story story){
        EntityManager em = emProvider.get();
        em.getTransaction().begin();
        em.persist(story);
        for(Content content: story.getContents()){
            content.setStory(story);
            em.persist(content);
            for(Qualifier qualifier: content.getQualifiers()){
                qualifier.setContent(content);
                em.persist(qualifier);
                for(Specification specification: qualifier.getSpecifications()){
                    specification.setQualifier(qualifier);
                    em.persist(specification);
                }
            }
        }
        em.getTransaction().commit();
    }

    @Transactional
    public Story getStoryById(UUID id, User user){
        EntityManager em = emProvider.get();
        Story story = em.find(Story.class, id);
        //em.getTransaction().begin();
        //em.persist(new StoryRecord(user, story));
        //em.getTransaction().commit();
        return story;
    }

    @Transactional
    public List<Story> getStoriesByQuery(String query, int amount){
        EntityManager em = emProvider.get();
        return em.createQuery(query, Story.class).setMaxResults(amount).getResultList();
    }

    @Transactional
    public List<Story> getStories(int amount){
        return getStoriesByQuery("SELECT s FROM Story s", amount);
    }

    @Transactional
    public List<Story> getStoriesByAuthorId(UUID authorId){
        EntityManager em = emProvider.get();
        return em.createQuery("FROM Story S WHERE S.authorId = :authorId", Story.class).setParameter("authorId", authorId).getResultList();
    }
}
