package com.systemtraining.test;

import com.alibaba.fastjson.JSON;
import com.systemtraining.SystemTrainingApplication;
import com.systemtraining.dto.ChosenAnswerDto;
import com.systemtraining.entity.Answer;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemTrainingApplication.class)
@Slf4j
class AnswerControllerTests {
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
    void testList(){
        Map<String,Object> map = new HashMap<>();
        map.put("pageNum",1);
        map.put("pageSize",15);
        map.put("username","admin");
        map.put("projectId","1668226706322665474");
        String body = JSON.toJSONString(map);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/answer/list")
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
    void testListNull(){
        Map<String,Object> map = new HashMap<>();
        map.put("pageNum",5);
        map.put("pageSize",10);
        map.put("username","admin");
        map.put("projectId","1668226706322665474");
        String body = JSON.toJSONString(map);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/answer/list")
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
    void testSubmit(){
//        ChosenAnswerDto chosenAnswerDto = new ChosenAnswerDto();
//        chosenAnswerDto.setQuestionnaireId("1672162070196310018");
//        chosenAnswerDto.setRoleId("1668209171955138565");
//
//        List<Map<String, String>> mapList = new ArrayList<>();
//        Map<String,String> map = new HashMap<>();
//        map.put("1672162118766350338","1672191013045583874");
//        mapList.add(map);
//
//        chosenAnswerDto.setAnswer(mapList);

        String body = "{\n" +
                "    \"roleId\": \"1668209171955138565\",\n" +
                "    \"questionnaireId\": \"1673285684975054850\",\n" +
                "    \"answer\": [\n" +
                "        {\n" +
                "            \"questionId\": \"1673285707301335041\",\n" +
                "            \"optionId\": \"1673285712967839747\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionId\": \"1673285727308165121\",\n" +
                "            \"optionId\": \"1673285880022773762\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionId\": \"1673285816885915650\",\n" +
                "            \"optionId\": \"1673285880022773762\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"questionId\": \"1673285707494273025\",\n" +
                "            \"optionId\": \"1673285719213158402\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/answer/submit")
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
    void testSubmitEmpty(){
        ChosenAnswerDto chosenAnswerDto = new ChosenAnswerDto();
        chosenAnswerDto.setQuestionnaireId("1673285684975054850");
        chosenAnswerDto.setRoleId("1668209171955138565");
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("1673285707301335041","1673285712967839747");
        mapList.add(map);
        chosenAnswerDto.setAnswer(mapList);

        String body = JSON.toJSONString(mapList);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/answer/submit")
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
    void testSubmitEmpty3(){
        ChosenAnswerDto chosenAnswerDto = new ChosenAnswerDto();
        chosenAnswerDto.setQuestionnaireId("1673285684975054850");
        chosenAnswerDto.setRoleId("1668209171955138565");
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("1673285707301335041", "1673285712967839747");
        mapList.add(map);
        chosenAnswerDto.setAnswer(mapList);

        String body = JSON.toJSONString(mapList);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/answer/submit")
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
    void testSubmit4() {
        // 创建一个模拟的ChosenAnswerDto对象作为输入参数
        ChosenAnswerDto chosenAnswerDto = new ChosenAnswerDto();
        chosenAnswerDto.setQuestionnaireId("1673285684975054850");
        chosenAnswerDto.setRoleId("1668209171955138565");

        // 创建一个包含两个问题回答的列表
        List<Map<String, String>> answerList = new ArrayList<>();

        // 第一个问题的回答
        Map<String, String> answer1 = new HashMap<>();
        answer1.put("questionId", "question1");
        answer1.put("optionId", "option1");
        answerList.add(answer1);

        // 第二个问题的回答
        Map<String, String> answer2 = new HashMap<>();
        answer2.put("questionId", "question2");
        answer2.put("optionId", "option2");
        answerList.add(answer2);

        chosenAnswerDto.setAnswer(answerList);

        // 将ChosenAnswerDto对象转换为JSON字符串
        String body = JSON.toJSONString(chosenAnswerDto);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/answer/submit")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
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
    void testReviewAnswer() {
        // 创建测试数据
        Answer answer = new Answer();
        answer.setId("1673133010426220545");

        // 将测试数据转换为 JSON 字符串
        String body = JSON.toJSONString(answer);

        // 执行测试
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/answer/review")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body).characterEncoding("utf-8"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            // 进行断言
            assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
            log.info(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSubmitChosenAnswer() {
        // 创建测试数据
        ChosenAnswerDto chosenAnswerDto = new ChosenAnswerDto();
        // 设置 chosenAnswerDto 的属性

        // 将测试数据转换为 JSON 字符串
        String body = JSON.toJSONString(chosenAnswerDto);

        // 执行测试
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/answer/submit")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body).characterEncoding("utf-8"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();

            // 进行断言
            assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
            log.info(mvcResult.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReview(){
        Answer answer = new Answer();
        answer.setId("1673133010426220545");

        String body = JSON.toJSONString(answer);
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/answer/review")
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

}
