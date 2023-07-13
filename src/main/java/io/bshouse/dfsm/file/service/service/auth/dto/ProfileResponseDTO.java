package io.bshouse.dfsm.file.service.service.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("localeId")
    private String localeId;
    @JsonProperty("menus")
    List<MenuResponseDTO> menuResponseDTOList;
    @JsonProperty("creationUser")
    private String creationUser;
    @JsonProperty("creationDateProfile")
    private Date creationDateProfile;
    @JsonProperty("updateUser")
    private String updateUser;
    @JsonProperty("updatedDateProfile")
    private Date updatedDateProfile;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("profileCode")
    private String profileCode;
    @JsonProperty("userType")
    private String userType;
}
