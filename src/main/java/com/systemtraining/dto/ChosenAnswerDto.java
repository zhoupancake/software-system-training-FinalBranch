package com.systemtraining.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChosenAnswerDto {
    private String questionnaireId;
    private String roleId;
    private List<Map<String, String >> answer;
}
