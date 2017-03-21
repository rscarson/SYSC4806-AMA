package com.sysc4806;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by adambatson on 3/20/2017.
 */
public class CommentTest {

    private Comment comment;

    @Before
    public void setUp() {
        comment = new Comment("Hello!");
    }

    @Test
    public void testUpvote() {
        assertEquals(0, comment.getVotes());
        comment.upVote();
        assertEquals(1, comment.getVotes());
    }

    @Test
    public void testDownvote() {
        assertEquals(0, comment.getVotes());
        comment.downVote();
        assertEquals(1, comment.getVotes());
    }

    @Test
    public void testGetAncestory() {
        Comment c2 = new Comment(comment, "Hello to you as well!");
        Comment c3 = new Comment(c2, "How are you today?");
        ArrayList<Comment> expected = new ArrayList<>();
        expected.add(c2);
        expected.add(comment);
        assertEquals(expected, c3.getAncestory());
    }

    @Test
    public void testGetChildren() {
        Comment c2 = new Comment("Hello how are you todsy?");
        Comment c3 = new Comment("Goodbye!");
        Comment c4 = new Comment("Good and you?");

        ArrayList<Comment> expected = new ArrayList<>();
        expected.add(c2);
        expected.add(c3);

        c2.addChild(c4);
        comment.addChild(c2);
        comment.addChild(c3);

        assertEquals(expected, comment.getChildren());
    }

}
