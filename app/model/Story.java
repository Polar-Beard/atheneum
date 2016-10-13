package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import java.util.UUID;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="stories")
@Indexed
public class Story {

    @Id
    private UUID storyId;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String title;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String description;
    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JoinColumn(name="authorId")
    //@JsonBackReference
    //private Author author;
    private UUID authorId;

    public UUID getAuthorId(){
        return authorId;
    }

    public void setAuthorId(UUID authorId){
        this.authorId = authorId;
    }

    public Story(){
        this.storyId = UUID.randomUUID();
    }

    public Story(String title, String description){
        this.title  = title;
        this.description = description;
    }

    public UUID getStoryId(){
      return storyId;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    /*public Author getAuthor(){
        return author;
    }*/

    public void setStoryId(UUID storyId){
      this.storyId = storyId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    /*public void setAuthor(Author author){
        this.author = author;
    }*/

}
