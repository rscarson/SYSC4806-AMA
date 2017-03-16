package com.sysc4806;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by adambatson on 3/2/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnAMAIndex() throws Exception {
        this.mockMvc.perform(get("/ama")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNewAMAFrom() throws Exception {
        mockMvc.perform(get("/ama/new")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldCreateANewPost() throws Exception {
        mockMvc.perform(post("/ama/new")
                    .param("username", "adambatson")
                    .param("title", "new AMA")
                    .param("description", "Ask me Anything!")
                    .param("tags", "AMA, X, Y, adam"))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void shouldRenderAnAMAView() throws Exception {
        shouldCreateANewPost();
        mockMvc.perform(get("/ama/view?id=1")).andExpect(status().isOk());
}

    @Test
    public void should404OnMissingAMA() throws Exception {
        mockMvc.perform((get("/ama/view?id=66")))
                .andExpect(status().is4xxClientError());
    }
}
