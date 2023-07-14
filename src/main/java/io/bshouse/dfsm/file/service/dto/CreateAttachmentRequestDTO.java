package io.bshouse.dfsm.file.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CreateAttachmentRequestDTO {
    private String originalName;
    private String mimeType;
    private String elementId;
    private String typeFile;
}
