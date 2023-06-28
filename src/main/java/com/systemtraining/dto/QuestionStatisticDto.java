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
public class QuestionStatisticDto {
    private String id;
    private String name;
    private String type;
    private Integer answerCount;
    private List<Option> option;
}
