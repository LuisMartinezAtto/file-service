package io.bshouse.dfsm.file.service.service.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("localeId")
    private String localeId;
    @JsonProperty("module")
    private String module;
    @JsonProperty("menuCode")
    private String menuCode;
    @JsonProperty("role")
    private String role;
    @JsonProperty("children")
    private List<MenuResponseDTO> children;
    @JsonProperty("userType")
    private String userType;
}
