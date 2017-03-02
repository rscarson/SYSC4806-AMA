package com.sysc4806;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by maxwelldemelo on 3/2/2017.
 */

@Entity
public class Post {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    @ManyToOne
    private User poster;

    private String description;

    private ArrayList<String> tags;

    public Post() {
        tags = new ArrayList<>();
    }

    public Post(String title, User poster, String description){
        this();

        this.title = title;
        this.poster = poster;
        this.description = description;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public User getPoster(){
        return poster;
    }

    public void setPoster(User poster){
        this.poster = poster;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public ArrayList<String> getTags(){
        return tags;
    }

    public void setTags(ArrayList<String> tags){
        this.tags = tags;
    }

    public String tagsToString(){
        String allTags = "";
        for(String tag:tags){
            if(tags.indexOf(tag) == tags.size()-1)
                allTags += tag;
            else
                allTags += tag + ", ";
        }
        return allTags;
    }

    public void addTags(String tag){
        tags.add(tag);
    }
}
