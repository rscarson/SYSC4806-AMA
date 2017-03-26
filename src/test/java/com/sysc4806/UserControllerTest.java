package com.sysc4806;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by maxwelldemelo on 3/2/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldHaveNoUser() throws Exception{
        User user = AuthenticationController.CurrentUser();
        assert(user == null);
    }

    @Test
    @WithMockUser
    public void shouldGetActiveUser() throws Exception{
        User user = AuthenticationController.CurrentUser();
        this.mockMvc.perform(get("/user/view?id="+user.getId())).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void shouldFollow() throws Exception{
        User user = AuthenticationController.CurrentUser();
        User u2 = new User(); userRepository.save(u2);
        this.mockMvc.perform(get("/user/follow?id="+u2.getId())).andExpect(status().isOk());
        user = AuthenticationController.CurrentUser();
        assert(user.isFollowing(u2));
    }

    @Test
    @WithMockUser
    public void shouldUnfollow() throws Exception{
        User user = AuthenticationController.CurrentUser();
        User u2 = new User(); userRepository.save(u2);
        user.follow(u2);
        this.mockMvc.perform(get("/user/unfollow?id="+u2.getId())).andExpect(status().isOk());
        user = AuthenticationController.CurrentUser();
        assert(!user.isFollowing(u2));
    }

    @Test
    @WithMockUser
    public void check404Exception() throws Exception{
        this.mockMvc.perform((get("user/view?id=404")))
                .andExpect(status().is4xxClientError());
    }
}
