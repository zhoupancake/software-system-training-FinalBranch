package com.systemtraining.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("questionnaire_info")
public class Questionnaire {
    @TableId
    private String id;
    private String name;
    private String comment;
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime creationDate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastUpdateDate;
    private String status;
    private String surveyObject;
    private String projectId;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    @TableLogic(value = "0", delval = "1")
    private String isDelete;

}
