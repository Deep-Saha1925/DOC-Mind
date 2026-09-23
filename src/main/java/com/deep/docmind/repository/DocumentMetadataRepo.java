package com.deep.docmind.repository;

import com.deep.docmind.entity.DocumentMetaData;
import com.deep.docmind.entity.DocumentStatus;
import com.deep.docmind.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentMetadataRepo extends JpaRepository<DocumentMetaData, UUID> {

    List<DocumentMetaData> findByStatus(DocumentStatus status);

    List<DocumentMetaData> findAllByOrderByCreatedAtDesc();

    List<DocumentMetaData> findByUserOrderByCreatedAtDesc(User user);

    Optional<DocumentMetaData> findByIdAndUser(UUID id, User user);
}
