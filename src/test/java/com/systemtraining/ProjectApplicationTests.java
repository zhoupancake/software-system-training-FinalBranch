package com.systemtraining;

import com.alibaba.fastjson.JSON;
import com.systemtraining.entity.Project;
import com.systemtraining.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemTrainingApplication.class)
@Slf4j
class ProjectApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp(){
        // 设定mockMvc能mock的controller是整个springBoot项目环境中的controller
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testQueryProjectReturnNull(){
        Project project = new Project();
        project.setProjectName("yyyyyy");
        project.setId("1");
        String body = JSON.toJSONString(project);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/queryProjectList")
                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                            .content(body).characterEncoding("utf-8")).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    // 正式执行接口,并返回接口的返回值
                    .andReturn();
            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
            log.info(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void testQueryProject(){
        MvcResult mvcResult;
        Project project2 = new Project();
        project2.setProjectName(null);
        project2.setId(null);
        String body2 = JSON.toJSONString(project2);
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/queryProjectList")
                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                            .content(body2).characterEncoding("utf-8")).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    // 正式执行接口,并返回接口的返回值
                    .andReturn();
            log.info(mvcResult.getResponse().getContentAsString());
            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddProject(){
        Project project = new Project();
        project.setProjectName("hhhhh");
        project.setProjectContent("eeeee");
        String body = JSON.toJSONString(project);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addProjectInfo")
                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                            .content(body).characterEncoding("utf-8")).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    // 正式执行接口,并返回接口的返回值
                    .andReturn();
            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
            log.info(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testModifyProject(){
        Project project = new Project();
        project.setId("1");
        project.setProjectName("hhhhh");
        project.setProjectContent("55555");
        String body = JSON.toJSONString(project);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/modifyProjectInfo")
                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                            .content(body).characterEncoding("utf-8")).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    // 正式执行接口,并返回接口的返回值
                    .andReturn();
            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
            log.info(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testProjectDelete(){
        Project project = new Project();
        project.setId("1");
        String body = JSON.toJSONString(project);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/deleteProjectById")
                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                            .content(body).characterEncoding("utf-8")).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    // 正式执行接口,并返回接口的返回值
                    .andReturn();
            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
            log.info(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEntity(){
        String str = User.builder().id("").createdBy("")
                .creationDate(LocalDateTime.now()).startTime(LocalDateTime.now()).password("")
                .lastUpdatedBy("").status("").lastUpdateDate(LocalDateTime.now()).username("")
                .stopTime(LocalDateTime.now()).build().toString();
        String aaa = User.builder().toString();
        User user = new User();
        User user1 = new User();
        boolean equals = user.equals(user1);
        log.info(str + aaa + user.hashCode());
        String str2 = Project.builder().id("").creationDate(LocalDateTime.now()).createdBy("").projectName("").projectContent("")
                .userId("").lastUpdateDate(LocalDateTime.now()).lastUpdatedBy("").build().toString();
        String bbb = Project.builder().toString();
        Project project = new Project();
        Project project1 = new Project();
        boolean equals1 = project.equals(project1);
        log.info(str2 + bbb + project.hashCode());
    }


}
