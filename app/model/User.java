package model;


import daos.UserDAO;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Created by Sara on 9/30/2016.
 */
@Entity
@Table(name="users")
@Indexed
public class User {
    @Id
    private String emailAddress;
    private String password;
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String firstName;
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String lastName;

    public User(){
    }

    public User(String emailAddress, String password){
        this.emailAddress = emailAddress;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean isValid(String emailAddress, String password){
        User user = (new UserDAO()).getUser(emailAddress);
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
