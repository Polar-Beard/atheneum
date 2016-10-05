package model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private String userEmailAddress;
    private List<UUID> storiesAuthoredIds;

    public Author(){
        authorId = UUID.randomUUID();
        storiesAuthoredIds = new ArrayList<>();
    }

    private UUID getAuthorId(){
        return authorId;
    }

    private String getUserEmailAddress(){
        return userEmailAddress;
    }

    private List<UUID> getStoriesAuthoredIds(){
        return storiesAuthoredIds;
    }

    private void setAuthorId(UUID authorId){
        this.authorId = authorId;
    }

    private void setUserEmailAddress(String userEmailAddress)
    {
        this.userEmailAddress = userEmailAddress;
    }

    private void setStoriesAuthoredIds(List<UUID> storiesAuthoredIds){
        this.storiesAuthoredIds = storiesAuthoredIds;
    }

    private void addStoryAuthoredId(UUID storyId){
        storiesAuthoredIds.add(storyId);
    }


}
