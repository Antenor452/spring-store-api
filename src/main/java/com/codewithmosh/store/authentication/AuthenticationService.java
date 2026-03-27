package com.codewithmosh.store.authentication;


import com.codewithmosh.store.authentication.config.JwtConfig;
import com.codewithmosh.store.authentication.dtos.JwtResponse;
import com.codewithmosh.store.authentication.dtos.LoginRequest;
import com.codewithmosh.store.exceptions.InvalidAuthenticationCredentialsException;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.users.User;
import com.codewithmosh.store.users.UserDto;
import com.codewithmosh.store.users.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {


    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserService userService;


    public JwtResponse login(LoginRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userService.getUserByEmail(request.getEmail());
        var accessJwt = jwtService.generateAccessToken(user);
        var refreshJwt = jwtService.generateRefreshToken(user);


        var cookie = new Cookie("refreshToken", refreshJwt.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);


        return new JwtResponse(accessJwt.toString());
    }

    public JwtResponse refreshToken(String refreshToken) {
        var refreshJwt = jwtService.parseToken(refreshToken);

        if (refreshJwt == null || refreshJwt.isExpired()) {
            throw new InvalidAuthenticationCredentialsException();
        }

        var user = userService.getUserById(refreshJwt.getUserId());

        var accessJwt = jwtService.generateAccessToken(user);

        return new JwtResponse(accessJwt.toString());
    }

    public UserDto me() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (String) authentication.getPrincipal();
        var user = userService.getUserById(Long.valueOf(userId));
        return userMapper.toDto(user);
    }

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (String) authentication.getPrincipal();
        System.out.println("User Id  = " + userId);
        return userService.getUserById(Long.valueOf(userId));

    }
}
