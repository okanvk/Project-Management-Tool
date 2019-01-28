package com.vukat.pmtool.pmToolTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vukat.pmtool.domain.User;
import com.vukat.pmtool.viewmodel.LoginRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@WebAppConfiguration
public class UserRestControllerTests extends HiberApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }


    @Test
    public void registerUser () throws Exception{


        User user =  new User();
        user.setUsername("can12@gmail.com");
        user.setPassword("can123");
        user.setConfirmPassword("can123");
        user.setFullName("name");



        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register/").
                contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated());

    }

    @Test
    public void loginUser () throws Exception{


        LoginRequest request = new LoginRequest();
        request.setPassword("can123");
        request.setUsername("can1@gmail.com");



        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login/").
                contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

