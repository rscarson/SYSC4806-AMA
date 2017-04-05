package com.sysc4806;

import com.ocpsoft.pretty.time.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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

    @Column(columnDefinition = "TEXT")
    private String source;

    @Column(columnDefinition = "TEXT")
    private String description;

    private ArrayList<String> tags;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> upVoters;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> dnVoters;

    private boolean completed;

    private int votes;

    public Post() {
        tags = new ArrayList<>();
        upVoters = new HashSet<>();
        dnVoters = new HashSet<>();
        votes = 0;
    }

    public Post(String title, User poster, String description){
        this();

        setTitle(title);
        setPoster(poster);
        setDescription(description);
        upVote(AuthenticationController.CurrentUser());
    }

    public Date getUpdated() { return updated; }
    public void setUpdated(Date updated) { this.updated = updated; }
    public String getFormattedUpdated() {
        PrettyTime p = new PrettyTime();
        return p.format(updated);
    }

    public boolean isCompleted() { return completed;  }
    public void setCompleted(boolean completed) { this.completed = completed; }

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
    public String getSource() { return source; }
    public void setDescription(String description){
        this.source = description;
        this.description = MarkdownTranslator.translate(description);
    }

    public boolean hasUpVoted(User u) { return u != null && upVoters.stream().filter(v -> v.getId() == u.getId()).collect(Collectors.toList()).size() > 0; }
    public void clearUpVotes(User u) {
        Iterator<User> i = upVoters.iterator();
        while (i.hasNext()) {
            User f = i.next();
            if (f.getId() == u.getId()) {
                i.remove();
                votes --;
            }
        }
    }
    public void upVote(User u) {
        if (u == null || poster == null)
            return;

        clearDownVotes(u);
        if (hasUpVoted(u)) {
            clearUpVotes(u);
        } else {
            upVoters.add(u);
            votes ++;
        }
    }

    public boolean hasDownVoted(User u) { return u != null && dnVoters.stream().filter(v -> v.getId() == u.getId()).collect(Collectors.toList()).size() > 0; }
    public void clearDownVotes(User u) {
        Iterator<User> i = dnVoters.iterator();
        while (i.hasNext()) {
            User f = i.next();
            if (f.getId() == u.getId()) {
                i.remove();
                votes ++;
            }
        }
    }
    public void downVote(User u) {
        if (u == null)
            return;

        clearUpVotes(u);
        if (hasDownVoted(u)) {
            clearDownVotes(u);
        } else {
            dnVoters.add(u);
            votes --;
        }
    }

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
