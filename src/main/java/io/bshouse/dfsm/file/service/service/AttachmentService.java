package io.bshouse.dfsm.file.service.service;
import io.bshouse.dfsm.file.service.dto.AttachmentResponseDTO;
import io.bshouse.dfsm.file.service.dto.ResponseDTO;
import io.bshouse.dfsm.file.service.map.MapperStructAttachment;
import io.bshouse.dfsm.file.service.model.Attachment;
import io.bshouse.dfsm.file.service.repository.AttachmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttachmentService {
    private final MapperStructAttachment mapperStructAnnouncement;
    private final AttachmentRepository attachmentRepository;


    public AttachmentService(MapperStructAttachment mapperStructAnnouncement, AttachmentRepository attachmentRepository) {
        this.mapperStructAnnouncement = mapperStructAnnouncement;
        this.attachmentRepository = attachmentRepository;
    }

    public List<AttachmentResponseDTO> getByElementId(Long elementId){
        return this.attachmentRepository.findByElementId(elementId).stream()
                .map(mapperStructAnnouncement::attachmentToAttachmentResponseDTO).collect(Collectors.toList());

    }
    public ResponseDTO getAll(Optional<Integer> page, Optional<Integer> pageSize){
        Integer pages = page.orElse(1) - 1;
        Integer pageSizes = pageSize.orElse(10);
        Pageable pageable = PageRequest.of(pages, pageSizes, Sort.Direction.DESC, "creationDate");
        Page<Attachment> attachments =  this.attachmentRepository.findAllAttachments(pageable);
        return ResponseDTO.builder()
                .data(attachments.stream().map(this.mapperStructAnnouncement::attachmentToAttachmentResponseDTO)
                        .collect(Collectors.toList()))
                .totalPages(Integer.valueOf(String.valueOf(attachments.getTotalPages())))
                .pageSize(pageSizes)
                .page(page.orElse(0))
                .totalElements(Integer.valueOf(String.valueOf(attachments.getTotalElements())))
                .status(true).build();

    }

}
