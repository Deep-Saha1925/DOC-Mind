package com.deep.docmind.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequestDto {

    @NotBlank(message = "Question cannot be empty")
    private String question;

    private UUID documentId;
    private Integer topK;
    private double minSimilarity;
    private String conversationId;

}
