package com.systemtraining.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.systemtraining.entity.ChosenAnswer;
import com.systemtraining.mapper.ChosenAnswerMapper;
import com.systemtraining.service.ChosenAnswerService;
import org.springframework.stereotype.Service;


@Service
public class ChosenAnswerServiceImpl extends ServiceImpl<ChosenAnswerMapper, ChosenAnswer> implements ChosenAnswerService {
}
