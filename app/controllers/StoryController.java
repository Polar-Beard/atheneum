package controllers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.inject.Inject;
import com.google.inject.Provider;
import model.Story;

import java.util.List;
import java.util.UUID;

import play.mvc.*;
import play.libs.Json;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonSerialize
public class StoryController extends Controller {
    private Provider<EntityManager> emProvider;

    @Inject
    public StoryController(Provider<EntityManager> emProvider){
        this.emProvider = emProvider;
    }

    public Result addStory() {
        Json json = new Json();
        JsonNode storyAsJson = request().body().asJson();
        if (storyAsJson == null) {
            return badRequest("Expected JSON body");
        }
        Story storyFromJson = json.fromJson(storyAsJson, Story.class);
        EntityManager em = emProvider.get();
        em.getTransaction().begin();
        em.persist(storyFromJson);
        em.getTransaction().commit();
        em.close();
        return ok("Story added to database");

    }

    public Result getStory(UUID storyId) {
        Json json = new Json();
        Story story = null;
        if (storyId == null) {
            return badRequest("Missing parameter [storyId]");
        } else {
            EntityManager em = emProvider.get();
            em.getTransaction().begin();
            story = em.find(Story.class, storyId);
            em.getTransaction().commit();
            em.close();
        }
        return ok(json.toJson(story));
    }

    public Result getStories(int numberOfStories) {
        EntityManager em = emProvider.get();
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
