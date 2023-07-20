package io.bshouse.dfsm.file.service.config;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozenConfig {
    @Bean
    public DozerBeanMapper dozer() {
       return new DozerBeanMapper();
    }
}
