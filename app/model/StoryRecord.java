package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Sara on 3/31/2017.
 */
@Entity
@Table(name="story_record")
@Indexed
public class StoryRecord {

    private enum State{
      Saved, Opened, Finished, Dismissed, Fetched
    }

    public StoryRecord(){

    }
    public StoryRecord(User user, Story story){
        this.user = user;
        this.story = story;
        this.id = UUID.randomUUID();
        this.createdDate = new Date();
    }

    @Id
    private UUID id;
    @Field(index= org.hibernate.search.annotations.Index.YES, analyze=Analyze.YES, store=Store.NO)
    private Date createdDate;
    @Field(index= Index.YES, analyze=Analyze.YES, store=Store.NO)
    private Date lastUpdateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORY_ID", nullable = false)
    @JsonBackReference
    private Story story;

    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private boolean stateSaved = false;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private boolean stateFinished = false;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private boolean stateOpened = false;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private boolean stateDismissed = false;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private boolean stateFetched= false;


    @ElementCollection
    @MapKeyColumn(name = "update_date")
    @Column(name = "state")
    @CollectionTable(name = "state_update_dates", joinColumns = @JoinColumn(name = "story_record_id"))
    private Map<Date,State> stateUpdateDates = new HashMap<>();

    public UUID getId(){
        return id;
    }

    public Date getCreatedDate(){
        return createdDate;
    }

    public Date getLastUpdateDate(){
        return lastUpdateDate;
    }

    public User getUser(){
        return user;
    }

    public Story getStory(){
        return story;
    }

    public boolean isStateSaved(){
        return stateSaved;
    }

    public boolean isStateFinished(){
        return stateFinished;
    }

    public boolean isStateOpened(){
        return stateOpened;
    }

    public boolean isStateDismissed(){
        return stateDismissed;
    }

    public boolean isStateFetched(){
        return stateFetched;
    }

    public void setStateSaved(boolean isSaved){
        if(stateSaved != isSaved){
            stateSaved = isSaved;
            lastUpdateDate = new Date();
            stateUpdateDates.put(lastUpdateDate,State.Saved);
        }
    }

    public void setStateFinished(boolean isFinished){
        if(stateFinished != isFinished){
            stateFinished = isFinished;
            lastUpdateDate = new Date();
            stateUpdateDates.put(lastUpdateDate,State.Finished);
        }
    }

    public void setStateOpened(boolean isOpened){
        if(stateOpened != isOpened){
            stateOpened = isOpened;
            lastUpdateDate = new Date();
            stateUpdateDates.put(lastUpdateDate,State.Opened);
        }
    }

    public void setStateDismissed(boolean isDismissed){
        if(stateDismissed != isDismissed){
            stateDismissed = isDismissed;
            lastUpdateDate = new Date();
            stateUpdateDates.put(lastUpdateDate, State.Dismissed);
        }
    }

    public void setStateFetched(boolean isFetched){
        if(stateFetched != isFetched){
            stateFetched = isFetched;
            lastUpdateDate = new Date();
            stateUpdateDates.put(lastUpdateDate, State.Fetched);
        }

    }
}
