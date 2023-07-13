package io.bshouse.dfsm.file.service.map;

import io.bshouse.dfsm.file.service.dto.AttachmentResponseDTO;
import io.bshouse.dfsm.file.service.model.Attachment;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperStructAttachment {
    private final DozerBeanMapper dozerBeanMapper;

    public MapperStructAttachment(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    public AttachmentResponseDTO attachmentToAttachmentResponseDTO(Attachment attachment) {
        AttachmentResponseDTO announcementResponseDTO = dozerBeanMapper.map(attachment, AttachmentResponseDTO.class);
        return announcementResponseDTO;
    }
}
