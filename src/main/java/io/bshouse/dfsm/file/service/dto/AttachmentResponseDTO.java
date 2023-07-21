package io.bshouse.dfsm.file.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.bshouse.dfsm.file.service.model.TypeFile;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class AttachmentResponseDTO {
    private Long id;
    private String originalName;
    private String mimeType;
    private String elementId;
    private String urlSource;
    @Enumerated(EnumType.STRING)
    private TypeFile typeFile;
    private Integer fileSize;
}
