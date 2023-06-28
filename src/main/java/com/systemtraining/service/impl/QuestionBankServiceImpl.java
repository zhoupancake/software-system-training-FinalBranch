package com.systemtraining.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.systemtraining.entity.QuestionBank;
import com.systemtraining.mapper.QuestionBankMapper;
import com.systemtraining.service.QuestionBankService;
import org.springframework.stereotype.Service;


@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService {
}
