package daos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import model.Content;
import model.Qualifier;
import model.Specification;
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
    public Story getStoryById(UUID id){
        EntityManager em = emProvider.get();
        return em.find(Story.class, id);
    }

    @Transactional
    public List<Story> getStories(int n){
        EntityManager em = emProvider.get();
        return em.createQuery("SELECT s FROM Story s", Story.class).setMaxResults(n).getResultList();
    }

    @Transactional
    public List<Story> getStoriesByAuthorId(UUID authorId){
        EntityManager em = emProvider.get();
        return em.createQuery("FROM Story S WHERE S.authorId = :authorId", Story.class).setParameter("authorId", authorId).getResultList();
    }
}
