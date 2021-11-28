package com.epam.training.unit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.epam.training.config.SpringConfig;
import com.epam.training.config.SpringMvcConfig;
import com.epam.training.controller.UserController;
import com.epam.training.model.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {SpringMvcConfig.class, SpringConfig.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void adduser() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/employees")
                .content(asJsonString(new UserEntity("sampleuser", "mail@mail.com")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andReturn().getResponse().getContentAsString();

        assertThat(response.contains("Main page"));
    }

    @Test
    public void shouldReturnCorrectUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", 0))
                .andExpect(model().attribute("name", "sampleuser"))
                .andExpect(model().attribute("email", "mail@mail.com"));
    }

    @Test
    public void shouldReturnCorrectUserByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/name/sampleuser")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", 0))
                .andExpect(model().attribute("name", "sampleuser"))
                .andExpect(model().attribute("email", "mail@mail.com"));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/users/0"))
                .andExpect(status().isAccepted());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}