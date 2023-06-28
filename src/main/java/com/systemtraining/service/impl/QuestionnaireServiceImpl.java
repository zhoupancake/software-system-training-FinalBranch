package com.systemtraining.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.systemtraining.entity.Questionnaire;
import com.systemtraining.mapper.QuestionnaireMapper;
import com.systemtraining.service.QuestionnaireService;
import org.springframework.stereotype.Service;


@Service
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements QuestionnaireService {
}
