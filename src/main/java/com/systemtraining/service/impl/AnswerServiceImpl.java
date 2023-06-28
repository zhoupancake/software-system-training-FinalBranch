package com.systemtraining.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.systemtraining.dto.AnswerInfoDto;
import com.systemtraining.entity.Answer;
import com.systemtraining.mapper.AnswerMapper;
import com.systemtraining.service.AnswerService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {
    @Override
    public List<AnswerInfoDto> getAnswerInfoList(String projectId ,String username, int pageSize, int pageNum) {
        return this.baseMapper.getAnswerInfo(projectId, username, pageSize, (pageNum - 1) * pageSize);
    }
}
