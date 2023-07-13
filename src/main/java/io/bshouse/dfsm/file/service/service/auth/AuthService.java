package io.bshouse.dfsm.file.service.service.auth;

import io.bshouse.dfsm.file.service.service.auth.dto.ResponseVerifyTokenDTO;

public interface AuthService {
    ResponseVerifyTokenDTO validateToken(String token);
}
