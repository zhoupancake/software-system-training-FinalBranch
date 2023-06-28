package com.systemtraining.test;

import com.systemtraining.SystemTrainingApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = SystemTrainingApplication.class)
@Slf4j
class ProjectApplicationTests {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Before
//    public void setUp(){
//        // 设定mockMvc能mock的controller是整个springBoot项目环境中的controller
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @Test
//    void testQueryProjectReturnNull(){
//        Project projectEntity = new Project();
//        projectEntity.setProjectName("yyyyyy");
//        projectEntity.setId("1");
//        String body = JSON.toJSONString(projectEntity);
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/queryProjectList")
//                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
//                            .content(body).characterEncoding("utf-8")).andExpect(MockMvcResultMatchers.status().isOk())
//                    .andDo(MockMvcResultHandlers.print())
//                    // 正式执行接口,并返回接口的返回值
//                    .andReturn();
//            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//            log.info(mvcResult.getResponse().getContentAsString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    void testQueryProject(){
//        MvcResult mvcResult;
//        Project projectEntity2 = new Project();
//        projectEntity2.setProjectName(null);
//        projectEntity2.setId(null);
//        String body2 = JSON.toJSONString(projectEntity2);
//        try {
//            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/queryProjectList")
//                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
//                            .content(body2).characterEncoding("utf-8")).andExpect(MockMvcResultMatchers.status().isOk())
//                    .andDo(MockMvcResultHandlers.print())
//                    // 正式执行接口,并返回接口的返回值
//                    .andReturn();
//            log.info(mvcResult.getResponse().getContentAsString());
//            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void testAddProject(){
//        Project projectEntity = new Project();
//        projectEntity.setProjectName("hhhhh");
//        projectEntity.setProjectContent("eeeee");
//        String body = JSON.toJSONString(projectEntity);
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addProjectInfo")
//                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
//                            .content(body).characterEncoding("utf-8")).andExpect(MockMvcResultMatchers.status().isOk())
//                    .andDo(MockMvcResultHandlers.print())
//                    // 正式执行接口,并返回接口的返回值
//                    .andReturn();
//            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//            log.info(mvcResult.getResponse().getContentAsString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void testModifyProject(){
//        Project projectEntity = new Project();
//        projectEntity.setId("1");
//        projectEntity.setProjectName("hhhhh");
//        projectEntity.setProjectContent("55555");
//        String body = JSON.toJSONString(projectEntity);
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/modifyProjectInfo")
//                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
//                            .content(body).characterEncoding("utf-8")).andExpect(MockMvcResultMatchers.status().isOk())
//                    .andDo(MockMvcResultHandlers.print())
//                    // 正式执行接口,并返回接口的返回值
//                    .andReturn();
//            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//            log.info(mvcResult.getResponse().getContentAsString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void testProjectDelete(){
//        Project projectEntity = new Project();
//        projectEntity.setId("1");
//        String body = JSON.toJSONString(projectEntity);
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/deleteProjectById")
//                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
//                            .content(body).characterEncoding("utf-8")).andExpect(MockMvcResultMatchers.status().isOk())
//                    .andDo(MockMvcResultHandlers.print())
//                    // 正式执行接口,并返回接口的返回值
//                    .andReturn();
//            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//            log.info(mvcResult.getResponse().getContentAsString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void testEntity(){
//        String str = User.builder().id("").createdBy("")
//                .creationDate(LocalDateTime.now()).startTime(LocalDateTime.now()).password("")
//                .lastUpdatedBy("").status("").lastUpdateDate(LocalDateTime.now()).username("")
//                .stopTime(LocalDateTime.now()).build().toString();
//        String aaa = User.builder().toString();
//        User userEntity = new User();
//        User userEntity1 = new User();
//        boolean equals = userEntity.equals(userEntity1);
//        log.info(str + aaa + userEntity.hashCode());
//        String str2 = Project.builder().id("").creationDate(LocalDateTime.now()).createdBy("").projectName("").projectContent("")
//                .userId("").lastUpdateDate(LocalDateTime.now()).lastUpdatedBy("").build().toString();
//        String bbb = Project.builder().toString();
//        Project projectEntity = new Project();
//        Project projectEntity1 = new Project();
//        boolean equals1 = projectEntity.equals(projectEntity1);
//        log.info(str2 + bbb + projectEntity.hashCode());
//    }
//

}
