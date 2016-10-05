package model;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;

/**
 * Created by Sara on 9/30/2016.
 */
@Entity
@Table(name="users")
@Indexed
public class User {
    @Id
    private String emailAddress;
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String password;
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String firstName;
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String lastName;

    @Inject private static Provider<EntityManager> emProvider;

    public User(){
    }

    public User(String emailAddress, String password){
        this.emailAddress = emailAddress;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean isValid(String emailAddress, String password){
        EntityManager em = emProvider.get();
        em.getTransaction().begin();
        User user = em.find(User.class, emailAddress);
        em.getTransaction().commit();
        em.close();
        return BCrypt.checkpw(password,user.getPassword());
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password){
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmailAddress(){
        return emailAddress;
    }

    public String getPassword(){
        return password;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
}
