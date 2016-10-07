package controllers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.inject.Inject;
import com.google.inject.Provider;
import daos.StoryDAO;
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
    private StoryDAO storyDAO;

    public StoryController(){
        storyDAO = new StoryDAO();
    }

    public Result addStory() {
        JsonNode storyAsJson = request().body().asJson();
        if (storyAsJson == null) {
            return badRequest("Expected JSON body");
        }
        Story story = Json.fromJson(storyAsJson, Story.class);
        storyDAO.addStory(story);
        return ok("Story added to database");
    }

    public Result getStory(UUID storyId) {
        if (storyId == null) {
            return badRequest("Missing parameter [storyId]");
        }
        Story story = storyDAO.getStoryById(storyId);
        return ok(Json.toJson(story));
    }

    public Result getStories(int numberOfStories) {
        List<Story> stories = storyDAO.getStories(numberOfStories);
        if (stories.isEmpty()) {
            return badRequest("No stories found in database");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        JsonNode jsonNode = objectMapper.valueToTree(stories);
        return ok(jsonNode);
    }
}
