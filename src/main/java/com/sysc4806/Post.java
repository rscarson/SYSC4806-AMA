package com.sysc4806;

import com.ocpsoft.pretty.time.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by maxwelldemelo on 3/2/2017.
 */

@Entity
public class Post {
    @Id
    @GeneratedValue
    private long id;

    private Date created;
    private Date updated;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }


    private String title;

    @ManyToOne
    private User poster;

    private String description;

    private ArrayList<String> tags;

    private int votes;

    public Post() {
        tags = new ArrayList<>();
        votes = 0;
    }

    public Post(String title, User poster, String description){
        this();

        this.title = title;
        this.poster = poster;
        this.description = description;
    }

    public Date getUpdated() { return updated; }
    public void setUpdated(Date updated) { this.updated = updated; }
    public String getFormattedUpdated() {
        PrettyTime p = new PrettyTime();
        return p.format(updated);
    }

    public Date getCreated() { return created; }
    public String getFormattedCreated() {
        PrettyTime p = new PrettyTime();
        return p.format(created);
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
        this.description = MarkdownTranslator.translate(description);
    }

    public void upVote() { votes ++; }
    public void downVote() { votes --; }
    public int getVotes() { return votes; }

    public ArrayList<String> getTags(){
        return tags;
    }

    public void addTags(String tag){
        tags.add(tag);
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
}
