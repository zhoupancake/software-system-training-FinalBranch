package com.systemtraining.test;

import com.alibaba.fastjson.JSON;
import com.systemtraining.SystemTrainingApplication;
import com.systemtraining.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SystemTrainingApplication.class)
@Slf4j

public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp(){
        // 设定mockMvc能mock的controller是整个springBoot项目环境中的controller
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testAddUserinfo(){
        User user = new User();
        user.setId("1672162078542974977");
        user.setPassword("admin");
        user.setStatus("1");

        String body = JSON.toJSONString(user);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUserInfo")
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
    void testAddUserinfoWithEmpty(){
        User user = new User();
        user.setId("1672162078542974975");
        user.setPassword("admin");
        user.setStatus("1");

        String body = JSON.toJSONString(user);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUserInfo")
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
    void testModifyUserinfo(){
        User user = new User();
        user.setId("1672162078542974977");
        user.setPassword("admin");
        user.setStatus("1");

        String body = JSON.toJSONString(user);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/modifyUserInfo")
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
    void testModifyUserInfo() {
        // 创建一个模拟的UserEntity对象作为输入参数
        User user = new User();
        user.setId("1672162078542974977");
        user.setPassword("admin");
        user.setStatus("1");

        // 将UserEntity对象转换为JSON字符串
        String body = JSON.toJSONString(user);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/modifyUserInfo")
                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                            .content(body).characterEncoding("utf-8"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            // 验证接口的返回状态码是否符合预期
            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

            // 打印接口的返回内容
            log.info(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testModifyUserinfoWithEmpty(){
        User user = new User();
        user.setId("1672162078542974975");
        user.setPassword("admin");
        user.setStatus("1");

        String body = JSON.toJSONString(user);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/modifyUserInfo")
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
    void testQueryUserList() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 5);
        map.put("username", "testUser");

        String body = JSON.toJSONString(map);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/queryUserList")
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                        .content(body).characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        log.info(mvcResult.getResponse().getContentAsString());
    }
//    @Test
//    void testDeleteUserInfo(){
//        User userEntity = new User();
//        userEntity.setId("1672162078542974977");
//        userEntity.setPassword("admin");
//        userEntity.setStatus("1");
//
//        String body = JSON.toJSONString(userEntity);
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/deleteUserInfo")
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

//    @Test
//    void testDeleteUserInfoWithEmpty(){
//        User userEntity = new User();
//        userEntity.setId("1672162078542974975");
//        userEntity.setPassword("admin");
//        userEntity.setStatus("1");
//
//        String body = JSON.toJSONString(userEntity);
//        MvcResult mvcResult;
//        try {
//            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/admin/deleteUserInfo")
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
}
