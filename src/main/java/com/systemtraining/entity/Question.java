package com.systemtraining.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("question_info")
public class Question {
    @TableId
    private String id;
    private String name;
    private String content;
    private Integer answerCount;
    private String type;
    private String questionnaireId;
    private String isMust;
    private String isLink;
}
