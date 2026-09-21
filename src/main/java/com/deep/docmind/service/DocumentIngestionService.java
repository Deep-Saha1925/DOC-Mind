package com.deep.docmind.service;

import com.deep.docmind.entity.DocumentMetaData;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentIngestionService {
    public int ingest(DocumentMetaData documentMetadata, List<Document> parsedDocs) {
        return 0;
    }
}
