package model;

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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long storyId;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String title;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String description;
    @ManyToOne
    @JoinColumn(name="authorId")
    private Author author;

    public Story(){
    }

    public Story(String title, String description){
        this.title  = title;
        this.description = description;
    }

    public Long getStoryId(){
      return storyId;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public Author getAuthor(){
        return author;
    }

    public void setStoryId(Long storyId){
      this.storyId = storyId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setAuthor(Author author){
        this.author = author;
    }

}
