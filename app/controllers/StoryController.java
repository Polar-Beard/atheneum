package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Story;

import java.util.List;
import java.util.UUID;

import play.mvc.*;
import play.libs.Json;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.inject.*;

import com.fasterxml.jackson.databind.JsonNode;

public class StoryController extends Controller{

  private EntityManager em;

  @Inject
  public StoryController(EntityManager em){
    this.em = em;
  }

  public Result addStory(){

      //Get HTTP POST request body as a Json object
      JsonNode storyAsJson = request().body().asJson();

      return addStoryToDatabase(storyAsJson);
    }

    public Result getStory(UUID storyId){

        Json json = new Json();
        Story story = null;

        if(storyId == null) {
            return badRequest("Missing parameter [storyId]");
        } else {
            em.getTransaction();
            story = em.find(Story.class, storyId);
        }
        return ok(json.toJson(story));
    }

    /*public Result updateStory(UUID storyId){

        Story story = null;
        JsonNode storyAsJson = request().body().asJson();

        //Find the story in the database
        if(storyId == null) {
            return badRequest("Missing parameter [storyId]");
        } else{
            em.getTransaction();
            story = em.find(Story.class, storyId);
        }

        //Creates a new story if could not be found in database
        if(story == null){
            return addStoryToDatabase(storyAsJson);
        } else {
            ObjectNode oNode = (ObjectNode) storyAsJson;
            oNode.put
            story.getStoryId()
        }



    }*/

    private Result addStoryToDatabase(JsonNode storyAsJson){
        Json json = new Json();
        Story storyFromJson = null;
        Result result = null;

        if(storyAsJson == null){
            result = badRequest("Expected JSON body");
        } else{
            storyFromJson = json.fromJson(storyAsJson, Story.class);

            //Save Story object in database
            em.getTransaction().begin();
            em.persist(storyFromJson);
            em.getTransaction().commit();

            result = ok("Story added to database");
        }
        return result;
    }

}
