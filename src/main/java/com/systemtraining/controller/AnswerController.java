package com.systemtraining.controller;

import com.systemtraining.common.HttpResponseEntity;
import com.systemtraining.dto.AnswerInfoDto;
import com.systemtraining.dto.ChosenAnswerDto;
import com.systemtraining.entity.*;
import com.systemtraining.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answer")
@Slf4j
@RequiredArgsConstructor
public class AnswerController {

    private final OptionService optionService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final AnswerLinkService answerLinkService;
    private final ChosenAnswerService chosenAnswerService;

    @PostMapping("/list")
    public HttpResponseEntity list(@RequestBody Map<String, Object> requestMap) {
        Integer pageNum = (Integer) requestMap.get("pageNum");
        Integer pageSize = (Integer) requestMap.get("pageSize");
        String username = (String) requestMap.get("username");
        String projectId = (String) requestMap.get("projectId");
        List<AnswerInfoDto> answerInfoList = answerService.getAnswerInfoList(projectId, username, pageSize, pageNum);
        boolean success = !answerInfoList.isEmpty();
        return HttpResponseEntity.response(success, "查询", answerInfoList);
    }

    // 提交答案的请求处理方法
    @PostMapping("/submit")
    @Transactional
    public HttpResponseEntity submit(@RequestBody ChosenAnswerDto chosenAnswerDto) {
        Answer answer = Answer.builder()
                .questionnaireId(chosenAnswerDto.getQuestionnaireId()) // 设置答案的问卷ID
                .roleId(chosenAnswerDto.getRoleId()) // 设置答案的角色ID
                .answerTime(LocalDateTime.now()) // 设置答案的提交时间为当前时间
                .build();

        boolean success = answerService.save(answer); // 调用AnswerService的方法保存答案实体
        List<Map<String, String>> chosenAnswers = chosenAnswerDto.getAnswer(); // 获取选定的答案列表
        Map<String, Integer> questionAnswerCountMap = new HashMap<>(); // 创建问题-答案数量的映射

        for (Map<String, String> chosenAnswer : chosenAnswers) {
            String questionId = chosenAnswer.get("questionId"); // 获取选定答案的问题ID
            String optionId = chosenAnswer.get("optionId"); // 获取选定答案的选项ID
            Option selectedOption = optionService.getById(optionId); // 根据选项ID获取选项实体
            log.info(selectedOption.toString()); // 输出选项实体的信息
            selectedOption.setPersonCount(selectedOption.getPersonCount() + 1); // 选项的人数加1
            optionService.updateById(selectedOption); // 更新选项实体
            questionAnswerCountMap.put(questionId, 1); // 将问题ID和答案数量1添加到映射中

            AnswerLink answerLink = AnswerLink.builder()
                    .questionId(questionId) // 设置答案链接的问题ID
                    .userId(chosenAnswerDto.getRoleId()) // 设置答案链接的用户ID
                    .answerId(answer.getId()) // 设置答案链接的答案ID
                    .build();
            answerLinkService.save(answerLink); // 保存答案链接实体

            ChosenAnswer chosenAnswerEntity = ChosenAnswer.builder()
                    .linkId(answerLink.getId()) // 设置选定答案的链接ID
                    .optionId(optionId) // 设置选定答案的选项ID
                    .build();
            chosenAnswerService.save(chosenAnswerEntity); // 保存选定答案实体
        }

        for (String questionId : questionAnswerCountMap.keySet()) {
            Question question = questionService.getById(questionId); // 根据问题ID获取问题实体
            question.setAnswerCount(question.getAnswerCount() + 1); // 问题的答案数量加1
            questionService.updateById(question); // 更新问题实体
        }

        return HttpResponseEntity.response(success, "提交", null); // 返回HttpResponseEntity对象
    }

    // 回看答案的请求处理方法
    @PostMapping("/review")
    public HttpResponseEntity review(@RequestBody Answer reviewAnswer) {
        Answer answer = answerService.lambdaQuery()
                .eq(Answer::getId, reviewAnswer.getId()).one(); // 根据答案ID获取答案实体
        List<AnswerLink> answerLinks = answerLinkService.lambdaQuery()
                .eq(AnswerLink::getAnswerId, answer.getId()).list(); // 根据答案ID获取答案链接列表
        List<Map<String, Object>> chosenAnswers = answerLinks.stream().map(answerLink -> {
            List<ChosenAnswer> chosenAnswerEntities = chosenAnswerService.lambdaQuery()
                    .eq(ChosenAnswer::getLinkId, answerLink.getId()).list(); // 根据答案链接ID获取选定答案列表
            Map<String, Object> chosenAnswerMap = new HashMap<>();
            for (ChosenAnswer chosenAnswerEntity : chosenAnswerEntities) {
                chosenAnswerMap.put("questionId", answerLink.getQuestionId()); // 设置选定答案的问题ID
                chosenAnswerMap.put("optionId", chosenAnswerEntity.getOptionId()); // 设置选定答案的选项ID
            }
            return chosenAnswerMap;
        }).collect(Collectors.toList()); // 将选定答案列表转换为Map集合
        boolean hasChosenAnswers = !chosenAnswers.isEmpty(); // 判断选定答案列表是否为空
        return HttpResponseEntity.response(hasChosenAnswers, "回看", chosenAnswers); // 返回HttpResponseEntity对象
    }
}
