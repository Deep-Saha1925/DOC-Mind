package com.deep.docmind.service;

import com.deep.docmind.dto.DocumentResponseDto;
import com.deep.docmind.entity.DocumentMetaData;
import com.deep.docmind.entity.DocumentStatus;
import com.deep.docmind.exception.DocumentProcessingException;
import com.deep.docmind.repository.DocumentMetadataRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentMetadata;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentMetadataService {

    private static final Logger log = LoggerFactory.getLogger(DocumentMetadataService.class);

    private final DocumentMetadataRepo documentMetadataRepo;
    private final JdbcTemplate jdbcTemplate;

    public DocumentResponseDto uploadAndProcess(MultipartFile file) {

        String filename = file.getOriginalFilename() != null ? file.getOriginalFilename() : "docuemnt";
        String contentType = file.getContentType() != null ? file.getContentType() : "application/octat-stream";

        // document meta data
        DocumentMetaData documentMetadata = DocumentMetadata
                .builder()
                .filename(filename)
                .contentType(contentType)
                .status(DocumentStatus.UPLOADING)
                .fileSize(file.getSize())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        documentMetadata = documentMetadataRepo.save(documentMetadata);
        List<Document> parsedDocs = null;
        int chunksCreated = 0;

        try {
            //parse the file
            parsedDocs = parserService.parse(file);

            //ingest service
            chunksCreated = ingestionService.ingest(documentMetadata, parsedDocs);
        } catch (
                DocumentProcessingException e
        ) {
            log.info("Document metadata deleting due to fail processing");
            documentMetadataRepo.delete(documentMetadata);
            throw e;
        }


        //documentMetadata.setTotalChunks(chunksCreated);
        //save the document metadata


        return DocumentResponseDto.builder()
                .id(documentMetadata.getId())
                .fileName(documentMetadata.getFilename())
                .fileSize(documentMetadata.getFileSize())
                .chunksCreated(chunksCreated)
                .status(documentMetadata.getStatus())
                .message("Document successfully processed and indexed.")
                .userId(user.getId())
                .build();

    }
}
