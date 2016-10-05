package model;

import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @Field
    private List<UUID> storiesAuthoredIds;


}
