package io.bshouse.dfsm.file.service.service.external.file.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class FileResponseDTO {
    @JsonProperty("url")
    private String url;
    @JsonProperty("code")
    private Long code;
    @JsonProperty("mensaje")
    private String message;
}
