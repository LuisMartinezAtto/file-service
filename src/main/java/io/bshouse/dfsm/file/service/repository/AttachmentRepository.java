package io.bshouse.dfsm.file.service.repository;

import io.bshouse.dfsm.file.service.model.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Optional<Attachment> findByElementId(Long elementId);
    @Query("select a from Attachment a")
    Page<Attachment> findAllAttachments(Pageable pageable);
    Optional<Attachment> findByOriginalName(String originalName);
    Optional<Attachment> findById(Long attachmentId);
}
