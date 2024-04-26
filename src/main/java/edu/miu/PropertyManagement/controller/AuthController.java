package edu.miu.PropertyManagement.controller;

import edu.miu.PropertyManagement.entity.dto.request.LoginRequest;
import edu.miu.PropertyManagement.entity.dto.request.RegisterRequest;
import edu.miu.PropertyManagement.entity.dto.response.LoginResponse;
import edu.miu.PropertyManagement.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authenticate")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
         try {
            LoginResponse loginResponse = authService.login(loginRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password");
        }
    }


//    @PostMapping("/refreshToken")
//    public LoginResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
//        return authService.refreshToken(refreshTokenRequest);
//    }

}
