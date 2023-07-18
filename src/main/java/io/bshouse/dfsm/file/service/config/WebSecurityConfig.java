package io.bshouse.dfsm.file.service.config;
import io.bshouse.dfsm.file.service.filter.TokenAuthenticationEntryPoint;
import io.bshouse.dfsm.file.service.filter.TokenAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final TokenAuthorizationFilter jwtAuthorizationFilter;
    public WebSecurityConfig(TokenAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             TokenAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .addFilterAfter(this.jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
               // .antMatchers(AttachmentController.BASE_URL,AttachmentController.BASE_URL + AttachmentController.ENDPOINT_GET_BY_ELEMENT_ID).permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(List.of("HEAD",
                "GET", "POST", "PUT", "DELETE","OPTIONS", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("script-src 'self'");
        return http.build();
    }*/
}
