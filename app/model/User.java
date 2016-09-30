package model;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by Sara on 9/30/2016.
 */
public class User {
    private String emailAddress;
    private String passwordHash;
    private String firstName;
    private String lastName;

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public void setPasswordHash(String password){
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
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

    public String getPasswordHash(){
        return passwordHash;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
}
