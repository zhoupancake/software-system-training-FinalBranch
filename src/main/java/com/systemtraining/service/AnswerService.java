package com.systemtraining.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.systemtraining.dto.AnswerInfoDto;
import com.systemtraining.entity.Answer;

import java.util.List;


public interface AnswerService extends IService<Answer> {

    List<AnswerInfoDto> getAnswerInfoList(String projectId,String username, int pageSize, int pageNum);
}
