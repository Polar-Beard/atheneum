package daos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import model.StoryRecord;

import javax.persistence.EntityManager;

/**
 * Created by Sara on 3/31/2017.
 */
public class StoryRecordDAO {

    @Inject private static Provider<EntityManager> emProvider;

    @Transactional
    public void addStoryRecord(StoryRecord storyRecord){
        EntityManager em = StoryDAO.emProvider.get();
        em.getTransaction().begin();
        em.persist(storyRecord);
        em.merge(storyRecord.getUser());
        em.getTransaction().commit();
    }
}
