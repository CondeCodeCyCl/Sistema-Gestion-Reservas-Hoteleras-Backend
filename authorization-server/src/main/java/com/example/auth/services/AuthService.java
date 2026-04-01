package com.example.auth.services;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse autenticar(LoginRequest request) throws Exception;
}