package com.deep.docmind.controller;

import com.deep.docmind.dto.ApiResponse;
import com.deep.docmind.dto.DocumentResponseDto;
import com.deep.docmind.service.DocumentMetadataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/documents")
@Tag(
        name = "Document Management",
        description = "Endpoints for managing documents and vector embedding"
)
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentMetadataService documentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<DocumentResponseDto>> uploadDocument(
            @RequestParam("file")MultipartFile file
            ){

        DocumentResponseDto response = this.documentService.uploadAndProcess(file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<DocumentResponseDto>builder()
                                .success(true)
                                .data(response)
                                .timestamp(LocalDateTime.now())
                                .message("Document indexed successfully")
                                .build()
                );
    }

}
