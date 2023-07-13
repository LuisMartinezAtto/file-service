package io.bshouse.dfsm.file.service.service.auth;
import io.bshouse.dfsm.file.service.config.FeignConfiguration;
import io.bshouse.dfsm.file.service.service.auth.dto.ResponseVerifyTokenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="auth", url="${auth.verify.token}", configuration = FeignConfiguration.class)
public interface AuthServiceExternal {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseVerifyTokenDTO verifyToken(@RequestHeader("Authorization") String token);
}
