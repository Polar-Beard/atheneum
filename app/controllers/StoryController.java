package controllers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import model.Story;

import java.util.List;
import java.util.UUID;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import play.mvc.*;
import play.libs.Json;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonSerialize
public class StoryController extends Controller {
    private static final String DB_PU = "me-atheneum-pu";
    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory(DB_PU);
        EntityManager em = emf.createEntityManager();
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        em.close();
    }

    public Result addStory() {
        Json json = new Json();
        Story storyFromJson = null;
        JsonNode storyAsJson = request().body().asJson();
        if (storyAsJson == null) {
            return badRequest("Expected JSON body");
        } else {
            storyFromJson = json.fromJson(storyAsJson, Story.class);
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(storyFromJson);
            em.getTransaction().commit();
            em.close();
            return ok("Story added to database");
        }
    }

    public Result getStory(UUID storyId) {
        Json json = new Json();
        if (storyId == null) {
            return badRequest("Missing parameter [storyId]");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Story story = em.find(Story.class, storyId);
        em.getTransaction().commit();
        em.close();
        return ok(json.toJson(story));
    }

    public Result getStories(int numberOfStories) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Story> stories = em.createQuery("SELECT s FROM Story s", Story.class).setMaxResults(numberOfStories).getResultList();
        Result result;
        if (!stories.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            JsonNode jsonNode = objectMapper.valueToTree(stories);
            result = ok(jsonNode);
        } else {
            result = badRequest("No stories found in database");
        }
        em.getTransaction().commit();
        em.close();
        return result;

    }
}
