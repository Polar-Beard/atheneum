package model;

import org.hibernate.search.annotations.*;

import java.util.UUID;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stories")
@Indexed
public class Story {

    @Id
    private UUID storyId;
    @Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
    private Date date;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String title;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String description;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String author;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private int viewCount;

    public Story(){
      this("","","", null);
    }

    public Story(String title, String description, String author, UUID storyId){
        this.title  = title;
        this.description = description;
        this.author = author;
        this.viewCount = 0;
        this.storyId = (storyId == null)? UUID.randomUUID() : storyId;
        this.date = new Date();
    }

    public UUID getStoryId(){
      return storyId;
    }

    public Date getDate(){
      return date;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getAuthor(){
        return author;
    }

    public int getViewCount(){
        return viewCount;
    }

    public void setStoryId(UUID storyId){
      this.storyId = storyId;
    }

    public void setDate(Date date){
      this.date = date;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setViewCount(int viewCount){
        this.viewCount = viewCount;
    }
}
