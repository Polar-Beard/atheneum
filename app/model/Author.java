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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long authorId;
    //@OneToMany(mappedBy = "author")
    //@JsonManagedReference
    //private List<Story> stories;

    public Author(){
        //stories = new ArrayList<>();
    }

   public Long getAuthorId(){
        return authorId;
    }

    /*public List<Story> getStories(){
        return stories;
    }*/

    public void setAuthorId(Long authorId){
        this.authorId = authorId;
    }

    /*public void setStories(List<Story> stories){
        this.stories = stories;
    }*/



}
