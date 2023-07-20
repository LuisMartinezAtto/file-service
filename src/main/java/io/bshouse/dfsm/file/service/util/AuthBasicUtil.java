package io.bshouse.dfsm.file.service.util;

import io.bshouse.dfsm.file.service.config.OnOffBoardingConfig;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AuthBasicUtil {
    private String authentication = "Basic ";
    private final OnOffBoardingConfig onOffBoardingConfig;

    public AuthBasicUtil(OnOffBoardingConfig onOffBoardingConfig) {
        this.onOffBoardingConfig = onOffBoardingConfig;
    }

    public String getAuthBasicKey() {
        return authentication + onOffBoardingConfig.getKey();
    }

    public HashMap getRequestBody() {
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("password", onOffBoardingConfig.getPassword());
        requestBody.put("username", onOffBoardingConfig.getUserName());
        requestBody.put("grant_type", onOffBoardingConfig.getGrantType());
        return requestBody;
    }
}
