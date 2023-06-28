package com.systemtraining.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.systemtraining.common.HttpResponseEntity;
import com.systemtraining.dto.QuestionDto;
import com.systemtraining.dto.QuestionStatisticDto;
import com.systemtraining.entity.Option;
import com.systemtraining.entity.Question;
import com.systemtraining.entity.QuestionBank;
import com.systemtraining.service.OptionService;
import com.systemtraining.service.QuestionBankService;
import com.systemtraining.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/question")
@Slf4j
@RequiredArgsConstructor // 使用Lombok生成一个构造函数，自动注入所有final字段
public class QuestionController {

    private final QuestionService questionService;
    private final OptionService optionService;
    private final QuestionBankService questionBankService;

    @PostMapping("/update")
    public HttpResponseEntity updateQuestion(@RequestBody QuestionDto questionDto) {
        log.error(questionDto.toString());
        LambdaQueryWrapper<Option> optionQueryWrapper = new LambdaQueryWrapper<>();
        optionQueryWrapper.eq(Option::getQuestionId, questionDto.getId());
        optionService.remove(optionQueryWrapper);
        Question question = Question.builder()
                .id(questionDto.getId())
                .name(questionDto.getName())
                .isMust(questionDto.getIsMust())
                .build();
        questionService.updateById(question);
        List<Option> options = questionDto.getOption();
        for (Option option : options) {
            option.setId(null);
            option.setQuestionId(questionDto.getId());
        }
        boolean success = optionService.saveBatch(options);
        return HttpResponseEntity.response(success, "编辑问题", null);
    }

    @PostMapping("/add")
    public HttpResponseEntity insertQuestion(@RequestBody Question question) {
        boolean success = questionService.save(question);
        return HttpResponseEntity.response(success, "添加问题", question.getId());
    }


    @PostMapping("/delete")
    public HttpResponseEntity removeQuestion(@RequestBody Question question) {
        LambdaQueryWrapper<Option> optionQueryWrapper = new LambdaQueryWrapper<>();
        optionService.remove(optionQueryWrapper.eq(Option::getQuestionId, question.getId()));
        boolean success = questionService.removeById(question);
        return HttpResponseEntity.response(success, "删除问题", null);
    }


    @PostMapping("/bank")
    public HttpResponseEntity listQuestionBanks() {
        List<QuestionBank> questionBankEntities = questionBankService.list();
        List<QuestionDto> questionDtoList = questionBankEntities.stream().map(e -> {
            List<Option> optionEntities = optionService.lambdaQuery()
                    .eq(Option::getQuestionId, e.getId())
                    .list();
            return QuestionDto.builder().type(e.getType())
                    .id(e.getId()).option(optionEntities)
                    .name(e.getName()).isMust("true").build();
        }).collect(Collectors.toList());
        boolean success = !questionDtoList.isEmpty();
        return HttpResponseEntity.response(success, "查询题库", questionDtoList);
    }

    @PostMapping("/link")
    public HttpResponseEntity getLinkedQuestion(@RequestBody Question questionEntity) {
        Question question = questionService.getById(questionEntity.getId());
        List<Option> optionEntities = optionService.lambdaQuery()
                .eq(Option::getQuestionId, questionEntity.getId())
                .list();

        QuestionDto questionDto = QuestionDto.builder().isMust(question.getIsMust())
                .name(question.getName()).type(question.getType())
                .option(optionEntities).id(question.getId()).build();
        return HttpResponseEntity.success("查询成功", questionDto);
    }


    @PostMapping("/statistic")
    public HttpResponseEntity getQuestionStatistic(@RequestBody Question questionEntity) {
        Question question = questionService.lambdaQuery()
                .eq(Question::getId, questionEntity.getId()).one();
        List<Option> optionEntities = optionService.lambdaQuery()
                .eq(Option::getQuestionId, question.getId())
                .list();
        QuestionStatisticDto statisticDto = QuestionStatisticDto.builder()
                .answerCount(question.getAnswerCount())
                .id(question.getId())
                .name(question.getName())
                .type(question.getType())
                .option(optionEntities).build();
        return HttpResponseEntity.success("查询成功", statisticDto);
    }


    @PostMapping("/all")
    public HttpResponseEntity getAllQuestions(@RequestBody Question question) {
        List<Question> questionEntities = questionService.lambdaQuery()
                .eq(Question::getQuestionnaireId, question.getQuestionnaireId())
                .list();
        List<QuestionDto> questionDtoList = questionEntities.stream().map(e -> {
            List<Option> options = optionService.lambdaQuery()
                    .eq(Option::getQuestionId, e.getId())
                    .list();
            return QuestionDto.builder().option(options)
                    .id(e.getId()).name(e.getName()).answerCount(e.getAnswerCount()).build();
        }).collect(Collectors.toList());
        boolean success = !questionDtoList.isEmpty();
        return HttpResponseEntity.response(success, "问卷问题查询", questionDtoList);
    }
}
