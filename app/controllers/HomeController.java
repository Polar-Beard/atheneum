package controllers;

import play.mvc.*;

import views.html.*;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private static EntityManagerFactory emf;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        emf = Persistence.createEntityManagerFactory("storyPu");
        EntityManager em = emf.createEntityManager();

        // Create a Story
        Story atlasShrugged = new Story("Atlas Shrugged", "Best story by a lot", "Ayn Rand");

        // Persist the Story
        em.persist(atlasShrugged);

        // Get new EM to ensure data is actually persisted in store and not local cache
        em.close();
        em = emf.createEntityManager();

        // Load it back
        Story loadedStory = em.find(Story.class, atlasShrugged.getStoryId());

        //assertThat(loadedStory, IsNull.notNullValue());
        //assertThat(loadedStory.getTitle()).isEqualTo("Atlas Shrugged");

        em.close();

        return ok(index.render("Your new application is ready."));
    }

}
