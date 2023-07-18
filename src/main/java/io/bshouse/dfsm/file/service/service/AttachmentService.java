package io.bshouse.dfsm.file.service.service;

import io.bshouse.dfsm.file.service.dto.AttachmentResponseDTO;
import io.bshouse.dfsm.file.service.dto.CreateAttachmentRequestDTO;
import io.bshouse.dfsm.file.service.dto.ResponseDTO;
import io.bshouse.dfsm.file.service.exception.AttachmentNotFoundException;
import io.bshouse.dfsm.file.service.map.MapperStructAttachment;
import io.bshouse.dfsm.file.service.model.Attachment;
import io.bshouse.dfsm.file.service.repository.AttachmentRepository;
import io.bshouse.dfsm.file.service.service.external.FileService;
import io.bshouse.dfsm.file.service.service.external.file.dto.FileResponseDTO;
import io.bshouse.dfsm.file.service.util.CustomMultipartFile;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttachmentService {
    private final MapperStructAttachment mapperStructAnnouncement;
    private final Logger logger = LoggerFactory.getLogger(AttachmentService.class);
    private final AttachmentRepository attachmentRepository;
    private final FileService fileService;


    public AttachmentService(MapperStructAttachment mapperStructAnnouncement, AttachmentRepository attachmentRepository, FileService fileService) {
        this.mapperStructAnnouncement = mapperStructAnnouncement;
        this.attachmentRepository = attachmentRepository;
        this.fileService = fileService;
    }

    public AttachmentResponseDTO getByElementId(Long elementId) throws AttachmentNotFoundException {
        return this.attachmentRepository.findByElementId(elementId).map(mapperStructAnnouncement::attachmentToAttachmentResponseDTO).orElseThrow(AttachmentNotFoundException::new);

    }
    public AttachmentResponseDTO getByAttachmentId(Long attachmentId) throws AttachmentNotFoundException {
        return this.attachmentRepository.findById(attachmentId)
                .map(mapperStructAnnouncement::attachmentToAttachmentResponseDTO).orElseThrow(AttachmentNotFoundException::new);

    }

    public ResponseDTO getAll(Optional<Integer> page, Optional<Integer> pageSize) {
        Integer pages = page.orElse(1) - 1;
        Integer pageSizes = pageSize.orElse(10);
        Pageable pageable = PageRequest.of(pages, pageSizes, Sort.Direction.DESC, "creationDate");
        Page<Attachment> attachments = this.attachmentRepository.findAllAttachments(pageable);
        return ResponseDTO.builder()
                .data(attachments.stream().map(this.mapperStructAnnouncement::attachmentToAttachmentResponseDTO)
                        .collect(Collectors.toList()))
                .totalPages(Integer.valueOf(String.valueOf(attachments.getTotalPages())))
                .pageSize(pageSizes)
                .page(page.orElse(0))
                .totalElements(Integer.valueOf(String.valueOf(attachments.getTotalElements())))
                .status(true).build();

    }

    public void createAttachment(CreateAttachmentRequestDTO createAttachmentRequestDTO) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Attachment attachment = this.mapperStructAnnouncement.createAttachmentRequestDTOToAttachment(createAttachmentRequestDTO);
        attachment.setCreationDate(new Date());
        attachment.setCreatedByUserId(Long.valueOf(securityContext.getAuthentication().getName()));
        FileResponseDTO fileResponseDTO = this.fileService.createFile(createAttachmentRequestDTO.getFile());
        attachment.setUrlSource(fileResponseDTO.getUrl());
        this.attachmentRepository.save(attachment);
    }

    public void deleteAttachment(Long attachmentId) {
        this.attachmentRepository.deleteById(attachmentId);
    }
}
