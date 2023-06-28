package com.systemtraining.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("option_info")
public class Option {
    @TableId
    private String id;
    private String chooseTerm;
    private String questionId;
    private Integer score;
    private Integer personCount;
    private String linkQuestionId;

}
