package com.sysc4806;

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
 * Created by maxwelldemelo on 3/2/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnUserIndex() throws Exception {
        this.mockMvc.perform(get("/user")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNewUserFrom() throws Exception {
        this.mockMvc.perform(get("/user/new")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void addUser() throws Exception {
        this.mockMvc.perform(post("/user/new")
                .param("username", "Max DeMelo"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void checkUser() throws Exception{
        addUser();
        this.mockMvc.perform(get("/user/view?id=1")).andExpect(status().isOk());
    }

    @Test
    public void check404Exception() throws Exception{
        this.mockMvc.perform((get("user/view?id=404")))
                .andExpect(status().is4xxClientError());
    }
}
