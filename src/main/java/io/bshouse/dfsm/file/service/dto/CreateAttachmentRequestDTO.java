package io.bshouse.dfsm.file.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/*@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)*/
@Getter
@Setter
public class CreateAttachmentRequestDTO {
    @NotNull(message = "originalName cannot be null")
    private String originalName;
    @NotNull(message = "mimeType cannot be null")
    private String mimeType;
    @NotNull(message = "elementId cannot be null")
    private String elementId;
    @NotNull(message = "typeFile cannot be null")
    private String typeFile;
    @NotNull(message = "file cannot be null")
    private MultipartFile file;
}
