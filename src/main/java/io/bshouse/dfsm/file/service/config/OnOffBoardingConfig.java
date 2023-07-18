package io.bshouse.dfsm.file.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "auth.on.off.boarding")
public class OnOffBoardingConfig {
    private String key;
    private String password;
    private String userName;
    private String grantType;
}
