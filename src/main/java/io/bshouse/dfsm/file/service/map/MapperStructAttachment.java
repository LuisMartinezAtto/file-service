package io.bshouse.dfsm.file.service.map;

import io.bshouse.dfsm.file.service.dto.AttachmentResponseDTO;
import io.bshouse.dfsm.file.service.dto.CreateAttachmentRequestDTO;
import io.bshouse.dfsm.file.service.model.Attachment;
import io.bshouse.dfsm.file.service.model.TypeFile;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperStructAttachment {
    private final DozerBeanMapper dozerBeanMapper;

    public MapperStructAttachment(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    public AttachmentResponseDTO attachmentToAttachmentResponseDTO(Attachment attachment) {
        return dozerBeanMapper.map(attachment, AttachmentResponseDTO.class);
    }
    public Attachment createAttachmentRequestDTOToAttachment(CreateAttachmentRequestDTO createAttachmentRequestDTO) {
        return  dozerBeanMapper.map(createAttachmentRequestDTO, Attachment.class);
    }
}
