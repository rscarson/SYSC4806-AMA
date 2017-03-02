package com.sysc4806;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by maxwelldemelo on 3/2/2017.
 */

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String userName;

    public User() {}

    public User(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }
}
