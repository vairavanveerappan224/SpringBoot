package com.example.E_commerce.refreshtoken;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repo;

    private final long REFRESH_EXPIRY_DAYS = 1;

    public RefreshTokenEntity create(String email) {

        RefreshTokenEntity token = new RefreshTokenEntity();
        token.setEmail(email);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(
                Instant.now().plus(REFRESH_EXPIRY_DAYS, ChronoUnit.DAYS)
        );

        return repo.save(token);
    }

    public RefreshTokenEntity validate(String token) {

        RefreshTokenEntity refreshToken = repo.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }

    public void deleteByEmail(String email) {
        repo.deleteByEmail(email);
    }
}
