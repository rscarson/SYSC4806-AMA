package com.sysc4806;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by maxwelldemelo on 3/2/2017.
 */

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> following;

    private String name;
    private String email;

    public User() { following = new ArrayList<>(); }
    public User(String email) { this(); setEmail(email); }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<User> getFollowing() { return following; }
    public void setFollowing(List<User> following) { this.following = following; }

    public void follow(User u) {
        following.add(u);
    }

    public void unfollow(User u) {
        following.remove(u);
    }
}
