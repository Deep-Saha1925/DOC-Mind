package com.deep.docmind.repository;

import com.deep.docmind.entity.DocumentMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentMetadataRepo extends JpaRepository<DocumentMetaData, UUID> {
}
