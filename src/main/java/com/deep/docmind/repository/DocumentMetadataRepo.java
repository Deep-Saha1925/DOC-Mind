package com.deep.docmind.repository;

import org.springframework.ai.document.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentMetadataRepo extends JpaRepository<DocumentMetadata, UUID> {
}
