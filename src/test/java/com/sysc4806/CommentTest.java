package com.sysc4806;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by adambatson on 3/20/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CommentTest {

    private Post post;
    private Comment comment;

    @Before
    @WithMockUser
    public void setUp() {
        comment = new Comment(AuthenticationController.CurrentUser(), "Hello!");
    }

    @Test
    @WithMockUser
    public void testUpvote() {
        assertEquals(1, comment.getVotes());
        comment.upVote(AuthenticationController.CurrentUser());
        assertEquals(0, comment.getVotes());
        comment.upVote(AuthenticationController.CurrentUser());
        assertEquals(1, comment.getVotes());
    }

    @Test
    @WithMockUser
    public void testDownvote() {
        assertEquals(1, comment.getVotes());
        comment.downVote(AuthenticationController.CurrentUser());
        assertEquals(-1, comment.getVotes());
        comment.downVote(AuthenticationController.CurrentUser());
        assertEquals(0, comment.getVotes());
    }

    @Test
    public void testGetChildren() {
        Comment c2 = new Comment(post, "Hello how are you todsy?");
        Comment c3 = new Comment(post, "Goodbye!");
        Comment c4 = new Comment(post, "Good and you?");

        ArrayList<Comment> expected = new ArrayList<>();
        expected.add(c2);
        expected.add(c3);

        c2.addChild(c4);
        comment.addChild(c2);
        comment.addChild(c3);

        assertEquals(expected, comment.getChildren());
    }

}
