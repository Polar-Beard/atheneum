package model;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Sara on 3/28/2017.
 */
@Entity
@Table(name = "category")
@Indexed
public class Category {

    private static long count = 0;

    public Category(){
        count++;
        this.order = count;
    }

    @Id
    @Column(name="CATEGORY_ID", unique = true, nullable = false)
    private long order;
    @Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
    private String title;
    @Transient
    private List<Story> storyList;
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String searchQuery;

    public long getOrder() {
        return order;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setStoryList(List<Story> storyList){
        this.storyList = storyList;
    }

    public List<Story> getStoryList(){
        return storyList;
    }
}
