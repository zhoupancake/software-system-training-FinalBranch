package com.systemtraining.dto;

import com.systemtraining.entity.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private String id;
    private String name;
    private String isMust;
    private String type;
    private List<Option> option;
    private Integer answerCount;
}
