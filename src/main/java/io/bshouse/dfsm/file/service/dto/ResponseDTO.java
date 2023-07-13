package io.bshouse.dfsm.file.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.*;

import static java.util.Objects.isNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    boolean status;
    List<String> errors;
    @JsonProperty("data")
    Object data;
    Integer totalPages;
    Integer page;
    Integer totalElements;
    Integer pageSize;

    public void addError(String message) {
        if (isNull(this.errors)) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(message);
    }

}
