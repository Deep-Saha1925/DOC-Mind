package com.deep.docmind.controller;

import com.deep.docmind.dto.ApiResponse;
import com.deep.docmind.dto.ChatRequestDto;
import com.deep.docmind.dto.ChatResponseDto;
import com.deep.docmind.entity.User;
import com.deep.docmind.service.RagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/chat")
@Tag(
        name = "Chat Management",
        description = "All chat related apis goes here."
)
@RequiredArgsConstructor
public class ChatController {

    private final RagService ragService;

    @PostMapping("/query")
    @Operation(summary = "Ask a question against all documents or a specific document with citations")
    public ResponseEntity<ApiResponse<ChatResponseDto>> askQuestion(
            @Valid @RequestBody ChatRequestDto chatRequestDto,
            Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();
        ChatResponseDto chatResponseDto = ragService.askQuestion(chatRequestDto, user);
        return ResponseEntity.ok(
                ApiResponse.
                        <ChatResponseDto>
                        builder()
                        .success(true)
                        .message(null)
                        .data(chatResponseDto)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    public ResponseEntity<String> chat(){
        return ResponseEntity.ok("CHATTING");
    }

}
