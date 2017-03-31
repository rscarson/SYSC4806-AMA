package com.sysc4806;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Max on 3/22/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired private PostRepository postRepository;

    @Test
    @WithMockUser
    public void shouldCreateANewComment() throws Exception{
        mockMvc.perform(post("/ama/new")
                .param("title", "new AMA")
                .param("description", "Ask me Anything!")
                .param("tags", "AMA, X, Y, adam"))
                .andReturn();
        mockMvc.perform(post("/comment/new")
                .param("postID", "1")
                .param("content", "First!"))
                .andReturn();

        List<Post> posts = postRepository.findByPosterIsNotNull();
        assert(posts.size() != 0);
        assert(commentRepository.findByPostAndParent(posts.get(0), null).size() != 0);
    }
}
