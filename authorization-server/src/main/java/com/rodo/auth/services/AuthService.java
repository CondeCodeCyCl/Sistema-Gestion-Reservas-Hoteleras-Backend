package com.rodo.auth.services;

import com.rodo.auth.dto.LoginRequest;
import com.rodo.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse autenticar(LoginRequest request) throws Exception;
}
