package com.codewithmosh.store.authentication;


import com.codewithmosh.store.authentication.dtos.JwtResponse;
import com.codewithmosh.store.authentication.dtos.LoginRequest;
import com.codewithmosh.store.users.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public JwtResponse login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        return authenticationService.login(request, response);
    }

    @GetMapping("/me")
    public UserDto me() {
 
        return authenticationService.me();
    }


    @PostMapping("/refresh")
    public JwtResponse refresh(
            @CookieValue(value = "refreshToken") String refreshToken
    ) {
        return authenticationService.refreshToken(refreshToken);
    }


}
