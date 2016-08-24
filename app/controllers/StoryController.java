package controllers;

import play.mvc.*;

import views.html.*;

import model.*;

import javax.persistence.EntityManager;
import javax.inject.*;
import javax.persistence.Persistence;

public class StoryController extends Controller{

  private EntityManager em;

  @Inject
  public StoryController(EntityManager em){
    this.em = em;
  }

  public Result create() {

      em.getTransaction().begin();

      // Create a Story
      Story atlasShrugged = new Story("The Fountainhead", "Howard Roark is one badass motherfucker", "Ayn Rand");

      // Persist the Story
      em.persist(atlasShrugged);
      em.getTransaction().commit();

      em.close();

      return ok("The Fountainhead created");
    }
}
