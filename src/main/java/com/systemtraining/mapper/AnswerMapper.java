package com.systemtraining.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.systemtraining.dto.AnswerInfoDto;
import com.systemtraining.entity.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {
    @Select("select answer_info.id, questionnaire_info.name questionnaire_name,questionnaire_info.id questionnaire_id, username, answer_time from answer_info, user_info, questionnaire_info" +
            " where answer_info.questionnaire_id = questionnaire_info.id and role_id = user_info.id and questionnaire_info.project_id = #{projectId} " +
            "and username like CONCAT('%', #{username}, '%') limit #{pageNum}, #{pageSize};")
    List<AnswerInfoDto> getAnswerInfo(String projectId, String username, int pageSize, int pageNum);
}
