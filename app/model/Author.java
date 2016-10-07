package model;


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
    @OneToMany
    @JoinColumn(name="author_id")
    private List<Story> stories;

    public Author(){
        stories = new ArrayList<>();
    }

    private Long getAuthorId(){
        return authorId;
    }

    private List<Story> getStories(){
        return stories;
    }

    private void setAuthorId(Long authorId){
        this.authorId = authorId;
    }

    private void setStories(List<Story> stories){
        this.stories = stories;
    }



}
