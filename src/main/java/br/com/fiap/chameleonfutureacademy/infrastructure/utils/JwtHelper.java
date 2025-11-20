package br.com.fiap.chameleonfutureacademy.infrastructure.utils;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.infrastructure.config.JwtProperties;
import br.com.fiap.chameleonfutureacademy.infrastructure.config.JwtUserData;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtHelper {

    private final JwtProperties jwtProperties;

    private final int TOKEN_EXPIRATION_MS = 24 * 60 * 60 * 1000; // 24 hours
    private final int REFRESH_TOKEN_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000; // 7 days

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

        return JWT.create()
                .withClaim("userId", user.getUserId())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(TOKEN_EXPIRATION_MS))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public String generateRefreshToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

        return JWT.create()
                .withClaim("userId", user.getUserId())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRATION_MS))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JwtUserData> validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

            DecodedJWT decode = JWT.require(algorithm).build().verify(token);
            return Optional.of(JwtUserData.builder()
                    .userId(decode.getClaim("userId").asLong())
                    .email(decode.getSubject())
                    .build());
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }

}
