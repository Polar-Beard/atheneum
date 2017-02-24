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
@Table(name = "qualifier")
@Indexed
public class Qualifier {

    @Id
    @Column(name = "QUALIFIER_ID", unique = true, nullable = false)
    private UUID qualifierId;
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private int type;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qualifier")
    private List<Specification> specifications;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID", nullable = false)
    private Content content;

    public Qualifier() {
        this.qualifierId = UUID.randomUUID();
        specifications = new ArrayList<>();
    }

    public void setQualifierId(UUID qualifierId) {
        this.qualifierId = qualifierId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Specification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<Specification> specifications){
        this.specifications = specifications;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
