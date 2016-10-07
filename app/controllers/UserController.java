package controllers;

import actions.BasicAuth;
import com.fasterxml.jackson.databind.JsonNode;

import daos.UserDAO;
import model.User;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;



/**
 * Created by Sara on 9/30/2016.
 */
public class UserController extends Controller {

    private UserDAO userDAO;

    public UserController(){
        userDAO = new UserDAO();
    }

    public Result register(){
        JsonNode userAsJson = request().body().asJson();
        if (userAsJson == null) {
            return badRequest("Expected JSON body");
        }
        User user = Json.fromJson(userAsJson, User.class);
        if (userDAO.addUser(user)) {
            return ok("User added to database.");
        } else {
            return badRequest("Error: User already exists in database");
        }
    }

    @BasicAuth
    public Result login(){
        return ok("Successfully logged in!");
    }
}
