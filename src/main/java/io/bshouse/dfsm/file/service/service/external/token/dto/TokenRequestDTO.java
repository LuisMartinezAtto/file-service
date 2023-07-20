package io.bshouse.dfsm.file.service.service.external.token.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenRequestDTO {
    @JsonProperty("grant_type")
    private String grantType;
    @JsonProperty("username")
    private String userName;
    private String password;
}
