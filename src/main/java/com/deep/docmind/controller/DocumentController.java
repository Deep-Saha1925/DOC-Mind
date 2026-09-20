package com.deep.docmind.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/documents")
@Tag(
        name = "Document Management",
        description = "Endpoints for managing documents and vector embedding"
)
public class DocumentController {

    @PostMapping
    public ResponseEntity<String> uploadDocument(){
        return ResponseEntity.ok("Document Uploaded");
    }

}
