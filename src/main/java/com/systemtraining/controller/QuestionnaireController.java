package com.systemtraining.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.systemtraining.common.HttpResponseEntity;
import com.systemtraining.dto.QuestionDto;
import com.systemtraining.dto.QuestionStatisticDto;
import com.systemtraining.dto.SameQuestionDto;
import com.systemtraining.entity.Option;
import com.systemtraining.entity.Question;
import com.systemtraining.entity.Questionnaire;
import com.systemtraining.service.OptionService;
import com.systemtraining.service.QuestionService;
import com.systemtraining.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questionnaire")
@RequiredArgsConstructor // lombok will generate a constructor that autowires all final fields
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;  // 问卷服务类

    private final QuestionService questionService;  // 问题服务类

    private final OptionService optionService;  // 选项服务类
    // 修改问卷
    @PostMapping("/update")
    public HttpResponseEntity modifyQuestionnaire(@RequestBody Questionnaire questionnaire) {
        return HttpResponseEntity.response(questionnaireService.updateById(questionnaire), "修改问卷", null);
    }

    // 删除问卷
    @PostMapping("/delete")
    public HttpResponseEntity deleteQuestionnaire(@RequestBody Questionnaire questionnaire) {
        LambdaQueryWrapper<Questionnaire> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Questionnaire::getStatus, "1");
        queryWrapper.eq(Questionnaire::getId, questionnaire.getId());
        return HttpResponseEntity.response(questionnaireService.remove(queryWrapper), "删除问卷", null);
    }

    // 发布问卷
    @PostMapping("/release")
    public HttpResponseEntity releaseQuestionnaire(@RequestBody Questionnaire questionnaire) {
        questionnaire.setStatus("1");
        questionnaire.setStartTime(LocalDateTime.now());
        return HttpResponseEntity.response(questionnaireService.updateById(questionnaire), "发布问卷", null);
    }

    // 关闭问卷
    @PostMapping("/close")
    public HttpResponseEntity closeQuestionnaire(@RequestBody Questionnaire questionnaire) {
        return HttpResponseEntity.response(questionnaireService.updateById(questionnaire), "关闭问卷", null);
    }

    // 添加问卷
    @PostMapping("/insert")
    public HttpResponseEntity insertQuestionnaire(@RequestBody Questionnaire questionnaire) {
        boolean bool = questionnaireService.save(questionnaire);
        return HttpResponseEntity.response(bool, "新增问卷", questionnaire.getId());
    }

    // 根据项目获取问卷列表
    @PostMapping("/list")
    public HttpResponseEntity listQuestionnaire(@RequestBody Questionnaire questionnaire) {
        List<Questionnaire> list = questionnaireService.query()
                .eq("project_id", questionnaire.getProjectId())
                .list();
        boolean bool = !list.isEmpty();
        return HttpResponseEntity.response(bool, "查询问卷", list);
    }

    // 获取当前问卷的所有题目
    @PostMapping("/questions")
    public HttpResponseEntity questions(@RequestBody Questionnaire questionnaire) {
        List<Question> questions = questionService.lambdaQuery()
                .eq(Question::getQuestionnaireId, questionnaire.getId())
                .ne(Question::getIsLink, '1')
                .list();
        List<QuestionDto> dtoList = questions.stream().map(e -> {
            List<Option> options = optionService.lambdaQuery()
                    .eq(Option::getQuestionId, e.getId())
                    .list();
            return QuestionDto.builder().option(options).isMust(e.getIsMust()).type(e.getType())
                    .id(e.getId()).name(e.getName()).build();
        }).collect(Collectors.toList());
        boolean bool = !dtoList.isEmpty();
        return HttpResponseEntity.response(bool, "问卷问题查询", dtoList);
    }

    // 生成问卷链接
    @PostMapping("/link")
    public HttpResponseEntity checkLinkOfQuestionnaire(@RequestBody Questionnaire questionnaire) {
        Questionnaire questionnaire1 = questionnaireService.lambdaQuery()
                .eq(Questionnaire::getStatus, "1")
                .eq(Questionnaire::getId, questionnaire.getId())
                .one();
        return HttpResponseEntity.response(questionnaire1 != null, "链接", questionnaire1);
    }

    @PostMapping("/same")
    public HttpResponseEntity querySameQuestion(@RequestBody Question question){
        Questionnaire questionnaire1 = questionnaireService.lambdaQuery()
                .eq(Questionnaire::getId, question.getQuestionnaireId())
                .one();
        List<Questionnaire> questionnaireEntities = questionnaireService.lambdaQuery()
                .eq(Questionnaire::getProjectId, questionnaire1.getProjectId())
                .ne(Questionnaire::getId, questionnaire1.getId())
                .list();
        List<SameQuestionDto> arrayList = new ArrayList<>();
        for (Questionnaire questionnaire :questionnaireEntities) {
            List<Question> questionEntities = questionService.lambdaQuery()
                    .eq(Question::getQuestionnaireId, questionnaire.getId())
                    .list();
            List<Question> collect = questionEntities.stream().filter(e -> {
                        if (e.getName() != null){
                            return e.getName().equals(question.getName());
                        } else {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
            if (!collect.isEmpty()){
                List<SameQuestionDto> questionDtos = collect.stream().map(e -> SameQuestionDto.builder()
                        .questionnaireName(questionnaire.getName())
                        .questionId(e.getId())
                        .count(e.getAnswerCount())
                        .build()).collect(Collectors.toList());
                arrayList.addAll(questionDtos);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", arrayList);
        map.put("total", arrayList.size());

        return HttpResponseEntity.success("相同问题", map);
    }

    @PostMapping("/relate") // 使用@PostMapping注解的方法，表示这个方法用来处理发送到"/relate" URI的POST HTTP请求。
    public HttpResponseEntity relateStatistics(@RequestBody Question questionEntity){
        // 定义处理请求的方法，该方法接受一个请求体为 Question 对象的 HTTP 请求。

        Question question = questionService.lambdaQuery() // 通过 questionService 查询特定问题的详细信息。
                .eq(Question::getId, questionEntity.getId()) // 查找和请求中的 questionEntity ID 匹配的问题实体。
                .one(); // 获取查询结果中的第一个（也应该是唯一的）问题实体。

        Questionnaire questionnaire = questionnaireService.lambdaQuery() // 通过 questionnaireService 查询特定问卷的详细信息。
                .eq(Questionnaire::getId, question.getQuestionnaireId()) // 查找和上面查询到的问题实体所关联的问卷实体。
                .one(); // 获取查询结果中的第一个（也应该是唯一的）问卷实体。

        List<Questionnaire> questionnaireList = questionnaireService.lambdaQuery() // 通过 questionnaireService 查询特定项目的所有问卷信息。
                .eq(Questionnaire::getProjectId, questionnaire.getProjectId()) // 查找和上面查询到的问卷实体所关联的项目的所有问卷。
                .list(); // 获取查询结果，可能是多个问卷实体。

        int answerCount= 0; // 初始化答案数量为 0。

        List<Option> optionList = optionService.lambdaQuery() // 通过 optionService 查询特定问题的所有选项信息。
                .eq(Option::getQuestionId, question.getId()) // 查找和上面查询到的问题实体所关联的所有选项。
                .list(); // 获取查询结果，可能是多个选项实体。

        System.out.println("optionList: " + optionList.toString()); // 打印所有选项的信息。

        for (Questionnaire questionnaireEntity : questionnaireList) { // 遍历项目中的所有问卷。

            System.out.println("Processing questionnaire: " + questionnaireEntity.getId()); // 打印正在处理的问卷的 ID。

            List<Question> questionEntities = questionService.lambdaQuery() // 通过 questionService 查询特定问卷的所有问题信息。
                    .eq(Question::getName, question.getName()) // 查找和初始查询到的问题名称相同的问题。
                    .eq(Question::getQuestionnaireId, questionnaireEntity.getId()) // 查找在当前正在处理的问卷中的问题。
                    .list(); // 获取查询结果，可能是多个问题实体。

            for (Question question1 : questionEntities) { // 遍历问卷中的所有问题。

                System.out.println("Found a question : " + question1.getId()); // 打印正在处理的问题的 ID。

                List<Option> optionEntities = optionService.lambdaQuery() // 通过 optionService 查询遍历的问题的所有选项信息。
                        .eq(Option::getQuestionId, question1.getId()) // 查找和当前正在处理的问题所关联的所有选项。
                        .list(); // 获取查询结果，可能是多个选项实体。

                System.out.println("optionEntities: " + optionEntities.toString()); // 打印所有选项的信息。

                answerCount += question1.getAnswerCount(); // 将当前问题的答案数量加到总答案数量上。

                for (Option falgEntity : optionList) { // 遍历标杆问题的所有选项。标杆问题是对照物，遍历的问题和它进行对比 看一样不一样

                    System.out.println("falgEntity.getChooseTerm() " + falgEntity.toString()); // 打印正在处理的选项的信息。

                    int addCount = falgEntity.getPersonCount(); // 获取该选项的人数。

                    for (Option option : optionEntities) { // 遍历当前问题的所有选项。

                        System.out.println("option: " + option.toString()); // 打印当前选项的信息。

                        if (option.getChooseTerm().equals(falgEntity.getChooseTerm())&&
                                !Objects.equals(option.getId(), falgEntity.getId())) { // 如果当前选项和正在处理的选项一样。
                            addCount += option.getPersonCount(); // 将当前选项的人数加到 addCount 上。
                            System.out.println("Incrementing count for option " + option.getChooseTerm() + ": " + option.getPersonCount()); // 打印当前选项和增加的人数。
                        }

                    }
                    falgEntity.setPersonCount(addCount); // 设置正在处理的选项的人数为 addCount。
                    System.out.println("Set person count for option " + falgEntity.getChooseTerm() + ": " + addCount); // 打印设置后的选项和人数。

                    System.out.println("====================================="); // 打印一行分隔符，用于在日志中分隔不同的处理环节。
                }
            }
        }

        QuestionStatisticDto statisticDto = QuestionStatisticDto.builder() // 使用 builder 模式构造一个新的 QuestionStatisticDto 对象。
                .id(question.getId()) // 设置 ID 为问题的 ID。
                .answerCount(answerCount) // 设置答案数量为 answerCount。
                .name(question.getName()) // 设置名称为问题的名称。
                .option(optionList) // 设置选项为选项列表。
                .type(question.getType()) // 设置类型为问题的类型。
                .build(); // 构造 QuestionStatisticDto 对象。

        return HttpResponseEntity.success("相同问题统计", statisticDto); // 返回一个包含统计结果的 HTTP 响应实体。

    }



}
