package model;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sara on 10/5/2016.
 */
@Entity
@Table(name="authors")
public class Author {

    @Id
    private UUID authorId;
    //@OneToMany(mappedBy = "author")
    //@JsonManagedReference
    //private List<Story> stories;

    public Author(){
        //stories = new ArrayList<>();
        this.authorId = UUID.randomUUID();
    }

   public UUID getAuthorId(){
        return authorId;
    }

    /*public List<Story> getStories(){
        return stories;
    }*/

    public void setAuthorId(UUID authorId){
        this.authorId = authorId;
    }

    /*public void setStories(List<Story> stories){
        this.stories = stories;
    }*/



}
