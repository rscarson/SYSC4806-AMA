package com.sysc4806;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.junit.Assert.*;

/**
 * Created by maxwelldemelo on 3/2/2017.
 */
public class PostTest {
    private Post p1;

    @Before
    public void setup() {
        p1 = new Post();
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
    public void testUpvote() {
        assertEquals(0, p1.getVotes());
        p1.upVote();
        assertEquals(1, p1.getVotes());
    }

    @Test
    public void testDownvote() {
        assertEquals(0, p1.getVotes());
        p1.downVote();
        assertEquals(-1, p1.getVotes());
    }
}
