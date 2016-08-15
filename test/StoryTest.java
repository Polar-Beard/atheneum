import static org.junit.Assert.*;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.hamcrest.core.IsNull;

import model.Story;

public class StoryTest{

  private static EntityManagerFactory emf;

  @BeforeClass
  public static void setUpEntityManagerFactory(){
    emf = Persistence.createEntityManagerFactory("storyPu");
  }

  @AfterClass
  public static void closeEntityManagerFactory(){
    emf.close();
  }

  @Test
  public void canPersistAndLoadStory(){
    EntityManager em = emf.createEntityManager();

    em.getTransaction().begin();

    //Create a Story
    Story atlasShrugged = new Story("Atlas Shrugged", "Best story ever", "Ayn Rand");

    //Persist the Story
    em.persist(atlasShrugged);
    em.getTransaction().commit();

    //Get new EM to ensure data is actually persisted in store and not local cache
    em.close();
    em = emf.createEntityManager();

    //Load it back
    em.getTransaction().begin();
    Story loadedStory = em.find(Story.class, atlasShrugged.getStoryId());

    assertThat(loadedStory, IsNull.notNullValue());
    //assertThat(loadedStory.getTitle()).isEqualTo("Atlas Shrugged");

    em.getTransaction().commit();

    em.close();
  }
}
