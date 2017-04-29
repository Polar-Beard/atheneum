package controller;

import actions.BasicAuth;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.google.inject.Inject;
import daos.StoryDAO;
import daos.StoryRecordDAO;
import daos.UserDAO;

import model.Story;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import model.StoryRecord;
import model.User;
import play.libs.Akka;
import play.mvc.*;
import play.libs.Json;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import scala.concurrent.duration.FiniteDuration;
import services.HttpAuthorizationParser;
import javax.persistence.EntityManager;

@JsonSerialize
public class StoryController extends Controller {

    @Inject ActorSystem actorSystem;
    private static ObjectMapper objectMapper;
    private StoryDAO storyDAO;
    private UserDAO userDAO;
    private StoryRecordDAO recordDAO;
    private DatabaseAccessor databaseAccessor = new DatabaseAccessor();

    static{
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public StoryController(){
        storyDAO = new StoryDAO();
        userDAO = new UserDAO();
        recordDAO = new StoryRecordDAO();
    }

    @BasicAuth
    public Result addStory() {
        String userEmail = getEmailFromHeader();
        JsonNode storyAsJson = request().body().asJson();
        if (storyAsJson == null) {
            return badRequest("Expected JSON body");
        }
        Story story = Json.fromJson(storyAsJson, Story.class);
        User author = userDAO.findUserByEmail(databaseAccessor.getEntityManager(),userEmail);
        story.setAuthorId(author.getUserId());
        storyDAO.addStory(story);
        return ok("Story added to database");
    }

    @BasicAuth
    public Result getStory(UUID storyId) {
        if (storyId == null) {
            return badRequest("Missing parameter [storyId]");
        }
        String userEmail = getEmailFromHeader();

        EntityManager entityManager = databaseAccessor.beginTransaction();

        User user1 = userDAO.findUserByEmail(userEmail);

        User user = entityManager.find(User.class, user1.getUserId());
        Story story = entityManager.find(Story.class, storyId);

        StoryRecord storyRecord = new StoryRecord(user, story);
        entityManager.persist(storyRecord);

        user.getStoryRecords().add(storyRecord);
        story.getStoryRecords().add(storyRecord);

        databaseAccessor.commitTransaction(entityManager);


        JsonNode jsonNode = objectMapper.valueToTree(story);
        return ok(jsonNode);
    }

    @BasicAuth
    public Result getStories(int numberOfStories) {
        List<Story> stories = storyDAO.getStories(numberOfStories);
        if (stories.isEmpty()) {
            return badRequest("No stories found in database");
        }
        JsonNode jsonNode = objectMapper.valueToTree(stories);
        return ok(jsonNode);
    }

    @BasicAuth
    public Result getCurrentUserStories(){
        String userEmail = getEmailFromHeader();
        User currentUser = userDAO.findUserByEmail(databaseAccessor.getEntityManager(), userEmail);
        List<Story> stories = storyDAO.getStoriesByAuthorId(currentUser.getUserId());
        if (stories.isEmpty()) {
            return badRequest("No stories found for current user");
        }
        ArrayNode arrayNode = objectMapper.valueToTree(stories);
        arrayNode.add(objectMapper.valueToTree(currentUser));
        return ok(arrayNode);
    }

    @BasicAuth
    public Result getStoryByAuthorId(UUID authorId){
        List<Story> stories = storyDAO.getStoriesByAuthorId(authorId);
        if(stories.isEmpty()){
            return badRequest("No stories found for user");
        }
        ArrayNode arrayNode = objectMapper.valueToTree(stories);
        //Add author's information to returned json.
        User author = userDAO.getUser(authorId);
        arrayNode.add(objectMapper.valueToTree(author));
        return ok(arrayNode);
    }

    private String getEmailFromHeader(){
        HttpAuthorizationParser httpAuthorizationParser = new HttpAuthorizationParser();
        String[] credString = httpAuthorizationParser.getAuthorizationFromHeader(ctx());
        return credString[0];
    }
}
