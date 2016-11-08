package model;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import java.util.UUID;

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
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String body;
    @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
    private UUID authorId;

    public Story(){
        this.storyId = UUID.randomUUID();
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

    public String getBody() {
        return body;
    }
    public UUID getAuthorId(){
        return authorId;
    }

    public void setStoryId(UUID storyId){
      this.storyId = storyId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setAuthorId(UUID authorId){
        this.authorId = authorId;
    }

    public void setBody(String body){
        this.body = body;
    }

}
