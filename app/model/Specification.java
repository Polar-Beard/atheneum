package model;

import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Sara on 2/21/2017.
 */

@Entity
@Table(name = "specification")
@Indexed
public class Specification {
    @Id
    @Column(name = "SPECIFICATION_ID", unique = true, nullable = false)
    private UUID specificationId;
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String name;
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String type;
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUALIFIER_ID", nullable = false)
    private Qualifier qualifier;

    public Specification(){
        this.specificationId = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setQualifier(Qualifier qualifier) {
        this.qualifier = qualifier;
    }

    public void setSpecificationId(UUID specificationId) {
        this.specificationId = specificationId;
    }
}
