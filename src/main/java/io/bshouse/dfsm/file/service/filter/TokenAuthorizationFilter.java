package io.bshouse.dfsm.file.service.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bshouse.dfsm.file.service.service.auth.AuthService;
import io.bshouse.dfsm.file.service.service.auth.dto.ProfileResponseDTO;
import io.bshouse.dfsm.file.service.service.auth.dto.ResponseVerifyTokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class TokenAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final AuthService authService;
    private final Logger logger = LoggerFactory.getLogger(TokenAuthorizationFilter.class);

    public TokenAuthorizationFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestTokenHeader = request.getHeader(HEADER);
        if (requestTokenHeader == null) {
            chain.doFilter(request, response);
            return;
        }
        if (requestTokenHeader.startsWith(PREFIX)) {
            String jwtToken = requestTokenHeader.substring(7);
            logger.info("Begin verify token : " + jwtToken);
            try {
                ResponseVerifyTokenDTO responseDTO = this.authService.validateToken(jwtToken);
                ////add email/userId to request the body of the next controller if needed
                request.setAttribute("email", responseDTO.getDataList().get("email"));
                request.setAttribute("userId", responseDTO.getDataList().get("userId"));
                logger.info(String.format("End Verify token response with body: %s", new ObjectMapper().writeValueAsString(responseDTO.isStatus())));
                Authentication authentication = new UsernamePasswordAuthenticationToken(isNull(responseDTO.getDataList().get("userId"))? "0" : responseDTO.getDataList().get("userId").toString(), null, getSimpleGrantedAuthorityList(responseDTO.getDataList()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                logger.error("Error validate Token  ", e);
            }
        }
        chain.doFilter(request, response);
    }

    private List<SimpleGrantedAuthority> getSimpleGrantedAuthorityList(Map<String, Object> dataList) throws IOException {
        byte[] json = new ObjectMapper().writeValueAsBytes(dataList.get("profiles"));
        TypeReference<ArrayList<ProfileResponseDTO>> typeRef = new TypeReference<>() {
        };
        List<ProfileResponseDTO> profileResponseDTOList = new ObjectMapper().readValue(json, typeRef);
        return profileResponseDTOList.stream().map(profileResponseDTO -> new SimpleGrantedAuthority(profileResponseDTO.getProfileCode())).collect(Collectors.toList());
    }
}
