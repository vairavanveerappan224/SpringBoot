    package com.example.E_commerce.auth;



    import com.example.E_commerce.Resposes.ApiResponse;
    import com.example.E_commerce.refreshtoken.RefreshTokenEntity;
    import com.example.E_commerce.refreshtoken.RefreshTokenService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.Map;

    @RestController
    @RequestMapping("/api/auth")
    @RequiredArgsConstructor
    public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final JwtUtil jwtUtil;
        private final RefreshTokenService refreshTokenService;

        @PostMapping("/login")
        public ApiResponse<?> login(@RequestBody LoginRequest request) {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            String accessToken =
                    jwtUtil.generateToken(request.getEmail());

            RefreshTokenEntity refreshToken =
                    refreshTokenService.create(request.getEmail());

            return new ApiResponse<>(
                    200,
                    true,
                    "Login success",
                    Map.of(
                            "accessToken", accessToken,
                            "refreshToken", refreshToken.getToken(),
                            "tokenType", "Bearer",
                            "expiresIn", 3600
                    )
            );
        }
        @PostMapping("/refresh")
        public ApiResponse<?> refresh(@RequestBody Map<String, String> body) {

            String refreshToken = body.get("refreshToken");

            RefreshTokenEntity token =
                    refreshTokenService.validate(refreshToken);

            String newAccessToken =
                    jwtUtil.generateToken(token.getEmail());

            return new ApiResponse<>(
                    200,
                    true,
                    "Token refreshed",
                    Map.of(
                            "accessToken", newAccessToken,
                            "expiresIn", 3600
                    )
            );
        }
        @PostMapping("/logout")
        public void logout(Authentication authentication) {
            refreshTokenService.deleteByEmail(authentication.getName());
        }
    }

