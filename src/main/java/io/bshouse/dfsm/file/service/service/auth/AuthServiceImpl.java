package io.bshouse.dfsm.file.service.service.auth;
import io.bshouse.dfsm.file.service.constants.GeneralConstants;
import io.bshouse.dfsm.file.service.service.auth.dto.ResponseVerifyTokenDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthServiceExternal authServiceExternal;

    public AuthServiceImpl(AuthServiceExternal authServiceExternal) {
        this.authServiceExternal = authServiceExternal;
    }

    @Override
    public ResponseVerifyTokenDTO validateToken(String token){
        String bearer = GeneralConstants.AUTHORIZATION_BEARER_TOKEN + token;
        return this.authServiceExternal.verifyToken(bearer);
    }
}
