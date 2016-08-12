package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class User
{
  private String userId;

  private String firstName;

  private String lastName;

  private String city;

  //Getters/ setters/ constructors go here

  public void setUserId(String userId){
    this.userId = userId;
  }

  public String getUserId(){
    return userId;
  }

  public void setFirstName(String firstName){
    this.firstName = firstName;
  }

  public String getFirstName(){
    return firstName;
  }

  public void setLastName(String lastName){
    this.lastName = lastName;
  }

  public String getLastName(){
    return lastName;
  }

  public void setCity(String city){
    this.city = city;
  }

  public String getCity(){
    return city;
  }
}
