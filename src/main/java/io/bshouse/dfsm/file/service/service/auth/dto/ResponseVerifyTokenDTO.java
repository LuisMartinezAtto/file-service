package io.bshouse.dfsm.file.service.service.auth.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class ResponseVerifyTokenDTO {
    boolean status;
    @JsonProperty("errors")
    List<String> errors;
    @JsonProperty("data")
    Map<String, Object> dataList;
    private String jwt;
}
