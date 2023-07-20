package io.bshouse.dfsm.file.service.service.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bshouse.dfsm.file.service.service.external.token.TokenOnOffOnBoardingService;
import io.bshouse.dfsm.file.service.service.external.token.dto.TokenResponseDTO;
import io.bshouse.dfsm.file.service.util.AuthBasicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokeService {
    public static final String BEARER_AUTHENTICATION = "Bearer ";
    private Logger logger = LoggerFactory.getLogger(TokeService.class);
    @Value("${onoffboarding.oauth.get.token}")
    private String tokenServiceEndpoint;
    private TokenOnOffOnBoardingService tokenOnOffOnBoardingService;
    private final AuthBasicUtil authBasicUtil;

    public TokeService(TokenOnOffOnBoardingService tokenOnOffOnBoardingService, AuthBasicUtil authBasicUtil) {
        this.tokenOnOffOnBoardingService = tokenOnOffOnBoardingService;
        this.authBasicUtil = authBasicUtil;
    }

    public TokenResponseDTO getToken() {
        this.logger.info("calling getToken  endpoint " + tokenServiceEndpoint + authBasicUtil.getRequestBody());
        TokenResponseDTO tokenResponseDTO = this.tokenOnOffOnBoardingService.getToken(authBasicUtil.getAuthBasicKey(), authBasicUtil.getRequestBody());
        this.logger.info("response getToken  endpoint " + tokenServiceEndpoint + tokenResponseDTO);
        return tokenResponseDTO;
    }
}
