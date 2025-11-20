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
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Auth.AuthRequestDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Auth.AuthResponseDTO;
import br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Auth.RefreshTokenRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuários e renovação de tokens JWT.")
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @Operation(summary = "Realizar login", description = "Autentica o usuário com e-mail e senha e retorna um token JWT e um refresh token.")
    @PostMapping
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = (User) authentication.getPrincipal();
        String token = jwtHelper.generateToken(user);
        String refreshToken = jwtHelper.generateRefreshToken(user);

        return ResponseEntity.ok(new AuthResponseDTO(token, refreshToken));
    }

    @Operation(summary = "Renovar token de acesso", description = "Valida o refresh token enviado e retorna um novo token JWT e um novo refresh token.")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody RefreshTokenRequestDTO request) {

        Optional<JwtUserData> jwtData = jwtHelper.validateToken(request.getRefreshToken());

        if (jwtData.isPresent()) {
            JwtUserData data = jwtData.get();
            User user = User.builder()
                    .userId(data.userId())
                    .email(data.email())
                    .build();

            String newToken = jwtHelper.generateToken(user);
            String newRefreshToken = jwtHelper.generateRefreshToken(user);

            return ResponseEntity.ok(new AuthResponseDTO(newToken, newRefreshToken));
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token inválido ou expirado");
    }

}
