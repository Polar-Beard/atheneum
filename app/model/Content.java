package model;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sara on 2/21/2017.
 */
@Entity
@Table(name = "content")
@Indexed
public class Content {

    @Id
    @Column(name = "CONTENT_ID", unique = true, nullable = false)
    private UUID contentId;
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private int type;
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String value;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "content")
    private List<Qualifier> qualifiers;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORY_ID", nullable = false)
    private Story story;

    public Content() {
        this.contentId = UUID.randomUUID();
        qualifiers = new ArrayList<>();
    }

    public void setContentId(UUID contentId) {
        this.contentId = contentId;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public List<Qualifier> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(List<Qualifier> qualifiers){
        this.qualifiers = qualifiers;
    }
}
