package controllers;

import actions.BasicAuth;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import model.User;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Created by Sara on 9/30/2016.
 */
public class UserController extends Controller {

    private Provider<EntityManager> emProvider;

    @Inject
    public UserController(Provider<EntityManager> emProvider){
        this.emProvider = emProvider;
    }

    public Result register(){
        Json json = new Json();
        JsonNode userAsJson = request().body().asJson();
        if (userAsJson == null) {
            return badRequest("Expected JSON body");
        } else {
            User user = json.fromJson(userAsJson, User.class);
            EntityManager em = emProvider.get();
            em.getTransaction().begin();

            //Add user so long as the email address is not already stored in database
            boolean userSuccessfullyAdded = false;
            if(em.find(User.class, user.getEmailAddress()) == null) {
                em.persist(user);
                userSuccessfullyAdded = true;
            }
            em.getTransaction().commit();
            em.close();
            return (userSuccessfullyAdded)? ok("User added to database."): badRequest("Error: User already exists in database.");
        }

    }

    @BasicAuth
    public Result login(){
        return ok("Successfully logged in!");
    }
}
