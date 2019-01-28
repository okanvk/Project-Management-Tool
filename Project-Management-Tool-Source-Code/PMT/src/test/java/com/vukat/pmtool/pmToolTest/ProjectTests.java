package com.vukat.pmtool.pmToolTest;

import com.vukat.pmtool.domain.Project;
import com.vukat.pmtool.services.ProjectService;
import com.vukat.pmtool.services.UserService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@WebAppConfiguration
public class ProjectTests extends HiberApplicationTests {

    private MockMvc mockMvc;


    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void saveProject(){




        Project project = new Project();



        project.setProjectName("PROJE İSİM");

        project.setDescription("DESCRIPTION");

        project.setProjectIdentifer("4322");
        Project project1 = projectService.saveOrUpdateProject(project,"can1@gmail.com");

        assertThat(project1.getUser().getUsername(),equalTo("can1@gmail.com"));


    }




}
