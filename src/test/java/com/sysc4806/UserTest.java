package com.sysc4806;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 3/23/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserTest {
    private User u1;

    @Autowired
    private UserRepository userRepository;

    @Before
    @WithMockUser
    public void setup() {
        u1 = AuthenticationController.CurrentUser();
    }

    @Test
    @WithMockUser
    public void shouldFollow() {
        User u2 = new User(); userRepository.save(u2);
        u1.getFollowing().clear();
        u1.follow(u2);

        Assert.assertEquals(1, u1.getFollowing().size());
    }

    @Test
    @WithMockUser
    public void uniqueFollowing() {
        User u2 = new User(); userRepository.save(u2);
        u1.getFollowing().clear();
        u1.follow(u2);
        u1.follow(u2);

        Assert.assertEquals(1, u1.getFollowing().size());
    }

    @Test
    @WithMockUser
    public void shouldUnfollow() {
        User u2 = new User(); userRepository.save(u2);
        u1.getFollowing().clear();
        u1.follow(u2);
        u1.unfollow(u2);

        assert(u1.getFollowing().isEmpty());
    }
}
