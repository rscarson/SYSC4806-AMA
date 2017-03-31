package com.sysc4806;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.junit.Assert.*;

/**
 * Created by maxwelldemelo on 3/2/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostTest {
    private Post p1;

    @Before
    @WithMockUser
    public void setup() {
        p1 = new Post("title", AuthenticationController.CurrentUser(), "desc");
    }

    @Test
    public void tagsToString(){
        p1.addTags("potato");
        p1.addTags("roger");
        p1.addTags("testy");
        p1.addTags("johnny");
        assertEquals("potato, roger, testy, johnny", p1.tagsToString());
    }

    @Test
    public void addTag(){
        p1.addTags("Testy");
        assertTrue(p1.getTags().contains("Testy"));
        assertEquals(1, p1.getTags().size());
    }

    @Test
    @WithMockUser
    public void testUpvote() {
        assertEquals(1, p1.getVotes());
        p1.upVote(AuthenticationController.CurrentUser());
        assertEquals(0, p1.getVotes());
        p1.upVote(AuthenticationController.CurrentUser());
        assertEquals(1, p1.getVotes());
    }

    @WithMockUser
    @Test
    public void testDownvote() {
        assertEquals(1, p1.getVotes());
        p1.downVote(AuthenticationController.CurrentUser());
        assertEquals(-1, p1.getVotes());
        p1.downVote(AuthenticationController.CurrentUser());
        assertEquals(0, p1.getVotes());
    }
}
