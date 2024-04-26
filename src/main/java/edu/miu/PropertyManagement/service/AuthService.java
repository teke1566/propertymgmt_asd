package edu.miu.PropertyManagement.service;

import edu.miu.PropertyManagement.entity.dto.request.LoginRequest;
import edu.miu.PropertyManagement.entity.dto.request.RegisterRequest;
import edu.miu.PropertyManagement.entity.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
   // public void activateUserAccount(long userId);

    void register(RegisterRequest registerRequest);
   // LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
