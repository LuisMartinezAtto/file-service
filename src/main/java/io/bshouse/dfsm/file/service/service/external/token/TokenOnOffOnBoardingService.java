package io.bshouse.dfsm.file.service.service.external.token;
import io.bshouse.dfsm.file.service.config.FeignConfiguration;
import io.bshouse.dfsm.file.service.service.external.token.dto.TokenResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "token", url = "${onoffboarding.oauth.get.token}", configuration = FeignConfiguration.class)
public interface TokenOnOffOnBoardingService {
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenResponseDTO getToken(@RequestHeader("Authorization") String token, @RequestBody Map<String, ?> headerMap);
}
