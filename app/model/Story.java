package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name="story")
@Indexed
public class Story {
    @Id
    @Column(name = "STORY_ID", unique = true, nullable = false)
    private UUID storyId;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String title;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String description;
    @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
    private UUID authorId;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "story")
    private List<Content> contents;
    @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
    private Date createdDate;
    @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
    private Date lastUpdatedDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "story")
    @JsonManagedReference
    private List<StoryRecord> storyRecords = new ArrayList<>();



    public Story(){
        this.storyId = UUID.randomUUID();
        this.contents = new ArrayList<>();
    }

    public void setStoryId(UUID storyId){
        this.storyId = storyId;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public UUID getAuthorId(){
        return authorId;
    }

    public void setAuthorId(UUID authorId){
        this.authorId = authorId;
    }

    public List<Content> getContents(){
        return this.contents;
    }

    public void setContents(List<Content> contents){
        this.contents = contents;
    }

    public void setStoryRecords(List<StoryRecord> storyRecords){
        this.storyRecords = storyRecords;
    }

    public List<StoryRecord> getStoryRecords(){
        return storyRecords;
    }
}
