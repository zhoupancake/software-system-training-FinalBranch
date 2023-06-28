package com.systemtraining.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerInfoDto {
    private String id;
    private String questionnaireName;
    private String questionnaireId;
    private String username;
    private LocalDateTime answerTime;
}
