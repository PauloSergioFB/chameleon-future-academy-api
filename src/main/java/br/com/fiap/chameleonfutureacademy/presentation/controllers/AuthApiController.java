package br.com.fiap.chameleonfutureacademy.presentation.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.chameleonfutureacademy.domainmodel.User;
import br.com.fiap.chameleonfutureacademy.infrastructure.config.JwtUserData;
import br.com.fiap.chameleonfutureacademy.infrastructure.utils.JwtHelper;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Auth.AuthRequest;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Auth.AuthResponse;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Auth.RefreshTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação", description = "Endpoints responsáveis por autenticação e renovação de tokens JWT.")
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @Operation(summary = "Login", description = "Autentica usuário e retorna access token + refresh token.")
    @PostMapping
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = (User) authentication.getPrincipal();
        String token = jwtHelper.generateToken(user);
        String refreshToken = jwtHelper.generateRefreshToken(user);

        return ResponseEntity.ok(new AuthResponse(token, refreshToken));
    }

    @Operation(summary = "Renovar Token", description = "Gera novos tokens a partir de um refresh token válido.")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> login(@RequestBody RefreshTokenRequest request) {

        Optional<JwtUserData> jwtData = jwtHelper.validateToken(request.refreshToken());

        if (jwtData.isPresent()) {
            JwtUserData data = jwtData.get();
            User user = User.builder()
                    .userId(data.userId())
                    .email(data.email())
                    .build();

            String newToken = jwtHelper.generateToken(user);
            String newRefreshToken = jwtHelper.generateRefreshToken(user);

            return ResponseEntity.ok(new AuthResponse(newToken, newRefreshToken));
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token inválido ou expirado");
    }

}
