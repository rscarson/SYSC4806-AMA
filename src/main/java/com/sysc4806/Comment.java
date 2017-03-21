package com.sysc4806;

import org.thymeleaf.processor.CommentNodeProcessorMatcher;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adambatson on 3/20/2017.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private Comment parent;
    @OneToMany
    private List<Comment> children;
    private String content;
    private int votes;

    public Comment(Comment parent, String content) {
        this.parent = parent;
        this.content = content;
        children = new ArrayList<>();
        votes = 0;
    }

    public Comment(String content) {
        this(null, content);
    }

    public Comment() {
        children = new ArrayList<>();
        votes = 0;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setParent(Comment parent) { this.parent = parent; }

    public Comment getParent() { return parent; }

    public void addChild(Comment child) { children.add(child); }

    public List<Comment> getChildren() { return children; }

    public void setContent(String content) { this.content = content; }

    public String getContent() { return content; }

    public int getVotes() { return votes; }

    public void upVote() { votes ++; }

    public void downVote() { votes ++; }

    public ArrayList<Comment> getAncestory() {
        ArrayList<Comment> parents = new ArrayList<>();
        Comment curr = this;
        while(curr.parent != null) {
            parents.add(curr.parent);
            curr = curr.parent;
        }
        return parents;
    }


}
