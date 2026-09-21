package com.deep.docmind.service;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DocumentParserService {
    public List<Document> parse(MultipartFile file) {
        return null;
    }
}
