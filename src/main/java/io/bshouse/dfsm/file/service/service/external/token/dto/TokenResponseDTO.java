package io.bshouse.dfsm.file.service.service.external.token.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TokenResponseDTO {
    @JsonProperty("access_token")
    private String accesToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_in")
    private Integer expiresIn;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("rfc")
    private Object rfc;
    @JsonProperty("jti")
    private String jti;
}
