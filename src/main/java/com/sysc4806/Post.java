package com.sysc4806;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany
    private List<Comment> comments;

    private String description;

    private ArrayList<String> tags;

    private int votes;

    public Post() {
        tags = new ArrayList<>();
        comments = new ArrayList<>();
        votes = 0;
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

    public List<Comment> getComments() { return comments; }

    public void addComment(Comment comment) { comments.add(comment); }

    public void upVote() { votes ++; }

    public void downVote() { votes --; }

    public int getVotes() { return votes; }

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
