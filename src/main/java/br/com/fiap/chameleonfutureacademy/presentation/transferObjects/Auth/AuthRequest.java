package br.com.fiap.chameleonfutureacademy.presentation.transferObjects.Auth;

import jakarta.validation.constraints.NotEmpty;

public record AuthRequest(
        @NotEmpty(message = "Email é obrigatório") String email,
        @NotEmpty(message = "Senha é obrigatória") String password) {

}
