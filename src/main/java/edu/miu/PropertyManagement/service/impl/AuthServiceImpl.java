package edu.miu.PropertyManagement.service.impl;

import edu.miu.PropertyManagement.entity.Role;
import edu.miu.PropertyManagement.entity.User;
import edu.miu.PropertyManagement.entity.dto.request.LoginRequest;
import edu.miu.PropertyManagement.entity.dto.request.RegisterRequest;
import edu.miu.PropertyManagement.entity.dto.response.LoginResponse;
import edu.miu.PropertyManagement.enums.Roles;
import edu.miu.PropertyManagement.repository.RoleRepo;
import edu.miu.PropertyManagement.repository.UserRepository;
import edu.miu.PropertyManagement.service.AuthService;
import edu.miu.PropertyManagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    @Autowired
    private final UserRepository userRepo;
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication result = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(result.getName());
            final String accessToken = jwtUtil.generateToken(userDetails);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            long userId = userRepo.findByEmail(userDetails.getUsername()).getId();
            var loginResponse = new LoginResponse(accessToken, roles, userId);
            return loginResponse;
        } catch (BadCredentialsException e) {
            System.out.println("ISSUE" + e.getMessage());
            throw new BadCredentialsException(e.getMessage());

        }
    }


    @Override
    public void register(RegisterRequest registerRequest) {
        try {
            User user = modelMapper.map(registerRequest, User.class);
            user.setName(Optional.ofNullable(registerRequest.getName()).orElse("Default Name"));
            Roles roleValue = registerRequest.getIsOwner() ? Roles.OWNER : Roles.CUSTOMER;
            Role role = roleRepo.findByRole(roleValue);
            user.setRole(Collections.singletonList(role));
            user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
           // user.setActive(false); // set the new user's status as inactive

            userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}

